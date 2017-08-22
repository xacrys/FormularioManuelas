package ec.gob.stptv.formularioManuelas.modelo.dao;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.entidades.DpaManzana;
import android.database.Cursor;

/**
 * Created by lmorales on 21/08/17.
 */

public class DpaManzanaDao extends DpaManzana {


    public static ArrayList<Values> getZonaSectorManzana(Cursor result) {

        ArrayList<Values> dpaManzanas = new ArrayList<Values>();
        dpaManzanas.add(new Values("-1", "Seleccione.."));

        if (result.moveToFirst())
            do {
                Values dpaManzana = new Values();
                String campo = result.getString(0);
                dpaManzana.setKey(campo);
                dpaManzana.setValue(campo);
                dpaManzanas.add(dpaManzana);

            } while (result.moveToNext());

        result.close();
        return dpaManzanas;
    }
}
