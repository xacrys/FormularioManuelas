package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;

/**
 * Ejemplo de entidad
 */
public class Persona implements Serializable {

	//Definicion de campos
	private Integer id;
	private Integer idvivienda;
	private Integer idhogar;
	private Integer idresidente;
	private String apellidos;
	private String nombres;
	private Integer sexo;
	private String fechanacimiento;
	private Integer edadanio;
	private Integer edadmes;
	private String correoelectronico;
	private Integer iddocumentacion;
	private String ci;
	private Integer idparentesco;
	private Integer idestadocivil;
	private Integer madrevive;
	private Integer ordenMadre;
	private Integer idnacionalidad;
	private Integer idetnia;
	private Integer idsegurosocial1;
	private Integer idsegurosocial2;
	private Integer idsegurosalud1;
	private Integer idsegurosalud2;
	private Integer tienediscapacidad;
	private Integer carnediscapacidad;
	private Integer discapacidadintelectual;
	private Integer porcentajeintelectual;
	private Integer discapacidadfisica;
	private Integer porcentajefisica;
	private Integer discapacidadceguera;
	private Integer porcentajeceguera;
	private Integer discapacidadvision;
	private Integer porcentajevision;
	private Integer discapacidadsordera;
	private Integer porcentajesordera;
	private Integer discapacidadhipoacusia;
	private Integer porcentajehipoacusia;
	private Integer discapacidadpsicosociales;
	private Integer porcentajepsicosociales;
	private Integer idestablecimientoeducacion;
	private Integer idproteccionsocial;
	private Integer necesitaayudatecnica;
	private Integer recibioayudatecnica;
	private Integer sillaruedas;
	private Integer muletas;
	private Integer andadores;
	private Integer bastonapoyo;
	private Integer ortesis;
	private Integer colchon;
	private Integer cojin;
	private Integer bastonrastreo;
	private Integer abaco;
	private Integer computadora;
	private Integer audifonos;
	private Integer implantes;
	private Integer cochepostular;
	private Integer paniales;
	private Integer sillabaniarse;
	private Integer camas;
	private Integer otrasayudas;
	private Integer gobiernocentral;
	private Integer gobiernoautonomo;
	private Integer organizacionprivada;
	private Integer ningunainstitucion;
	private Integer idtipoenfermedad;
	private Integer enfermedaddiagnostico;
	private Integer idenfermedadcatastrofica;
	private Integer orden;
	private boolean flag;

	private String fechainicio;
	private String fechafin;
	private Integer informacioncompleta;
	private Integer tipoEdad;

	//Nombre de la tabla
	public final static String NOMBRE_TABLA = "persona";

