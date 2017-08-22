package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.DatePickerFragment;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar,
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


    }

    /**
     * @return estado de la validación
     */
    protected Boolean validarCampos() {
        Boolean cancel = true;
        View focusView = null;
        cedulaEditText.setError(null);
        cedulaEditText.clearFocus();
        if (((Values) tipoResidenteSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.tipoResidente));
        } else if (((Values) documentoSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.etiquetaCedula));
        } else if ((((Values) documentoSpinner.getSelectedItem()).getKey().equals("1") ||
                ((Values) documentoSpinner.getSelectedItem()).getKey().equals("2")) &&
                TextUtils.isEmpty(cedulaEditText.getText().toString().trim())) {
            cedulaEditText.setError(getString(R.string.errorCampoRequerido));
//            focusView = cedulaEditText;
        } else if (TextUtils.isEmpty(apellidosEditText.getText().toString().trim())) {
            apellidosEditText.setError(null);
            apellidosEditText.clearFocus();
            apellidosEditText.setError(getString(R.string.errorCampoRequerido));
//            focusView = apellidosEditText;
        } else if (TextUtils.isEmpty(nombresEditText.getText().toString().trim())) {
            nombresEditText.setError(null);
            nombresEditText.clearFocus();
            nombresEditText.setError(getString(R.string.errorCampoRequerido));
//            focusView = nombresEditText;
        } else if (((Values) sexoSpinner.getSelectedItem()).getKey().equals("-1")) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.sexoTitulo));
        } else if (edadRadioGroup.getCheckedRadioButtonId() == -1) {
            getAlert(getString(R.string.validacion_aviso), getString(R.string.seleccione_pregunta) + getString(R.string.edadTitulo));
        } else if (edadRadioGroup.getCheckedRadioButtonId() == R.id.edadAniosCumplidosOpcion2RadioButton &&
                TextUtils.isEmpty(aniosEditText.getText().toString().trim())
                ) {
            aniosEditText.setError(null);
            aniosEditText.clearFocus();
            aniosEditText.setError(getString(R.string.errorCampoRequerido));
//            focusView = aniosEditText;
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
                if (!validarCampos()) {
                    getAlert("Hogar", "Si guarda Hogar");
                }
            }
        });

        fechaNacimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(1);
            }
        });


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

            if (dateComponente!=null && dateActual.compareTo(dateComponente) < 0) {

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
