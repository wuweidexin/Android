package com.chen.mainActivity;

import com.chen.chaobiao1_1.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
/**
 * ��Ҫ���ܣ�������ҳTabActivity
 * @author chen
 *
 */
public class AllMenuItem extends TabActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_menu_item);
		createTabHost();


	}

	/*
	 * ����Tab��ҳģʽ
	 */
	private void createTabHost() {
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		TabSpec spec;
		Intent intent;
		

		/*
		 * ÿ��tap��ҳ����ֱ���һ��Activity����ЩActivity
		 * ��������GridView���񲼾�
		 */
		intent = new Intent().setClass(this, AllParameterSetting.class);
		spec = tabHost.newTabSpec("parameterSetting").setIndicator("��������", 
				res.getDrawable(R.drawable.setparemeter))
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, AllCopyWatch.class);
		spec = tabHost.newTabSpec("copyWatch").setIndicator("��      ��", 
				res.getDrawable(R.drawable.copywatch))
				.setContent(intent);
		tabHost.addTab(spec);
		
		

		intent = new Intent().setClass(this, AllDataTransmit.class);
		spec = tabHost.newTabSpec("dataTransmit").setIndicator("���ݴ���", 
				res.getDrawable(R.drawable.datatransmit))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);




	}



}
