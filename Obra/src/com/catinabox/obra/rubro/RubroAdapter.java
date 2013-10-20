package com.catinabox.obra.rubro;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catinabox.obra.R;

public class RubroAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Rubro> rubros;
	
	public RubroAdapter(Context context, ArrayList<Rubro> rubros) {
		super(context, R.layout.rubro);
		this.context = context;
		this.rubros = rubros;
	}
	
	@Override
	public int getCount() {
		return rubros.size();
	}
	
	private static class PlaceHolder {
		TextView rubro;
		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.rubro = (TextView) convertView.findViewById(R.id.rubro);
			return placeHolder;
		}

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.rubro, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		placeHolder.rubro.setText(rubros.get(position).getNombre());
		return (convertView);
	}
}
