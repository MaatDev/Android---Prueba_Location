package com.my;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;

public class SensorLocation {
	
	private final String TAG = getClass().getSimpleName();
	
	//Par�metros;
	
	private double latitude;
	private double longitude;
	
	private Context context;
	private LocationManager mySensor;
	private Location myCurrentLocation;
	private String provider;
	private boolean successSerching=false;
	
	public SensorLocation(Context context){
		Log.v(TAG, "Estoy en "+TAG+": Constructor");
		
		this.context = context;
				
	}
	
	public void quickUpdateParameters(){
		Log.v(TAG, "Estoy en "+TAG+": quickUpdateParameters");
		
		//Intento de buscar localizaci�n con gps;
		
		this.provider = LocationManager.GPS_PROVIDER;
		this.registarSensor(this.provider);
		this.subProcessUpdating(this.provider);
		this.unregisterSensor();	
		
		if(this.myCurrentLocation == null){
			
			//Intento de buscar localizaci�n por network;
			
			this.provider = LocationManager.NETWORK_PROVIDER;
			this.registarSensor(this.provider);
			this.subProcessUpdating(this.provider);
			this.unregisterSensor();
			
			
			if(this.myCurrentLocation == null){
				this.provider = "none";
				
//				//Intento de buscar localizaci�n por passive
//				this.provider = LocationManager.PASSIVE_PROVIDER;
//				registarSensor(this.provider);
//				subProcessUpdating(this.provider);
//				unregisterSensor();
								
			}
			
		}	
		
		
	}
	
	public void subProcessUpdating(String provider){
		Log.v(TAG, "Estoy en "+TAG+": subProcessUpdating");
		
		//Precisi�n de la localizaci�n;
		
		float accuracy = 999999;
		
		//Contador para buscar localizaci�n;
		
		int numInteration=0;
		
		//El tiempo esperado como m�ximo;
		
		int numInterationMaxWait=30;
		
		//El intervalo que se espera por cada iteraci�n;
		
		int miliSecondsInterval=100;
		
		while(numInteration < numInterationMaxWait ){

			this.myCurrentLocation = this.mySensor.getLastKnownLocation(this.provider);
			
			if(myCurrentLocation != null ){
				
				//Revisar la precisi�n de la localizaci�n   " && myCurrentLocation.getAccuracy() != 0.0 ";
				
				if(myCurrentLocation.getAccuracy() <= accuracy ){
					
					accuracy = myCurrentLocation.getAccuracy();
					Log.v(TAG, "location: "+myCurrentLocation.getLatitude()+"/"+myCurrentLocation.getLongitude());
					
					//Actualizar longitude y latitude;
					
					this.latitude = myCurrentLocation.getLatitude();
					this.longitude = myCurrentLocation.getLongitude();
					
				}
			}
			
			Log.v(TAG,"N�mero de iteraci�n: "+numInteration);
			try {
				Thread.sleep(miliSecondsInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				numInteration++;				
			}
			
		}
		
		if(myCurrentLocation != null){
			
			//Actualizar los datos si que encontro localizaci�n;
			
			this.successSerching = true;

		}
		
	}
	
	public boolean checkLocationSettings(Context context){
		Log.v(TAG, "Estoy en "+TAG+": checkLocationSettings");
		
		//Obtener el servicio de localizaci�n;
		
		this.mySensor = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		//Revisar si est� habilitado los sensores;
		
		if(this.mySensor.isProviderEnabled(LocationManager.GPS_PROVIDER) 
				||this.mySensor.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			
			return true;
		}
		
		return false;
		
	}
	
	public void openSettingLocation(Context context){
		Log.v(TAG, "Estoy en "+TAG+": openSettingLocation");
		
		//Abrir la ventana de settings para localizaci�n;
		
		context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		
	}
	
	public void registarSensor(String provider){
		//Inicializar myCurrentLocation;
		
		this.myCurrentLocation = null;
	
		//Obtener el servicio;
		
		this.mySensor = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);	
			
		//Inicializar el proveedor: gps, network, passive;
		
		this.provider = provider;
		
		//Inicializar el �xito de b�squeda;
		
		this.successSerching = false;		
		
		//Registrar el LocationListener;
		
		this.mySensor.requestLocationUpdates(provider, 0, 0, mySensorListener);
	}
	
	public void unregisterSensor(){
		Log.v(TAG, "Estoy en "+TAG+": unregisterSensor");
		
		//Se quita el listener para que no consuma energ�a;
		
		this.mySensor.removeUpdates(mySensorListener);
		
	}
	
	public final LocationListener mySensorListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
						
		}
	};

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isSuccessSerching() {
		return successSerching;
	}

	public String getProvider() {
		return provider;
	}
	

}
