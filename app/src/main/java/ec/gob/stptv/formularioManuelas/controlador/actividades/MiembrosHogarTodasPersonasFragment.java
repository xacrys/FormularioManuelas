package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;

/**
 * Created by lmorales on 17/08/17.
 */

public class MiembrosHogarTodasPersonasFragment extends Fragment {

    private Button nuevoButton;
    private Button atrasButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar_todas_personas,
                container, false);


        Bundle extra = getActivity().getIntent().getExtras();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();
        return item;
    }

    /**
     * Método para obtener las controles de la vista
     *
     */
    private void obtenerVistas(View item) {

        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);
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
    }


    /**
     * Metodo para cerrar la ventana
     */
    private void cerrarVentana() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

}
