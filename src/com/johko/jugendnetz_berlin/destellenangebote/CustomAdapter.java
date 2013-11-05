package com.johko.jugendnetz_berlin.destellenangebote;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Content> {
	
	Activity context;
	private ArrayList<Content> contents;
			
	public CustomAdapter(Context context, int textViewResourceId,
			ArrayList<Content> objects) {
		super(context, textViewResourceId, objects);
		contents = objects;
	}

	private class ViewHolder{
		TextView title;
		TextView date;
	}
	
	public Content getItem(int position){
		return contents.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder;
		View v = convertView;

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			TextView title = (TextView) v.findViewById(R.id.title);
			TextView date = (TextView) v.findViewById(R.id.date);
			v.setTag(holder);
			v = inflater.inflate(R.layout.list_row, null);
			title.setText(getItem(position).getTitle2(position));
			date.setText(getItem(position).getDate2(position));
			
		
		//Content myContent = getItem(position);
		
		
		return v;
	}

}
