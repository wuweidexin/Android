package com.chen.mainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chen.chaobiao1_1.R;
import com.chen.datatransmit.*;
import com.chen.industrywirless.IndWirCop;
import com.chen.mainActivity.AllParameterSetting.ItemClickListener;
import com.chen.manual.ManCopIndex;
import com.chen.peowirless.CopyPeo;
import com.chen.peowirless.PeoWirGroCop;
import com.chen.recordbuilding.IndustryRecordbuilding;
import com.chen.recordbuilding.PeoRecordbuilding;

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
 * ��Ҫ���ܣ��������ݲ���Activity
 * @author chen
 *
 */
public class AllDataTransmit extends Activity {
	private ListView lvDataTra;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_data_transmit);
		createView();
	}
	private void createView() {
		GridView gridView = (GridView)findViewById(R.id.all_dat_itemsview);
		List<HashMap<String, Object>> lstImageItem
		= new ArrayList<HashMap<String, Object>>();
		lstImageItem.add(getMap(R.drawable.download, "��������"));
		lstImageItem.add(getMap(R.drawable.time, "ϵͳʱ��"));
		lstImageItem.add(getMap(R.drawable.version, "��ȡ�汾"));
	

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
	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String, Object> item = 
					(HashMap<String, Object>)arg0.getItemAtPosition(arg2);
			setTitle((String)item.get("itemText"));
			String s = (String)item.get("itemText");
			Log.d("�����ˣ�", s);
			
 			Intent i = null;
			if(s.equals("��������"))	{
				i = new Intent(AllDataTransmit.this, DataTransmit.class);
				
			}else if(s.equals("��ȡ�汾"))	{
				i = new Intent(AllDataTransmit.this, ReadVersion.class);
			}else if(s.equals("ϵͳʱ��"))	{
				i = new Intent(AllDataTransmit.this, SystemTime.class);
			}
			startActivity(i);

		}

	}
	private HashMap<String, Object> getMap(int imageView, String text){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("itemImage", imageView);
		map.put("itemText", text);
		return map;

	}
}
