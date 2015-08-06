package com.chen.contentprovider;

import java.util.HashMap;

import com.chen.contentprovider.TestContentProvider.TestContentProviderMetaData.BookTableMetaData;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
/*
 * 基于ContentProvider的操作，ContentProvider是android未来共享数据而
 * 提供的一个标准化数据管道
 * 未完成P213
 */
public class TestContentProvider extends ContentProvider{
	public static final String TAG = "TestContentProvider";
	public DatabaseHelper openHelper = null;
	//创建表列明与JavaBean映射
	public static HashMap<String, String> sBookProjectionMap = null;
	static{
		sBookProjectionMap = new HashMap<String, String>();
		sBookProjectionMap.put(BookTableMetaData._ID, BookTableMetaData._ID);
		sBookProjectionMap.put(BookTableMetaData.BOOK_NAME, BookTableMetaData.BOOK_NAME);
		sBookProjectionMap.put(BookTableMetaData.BOOK_ISBN, BookTableMetaData.BOOK_ISBN);
		sBookProjectionMap.put(BookTableMetaData.BOOK_AUTHOR, BookTableMetaData.BOOK_AUTHOR);
		sBookProjectionMap.put(BookTableMetaData.CREATED_DATE, BookTableMetaData.CREATED_DATE);
		sBookProjectionMap.put(BookTableMetaData.MODIFIED_DATE, BookTableMetaData.MODIFIED_DATE);
	}
	private static UriMatcher sUriMatcher = null;
	private static final int INCOMMING_BOOK_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMMING_SINGLE_BOOK_URI_INDICATOR = 2;
	static{
		sUriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(TestContentProviderMetaData.AUTHORITY, "books", INCOMMING_BOOK_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(TestContentProviderMetaData.AUTHORITY, "books/#", INCOMMING_SINGLE_BOOK_URI_INDICATOR);
		
		
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		Log.i(TAG, "del");
		SQLiteDatabase db = openHelper.getWritableDatabase();
		int count = 0;
		switch(sUriMatcher.match(uri)){
		case INCOMMING_BOOK_COLLECTION_URI_INDICATOR:
			count = db.delete(BookTableMetaData.BOOK_NAME, whereClause, whereArgs); break;
		case INCOMMING_SINGLE_BOOK_URI_INDICATOR:
			String rowID = uri.getPathSegments().get(1);
			String where = BookTableMetaData._ID + "=" +rowID +(!TextUtils.isEmpty(whereClause)?" AND ("+ whereClause+')':"");
			count = db.delete(BookTableMetaData.TABLE_NAME, where, whereArgs);
			break;
			default:
				throw new IllegalArgumentException("Unknown URI"+uri);
		}
		
		this.getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch(sUriMatcher.match(uri)){
		case INCOMMING_BOOK_COLLECTION_URI_INDICATOR:
			return BookTableMetaData.CONTENT_TYPE;
		case INCOMMING_SINGLE_BOOK_URI_INDICATOR:
			return BookTableMetaData.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI"+uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert");
		long now = Long.valueOf(System.currentTimeMillis());
		if(values.containsKey(BookTableMetaData.CREATED_DATE) == false){
			values.put(BookTableMetaData.CREATED_DATE, now);
		}
		if(values.containsKey(BookTableMetaData.MODIFIED_DATE) == false){
			values.put(BookTableMetaData.MODIFIED_DATE, now);
		}
		if(values.containsKey(BookTableMetaData.BOOK_NAME) == false){
			throw new SQLException("Failed to insert row, because Book Name is needed" + uri);
		}
		if(values.containsKey(BookTableMetaData.BOOK_ISBN) == false){
			values.put(BookTableMetaData.BOOK_ISBN, "Unkown ISBN");
		}
		if(values.containsKey(BookTableMetaData.BOOK_AUTHOR) == false){
			values.put(BookTableMetaData.BOOK_AUTHOR, "Unkown ahthor");
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, TestContentProviderMetaData.DATABASE_NAME, null, 
					TestContentProviderMetaData.DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	static class TestContentProviderMetaData{
		public static final String DATABASE_NAME = "book.db";
		public static final int DATABASE_VERSION = 1;
		public static final String AUTHORITY = "";
		public static final String BOOKS_TABLE_NAME = "";
		public static final class BookTableMetaData implements BaseColumns{
			public static final String TABLE_NAME = "books";
			public static final String BOOK_NAME = "name";
			public static final String BOOK_ISBN = "isbn";
			public static final String BOOK_AUTHOR = "author";
			public static final String CREATED_DATE = "created";
			public static final String MODIFIED_DATE = "modified";
			public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/books");
			public static final Uri CONTENT_SINGLE_URI = Uri.parse("content://"+AUTHORITY+"/books/#");
			public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.androidbook.book";
			public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/";
			public static final String DEFAULT_SORT_ORDER = "modified DESC";
		}
	}
}
