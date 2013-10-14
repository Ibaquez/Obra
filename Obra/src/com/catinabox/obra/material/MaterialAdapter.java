package com.catinabox.obra.material;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catinabox.obra.R;

public class MaterialAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Material> materiales;
	
	public MaterialAdapter(Context context, ArrayList<Material> materiales) {
		super(context, R.layout.material);
		this.context = context;
		this.materiales = materiales;
	}
	
	@Override
	public int getCount() {
		return materiales.size();
	}
	
	private static class PlaceHolder {
		TextView descripcion;
		TextView id;
		TextView unidad;
		TextView precio;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.descripcion = (TextView) convertView.findViewById(R.id.descripcion);
			placeHolder.id = (TextView) convertView.findViewById(R.id.id);
			placeHolder.unidad = (TextView) convertView.findViewById(R.id.unidad);
			placeHolder.precio = (TextView) convertView.findViewById(R.id.precio);
			return placeHolder;
		}

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.material, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.descripcion.setText(materiales.get(position).getDescripcion());
		placeHolder.id.setText(materiales.get(position).getId());
		placeHolder.unidad.setText(materiales.get(position).getUnidad());
		placeHolder.precio.setText(materiales.get(position).getPrecio());
		return (convertView);
	}
}
