package com.example.testormlite;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelpers extends OrmLiteSqliteOpenHelper{

	private static DatabaseHelpers databaseHelper;
	private Dao<Sys_User, String> sys_user_dao; 
	private static String DATABASE_NAME = "TestORMLite.db";
	private static int VERSION = 1;
	
	public DatabaseHelpers(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		
		try {
			TableUtils.createTable(arg1,Sys_User.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initSys_User();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	public static DatabaseHelpers getInstance(Context context){
		if(databaseHelper == null){
    		databaseHelper = OpenHelperManager.getHelper(context, 
    				DatabaseHelpers.class);
    	}
		return databaseHelper;
	}
	/**
	 * 
	 * @return 返回sys_user_dao
	 * @throws SQLException
	 */
	public Dao<Sys_User, String> getSys_UserDao() throws SQLException{
		if(sys_user_dao == null){
			sys_user_dao = getDao(Sys_User.class);
		}
		return sys_user_dao;
		
	}
	/**
	 * 初始化几个Sys_User
	 * @return
	 */
	public void initSys_User(){
		Sys_User s = new Sys_User();
		s.setCompany_id("1");
		s.setDel_status("0");
		s.setDept_id("1");
		s.setEmail("123456789@qq.com");
		s.setId("1");
		s.setId_no("1");
		s.setLast_time(null);
		s.setLocked("lock");
		s.setMobile_phone("123456789");
		s.setName("李云龙");
		s.setOffice_phone("40040088");
		s.setPassword("000000");
		s.getRetry_count();
		s.setSex("男");
		s.setSort_num(12);
		
		//insert a User
		try {
			getSys_UserDao().create(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static DatabaseHelpers getHelper(Context context){
		if(databaseHelper == null){
			databaseHelper = OpenHelperManager.getHelper(context, 
    				DatabaseHelpers.class);
    	}
		return databaseHelper;
	}
}
