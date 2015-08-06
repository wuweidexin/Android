package com.chen.datatransmit;

import com.chen.chaobiao1_1.R;
import com.chen.mainActivity.AllMenuItem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
/**
 * 主要功能：调用系统进行时间修改
 * @author chen
 *
 */
public class SystemTime extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		Button	timeAlert = null,
				back = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dat_tra_systemtime);
		timeAlert = (Button)findViewById(R.id.dat_tra_timealter);
		back = (Button)findViewById(R.id.dat_tra_back);
		timeAlert.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//调用系统时间修改Intent，进行系统时间修改
				startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SystemTime.this,AllMenuItem.class);
				startActivity(intent);
			}
		});
	}

}
