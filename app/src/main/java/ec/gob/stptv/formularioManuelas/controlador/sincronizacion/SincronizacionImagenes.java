package ec.gob.stptv.formularioManuelas.controlador.sincronizacion;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import ec.gob.stptv.formularioManuelas.controlador.actividades.ImagenesActivity;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.dao.ImagenDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Imagen;

public class SincronizacionImagenes {

	private ContentResolver cr;
	private Gson gson;
	
	public SincronizacionImagenes() {

	}
	
	public SincronizacionImagenes(Context context) {
		cr = context.getContentResolver();
		gson = new GsonBuilder().serializeNulls().create();
		
	}
	
	public void sincronizarAll(final Imagen imagen, Activity activity) {
		String json = gson.toJson(imagen);
		Utilitarios.logError("JSON", json);
		ImagenSincronizacionTask imagenSincronizacionTask = new ImagenSincronizacionTask();
		DefaultHttpClient httpClient = new DefaultHttpClient();

			try {
				imagenSincronizacionTask.execute(json, imagen, activity, httpClient).get(Global.ASYNCTASK_TIMEROUT_IMAGENES, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			} catch (TimeoutException e1) {
				Log.e("", "Termino el tiempo de coneccion");
				httpClient.getConnectionManager().shutdown();
				imagenSincronizacionTask.cancel(true);
				e1.printStackTrace();
			}
	} 
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class ImagenSincronizacionTask extends AsyncTask<Object, Void, String> {
		
		Imagen imagen;
		private Activity activity;
		private DefaultHttpClient httpClient;
		
		
		@Override
		protected String doInBackground(Object... params) {
			
			String json = (String)params[0];
			imagen = (Imagen)params[1];
			activity = (Activity)params[2];
			httpClient = (DefaultHttpClient)params[3];

			String _respuesta = WebService.sendJsonCompres(Global.URL_WEB_SERVICE_IMAGENES, json, httpClient);
			Utilitarios.logInfo(SincronizacionImagenes.class.getName(), "respuesta" + _respuesta);
			
			
			return _respuesta;
		}

		@Override
		protected void onPostExecute(String _respuesta) {
			if (!_respuesta.equals("")) {
				
				if(_respuesta.equals(String.valueOf(Global.SINCRONIZACION_COMPLETA)))
				{
					
					imagen.setFechasincronizacion(Utilitarios.getCurrentDate());
					imagen.setEstadosincronizacion(Global.SINCRONIZACION_COMPLETA);
					//ImagenDao.updateByViviendaId(cr, imagen);
					((ImagenesActivity)activity).changeRowSincronizada(imagen);
					//((ImagenesActivity)activity).buscarImagenes();
					
				}
				else
				{
					if(_respuesta.equals(String.valueOf(Global.SINCRONIZACION_INCOMPLETA)))
					{
						imagen.setFechasincronizacion(Utilitarios.getCurrentDate());
						imagen.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
						//ImagenDao.updateByViviendaId(cr, imagen);
					}
					
				}
			}
			else
			{
				imagen.setFechasincronizacion(Utilitarios.getCurrentDate());
				imagen.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
				//ImagenDao.updateByViviendaId(cr, imagen);
			}
			
			((ImagenesActivity)activity).closeProgressBar();
			
		}
		@Override
		protected void onCancelled() {

			if(activity != null)
			{
				((ImagenesActivity)activity).closeProgressBar();
			}

			super.onCancelled();
		}
	}
		
}
