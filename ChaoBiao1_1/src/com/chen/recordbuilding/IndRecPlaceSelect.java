package com.chen.recordbuilding;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllCopyWatch;
import com.chen.mainActivity.AllMenuItem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
/**
 * 主要功能：选择工业用户的区域、小区
 * @author chen
 *
 */
public class IndRecPlaceSelect extends Activity{
	DataBaseDemo dbd = new DataBaseDemo(this);
	Spinner areaSpin = null;
	Spinner biotopeSpin = null;
	Button chose = null;
	Button back = null;
	Intent intent = null;
	Cursor  cursor = null;
	ArrayAdapter<String> adapter = null;
	
	//定义一组Sting、int用来存储查询出来的结果
	String[] aName = null;
	int [] aId = null;
	String[] biotopeS = null;
	int[] biotopeIdS = null;
	//定义一组Sting用来存储下拉条选择的结果
	String areaSelect = null;
	String areaIdSelect = null;
	String biotopeSelect = null;
	String biotopeIdSelect = null;
	
	//抽象类用来定义表中的字段
	private interface PerRec{
		String areaName = "name";
		String areaId = "_id";
		String biotopeID = "_id";
		String bio_are_id = "area";
	}
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ind_rec_placeselect);
		creatView();
	}
	/*
	 * 创建视图
	 */
	private void creatView() {
		areaSpin = (Spinner)findViewById(R.id.ind_recsel_choseArea);
		biotopeSpin = (Spinner)findViewById(R.id.ind_recsel_choseBiotope);
		chose = (Button)findViewById(R.id.ind_recsel_chose);
		back = (Button)findViewById(R.id.ind_recsel_back);
		
		
		
		String areaSql = "select * from area";
		cursor = dbd.queryData(areaSql, null);
		aName = new String[cursor.getCount()];
		aId = new int[cursor.getCount()];
		cursor.moveToFirst();
		for (int i = 0; i < aName.length; i++) {
			aId[i] = cursor.getInt(0);
			aName[i] = cursor.getString(1);
			System.out.println("Id是："+aId[i] +"区域名："+ aName[i]);
			cursor.moveToNext();
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSpin.setAdapter(adapter);
		areaSpin.setOnItemSelectedListener(areaLlistener);
		
		
		
		//选择按钮的事件处理
		chose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putString("areaSelect", areaSelect);
				b.putString("areaIdSelect", areaIdSelect);
				b.putString("biotopeSelect", biotopeSelect);
				b.putString("biotopeIdSelect", biotopeIdSelect);
				intent = new Intent(IndRecPlaceSelect.this,IndustryRecordbuilding.class);
				intent.putExtra("db", b);
				startActivity(intent);
			}
		});
		
		//返回按钮的事件处理
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(IndRecPlaceSelect.this, AllMenuItem.class);
				startActivity(intent);
			}
		});
	}
	
	/*
	 *监听区域选择的下拉列表的方法 
	 */
	private Spinner.OnItemSelectedListener areaLlistener = new Spinner.OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			areaIdSelect = String.valueOf(aId[arg2]);
			areaSelect = aName[arg2];
			
			creatBiotopeSpin();
			
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	};
	/*
	 * 创建小区下拉条
	 */
	private void creatBiotopeSpin() {
		String biotopeSql = "select _id,name" +
				" from biotope where  area = ?";
		String[] selection = {areaIdSelect};
		cursor = dbd.queryData(biotopeSql, selection);
		biotopeS = new String[cursor.getCount()];
		biotopeIdS = new int[cursor.getCount()];
		cursor.moveToFirst();
		for (int i = 0; i < biotopeS.length; i++) {
			biotopeIdS[i] = cursor.getInt(0);
			biotopeS[i] = cursor.getString(1);
			cursor.moveToNext();
			System.out.println("小区ID:"+biotopeIdS[i]+"小区名字："+ biotopeS[i]);
		}
		adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, biotopeS);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		biotopeSpin.setAdapter(adapter);
		biotopeSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				biotopeSelect = biotopeS[arg2];
				biotopeIdSelect = String.valueOf(biotopeIdS[arg2]);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		
	}
}
