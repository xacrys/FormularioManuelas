package ec.gob.stptv.formularioManuelas.modelo.bd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

/**
 * Clase que permite crear, actualizar, y conectar con una base de datos SQLite
 */
public class BaseDatosHelper extends SQLiteOpenHelper {
    // Ruta por defecto de las bases de datos en el sistema Android
    protected  static String BD_RUTA = "/data/data/" + Global.PACKAGE_NAME + "/databases/";
    //nombre de la base
    protected  static String BD_NOMBRE = "REGISTROMANUELAS.sqlite";

    protected  static SQLiteDatabase baseDatos;

    protected  final Context contexto;
    private static String TAG = "BaseDatosHelper";
    /**
     * Constructor que toma referencia hacia el contexto de la aplicación que lo
     * invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
     * Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la
     * base de datos.
     *
     * @param context
     */
    public BaseDatosHelper(Context context) {

        super(context, BD_NOMBRE, null, 1);
        this.contexto = context;

    }

    /**
     * Crea una base de datos vacía en el sistema y la reescribe con nuestro
     * fichero de base de datos.
     * */
    public void crearBaseDatos() throws IOException {

        boolean activo = validarBaseDatos();

        if (activo) {
            //la base de datos existe y no hacemos nada.
        } else {
            //Llamando a este metodo se crea la base de datos vacía en la ruta
            //por defecto del sistema de nuestra aplicación por lo que podremos sobreescribirla con
            //nuestra base de datos.
            this.getReadableDatabase();
            try {
                copiarBaseDatos();
            } catch (IOException e) {
                throw new Error("Error copiando Base de Datos");
            }
        }

    }

    /**
     * Valida si existe la base de datos
     * @return
     */
    private boolean validarBaseDatos() {

        String myPath = BD_RUTA + BD_NOMBRE;
        File dbFile = new File(myPath);
        if (!dbFile.exists()) {
            Utilitarios.appendLog(Global.INFO, Utilitarios.getCurrentDateAndHour(), TAG, "Database: No existe la BD");
        } else {
            Utilitarios.appendLog(Global.INFO, Utilitarios.getCurrentDateAndHour(), TAG, "Database: Existe la BD");
        }
        return dbFile.exists();
    }

    /**
     * Copia nuestra base de datos desde la carpeta assets a la recién creada
     * base de datos en la carpeta de sistema, desde dónde podremos acceder a
     * ella. Esto se hace con bytestream.
     * */
    private void copiarBaseDatos() throws IOException {
        //Abrimos el fichero de base de datos como entrada
        InputStream myInput = contexto.getAssets().open(BD_NOMBRE);

        //Ruta a la base de datos vacía recién creada
        String outFileName = BD_RUTA + BD_NOMBRE;

        //Abrimos la base de datos vacía como salida
        OutputStream myOutput = new FileOutputStream(outFileName);

        //Transferimos los bytes desde el fichero de entrada al de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Liberamos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * abre la conexion
     * @throws SQLException
     */
    public void abrirConeccion() throws SQLException {
        String ruta = BD_RUTA + BD_NOMBRE;
        baseDatos = SQLiteDatabase.openDatabase(ruta, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (baseDatos != null)
            baseDatos.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
