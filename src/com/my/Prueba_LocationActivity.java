package com.my;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//http://www.helloandroid.com/tutorials/calling-system-settings-android-app-gps-example
public class Prueba_LocationActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private final String TAG = getClass().getSimpleName();
	
	public static boolean ejecutando = false;
	
	private Button button;
	private SensorLocation sensor;
	private TextView texto;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.v(TAG, "Estoy en "+TAG+": onCreate");
        
        //Inicializar variables
        
        this.button = (Button) findViewById(R.id.button1);
        this.texto = (TextView)findViewById(R.id.texto);
        this.button.setOnClickListener(this);
        this.sensor = new SensorLocation(this);    
                
    }
    
    
    public void checkLocationSettings(){
    	Log.v(TAG, "Estoy en "+TAG+": checkLocationSettings");
    	
    	//Crear un diálogo
    	
    	final Dialog dialog=new android.app.Dialog(this);
    	dialog.setContentView(R.layout.ventana_location_dialog);
    	dialog.setTitle("Location Settings");
    	dialog.setCancelable(false);
    	dialog.show();
    	
    	//Crear un botón para abrir el settings de location
    	
    	Button LocationSettings = (Button) dialog.findViewById(R.id.btn_location_setting);
    	LocationSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			}
		});
    	
    	//Crear un botón para cancelar		
    	
    	Button LocationCancel = (Button) dialog.findViewById(R.id.btn_location_cancel);
    	LocationCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
//				finish();
			}
		});
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Estoy en "+TAG+": onClick");
		ejecutando = true;
		
		//Revisar si está hablitado los proveedores de localización: gps o network
		
		if(sensor.checkLocationSettings(this)){
			
//			ProgressTask tarea = new ProgressTask(this);
//			tarea.execute(this);
				
			this.sensor.quickUpdateParameters();
			String cadena;
					
			//Mostrar el resultado de búsqueda
			if(sensor.isSuccessSerching()){
								
				cadena = "El proveedor es: "+this.sensor.getProvider()+'\n'+
						"Latitude: "+this.sensor.getLatitude()+'\n'+
						"Longitude: "+this.sensor.getLongitude();	
				texto.setText(cadena);
				
			}else{
				
				texto.setText("No se pudo encontrar la localización");
				
			}
			ejecutando = false;
			
													
		}else{
			
			this.checkLocationSettings();
			
		}
		
		ejecutando = false;
	}
    
}