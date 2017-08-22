package ec.gob.stptv.formularioManuelas.modelo.entidades;



/**
 * Created by lmorales on 21/08/17.
 */
public class Localidad {

	private String codigo;
	private String descripcion;
	private int nivel;
	private int orden;
	private String codigoPadre;
	private int estado;
	private String fechaRegistro;
	
	public final static String NOMBRE_TABLA = "localidad";

	public final static String COLUMNA_LOC_CODIGO = "codigo";
	public final static String COLUMNA_LOC_DESCRIPCION = "descripcion";
	public final static String COLUMNA_LOC_NIVEL = "nivel";
	public final static String COLUMNA_LOC_ORDEN = "orden";
	public final static String COLUMNA_LOC_CODIGOPADRE = "codigopadre";
	public final static String COLUMNA_LOC_ESTADO = "estado";
	public final static String COLUMNA_LOC_FECHAREGISTRO = "fecharegistro";
	
	
	public static final String[] COLUMNAS = new String[] { COLUMNA_LOC_CODIGO,
		COLUMNA_LOC_DESCRIPCION, COLUMNA_LOC_NIVEL, COLUMNA_LOC_ORDEN,
		COLUMNA_LOC_CODIGOPADRE, COLUMNA_LOC_ESTADO, COLUMNA_LOC_FECHAREGISTRO};
	
	public static String whereByCodigoPadre= COLUMNA_LOC_CODIGOPADRE + " LIKE ?";

	public static final String DEFAULT_SORT_ORDER = COLUMNA_LOC_DESCRIPCION + " ASC";
	
	public Localidad()
	{
		
	}
	
	public Localidad(String codigo, String descripcion, int nivel, int orden,
			String codigoPadre, int estado, String fechaRegistro) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.orden = orden;
		this.codigoPadre = codigoPadre;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		
		return this.descripcion;
	}
	
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getNivel() {
		return nivel;
	}


	public void setNivel(int nivel) {
		this.nivel = nivel;
	}


	public int getOrden() {
		return orden;
	}


	public void setOrden(int orden) {
		this.orden = orden;
	}


	public String getCodigoPadre() {
		return codigoPadre;
	}


	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}


	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public String getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
}
