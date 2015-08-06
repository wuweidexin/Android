package com.chen.manual;


import com.chen.chaobiao1_1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ManCopWrite extends Activity{
	Button back = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.man_cop_write);
		createView();
	}

	private void createView() {
		RadioButton rb1 = (RadioButton)findViewById(R.id.man_cop_wriPrice);
		RadioButton rb2 = (RadioButton)findViewById(R.id.man_cop_wriMoney);
		RadioButton rb3 = (RadioButton)findViewById(R.id.man_cop_wriTime);
		RadioButton rb4 = (RadioButton)findViewById(R.id.man_cop_wriValue);
		RadioButton rb5 = (RadioButton)findViewById(R.id.man_cop_wriStandarW);
		RadioButton rb6 = (RadioButton)findViewById(R.id.man_cop_wriURL);
		RadioGroup rg = (RadioGroup)findViewById(R.id.man_cop_wriRG);
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				changeView(checkedId);
				
			}

			private void changeView(int checkedId) {
				RelativeLayout r = (RelativeLayout)findViewById(R.id.man_cop_wriRL);
				LinearLayout.LayoutParams params = new 
						LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.HORIZONTAL
								);
				
				TextView wMess = (TextView)findViewById(R.id.man_cop_wMess);
				EditText wMessEdit = (EditText)findViewById(R.id.man_cop_wMessEdit);
				
				TextView wMoney = (TextView)findViewById(R.id.man_cop_wMoney);
				EditText wMoneyEdit = (EditText)findViewById(R.id.man_cop_wMoneyEdit);
				TextView wTime = (TextView)findViewById(R.id.man_cop_wTime);
				EditText wTimeEdit = (EditText)findViewById(R.id.man_cop_wTimeEdit);
				
				TextView wStandarTime = (TextView)findViewById(R.id.man_cop_wStandarTime);
				EditText wStandarTimeEdit = (EditText)findViewById(R.id.man_cop_wStandarTimeEdit);
				
				TextView wStandarWat = (TextView)findViewById(R.id.man_cop_wStandarWat);
				EditText wStandarWatEdit = (EditText)findViewById(R.id.man_cop_wStandarWatEdit);
				
				TextView wURL = (TextView)findViewById(R.id.man_cop_wURL);
				EditText wURLEdit = (EditText)findViewById(R.id.man_cop_wURLEdit);
				
				CheckBox wChoseValue = (CheckBox)findViewById(R.id.man_cop_wChoseValue);
				
				switch(checkedId){
				
				case R.id.man_cop_wriPrice:
					wMess.setVisibility(0);
					wMessEdit.setVisibility(0);
					wMoney.setVisibility(8);
					wMoneyEdit.setVisibility(8);
					wTime.setVisibility(8);
					wTimeEdit.setVisibility(8);
					wStandarTime.setVisibility(8);
					wStandarTimeEdit.setVisibility(8);
					wStandarWat.setVisibility(8);
					wStandarWatEdit.setVisibility(8);
					wURL.setVisibility(8);
					wURLEdit.setVisibility(8);
					wChoseValue.setVisibility(8);
					break;
				case R.id.man_cop_wriMoney:
					wMess.setVisibility(8);
					wMessEdit.setVisibility(8);
					wMoney.setVisibility(0);
					wMoneyEdit.setVisibility(0);
					wTime.setVisibility(0);
					wTimeEdit.setVisibility(0);
					wStandarTime.setVisibility(8);
					wStandarTimeEdit.setVisibility(8);
					wStandarWat.setVisibility(8);
					wStandarWatEdit.setVisibility(8);
					wURL.setVisibility(8);
					wURLEdit.setVisibility(8);
					wChoseValue.setVisibility(8);
					break;
				case R.id.man_cop_wriTime:
					wMess.setVisibility(8);
					wMessEdit.setVisibility(8);
					wMoney.setVisibility(8);
					wMoneyEdit.setVisibility(8);
					wTime.setVisibility(8);
					wTimeEdit.setVisibility(8);
					wStandarTime.setVisibility(0);
					wStandarTimeEdit.setVisibility(0);
					wStandarWat.setVisibility(8);
					wStandarWatEdit.setVisibility(8);
					wURL.setVisibility(8);
					wURLEdit.setVisibility(8);
					wChoseValue.setVisibility(8);
					break;
				case R.id.man_cop_wriValue:
					wMess.setVisibility(8);
					wMessEdit.setVisibility(8);
					wMoney.setVisibility(8);
					wMoneyEdit.setVisibility(8);
					wTime.setVisibility(8);
					wTimeEdit.setVisibility(8);
					wStandarTime.setVisibility(8);
					wStandarTimeEdit.setVisibility(8);
					wStandarWat.setVisibility(8);
					wStandarWatEdit.setVisibility(8);
					wURL.setVisibility(8);
					wURLEdit.setVisibility(8);
					wChoseValue.setVisibility(0);
					break;
				case R.id.man_cop_wriStandarW:
					wMess.setVisibility(8);
					wMessEdit.setVisibility(8);
					wMoney.setVisibility(8);
					wMoneyEdit.setVisibility(8);
					wTime.setVisibility(8);
					wTimeEdit.setVisibility(8);
					wStandarTime.setVisibility(8);
					wStandarTimeEdit.setVisibility(8);
					wStandarWat.setVisibility(0);
					wStandarWatEdit.setVisibility(0);
					wURL.setVisibility(8);
					wURLEdit.setVisibility(8);
					wChoseValue.setVisibility(8);
					break;
				case R.id.man_cop_wriURL:
					wMess.setVisibility(8);
					wMessEdit.setVisibility(8);
					wMoney.setVisibility(8);
					wMoneyEdit.setVisibility(8);
					wTime.setVisibility(8);
					wTimeEdit.setVisibility(8);
					wStandarTime.setVisibility(8);
					wStandarTimeEdit.setVisibility(8);
					wStandarWat.setVisibility(8);
					wStandarWatEdit.setVisibility(8);
					wURL.setVisibility(0);
					wURLEdit.setVisibility(0);
					wChoseValue.setVisibility(8);
					break;
				}
				
			}
		});
		
		Button b = (Button)findViewById(R.id.man_cop_buExecuteComW);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		back = (Button)findViewById(R.id.man_cop_exitW);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ManCopWrite.this, ManCopIndex.class);
				startActivity(intent);
			}
		});
	}
}
		/*	private  final String[] manReadCom = {
			"请选择",
			"读计量数据",
			"读价格表",
			"读表地址",
			"读购入金额",
			"读历史计量数据",
			"读流量计信息"};
		 * 
		 * private  final String[] manfactoryReset = {"请选择","出厂设置"};
	
		private ArrayAdapter<String> readAdapter;
		private ArrayAdapter<String> executeAdapter;
		private ArrayAdapter<String> resetAdapter;
		 * Spinner read = (Spinner)findViewById(R.id.man_cop_readComW);
		Spinner manExecute = (Spinner)findViewById(R.id.man_cop_executeComW);
		Spinner factoryReset = (Spinner)findViewById(R.id.man_cop_factoryResetW);
		
		readAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item,
				manReadCom
				);
		readAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		read.setAdapter(readAdapter);
		//read.setOnItemSelectedListener();
		
		executeAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item,
				manExecuteCom
				);
		executeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		manExecute.setAdapter(executeAdapter);
		
		resetAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item,
				manfactoryReset
				);
		resetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		factoryReset.setAdapter(resetAdapter);
		
		
		manExecute.setOnItemSelectedListener(manExecuteListener);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		*/
		
	

