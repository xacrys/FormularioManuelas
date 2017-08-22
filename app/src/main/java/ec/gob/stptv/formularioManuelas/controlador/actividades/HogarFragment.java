package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.*;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
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
    private Spinner numCuartosSpinner;
    private Spinner numDormitoriosSpinner;
    private Button guardarPersonaButton;
    private Button atrasButton;
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
        this.mallasValidacion();


        return item;
    }


    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {

        tipoHogarSpinner = item.findViewById(R.id.tipoHogarSpinner);
        documentoHogarSpinner = item.findViewById(R.id.documentoHogarSpinner);
        numCuartosSpinner = item.findViewById(R.id.numCuartosSpinner);
        numDormitoriosSpinner = item.findViewById(R.id.numDormitoriosSpinner);
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
        atrasButton = item.findViewById(R.id.atrasButton);


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
        energeticoCocinaSpinner.setAdapter(HogarPreguntas.getEnergeticoCocinaAdapter(getActivity()));
        numCuartosSpinner.setAdapter(HogarPreguntas.getNumCuartosAdapter(getActivity()));
        numDormitoriosSpinner.setAdapter(HogarPreguntas.getNumDormitoriosAdapter(getActivity()));

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

        //Listener para cuando cambia la elección Tipo de Hogar
        tipoHogarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("1") ||
                        ((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("2")) {
                    documentoHogarSpinner.setEnabled(true);
                }
                else
                {
                    documentoHogarSpinner.setSelection(0);
                    documentoHogarSpinner.setEnabled(false);
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Listener para cuando cambia la elección cuenta el Hogar con Servicio Higiénico
        servicioSanitarioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")) {
                    ubicacionSanitarioSpinner.setSelection(0);
                    ubicacionSanitarioSpinner.setEnabled(false);
                }
                else{
                    ubicacionSanitarioSpinner.setEnabled(true);
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Listener para cuando cambia la elección cuenta el Hogar con Servicio Higiénico
        tipoAlumbradoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey())== 1) {
                    codigoElectricoEditText.setEnabled(true);
                }
                else{
                    codigoElectricoEditText.setText("");
                    codigoElectricoEditText.setEnabled(false);
                    codigoElectricoEditText.setError(null);
                    codigoElectricoEditText.clearFocus();
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        terrenoAgropecuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb){
                    for (int cont = 0; cont < terrenoAgropecuarioSi.getChildCount(); cont++) {
                        terrenoAgropecuarioSi.getChildAt(cont).setEnabled(true);
                    }
                }
                else
                {
                    terrenoAgropecuarioSi.check(-1);
                    for (int cont = 0; cont < terrenoAgropecuarioSi.getChildCount(); cont++) {
                        terrenoAgropecuarioSi.getChildAt(cont).setEnabled(false);
                    }
                }

            }
        });

    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     */
    protected boolean validarCampos() {
        boolean cancel = true;
        View focusView = null;
        if (((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tenenciaHogar));
        } else if ((((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("1") ||
                ((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("2")) &&
                ((Values) documentoHogarSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.documentoHogar));
        } else if (((Values) numCuartosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.cuartos));
        } else if (((Values) numDormitoriosSpinner
                .getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.dormitorios));
//            focusView = numDormitoriosEditText;
        } else if (((Values) fuenteAguaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.fuenteAgua));
        } else if (((Values) ubicacionAguaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ubicacionAgua));
        } else if (((Values) tratamientoAguaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tratamientoAgua));
        } else if (((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.servicioSanitario));
        } else if (((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                !((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")
                ) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ubicacionSanitario));
        } else if (((Values) servicioDuchaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.servicioDucha));
        } else if (((Values) eliminaBasuraSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.eliminaBasura));
        } else if (((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoAlumbrado));
        } else if (codigoElectricoEditText.getText().toString().trim().equals("") &&
                ((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals("1")) {
            codigoElectricoEditText.setError(null);
            codigoElectricoEditText.clearFocus();
            codigoElectricoEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (((Values) energeticoCocinaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.energeticoCocina));
        } else if (gasParaCalefonOpcion.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.gasParaCalefon));
        } else if (terrenoAgropecuario.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.terrenoAgropecuario));
        } else if (terrenoAgropecuarioSi.getCheckedRadioButtonId() == -1 &&
                terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb
                ) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.terrenoAgropecuarioSi));
        } else {
            cancel = false;
        }
        return cancel;
    }

    /***
     * Método para enviar una aleta
     * @param title me permite agregar un titulo
     * @param message me permite agregar el mensaje
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
                if (!validarCampos()) {
                    getAlert("Cris", "Si guarda");
                }

            }
        });

        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ViviendaFragment();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


    }
}
