package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;

/***
 * Autor: Christian J. Tintin
 */

public class HogarFragment extends Fragment {

    private Spinner tipoHogarSpinner;
    private Spinner documentoHogarSpinner;
    private Spinner fuenteAguaSpinner;
    private Spinner ubicacionAguaSpinner;
    private Spinner tratamientoAguaSpinner;
    private Spinner servicioSanitarioSpinner;
    private Spinner ubicacionSanitarioSpinner;
    private Spinner servicioDuchaSpinner;
    private Spinner eliminaBasuraSpinner;
    private Spinner tipoAlumbradoSpinner;
    private Spinner energeticoCocinaSpinner;
    private EditText codigoElectricoEditText;
    private EditText numCuartosEditText;
    private EditText numDormitoriosEditText;
    private Button guardarPersonaButton;
    private RadioGroup gasParaCalefonOpcion;
    private RadioGroup terrenoAgropecuario;
    private RadioGroup terrenoAgropecuarioSi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_hogar,
                container, false);


        Bundle extra = getActivity().getIntent().getExtras();

        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();


        return item;
    }


    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {

        tipoHogarSpinner = item.findViewById(R.id.tipoHogarSpinner);
        documentoHogarSpinner = item.findViewById(R.id.documentoHogarSpinner);
        numCuartosEditText = item.findViewById(R.id.numCuartosEditText);
        numDormitoriosEditText = item.findViewById(R.id.numDormitoriosEditText);
        fuenteAguaSpinner = item.findViewById(R.id.fuenteAguaSpinner);
        ubicacionAguaSpinner = item.findViewById(R.id.ubicacionAguaSpinner);
        tratamientoAguaSpinner = item.findViewById(R.id.tratamientoAguaSpinner);
        servicioSanitarioSpinner = item.findViewById(R.id.servicioSanitarioSpinner);
        ubicacionSanitarioSpinner = item.findViewById(R.id.ubicacionSanitarioSpinner);
        servicioDuchaSpinner = item.findViewById(R.id.servicioDuchaSpinner);
        eliminaBasuraSpinner = item.findViewById(R.id.eliminaBasuraSpinner);
        tipoAlumbradoSpinner = item.findViewById(R.id.tipoAlumbradoSpinner);
        codigoElectricoEditText = item.findViewById(R.id.codigoElectricoEditText);
        energeticoCocinaSpinner = item.findViewById(R.id.energeticoCocinaSpinner);
        guardarPersonaButton = item.findViewById(R.id.guardarPersonaButton);
        gasParaCalefonOpcion = item.findViewById(R.id.gasParaCalefon);
        terrenoAgropecuario = item.findViewById(R.id.terrenoAgropecuario);
        terrenoAgropecuarioSi = item.findViewById(R.id.terrenoAgropecuarioSi);



    }

    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCamposVivienda() {

    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {

        tipoHogarSpinner.setAdapter(HogarPreguntas.getTenenciaHogarAdapter(getActivity()));
        documentoHogarSpinner.setAdapter(HogarPreguntas.getDocumentoHogarAdapter(getActivity()));
        fuenteAguaSpinner.setAdapter(HogarPreguntas.getFuenteAguaAdapter(getActivity()));
        ubicacionAguaSpinner.setAdapter(HogarPreguntas.getUbicacionAguaAdapter(getActivity()));
        tratamientoAguaSpinner.setAdapter(HogarPreguntas.getTratamientoAguaAdapter(getActivity()));
        servicioSanitarioSpinner.setAdapter(HogarPreguntas.getServicioSanitarioAdapter(getActivity()));
        ubicacionSanitarioSpinner.setAdapter(HogarPreguntas.getUbicacionSanitarioAdapter(getActivity()));
        servicioDuchaSpinner.setAdapter(HogarPreguntas.getServicioDuchaAdapter(getActivity()));
        eliminaBasuraSpinner.setAdapter(HogarPreguntas.getEliminaBasuraAdapter(getActivity()));
        tipoAlumbradoSpinner.setAdapter(HogarPreguntas.getTipoAlumbradoAdapter(getActivity()));
        energeticoCocinaSpinner.setAdapter(HogarPreguntas.getEnergeticoCocinaAdapter(getActivity()
        ));

    }

    /**
     * Método para habilitar o desabilitar los controles de la vista
     */
    public void habilitarDeshabilitar() {

    }

    /**
     * Método que guarda la vivienda en la base de datos
     */
    private void guardar() {

    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {


    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     */
    protected boolean validarCampos() {
        boolean cancel=true;
        View focusView = null;
        if (((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tenenciaHogar));
            return cancel;
        }

        if ((((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("1") ||
                ((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("2")) &&
                        ((Values) documentoHogarSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.documentoHogar));
            return cancel;
        }

        if (numCuartosEditText.getText().toString().trim().equals(""))
        {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.cuartos));
            focusView = numCuartosEditText;
            focusView.setFocusable(true);
            return cancel;
        }
        if (numDormitoriosEditText.getText().toString().trim().equals(""))
        {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.dormitorios));
            focusView = numDormitoriosEditText;
            return cancel;
        }

        if (((Values) fuenteAguaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.fuenteAgua));
            return cancel;
        }

        if (((Values) ubicacionAguaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ubicacionAgua));
            return cancel;
        }

        if (((Values) tratamientoAguaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tratamientoAgua));
            return cancel;
        }

        if (((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.servicioSanitario));
            return cancel;
        }

        if (((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("-1") &&
                !((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")
                ) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ubicacionSanitario));
            return cancel;
        }
        if (((Values) servicioDuchaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.servicioDucha));
            return cancel;
        }
        if (((Values) eliminaBasuraSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.eliminaBasura));
            return cancel;
        }
        if (((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoAlumbrado));
            return cancel;
        }
        if (codigoElectricoEditText.getText().toString().trim().equals("") &&
        ((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals("1"))
        {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.codigoElectrico));
            focusView = codigoElectricoEditText;
            return cancel;
        }
        if (((Values) energeticoCocinaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.energeticoCocina));
            return cancel;
        }

        if(gasParaCalefonOpcion.getCheckedRadioButtonId()== -1){
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.gasParaCalefon));
            return cancel;
        }

        if(terrenoAgropecuario.getCheckedRadioButtonId()== -1){
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.terrenoAgropecuario));
            return cancel;
        }

        if(terrenoAgropecuarioSi.getCheckedRadioButtonId()== -1 &&
                terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb
                ){
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.terrenoAgropecuarioSi));
            return cancel;
        }

        return false;
    }

    /***
     * Método para enviar una aleta
     * @param title
     * @param message
     */
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

    /**
     * Método para realizar las acciones
     */
    private void realizarAcciones() {

        guardarPersonaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarCampos()){
                    getAlert("Cris","Si guarda");
                }

            }
        });

    }
}
