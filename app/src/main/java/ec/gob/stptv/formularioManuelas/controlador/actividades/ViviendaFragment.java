package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

/***
 * Autor:Christian Tintin
 */
public class ViviendaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
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



    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {





    }
}
