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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A list fragment representing a list of Items. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ItemDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class List_Fragment extends ListFragment {

	private final String TAG = RSSMain.class.getSimpleName();
	// URL to read from
	private final String[] URL_POOL = new String[] {
			"http://jugendnetz-berlin.de/de/veranstaltungen.php",
			"http://jugendnetz-berlin.de/de/neuigkeiten.php",
			"http://www.jugendnetz-berlin.de/de/arbeitswelt/rss.php"
			 };
	private String URL_RSS;
	public int index = 0;
	private Callbacks mCallbacks = sDummyCallbacks;
	private static Content content;
	public CustomAdapter adapter;
	private boolean connected = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new CustomAdapter(getActivity(),
				android.R.layout.simple_list_item_1, XmlHandler.myContent);
		content = new Content();
		update(getActivity());	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.custom_list, null);
		
		return layout;
	}

	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String url);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String url) {
		}
	};

	public void update(Activity act) {
		URL_RSS = URL_POOL[index];
		new ReadRssFeedTask(act).execute();
		if(!connected){
			Toast.makeText(getActivity().getApplicationContext(), "Keine Internetverbindung", Toast.LENGTH_LONG)
	        .show();
		}
	}

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

		@Override
		protected void onPreExecute() {
			dialog.setTitle("Bitte warten");
			dialog.setMessage("Die Daten werden vom Server geladen.");
			dialog.show();
			adapter.clear();
		}

		@Override
		protected Void doInBackground(Void... tmp) {
			try {
				response = client.execute(httpGet);
				connected = true;
			} catch (ClientProtocolException e) {
				
			} catch (IOException e) {
				connected = false;
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
						} catch (SAXException e) {
						}

						XmlHandler myHandler = new XmlHandler(content);

						try {

							sp.parse(entity.getContent(), myHandler);

						} catch (SAXException e) {
						} catch (IOException e) {
						}

					} catch (IllegalStateException e) {
					}

				} else {
				}
			} else {

			}

			return null;
		}

		protected void onExecute(Void result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}

		@Override
		protected void onPostExecute(Void result) {
			// End dialog feed when completely parsed
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			adapter.notifyDataSetChanged();

			// fill list with data
			adapter = new CustomAdapter(getActivity(),
					android.R.layout.simple_list_item_1, XmlHandler.myContent);

			setListAdapter(adapter);

		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		view.setSelected(true);
		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(XmlHandler.myContent.get(position).getUrl(
				position));
	}

}
