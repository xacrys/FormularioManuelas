package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        //documentoSpinner = item.findViewById(R.id.documentoSpinner);
        parentescoSpinner=item.findViewById(R.id.parentescoSpinner);
        estadoCivilSpinner=item.findViewById(R.id.estadoCivilSpinner);
        nacionalidadSpinner=item.findViewById(R.id.nacionalidadSpinner);

        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);

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

    }
}
