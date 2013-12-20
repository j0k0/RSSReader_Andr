package com.johko.jugendnetz_berlin.destellenangebote;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RSSMain extends FragmentActivity implements TabListener,
		List_Fragment.Callbacks {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		bar.addTab(bar.newTab().setText(R.string.title_section3)
				.setTabListener(this));
		bar.addTab(bar.newTab().setText(R.string.title_section2)
				.setTabListener(this));
		bar.addTab(bar.newTab().setText(R.string.title_section1)
				.setTabListener(this));

	}

	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return true;
	  } 
	
	/*@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	    	
	      break;

	    default:
	      break;
	    }

	    return true;
	  } */
	
	@Override
	public void onItemSelected(String url) {

		System.out.println(url);
		Intent intent = new Intent(RSSMain.this, Show.class);
		intent.putExtra("URL", url);

		startActivity(intent);

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		List_Fragment old = (List_Fragment) getSupportFragmentManager()
				.findFragmentById(R.id.feedListe);
		old.index = tab.getPosition();
		System.out.println(old.index);
		old.update(this);

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}
	
	@Override
	public void onResume(){
		super.onResume();
		List_Fragment recentFrag = (List_Fragment) getSupportFragmentManager()
				.findFragmentById(R.id.feedListe);
		recentFrag.update(this);
	}

}
