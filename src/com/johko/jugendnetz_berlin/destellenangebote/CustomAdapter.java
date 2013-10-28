package com.johko.jugendnetz_berlin.destellenangebote;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Content> {
	
	private ArrayList<Content> myContent;
			
	public CustomAdapter(Context context, int textViewResourceId,
			ArrayList<Content> objects) {
		super(context, textViewResourceId, objects);
		myContent = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View v = convertView;
		
		if(v==null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_row, null);
		}
		
		Content actCont = myContent.get(position);
		
		if(actCont != null){
			TextView title = (TextView) v.findViewById(R.id.title);
			TextView date = (TextView) v.findViewById(R.id.date);
			 
			if(title != null){
				actCont.getTitle();
			}
			if(date != null){
				actCont.getDate();
			}
		}
		
		return v;
	}

}