	//Atributos de la Tabla
	public final static String COLUMNA_ID="id";
	public final static String COLUMNA_IDVIVIENDA="idvivienda";
	public final static String COLUMNA_IDHOGAR="idhogar";
	public final static String COLUMNA_IDRESIDENTE="idresidente";
	public final static String COLUMNA_APELLIDOS="apellidos";
	public final static String COLUMNA_NOMBRES="nombres";
	public final static String COLUMNA_SEXO="sexo";
	public final static String COLUMNA_FECHANACIMIENTO="fechanacimiento";
	public final static String COLUMNA_EDADANIO="edadanio";
	public final static String COLUMNA_EDADMES="edadmes";
	public final static String COLUMNA_CORREOELECTRONICO="correoelectronico";
	public final static String COLUMNA_IDDOCUMENTACION="iddocumentacion";
	public final static String COLUMNA_CI="ci";
	public final static String COLUMNA_IDPARENTESCO="idparentesco";
	public final static String COLUMNA_IDESTADOCIVIL="idestadocivil";
	public final static String COLUMNA_MADREVIVE="madrevive";
	public final static String COLUMNA_ORDENMADRE="ordenmadre";
	public final static String COLUMNA_IDNACIONALIDAD="idnacionalidad";
	public final static String COLUMNA_IDETNIA="idetnia";
	public final static String COLUMNA_IDSEGUROSOCIAL1="idsegurosocial1";
	public final static String COLUMNA_IDSEGUROSOCIAL2="idsegurosocial2";
	public final static String COLUMNA_IDSEGUROSALUD1="idsegurosalud1";
	public final static String COLUMNA_IDSEGUROSALUD2="idsegurosalud2";
	public final static String COLUMNA_TIENEDISCAPACIDAD="tienediscapacidad";
	public final static String COLUMNA_CARNEDISCAPACIDAD="carnetdiscapacidad";
	public final static String COLUMNA_DISCAPACIDADINTELECTUAL="discapacidadintelectual";
	public final static String COLUMNA_PORCENTAJEINTELECTUAL="porcentajeintelectual";
	public final static String COLUMNA_DISCAPACIDADFISICA="discapacidadfisica";
	public final static String COLUMNA_PORCENTAJEFISICA="porcentajefisica";
	public final static String COLUMNA_DISCAPACIDADCEGUERA="discapacidadceguera";
	public final static String COLUMNA_PORCENTAJECEGUERA="porcentajeceguera";
	public final static String COLUMNA_DISCAPACIDADVISION="discapacidadvision";
	public final static String COLUMNA_PORCENTAJEVISION="porcentajevision";
	public final static String COLUMNA_DISCAPACIDADSORDERA="discapacidadsordera";
	public final static String COLUMNA_PORCENTAJESORDERA="porcentajesordera";
	public final static String COLUMNA_DISCAPACIDADHIPOACUSIA="discapacidadhipoacusia";
	public final static String COLUMNA_PORCENTAJEHIPOACUSIA="porcentajehipoacusia";
	public final static String COLUMNA_DISCAPACIDADPSICOSOCIALES="discapacidadpsicosociales";
	public final static String COLUMNA_PORCENTAJEPSICOSOCIALES="porcentajepsicosociales";
	public final static String COLUMNA_IDESTABLECIMIENTOEDUCACION="idestablecimientoeducacion";
	public final static String COLUMNA_IDPROTECCIONSOCIAL="idproteccionsocial";
	public final static String COLUMNA_NECESITAAYUDATECNICA="necesitaayudatecnica";
	public final static String COLUMNA_RECIBIOAYUDATECNICA="recibioayudatecnica";
	public final static String COLUMNA_SILLARUEDAS="sillaruedas";
	public final static String COLUMNA_MULETAS="muletas";
	public final static String COLUMNA_ANDADORES="andadores";
	public final static String COLUMNA_BASTONAPOYO="bastonapoyo";
	public final static String COLUMNA_ORTESIS="ortesis";
	public final static String COLUMNA_COLCHON="colchon";
	public final static String COLUMNA_COJIN="cojin";
	public final static String COLUMNA_BASTONRASTREO="bastonrastreo";
	public final static String COLUMNA_ABACO="abaco";
	public final static String COLUMNA_COMPUTADORA="computadora";
	public final static String COLUMNA_AUDIFONOS="audifonos";
	public final static String COLUMNA_IMPLANTES="implantes";
	public final static String COLUMNA_COCHEPOSTULAR="cochepostular";
	public final static String COLUMNA_PANIALES="paniales";
	public final static String COLUMNA_SILLABANIARSE="sillabaniarse";
	public final static String COLUMNA_CAMAS="camas";
	public final static String COLUMNA_OTRASAYUDAS="otrasayudas";
	public final static String COLUMNA_GOBIERNOCENTRAL="gobiernocentral";
	public final static String COLUMNA_GOBIERNOAUTONOMO="gobiernoautonomo";
	public final static String COLUMNA_ORGANIZACIONPRIVADA="organizacionprivada";
	public final static String COLUMNA_NINGUNAINSTITUCION="ningunainstitucion";
	public final static String COLUMNA_IDTIPOENFERMEDAD="idtipoenfermedad";
	public final static String COLUMNA_ENFERMEDADDIAGNOSTICO="enfermedaddiagnostico";
	public final static String COLUMNA_IDENFERMEDADCATASTROFICA="idenfermedadcatastrofica";
	public final static String COLUMNA_ORDEN="orden";
	public final static String COLUMNA_FECHAINICIO="fechainicio";
	public final static String COLUMNA_FECHAFIN="fechafin";
	public final static String COLUMNA_INFORMACIONCOMPLETA="informacioncompleta";
	public final static String COLUMNA_TIPOEDAD = "tipoedad";

