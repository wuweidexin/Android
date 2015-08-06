package com.chen.manual;

import com.chen.chaobiao1_1.R;
import com.chen.mainActivity.AllMenuItem;
import com.chen.recordbuilding.IndustryRecordbuilding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
/**
 * 该类是手工操作的进入的首个类
 * 通过点击下拉条来进入相应的选项执行相应的命令
 * @author Administrator
 *
 */
public class ManCopIndex extends Activity{
	long temp;
	Button back = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.man_cop_index);
		createView();
	}
	private void createView() {
	
		RadioButton rb1 = (RadioButton)findViewById(R.id.man_cop_indRead);
		RadioButton rb2 = (RadioButton)findViewById(R.id.man_cop_indWrite);
		RadioButton rb3 = (RadioButton)findViewById(R.id.man_cop_indReset);
		RadioGroup rg = (RadioGroup)findViewById(R.id.man_cop_indRG);
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				temp = checkedId;
			}
		});
		
		Button b = (Button)findViewById(R.id.man_cop_butExecuteCom);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(R.id.man_cop_indRead == temp){
					Intent i = new Intent(ManCopIndex.this, ManCopRead.class);
					startActivity(i);
				}else if(R.id.man_cop_indWrite == temp){
					Intent i = new Intent(ManCopIndex.this, ManCopWrite.class);
					startActivity(i);
				}else{
					
				}
				
			}
		});
		back = (Button)findViewById(R.id.man_cop_butExit);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ManCopIndex.this, AllMenuItem.class);
				startActivity(intent);
			}
		});
	}
	
	/*private  final String[] manReadCom = {
			"读计量数据",
			"读价格表",
			"读表地址",
			"读购入金额",
			"读历史计量数据",
			"读流量计信息"};
	private  final String[] manExecuteCom = {
			"写价格表",
			"写购入金额",
			"写标准时间",
			"写阀门控制",
			"写基表底数",
			"写地址"};
	private  final String[] manfactoryReset = {"出厂设置"};
	private ArrayAdapter<String> readAdapter;
	private ArrayAdapter<String> executeAdapter;
	private ArrayAdapter<String> resetAdapter;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.man_cop_index);
		createView();
	}

	private void createView() {
		Spinner read = (Spinner)findViewById(R.id.man_cop_readCom);
		Spinner manExecute = (Spinner)findViewById(R.id.man_cop_executeCom);
		Spinner factoryReset = (Spinner)findViewById(R.id.man_cop_factoryReset);
		
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
		
		
		Button b = (Button)findViewById(R.id.man_cop_butExecuteCom);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ManCopIndex.this, ManCopWrite.class);
				startActivity(i);
			}
		});
	}
*/
}
