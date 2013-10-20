package com.catinabox.obra.material;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.catinabox.obra.R;
import com.catinabox.obra.ficha.FichaActivity;

public class MaterialActivity extends Activity{
	private String materialUrl = "http://www.revistaobra.com.ar/android/materiales.php";
	private ArrayList<Material> materiales = new ArrayList<Material>();
	private String categoria;
	private String subcategoria;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.materiales);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			categoria = extras.getString("categoria");
			subcategoria = extras.getString("subcategoria");
		}
		//((TextView) findViewById(R.id.path)).setText("Materiales > " + categoria + " > " + subcategoria);
		this.setTitle("Materiales > " + categoria + " > " + subcategoria);
		new Servicio().execute();
		dialog = new ProgressDialog(MaterialActivity.this);
		dialog.setMessage("Actualizando informacion...");
		dialog.show();
	}
	
	private void setLista(){
		ListView lista;
		lista = (ListView) findViewById(R.id.materiales);
		lista.setAdapter(new MaterialAdapter(MaterialActivity.this,materiales));
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
				dom = builder.parse(materialUrl + "?categoria=" + categoria.replace(" ", "%20") + "&subcategoria=" + subcategoria.replace(" ", "%20"));
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("material");
				for (int i=0;i<items.getLength();i++){
					Node item = items.item(i);
					Material material = new Material();
					NodeList properties = item.getChildNodes();
					
					for (int j=0;j<properties.getLength();j++){
						Node property = properties.item(j);
						String name = property.getNodeName();
						if (name.equalsIgnoreCase("descripcion")){
							material.setDescripcion(property.getTextContent());
						}
						if (name.equalsIgnoreCase("id")){
							material.setId(property.getTextContent());
						}
						if (name.equalsIgnoreCase("unidad")){
							material.setUnidad(property.getTextContent());
						}
						if (name.equalsIgnoreCase("precio")){
							material.setPrecio(property.getTextContent());
						}
						
					}
					
					materiales.add(material);
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
