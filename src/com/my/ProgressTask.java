package com.my;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ProgressTask extends AsyncTask<Object, Boolean, String>{
	
	private final String TAG = getClass().getSimpleName();
	private ProgressDialog dialog;
	private Context context;
	private SensorLocation sensor;
	
	
	public ProgressTask(Context context){
		Log.v(TAG,"Estoy en "+TAG+": Constructor");
		this.context = context;
		this.sensor = new SensorLocation(context);
		this.dialog = new ProgressDialog(this.context);
		this.dialog.setMessage("Please, wait...");
	}
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		Log.v(TAG,"Estoy en "+TAG+": doInBackground");
		

		publishProgress(true);	
		
		
		
//		while(Prueba_LocationActivity.ejecutando){
			//Esperar
//			Log.v("TAG", "Estado: "+Prueba_LocationActivity.ejecutando);
			this.sensor.quickUpdateParameters();

//		}
		
		return null;
	}
	
	
	@Override
	protected void onProgressUpdate(Boolean... values) {
		dialog.show();
	}
	
	@Override
	protected void onPostExecute(String result) {
		Log.v(TAG,"Estoy en "+TAG+": onPostExecute");
		
		this.dialog.dismiss();
	}

}
