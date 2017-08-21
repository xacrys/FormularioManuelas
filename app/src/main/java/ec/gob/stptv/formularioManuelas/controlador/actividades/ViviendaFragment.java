package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;

/***
 * Autor:Christian Tintin
 */
public class ViviendaFragment extends Fragment {

    private Spinner tipoLevantamientoSpinner;
    private Spinner areaSpinner;
    private Spinner provinciaSpinner;
    private Spinner cantonSpinner;
    private Spinner parroquiaSpinner;
    private EditText localidadEditText;
    private Spinner zonaSpinner;
    private Spinner sectorSpinner;
    private Spinner manzanaSpinner;
    private EditText divisionEditText;
    private EditText edificioEditText;
    private EditText viviendaEditText;
    private Spinner hogarInicialSpinner;
    private Spinner hogarFinalSpinner;
    private EditText calle1EditText;
    private EditText numeroCasaEditText;
    private EditText calle2EditText;
    private EditText conjuntoHabitacionalEditText;
    private EditText loteEditText;
    private EditText departamentoEditText;
    private EditText pisoEditText;
    private EditText telefonoConvencionalEditText;
    private EditText telefonoCelularEditText;
    private EditText referenciaUbicacionEditText;



    private Spinner condicionOcupacionSpinner;
    private Spinner tipoViviendaSpinner;
    private Spinner viaAccesoPrincipalSpinner;
    private Spinner materialTechoTechoSpinner;
    private Spinner materialPisoSpinner;
    private Spinner materialParedesSpinner;
    private Spinner estadoTechoSpinner;
    private Spinner estadoPisoSpinner;
    private Spinner estadoParedSpinner;

    private Button guadarButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
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

        tipoLevantamientoSpinner = item.findViewById(R.id.tipoLevantamientoSpinner);
        areaSpinner = item.findViewById(R.id.areaSpinner);
        provinciaSpinner = item.findViewById(R.id.provinciaSpinner);
        cantonSpinner = item.findViewById(R.id.cantonSpinner);
        parroquiaSpinner = item.findViewById(R.id.parroquiaSpinner);
        localidadEditText = item.findViewById(R.id.localidadEditText);
        zonaSpinner = item.findViewById(R.id.zonaSpinner);
        sectorSpinner = item.findViewById(R.id.sectorSpinner);
        localidadEditText = item.findViewById(R.id.localidadEditText);
        manzanaSpinner = item.findViewById(R.id.manzanaSpinner);
        divisionEditText = item.findViewById(R.id.divisionEditText);
        edificioEditText = item.findViewById(R.id.edificioEditText);
        viviendaEditText = item.findViewById(R.id.viviendaEditText);
        hogarInicialSpinner = item.findViewById(R.id.hogarInicialSpinner);
        hogarFinalSpinner = item.findViewById(R.id.hogarFinalSpinner);
        calle1EditText = item.findViewById(R.id.calle1EditText);
        numeroCasaEditText = item.findViewById(R.id.numeroCasaEditText);
        calle2EditText = item.findViewById(R.id.calle2EditText);
        conjuntoHabitacionalEditText = item.findViewById(R.id.conjuntoHabitacionalEditText);
        loteEditText = item.findViewById(R.id.loteEditText);
        departamentoEditText = item.findViewById(R.id.departamentoEditText);
        pisoEditText = item.findViewById(R.id.pisoEditText);
        telefonoConvencionalEditText = item.findViewById(R.id.telefonoConvencionalEditText);
        telefonoCelularEditText = item.findViewById(R.id.telefonoCelularEditText);
        referenciaUbicacionEditText = item.findViewById(R.id.referenciaUbicacionEditText);
        condicionOcupacionSpinner = item.findViewById(R.id.condicionOcupacionSpinner);

        tipoViviendaSpinner = item.findViewById(R.id.tipoViviendaSpinner);
        viaAccesoPrincipalSpinner = item.findViewById(R.id.viaAccesoPrincipalSpinner);
        materialTechoTechoSpinner = item.findViewById(R.id.materialTechoTechoSpinner);
        materialPisoSpinner = item.findViewById(R.id.materialPisoSpinner);
        materialParedesSpinner = item.findViewById(R.id.materialParedesSpinner);
        estadoTechoSpinner = item.findViewById(R.id.estadoTechoSpinner);
        estadoPisoSpinner = item.findViewById(R.id.estadoPisoSpinner);
        estadoParedSpinner = item.findViewById(R.id.estadoParedSpinner);

        guadarButton = item.findViewById(R.id.guardarButton);

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

        tipoLevantamientoSpinner.setAdapter(ViviendaPreguntas.getTipoLevantamientoAdapter(getActivity()));
        areaSpinner.setAdapter(ViviendaPreguntas.getAreaAdapter(getActivity()));
        condicionOcupacionSpinner.setAdapter(ViviendaPreguntas.getCondicionOcupacionAdapter(getActivity()));

        tipoViviendaSpinner.setAdapter(ViviendaPreguntas.getTipoViviendaAdapter(getActivity()));
        viaAccesoPrincipalSpinner.setAdapter(ViviendaPreguntas.getViviendaViaAccesoPrincipalAdapter(getActivity()));
        materialTechoTechoSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialTechoAdapter(getActivity()));
        materialPisoSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialPisoAdapter(getActivity()));
        materialParedesSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialParedesAdapter(getActivity()));
        estadoTechoSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));
        estadoPisoSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));
        estadoParedSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));



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

        boolean cancel = false;
        View focusView = null;

        if (((Values) tipoLevantamientoSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.tipoLevantamiento));
            focusView = tipoLevantamientoSpinner;
            cancel = true;
            return cancel;
        }

        if (((Values) areaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.area));
            focusView = areaSpinner;
            cancel = true;
            return cancel;
        }


        if (((Values) provinciaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.provincia));
            focusView = provinciaSpinner;
            cancel = true;
            return cancel;
        }

        if (((Values) cantonSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.canton));
            focusView = cantonSpinner;
            cancel = true;
            return cancel;
        }

        if (((Values) parroquiaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.canton));
            focusView = parroquiaSpinner;
            cancel = true;
            return cancel;
        }

        if (((Values) zonaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.zona));
            focusView = zonaSpinner;
            cancel = true;
            return cancel;
        }

        if (((Values) sectorSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.sector));
            focusView = sectorSpinner;
            cancel = true;
            return cancel;
        }

        if (!((Values) zonaSpinner.getSelectedItem()).getKey().equals("999") &&
                ((Values) manzanaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.manzana));
            focusView = manzanaSpinner;
            cancel = true;
            return cancel;
        }


        calle1EditText.setError(null);
        calle1EditText.clearFocus();
        if (TextUtils.isEmpty(calle1EditText.getText().toString().trim())) {
            calle1EditText
                    .setError(getString(R.string.errorCampoRequerido));
            focusView = calle1EditText;
            cancel = true;
        }

        return false;
    }

    /**
     * Muestra las alertas
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

        guadarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validarCampos())
                    return;

            }
        });

    }
}
