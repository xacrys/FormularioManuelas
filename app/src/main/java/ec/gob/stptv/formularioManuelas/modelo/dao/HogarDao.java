package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;


/**
 * Created by lmorales on 23/08/17.
 */

public class HogarDao extends Hogar{

    public static Uri save(ContentResolver cr, Hogar hogar) {
        ContentValues values = getValues(hogar);
        values.remove(COLUMNA_ID);
        Uri id = cr.insert(FormularioManuelasProvider.CONTENT_URI_HOGAR, values);
        return id;
    }

    public static int update(ContentResolver cr, Hogar hogar) {
        ContentValues values = getValues(hogar);
        values.remove(COLUMNA_ID);
        int result = cr.update(FormularioManuelasProvider.CONTENT_URI_HOGAR, values, whereById, new String[] { String.valueOf(hogar.getId())});
        return result;
    }

    public static ArrayList<Hogar> getHogares(ContentResolver cr, String where, String[]  parametros, String orderBy) {

        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_HOGAR,
                columnas, where, parametros,
                orderBy);

        ArrayList<Hogar> hogares = new ArrayList<Hogar>();

        if (result.moveToFirst())
            do {
                hogares.add(newHogar(result));
            } while (result.moveToNext());

        result.close();
        return hogares;
    }


    public static Hogar getHogar(ContentResolver cr, String where, String[] parametros) {

        Cursor result = cr.query(
                FormularioManuelasProvider.CONTENT_URI_HOGAR,
                columnas, where, parametros, null);

        Hogar hogar = null;

        if ((result.getCount() == 0) || !result.moveToFirst()) {
            hogar = null;

        } else {
            if (result.moveToFirst()) {
                hogar = newHogar(result);
            }
        }

        result.close();
        return hogar;
    }
}
