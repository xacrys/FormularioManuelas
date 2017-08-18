package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;

/***
 *  Autor: Christian Tintin
 */

public class MiembrosHogarFragment extends Fragment {

    private Spinner documentoSpinner;
    private Spinner parentescoSpinner;
    private Spinner estadoCivilSpinner;
    private Spinner nacionalidadSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar_todas_personas,
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

        documentoSpinner = item.findViewById(R.id.documentoSpinner);
        parentescoSpinner=item.findViewById(R.id.parentescoSpinner);
        estadoCivilSpinner=item.findViewById(R.id.estadoCivilSpinner);
        nacionalidadSpinner=item.findViewById(R.id.nacionalidadSpinner);

    }


    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {

        documentoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoDocumentoAdapter(getActivity()));
        parentescoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoParentescoAdapter(getActivity()));
        estadoCivilSpinner.setAdapter(PersonaPreguntas.getControlTrabajoEstadoCivilAdapter(getActivity()));
        nacionalidadSpinner.setAdapter(PersonaPreguntas.getNacionalidadAdapter(getActivity()));



    }
}
