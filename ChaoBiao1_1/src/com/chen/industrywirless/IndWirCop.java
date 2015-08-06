package com.chen.industrywirless;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllMenuItem;
import com.chen.recordbuilding.IndRecPlaceSelect;
import com.chen.recordbuilding.IndustryRecordbuilding;

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
 * 主要功能：选择区域、小区、楼、单元、楼层
 * @author chen
 *
 */
public class IndWirCop extends Activity{
	
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
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ind_wir_cop);
		creatView();
	}
	/*
	 * 创建视图
	 */
	private void creatView() {
		areaSpin = (Spinner)findViewById(R.id.ind_wir_choseArea);
		biotopeSpin = (Spinner)findViewById(R.id.ind_wir_choseBiotope);
		chose = (Button)findViewById(R.id.ind_wir_chose);
		back = (Button)findViewById(R.id.ind_wir_back);
		
		String areaSql = "select _id,name from area";
		cursor = dbd.queryData(areaSql, null);
		//查询区域结果不为空，初始化区域下拉条
		if(cursor != null){
			aName = new String[cursor.getCount()];
			aId = new int[cursor.getCount()];
			cursor.moveToFirst();
			for (int i = 0; i < aName.length; i++) {
				aId[i] = cursor.getInt(0);
				aName[i] = cursor.getString(1);
				cursor.moveToNext();
			}
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aName);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaSpin.setAdapter(adapter);
			areaSpin.setOnItemSelectedListener(areaLlistener);//添加监听
		}
		
		
		
		
		/*
		 * 选择按钮的事件处理,往IndWirCopShow中传入区域、小区、楼号、单元、楼层
		 */
		chose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				Bundle b = new Bundle();
				b.putString("areaSelect", areaSelect);
				b.putString("areaIdSelect", areaIdSelect);
				b.putString("biotopeSelect", biotopeSelect);
				b.putString("biotopeIdSelect", biotopeIdSelect);
				intent = new Intent(IndWirCop.this,IndWirCopShow.class);
				intent.putExtra("db", b);
				startActivity(intent);
			}
		});
		
		//返回按钮的事件处理
		back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(IndWirCop.this, AllMenuItem.class);
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
			areaIdSelect = String.valueOf(aId[arg2]);//保存选择区域ID
			areaSelect = aName[arg2];//保存选择区域名称
			creatBiotopeSpin();//创建小区下拉条
			
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	};
	/*
	 * 小区下拉条创建
	 */
	private void creatBiotopeSpin() {
		String biotopeSql = "select _id,name" +
				" from biotope where  area = ?";
		String[] selection = {areaIdSelect};
		cursor = dbd.queryData(biotopeSql, selection);
		biotopeS = new String[cursor.getCount()];
		biotopeIdS = new int[cursor.getCount()];
		cursor.moveToFirst();
		//查询区域结果不为空，初始化小区下拉条
		for (int i = 0; i < biotopeS.length; i++) {
			biotopeIdS[i] = cursor.getInt(0);
			biotopeS[i] = cursor.getString(1);
			cursor.moveToNext();
			//System.out.println("小区ID:"+biotopeIdS[i]+"小区名字："+ biotopeS[i]);
		}
		adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, biotopeS);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		biotopeSpin.setAdapter(adapter);
		//小区下拉条监听事件
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
	
	/*
	 * 这个类是创建工业抄表的第一个类
	 */


}
