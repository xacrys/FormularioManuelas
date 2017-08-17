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

        this.getViews(item);
        this.loadPreguntas();


        return item;
    }

    private void getViews(View item) {

        tipoHogarSpinner = item
                .findViewById(R.id.tipoHogarSpinner);



    }


    /***
     * Método para cargar las preguntas
     */
    private void loadPreguntas() {

        tipoHogarSpinner.setAdapter(HogarPreguntas
                .getTipoViviendaAdapter(getActivity()));


    }

}
