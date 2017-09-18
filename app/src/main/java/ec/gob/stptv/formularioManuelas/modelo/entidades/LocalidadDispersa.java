package ec.gob.stptv.formularioManuelas.modelo.entidades;


/**
 * Localidad dispersas
 */
public class LocalidadDispersa {

	private String idDPA;
	private String idLocalidad;
	private String descripcion;
		
	
	public final static String NOMBRE_TABLA = "localidadesdispersas";

	//public final static String COLUMNA_ID_ = "ID";
	public final static String COLUMNA_IDDPA = "iddpa";
	public final static String COLUMNA_IDLOCALIDAD = "idlocalidad";
	public final static String COLUMNA_DESCRIPCION = "descripcion";
	
	public static final String[] columnas = new String[] { COLUMNA_IDLOCALIDAD, COLUMNA_DESCRIPCION };
	
	public static String whereByIDDPA = COLUMNA_IDDPA + " = ? ";

	
	public LocalidadDispersa() {
		
	}

	public LocalidadDispersa(Integer id, String idDPA, String idLocalidad,
                             String descripcion) {
		super();
		this.idDPA = idDPA;
		this.idLocalidad = idLocalidad;
		this.descripcion = descripcion;
	}

	public String getIdDPA() {
		return idDPA;
	}

	public void setIdDPA(String idDPA) {
		this.idDPA = idDPA;
	}

	public String getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(String idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}

