package ec.gob.stptv.formularioManuelas.controlador.util;

/**
 * Created by lmorales on 14/07/17.
 */

public class Global {
    /**
     * Nombre del paquete de la aplicacion
     */
    public static final String PACKAGE_NAME = "ec.gob.stptv.formularioManuelas";

    /**
     * Ambientes de desarrollo
     */
    public static final String DEVELOPMENT_ENVIRONMENT = "DEV";
    public static final String PRODUCTION_ENVIRONMENT = "PRO";
    public static final String PRODUCTION_ENVIRONMENT_PRUEBAS = "PRUEBAS";
    public static final String ENVIRONMENT = DEVELOPMENT_ENVIRONMENT;

    /**
     * IPS de produccion
     */
    //public static final String IP_SERVIDOR = "http://192.168.10.151:8080";
    public static final String IP_SERVIDOR = "http://200.125.208.21:8080";

    /**
     * WEB SERVICES
     */
    public static final String URL_WEB_SERVICES_LOGEO = "/SeguridadSptuvWeb/webresources";
    public static final String URL_WEB_SERVICES_INGRESO = "/Formulario-Discapacidades-war/webresources";
    public static final String URL_WEB_SERVICE_USUARIOS= IP_SERVIDOR + URL_WEB_SERVICES_LOGEO + "/logeoManuela";
    public static final String URL_WEB_SERVICE_INGRESO_FORMULARIO= IP_SERVIDOR + URL_WEB_SERVICES_INGRESO +"/vivienda";
    public static final String URL_WEB_SERVICE_VIVCODIGO= IP_SERVIDOR + URL_WEB_SERVICES_INGRESO +"/vivcodigo";
    public static final String URL_WEB_SERVICE_IMAGENES= IP_SERVIDOR + URL_WEB_SERVICES_INGRESO + "/ingresoImagenes";

    /**
     * Tiempo de ejecucion de Login
     */
    public static final int ASYNCTASK_TIMEOUT_LOGIN =  120000; // 1min

    /**
     * Tiempo de ejecucion de sincronizacion
     */
    //public static final int ASYNCTASK_TIMROUT =  420000;
    public static final int ASYNCTASK_TIMEOUT =  120000; //2 minutos

    /**
     * Atributos para el manejo de logs
     */
    public static final String INFO = "I";
    public static final String ERROR = "E";
    public static final String PATH_LOGS = "/logs/formularioManuelas";

    /**
     * Ancho de la foto de vivienda y certificado
     */
    public static final int ANCHO_FOTO = 650;

    /**
     * Cadena para el servicio de localizacion
     */
    public static final String BROADCAST_ACTION_PROGRESS =
            "ec.gob.stptv.formularioManuelas.action.PROGRESS";

    /**
     * Cadena para el servicio de localizacion
     */
    public static final String BROADCAST_ACTION_FIN =
            "ec.gob.stptv.formularioManuelas.action.FIN";

    /**
     * Tiempo mínimo entre actualizaciones, en milisegundos.
     */
    public static final int LOCATION_INTERVAL = 1000;//60000 * 2; // 2 segundo
    /**
     * Distancia mínima entre actualizaciones, en metros.
     */
    public static final float LOCATION_DISTANCE = 0;

    /**
     * Tiempo de ejecucion de sincronizacion
     */
    public static final int ASYNCTASK_TIMEROUT =  120000; // 2 min
    public static final int ASYNCTASK_TIMEROUT_IMAGENES =  420000; // 7 min

    /**
     * Valor para la sincronizacion completa
     */
    public static final int SINCRONIZACION_COMPLETA = 1;

    /**
     * Valor para la sincronizacion incompleta
     */
    public static final int SINCRONIZACION_INCOMPLETA = -1;

    /**
     * Valor para la sincronizacion con certificado duplicado
     */
    public static final int SINCRONIZACION_CERTIFICADO_REPETIDO = -2;

    /**
     * Valor para la opcion seleccione
     */
    public static final int VALOR_SELECCIONE = 99;

    /**
     * Valor para la opcion seleccione para manejatr la dpa manzana
     */
    public static final int VALOR_SELECCIONE_DPA = -1;

    /**
     * Valor maximo de hogar final
     */
    public static final int MAXIMO_VALOR_HOGAR_FINAL = 8;

    /**
     * Valor Cadenas Vacias
     */
    public static final String CADENAS_VACIAS = "";

    /**
     * Valor Cadenas Vacias
     */
    public static final Integer ENTEROS_VACIOS = 99;

    /**
     * numero de visitas que se puede hacer en el formulario
     */
    public static final int NUMERO_VISITAS_MAXIMO = 3;

    /**
     * Valor para la opcion SI
     */
    public static final int SI = 1;

    /**
     * Valor para la opcion NO
     */
    public static final int NO = 2;

    public static final int INFORMACION_COMPLETA = 1;
    public static final int INFORMACION_INCOMPLETA = 0;
    /**
     * Valor para la opcion edad parentesco
     */
    public static final int EDAD_PARENTESCO = 12;

    /**
     * Valor para la edad de 18 años
     */
    public static final int EDAD_18ANIOS = 18;

    /**
     * Valor para la edad de 12 años
     */
    public static final int EDAD_12ANIOS = 12;

    /**
     * VAlor de mujer
     */
    public static final int GENERO_FEMENINO = 2;

    /**
     * Valor para la opcion SI
     */
    public static final int FECHA_NACIMIENTO = 1;

    /**
     * Valor para la opcion NO
     */
    public static final int ANIOS_CUMPLIDOS = 2;

    /**
     * Calidad de la foto entre 0-100
     */
    public static final int CALIDAD_FOTO = 70;



}
