package ec.gob.stptv.formularioManuelas.modelo.dao;

import java.util.ArrayList;

import android.database.Cursor;

import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localidad;

/**
 * Created by lmorales on 21/08/17.
 */
public class LocalidadDao extends Localidad {


	public static ArrayList<Values> getLocalidades(Cursor result) {
		
		ArrayList<Values> localidades = new ArrayList<Values>();
		localidades.add(new Values(String.valueOf(Global.VALOR_SELECCIONE_DPA), "Seleccione.."));

		if (result.moveToFirst())
			do {
				
				Values localidad = new Values();
				localidad.setKey(result.getString(result.getColumnIndex(COLUMNA_LOC_CODIGO)));
				localidad.setValue(result.getString(result.getColumnIndex(COLUMNA_LOC_DESCRIPCION)));
				localidades.add(localidad);
			} while (result.moveToNext());
		
		result.close();
		return localidades;
	}
}
