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
        Uri id = cr.insert(FormularioManuelasProvider.CONTENT_URI_PERSONA, values);
        return id;
    }

    public static int update(ContentResolver cr, Persona persona) {
        ContentValues values = getValues(persona);
        values.remove(COLUMNA_ID);
        int result = cr.update(FormularioManuelasProvider.CONTENT_URI_PERSONA, values, whereById, new String[] { String.valueOf(persona.getId()) });
        return result;
    }

    public static int updateCabezera(ContentResolver cr, Persona persona,
                                     String[] selectionArgs) {
        ContentValues values = new ContentValues();
        values.put(COLUMNA_IDDOCUMENTACION, persona.getIddocumentacion());
        values.put(COLUMNA_CI, persona.getCi());
        values.put(COLUMNA_IDRESIDENTE, persona.getIdresidente());
        values.put(COLUMNA_APELLIDOS, persona.getApellidos());
        values.put(COLUMNA_NOMBRES, persona.getNombres());
        values.put(COLUMNA_SEXO, persona.getSexo());
        values.put(COLUMNA_EDADANIO, persona.getEdadanio());
        values.put(COLUMNA_EDADMES, persona.getEdadmes());

        int result = cr.update(FormularioManuelasProvider.CONTENT_URI_PERSONA,
                values, whereById, selectionArgs);
        return result;
    }

    public static ArrayList<Persona> getPersonas(ContentResolver cr, String where, String[]  parametros, String orderBy) {
        Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_PERSONA,
                columnas, where, parametros,
                orderBy);

        ArrayList<Persona> personas = new ArrayList<Persona>();

        if (result.moveToFirst())
            do {
                personas.add(newPersona(result));
            } while (result.moveToNext());

        result.close();
        return personas;
    }
}
