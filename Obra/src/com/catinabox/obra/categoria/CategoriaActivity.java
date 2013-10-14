package com.catinabox.obra.categoria;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.catinabox.obra.R;
import com.catinabox.obra.subcategoria.SubCategoriaActivity;

public class CategoriaActivity extends Activity implements OnItemClickListener {
	private String categoriaUrl = "http://www.revistaobra.com.ar/android/categorias.php";
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.categorias);
		new Servicio().execute();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent myIntent = new Intent(CategoriaActivity.this, SubCategoriaActivity.class);
		myIntent.putExtra("categoria",categorias.get(arg2).getNombre());
		CategoriaActivity.this.startActivity(myIntent);
	}
	
	private void setLista(){
		ListView lista;
		lista = (ListView) findViewById(R.id.categorias);
		lista.setAdapter(new CategoriaAdapter(CategoriaActivity.this,categorias));
		lista.setOnItemClickListener(this);
		((TextView) findViewById(R.id.loadingText)).setVisibility(View.GONE);
	}
	
	private class Servicio extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			Document dom;
			
			try {
				builder = factory.newDocumentBuilder();
				dom = builder.parse(categoriaUrl);
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("categoria");
				for (int i=0;i<items.getLength();i++){
					Node item = items.item(i);
					Categoria categoria = new Categoria();
					categoria.setNombre(item.getTextContent());
					categorias.add(categoria);
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
