package ec.gob.stptv.formularioManuelas.controlador.actividades;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.servicio.LocalizacionService;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.WebService;
import ec.gob.stptv.formularioManuelas.controlador.util.ClaveEncriptada;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.FaseDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.UsuarioDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

import static android.content.Intent.EXTRA_EMAIL;
import static android.content.Intent.makeMainSelectorActivity;

public class LoginActivity extends Activity {

    private ContentResolver contentResolver;

    private UserLoginTask mAuthTask = null;
    private VivcodigoTask vivcodigoTask = null;

    private String mEmail;
    private String mPassword;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;
    private Button sign_in_button;
    Intent msgIntent;

    private DefaultHttpClient httpClient;
    PackageInfo infoApp;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utilitarios.createFolder("/logs/formularioManuelas");
        setContentView(R.layout.activity_login);
        try
        {
            infoApp = getPackageManager().getPackageInfo(getPackageName(), 0);
            setTitle(getTitle() + " V. " + infoApp.versionName + " " + Global.ENVIRONMENT + " - VC. " + infoApp.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        contentResolver = getContentResolver();
        this.obtenerVistas();

        // Set up the login form.
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);

        mPasswordView
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == R.id.login || id == EditorInfo.IME_NULL) {
                            attemptLogin();
                            return true;
                        }
                        return false;
                    }
                });

        sign_in_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utilitarios.hideSoftKeyboard(LoginActivity.this);
                        attemptLogin();
                    }
                });
    }

    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas() {
        mEmailView = findViewById(R.id.usuario);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = findViewById(R.id.login_status_message);
        sign_in_button = findViewById(R.id.sign_in_button);

    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     */
    protected boolean validarCampos() {

        boolean cancel = false;
        View focusView = null;

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        // valida usuario.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.errorCampoRequerido));
            mEmailView.requestFocus();
           return true;
        }
        //valida password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.errorCampoRequerido));
            mPasswordView.requestFocus();
            return true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.errorPasswordInvalido));
            mPasswordView.requestFocus();
            return true;
        }

        return cancel;
    }

    /**
     *Intenta iniciar sesión o registrar la cuenta especificada en el formulario de inicio de sesión.
     *Si hay errores de formulario (correo electrónico no válido, campos perdidos, etc.), el
     *Se presentan errores y no se realiza ningún intento de inicio de sesión real.
     */
    public void attemptLogin() {
        Utilitarios.logError("mAuthTask", mAuthTask+"");
        Utilitarios.logError("vivcodigoTask", vivcodigoTask+"");
        if (mAuthTask != null) {
            return;
        }
        if (validarCampos())
            return;

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
        showProgress(true);
        mAuthTask = new UserLoginTask();

        if (!Utilitarios.verificarConexion(this))
        {
            Toast toast = Toast.makeText(this,getString(R.string.error_conectar_servidor),Toast.LENGTH_LONG);
            toast.show();
            String[] parametros = new String[]{
                    mEmailView.getText().toString(),
                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};
            String where = Usuario.whereByUsuarioYPassword;
            Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
            if (usuario != null) {

                Intent intent = new Intent(LoginActivity.this,
                        FormulariosActivity.class);
                finish();
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
            else
            {
                mEmailView.setError(getString(R.string.error_incorrect_password));
                mEmailView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }
        }
        else
        {
            httpClient = new DefaultHttpClient();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        mAuthTask.execute(Global.URL_WEB_SERVICE_USUARIOS).get(Global.ASYNCTASK_TIMEOUT_LOGIN, TimeUnit.MILLISECONDS);

                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (TimeoutException e1) {

                        Log.e("", "Termino el tiempo de coneccion");
                        httpClient.getConnectionManager().shutdown();

                        if(!mAuthTask.isCancelled()){
                            mAuthTask.cancel(true);
                        }
                        e1.printStackTrace();
                    }
                }
            }).start();
        }


    }

    /**
     *
     Muestra la interfaz de usuario de progreso y oculta el formulario de inicio de sesión.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        Utilitarios.logError("si entra al show progress", "show progresas");
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        mLoginStatusView.setVisibility(View.VISIBLE);
        mLoginStatusView.animate().setDuration(shortAnimTime)
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoginStatusView.setVisibility(show ? View.VISIBLE
                                : View.GONE);
                    }
                });

        mLoginFormView.setVisibility(View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime)
                .alpha(show ? 0 : 1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoginFormView.setVisibility(show ? View.GONE
                                : View.VISIBLE);
                    }
                });
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("SimpleDateFormat")
    public class UserLoginTask extends AsyncTask<String, Void, Object> {
        protected Object doInBackground(String... params) {

            // Primero busca en la web luego localmente

            Object respuesta = null;
            contentResolver = getContentResolver();

            JSONObject values = new JSONObject();

            try {

                String user = mEmailView.getText().toString();
                String password = ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString());
                String imei = Utilitarios.getImeiDispositivo(getApplicationContext());
                if (TextUtils.isEmpty(imei)) {
                    String[] parametros = new String[] {user, password};
                    String where= Usuario.whereByUsuarioYPassword;
                    Usuario usuarioBD = UsuarioDao.getUsuario(contentResolver,where, parametros );
                    if(usuarioBD != null)
                    {
                        imei = usuarioBD.getImei();
                    }
                }

                values.put("login", user);
                values.put("password", password);
                values.put("imei", imei);
                values.put("version", infoApp.versionName);

                String _respuesta = WebService.getJsonData(params[0], values, httpClient);
                Utilitarios.logError("values", values.toString());
                Utilitarios.logError("respuesta", _respuesta);

                if (!_respuesta.equals(""))
                {
                    try
                    {
                        respuesta = new JSONObject(_respuesta);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
                else
                {
                    Utilitarios.logError("1 nada pero busca en la base","error busca en la base");
                    String where= Usuario.whereByUsuarioYPassword;
                    String[] parametros = new String[] {
                            mEmailView.getText().toString(),
                            ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};
                    Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
                    if (usuario != null) {

                        respuesta = usuario;

                        Utilitarios.logInfo(Usuario.class.getName(), usuario.toString());
                    } else
                    {
                        Utilitarios.logInfo(Usuario.class.getName(),"no existen el usuario en la BD local");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }

            return respuesta;
        }

        @Override
        protected void onPostExecute(final Object _respuesta) {


             //Usuario de pruebas
//            Usuario usuario = new Usuario();
//            usuario.setUsuario("0503380164");
//            usuario.setPassword("0503380164");
//            usuario.setIdusuario("12");
//            usuario.setNombres("Lorena");
//            usuario.setApellidos("Morales");
//            usuario.setCedula("050338014");
//            usuario.setIddispositivo(2);
//            usuario.setCodigo(1);
//            usuario.setMaxvivcodigo("2-1");
//            finish();
//            Intent intent = new Intent(LoginActivity.this,
//                    FormulariosActivity.class);
//            intent.putExtra("usuario", usuario);
//            String where;
//            String parametros[];
//            where = Fase.whereById;
//            parametros = new String[] {String.valueOf(1)};
//            Fase fase = FaseDao.getFase(contentResolver, where, parametros);
//            if (fase == null){
//                fase = new Fase();
//                fase.setId(1);
//                fase.setFechainicio(Utilitarios.getCurrentDate());
//                fase.setFechafin(Utilitarios.getCurrentDate());
//                fase.setNombrefase("FASE MANUELAS");
//                fase.setEstado(1);
//                FaseDao.save(contentResolver, fase);
//            }
//
//            Utilitarios.logError("resouesta del login", ""+_respuesta);
//            startActivity(intent);

            JSONObject respuesta = null;
            if (_respuesta != null ) {
                if (_respuesta.getClass() == Usuario.class)
                {
                    Intent intent = new Intent(LoginActivity.this, FormulariosActivity.class);
                    finish();
                    intent.putExtra("usuario", (Usuario) _respuesta);
                    startActivity(intent);
                }
                if (_respuesta.getClass() == JSONObject.class) {
                    try {
                        respuesta = (JSONObject) _respuesta;
                        Integer codigo = respuesta.getInt("codigo");
                        switch (codigo) {
                            case 0:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.error_servidor));
                                break;
                            case 1:
                                /*Date fechaActual = new Date();
                                DateFormat formato = new SimpleDateFormat(
                                        "yyyy-MM-dd");

                                String[] parametros = new String[] {
                                        respuesta.getString("usuario"),
                                        respuesta.getString("contrasenia") };

                                String where= Usuario.whereByUsuarioYPassword;
                                Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
                                if (usuario != null)
                                {

                                    usuario.setIdusuario(respuesta.getString("idUsuario"));
                                    usuario.setNombres(respuesta.getString("nombres"));
                                    usuario.setApellidos(respuesta.getString("apellidos"));
                                    usuario.setCedula(respuesta.getString("cedula"));
                                    //usuario.setCodigoGrupo(respuesta.getInt("codigoGrupo"));
                                    usuario.setIddispositivo(respuesta.getInt("idDispositivo"));
                                    //usuario.setEstado(respuesta.getInt("estado"));
                                    //usuario.setMaxVivCodigo(respuesta.getString("maxVivCodigo"));
                                    UsuarioDao.update(contentResolver,usuario);
                                }
                                else
                                {
                                    Log.e("", "Entra a crear el usuario");
                                    Usuario _usuario = new Usuario();
                                    _usuario.setUsuario(respuesta.getString("usuario"));
                                    _usuario.setPassword(respuesta.getString("contrasenia"));
                                    _usuario.setIdusuario(respuesta.getString("idUsuario"));
                                    _usuario.setNombres(respuesta.getString("nombres"));
                                    _usuario.setApellidos(respuesta.getString("apellidos"));
                                    _usuario.setCedula(respuesta.getString("cedula"));
                                    //_usuario.setCodigoGrupo(respuesta.getInt("codigoGrupo"));
                                    _usuario.setIddispositivo(respuesta.getInt("idDispositivo"));
                                    //_usuario.setRol("user");
                                    //_usuario.setEstado(respuesta.getInt("estado"));
                                    _usuario.setImei(Utilitarios.getImeiDispositivo(getApplicationContext()));
                                    //_usuario.setMaxVivCodigo(respuesta.getString("maxVivCodigo"));
                                    _usuario.setFecharegistro(formato.format(fechaActual));
                                    _usuario.setCodigo(respuesta.getInt("codigo"));
                                    UsuarioDao.save(contentResolver,_usuario);
                                    usuario = _usuario;
                                }
                                String idFase = respuesta.getString("idFase");
                                parametros = new String[] {idFase};
                                Fase fase = FaseDao.getFase(contentResolver, Fase.whereById,parametros );
                                if (fase == null){
                                    fase = new Fase();
                                    fase.setId(Integer.parseInt(idFase));
                                    fase.setFechainicio(respuesta.getString("fechaInicio"));
                                    fase.setFechafin(respuesta.getString("fechaFin"));
                                    fase.setNombrefase(respuesta.getString("fase"));
                                    //fase.setNombreOperativo(respuesta.getString("nombreOperativo"));
                                    fase.setEstado(1);
                                    FaseDao.save(contentResolver,fase);
                                    FaseDao.updateEstadoFases(contentResolver,fase);
                                    Utilitarios.printObject(fase);
                                }else{
                                    fase.setId(Integer.parseInt(idFase));
                                    fase.setFechainicio(respuesta.getString("fechaInicio"));
                                    fase.setFechafin(respuesta.getString("fechaFin"));
                                    fase.setNombrefase(respuesta.getString("fase"));
                                    //fase.setNombreOperativo(respuesta.getString("nombreOperativo"));
                                    fase.setEstado(1);
                                    FaseDao.update(contentResolver, fase);
                                    FaseDao.updateEstadoFases(contentResolver,fase);
                                    Utilitarios.printObject(fase);
                                }*/

                                getVivcodigo(respuesta);
                                break;

                            case -1:
                                mAuthTask = null;
                                showProgress(false);
                                mEmailView.setError(getString(R.string.error_incorrect_password));
                                mEmailView.requestFocus();
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.error_incorrect_password));
                                break;

                            case -2:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.error_asignacion_tablet));
                                break;

                            case -3:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.error_asignacion_fase));
                                break;

                            case -4:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.error_version));
                                break;

                            case -5:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoFase));
                                break;

                            case -6:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoVersion));
                                break;

                            case -7:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorFaseVersion));
                                break;

                            case -8:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoFaseVersion));
                                break;
                            case -9:
                                mAuthTask = null;
                                showProgress(false);
                                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorRol));
                                break;

                            default:
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else
            {
                Log.e("ddddddddddd", "Login - Error en el servidor");
                String[] parametros = new String[] {
                        mEmailView.getText().toString(),
                        ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};
                String where= Usuario.whereByUsuarioYPassword;
                Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
                if (usuario != null) {
                    getVivcodigo(respuesta);
                }
                else
                {
                    mEmailView.setError(getString(R.string.error_incorrect_password));
                    mEmailView.requestFocus();
                    mAuthTask = null;
                    showProgress(false);
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.error_conectar_servidor),
                        Toast.LENGTH_LONG);
                toast.show();
            }


        }

        @Override
        protected void onCancelled() {

            Log.e("ddddddddddd", "Login onCancelled");
//            String[] parametros = new String[] {
//                    mEmailView.getText().toString(),
//                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString()),
//            };
//
//            String where= Usuario.whereByUsuarioYPassword;
//            Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
//
//            if (usuario != null) {
//
//                Intent intent = new Intent(LoginActivity.this,
//                        FormulariosActivity.class);
//                finish();
//                intent.putExtra("usuario",usuario);
//                startActivity(intent);
//            }
//            else
//            {
//                mEmailView.setError(getString(R.string.error_incorrect_password));
//                mEmailView.requestFocus();
//                mAuthTask = null;
//                showProgress(false);
//            }

            //esto le puse ya q de aki no deberia pasar sino desde el oncancel del vivcodigo
            mEmailView.setError(getString(R.string.error_incorrect_password));
            mEmailView.requestFocus();
            mAuthTask = null;
            showProgress(false);

            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.error_conectar_servidor),
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onResume() {
        /**
         * Iniciar el servicio de localizacion
         */
        try {
            LocationManager locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                showSettingsAlert();
            } else {
                msgIntent = new Intent(LoginActivity.this,
                        LocalizacionService.class);
                msgIntent.putExtra("intervalo", "Intervalo 10s");
                startService(msgIntent);
            }
        } catch (Exception e) {

        }
        super.onResume();
    }

    /**
     * muestra una alerta q no esta habiliadp gps
     */
    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Configuracion del GPS");
        alertDialog
                .setMessage("GPS no esta habilitado. Puede habilitar el GPS en configuraciones?");

        alertDialog.setPositiveButton("Configuracion",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialog.cancel();

                    }
                });

        alertDialog.show();
    }

    /**
     * muestra mensajes de alerta
     * @param title
     * @param message
     */
    private void getAlert(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message).setTitle(title);

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     *Obtiene el viv codigo
     */
    public void getVivcodigo(final JSONObject _respuesta) {

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
        showProgress(true);
        vivcodigoTask = new VivcodigoTask();

        if (!Utilitarios.verificarConexion(this))
        {
            Toast toast = Toast.makeText(this,getString(R.string.error_conectar_servidor),Toast.LENGTH_LONG);
            toast.show();
            String[] parametros = new String[]{
                    mEmailView.getText().toString(),
                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};
            String where = Usuario.whereByUsuarioYPassword;
            Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
            if (usuario != null) {
                Intent intent = new Intent(LoginActivity.this,
                        FormulariosActivity.class);
                finish();
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
            else
            {
                mEmailView.setError(getString(R.string.error_incorrect_password));
                mEmailView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }
        }
        else
        {
            httpClient = new DefaultHttpClient();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //vivcodigoTask.execute(Global.URL_WEB_SERVICE_VIVCODIGO).get(Global.ASYNCTASK_TIMEOUT_LOGIN, TimeUnit.MILLISECONDS);
                        vivcodigoTask.execute(_respuesta, httpClient).get(Global.ASYNCTASK_TIMEOUT_LOGIN, TimeUnit.MILLISECONDS);

                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (TimeoutException e1) {

                        Log.e("", "Termino el tiempo de coneccion del viv codigo");
                        httpClient.getConnectionManager().shutdown();
                        if(!vivcodigoTask.isCancelled()){
                            vivcodigoTask.cancel(true);
                        }
                        e1.printStackTrace();
                    }
                }
            }).start();
        }

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("SimpleDateFormat")
    public class VivcodigoTask extends AsyncTask<Object, Void, String> {
        JSONObject respuestaUsuario;
        DefaultHttpClient httpClient;

        protected String doInBackground(Object... params) {

            respuestaUsuario = (JSONObject)params[0];
            httpClient = (DefaultHttpClient)params[1];

            String[] parametros = new String[]{
                    mEmailView.getText().toString(),
                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString()),
            };
            JSONObject values = new JSONObject();
            try {
                if (respuestaUsuario!= null){
                    values.put("dispositivo", respuestaUsuario.getString("idDispositivo"));
                    values.put("imei", Utilitarios.getImeiDispositivo(getApplicationContext()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String _respuesta = WebService.getJsonData(Global.URL_WEB_SERVICE_VIVCODIGO, values, httpClient);
            Utilitarios.logInfo("values", values.toString());
            Utilitarios.logInfo("respuesta", _respuesta);
            return _respuesta;

        }

        @Override
        protected void onPostExecute(final String _respuesta) {
            if (_respuesta != null && !_respuesta.equals("")) {
                if (!_respuesta.equals(String.valueOf(Global.SINCRONIZACION_INCOMPLETA))) {

//                    String[] parametros = new String[]{
//                            mEmailView.getText().toString(),
//                            ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString()),
//                    };
//                    String where = Usuario.whereByUsuarioYPassword;
//                    Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
//                    usuario.setMaxvivcodigo(_respuesta);
//                    UsuarioDao.update(contentResolver, usuario);
//                    Intent intent = new Intent(LoginActivity.this,
//                            FormulariosActivity.class);
//                    finish();
//                    intent.putExtra("usuario", usuario);
//                    startActivity(intent);

                    try {
                        Date fechaActual = new Date();
                        DateFormat formato = new SimpleDateFormat(
                                "yyyy-MM-dd");

                        String[] parametros = new String[]{respuestaUsuario.getString("usuario"),
                                respuestaUsuario.getString("contrasenia")};

                        String where = Usuario.whereByUsuarioYPassword;
                        Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);
                        if (usuario != null) {

                            usuario.setIdusuario(respuestaUsuario.getString("idUsuario"));
                            usuario.setNombres(respuestaUsuario.getString("nombres"));
                            usuario.setApellidos(respuestaUsuario.getString("apellidos"));
                            usuario.setCedula(respuestaUsuario.getString("cedula"));
                            //usuario.setCodigoGrupo(respuesta.getInt("codigoGrupo"));
                            usuario.setIddispositivo(respuestaUsuario.getInt("idDispositivo"));
                            //usuario.setEstado(respuesta.getInt("estado"));
                            usuario.setMaxvivcodigo(_respuesta);
                            UsuarioDao.update(contentResolver, usuario);
                        } else {
                            Log.e("", "Entra a crear el usuario");
                            Usuario _usuario = new Usuario();
                            _usuario.setUsuario(respuestaUsuario.getString("usuario"));
                            _usuario.setPassword(respuestaUsuario.getString("contrasenia"));
                            _usuario.setIdusuario(respuestaUsuario.getString("idUsuario"));
                            _usuario.setNombres(respuestaUsuario.getString("nombres"));
                            _usuario.setApellidos(respuestaUsuario.getString("apellidos"));
                            _usuario.setCedula(respuestaUsuario.getString("cedula"));
                            //_usuario.setCodigoGrupo(respuesta.getInt("codigoGrupo"));
                            _usuario.setIddispositivo(respuestaUsuario.getInt("idDispositivo"));
                            //_usuario.setRol("user");
                            //_usuario.setEstado(respuesta.getInt("estado"));
                            _usuario.setImei(Utilitarios.getImeiDispositivo(getApplicationContext()));
                            _usuario.setMaxvivcodigo(_respuesta);
                            _usuario.setFecharegistro(formato.format(fechaActual));
                            _usuario.setCodigo(respuestaUsuario.getInt("codigo"));
                            UsuarioDao.save(contentResolver, _usuario);
                            usuario = _usuario;
                        }
                        String idFase = respuestaUsuario.getString("idFase");
                        parametros = new String[]{idFase};
                        Fase fase = FaseDao.getFase(contentResolver, Fase.whereById, parametros);
                        if (fase == null) {
                            fase = new Fase();
                            fase.setId(Integer.parseInt(idFase));
                            fase.setFechainicio(respuestaUsuario.getString("fechaInicio"));
                            fase.setFechafin(respuestaUsuario.getString("fechaFin"));
                            fase.setNombrefase(respuestaUsuario.getString("fase"));
                            //fase.setNombreOperativo(respuesta.getString("nombreOperativo"));
                            fase.setEstado(1);
                            FaseDao.save(contentResolver, fase);
                            FaseDao.updateEstadoFases(contentResolver, fase);
                            Utilitarios.printObject(fase);
                        } else {
                            fase.setId(Integer.parseInt(idFase));
                            fase.setFechainicio(respuestaUsuario.getString("fechaInicio"));
                            fase.setFechafin(respuestaUsuario.getString("fechaFin"));
                            fase.setNombrefase(respuestaUsuario.getString("fase"));
                            //fase.setNombreOperativo(respuesta.getString("nombreOperativo"));
                            fase.setEstado(1);
                            FaseDao.update(contentResolver, fase);
                            FaseDao.updateEstadoFases(contentResolver, fase);
                            Utilitarios.printObject(fase);
                        }
                        Intent intent = new Intent(LoginActivity.this, FormulariosActivity.class);
                        finish();
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    vivcodigoTask = null;
                    mAuthTask = null;
                    showProgress(false);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.error_servidor),
                            Toast.LENGTH_LONG);
                    toast.show();

                }
            }
            else{
                String[] parametros = new String[]{
                        mEmailView.getText().toString(),
                        ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};
                String where = Usuario.whereByUsuarioYPassword;
                Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);

                if (usuario != null) {
                    Intent intent = new Intent(LoginActivity.this,
                            FormulariosActivity.class);
                    finish();
                    intent.putExtra("usuario",usuario);
                    startActivity(intent);
                }else
                {
                    mEmailView.setError(getString(R.string.error_incorrect_password));
                    mEmailView.requestFocus();
                    vivcodigoTask = null;
                    mAuthTask = null;
                    showProgress(false);
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.error_conectar_servidor),
                        Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        protected void onCancelled() {
            String[] parametros = new String[] {
                    mEmailView.getText().toString(),
                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString())};

            String where= Usuario.whereByUsuarioYPassword;
            Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);

            if (usuario != null) {

                Intent intent = new Intent(LoginActivity.this,
                        FormulariosActivity.class);
                finish();
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
            else
            {
                mEmailView.setError(getString(R.string.error_incorrect_password));
                mEmailView.requestFocus();
                vivcodigoTask = null;
                mAuthTask = null;
                showProgress(false);
            }

            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.error_conectar_servidor),
                    Toast.LENGTH_LONG);
            toast.show();

        }
    }



}
