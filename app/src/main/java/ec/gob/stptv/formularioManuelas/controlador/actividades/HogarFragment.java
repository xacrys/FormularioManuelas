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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;

import ec.gob.stptv.formularioManuelas.*;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.HogarDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

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
    private static Hogar hogar;
    private TabHost tabs;
    private Vivienda vivienda;
    private ContentResolver contentResolver;


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

        Log.e("prueba",""+vivienda.getId());

        parametros = new String[] { String.valueOf(vivienda.getId()) };
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
        guardarPersonaButton = item.findViewById(R.id.guardarPersonaButton);
        gasParaCalefonOpcion = item.findViewById(R.id.gasParaCalefon);
        terrenoAgropecuario = item.findViewById(R.id.terrenoAgropecuario);
        terrenoAgropecuarioSi = item.findViewById(R.id.terrenoAgropecuarioSi);
        atrasButton = item.findViewById(R.id.atrasButton);


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
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) fuenteAguaSpinner.getAdapter(), String.valueOf(hogar.getIdagua()));
        fuenteAguaSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) ubicacionAguaSpinner.getAdapter(), String.valueOf(hogar.getIdredagua()));
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

        hogar.setIdpropiedadvivienda(Integer.parseInt(((Values) tipoHogarSpinner.getSelectedItem()).getKey()));
        hogar.setIddocumentovivienda(Integer.parseInt(((Values) documentoHogarSpinner.getSelectedItem()).getKey()));

        hogar.setCuartos(Integer.parseInt(((Values) numCuartosSpinner.getSelectedItem()).getKey()));
        hogar.setDormitorio(Integer.parseInt(((Values) numDormitoriosSpinner.getSelectedItem()).getKey()));
        hogar.setIdagua(Integer.parseInt(((Values) fuenteAguaSpinner.getSelectedItem()).getKey()));
        hogar.setIdredagua(Integer.parseInt(((Values) ubicacionAguaSpinner.getSelectedItem()).getKey()));
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
                hogar.setGascalefon(Global.ENTEROS_VACIOS);
            }
        }

        if (terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion1rb) {
            hogar.setTerreno(Global.SI);
        } else {
            if (terrenoAgropecuario.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioOpcion2rb) {
                hogar.setTerreno(Global.NO);
            } else {
                hogar.setTerreno(Global.ENTEROS_VACIOS);
            }
        }

        if (terrenoAgropecuarioSi.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioSiOpcion1rb) {
            hogar.setTerrenoservicios(Global.SI);
        } else {
            if (terrenoAgropecuarioSi.getCheckedRadioButtonId() == R.id.terrenoAgropecuarioSiOpcion2rb) {
                hogar.setTerrenoservicios(Global.NO);
            } else {
                hogar.setTerrenoservicios(Global.ENTEROS_VACIOS);
            }
        }
        hogar.setFechainicio(Utilitarios.getCurrentDate());
        hogar.setFechafin(Utilitarios.getCurrentDate());

        if (hogar.getId() == 0) {
            Utilitarios.logInfo(HogarFragment.class.getName(), "Guardar hogar");
            hogar.setIdvivienda(vivienda.getId());
            Uri uri = HogarDao.save(contentResolver, hogar);
            String id = uri.getPathSegments().get(1);
            hogar.setId(Integer.parseInt(id));

        } else {
            Utilitarios.logInfo(HogarFragment.class.getName(),"Actualiza hogar");
            HogarDao.update(contentResolver, hogar);
        }

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

        documentoHogarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(vivienda.getIdtipovivienda()>2 && Integer.parseInt(((Values)documentoHogarSpinner.getSelectedItem()).getKey())>0
                        && !((Values)documentoHogarSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))){
                    getAlert(getString(R.string.errorTipoUno), getString(R.string.seleccione_pregunta) + getString(R.string.errorDocumentoPorTipoVivienda));
                    documentoHogarSpinner.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

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

    /**
     * regresa la vivienda
     * @return Objeto Hogar
     */
    public static Hogar getHogar() {
        return hogar;
    }
}
