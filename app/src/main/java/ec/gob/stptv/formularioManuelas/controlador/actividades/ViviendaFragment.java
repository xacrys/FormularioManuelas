package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

/**
 * Created by lmorales on 17/08/17.
 */

public class ViviendaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
                container, false);


        Bundle extra = getActivity().getIntent().getExtras();

        return item;
    }
}
