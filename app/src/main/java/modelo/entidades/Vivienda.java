package modelo.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Vivienda implements Parcelable {

	/**
	 * id                   bigint  NOT NULL  ,
	 idtipolevantamiento  bigint  NOT NULL  ,
	 idfase               bigint  NOT NULL  ,
	 idarea               bigint  NOT NULL  ,
	 codigodpa            varchar(6)  NOT NULL  ,
	 aniodpa              smallint  NOT NULL  ,
	 manzana              smallint  NOT NULL  ,
	 localidad            varchar(500)  NOT NULL  ,
	 edificio             varchar(3)  NOT NULL  ,
	 vivienda             varchar(4)  NOT NULL  ,
	 totalhogar           smallint  NOT NULL  ,
	 calle1               varchar(500)  NOT NULL  ,
	 calle2               varchar(500)  NOT NULL  ,
	 conjuntohabitacional varchar(500)  NOT NULL  ,
	 numerocasa           varchar(50)  NOT NULL  ,
	 lote                 smallint  NOT NULL  ,
	 departamento         smallint  NOT NULL  ,
	 piso                 smallint  NOT NULL  ,
	 telefonoconvencional varchar(20)  NOT NULL  ,
	 telefonocelular      varchar(20)  NOT NULL  ,
	 referenciaubicacion  varchar(1000)  NOT NULL  ,
	 idocupada            bigint  NOT NULL  ,
	 idtipovivienda       bigint  NOT NULL  ,
	 idviacceso           bigint  NOT NULL  ,
	 idmaterialtecho      bigint  NOT NULL  ,
	 idmaterialpiso       bigint  NOT NULL  ,
	 idmaterialpared      bigint  NOT NULL  ,
	 idestadovivienda_techo bigint  NOT NULL  ,
	 idestadovivienda_piso bigint  NOT NULL  ,
	 idestadovivienda_pared bigint  NOT NULL  ,
	 formulario           bigint  NOT NULL  ,
	 fechaactualizacion   date  NOT NULL  ,
	 fechacreacion        date  NOT NULL  ,
	 numerovisitas        smallint  NOT NULL  ,
	 idcontrolentrevista  bigint  NOT NULL  ,
	 flag                 boolean  NOT NULL
	 */
	private int codigo;
	private String usuUsuario;
	private String codigoFormulario;
	private int codigoUnicoEquipo;
	private String identificadorEquipo;
	private int fase;
	private int tipoLevantamientoBarrido;
	private String codigoProvincia;
	private String codigoCanton;
	private String codigoParroquia;
	private String codigoParroquiaUrbana;
	private String codigoZona;
	private String codigoSector;
	private String codigoBarrioComunidad;
	private int codigoManzana;
	private String codigoDivision;
	private Integer codigoEdificio;
	private Integer codigoVivienda;
	private Integer codigoHogarInicial;
	private Integer codigoHogarFinal;
	private String latitud;
	private String longitud;
	private String calle1;
	private String numero;
	private String calle2;
	private String nombreConjuntoEdificio;
	private String lote;
	private String departamento;
	private int piso;
	private String telefonoLocal;
	private String telefonoCelular;
	private String referenciaUbicacion;
	private int condicionOcupacion = -1;
	private int controlEntrevista = -1;
	private String observacion = "-1";
	private String fechaRegistro;
	
	private int visitas;
	private String sticker;
	
	private String fechaInicio;
	private String fechaFin;
	private int estadoSincronizacion;
	private String fechaSincronizacion;
	
	private int isValidate = -1;

	/*
	 * CREATE TABLE [RS_SECCION_VIVIENDA_CONTROL] ([VIV_CODIGO] text PRIMARY KEY
	 * NOT NULL, [USU_USUARIO] text, [VIV_CODIGOFORMULARIO] text NOT NULL,
	 * [VIV_CODIGOUNICOEQUIPO] integer, [VIV_IDENTIFICADOREQUIPO] text,
	 * [VIV_FASE] integer, [VIV_TIPOLEVANTAMIENTOBARRIDO] integer,
	 * [VIV_CODIGOPROVINCIA] text, [VIV_CODIGOCANTON] text,
	 * [VIV_CODIGOPARROQUIA] text, [VIV_CODIGOPARROQUIAURBANA] text,
	 * [VIV_CODIGOZONA] text, [VIV_CODIGOSECTOR] text,
	 * [VIV_CODIGOBARRIOCOMUNIDAD] text, [VIV_CODIGOMANZANA] integer,
	 * [VIV_CODIGODIVISION] integer, [VIV_CODIGOEDIFICIO] integer,
	 * [VIV_CODIGOVIVIENDA] integer, [VIV_CODIGOHOGARINICIAL] integer,
	 * [VIV_CODIGOHOGARFINAL] integer, [VIV_LATITUD] text, [VIV_LONGITUD] text,
	 * [VIV_CALLE1] text, [VIV_NUMERO] text, [VIV_CALLE2] text,
	 * [VIV_NOMBRECONJUNTOEDIFICIO] text, [VIV_LOTE] integer, [VIV_DEPARTAMENTO]
	 * text, [VIV_PISO] integer, [VIV_TELEFONOLOCAL] text, [VIV_TELEFONOCELULAR]
	 * text, [VIV_REFERENCIAUBICACION] text, [VIV_CONDICIONOCUPACION] integer,
	 * [VIV_CONTROLENTREVISTA] integer, [VIV_OBSERVACION] TEXT,
	 * [VIV_FECHAINICIO] datetime NOT NULL, [VIV_FECHAFIN] datetime,
	 * [VIV_ESTADOSINCRONIZACION] integer NOT NULL, [VIV_FECHASINCRONIZACION]
	 * datetime NOT NULL)
	 */

	/*
	 * Atributos de la Tabla
	 */
	public final static String NOMBRE_TABLA = "RS_SECCION_VIVIENDA_CONTROL";

	public final static String COLUMNA_VIV_CODIGO = "VIV_CODIGO";
	public final static String COLUMNA_USU_USUARIO = "USU_USUARIO";
	public final static String COLUMNA_VIV_CODIGOFORMULARIO = "VIV_CODIGOFORMULARIO";
	public final static String COLUMNA_VIV_CODIGOUNICOEQUIPO = "VIV_CODIGOUNICOEQUIPO";
	public final static String COLUMNA_VIV_IDENTIFICADOREQUIPO = "VIV_IDENTIFICADOREQUIPO";
	public final static String COLUMNA_VIV_FASE = "VIV_FASE";
	public final static String COLUMNA_VIV_TIPOLEVANTAMIENTOBARRIDO = "VIV_TIPOLEVANTAMIENTOBARRIDO";
	public final static String COLUMNA_VIV_CODIGOPROVINCIA = "VIV_CODIGOPROVINCIA";
	public final static String COLUMNA_VIV_CODIGOCANTON = "VIV_CODIGOCANTON";
	public final static String COLUMNA_VIV_CODIGOPARROQUIA = "VIV_CODIGOPARROQUIA";
	public final static String COLUMNA_VIV_CODIGOPARROQUIAURBANA = "VIV_CODIGOPARROQUIAURBANA";
	public final static String COLUMNA_VIV_CODIGOZONA = "VIV_CODIGOZONA";
	public final static String COLUMNA_VIV_CODIGOSECTOR = "VIV_CODIGOSECTOR";
	public final static String COLUMNA_VIV_CODIGOBARRIOCOMUNIDAD = "VIV_CODIGOBARRIOCOMUNIDAD";
	public final static String COLUMNA_VIV_CODIGOMANZANA = "VIV_CODIGOMANZANA";
	public final static String COLUMNA_VIV_CODIGODIVISION = "VIV_CODIGODIVISION";
	public final static String COLUMNA_VIV_CODIGOEDIFICIO = "VIV_CODIGOEDIFICIO";
	public final static String COLUMNA_VIV_CODIGOVIVIENDA = "VIV_CODIGOVIVIENDA";
	public final static String COLUMNA_VIV_CODIGOHOGARINICIAL = "VIV_CODIGOHOGARINICIAL";
	public final static String COLUMNA_VIV_CODIGOHOGARFINAL = "VIV_CODIGOHOGARFINAL";
	public final static String COLUMNA_VIV_LATITUD = "VIV_LATITUD";
	public final static String COLUMNA_VIV_LONGITUD = "VIV_LONGITUD";
	public final static String COLUMNA_VIV_CALLE1 = "VIV_CALLE1";
	public final static String COLUMNA_VIV_NUMERO = "VIV_NUMERO";
	public final static String COLUMNA_VIV_CALLE2 = "VIV_CALLE2";
	public final static String COLUMNA_VIV_NOMBRECONJUNTOEDIFICIO = "VIV_NOMBRECONJUNTOEDIFICIO";
	public final static String COLUMNA_VIV_LOTE = "VIV_LOTE";
	public final static String COLUMNA_VIV_DEPARTAMENTO = "VIV_DEPARTAMENTO";
	public final static String COLUMNA_VIV_PISO = "VIV_PISO";
	public final static String COLUMNA_VIV_TELEFONOLOCAL = "VIV_TELEFONOLOCAL";
	public final static String COLUMNA_VIV_TELEFONOCELULAR = "VIV_TELEFONOCELULAR";
	public final static String COLUMNA_VIV_REFERENCIAUBICACION = "VIV_REFERENCIAUBICACION";
	public final static String COLUMNA_VIV_CONDICIONOCUPACION = "VIV_CONDICIONOCUPACION";
	public final static String COLUMNA_VIV_CONTROLENTREVISTA = "VIV_CONTROLENTREVISTA";
	public final static String COLUMNA_VIV_OBSERVACION = "VIV_OBSERVACION";
	public final static String COLUMNA_VIV_FECHAREGISTRO = "VIV_FECHAREGISTRO";
	
	
	public final static String COLUMNA_VIV_VISITAS = "VIV_VISITAS";
	public final static String COLUMNA_VIV_STICKER = "VIV_STICKER";
	
	
	public final static String COLUMNA_VIV_FECHAINICIO = "VIV_FECHAINICIO";
	public final static String COLUMNA_VIV_FECHAFIN = "VIV_FECHAFIN";
	public final static String COLUMNA_VIV_ESTADOSINCRONIZACION = "VIV_ESTADOSINCRONIZACION";
	public final static String COLUMNA_VIV_FECHASINCRONIZACION = "VIV_FECHASINCRONIZACION";
	
	public final static String COLUMNA_VIV_ISVALIDATE = "VIV_ISVALIDATE";

	public static final String[] columnas = new String[] { COLUMNA_VIV_CODIGO,
			COLUMNA_USU_USUARIO, COLUMNA_VIV_CODIGOFORMULARIO,
			COLUMNA_VIV_CODIGOUNICOEQUIPO, COLUMNA_VIV_IDENTIFICADOREQUIPO,
			COLUMNA_VIV_FASE, COLUMNA_VIV_TIPOLEVANTAMIENTOBARRIDO,
			COLUMNA_VIV_CODIGOPROVINCIA, COLUMNA_VIV_CODIGOCANTON,
			COLUMNA_VIV_CODIGOPARROQUIA, COLUMNA_VIV_CODIGOPARROQUIAURBANA,
			COLUMNA_VIV_CODIGOZONA, COLUMNA_VIV_CODIGOSECTOR,
			COLUMNA_VIV_CODIGOBARRIOCOMUNIDAD, COLUMNA_VIV_CODIGOMANZANA,
			COLUMNA_VIV_CODIGODIVISION, COLUMNA_VIV_CODIGOEDIFICIO,
			COLUMNA_VIV_CODIGOVIVIENDA, COLUMNA_VIV_CODIGOHOGARINICIAL,
			COLUMNA_VIV_CODIGOHOGARFINAL, COLUMNA_VIV_LATITUD,
			COLUMNA_VIV_LONGITUD, COLUMNA_VIV_CALLE1, COLUMNA_VIV_NUMERO,
			COLUMNA_VIV_CALLE2, COLUMNA_VIV_NOMBRECONJUNTOEDIFICIO,
			COLUMNA_VIV_LOTE, COLUMNA_VIV_DEPARTAMENTO, COLUMNA_VIV_PISO,
			COLUMNA_VIV_TELEFONOLOCAL, COLUMNA_VIV_TELEFONOCELULAR,
			COLUMNA_VIV_REFERENCIAUBICACION, COLUMNA_VIV_CONDICIONOCUPACION,
			COLUMNA_VIV_CONTROLENTREVISTA, COLUMNA_VIV_OBSERVACION, COLUMNA_VIV_FECHAREGISTRO,
			COLUMNA_VIV_VISITAS, COLUMNA_VIV_STICKER,
			COLUMNA_VIV_FECHAINICIO, COLUMNA_VIV_FECHAFIN,
			COLUMNA_VIV_ESTADOSINCRONIZACION, COLUMNA_VIV_FECHASINCRONIZACION,
			COLUMNA_VIV_ISVALIDATE};

	/*
	 * Consultas
	 */
	public static String whereById = COLUMNA_VIV_CODIGO + "= ?";
	public static String whereByCertificado = COLUMNA_VIV_CODIGOFORMULARIO + " like ?";
	
	/**
	 * Cadena para traer las manzanas
	 */
	
	public static String whereByFechasControlEntrevistaFormularios = COLUMNA_VIV_FASE + " = ? AND " + "(" + COLUMNA_VIV_FECHAREGISTRO
			+ " BETWEEN  ?"
			+ " AND ?) AND " 
			+ COLUMNA_VIV_CONTROLENTREVISTA
			+ " = ?";
	
	public static String whereByFechasCondicionOcupacionFormularios = COLUMNA_VIV_FASE + " = ? AND " + "(" + COLUMNA_VIV_FECHAREGISTRO
			+ " BETWEEN  ?"
			+ " AND ?) AND " 
			+ COLUMNA_VIV_CONDICIONOCUPACION
			+ " = ?";
	
	public static String whereByFechasFormularios =  COLUMNA_VIV_FASE + " = ? AND " +"(" + COLUMNA_VIV_FECHAREGISTRO
			+ " BETWEEN  ?"	+ " AND ?) AND " + COLUMNA_VIV_CONTROLENTREVISTA +" <> ?" ;
	
	public static String whereByFaseFechasFormularios =  COLUMNA_VIV_FASE + " = ? AND " +"(" + COLUMNA_VIV_FECHAREGISTRO
			+ " BETWEEN  ?"
			+ " AND ?)";
	
	public static String whereByEstadoSincronizacionConFase =  COLUMNA_VIV_FASE + " = ? AND " + COLUMNA_VIV_ESTADOSINCRONIZACION + "  = ? AND " + COLUMNA_VIV_VISITAS + "  <> 0";
	public static String whereByEstadoSincronizacion =  COLUMNA_VIV_ESTADOSINCRONIZACION + "  = ? AND " + COLUMNA_VIV_VISITAS + "  <> 0 AND " + COLUMNA_VIV_CONTROLENTREVISTA +" <> ?" ;
	public static String whereByEstadoSincronizacionAuditoria =  COLUMNA_VIV_ESTADOSINCRONIZACION + "  = ? AND "  + COLUMNA_VIV_CONTROLENTREVISTA +" <> ?" ;
	public static String whereByEstadoSincronizacionIncompleta =  "(" + COLUMNA_VIV_ESTADOSINCRONIZACION + " = ? OR " + COLUMNA_VIV_ESTADOSINCRONIZACION + " = ?) AND " + COLUMNA_VIV_VISITAS + "  <> 0";
	
	

	public Vivienda() {
		codigo = 0;
	}
	
	public Vivienda(Vivienda vivienda) {
		
		this.codigo = vivienda.getCodigo();
		this.usuUsuario = vivienda.getUsuUsuario();
		this.codigoFormulario = vivienda.getCodigoFormulario();
		this.codigoUnicoEquipo = vivienda.getCodigoUnicoEquipo();
		this.identificadorEquipo = vivienda.getIdentificadorEquipo();
		this.fase = vivienda.getFase();
		this.tipoLevantamientoBarrido = vivienda.getTipoLevantamientoBarrido();
		this.codigoProvincia = vivienda.getCodigoProvincia();
		this.codigoCanton = vivienda.getCodigoCanton();
		this.codigoParroquia = vivienda.getCodigoParroquia();
		this.codigoParroquiaUrbana = vivienda.getCodigoParroquiaUrbana();
		this.codigoZona = vivienda.getCodigoZona();
		this.codigoSector = vivienda.getCodigoSector();
		this.codigoBarrioComunidad = vivienda.getCodigoBarrioComunidad();
		this.codigoManzana = vivienda.getCodigoManzana();
		this.codigoDivision = vivienda.getCodigoDivision();
		this.codigoEdificio = vivienda.getCodigoEdificio();
		this.codigoVivienda = vivienda.getCodigoVivienda();
		this.codigoHogarInicial = vivienda.getCodigoHogarInicial();
		this.codigoHogarFinal = vivienda.getCodigoHogarFinal();
		this.latitud = vivienda.getLatitud();
		this.longitud = vivienda.getLongitud();
		this.calle1 = vivienda.getCalle1();
		this.numero = vivienda.getNumero();
		this.calle2 = vivienda.getCalle2();
		this.nombreConjuntoEdificio = vivienda.getNombreConjuntoEdificio();
		this.lote = vivienda.getLote();
		this.departamento = vivienda.getDepartamento();
		this.piso = vivienda.getPiso();
		this.telefonoLocal = vivienda.getTelefonoLocal();
		this.telefonoCelular = vivienda.getTelefonoCelular();
		this.referenciaUbicacion = vivienda.getReferenciaUbicacion();
		this.condicionOcupacion = vivienda.getCondicionOcupacion();
		this.controlEntrevista = vivienda.getControlEntrevista();
		this.observacion = vivienda.getObservacion();
		this.fechaRegistro =  vivienda.getFechaRegistro();
		this.visitas =  vivienda.getVisitas();
		this.sticker =  vivienda.getSticker();
		this.fechaInicio = vivienda.getFechaInicio();
		this.fechaFin = vivienda.getFechaFin();
		this.estadoSincronizacion = vivienda.getEstadoSincronizacion();
		this.fechaSincronizacion = vivienda.getFechaSincronizacion();
		this.isValidate = vivienda.getIsValidate();
	}

	





	public Vivienda(int codigo, String usuUsuario, String codigoFormulario,
                    int codigoUnicoEquipo, String identificadorEquipo, int fase,
                    int tipoLevantamientoBarrido, String codigoProvincia,
                    String codigoCanton, String codigoParroquia,
                    String codigoParroquiaUrbana, String codigoZona,
                    String codigoSector, String codigoBarrioComunidad,
                    int codigoManzana, String codigoDivision, Integer codigoEdificio,
                    Integer codigoVivienda, Integer codigoHogarInicial,
                    Integer codigoHogarFinal, String latitud, String longitud,
                    String calle1, String numero, String calle2,
                    String nombreConjuntoEdificio, String lote, String departamento,
                    int piso, String telefonoLocal, String telefonoCelular,
                    String referenciaUbicacion, int condicionOcupacion,
                    int controlEntrevista, String observacion, String fechaRegistro,
                    int visitas, String sticker, String fechaInicio, String fechaFin,
                    int estadoSincronizacion, String fechaSincronizacion, int isValidate) {
		super();
		this.codigo = codigo;
		this.usuUsuario = usuUsuario;
		this.codigoFormulario = codigoFormulario;
		this.codigoUnicoEquipo = codigoUnicoEquipo;
		this.identificadorEquipo = identificadorEquipo;
		this.fase = fase;
		this.tipoLevantamientoBarrido = tipoLevantamientoBarrido;
		this.codigoProvincia = codigoProvincia;
		this.codigoCanton = codigoCanton;
		this.codigoParroquia = codigoParroquia;
		this.codigoParroquiaUrbana = codigoParroquiaUrbana;
		this.codigoZona = codigoZona;
		this.codigoSector = codigoSector;
		this.codigoBarrioComunidad = codigoBarrioComunidad;
		this.codigoManzana = codigoManzana;
		this.codigoDivision = codigoDivision;
		this.codigoEdificio = codigoEdificio;
		this.codigoVivienda = codigoVivienda;
		this.codigoHogarInicial = codigoHogarInicial;
		this.codigoHogarFinal = codigoHogarFinal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.calle1 = calle1;
		this.numero = numero;
		this.calle2 = calle2;
		this.nombreConjuntoEdificio = nombreConjuntoEdificio;
		this.lote = lote;
		this.departamento = departamento;
		this.piso = piso;
		this.telefonoLocal = telefonoLocal;
		this.telefonoCelular = telefonoCelular;
		this.referenciaUbicacion = referenciaUbicacion;
		this.condicionOcupacion = condicionOcupacion;
		this.controlEntrevista = controlEntrevista;
		this.observacion = observacion;
		this.fechaRegistro = fechaRegistro;
		this.visitas = visitas;
		this.sticker = sticker;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estadoSincronizacion = estadoSincronizacion;
		this.fechaSincronizacion = fechaSincronizacion;
		this.isValidate = isValidate;
	}

	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public String getUsuUsuario() {
		return usuUsuario;
	}



	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}



	public String getCodigoFormulario() {
		return codigoFormulario;
	}



	public void setCodigoFormulario(String codigoFormulario) {
		this.codigoFormulario = codigoFormulario;
	}



	public int getCodigoUnicoEquipo() {
		return codigoUnicoEquipo;
	}



	public void setCodigoUnicoEquipo(int codigoUnicoEquipo) {
		this.codigoUnicoEquipo = codigoUnicoEquipo;
	}



	public String getIdentificadorEquipo() {
		return identificadorEquipo;
	}



	public void setIdentificadorEquipo(String identificadorEquipo) {
		this.identificadorEquipo = identificadorEquipo;
	}



	public int getFase() {
		return fase;
	}



	public void setFase(int fase) {
		this.fase = fase;
	}



	public int getTipoLevantamientoBarrido() {
		return tipoLevantamientoBarrido;
	}



	public void setTipoLevantamientoBarrido(int tipoLevantamientoBarrido) {
		this.tipoLevantamientoBarrido = tipoLevantamientoBarrido;
	}



	public String getCodigoProvincia() {
		return codigoProvincia;
	}



	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}



	public String getCodigoCanton() {
		return codigoCanton;
	}



	public void setCodigoCanton(String codigoCanton) {
		this.codigoCanton = codigoCanton;
	}



	public String getCodigoParroquia() {
		return codigoParroquia;
	}



	public void setCodigoParroquia(String codigoParroquia) {
		this.codigoParroquia = codigoParroquia;
	}



	public String getCodigoParroquiaUrbana() {
		return codigoParroquiaUrbana;
	}



	public void setCodigoParroquiaUrbana(String codigoParroquiaUrbana) {
		this.codigoParroquiaUrbana = codigoParroquiaUrbana;
	}



	public String getCodigoZona() {
		return codigoZona;
	}



	public void setCodigoZona(String codigoZona) {
		this.codigoZona = codigoZona;
	}



	public String getCodigoSector() {
		return codigoSector;
	}



	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}



	public String getCodigoBarrioComunidad() {
		return codigoBarrioComunidad;
	}



	public void setCodigoBarrioComunidad(String codigoBarrioComunidad) {
		this.codigoBarrioComunidad = codigoBarrioComunidad;
	}



	public int getCodigoManzana() {
		return codigoManzana;
	}



	public void setCodigoManzana(int codigoManzana) {
		this.codigoManzana = codigoManzana;
	}



	public String getCodigoDivision() {
		return codigoDivision;
	}



	public void setCodigoDivision(String codigoDivision) {
		this.codigoDivision = codigoDivision;
	}



	public Integer getCodigoEdificio() {
		return codigoEdificio;
	}



	public void setCodigoEdificio(Integer codigoEdificio) {
		this.codigoEdificio = codigoEdificio;
	}



	public Integer getCodigoVivienda() {
		return codigoVivienda;
	}



	public void setCodigoVivienda(Integer codigoVivienda) {
		this.codigoVivienda = codigoVivienda;
	}



	public Integer getCodigoHogarInicial() {
		return codigoHogarInicial;
	}



	public void setCodigoHogarInicial(Integer codigoHogarInicial) {
		this.codigoHogarInicial = codigoHogarInicial;
	}



	public Integer getCodigoHogarFinal() {
		
		return codigoHogarFinal;
	}



	public void setCodigoHogarFinal(Integer codigoHogarFinal) {
		this.codigoHogarFinal = codigoHogarFinal;
	}



	public String getLatitud() {
		return latitud;
	}



	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}



	public String getLongitud() {
		return longitud;
	}



	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}



	public String getCalle1() {
		return calle1;
	}



	public void setCalle1(String calle1) {
		this.calle1 = calle1;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getCalle2() {
		return calle2;
	}



	public void setCalle2(String calle2) {
		this.calle2 = calle2;
	}



	public String getNombreConjuntoEdificio() {
		return nombreConjuntoEdificio;
	}



	public void setNombreConjuntoEdificio(String nombreConjuntoEdificio) {
		this.nombreConjuntoEdificio = nombreConjuntoEdificio;
	}



	public String getLote() {
		return lote;
	}



	public void setLote(String lote) {
		this.lote = lote;
	}



	public String getDepartamento() {
		return departamento;
	}



	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}



	public int getPiso() {
		return piso;
	}



	public void setPiso(int piso) {
		this.piso = piso;
	}



	public String getTelefonoLocal() {
		return telefonoLocal;
	}



	public void setTelefonoLocal(String telefonoLocal) {
		this.telefonoLocal = telefonoLocal;
	}



	public String getTelefonoCelular() {
		return telefonoCelular;
	}



	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}



	public String getReferenciaUbicacion() {
		return referenciaUbicacion;
	}



	public void setReferenciaUbicacion(String referenciaUbicacion) {
		this.referenciaUbicacion = referenciaUbicacion;
	}



	public int getCondicionOcupacion() {
		return condicionOcupacion;
	}



	public void setCondicionOcupacion(int condicionOcupacion) {
		this.condicionOcupacion = condicionOcupacion;
	}



	public int getControlEntrevista() {
		return controlEntrevista;
	}



	public void setControlEntrevista(int controlEntrevista) {
		this.controlEntrevista = controlEntrevista;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	
	
	public int getVisitas() {
		return visitas;
	}



	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}



	public String getSticker() {
		return sticker;
	}



	public void setSticker(String sticker) {
		this.sticker = sticker;
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



	public int getEstadoSincronizacion() {
		return estadoSincronizacion;
	}



	public void setEstadoSincronizacion(int estadoSincronizacion) {
		this.estadoSincronizacion = estadoSincronizacion;
	}



	public String getFechaSincronizacion() {
		return fechaSincronizacion;
	}



	public void setFechaSincronizacion(String fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}


	public int getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(int isValidate) {
		this.isValidate = isValidate;
	}

	
	@Override
	public void writeToParcel(Parcel dest, int flags) {

		
		dest.writeInt(this.codigo);
		dest.writeString(this.usuUsuario);
		dest.writeString(this.codigoFormulario);
		dest.writeInt(this.codigoUnicoEquipo);
		dest.writeString(this.identificadorEquipo);
		dest.writeInt(this.fase);
		dest.writeInt(this.tipoLevantamientoBarrido);
		dest.writeString(this.codigoProvincia);
		dest.writeString(this.codigoCanton);
		dest.writeString(this.codigoParroquia);
		dest.writeString(this.codigoParroquiaUrbana);
		dest.writeString(this.codigoZona);
		dest.writeString(this.codigoSector);
		dest.writeString(this.codigoBarrioComunidad);
		dest.writeInt(this.codigoManzana);
		dest.writeString(this.codigoDivision);
		dest.writeInt(this.codigoEdificio);
		dest.writeInt(this.codigoVivienda);
		dest.writeInt(this.codigoHogarInicial);
		dest.writeInt(this.codigoHogarFinal);
		dest.writeString(this.latitud);
		dest.writeString(this.longitud);
		dest.writeString(this.calle1);
		dest.writeString(this.numero);
		dest.writeString(this.calle2);
		dest.writeString(this.nombreConjuntoEdificio);
		dest.writeString(this.lote);
		dest.writeString(this.departamento);
		dest.writeInt(this.piso);
		dest.writeString(this.telefonoLocal);
		dest.writeString(this.telefonoCelular);
		dest.writeString(this.referenciaUbicacion);
		dest.writeInt(this.condicionOcupacion);
		dest.writeInt(this.controlEntrevista);
		dest.writeString(this.observacion);
		dest.writeString(this.fechaRegistro);
		
		dest.writeInt(this.visitas);
		dest.writeString(this.sticker);
		
		dest.writeString(this.fechaInicio);
		dest.writeString(this.fechaFin);
		dest.writeInt(this.estadoSincronizacion);
		dest.writeString(this.fechaSincronizacion);
		
		dest.writeInt(this.isValidate);
		
		
	}
	
	public Vivienda(Parcel in) {

		this.codigo = in.readInt();
		this.usuUsuario = in.readString();
		this.codigoFormulario = in.readString();
		this.codigoUnicoEquipo = in.readInt();
		this.identificadorEquipo = in.readString();
		this.fase = in.readInt();
		this.tipoLevantamientoBarrido = in.readInt();
		this.codigoProvincia = in.readString();
		this.codigoCanton = in.readString();
		this.codigoParroquia = in.readString();
		this.codigoParroquiaUrbana = in.readString();
		this.codigoZona = in.readString();
		this.codigoSector = in.readString();
		this.codigoBarrioComunidad = in.readString();
		this.codigoManzana = in.readInt();
		this.codigoDivision = in.readString();
		this.codigoEdificio = in.readInt();
		this.codigoVivienda = in.readInt();
		this.codigoHogarInicial = in.readInt();
		this.codigoHogarFinal = in.readInt();
		this.latitud = in.readString();
		this.longitud = in.readString();
		this.calle1 = in.readString();
		this.numero = in.readString();
		this.calle2 = in.readString();
		this.nombreConjuntoEdificio = in.readString();
		this.lote = in.readString();
		this.departamento = in.readString();
		this.piso = in.readInt();
		this.telefonoLocal = in.readString();
		this.telefonoCelular = in.readString();
		this.referenciaUbicacion = in.readString();
		this.condicionOcupacion = in.readInt();
		this.controlEntrevista = in.readInt();
		this.observacion = in.readString();
		this.fechaRegistro = in.readString();
		
		this.visitas = in.readInt();
		this.sticker = in.readString();

		
		this.fechaInicio = in.readString();
		this.fechaFin = in.readString();
		this.estadoSincronizacion = in.readInt();
		this.fechaSincronizacion = in.readString();
		
		this.isValidate = in.readInt();
	
	}
	
	public static final Parcelable.Creator<Vivienda> CREATOR
	= new Parcelable.Creator<Vivienda>() {
	    public Vivienda createFromParcel(Parcel in) {
	        return new Vivienda(in);
	    }

	    public Vivienda[] newArray(int size) {
	        return new Vivienda[size];
	    }
	};


	

}
