package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Clase Imagen.
 *
 */
public class Imagen implements Serializable{

	private Integer id;
	private Integer idvivienda;
	private Integer codigoequipo;
	private Integer tipo;
	private String imagen;
	private String fecha;
	private Integer estadosincronizacion;
	private String fechasincronizacion;
	
	private String mensaje;
	private String codigoFormulario;
	


	public final static String NOMBRE_TABLA = "imagenes";

	public final static String COLUMNA_ID = "id";
	public final static String COLUMNA_IDVIVIENDA = "idvivienda";
	public final static String COLUMNA_CODIGOEQUIPO = "codigoequipo";
	public final static String COLUMNA_TIPO = "tipo";
	public final static String COLUMNA_IMAGEN = "imagen";
	public final static String COLUMNA_FECHA = "fecha";
	public final static String COLUMNA_ESTADOSINCRONIZACION = "estado_sincronizacion";
	public final static String COLUMNA_FECHASINCRONIZACION = "fecha_sincronizacion";

	public static final String[] columnas_imagen = new String[] { 
		NOMBRE_TABLA + "." + COLUMNA_ID, 
		NOMBRE_TABLA + "." + COLUMNA_IDVIVIENDA,
		NOMBRE_TABLA + "." + COLUMNA_CODIGOEQUIPO,
		NOMBRE_TABLA + "." + COLUMNA_TIPO, 
		NOMBRE_TABLA + "." + COLUMNA_IMAGEN, 
		NOMBRE_TABLA + "." + COLUMNA_FECHA,
		NOMBRE_TABLA + "." + COLUMNA_ESTADOSINCRONIZACION,
		NOMBRE_TABLA + "." + COLUMNA_FECHASINCRONIZACION };
	
	public static final String[] columnas = new String[] { 
		COLUMNA_ID,
			COLUMNA_IDVIVIENDA,
		COLUMNA_CODIGOEQUIPO,
		COLUMNA_TIPO, 
		COLUMNA_IMAGEN, 
		COLUMNA_FECHA,
		COLUMNA_ESTADOSINCRONIZACION,
		COLUMNA_FECHASINCRONIZACION };
	
	public static final String[] columnasDistinct = new String[] { COLUMNA_IDVIVIENDA,
		COLUMNA_TIPO, COLUMNA_IMAGEN, COLUMNA_FECHA,
		COLUMNA_ESTADOSINCRONIZACION, COLUMNA_FECHASINCRONIZACION };

	/*
	 * Consultas
	 */
	public static String whereById = COLUMNA_ID + "= ?";
	public static String whereByViviendaId = COLUMNA_IDVIVIENDA + "= ?";
	
	public static String whereByViviendaIdAndTipo = COLUMNA_IDVIVIENDA
			+ "= ? and " + COLUMNA_TIPO + "= ?";
	
	public static String whereByEstadoSincronizacion =  COLUMNA_ESTADOSINCRONIZACION + "  = ?";
	
	public static String whereByFechasEstadoSincronizacion = "(" + COLUMNA_FECHA + " BETWEEN ? AND ?) AND " + NOMBRE_TABLA + "." + COLUMNA_ESTADOSINCRONIZACION + " = ?";
	
	public Imagen() {
		setId(0);
	}


	/**
	 * Método que devuelve los valores de un registro
	 * @param imagen
	 * @return
	 */
	public static ContentValues getValues(Imagen imagen) {
		ContentValues values = new ContentValues();
		values.put(COLUMNA_ID, imagen.getId());
		values.put(COLUMNA_IDVIVIENDA, imagen.getIdvivienda());
		values.put(COLUMNA_CODIGOEQUIPO, imagen.getCodigoequipo());
		values.put(COLUMNA_TIPO, imagen.getTipo());
		values.put(COLUMNA_IMAGEN, imagen.getImagen());
		values.put(COLUMNA_FECHA, imagen.getFecha());
		values.put(COLUMNA_ESTADOSINCRONIZACION, imagen.getEstadosincronizacion());
		values.put(COLUMNA_FECHASINCRONIZACION, imagen.getFechasincronizacion());
		return values;
	}

	/**
	 * Método que crea una nueva imagen
	 * @param result
	 * @return
	 */
	public static Imagen newImagen(Cursor result) {
		Imagen imagen = new Imagen();
		imagen.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		imagen.setIdvivienda(result.getInt(result.getColumnIndex(COLUMNA_IDVIVIENDA)));
		imagen.setCodigoequipo(result.getInt(result.getColumnIndex(COLUMNA_CODIGOEQUIPO)));
		imagen.setTipo(result.getInt(result.getColumnIndex(COLUMNA_TIPO)));
		imagen.setImagen(result.getString(result.getColumnIndex(COLUMNA_IMAGEN)));
		imagen.setFecha(result.getString(result.getColumnIndex(COLUMNA_FECHA)));
		imagen.setEstadosincronizacion(result.getInt(result.getColumnIndex(COLUMNA_ESTADOSINCRONIZACION)));
		imagen.setFechasincronizacion(result.getString(result.getColumnIndex(COLUMNA_FECHASINCRONIZACION)));

		return imagen;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdvivienda() {
		return idvivienda;
	}

	public void setIdvivienda(Integer idvivienda) {
		this.idvivienda = idvivienda;
	}

	public Integer getCodigoequipo() {
		return codigoequipo;
	}

	public void setCodigoequipo(Integer codigoequipo) {
		this.codigoequipo = codigoequipo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getEstadosincronizacion() {
		return estadosincronizacion;
	}

	public void setEstadosincronizacion(Integer estadosincronizacion) {
		this.estadosincronizacion = estadosincronizacion;
	}

	public String getFechasincronizacion() {
		return fechasincronizacion;
	}

	public void setFechasincronizacion(String fechasincronizacion) {
		this.fechasincronizacion = fechasincronizacion;
	}
}
