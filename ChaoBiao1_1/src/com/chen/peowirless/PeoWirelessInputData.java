package com.chen.peowirless;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.industrywirless.IndWirInputData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 主要功能：无线抄表数据录入的Activity
 * @author Administrator
 *
 */
public class PeoWirelessInputData extends Activity{
	TextView watchNum,customer;
	EditText airUse,moneyleave;
	Button record;
	String w_id;
	DataBaseDemo dbd = new DataBaseDemo(this);
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peo_wir_inputdata);
		createView();
	}
	private void createView() {
		Intent i = getIntent();
		Bundle b = i.getBundleExtra("db");
		w_id = b.getString("w_id");
		String c_name = b.getString("c_name");
		watchNum = (TextView)findViewById(R.id.peo_wir_inputwatch);
		customer = (TextView)findViewById(R.id.peo_wir_inputcustomer);
		airUse = (EditText)findViewById(R.id.peo_wir_inputairuse);
		moneyleave = (EditText)findViewById(R.id.peo_wir_inputmoneyleave);
		//back = (Button)findViewById(R.id.ind_wir_inputback);
		record = (Button)findViewById(R.id.peo_wir_inputrecord);
		watchNum.setText(w_id);
		customer.setText(c_name);
		
		
		record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String a_use = airUse.getText().toString().trim();
				String m_leave = moneyleave.getText().toString().trim();
				if(a_use.equals("")||m_leave.equals("")){
					showMessDialog(PeoWirelessInputData.this,"数据输入为空，请重新输入");
				}else{
					Log.d("使用量、剩余金额", a_use+"****"+m_leave);
					ContentValues cv = new ContentValues();
					cv.put("totaluse", a_use);
					cv.put("moneyleave", m_leave);
					String[] wId = {w_id};
					dbd.updateData("watch", cv, "_id=?", wId);
					showMessDialog(PeoWirelessInputData.this,"录入成功，按返回键返回上一页");
				}
				
			}
		});
		
	}
	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("提示");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.setNegativeButton("返回", null);
		builder.create().show();

	}

}
