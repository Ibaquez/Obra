package com.catinabox.obra.ficha;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.catinabox.obra.R;
import com.catinabox.obra.R.layout;
import com.catinabox.obra.R.menu;
import com.catinabox.obra.rubro.Rubro;
import com.catinabox.obra.rubro.RubroActivity;
import com.catinabox.obra.rubro.RubroAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FichaActivity  extends Activity{
	private String fichaUrl = "http://www.revistaobra.com.ar/android/fichas.php";
	private ArrayList<Ficha> fichas = new ArrayList<Ficha>();
	private String rubro;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichas);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			rubro = extras.getString("rubro");
		}
		this.setTitle("Guia de Obra > " + rubro);
		new Servicio().execute();
		dialog = new ProgressDialog(FichaActivity.this);
		dialog.setMessage("Actualizando informacion...");
		dialog.show();
	}

	private void setLista(){
		ListView lista;
		lista = (ListView) findViewById(R.id.fichas);
		lista.setAdapter(new FichaAdapter(FichaActivity.this,fichas));
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
				dom = builder.parse(fichaUrl + "?rubro=" + rubro.replace(" ", "%20"));
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("ficha");
				for (int i=0;i<items.getLength();i++){
					Node item = items.item(i);
					Ficha ficha = new Ficha();
					NodeList properties = item.getChildNodes();
					
					for (int j=0;j<properties.getLength();j++){
						Node property = properties.item(j);
						String name = property.getNodeName();
						if (name.equalsIgnoreCase("actividad")){
							ficha.setActividad(property.getTextContent());
						}
						if (name.equalsIgnoreCase("direccion")){
							ficha.setDireccion(property.getTextContent());
						}
						if (name.equalsIgnoreCase("razonSocial")){
							ficha.setRazonSocial(property.getTextContent());
						}
						if (name.equalsIgnoreCase("localidad")){
							ficha.setLocalidad(property.getTextContent());
						}
						
					}
					fichas.add(ficha);
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

}
