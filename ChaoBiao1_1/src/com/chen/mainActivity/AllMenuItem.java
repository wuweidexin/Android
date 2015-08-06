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
 * 主要功能：构建分页TabActivity
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
	 * 创建Tab分页模式
	 */
	private void createTabHost() {
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		TabSpec spec;
		Intent intent;
		

		/*
		 * 每个tap分页里面分别是一个Activity，这些Activity
		 * 布局是用GridView网格布局
		 */
		intent = new Intent().setClass(this, AllParameterSetting.class);
		spec = tabHost.newTabSpec("parameterSetting").setIndicator("参数设置", 
				res.getDrawable(R.drawable.setparemeter))
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, AllCopyWatch.class);
		spec = tabHost.newTabSpec("copyWatch").setIndicator("抄      表", 
				res.getDrawable(R.drawable.copywatch))
				.setContent(intent);
		tabHost.addTab(spec);
		
		

		intent = new Intent().setClass(this, AllDataTransmit.class);
		spec = tabHost.newTabSpec("dataTransmit").setIndicator("数据传输", 
				res.getDrawable(R.drawable.datatransmit))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);




	}



}
