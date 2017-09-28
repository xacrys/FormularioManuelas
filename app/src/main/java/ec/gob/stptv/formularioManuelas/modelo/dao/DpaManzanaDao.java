package ec.gob.stptv.formularioManuelas.modelo.dao;

import java.util.ArrayList;
import java.util.StringTokenizer;

import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.entidades.DpaManzana;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * Created by lmorales on 21/08/17.
 */

public class DpaManzanaDao extends DpaManzana {


    public static ArrayList<Values> getZonaSector(Cursor result) {

        ArrayList<Values> dpaManzanas = new ArrayList<Values>();
        dpaManzanas.add(new Values(String.valueOf(Global.VALOR_SELECCIONE_DPA), "Seleccione.."));

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

    public static ArrayList<Values> getManzana(Cursor result) {

        ArrayList<Values> dpaManzanas = new ArrayList<Values>();
        dpaManzanas.add(new Values(Global.VALOR_CADENA_MANZANA, "Seleccione.."));

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

    /**
     * Método que obtiene la persona por id
     * @param cr
     * @param where
     * @param id
     * @return
     */
    public static DpaManzana  getDpaManzana(ContentResolver cr, String where, String[] id) {

        Cursor result = cr.query(
                FormularioManuelasProvider.CONTENT_URI_DPAMANZANA,
                columnas, where, id, null);

        DpaManzana dpaManzana = null;
        if (result != null) {
            if ((result.getCount() == 0) || !result.moveToFirst()) {
                dpaManzana = null;
            } else {
                if (result.moveToFirst()) {
                    dpaManzana = newDpaManzana(result);
                }
            }
            result.close();
        }
        return dpaManzana;
    }
}
