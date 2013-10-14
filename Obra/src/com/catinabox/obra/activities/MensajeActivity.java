package com.catinabox.obra.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.catinabox.obra.R;

public class MensajeActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mensaje);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if(extras.getString("respuesta").equals("true")){
				((Button) findViewById(R.id.continuar)).setOnClickListener(this);
			}else{
				((Button) findViewById(R.id.continuar)).setEnabled(false);
			}
		    ((TextView) findViewById(R.id.mensaje)).setText(extras.getString("mensaje"));
		}
	}

	@Override
	public void onClick(View v) {
		Intent myIntent = new Intent(MensajeActivity.this, ServicioActivity.class);
		MensajeActivity.this.startActivity(myIntent);
	}
}
