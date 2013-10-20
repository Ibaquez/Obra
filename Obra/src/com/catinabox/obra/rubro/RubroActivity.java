package com.catinabox.obra.rubro;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.catinabox.obra.R;
import com.catinabox.obra.ficha.FichaActivity;

public class RubroActivity extends Activity implements OnItemClickListener{
	private String rubroUrl = "http://www.revistaobra.com.ar/android/rubro.php";
	private ArrayList<Rubro> rubros = new ArrayList<Rubro>();
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rubros);
		new Servicio().execute();
		dialog = new ProgressDialog(RubroActivity.this);
		dialog.setMessage("Actualizando informacion...");
		dialog.show();
	}
	
	private void setLista(){
		ListView lista;
		lista = (ListView) findViewById(R.id.rubros);
		lista.setAdapter(new RubroAdapter(RubroActivity.this,rubros));
		lista.setOnItemClickListener(this);
		dialog.hide(); 
	}

	private class Servicio extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			Document dom;
			
			try {
				builder = factory.newDocumentBuilder();
				dom = builder.parse(rubroUrl);
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("rubro");
				for (int i=0;i<items.getLength();i++){
					Node item = items.item(i);
					Rubro rubro = new Rubro();
					rubro.setNombre(item.getTextContent().trim());
					rubros.add(rubro);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setLista();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent myIntent = new Intent(RubroActivity.this, FichaActivity.class);
		myIntent.putExtra("rubro",rubros.get(arg2).getNombre());
		RubroActivity.this.startActivity(myIntent);
	}

}
