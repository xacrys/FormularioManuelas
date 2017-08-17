package ec.gob.stptv.formularioManuelas.modelo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.bd.BaseDatosHelper;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localidad;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localizacion;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

/**
 * Clase que envuelva la base de datos de la aplicación para uso externo o Local.
 * Tiene métodos de consulta de datos, inserción, modificación y eliminación a través de un Content Resolver.
 */
public class FormularioManuelasProvider extends ContentProvider {

    private BaseDatosHelper helperBd;
    private SQLiteDatabase dataBase;

    //Esquema que indica al framework de Android que la URI se refiere a un content provider.
    private static final String ESQUEMA = "content://";
    //Representa la identificación única del Content Provider sobre otros.
    public static final String AUTORIDAD = "ec.gob.stptv.formularioManuelas.modelo.provider.FormularioManuelas";

    // Definicion del CONTENT_URI (URI sirven para identificar los recursos)
    private static final String uri_vivienda = ESQUEMA + AUTORIDAD + "/viviendas";
    private static final String uri_hogar = ESQUEMA + AUTORIDAD + "/hogares";
    private static final String uri_persona = ESQUEMA + AUTORIDAD + "/personas";
    private static final String uri_usuario = ESQUEMA + AUTORIDAD + "/usuarios";
    private static final String uri_fase = ESQUEMA + AUTORIDAD	+ "/fases";
    private static final String uri_localizacion = ESQUEMA + AUTORIDAD + "/localizaciones";
    private static final String uri_localidad = ESQUEMA + AUTORIDAD + "/localidades";

    public static final Uri CONTENT_URI_VIVIENDA = Uri.parse(uri_vivienda);
    public static final Uri CONTENT_URI_HOGAR = Uri.parse(uri_hogar);
    public static final Uri CONTENT_URI_PERSONA = Uri.parse(uri_persona);
    public static final Uri CONTENT_URI_USUARIO = Uri.parse(uri_usuario);
    public static final Uri CONTENT_URI_FASE = Uri.parse(uri_fase);
    public static final Uri CONTENT_URI_LOCALIZACION = Uri.parse(uri_localizacion);
    public static final Uri CONTENT_URI_LOCALIDAD = Uri.parse(uri_localidad);


    // Definicion del CONTENT_URI UriMatcher. Ayuda a distinguir entre una URI q retorna múltiples filas y una sola fila
    private static final UriMatcher uriMatcher;

    //Necesario para UriMatcher
    //Acceso genérico a tabla
    private static final int VIVIENDA = 1;
    //Acceso a tabla por ID
    private static final int VIVIENDA_ID = 2;

    private static final int HOGAR = 3;
    private static final int HOGAR_ID = 4;

    private static final int PERSONA = 5;
    private static final int PERSONA_ID = 6;

    private static final int USUARIO = 7;
    private static final int USUARIO_ID = 8;

    private static final int FASE = 9;
    private static final int FASE_ID = 10;

    private static final int LOCALIZACION = 11;
    private static final int LOCALIZACION_ID = 12;

    private static final int LOCALIDAD = 13;
    private static final int LOCALIDAD_ID = 14;


    // Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTORIDAD, "viviendas", VIVIENDA);
        uriMatcher.addURI(AUTORIDAD, "vivienda/#", VIVIENDA_ID);

        uriMatcher.addURI(AUTORIDAD, "hogares", HOGAR);
        uriMatcher.addURI(AUTORIDAD, "hogare/#", HOGAR_ID);

        uriMatcher.addURI(AUTORIDAD, "personas", PERSONA);
        uriMatcher.addURI(AUTORIDAD, "persona/#", PERSONA_ID);

        uriMatcher.addURI(AUTORIDAD, "usuarios", USUARIO);
        uriMatcher.addURI(AUTORIDAD, "usuario/#", USUARIO_ID);

        uriMatcher.addURI(AUTORIDAD, "fases",FASE);
        uriMatcher.addURI(AUTORIDAD, "fase/#",	FASE_ID);

        uriMatcher.addURI(AUTORIDAD, "localizaciones", LOCALIZACION);
        uriMatcher.addURI(AUTORIDAD, "localizacion/#", LOCALIZACION_ID);

