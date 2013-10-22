package com.johko.jugendnetz_berlin.destellenangebote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Show extends Activity {

	private WebView webView;
	
	private ProgressDialog dialog;
	
	public Show() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		webView = (WebView) findViewById(R.id.webView);
		
		dialog = new ProgressDialog(this);
		dialog.setTitle("Bitte warten Sie!");
		dialog.setMessage("Die Website wird geladen.");
		
		//Nachricht Adresse aus der MainActivity
		String url = getIntent().getExtras().getString("URL");
		
		webView.setWebViewClient(new WebViewClient(){
			
			//when site starts loading
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon){
				//Show wait dialog
				dialog.show();
			}
			
			//when site finished loading
			@Override
			public void onPageFinished(WebView view, String url){
				//if dialog is showing - dismiss
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl(url);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event){
		//if back button pushed
		if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
			webView.goBack();
			return true;
		}
	
		return super.onKeyDown(keyCode, event);
	}
	
	
}
