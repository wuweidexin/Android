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
 * ��Ҫ���ܣ�ѡ��ҵ�û�������С��
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
	
	//����һ��Sting��int�����洢��ѯ�����Ľ��
	String[] aName = null;
	int [] aId = null;
	String[] biotopeS = null;
	int[] biotopeIdS = null;
	//����һ��Sting�����洢������ѡ��Ľ��
	String areaSelect = null;
	String areaIdSelect = null;
	String biotopeSelect = null;
	String biotopeIdSelect = null;
	
	//����������������е��ֶ�
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
	 * ������ͼ
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
			System.out.println("Id�ǣ�"+aId[i] +"��������"+ aName[i]);
			cursor.moveToNext();
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSpin.setAdapter(adapter);
		areaSpin.setOnItemSelectedListener(areaLlistener);
		
		
		
		//ѡ��ť���¼�����
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
		
		//���ذ�ť���¼�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(IndRecPlaceSelect.this, AllMenuItem.class);
				startActivity(intent);
			}
		});
	}
	
	/*
	 *��������ѡ��������б�ķ��� 
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
	 * ����С��������
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
			System.out.println("С��ID:"+biotopeIdS[i]+"С�����֣�"+ biotopeS[i]);
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