        uriMatcher.addURI(AUTORIDAD, "localidades", LOCALIDAD);
        uriMatcher.addURI(AUTORIDAD, "localidad/#", LOCALIDAD_ID);
    }

    /**
     * Método se que utiliza para identificar el tipo de datos que devuelve el content provider.
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            //Lista de registros como resultado
            case VIVIENDA:
                return "vnd.android.cursor.dir/vnd.stptv.vivienda";
            //Un registro único concreto
            case VIVIENDA_ID:
                return "vnd.android.cursor.item/vnd.stptv.vivienda";

            case HOGAR:
                return "vnd.android.cursor.dir/vnd.stptv.hogar";
            case HOGAR_ID:
                return "vnd.android.cursor.item/vnd.stptv.hogar";

            case PERSONA:
                return "vnd.android.cursor.dir/vnd.stptv.persona";
            case PERSONA_ID:
                return "vnd.android.cursor.item/vnd.stptv.persona";


            default:
                return null;
        }
    }

    /**
     * Método que inicializa la base de datos
     * @return
     */
    @Override
    public boolean onCreate() {
        try {
            helperBd = new BaseDatosHelper(getContext());
            helperBd.crearBaseDatos();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        return (helperBd != null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Construye un nuevo generador de consultas
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //Elige la proyeccion y ajuste la clausula "where" basada en URI de patrones.
        switch (uriMatcher.match(uri)) {
            case VIVIENDA:
                qb.setTables(Vivienda.NOMBRE_TABLA);
                break;

            case HOGAR:
                qb.setTables(Hogar.NOMBRE_TABLA);
                break;

            case PERSONA:
                qb.setTables(Persona.NOMBRE_TABLA);
                break;

            case USUARIO:
                qb.setTables(Usuario.NOMBRE_TABLA);
                break;

            case FASE:
                qb.setTables(Fase.NOMBRE_TABLA);
                break;

            case LOCALIZACION:
                qb.setTables(Localizacion.NOMBRE_TABLA);
                break;

            case LOCALIDAD:
                qb.setTables(Localidad.NOMBRE_TABLA);
                break;

            default:
                // Si el URI no coincide con ninguno de los patrones conocidos, lanza una excepcion
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String orderBy = null;
        // Si no se especifica el orden de clasificacion, utiliza el valor por defecto
        if (TextUtils.isEmpty(sortOrder)) {
            // orderBy = DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        // Abre el objeto de base de datos en modo "leer", ya que no se escribe
        dataBase = helperBd.getReadableDatabase();
        Cursor c = qb.query(dataBase, projection, selection, selectionArgs,
                null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        Uri nuevaUri = null;
        try {
            dataBase = helperBd.getWritableDatabase();
            switch (uriMatcher.match(uri)) {
                case VIVIENDA:
                    id = dataBase
                            .insertOrThrow(Vivienda.NOMBRE_TABLA, null, values);
                    if (id > 0) {
                        nuevaUri = ContentUris.withAppendedId(CONTENT_URI_VIVIENDA,id);
                    }
                    break;

                case HOGAR:

                    id = dataBase.insertOrThrow(Hogar.NOMBRE_TABLA, null, values);
                    if (id > 0) {

                        Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                                "Registro insertado: Tabla: " + Hogar.NOMBRE_TABLA
                                        + " Valores: " + values.toString());
                        nuevaUri = ContentUris
                                .withAppendedId(CONTENT_URI_HOGAR, id);
                    }
                    break;

                case PERSONA:

                    id = dataBase.insertOrThrow(Persona.NOMBRE_TABLA, null, values);

                    if (id > 0) {
                        Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                                "Registro insertado: Tabla: "
                                        + Persona.NOMBRE_TABLA + " Id: " + id
                                        + " Valores: " + values.toString());
                        nuevaUri = ContentUris.withAppendedId(CONTENT_URI_PERSONA,
                                id);
                    }
                    break;

                case USUARIO:

                    id = dataBase.insertOrThrow(Usuario.NOMBRE_TABLA, null, values);

                    if (id > 0) {

                        Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                                "Registro insertado: Tabla: "
                                        + Usuario.NOMBRE_TABLA + " Valores: "
                                        + values.toString());
                        nuevaUri = ContentUris.withAppendedId(CONTENT_URI_USUARIO,
                                id);
                    }
                    break;

                case FASE:

                    id = dataBase.insertOrThrow(Fase.NOMBRE_TABLA, null, values);

                    if (id > 0) {

                        Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                                "Registro insertado: Tabla: "
                                        + Fase.NOMBRE_TABLA + " Valores: "
                                        + values.toString());
                        nuevaUri = ContentUris.withAppendedId(CONTENT_URI_FASE,
                                id);
                    }
                    break;

                case LOCALIZACION:

                    id = dataBase.insertOrThrow(Localizacion.NOMBRE_TABLA, null, values);

                    if (id > 0) {

                        Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                                "Registro insertado: Tabla: "
                                        + Localizacion.NOMBRE_TABLA + " Valores: "
                                        + values.toString());
                        nuevaUri = ContentUris.withAppendedId(CONTENT_URI_LOCALIZACION,
                                id);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("URI desconocida " + uri);
            }
            getContext().getContentResolver().notifyChange(nuevaUri, null);
        } catch (SQLiteException e) {
            Log.i("SQLiteException", e.getMessage());
        }
        return nuevaUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int filasAfectadas = 0;
        dataBase = helperBd.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case VIVIENDA:
                filasAfectadas = dataBase.update(Vivienda.NOMBRE_TABLA, values,
                        selection, selectionArgs);
                break;

            case HOGAR:

                filasAfectadas = dataBase.update(Hogar.NOMBRE_TABLA, values,
                        selection, selectionArgs);

                if (filasAfectadas > 0) {

                    Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                            "Registro actualizado: Tabla: " + Hogar.NOMBRE_TABLA
                                    + " Id: " + selectionArgs[0] + " Valores: "
                                    + values.toString());
                }
                break;

            case PERSONA:
                Log.i("", "ContetProvider PERSONAS ");

                filasAfectadas = dataBase.update(Persona.NOMBRE_TABLA, values,
                        selection, selectionArgs);


                if (filasAfectadas > 0) {

                    Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                            "Registro actualizado: Tabla: " + Persona.NOMBRE_TABLA
                                    + " Id: " + selectionArgs[0] + " Valores: Update " + Persona.NOMBRE_TABLA + "set "
                                    + values.toString() + " where " + selection + " valores: " + selectionArgs[0] + "-" + selectionArgs[1]);

                }
                break;


            case USUARIO:

                filasAfectadas = dataBase.update(Usuario.NOMBRE_TABLA, values,
                        selection, selectionArgs);

                if (filasAfectadas > 0) {

                    Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                            "Registro actualizado: Tabla: " + Usuario.NOMBRE_TABLA
                                    + " Id: " + selectionArgs[0] + " Valores: "
                                    + values.toString());
                }
                break;

            case FASE:

                filasAfectadas = dataBase.update(Fase.NOMBRE_TABLA, values,
                        selection, selectionArgs);

                if (filasAfectadas > 0) {
                    Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                            "Registro actualizado: Tabla: " + Fase.NOMBRE_TABLA
                                    + " Id: " + selectionArgs[0] + " Valores: "
                                    + values.toString());
                }
                break;

            case LOCALIZACION:

                filasAfectadas = dataBase.update(Localizacion.NOMBRE_TABLA, values,
                        selection, selectionArgs);

                if (filasAfectadas > 0) {
                    Utilitarios.logInfo(FormularioManuelasProvider.class.getName(),
                            "Registro actualizado: Tabla: " + Localizacion.NOMBRE_TABLA
                                    + " Id: " + selectionArgs[0] + " Valores: "
                                    + values.toString());
                }
                break;
        }
        return filasAfectadas;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int filaEliminada = 0;
        dataBase = helperBd.getWritableDatabase();
        String tabla = "";
        switch (uriMatcher.match(uri)) {
            case VIVIENDA:
                tabla = Vivienda.NOMBRE_TABLA;
                filaEliminada = dataBase.delete(tabla, selection, selectionArgs);
                break;

            case HOGAR:
                tabla = Hogar.NOMBRE_TABLA;
                filaEliminada = dataBase.delete(tabla, selection, selectionArgs);
                break;

            case PERSONA:
                tabla = Persona.NOMBRE_TABLA;
                filaEliminada = dataBase.delete(tabla, selection, selectionArgs);
                break;

            case FASE:
                tabla = Fase.NOMBRE_TABLA;
                filaEliminada = dataBase.delete(tabla, selection, selectionArgs);
                break;

            case LOCALIZACION:
                tabla = Localizacion.NOMBRE_TABLA;
                filaEliminada = dataBase.delete(tabla, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("URI desconocida " + uri);
        }
        return filaEliminada;
    }

}
