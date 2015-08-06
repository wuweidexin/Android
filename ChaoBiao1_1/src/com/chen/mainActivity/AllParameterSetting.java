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
 * ��Ҫ���ܣ���������ѡ��ģ��
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
	 * ��ʼ����ͼ
	 */
	private void createView() {
		GridView gridView = (GridView)findViewById(R.id.all_par_itemsview);
		List<HashMap<String, Object>> lstImageItem
		= new ArrayList<HashMap<String, Object>>();
		lstImageItem.add(getMap(R.drawable.setting, "��������"));
		lstImageItem.add(getMap(R.drawable.password, "�޸�����"));
	

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
	 * gridView����¼�����
	 */
	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String, Object> item = 
					(HashMap<String, Object>)arg0.getItemAtPosition(arg2);
			setTitle((String)item.get("itemText"));
			String s = (String)item.get("itemText");
			//Log.d("�����ˣ�", s);
			
 			Intent i = null;
			if(s.equals("��������"))	{
				//i = new Intent(AllParameterSetting.this, WatchParameterSetting.class);
				i = new Intent(AllParameterSetting.this, ClientActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}else if(s.equals("�޸�����")){
				i = new Intent(AllParameterSetting.this, AlterPwd.class);
				startActivity(i);
			}
			
		}

	}
	//����gridView��Ҫ���ǵķ���
	private HashMap<String, Object> getMap(int imageView, String text){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("itemImage", imageView);
		map.put("itemText", text);
		return map;

	}
}
