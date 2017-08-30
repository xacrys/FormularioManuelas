package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ejemplo de entidad
 */
public class Hogar implements Serializable {

	//Definicion de campos
	private Integer id;
	private Integer idvivienda;
	private Integer idpropiedadvivienda;
	private Integer iddocumentovivienda;
	private Integer cuartos;
	private Integer dormitorio;
	private Integer idagua;
	private Integer idredagua;
	private Integer idtratamientoagua;
	private Integer idtiposshh;
	private Integer idsshh;
	private Integer idducha;
	private Integer idbasura;
	private Integer idalumbrado;
	private String planillapago;
	private Integer idtipococina;
	private Integer gascalefon;
	private Integer terreno;
	private Integer terrenoservicios;

	private String fechaactualizacion;
	private String fechacreacion;
	private boolean flag;

	private String fechainicio;
	private String fechafin;

	private ArrayList<Persona> listaPersonas = new ArrayList<>();

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "hogar";

	//Atributos de la Tabla
	public final static String COLUMNA_ID= "id";
	public final static String COLUMNA_IDVIVIENDA= "idvivienda";
	public final static String COLUMNA_IDPROPIEDADVIVIENDA= "idpropiedadvivienda";
	public final static String COLUMNA_IDDOCUMENTOVIVIENDA= "iddocumentovivienda";
	public final static String COLUMNA_CUARTOS= "cuartos";
	public final static String COLUMNA_DORMITORIO= "dormitorio";
	public final static String COLUMNA_IDAGUA= "idagua";
	public final static String COLUMNA_IDREDAGUA= "idredagua";
	public final static String COLUMNA_IDTRATAMIENTOAGUA= "idtratamientoagua";
	public final static String COLUMNA_IDTIPOSSHH= "idtiposshh";
	public final static String COLUMNA_IDSSHH= "idsshh";
	public final static String COLUMNA_IDDUCHA= "idducha";
	public final static String COLUMNA_IDBASURA= "idbasura";
	public final static String COLUMNA_IDALUMBRADO= "idalumbrado";
	public final static String COLUMNA_PLANILLAPAGO= "planillapago";
	public final static String COLUMNA_IDTIPOCOCINA= "idtipococina";
	public final static String COLUMNA_GASCALEFON= "gascalefon";
	public final static String COLUMNA_TERRENO= "terreno";
	public final static String COLUMNA_TERRENOSERVICIOS= "terrenoservicios";

	public final static String COLUMNA_FECHAACTUALIZACION= "fechaactualizacion";
	public final static String COLUMNA_FECHACREACION= "fechacreacion";

	public final static String COLUMNA_FECHAINICIO= "fechainicio";
	public final static String COLUMNA_FECHAFIN= "fechafin";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_ID,
			COLUMNA_IDVIVIENDA,
			COLUMNA_IDPROPIEDADVIVIENDA,
			COLUMNA_IDDOCUMENTOVIVIENDA,
			COLUMNA_CUARTOS,
			COLUMNA_DORMITORIO,
			COLUMNA_IDAGUA,
			COLUMNA_IDREDAGUA,
			COLUMNA_IDTRATAMIENTOAGUA,
			COLUMNA_IDTIPOSSHH,
			COLUMNA_IDSSHH,
			COLUMNA_IDDUCHA,
			COLUMNA_IDBASURA,
			COLUMNA_IDALUMBRADO,
			COLUMNA_PLANILLAPAGO,
			COLUMNA_IDTIPOCOCINA,
			COLUMNA_GASCALEFON,
			COLUMNA_TERRENO,
			COLUMNA_TERRENOSERVICIOS,

			COLUMNA_FECHAACTUALIZACION,
			COLUMNA_FECHACREACION,

