package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
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
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
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
import android.content.DialogInterface;

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
    private ContentResolver contentResolver;
    private Vivienda vivienda;
    private Hogar hogar;
    private static TableLayout personasTableLayout;
    private int ordenPersona = 0;
    private Persona persona;
    private int tipoGestion = 1;
    private int cantidaPersonas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar,
                container, false);

        contentResolver = getActivity().getContentResolver();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();
        this.mallasValidacion();
        return item;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vivienda = ViviendaFragment.getVivienda();
        if (vivienda.getId() != 0) {
            getPersonas();
        }

    }


    /**
     * Metòdo que obtiene todas las personas
     */
    public void getPersonas() {

        personasTableLayout.removeAllViews();

        ArrayList<Persona> personas = PersonaDao.getPersonas(contentResolver,
                Persona.whereByViviendaId,
                new String[] { String.valueOf(vivienda.getId()) },
                Persona.COLUMNA_ORDEN);
        ordenPersona = personas.size();

        Utilitarios.logInfo(MiembrosHogarFragment.class.getName(),"cantidad fde personas"+personas.size() + "");

        for (Persona _persona : personas) {

            addFila(_persona);
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
     * @param _persona
     */
    private void getAlertActions(String title, final Persona _persona) {

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
                            /**
                             * Completar información de la persona
                             */
                            case 0:

                                FragmentManager fragMgr = getFragmentManager();
                                Fragment currentFragment = fragMgr
                                        .findFragmentById(R.id.miembros_hogar_fragment);

                                Fragment newFragment = new MiembrosHogarTodasPersonasFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("persona", _persona);

                                newFragment.setArguments(args);
                                FragmentTransaction transaction = getFragmentManager()
                                        .beginTransaction();

                                transaction.add(R.id.fragmentContainer,
                                        newFragment);

                                transaction.remove(currentFragment);

                                transaction.addToBackStack(null);

                                transaction.commit();


                                break;
                            /**
                             * Editar los datos de la persona
                             */

                            case 1:

                                tipoGestion = 2;

                                llenarCampos(_persona);

                                if (_persona.getIdparentesco() == PersonaPreguntas.ControlTrabajoParentesco.JEFE.getValor()){

                                    getAlert(
                                            getString(R.string.validacion_aviso),
                                            getString(R.string.mvNoEditar));

                                    sexoSpinner.setEnabled(false);

                                    aniosEditText.setEnabled(false);

                                }
                                else{

                                    sexoSpinner.setEnabled(true);
                                    aniosEditText.setEnabled(true);
                                }

                                break;

                            /**
                             * Eliminar la persona
                             */

                            case 2:

                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        getActivity());
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

                                                /*if(_persona.getIdparentesco() == PersonaPreguntas.ControlTrabajoParentesco.JEFE.getValor()){
                                                    eliminarJefeHogar(_persona);
                                                }
                                                else{
                                                    PersonaDao.delete(contentResolver,_persona);
                                                }
                                                getPersonas();*/
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
                            //}

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
     * Método que llena los controles con datos de la base
     * @param _persona
     */
    @SuppressWarnings("unchecked")
    protected void llenarCampos(Persona _persona) {

        int posicion = Utilitarios.getPosicionByKey(
                (ArrayAdapter<Values>) tipoResidenteSpinner.getAdapter(),
                String.valueOf(_persona.getIdresidente()));
        tipoResidenteSpinner.setSelection(posicion);

        posicion = Utilitarios.getPosicionByKey(
                (ArrayAdapter<Values>) sexoSpinner.getAdapter(),
                String.valueOf(_persona.getSexo()));
        sexoSpinner.setSelection(posicion);


        posicion = Utilitarios.getPosicionByKey(
                (ArrayAdapter<Values>) documentoSpinner.getAdapter(),
                String.valueOf(_persona.getIddocumentacion()));
        documentoSpinner.setSelection(posicion);

        if(!_persona.getCi().equals(Global.CADENAS_VACIAS)){
            cedulaEditText.setText(_persona.getCi());
        }
        else{
            cedulaEditText.setText("");
        }


        apellidosEditText.setText(persona.getApellidos());
        nombresEditText.setText(persona.getNombres());

        aniosEditText.setText(String.valueOf(persona.getEdadanio()));
        if (persona.getEdadmes() == -1) {
            mesesEditText.setVisibility(View.INVISIBLE);
            mesesEditText.setText("");
        } else {
            mesesEditText.setVisibility(View.VISIBLE);
            mesesEditText.setText(String.valueOf(persona.getEdadmes()));
        }

    }


    /**
     * Metódo que eprmite limpiar los datos
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
        personasTableLayout = item.findViewById(R.id.personasTableLayout);


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
            cedulaEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (TextUtils.isEmpty(apellidosEditText.getText().toString().trim())) {
            apellidosEditText.setError(null);
            apellidosEditText.clearFocus();
            apellidosEditText.setError(getString(R.string.errorCampoRequerido));
        } else if (TextUtils.isEmpty(nombresEditText.getText().toString().trim())) {
            nombresEditText.setError(null);
            nombresEditText.clearFocus();
            nombresEditText.setError(getString(R.string.errorCampoRequerido));
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
        } else if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton &&
                TextUtils.isEmpty(mesesEditText.getText().toString().trim())
                ) {
            mesesEditText.setError(null);
            mesesEditText.clearFocus();
            mesesEditText.setError(getString(R.string.errorCampoRequerido));
//            focusView = mesesEditText;
        } else if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton &&
                fechaNacimientoButton.getText().toString().trim().equals("Seleccione")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + "Botón fecha de Nacimiento");
        } else {
            cancel = false;
        }
        return cancel;
    }


    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {
        tipoResidenteSpinner.setAdapter(PersonaPreguntas.getTipoResidenteAdapter(getActivity()));
        documentoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoDocumentoAdapter(getActivity()));
        sexoSpinner.setAdapter(PersonaPreguntas.getSexoPersonaAdapter(getActivity()));

    }

    /**
     * metodo para realizar las acciones
     */
    private void realizarAcciones() {

        nuevoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Fragment newFragment = new MiembrosHogarTodasPersonasFragment();
                Bundle args = new Bundle();
                //args.putParcelable("persona", persona);

                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

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

        edadRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadFechaNacimientoOpcion1RadioButton) {
                    fechaNacimientoButton.setEnabled(true);
                    aniosEditText.setEnabled(false);
                    mesesEditText.setEnabled(false);
                    aniosEditText.setText("");
                    mesesEditText.setText("");
                    aniosEditText.setError(null);
                    aniosEditText.clearFocus();
                    mesesEditText.setError(null);
                    mesesEditText.clearFocus();

                } else {
                    fechaNacimientoButton.setEnabled(false);
                    fechaNacimientoButton.setText(R.string.seleccioneFecha);
                    aniosEditText.setEnabled(true);
                    mesesEditText.setEnabled(true);
                }
            }
        });

    }

    /**
     * Método que guarda la vivienda en la base de datos
     */
    private void guardar() {

        vivienda = ViviendaFragment.getVivienda();
        hogar = HogarFragment.getHogar();

        int edadMeses = 0;
        int edadAnios = Integer.valueOf((aniosEditText.getText().toString()));
        int genero = Integer.valueOf(((Values) sexoSpinner.getSelectedItem())
                .getKey());

        if (mesesEditText.getText().length() > 0
                && mesesEditText.getVisibility() == View.VISIBLE) {

            edadMeses = Integer.valueOf(mesesEditText.getText().toString());
        }

        if (mesesEditText.getVisibility() == View.INVISIBLE) {
            edadMeses = -1;
        }

        Utilitarios.logInfo(MiembrosHogarFragment.class.getName(), "TipoGestion: " + tipoGestion);
        Utilitarios.logInfo(MiembrosHogarFragment.class.getName(), "edad en meses" + edadMeses);

        // Guardar nuevo registro
        if (tipoGestion == 1) {
            Persona persona = new Persona();
            persona.setIdvivienda(vivienda.getId());
            persona.setIdhogar(hogar.getId());
            persona.setIdresidente(Integer
                    .valueOf(((Values) tipoResidenteSpinner.getSelectedItem())
                            .getKey()));
            persona.setOrden(ordenPersona + 1);


            persona.setApellidos(apellidosEditText.getText()
                    .toString().trim());
            persona.setNombres(nombresEditText.getText()
                    .toString().trim());

            /*persona.setFonetico(Fonetico.convertir(apellidosEditText.getText()
                    .toString().trim()+ nombresEditText.getText()
                    .toString().trim()));*/

            persona.setSexo(Integer.valueOf(((Values) sexoSpinner
                    .getSelectedItem()).getKey()));
            persona.setEdadanio(edadAnios);
            persona.setEdadmes(edadMeses);
            persona.setInformacioncompleta(Global.INFORMACION_INCOMPLETA);
            persona.setFechainicio(Utilitarios.getCurrentDateAndHour());

            persona.setIddocumentacion(Integer
                    .parseInt(((Values) documentoSpinner.getSelectedItem())
                            .getKey()));

            if (!TextUtils.isEmpty(cedulaEditText.getText().toString())) {
                persona.setCi(cedulaEditText.getText().toString());
            }
            else{
                persona.setCi(Global.CADENAS_VACIAS);
            }

            // if (persona.getParentescoNucleo() == 1) {

            if ((persona.getOrden() == 1)) {
                if (JefeHogar(edadAnios))
                    return;

                persona.setIdparentesco(1);
            }

			/*
			 * if (JefeNucleo(edadAnios)) return;
			 */

            // }

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

                persona.setIdresidente(Integer
                        .valueOf(((Values) tipoResidenteSpinner
                                .getSelectedItem()).getKey()));
                persona.setApellidos(apellidosEditText.getText()
                        .toString().trim());
                persona.setNombres(nombresEditText.getText()
                        .toString().trim());

                /*persona.setFonetico(Fonetico.convertir(apellidosEditText.getText()
                        .toString().trim()+ nombresEditText.getText()
                        .toString().trim()));*/

                persona.setIddocumentacion(Integer
                        .parseInt(((Values) documentoSpinner.getSelectedItem())
                                .getKey()));

                if (!TextUtils.isEmpty(cedulaEditText.getText().toString())) {
                    persona.setCi(cedulaEditText.getText().toString());
                }
                else{
                    persona.setCi(Global.CADENAS_VACIAS);
                }

                /*if (persona.getParentescoNucleo() == 1) {

                    if ((persona.getOrden() == 1)) {

                        if (JefeHogar(edadAnios))
                            return;
                    }

                    if (JefeNucleo(edadAnios))
                        return;

                }
                if (persona.getParentescoNucleo() == 2)  {

                    if (EdadConyuge(edadAnios))
                        return;
                }*/

                persona.setEdadmes(edadMeses);

                if (persona.getEdadanio() != edadAnios
                        || persona.getSexo() != genero) {

                    Persona personaNew = new Persona();
                    personaNew.setIdresidente(persona.getIdresidente());
                    personaNew.setIdvivienda(persona.getIdvivienda());
                    personaNew.setIdhogar(persona.getIdhogar());
                    personaNew.setId(persona.getId());
                    personaNew.setApellidos(persona.getApellidos());
                    personaNew.setNombres(persona.getNombres());
                    personaNew.setSexo(genero);
                    personaNew.setEdadanio(edadAnios);
                    personaNew.setEdadmes(edadMeses);
                    personaNew.setOrden(persona.getOrden());
                    personaNew.setIddocumentacion(persona.getIddocumentacion());
                    personaNew.setCi(persona.getCi());

                    if (persona.getOrden()== 1){
                        personaNew.setIdparentesco(persona.getIdparentesco());

                    }

                    personaNew
                            .setInformacioncompleta(Global.INFORMACION_INCOMPLETA);

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
     * Metodo para  el jefe de hogar
     */
    public boolean JefeHogar(Integer edad) {
        boolean cancel = false;

        if (edad < Global.EDAD_PARENTESCO) {

            getAlert(getString(R.string.validacion_aviso),
                    getString(R.string.mensajeValidacionEdadParentesco));
            cancel = true;
            return cancel;
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
}
