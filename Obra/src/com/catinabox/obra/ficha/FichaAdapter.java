package com.catinabox.obra.ficha;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catinabox.obra.R;


public class FichaAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Ficha> fichas;
	
	public FichaAdapter(Context context, ArrayList<Ficha> fichas) {
		super(context, R.layout.ficha);
		this.context = context;
		this.fichas = fichas;
	}
	
	@Override
	public int getCount() {
		return fichas.size();
	}
	
	private static class PlaceHolder {
		TextView descripcion;
		TextView razonSocial;
		TextView localidad;
		TextView direccion;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.descripcion = (TextView) convertView.findViewById(R.id.descripcion);
			placeHolder.razonSocial = (TextView) convertView.findViewById(R.id.razonSocial);
			placeHolder.localidad = (TextView) convertView.findViewById(R.id.localidad);
			placeHolder.direccion = (TextView) convertView.findViewById(R.id.direccion);
			return placeHolder;
		}

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.ficha, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.descripcion.setText(fichas.get(position).getActividad());
		placeHolder.razonSocial.setText(fichas.get(position).getRazonSocial());
		placeHolder.localidad.setText(fichas.get(position).getLocalidad());
		placeHolder.direccion.setText(fichas.get(position).getDireccion());
		return (convertView);
	}
}