	//crear un string con las columnas de la tabla
	public static final String[] columnas = new String[] {
			COLUMNA_ID,
			COLUMNA_IDVIVIENDA,
			COLUMNA_IDHOGAR,
			COLUMNA_IDRESIDENTE,
			COLUMNA_APELLIDOS,
			COLUMNA_NOMBRES,
			COLUMNA_SEXO,
			COLUMNA_FECHANACIMIENTO,
			COLUMNA_EDADANIO,
			COLUMNA_EDADMES,
			COLUMNA_CORREOELECTRONICO,
			COLUMNA_IDDOCUMENTACION,
			COLUMNA_CI,
			COLUMNA_IDPARENTESCO,
			COLUMNA_IDESTADOCIVIL,
			COLUMNA_MADREVIVE,
			COLUMNA_ORDENMADRE,
			COLUMNA_ORDEN,
			COLUMNA_IDNACIONALIDAD,
			COLUMNA_IDETNIA,
			COLUMNA_IDSEGUROSOCIAL1,
			COLUMNA_IDSEGUROSOCIAL2,
			COLUMNA_IDSEGUROSALUD1,
			COLUMNA_IDSEGUROSALUD2,
			COLUMNA_TIENEDISCAPACIDAD,
			COLUMNA_CARNEDISCAPACIDAD,
			COLUMNA_DISCAPACIDADINTELECTUAL,
			COLUMNA_PORCENTAJEINTELECTUAL,
			COLUMNA_DISCAPACIDADFISICA,
			COLUMNA_PORCENTAJEFISICA,
			COLUMNA_DISCAPACIDADCEGUERA,
			COLUMNA_PORCENTAJECEGUERA,
			COLUMNA_DISCAPACIDADVISION,
			COLUMNA_PORCENTAJEVISION,
			COLUMNA_DISCAPACIDADSORDERA,
			COLUMNA_PORCENTAJESORDERA,
			COLUMNA_DISCAPACIDADHIPOACUSIA,
			COLUMNA_PORCENTAJEHIPOACUSIA,
			COLUMNA_DISCAPACIDADPSICOSOCIALES,
			COLUMNA_PORCENTAJEPSICOSOCIALES,
			COLUMNA_IDESTABLECIMIENTOEDUCACION,
			COLUMNA_IDPROTECCIONSOCIAL,
			COLUMNA_NECESITAAYUDATECNICA,
			COLUMNA_RECIBIOAYUDATECNICA,
			COLUMNA_SILLARUEDAS,
			COLUMNA_MULETAS,
			COLUMNA_ANDADORES,
			COLUMNA_BASTONAPOYO,
			COLUMNA_ORTESIS,
			COLUMNA_COLCHON,
			COLUMNA_COJIN,
			COLUMNA_BASTONRASTREO,
			COLUMNA_ABACO,
			COLUMNA_COMPUTADORA,
			COLUMNA_AUDIFONOS,
			COLUMNA_IMPLANTES,
			COLUMNA_COCHEPOSTULAR,
			COLUMNA_PANIALES,
			COLUMNA_SILLABANIARSE,
			COLUMNA_CAMAS,
			COLUMNA_OTRASAYUDAS,
			COLUMNA_GOBIERNOCENTRAL,
			COLUMNA_GOBIERNOAUTONOMO,
			COLUMNA_ORGANIZACIONPRIVADA,
			COLUMNA_NINGUNAINSTITUCION,
			COLUMNA_IDTIPOENFERMEDAD,
			COLUMNA_ENFERMEDADDIAGNOSTICO,
			COLUMNA_IDENFERMEDADCATASTROFICA,
			COLUMNA_ORDEN,
			COLUMNA_FECHAINICIO,
			COLUMNA_FECHAFIN,
			COLUMNA_INFORMACIONCOMPLETA,
			COLUMNA_TIPOEDAD,
	};

	private String[] generos = new String[] { "", "Hombre", "Mujer" };

	//consultas
	public static String whereById = COLUMNA_ID + "= ?";
	public static String whereByViviendaId = COLUMNA_IDVIVIENDA + " = ?";



