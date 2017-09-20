package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.TabHost;

import ec.gob.stptv.formularioManuelas.*;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.HogarDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

/***
 * Autor: Christian J. Tintin
 */

public class HogarFragment extends Fragment {

    private EditText codigoElectricoEditText;
    private Button guardarPersonaButton;
    //private Button atrasButton;
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
    private Spinner numCuartosSpinner;
    private Spinner numDormitoriosSpinner;
    private RadioGroup gasParaCalefonOpcion;
    private RadioGroup terrenoAgropecuario;
    private RadioGroup terrenoAgropecuarioSi;
    private static Hogar hogar;
    private TabHost tabs;
    private Vivienda vivienda;
    private ContentResolver contentResolver;


    private Spinner tipoViviendaSpinner;
    private Spinner viaAccesoPrincipalSpinner;
    private Spinner materialTechoSpinner;
    private Spinner materialPisoSpinner;
    private Spinner materialParedesSpinner;
    private Spinner estadoTechoSpinner;
    private Spinner estadoPisoSpinner;
    private Spinner estadoParedSpinner;
    private LinearLayout pantallaHogarLinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_hogar,
                container, false);

        this.contentResolver = getActivity().getContentResolver();

        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();
        this.mallasValidacion();


        return item;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabs = getActivity().findViewById(android.R.id.tabhost);
        String[] parametros;

        this.vivienda = ViviendaFragment.getVivienda();

        if (vivienda.getId()!= 0){
            if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                    && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA.getValor()) {
                Utilitarios.disableEnableViews(getActivity(), false, pantallaHogarLinearLayout);
            }
        }
        parametros = new String[]{String.valueOf(vivienda.getId())};
        hogar = HogarDao.getHogar(contentResolver, Hogar.whereByViviendaId, parametros);
        if (hogar != null) {
            this.llenarCamposHogar();
        } else {
            hogar = new Hogar();
        }

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
        guardarPersonaButton = item.findViewById(R.id.guardarButton);
        gasParaCalefonOpcion = item.findViewById(R.id.gasParaCalefon);
        terrenoAgropecuario = item.findViewById(R.id.terrenoAgropecuario);
        terrenoAgropecuarioSi = item.findViewById(R.id.terrenoAgropecuarioSi);
        //atrasButton = item.findViewById(R.id.atrasButton);

        tipoViviendaSpinner = item.findViewById(R.id.tipoViviendaSpinner);
        viaAccesoPrincipalSpinner = item.findViewById(R.id.viaAccesoPrincipalSpinner);
        materialTechoSpinner = item.findViewById(R.id.materialTechoSpinner);
        materialPisoSpinner = item.findViewById(R.id.materialPisoSpinner);
        materialParedesSpinner = item.findViewById(R.id.materialParedesSpinner);
        estadoTechoSpinner = item.findViewById(R.id.estadoTechoSpinner);
        estadoPisoSpinner = item.findViewById(R.id.estadoPisoSpinner);
        estadoParedSpinner = item.findViewById(R.id.estadoParedSpinner);
        pantallaHogarLinearLayout = item.findViewById(R.id.pantallaHogarLinearLayout);


    }

    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCamposHogar() {
        int posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tipoHogarSpinner.getAdapter(), String.valueOf(hogar.getIdpropiedadvivienda()));
        tipoHogarSpinner.setSelection(posicion);

        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) documentoHogarSpinner.getAdapter(), String.valueOf(hogar.getIddocumentovivienda()));
        documentoHogarSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) numCuartosSpinner.getAdapter(), String.valueOf(hogar.getCuartos()));
        numCuartosSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) numDormitoriosSpinner.getAdapter(), String.valueOf(hogar.getDormitorio()));
        numDormitoriosSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) fuenteAguaSpinner.getAdapter(), String.valueOf(hogar.getIdredagua()));
        fuenteAguaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) ubicacionAguaSpinner.getAdapter(), String.valueOf(hogar.getIdagua()));
        ubicacionAguaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tratamientoAguaSpinner.getAdapter(), String.valueOf(hogar.getIdtratamientoagua()));
        tratamientoAguaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) servicioSanitarioSpinner.getAdapter(), String.valueOf(hogar.getIdtiposshh()));
        servicioSanitarioSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) ubicacionSanitarioSpinner.getAdapter(), String.valueOf(hogar.getIdsshh()));
        ubicacionSanitarioSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) servicioDuchaSpinner.getAdapter(), String.valueOf(hogar.getIdducha()));
        servicioDuchaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) eliminaBasuraSpinner.getAdapter(), String.valueOf(hogar.getIdbasura()));
        eliminaBasuraSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tipoAlumbradoSpinner.getAdapter(), String.valueOf(hogar.getIdalumbrado()));
        tipoAlumbradoSpinner.setSelection(posicion);
        if (!hogar.getPlanillapago().equals(Global.CADENAS_VACIAS)) {
            codigoElectricoEditText.setText(String.valueOf(hogar.getPlanillapago()));
        } else {
            codigoElectricoEditText.setText("");
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) energeticoCocinaSpinner.getAdapter(), String.valueOf(hogar.getIdtipococina()));
        energeticoCocinaSpinner.setSelection(posicion);
        if (hogar.getGascalefon() == Global.SI) {
            gasParaCalefonOpcion
                    .check(R.id.gasParaCalefonOpcion1);
        } else {
            if (hogar.getGascalefon() == Global.NO) {
                gasParaCalefonOpcion
                        .check(R.id.gasParaCalefonOpcion2);
            }
        }
        if (hogar.getTerreno() == Global.SI) {
            terrenoAgropecuario
                    .check(R.id.terrenoAgropecuarioOpcion1rb);
        } else {
            if (hogar.getTerreno() == Global.NO) {
                terrenoAgropecuario
                        .check(R.id.terrenoAgropecuarioOpcion2rb);
            }
        }
        if (hogar.getTerrenoservicios() == Global.SI) {
            terrenoAgropecuarioSi
                    .check(R.id.terrenoAgropecuarioSiOpcion1rb);
        } else {
            if (hogar.getTerrenoservicios() == Global.NO) {
                terrenoAgropecuarioSi
                        .check(R.id.terrenoAgropecuarioSiOpcion2rb);
            }
        }

        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tipoViviendaSpinner.getAdapter(), String.valueOf(vivienda.getIdtipovivienda()));
        tipoViviendaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) viaAccesoPrincipalSpinner.getAdapter(), String.valueOf(vivienda.getIdviacceso()));
        viaAccesoPrincipalSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) materialTechoSpinner.getAdapter(), String.valueOf(vivienda.getIdmaterialtecho()));
        materialTechoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) materialPisoSpinner.getAdapter(), String.valueOf(vivienda.getIdmaterialpiso()));
        materialPisoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) materialParedesSpinner.getAdapter(), String.valueOf(vivienda.getIdmaterialpared()));
        materialParedesSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoTechoSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendatecho()));
        estadoTechoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoPisoSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendapiso()));
        estadoPisoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoParedSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendapared()));
        estadoParedSpinner.setSelection(posicion);

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

        tipoViviendaSpinner.setAdapter(ViviendaPreguntas.getTipoViviendaAdapter(getActivity()));
        viaAccesoPrincipalSpinner.setAdapter(ViviendaPreguntas.getViviendaViaAccesoPrincipalAdapter(getActivity()));
        materialTechoSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialTechoAdapter(getActivity()));
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

        vivienda.setIdtipovivienda(Integer.parseInt(((Values) tipoViviendaSpinner.getSelectedItem()).getKey()));
        vivienda.setIdviacceso(Integer.parseInt(((Values) viaAccesoPrincipalSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialtecho(Integer.parseInt(((Values) materialTechoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialpiso(Integer.parseInt(((Values) materialPisoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialpared(Integer.parseInt(((Values) materialParedesSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendatecho(Integer.parseInt(((Values) estadoTechoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendapiso(Integer.parseInt(((Values) estadoPisoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendapared(Integer.parseInt(((Values) estadoParedSpinner.getSelectedItem()).getKey()));
        ViviendaDao.update(contentResolver,vivienda);

        hogar.setIdpropiedadvivienda(Integer.parseInt(((Values) tipoHogarSpinner.getSelectedItem()).getKey()));
        hogar.setIddocumentovivienda(Integer.parseInt(((Values) documentoHogarSpinner.getSelectedItem()).getKey()));
        hogar.setCuartos(Integer.parseInt(((Values) numCuartosSpinner.getSelectedItem()).getKey()));
        hogar.setDormitorio(Integer.parseInt(((Values) numDormitoriosSpinner.getSelectedItem()).getKey()));
        hogar.setIdredagua(Integer.parseInt(((Values) fuenteAguaSpinner.getSelectedItem()).getKey()));
        hogar.setIdagua(Integer.parseInt(((Values) ubicacionAguaSpinner.getSelectedItem()).getKey()));
        hogar.setIdtratamientoagua(Integer.parseInt(((Values) tratamientoAguaSpinner.getSelectedItem()).getKey()));
        hogar.setIdtiposshh(Integer.parseInt(((Values) servicioSanitarioSpinner.getSelectedItem()).getKey()));
        hogar.setIdsshh(Integer.parseInt(((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey()));
        hogar.setIdducha(Integer.parseInt(((Values) servicioDuchaSpinner.getSelectedItem()).getKey()));
        hogar.setIdbasura(Integer.parseInt(((Values) eliminaBasuraSpinner.getSelectedItem()).getKey()));
        hogar.setIdalumbrado(Integer.parseInt(((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey()));
        if (!(TextUtils.isEmpty(codigoElectricoEditText.getText()))) {
            hogar.setPlanillapago(codigoElectricoEditText.getText().toString().trim());
        } else {
            hogar.setPlanillapago(Global.CADENAS_VACIAS);
        }
        hogar.setIdtipococina(Integer.parseInt(((Values) energeticoCocinaSpinner.getSelectedItem()).getKey()));

        if (gasParaCalefonOpcion.getCheckedRadioButtonId() == R.id.gasParaCalefonOpcion1) {
            hogar.setGascalefon(Global.SI);
        } else {
            if (gasParaCalefonOpcion.getCheckedRadioButtonId() == R.id.gasParaCalefonOpcion2) {
                hogar.setGascalefon(Global.NO);
            } else {
                hogar.setGascalefon(Global.ENTEROS_VACIOS_CATALOGOS);
            }
        }

        if (terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb) {
            hogar.setTerreno(Global.SI);
        } else {
            if (terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion2rb) {
                hogar.setTerreno(Global.NO);
            } else {
                hogar.setTerreno(Global.ENTEROS_VACIOS_CATALOGOS);
            }
        }

        if (terrenoAgropecuarioSi.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioSiOpcion1rb) {
            hogar.setTerrenoservicios(Global.SI);
        } else {
            if (terrenoAgropecuarioSi.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioSiOpcion2rb) {
                hogar.setTerrenoservicios(Global.NO);
            } else {
                hogar.setTerrenoservicios(Global.ENTEROS_VACIOS_CATALOGOS);
            }
        }
        hogar.setFechainicio(Utilitarios.getCurrentDateAndHour());
        hogar.setFechafin(Utilitarios.getCurrentDateAndHour());

        if (hogar.getId() == 0) {
            Utilitarios.logInfo(HogarFragment.class.getName(), "Guardar hogar");
            hogar.setIdvivienda(vivienda.getId());
            Uri uri = HogarDao.save(contentResolver, hogar);
            String id = uri.getPathSegments().get(1);
            hogar.setId(Integer.parseInt(id));

        } else {
            Utilitarios.logInfo(HogarFragment.class.getName(), "Actualiza hogar");
            HogarDao.update(contentResolver, hogar);
        }

    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {

        //Listener para cuando cambia la elección Tipo de Hogar
        tipoViviendaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(((Values) tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.ENTEROS_VACIOS_CATALOGOS))){
                    materialTechoSpinner.setSelection(0);
                    materialTechoSpinner.setEnabled(false);

                    materialPisoSpinner.setSelection(0);
                    materialPisoSpinner.setEnabled(false);

                    materialParedesSpinner.setSelection(0);
                    materialParedesSpinner.setEnabled(false);

                    numCuartosSpinner.setSelection(0);
                    numCuartosSpinner.setEnabled(false);

                    numDormitoriosSpinner.setSelection(0);
                    numDormitoriosSpinner.setEnabled(false);

                    servicioSanitarioSpinner.setSelection(0);
                    servicioSanitarioSpinner.setEnabled(false);

                    ubicacionAguaSpinner.setSelection(0);
                    ubicacionAguaSpinner.setEnabled(false);

                    ubicacionSanitarioSpinner.setSelection(0);
                    ubicacionSanitarioSpinner.setEnabled(false);

                }else{
                    materialTechoSpinner.setEnabled(true);

                    materialPisoSpinner.setEnabled(true);

                    materialParedesSpinner.setEnabled(true);

                    numCuartosSpinner.setEnabled(true);

                    numDormitoriosSpinner.setEnabled(true);

                    servicioSanitarioSpinner.setEnabled(true);

                    ubicacionAguaSpinner.setEnabled(true);

                    ubicacionSanitarioSpinner.setEnabled(true);
                }


                if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("1") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("5")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("4") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("5") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else if(!((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values)materialPisoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)tipoViviendaSpinner.getSelectedItem()).getKey())>4 &&
                        Integer.parseInt(((Values)materialPisoSpinner.getSelectedItem()).getKey())<4){
                    materialPisoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondePiso));
                }
                else if(!((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values)materialParedesSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)tipoViviendaSpinner.getSelectedItem()).getKey())>4 &&
                        Integer.parseInt(((Values)materialParedesSpinner.getSelectedItem()).getKey())<3){
                    materialParedesSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondePared));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")){
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeDepartamento));
                    servicioSanitarioSpinner.setSelection(0);
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values)ubicacionAguaSpinner.getSelectedItem()).getKey().equals("3")){
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeDepartamentoUbicacionAgua));
                    ubicacionAguaSpinner.setSelection(0);
                }
                else if(((Values) tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("3")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.mensajeDepartamentoUbicacionSanitario));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        materialTechoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("1") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("5")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else
                if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("4") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("5") &&
                        ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1")){
                    materialTechoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeTecho));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("4") &&
                        !((Values)numCuartosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)numCuartosSpinner.getSelectedItem()).getKey())>2){
                    numCuartosSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeCuartos));
                }
                else if(((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1") &&
                            ((Values)materialParedesSpinner.getSelectedItem()).getKey().equals("4")){
                    materialParedesSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeMaterialTechoParedes));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        materialPisoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values)materialPisoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)tipoViviendaSpinner.getSelectedItem()).getKey())>4 &&
                        Integer.parseInt(((Values)materialPisoSpinner.getSelectedItem()).getKey())<4){
                    materialPisoSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondePiso));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        materialParedesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values)materialParedesSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)tipoViviendaSpinner.getSelectedItem()).getKey())>4 &&
                        Integer.parseInt(((Values)materialParedesSpinner.getSelectedItem()).getKey())<3){
                    materialParedesSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondePared));
                }else if(((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("1") &&
                        ((Values)materialParedesSpinner.getSelectedItem()).getKey().equals("4")){
                    materialParedesSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeMaterialTechoParedes));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tipoHogarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("1") ||
                        ((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals("2")) {
                    documentoHogarSpinner.setEnabled(true);
                } else {
                    documentoHogarSpinner.setSelection(0);
                    documentoHogarSpinner.setEnabled(false);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        terrenoAgropecuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb) {
                    for (int cont = 0; cont < terrenoAgropecuarioSi.getChildCount(); cont++) {
                        terrenoAgropecuarioSi.getChildAt(cont).setEnabled(true);
                    }
                } else {
                    terrenoAgropecuarioSi.check(-1);
                    for (int cont = 0; cont < terrenoAgropecuarioSi.getChildCount(); cont++) {
                        terrenoAgropecuarioSi.getChildAt(cont).setEnabled(false);
                    }
                }

            }
        });

        numCuartosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!((Values) numCuartosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values) numDormitoriosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values) numCuartosSpinner.getSelectedItem()).getKey()) < Integer.parseInt(((Values) numDormitoriosSpinner.getSelectedItem()).getKey())) {
                    numDormitoriosSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDormitoriosMayorCuartos));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("4") &&
                        !((Values)numCuartosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values)numCuartosSpinner.getSelectedItem()).getKey())>2){
                    numCuartosSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeCuartos));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        numDormitoriosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!((Values) numDormitoriosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values) numCuartosSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values) numCuartosSpinner.getSelectedItem()).getKey()) < Integer.parseInt(((Values) numDormitoriosSpinner.getSelectedItem()).getKey())) {
                    numDormitoriosSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDormitoriosMayorCuartos));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ubicacionAguaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values)ubicacionAguaSpinner.getSelectedItem()).getKey().equals("3")){
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeDepartamentoUbicacionAgua));
                    ubicacionAguaSpinner.setSelection(0);
                }
            }

            @Override
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
                else {
                    ubicacionSanitarioSpinner.setEnabled(true);
                }
                if(((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("5") &&
                        ((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("1")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.seccion3MensajeNoCorrespondeLetrina));
                }
                else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")){
                    servicioSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeDepartamento));
                }
                else if(((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("1") &&
                        ((Values)ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("3")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeSanitarioUbicacionSanitario));
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ubicacionSanitarioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(((Values) servicioSanitarioSpinner.getSelectedItem()).getKey().equals("5") &&
                        ((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("1")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.seccion3MensajeNoCorrespondeLetrina));
                }else if(((Values) tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                        ((Values) ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("3")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.mensajeDepartamentoUbicacionSanitario));
                }else if(((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("1") &&
                        ((Values)ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("3")){
                    ubicacionSanitarioSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso),getString(R.string.mensajeSanitarioUbicacionSanitario));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        tipoAlumbradoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey()) == 1) {
                    codigoElectricoEditText.setEnabled(true);
                } else if (!((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values) energeticoCocinaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey()) > 2 &&
                        Integer.parseInt(((Values) energeticoCocinaSpinner.getSelectedItem()).getKey()) == 3) {
                    energeticoCocinaSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorAlumbradoCocina));
                } else {
                    codigoElectricoEditText.setText("");
                    codigoElectricoEditText.setEnabled(false);
                    codigoElectricoEditText.setError(null);
                    codigoElectricoEditText.clearFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        energeticoCocinaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        !((Values) energeticoCocinaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) &&
                        Integer.parseInt(((Values) tipoAlumbradoSpinner.getSelectedItem()).getKey()) > 2 &&
                        Integer.parseInt(((Values) energeticoCocinaSpinner.getSelectedItem()).getKey()) == 3) {
                    energeticoCocinaSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.errorAlumbradoCocina));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        documentoHogarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(vivienda.getIdtipovivienda()>2 && Integer.parseInt(((Values)documentoHogarSpinner.getSelectedItem()).getKey())>0
//                        && !((Values)documentoHogarSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))){
//                    getAlert(getString(R.string.errorTipoUno), getString(R.string.seleccione_pregunta) + getString(R.string.errorDocumentoPorTipoVivienda));
//                    documentoHogarSpinner.setSelection(0);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView){
//
//            }
//        });

    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     */
    protected boolean validarCampos() {
        boolean cancel = true;
        if (((Values) tipoViviendaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoVivienda));
        } else if (((Values) viaAccesoPrincipalSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.viaAccesoPrincipal));
        } else if (((Values) materialTechoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.materialTecho));
        } else if (((Values) materialPisoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.materialPiso));
        } else if (((Values) materialParedesSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.materialParedes));
        } else if (((Values) estadoTechoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.estadoTecho));
        } else if (((Values) estadoPisoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.estadoPiso));
        } else if (((Values) estadoParedSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.estadoPared));
        } else if (((Values) tipoHogarSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
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
            codigoElectricoEditText.requestFocus();
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
        } else if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                ((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("6")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.seccion3MensajeNoCorrespondeDepartamento));

        }else {
            cancel = false;
        }
        if (!TextUtils.isEmpty(codigoElectricoEditText.getText().toString())) {

            if (codigoElectricoEditText.getText().toString().length() != 10) {
                codigoElectricoEditText.setError(getString(R.string.error_numero_medidor));
                codigoElectricoEditText.requestFocus();
                return true;
            }
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
                if (validarCampos())
                    return;
                guardar();

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setCancelable(false);
                builder.setMessage(
                        "Datos del Hogar ingresados correctamente, siga con la Sección Miembros del Hogar")
                        .setTitle(R.string.confirmacion_aviso);

                builder.setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                tabs.getTabWidget().getChildTabViewAt(2)
                                        .setEnabled(true);
                                tabs.setCurrentTab(2);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                mensajesAlerta();
            }
        });

