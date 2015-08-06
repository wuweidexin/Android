package com.example.myearthquake;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity {

	private ListView listView;
	private List<Quake> list = new ArrayList<Quake>(); 
	private ArrayAdapter<Quake> ad  = null;
	static final private int QUAKE_DIALOG = 1;
	private static final int SHOW_PREFERENCES = 1;
	private Quake quake;
	static final private int UPDATE = Menu.FIRST;
	static final private int PREFERENCE = Menu.FIRST + 1;
	NotificationManager notificationManager;
	EarthquakeReceiver receiver;

	private boolean autoUpdate = false;
	private int miniMagnitude = 0;
	private int reFrequery = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView)findViewById(R.id.earthquakeListView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				quake = list.get(position);
				showDialog(QUAKE_DIALOG);
			}
		});
		ad = new ArrayAdapter<Quake>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(ad);
		loadQuakesFromContentProvider();
		refreshEarthquake();
		String svcName = Context.NOTIFICATION_SERVICE;
	    notificationManager = (NotificationManager)getSystemService(svcName);
	}
	Cursor c ;
	private void loadQuakesFromContentProvider() {
		list.clear();

		ContentResolver cr = getContentResolver();
		c = cr.query(
				EarthquakeProvider.CONTENT_URI,
				null, null, null, null) ;
		if(c != null){
			if(c.moveToFirst()){
				do{
					Long datems = c.getLong(EarthquakeProvider.DATE_COLUMN);
					String details = c.getString(EarthquakeProvider.DETAILS_COLUMN);
					Float lat = c.getFloat(EarthquakeProvider.LATITUDE_COLUMN);
					Float lng = c.getFloat(EarthquakeProvider.LONGITUDE_COLUMN);
					Double mag = c.getDouble(EarthquakeProvider.MAGNITUDE_COLUMN);
					String link = c.getString(EarthquakeProvider.LINK_COLUMN);

					Location location = new Location("dummy");
					location.setLongitude(lng);
					location.setLatitude(lat);

					Date date = new Date(datems);

					Quake q = new Quake(details, date, location, mag, link);
					addQuakeToArray(q);
				}while(c.moveToNext());
			}
		}

	}

	private void addQuakeToArray(Quake q) {
		if (q.getMagnitude() > miniMagnitude) {
			// Add the new quake to our list of earthquakes.
			
			list.add(q);

			// Notify the array adapter of a change.
			ad.notifyDataSetChanged();
		}
	}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		menu.add(0, UPDATE, Menu.NONE, R.string.menu_update);
		menu.add(0, PREFERENCE, Menu.NONE, R.string.refencence);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int position = item.getItemId();
		switch (position) {
		case (UPDATE): {
			refreshEarthquake();
			return true; 
		}
		case (PREFERENCE): {
			Intent i = new Intent(this, Preference.class);
			startActivityForResult(i, SHOW_PREFERENCES);
			return true;
		}
		} 
		return false;
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case QUAKE_DIALOG:{
			LayoutInflater li = LayoutInflater.from(this);
			View v = li.inflate(R.layout.quake_details, null);

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("");
			dialog.setView(v);
			return dialog.create();	
		}
		}
		return null;


	}


	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch(id){
		case QUAKE_DIALOG:{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String datas = sdf.format(quake.getDate());
			
			String quakeText = "震级: " + quake.getMagnitude() + "\n"
					+ "详情： " + quake.getDetail() + "\n"
					+ "链接： " + quake.getLink();
			AlertDialog quakeDialog = (AlertDialog)dialog;
			TextView tv = (TextView)quakeDialog.findViewById(R.id.quakeDetailsTextView);
	        quakeDialog.setTitle(datas);
			tv.setText(quakeText);
			break;
		}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == SHOW_PREFERENCES){
			if(resultCode == Activity.RESULT_OK){
				updateFromPreferences();
				refreshEarthquake();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void updateFromPreferences() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		autoUpdate = sp.getBoolean(Preference.PREF_AUTO_UPDATE, false);
		miniMagnitude = Integer.valueOf(sp.getString(Preference.PREF_MIN_MAG, "0"));
		reFrequery = Integer.valueOf(sp.getString(Preference.PREF_UPDATE_FREQ, "0"));
		
		Log.d("主函数中的：", "更新时间"+sp.getString(Preference.PREF_MIN_MAG, "0") + "  更新频率   "+sp.getString(Preference.PREF_UPDATE_FREQ, "0"));
	}
	
	public void refreshEarthquake(){
		//list.clear();
		startService(new Intent(this, EarthquakeService.class));

	}
	public class EarthquakeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			loadQuakesFromContentProvider();
			notificationManager.cancel(EarthquakeService.NOTIFICATION_ID);
		}
	}

	@Override 
	public void onResume() {
		notificationManager.cancel(EarthquakeService.NOTIFICATION_ID);

		IntentFilter filter;
		filter = new IntentFilter(EarthquakeService.NEW_EARTHQUAKES_FOUND);
		receiver = new EarthquakeReceiver();
		registerReceiver(receiver, filter);

		loadQuakesFromContentProvider();
		super.onResume();
	}

	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		c.close();
		super.onDestroy();
	}
}
