package com.catinabox.obra.subcategoria;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catinabox.obra.R;

public class SubCategoriaAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<SubCategoria> subcategorias;
	
	public SubCategoriaAdapter(Context context, ArrayList<SubCategoria> subcategorias) {
		super(context, R.layout.categoria);
		this.context = context;
		this.subcategorias = subcategorias;
	}
	
	@Override
	public int getCount() {
		return subcategorias.size();
	}
	
	private static class PlaceHolder {
		TextView subcategoria;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.subcategoria = (TextView) convertView.findViewById(R.id.subcategoria);
			return placeHolder;
		}

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.subcategoria, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.subcategoria.setText(subcategorias.get(position).getNombre());
		return (convertView);
	}
}
