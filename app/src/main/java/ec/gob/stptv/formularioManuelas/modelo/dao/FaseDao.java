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
        values.remove(COLUMNA_ID);
        return cr.insert(FormularioManuelasProvider.CONTENT_URI_FASE, values);
    }


}
