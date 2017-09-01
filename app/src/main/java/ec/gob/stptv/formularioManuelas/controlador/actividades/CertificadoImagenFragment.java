package ec.gob.stptv.formularioManuelas.controlador.actividades;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.SincronizacionVivienda;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.IntentIntegrator;
import ec.gob.stptv.formularioManuelas.controlador.util.IntentResult;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.FaseDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ImagenDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Imagen;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class CertificadoImagenFragment extends Fragment {


    Vivienda vivienda;

    private Button capturarFotoViviendaButton;


    private static final int REQUEST_CODE = 0x0000c0de;
    private static final int REQUEST_PICTURE_VIVIENDA = 1;

    private Animation myFadeInAnimation;
    private Bitmap imagenViviendaBitmap;
    private Button actualizarCertificadoButton;
    private Button limpiarButton;
    private Button actualizarCertificadoVerificarButton;
    private Button limpiarCertificadoVerificarButton;
    private Button finalizarEntrevistaButton;
    private EditText certificadoEditText;
    private EditText certificadoVerificarEditText;
    private ImageView fotoViviendaImageView;
    private TextView avisoEncuestaCompletaTextView;
    protected Uri mImagenViviendaUri;
    private Boolean botonCapturar;
    private Boolean botonCapturarVerificar;
    private ContentResolver cr;
    private Imagen imagenVivienda;
    private File photoVivienda;
    private LinearLayout pantallaCertificadoImagenLinearLayout;


    public static final String PRODUCT_MODE = "PRODUCT_MODE";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View item = inflater.inflate(
                R.layout.activity_main_fragment_certificado_imagen, container,
                false);

        cr = getActivity().getContentResolver();
        this.obtenerVistas(item);
        this.realizarAcciones();
        return item;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        linlaHeaderProgress = getActivity().findViewById(R.id.linlaHeaderProgress);
        vivienda = ViviendaFragment.getVivienda();
        Utilitarios.logError("eeeeeeeeeeeeeeeeeeeeee",""+vivienda.getId());
        if (vivienda.getId() != 0) {
            this.llenarCampos();
            if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                    && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA.getValor()) {
                Utilitarios.disableEnableViews(getActivity(), false, pantallaCertificadoImagenLinearLayout);
            }
        }
    }

    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {
        actualizarCertificadoButton = item.findViewById(R.id.actualizarCertificadoButton);
        capturarFotoViviendaButton = item.findViewById(R.id.capturarFotoViviendaButton);
        limpiarButton = item.findViewById(R.id.limpiarButton);
        certificadoEditText = item.findViewById(R.id.certificadoEditText);
        avisoEncuestaCompletaTextView = item.findViewById(R.id.avisoEncuestaCompletaTextView);
        actualizarCertificadoVerificarButton = item.findViewById(R.id.actualizarCertificadoVerificarButton);
        certificadoVerificarEditText = item.findViewById(R.id.certificadoVerificarEditText);
        limpiarCertificadoVerificarButton = item.findViewById(R.id.limpiarCertificadoVerificarButton);
        myFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        fotoViviendaImageView = item.findViewById(R.id.fotoViviendaImageView);
        finalizarEntrevistaButton = item.findViewById(R.id.finalizarEntrevistaButton);
        botonCapturar = false;
        botonCapturarVerificar = false;
        pantallaCertificadoImagenLinearLayout = item.findViewById(R.id.pantallaCertificadoImagenLinearLayout);
    }

    @Override
    public void onResume() {
        avisoEncuestaCompletaTextView.startAnimation(myFadeInAnimation);
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICTURE_VIVIENDA && resultCode == Activity.RESULT_OK) {
            if (imagenViviendaBitmap != null) {
                imagenViviendaBitmap.recycle();
            }

            imagenViviendaBitmap = BitmapFactory.decodeFile(mImagenViviendaUri.getPath());
            fotoViviendaImageView.setImageBitmap(imagenViviendaBitmap);

            if (imagenViviendaBitmap != null) {

                Imagen _imagen = ImagenDao.getImagen(cr, Imagen.whereByViviendaIdAndTipo,
                        new String[] { String.valueOf(vivienda.getId()),  String.valueOf(REQUEST_PICTURE_VIVIENDA)});

                if (_imagen == null){
                    Imagen imagen = new Imagen();
                    imagen.setIdvivienda(vivienda.getId());
                    //imagen.setCodigoequipo(vivienda.getIdentificadorequipo());pendiente
                    imagen.setImagen(Utilitarios.encodeTobase64ImageColor(imagenViviendaBitmap, Global.CALIDAD_FOTO));
                    imagen.setTipo(REQUEST_PICTURE_VIVIENDA);
                    imagen.setFecha(Utilitarios.getCurrentDate());
                    imagen.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
                    imagen.setFechasincronizacion("");
                    Uri uri = ImagenDao.save(cr, imagen);
                    if(uri != null)
                    {
                        imagenVivienda = imagen;
                    }
                }else
                {
                    _imagen.setFecha(Utilitarios.getCurrentDate());
                    _imagen.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
                    _imagen.setImagen(Utilitarios.encodeTobase64ImageColor(imagenViviendaBitmap, Global.CALIDAD_FOTO));
                    int result = ImagenDao.update(cr, _imagen);
                    if(result > 0)
                    {
                        imagenVivienda = _imagen;
                    }
                }
            }
            photoVivienda.delete();

        } else if (requestCode == REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                if (botonCapturar) {
                    certificadoEditText.setText(scanContent);
                    botonCapturar = false;
                } else if (botonCapturarVerificar) {
                    certificadoVerificarEditText.setText(scanContent);
                    botonCapturarVerificar = false;
                }


            } else {
                getAlert(getString(R.string.validacion_aviso), getString(R.string.errorCodigoBarra));
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void realizarAcciones() {

        actualizarCertificadoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, REQUEST_CODE);
                    botonCapturar = true;
                } catch (Exception e) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorLectorBarra));
                }

            }
        });

        actualizarCertificadoVerificarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, REQUEST_CODE);
                    botonCapturarVerificar = true;
                } catch (Exception e) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorLectorBarra));
                }
            }
        });

        limpiarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                certificadoEditText.setText("");
            }
        });

        limpiarCertificadoVerificarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                certificadoVerificarEditText.setText("");
            }
        });

        capturarFotoViviendaButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                photoVivienda = null;

                try {
                    photoVivienda = createTemporaryFile("imagenVivienda", ".jpg");
                    mImagenViviendaUri = Uri.fromFile(photoVivienda);
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImagenViviendaUri);
                    startActivityForResult(cameraIntent, REQUEST_PICTURE_VIVIENDA);
                    // photo.delete();
                } catch (Exception e) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorLectorFoto));
                }
            }
        });

        finalizarEntrevistaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarCampos())
                    return;
                actualizarVivienda();


            }
        });
    }


    private void getAlert(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    private Boolean validarCampos() {
        Boolean validacion = true;
        if (certificadoEditText.getText().toString().equals("")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seccionCodigoBarraVacio));
        } else if (certificadoVerificarEditText.getText().toString().equals("")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seccionCodigoBarraVerificadorVacio));
        } else if (!certificadoEditText.getText().toString().equals(certificadoVerificarEditText.getText().toString())) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seccionCodigoBarrasDiferentes));
        } else if (imagenViviendaBitmap == null) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seccionImagenViviendaVacio));
        } else {
            validacion=false;
        }
        return validacion;
    }

    /**
     * actualiza los campos de la vivienda
     */
    private void actualizarVivienda() {

        vivienda.setCertificado(certificadoEditText.getText().toString());
        vivienda.setIdcontrolentrevista(ControlPreguntas.ControlEntrevista.COMPLETA.getValor());

        if (vivienda.getEstadosincronizacion() != Global.SINCRONIZACION_CERTIFICADO_REPETIDO){
            vivienda.setNumerovisitas(vivienda.getNumerovisitas() + 1);
        }
        ViviendaDao.update(cr, vivienda);
        SincronizacionVivienda sincronizacionVivienda = new SincronizacionVivienda(getActivity());
        if (Utilitarios.verificarConexion(getActivity())) {
            sincronizacionVivienda.sincronizar(vivienda, getActivity());
        } else {
            Toast.makeText(getActivity(), "No existe conexión a internet", Toast.LENGTH_LONG).show();
            //ojo: cuando no hay inter vuelve a capturar otro certificado y pasa la cvalidaciond e duplicados entonces le pone en estado sincronizado incompleto
            if (vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_CERTIFICADO_REPETIDO) {
                vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
                ViviendaDao.update(cr, vivienda);
            }
            getActivity().finish();
        }

    }

    /**
     * Método que permite guardar la imagen
     */
    private void guardarImagen(){
        vivienda = ViviendaFragment.getVivienda();
        Imagen imagen = new Imagen();
        imagen.setIdvivienda(vivienda.getId());
        imagen.setTipo(REQUEST_PICTURE_VIVIENDA);
        imagen.setImagen(Utilitarios.encodeTobase64ImageColor(imagenViviendaBitmap, Global.CALIDAD_FOTO));
        imagen.setFecha(Utilitarios.getCurrentDate());
        Uri uri = ImagenDao.save(cr, imagen);
        if(uri != null)
        {
            imagenVivienda = imagen;
        }

    }

    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCampos() {
        if (!vivienda.getCertificado().equals(Global.CADENAS_VACIAS)) {
            certificadoEditText.setText(vivienda.getCertificado());
        } else {
            certificadoEditText.setText("");
        }

        if (!vivienda.getCertificado().equals(Global.CADENAS_VACIAS)) {
            certificadoVerificarEditText.setText(vivienda.getCertificado());
        } else {
            certificadoVerificarEditText.setText("");
        }

        this.imagenVivienda = ImagenDao.getImagen(cr,	Imagen.whereByViviendaIdAndTipo, new String[] { String.valueOf(vivienda.getId()), String.valueOf(REQUEST_PICTURE_VIVIENDA) });
        if(imagenVivienda != null)
        {
            new GetImagen().execute(
                    String.valueOf(vivienda.getId()),
                    String.valueOf(REQUEST_PICTURE_VIVIENDA));
        }
    }

    /**
     * Clase para retornar la imagen
     */
    private class GetImagen extends AsyncTask<String, Void, Bitmap> {

        String tipo = "";

        protected void onPreExecute() {

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            tipo = params[1];

            Imagen imagen = ImagenDao.getImagen(cr, Imagen.whereByViviendaIdAndTipo, params);

            Bitmap _imagen = Utilitarios.decodeBase64(imagen.getImagen());

            return _imagen;
        }

        @Override
        protected void onPostExecute(Bitmap imagen) {
            imagenViviendaBitmap = imagen;
            fotoViviendaImageView.setImageBitmap(imagen);
        }

    }

}
