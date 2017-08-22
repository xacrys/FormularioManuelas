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
	public final static String COLUMNA_MAXVIVCODIGO = "MAXVIVCODIGO";

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


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public int getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(int codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	public int getCodigoDispositivo() {
		return codigoDispositivo;
	}

	public void setCodigoDispositivo(int codigoDispositivo) {
		this.codigoDispositivo = codigoDispositivo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getMaxVivCodigo() {
		return maxVivCodigo;
	}

	public void setMaxVivCodigo(String maxVivCodigo) {
		this.maxVivCodigo = maxVivCodigo;
	}
}
