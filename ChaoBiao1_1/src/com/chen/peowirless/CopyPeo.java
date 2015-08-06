package com.chen.peowirless;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllMenuItem;
import com.chen.util.GetSQL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

/**
 * 
 * 主要功能：区域、小区、楼号、单元、层号的选择
 * 同时往下一个页面中传入上面的数据
 * @author chen
 *
 */
public class CopyPeo extends Activity{
	private Spinner areaSpin;
	private Spinner biotopeSpin;
	private Spinner buildSpin;
	private Spinner unitSpin;
	private EditText leverEdit;
	DataBaseDemo dbm = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();
	String area_id = null;
	String biotope_id = null;
	String build_id = null;
	String unit_id = null;
	String address = null;
	String lever = null;
	private Cursor cursor = null;
	
	Intent intent = null;

	private interface CopyPeoS{
		String Table_Name = "area";
		String Name = "name";
		String Id = "_id";

		String Biotope = "biotope";
		String Bio_id = "_id";
		String Bio_name = "name";
		String Bio_area = "area";
		
		String Unit = "unit";
		String Uni_id = "_id";
		String Uni_num = "num";
		String Uni_biotope = "biotope";
		}

	protected void onCreate(final Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cop_peo_index);
		createView();
	}



	@SuppressWarnings("deprecation")
	private void createView() {
		Button back = (Button)findViewById(R.id.cop_peo_cancle);
		Button copy = (Button)findViewById(R.id.cop_peo_submit);
		areaSpin = (Spinner)findViewById(R.id.cop_peo_area1);
		biotopeSpin = (Spinner)findViewById(R.id.cop_peo_biotope1);
		buildSpin = (Spinner)findViewById(R.id.cop_peo_bui1);
		unitSpin =(Spinner)findViewById(R.id.cop_peo_unit1);
		leverEdit =(EditText)findViewById(R.id.cop_peo_flo1);
		
		//查询区域表中的区域名称

		getArea();

		copy.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				lever = leverEdit.getText().toString().trim();
				if(!lever.equals("")){
					Bundle b = new Bundle();
					b.putString("area_id", area_id);
					b.putString("biotope_id", biotope_id);
					b.putString("build_id", build_id);
					b.putString("unit_id", unit_id);
					b.putString("lever", "1");
					b.putString("address", address);
					intent = new Intent(CopyPeo.this, PeoWirCopShow.class);
					intent.putExtra("db", b);
					startActivity(intent);
				}else{
					showMessDialog(CopyPeo.this, "信息不完整，请检查楼层是否输入！");
				}
				
			}

		});
		
		//返回按钮事件处理
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(CopyPeo.this, AllMenuItem.class);
				startActivity(intent);
			}
		});
	}
	
	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("提示");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.create().show();
		
	}
	public void getArea(){
		cursor = dbm.getCursor(CopyPeoS.Table_Name, new String[]{CopyPeoS.Id,CopyPeoS.Name}, null, null);
		if(cursor != null)
		{
			String[] cha = new String[cursor.getCount()];
			cursor.moveToFirst();
			for(int i=0; i<cha.length; i++){
				cha[i] = cursor.getString(1);

				cursor.moveToNext();
			}

			ArrayAdapter<String> sadapterArea = 
					new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cha
							);
			sadapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaSpin.setAdapter(sadapterArea);
			areaSpin.setOnItemSelectedListener(spinnerListener);
		}else{
			
		}
		
	}
	
	
	
	//对应区域下拉列表的事件监听
	private Spinner.OnItemSelectedListener spinnerListener = new Spinner.OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2,
				long arg3) {
			address ="地址是："+ adapter.getSelectedItem().toString();
			System.out.println("地址是："+address);
			area_id = String.valueOf(arg2+1);
			System.out.println("区域选择是："+area_id);
			
			getBiotope();
		}


		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	//创建小区的下拉类表
	public void getBiotope(){
		String ids = String.valueOf(area_id);
		String selection = "area=?";
		String[] selectionArgs ={ids};
		cursor = dbm.getCursor(CopyPeoS.Biotope, new String[]{CopyPeoS.Bio_id,CopyPeoS.Bio_name,CopyPeoS.Bio_area}, selection, selectionArgs);
		if(cursor != null){
			String[] cha = new String[cursor.getCount()];
			cursor.moveToFirst();
			for (int i = 0; i < cha.length; i++) {
				cha[i] = cursor.getString(1);
				cursor.moveToNext();
			}

			ArrayAdapter<String> sadapterBiotope = 
					new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cha
							);
			sadapterBiotope.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			biotopeSpin.setAdapter(sadapterBiotope);
			biotopeSpin.setOnItemSelectedListener(sBiotopeListener);
			cursor.close();
			dbm.close();
		}else{
			
		}
		
	}

	//对应小区下拉列表的监听
	private Spinner.OnItemSelectedListener sBiotopeListener = new Spinner.OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2,
				long arg3) {
			System.out.println("小区选择："+(arg2+1));
			address=address+"  "+adapter.getSelectedItem().toString();
			biotope_id = String.valueOf(arg2+1);
				getBuild();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		
		}

	};
	//创建楼号下拉列表
		private void getBuild() {
			String[] sarg ={area_id,biotope_id};
			String qBuildNum = 
					getSql.getqBuildNum();
			cursor = dbm.queryData(qBuildNum,sarg);
			if(cursor != null){
				String[] cha = new String[cursor.getCount()];
				System.out.println("楼数为："+ cursor.getCount());
				
				cursor.moveToFirst();
				for (int i = 0; i < cha.length; i++) {
					cha[i] = cursor.getString(0);
					cursor.moveToNext();
				}

				ArrayAdapter<String> sadapterBuild = 
						new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cha
								);
				sadapterBuild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				buildSpin.setAdapter(sadapterBuild);
				buildSpin.setOnItemSelectedListener(sBuildingListener);
				
				
				cursor.close();
				dbm.close();
			}else{
				
			}
			
			
		}
		private Spinner.OnItemSelectedListener sBuildingListener = new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				build_id = String.valueOf(arg2+1);
				address=address+"  楼号："+arg0.getSelectedItem().toString();
					getUnit();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
	
	//创建单元下拉列表
	private void getUnit() {
		String[] sarg ={area_id,biotope_id,build_id};
		String qUnitNum = 
				getSql.getqUnitNum() ;
		cursor = dbm.queryData(qUnitNum,sarg);
		if(cursor != null){
			String[] cha = new String[cursor.getCount()];
			//System.out.println("单元数为："+ cursor.getCount());
			
			cursor.moveToFirst();
			for (int i = 0; i < cha.length; i++) {
				cha[i] = cursor.getString(0);
				cursor.moveToNext();
			}
			ArrayAdapter<String> sadapterUnit = 
					new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cha
							);
			sadapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			unitSpin.setAdapter(sadapterUnit);
			unitSpin.setOnItemSelectedListener(sUnitListener);
			cursor.close();
			dbm.close();
		}else{
			
		}
		
	}
	
	private Spinner.OnItemSelectedListener sUnitListener = new Spinner.OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2,
				long arg3) {
			System.out.println("单元选择："+(arg2+1));
			unit_id= String.valueOf(arg2+1);
			address=address+"  单元："+adapter.getSelectedItem().toString();
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}

	};
}



