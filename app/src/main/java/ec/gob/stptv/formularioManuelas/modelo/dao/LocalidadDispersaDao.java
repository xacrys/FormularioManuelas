package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.database.Cursor;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.entidades.LocalidadDispersa;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Created by lmorales on 18/09/17.
 */

public class LocalidadDispersaDao extends  LocalidadDispersa{
    public static ArrayList<Values> getLocalidades(ContentResolver cr, String[] parametros) {

        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_LOCALIDADDISPERSA,
                LocalidadDispersa.columnas, LocalidadDispersa.whereByIDDPA, parametros, null);

        ArrayList<Values> localidades = new ArrayList<Values>();

        if (result.moveToFirst())
            do {
                Values localidad = new Values();
                localidad.setKey(result.getString(result.getColumnIndex(COLUMNA_IDLOCALIDAD)));
                localidad.setValue(result.getString(result.getColumnIndex(COLUMNA_DESCRIPCION)));
                localidades.add(localidad);
            } while (result.moveToNext());

        result.close();

        return localidades;
    }
}
