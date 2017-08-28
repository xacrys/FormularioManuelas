package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class Localizacion implements Serializable {

	//Definicion de campos
	private Integer id;
	private Integer  viviendaId;
	private Double  latitud;
	private Double  longitud;
	private Double  altitud;
	private Float  presicion;
	private String proveedor;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "localizacion";

	//Atributos de la Tabla
	public final static String COLUMNA_ID = "id";
	public final static String COLUMNA_IDVIVIENDA = "idvivienda";
	public final static String COLUMNA_LATITUD = "latitud";
	public final static String COLUMNA_LONGITUD = "longitud";
	public final static String COLUMNA_ALTITUD = "altitud";
	public final static String COLUMNA_PRESICION = "presicion";
	public final static String COLUMNA_PROVEEDOR = "proveedor";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_ID,
			COLUMNA_IDVIVIENDA,
			COLUMNA_LATITUD,
			COLUMNA_LONGITUD,
			COLUMNA_ALTITUD,
			COLUMNA_PRESICION,
			COLUMNA_PROVEEDOR
	};

	//consultas
	public static String whereById = COLUMNA_ID + "= ?";

	public static String whereByViviendaId = COLUMNA_IDVIVIENDA + " = ?";


	public Localizacion() {
	}

	public Localizacion(int id, int codigoViviendaId, Double latitud,
						Double longitud, Double altitud, Float presicion, String proveedor) {
		super();
		this.setId(id);
		this.setViviendaId(codigoViviendaId);
		this.setLatitud(latitud);
		this.setLongitud(longitud);
		this.setAltitud(altitud);
		this.setPresicion(presicion);
		this.setProveedor(proveedor);
	}

	/**
	 * Método que devuelve los valores de un registro
	 * @param localizacion
	 * @return
	 */
	public static ContentValues getValues(Localizacion localizacion) {
		ContentValues values = new ContentValues();

		values.put(COLUMNA_ID, localizacion.getId());
		values.put(COLUMNA_IDVIVIENDA, localizacion.getViviendaId());
		values.put(COLUMNA_LATITUD, localizacion.getLatitud());
		values.put(COLUMNA_LONGITUD, localizacion.getLongitud());
		values.put(COLUMNA_ALTITUD, localizacion.getAltitud());
		values.put(COLUMNA_PRESICION, localizacion.getPresicion());
		values.put(COLUMNA_PROVEEDOR, localizacion.getProveedor());
		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static Localizacion newLocalizacion(Cursor result) {

		Localizacion localizacion = new Localizacion();

		localizacion.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		localizacion.setViviendaId(result.getInt(result.getColumnIndex(COLUMNA_IDVIVIENDA)));
		localizacion.setLatitud(result.getDouble(result.getColumnIndex(COLUMNA_LATITUD)));
		localizacion.setLongitud(result.getDouble(result.getColumnIndex(COLUMNA_LONGITUD)));
		localizacion.setAltitud(result.getDouble(result.getColumnIndex(COLUMNA_ALTITUD)));
		localizacion.setPresicion(result.getFloat(result.getColumnIndex(COLUMNA_PRESICION)));
		localizacion.setProveedor(result.getString(result.getColumnIndex(COLUMNA_PROVEEDOR)));

		return localizacion;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViviendaId() {
		return viviendaId;
	}

	public void setViviendaId(Integer viviendaId) {
		this.viviendaId = viviendaId;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getAltitud() {
		return altitud;
	}

	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}

	public Float getPresicion() {
		return presicion;
	}

	public void setPresicion(Float presicion) {
		this.presicion = presicion;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
}
