package com.example.myearthquake;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class EarthquakeService extends Service {

	public static final String NEW_EARTHQUAKES_FOUND = "new_earthquakes_foud";
	private Notification newNotification;
	public static final int NOTIFICATION_ID = 1;
	private EarthquakeLookupTask lastLookup = null;

	AlarmManager alarms;
	PendingIntent alarmIntent;

	public void onCreate() {

		int icon = R.drawable.ic_launcher;
		String tiketText = "检测到新地震信息";
		long when = System.currentTimeMillis();
		newNotification = new Notification(icon, tiketText, when);
		alarms = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		String ALARMS_ACTION = EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM;
		Intent intentFilter = new Intent(ALARMS_ACTION);
		alarmIntent = PendingIntent.getBroadcast(this, 0, intentFilter, 0);

	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = getApplicationContext();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

		int minMagIndex = Integer.valueOf(sp.getString(Preference.PREF_MIN_MAG, "0"));
		if (minMagIndex < 0)
			minMagIndex = 0;

		int freqIndex = Integer.valueOf(sp.getString(Preference.PREF_UPDATE_FREQ, "0"));
		if (freqIndex < 0)
			freqIndex = 0;

		boolean autoUpdate = 
				sp.getBoolean(Preference.PREF_AUTO_UPDATE, false);

		Resources r = getResources();
		int[] freqValues = r.getIntArray(R.array.update_freq_values);

		int updateFreq = freqIndex;//freqValues[freqIndex];
		Log.d("更新", 
				 "最小震级是：" + minMagIndex + 
				 "更新频率 "+updateFreq);
		
		if(autoUpdate){
			int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
			long timeToFresh = SystemClock.elapsedRealtime() + 
					updateFreq*60*1000;
			alarms.setRepeating(alarmType, timeToFresh, 
					updateFreq*60*1000, alarmIntent); 
		}else
		{
			alarms.cancel(alarmIntent);
		}
		refreshEarthquakes();

		return Service.START_NOT_STICKY;
	}
	private void refreshEarthquakes() {
		if (lastLookup == null || 
				lastLookup.getStatus().equals(AsyncTask.Status.FINISHED)) {
			lastLookup = new EarthquakeLookupTask();
			lastLookup.execute((Void[])null);
		}
	}
	
	
	private class EarthquakeLookupTask extends AsyncTask<Void, Quake, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			URL url = null;
			try {
				Resources r = getResources();
				url = new URL(r.getString(R.string.quake_feed));

				URLConnection connection;
				connection = url.openConnection();

				HttpURLConnection hConnection = (HttpURLConnection)connection;
				int responseCode =  hConnection.getResponseCode(); 
				if(responseCode == HttpURLConnection.HTTP_OK){
					InputStream in = hConnection.getInputStream();

					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document d = db.parse(in);
					Element e = d.getDocumentElement();


					NodeList nodeList = e.getElementsByTagName("entry");

					if(nodeList != null && nodeList.getLength() > 0){
						for (int i = 1; i < nodeList.getLength(); i++) {
							Element entry = (Element)nodeList.item(i);
							Element title = (Element) entry.getElementsByTagName("title").item(0);
							Element g = (Element) entry.getElementsByTagName("georss:point").item(0);
							Element when = (Element) entry.getElementsByTagName("updated").item(0);
							Element link = (Element) entry.getElementsByTagName("link").item(0);

							String detail = title.getFirstChild().getNodeValue();
							String hostname = "http://earthquake.usgs.gov";
							String linkString = hostname + link.getAttribute("href");
							String point  = g.getFirstChild().getNodeValue();
							String dt = when.getFirstChild().getNodeValue();

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
							Date qdate = new GregorianCalendar(0,0,0).getTime();
							try {
								qdate = sdf.parse(dt);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}

							String[] location = point.split(" ");
							Location l = new Location("dummyGPS");
							l.setLatitude(Double.parseDouble(location[0]));
							l.setLongitude(Double.parseDouble(location[1]));

							String magnitudeString = detail.split(" ")[1];
							int end =  magnitudeString.length()-1;
							double magnitude = Double.parseDouble(magnitudeString.substring(0, end));

							detail = detail.split(",")[1].trim();

							Quake quake = new Quake( detail, qdate, l, magnitude, linkString);

							// Process a newly found earthquake
							addNewQuake(quake);
						}
					}


				}


				Log.d("URI是：", url.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Quake... values) {
			String svcName = Context.NOTIFICATION_SERVICE;
			NotificationManager notificationManager;
		      notificationManager = (NotificationManager)getSystemService(svcName);

			Context context = getApplicationContext();
			String expandedTitle = values[0].getDate().toString();
			String expandedText = "M:" + values[0].getMagnitude() + " " +
					values[0].getDetail();
			Intent startActivityIntent = new Intent(EarthquakeService.this,
					Main.class);
			PendingIntent launchIntent = PendingIntent.getActivity(context,0, 
					startActivityIntent, 0);

			newNotification.setLatestEventInfo(context, 
					expandedTitle, 
					expandedText,
					launchIntent);
			newNotification.when = java.lang.System.currentTimeMillis();

			notificationManager.notify(NOTIFICATION_ID, newNotification);

			Toast.makeText(context, expandedTitle, Toast.LENGTH_SHORT).show();
		}

	}
	
	private void addNewQuake(Quake quake) {
		ContentResolver cr = getContentResolver();
		String w = EarthquakeProvider.KEY_DATE + "=" + quake.getDate().getTime();
		int num = cr.query(EarthquakeProvider.CONTENT_URI, null, w, null, null).getCount();
		if(num == 0){
			
			ContentValues values = new ContentValues();    

			values.put(EarthquakeProvider.KEY_DATE, quake.getDate().getTime());
			values.put(EarthquakeProvider.KEY_DETAILS, quake.getDetail());

			double lat = quake.getLocation().getLatitude();
			double lng = quake.getLocation().getLongitude();
			values.put(EarthquakeProvider.KEY_LOCATION_LAT, lat);
			values.put(EarthquakeProvider.KEY_LOCATION_LNG, lng);
			values.put(EarthquakeProvider.KEY_LINK, quake.getLink());
			values.put(EarthquakeProvider.KEY_MAGNITUDE, quake.getMagnitude());

			cr.insert(EarthquakeProvider.CONTENT_URI, values);
			announceNewQuake(quake);
			
		}

	}
	private void announceNewQuake(Quake quake) {
		Intent intent = new Intent(NEW_EARTHQUAKES_FOUND);
		intent.putExtra("date", quake.getDate().getTime());
		intent.putExtra("details", quake.getDetail());
		intent.putExtra("longitude", quake.getLocation().getLongitude());
		intent.putExtra("latitude", quake.getLocation().getLatitude());
		intent.putExtra("magnitude", quake.getMagnitude());

		sendBroadcast(intent);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
