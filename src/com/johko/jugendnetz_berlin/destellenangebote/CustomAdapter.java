package com.johko.jugendnetz_berlin.destellenangebote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Content> {

	Activity context;
	
    private List<Content> contents;

    public CustomAdapter(Context context, int textViewResourceId,
                    ArrayList<Content> objects) {
            super(context, textViewResourceId, objects);
            contents = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
            
            View v = convertView;
            
            if(v == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_row, null);
            }

            Content i = contents.get(position);
            
            //create custom item list view
            if (i != null){
            	 TextView title = (TextView) v.findViewById(R.id.title);
                 TextView date = (TextView) v.findViewById(R.id.date);
            	
            	 title.setText(getItem(position).getTitle());
                 date.setText(getItem(position).getDate());
            }
                    
            
            return v;
    }

}
