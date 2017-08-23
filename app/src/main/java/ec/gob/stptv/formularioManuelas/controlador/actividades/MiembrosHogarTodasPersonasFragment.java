package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private RadioGroup necesitaAyudaTecnicaRadioGroup;
    private RadioGroup recibioAyudaTecnicaRadioGroup;
    private RadioGroup sillaRuedasRadioGroup;
    private RadioGroup muletasRadioGroup;
    private RadioGroup andadoresRadioGroup;
    private RadioGroup bastonApoyoRadioGroup;
    private RadioGroup ortesisRadioGroup;
    private RadioGroup colchonRadioGroup;
    private RadioGroup cojinRadioGroup;
    private RadioGroup bastonRastreoRadioGroup;
    private RadioGroup abacoRadioGroup;
    private RadioGroup computadoraRadioGroup;
    private RadioGroup implantesRadioGroup;
    private RadioGroup cochePostularRadioGroup;
    private RadioGroup panialesRadioGroup;
    private RadioGroup sillaBaniarseRadioGroup;
    private RadioGroup camasRadioGroup;
    private RadioGroup otrosRadioGroup;
    private RadioGroup audifonosRadioGroup;
    private RadioGroup gobiernoCentralRadioGroup;
    private RadioGroup gobiernoAutonomoRadioGroup;
    private RadioGroup organizacionPrivadaRadioGroup;
    private RadioGroup ningunaRadioGroup;
    private RadioGroup diagnosticoMedicoRadioGroup;
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
        this.mallasValidacion();
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
        discapacidadIntelectualRadioGroup = item.findViewById(R.id.discapacidadIntelectualRadioGroup);
        discapacidadHipoacusiaRadioGroup = item.findViewById(R.id.discapacidadHipoacusiaRadioGroup);
        porcentajeIntelectualEditText = item.findViewById(R.id.porcentajeIntelectualEditText);
        porcentajeFisicaEditText = item.findViewById(R.id.porcentajeFisicaEditText);
        discapacidadCegueraRadioGroup = item.findViewById(R.id.discapacidadCegueraRadioGroup);
        discapacidadVisionRadioGroup = item.findViewById(R.id.discapacidadVisionRadioGroup);
        discapacidadSorderaRadioGroup = item.findViewById(R.id.discapacidadSorderaRadioGroup);
        discapacidadPsicosocialesRadioGroup = item.findViewById(R.id.discapacidadPsicosocialesRadioGroup);
        necesitaAyudaTecnicaRadioGroup = item.findViewById(R.id.necesitaAyudaTecnicaRadioGroup);
        recibioAyudaTecnicaRadioGroup = item.findViewById(R.id.recibioAyudaTecnicaRadioGroup);
        sillaRuedasRadioGroup = item.findViewById(R.id.sillaRuedasRadioGroup);
        muletasRadioGroup = item.findViewById(R.id.muletasRadioGroup);
        andadoresRadioGroup = item.findViewById(R.id.andadoresRadioGroup);
        bastonApoyoRadioGroup = item.findViewById(R.id.bastonApoyoRadioGroup);
        ortesisRadioGroup = item.findViewById(R.id.ortesisRadioGroup);
        colchonRadioGroup = item.findViewById(R.id.colchonRadioGroup);
        cojinRadioGroup = item.findViewById(R.id.cojinRadioGroup);
        bastonRastreoRadioGroup = item.findViewById(R.id.bastonRastreoRadioGroup);
        abacoRadioGroup = item.findViewById(R.id.abacoRadioGroup);
        computadoraRadioGroup = item.findViewById(R.id.computadoraRadioGroup);
        implantesRadioGroup = item.findViewById(R.id.implantesRadioGroup);
        cochePostularRadioGroup = item.findViewById(R.id.cochePostularRadioGroup);
        panialesRadioGroup = item.findViewById(R.id.panialesRadioGroup);
        sillaBaniarseRadioGroup = item.findViewById(R.id.sillaBaniarseRadioGroup);
        camasRadioGroup = item.findViewById(R.id.camasRadioGroup);
        otrosRadioGroup = item.findViewById(R.id.otrosRadioGroup);
        audifonosRadioGroup = item.findViewById(R.id.audifonosRadioGroup);
        gobiernoCentralRadioGroup = item.findViewById(R.id.gobiernoCentralRadioGroup);
        gobiernoAutonomoRadioGroup = item.findViewById(R.id.gobiernoAutonomoRadioGroup);
        organizacionPrivadaRadioGroup = item.findViewById(R.id.organizacionPrivadaRadioGroup);
        ningunaRadioGroup = item.findViewById(R.id.ningunaRadioGroup);
        diagnosticoMedicoRadioGroup = item.findViewById(R.id.diagnosticoMedicoRadioGroup);
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

        discapacidadRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion2RadioButton) {
                    carnetConadisRadioGroup.check(-1);
                    for (int cont = 0; cont < carnetConadisRadioGroup.getChildCount(); cont++) {
                        carnetConadisRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    discapacidadIntelectualRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadIntelectualRadioGroup.getChildCount(); cont++) {
                        discapacidadIntelectualRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeIntelectualEditText.setEnabled(false);
                    porcentajeIntelectualEditText.setText("");
                    discapacidadFisicaRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadFisicaRadioGroup.getChildCount(); cont++) {
                        discapacidadFisicaRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeFisicaEditText.setEnabled(false);
                    porcentajeFisicaEditText.setText("");
                    discapacidadCegueraRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadCegueraRadioGroup.getChildCount(); cont++) {
                        discapacidadCegueraRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeCegueraEditText.setEnabled(false);
                    porcentajeCegueraEditText.setText("");
                    discapacidadVisionRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadVisionRadioGroup.getChildCount(); cont++) {
                        discapacidadVisionRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeVisionEditText.setEnabled(false);
                    porcentajeVisionEditText.setText("");
                    discapacidadSorderaRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadSorderaRadioGroup.getChildCount(); cont++) {
                        discapacidadSorderaRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeSorderaEditText.setEnabled(false);
                    porcentajeSorderaEditText.setText("");
                    discapacidadHipoacusiaRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadHipoacusiaRadioGroup.getChildCount(); cont++) {
                        discapacidadHipoacusiaRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajeHipoacusiaEditText.setEnabled(false);
                    porcentajeHipoacusiaEditText.setText("");
                    discapacidadPsicosocialesRadioGroup.check(-1);
                    for (int cont = 0; cont < discapacidadPsicosocialesRadioGroup.getChildCount(); cont++) {
                        discapacidadPsicosocialesRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    porcentajePsicosocialesEditText.setText("");
                    porcentajePsicosocialesEditText.setEnabled(false);
                    asistenciaEstablecimientoSpinner.setSelection(0);
                    asistenciaEstablecimientoSpinner.setEnabled(false);
                    proteccionSocialpinner.setSelection(0);
                    proteccionSocialpinner.setEnabled(false);
                    necesitaAyudaTecnicaRadioGroup.check(-1);
                    for (int cont = 0; cont < necesitaAyudaTecnicaRadioGroup.getChildCount(); cont++) {
                        necesitaAyudaTecnicaRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    deshabilitarAyudasTecnicas();
                    deshabilitarInstitucionesAyudasTecnicas();

                } else {
                    for (int cont = 0; cont < carnetConadisRadioGroup.getChildCount(); cont++) {
                        carnetConadisRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadIntelectualRadioGroup.getChildCount(); cont++) {
                        discapacidadIntelectualRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadFisicaRadioGroup.getChildCount(); cont++) {
                        discapacidadFisicaRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadCegueraRadioGroup.getChildCount(); cont++) {
                        discapacidadCegueraRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadVisionRadioGroup.getChildCount(); cont++) {
                        discapacidadVisionRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadSorderaRadioGroup.getChildCount(); cont++) {
                        discapacidadSorderaRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadHipoacusiaRadioGroup.getChildCount(); cont++) {
                        discapacidadHipoacusiaRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    for (int cont = 0; cont < discapacidadPsicosocialesRadioGroup.getChildCount(); cont++) {
                        discapacidadPsicosocialesRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    asistenciaEstablecimientoSpinner.setEnabled(true);
                    proteccionSocialpinner.setEnabled(true);
                    for (int cont = 0; cont < necesitaAyudaTecnicaRadioGroup.getChildCount(); cont++) {
                        necesitaAyudaTecnicaRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    habilitarAyudasTecnicas();
                    habilitarInstitucionesAyudasTecnicas();

                }
            }
        });

        sufreEnfermedadesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey()) == 4) {
                    diagnosticoMedicoRadioGroup.check(-1);
                    for (int cont = 0; cont < diagnosticoMedicoRadioGroup.getChildCount(); cont++) {
                        diagnosticoMedicoRadioGroup.getChildAt(cont).setEnabled(false);
                    }
                    enfermedadCatastroficaSpinner.setSelection(0);
                    enfermedadCatastroficaSpinner.setEnabled(false);
                } else {
                    for (int cont = 0; cont < diagnosticoMedicoRadioGroup.getChildCount(); cont++) {
                        diagnosticoMedicoRadioGroup.getChildAt(cont).setEnabled(true);
                    }
                    enfermedadCatastroficaSpinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        seguroSocial1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(((Values) seguroSocial2Spinner.getSelectedItem()).getKey())
                         &&
                        !((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSocialIguales));
                    seguroSocial2Spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        seguroSocial2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) seguroSocial2Spinner.getSelectedItem()).getKey().equals(((Values) seguroSocial1Spinner.getSelectedItem()).getKey())
                         &&
                        !((Values) seguroSocial2Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSocialIguales));
                    seguroSocial2Spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        seguroSalud1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(((Values) seguroSalud2Spinner.getSelectedItem()).getKey())
                         &&
                        !((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSaludIguales));
                    seguroSalud2Spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        seguroSalud2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) seguroSalud2Spinner.getSelectedItem()).getKey().equals(((Values) seguroSalud1Spinner.getSelectedItem()).getKey())
                         &&
                        !((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSaludIguales));
                    seguroSalud2Spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        discapacidadIntelectualRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton) {
                    porcentajeIntelectualEditText.setEnabled(true);
                } else {
                    porcentajeIntelectualEditText.setEnabled(false);
                    porcentajeIntelectualEditText.setText("");
                }
            }
        });

        discapacidadFisicaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton) {
                    porcentajeFisicaEditText.setEnabled(true);
                } else {
                    porcentajeFisicaEditText.setEnabled(false);
                    porcentajeFisicaEditText.setText("");
                }
            }
        });

        discapacidadCegueraRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton) {
                    porcentajeCegueraEditText.setEnabled(true);
                } else {
                    porcentajeCegueraEditText.setEnabled(false);
                    porcentajeCegueraEditText.setText("");
                }
            }
        });

        discapacidadVisionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton) {
                    porcentajeVisionEditText.setEnabled(true);
                } else {
                    porcentajeVisionEditText.setEnabled(false);
                    porcentajeVisionEditText.setText("");
                }
            }
        });

        discapacidadSorderaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton) {
                    porcentajeSorderaEditText.setEnabled(true);
                } else {
                    porcentajeSorderaEditText.setEnabled(false);
                    porcentajeSorderaEditText.setText("");
                }
            }
        });

        discapacidadHipoacusiaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton) {
                    porcentajeHipoacusiaEditText.setEnabled(true);
                } else {
                    porcentajeHipoacusiaEditText.setEnabled(false);
                    porcentajeHipoacusiaEditText.setText("");
                }
            }
        });

        discapacidadPsicosocialesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton) {
                    porcentajePsicosocialesEditText.setEnabled(true);
                } else {
                    porcentajePsicosocialesEditText.setEnabled(false);
                    porcentajePsicosocialesEditText.setText("");
                }
            }
        });

        necesitaAyudaTecnicaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion2RadioButton) {
                    deshabilitarAyudasTecnicas();
                    deshabilitarInstitucionesAyudasTecnicas();
                } else {
                    habilitarAyudasTecnicas();
                    habilitarAyudasTecnicas();
                }
            }
        });

        recibioAyudaTecnicaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.recibioAyudaTecnicaOpcion1RadioButton) {
                    habilitarInstitucionesAyudasTecnicas();
                } else {
                    deshabilitarInstitucionesAyudasTecnicas();
                }
            }
        });




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
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.aporteSeguro));
        } else if (((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(((Values) seguroSocial2Spinner.getSelectedItem()).getKey())
                 &&
                !((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSocialIguales));
        } else if (((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                ((Values) seguroSalud2Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.seguroSalud));
        } else if (((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(((Values) seguroSalud2Spinner.getSelectedItem()).getKey())
                 &&
                !((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.errorSeguroSaludIguales));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.poseeDiscapacidadTitulo));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                carnetConadisRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.carneDiscapacidadTitulo));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+getString(R.string.tipoDiscapacidadIntelectual));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeIntelectualEditText.getText().toString().trim())) {
            porcentajeIntelectualEditText.setError(null);
            porcentajeIntelectualEditText.clearFocus();
            porcentajeIntelectualEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadFisica));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeFisicaEditText.getText().toString().trim())) {
            porcentajeFisicaEditText.setError(null);
            porcentajeFisicaEditText.clearFocus();
            porcentajeFisicaEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadCeguera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeCegueraEditText.getText().toString().trim())) {
            porcentajeCegueraEditText.setError(null);
            porcentajeCegueraEditText.clearFocus();
            porcentajeCegueraEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadBajaVision));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeVisionEditText.getText().toString().trim())) {
            porcentajeVisionEditText.setError(null);
            porcentajeVisionEditText.clearFocus();
            porcentajeVisionEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadSordera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeSorderaEditText.getText().toString().trim())) {
            porcentajeSorderaEditText.setError(null);
            porcentajeSorderaEditText.clearFocus();
            porcentajeSorderaEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadHipoacusia));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeHipoacusiaEditText.getText().toString().trim())) {
            porcentajeHipoacusiaEditText.setError(null);
            porcentajeHipoacusiaEditText.clearFocus();
            porcentajeHipoacusiaEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo)+" "+getString(R.string.tipoDiscapacidadPsicosociales));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajePsicosocialesEditText.getText().toString().trim())) {
            porcentajePsicosocialesEditText.setError(null);
            porcentajePsicosocialesEditText.clearFocus();
            porcentajePsicosocialesEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                ((Values) asistenciaEstablecimientoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.asistenciaEstablecimiento));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                ((Values) proteccionSocialpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.servicioProteccion));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.necesitaAyudaTecnica));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.recibioAyudaTecnica));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                sillaRuedasRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionA));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                muletasRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionB));
        } else if (
                discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                        necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                andadoresRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionC));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                bastonApoyoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionD));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                ortesisRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionE));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                colchonRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionF));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                cojinRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionG));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                        necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                bastonRastreoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionH));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                abacoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionI));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                computadoraRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionJ));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                audifonosRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionK));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                implantesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionL));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                cochePostularRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionM));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                panialesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionN));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                sillaBaniarseRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas)+" "+getString(R.string.ayudasTecnicasOpcionO));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                camasRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) +getString(R.string.ayudasTecnicas)+" "+ getString(R.string.ayudasTecnicasOpcionP));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                otrosRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) +getString(R.string.ayudasTecnicas)+" "+ getString(R.string.ayudasTecnicasOpcionQ));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                gobiernoCentralRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) +getString(R.string.institucionAyudasTecnicas)+" "+ getString(R.string.institucionAyudasTecnicasOpcionA));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.recibioAyudaTecnicaOpcion1RadioButton &&
                gobiernoAutonomoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas)+" "+getString(R.string.institucionAyudasTecnicasOpcionB));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                organizacionPrivadaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas)+" "+getString(R.string.institucionAyudasTecnicasOpcionC));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                ningunaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas)+" "+getString(R.string.institucionAyudasTecnicasOpcionD));
        } else if (((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.sufreEnfermedades));
        } else if (!((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("4") &&
                diagnosticoMedicoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.diagnosticoMedico));
        } else if (!((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("4") &&
                ((Values) enfermedadCatastroficaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.enfermedadCatastrofica));
        } else {
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

    private void deshabilitarAyudasTecnicas() {
        recibioAyudaTecnicaRadioGroup.check(-1);
        for (int cont = 0; cont < recibioAyudaTecnicaRadioGroup.getChildCount(); cont++) {
            recibioAyudaTecnicaRadioGroup.getChildAt(cont).setEnabled(false);
        }
        sillaRuedasRadioGroup.check(-1);
        for (int cont = 0; cont < sillaRuedasRadioGroup.getChildCount(); cont++) {
            sillaRuedasRadioGroup.getChildAt(cont).setEnabled(false);
        }
        muletasRadioGroup.check(-1);
        for (int cont = 0; cont < muletasRadioGroup.getChildCount(); cont++) {
            muletasRadioGroup.getChildAt(cont).setEnabled(false);
        }
        andadoresRadioGroup.check(-1);
        for (int cont = 0; cont < andadoresRadioGroup.getChildCount(); cont++) {
            andadoresRadioGroup.getChildAt(cont).setEnabled(false);
        }
        bastonApoyoRadioGroup.check(-1);
        for (int cont = 0; cont < bastonApoyoRadioGroup.getChildCount(); cont++) {
            bastonApoyoRadioGroup.getChildAt(cont).setEnabled(false);
        }
        ortesisRadioGroup.check(-1);
        for (int cont = 0; cont < ortesisRadioGroup.getChildCount(); cont++) {
            ortesisRadioGroup.getChildAt(cont).setEnabled(false);
        }
        colchonRadioGroup.check(-1);
        for (int cont = 0; cont < colchonRadioGroup.getChildCount(); cont++) {
            colchonRadioGroup.getChildAt(cont).setEnabled(false);
        }
        cojinRadioGroup.check(-1);
        for (int cont = 0; cont < cojinRadioGroup.getChildCount(); cont++) {
            cojinRadioGroup.getChildAt(cont).setEnabled(false);
        }
        bastonRastreoRadioGroup.check(-1);
        for (int cont = 0; cont < bastonRastreoRadioGroup.getChildCount(); cont++) {
            bastonRastreoRadioGroup.getChildAt(cont).setEnabled(false);
        }
        abacoRadioGroup.check(-1);
        for (int cont = 0; cont < abacoRadioGroup.getChildCount(); cont++) {
            abacoRadioGroup.getChildAt(cont).setEnabled(false);
        }
        computadoraRadioGroup.check(-1);
        for (int cont = 0; cont < computadoraRadioGroup.getChildCount(); cont++) {
            computadoraRadioGroup.getChildAt(cont).setEnabled(false);
        }
        audifonosRadioGroup.check(-1);
        for (int cont = 0; cont < audifonosRadioGroup.getChildCount(); cont++) {
            audifonosRadioGroup.getChildAt(cont).setEnabled(false);
        }
        implantesRadioGroup.check(-1);
        for (int cont = 0; cont < implantesRadioGroup.getChildCount(); cont++) {
            implantesRadioGroup.getChildAt(cont).setEnabled(false);
        }
        cochePostularRadioGroup.check(-1);
        for (int cont = 0; cont < cochePostularRadioGroup.getChildCount(); cont++) {
            cochePostularRadioGroup.getChildAt(cont).setEnabled(false);
        }
        panialesRadioGroup.check(-1);
        for (int cont = 0; cont < panialesRadioGroup.getChildCount(); cont++) {
            panialesRadioGroup.getChildAt(cont).setEnabled(false);
        }
        sillaBaniarseRadioGroup.check(-1);
        for (int cont = 0; cont < sillaBaniarseRadioGroup.getChildCount(); cont++) {
            sillaBaniarseRadioGroup.getChildAt(cont).setEnabled(false);
        }
        camasRadioGroup.check(-1);
        for (int cont = 0; cont < camasRadioGroup.getChildCount(); cont++) {
            camasRadioGroup.getChildAt(cont).setEnabled(false);
        }
        otrosRadioGroup.check(-1);
        for (int cont = 0; cont < otrosRadioGroup.getChildCount(); cont++) {
            otrosRadioGroup.getChildAt(cont).setEnabled(false);
        }

    }


    private void habilitarAyudasTecnicas() {
        for (int cont = 0; cont < recibioAyudaTecnicaRadioGroup.getChildCount(); cont++) {
            recibioAyudaTecnicaRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < sillaRuedasRadioGroup.getChildCount(); cont++) {
            sillaRuedasRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < muletasRadioGroup.getChildCount(); cont++) {
            muletasRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < andadoresRadioGroup.getChildCount(); cont++) {
            andadoresRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < bastonApoyoRadioGroup.getChildCount(); cont++) {
            bastonApoyoRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < ortesisRadioGroup.getChildCount(); cont++) {
            ortesisRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < colchonRadioGroup.getChildCount(); cont++) {
            colchonRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < cojinRadioGroup.getChildCount(); cont++) {
            cojinRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < bastonRastreoRadioGroup.getChildCount(); cont++) {
            bastonRastreoRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < abacoRadioGroup.getChildCount(); cont++) {
            abacoRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < computadoraRadioGroup.getChildCount(); cont++) {
            computadoraRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < audifonosRadioGroup.getChildCount(); cont++) {
            audifonosRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < implantesRadioGroup.getChildCount(); cont++) {
            implantesRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < cochePostularRadioGroup.getChildCount(); cont++) {
            cochePostularRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < panialesRadioGroup.getChildCount(); cont++) {
            panialesRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < sillaBaniarseRadioGroup.getChildCount(); cont++) {
            sillaBaniarseRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < camasRadioGroup.getChildCount(); cont++) {
            camasRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < otrosRadioGroup.getChildCount(); cont++) {
            otrosRadioGroup.getChildAt(cont).setEnabled(true);
        }

    }

    private void deshabilitarInstitucionesAyudasTecnicas(){
        gobiernoCentralRadioGroup.check(-1);
        for (int cont = 0; cont < gobiernoCentralRadioGroup.getChildCount(); cont++) {
            gobiernoCentralRadioGroup.getChildAt(cont).setEnabled(false);
        }
        gobiernoAutonomoRadioGroup.check(-1);
        for (int cont = 0; cont < gobiernoAutonomoRadioGroup.getChildCount(); cont++) {
            gobiernoAutonomoRadioGroup.getChildAt(cont).setEnabled(false);
        }
        organizacionPrivadaRadioGroup.check(-1);
        for (int cont = 0; cont < organizacionPrivadaRadioGroup.getChildCount(); cont++) {
            organizacionPrivadaRadioGroup.getChildAt(cont).setEnabled(false);
        }
        ningunaRadioGroup.check(-1);
        for (int cont = 0; cont < ningunaRadioGroup.getChildCount(); cont++) {
            ningunaRadioGroup.getChildAt(cont).setEnabled(false);
        }
    }

    private void habilitarInstitucionesAyudasTecnicas(){
        for (int cont = 0; cont < gobiernoCentralRadioGroup.getChildCount(); cont++) {
            gobiernoCentralRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < gobiernoAutonomoRadioGroup.getChildCount(); cont++) {
            gobiernoAutonomoRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < organizacionPrivadaRadioGroup.getChildCount(); cont++) {
            organizacionPrivadaRadioGroup.getChildAt(cont).setEnabled(true);
        }
        for (int cont = 0; cont < ningunaRadioGroup.getChildCount(); cont++) {
            ningunaRadioGroup.getChildAt(cont).setEnabled(true);
        }
    }

}