	public Persona() {
		//codigo = 0;
	}
	/**
	 * Método que devuelve los valores de un registro
	 * @param persona
	 * @return
	 */
	public static ContentValues getValues(Persona persona) {
		ContentValues values = new ContentValues();
		values.put(COLUMNA_ID, persona.getId());
		values.put(COLUMNA_IDVIVIENDA, persona.getIdvivienda());
		values.put(COLUMNA_IDHOGAR, persona.getIdhogar());
		values.put(COLUMNA_IDRESIDENTE, persona.getIdresidente());
		values.put(COLUMNA_APELLIDOS, persona.getApellidos());
		values.put(COLUMNA_NOMBRES, persona.getNombres());
		values.put(COLUMNA_SEXO, persona.getSexo());
		values.put(COLUMNA_FECHANACIMIENTO, persona.getFechanacimiento());
		values.put(COLUMNA_EDADANIO, persona.getEdadanio());
		values.put(COLUMNA_EDADMES, persona.getEdadmes());
		values.put(COLUMNA_CORREOELECTRONICO, persona.getCorreoelectronico());
		values.put(COLUMNA_IDDOCUMENTACION, persona.getIddocumentacion());
		values.put(COLUMNA_CI, persona.getCi());
		values.put(COLUMNA_IDPARENTESCO, persona.getIdparentesco());
		values.put(COLUMNA_IDESTADOCIVIL, persona.getIdestadocivil());
		values.put(COLUMNA_MADREVIVE, persona.getMadrevive());
		values.put(COLUMNA_ORDENMADRE, persona.getOrdenMadre());
		values.put(COLUMNA_IDNACIONALIDAD, persona.getIdnacionalidad());
		values.put(COLUMNA_IDETNIA, persona.getIdetnia());
		values.put(COLUMNA_IDSEGUROSOCIAL1, persona.getIdsegurosocial1());
		values.put(COLUMNA_IDSEGUROSOCIAL2, persona.getIdsegurosocial2());
		values.put(COLUMNA_IDSEGUROSALUD1, persona.getIdsegurosalud1());
		values.put(COLUMNA_IDSEGUROSALUD2, persona.getIdsegurosalud2());
		values.put(COLUMNA_TIENEDISCAPACIDAD, persona.getTienediscapacidad());
		values.put(COLUMNA_CARNEDISCAPACIDAD, persona.getCarnediscapacidad());
		values.put(COLUMNA_DISCAPACIDADINTELECTUAL, persona.getDiscapacidadintelectual());
		values.put(COLUMNA_PORCENTAJEINTELECTUAL, persona.getPorcentajeintelectual());
		values.put(COLUMNA_DISCAPACIDADFISICA, persona.getDiscapacidadfisica());
		values.put(COLUMNA_PORCENTAJEFISICA, persona.getPorcentajefisica());
		values.put(COLUMNA_DISCAPACIDADCEGUERA, persona.getDiscapacidadceguera());
		values.put(COLUMNA_PORCENTAJECEGUERA, persona.getPorcentajeceguera());
		values.put(COLUMNA_DISCAPACIDADVISION, persona.getDiscapacidadvision());
		values.put(COLUMNA_PORCENTAJEVISION, persona.getPorcentajevision());
		values.put(COLUMNA_DISCAPACIDADSORDERA, persona.getDiscapacidadsordera());
		values.put(COLUMNA_PORCENTAJESORDERA, persona.getPorcentajesordera());
		values.put(COLUMNA_DISCAPACIDADHIPOACUSIA, persona.getDiscapacidadhipoacusia());
		values.put(COLUMNA_PORCENTAJEHIPOACUSIA, persona.getPorcentajehipoacusia());
		values.put(COLUMNA_DISCAPACIDADPSICOSOCIALES, persona.getDiscapacidadpsicosociales());
		values.put(COLUMNA_PORCENTAJEPSICOSOCIALES, persona.getPorcentajepsicosociales());
		values.put(COLUMNA_IDESTABLECIMIENTOEDUCACION, persona.getIdestablecimientoeducacion());
		values.put(COLUMNA_IDPROTECCIONSOCIAL, persona.getIdproteccionsocial());
		values.put(COLUMNA_NECESITAAYUDATECNICA, persona.getNecesitaayudatecnica());
		values.put(COLUMNA_RECIBIOAYUDATECNICA, persona.getRecibioayudatecnica());
		values.put(COLUMNA_SILLARUEDAS, persona.getSillaruedas());
		values.put(COLUMNA_MULETAS, persona.getMuletas());
		values.put(COLUMNA_ANDADORES, persona.getAndadores());
		values.put(COLUMNA_BASTONAPOYO, persona.getBastonapoyo());
		values.put(COLUMNA_ORTESIS, persona.getOrtesis());
		values.put(COLUMNA_COLCHON, persona.getColchon());
		values.put(COLUMNA_COJIN, persona.getCojin());
		values.put(COLUMNA_BASTONRASTREO, persona.getBastonrastreo());
		values.put(COLUMNA_ABACO, persona.getAbaco());
		values.put(COLUMNA_COMPUTADORA, persona.getComputadora());
		values.put(COLUMNA_AUDIFONOS, persona.getAudifonos());
		values.put(COLUMNA_IMPLANTES, persona.getImplantes());
		values.put(COLUMNA_COCHEPOSTULAR, persona.getCochepostular());
		values.put(COLUMNA_PANIALES, persona.getPaniales());
		values.put(COLUMNA_SILLABANIARSE, persona.getSillabaniarse());
		values.put(COLUMNA_CAMAS, persona.getCamas());
		values.put(COLUMNA_OTRASAYUDAS, persona.getOtrasayudas());
		values.put(COLUMNA_GOBIERNOCENTRAL, persona.getGobiernocentral());
		values.put(COLUMNA_GOBIERNOAUTONOMO, persona.getGobiernoautonomo());
		values.put(COLUMNA_ORGANIZACIONPRIVADA, persona.getOrganizacionprivada());
		values.put(COLUMNA_NINGUNAINSTITUCION, persona.getNingunainstitucion());
		values.put(COLUMNA_IDTIPOENFERMEDAD, persona.getIdtipoenfermedad());
		values.put(COLUMNA_ENFERMEDADDIAGNOSTICO, persona.getEnfermedaddiagnostico());
		values.put(COLUMNA_IDENFERMEDADCATASTROFICA, persona.getIdenfermedadcatastrofica());
		values.put(COLUMNA_ORDEN, persona.getOrden());
		values.put(COLUMNA_FECHAINICIO, persona.getFechainicio());
		values.put(COLUMNA_FECHAFIN, persona.getFechafin());
		values.put(COLUMNA_INFORMACIONCOMPLETA, persona.getInformacioncompleta());
		values.put(COLUMNA_TIPOEDAD, persona.getTipoEdad());

		return values;
	}

