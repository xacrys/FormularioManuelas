package ec.gob.stptv.formularioManuelas.modelo.entidades;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ejemplo de entidad
 */
public class Vivienda implements Serializable {

    private Integer id;
    private Integer idtipolevantamiento;
    private Integer idfase;
    private Integer idarea;
    private String idprovincia;
    private String idcanton;
    private String idparroquia;
    private String codigodpa;
    private Integer aniodpa;
    private String zona;
    private String sector;
    private String manzana;
    private String localidad;
    private String division;
    private String edificio;
    private String vivienda;
    private Integer hogarinicial;
    private Integer hogarfinal;
    private String calle1;
    private String calle2;
    private String conjuntohabitacional;
    private String numerocasa;
    private String lote;
    private String departamento;
    private String piso;
    private String telefonoconvencional;
    private String telefonocelular;
    private String referenciaubicacion;
    private Integer idocupada;
    private Integer idtipovivienda;
    private Integer idviacceso;
    private Integer idmaterialtecho;
    private Integer idmaterialpiso;
    private Integer idmaterialpared;
    private Integer idestadoviviendaTecho;
    private Integer idestadoviviendaPiso;
    private Integer idestadoviviendaPared;
    private Integer formulario;
    private String fechaactualizacion;
    private String fechacreacion;
    private Integer numerovisitas;
    private Integer idcontrolentrevista;
    private boolean flag;

    private String vivcodigo;
    private String imei;
    private Integer idusuario;
    private Integer idgrupo;
    private Integer codigounicodispositivo;
    private String fechainicio;
    private String fechafin;
    private String fechasincronizacion;
    private Integer estadosincronizacion;
    private String observacion;

    //para sincronizar
    private Hogar hogar;
    private ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
    private ArrayList<Localizacion> listaLocalizacion = new ArrayList<Localizacion>();
    private String mensaje;


    //Nombre de la tabla
    public final static String NOMBRE_TABLA = "vivienda";

    //Atributos de la Tabla
    public final static String COLUMNA_ID = "id";
    public final static String COLUMNA_IDTIPOLEVANTAMIENTO = "idtipolevantamiento";
    public final static String COLUMNA_IDFASE = "idfase";
    public final static String COLUMNA_IDAREA = "idarea";
    public final static String COLUMNA_IDPROVINCIA = "idprovincia";
    public final static String COLUMNA_IDCANTON = "idcanton";
    public final static String COLUMNA_IDPARROQUIA = "idparroquia";
    public final static String COLUMNA_CODIGODPA = "codigodpa";
    public final static String COLUMNA_ANIODPA = "aniodpa";
    public final static String COLUMNA_ZONA = "zona";
    public final static String COLUMNA_SECTOR = "sector";
    public final static String COLUMNA_MANZANA = "manzana";
    public final static String COLUMNA_DIVISION = "division";
    public final static String COLUMNA_LOCALIDAD = "localidad";
    public final static String COLUMNA_EDIFICIO = "edificio";
    public final static String COLUMNA_VIVIENDA = "vivienda";
    public final static String COLUMNA_HOGAR_INICIAL = "hogarinicial";
    public final static String COLUMNA_HOGAR_FINAL = "hogarfinal";
    public final static String COLUMNA_CALLE1 = "calle1";
    public final static String COLUMNA_CALLE2 = "calle2";
    public final static String COLUMNA_CONJUNTOHABITACIONAL = "conjuntohabitacional";
    public final static String COLUMNA_NUMEROCASA = "numerocasa";
    public final static String COLUMNA_LOTE = "lote";
    public final static String COLUMNA_DEPARTAMENTO = "departamento";
    public final static String COLUMNA_PISO = "piso";
    public final static String COLUMNA_TELEFONOCONVENCIONAL = "telefonoconvencional";
    public final static String COLUMNA_TELEFONOCELULAR = "telefonocelular";
    public final static String COLUMNA_REFERENCIAUBICACION = "referenciaubicacion";
    public final static String COLUMNA_IDOCUPADA = "idocupada";
    public final static String COLUMNA_IDTIPOVIVIENDA = "idtipovivienda";
    public final static String COLUMNA_IDVIACCESO = "idviacceso";
    public final static String COLUMNA_IDMATERIALTECHO = "idmaterialtecho";
    public final static String COLUMNA_IDMATERIALPISO = "idmaterialpiso";
    public final static String COLUMNA_IDMATERIALPARED = "idmaterialpared";
    public final static String COLUMNA_IDESTADOVIVIENDATECHO = "idestadovivienda_techo";
    public final static String COLUMNA_IDESTADOVIVIENDAPISO = "idestadovivienda_piso";
    public final static String COLUMNA_IDESTADOVIVIENDAPARED = "idestadovivienda_pared";
    public final static String COLUMNA_FORMULARIO = "formulario";
    public final static String COLUMNA_FECHAACTUALIZACION = "fechaactualizacion";
    public final static String COLUMNA_FECHACREACION = "fechacreacion";
    public final static String COLUMNA_NUMEROVISITAS = "numerovisitas";
    public final static String COLUMNA_IDCONTROLENTREVISTA = "idcontrolentrevista";
    public final static String COLUMNA_FLAG = "flag";
    public final static String COLUMNA_VIVCODIGO = "vivcodigo";
    public final static String COLUMNA_IMEI = "imei";
    public final static String COLUMNA_IDUSUARIO = "idusuario";
    public final static String COLUMNA_IDGRUPO = "idgrupo";
    public final static String COLUMNA_CODIGOUNICODISPOSITIVO = "codigounicoequipo";
    public final static String COLUMNA_FECHAINICIO = "fechainicio";
    public final static String COLUMNA_FECHAFIN = "fechafin";
    public final static String COLUMNA_FECHASINCRONIZACION = "fechasincronizacion";
    public final static String COLUMNA_ESTADOSINCRONIZACION = "estadosincronizacion";
    public final static String COLUMNA_OBSERVACION = "observacion";


