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
	private String idusuario;
	private String nombres;
	private String apellidos;
	private String cedula;
	private Integer iddispositivo;
	private String imei;
	private String maxvivcodigo;
	private String fecharegistro;
	private Integer codigo;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "usuario";

	//Atributos de la Tabla
	public final static String COLUMNA_USUARIO = "usuario";
	public final static String COLUMNA_PASSWORD = "password";
	public final static String COLUMNA_IDUSUARIO = "idusuario";
	public final static String COLUMNA_NOMBRES = "nombres";
	public final static String COLUMNA_APELLIDOS = "apellidos";
	public final static String COLUMNA_CEDULA = "cedula";
	public final static String COLUMNA_IDDISPOSITIVO = "iddispositivo";
	public final static String COLUMNA_IMEI = "imei";
	public final static String COLUMNA_MAXVIVCODIGO = "maxvivcodigo";
	public final static String COLUMNA_FECHAREGISTRO = "fechaingreso";
	public final static String COLUMNA_CODIGO = "codigo;";


	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_USUARIO,
			COLUMNA_PASSWORD,
			COLUMNA_IDUSUARIO,
			COLUMNA_NOMBRES,
			COLUMNA_APELLIDOS,
			COLUMNA_CEDULA,
			COLUMNA_IDDISPOSITIVO,
			COLUMNA_IMEI,
			COLUMNA_MAXVIVCODIGO,
			COLUMNA_FECHAREGISTRO,
			COLUMNA_CODIGO
	};

	//consultas
	public static String whereByUsuarioYPassword = COLUMNA_USUARIO + " LIKE ? AND " + COLUMNA_PASSWORD + " LIKE ?";

	public static String whereById = COLUMNA_USUARIO + " LIKE ?";

	public Usuario() {

	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param usuario
	 * @return
	 */
	public static ContentValues getValues(Usuario usuario) {
		ContentValues values = new ContentValues();
		values.put(COLUMNA_USUARIO, usuario.getUsuario());
		values.put(COLUMNA_PASSWORD, usuario.getPassword());
		values.put(COLUMNA_IDUSUARIO, usuario.getIdusuario());
		values.put(COLUMNA_NOMBRES, usuario.getNombres());
		values.put(COLUMNA_APELLIDOS, usuario.getApellidos());
		values.put(COLUMNA_CEDULA, usuario.getCedula());
		values.put(COLUMNA_IDDISPOSITIVO, usuario.getIddispositivo());
		values.put(COLUMNA_IMEI, usuario.getImei());
		values.put(COLUMNA_MAXVIVCODIGO, usuario.getMaxvivcodigo());
		values.put(COLUMNA_FECHAREGISTRO, usuario.getFecharegistro());
		values.put(COLUMNA_CODIGO, usuario.getCodigo());
		return values;
	}

	/**
	 * Método que crea un nuevo usuario
	 * @param result
	 * @return
	 */
	public static Usuario newUsuario(Cursor result) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(result.getString(result.getColumnIndex(COLUMNA_USUARIO)));
		usuario.setPassword(result.getString(result.getColumnIndex(COLUMNA_PASSWORD)));
		usuario.setIdusuario(result.getString(result.getColumnIndex(COLUMNA_IDUSUARIO)));
		usuario.setNombres(result.getString(result.getColumnIndex(COLUMNA_NOMBRES)));
		usuario.setApellidos(result.getString(result.getColumnIndex(COLUMNA_APELLIDOS)));
		usuario.setCedula(result.getString(result.getColumnIndex(COLUMNA_CEDULA)));
		usuario.setIddispositivo(result.getInt(result.getColumnIndex(COLUMNA_IDDISPOSITIVO)));
		usuario.setImei(result.getString(result.getColumnIndex(COLUMNA_IMEI)));
		usuario.setMaxvivcodigo(result.getString(result.getColumnIndex(COLUMNA_MAXVIVCODIGO)));
		usuario.setFecharegistro(result.getString(result.getColumnIndex(COLUMNA_FECHAREGISTRO)));
		usuario.setCodigo(result.getInt(result.getColumnIndex(COLUMNA_CODIGO)));
		return usuario;
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

	public String getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
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

	public Integer getIddispositivo() {
		return iddispositivo;
	}

	public void setIddispositivo(Integer iddispositivo) {
		this.iddispositivo = iddispositivo;
	}

	public String getMaxvivcodigo() {
		return maxvivcodigo;
	}

	public void setMaxvivcodigo(String maxvivcodigo) {
		this.maxvivcodigo = maxvivcodigo;
	}

	public String getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(String fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