//        atrasButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment newFragment = new ViviendaFragment();
//                Bundle args = new Bundle();
//                newFragment.setArguments(args);
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentContainer, newFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//            }
//        });


    }

    /**
     * Metodo para mensajes de alerta
     *
     * @return boolean
     */
    protected void mensajesAlerta() {

        if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("1") &&
                ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("6")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idTipoViviendaCasaMaterialTecho));
        }

        if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                ((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("6")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idTipoViviendaDepartamentoMaterialTecho));
        }

        if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                ((Values)ubicacionAguaSpinner.getSelectedItem()).getKey().equals("2")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idTipoViviendaUbicacionAgua));
        }

        if(((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("2") &&
                ((Values)ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("2")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idTipoViviendaUbicacionSanitario));
        }

        if( ((Values)tipoViviendaSpinner.getSelectedItem()).getKey().equals("3") &&
                Integer.parseInt(((Values)tipoHogarSpinner.getSelectedItem()).getKey()) < 3 &&
                ((Values)documentoHogarSpinner.getSelectedItem()).getKey().equals("1") ){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idTipoViviendaTipoHogarDocumento));
        }

        if(((Values)materialTechoSpinner.getSelectedItem()).getKey().equals("6")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_materialTechoOpcionOtro));
        }

        if(((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("3") &&
                ((Values)ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("1")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idServicioSanitarioUbicacionSanitarioPozo));
        }

        if(((Values)servicioSanitarioSpinner.getSelectedItem()).getKey().equals("4") &&
                ((Values)ubicacionSanitarioSpinner.getSelectedItem()).getKey().equals("1")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idServicioSanitarioUbicacionSanitarioDescarga));
        }

        if(((Values)viaAccesoPrincipalSpinner.getSelectedItem()).getKey().equals("6") &&
                ((Values)eliminaBasuraSpinner.getSelectedItem()).getKey().equals("1")){
            getAlert(getString(R.string.validacion_aviso),getString(R.string.mv_idViaAccesoPrincipalEliminanBasura));
        }
    }


    /**
     * regresa la vivienda
     *
     * @return Objeto Hogar
     */
    public static Hogar getHogar() {
        return hogar;
    }
}
