package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.InputFilterMinMax;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.PersonaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;

/***
 *  Autor:Christian Tintin
 */

public class MiembrosHogarTodasPersonasFragment extends Fragment {

    private TextView nombresCompletosTextView;
    private TextView edadAniosTextView;
    private TextView infEdadMesesTextView;
    private TextView edadMesesTextView;
    private TextView sexoTextView;
    private Button atrasButton;
    private Button guardarPersonaButton;
    private EditText cedulaEditText;
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
    private Persona persona;
    private ContentResolver contentResolver;
    private LinearLayout menores18AniosLinearLayout;
    private LinearLayout codigoPersonaLinearLayout;
    ArrayList<Values> codigosMadres;
    private Hogar hogar;
    private int contador;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar_todas_personas,
                container, false);
        this.contentResolver = getActivity().getContentResolver();
        this.obtenerVistas(item);
        persona = (Persona) getArguments().getSerializable("persona");
        hogar = HogarFragment.getHogar();
        this.cargarPreguntas();
        this.realizarAcciones();
        this.mallasValidacion();
        return item;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            //persona = (Persona) getArguments().getSerializable("persona");
            //se llena las etiquetas del encabezado
            if (persona != null) {
                nombresCompletosTextView.setText(persona.getNombresCompletos());
                edadAniosTextView.setText(String.valueOf(persona.getEdadCompleto()));
                if (persona != null && persona.getEdadanio() < 5) {
                    infEdadMesesTextView.setVisibility(View.VISIBLE);
                    edadMesesTextView.setText(String.valueOf(persona.getEdadmes()));
                    etniaSpinner.setSelection(0);
                    etniaSpinner.setEnabled(false);
                }
                if (persona != null && persona.getEdadanio() < 12) {
                    estadoCivilSpinner.setSelection(0);
                    estadoCivilSpinner.setEnabled(false);
                }
                if (persona != null && persona.getEdadanio() < 15) {
                    seguroSocial1Spinner.setSelection(0);
                    seguroSocial1Spinner.setEnabled(false);
                    seguroSocial2Spinner.setSelection(0);
                    seguroSocial2Spinner.setEnabled(false);
                }
                sexoTextView.setText(String.valueOf(persona.getGeneroCompleto()));
                //que se visualice dependiendo la edad
                //que quede quemado 'NO'cuando no hay madres
                if (codigosMadres.size() == 1) {
                    viveMadreHogarRadioGroup
                            .check(R.id.viveMadreHogarOpcion2RadioButton);
                }
                if (persona.getEdadanio() < Global.EDAD_18ANIOS) {
                    viveMadreHogarRadioGroup
                            .setVisibility(View.VISIBLE);
                    codigoPersonaLinearLayout.setVisibility(View.VISIBLE);

                } else {
                    viveMadreHogarRadioGroup
                            .setVisibility(View.INVISIBLE);
                    codigoPersonaLinearLayout.setVisibility(View.INVISIBLE);
                }


                this.llenarCamposMiembrosHogar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {

        nombresCompletosTextView = item.findViewById(R.id.nombresCompletosTextView);
        edadAniosTextView = item.findViewById(R.id.edadAniosTextView);
        infEdadMesesTextView = item.findViewById(R.id.infEdadMesesTextView);
        edadMesesTextView = item.findViewById(R.id.edadMesesTextView);
        sexoTextView = item.findViewById(R.id.sexoTextView);
        menores18AniosLinearLayout = item.findViewById(R.id.menores18AniosLinearLayout);
        codigoPersonaLinearLayout = item.findViewById(R.id.codigoPersonaLinearLayout);
        atrasButton = item.findViewById(R.id.atrasButton);
        guardarPersonaButton = item.findViewById(R.id.guardarPersonaButton);
        cedulaEditText = item.findViewById(R.id.cedulaEditText);
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
        porcentajeIntelectualEditText = item.findViewById(R.id.porcentajeIntelectualEditText);
        porcentajeIntelectualEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajeFisicaEditText = item.findViewById(R.id.porcentajeFisicaEditText);
        porcentajeFisicaEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajeCegueraEditText = item.findViewById(R.id.porcentajeCegueraEditText);
        porcentajeCegueraEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajeVisionEditText = item.findViewById(R.id.porcentajeVisionEditText);
        porcentajeVisionEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajeSorderaEditText = item.findViewById(R.id.porcentajeSorderaEditText);
        porcentajeSorderaEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajeHipoacusiaEditText = item.findViewById(R.id.porcentajeHipoacusiaEditText);
        porcentajeHipoacusiaEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
        porcentajePsicosocialesEditText = item.findViewById(R.id.porcentajePsicosocialesEditText);
        porcentajePsicosocialesEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});

    }


    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCamposMiembrosHogar() {
        if (!persona.getCi().equals(Global.CADENAS_VACIAS)) {
            cedulaEditText.setText(persona.getCi());
        } else {
            cedulaEditText.setText("");
        }

        int posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) parentescoSpinner.getAdapter(), String.valueOf(persona.getIdparentesco()));
        parentescoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoCivilSpinner.getAdapter(), String.valueOf(persona.getIdestadocivil()));
        estadoCivilSpinner.setSelection(posicion);
        if (persona.getMadrevive() == Global.SI) {
            viveMadreHogarRadioGroup.check(R.id.viveMadreHogarOpcion1RadioButton);
        } else {
            if (persona.getMadrevive() == Global.NO) {
                viveMadreHogarRadioGroup.check(R.id.viveMadreHogarOpcion2RadioButton);
            }
        }
        if (codigoPersonaMadreSpinner.getVisibility() == View.VISIBLE && codigoPersonaMadreSpinner.getCount() > 0) {
            posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) codigoPersonaMadreSpinner.getAdapter(), String.valueOf(persona.getOrdenMadre()));
            codigoPersonaMadreSpinner.setSelection(posicion);
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) nacionalidadSpinner.getAdapter(), String.valueOf(persona.getIdnacionalidad()));
        nacionalidadSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) etniaSpinner.getAdapter(), String.valueOf(persona.getIdnacionalidad()));
        etniaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) seguroSocial1Spinner.getAdapter(), String.valueOf(persona.getIdsegurosocial1()));
        seguroSocial1Spinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) seguroSocial2Spinner.getAdapter(), String.valueOf(persona.getIdsegurosocial2()));
        seguroSocial2Spinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) seguroSalud1Spinner.getAdapter(), String.valueOf(persona.getIdsegurosalud1()));
        seguroSalud1Spinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) seguroSalud2Spinner.getAdapter(), String.valueOf(persona.getIdsegurosalud2()));
        seguroSalud2Spinner.setSelection(posicion);
        if (persona.getTienediscapacidad() == Global.SI) {
            discapacidadRadioGroup.check(R.id.discapacidadOpcion1RadioButton);
        } else {
            if (persona.getTienediscapacidad() == Global.NO) {
                discapacidadRadioGroup.check(R.id.discapacidadOpcion2RadioButton);
            }
        }
        if (persona.getCarnediscapacidad() == Global.SI) {
            carnetConadisRadioGroup.check(R.id.carnetConadisOpcion1RadioButton);
        } else {
            if (persona.getCarnediscapacidad() == Global.NO) {
                carnetConadisRadioGroup.check(R.id.carnetConadisOpcion2RadioButton);
            }
        }
        if (persona.getDiscapacidadintelectual() == Global.SI) {
            discapacidadIntelectualRadioGroup.check(R.id.discapacidadIntelectualOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadintelectual() == Global.NO) {
                discapacidadIntelectualRadioGroup.check(R.id.discapacidadIntelectualOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajeintelectual().equals(Global.ENTEROS_VACIOS)) {
            porcentajeIntelectualEditText.setText(String.valueOf(persona.getPorcentajeintelectual()));
        } else {
            porcentajeIntelectualEditText.setText("");
        }

        if (persona.getDiscapacidadfisica() == Global.SI) {
            discapacidadFisicaRadioGroup.check(R.id.discapacidadFisicaOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadfisica() == Global.NO) {
                discapacidadFisicaRadioGroup.check(R.id.discapacidadFisicaOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajefisica().equals(Global.ENTEROS_VACIOS)) {
            porcentajeFisicaEditText.setText(String.valueOf(persona.getPorcentajefisica()));
        } else {
            porcentajeFisicaEditText.setText("");
        }

        if (persona.getDiscapacidadceguera() == Global.SI) {
            discapacidadCegueraRadioGroup.check(R.id.discapacidadCegueraOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadceguera() == Global.NO) {
                discapacidadCegueraRadioGroup.check(R.id.discapacidadCegueraOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajeceguera().equals(Global.ENTEROS_VACIOS)) {
            porcentajeCegueraEditText.setText(String.valueOf(persona.getPorcentajeceguera()));
        } else {
            porcentajeCegueraEditText.setText("");
        }

        if (persona.getDiscapacidadvision() == Global.SI) {
            discapacidadVisionRadioGroup.check(R.id.discapacidadVisionOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadvision() == Global.NO) {
                discapacidadVisionRadioGroup.check(R.id.discapacidadVisionOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajevision().equals(Global.ENTEROS_VACIOS)) {
            porcentajeVisionEditText.setText(String.valueOf(persona.getPorcentajevision()));
        } else {
            porcentajeVisionEditText.setText("");
        }

        if (persona.getDiscapacidadsordera() == Global.SI) {
            discapacidadSorderaRadioGroup.check(R.id.discapacidadSorderaOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadsordera() == Global.NO) {
                discapacidadSorderaRadioGroup.check(R.id.discapacidadSorderaOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajesordera().equals(Global.ENTEROS_VACIOS)) {
            porcentajeSorderaEditText.setText(String.valueOf(persona.getPorcentajesordera()));
        } else {
            porcentajeSorderaEditText.setText("");
        }

        if (persona.getDiscapacidadhipoacusia() == Global.SI) {
            discapacidadHipoacusiaRadioGroup.check(R.id.discapacidadHipoacusiaOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadhipoacusia() == Global.NO) {
                discapacidadHipoacusiaRadioGroup.check(R.id.discapacidadHipoacusiaOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajehipoacusia().equals(Global.ENTEROS_VACIOS)) {
            porcentajeHipoacusiaEditText.setText(String.valueOf(persona.getPorcentajehipoacusia()));
        } else {
            porcentajeHipoacusiaEditText.setText("");
        }

        if (persona.getDiscapacidadpsicosociales() == Global.SI) {
            discapacidadPsicosocialesRadioGroup.check(R.id.discapacidadPsicosocialesOpcion1RadioButton);
        } else {
            if (persona.getDiscapacidadpsicosociales() == Global.NO) {
                discapacidadPsicosocialesRadioGroup.check(R.id.discapacidadPsicosocialesOpcion2RadioButton);
            }
        }
        if (!persona.getPorcentajepsicosociales().equals(Global.ENTEROS_VACIOS)) {
            porcentajePsicosocialesEditText.setText(String.valueOf(persona.getPorcentajepsicosociales()));
        } else {
            porcentajePsicosocialesEditText.setText("");
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) asistenciaEstablecimientoSpinner.getAdapter(), String.valueOf(persona.getIdestablecimientoeducacion()));
        asistenciaEstablecimientoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) proteccionSocialpinner.getAdapter(), String.valueOf(persona.getIdproteccionsocial()));
        proteccionSocialpinner.setSelection(posicion);
        if (persona.getNecesitaayudatecnica() == Global.SI) {
            necesitaAyudaTecnicaRadioGroup.check(R.id.necesitaAyudaTecnicaOpcion1RadioButton);
        } else {
            if (persona.getNecesitaayudatecnica() == Global.NO) {
                necesitaAyudaTecnicaRadioGroup.check(R.id.necesitaAyudaTecnicaOpcion2RadioButton);
            }
        }
        if (persona.getRecibioayudatecnica() == Global.SI) {
            recibioAyudaTecnicaRadioGroup.check(R.id.recibioAyudaTecnicaOpcion1RadioButton);
        } else {
            if (persona.getRecibioayudatecnica() == Global.NO) {
                recibioAyudaTecnicaRadioGroup.check(R.id.recibioAyudaTecnicaOpcion2RadioButton);
            }
        }
        if (persona.getSillaruedas() == Global.SI) {
            sillaRuedasRadioGroup.check(R.id.sillaRuedasOpcion1RadioButton);
        } else {
            if (persona.getSillaruedas() == Global.NO) {
                sillaRuedasRadioGroup.check(R.id.sillaRuedasOpcion2RadioButton);
            }
        }
        if (persona.getMuletas() == Global.SI) {
            muletasRadioGroup.check(R.id.muletasOpcion1RadioButton);
        } else {
            if (persona.getMuletas() == Global.NO) {
                muletasRadioGroup.check(R.id.muletasOpcion2RadioButton);
            }
        }
        if (persona.getAndadores() == Global.SI) {
            andadoresRadioGroup.check(R.id.andadoresOpcion1RadioButton);
        } else {
            if (persona.getAndadores() == Global.NO) {
                andadoresRadioGroup.check(R.id.andadoresOpcion2RadioButton);
            }
        }
        if (persona.getBastonapoyo() == Global.SI) {
            bastonApoyoRadioGroup.check(R.id.bastonApoyoOpcion1RadioButton);
        } else {
            if (persona.getBastonapoyo() == Global.NO) {
                bastonApoyoRadioGroup.check(R.id.bastonApoyoOpcion2RadioButton);
            }
        }
        if (persona.getOrtesis() == Global.SI) {
            ortesisRadioGroup.check(R.id.ortesisOpcion1RadioButton);
        } else {
            if (persona.getOrtesis() == Global.NO) {
                ortesisRadioGroup.check(R.id.ortesisOpcion2RadioButton);
            }
        }
        if (persona.getColchon() == Global.SI) {
            colchonRadioGroup.check(R.id.colchonOpcion1RadioButton);
        } else {
            if (persona.getColchon() == Global.NO) {
                colchonRadioGroup.check(R.id.colchonOpcion2RadioButton);
            }
        }
        if (persona.getCojin() == Global.SI) {
            cojinRadioGroup.check(R.id.cojinOpcion1RadioButton);
        } else {
            if (persona.getColchon() == Global.NO) {
                cojinRadioGroup.check(R.id.cojinOpcion2RadioButton);
            }
        }
        if (persona.getBastonrastreo() == Global.SI) {
            bastonRastreoRadioGroup.check(R.id.bastonRastreoOpcion1RadioButton);
        } else {
            if (persona.getBastonrastreo() == Global.NO) {
                bastonRastreoRadioGroup.check(R.id.bastonRastreoOpcion2RadioButton);
            }
        }
        if (persona.getAbaco() == Global.SI) {
            abacoRadioGroup.check(R.id.abacoOpcion1RadioButton);
        } else {
            if (persona.getAbaco() == Global.NO) {
                abacoRadioGroup.check(R.id.abacoOpcion2RadioButton);
            }
        }
        if (persona.getComputadora() == Global.SI) {
            computadoraRadioGroup.check(R.id.computadoraOpcion1RadioButton);
        } else {
            if (persona.getComputadora() == Global.NO) {
                computadoraRadioGroup.check(R.id.computadoraOpcion2RadioButton);
            }
        }
        if (persona.getAudifonos() == Global.SI) {
            audifonosRadioGroup.check(R.id.audifonosOpcion1RadioButton);
        } else {
            if (persona.getAudifonos() == Global.NO) {
                audifonosRadioGroup.check(R.id.audifonosOpcion2RadioButton);
            }
        }
        if (persona.getImplantes() == Global.SI) {
            implantesRadioGroup.check(R.id.implantesOpcion1RadioButton);
        } else {
            if (persona.getImplantes() == Global.NO) {
                implantesRadioGroup.check(R.id.implantesOpcion2RadioButton);
            }
        }
        if (persona.getCochepostular() == Global.SI) {
            cochePostularRadioGroup.check(R.id.cochePostularOpcion1RadioButton);
        } else {
            if (persona.getCochepostular() == Global.NO) {
                cochePostularRadioGroup.check(R.id.cochePostularOpcion2RadioButton);
            }
        }
        if (persona.getPaniales() == Global.SI) {
            panialesRadioGroup.check(R.id.panialesOpcion1RadioButton);
        } else {
            if (persona.getPaniales() == Global.NO) {
                panialesRadioGroup.check(R.id.panialesOpcion2RadioButton);
            }
        }
        if (persona.getSillabaniarse() == Global.SI) {
            sillaBaniarseRadioGroup.check(R.id.sillaBaniarseOpcion1RadioButton);
        } else {
            if (persona.getSillabaniarse() == Global.NO) {
                sillaBaniarseRadioGroup.check(R.id.sillaBaniarseOpcion2RadioButton);
            }
        }
        if (persona.getCamas() == Global.SI) {
            camasRadioGroup.check(R.id.camasOpcion1RadioButton);
        } else {
            if (persona.getCamas() == Global.NO) {
                camasRadioGroup.check(R.id.camasOpcion2RadioButton);
            }
        }
        if (persona.getOtrasayudas() == Global.SI) {
            otrosRadioGroup.check(R.id.otrosOpcion1RadioButton);
        } else {
            if (persona.getOtrasayudas() == Global.NO) {
                otrosRadioGroup.check(R.id.otrosOpcion2RadioButton);
            }
        }
        if (persona.getGobiernocentral() == Global.SI) {
            gobiernoCentralRadioGroup.check(R.id.gobiernoCentralOpcion1RadioButton);
        } else {
            if (persona.getGobiernocentral() == Global.NO) {
                gobiernoCentralRadioGroup.check(R.id.gobiernoCentralOpcion2RadioButton);
            }
        }
        if (persona.getGobiernoautonomo() == Global.SI) {
            gobiernoAutonomoRadioGroup.check(R.id.gobiernoAutonomoOpcion1RadioButton);
        } else {
            if (persona.getGobiernoautonomo() == Global.NO) {
                gobiernoAutonomoRadioGroup.check(R.id.gobiernoAutonomoOpcion2RadioButton);
            }
        }
        if (persona.getOrganizacionprivada() == Global.SI) {
            organizacionPrivadaRadioGroup.check(R.id.organizacionPrivadaOpcion1RadioButton);
        } else {
            if (persona.getOrganizacionprivada() == Global.NO) {
                organizacionPrivadaRadioGroup.check(R.id.organizacionPrivadaOpcion2RadioButton);
            }
        }
        if (persona.getNingunainstitucion() == Global.SI) {
            ningunaRadioGroup.check(R.id.ningunaOpcion1RadioButton);
        } else {
            if (persona.getNingunainstitucion() == Global.NO) {
                ningunaRadioGroup.check(R.id.ningunaOpcion2RadioButton);
            }
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) sufreEnfermedadesSpinner.getAdapter(), String.valueOf(persona.getIdtipoenfermedad()));
        sufreEnfermedadesSpinner.setSelection(posicion);
        if (persona.getEnfermedaddiagnostico() == Global.SI) {
            diagnosticoMedicoRadioGroup.check(R.id.diagnosticoMedicoOpcion1RadioButton);
        } else {
            if (persona.getEnfermedaddiagnostico() == Global.NO) {
                diagnosticoMedicoRadioGroup.check(R.id.diagnosticoMedicoOpcion2RadioButton);
            }
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) enfermedadCatastroficaSpinner.getAdapter(), String.valueOf(persona.getIdenfermedadcatastrofica()));
        enfermedadCatastroficaSpinner.setSelection(posicion);

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
        String where;
        String parametros[];
        where = Persona.whereByIdHogar;
        parametros = new String[]{String.valueOf(hogar.getId())};

        codigosMadres = new ArrayList<>();
        codigosMadres.add(new Values(Global.VALOR_SELECCIONE,
                getString(R.string.seleccionRespuesta)));

        ArrayList<Persona> personas = PersonaDao.getPersonas(contentResolver,
                where, parametros, Persona.COLUMNA_ORDEN);
        for (Persona _persona : personas) {
            if ((_persona.getEdadanio() >= Global.EDAD_12ANIOS)
                    && (_persona.getSexo() == Global.GENERO_FEMENINO)
                    && (_persona.getId() != persona.getId())) {
                codigosMadres.add(new Values(_persona.getId(), _persona.getNombresCompletos()));
            }
        }
        ArrayAdapter<Values> adapterSeccion5CodigoMadre = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item,
                codigosMadres);
        adapterSeccion5CodigoMadre
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codigoPersonaMadreSpinner.setAdapter(adapterSeccion5CodigoMadre);

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

        persona.setIdparentesco(Integer.parseInt(((Values) parentescoSpinner.getSelectedItem()).getKey()));
        persona.setIdestadocivil(Integer.parseInt(((Values) estadoCivilSpinner.getSelectedItem()).getKey()));
        if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == R.id.viveMadreHogarOpcion1RadioButton) {
            persona.setMadrevive(Global.SI);
            persona.setOrdenMadre(Integer.parseInt(((Values) codigoPersonaMadreSpinner.getSelectedItem()).getKey()));
        } else {
            if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == R.id.viveMadreHogarOpcion2RadioButton) {
                persona.setMadrevive(Global.NO);
                persona.setOrdenMadre(Global.ENTEROS_VACIOS);
            } else {
                persona.setMadrevive(Global.ENTEROS_VACIOS);
                persona.setOrdenMadre(Global.ENTEROS_VACIOS);
            }
        }
        persona.setIdnacionalidad(Integer.parseInt(((Values) nacionalidadSpinner.getSelectedItem()).getKey()));
        persona.setIdetnia(Integer.parseInt(((Values) etniaSpinner.getSelectedItem()).getKey()));
        persona.setIdsegurosocial1(Integer.parseInt(((Values) seguroSocial1Spinner.getSelectedItem()).getKey()));
        persona.setIdsegurosocial2(Integer.parseInt(((Values) seguroSocial2Spinner.getSelectedItem()).getKey()));
        persona.setIdsegurosalud1(Integer.parseInt(((Values) seguroSalud1Spinner.getSelectedItem()).getKey()));
        persona.setIdsegurosalud2(Integer.parseInt(((Values) seguroSalud2Spinner.getSelectedItem()).getKey()));
        if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton) {
            persona.setTienediscapacidad(Global.SI);
        } else {
            if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion2RadioButton) {
                persona.setTienediscapacidad(Global.NO);
            } else {
                persona.setTienediscapacidad(Global.ENTEROS_VACIOS);
            }
        }
        if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton) {
            persona.setCarnediscapacidad(Global.SI);
        } else {
            if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion2RadioButton) {
                persona.setCarnediscapacidad(Global.NO);
            } else {
                persona.setCarnediscapacidad(Global.ENTEROS_VACIOS);
            }
        }

        if (discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton) {
            persona.setDiscapacidadintelectual(Global.SI);
        } else {
            if (discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion2RadioButton) {
                persona.setDiscapacidadintelectual(Global.NO);
            } else {
                persona.setDiscapacidadintelectual(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeIntelectualEditText.getText().toString())) {
            persona.setPorcentajeintelectual(Integer.parseInt(porcentajeIntelectualEditText.getText().toString()));
        } else {
            persona.setPorcentajeintelectual(Global.ENTEROS_VACIOS);
        }

        if (discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton) {
            persona.setDiscapacidadfisica(Global.SI);
        } else {
            if (discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion2RadioButton) {
                persona.setDiscapacidadfisica(Global.NO);
            } else {
                persona.setDiscapacidadfisica(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeFisicaEditText.getText().toString())) {
            persona.setPorcentajefisica(Integer.parseInt(porcentajeFisicaEditText.getText().toString()));
        } else {
            persona.setPorcentajefisica(Global.ENTEROS_VACIOS);
        }

        if (discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton) {
            persona.setDiscapacidadceguera(Global.SI);
        } else {
            if (discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion2RadioButton) {
                persona.setDiscapacidadceguera(Global.NO);
            } else {
                persona.setDiscapacidadceguera(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeCegueraEditText.getText().toString())) {
            persona.setPorcentajeceguera(Integer.parseInt(porcentajeCegueraEditText.getText().toString()));
        } else {
            persona.setPorcentajeceguera(Global.ENTEROS_VACIOS);
        }

        if (discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton) {
            persona.setDiscapacidadvision(Global.SI);
        } else {
            if (discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion2RadioButton) {
                persona.setDiscapacidadvision(Global.NO);
            } else {
                persona.setDiscapacidadvision(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeVisionEditText.getText().toString())) {
            persona.setPorcentajevision(Integer.parseInt(porcentajeVisionEditText.getText().toString()));
        } else {
            persona.setPorcentajevision(Global.ENTEROS_VACIOS);
        }

        if (discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton) {
            persona.setDiscapacidadsordera(Global.SI);
        } else {
            if (discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion2RadioButton) {
                persona.setDiscapacidadsordera(Global.NO);
            } else {
                persona.setDiscapacidadsordera(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeSorderaEditText.getText().toString())) {
            persona.setPorcentajesordera(Integer.parseInt(porcentajeSorderaEditText.getText().toString()));
        } else {
            persona.setPorcentajesordera(Global.ENTEROS_VACIOS);
        }

        if (discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton) {
            persona.setDiscapacidadhipoacusia(Global.SI);
        } else {
            if (discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion2RadioButton) {
                persona.setDiscapacidadhipoacusia(Global.NO);
            } else {
                persona.setDiscapacidadhipoacusia(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajeHipoacusiaEditText.getText().toString())) {
            persona.setPorcentajehipoacusia(Integer.parseInt(porcentajeHipoacusiaEditText.getText().toString()));
        } else {
            persona.setPorcentajehipoacusia(Global.ENTEROS_VACIOS);
        }

        if (discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton) {
            persona.setDiscapacidadpsicosociales(Global.SI);
        } else {
            if (discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion2RadioButton) {
                persona.setDiscapacidadpsicosociales(Global.NO);
            } else {
                persona.setDiscapacidadpsicosociales(Global.ENTEROS_VACIOS);
            }
        }
        if (!TextUtils.isEmpty(porcentajePsicosocialesEditText.getText().toString())) {
            persona.setPorcentajepsicosociales(Integer.parseInt(porcentajePsicosocialesEditText.getText().toString()));
        } else {
            persona.setPorcentajepsicosociales(Global.ENTEROS_VACIOS);
        }

        persona.setIdestablecimientoeducacion(Integer.parseInt(((Values) asistenciaEstablecimientoSpinner.getSelectedItem()).getKey()));
        persona.setIdproteccionsocial(Integer.parseInt(((Values) proteccionSocialpinner.getSelectedItem()).getKey()));
        if (necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton) {
            persona.setNecesitaayudatecnica(Global.SI);
        } else {
            if (necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion2RadioButton) {
                persona.setNecesitaayudatecnica(Global.NO);
            } else {
                persona.setNecesitaayudatecnica(Global.ENTEROS_VACIOS);
            }
        }
        if (recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.recibioAyudaTecnicaOpcion1RadioButton) {
            persona.setRecibioayudatecnica(Global.SI);
        } else {
            if (recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.recibioAyudaTecnicaOpcion2RadioButton) {
                persona.setRecibioayudatecnica(Global.NO);
            } else {
                persona.setRecibioayudatecnica(Global.ENTEROS_VACIOS);
            }
        }

        if (sillaRuedasRadioGroup.getCheckedRadioButtonId() == R.id.sillaRuedasOpcion1RadioButton) {
            persona.setSillaruedas(Global.SI);
        } else {
            if (sillaRuedasRadioGroup.getCheckedRadioButtonId() == R.id.sillaRuedasOpcion2RadioButton) {
                persona.setSillaruedas(Global.NO);
            } else {
                persona.setSillaruedas(Global.ENTEROS_VACIOS);
            }
        }

        if (muletasRadioGroup.getCheckedRadioButtonId() == R.id.muletasOpcion1RadioButton) {
            persona.setMuletas(Global.SI);
        } else {
            if (muletasRadioGroup.getCheckedRadioButtonId() == R.id.muletasOpcion2RadioButton) {
                persona.setMuletas(Global.NO);
            } else {
                persona.setMuletas(Global.ENTEROS_VACIOS);
            }
        }

        if (andadoresRadioGroup.getCheckedRadioButtonId() == R.id.andadoresOpcion1RadioButton) {
            persona.setAndadores(Global.SI);
        } else {
            if (andadoresRadioGroup.getCheckedRadioButtonId() == R.id.andadoresOpcion2RadioButton) {
                persona.setAndadores(Global.NO);
            } else {
                persona.setAndadores(Global.ENTEROS_VACIOS);
            }
        }
        if (bastonApoyoRadioGroup.getCheckedRadioButtonId() == R.id.bastonApoyoOpcion1RadioButton) {
            persona.setBastonapoyo(Global.SI);
        } else {
            if (bastonApoyoRadioGroup.getCheckedRadioButtonId() == R.id.bastonApoyoOpcion2RadioButton) {
                persona.setBastonapoyo(Global.NO);
            } else {
                persona.setBastonapoyo(Global.ENTEROS_VACIOS);
            }
        }
        if (ortesisRadioGroup.getCheckedRadioButtonId() == R.id.ortesisOpcion1RadioButton) {
            persona.setOrtesis(Global.SI);
        } else {
            if (ortesisRadioGroup.getCheckedRadioButtonId() == R.id.ortesisOpcion2RadioButton) {
                persona.setOrtesis(Global.NO);
            } else {
                persona.setOrtesis(Global.ENTEROS_VACIOS);
            }
        }
        if (colchonRadioGroup.getCheckedRadioButtonId() == R.id.colchonOpcion1RadioButton) {
            persona.setColchon(Global.SI);
        } else {
            if (colchonRadioGroup.getCheckedRadioButtonId() == R.id.colchonOpcion2RadioButton) {
                persona.setColchon(Global.NO);
            } else {
                persona.setColchon(Global.ENTEROS_VACIOS);
            }
        }
        if (cojinRadioGroup.getCheckedRadioButtonId() == R.id.cojinOpcion1RadioButton) {
            persona.setCojin(Global.SI);
        } else {
            if (cojinRadioGroup.getCheckedRadioButtonId() == R.id.cojinOpcion2RadioButton) {
                persona.setCojin(Global.NO);
            } else {
                persona.setCojin(Global.ENTEROS_VACIOS);
            }
        }
        if (bastonRastreoRadioGroup.getCheckedRadioButtonId() == R.id.bastonRastreoOpcion1RadioButton) {
            persona.setBastonrastreo(Global.SI);
        } else {
            if (bastonRastreoRadioGroup.getCheckedRadioButtonId() == R.id.bastonRastreoOpcion2RadioButton) {
                persona.setBastonrastreo(Global.NO);
            } else {
                persona.setBastonrastreo(Global.ENTEROS_VACIOS);
            }
        }
        if (abacoRadioGroup.getCheckedRadioButtonId() == R.id.abacoOpcion1RadioButton) {
            persona.setAbaco(Global.SI);
        } else {
            if (abacoRadioGroup.getCheckedRadioButtonId() == R.id.abacoOpcion2RadioButton) {
                persona.setAbaco(Global.NO);
            } else {
                persona.setAbaco(Global.ENTEROS_VACIOS);
            }
        }
        if (computadoraRadioGroup.getCheckedRadioButtonId() == R.id.computadoraOpcion1RadioButton) {
            persona.setComputadora(Global.SI);
        } else {
            if (computadoraRadioGroup.getCheckedRadioButtonId() == R.id.computadoraOpcion2RadioButton) {
                persona.setComputadora(Global.NO);
            } else {
                persona.setComputadora(Global.ENTEROS_VACIOS);
            }
        }
        if (audifonosRadioGroup.getCheckedRadioButtonId() == R.id.audifonosOpcion1RadioButton) {
            persona.setAudifonos(Global.SI);
        } else {
            if (audifonosRadioGroup.getCheckedRadioButtonId() == R.id.audifonosOpcion2RadioButton) {
                persona.setAudifonos(Global.NO);
            } else {
                persona.setAudifonos(Global.ENTEROS_VACIOS);
            }
        }
        if (implantesRadioGroup.getCheckedRadioButtonId() == R.id.implantesOpcion1RadioButton) {
            persona.setImplantes(Global.SI);
        } else {
            if (implantesRadioGroup.getCheckedRadioButtonId() == R.id.implantesOpcion2RadioButton) {
                persona.setImplantes(Global.NO);
            } else {
                persona.setImplantes(Global.ENTEROS_VACIOS);
            }
        }
        if (cochePostularRadioGroup.getCheckedRadioButtonId() == R.id.cochePostularOpcion1RadioButton) {
            persona.setCochepostular(Global.SI);
        } else {
            if (cochePostularRadioGroup.getCheckedRadioButtonId() == R.id.cochePostularOpcion2RadioButton) {
                persona.setCochepostular(Global.NO);
            } else {
                persona.setCochepostular(Global.ENTEROS_VACIOS);
            }
        }
        if (panialesRadioGroup.getCheckedRadioButtonId() == R.id.panialesOpcion1RadioButton) {
            persona.setPaniales(Global.SI);
        } else {
            if (panialesRadioGroup.getCheckedRadioButtonId() == R.id.panialesOpcion2RadioButton) {
                persona.setPaniales(Global.NO);
            } else {
                persona.setPaniales(Global.ENTEROS_VACIOS);
            }
        }
        if (sillaBaniarseRadioGroup.getCheckedRadioButtonId() == R.id.sillaBaniarseOpcion1RadioButton) {
            persona.setSillabaniarse(Global.SI);
        } else {
            if (sillaBaniarseRadioGroup.getCheckedRadioButtonId() == R.id.sillaBaniarseOpcion2RadioButton) {
                persona.setSillabaniarse(Global.NO);
            } else {
                persona.setSillabaniarse(Global.ENTEROS_VACIOS);
            }
        }
        if (camasRadioGroup.getCheckedRadioButtonId() == R.id.camasOpcion1RadioButton) {
            persona.setCamas(Global.SI);
        } else {
            if (camasRadioGroup.getCheckedRadioButtonId() == R.id.camasOpcion2RadioButton) {
                persona.setCamas(Global.NO);
            } else {
                persona.setCamas(Global.ENTEROS_VACIOS);
            }
        }
        if (otrosRadioGroup.getCheckedRadioButtonId() == R.id.otrosOpcion1RadioButton) {
            persona.setOtrasayudas(Global.SI);
        } else {
            if (otrosRadioGroup.getCheckedRadioButtonId() == R.id.otrosOpcion2RadioButton) {
                persona.setOtrasayudas(Global.NO);
            } else {
                persona.setOtrasayudas(Global.ENTEROS_VACIOS);
            }
        }
        if (gobiernoCentralRadioGroup.getCheckedRadioButtonId() == R.id.gobiernoCentralOpcion1RadioButton) {
            persona.setGobiernocentral(Global.SI);
        } else {
            if (gobiernoCentralRadioGroup.getCheckedRadioButtonId() == R.id.gobiernoCentralOpcion2RadioButton) {
                persona.setGobiernocentral(Global.NO);
            } else {
                persona.setGobiernocentral(Global.ENTEROS_VACIOS);
            }
        }
        if (gobiernoAutonomoRadioGroup.getCheckedRadioButtonId() == R.id.gobiernoAutonomoOpcion1RadioButton) {
            persona.setGobiernoautonomo(Global.SI);
        } else {
            if (gobiernoAutonomoRadioGroup.getCheckedRadioButtonId() == R.id.gobiernoAutonomoOpcion2RadioButton) {
                persona.setGobiernoautonomo(Global.NO);
            } else {
                persona.setGobiernoautonomo(Global.ENTEROS_VACIOS);
            }
        }
        if (organizacionPrivadaRadioGroup.getCheckedRadioButtonId() == R.id.organizacionPrivadaOpcion1RadioButton) {
            persona.setOrganizacionprivada(Global.SI);
        } else {
            if (organizacionPrivadaRadioGroup.getCheckedRadioButtonId() == R.id.organizacionPrivadaOpcion2RadioButton) {
                persona.setOrganizacionprivada(Global.NO);
            } else {
                persona.setOrganizacionprivada(Global.ENTEROS_VACIOS);
            }
        }
        if (ningunaRadioGroup.getCheckedRadioButtonId() == R.id.ningunaOpcion1RadioButton) {
            persona.setNingunainstitucion(Global.SI);
        } else {
            if (ningunaRadioGroup.getCheckedRadioButtonId() == R.id.ningunaOpcion2RadioButton) {
                persona.setNingunainstitucion(Global.NO);
            } else {
                persona.setNingunainstitucion(Global.ENTEROS_VACIOS);
            }
        }
        persona.setIdtipoenfermedad(Integer.parseInt(((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey()));
        if (diagnosticoMedicoRadioGroup.getCheckedRadioButtonId() == R.id.diagnosticoMedicoOpcion1RadioButton) {
            persona.setEnfermedaddiagnostico(Global.SI);
        } else {
            if (diagnosticoMedicoRadioGroup.getCheckedRadioButtonId() == R.id.diagnosticoMedicoOpcion2RadioButton) {
                persona.setEnfermedaddiagnostico(Global.NO);
            } else {
                persona.setEnfermedaddiagnostico(Global.ENTEROS_VACIOS);
            }
        }
        persona.setIdenfermedadcatastrofica(Integer.parseInt(((Values) enfermedadCatastroficaSpinner.getSelectedItem()).getKey()));

        persona.setInformacioncompleta(Global.INFORMACION_COMPLETA);
        int filaAfectadas = PersonaDao.update(contentResolver, persona);

        if (filaAfectadas > 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {

        viveMadreHogarRadioGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {

                        if (viveMadreHogarRadioGroup
                                .getCheckedRadioButtonId() == R.id.viveMadreHogarOpcion1RadioButton) {
                            codigoPersonaLinearLayout.setVisibility(View.VISIBLE);
                            codigoPersonaMadreSpinner.setEnabled(true);
                        } else {
                            codigoPersonaLinearLayout.setVisibility(View.INVISIBLE);
                            codigoPersonaMadreSpinner.setSelection(0);
                            codigoPersonaMadreSpinner.setEnabled(false);
                        }
                    }
                });



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
                    asistenciaEstablecimientoSpinner.setClickable(false);
                    proteccionSocialpinner.setClickable(false);
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
                    asistenciaEstablecimientoSpinner.setClickable(true);
                    proteccionSocialpinner.setClickable(true);
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
                } else if (Integer.parseInt(((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey()) == 2 ||
                        Integer.parseInt(((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey()) == 3) {
                    enfermedadCatastroficaSpinner.setEnabled(false);
                    for (int cont = 0; cont < diagnosticoMedicoRadioGroup.getChildCount(); cont++) {
                        diagnosticoMedicoRadioGroup.getChildAt(cont).setEnabled(true);
                    }
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
                if (!((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    seguroSocial2Spinner.setClickable(true);
                }
                else{
                    seguroSocial2Spinner.setClickable(false);
                }
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
                if (!((Values) seguroSalud1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
                    seguroSalud2Spinner.setClickable(true);
                }
                else{
                    seguroSalud2Spinner.setClickable(false);
                }
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
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton) {
                    porcentajeIntelectualEditText.setEnabled(true);
                } else {
                    porcentajeIntelectualEditText.setEnabled(false);
                    porcentajeIntelectualEditText.setText("");
                    porcentajeIntelectualEditText.setError(null);
                }
            }
        });

        discapacidadFisicaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton) {
                    porcentajeFisicaEditText.setEnabled(true);
                } else {
                    porcentajeFisicaEditText.setEnabled(false);
                    porcentajeFisicaEditText.setText("");
                    porcentajeFisicaEditText.setError(null);
                }
            }
        });

        discapacidadCegueraRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton) {
                    porcentajeCegueraEditText.setEnabled(true);
                } else {
                    porcentajeCegueraEditText.setEnabled(false);
                    porcentajeCegueraEditText.setText("");
                    porcentajeCegueraEditText.setError(null);
                }
            }
        });

        discapacidadVisionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton) {
                    porcentajeVisionEditText.setEnabled(true);
                } else {
                    porcentajeVisionEditText.setEnabled(false);
                    porcentajeVisionEditText.setText("");
                    porcentajeVisionEditText.setError(null);
                }
            }
        });

        discapacidadSorderaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton) {
                    porcentajeSorderaEditText.setEnabled(true);
                } else {
                    porcentajeSorderaEditText.setEnabled(false);
                    porcentajeSorderaEditText.setText("");
                    porcentajeSorderaEditText.setError(null);
                }
            }
        });

        discapacidadHipoacusiaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton) {
                    porcentajeHipoacusiaEditText.setEnabled(true);
                } else {
                    porcentajeHipoacusiaEditText.setEnabled(false);
                    porcentajeHipoacusiaEditText.setText("");
                    porcentajeHipoacusiaEditText.setError(null);
                }
            }
        });

        discapacidadPsicosocialesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                        discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton) {
                    porcentajePsicosocialesEditText.setEnabled(true);
                } else {
                    porcentajePsicosocialesEditText.setEnabled(false);
                    porcentajePsicosocialesEditText.setText("");
                    porcentajePsicosocialesEditText.setError(null);
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

        parentescoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ((((Values) parentescoSpinner.getSelectedItem()).getKey().equals("5") ||
                        ((Values) parentescoSpinner.getSelectedItem()).getKey().equals("6") ||
                        ((Values) parentescoSpinner.getSelectedItem()).getKey().equals("7")) &&
                        persona != null && persona.getEdadanio() < 12) {
                    parentescoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorParentescoEdad));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        carnetConadisRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton) {
                    habilitarPorcentajes();
                } else {
                    deshabilitarPorcentajes();
                }
            }
        });

        proteccionSocialpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (persona != null && persona.getEdadanio() > 3 &&
                        Integer.parseInt(((Values) proteccionSocialpinner.getSelectedItem()).getKey()) > 0 &&
                        Integer.parseInt(((Values) proteccionSocialpinner.getSelectedItem()).getKey()) < 3) {
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.advertenciaServicioProteccion));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        } else if (((Values) estadoCivilSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))
                && persona != null && persona.getEdadanio() > 11) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlTrabajoEstadoCivilTitulo));
        } else if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlMadreHogarTitulo));
        } else if (viveMadreHogarRadioGroup.getCheckedRadioButtonId() == R.id.viveMadreHogarOpcion1RadioButton &&
                ((Values) estadoCivilSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.controlMadreHogarEtiqueta));
        } else if (((Values) nacionalidadSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.nacionalidad));
        } else if (((Values) etniaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))
                && persona != null && persona.getEdadanio() > 4) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.autodefinicionEtnica));
        } else if (((Values) seguroSocial1Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                ((Values) seguroSocial2Spinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))
                && persona != null && persona.getEdadanio() > 14) {
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
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + getString(R.string.tipoDiscapacidadIntelectual));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton &&
                carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeIntelectualEditText.getText().toString().trim())) {
            porcentajeIntelectualEditText.setError(null);
            porcentajeIntelectualEditText.clearFocus();
            porcentajeIntelectualEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeIntelectualEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadFisica));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeFisicaEditText.getText().toString().trim())) {
            porcentajeFisicaEditText.setError(null);
            porcentajeFisicaEditText.clearFocus();
            porcentajeFisicaEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeFisicaEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadCeguera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeCegueraEditText.getText().toString().trim())) {
            porcentajeCegueraEditText.setError(null);
            porcentajeCegueraEditText.clearFocus();
            porcentajeCegueraEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeCegueraEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadBajaVision));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeVisionEditText.getText().toString().trim())) {
            porcentajeVisionEditText.setError(null);
            porcentajeVisionEditText.clearFocus();
            porcentajeVisionEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeVisionEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadSordera));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeSorderaEditText.getText().toString().trim())) {
            porcentajeSorderaEditText.setError(null);
            porcentajeSorderaEditText.clearFocus();
            porcentajeSorderaEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeSorderaEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadHipoacusia));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadHipoacusiaOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajeHipoacusiaEditText.getText().toString().trim())) {
            porcentajeHipoacusiaEditText.setError(null);
            porcentajeHipoacusiaEditText.clearFocus();
            porcentajeHipoacusiaEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajeHipoacusiaEditText.requestFocus();
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoDiscapacidadTitulo) + " " + getString(R.string.tipoDiscapacidadPsicosociales));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton &&
                TextUtils.isEmpty(porcentajePsicosocialesEditText.getText().toString().trim())) {
            porcentajePsicosocialesEditText.setError(null);
            porcentajePsicosocialesEditText.clearFocus();
            porcentajePsicosocialesEditText.setError(getString(R.string.errorCampoRequerido));
            porcentajePsicosocialesEditText.requestFocus();
        } else if (carnetConadisRadioGroup.getCheckedRadioButtonId() == R.id.carnetConadisOpcion1RadioButton &&
                porcentajeIntelectualEditText.getText().toString().equals("") &&
                porcentajeFisicaEditText.getText().toString().equals("") &&
                porcentajeCegueraEditText.getText().toString().equals("") &&
                porcentajeVisionEditText.getText().toString().equals("") &&
                porcentajeSorderaEditText.getText().toString().equals("") &&
                porcentajeHipoacusiaEditText.getText().toString().equals("") &&
                porcentajePsicosocialesEditText.getText().toString().equals("")) {
            getAlert(getString(R.string.validacion_aviso),  getString(R.string.tipoDiscapacidadTitulo) + getString(R.string.errorCarnetConadis));
        } else if (validarNumPorcentajes()) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.tipoDiscapacidadTitulo) + getString(R.string.errorCarnetConadis2));
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
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionA));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                muletasRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionB));
        } else if (
                discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                        necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                        andadoresRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionC));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                bastonApoyoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionD));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                ortesisRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionE));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                colchonRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionF));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                cojinRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionG));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                bastonRastreoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionH));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                abacoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionI));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                computadoraRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionJ));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                audifonosRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionK));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                implantesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionL));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                cochePostularRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionM));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                panialesRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionN));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                sillaBaniarseRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionO));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                camasRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionP));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                otrosRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.ayudasTecnicas) + " " + getString(R.string.ayudasTecnicasOpcionQ));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                gobiernoCentralRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas) + " " + getString(R.string.institucionAyudasTecnicasOpcionA));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                recibioAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.recibioAyudaTecnicaOpcion1RadioButton &&
                gobiernoAutonomoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas) + " " + getString(R.string.institucionAyudasTecnicasOpcionB));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                organizacionPrivadaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas) + " " + getString(R.string.institucionAyudasTecnicasOpcionC));
        } else if (discapacidadRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadOpcion1RadioButton &&
                necesitaAyudaTecnicaRadioGroup.getCheckedRadioButtonId() == R.id.necesitaAyudaTecnicaOpcion1RadioButton &&
                ningunaRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.institucionAyudasTecnicas) + " " + getString(R.string.institucionAyudasTecnicasOpcionD));
        } else if (((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.sufreEnfermedades));
        } else if (!((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("4") &&
                diagnosticoMedicoRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.diagnosticoMedico));
        } else if (!((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("4") &&
                !((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("2") &&
                !((Values) sufreEnfermedadesSpinner.getSelectedItem()).getKey().equals("3") &&
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
                if (validarCampos())
                    return;
                guardar();
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

    private void deshabilitarInstitucionesAyudasTecnicas() {
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

    private void habilitarInstitucionesAyudasTecnicas() {
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

    private void habilitarPorcentajes() {
        if (discapacidadIntelectualRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton) {
            porcentajeIntelectualEditText.setEnabled(true);
        }
        if (discapacidadFisicaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadFisicaOpcion1RadioButton) {
            porcentajeFisicaEditText.setEnabled(true);
        }
        if (discapacidadCegueraRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadCegueraOpcion1RadioButton) {
            porcentajeCegueraEditText.setEnabled(true);
        }
        if (discapacidadVisionRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadVisionOpcion1RadioButton) {
            porcentajeVisionEditText.setEnabled(true);
        }

        if (discapacidadSorderaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadSorderaOpcion1RadioButton) {
            porcentajeSorderaEditText.setEnabled(true);
        }
        if (discapacidadHipoacusiaRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadIntelectualOpcion1RadioButton) {
            porcentajeHipoacusiaEditText.setEnabled(true);
        }
        if (discapacidadPsicosocialesRadioGroup.getCheckedRadioButtonId() == R.id.discapacidadPsicosocialesOpcion1RadioButton) {
            porcentajePsicosocialesEditText.setEnabled(true);
        }
    }

    private void deshabilitarPorcentajes() {
        porcentajeFisicaEditText.setEnabled(false);
        porcentajeIntelectualEditText.setEnabled(false);
        porcentajePsicosocialesEditText.setEnabled(false);
        porcentajeHipoacusiaEditText.setEnabled(false);
        porcentajeSorderaEditText.setEnabled(false);
        porcentajeVisionEditText.setEnabled(false);
        porcentajeCegueraEditText.setEnabled(false);
        porcentajeFisicaEditText.setText("");
        porcentajeIntelectualEditText.setText("");
        porcentajePsicosocialesEditText.setText("");
        porcentajeHipoacusiaEditText.setText("");
        porcentajeSorderaEditText.setText("");
        porcentajeVisionEditText.setText("");
        porcentajeCegueraEditText.setText("");
        porcentajeFisicaEditText.setError(null);
        porcentajeIntelectualEditText.setError(null);
        porcentajePsicosocialesEditText.setError(null);
        porcentajeHipoacusiaEditText.setError(null);
        porcentajeSorderaEditText.setError(null);
        porcentajeVisionEditText.setError(null);
        porcentajeCegueraEditText.setError(null);
    }

    public Boolean validarNumPorcentajes() {
        contador = 0;
        if (!porcentajeIntelectualEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajeFisicaEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajeCegueraEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajeVisionEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajeSorderaEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajeVisionEditText.getText().toString().equals("")) {
            contador++;
        }
        if (!porcentajePsicosocialesEditText.getText().toString().equals("")) {
            contador++;
        }
        return contador > 1;
    }

}
