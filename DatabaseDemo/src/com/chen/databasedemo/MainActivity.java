package com.chen.databasedemo;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SQLiteDatabase mSqlDatabase = null;
	private final String DATABASE_NAME = "Test.db";
	private final String TABLE_NAME = "TABLE_TEXT1";
	private final String COLUMN_ID = "_id";
	private final String COLUMN_NAME = "name";
	private final String COLUMN_AGE = "age";
	private final String CREATETABLE = "create table if not exists "
			+ TABLE_NAME +" (" +
			COLUMN_ID + " INTEGER PRIMARY KEY," +
			COLUMN_NAME + " TEXT," +
			COLUMN_AGE + " INTEGER" +
			");";
	private ListView lv;
	private TextView textv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView)findViewById(R.id.lv);
		textv = (TextView)findViewById(R.id.textv);
		createTable();
		//insertData();
		queryData();
	}

	private void queryData() {
		try{
			String str = "select * from "+ TABLE_NAME;
			Cursor cursor = mSqlDatabase.rawQuery(str, null);
			int count = cursor.getCount();
			System.out.println("记录数是:" + count);
			
			if(cursor != null){
				@SuppressWarnings("deprecation")
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(
								this,
								R.layout.list, 
								cursor, 
								new String[]{COLUMN_ID,COLUMN_NAME,COLUMN_AGE}, 
								new int[]{R.id.tv1,R.id.tv2,R.id.tv3}
								);
				lv.setAdapter(adapter);
				textv.setText(Integer.toString(cursor.getCount()));
			}
		}catch(Exception e){
			Log.d("查询数据异常：", e.toString());
		}

	}

	private void insertData() {
		try{
			String str = "insert into " + TABLE_NAME + "(" +
					 COLUMN_NAME + "," + COLUMN_AGE + ")" +
					" values(?,?)";
			Object[] obj = new Object[]{"王五",22};
			Object[] obj1 = new Object[]{"张三",21};
			mSqlDatabase.execSQL(str, obj);
			mSqlDatabase.execSQL(str, obj1);
		}catch(Exception e){
			Log.d("插入数据异常：", e.toString());
		}

	}

	private void createTable() {

		try{
			mSqlDatabase = this.openOrCreateDatabase(DATABASE_NAME, Activity.MODE_PRIVATE, null);

		}catch(Exception e){
			Log.d("打开或创建数据库异常：",e.toString());
		}

		try{
			mSqlDatabase.execSQL(CREATETABLE);
		}catch(Exception e){
			Log.d("创建表异常：", e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
