package com.catinabox.obra;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LogInProcessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginprocess);
	}
	// ultimo intento de cerrar

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.log_in_process, menu);
		return true;
	}

}
