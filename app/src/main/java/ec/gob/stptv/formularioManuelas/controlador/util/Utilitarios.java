package ec.gob.stptv.formularioManuelas.controlador.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.GZIPOutputStream;

import ec.gob.stptv.formularioManuelas.modelo.entidades.Localizacion;

/**
 * Created by lmorales on 14/07/17.
 */

public class Utilitarios {

    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * Método que verifica la conexión a internet
     *
     * @param ctx
     * @return
     */
    public static boolean verificarConexion(Context ctx) {
        boolean status = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            }
        }
        return status;
    }

    /**
     * Método que remueve los elementos de un array
     *
     * @param adapter
     */
    public static void removeAll(ArrayAdapter<?> adapter) {
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Método que imprimi logs de informacion dependiendo el ambiente de desarrollo
     *
     * @param clase
     * @param msg
     */
    public static void logInfo(String clase, String msg) {
        if (Global.ENVIRONMENT.equals(Global.DEVELOPMENT_ENVIRONMENT)) {
            Log.i(clase, msg);
        }
    }

    /**
     * Método que imprimi logs de erro dependiendo el ambiente de desarrollo
     *
     * @param clase
     * @param msg
     */
    public static void logError(String clase, String msg) {
        if (Global.ENVIRONMENT.equals(Global.DEVELOPMENT_ENVIRONMENT)) {
            Log.e(clase, msg);
        }
    }

    /**
     * Método que imprime los errores capturados en un try catch
     *
     * @param clase
     * @param msg
     * @param e
     */
    public static void logError(String clase, String msg, Exception e) {
        if (Global.ENVIRONMENT.equals(Global.DEVELOPMENT_ENVIRONMENT)) {
            Log.e(clase, msg, e);
        }
    }

    /**
     * Método que permite obtener la mac address
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {

        WifiManager wifiMan = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        String macAddr = wifiInf.getMacAddress();
        /*
		 * Quitar estas lineas de codigo en produccion, porque solo es para
		 * test en el emulador
		 */
        System.out.println("MAC address info---- " + macAddr);
        if (macAddr == null) {
            macAddr = "60:21:C0:A3:FC:00";

        }
        return macAddr;
    }

    /**
     * Método que permite obtener el imei del dispositivo
     *
     * @param context
     * @return
     */
    public static String getImeiDispositivo(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
        //return "357103040245095";
    }

    /**
     * Método que permite obtener la fecha en un formato establecido
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {

        Date fechaActual = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        return formato.format(fechaActual);
    }

    /**
     * Método que permite obtener el año dado una fecha
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentYear() {

        Date fechaActual = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy");

        return formato.format(fechaActual);
    }


    /**
     * Obtiene la fecha y hora según un formato establecido
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateAndHour() {

        Date fechaActual = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(fechaActual);
    }


    /**
     * Metódo que permite codificar la foto a color
     *
     * @param image
     * @param calidadFoto
     * @return
     */
    public static String encodeTobase64ImageColor(Bitmap image, int calidadFoto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int targetW = image.getWidth();
        int targetH = image.getHeight();
        int newW = Global.ANCHO_FOTO;
        float division = (float) targetH / (float) targetW;
        int newH = (int) (division * newW);
        Bitmap immagex = getResizedBitmap(image, newW, newH);
        immagex.compress(Bitmap.CompressFormat.JPEG, calidadFoto, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    /**
     * Método para definir el ancho y alto de una imagen
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth,
                newHeight, true);
        return resizedBitmap;
    }

    /**
     * Método que transforma una imagen a base64
     *
     * @param input
     * @return
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * Metódo que permite codificar la foto a blanco y negro
     *
     * @param _image
     * @param calidadFoto
     * @return
     */
    public static String encodeTobase64ImageBlackAndWhite(Bitmap _image, int calidadFoto) {
        Bitmap image = convertToBlackAndWhite(_image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int targetW = image.getWidth();
        int targetH = image.getHeight();
        int newW = Global.ANCHO_FOTO;
        float division = (float) targetH / (float) targetW;
        int newH = (int) (division * newW);
        Bitmap immagex = getResizedBitmap(image, newW, newH);
        immagex.compress(Bitmap.CompressFormat.JPEG, calidadFoto, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    /**
     * Método que convierte una imagen a blanco y negro
     *
     * @param sampleBitmap
     * @return
     */
    public static Bitmap convertToBlackAndWhite(Bitmap sampleBitmap) {
        ColorMatrix bwMatrix = new ColorMatrix();
        bwMatrix.setSaturation(0);
        final ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(bwMatrix);
        Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);
        Canvas myCanvas = new Canvas(rBitmap);
        myCanvas.drawBitmap(rBitmap, 0, 0, paint);
        return rBitmap;
    }

    /**
     * Método que permite obtener la fecha en un formato establecido
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(int year, int monthOfYear, int dayOfMonth) {

        GregorianCalendar fechaActual = new GregorianCalendar(year,
                monthOfYear, dayOfMonth);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        return formato.format(fechaActual.getTime());

    }

    /**
     * Método que permite obtener la posicion de la lista de un Spinner dado un valor
     *
     * @param values
     * @param index
     * @return
     */
    public static int getPosicionByKey(ArrayAdapter<Values> values,
                                       String index) {
        int posicion = -1;
        for (int i = 0; i < values.getCount(); i++) {
            if (values.getItem(i).getKey().equals(index)) {
                posicion = i;
            }
        }
        return posicion;
    }

    /**
     * Método que permite obtener el valor de la lista de un Spinner dado un valor
     *
     * @param values
     * @param index
     * @return
     */
    public static Values getValueByKey(ArrayAdapter<Values> values,
                                       String index) {
        Values value = new Values("0", "0");
        for (int i = 0; i < values.getCount(); i++) {
            if (values.getItem(i).getKey().equals(index)) {
                value = values.getItem(i);
            }
        }
        return value;
    }

    /**
     * Método que permite obtener la posicion de un array list
     *
     * @param values
     * @param index
     * @return
     */
    public static int getPosicionByKey(ArrayList<Values> values,
                                       String index) {
        int posicion = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getKey().equals(index)) {
                posicion = i;
            }
        }
        return posicion;

    }

    /**
     * Método que permite mostrar alertas
     *
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static Dialog getDialogAlert(Context context, String title,
                                        String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    /**
     * Método que permite habilitar o deshabilitar vistas en un layout
     *
     * @param context
     * @param enable    Parametro true o false para deshabilitar/habilitar
     * @param viewGroup El layout a deshabilitar/habilitar
     */
    public static void disableEnableViews(Context context, boolean enable, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child.getClass() == Button.class) {
                child.setEnabled(enable);
            }
            if (child.getClass() == TableRow.class) {
                child.setClickable(enable);
                child.setEnabled(enable);
            }
            if (child instanceof ViewGroup) {
                disableEnableViews(context, enable, (ViewGroup) child);
            }
        }
    }
    /**
     * Metodo para imprimir los valores de un objeto json
     *
     * @param object El objeto a imprimir
     */
    public static void printObject(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(object);
        Utilitarios.logInfo("Entidad " + object.getClass().toString(), json);
    }

    /**
     * Metodo para validar la cedula
     *
     * @param cedula
     * @return
     */
    public static boolean validadorCedula(String cedula) {
        int total = 0;
        int tamanoLongitudCedula = 10;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int numeroProvincias = 24;
        int tercerDigito = 6;
        if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {
            int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));
            int digitoTres = Integer.parseInt(cedula.charAt(2) + "");
            if (((provincia > 0 && provincia <= numeroProvincias) || provincia == 30) && digitoTres < tercerDigito) {
                int digitoVerificador = Integer.parseInt(cedula.charAt(9) + "");
                for (int i = 0; i < coeficientes.length; i++) {
                    int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");
                    total = valor >= 10 ? total + (valor - 9) : total + valor;
                }
                int digitoVerificardorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : 10 - total;
                if (digitoVerificador == digitoVerificardorObtenido) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que permite ocultar el teclado
     *
     * @param rootView
     * @param context
     */
    public static void hideKeyboard(View rootView, Context context) {

        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    /**
     * Método que valida que no ingrese espacios en blanco en un edittext al inicio
     *
     * @param editText
     * @return
     */
    public static TextWatcher clearSpaceEditText(final EditText editText) {
        return (new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (editText.getText().length() == 1) {
                        String val = s.toString();
                        if (val.equals(" ")) {
                            s.clear();
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Do something
                }
            }
        });
    }

    /**
     * Método que valida que ingrese la primera letra S
     *
     * @param editText
     * @return
     */
    public static TextWatcher letraSEditText(final EditText editText) {
        return (new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (editText.getText().length() == 0) {
                        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    }
                    if (editText.getText().length() == 1) {
                        String val = s.toString();
                        if (!(val.equals("S"))) {
                            s.clear();
                        }
                    }
                    if (editText.getText().length() >= 1) {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                } catch (NumberFormatException ex) {
                    // Do something
                }
            }
        });
    }

    /**
     * Método que valida que ingrese el primer digito 0
     *
     * @param editText
     * @return
     */
    public static TextWatcher numeroCeroEditText(final EditText editText) {
        return (new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (editText.getText().length() == 1) {
                        String val = s.toString();
                        if (!(val.equals("0"))) {
                            s.clear();
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Do something
                }
            }
        });
    }

    /**
     * Método que permite crear un archivo para los Log
     *
     * @param text
     */
    public static void createFileLog(String text) {
        if (Global.ENVIRONMENT.equals(Global.DEVELOPMENT_ENVIRONMENT)) {
            try {
                File file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "test_"
                        + Utilitarios.getCurrentDateAndHour() + ".txt");
                file.createNewFile();
                if (file.exists()) {
                    OutputStream fo = new FileOutputStream(file);
                    fo.write(text.toString().getBytes());
                    fo.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Método que permite crear un archivo para los Log
     *
     * @param text
     * @param tipo
     */
    public static void createFileLog(String text, int tipo) {
        if (Global.ENVIRONMENT.equals(Global.DEVELOPMENT_ENVIRONMENT)) {
            try {
                File file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "test_"
                        + tipo + ".txt");
                file.createNewFile();
                if (file.exists()) {
                    OutputStream fo = new FileOutputStream(file);
                    fo.write(text.toString().getBytes());
                    fo.close();

                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Método que valida si mi cadena está teniendo sólo dígitos
     *
     * @param str
     * @return
     */
    public static boolean isIntegerRegex(String str) {
        return str.matches("^[0-9]+$");
    }

    /**
     * Método que permite comprimir
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static final byte[] compress(final byte[] input) throws IOException {
        return compress(new ByteArrayInputStream(input));
    }

    public static final byte[] compress(final InputStream is) throws IOException {
        GZIPOutputStream gzos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            gzos = new GZIPOutputStream(baos);
            copy(is, gzos);
            gzos.finish();
            return baos.toByteArray();
        } finally {
            closeQuietly(gzos);
        }
    }

    private static long copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 4K buffer
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    private static void closeQuietly(final OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException i) {
            // ignore any IOException's, this is closeQuietly
        }
    }

    /**
     * Método que permite sacar el backup de una base de datos
     *
     * @param context
     */
    public static void backupDataBase(Context context) {
        final String BD_RUTA = "/data/data/" + Global.PACKAGE_NAME + "/databases/";
        final String BD_NOMBRE = "NombreBase.sqlite";
        String inFileName = BD_RUTA + BD_NOMBRE;
        try {
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            String outFileName = Environment.getExternalStorageDirectory() + Global.PATH_LOGS + "/backupNombreProyecto_" + getImeiDispositivo(context) + "_" + getCurrentDateAndHour() + ".sqlite";
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            Log.e("", e.toString());
        }
    }


    /**
     * Método que escribe en un archivo segun la ruta establecida
     *
     * @param typeLog
     * @param date
     * @param tag
     * @param message
     */
    public static void appendLog(String typeLog, String date, String tag, String message) {
        String stringLog = typeLog + "\t" + date + "\t" + tag + "\t" + message;
        File logFile = new File(Environment.getExternalStorageDirectory() + Global.PATH_LOGS + "/formularioManuelas.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                    true));
            buf.append(stringLog);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que permite crear una carpeta en el dispositivo
     *
     * @param name
     */
    public static void createFolder(String name) {
        File dir = new File(Environment.getExternalStorageDirectory() + name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Método que permite enviar un email
     *
     * @param contex
     * @param to
     * @param subject
     * @param body
     */
    public static void sendMailHtml(Context contex, String[] to, String subject, String body) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
        contex.startActivity(Intent.createChooser(emailIntent, "Email:"));
    }

    /**
     * Método que lanza una aplicacion
     *
     * @param context
     * @param packageName
     */
    public static void launchApp(Context context, String packageName) {
        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(
                packageName);
        if (mIntent != null) {
            try {
                context.startActivity(mIntent);
            } catch (ActivityNotFoundException err) {
                Toast t = Toast.makeText(context,
                        "Aplicación no instalada", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    /**
     * Método que calcula la edad años meses  y dias
     *
     * @param fechaNacimiento
     * @return
     */
    public static String calcularEdad(Date fechaNacimiento) {

        Date actual = new Date();
        Integer dia;
        Integer mes;
        Integer anio;
        Integer diaNacimiento;
        Integer mesNacimiento;
        Integer anioNacimiento;
        dia = obtenerDia(actual);
        mes = obtenerMes(actual);
        anio = obtenerAnio(actual);
        diaNacimiento = obtenerDia(fechaNacimiento);
        mesNacimiento = obtenerMes(fechaNacimiento);
        anioNacimiento = obtenerAnio(fechaNacimiento);
        if (diaNacimiento > dia) {
            mes = mes - 1;
            dia = dia + 30;
            dia = dia - diaNacimiento;
        } else {
            dia = dia - diaNacimiento;
        }
        if (mesNacimiento > mes) {
            anio = anio - 1;
            mes = mes + 12;
            mes = mes - mesNacimiento;
        } else {
            mes = mes - mesNacimiento;
        }
        anio = anio - anioNacimiento;

        Integer edadDias = (dia);
        Integer edadMeses = (mes);
        Integer edadAnios = (anio);

        return (String.valueOf(edadAnios) + "-" + String.valueOf(edadMeses) + "-" + String.valueOf(edadDias));
    }

    private static Integer obtenerAnio(Date date) {
        if (null == date) {
            return 0;
        } else {
            String formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));
        }
    }

    private static Integer obtenerMes(Date date) {
        if (null == date) {
            return 0;
        } else {
            String formato = "MM";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));
        }
    }

    private static Integer obtenerDia(Date date) {
        if (null == date) {
            return 0;
        } else {
            String formato = "dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));
        }
    }

    /**
     * Método que calcula la edad en años
     *
     * @param fecha
     * @return
     */
    public static int getEdad(String fecha) {

        Date fechaNac = null;
        try {
            /**
             * Se puede cambiar la mascara por el formato de la fecha que se
             * quiera recibir, por ejemplo año mes día "yyyy-MM-dd" en este caso
             * es día mes año
             */
            fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        // Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        // Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        // Se restan la fecha actual y la fecha de nacimiento
        int anio = fechaActual.get(Calendar.YEAR)
                - fechaNacimiento.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH)
                - fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)
                - fechaNacimiento.get(Calendar.DATE);
        // Se ajusta el año dependiendo el mes y el día
        if (mes < 0 || (mes == 0 && dia < 0)) {
            anio--;
        }
        // Regresa la edad en base a la fecha de nacimiento
        return anio;
    }

    /**
     * Método que permite obtener la fecha dado un formato
     *
     * @param fecha
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatoFecha(String fecha) {

        Date dateComponente = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dateComponente = formatoFecha.parse(fecha);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String fechaNacimientoCadena = formatoFecha.format(dateComponente);

        return fechaNacimientoCadena;
    }

    /**
     * Permite que se active el boton guardar dependiendo un cierto numero de caracteres
     *
     * @param editText
     * @param numero
     * @return
     */
    public static TextWatcher habilitarBotonCaracteresEditText(final EditText editText, final int numero, final Button button, final TextView textView, final int total) {
        return (new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                textView.setText("Caracteres:" + (total - s.length()) + " de 255");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (editText.getText().length() > numero) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

        });

    }

    /**
     * Oculta el teclado
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static Boolean validarCodigoRegion(String convencional) {
        Integer primerNumero = Integer.parseInt(convencional.substring(0, 1));
        Integer segundoNumero = Integer.parseInt(convencional.substring(1, 2));
        if (primerNumero == 0) {
            return segundoNumero > 1 && segundoNumero < 8;
        } else {
            return false;
        }

    }

    /**
     * Metodo para obtener los puntos mas exactos de una ubicacion
     * @param localizaciones Puntos capturados
     * @param max numeros de puntos a obtener
     * @return Retorna un ArrayList filtrado con los puntos mas exactos
     */
    public static ArrayList<Localizacion> getLocalizacionesPorPresicion(ArrayList<Localizacion> localizaciones, int max) {

        ArrayList<Localizacion> localizacinesGPS = new ArrayList<Localizacion>();
        ArrayList<Localizacion> localizacinesNetwork = new ArrayList<Localizacion>();
        ArrayList<Localizacion> localizacinesFinal = new ArrayList<Localizacion>();

        //Primero buscamos los puntos del proveedor GPS
        for (Localizacion localizacion : localizaciones)
        {
            if(localizacion.getProveedor().equals(LocationManager.GPS_PROVIDER))
            {
                localizacinesGPS.add(localizacion);
            }
        }
        //Ordenamos de forma ascendente por la presicion
        Collections.sort(localizacinesGPS, new Comparator<Localizacion>() {
            @Override
            public int compare(Localizacion  localizacion1, Localizacion  localizacion2)
            {
                return  localizacion1.getPresicion().compareTo(localizacion2.getPresicion());
            }
        });

        // Escogemos los n primeros valores
        if(localizacinesGPS.size() > 0)
        {
            int i = 0;
            while (i < localizacinesGPS.size() && i < max) {
                localizacinesFinal.add(localizacinesGPS.get(i));
                i++;
            }
        }
        else
        {// Sino buscamos los puntos del proveedor Network
            for (Localizacion localizacion : localizaciones)
            {
                if(localizacion.getProveedor().equals(LocationManager.NETWORK_PROVIDER))
                {
                    localizacinesNetwork.add(localizacion);
                }
            }

            //Ordenamos de forma ascendente por la presicion
            Collections.sort(localizacinesNetwork, new Comparator<Localizacion>() {
                @Override
                public int compare(Localizacion  localizacion1, Localizacion  localizacion2)
                {
                    return  localizacion1.getPresicion().compareTo(localizacion2.getPresicion());
                }
            });

            if(localizacinesNetwork.size() > 0)
            {
                int i = 0;
                while (i < localizacinesNetwork.size() && i < max) {
                    localizacinesFinal.add(localizacinesNetwork.get(i));
                    i++;
                }
            }
        }
        return localizacinesFinal;
    }

}
