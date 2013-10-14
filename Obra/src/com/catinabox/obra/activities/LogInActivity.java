package com.catinabox.obra.activities;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.catinabox.obra.R;

public class LogInActivity extends Activity implements OnClickListener{
	private String logInUrl = "http://www.revistaobra.com.ar/android/login.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		((Button) findViewById(R.id.login)).setOnClickListener(this);
		//SharedPreferences sharedPref = this.getSharedPreferences("com.catinabox.Obra", this.MODE_PRIVATE);
		SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
		((EditText) findViewById(R.id.usuario)).setText(sharedPref.getString("usuario", ""));
		((EditText) findViewById(R.id.password)).setText(sharedPref.getString("password", ""));
		((CheckBox) findViewById(R.id.recordarme)).setChecked(sharedPref.getBoolean("remember", false));
		if(sharedPref.getBoolean("remember", false)){
			this.onClick(null);
		}
	}

	private void setEnable(Boolean enable){
		((EditText) findViewById(R.id.usuario)).setEnabled(enable);
		((EditText) findViewById(R.id.password)).setEnabled(enable);
		((Button) findViewById(R.id.login)).setEnabled(enable);
	} 
	
	@Override
	public void onClick(View arg0) {
		this.setEnable(false);
		new LogIn().execute(((EditText) findViewById(R.id.usuario)).getText().toString(),((EditText) findViewById(R.id.password)).getText().toString());
	}
	
	private void setStorage(){
		SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("remember", ((CheckBox) findViewById(R.id.recordarme)).isChecked());
		if(((CheckBox) findViewById(R.id.recordarme)).isChecked()){
			editor.putString("usuario",((EditText) findViewById(R.id.usuario)).getText().toString());
			editor.putString("password",((EditText) findViewById(R.id.password)).getText().toString());
		}else{
			editor.putString("usuario","");
			editor.putString("password","");
		}
		editor.commit();
	}
	
	private void onLogIn(){
		Intent myIntent = new Intent(LogInActivity.this, ServicioActivity.class);
		LogInActivity.this.startActivity(myIntent);
		this.setEnable(true);
	}
	
	private void onMessage(String mensaje){
		Intent myIntent = new Intent(LogInActivity.this, MensajeActivity.class);
		myIntent.putExtra("mensaje",mensaje);
		myIntent.putExtra("respuesta","true");
		LogInActivity.this.startActivity(myIntent);
		this.setEnable(true);
	}
	
	private void onError(String mensaje){
		Intent myIntent = new Intent(LogInActivity.this, MensajeActivity.class);
		myIntent.putExtra("mensaje",mensaje);
		myIntent.putExtra("respuesta","false");
		LogInActivity.this.startActivity(myIntent);
		this.setEnable(true);
	}
	
	
	private class LogIn extends AsyncTask<String, Void, Boolean>{
		private String mensaje = "";
		
		@Override
		protected Boolean doInBackground(String... arg0) {
			String usuario = arg0[0];
			String contrasena = arg0[1];
			String url = logInUrl + "?usuario=" + usuario + "&password=" + contrasena;
			String respuesta = "";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			Document dom;
			try {
				builder = factory.newDocumentBuilder();
				dom = builder.parse(url);
				Element root = dom.getDocumentElement();
				respuesta = root.getElementsByTagName("respuesta").item(0).getTextContent();
				mensaje = root.getElementsByTagName("mensaje").item(0).getTextContent();
			} catch (Exception e) {
				mensaje = e.getLocalizedMessage();
				respuesta = "false";
			}
			return respuesta.equals("true");
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(mensaje.equals("") && result){
				setStorage();
				onLogIn();
			}else if(result){
				setStorage();
				onMessage(mensaje);
			}else{
				onError(mensaje);
			}
		}
		
	}
}
