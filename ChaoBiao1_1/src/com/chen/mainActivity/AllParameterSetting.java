package com.chen.mainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chen.chaobiao1_1.R;
import com.chen.mainActivity.AllCopyWatch.ItemClickListener;
import com.chen.parametersetting.AlterPwd;
import com.chen.parametersetting.WatchParameterSetting;
import com.ds.bluetooth.ClientActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 主要功能：构建参数选择模块
 * @author chen
 *
 */
public class AllParameterSetting extends Activity {
	private ListView lvPraSet;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_parameter_setting);
		createView();
	}
	/*
	 * 初始化视图
	 */
	private void createView() {
		GridView gridView = (GridView)findViewById(R.id.all_par_itemsview);
		List<HashMap<String, Object>> lstImageItem
		= new ArrayList<HashMap<String, Object>>();
		lstImageItem.add(getMap(R.drawable.setting, "参数设置"));
		lstImageItem.add(getMap(R.drawable.password, "修改密码"));
	

		SimpleAdapter salmageItems = new SimpleAdapter(
				this, 
				lstImageItem,
				R.layout.griditem, 
				new String[]{"itemImage", "itemText"},
				new int[]{R.id.itemImage, R.id.itemText}
				);

		gridView.setAdapter(salmageItems);
		gridView.setOnItemClickListener(new ItemClickListener());



	}
	/*
	 * gridView点击事件监听
	 */
	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String, Object> item = 
					(HashMap<String, Object>)arg0.getItemAtPosition(arg2);
			setTitle((String)item.get("itemText"));
			String s = (String)item.get("itemText");
			//Log.d("你点击了：", s);
			
 			Intent i = null;
			if(s.equals("参数设置"))	{
				//i = new Intent(AllParameterSetting.this, WatchParameterSetting.class);
				i = new Intent(AllParameterSetting.this, ClientActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}else if(s.equals("修改密码")){
				i = new Intent(AllParameterSetting.this, AlterPwd.class);
				startActivity(i);
			}
			
		}

	}
	//创建gridView需要覆盖的方法
	private HashMap<String, Object> getMap(int imageView, String text){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("itemImage", imageView);
		map.put("itemText", text);
		return map;

	}
}