    //crear un string con las columnas de la tabla
    public static final String[] columnas = new String[]{
            COLUMNA_ID,
            COLUMNA_IDTIPOLEVANTAMIENTO,
            COLUMNA_IDFASE,
            COLUMNA_IDAREA,
            COLUMNA_IDPROVINCIA,
            COLUMNA_IDCANTON,
            COLUMNA_IDPARROQUIA,
            COLUMNA_CODIGODPA,
            COLUMNA_ANIODPA,
            COLUMNA_ZONA,
            COLUMNA_SECTOR,
            COLUMNA_MANZANA,
            COLUMNA_DIVISION,
            COLUMNA_LOCALIDAD,
            COLUMNA_EDIFICIO,
            COLUMNA_VIVIENDA,
            COLUMNA_HOGAR_INICIAL,
            COLUMNA_HOGAR_FINAL,
            COLUMNA_CALLE1,
            COLUMNA_CALLE2,
            COLUMNA_CONJUNTOHABITACIONAL,
            COLUMNA_NUMEROCASA,
            COLUMNA_LOTE,
            COLUMNA_DEPARTAMENTO,
            COLUMNA_PISO,
            COLUMNA_TELEFONOCONVENCIONAL,
            COLUMNA_TELEFONOCELULAR,
            COLUMNA_REFERENCIAUBICACION,
            COLUMNA_IDOCUPADA,
            COLUMNA_IDTIPOVIVIENDA,
            COLUMNA_IDVIACCESO,
            COLUMNA_IDMATERIALTECHO,
            COLUMNA_IDMATERIALPISO,
            COLUMNA_IDMATERIALPARED,
            COLUMNA_IDESTADOVIVIENDATECHO,
            COLUMNA_IDESTADOVIVIENDAPISO,
            COLUMNA_IDESTADOVIVIENDAPARED,
            COLUMNA_FORMULARIO,
            COLUMNA_FECHAACTUALIZACION,
            COLUMNA_FECHACREACION,
            COLUMNA_NUMEROVISITAS,
            COLUMNA_IDCONTROLENTREVISTA,
            COLUMNA_FLAG,
            COLUMNA_VIVCODIGO,
            COLUMNA_IMEI,
            COLUMNA_IDUSUARIO,
            COLUMNA_IDGRUPO,
            COLUMNA_CODIGOUNICODISPOSITIVO,
            COLUMNA_FECHAINICIO,
            COLUMNA_FECHAFIN,
            COLUMNA_FECHASINCRONIZACION,
            COLUMNA_ESTADOSINCRONIZACION,
            COLUMNA_OBSERVACION
    };

    //consultas
    public static String whereById = COLUMNA_ID + "= ?";

    public static String whereByFechasControlEntrevistaFormularios = COLUMNA_IDFASE + " = ? AND " + "(" + COLUMNA_FECHACREACION
            + " BETWEEN  ?"
            + " AND ?) AND "
            + COLUMNA_IDCONTROLENTREVISTA
            + " = ?";