	/**
	 * Método que crea una nueva persona
	 * @param result
	 * @return
	 */
	public static Persona newPersona(Cursor result) {
		Persona persona = new Persona();
		persona.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
		persona.setIdvivienda(result.getInt(result.getColumnIndex(COLUMNA_IDVIVIENDA)));
		persona.setIdhogar(result.getInt(result.getColumnIndex(COLUMNA_IDHOGAR)));
		persona.setIdresidente(result.getInt(result.getColumnIndex(COLUMNA_IDRESIDENTE)));
		persona.setApellidos(result.getString(result.getColumnIndex(COLUMNA_APELLIDOS)));
		persona.setNombres(result.getString(result.getColumnIndex(COLUMNA_NOMBRES)));
		persona.setSexo(result.getInt(result.getColumnIndex(COLUMNA_SEXO)));
		persona.setFechanacimiento(result.getString(result.getColumnIndex(COLUMNA_FECHANACIMIENTO)));
		persona.setEdadanio(result.getInt(result.getColumnIndex(COLUMNA_EDADANIO)));
		persona.setEdadmes(result.getInt(result.getColumnIndex(COLUMNA_EDADMES)));
		persona.setCorreoelectronico(result.getString(result.getColumnIndex(COLUMNA_CORREOELECTRONICO)));
		persona.setIddocumentacion(result.getInt(result.getColumnIndex(COLUMNA_IDDOCUMENTACION)));
		persona.setCi(result.getString(result.getColumnIndex(COLUMNA_CI)));
		persona.setIdparentesco(result.getInt(result.getColumnIndex(COLUMNA_IDPARENTESCO)));
		persona.setIdestadocivil(result.getInt(result.getColumnIndex(COLUMNA_IDESTADOCIVIL)));
		persona.setMadrevive(result.getInt(result.getColumnIndex(COLUMNA_MADREVIVE)));
		persona.setOrdenMadre(result.getInt(result.getColumnIndex(COLUMNA_ORDENMADRE)));
		persona.setIdnacionalidad(result.getInt(result.getColumnIndex(COLUMNA_IDNACIONALIDAD)));
		persona.setIdetnia(result.getInt(result.getColumnIndex(COLUMNA_IDETNIA)));
		persona.setIdsegurosocial1(result.getInt(result.getColumnIndex(COLUMNA_IDSEGUROSOCIAL1)));
		persona.setIdsegurosocial2(result.getInt(result.getColumnIndex(COLUMNA_IDSEGUROSOCIAL2)));
		persona.setIdsegurosalud1(result.getInt(result.getColumnIndex(COLUMNA_IDSEGUROSALUD1)));
		persona.setIdsegurosalud2(result.getInt(result.getColumnIndex(COLUMNA_IDSEGUROSALUD2)));
		persona.setTienediscapacidad(result.getInt(result.getColumnIndex(COLUMNA_TIENEDISCAPACIDAD)));
		persona.setCarnediscapacidad(result.getInt(result.getColumnIndex(COLUMNA_CARNEDISCAPACIDAD)));
		persona.setDiscapacidadintelectual(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADINTELECTUAL)));
		persona.setPorcentajeintelectual(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJEINTELECTUAL)));
		persona.setDiscapacidadfisica(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADFISICA)));
		persona.setPorcentajefisica(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJEFISICA)));
		persona.setDiscapacidadceguera(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADCEGUERA)));
		persona.setPorcentajeceguera(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJECEGUERA)));
		persona.setDiscapacidadvision(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADVISION)));
		persona.setPorcentajevision(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJEVISION)));
		persona.setDiscapacidadsordera(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADSORDERA)));
		persona.setPorcentajesordera(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJESORDERA)));
		persona.setDiscapacidadhipoacusia(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADHIPOACUSIA)));
		persona.setPorcentajehipoacusia(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJEHIPOACUSIA)));
		persona.setDiscapacidadpsicosociales(result.getInt(result.getColumnIndex(COLUMNA_DISCAPACIDADPSICOSOCIALES)));
		persona.setPorcentajepsicosociales(result.getInt(result.getColumnIndex(COLUMNA_PORCENTAJEPSICOSOCIALES)));
		persona.setIdestablecimientoeducacion(result.getInt(result.getColumnIndex(COLUMNA_IDESTABLECIMIENTOEDUCACION)));
		persona.setIdproteccionsocial(result.getInt(result.getColumnIndex(COLUMNA_IDPROTECCIONSOCIAL)));
		persona.setNecesitaayudatecnica(result.getInt(result.getColumnIndex(COLUMNA_NECESITAAYUDATECNICA)));
		persona.setRecibioayudatecnica(result.getInt(result.getColumnIndex(COLUMNA_RECIBIOAYUDATECNICA)));
		persona.setSillaruedas(result.getInt(result.getColumnIndex(COLUMNA_SILLARUEDAS)));
		persona.setMuletas(result.getInt(result.getColumnIndex(COLUMNA_MULETAS)));
		persona.setAndadores(result.getInt(result.getColumnIndex(COLUMNA_ANDADORES)));
		persona.setBastonapoyo(result.getInt(result.getColumnIndex(COLUMNA_BASTONAPOYO)));
		persona.setOrtesis(result.getInt(result.getColumnIndex(COLUMNA_ORTESIS)));
		persona.setColchon(result.getInt(result.getColumnIndex(COLUMNA_COLCHON)));
		persona.setCojin(result.getInt(result.getColumnIndex(COLUMNA_COJIN)));
		persona.setBastonrastreo(result.getInt(result.getColumnIndex(COLUMNA_BASTONRASTREO)));
		persona.setAbaco(result.getInt(result.getColumnIndex(COLUMNA_ABACO)));
		persona.setComputadora(result.getInt(result.getColumnIndex(COLUMNA_COMPUTADORA)));
		persona.setAudifonos(result.getInt(result.getColumnIndex(COLUMNA_AUDIFONOS)));
		persona.setImplantes(result.getInt(result.getColumnIndex(COLUMNA_IMPLANTES)));
		persona.setCochepostular(result.getInt(result.getColumnIndex(COLUMNA_COCHEPOSTULAR)));
		persona.setPaniales(result.getInt(result.getColumnIndex(COLUMNA_PANIALES)));
		persona.setSillabaniarse(result.getInt(result.getColumnIndex(COLUMNA_SILLABANIARSE)));
		persona.setCamas(result.getInt(result.getColumnIndex(COLUMNA_CAMAS)));
		persona.setOtrasayudas(result.getInt(result.getColumnIndex(COLUMNA_OTRASAYUDAS)));
		persona.setGobiernocentral(result.getInt(result.getColumnIndex(COLUMNA_GOBIERNOCENTRAL)));
		persona.setGobiernoautonomo(result.getInt(result.getColumnIndex(COLUMNA_GOBIERNOAUTONOMO)));
		persona.setOrganizacionprivada(result.getInt(result.getColumnIndex(COLUMNA_ORGANIZACIONPRIVADA)));
		persona.setNingunainstitucion(result.getInt(result.getColumnIndex(COLUMNA_NINGUNAINSTITUCION)));
		persona.setIdtipoenfermedad(result.getInt(result.getColumnIndex(COLUMNA_IDTIPOENFERMEDAD)));
		persona.setEnfermedaddiagnostico(result.getInt(result.getColumnIndex(COLUMNA_ENFERMEDADDIAGNOSTICO)));
		persona.setIdenfermedadcatastrofica(result.getInt(result.getColumnIndex(COLUMNA_IDENFERMEDADCATASTROFICA)));
		persona.setOrden(result.getInt(result.getColumnIndex(COLUMNA_ORDEN)));
		persona.setFechainicio(result.getString(result.getColumnIndex(COLUMNA_FECHAINICIO)));
		persona.setFechafin(result.getString(result.getColumnIndex(COLUMNA_FECHAFIN)));
		persona.setInformacioncompleta(result.getInt(result.getColumnIndex(COLUMNA_INFORMACIONCOMPLETA)));
		persona.setTipoEdad(result.getInt(result.getColumnIndex(COLUMNA_TIPOEDAD)));

		return persona;
	}

	public String getNombresCompletos() {
		return orden + ". " + apellidos + " " + nombres;
	}

	public String getGeneroCompleto() {
		return "Sexo: " + generos[sexo];
	}

	public String getGeneroDescripcion() {
		return generos[sexo];
	}

	public String getEdadCompleto() {
		return "Edad: " + edadanio;
	}


	public String getCedulaCompleta() {
		return "Cedula: " + ci;
	}

	public String getParentescoCompleto(Context contex) {

		ArrayList<Values> parentesco = new ArrayList<Values>();

		parentesco.add(new Values("-1", contex.getString(R.string.seleccionRespuesta)));
		parentesco.add(new Values("1", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion1)));
		parentesco.add(new Values("2", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion2)));
		parentesco.add(new Values("3", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion3)));
		parentesco.add(new Values("4", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion4)));
		parentesco.add(new Values("5", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion5)));
		parentesco.add(new Values("6", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion6)));
		parentesco.add(new Values("7", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion7)));
		parentesco.add(new Values("8", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion8)));
		parentesco.add(new Values("9", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion9)));
		parentesco.add(new Values("10", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion10)));
		parentesco.add(new Values("11", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion11)));
		parentesco.add(new Values("12", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion12)));
		parentesco.add(new Values("12", contex.getString(R.string.controlTrabajoParentescoJefeHogarOpcion13)));

		int posicion = Utilitarios.getPosicionByKey(
				parentesco, String.valueOf(idparentesco));

		return "Parentesco RJH: " + parentesco.get(posicion).getValue();
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

	public Integer getIdhogar() {
		return idhogar;
	}

	public void setIdhogar(Integer idhogar) {
		this.idhogar = idhogar;
	}

	public Integer getIdresidente() {
		return idresidente;
	}

	public void setIdresidente(Integer idresidente) {
		this.idresidente = idresidente;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public String getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Integer getEdadanio() {
		return edadanio;
	}

	public void setEdadanio(Integer edadanio) {
		this.edadanio = edadanio;
	}

	public Integer getEdadmes() {
		return edadmes;
	}

	public void setEdadmes(Integer edadmes) {
		this.edadmes = edadmes;
	}

	public String getCorreoelectronico() {
		return correoelectronico;
	}

	public void setCorreoelectronico(String correoelectronico) {
		this.correoelectronico = correoelectronico;
	}

	public Integer getIddocumentacion() {
		return iddocumentacion;
	}

	public void setIddocumentacion(Integer iddocumentacion) {
		this.iddocumentacion = iddocumentacion;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public Integer getIdparentesco() {
		return idparentesco;
	}

	public void setIdparentesco(Integer idparentesco) {
		this.idparentesco = idparentesco;
	}

	public Integer getIdestadocivil() {
		return idestadocivil;
	}

	public void setIdestadocivil(Integer idestadocivil) {
		this.idestadocivil = idestadocivil;
	}

	public Integer getMadrevive() {
		return madrevive;
	}

	public void setMadrevive(Integer madrevive) {
		this.madrevive = madrevive;
	}

	public Integer getIdnacionalidad() {
		return idnacionalidad;
	}

	public void setIdnacionalidad(Integer idnacionalidad) {
		this.idnacionalidad = idnacionalidad;
	}

	public Integer getIdetnia() {
		return idetnia;
	}

	public void setIdetnia(Integer idetnia) {
		this.idetnia = idetnia;
	}

	public Integer getIdsegurosocial1() {
		return idsegurosocial1;
	}

	public void setIdsegurosocial1(Integer idsegurosocial1) {
		this.idsegurosocial1 = idsegurosocial1;
	}

	public Integer getIdsegurosocial2() {
		return idsegurosocial2;
	}

	public void setIdsegurosocial2(Integer idsegurosocial2) {
		this.idsegurosocial2 = idsegurosocial2;
	}

	public Integer getIdsegurosalud1() {
		return idsegurosalud1;
	}

	public void setIdsegurosalud1(Integer idsegurosalud1) {
		this.idsegurosalud1 = idsegurosalud1;
	}

	public Integer getIdsegurosalud2() {
		return idsegurosalud2;
	}

	public void setIdsegurosalud2(Integer idsegurosalud2) {
		this.idsegurosalud2 = idsegurosalud2;
	}

	public Integer getTienediscapacidad() {
		return tienediscapacidad;
	}

	public void setTienediscapacidad(Integer tienediscapacidad) {
		this.tienediscapacidad = tienediscapacidad;
	}

	public Integer getCarnediscapacidad() {
		return carnediscapacidad;
	}

	public void setCarnediscapacidad(Integer carnediscapacidad) {
		this.carnediscapacidad = carnediscapacidad;
	}

	public Integer getIdestablecimientoeducacion() {
		return idestablecimientoeducacion;
	}

	public void setIdestablecimientoeducacion(Integer idestablecimientoeducacion) {
		this.idestablecimientoeducacion = idestablecimientoeducacion;
	}

	public Integer getIdproteccionsocial() {
		return idproteccionsocial;
	}

	public void setIdproteccionsocial(Integer idproteccionsocial) {
		this.idproteccionsocial = idproteccionsocial;
	}

	public Integer getNecesitaayudatecnica() {
		return necesitaayudatecnica;
	}

	public void setNecesitaayudatecnica(Integer necesitaayudatecnica) {
		this.necesitaayudatecnica = necesitaayudatecnica;
	}

	public Integer getRecibioayudatecnica() {
		return recibioayudatecnica;
	}

	public void setRecibioayudatecnica(Integer recibioayudatecnica) {
		this.recibioayudatecnica = recibioayudatecnica;
	}

	public Integer getIdtipoenfermedad() {
		return idtipoenfermedad;
	}

	public void setIdtipoenfermedad(Integer idtipoenfermedad) {
		this.idtipoenfermedad = idtipoenfermedad;
	}

	public Integer getEnfermedaddiagnostico() {
		return enfermedaddiagnostico;
	}

	public void setEnfermedaddiagnostico(Integer enfermedaddiagnostico) {
		this.enfermedaddiagnostico = enfermedaddiagnostico;
	}

	public Integer getIdenfermedadcatastrofica() {
		return idenfermedadcatastrofica;
	}

	public void setIdenfermedadcatastrofica(Integer idenfermedadcatastrofica) {
		this.idenfermedadcatastrofica = idenfermedadcatastrofica;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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

	public Integer getInformacioncompleta() {
		return informacioncompleta;
	}

	public void setInformacioncompleta(Integer informacioncompleta) {
		this.informacioncompleta = informacioncompleta;
	}

	public Integer getOrdenMadre() {
		return ordenMadre;
	}

	public void setOrdenMadre(Integer ordenMadre) {
		this.ordenMadre = ordenMadre;
	}


	public Integer getDiscapacidadintelectual() {
		return discapacidadintelectual;
	}

	public void setDiscapacidadintelectual(Integer discapacidadintelectual) {
		this.discapacidadintelectual = discapacidadintelectual;
	}

	public Integer getPorcentajeintelectual() {
		return porcentajeintelectual;
	}

	public void setPorcentajeintelectual(Integer porcentajeintelectual) {
		this.porcentajeintelectual = porcentajeintelectual;
	}

	public Integer getDiscapacidadfisica() {
		return discapacidadfisica;
	}

	public void setDiscapacidadfisica(Integer discapacidadfisica) {
		this.discapacidadfisica = discapacidadfisica;
	}

	public Integer getPorcentajefisica() {
		return porcentajefisica;
	}

	public void setPorcentajefisica(Integer porcentajefisica) {
		this.porcentajefisica = porcentajefisica;
	}

	public Integer getDiscapacidadceguera() {
		return discapacidadceguera;
	}

	public void setDiscapacidadceguera(Integer discapacidadceguera) {
		this.discapacidadceguera = discapacidadceguera;
	}

	public Integer getPorcentajeceguera() {
		return porcentajeceguera;
	}

	public void setPorcentajeceguera(Integer porcentajeceguera) {
		this.porcentajeceguera = porcentajeceguera;
	}

	public Integer getDiscapacidadvision() {
		return discapacidadvision;
	}

	public void setDiscapacidadvision(Integer discapacidadvision) {
		this.discapacidadvision = discapacidadvision;
	}

	public Integer getPorcentajevision() {
		return porcentajevision;
	}

	public void setPorcentajevision(Integer porcentajevision) {
		this.porcentajevision = porcentajevision;
	}

	public Integer getDiscapacidadsordera() {
		return discapacidadsordera;
	}

	public void setDiscapacidadsordera(Integer discapacidadsordera) {
		this.discapacidadsordera = discapacidadsordera;
	}

	public Integer getPorcentajesordera() {
		return porcentajesordera;
	}

	public void setPorcentajesordera(Integer porcentajesordera) {
		this.porcentajesordera = porcentajesordera;
	}

	public Integer getDiscapacidadhipoacusia() {
		return discapacidadhipoacusia;
	}

	public void setDiscapacidadhipoacusia(Integer discapacidadhipoacusia) {
		this.discapacidadhipoacusia = discapacidadhipoacusia;
	}

	public Integer getPorcentajehipoacusia() {
		return porcentajehipoacusia;
	}

	public void setPorcentajehipoacusia(Integer porcentajehipoacusia) {
		this.porcentajehipoacusia = porcentajehipoacusia;
	}

	public Integer getDiscapacidadpsicosociales() {
		return discapacidadpsicosociales;
	}

	public void setDiscapacidadpsicosociales(Integer discapacidadpsicosociales) {
		this.discapacidadpsicosociales = discapacidadpsicosociales;
	}

	public Integer getSillaruedas() {
		return sillaruedas;
	}

	public void setSillaruedas(Integer sillaruedas) {
		this.sillaruedas = sillaruedas;
	}

	public Integer getMuletas() {
		return muletas;
	}

	public void setMuletas(Integer muletas) {
		this.muletas = muletas;
	}

	public Integer getAndadores() {
		return andadores;
	}

	public void setAndadores(Integer andadores) {
		this.andadores = andadores;
	}

	public Integer getBastonapoyo() {
		return bastonapoyo;
	}

	public void setBastonapoyo(Integer bastonapoyo) {
		this.bastonapoyo = bastonapoyo;
	}

	public Integer getOrtesis() {
		return ortesis;
	}

	public void setOrtesis(Integer ortesis) {
		this.ortesis = ortesis;
	}

	public Integer getColchon() {
		return colchon;
	}

	public void setColchon(Integer colchon) {
		this.colchon = colchon;
	}

	public Integer getCojin() {
		return cojin;
	}

	public void setCojin(Integer cojin) {
		this.cojin = cojin;
	}

	public Integer getBastonrastreo() {
		return bastonrastreo;
	}

	public void setBastonrastreo(Integer bastonrastreo) {
		this.bastonrastreo = bastonrastreo;
	}

	public Integer getAbaco() {
		return abaco;
	}

	public void setAbaco(Integer abaco) {
		this.abaco = abaco;
	}

	public Integer getComputadora() {
		return computadora;
	}

	public void setComputadora(Integer computadora) {
		this.computadora = computadora;
	}

	public Integer getAudifonos() {
		return audifonos;
	}

	public void setAudifonos(Integer audifonos) {
		this.audifonos = audifonos;
	}

	public Integer getImplantes() {
		return implantes;
	}

	public void setImplantes(Integer implantes) {
		this.implantes = implantes;
	}

	public Integer getCochepostular() {
		return cochepostular;
	}

	public void setCochepostular(Integer cochepostular) {
		this.cochepostular = cochepostular;
	}

	public Integer getPaniales() {
		return paniales;
	}

	public void setPaniales(Integer paniales) {
		this.paniales = paniales;
	}

	public Integer getSillabaniarse() {
		return sillabaniarse;
	}

	public void setSillabaniarse(Integer sillabaniarse) {
		this.sillabaniarse = sillabaniarse;
	}

	public Integer getCamas() {
		return camas;
	}

	public void setCamas(Integer camas) {
		this.camas = camas;
	}

	public Integer getOtrasayudas() {
		return otrasayudas;
	}

	public void setOtrasayudas(Integer otrasayudas) {
		this.otrasayudas = otrasayudas;
	}

	public Integer getGobiernocentral() {
		return gobiernocentral;
	}

	public void setGobiernocentral(Integer gobiernocentral) {
		this.gobiernocentral = gobiernocentral;
	}

	public Integer getGobiernoautonomo() {
		return gobiernoautonomo;
	}

	public void setGobiernoautonomo(Integer gobiernoautonomo) {
		this.gobiernoautonomo = gobiernoautonomo;
	}

	public Integer getOrganizacionprivada() {
		return organizacionprivada;
	}

	public void setOrganizacionprivada(Integer organizacionprivada) {
		this.organizacionprivada = organizacionprivada;
	}

	public Integer getNingunainstitucion() {
		return ningunainstitucion;
	}

	public void setNingunainstitucion(Integer ningunainstitucion) {
		this.ningunainstitucion = ningunainstitucion;
	}

	public Integer getPorcentajepsicosociales() {
		return porcentajepsicosociales;
	}

	public void setPorcentajepsicosociales(Integer porcentajepsicosociales) {
		this.porcentajepsicosociales = porcentajepsicosociales;
	}


	public Integer getTipoEdad() {
		return tipoEdad;
	}

	public void setTipoEdad(Integer tipoEdad) {
		this.tipoEdad = tipoEdad;
	}
}
