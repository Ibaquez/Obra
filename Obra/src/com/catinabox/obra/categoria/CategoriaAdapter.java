package com.catinabox.obra.categoria;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catinabox.obra.R;


public class CategoriaAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Categoria> categorias;
	
	public CategoriaAdapter(Context context, ArrayList<Categoria> categorias) {
		super(context, R.layout.categoria);
		this.context = context;
		this.categorias = categorias;
	}
	
	@Override
	public int getCount() {
		return categorias.size();
	}
	
	private static class PlaceHolder {
		TextView categoria;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.categoria = (TextView) convertView.findViewById(R.id.categoria);
			return placeHolder;
		}

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.categoria, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.categoria.setText(categorias.get(position).getNombre());
		return (convertView);
	}
}
