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
     * Atributos para el manejo de logs
     */
    public static final String INFO = "I";
    public static final String ERROR = "E";
    public static final String PATH_LOGS = "/logs/NombreProyecto";

    /**
     * Ancho de la foto de vivienda y certificado
     */
    public static final int ANCHO_FOTO = 650;

    /**
     * Cadena para el servicio de localizacion
     */
    public static final String BROADCAST_ACTION_PROGRESS =
            "ec.gob.institucion.nombreAplicacion.action.PROGRESS";

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
    public static final int SINCRONIZACION_INCOMPLETA = 0;

    /**
     * Valor para la sincronizacion con certificado duplicado
     */
    public static final int SINCRONIZACION_CERTIFICADO_REPETIDO = 2;

    /**
     * Valor para la opcion seleccione
     */
    public static final int VALOR_SELECCIONE = 99;

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
}
