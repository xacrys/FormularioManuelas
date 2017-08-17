package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;

/**
 * Created by lmorales on 17/08/17.
 */

public class HogarFragment extends Fragment {

    Spinner tipoHogarSpinner;
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
     * @param item
     */
    private void obtenerVistas(View item) {

        tipoHogarSpinner = item
                .findViewById(R.id.tipoHogarSpinner);



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

        tipoHogarSpinner.setAdapter(HogarPreguntas
                .getTipoViviendaAdapter(getActivity()));
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
     * Método que valida campos obligatorios, numeros de telefonos etc.
     * @return
     */
    protected boolean validarCampos() {

        return false;
    }
}
