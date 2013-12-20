package com.johko.jugendnetz_berlin.destellenangebote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebDetail extends Fragment {

	public String url = "http://m.jugendnetz-berlin.de/de/index.php";
	private WebView webView;
	private ProgressDialog dialog;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getArguments();
		url = bundle.getString("url");
		
		dialog = new ProgressDialog(getActivity());
		dialog.setTitle("Bitte warten");
		dialog.setMessage("jugendnetz-berlin.de wird geladen.");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("currentURL", url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.web, container, false);
		webView = (WebView) view.findViewById(R.id.web_view);
		webView.setWebViewClient(new MyWebClient());;
		webView.loadUrl(url);
		return view;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void updateURLContent(String newUrl) {
		url = newUrl;
		webView = (WebView) getView().findViewById(R.id.web_view);
		webView.setWebViewClient(new MyWebClient());;
		webView.loadUrl(url);
	}

	private class MyWebClient extends WebViewClient {
		// when site starts loading
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					// Show wait dialog
					dialog.show();
				}

				// when site finished loading
				@Override
				public void onPageFinished(WebView view, String url) {
					//if dialog is showing - dismiss
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.startsWith("tel:")) {
						Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
						startActivity(dial);
						return true;
					} else if (url.startsWith("mailto:")) {
						Intent sendMail = new Intent(Intent.ACTION_SENDTO,
								Uri.parse(url));
						startActivity(sendMail);
						return true;
					} else if (url.startsWith("geo:")) {
						Intent searchAddress = new Intent(Intent.ACTION_VIEW,
								Uri.parse(url));
						startActivity(searchAddress);
						return true;
					} else {
						view.loadUrl(url);
						return true;
					}

				}
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if back button pushed
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}

		return false;
	}

}
