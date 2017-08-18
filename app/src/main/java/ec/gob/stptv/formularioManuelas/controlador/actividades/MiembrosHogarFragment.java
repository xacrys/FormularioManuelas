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

    private Button nuevoButton;
    private Button atrasButton;
    private Spinner tipoResidenteSpinner;
    private Spinner documentoSpinner;
    private Spinner sexoSpinner;

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
     *
     */
    private void obtenerVistas(View item) {

        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);
        tipoResidenteSpinner = item.findViewById(R.id.tipoResidenteSpinner);
        documentoSpinner = item.findViewById(R.id.documentoSpinner);
        sexoSpinner = item.findViewById(R.id.sexoSpinner);


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

    }
}
