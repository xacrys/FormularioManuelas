package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class Fase implements Serializable {

	//Definicion de campos
	private int id;
	private String fechaInicio;
	private String fechaFin;
	private String nombreFase;
	private String nombreOperativo;

	private int estado;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "fase";

	//Atributos de la Tabla
	public final static String COLUMNA_ID = "id";
	public final static String COLUMNA_FECHA_INICIO = "fecha_inicio";
	public final static String COLUMNA_FECHA_FIN = "fecha_fin";
	public final static String COLUMNA_NOMBRE_FASE= "nombre_fase";
	public final static String COLUMNA_NOMBRE_OPERATIVO= "nombre_operativo";
	public final static String COLUMNA_ESTADO = "estado";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_ID,
			COLUMNA_FECHA_INICIO,
			COLUMNA_FECHA_FIN,
			COLUMNA_NOMBRE_FASE,
			COLUMNA_NOMBRE_OPERATIVO,
			COLUMNA_ESTADO
	};

	//consultas
	public static String whereById = COLUMNA_ID + "= ?";
	public static String whereByIdDistinto = COLUMNA_ID + " <> ?";
	public static String whereDates =

			"( ? BETWEEN " + COLUMNA_FECHA_INICIO
					+ " AND " + COLUMNA_FECHA_FIN + ")";
	public static String whereDatesEnabled =

			"( ? BETWEEN " + COLUMNA_FECHA_INICIO
					+ " AND " + COLUMNA_FECHA_FIN + ") AND " + COLUMNA_ESTADO + "= 1";
	public static String whereFaseEnabled = COLUMNA_ESTADO + "= 1";


	@Override
	public String toString() {
		String name = "";
		if(getEstado() == 1)
		{
			name = getNombreOperativo() + " - " + getNombreFase() + " (Actual)";
		}
		else
		{
			name = getNombreOperativo() + " - " + getNombreFase();
		}

		return name;
	}

	public Fase() {
		this.setId(0);
	}


	public Fase(int id, String fechaInicio, String fechaFin, String nombreFase,
				String nombreOperativo, int estado) {
		super();
		this.setId(id);
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
		this.setNombreFase(nombreFase);
		this.setNombreOperativo(nombreOperativo);
		this.setEstado(estado);
	}

	/**
	 * Método que devuelve los valores de un registro
	 * @param vivienda
	 * @return
	 */
	public static ContentValues getValues(Fase vivienda) {
		ContentValues values = new ContentValues();
		values.put(Fase.COLUMNA_ID, vivienda.getId());
		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static Fase newVivienda(Cursor result) {
		Fase vivienda = new Fase();
		vivienda.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		return vivienda;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombreFase() {
		return nombreFase;
	}

	public void setNombreFase(String nombreFase) {
		this.nombreFase = nombreFase;
	}

	public String getNombreOperativo() {
		return nombreOperativo;
	}

	public void setNombreOperativo(String nombreOperativo) {
		this.nombreOperativo = nombreOperativo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}
