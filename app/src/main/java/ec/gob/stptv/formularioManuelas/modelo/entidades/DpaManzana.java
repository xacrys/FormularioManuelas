package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Ejemplo de entidad
 */
public class DpaManzana implements Serializable {

	//Definicion de campos
	private Integer id;
	private String idprovincia;
	private String idcanton;
	private String idparroquia;
	private String zona;
	private String sector;
	private String manzana;
	private String provincia;
	private String canton;
	private String parroquia;
	private Integer totalhogar;
	private Integer anio;
	private String dpacompuesta;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "dpamanzana";

	//Atributos de la Tabla
	public final static String COLUMNA_ID = "id";
	public final static String COLUMNA_IDPROVINCIA = "idprovincia";
	public final static String COLUMNA_IDCANTON = "idcanton";
	public final static String COLUMNA_IDPARROQUIA = "idparroquia";
	public final static String COLUMNA_ZONA = "zona";
	public final static String COLUMNA_SECTOR = "sector";
	public final static String COLUMNA_MANZANA = "manzana";
	public final static String COLUMNA_PROVINCIA = "provincia";
	public final static String COLUMNA_CANTON = "canton";
	public final static String COLUMNA_PARROQUIA = "parroquia";
	public final static String COLUMNA_TOTALHOGAR = "totalhogar";
	public final static String COLUMNA_ANIO = "anio";
	public final static String COLUMNA_DPACOMPUESTA = "dpacompuesta";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[]{
			COLUMNA_ID,
			COLUMNA_IDPROVINCIA,
			COLUMNA_IDCANTON,
			COLUMNA_IDPARROQUIA,
			COLUMNA_ZONA,
			COLUMNA_SECTOR,
			COLUMNA_MANZANA,
			COLUMNA_PROVINCIA,
			COLUMNA_CANTON,
			COLUMNA_PARROQUIA,
			COLUMNA_TOTALHOGAR,
			COLUMNA_ANIO,
			COLUMNA_DPACOMPUESTA
	};

	//consultas
	/**
	 * Cadena para obtener las zonas
	 */
	public static String whereById = COLUMNA_ID + "= ?";
	public static String whereByProvinciaCantonParroquia = COLUMNA_IDPROVINCIA
			+ " LIKE ? AND "
			+ COLUMNA_IDCANTON
			+ " LIKE ? AND "
			+ COLUMNA_IDPARROQUIA + " LIKE ?";

	/**
	 * Cadena para traer los sectores
	 */

	public static String whereByProvinciaCantonParroquiaZona = COLUMNA_IDPROVINCIA
			+ " LIKE ? AND "
			+ COLUMNA_IDCANTON
			+ " LIKE ? AND "
			+ COLUMNA_IDPARROQUIA
			+ " LIKE ? AND "
			+ COLUMNA_ZONA
			+ " LIKE ?";

	/**
	 * Cadena para traer las manzanas
	 */

	public static String whereByProvinciaCantonParroquiaZonaSector = COLUMNA_IDPROVINCIA
			+ " LIKE ? AND "
			+ COLUMNA_IDCANTON
			+ " LIKE ? AND "
			+ COLUMNA_IDPARROQUIA
			+ " LIKE ? AND "
			+ COLUMNA_ZONA
			+ " LIKE ? AND "
			+ COLUMNA_SECTOR
			+ " LIKE ?";


	public DpaManzana() {
		setId(0);
	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param vivienda
	 * @return
	 */
	public static ContentValues getValues(DpaManzana vivienda) {
		ContentValues values = new ContentValues();
		values.put(DpaManzana.COLUMNA_ID, vivienda.getId());
		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static DpaManzana newVivienda(Cursor result) {
		DpaManzana vivienda = new DpaManzana();
		vivienda.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		return vivienda;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getIdcanton() {
		return idcanton;
	}

	public void setIdcanton(String idcanton) {
		this.idcanton = idcanton;
	}

	public String getIdparroquia() {
		return idparroquia;
	}

	public void setIdparroquia(String idparroquia) {
		this.idparroquia = idparroquia;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getManzana() {
		return manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getParroquia() {
		return parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public Integer getTotalhogar() {
		return totalhogar;
	}

	public void setTotalhogar(Integer totalhogar) {
		this.totalhogar = totalhogar;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getDpacompuesta() {
		return dpacompuesta;
	}

	public void setDpacompuesta(String dpacompuesta) {
		this.dpacompuesta = dpacompuesta;
	}
}