			COLUMNA_FECHAINICIO,
			COLUMNA_FECHAFIN
	};

	//consultas
	public static String whereByViviendaId = COLUMNA_IDVIVIENDA + "= ?";
	public static String whereById = COLUMNA_ID + "= ?";


	public Hogar() {
		id = 0;
	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param hogar
	 * @return
	 */
	public static ContentValues getValues(Hogar hogar) {
		ContentValues values = new ContentValues();
		values.put(COLUMNA_ID, hogar.getId());
		values.put(COLUMNA_IDVIVIENDA, hogar.getIdvivienda());
		values.put(COLUMNA_IDPROPIEDADVIVIENDA, hogar.getIdpropiedadvivienda());
		values.put(COLUMNA_IDDOCUMENTOVIVIENDA, hogar.getIddocumentovivienda());
		values.put(COLUMNA_CUARTOS, hogar.getCuartos());
		values.put(COLUMNA_DORMITORIO, hogar.getDormitorio());
		values.put(COLUMNA_IDAGUA, hogar.getIdagua());
		values.put(COLUMNA_IDREDAGUA, hogar.getIdredagua());
		values.put(COLUMNA_IDTRATAMIENTOAGUA, hogar.getIdtratamientoagua());
		values.put(COLUMNA_IDTIPOSSHH, hogar.getIdtiposshh());
		values.put(COLUMNA_IDSSHH, hogar.getIdsshh());
		values.put(COLUMNA_IDDUCHA, hogar.getIdducha());
		values.put(COLUMNA_IDBASURA, hogar.getIdbasura());
		values.put(COLUMNA_IDALUMBRADO, hogar.getIdalumbrado());
		values.put(COLUMNA_PLANILLAPAGO, hogar.getPlanillapago());
		values.put(COLUMNA_IDTIPOCOCINA, hogar.getIdtipococina());
		values.put(COLUMNA_GASCALEFON, hogar.getGascalefon());
		values.put(COLUMNA_TERRENO, hogar.getTerreno());
		values.put(COLUMNA_TERRENOSERVICIOS, hogar.getTerrenoservicios());
		values.put(COLUMNA_FECHAACTUALIZACION, hogar.getFechaactualizacion());
		values.put(COLUMNA_FECHACREACION, hogar.getFechacreacion());
		values.put(COLUMNA_FECHAINICIO, hogar.getFechainicio());
		values.put(COLUMNA_FECHAFIN, hogar.getFechafin());

		return values;
	}

	/**
	 * Método que crea una nueva vivienda
	 * @param result
	 * @return
	 */
	public static Hogar newHogar(Cursor result) {
		Hogar vivienda = new Hogar();
		vivienda.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		vivienda.setIdvivienda(result.getInt(result.getColumnIndex(COLUMNA_IDVIVIENDA)));
		vivienda.setIdpropiedadvivienda(result.getInt(result.getColumnIndex(COLUMNA_IDPROPIEDADVIVIENDA)));
		vivienda.setIddocumentovivienda(result.getInt(result.getColumnIndex(COLUMNA_IDDOCUMENTOVIVIENDA)));
		vivienda.setCuartos(result.getInt(result.getColumnIndex(COLUMNA_CUARTOS)));
		vivienda.setDormitorio(result.getInt(result.getColumnIndex(COLUMNA_DORMITORIO)));
		vivienda.setIdagua(result.getInt(result.getColumnIndex(COLUMNA_IDAGUA)));
		vivienda.setIdredagua(result.getInt(result.getColumnIndex(COLUMNA_IDREDAGUA)));
		vivienda.setIdtratamientoagua(result.getInt(result.getColumnIndex(COLUMNA_IDTRATAMIENTOAGUA)));
		vivienda.setIdtiposshh(result.getInt(result.getColumnIndex(COLUMNA_IDTIPOSSHH)));
		vivienda.setIdsshh(result.getInt(result.getColumnIndex(COLUMNA_IDSSHH)));
		vivienda.setIdducha(result.getInt(result.getColumnIndex(COLUMNA_IDDUCHA)));
		vivienda.setIdbasura(result.getInt(result.getColumnIndex(COLUMNA_IDBASURA)));
		vivienda.setIdalumbrado(result.getInt(result.getColumnIndex(COLUMNA_IDALUMBRADO)));
		vivienda.setPlanillapago(result.getString(result.getColumnIndex(COLUMNA_PLANILLAPAGO)));
		vivienda.setIdtipococina(result.getInt(result.getColumnIndex(COLUMNA_IDTIPOCOCINA)));
		vivienda.setGascalefon(result.getInt(result.getColumnIndex(COLUMNA_GASCALEFON)));
		vivienda.setTerreno(result.getInt(result.getColumnIndex(COLUMNA_TERRENO)));
		vivienda.setTerrenoservicios(result.getInt(result.getColumnIndex(COLUMNA_TERRENOSERVICIOS)));
		vivienda.setFechaactualizacion(result.getString(result.getColumnIndex(COLUMNA_FECHAACTUALIZACION)));
		vivienda.setFechacreacion(result.getString(result.getColumnIndex(COLUMNA_FECHACREACION)));
		vivienda.setFechainicio(result.getString(result.getColumnIndex(COLUMNA_FECHAINICIO)));
		vivienda.setFechafin(result.getString(result.getColumnIndex(COLUMNA_FECHAFIN)));

		return vivienda;
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

	public Integer getIdpropiedadvivienda() {
		return idpropiedadvivienda;
	}

	public void setIdpropiedadvivienda(Integer idpropiedadvivienda) {
		this.idpropiedadvivienda = idpropiedadvivienda;
	}

	public Integer getIddocumentovivienda() {
		return iddocumentovivienda;
	}

	public void setIddocumentovivienda(Integer iddocumentovivienda) {
		this.iddocumentovivienda = iddocumentovivienda;
	}

	public Integer getCuartos() {
		return cuartos;
	}

	public void setCuartos(Integer cuartos) {
		this.cuartos = cuartos;
	}

	public Integer getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(Integer dormitorio) {
		this.dormitorio = dormitorio;
	}

	public Integer getIdagua() {
		return idagua;
	}

	public void setIdagua(Integer idagua) {
		this.idagua = idagua;
	}

	public Integer getIdredagua() {
		return idredagua;
	}

	public void setIdredagua(Integer idredagua) {
		this.idredagua = idredagua;
	}

	public Integer getIdtratamientoagua() {
		return idtratamientoagua;
	}

	public void setIdtratamientoagua(Integer idtratamientoagua) {
		this.idtratamientoagua = idtratamientoagua;
	}

	public Integer getIdtiposshh() {
		return idtiposshh;
	}

	public void setIdtiposshh(Integer idtiposshh) {
		this.idtiposshh = idtiposshh;
	}

	public Integer getIdsshh() {
		return idsshh;
	}

	public void setIdsshh(Integer idsshh) {
		this.idsshh = idsshh;
	}

	public Integer getIdducha() {
		return idducha;
	}

	public void setIdducha(Integer idducha) {
		this.idducha = idducha;
	}

	public Integer getIdbasura() {
		return idbasura;
	}

	public void setIdbasura(Integer idbasura) {
		this.idbasura = idbasura;
	}

	public Integer getIdalumbrado() {
		return idalumbrado;
	}

	public void setIdalumbrado(Integer idalumbrado) {
		this.idalumbrado = idalumbrado;
	}

	public String getPlanillapago() {
		return planillapago;
	}

	public void setPlanillapago(String planillapago) {
		this.planillapago = planillapago;
	}

	public Integer getIdtipococina() {
		return idtipococina;
	}

	public void setIdtipococina(Integer idtipococina) {
		this.idtipococina = idtipococina;
	}

	public Integer getGascalefon() {
		return gascalefon;
	}

	public void setGascalefon(Integer gascalefon) {
		this.gascalefon = gascalefon;
	}

	public Integer getTerreno() {
		return terreno;
	}

	public void setTerreno(Integer terreno) {
		this.terreno = terreno;
	}

	public Integer getTerrenoservicios() {
		return terrenoservicios;
	}

	public void setTerrenoservicios(Integer terrenoservicios) {
		this.terrenoservicios = terrenoservicios;
	}

	public String getFechaactualizacion() {
		return fechaactualizacion;
	}

	public void setFechaactualizacion(String fechaactualizacion) {
		this.fechaactualizacion = fechaactualizacion;
	}

	public String getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(String fechacreacion) {
		this.fechacreacion = fechacreacion;
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
}
