package com.johko.jugendnetz_berlin.destellenangebote;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RSSMain extends Activity {

	public final String TAG = RSSMain.class.getSimpleName();
	// URL to read from
	public final String URL_RSS = "http://www.jugendnetz-berlin.de/de/arbeitswelt/rss.php";

	private ListView liste;

	private static Content content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		liste = (ListView) findViewById(R.id.feedListe);
		liste.setSelector(R.drawable.list_selector);

		content = new Content();

		// Start parsing feed
		new ReadRssFeedTask(this).execute();

		// On click of an list item create a new intent and submit the according
		// URL
		liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(RSSMain.this, Show.class);

				intent.putExtra("URL", XmlHandler.myContent.get(position)
						.getUrl(position));

				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Inner class to use SAX Parser for parsing RSS feed
	public class ReadRssFeedTask extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;
		private HttpClient client;
		private HttpGet httpGet;
		private HttpResponse response;

		public ReadRssFeedTask(Activity activity) {
			client = new DefaultHttpClient();
			httpGet = new HttpGet(URL_RSS);
			response = null;
			dialog = new ProgressDialog(activity);
		}

		protected void onPreExecute() {
			dialog.setTitle("Bitte warten");
			dialog.setMessage("Der RSS Feed wird vom Server geladen.");
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... tmp) {
			try {
				response = client.execute(httpGet);
			} catch (ClientProtocolException e) {
				Log.e(TAG, "Falsches Protokoll" + e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, "URL ist falsch, URL:" + URL_RSS + e.getMessage());
			}

			if (response != null) {
				StatusLine statusLine = response.getStatusLine();

				if (statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();

					try {
						SAXParserFactory spf = SAXParserFactory.newInstance();
						SAXParser sp = null;
						try {
							sp = spf.newSAXParser();
						} catch (ParserConfigurationException e) {
							Log.e(TAG, "Fehler:" + e.getMessage());
						} catch (SAXException e) {
							Log.e(TAG, "Fehler im Handler" + e.getMessage());
						}

						XmlHandler myHandler = new XmlHandler(content);

						try {

							sp.parse(entity.getContent(), myHandler);
						} catch (SAXException e) {
							Log.e(TAG,
									"Fehler beim Parsen. Fehler:"
											+ e.getMessage());
						} catch (IOException e) {
							Log.e(TAG,
									"URL " + URL_RSS
											+ " konnte nicht geöffnet werden!"
											+ e.getMessage());
						}

					} catch (IllegalStateException e) {
						e.printStackTrace();
					}

				} else {
					Log.i(RSSMain.class.getSimpleName(),
							"Der Server antwortet mit anderem Statuscode als 200.");
				}
			} else {
				Log.i(RSSMain.class.getSimpleName(),
						"Keine Internetverbindung.");
			}

			return null;
		}

		protected void onExecute(Void result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			liste.setAdapter(new CustomAdapter(RSSMain.this,
					android.R.layout.simple_list_item_activated_1,
					XmlHandler.myContent));
		}

		protected void onPostExecute(Void result) {
			// End dialog feed when completely parsed
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			// fill list with data

			liste.setAdapter(new CustomAdapter(RSSMain.this,
					android.R.layout.simple_list_item_activated_1,
					XmlHandler.myContent));
		}
	}

}

