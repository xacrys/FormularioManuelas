package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Created by lmorales on 23/08/17.
 */

public class FaseDao extends Fase{

    public static Uri save(ContentResolver cr, Fase fase) {
        ContentValues values = getValues(fase);
        return cr.insert(FormularioManuelasProvider.CONTENT_URI_FASE, values);
    }

    /**
     * Método que obtiene la fase por id
     * @param cr
     * @param where
     * @param id
     * @return
     */
    public static Fase getFase(ContentResolver cr, String where, String[] id) {

        Cursor result = cr.query(
                FormularioManuelasProvider.CONTENT_URI_FASE,
                columnas, where, id, null);

        Fase fase = null;
        if (result != null) {
            if ((result.getCount() == 0) || !result.moveToFirst()) {
                fase = null;

            } else {
                if (result.moveToFirst()) {
                    fase = newFase(result);
                }
            }
            result.close();
        }
        return fase;
    }


    /**
     * Metodo que obtene las fases
     * @param cr
     * @param where
     * @param parametros
     * @param orderBy
     * @return
     */
    public static ArrayList<Fase> getFases(ContentResolver cr, String where, String[]  parametros, String orderBy) {
        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_FASE,
                columnas, where, parametros,
                orderBy);

        ArrayList<Fase> fases = new ArrayList<>();

        if (result!= null){
            if (result.moveToFirst())
                do {
                    fases.add(newFase(result));
                }
                while (result.moveToNext());
            result.close();
        }
        return fases;
    }



}
