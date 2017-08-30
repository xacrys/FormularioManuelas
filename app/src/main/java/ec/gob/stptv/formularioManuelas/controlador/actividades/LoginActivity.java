package ec.gob.stptv.formularioManuelas.controlador.actividades;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.WebService;
import ec.gob.stptv.formularioManuelas.controlador.util.ClaveEncriptada;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.FaseDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.UsuarioDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

import static android.content.Intent.EXTRA_EMAIL;

public class LoginActivity extends Activity {

    private ContentResolver contentResolver;

    private UserLoginTask mAuthTask = null;

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
        this.realizarAcciones();

        // Set up the login form.
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);

        findViewById(R.id.sign_in_button).setOnClickListener(
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
        sign_in_button = findViewById(R.id.guardarButton);

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
            focusView = mPasswordView;
            mPasswordView.requestFocus();
            return true;
        }

        return cancel;
    }

    /**
     * Método para realizar las acciones
     */
    private void realizarAcciones() {

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitarios.hideSoftKeyboard(LoginActivity.this);
                attemptLogin();
            }
        });
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
    }

    /**
     *Intenta iniciar sesión o registrar la cuenta especificada en el formulario de inicio de sesión.
     *Si hay errores de formulario (correo electrónico no válido, campos perdidos, etc.), el
     *Se presentan errores y no se realiza ningún intento de inicio de sesión real.
     */
    public void attemptLogin() {
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


        if (!Utilitarios.verificarConexion(this)) {
            Toast toast = Toast.makeText(this, getString(R.string.error_conectar_servidor), Toast.LENGTH_LONG);
            toast.show();

            String[] parametros = new String[]{
                    mEmailView.getText().toString(),
                    ClaveEncriptada.claveEncriptada(mPasswordView.getText().toString()),
            };
            String where = Usuario.whereByUsuarioYPassword;
            Usuario usuario = UsuarioDao.getUsuario(contentResolver, where, parametros);

            if (usuario != null) {

                Intent intent = new Intent(LoginActivity.this,
                        FormulariosActivity.class);
                finish();
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            } else {
//                    mEmailView.setError(getString(R.string.in));
                mEmailView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }

        } else {

            httpClient = new DefaultHttpClient();
            new Thread(new Runnable() {


                public void run() {
                    try {

                        mAuthTask.execute(Global.URL_WEB_SERVICE_USUARIOS).get(Global.ASYNCTASK_TIMEROUT_LOGIN, TimeUnit.MILLISECONDS);

                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (TimeoutException e1) {

                        Log.e("", "Termino el tiempo de coneccion");
                        httpClient.getConnectionManager().shutdown();

                        if (!mAuthTask.isCancelled()) {
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
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("SimpleDateFormat")
    public class UserLoginTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... params) {

            // Primero busca en la web luego localmente
            Object respuesta = null;
            contentResolver = getContentResolver();
            JSONObject values = new JSONObject();
            try {

                String user = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
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
                Utilitarios.logInfo("values", values.toString());
                Utilitarios.logInfo("respuesta", _respuesta);

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
                    String[] parametros = new String[] {
                            mEmailView.getText().toString(),
                            mPasswordView.getText().toString(),
                    };

                    String where= Usuario.whereByUsuarioYPassword;
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

            // Usuario de pruebas

			/*
			 * Usuario usuario = new Usuario();
			 *
			 * usuario.setUsuario("2100511829");
			 * usuario.setCodido("2100511829"); usuario.setIdUsuario(12);
			 * usuario.setNombres("Christian"); usuario.setApellidos("Sasig");
			 * usuario.setCedula("2100511829"); usuario.setCodigoGrupo(14);
			 * usuario.setCodigoDispositivo(45); usuario.setRol("user");
			 * usuario.setEstado(1);
			 * usuario.setDireccionFisica(getMacAddress());
			 *
			 * usuario.setMaxVivCodigo("13-7");
			 *
			 * finish(); Intent intent = new Intent(LoginActivity.this,
			 * FormulariosActivity.class); intent.putExtra("usuario", usuario);
			 *
			 * startActivity(intent);
			 */


        }

        @Override
        protected void onCancelled() {

//            Log.e("ddddddddddd", "Login onCancelled");
//
//            String[] parametros = new String[] {
//                    mEmailView.getText().toString(),
//                    mPasswordView.getText().toString(),
//            };
//
//            Cursor cursor = cr.query(
//                    RegistroSocialProvider.CONTENT_URI_USUARIO,
//                    Usuario.COLUMNAS, Usuario.whereByUsuarioYPassword,
//                    parametros, Usuario.COLUMNA_ID_USUARIO);
//
//            Usuario usuario = UsuarioDao.getUsuarioPorId(cursor);
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
//
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    getString(R.string.error_conectar_servidor),
//                    Toast.LENGTH_LONG);
//            toast.show();

            //mAuthTask = null;
            //showProgress(false);
        }
    }







}
