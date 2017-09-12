package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.graphics.Bitmap;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.servicio.LocalizacionService;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.DpaManzanaDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.HogarDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.LocalidadDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.LocalizacionDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.PersonaDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.DpaManzana;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localidad;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localizacion;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/***
 * Autor:Christian Tintin
 */
public class ViviendaFragment extends Fragment {

    private TextView faseTextView;
    private Spinner tipoLevantamientoSpinner;
    private Spinner areaSpinner;
    private Spinner provinciaSpinner;
    private Spinner cantonSpinner;
    private Spinner parroquiaSpinner;
    private Spinner parroquiaUrbanoSpinner;
    private Spinner zonaSpinner;
    private Spinner sectorSpinner;
    private Spinner manzanaSpinner;
    private Spinner hogarInicialSpinner;
    private Spinner hogarFinalSpinner;

    private EditText divisionEditText;
    private EditText edificioEditText;
    private EditText viviendaEditText;
    private EditText localidadEditText;
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


    private Button guadarButton;
    private ContentResolver contentResolver;
    private static Usuario usuario;
    private TabHost tabs;
    private static Vivienda vivienda;
    public boolean aplicaMalla = true;
    private static boolean isEnabledObervacion = true;
    private LinearLayout pantallaControlViendaLinearLayout;

