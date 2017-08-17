package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class Usuario implements Serializable {

	//Definicion de campos
	private String usuario;
	private String password;
	private String idUsuario;
	private String nombres;
	private String apellidos;
	private String cedula;
	private int codigoGrupo;
	private int codigoDispositivo;
	private String rol;
	private int estado;
	private String imei;
	private String fechaRegistro;
	private String maxVivCodigo;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "usuario";

	//Atributos de la Tabla
	public final static String COLUMNA_VIV_CODIGO = "VIV_CODIGO";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_VIV_CODIGO
	};

	//consultas
	public static String whereById = COLUMNA_VIV_CODIGO + "= ?";


	public Usuario() {

	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param vivienda
	 * @return
	 */
	public static ContentValues getValues(Usuario vivienda) {
		ContentValues values = new ContentValues();

		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static Usuario newVivienda(Cursor result) {
		Usuario vivienda = new Usuario();

		return vivienda;
	}


}
