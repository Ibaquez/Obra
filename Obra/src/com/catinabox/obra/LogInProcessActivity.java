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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present. kktt
		getMenuInflater().inflate(R.menu.log_in_process, menu);
		return true;
	}

}
