package com.catinabox.obra.subcategoria;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.catinabox.obra.R;
import com.catinabox.obra.categoria.CategoriaActivity;
import com.catinabox.obra.material.MaterialActivity;

public class SubCategoriaActivity extends Activity implements OnItemClickListener {
	private String subCategoriaUrl = "http://www.revistaobra.com.ar/android/subcategorias.php";
	private ArrayList<SubCategoria> subcategorias = new ArrayList<SubCategoria>();
	private String categoria;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.subcategorias);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			categoria = extras.getString("categoria");
		}
		//((TextView) findViewById(R.id.path)).setText("Materiales > " + categoria);
		this.setTitle("Materiales > " + categoria);
		new Servicio().execute();
		dialog = new ProgressDialog(SubCategoriaActivity.this);
		dialog.setMessage("Actualizando informacion...");
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent myIntent = new Intent(SubCategoriaActivity.this, MaterialActivity.class);
		myIntent.putExtra("categoria",categoria);
		myIntent.putExtra("subcategoria",subcategorias.get(arg2).getNombre());
		SubCategoriaActivity.this.startActivity(myIntent);
	}

	private void setLista(){
		ListView lista;
		lista = (ListView) findViewById(R.id.subcategorias);
		lista.setAdapter(new SubCategoriaAdapter(SubCategoriaActivity.this,subcategorias));
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
				dom = builder.parse(subCategoriaUrl + "?categoria=" + categoria.replace(" ", "%20"));
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("subcategoria");
				for (int i=0;i<items.getLength();i++){
					Node item = items.item(i);
					SubCategoria subcategoria = new SubCategoria();
					subcategoria.setNombre(item.getTextContent());
					subcategorias.add(subcategoria);
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