    public static String whereByFechasFormularios =  COLUMNA_IDFASE + " = ? AND " +"(" + COLUMNA_FECHACREACION
            + " BETWEEN  ?"	+ " AND ?) AND " + COLUMNA_IDCONTROLENTREVISTA +" <> ?" ;


    public Vivienda() {
        id = 0;
    }

    /**
     * Método que devuelve los valores de un registro
     *
     * @param vivienda
     * @return
     */
    public static ContentValues getValues(Vivienda vivienda) {
        ContentValues values = new ContentValues();
        values.put(Vivienda.COLUMNA_ID, vivienda.getId());
        values.put(Vivienda.COLUMNA_IDTIPOLEVANTAMIENTO, vivienda.getIdtipolevantamiento());
        values.put(Vivienda.COLUMNA_IDFASE, vivienda.getIdfase());
        values.put(Vivienda.COLUMNA_IDAREA, vivienda.getIdarea());
        values.put(Vivienda.COLUMNA_IDPROVINCIA, vivienda.getIdprovincia());
        values.put(Vivienda.COLUMNA_IDCANTON, vivienda.getIdcanton());
        values.put(Vivienda.COLUMNA_IDPARROQUIA, vivienda.getIdparroquia());
        values.put(Vivienda.COLUMNA_CODIGODPA, vivienda.getCodigodpa());
        values.put(Vivienda.COLUMNA_ANIODPA, vivienda.getAniodpa());
        values.put(Vivienda.COLUMNA_ZONA, vivienda.getZona());
        values.put(Vivienda.COLUMNA_SECTOR, vivienda.getSector());
        values.put(Vivienda.COLUMNA_MANZANA, vivienda.getManzana());
        values.put(Vivienda.COLUMNA_DIVISION, vivienda.getDivision());
        values.put(Vivienda.COLUMNA_LOCALIDAD, vivienda.getLocalidad());
        values.put(Vivienda.COLUMNA_EDIFICIO, vivienda.getEdificio());
        values.put(Vivienda.COLUMNA_IDCANTON, vivienda.getIdcanton());
        values.put(Vivienda.COLUMNA_VIVIENDA, vivienda.getVivienda());
        values.put(Vivienda.COLUMNA_HOGAR_INICIAL, vivienda.getHogarinicial());
        values.put(Vivienda.COLUMNA_HOGAR_FINAL, vivienda.getHogarfinal());
        values.put(Vivienda.COLUMNA_CALLE1, vivienda.getCalle1());
        values.put(Vivienda.COLUMNA_CALLE2, vivienda.getCalle2());
        values.put(Vivienda.COLUMNA_CONJUNTOHABITACIONAL, vivienda.getConjuntohabitacional());
        values.put(Vivienda.COLUMNA_NUMEROCASA, vivienda.getNumerocasa());
        values.put(Vivienda.COLUMNA_LOTE, vivienda.getLote());
        values.put(Vivienda.COLUMNA_DEPARTAMENTO, vivienda.getDepartamento());
        values.put(Vivienda.COLUMNA_PISO, vivienda.getPiso());
        values.put(Vivienda.COLUMNA_TELEFONOCONVENCIONAL, vivienda.getTelefonoconvencional());
        values.put(Vivienda.COLUMNA_TELEFONOCELULAR, vivienda.getTelefonocelular());
        values.put(Vivienda.COLUMNA_REFERENCIAUBICACION, vivienda.getReferenciaubicacion());
        values.put(Vivienda.COLUMNA_IDOCUPADA, vivienda.getIdocupada());
        values.put(Vivienda.COLUMNA_IDTIPOVIVIENDA, vivienda.getIdtipovivienda());
        values.put(Vivienda.COLUMNA_IDVIACCESO, vivienda.getIdviacceso());
        values.put(Vivienda.COLUMNA_IDMATERIALTECHO, vivienda.getIdmaterialtecho());
        values.put(Vivienda.COLUMNA_IDMATERIALPISO, vivienda.getIdmaterialpiso());
        values.put(Vivienda.COLUMNA_IDMATERIALPARED, vivienda.getIdmaterialpared());
        values.put(Vivienda.COLUMNA_IDESTADOVIVIENDATECHO, vivienda.getIdestadoviviendaTecho());
        values.put(Vivienda.COLUMNA_IDESTADOVIVIENDAPISO, vivienda.getIdestadoviviendaPiso());
        values.put(Vivienda.COLUMNA_IDESTADOVIVIENDAPARED, vivienda.getIdestadoviviendaPared());
        values.put(Vivienda.COLUMNA_FORMULARIO, vivienda.getFormulario());
        values.put(Vivienda.COLUMNA_FECHAACTUALIZACION, vivienda.getFechaactualizacion());
        values.put(Vivienda.COLUMNA_FECHACREACION, vivienda.getFechacreacion());
        values.put(Vivienda.COLUMNA_NUMEROVISITAS, vivienda.getNumerovisitas());
        values.put(Vivienda.COLUMNA_IDCONTROLENTREVISTA, vivienda.getIdcontrolentrevista());
        values.put(Vivienda.COLUMNA_VIVCODIGO, vivienda.getVivcodigo());
        values.put(Vivienda.COLUMNA_IMEI, vivienda.getImei());
        values.put(Vivienda.COLUMNA_IDGRUPO, vivienda.getIdgrupo());
        values.put(Vivienda.COLUMNA_FECHASINCRONIZACION, vivienda.getFechasincronizacion());
        values.put(Vivienda.COLUMNA_FECHAINICIO, vivienda.getFechainicio());
        values.put(Vivienda.COLUMNA_FECHAFIN, vivienda.getFechafin());
        values.put(Vivienda.COLUMNA_ESTADOSINCRONIZACION, vivienda.getEstadosincronizacion());
        values.put(Vivienda.COLUMNA_OBSERVACION, vivienda.getObservacion());

        return values;
    }

