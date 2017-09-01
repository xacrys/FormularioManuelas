package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Created by lmorales on 30/08/17.
 */

public class UsuarioDao extends Usuario{

    public static Uri save(ContentResolver cr, Usuario usuario) {
        ContentValues values = getValues(usuario);
        return cr.insert(FormularioManuelasProvider.CONTENT_URI_USUARIO, values);
    }

    public static int update(ContentResolver cr, Usuario usuario) {
        ContentValues values = getValues(usuario);
        return cr.update(FormularioManuelasProvider.CONTENT_URI_USUARIO, values, whereById, new String[] { String.valueOf(usuario.getUsuario()) });
    }

    public static Usuario getUsuario(ContentResolver cr, String where, String[] id) {

        Cursor result = cr.query(
                FormularioManuelasProvider.CONTENT_URI_USUARIO,
                columnas, where, id, null);
        Usuario usuario = null;
        if (result != null) {
            if ((result.getCount() == 0) || !result.moveToFirst()) {
                usuario = null;
            } else {
                if (result.moveToFirst()) {
                    usuario = newUsuario(result);
                }
            }
            result.close();
        }
        return usuario;
    }

}
