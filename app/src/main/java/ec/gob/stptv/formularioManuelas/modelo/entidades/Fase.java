package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class Fase implements Serializable {

	//Definicion de campos
	private Integer id;
	private String fechainicio;
	private String fechafin;
	private String nombrefase;
	private String nombreoperativo;

	private Integer estado;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "fase";

	//Atributos de la Tabla
	public final static String COLUMNA_ID = "id";
	public final static String COLUMNA_FECHAINICIO = "fechainicio";
	public final static String COLUMNA_FECHAFIN = "fechafin";
	public final static String COLUMNA_NOMBREFASE= "nombrefase";
	public final static String COLUMNA_NOMBREOPERATIVO= "nombreoperativo";
	public final static String COLUMNA_ESTADO = "estado";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_ID,
			COLUMNA_FECHAINICIO,
			COLUMNA_FECHAFIN,
			COLUMNA_NOMBREFASE,
			COLUMNA_NOMBREOPERATIVO,
			COLUMNA_ESTADO
	};

	//consultas
	public static String whereById = COLUMNA_ID + "= ?";
	public static String whereByEstado = COLUMNA_ESTADO + "= ?";
	public static String whereByIdDistinto = COLUMNA_ID + " <> ?";
	public static String whereDates =

			"( ? BETWEEN " + COLUMNA_FECHAINICIO
					+ " AND " + COLUMNA_FECHAFIN + ")";
	public static String whereDatesEnabled =

			"( ? BETWEEN " + COLUMNA_FECHAINICIO
					+ " AND " + COLUMNA_FECHAFIN + ") AND " + COLUMNA_ESTADO + "= 1";
	public static String whereFaseEnabled = COLUMNA_ESTADO + "= 1";


	@Override
	public String toString() {
		String name;
		if(getEstado() == 1)
		{
			name = getNombrefase() + " (Actual)";
		}
		else
		{
			name = getNombrefase();
		}

		return name;
	}

	public Fase() {
		this.id = 0;
	}


	public Fase(int id, String fechaInicio, String fechaFin, String nombreFase,
				String nombreOperativo, int estado) {
		super();
		this.setId(id);
		this.setFechainicio(fechaInicio);
		this.setFechafin(fechaFin);
		this.setNombrefase(nombreFase);
		this.setNombreoperativo(nombreOperativo);
		this.setEstado(estado);
	}

	/**
	 * Método que devuelve los valores de un registro
	 * @param fase
	 * @return
	 */
	public static ContentValues getValues(Fase fase) {
		ContentValues values = new ContentValues();
		values.put(Fase.COLUMNA_ID, fase.getId());
		values.put(Fase.COLUMNA_FECHAINICIO, fase.getFechainicio());
		values.put(Fase.COLUMNA_FECHAFIN, fase.getFechafin());
		values.put(Fase.COLUMNA_NOMBREFASE, fase.getNombrefase());
		values.put(Fase.COLUMNA_NOMBREOPERATIVO, fase.getNombreoperativo());
		values.put(Fase.COLUMNA_ESTADO, fase.getEstado());

		return values;
	}

	/**
	 * Método que crea una nueva fase
	 * @param result
	 * @return
	 */
	public static Fase newFase(Cursor result) {
		Fase fase = new Fase();
		fase.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		fase.setFechainicio(result.getString(result.getColumnIndex(COLUMNA_FECHAINICIO)));
		fase.setFechafin(result.getString(result.getColumnIndex(COLUMNA_FECHAFIN)));
		fase.setNombrefase(result.getString(result.getColumnIndex(COLUMNA_NOMBREFASE)));
		fase.setNombreoperativo(result.getString(result.getColumnIndex(COLUMNA_NOMBREOPERATIVO)));
		fase.setEstado(result.getInt(result.getColumnIndex(COLUMNA_ESTADO)));

		return fase;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getFechafin() {
		return fechafin;
	}

	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public String getNombrefase() {
		return nombrefase;
	}

	public void setNombrefase(String nombrefase) {
		this.nombrefase = nombrefase;
	}

	public String getNombreoperativo() {
		return nombreoperativo;
	}

	public void setNombreoperativo(String nombreoperativo) {
		this.nombreoperativo = nombreoperativo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
