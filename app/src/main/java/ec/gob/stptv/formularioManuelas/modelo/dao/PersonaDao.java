package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Created by lmorales on 23/08/17.
 */

public class PersonaDao extends Persona{

    public static Uri save(ContentResolver cr, Persona persona) {
        ContentValues values = getValues(persona);
        values.remove(COLUMNA_ID);
        return cr.insert(FormularioManuelasProvider.CONTENT_URI_PERSONA, values);
    }

    public static int update(ContentResolver cr, Persona persona) {
        ContentValues values = getValues(persona);
        values.remove(COLUMNA_ID);
        return cr.update(FormularioManuelasProvider.CONTENT_URI_PERSONA, values, whereById, new String[] { String.valueOf(persona.getId()) });
    }
    public static int delete(ContentResolver cr, Persona persona) {
        int filaEliminada;

        filaEliminada = cr.delete(FormularioManuelasProvider.CONTENT_URI_PERSONA,
                whereById,
                new String[] { String.valueOf(persona.getId()) });

        if (filaEliminada > 0) {
            ordenar(cr, persona.getIdhogar());
        }

        return filaEliminada;
    }


    public static int updateCabezera(ContentResolver cr, Persona persona,
                                     String[] selectionArgs) {
        ContentValues values = new ContentValues();
        values.put(COLUMNA_IDRESIDENTE, persona.getIdresidente());
        values.put(COLUMNA_IDDOCUMENTACION, persona.getIddocumentacion());
        values.put(COLUMNA_CI, persona.getCi());
        values.put(COLUMNA_APELLIDOS, persona.getApellidos());
        values.put(COLUMNA_NOMBRES, persona.getNombres());
        values.put(COLUMNA_SEXO, persona.getSexo());
        values.put(COLUMNA_TIPOEDAD, persona.getTipoEdad());
        values.put(COLUMNA_FECHANACIMIENTO, persona.getFechanacimiento());
        values.put(COLUMNA_EDADANIO, persona.getEdadanio());
        values.put(COLUMNA_EDADMES, persona.getEdadmes());
        values.put(COLUMNA_IDPARENTESCO, persona.getIdparentesco());
        values.put(COLUMNA_CORREOELECTRONICO, persona.getCorreoelectronico());

        return cr.update(FormularioManuelasProvider.CONTENT_URI_PERSONA,
                values, whereById, selectionArgs);
    }

    public static ArrayList<Persona> getPersonas(ContentResolver cr, String where, String[]  parametros, String orderBy) {
        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_PERSONA,
                columnas, where, parametros,
                orderBy);

        ArrayList<Persona> personas = new ArrayList<>();

        if (result!= null){
            if (result.moveToFirst())
                do {
                    personas.add(newPersona(result));
                }
                while (result.moveToNext());
            result.close();
        }
        return personas;
    }

    /**
     * Método que obtiene la persona por id
     * @param cr
     * @param where
     * @param id
     * @return
     */
    public static Persona getPersona(ContentResolver cr, String where, String[] id) {

        Cursor result = cr.query(
                FormularioManuelasProvider.CONTENT_URI_PERSONA,
                columnas, where, id, null);

        Persona persona = null;
        if (result != null) {
            if ((result.getCount() == 0) || !result.moveToFirst()) {
                persona = null;

            } else {
                if (result.moveToFirst()) {

                    persona = newPersona(result);

                }
            }
            result.close();
        }
        return persona;
    }

    /**
     * Metodo que ordena a als personas cuando elimina
     * @param cr
     * @param idhogar
     */
    private static void ordenar(ContentResolver cr, int idhogar) {

        ArrayList<Persona> personas = getPersonas(cr, Persona.whereByIdHogar, new String[] { String.valueOf(idhogar) }, Persona.COLUMNA_ORDEN);

        int orden = 1;
        for (Persona _persona : personas) {

            _persona.setOrden(orden);
            update(cr, _persona);
            orden++;

        }

    }
}
