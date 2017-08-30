package ec.gob.stptv.formularioManuelas.modelo.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Localizacion;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 Created by lmorales on 29/08/17.
 */
public class LocalizacionDao extends Localizacion {

	private static final long serialVersionUID = -6371875472049586923L;

	public static Uri save(ContentResolver cr, Localizacion localizacion) {

		ContentValues values = getValues(localizacion);
		values.remove(COLUMNA_ID);
		Uri id = cr.insert(FormularioManuelasProvider.CONTENT_URI_LOCALIZACION, values);
		return id;
	}

	public static int update(ContentResolver cr, Localizacion localizacion) {
		
		ContentValues values = getValues(localizacion);
		values.remove(COLUMNA_ID);
		int result = cr.update(FormularioManuelasProvider.CONTENT_URI_LOCALIZACION, values, whereById, new String[] { String.valueOf(localizacion.getId()) });
		return result;
	}
	

	public static ArrayList<Localizacion> getLocalizaciones(ContentResolver cr, String where, String[]  parametros, String orderBy) {
				
			Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_LOCALIZACION,
					Localizacion.columnas, where, parametros,
					orderBy);
			
			ArrayList<Localizacion> localizaciones = new ArrayList<Localizacion>();

			if (result.moveToFirst())
				do {
					localizaciones.add(newLocalizacion(result));
				} while (result.moveToNext());
			
			result.close();
			return localizaciones;
	}
	
	public static Localizacion getLocalizacion(ContentResolver cr, String[] id, String where) {
		
		Cursor result = cr.query(
				FormularioManuelasProvider.CONTENT_URI_LOCALIZACION,
				Localizacion.columnas, where, id, null);
		
		Localizacion localizacion = null;

		if ((result.getCount() == 0) || !result.moveToFirst()) {
			localizacion = null;

		} else {
			if (result.moveToFirst()) {
				
				localizacion = newLocalizacion(result);
			
				}
			} 
		
		result.close();
		return localizacion;
	}
}
