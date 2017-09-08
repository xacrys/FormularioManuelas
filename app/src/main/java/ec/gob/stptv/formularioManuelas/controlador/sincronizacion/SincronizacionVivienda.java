package ec.gob.stptv.formularioManuelas.controlador.sincronizacion;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.actividades.FormulariosActivity;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.dao.HogarDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.LocalizacionDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.PersonaDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Localizacion;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class SincronizacionVivienda {
	private ContentResolver cr;
	//private Vivienda vivienda; 
	private Gson gson;
	private ProgressDialog mProgressDialog;

	public SincronizacionVivienda(Context context) {
		cr = context.getContentResolver();
		gson = new GsonBuilder().serializeNulls().create();
		
	}
	
	
	public void sincronizar(final Vivienda vivienda, final Activity activity) {
		
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setMessage("Sincronizando la ficha esto puede tardar hasta 2 minutos,  por favor no cierre la ventana y espere... si no sincroniza intentelo mas tarde");
		mProgressDialog.setIcon(android.R.drawable.stat_sys_upload);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setTitle("Sincronizacion");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		
		//vivienda.setVivCodigo(vivienda.getCodigoEquipo() + "-" + vivienda.getId());
		vivienda.setVivcodigo(vivienda.getVivcodigo());
		String where = null;
		String parametros[];
		
		if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()){

			where = Hogar.whereByViviendaId;
			parametros = new String[] { String.valueOf(vivienda.getId()) };
			Hogar hogar = HogarDao.getHogar(cr, where, parametros);
			vivienda.setHogar(hogar);

			where = Persona.whereByIdHogar;
			parametros = new String[] { String.valueOf(hogar.getId()) };
			ArrayList<Persona> personas = PersonaDao.getPersonas(cr, where, parametros, null);
			hogar.setListaPersonas(personas);
		}
		
		where = Localizacion.whereByViviendaId;
		parametros = new String[] { String.valueOf(vivienda.getId()) };
		ArrayList<Localizacion> localizaciones = LocalizacionDao.getLocalizaciones(cr, where, parametros, null);
		vivienda.setListaLocalizacion(localizaciones);
		
		final String json = gson.toJson(vivienda);
		Utilitarios.logInfo("servicio web json", json);
		
		new Thread(new Runnable() {
			
			ViviendaSincronizacionTask preregistroSincronizacionTask = new ViviendaSincronizacionTask();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
            public void run() {
            	try {
            		preregistroSincronizacionTask.execute(json, vivienda, httpClient, activity).get(Global.ASYNCTASK_TIMEOUT, TimeUnit.MILLISECONDS);
    			} catch (InterruptedException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			} catch (ExecutionException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			} catch (TimeoutException e1) {
    				Log.e("", "Termino el tiempo - Formulario");
    				httpClient.getConnectionManager().shutdown();
    				preregistroSincronizacionTask.cancel(true);
    				e1.printStackTrace();
    			}
            }
        }).start();
		
		Utilitarios.createFileLog(json);

		} 
	
	
	
	/**
	 * Clase para la sincronizacion de la entidad preregistro en un hilo secundario
	 * @author
	 *
	 */
	public class ViviendaSincronizacionTask extends AsyncTask<Object , Void, String> {
		
		Vivienda vivienda;
		DefaultHttpClient httpClient;
		Activity activity;
		
		@Override
		protected String doInBackground(Object... params) {
		
			String json = (String)params[0];
			vivienda = (Vivienda)params[1];
			httpClient = (DefaultHttpClient)params[2];
			activity = (Activity)params[3];
			String _respuesta = WebService.sendJson(Global.URL_WEB_SERVICE_INGRESO_FORMULARIO, json, httpClient);
			Utilitarios.logError("respuesta", "*" + _respuesta + "*");
			return _respuesta;
		}

		@Override
		protected void onPostExecute(String _respuesta) {
			mProgressDialog.dismiss();
			if (!_respuesta.equals("")) {
				if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_COMPLETA))) {

					Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion completa");
					vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
					vivienda.setEstadosincronizacion(Global.SINCRONIZACION_COMPLETA);
					ViviendaDao.update(cr, vivienda);
					getAlertDialog(activity.getString(R.string.confirmacion_aviso), activity.getString(R.string.sinronizacion_correcta), activity);

				} else {

					if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_INCOMPLETA))) {
						Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion incompleta");
						vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
						vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
						ViviendaDao.update(cr, vivienda);
						getAlertDialog(activity.getString(R.string.confirmacion_aviso), activity.getString(R.string.sinronizacion_incorrecta), activity);
					}
					else
					{
						if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_CERTIFICADO_REPETIDO))) {

							Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion certificado ya existe en el servidor");
							vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
							vivienda.setEstadosincronizacion(Global.SINCRONIZACION_CERTIFICADO_REPETIDO);
							ViviendaDao.update(cr, vivienda);
							getAlertDialog(activity.getString(R.string.confirmacion_aviso), activity.getString(R.string.sinronizacion_duplicado), activity);
						}
					}
				}
			}
			else
			{
				Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion incompleta");
				vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
				vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
				ViviendaDao.update(cr, vivienda);
				getAlertDialog(activity.getString(R.string.confirmacion_aviso), activity.getString(R.string.sinronizacion_incorrecta), activity);
			}


		}
		
		@Override
		protected void onCancelled() {

			if(activity != null)
			{
				mProgressDialog.dismiss();
				activity.finish();
				
			}
			
			
			super.onCancelled();
		}
		
		private void getAlertDialog(String title, String message, final Activity activity) {

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);

			builder.setMessage(message).setTitle(title);

			builder.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							activity.finish();
						}
					});
			
			builder.setCancelable(false);
			AlertDialog dialog = builder.create();
			dialog.show();

		}
	}
	
	public void sincronizarAll(final Vivienda vivienda, Activity activity) {

		//vivienda.setVivcodigo(vivienda.getCodigoEquipo() + "-" + vivienda.getId());
		vivienda.setVivcodigo(vivienda.getVivcodigo());
		String where = null;
		String parametros[];
		
		if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()){
			where = Hogar.whereByViviendaId;
			parametros = new String[] { String.valueOf(vivienda.getId()) };
			Hogar hogar = HogarDao.getHogar(cr, where, parametros);
			vivienda.setHogar(hogar);

			where = Persona.whereByIdHogar;
			parametros = new String[] { String.valueOf(hogar.getId()) };
			ArrayList<Persona> personas = PersonaDao.getPersonas(cr, where, parametros, null);
			hogar.setListaPersonas(personas);
		}

		where = Localizacion.whereByViviendaId;
		parametros = new String[] { String.valueOf(vivienda.getId()) };
		ArrayList<Localizacion> localizaciones = LocalizacionDao.getLocalizaciones(cr, where, parametros, null);
		vivienda.setListaLocalizacion(localizaciones);
		
		final String json = gson.toJson(vivienda);
		Utilitarios.logInfo("servicio web json", "codigo de vivienda:"+  vivienda.getId() +"---"+  json);

		ViviendasSincronizacionProgressTask viviendasSincronizacionProgressTask = new ViviendasSincronizacionProgressTask();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			viviendasSincronizacionProgressTask.execute(json, vivienda, activity, httpClient).get(Global.ASYNCTASK_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			Log.e("", "Termino el tiempo de coneccion");
			httpClient.getConnectionManager().shutdown();
			viviendasSincronizacionProgressTask.cancel(true);
			e1.printStackTrace();
		}
		
		Utilitarios.createFileLog(json);
	
	}	
	
	
	
	/**
	 * Clase para la sincronizacion de la entidad preregistros en un hilo secundario cada 
	 * 	 * @author
	 *
	 */
	public class ViviendasSincronizacionProgressTask extends AsyncTask<Object , Void, String> {
		
		Vivienda vivienda;
		DefaultHttpClient httpClient;
		Activity activity;
		
		@Override
		protected String doInBackground(Object... params) {

			String json = (String)params[0];
			vivienda = (Vivienda)params[1];
			activity = (Activity)params[2];
			httpClient =  (DefaultHttpClient)params[3];
			Log.e("servicio", json);
			String _respuesta = WebService.sendJson(Global.URL_WEB_SERVICE_INGRESO_FORMULARIO, json, httpClient);
			Log.i("respuesta", "*" + _respuesta + "*");
			return _respuesta;
		}

		@Override
		protected void onPostExecute(String _respuesta) {
			if (!_respuesta.equals("")) {
				if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_COMPLETA))) {

					Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion completa");
					vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
					vivienda.setEstadosincronizacion(Global.SINCRONIZACION_COMPLETA);
					ViviendaDao.update(cr, vivienda);

				} else {

					if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_INCOMPLETA))) {
						Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion incompleta");
						vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
						vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
						ViviendaDao.update(cr, vivienda);
					}
					else
					{
						if (_respuesta.equals(String.valueOf(Global.SINCRONIZACION_CERTIFICADO_REPETIDO))) {

							Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion certificado ya existe en el servidor");
							vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
							vivienda.setEstadosincronizacion(Global.SINCRONIZACION_CERTIFICADO_REPETIDO);
							ViviendaDao.update(cr, vivienda);
						}
					}
				}
			}
			else
			{
				Utilitarios.logInfo(SincronizacionVivienda.class.getName(), "sincronizacion incompleta");
				vivienda.setFechasincronizacion(Utilitarios.getCurrentDate());
				vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
				ViviendaDao.update(cr, vivienda);
			}
			((FormulariosActivity)activity).buscarFormularios();
			((FormulariosActivity)activity).closeProgressBar();
		}
		
		@Override
		protected void onCancelled() {
			
			if(activity != null)
			{
				((FormulariosActivity)activity).closeProgressBar();
			}

			super.onCancelled();
		}
	}
	
}
