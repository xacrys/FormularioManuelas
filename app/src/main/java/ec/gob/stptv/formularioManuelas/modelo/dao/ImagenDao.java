package ec.gob.stptv.formularioManuelas.modelo.dao;

import java.util.ArrayList;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Imagen;
import ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelasProvider;

/**
 * Clase para la gestión de imagenes.
 *
 *
 */
public class ImagenDao extends Imagen {

	public static Uri save(ContentResolver cr, Imagen imagen) {
		ContentValues values = getValues(imagen);
		values.remove(COLUMNA_ID);
		return cr.insert(FormularioManuelasProvider.CONTENT_URI_IMAGEN, values);
	}

	public static int update(ContentResolver cr, Imagen imagen) {
		ContentValues values = getValues(imagen);
		values.remove(COLUMNA_ID);
		return cr.update(FormularioManuelasProvider.CONTENT_URI_IMAGEN, values, whereById, new String[] { String.valueOf(imagen.getId()) });
	}

	public static int delete(ContentResolver cr, Imagen imagen) {
		int filaEliminada;
		filaEliminada = cr.delete(FormularioManuelasProvider.CONTENT_URI_IMAGEN,
				whereByVivcodigoAndTipo,
				new String[] { String.valueOf(imagen.getVivcodigo()), String.valueOf(imagen.getTipo()) });

		return filaEliminada;
	}

	public static ArrayList<Imagen> getImagenes(ContentResolver cr, String where, String[]  parametros, String orderBy) {
		Cursor result = cr.query(FormularioManuelasProvider.CONTENT_URI_IMAGEN,
				columnas, where, parametros,
				orderBy);

		ArrayList<Imagen> imagenes = new ArrayList<>();

		if (result!= null){
			if (result.moveToFirst())
				do {
					imagenes.add(newImagen(result));
				}
				while (result.moveToNext());
			result.close();
		}
		return imagenes;
	}

	public static Imagen getImagen(ContentResolver cr, String where, String[] id) {

		Cursor result = cr.query(
				FormularioManuelasProvider.CONTENT_URI_IMAGEN,
				columnas, where, id, null);
		Imagen imagen = null;
		if (result != null) {
			if ((result.getCount() == 0) || !result.moveToFirst()) {
				imagen = null;

			} else {
				if (result.moveToFirst()) {
					imagen = newImagen(result);
				}
			}
			result.close();
		}
		return imagen;
	}

}
