package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Created by lmorales on 21/08/17.
 */

public class ViviendaDao  extends Vivienda{

    public static Uri save(ContentResolver cr, Vivienda vivienda) {
        Uri id = cr.insert(FormularioManuelasProvider.CONTENT_URI_VIVIENDA, getValues(vivienda));
        return id;
    }

    public static int update(ContentResolver cr, Vivienda vivienda) {
        ContentValues values = getValues(vivienda);
        values.remove(COLUMNA_ID);

        int result = cr.update(FormularioManuelasProvider.CONTENT_URI_VIVIENDA, values, whereById, new String[] { String.valueOf(vivienda.getId()) });
        return result;
    }

    public static int getUltimoRegistro(ContentResolver cr, Usuario usuario) {
        int ultimaVivienda = 0;
        Cursor result = cr
                .query(FormularioManuelasProvider.CONTENT_URI_VIVIENDA,
                        new String[] { "ifnull(max(CAST(id as integer)),0)" },
                        null, null, null);

        if ((result.getCount() == 0) || !result.moveToFirst()) {

            ultimaVivienda = 0;

        } else {
            if (result.moveToFirst()) {

                Utilitarios.logInfo(ViviendaDao.class.getName(), "getUltimoRegistro de Tabla Vivienda");

                ultimaVivienda = result.getInt(0);
            }
        }
        result.close();

        /**
         * Si ultimaVivienda == 0, busca el maxVivCodigo que trajo del servidor para empezar desde este id
         */
        if(ultimaVivienda == 0)
        {
            /*Cursor cursor = cr.query(FormularioManuelasProvider.CONTENT_URI_USUARIO,
                    new String[]{Usuario.COLUMNA_MAXVIVCODIGO}, Usuario.whereById,
                    new String[] {usuario.getUsuario() }, null);

            if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
                ultimaVivienda = 0;

            } else
            {
                if (cursor.moveToFirst()) {

                    Utilitarios.logInfo(ViviendaDao.class.getName(), "getUltimoRegistro de Tabla Usuario");

                    String _maxViviCodigo = cursor.getString(cursor.getColumnIndex(Usuario.COLUMNA_MAXVIVCODIGO));
                    Utilitarios.logInfo(ViviendaDao.class.getName(), "getUltimoRegistro maxViviCodigo: " + _maxViviCodigo);

                    if(!_maxViviCodigo.equals(""))
                    {
                        String[] maxViviCodigo = _maxViviCodigo.split("-");
                        //Log.i("Vivienda", "getUltimoRegistro maxViviCodigo[1]: " + maxViviCodigo[1]);
                        ultimaVivienda = Integer.parseInt(maxViviCodigo[1]);
                    }
                    else
                    {
                        ultimaVivienda = 0;
                    }
                }
            }
            cursor.close();*/
        }

        return ultimaVivienda + 1;
    }

    public static ArrayList<Vivienda> getViviendas(ContentResolver cr, String where, String[]  parametros, String orderBy) {

        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_VIVIENDA,
                columnas, where, parametros,
                orderBy);

        ArrayList<Vivienda> viviendas = new ArrayList<Vivienda>();

        if (result.moveToFirst())
            do {
                viviendas.add(newVivienda(result));
            } while (result.moveToNext());

        result.close();
        return viviendas;
    }
}
