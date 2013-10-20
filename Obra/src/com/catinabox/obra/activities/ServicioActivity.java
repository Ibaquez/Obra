package com.catinabox.obra.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.catinabox.obra.R;
import com.catinabox.obra.rubro.RubroActivity;

public class ServicioActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.servicio);
		((Button) findViewById(R.id.mercMateriales)).setOnClickListener(this);
		((Button) findViewById(R.id.ayuda)).setOnClickListener(this);
		((Button) findViewById(R.id.guiaObra)).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.mercMateriales){
			Intent myIntent = new Intent(ServicioActivity.this, LogInActivity.class);
			ServicioActivity.this.startActivity(myIntent);
		}
		if(v.getId() == R.id.ayuda){
			Intent myIntent = new Intent(ServicioActivity.this, CriterioActivity.class);
			ServicioActivity.this.startActivity(myIntent);
		}
		if(v.getId() == R.id.guiaObra){
			Intent myIntent = new Intent(ServicioActivity.this, RubroActivity.class);
			ServicioActivity.this.startActivity(myIntent);
		}
	}
}
