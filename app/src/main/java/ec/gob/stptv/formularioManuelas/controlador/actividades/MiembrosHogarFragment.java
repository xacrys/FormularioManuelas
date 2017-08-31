package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.DatePickerFragment;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.PersonaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/***
 *  Autor: Christian Tintin
 */

public class MiembrosHogarFragment extends Fragment {

    private Button nuevoButton;
    private Button atrasButton;
    private Button aniadirButton;
    private Button fechaNacimientoButton;
    private Spinner tipoResidenteSpinner;
    private Spinner documentoSpinner;
    private Spinner sexoSpinner;
    private EditText cedulaEditText;
    private EditText apellidosEditText;
    private EditText nombresEditText;
    private EditText correoEditText;
    private EditText aniosEditText;
    private EditText mesesEditText;
    private RadioGroup edadRadioGroup;
    private Spinner parentescoSpinner;
    private ContentResolver contentResolver;
    private Hogar hogar;
    private Vivienda vivienda;
    private static TableLayout personasTableLayout;
    private int ordenPersona = 0;
    private Persona persona;
    private int tipoGestion = 1;
    private int cantidaPersonas = 0;
    private LinearLayout pantallaMiembrosHogarLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar,
                container, false);

        contentResolver = getActivity().getContentResolver();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.habilitarDeshabilitar();
        this.realizarAcciones();
        this.mallasValidacion();
        return item;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hogar = HogarFragment.getHogar();
        vivienda = ViviendaFragment.getVivienda();
        if (vivienda.getId()!= 0){
            if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA
                    .getValor() && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA.getValor()) {
                Utilitarios.disableEnableViews(getActivity(), false, pantallaMiembrosHogarLinearLayout);
            }
        }
        aniosEditText.setEnabled(true);
        if (hogar.getId() != 0) {
            getPersonas();
        }

    }

    /**
     * Método para habilitar o desabilitar los controles de la vista
     */
    public void habilitarDeshabilitar() {
        sexoSpinner.setEnabled(true);
        aniosEditText.setEnabled(true);
        fechaNacimientoButton.setVisibility(View.INVISIBLE);
        aniosEditText.setVisibility(View.INVISIBLE);

    }



    /**
     * Metòdo que obtiene todas las personas
     */
    public void getPersonas() {

        personasTableLayout.removeAllViews();

        ArrayList<Persona> personas = PersonaDao.getPersonas(contentResolver,
                Persona.whereByIdHogar,
                new String[] { String.valueOf(hogar.getId()) },
                Persona.COLUMNA_ORDEN);
        ordenPersona = personas.size();

        Utilitarios.logInfo(MiembrosHogarFragment.class.getName(),"cantidad fde personas"+personas.size() + "");

        for (Persona _persona : personas) {

            addFila(_persona);
        }
        if (ordenPersona == 0){
            parentescoSpinner.setSelection(1);
            parentescoSpinner.setEnabled(false);
        }
    }

    /**
     * Método que permite agregando personas en la tabla
     * @param _persona
     */
    private void addFila(Persona _persona) {

        TableRow row = new TableRow(getActivity());

        row.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (_persona.getInformacioncompleta() == Global.INFORMACION_COMPLETA) {

            row.setBackgroundResource(R.drawable.table_row_selector_validation);

        } else {

            row.setBackgroundResource(R.drawable.table_row_selector);
        }

        row.setClickable(true);


        row.setTag(_persona);

        TextView columnaNombres = new TextView(getActivity());
        columnaNombres.setLayoutParams(new TableRow.LayoutParams(200,
                LayoutParams.WRAP_CONTENT));
        columnaNombres.setPadding(10, 10, 10, 10);
        columnaNombres.setTextColor(getResources().getColor(R.color.negro));
        //columnaNombres.setText(_persona.getCodigoPersona() +"---"+ _persona.getNombresCompletos());
        columnaNombres.setText(_persona.getNombresCompletos());

        TextView columnaGenero = new TextView(getActivity());
        columnaGenero.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
        columnaGenero.setPadding(10, 10, 10, 10);
        columnaGenero.setTextColor(getResources().getColor(R.color.negro));
        columnaGenero.setText(_persona.getGeneroCompleto());

        TextView columnaEdad = new TextView(getActivity());
        columnaEdad.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
        columnaEdad.setPadding(10, 10, 10, 10);
        columnaEdad.setTextColor(getResources().getColor(R.color.negro));
        columnaEdad.setText(_persona.getEdadCompleto());

        TextView columnaParentesco = new TextView(getActivity());
        columnaParentesco.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
        columnaParentesco.setPadding(10, 10, 10, 10);
        columnaParentesco.setTextColor(getResources().getColor(R.color.negro));
        columnaParentesco.setText(_persona.getParentescoCompleto(getActivity()));


        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                persona = (Persona) v.getTag();
                tipoGestion = 1;
                limpiarCampos();
                getAlertActions(persona.getNombresCompletos(), persona);
            }
        });

        row.addView(columnaNombres);
        row.addView(columnaGenero);
        row.addView(columnaEdad);
        row.addView(columnaParentesco);


        personasTableLayout.addView(row);

        cantidaPersonas = _persona.getOrden();

        // listItems.add(persona);
        // adapter.notifyDataSetChanged();
    }


    /**
     * Metdo que realiza las acciones en las persona por ejemplo completar la informacion, eliminar actualizar
     * @param title
     * @param ppersona
     */
    private void getAlertActions(String title, final Persona ppersona) {

        final String[] acciones = {
                getString(R.string.completarInformacion),
                getString(R.string.editar),
                getString(R.string.eliminarRegistro) };


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(getResources().getDrawable(
                android.R.drawable.ic_dialog_info));
        builder.setTitle(title);
        builder.setSingleChoiceItems(acciones, -1,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                FragmentManager fragMgr = getFragmentManager();
                                Fragment currentFragment = fragMgr.findFragmentById(R.id.miembros_hogar_fragment);
                                Fragment newFragment = new MiembrosHogarTodasPersonasFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("persona", ppersona);
                                newFragment.setArguments(args);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.add(R.id.fragmentContainer, newFragment);
                                transaction.remove(currentFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 1:
                                tipoGestion = 2;
                                llenarCampos(ppersona);
                                if (ppersona.getIdparentesco() == PersonaPreguntas.ControlTrabajoParentesco.JEFE.getValor()){
                                    getAlert(
                                            getString(R.string.validacion_aviso),
                                            getString(R.string.mvNoEditar));

                                    sexoSpinner.setEnabled(false);
                                    aniosEditText.setEnabled(false);
                                    parentescoSpinner.setEnabled(false);
                                }
                                else{
                                    sexoSpinner.setEnabled(true);
                                    aniosEditText.setEnabled(true);
                                    parentescoSpinner.setEnabled(true);
                                }
                                break;
                            case 2:

                                if ((cantidaPersonas > 1) && (ppersona.getOrden() == 1)) {
                                    getAlert(
                                            getString(R.string.validacion_aviso),
                                            getString(R.string.seccion5MensajeEliminarJefeDeHogar));
                                    break;

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage(
                                            getString(R.string.mensajeEliminarMiembroHogar))
                                            .setTitle("Eliminar");
                                    builder.setIcon(getResources().getDrawable(
                                            android.R.drawable.ic_dialog_alert));
                                    builder.setPositiveButton(
                                            getString(R.string.opcion_si),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {
                                                    if ((ppersona
                                                            .getEdadanio() >= Global.EDAD_12ANIOS) && (ppersona.getSexo().equals(Global.GENERO_FEMENINO))) {
                                                        validarMadreEHijos(ppersona);
                                                    }
                                                    PersonaDao.delete(contentResolver, ppersona);
                                                    getPersonas();
                                                }
                                            });

                                    builder.setNegativeButton(
                                            getString(R.string.opcion_no),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog dialogo = builder.create();
                                    dialogo.show();
                                    break;
                                }
                        }
                        dialog.dismiss();
                    }
                });

        builder.setCancelable(true);
        builder.setPositiveButton("Cerrar",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Recorrer tabla para ver si la madre que ingresa como aprametro tiene hijos y si tiene elimina
     * la pregunta 11 y pone en informacion incompleta
     *
     */
    protected void validarMadreEHijos(Persona madre) {
        for (int i = 0; i < personasTableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) personasTableLayout.getChildAt(i);
            Persona _persona = (Persona) row.getTag();
            if ((_persona.getMadrevive() == Global.SI) && (madre.getId().equals( _persona.getOrdenMadre()))) {
                _persona.setMadrevive(Global.ENTEROS_VACIOS);
                _persona.setOrdenMadre(Global.ENTEROS_VACIOS);
                _persona.setInformacioncompleta(Global.INFORMACION_INCOMPLETA);
                PersonaDao.update(contentResolver,_persona);
            }
        }
    }
    /**
     * Método que llena los controles con datos de la base
     * @param ppersona
     */
    @SuppressWarnings("unchecked")
    protected void llenarCampos(Persona ppersona) {
        int posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) tipoResidenteSpinner.getAdapter(), String.valueOf(ppersona.getIdresidente()));
        tipoResidenteSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) sexoSpinner.getAdapter(), String.valueOf(ppersona.getSexo()));
        sexoSpinner.setSelection(posicion);
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) documentoSpinner.getAdapter(),String.valueOf(ppersona.getIddocumentacion()));
        documentoSpinner.setSelection(posicion);
        if(!ppersona.getCi().equals(Global.CADENAS_VACIAS)){
            cedulaEditText.setText(ppersona.getCi());
        }
        else{
            cedulaEditText.setText("");
        }
        apellidosEditText.setText(persona.getApellidos());
        nombresEditText.setText(persona.getNombres());
        if (persona.getTipoEdad() == Global.FECHA_NACIMIENTO) {
            edadRadioGroup
                    .check(R.id.edadFechaNacimientoOpcion1RadioButton);
            fechaNacimientoButton.setText(persona.getFechanacimiento());
            if (persona.getEdadanio() >= 5){
                correoEditText.setEnabled(true);
            }else{
                correoEditText.setEnabled(false);
            }
        } else {
            if (persona.getTipoEdad() == Global.ANIOS_CUMPLIDOS) {
                edadRadioGroup.check(R.id.edadAniosCumplidosOpcion2RadioButton);
                aniosEditText.setText(String.valueOf(persona.getEdadanio()));
                if (persona.getEdadmes().equals(Global.ENTEROS_VACIOS)) {
                    mesesEditText.setVisibility(View.INVISIBLE);
                    mesesEditText.setText("");
                } else {
                    mesesEditText.setVisibility(View.VISIBLE);
                    mesesEditText.setText(String.valueOf(persona.getEdadmes()));
                }

            }
        }
        posicion = Utilitarios.getPosicionByKey((ArrayAdapter<Values>) parentescoSpinner.getAdapter(),String.valueOf(ppersona.getIdparentesco()));
        Log.e("parentesco", posicion+"");
        parentescoSpinner.setSelection(posicion);

    }

    /**
     * Metódo que permite limpiar los datos
     */
    protected void limpiarCampos() {
        documentoSpinner.setSelection(0);
        tipoResidenteSpinner.setSelection(0);
        sexoSpinner.setSelection(0);
        sexoSpinner.setEnabled(true);
        apellidosEditText.setText("");
        nombresEditText.setText("");
        aniosEditText.setText("");
        aniosEditText.setEnabled(true);
        mesesEditText.setText("");
        cedulaEditText.setText("");
        cedulaEditText.setEnabled(false);
        correoEditText.setText("");
        edadRadioGroup.clearCheck();
        edadRadioGroup.setVisibility(View.VISIBLE);
        aniosEditText.setVisibility(View.INVISIBLE);
        fechaNacimientoButton.setText(getString(R.string.fechaSeleccione));
        fechaNacimientoButton.setVisibility(View.INVISIBLE);
        parentescoSpinner.setSelection(0);
        parentescoSpinner.setEnabled(true);

    }

    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {
        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);
        aniadirButton = item.findViewById(R.id.aniadirButton);
        fechaNacimientoButton = item.findViewById(R.id.fechaNacimientoButton);
        tipoResidenteSpinner = item.findViewById(R.id.tipoResidenteSpinner);
        documentoSpinner = item.findViewById(R.id.documentoSpinner);
        sexoSpinner = item.findViewById(R.id.sexoSpinner);
        cedulaEditText = item.findViewById(R.id.cedulaEditText);
        apellidosEditText = item.findViewById(R.id.apellidosEditText);
        nombresEditText = item.findViewById(R.id.nombresEditText);
        correoEditText = item.findViewById(R.id.correoEditText);
        edadRadioGroup = item.findViewById(R.id.edadRadioGroup);
        aniosEditText = item.findViewById(R.id.aniosEditText);
        mesesEditText = item.findViewById(R.id.mesesEditText);
        parentescoSpinner = item.findViewById(R.id.parentescoSpinner);
        personasTableLayout = item.findViewById(R.id.personasTableLayout);
        pantallaMiembrosHogarLinearLayout = item
                .findViewById(R.id.pantallaMiembrosHogarLinearLayout);

    }

    /**
     * @return estado de la validación
     */
    protected Boolean validarCampos() {
        Boolean cancel = true;
        cedulaEditText.setError(null);
        cedulaEditText.clearFocus();
        if (((Values) tipoResidenteSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoResidente));
        } else if (((Values) documentoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.etiquetaCedula));
        } else if ((((Values) documentoSpinner.getSelectedItem()).getKey().equals("1") ||
                ((Values) documentoSpinner.getSelectedItem()).getKey().equals("2")) &&
                TextUtils.isEmpty(cedulaEditText.getText().toString().trim())) {
            cedulaEditText.setError(null);
            cedulaEditText.clearFocus();
            cedulaEditText.setError(getString(R.string.errorCampoRequerido));
            cedulaEditText.requestFocus();
        } else if (TextUtils.isEmpty(apellidosEditText.getText().toString().trim())) {
            apellidosEditText.setError(null);
            apellidosEditText.clearFocus();
            apellidosEditText.setError(getString(R.string.errorCampoRequerido));
            apellidosEditText.requestFocus();
        } else if (TextUtils.isEmpty(nombresEditText.getText().toString().trim())) {
            nombresEditText.setError(null);
            nombresEditText.clearFocus();
            nombresEditText.setError(getString(R.string.errorCampoRequerido));
            apellidosEditText.requestFocus();
        } else if (((Values) sexoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.sexoTitulo));
        } else if (edadRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.edadTitulo));
        } else if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton &&
                TextUtils.isEmpty(aniosEditText.getText().toString().trim())
                ) {
            aniosEditText.setError(null);
            aniosEditText.clearFocus();
            aniosEditText.setError(getString(R.string.errorCampoRequerido));
            aniosEditText.requestFocus();
        } else
            if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton &&
                TextUtils.isEmpty(mesesEditText.getText().toString().trim()) &&  mesesEditText.getVisibility() == View.VISIBLE
                ) {
            mesesEditText.setError(null);
            mesesEditText.clearFocus();
            mesesEditText.setError(getString(R.string.errorCampoRequerido));
                mesesEditText.requestFocus();
//            focusView = mesesEditText;
        }

        else if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton &&
                fechaNacimientoButton.getText().toString().trim().equals(getString(R.string.fechaSeleccione))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + "Botón fecha de Nacimiento");
        }else if (((Values) parentescoSpinner.getSelectedItem()).getKey().equals(String.valueOf(Global.VALOR_SELECCIONE))) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.parentescoJefeHogar));
        }
        else {
            cancel = false;
        }

        if (!TextUtils.isEmpty(cedulaEditText.getText().toString())) {
            if ((cedulaEditText.getText().toString().length() != 10) || (cedulaEditText.getText().toString().equals("0000000000"))){
                getAlert(getString(R.string.validacion_aviso),
                        getString(R.string.seccion5MensajeNoCedula));
                return true;
            }
            if (!Utilitarios.validadorCedula(cedulaEditText.getText().toString())){
                getAlert(getString(R.string.validacion_aviso),
                        getString(R.string.seccion5MensajeNoCedula));
                cedulaEditText.requestFocus();
                return true;
            }

            String where;
            String parametros[];
            where = Persona.whereByIdHogarCedula;
            parametros = new String[] {String.valueOf(hogar.getId()), cedulaEditText.getText().toString()};
            Persona _persona = PersonaDao.getPersona(contentResolver, where, parametros);
            if (tipoGestion == 1){
                if (_persona != null){
                    getAlert(getString(R.string.validacion_aviso),
                            getString(R.string.seccion5MensajeCedulaExistente));
                    cedulaEditText.requestFocus();
                    return true;
                }
            }else{
                if (tipoGestion == 2){
                    if (_persona != null) {
                        if (persona != null) {
                            if (!_persona.getId().equals(persona.getId())) {
                                getAlert(getString(R.string.validacion_aviso),
                                        getString(R.string.seccion5MensajeCedulaExistente));
                                cedulaEditText.requestFocus();
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return cancel;
    }

    /**
     * Metodo para contar el numero de personas en la tabla
     */
    public static int getCountTablaPersonas() {

        return personasTableLayout.getChildCount();
    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {
        tipoResidenteSpinner.setAdapter(PersonaPreguntas.getTipoResidenteAdapter(getActivity()));
        documentoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoDocumentoAdapter(getActivity()));
        sexoSpinner.setAdapter(PersonaPreguntas.getSexoPersonaAdapter(getActivity()));
        parentescoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoParentescoAdapter(getActivity()));
        if (ordenPersona == 0){
            parentescoSpinner.setSelection(1);
            parentescoSpinner.setEnabled(false);
        }
    }

    /**
     * metodo para realizar las acciones
     */
    private void realizarAcciones() {

        nuevoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tipoGestion = 1;
                limpiarCampos();
            }
        });

        aniadirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarCampos())
                    return;

                guardar();

            }
        });

        fechaNacimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(1);
            }
        });

        // /mesesEditText.setEnabled(false);
        mesesEditText.setVisibility(View.INVISIBLE);
        mesesEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int val = Integer.parseInt(s.toString());
                    if (val > 11) {
                        s.clear();
                    }
                } catch (NumberFormatException ex) {
                    // Do something
                }

            }
        });

        aniosEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int val = Integer.parseInt(s.toString());
                    if (val >= 0 && val < 5) {
                        mesesEditText.setVisibility(View.VISIBLE);
                        correoEditText.setText("");
                        correoEditText.setEnabled(false);
                    } else {
                        if (val >= 5) {
                            mesesEditText.setVisibility(View.INVISIBLE);
                            mesesEditText.setText("");
                            mesesEditText.setError(null);
                            mesesEditText.clearFocus();
                            correoEditText.setEnabled(true);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Do something
                }

            }
        });

        apellidosEditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(apellidosEditText));
        nombresEditText.addTextChangedListener(Utilitarios
                .clearSpaceEditText(nombresEditText));


    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {
        //Listener para cuando cambia la elección Tipo de Hogar
        documentoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Values) documentoSpinner.getSelectedItem()).getKey().equals("1") ||
                        ((Values) documentoSpinner.getSelectedItem()).getKey().equals("2")) {
                    cedulaEditText.setEnabled(true);
                } else {
                    cedulaEditText.setText("");
                    cedulaEditText.setEnabled(false);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        edadRadioGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub

                        if (edadRadioGroup
                                .getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton) {

                            fechaNacimientoButton.setVisibility(View.VISIBLE);
                            fechaNacimientoButton.setEnabled(true);
                            aniosEditText.setError(null);
                            mesesEditText.setError(null);
                            aniosEditText.setText("");
                            mesesEditText.setText("");
                            aniosEditText.setVisibility(View.INVISIBLE);
                            mesesEditText.setVisibility(View.INVISIBLE);
                        }else{
                            if (edadRadioGroup
                                    .getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton) {
                                fechaNacimientoButton.setVisibility(View.INVISIBLE);
                                fechaNacimientoButton.setEnabled(false);
                                fechaNacimientoButton.setText(getString(R.string.fechaSeleccione));
                                aniosEditText.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        parentescoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int parentesco = Integer.valueOf(((Values) adapterView
                        .getAdapter().getItem(i)).getKey());

                if (getCountTablaPersonas() > 1){
                    if (parentesco == 1 && getCountTablaPersonas() >0){
                        getAlert(getString(R.string.validacion_aviso),
                                getString(R.string.mensajeJefeHogarMasUno));
                        parentescoSpinner.setSelection(0);
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    /**
     * Método que guarda la vivienda en la base de datos
     */
    private void guardar() {
        hogar = HogarFragment.getHogar();
        int edadMeses;
        int edadAnios;

        if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton) {
            // fecha de nacimiento
            Date dateComponente = null;
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            try {
                dateComponente = formatoFecha.parse(fechaNacimientoButton.getText().toString());

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String edad = Utilitarios.calcularEdad(dateComponente);
            String[] edadArray = edad.split("-");
            edadAnios = Integer.parseInt(edadArray[0]);
            edadMeses = Integer.parseInt(edadArray[1]);
            // int edadDias = Integer.parseInt(edadArray[2]);

        } else {
            // anios cumplidos
            edadAnios = Integer.valueOf((aniosEditText.getText().toString()));
            if (mesesEditText.getText().length() > 0 && mesesEditText.getVisibility() == View.VISIBLE) {

                edadMeses = Integer.valueOf(mesesEditText.getText().toString());
            }else{
                edadMeses = Global.ENTEROS_VACIOS;
            }
        }

        int genero = Integer.valueOf(((Values) sexoSpinner.getSelectedItem()).getKey());
        // Guardar nuevo registro
        if (tipoGestion == 1) {
            Persona persona = new Persona();
            persona.setIdhogar(hogar.getId());
            persona.setIdresidente(Integer.valueOf(((Values) tipoResidenteSpinner.getSelectedItem()).getKey()));
            persona.setOrden(ordenPersona + 1);

            persona.setNombres(nombresEditText.getText().toString().trim());
            persona.setApellidos(apellidosEditText.getText().toString().trim());
            persona.setSexo(Integer.valueOf(((Values) sexoSpinner.getSelectedItem()).getKey()));
            persona.setIdparentesco(Integer.valueOf(((Values) parentescoSpinner.getSelectedItem()).getKey()));

            if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton) {

                persona.setTipoEdad(Global.FECHA_NACIMIENTO);
                persona.setFechanacimiento(fechaNacimientoButton.getText().toString());

            } else {
                if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton) {

                    persona.setTipoEdad(Global.ANIOS_CUMPLIDOS);
                    persona.setFechanacimiento(Global.CADENAS_VACIAS);

                } else {
                    persona.setTipoEdad(edadRadioGroup.getCheckedRadioButtonId());
                }
            }

            persona.setEdadanio(edadAnios);
            persona.setEdadmes(edadMeses);
            persona.setInformacioncompleta(Global.INFORMACION_INCOMPLETA);
            persona.setFechainicio(Utilitarios.getCurrentDateAndHour());

            persona.setCorreoelectronico(correoEditText.getText().toString().trim());

            persona.setIddocumentacion(Integer.parseInt(((Values) documentoSpinner.getSelectedItem()).getKey()));

            if (!TextUtils.isEmpty(cedulaEditText.getText().toString())) {
                persona.setCi(cedulaEditText.getText().toString());
            } else {
                persona.setCi(Global.CADENAS_VACIAS);
            }

            if ((persona.getOrden() == 1)) {
                if (validarJefeHogar(edadAnios))
                    return;
                persona.setIdparentesco(1);

            }

            Uri uri = PersonaDao.save(contentResolver, persona);
            String id = uri.getPathSegments().get(1);
            if (!id.equals("0")) {
                persona.setId(Integer.parseInt(id));
                addFila(persona);
                limpiarCampos();
                tipoGestion = 1;
                ordenPersona++;
            }

        } else {
            if (tipoGestion == 2) {
                persona.setIdresidente(Integer.valueOf(((Values) tipoResidenteSpinner.getSelectedItem()).getKey()));
                persona.setNombres(nombresEditText.getText().toString().trim());
                persona.setApellidos(apellidosEditText.getText().toString().trim());
                persona.setIddocumentacion(Integer.parseInt(((Values) documentoSpinner.getSelectedItem()).getKey()));
                persona.setCorreoelectronico(correoEditText.getText().toString().trim());
                persona.setIdparentesco(Integer.valueOf(((Values) parentescoSpinner.getSelectedItem()).getKey()));
                if (!TextUtils.isEmpty(cedulaEditText.getText().toString())) {
                    persona.setCi(cedulaEditText.getText().toString());
                } else {
                    persona.setCi(Global.CADENAS_VACIAS);
                }
                if (persona.getIdparentesco() == 1) {
                    if ((persona.getOrden() == 1)) {
                        if (validarJefeHogar(edadAnios))
                            return;
                    }
                }
                if (persona.getIdparentesco() == 2) {
                    if (validarEdadConyuge(edadAnios))
                        return;
                }
                persona.setEdadmes(edadMeses);
                if ((persona.getEdadanio() >= Global.EDAD_12ANIOS)
                        && (persona.getSexo() == Global.GENERO_FEMENINO)) {
                    if (genero != Global.GENERO_FEMENINO) {

                        //validarMadreEHijos(persona);

                    } else {
                        if (edadAnios < Global.EDAD_12ANIOS) {

                            //validarMadreEHijos(persona);
                        }
                    }
                }

                if (persona.getEdadanio() != edadAnios || persona.getSexo() != genero) {

                    final Persona personaNew = new Persona();
                    personaNew.setIdresidente(persona.getIdresidente());
                    personaNew.setIdhogar(persona.getIdhogar());
                    personaNew.setId(persona.getId());
                    personaNew.setNombres(persona.getNombres());
                    personaNew.setApellidos(persona.getApellidos());
                    personaNew.setSexo(genero);
                    personaNew.setIdparentesco(persona.getIdparentesco());
                    if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton) {

                        personaNew.setTipoEdad(Global.FECHA_NACIMIENTO);
                        personaNew.setFechanacimiento(fechaNacimientoButton.getText().toString());

                    } else {
                        if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton) {

                            personaNew.setTipoEdad(Global.ANIOS_CUMPLIDOS);
                            personaNew.setFechanacimiento(Global.CADENAS_VACIAS);

                        } else {
                            personaNew.setTipoEdad(edadRadioGroup.getCheckedRadioButtonId());
                        }
                    }
                    personaNew.setEdadanio(edadAnios);
                    personaNew.setEdadmes(edadMeses);
                    personaNew.setOrden(persona.getOrden());
                    personaNew.setIddocumentacion(persona.getIddocumentacion());
                    personaNew.setCi(persona.getCi());

                    if (persona.getOrden() == 1) {
                        personaNew.setIdparentesco(persona.getIdparentesco());
                    }

                    personaNew.setInformacioncompleta(Global.INFORMACION_INCOMPLETA);
                    PersonaDao.update(contentResolver, personaNew);

                } else {
                    PersonaDao.updateCabezera(
                            contentResolver,
                            persona,
                            new String[] {
                                    String.valueOf(persona.getId())});

                }
                getPersonas();

            }
        }

    }

    /**
     * Metodo para validar la edad de la conyuge
     * @param edad
     * @return
     */
    public boolean validarEdadConyuge(Integer edad) {
        if (edad < 12) {

            getAlert(
                    getString(R.string.validacion_aviso),
                    getString(R.string.seccion5MensajePregunta12EdadConyugeNucleo));
            return true;
        }

        return false;
    }

    /**
     * Metodo para validar el jefe de hogar
     */
    public boolean validarJefeHogar(Integer edad) {
        boolean cancel = false;

        if (edad < Global.EDAD_PARENTESCO) {

            getAlert(getString(R.string.validacion_aviso),
                    getString(R.string.seccion5MensajeValidacionEdadParentesco));
            return true;
        }
        return false;
    }

    /**
     * Metodo para  el jefe de hogar
     */
    public boolean JefeHogar(Integer edad) {
        boolean cancel = false;

        if (edad < Global.EDAD_PARENTESCO) {

            getAlert(getString(R.string.validacion_aviso),
                    getString(R.string.mensajeValidacionEdadParentesco));
            return true;
        }
        return false;
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


    private void showDatePicker(int tipoFecha) {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        if (tipoFecha == 1) {
            date.setCallBack(ondateInicio);
        }

        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondateInicio = new DatePickerDialog.OnDateSetListener() {

        @SuppressLint("SimpleDateFormat")
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fechaNacimientoButton.setText(Utilitarios.getDate(year, monthOfYear, dayOfMonth));
            Date dateActual = null;
            Date dateComponente = null;
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateActual = formatoFecha.parse(Utilitarios.getCurrentDate());
                dateComponente = formatoFecha.parse(fechaNacimientoButton.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dateComponente != null && dateActual.compareTo(dateComponente) < 0) {

                getAlert(getString(R.string.validacion_aviso), getString(R.string.mvFechaNacimientoActual));
                fechaNacimientoButton.requestFocus();
                fechaNacimientoButton.setText(R.string.seleccioneFecha);
                return;
            }
            String edad = Utilitarios.calcularEdad(dateComponente);
            String[] edadArray = edad.split("-");
            String _anios = edadArray[0];
            int anios = Integer.parseInt(_anios);
            if (anios >= 0 && anios < 5) {
                correoEditText.setText("");
                correoEditText.setEnabled(false);
            } else {
                if (anios >= 5) {
                    correoEditText.setEnabled(true);
                }
            }
        }
    };

    /**
     * Metodo para validar la informacion completa de las personas
     */
    public static boolean validarInformacionCompletaPersona() {

        boolean isValidate = true;
        for (int i = 0; i < personasTableLayout.getChildCount(); i++) {

            TableRow row = (TableRow) personasTableLayout.getChildAt(i);
            Persona _persona = (Persona) row.getTag();
            if (_persona.getInformacioncompleta() == Global.INFORMACION_INCOMPLETA) {
                isValidate = false;
            }
        }
        return isValidate;
    }
}
