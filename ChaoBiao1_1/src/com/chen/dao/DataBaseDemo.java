package com.chen.dao;

import com.chen.util.GetSQL;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 主要功能：初始化数据库、增删查改操作
 * 
 * @author chen
 *
 */

public class DataBaseDemo extends SQLiteOpenHelper{
	static GetSQL getSql = new GetSQL();
	private static String DATABASE_NAME = getSql.getDATABASE_NAME();//获取数据库名字
	private SQLiteDatabase sdb = null;
	
	//初始化数据库
	public DataBaseDemo(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	public DataBaseDemo(Context context) {
		super(context,DATABASE_NAME, null, 1);
	}
	
	//在第一次新安装app时数据库将执行此方法，以后如果数据库不变更将不再执行此方法
	public void onCreate(SQLiteDatabase db) {
		//创建表语句
		db.execSQL(getSql.getCreateAdmin());
		db.execSQL(getSql.getCreateAircomsumption());
		db.execSQL( getSql.getCreateArea());
		db.execSQL( getSql.getCreateBiotope());
		db.execSQL(getSql.getCreateBuilding());
		db.execSQL( getSql.getCreateCustomer());
		db.execSQL( getSql.getCreateIndustryuser());
		db.execSQL( getSql.getCreateLever());
		db.execSQL( getSql.getCreateUnit());
		db.execSQL( getSql.getCreateWatch());
		//下面是给表初始化数据的语句
		Object[] obj0 = new Object[]{"11","11"};
		Object[] obj = new Object[]{"22","22"};
		db.execSQL( getSql.getInsertAdmin(), obj0);
		db.execSQL( getSql.getInsertAdmin(), obj);
		
		Object[] obj1 = new Object[]{"rr",1,1,1,1,1,1,1,1,1};
		Object[] obj2 = new Object[]{"rr",1,1,2,2,2,2,3,1,1};
		
		db.execSQL(getSql.getInsertCustomer(), obj1);
		db.execSQL(getSql.getInsertCustomer(), obj2);
		
		Object[] obj3 = new Object[]{"rr",1,1,1,1,1,};
		db.execSQL( getSql.getInsertIndustryuser(), obj3);
		
		Object[] obj4 = new Object[]{00000001,1,0,0,1,"2012-5-8","开阀",1,1};
		Object[] obj5 = new Object[]{00000002,1,0,0,1,"2012-5-8","开阀",1,1};
		
		db.execSQL( getSql.getInsertWatch(), obj4);
		db.execSQL( getSql.getInsertWatch(), obj5);
		
		Object[] obj6 = new Object[]{200,200,200,200,200,200,200,200,200,0,212,100,1};
		Object[] obj7 = new Object[]{200,200,200,200,200,200,200,200,200,0,212,100,1};
		
		db.execSQL( getSql.getInsertAircomsumption(), obj6);
		db.execSQL( getSql.getInsertAircomsumption(), obj7);
		
		Object[] obj8 = new Object[]{"临潼"};
		Object[] obj9 = new Object[]{"雁塔"};
		
		db.execSQL( getSql.getInsertArea(), obj8);
		db.execSQL( getSql.getInsertArea(), obj9);
		
		Object[] obj11 = new Object[]{"朝阳小区",1};
		Object[] obj12 = new Object[]{"跑步小区",2};
		
		db.execSQL( getSql.getInsertBiotope(), obj11);
		db.execSQL( getSql.getInsertBiotope(), obj12);

		
		Object[] obj14 = new Object[]{1,1};
		Object[] obj15 = new Object[]{1,2};
		db.execSQL( getSql.getInsertBuilding(), obj14);
		db.execSQL( getSql.getInsertBuilding(), obj15);
		
		
		Object[] obj17 = new Object[]{1,1};
		Object[] obj18 = new Object[]{1,2};
		db.execSQL( getSql.getInsertUnit(), obj17);
		db.execSQL( getSql.getInsertUnit(), obj18);

		Object[] obj19 = new Object[]{1,1};	
		Object[] obj20 = new Object[]{2,1};
		Object[] obj21 = new Object[]{1,2};
		
		db.execSQL( getSql.getInsertLever(), obj19);
		db.execSQL( getSql.getInsertLever(), obj20);
		db.execSQL( getSql.getInsertLever(), obj21);
	}

	//继承SQLiteOpenHelper必须实现的一个类，主要更新数据库版本等
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/*
	 * 更新数据操作
	 */
	public int updateData(String table, ContentValues values, String whereClause, String[] whereArgs){
		int result = -1;
		try {
			result = this.getReadableDatabase().update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			
		}
		return result;
	}
	/*
	 * 查询数据操作
	 *
	 */
	public Cursor queryData(String sql,String[] selectionArg) {
		Cursor cursor = null;
		try{
			cursor = this.getReadableDatabase().rawQuery(sql, selectionArg);
		}catch(Exception e){
			Log.d("查询数据异常：", e.toString());
		}
		return cursor;

	}
	//查询表数据传回游标
	public Cursor getCursor(String table, String[] columns, String selection, String[] selectionArgs){
		
		Cursor c = this.getReadableDatabase().query(table, columns, selection, selectionArgs, null, null, null);
		return c;
		
	}
	/*
	 * 插入数据操作
	 */
	public void insertData(String sql, Object[] obj) {
		try{		
			this.getWritableDatabase().execSQL(sql, obj);
		}catch(Exception e){
			Log.d("插入数据异常：", e.toString());
		}

	}

}