    /**
     * Método que crea una nueva vivienda
     *
     * @param result
     * @return
     */
    public static Vivienda newVivienda(Cursor result) {
        Vivienda vivienda = new Vivienda();
        vivienda.setId(result.getInt(result.getColumnIndex(COLUMNA_ID)));
        vivienda.setIdtipolevantamiento(result.getInt(result.getColumnIndex(COLUMNA_IDTIPOLEVANTAMIENTO)));
        vivienda.setIdfase(result.getInt(result.getColumnIndex(COLUMNA_IDFASE)));
        vivienda.setIdarea(result.getInt(result.getColumnIndex(COLUMNA_IDAREA)));
        vivienda.setIdprovincia(result.getString(result.getColumnIndex(COLUMNA_IDPROVINCIA)));
        vivienda.setIdcanton(result.getString(result.getColumnIndex(COLUMNA_IDCANTON)));
        vivienda.setIdparroquia(result.getString(result.getColumnIndex(COLUMNA_IDPARROQUIA)));
        vivienda.setCodigodpa(result.getString(result.getColumnIndex(COLUMNA_CODIGODPA)));
        vivienda.setAniodpa(result.getInt(result.getColumnIndex(COLUMNA_ANIODPA)));
        vivienda.setZona(result.getString(result.getColumnIndex(COLUMNA_ZONA)));
        vivienda.setSector(result.getString(result.getColumnIndex(COLUMNA_SECTOR)));
        vivienda.setManzana(result.getString(result.getColumnIndex(COLUMNA_MANZANA)));
        vivienda.setDivision(result.getString(result.getColumnIndex(COLUMNA_DIVISION)));
        vivienda.setLocalidad(result.getString(result.getColumnIndex(COLUMNA_LOCALIDAD)));
        vivienda.setEdificio(result.getString(result.getColumnIndex(COLUMNA_EDIFICIO)));
        vivienda.setVivienda(result.getString(result.getColumnIndex(COLUMNA_VIVIENDA)));
        vivienda.setHogarinicial(result.getInt(result.getColumnIndex(COLUMNA_HOGAR_INICIAL)));
        vivienda.setHogarfinal(result.getInt(result.getColumnIndex(COLUMNA_HOGAR_FINAL)));
        vivienda.setCalle1(result.getString(result.getColumnIndex(COLUMNA_CALLE1)));
        vivienda.setCalle2(result.getString(result.getColumnIndex(COLUMNA_CALLE2)));
        vivienda.setConjuntohabitacional(result.getString(result.getColumnIndex(COLUMNA_CONJUNTOHABITACIONAL)));
        vivienda.setNumerocasa(result.getString(result.getColumnIndex(COLUMNA_NUMEROCASA)));
        vivienda.setLote(result.getString(result.getColumnIndex(COLUMNA_LOTE)));
        vivienda.setDepartamento(result.getString(result.getColumnIndex(COLUMNA_DEPARTAMENTO)));
        vivienda.setPiso(result.getString(result.getColumnIndex(COLUMNA_PISO)));
        vivienda.setTelefonoconvencional(result.getString(result.getColumnIndex(COLUMNA_TELEFONOCONVENCIONAL)));
        vivienda.setTelefonocelular(result.getString(result.getColumnIndex(COLUMNA_TELEFONOCELULAR)));
        vivienda.setReferenciaubicacion(result.getString(result.getColumnIndex(COLUMNA_REFERENCIAUBICACION)));
        vivienda.setIdocupada(result.getInt(result.getColumnIndex(COLUMNA_IDOCUPADA)));
        vivienda.setIdtipovivienda(result.getInt(result.getColumnIndex(COLUMNA_IDTIPOVIVIENDA)));
        vivienda.setIdviacceso(result.getInt(result.getColumnIndex(COLUMNA_IDVIACCESO)));
        vivienda.setIdmaterialtecho(result.getInt(result.getColumnIndex(COLUMNA_IDMATERIALTECHO)));
        vivienda.setIdmaterialpiso(result.getInt(result.getColumnIndex(COLUMNA_IDMATERIALPISO)));
        vivienda.setIdmaterialpared(result.getInt(result.getColumnIndex(COLUMNA_IDMATERIALPARED)));
        vivienda.setIdestadoviviendaTecho(result.getInt(result.getColumnIndex(COLUMNA_IDESTADOVIVIENDATECHO)));
        vivienda.setIdestadoviviendaPiso(result.getInt(result.getColumnIndex(COLUMNA_IDESTADOVIVIENDAPISO)));
        vivienda.setIdestadoviviendaPared(result.getInt(result.getColumnIndex(COLUMNA_IDESTADOVIVIENDAPARED)));
        vivienda.setFormulario(result.getInt(result.getColumnIndex(COLUMNA_FORMULARIO)));
        vivienda.setFechaactualizacion(result.getString(result.getColumnIndex(COLUMNA_FECHAACTUALIZACION)));
        vivienda.setFechacreacion(result.getString(result.getColumnIndex(COLUMNA_FECHACREACION)));
        vivienda.setNumerovisitas(result.getInt(result.getColumnIndex(COLUMNA_NUMEROVISITAS)));
        vivienda.setIdcontrolentrevista(result.getInt(result.getColumnIndex(COLUMNA_IDCONTROLENTREVISTA)));
        vivienda.setVivcodigo(result.getString(result.getColumnIndex(COLUMNA_VIVCODIGO)));
        vivienda.setImei(result.getString(result.getColumnIndex(COLUMNA_IMEI)));
        vivienda.setIdusuario(result.getInt(result.getColumnIndex(COLUMNA_IDUSUARIO)));
        vivienda.setIdgrupo(result.getInt(result.getColumnIndex(COLUMNA_IDGRUPO)));
        vivienda.setCodigounicodispositivo(result.getInt(result.getColumnIndex(COLUMNA_CODIGOUNICODISPOSITIVO)));
        vivienda.setFechainicio(result.getString(result.getColumnIndex(COLUMNA_FECHAINICIO)));
        vivienda.setFechafin(result.getString(result.getColumnIndex(COLUMNA_FECHAFIN)));
        vivienda.setFechasincronizacion(result.getString(result.getColumnIndex(COLUMNA_FECHASINCRONIZACION)));
        vivienda.setEstadosincronizacion(result.getInt(result.getColumnIndex(COLUMNA_ESTADOSINCRONIZACION)));
        vivienda.setObservacion(result.getString(result.getColumnIndex(COLUMNA_OBSERVACION)));
        return vivienda;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdtipolevantamiento() {
        return idtipolevantamiento;
    }

    public void setIdtipolevantamiento(Integer idtipolevantamiento) {
        this.idtipolevantamiento = idtipolevantamiento;
    }

    public Integer getIdfase() {
        return idfase;
    }

    public void setIdfase(Integer idfase) {
        this.idfase = idfase;
    }

    public Integer getIdarea() {
        return idarea;
    }

    public void setIdarea(Integer idarea) {
        this.idarea = idarea;
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

    public String getCodigodpa() {
        return codigodpa;
    }

    public void setCodigodpa(String codigodpa) {
        this.codigodpa = codigodpa;
    }

    public Integer getAniodpa() {
        return aniodpa;
    }

    public void setAniodpa(Integer aniodpa) {
        this.aniodpa = aniodpa;
    }


    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public String getCalle2() {
        return calle2;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public String getConjuntohabitacional() {
        return conjuntohabitacional;
    }

    public void setConjuntohabitacional(String conjuntohabitacional) {
        this.conjuntohabitacional = conjuntohabitacional;
    }

    public String getNumerocasa() {
        return numerocasa;
    }

    public void setNumerocasa(String numerocasa) {
        this.numerocasa = numerocasa;
    }


    public String getTelefonoconvencional() {
        return telefonoconvencional;
    }

    public void setTelefonoconvencional(String telefonoconvencional) {
        this.telefonoconvencional = telefonoconvencional;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getReferenciaubicacion() {
        return referenciaubicacion;
    }

    public void setReferenciaubicacion(String referenciaubicacion) {
        this.referenciaubicacion = referenciaubicacion;
    }

    public Integer getIdocupada() {
        return idocupada;
    }

    public void setIdocupada(Integer idocupada) {
        this.idocupada = idocupada;
    }

    public Integer getIdtipovivienda() {
        return idtipovivienda;
    }

    public void setIdtipovivienda(Integer idtipovivienda) {
        this.idtipovivienda = idtipovivienda;
    }

    public Integer getIdviacceso() {
        return idviacceso;
    }

    public void setIdviacceso(Integer idviacceso) {
        this.idviacceso = idviacceso;
    }

    public Integer getIdmaterialtecho() {
        return idmaterialtecho;
    }

    public void setIdmaterialtecho(Integer idmaterialtecho) {
        this.idmaterialtecho = idmaterialtecho;
    }

    public Integer getIdmaterialpiso() {
        return idmaterialpiso;
    }

    public void setIdmaterialpiso(Integer idmaterialpiso) {
        this.idmaterialpiso = idmaterialpiso;
    }

    public Integer getIdmaterialpared() {
        return idmaterialpared;
    }

    public void setIdmaterialpared(Integer idmaterialpared) {
        this.idmaterialpared = idmaterialpared;
    }

    public Integer getIdestadoviviendaTecho() {
        return idestadoviviendaTecho;
    }

    public void setIdestadoviviendaTecho(Integer idestadoviviendaTecho) {
        this.idestadoviviendaTecho = idestadoviviendaTecho;
    }

    public Integer getIdestadoviviendaPiso() {
        return idestadoviviendaPiso;
    }

    public void setIdestadoviviendaPiso(Integer idestadoviviendaPiso) {
        this.idestadoviviendaPiso = idestadoviviendaPiso;
    }

    public Integer getIdestadoviviendaPared() {
        return idestadoviviendaPared;
    }

    public void setIdestadoviviendaPared(Integer idestadoviviendaPared) {
        this.idestadoviviendaPared = idestadoviviendaPared;
    }

    public Integer getFormulario() {
        return formulario;
    }

    public void setFormulario(Integer formulario) {
        this.formulario = formulario;
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

    public Integer getNumerovisitas() {
        return numerovisitas;
    }

    public void setNumerovisitas(Integer numerovisitas) {
        this.numerovisitas = numerovisitas;
    }

    public Integer getIdcontrolentrevista() {
        return idcontrolentrevista;
    }

    public void setIdcontrolentrevista(Integer idcontrolentrevista) {
        this.idcontrolentrevista = idcontrolentrevista;
    }



    public String getVivcodigo() {
        return vivcodigo;
    }

    public void setVivcodigo(String vivcodigo) {
        this.vivcodigo = vivcodigo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Integer idgrupo) {
        this.idgrupo = idgrupo;
    }

    public Integer getCodigounicodispositivo() {
        return codigounicodispositivo;
    }

    public void setCodigounicodispositivo(Integer codigounicodispositivo) {
        this.codigounicodispositivo = codigounicodispositivo;
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

    public String getFechasincronizacion() {
        return fechasincronizacion;
    }

    public void setFechasincronizacion(String fechasincronizacion) {
        this.fechasincronizacion = fechasincronizacion;
    }

    public Integer getEstadosincronizacion() {
        return estadosincronizacion;
    }

    public void setEstadosincronizacion(Integer estadosincronizacion) {
        this.estadosincronizacion = estadosincronizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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


    public Integer getHogarinicial() {
        return hogarinicial;
    }

    public void setHogarinicial(Integer hogarinicial) {
        this.hogarinicial = hogarinicial;
    }

    public Integer getHogarfinal() {
        return hogarfinal;
    }

    public void setHogarfinal(Integer hogarfinal) {
        this.hogarfinal = hogarfinal;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }


    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
