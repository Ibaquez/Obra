package com.catinabox.obra.activities;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.catinabox.obra.R;

import com.catinabox.obra.categoria.CategoriaActivity;

public class CriterioActivity extends Activity implements OnClickListener{
	private String textoUrl = "http://www.revistaobra.com.ar/android/Criterio.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.criterio);
		new CriterioText().execute();
		((Button) findViewById(R.id.MercadoMaterialesRedir)).setOnClickListener(this);
	}

	private class CriterioText extends AsyncTask<String, Void, Boolean>{
		private String mensaje = "";
		
		@Override
		protected Boolean doInBackground(String... arg0) {
			String url = textoUrl;
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			Document dom;
			try {
				builder = factory.newDocumentBuilder();
				dom = builder.parse(url);
				Element root = dom.getDocumentElement();
				mensaje = root.getTextContent();
			} catch (Exception e) {
				mensaje = e.getLocalizedMessage();
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			((TextView) findViewById(R.id.CriterioTxtDisplay)).setText(mensaje);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		Intent myIntent = new Intent(CriterioActivity.this, CategoriaActivity.class);
		CriterioActivity.this.startActivity(myIntent);
	}

}
