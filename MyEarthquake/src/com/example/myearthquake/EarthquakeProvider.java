package com.example.myearthquake;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class EarthquakeProvider extends ContentProvider {
	public static final Uri CONTENT_URI= Uri.parse("content://com.example.provider.earthquake/earthquakes");
	private static final int QUAKES = 1;
	private static final int QUAKES_ID = 2;
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.example.myearthquake", "earthquakes", QUAKES);
		uriMatcher.addURI("com.example.myearthquake", "earthquakes/#", QUAKES_ID);
	}
	private SQLiteDatabase earthDatabase;
	@Override
	public boolean onCreate() {
		Context context = getContext();
		
		earthquakeDatabaseHelper edh = new earthquakeDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		earthDatabase = edh.getWritableDatabase();
		return (earthDatabase == null) ? true : false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
		sqb.setTables(EARTHQUAKE_TABLE);
		switch(uriMatcher.match(uri)){
		case QUAKES_ID: sqb.appendWhere(KEY_ID + "=" + uri.getPathSegments().get(1)); break;
		default: break;
		}
		
		String orderBy;
		if(TextUtils.isEmpty(sortOrder)){
			orderBy = KEY_DATE;
		}else{
			orderBy = sortOrder;
		}
		
		Cursor c = earthDatabase.query(EARTHQUAKE_TABLE, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
		case QUAKES: return "vnd.android.cursor.dir/vnd.example.myearthquake";
		case QUAKES_ID: return "vnd.android.cursor.item/vnd.example.myearthquake";
		default: throw new IllegalArgumentException("不支持的Uri" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = earthDatabase.insert(EARTHQUAKE_TABLE, "quake", values);
		
		if(rowID > 0 ){
			Uri uri2 = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri2, null);
			return uri2;
		}
		 throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		int count = 0;
		switch(uriMatcher.match(uri)){
		case QUAKES:
		{
			earthDatabase.delete(EARTHQUAKE_TABLE, whereClause, whereArgs);
			break;
		}
		case QUAKES_ID:
		{
			String segment = uri.getPathSegments().get(1);
			count = earthquakeDB.delete(EARTHQUAKE_TABLE, KEY_ID + "="
                    + segment
                    + (!TextUtils.isEmpty(whereClause) ? " AND (" 
                    + whereClause + ')' : ""), whereArgs);break;


		}
		default: throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause,
			String[] whereArgs) {
		int count = 0;
		switch(uriMatcher.match(uri)){
		case QUAKES:{
			count = earthDatabase.update(EARTHQUAKE_TABLE, values, whereClause, whereArgs);
		}
		case QUAKES_ID:{
			String segment = uri.getPathSegments().get(1);
			count = earthDatabase.update(EARTHQUAKE_TABLE, values, 
					KEY_ID + "=" + (!TextUtils.isEmpty(whereClause)? 
					"AND (" + whereClause + ")" : ""),
					whereArgs); break;
		}
		default: throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	
	
	private SQLiteDatabase earthquakeDB;
	private static final String TAG = "EarthquakeProvider";
	private static final String DATABASE_NAME = "earthquakes.db";
	private static final int DATABASE_VERSION = 1;
	private static final String EARTHQUAKE_TABLE = "earthquakes";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_LOCATION_LAT = "latitude";
	public static final String KEY_LOCATION_LNG = "longitude";
	public static final String KEY_MAGNITUDE = "magnitude";
	public static final String KEY_LINK = "link";
	
	public static final int DATE_COLUMN = 1;
	public static final int DETAILS_COLUMN  = 2;
	public static final int LONGITUDE_COLUMN  = 3;
	public static final int LATITUDE_COLUMN  = 4;
	public static final int MAGNITUDE_COLUMN  = 5;
	public static final int LINK_COLUMN  = 6;
	
	private static class earthquakeDatabaseHelper extends SQLiteOpenHelper{

		private static final String DATABASE_CREATE = "create table " + EARTHQUAKE_TABLE +"("
				+ KEY_ID + " integer primary key autoincrement, "
				+ KEY_DATE + " INTEGER, "
				+ KEY_DETAILS + " TEXT, "
				+ KEY_LOCATION_LAT + " FLOAT, "
				+ KEY_LOCATION_LNG + " FLOAT, "
				+ KEY_MAGNITUDE + " FLOAT, "
				+ KEY_LINK + " TEXT);";
		public earthquakeDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
}
