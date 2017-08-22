package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;

/***
 *  Autor:Christian Tintin
 */

public class MiembrosHogarTodasPersonasFragment extends Fragment {

    private Button nuevoButton;
    private Button atrasButton;
    private Button guardarPersonaButton;
    private Spinner parentescoSpinner;
    private Spinner estadoCivilSpinner;
    private Spinner nacionalidadSpinner;
    private Spinner etniaSpinner;
    private Spinner seguroSocial1Spinner;
    private Spinner seguroSocial2Spinner;
    private Spinner seguroSalud1Spinner;
    private Spinner seguroSalud2Spinner;
    private Spinner asistenciaEstablecimientoSpinner;
    private Spinner proteccionSocialpinner;
    private Spinner sufreEnfermedadesSpinner;
    private Spinner enfermedadCatastroficaSpinner;
    private Spinner codigoPersonaMadreSpinner;
    private RadioGroup viveMadreHogarRadioGroup;
    private RadioGroup discapacidadRadioGroup;
    private RadioGroup carnetConadisRadioGroup;
    private RadioGroup discapacidadIntelectualRadioGroup;
    private RadioGroup discapacidadFisicaRadioGroup;
    private RadioGroup discapacidadCegueraRadioGroup;
    private RadioGroup discapacidadVisionRadioGroup;
    private RadioGroup discapacidadSorderaRadioGroup;
    private RadioGroup discapacidadHipoacusiaRadioGroup;
    private RadioGroup discapacidadPsicosocialesRadioGroup;
    private EditText porcentajeIntelectualEditText;
    private EditText porcentajeFisicaEditText;
    private EditText porcentajeCegueraEditText;
    private EditText porcentajeVisionEditText;
    private EditText porcentajeSorderaEditText;
    private EditText porcentajeHipoacusiaEditText;
    private EditText porcentajePsicosocialesEditText;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar_todas_personas,
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

        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);
        guardarPersonaButton = item.findViewById(R.id.guardarPersonaButton);
        parentescoSpinner = item.findViewById(R.id.parentescoSpinner);
        estadoCivilSpinner = item.findViewById(R.id.estadoCivilSpinner);
        nacionalidadSpinner = item.findViewById(R.id.nacionalidadSpinner);
        etniaSpinner = item.findViewById(R.id.etniaSpinner);
        seguroSocial1Spinner = item.findViewById(R.id.seguroSocial1Spinner);
        seguroSocial2Spinner = item.findViewById(R.id.seguroSocial2Spinner);
        seguroSalud1Spinner = item.findViewById(R.id.seguroSalud1Spinner);
        seguroSalud2Spinner = item.findViewById(R.id.seguroSalud2Spinner);
        asistenciaEstablecimientoSpinner = item.findViewById(R.id.asistenciaEstablecimientoSpinner);
        proteccionSocialpinner = item.findViewById(R.id.proteccionSocialpinner);
        sufreEnfermedadesSpinner = item.findViewById(R.id.sufreEnfermedadesSpinner);
        enfermedadCatastroficaSpinner = item.findViewById(R.id.enfermedadCatastroficaSpinner);
        codigoPersonaMadreSpinner = item.findViewById(R.id.codigoPersonaMadreSpinner);
        viveMadreHogarRadioGroup = item.findViewById(R.id.viveMadreHogarRadioGroup);
        discapacidadRadioGroup = item.findViewById(R.id.discapacidadRadioGroup);
        carnetConadisRadioGroup = item.findViewById(R.id.carnetConadisRadioGroup);
        discapacidadFisicaRadioGroup = item.findViewById(R.id.discapacidadFisicaRadioGroup);
        porcentajeIntelectualEditText = item.findViewById(R.id.porcentajeIntelectualEditText);
        porcentajeFisicaEditText = item.findViewById(R.id.porcentajeFisicaEditText);
        discapacidadCegueraRadioGroup = item.findViewById(R.id.discapacidadCegueraRadioGroup);
        discapacidadVisionRadioGroup = item.findViewById(R.id.discapacidadVisionRadioGroup);
        discapacidadSorderaRadioGroup = item.findViewById(R.id.discapacidadSorderaRadioGroup);
        discapacidadPsicosocialesRadioGroup = item.findViewById(R.id.discapacidadPsicosocialesRadioGroup);
        porcentajeCegueraEditText = item.findViewById(R.id.porcentajeCegueraEditText);
        porcentajeVisionEditText = item.findViewById(R.id.porcentajeVisionEditText);
        porcentajeSorderaEditText = item.findViewById(R.id.porcentajeSorderaEditText);
        porcentajeHipoacusiaEditText = item.findViewById(R.id.porcentajeHipoacusiaEditText);
        porcentajePsicosocialesEditText = item.findViewById(R.id.porcentajePsicosocialesEditText);



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
        parentescoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoParentescoAdapter(getActivity()));
        estadoCivilSpinner.setAdapter(PersonaPreguntas.getControlTrabajoEstadoCivilAdapter(getActivity()));
        nacionalidadSpinner.setAdapter(PersonaPreguntas.getNacionalidadAdapter(getActivity()));
        etniaSpinner.setAdapter(PersonaPreguntas.getAutoDefinicionEtnicaAdapter(getActivity()));
        seguroSocial1Spinner.setAdapter(PersonaPreguntas.getAporteSeguroAdapter(getActivity()));
        seguroSocial2Spinner.setAdapter(PersonaPreguntas.getAporteSeguroAdapter(getActivity()));
        seguroSalud1Spinner.setAdapter(PersonaPreguntas.getSeguroSaludAdapter(getActivity()));
        seguroSalud2Spinner.setAdapter(PersonaPreguntas.getSeguroSaludAdapter(getActivity()));
        proteccionSocialpinner.setAdapter(PersonaPreguntas.getServicioProteccionAdapter(getActivity()));
        asistenciaEstablecimientoSpinner.setAdapter(PersonaPreguntas.getAsistenciaEstablecimientoAdapter(getActivity()));
        sufreEnfermedadesSpinner.setAdapter(PersonaPreguntas.getSufreEnfermedadesAdapter(getActivity()));
        enfermedadCatastroficaSpinner.setAdapter(PersonaPreguntas.getEnfermedadCatastroficaAdapter(getActivity()));


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

        Boolean flag = true;
        if (((Values) parentescoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlTrabajoParentescoJefeHogar));
        } else if (((Values) estadoCivilSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlTrabajoEstadoCivilTitulo));
        } else if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlMadreHogarTitulo));
        } else if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == R.id.viveMadreHogarOpcion1RadioButton &&
                ((Values) estadoCivilSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlMadreHogarEtiqueta));
        } else if (((Values) nacionalidadSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.nacionalidad));
        } else if (((Values) etniaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.autodefinicionEtnica));
        } else if (((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                ((Values) seguroSocial2Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.aporteSeguroTitulo));
        } else if (((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) ||
                ((Values) seguroSalud2Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.seguroSalud));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.poseeDiscapacidadTitulo));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton ||
                carnetConadisRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.carneDiscapacidadTitulo));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadIntelectual));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeIntelectualEditText.getText().toString().trim())) {
            porcentajeIntelectualEditText.setError(null);
            porcentajeIntelectualEditText.clearFocus();
            porcentajeIntelectualEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadFisica));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeFisicaEditText.getText().toString().trim())) {
            porcentajeFisicaEditText.setError(null);
            porcentajeFisicaEditText.clearFocus();
            porcentajeFisicaEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadCeguera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeCegueraEditText.getText().toString().trim())) {
            porcentajeCegueraEditText.setError(null);
            porcentajeCegueraEditText.clearFocus();
            porcentajeCegueraEditText.setError(getString(R.string.errorCampoRequerido));
        }
        else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadBajaVision));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeVisionEditText.getText().toString().trim())) {
            porcentajeVisionEditText.setError(null);
            porcentajeVisionEditText.clearFocus();
            porcentajeVisionEditText.setError(getString(R.string.errorCampoRequerido));
        }
        else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadSordera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton&&
                TextUtils.isEmpty(porcentajeSorderaEditText.getText().toString().trim())) {
            porcentajeSorderaEditText.setError(null);
            porcentajeSorderaEditText.clearFocus();
            porcentajeSorderaEditText.setError(getString(R.string.errorCampoRequerido));
        }
        else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadSordera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeHipoacusiaEditText.getText().toString().trim())) {
            porcentajeHipoacusiaEditText.setError(null);
            porcentajeHipoacusiaEditText.clearFocus();
            porcentajeHipoacusiaEditText.setError(getString(R.string.errorCampoRequerido));
        }
        else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadSordera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajePsicosocialesEditText.getText().toString().trim())) {
            porcentajePsicosocialesEditText.setError(null);
            porcentajePsicosocialesEditText.clearFocus();
            porcentajePsicosocialesEditText.setError(getString(R.string.errorCampoRequerido));
        }
        else {
            flag = false;
        }
        return flag;

    }

    /**
     * mètodo para realizar las acciones
     */
    private void realizarAcciones() {

        atrasButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarVentana();

            }
        });

        guardarPersonaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validarCampos()) {
                    getAlert("HogarTodasPersonas", "Guardado Exitoso");
                }
            }
        });
    }


    /**
     * Metodo para cerrar la ventana
     */
    private void cerrarVentana() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
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

}
