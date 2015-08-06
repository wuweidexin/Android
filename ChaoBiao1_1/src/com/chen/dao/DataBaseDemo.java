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
 * ��Ҫ���ܣ���ʼ�����ݿ⡢��ɾ��Ĳ���
 * 
 * @author chen
 *
 */

public class DataBaseDemo extends SQLiteOpenHelper{
	static GetSQL getSql = new GetSQL();
	private static String DATABASE_NAME = getSql.getDATABASE_NAME();//��ȡ���ݿ�����
	private SQLiteDatabase sdb = null;
	
	//��ʼ�����ݿ�
	public DataBaseDemo(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	public DataBaseDemo(Context context) {
		super(context,DATABASE_NAME, null, 1);
	}
	
	//�ڵ�һ���°�װappʱ���ݿ⽫ִ�д˷������Ժ�������ݿⲻ���������ִ�д˷���
	public void onCreate(SQLiteDatabase db) {
		//���������
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
		//�����Ǹ����ʼ�����ݵ����
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
		
		Object[] obj4 = new Object[]{00000001,1,0,0,1,"2012-5-8","����",1,1};
		Object[] obj5 = new Object[]{00000002,1,0,0,1,"2012-5-8","����",1,1};
		
		db.execSQL( getSql.getInsertWatch(), obj4);
		db.execSQL( getSql.getInsertWatch(), obj5);
		
		Object[] obj6 = new Object[]{200,200,200,200,200,200,200,200,200,0,212,100,1};
		Object[] obj7 = new Object[]{200,200,200,200,200,200,200,200,200,0,212,100,1};
		
		db.execSQL( getSql.getInsertAircomsumption(), obj6);
		db.execSQL( getSql.getInsertAircomsumption(), obj7);
		
		Object[] obj8 = new Object[]{"����"};
		Object[] obj9 = new Object[]{"����"};
		
		db.execSQL( getSql.getInsertArea(), obj8);
		db.execSQL( getSql.getInsertArea(), obj9);
		
		Object[] obj11 = new Object[]{"����С��",1};
		Object[] obj12 = new Object[]{"�ܲ�С��",2};
		
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

	//�̳�SQLiteOpenHelper����ʵ�ֵ�һ���࣬��Ҫ�������ݿ�汾��
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/*
	 * �������ݲ���
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
	 * ��ѯ���ݲ���
	 *
	 */
	public Cursor queryData(String sql,String[] selectionArg) {
		Cursor cursor = null;
		try{
			cursor = this.getReadableDatabase().rawQuery(sql, selectionArg);
		}catch(Exception e){
			Log.d("��ѯ�����쳣��", e.toString());
		}
		return cursor;

	}
	//��ѯ�����ݴ����α�
	public Cursor getCursor(String table, String[] columns, String selection, String[] selectionArgs){
		
		Cursor c = this.getReadableDatabase().query(table, columns, selection, selectionArgs, null, null, null);
		return c;
		
	}
	/*
	 * �������ݲ���
	 */
	public void insertData(String sql, Object[] obj) {
		try{		
			this.getWritableDatabase().execSQL(sql, obj);
		}catch(Exception e){
			Log.d("���������쳣��", e.toString());
		}

	}

}
