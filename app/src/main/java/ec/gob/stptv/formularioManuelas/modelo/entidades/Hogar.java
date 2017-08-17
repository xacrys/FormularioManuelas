package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class Hogar implements Serializable {

	//Definicion de campos
	private int codigo;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "hogar";

	//Atributos de la Tabla
	public final static String COLUMNA_VIV_CODIGO = "VIV_CODIGO";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_VIV_CODIGO
	};

	//consultas
	public static String whereById = COLUMNA_VIV_CODIGO + "= ?";


	public Hogar() {
		codigo = 0;
	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param vivienda
	 * @return
	 */
	public static ContentValues getValues(Hogar vivienda) {
		ContentValues values = new ContentValues();
		values.put(Hogar.COLUMNA_VIV_CODIGO, vivienda.getCodigo());
		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static Hogar newVivienda(Cursor result) {
		Hogar vivienda = new Hogar();
		vivienda.setCodigo(result.getInt(result.getColumnIndex(COLUMNA_VIV_CODIGO)));
		return vivienda;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}