package com.johko.jugendnetz_berlin.destellenangebote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private boolean mIsBackButtonPressed = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_screen);
		
		Handler handler = new Handler();
		
		handler.postDelayed(new Runnable(){
			
			@Override
			public void run(){
				finish();
				
				if(!mIsBackButtonPressed){
					Intent intent = new Intent(SplashScreen.this, RSSMain.class);
					SplashScreen.this.startActivity(intent);
				}
			}
		}, 3000);
	}
	
	@Override
	public void onBackPressed(){
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}

}
