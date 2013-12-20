package com.johko.jugendnetz_berlin.destellenangebote;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;



public class Show extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_act);
		ActionBar bar = getActionBar();
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);
		//gets committed URL from RSS activity
		String url = getIntent().getExtras().getString("URL");
		
		// creates new fragment & sets URL
		WebDetail webF = new WebDetail();
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		webF.setArguments(bundle);
		//webF.updateURLContent(url);
		getSupportFragmentManager().beginTransaction()
		.add(R.id.web_container, webF).commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
}
