package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.DpaManzanaDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.LocalidadDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.DpaManzana;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localidad;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

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
    private Spinner materialTechoSpinner;
    private Spinner materialPisoSpinner;
    private Spinner materialParedesSpinner;
    private Spinner estadoTechoSpinner;
    private Spinner estadoPisoSpinner;
    private Spinner estadoParedSpinner;

    private Button guadarButton;
    private ContentResolver contentResolver;
    private static Usuario usuario;
    private TabHost tabs;
    private static Vivienda vivienda;
    public boolean aplicaMalla = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
                container, false);

        Bundle extra = getActivity().getIntent().getExtras();
        contentResolver = getActivity().getContentResolver();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();

        try {
            vivienda = (Vivienda) extra.getSerializable("vivienda");
            //usuario = (Usuario) extra.getSerializable("usuario");

            if (vivienda.getId() != 0) {

                this.llenarCamposVivienda();
                /*vivienda.setFechaInicio(fechaYHoraInicio);
                vivienda.setFechaRegistro(Utilitarios.getCurrentDate());
                vivienda.setIdGrupo(usuario.getCodigoGrupo());*/

            }

        } catch (Exception e) {

            vivienda = new Vivienda();
            //vivienda.setFechainicio(fechaYHoraInicio);
            vivienda.setFechacreacion(Utilitarios.getCurrentDate());
            //vivienda.setIdgrupo(usuario.getCodigoGrupo();

            Utilitarios.logInfo(ViviendaFragment.class.getName(), "Formulario nuevo");
        }

        cargarUbicacionGeografica();

        return item;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabs = getActivity().findViewById(android.R.id.tabhost);
        tabs.setup();

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
        materialTechoSpinner = item.findViewById(R.id.materialTechoSpinner);
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

        int posicion = Utilitarios.getPosicionByKey(
                (ArrayAdapter<Values>) tipoLevantamientoSpinner.getAdapter(), String.valueOf(vivienda.getIdtipolevantamiento()));
        tipoLevantamientoSpinner.setSelection(posicion);

        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) areaSpinner.getAdapter(), String.valueOf(vivienda.getIdarea()));
        areaSpinner.setSelection(posicion);
        if (!vivienda.getLocalidad().equals(Global.CADENAS_VACIAS)) {
            localidadEditText.setText(vivienda.getLocalidad());
        } else {
            localidadEditText.setText("");
        }
        if (!vivienda.getDivision().equals(Global.CADENAS_VACIAS)) {
            divisionEditText.setText(String.valueOf(vivienda.getDivision()));
        } else {
            divisionEditText.setText("");
        }
        if (vivienda.getEdificio().equals(Global.CADENAS_VACIAS)) {
            edificioEditText.setText(vivienda.getEdificio());
        } else {
            edificioEditText.setText("");
        }
        if (!vivienda.getVivienda().equals(Global.CADENAS_VACIAS)) {
            viviendaEditText.setText(vivienda.getVivienda());
        } else {
            viviendaEditText.setText("");
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) hogarInicialSpinner.getAdapter(), String.valueOf(vivienda.getHogarinicial()));
        hogarInicialSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) hogarFinalSpinner.getAdapter(), String.valueOf(vivienda.getHogarfinal()));
        hogarFinalSpinner.setSelection(posicion);
        if (!vivienda.getCalle1().equals(Global.CADENAS_VACIAS)) {
            calle1EditText.setText(vivienda.getCalle1());
        } else {
            calle1EditText.setText("");
        }
        if (!vivienda.getNumerocasa().equals(Global.CADENAS_VACIAS)) {
            numeroCasaEditText.setText(vivienda.getNumerocasa());
        } else {
            numeroCasaEditText.setText("");
        }
        if (!vivienda.getCalle2().equals(Global.CADENAS_VACIAS)) {
            calle2EditText.setText(vivienda.getCalle2());
        } else {
            calle2EditText.setText("");
        }
        if (!vivienda.getConjuntohabitacional().equals(Global.CADENAS_VACIAS)) {
            conjuntoHabitacionalEditText.setText(vivienda.getConjuntohabitacional());
        } else {
            conjuntoHabitacionalEditText.setText("");
        }
        if (!vivienda.getLote().equals(Global.CADENAS_VACIAS)) {
            loteEditText.setText(String.valueOf(vivienda.getLote()));
        } else {
            loteEditText.setText("");
        }
        if (!vivienda.getDepartamento().equals(Global.CADENAS_VACIAS)) {
            departamentoEditText.setText(vivienda.getDepartamento());
        } else {
            departamentoEditText.setText("");
        }
        if (!vivienda.getPiso().equals(Global.CADENAS_VACIAS)) {
            pisoEditText.setText(vivienda.getPiso());
        } else {
            pisoEditText.setText("");
        }
        if (!vivienda.getTelefonoconvencional().equals(Global.CADENAS_VACIAS)) {
            telefonoConvencionalEditText.setText(vivienda.getTelefonoconvencional());
        } else {
            telefonoConvencionalEditText.setText("");
        }
        if (!vivienda.getTelefonocelular().equals(Global.CADENAS_VACIAS)) {
            telefonoCelularEditText.setText(vivienda.getTelefonocelular());
        } else {
            telefonoCelularEditText.setText("");
        }
        if (!vivienda.getReferenciaubicacion().equals(Global.CADENAS_VACIAS)) {
            referenciaUbicacionEditText.setText(vivienda.getReferenciaubicacion());
        } else {
            referenciaUbicacionEditText.setText("");
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) condicionOcupacionSpinner.getAdapter(), String.valueOf(vivienda.getIdocupada()));
        condicionOcupacionSpinner.setSelection(posicion);
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
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoTechoSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendaTecho()));
        estadoTechoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoPisoSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendaPiso()));
        estadoPisoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) estadoParedSpinner.getAdapter(), String.valueOf(vivienda.getIdestadoviviendaPared()));
        estadoParedSpinner.setSelection(posicion);

    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {

        tipoLevantamientoSpinner.setAdapter(ViviendaPreguntas.getTipoLevantamientoAdapter(getActivity()));
        areaSpinner.setAdapter(ViviendaPreguntas.getAreaAdapter(getActivity()));
        /**
         * hogar inicial
         */
        ArrayList<Values> hogarInicial = new ArrayList<Values>();

        hogarInicial.add(new Values("-1",
                getString(R.string.seleccionRespuesta)));

        for (int i = 1; i <= Global.MAXIMO_VALOR_HOGAR_FINAL; i++) {

            String value = String.valueOf(i);
            hogarInicial.add(new Values(value, value));

        }
        ArrayAdapter<Values> adapterHogarInicial = new ArrayAdapter<Values>(
                getActivity(), android.R.layout.simple_spinner_item,
                hogarInicial);
        adapterHogarInicial
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hogarInicialSpinner.setAdapter(adapterHogarInicial);

        /**
         * hogar final
         */
        ArrayList<Values> hogarFinal = new ArrayList<Values>();

        hogarFinal.add(new Values("-1",
                getString(R.string.seleccionRespuesta)));

        for (int i = 1; i <= Global.MAXIMO_VALOR_HOGAR_FINAL; i++) {

            String value = String.valueOf(i);
            hogarFinal.add(new Values(value, value));

        }
        ArrayAdapter<Values> adapterHogarFinal = new ArrayAdapter<Values>(
                getActivity(), android.R.layout.simple_spinner_item,
                hogarFinal);
        adapterHogarFinal
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hogarFinalSpinner.setAdapter(adapterHogarFinal);

        condicionOcupacionSpinner.setAdapter(ViviendaPreguntas.getCondicionOcupacionAdapter(getActivity()));

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

        vivienda.setIdtipolevantamiento(Integer
                .parseInt(((Values) tipoLevantamientoSpinner.getSelectedItem())
                        .getKey()));

        vivienda.setIdarea(Integer
                .parseInt(((Values) areaSpinner.getSelectedItem())
                        .getKey()));

        vivienda.setIdprovincia(((Values) provinciaSpinner.getSelectedItem()).getKey());
        vivienda.setIdcanton(((Values) cantonSpinner.getSelectedItem()).getKey());
        vivienda.setIdparroquia(((Values) parroquiaSpinner.getSelectedItem()).getKey());
        if (!TextUtils.isEmpty(localidadEditText.getText().toString())) {
            vivienda.setLocalidad(localidadEditText.getText()
                    .toString().trim());
        } else {
            vivienda.setLocalidad(Global.CADENAS_VACIAS);
        }
        vivienda.setZona(((Values) zonaSpinner.getSelectedItem()).getKey());

        vivienda.setSector(((Values) sectorSpinner.getSelectedItem()).getKey());

        vivienda.setManzana(((Values) manzanaSpinner.getSelectedItem()).getKey());
        if (!TextUtils.isEmpty(divisionEditText.getText().toString())) {
            vivienda.setDivision(divisionEditText.getText().toString());
        } else {
            vivienda.setDivision(Global.CADENAS_VACIAS);
        }

        if (!TextUtils.isEmpty(edificioEditText.getText().toString())) {
            vivienda.setEdificio(edificioEditText.getText().toString());
        } else {
            vivienda.setEdificio(Global.CADENAS_VACIAS);
        }

        if (!TextUtils.isEmpty(viviendaEditText.getText().toString())) {
            vivienda.setVivienda(viviendaEditText.getText().toString());
        } else {
            vivienda.setVivienda(Global.CADENAS_VACIAS);
        }

        vivienda.setHogarinicial(Integer.parseInt(((Values) hogarInicialSpinner
                .getSelectedItem()).getKey()));

        vivienda.setHogarfinal(Integer.parseInt(((Values) hogarFinalSpinner
                .getSelectedItem()).getKey()));
        if (!TextUtils.isEmpty(calle1EditText.getText().toString())) {
            vivienda.setCalle1(calle1EditText.getText().toString().trim());
        } else {
            vivienda.setCalle1(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(numeroCasaEditText.getText().toString())) {
            vivienda.setNumerocasa(numeroCasaEditText.getText().toString().trim());
        } else {
            vivienda.setNumerocasa(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(calle2EditText.getText().toString())) {

            vivienda.setCalle2(calle2EditText.getText().toString().trim());
        } else {
            vivienda.setCalle2(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(conjuntoHabitacionalEditText.getText().toString())) {
            vivienda.setConjuntohabitacional(conjuntoHabitacionalEditText.getText()
                    .toString().trim());
        } else {
            vivienda.setConjuntohabitacional(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(loteEditText.getText().toString())) {
            vivienda.setLote(loteEditText.getText().toString().trim());
        } else {
            vivienda.setLote(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(departamentoEditText.getText().toString())) {
            vivienda.setDepartamento(departamentoEditText.getText()
                    .toString().trim());
        } else {
            vivienda.setDepartamento(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(pisoEditText.getText().toString())) {
            vivienda.setPiso(pisoEditText.getText()
                    .toString());
        } else {
            vivienda.setPiso(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(telefonoConvencionalEditText.getText()
                .toString())) {
            vivienda.setTelefonoconvencional(telefonoConvencionalEditText.getText()
                    .toString());
        } else {
            vivienda.setTelefonoconvencional(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(telefonoCelularEditText.getText()
                .toString())) {
            vivienda.setTelefonocelular(telefonoCelularEditText.getText()
                    .toString());
        } else {
            vivienda.setTelefonocelular(Global.CADENAS_VACIAS);
        }
        if (!TextUtils.isEmpty(referenciaUbicacionEditText.getText()
                .toString())) {
            vivienda.setReferenciaubicacion(referenciaUbicacionEditText
                    .getText().toString().trim());
        } else {
            vivienda.setReferenciaubicacion(Global.CADENAS_VACIAS);
        }

        vivienda.setIdocupada(Integer.parseInt(((Values) condicionOcupacionSpinner.getSelectedItem()).getKey()));
        vivienda.setIdtipovivienda(Integer.parseInt(((Values) tipoViviendaSpinner.getSelectedItem()).getKey()));
        vivienda.setIdviacceso(Integer.parseInt(((Values) viaAccesoPrincipalSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialtecho(Integer.parseInt(((Values) materialTechoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialpiso(Integer.parseInt(((Values) materialPisoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdmaterialpared(Integer.parseInt(((Values) materialParedesSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendaTecho(Integer.parseInt(((Values) estadoTechoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendaPiso(Integer.parseInt(((Values) estadoPisoSpinner.getSelectedItem()).getKey()));
        vivienda.setIdestadoviviendaPared(Integer.parseInt(((Values) estadoParedSpinner.getSelectedItem()).getKey()));

        vivienda.setFechacreacion(Utilitarios.getCurrentDate());
        vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
        vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());

        //vivienda.setIdfase(fase.getId());
        vivienda.setIdfase(1);
        vivienda.setIdcontrolentrevista(1);


        if (vivienda.getId() == 0) {

            vivienda.setId(ViviendaDao.getUltimoRegistro(contentResolver, usuario));
            //vivienda.setVivcodigo(usuario.getCodigoDispositivo()+ "-" + ViviendaDao.getUltimoRegistro(contentResolver, usuario));
            vivienda.setVivcodigo("62"+ "-" + ViviendaDao.getUltimoRegistro(contentResolver, usuario));

            ViviendaDao.save(contentResolver, vivienda);
            hogarInicialSpinner.setEnabled(false);

            /*ArrayList<Localizacion> localizacionesTemp = Utilitarios.getLocalizacionesPorPresicion(localizaciones, 5);
            for (Localizacion localizacion : localizacionesTemp) {
                Log.e("", "localizacion final PROVEEDOR: " + localizacion.getProveedor() + " PRESICION: " + localizacion.getPresicion());
                localizacion.setViviendaId(vivienda.getId());
                LocalizacionDao.save(cr, localizacion);
            }*/


        } else {
            ViviendaDao.update(contentResolver, vivienda);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog;

        builder.setMessage(
                "Datos de la Vivienda ingresados correctamente, siga con la Sección Hogar")
                .setTitle(getString(R.string.confirmacion_aviso));

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        tabs.getTabWidget().getChildTabViewAt(1)
                                .setEnabled(true);
                        tabs.setCurrentTab(1);
                    }
                });
        dialog = builder.create();
        dialog.show();


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
            cancel = true;
            return cancel;
        }

        if (((Values) areaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.area));
            cancel = true;
            return cancel;
        }

        if (((Values) provinciaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.provincia));
            cancel = true;
            return cancel;
        }
        if (((Values) cantonSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.canton));
            cancel = true;
            return cancel;
        }
        if (((Values) parroquiaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.canton));
            cancel = true;
            return cancel;
        }
        if (((Values) zonaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.zona));
            cancel = true;
            return cancel;
        }

        if (((Values) sectorSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.sector));
            cancel = true;
            return cancel;
        }

        if (!((Values) zonaSpinner.getSelectedItem()).getKey().equals("999") &&
                ((Values) manzanaSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.manzana));
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

        calle2EditText.setError(null);
        calle2EditText.clearFocus();
        if (TextUtils.isEmpty(calle2EditText.getText().toString().trim())) {
            calle2EditText
                    .setError(getString(R.string.errorCampoRequerido));
            focusView = calle2EditText;
            cancel = true;
        }

        pisoEditText.setError(null);
        pisoEditText.clearFocus();
        if (TextUtils.isEmpty(pisoEditText.getText().toString().trim())) {
            pisoEditText
                    .setError(getString(R.string.errorCampoRequerido));
            focusView = pisoEditText;
            cancel = true;
        } else {
            if (Integer.valueOf(pisoEditText.getText().toString()) <= 0) {
                pisoEditText
                        .setError("El numero de piso debe ser un numero entero mayor que cero");
                focusView = pisoEditText;
                cancel = true;
            }
        }

        referenciaUbicacionEditText.setError(null);
        referenciaUbicacionEditText.clearFocus();
        if (TextUtils.isEmpty(referenciaUbicacionEditText.getText().toString().trim())) {
            referenciaUbicacionEditText
                    .setError(getString(R.string.errorCampoRequerido));
            focusView = referenciaUbicacionEditText;
            cancel = true;
        }

        telefonoConvencionalEditText.setError(null);
        telefonoConvencionalEditText.clearFocus();
        if ((telefonoConvencionalEditText.getText().toString().length() > 0)
                && (telefonoConvencionalEditText.getText().toString().length() < 9)) {

            telefonoConvencionalEditText
                    .setError(getString(R.string.error_numero_fijo));
            telefonoConvencionalEditText.requestFocus();
            cancel = true;
            return cancel;
        }

        if (((Values) tipoViviendaSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.tipoVivienda));
            cancel = true;
            return cancel;
        }

        if (((Values) viaAccesoPrincipalSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.viaAccesoPrincipal));
            cancel = true;
            return cancel;
        }

        if (((Values) materialTechoSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.materialTecho));
            cancel = true;
            return cancel;
        }

        if (((Values) materialPisoSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.materialPiso));
            cancel = true;
            return cancel;
        }

        if (((Values) materialParedesSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.materialParedes));
            cancel = true;
            return cancel;
        }

        if (((Values) estadoTechoSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.estadoTecho));
            cancel = true;
            return cancel;
        }

        if (((Values) estadoPisoSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.estadoPiso));
            cancel = true;
            return cancel;
        }

        if (((Values) estadoParedSpinner.getSelectedItem())
                .getKey().equals("-1")) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.estadoPared));
            cancel = true;
            return cancel;
        }


        return false;
    }

    /**
     * Muestra las alertas
     *
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
                guardar();

                /*Fragment newFragment = new HogarFragment();
                Bundle args = new Bundle();
                //args.putParcelable("persona", persona);

                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.viviendaFragmentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/

            }
        });

    }

    /**
     * Métdo que permite cargar la dpa, zonas, sectores y manzanas
     */
    public void cargarUbicacionGeografica() {
        /*Cursor cursor = contentResolver.query(
                FormularioManuelasProvider.CONTENT_URI_LOCALIDAD, new String[] {
                        "DISTINCT " + DpaManzana.COLUMNA_IDPROVINCIA,
                        DpaManzana.COLUMNA_PROVINCIA },
                null, null,
                DpaManzana.COLUMNA_PROVINCIA);*/

        String[] parametros = new String[]{"EC"};
        Cursor cursor = contentResolver.query(
                FormularioManuelasProvider.CONTENT_URI_LOCALIDAD, new String[]{
                        Localidad.COLUMNA_LOC_CODIGO,
                        Localidad.COLUMNA_LOC_DESCRIPCION},
                Localidad.whereByCodigoPadre, parametros,
                Localidad.COLUMNA_LOC_DESCRIPCION);

        //ArrayList<Values> localidades = DpaManzanaDao.getDpaManzanas(cursor);
        ArrayList<Values> localidades = LocalidadDao.getLocalidades(cursor);

        ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                getActivity(), android.R.layout.simple_spinner_item,
                localidades);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinciaSpinner.setAdapter(adapter1);

        if (vivienda.getId() != 0) {
            int posicion = Utilitarios.getPosicionByKey(
                    (ArrayAdapter<Values>) provinciaSpinner.getAdapter(),
                    vivienda.getIdprovincia());
            provinciaSpinner.setSelection(posicion);
        }

        provinciaSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @SuppressWarnings("unchecked")
                    public void onItemSelected(AdapterView<?> adapter,
                                               View arg1, int position, long arg3) {

                        Values provincia = (Values) adapter.getAdapter()
                                .getItem(position);

                        if (!provincia.getKey().equals("-1")) {

                            ArrayList<Values> localidades = null;
                            Cursor cursor = contentResolver.query(
                                    FormularioManuelasProvider.CONTENT_URI_LOCALIDAD, new String[]{
                                            Localidad.COLUMNA_LOC_CODIGO,
                                            Localidad.COLUMNA_LOC_DESCRIPCION},
                                    Localidad.whereByCodigoPadre, new String[]{provincia.getKey()},
                                    Localidad.COLUMNA_LOC_DESCRIPCION);

                            localidades = LocalidadDao.getLocalidades(cursor);
                            ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                                    getActivity(), android.R.layout.simple_spinner_item,
                                    localidades);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cantonSpinner.setAdapter(adapter1);

                            if (vivienda.getId() != 0 && aplicaMalla == true ) {
                                int posicion = Utilitarios.getPosicionByKey(
                                        (ArrayAdapter<Values>) cantonSpinner.getAdapter(),
                                        vivienda.getIdcanton());
                                cantonSpinner.setSelection(posicion);
                            }


                        } else {
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) cantonSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) parroquiaSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) zonaSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) sectorSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) manzanaSpinner
                                            .getAdapter());
                        }

                    }

                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

        cantonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressWarnings("unchecked")
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int position, long arg3) {

                Values canton = (Values) adapter.getAdapter().getItem(position);
                if (!canton.getKey().equals("-1")) {
                    //new GetParroquias().execute(canton.getKey());

                    ArrayList<Values> localidades = null;
                    Cursor cursor = contentResolver.query(FormularioManuelasProvider.CONTENT_URI_LOCALIDAD,
                            new String[]{Localidad.COLUMNA_LOC_CODIGO,
                                    Localidad.COLUMNA_LOC_DESCRIPCION},
                            Localidad.whereByCodigoPadre, new String[]{canton.getKey()},
                            Localidad.COLUMNA_LOC_DESCRIPCION);

                    localidades = LocalidadDao.getLocalidades(cursor);

                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    parroquiaSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla == true) {
                        int posicion = Utilitarios.getPosicionByKey(
                                (ArrayAdapter<Values>) parroquiaSpinner.getAdapter(),
                                vivienda.getIdparroquia());
                        parroquiaSpinner.setSelection(posicion);
                    }


                } else {
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) parroquiaSpinner
                                    .getAdapter());
                    Utilitarios.removeAll((ArrayAdapter<Values>) zonaSpinner
                            .getAdapter());
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) sectorSpinner
                                    .getAdapter());
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) manzanaSpinner
                                    .getAdapter());
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        parroquiaSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @SuppressWarnings("unchecked")
                    public void onItemSelected(AdapterView<?> adapter,
                                               View arg1, int position, long arg3) {

                        Values parroquia = (Values) adapter.getAdapter()
                                .getItem(position);
                        String _idParroquia = parroquia.getKey();
                        if (!_idParroquia.equals("-1")) {

                            String idProvincia = _idParroquia.substring(0, 2);
                            String idCanton = _idParroquia.substring(2, 4);
                            String idParroquia = _idParroquia.substring(4, 6);

                            String[] args = {idProvincia, idCanton,
                                    idParroquia};

                            ArrayList<Values> localidades = null;
                            Cursor cursor = contentResolver.query(
                                    FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                                    new String[]{"DISTINCT " + DpaManzana.COLUMNA_ZONA},
                                    DpaManzana.whereByProvinciaCantonParroquia, args,
                                    DpaManzana.COLUMNA_ZONA);

                            localidades = DpaManzanaDao.getZonaSectorManzana(cursor);

                            ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                                    getActivity(), android.R.layout.simple_spinner_item,
                                    localidades);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            zonaSpinner.setAdapter(adapter1);

                            if (vivienda.getId() != 0 && aplicaMalla == true) {
                                int posicion = Utilitarios.getPosicionByKey(
                                        (ArrayAdapter<Values>) zonaSpinner.getAdapter(),
                                        vivienda.getZona());
                                zonaSpinner.setSelection(posicion);
                            }
                            /*****  Directamente y no ocupar otro hilo ******/


                        } else {
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) zonaSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) sectorSpinner
                                            .getAdapter());
                            Utilitarios
                                    .removeAll((ArrayAdapter<Values>) manzanaSpinner
                                            .getAdapter());
                        }

                    }

                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

        zonaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressWarnings("unchecked")
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int position, long arg3) {

                Values parroquia = ((Values) parroquiaSpinner
                        .getSelectedItem());

                Values zona = (Values) adapter.getAdapter().getItem(position);

                String _idParroquia = parroquia.getKey();
                if (!zona.getKey().equals("-1")) {

                    String idProvincia = _idParroquia.substring(0, 2);
                    String idCanton = _idParroquia.substring(2, 4);
                    String idParroquia = _idParroquia.substring(4, 6);

                    String[] args = { idProvincia, idCanton, idParroquia,
                            zona.getKey() };

                    if (zona.getKey().equals("999"))
                    {
                        manzanaSpinner.setEnabled(false);
                    }
                    else
                    {
                        manzanaSpinner.setEnabled(true);
                    }

                    /*****  Directamente y no ocupar otro hilo ******/

                    ArrayList<Values> localidades = null;

                    Cursor cursor = contentResolver.query(
                            FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                            new String[] { "DISTINCT " + DpaManzana.COLUMNA_SECTOR },
                            DpaManzana.whereByProvinciaCantonParroquiaZona,
                            args, DpaManzana.COLUMNA_SECTOR);

                    localidades = DpaManzanaDao.getZonaSectorManzana(cursor);


                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectorSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla == true) {
                        int posicion = Utilitarios.getPosicionByKey(
                                (ArrayAdapter<Values>) sectorSpinner.getAdapter(),
                                vivienda.getSector());
                        sectorSpinner.setSelection(posicion);
                    }

                    /*****  Directamente y no ocupar otro hilo ******/

                } else {
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) sectorSpinner
                                    .getAdapter());
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) manzanaSpinner
                                    .getAdapter());
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        sectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressWarnings("unchecked")
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int position, long arg3) {

                Values parroquia = ((Values) parroquiaSpinner
                        .getSelectedItem());
                Values zona = ((Values) zonaSpinner.getSelectedItem());

                Values sector = (Values) adapter.getAdapter().getItem(position);

                String _idParroquia = parroquia.getKey();
                if (!sector.getKey().equals("-1")) {

                    String idProvincia = _idParroquia.substring(0, 2);
                    String idCanton = _idParroquia.substring(2, 4);
                    String idParroquia = _idParroquia.substring(4, 6);

                    String[] args = { idProvincia, idCanton, idParroquia,
                            zona.getKey(), sector.getKey() };

                    //taskManzanas = new GetManzanas();
                    //taskManzanas.execute(args);

                    /*****  Directamente y no ocupar otro hilo ******/

                    ArrayList<Values> localidades = null;

                    Cursor cursor = contentResolver.query(
                            FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                            new String[] { DpaManzana.COLUMNA_MANZANA },
                            DpaManzana.whereByProvinciaCantonParroquiaZonaSector,
                            args, "CAST(" + DpaManzana.COLUMNA_MANZANA  + " AS INTEGER)");

                    localidades = DpaManzanaDao.getZonaSectorManzana(cursor);


                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<Values>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    manzanaSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla == true) {
                        int posicion = Utilitarios.getPosicionByKey(
                                (ArrayAdapter<Values>) manzanaSpinner.getAdapter(),
                                String.valueOf(vivienda.getManzana()));
                        manzanaSpinner.setSelection(posicion);

                        aplicaMalla = false;
                    }


                } else {
                    Utilitarios
                            .removeAll((ArrayAdapter<Values>) manzanaSpinner
                                    .getAdapter());
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



    }

    /**
     * regresa la vivienda
     * @return
     */
    public static Vivienda getVivienda() {
        return vivienda;
    }


}
