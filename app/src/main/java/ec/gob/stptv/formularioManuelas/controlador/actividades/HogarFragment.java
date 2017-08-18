package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;

/***
 * Autor: Christian Tintin
 */

public class HogarFragment extends Fragment {

    Spinner tipoHogarSpinner;
    Spinner documentoHogarSpinner;
    Spinner fuenteAguaSpinner;
    Spinner ubicacionAguaSpinner;
    Spinner tratamientoAguaSpinner;
    Spinner servicioSanitarioSpinner;
    Spinner ubicacionSanitarioSpinner;
    Spinner servicioDuchaSpinner;
    Spinner eliminaBasuraSpinner;
    Spinner tipoAlumbradoSpinner;
    Spinner energeticoCocinaSpinner;
    EditText codigoElectricoEditText;
    EditText numCuartosEditText;
    EditText numDormitoriosEditText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_hogar,
                container, false);


        Bundle extra = getActivity().getIntent().getExtras();

        this.obtenerVistas(item);
        this.cargarPreguntas();


        return item;
    }



    /**
     * Método para obtener las controles de la vista
     *
     */
    private void obtenerVistas(View item) {

        tipoHogarSpinner = item.findViewById(R.id.tipoHogarSpinner);
        documentoHogarSpinner = item.findViewById(R.id.documentoHogarSpinner);
        numCuartosEditText = item.findViewById(R.id.numCuartosEditText);
        numDormitoriosEditText = item.findViewById(R.id.numDormitoriosEditText);
        fuenteAguaSpinner = item.findViewById(R.id.fuenteAguaSpinner);
        ubicacionAguaSpinner=item.findViewById(R.id.ubicacionAguaSpinner);
        tratamientoAguaSpinner=item.findViewById(R.id.tratamientoAguaSpinner);
        servicioSanitarioSpinner=item.findViewById(R.id.servicioSanitarioSpinner);
        ubicacionSanitarioSpinner = item.findViewById(R.id.ubicacionSanitarioSpinner);
        servicioDuchaSpinner=item.findViewById(R.id.servicioDuchaSpinner);
        eliminaBasuraSpinner = item.findViewById(R.id.eliminaBasuraSpinner);
        tipoAlumbradoSpinner = item.findViewById(R.id.tipoAlumbradoSpinner);
        codigoElectricoEditText = item.findViewById(R.id.codigoElectricoEditText);
        energeticoCocinaSpinner = item.findViewById(R.id.energeticoCocinaSpinner);
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

        tipoHogarSpinner.setAdapter(HogarPreguntas.getTenenciaHogarAdapter(getActivity()));
        documentoHogarSpinner.setAdapter(HogarPreguntas.getDocumentoHogarAdapter(getActivity()));
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
     *
     */
    protected boolean validarCampos() {

        return false;
    }
}
