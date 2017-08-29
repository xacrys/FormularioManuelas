package ec.gob.stptv.formularioManuelas.controlador.servicio;

import android.R;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

public class LocalizacionService extends Service {
	
	private static final String TAG = "ec.gob.mcds.rips.fichafamiliar.controlador.service.localizacion";
	private LocationManager mLocationManager = null;
	
	private NotificationManager mNotificationManager;
	private int notifyID = 1;
	
	
	
	private class LocationListener implements android.location.LocationListener {
		Location mLastLocation;

		public LocationListener(String provider) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "LocationListener " + provider);
			mLastLocation = new Location(provider);
			
		}

		@Override
		public void onLocationChanged(Location location) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onLocationChanged: " + location);
			mLastLocation.set(location);
			
			//Comunicamos el progreso
            Intent bcIntent = new Intent();
            bcIntent.setAction(Global.BROADCAST_ACTION_PROGRESS);
            bcIntent.putExtra("location", mLastLocation);
            sendBroadcast(bcIntent);
            setLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onProviderDisabled: " + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onProviderEnabled: " + provider);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onStatusChanged: " + provider);
		}
	}

	LocationListener[] mLocationListeners = new LocationListener[] {
			new LocationListener(LocationManager.GPS_PROVIDER),
			new LocationListener(LocationManager.NETWORK_PROVIDER) };
	private Location location;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onStartCommand");
		
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		
		Utilitarios.appendLog(Global.INFO, Utilitarios.getCurrentDateAndHour(), TAG, "onCreate");
		mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		    notifyID = 1;
		    NotificationCompat.Builder  mNotifyBuilder = new NotificationCompat.Builder(this)
		        .setContentTitle("Capturando localizacion GPS")
		        .setContentText("Servicio ejecutandose...")
					.setSmallIcon(R.drawable.ic_menu_agenda);
		        //.setSmallIcon(ec.gob.mcds.rips.fichafamiliar.controlador.R.drawable.ic_msp);
		    mNotifyBuilder.setOngoing(true);
		        mNotificationManager.notify(
		                notifyID,
		                mNotifyBuilder.build());
		        Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "onCreate");
		initializeLocationManager();
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, Global.LOCATION_INTERVAL,
					Global.LOCATION_DISTANCE, mLocationListeners[1]);
			
			
			 if (mLocationManager != null) {
		            location = mLocationManager
		                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		            
		            if (location != null) {
		            	Utilitarios.logInfo(LocalizacionService.class.getName(), "getLastKnownLocation: " + location);
		            	//Comunicamos el progreso
		                Intent bcIntent = new Intent();
		                bcIntent.setAction(Global.BROADCAST_ACTION_PROGRESS);
		                bcIntent.putExtra("location", location);
		                sendBroadcast(bcIntent);
		            }
		        }
			
			
			
		} catch (SecurityException ex) {
			Utilitarios.logInfo(LocalizacionService.class.getName(),"fail to request location update, ignore"+ ex);
		} catch (IllegalArgumentException ex) {
			Utilitarios.logInfo(LocalizacionService.class.getName(), "network provider does not exist, " + ex.getMessage());
		}
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,  Global.LOCATION_INTERVAL,
					Global.LOCATION_DISTANCE, mLocationListeners[0]);
			
			if (mLocationManager != null) {
	            location = mLocationManager
	                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	            
	            if (location != null) {
	            	Utilitarios.logInfo(LocalizacionService.class.getName(),TAG + "getLastKnownLocation: " + location);
	            	//Comunicamos el progreso
	                Intent bcIntent = new Intent();
	                bcIntent.setAction(Global.BROADCAST_ACTION_PROGRESS);
	                bcIntent.putExtra("location", location);
	                sendBroadcast(bcIntent);
	            }
	        }
			
		} catch (SecurityException ex) {
			Utilitarios.logInfo(LocalizacionService.class.getName(),TAG + "fail to request location update, ignore"+ ex);
		} catch (IllegalArgumentException ex) {
			
			Utilitarios.logInfo(LocalizacionService.class.getName(),TAG + "gps provider does not exist " + ex.getMessage());
		}
		//this.callAsynchronousTask();
	}

	@Override
	public void onDestroy() {
		Utilitarios.appendLog(Global.INFO, Utilitarios.getCurrentDateAndHour(), TAG, "onDestroy");
		Utilitarios.logInfo(LocalizacionService.class.getName(),TAG + "onDestroy");
		mNotificationManager.cancel(notifyID);
		super.onDestroy();
		if (mLocationManager != null) {
			for (int i = 0; i < mLocationListeners.length; i++) {
				try {
					mLocationManager.removeUpdates(mLocationListeners[i]);
				} catch (Exception ex) {
					Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "fail to remove location listners, ignore" + ex);
				}
			}
		}
	}

	private void initializeLocationManager() {
		Utilitarios.logInfo(LocalizacionService.class.getName(), TAG + "initializeLocationManager");
		if (mLocationManager == null) {
			mLocationManager = (LocationManager) getApplicationContext()
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}
	
	
	public void setLocation (Location location) {
		this.location = location;
	}
	
	public Location getLocation () {
		return this.location;
	}
	
}