    //Atriutos para la captura de puntos GPS
    private ArrayList<Localizacion> localizaciones = new ArrayList<>();
    private int maxNumeroLocalizaciones = 50;
    private Location location = null;
    //private ProgressReceiver progressReceiver;
    private LocationManager locationManager = null;
    private TextView latitudTextView;
    private TextView longitudTextView;
    private Button capturarPuntoGPSbutton;
    private Fase fase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
                container, false);
        Bundle extra = getActivity().getIntent().getExtras();
        contentResolver = getActivity().getContentResolver();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.habilitarDeshabilitar();
        this.realizarAcciones();
        try {
            vivienda = (Vivienda) extra.getSerializable("vivienda");
            usuario = (Usuario) extra.getSerializable("usuario");
            fase = (Fase) extra.getSerializable("fase");

            if (vivienda.getId() != 0) {
                this.llenarCamposVivienda();
                vivienda.setFechainicio(Utilitarios.getCurrentDateAndHour());
                vivienda.setFechaencuesta(Utilitarios.getCurrentDate());
            }
        } catch (Exception e) {
            vivienda = new Vivienda();
            vivienda.setFechainicio(Utilitarios.getCurrentDateAndHour());
            vivienda.setFechaencuesta(Utilitarios.getCurrentDate());
            Utilitarios.logInfo(ViviendaFragment.class.getName(), "Formulario nuevo");
        }
        cargarUbicacionGeografica();
        this.mallasValidacion();

        return item;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        faseTextView.setText(fase.getNombrefase());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLastLocation();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Global.BROADCAST_ACTION_PROGRESS);
        filter.addAction(Global.BROADCAST_ACTION_FIN);
        //progressReceiver = new ProgressReceiver();
        //getActivity().registerReceiver(progressReceiver, filter);

        tabs = getActivity().findViewById(android.R.id.tabhost);
        tabs.setup();
        if (vivienda.getId() != 0) {
            if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                    && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA.getValor()) {
                Utilitarios.disableEnableViews(getActivity(), false, pantallaControlViendaLinearLayout);
                isEnabledObervacion = false;
                getActivity().invalidateOptionsMenu();
            } else {
                if ((vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INFORMANTE_NO_CALIFICADO.getValor()
                        || vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.NADIE_EN_CASA.getValor()
                        || vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.RECHAZO.getValor())) {
                    Utilitarios.disableEnableViews(getActivity(), false, pantallaControlViendaLinearLayout);
                    isEnabledObervacion = false;
                    getActivity().invalidateOptionsMenu();
                    tabs.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                    tabs.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                    tabs.getTabWidget().getChildTabViewAt(3).setEnabled(false);

                } else {
                    isEnabledObervacion = true;
                    getActivity().invalidateOptionsMenu();
                }
            }
        }else{
            isEnabledObervacion = true;
            getActivity().invalidateOptionsMenu();
        }

        /*
        if (vivienda.getId() == 0) {
            tabs.getTabWidget().getChildTabViewAt(1).setEnabled(false);
            tabs.getTabWidget().getChildTabViewAt(2).setEnabled(false);
            tabs.getTabWidget().getChildTabViewAt(3).setEnabled(false);
            isEnabledObervacion = true;
            getActivity().invalidateOptionsMenu();
        } else {
            if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                    && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INCOMPLETA.getValor()) {
                isEnabledObervacion = true;
                Hogar hogar = HogarDao.getHogar(contentResolver, Hogar.whereById, new String[]{String.valueOf(vivienda.getId())});

                if (hogar != null){
                    if (hogar.getId() != 0) {
                        tabs.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                        tabs.getTabWidget().getChildTabViewAt(2).setEnabled(true);
                        tabs.getTabWidget().getChildTabViewAt(3).setEnabled(true);
                    } else {
                        tabs.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                        tabs.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                        tabs.getTabWidget().getChildTabViewAt(3).setEnabled(false);
                    }
                }else{
                    tabs.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                    tabs.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                    tabs.getTabWidget().getChildTabViewAt(3).setEnabled(false);
                }

                getActivity().invalidateOptionsMenu();
            } else {
                if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                        && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA.getValor()) {
                    Utilitarios.disableEnableViews(getActivity(), false, pantallaControlViendaLinearLayout);
                    condicionOcupacionSpinner.setEnabled(false);
                    tabs.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                    tabs.getTabWidget().getChildTabViewAt(2).setEnabled(true);
                    tabs.getTabWidget().getChildTabViewAt(3).setEnabled(true);

                    isEnabledObervacion = false;
                    getActivity().invalidateOptionsMenu();
                } else {
                    if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                            && (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INFORMANTE_NO_CALIFICADO.getValor()
                            || vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.NADIE_EN_CASA.getValor()
                            || vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.RECHAZO.getValor())) {
                        Utilitarios.disableEnableViews(getActivity(), false, pantallaControlViendaLinearLayout);
                        condicionOcupacionSpinner.setEnabled(false);
                        tabs.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                        tabs.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                        tabs.getTabWidget().getChildTabViewAt(3).setEnabled(false);

                        isEnabledObervacion = false;
                        getActivity().invalidateOptionsMenu();
                    }
                }
            }
        }*/
    }


    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {

        faseTextView = item.findViewById(R.id.faseTextView);
        tipoLevantamientoSpinner = item.findViewById(R.id.tipoLevantamientoSpinner);
        areaSpinner = item.findViewById(R.id.areaSpinner);
        provinciaSpinner = item.findViewById(R.id.provinciaSpinner);
        cantonSpinner = item.findViewById(R.id.cantonSpinner);
        parroquiaSpinner = item.findViewById(R.id.parroquiaSpinner);
        parroquiaUrbanoSpinner = item.findViewById(R.id.parroquiaUrbanoSpinner);
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
        guadarButton = item.findViewById(R.id.guardarButton);
        pantallaControlViendaLinearLayout = item.findViewById(R.id.pantallaControlViendaLinearLayout);
        latitudTextView = item.findViewById(R.id.latitudTextView);
        longitudTextView = item.findViewById(R.id.longitudTextView);
        capturarPuntoGPSbutton = item.findViewById(R.id.capturarPuntoGPSbutton);

    }

    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCamposVivienda() {

        int posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tipoLevantamientoSpinner.getAdapter(), String.valueOf(vivienda.getIdtipolevantamiento()));
        tipoLevantamientoSpinner.setSelection(posicion);

        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) areaSpinner.getAdapter(), String.valueOf(vivienda.getIdarea()));
        areaSpinner.setSelection(posicion);

        ArrayList<Localizacion> localizaciones = LocalizacionDao.getLocalizaciones(contentResolver, Localizacion.whereByViviendaId, new String[]{String.valueOf(vivienda.getId())}, null);
        if(localizaciones.size() > 0)
        {
            Double latitud = localizaciones.get(0).getLatitud();
            Double longitud = localizaciones.get(0).getLongitud();
            latitudTextView.setText(String.valueOf(latitud));
            longitudTextView.setText(String.valueOf(longitud));
        }

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
        if (!vivienda.getEdificio().equals(Global.CADENAS_VACIAS)) {
            edificioEditText.setText(vivienda.getEdificio());
        } else {
            edificioEditText.setText("");
        }
        if (!vivienda.getVivienda().equals(Global.CADENAS_VACIAS)) {
            viviendaEditText.setText(vivienda.getVivienda());
        } else {
            viviendaEditText.setText("");
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) hogarInicialSpinner.getAdapter(), String.valueOf(vivienda.getHogar1()));
        hogarInicialSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) hogarFinalSpinner.getAdapter(), String.valueOf(vivienda.getHogart()));
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


    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {

        tipoLevantamientoSpinner.setAdapter(ViviendaPreguntas.getTipoLevantamientoAdapter(getActivity()));
        areaSpinner.setAdapter(ViviendaPreguntas.getAreaAdapter(getActivity()));
        ArrayList<Values> hogarInicial = new ArrayList<>();

        hogarInicial.add(new Values(String.valueOf(Global.VALOR_SELECCIONE),
                getString(R.string.seleccionRespuesta)));

        for (int i = 1; i <= Global.MAXIMO_VALOR_HOGAR_FINAL; i++) {

            String value = String.valueOf(i);
            hogarInicial.add(new Values(value, value));

        }
        ArrayAdapter<Values> adapterHogarInicial = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item,
                hogarInicial);
        adapterHogarInicial
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hogarInicialSpinner.setAdapter(adapterHogarInicial);

        ArrayList<Values> hogarFinal = new ArrayList<>();

        hogarFinal.add(new Values(String.valueOf(Global.VALOR_SELECCIONE),
                getString(R.string.seleccionRespuesta)));

        for (int i = 1; i <= Global.MAXIMO_VALOR_HOGAR_FINAL; i++) {

            String value = String.valueOf(i);
            hogarFinal.add(new Values(value, value));

        }
        ArrayAdapter<Values> adapterHogarFinal = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item,
                hogarFinal);
        adapterHogarFinal
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hogarFinalSpinner.setAdapter(adapterHogarFinal);

        condicionOcupacionSpinner.setAdapter(ViviendaPreguntas.getCondicionOcupacionAdapter(getActivity()));

    }

    /**
     * Método para habilitar o desabilitar los controles de la vista
     */
    public void habilitarDeshabilitar() {

        hogarInicialSpinner.setEnabled(true);
        hogarInicialSpinner.setSelection(1);
        parroquiaUrbanoSpinner.setEnabled(false);


    }

    /**
     * Método que guarda la vivienda en la base de datos
     */
    private void guardar() {

        vivienda.setIdusuario(Integer.parseInt(usuario.getIdusuario()));
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

        vivienda.setHogar1(Integer.parseInt(((Values) hogarInicialSpinner
                .getSelectedItem()).getKey()));

        vivienda.setHogart(Integer.parseInt(((Values) hogarFinalSpinner
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
        vivienda.setIddpa(((Values) parroquiaSpinner.getSelectedItem()).getKey() +
                ((Values) zonaSpinner.getSelectedItem()).getKey()
                + ((Values) sectorSpinner.getSelectedItem()).getKey()
                + ((Values) manzanaSpinner.getSelectedItem()).getKey());
        //guarda el id de la dpa
        String idProvincia = vivienda.getIdparroquia().substring(0, 2);
        String idCanton = vivienda.getIdparroquia().substring(2, 4);
        String idParroquia = vivienda.getIdparroquia().substring(4, 6);
        String where;
        String parametros[];
        where = DpaManzana.whereByPCPZSM;
        parametros = new String[] {idProvincia, idCanton, idParroquia, vivienda.getZona(),vivienda.getSector(), vivienda.getManzana()};
        DpaManzana dpaManzana = DpaManzanaDao.getDpaManzana(contentResolver, where, parametros);
        Utilitarios.logError("id de dapa", "*"+dpaManzana.getId()+"*");
        if (dpaManzana!= null){
            vivienda.setIddpa(String.valueOf(dpaManzana.getId()));
        }
//        else{
//            vivienda.setIddpa("");
//        }

        if (Integer
                .valueOf(((Values) condicionOcupacionSpinner
                        .getSelectedItem()).getKey()) == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                .getValor() && ((Values) condicionOcupacionSpinner
                .getSelectedItem()).getKey().equals("1")) {

            vivienda.setIdcontrolentrevista(ControlPreguntas.ControlEntrevista.INCOMPLETA
                    .getValor());
        }
        vivienda.setIdfase(fase.getId());
        //vivienda.setIdfase(1);
        vivienda.setIdentificadorequipo(Utilitarios.getImeiDispositivo(getActivity()));
        vivienda.setFechaencuesta(Utilitarios.getCurrentDate());
        vivienda.setFechainicio(Utilitarios.getCurrentDateAndHour());
        vivienda.setFechafin(Utilitarios.getCurrentDateAndHour());
        vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
        vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());

        if (vivienda.getId() == 0) {
            if (vivienda.getIdocupada() != ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()) {
                vivienda.setNumerovisitas(1);
            } else {
                vivienda.setNumerovisitas(0);
            }

            vivienda.setId(ViviendaDao.getUltimoRegistro(contentResolver, usuario));
            vivienda.setVivcodigo(usuario.getIddispositivo()+ "-" + ViviendaDao.getUltimoRegistro(contentResolver, usuario));
            ViviendaDao.save(contentResolver, vivienda);
            hogarInicialSpinner.setEnabled(false);

            ArrayList<Localizacion> localizacionesTemp = Utilitarios.getLocalizacionesPorPresicion(localizaciones, 5);
            for (Localizacion localizacion : localizacionesTemp) {
                localizacion.setViviendaId(vivienda.getId());
                LocalizacionDao.save(contentResolver, localizacion);
            }

        } else {
            if (vivienda.getIdocupada() != ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()) {
                vivienda.setNumerovisitas(vivienda.getNumerovisitas() + 1);
            }
            ViviendaDao.update(contentResolver, vivienda);
        }
    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     */
    protected boolean validarCampos() {

        boolean cancel = false;
        View focusView = null;

        if (((Values) tipoLevantamientoSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.tipoLevantamiento));
            return true;
        }

        if (((Values) areaSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.area));
            return true;
        }

        if (((Values) provinciaSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.provincia));
            return true;
        }
        if (((Values) cantonSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.canton));
            return  true;
        }
        if (((Values) parroquiaSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.parroquia));
            return true;
        }

        localidadEditText.setError(null);
        localidadEditText.clearFocus();
        if (localidadEditText.getText().toString().equals("")) {
            localidadEditText
                    .setError(getString(R.string.errorCampoRequerido));
            localidadEditText.requestFocus();

            cancel = true;
            //return cancel;
        }
        if (((Values) zonaSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.zona));
            return true;
        }

        if (((Values) sectorSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.sector));
            return true;
        }

        if (((Values) manzanaSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.manzana));
            return true;
        }

        viviendaEditText.setError(null);
        viviendaEditText.clearFocus();
        if (viviendaEditText.getText().length() == 0
                && Integer
                .valueOf(((Values) condicionOcupacionSpinner
                        .getSelectedItem()).getKey()) == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                .getValor()) {
            viviendaEditText
                    .setError(getString(R.string.mv_vivienda_ocupada));
            viviendaEditText.requestFocus();
            cancel = true;
            //return cancel;
        }

        //estas validaciones se hacia dependiendo la condicion de ocupacion pero como ahora es slo ocupada entonces no se
        //hace esa validacion x ejm destruida, tempòral etc
        if (((Values) hogarFinalSpinner.getSelectedItem())
                .getKey().equals(String.valueOf(Global.VALOR_SELECCIONE)) && Integer
                .valueOf(((Values) condicionOcupacionSpinner
                        .getSelectedItem()).getKey()) == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                .getValor()) {
            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seleccione_pregunta)
                            + getString(R.string.hogar));
            return true;
        }


        if (((Values) tipoLevantamientoSpinner
                .getSelectedItem()).getKey().equals("1")) {

            if (edificioEditText.getText().length() == 0
                    && (Integer
                    .valueOf(((Values) condicionOcupacionSpinner
                            .getSelectedItem()).getKey()) == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                    .getValor())) {
                edificioEditText
                        .setError(getString(R.string.mv_edificio_requerido));
                edificioEditText.requestFocus();
                cancel = true;
                //return cancel;
            }

            if ((edificioEditText.getText().length() > 0
                    && Integer.valueOf(edificioEditText.getText()
                    .toString()) <= 0 && (Integer
                    .valueOf(((Values) condicionOcupacionSpinner
                            .getSelectedItem()).getKey()) == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                    .getValor()))) {
                edificioEditText
                        .setError(getString(R.string.mv_campo_edificio_mayor_cero));
                edificioEditText.requestFocus();
                cancel = true;
                //return cancel;
            }
        }


        calle1EditText.setError(null);
        calle1EditText.clearFocus();
        if (TextUtils.isEmpty(calle1EditText.getText().toString().trim())) {
            calle1EditText
                    .setError(getString(R.string.errorCampoRequerido));
//            focusView = calle1EditText;
            cancel = true;
        }

        calle2EditText.setError(null);
        calle2EditText.clearFocus();
        if (TextUtils.isEmpty(calle2EditText.getText().toString().trim())) {
            calle2EditText
                    .setError(getString(R.string.errorCampoRequerido));
//            focusView = calle2EditText;
            cancel = true;
        }

        pisoEditText.setError(null);
        pisoEditText.clearFocus();
        if (TextUtils.isEmpty(pisoEditText.getText().toString().trim())) {
            pisoEditText
                    .setError(getString(R.string.errorCampoRequerido));
//            focusView = pisoEditText;
            cancel = true;
        } else {
            if (Integer.valueOf(pisoEditText.getText().toString()) <= 0) {
                pisoEditText
                        .setError("El numero de piso debe ser un numero entero mayor que cero");
//                focusView = pisoEditText;
                cancel = true;
            }
        }

        telefonoCelularEditText.setError(null);
        telefonoCelularEditText.clearFocus();
        if (TextUtils.isEmpty(telefonoCelularEditText.getText().toString().trim())) {
            telefonoCelularEditText
                    .setError(getString(R.string.errorCampoRequerido));
//            focusView = telefonoCelularEditText;
            cancel = true;
        }

        referenciaUbicacionEditText.setError(null);
        referenciaUbicacionEditText.clearFocus();
        if (TextUtils.isEmpty(referenciaUbicacionEditText.getText().toString().trim())) {
            referenciaUbicacionEditText
                    .setError(getString(R.string.errorCampoRequerido));
//            focusView = referenciaUbicacionEditText;
            cancel = true;
        }

        telefonoConvencionalEditText.setError(null);
        telefonoConvencionalEditText.clearFocus();
        if ((telefonoConvencionalEditText.getText().toString().length() > 0)
                && telefonoConvencionalEditText.getText().toString().length() < 9) {
            telefonoConvencionalEditText.setError(getString(R.string.error_numero_fijo));
            telefonoConvencionalEditText.requestFocus();
            return true;
        }

        telefonoConvencionalEditText.setError(null);
        telefonoConvencionalEditText.clearFocus();
        if (telefonoConvencionalEditText.getText().toString().length() > 2 &&
                !Utilitarios.validarCodigoRegion(telefonoConvencionalEditText.getText().toString()) &&
                telefonoConvencionalEditText.getText().toString().length() == 9) {
            telefonoConvencionalEditText.setError(getString(R.string.errorCodigoRegionFijo));
            telefonoConvencionalEditText.requestFocus();
            return true;

        }
        telefonoConvencionalEditText.clearFocus();
        if (telefonoConvencionalEditText.getText().toString().equals("000000000")) {

            telefonoConvencionalEditText
                    .setError(getString(R.string.error_numero_celularCeros));
            telefonoConvencionalEditText.requestFocus();
            return true;
        }


        telefonoCelularEditText.clearFocus();
        if ((telefonoCelularEditText.getText().toString().length() > 0)
                && (telefonoCelularEditText.getText().toString().length() < 10)
                ) {

            telefonoCelularEditText.setError(getString(R.string.error_numero_celular));
            telefonoCelularEditText.requestFocus();
            return true;
        }
        telefonoCelularEditText.clearFocus();
        if (telefonoCelularEditText.getText().toString().length() > 2 &&
                !telefonoCelularEditText.getText().toString().substring(0, 2).equals("09") &&
                telefonoCelularEditText.getText().toString().length() == 10
                ) {
            telefonoCelularEditText.setError(getString(R.string.errorDigitoNumCelular));
            telefonoCelularEditText.requestFocus();
            return true;
        }

        telefonoCelularEditText.clearFocus();
        if (telefonoCelularEditText.getText().toString().equals("0000000000")) {
            telefonoCelularEditText
                    .setError(getString(R.string.error_numero_celularCeros));
            telefonoCelularEditText.requestFocus();
            return true;
        }



        if(TextUtils.isEmpty(latitudTextView.getText().toString())
                && TextUtils.isEmpty(longitudTextView.getText().toString()))
        {
            getAlert(getString(R.string.validacion_aviso),
                    getString(R.string.mv_no_punto_gps));
            return  true;
        }



        return cancel;
    }

    /**
     * Muestra las alertas
     *
     * @param title titulo del mensaje de alerta
     * @param message contenido del mensaje de alerta
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

        capturarPuntoGPSbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    if (location != null) {
                        if (TextUtils.isEmpty(latitudTextView.getText()) && TextUtils.isEmpty(latitudTextView.getText())) {
                            latitudTextView.setText(String.valueOf(location.getLatitude()));
                            longitudTextView.setText(String.valueOf(location.getLongitude()));
                            localizaciones.add(new Localizacion(0, 0, location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy(), location.getProvider()));
                        }
                    } else {
                        if (locationManager != null) {
                            Location _locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (_locationGPS != null) {
                                latitudTextView.setText(String.valueOf(_locationGPS.getLatitude()));
                                longitudTextView.setText(String.valueOf(_locationGPS.getLongitude()));
                                localizaciones.add(new Localizacion(0, 0, _locationGPS.getLatitude(), _locationGPS.getLongitude(), _locationGPS.getAltitude(), _locationGPS.getAccuracy(), _locationGPS.getProvider()));
                            }
                            else
                            {
                                Location _locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                if (_locationNet != null) {
                                    latitudTextView.setText(String.valueOf(_locationNet.getLatitude()));
                                    longitudTextView.setText(String.valueOf(_locationNet.getLongitude()));
                                    localizaciones.add(new Localizacion(0, 0, _locationNet.getLatitude(), _locationNet.getLongitude(), _locationNet.getAltitude(), _locationNet.getAccuracy(), _locationNet.getProvider()));
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Buscando punto GPS", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Buscando punto GPS",	Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });



        calle1EditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(calle1EditText));

        calle2EditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(calle2EditText));

        referenciaUbicacionEditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(referenciaUbicacionEditText));

        pisoEditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(pisoEditText));

        localidadEditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(localidadEditText));

        telefonoConvencionalEditText.addTextChangedListener
                (Utilitarios.numeroCeroEditText(telefonoConvencionalEditText));

        telefonoCelularEditText.addTextChangedListener
                (Utilitarios.numeroCeroEditText(telefonoCelularEditText));



    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {

        tipoLevantamientoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) tipoLevantamientoSpinner.getSelectedItem()).getKey().equals("1")) {
                    edificioEditText.setText("1");
                    edificioEditText.setEnabled(false);
                } else {
                    edificioEditText.setText("");
                    edificioEditText.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

//        edificioEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(!edificioEditText.getText().toString().equals(""))
//                {
//                    if(Integer.parseInt(edificioEditText.getText().toString())>1 &&
//                            ((Values)tipoLevantamientoSpinner.getSelectedItem()).getKey().equals("1")){
//                        edificioEditText.setText("1");
//                    }
//                }
//
//            }
//        });

        hogarInicialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(((Values) hogarInicialSpinner.getSelectedItem()).getKey()) > Integer.parseInt(((Values) hogarFinalSpinner.getSelectedItem()).getKey())) {
                    hogarInicialSpinner.setSelection(1);
                    hogarFinalSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.errorInconsistenciaHogar));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hogarFinalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(((Values) hogarInicialSpinner.getSelectedItem()).getKey()) > Integer.parseInt(((Values) hogarFinalSpinner.getSelectedItem()).getKey())) {
                    hogarInicialSpinner.setSelection(1);
                    hogarFinalSpinner.setSelection(0);
                    getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.errorInconsistenciaHogar));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

        ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
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

                        if (!provincia.getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {

                            ArrayList<Values> localidades;
                            Cursor cursor = contentResolver.query(
                                    FormularioManuelasProvider.CONTENT_URI_LOCALIDAD, new String[]{
                                            Localidad.COLUMNA_LOC_CODIGO,
                                            Localidad.COLUMNA_LOC_DESCRIPCION},
                                    Localidad.whereByCodigoPadre, new String[]{provincia.getKey()},
                                    Localidad.COLUMNA_LOC_DESCRIPCION);

                            localidades = LocalidadDao.getLocalidades(cursor);
                            ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
                                    getActivity(), android.R.layout.simple_spinner_item,
                                    localidades);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cantonSpinner.setAdapter(adapter1);

                            if (vivienda.getId() != 0 && aplicaMalla) {
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
                if (!canton.getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {
                    //new GetParroquias().execute(canton.getKey());

                    ArrayList<Values> localidades;
                    Cursor cursor = contentResolver.query(FormularioManuelasProvider.CONTENT_URI_LOCALIDAD,
                            new String[]{Localidad.COLUMNA_LOC_CODIGO,
                                    Localidad.COLUMNA_LOC_DESCRIPCION},
                            Localidad.whereByCodigoPadre, new String[]{canton.getKey()},
                            Localidad.COLUMNA_LOC_DESCRIPCION);

                    localidades = LocalidadDao.getLocalidades(cursor);

                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    parroquiaSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla) {
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
                        if (!_idParroquia.equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {

                            String idProvincia = _idParroquia.substring(0, 2);
                            String idCanton = _idParroquia.substring(2, 4);
                            String idParroquia = _idParroquia.substring(4, 6);

                            String[] args = {idProvincia, idCanton,
                                    idParroquia};

                            ArrayList<Values> localidades;
                            Cursor cursor = contentResolver.query(
                                    FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                                    new String[]{"DISTINCT " + DpaManzana.COLUMNA_ZONA},
                                    DpaManzana.whereByProvinciaCantonParroquia, args,
                                    DpaManzana.COLUMNA_ZONA);

                            localidades = DpaManzanaDao.getZonaSectorManzana(cursor);

                            ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
                                    getActivity(), android.R.layout.simple_spinner_item,
                                    localidades);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            zonaSpinner.setAdapter(adapter1);

                            if (vivienda.getId() != 0 && aplicaMalla) {
                                int posicion = Utilitarios.getPosicionByKey(
                                        (ArrayAdapter<Values>) zonaSpinner.getAdapter(),
                                        vivienda.getZona());
                                zonaSpinner.setSelection(posicion);
                            }


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
                if (!zona.getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {

                    String idProvincia = _idParroquia.substring(0, 2);
                    String idCanton = _idParroquia.substring(2, 4);
                    String idParroquia = _idParroquia.substring(4, 6);

                    String[] args = {idProvincia, idCanton, idParroquia,
                            zona.getKey()};

//                    if (zona.getKey().equals("999")) {
//                        manzanaSpinner.setEnabled(false);
//                    } else {
//                        manzanaSpinner.setEnabled(true);
//                    }

                    ArrayList<Values> localidades;

                    Cursor cursor = contentResolver.query(
                            FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                            new String[]{"DISTINCT " + DpaManzana.COLUMNA_SECTOR},
                            DpaManzana.whereByProvinciaCantonParroquiaZona,
                            args, DpaManzana.COLUMNA_SECTOR);

                    localidades = DpaManzanaDao.getZonaSectorManzana(cursor);


                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectorSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla) {
                        int posicion = Utilitarios.getPosicionByKey(
                                (ArrayAdapter<Values>) sectorSpinner.getAdapter(),
                                vivienda.getSector());
                        sectorSpinner.setSelection(posicion);
                    }



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
                if (!sector.getKey().equals(String.valueOf(Global.VALOR_SELECCIONE_DPA))) {

                    String idProvincia = _idParroquia.substring(0, 2);
                    String idCanton = _idParroquia.substring(2, 4);
                    String idParroquia = _idParroquia.substring(4, 6);

                    String[] args = {idProvincia, idCanton, idParroquia,
                            zona.getKey(), sector.getKey()};

                    ArrayList<Values> localidades;

                    Cursor cursor = contentResolver.query(
                            FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                            new String[]{DpaManzana.COLUMNA_MANZANA},
                            DpaManzana.whereByProvinciaCantonParroquiaZonaSector,
                            args, "CAST(" + DpaManzana.COLUMNA_MANZANA + " AS INTEGER)");

                    localidades = DpaManzanaDao.getZonaSectorManzana(cursor);


                    ArrayAdapter<Values> adapter1 = new ArrayAdapter<>(
                            getActivity(), android.R.layout.simple_spinner_item,
                            localidades);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    manzanaSpinner.setAdapter(adapter1);

                    if (vivienda.getId() != 0 && aplicaMalla) {
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
     *
     * @return el objeto Vivienda
     */
    public static Vivienda getVivienda() {
        return vivienda;
    }

    /**
     * Habilita y deshabilita el dialogo de observaciones
     * @return
     */
    public static boolean isEnabledObervaciones() {
        return isEnabledObervacion ;
    }

    /**
     *está destinado a recibir y responder ante eventos globales generados por el sistema,
     */
    /*public class ProgressReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Utilitarios.logError("onReceive", "onReceive " + location);
            if (intent.getAction().equals(Global.BROADCAST_ACTION_PROGRESS)) {
                location = intent.getParcelableExtra("location");
                if (vivienda.getId() == 0 && localizaciones.size() <= maxNumeroLocalizaciones) {
                    localizaciones.add(new Localizacion(0, 0, location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy(), location.getProvider()));
                }
                if ((TextUtils.isEmpty(latitudTextView.getText()) && TextUtils.isEmpty(latitudTextView.getText())) ) {
                    latitudTextView.setText(String.valueOf(location.getLatitude()));
                    longitudTextView.setText(String.valueOf(location.getLongitude()));
                } else {
                    if (location.getProvider().equals(LocationManager.GPS_PROVIDER)	&& vivienda.getId() == 0) {
                        Utilitarios.logInfo(ViviendaFragment.class.getName(), "Longitud y Latitud cambiadas por el proveedor gps");
                        latitudTextView.setText(String.valueOf(location.getLatitude()));
                        longitudTextView.setText(String.valueOf(location.getLongitude()));
                    }
                }
            }

        }
    }*/


    /**
     * Captura puntos cuando es una vivienda nueva al crear al actividad(carga de la seccion vivienda)
     * sino encuentra los puntos dar clic en el boton capturar.
     */
    private void getLastLocation() {
        Utilitarios.logError("Metodo getLastLocation","getLastLocation");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            if (location != null) {
                if (vivienda.getId() == 0 && localizaciones.size() <= maxNumeroLocalizaciones) {
                    localizaciones.add(new Localizacion(0, 0, location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy(), location.getProvider()));
                    latitudTextView.setText(String.valueOf(location.getLatitude()));
                    longitudTextView.setText(String.valueOf(location.getLongitude()));
                }
            } else {
                if (locationManager != null) {

                    Location _locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (_locationGPS != null) {
                        if (vivienda.getId() == 0 && localizaciones.size() <= maxNumeroLocalizaciones) {
                            localizaciones.add(new Localizacion(0, 0, _locationGPS.getLatitude(), _locationGPS.getLongitude(), _locationGPS.getAltitude(), _locationGPS.getAccuracy(), _locationGPS.getProvider()));
                            latitudTextView.setText(String.valueOf(_locationGPS.getLatitude()));
                            longitudTextView.setText(String.valueOf(_locationGPS.getLongitude()));
                        }
                    }
                    else
                    {
                        Location _locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (_locationNet != null) {
                            if (vivienda.getId() == 0 && localizaciones.size() <= maxNumeroLocalizaciones) {
                                localizaciones.add(new Localizacion(0, 0, _locationNet.getLatitude(), _locationNet.getLongitude(), _locationNet.getAltitude(), _locationNet.getAccuracy(), _locationNet.getProvider()));
                                latitudTextView.setText(String.valueOf(_locationNet.getLatitude()));
                                longitudTextView.setText(String.valueOf(_locationNet.getLongitude()));
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Buscando punto GPS", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Buscando punto GPS", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
