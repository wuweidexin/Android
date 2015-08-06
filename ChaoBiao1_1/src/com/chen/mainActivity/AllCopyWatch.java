package com.chen.mainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.industrywirless.IndWirCop;
import com.chen.manual.ManCopIndex;
import com.chen.peowirless.CopyPeo;
import com.chen.peowirless.PeoWirGroCop;
import com.chen.recordbuilding.IndRecPlaceSelect;
import com.chen.recordbuilding.IndustryRecordbuilding;
import com.chen.recordbuilding.PeoRecPlaceSelect;
import com.chen.recordbuilding.PeoRecordbuilding;
import com.chen.util.GetSQL;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/**
 * ��Ҫ���ܣ�
 * @author chen
 *
 */
public class AllCopyWatch extends Activity{
	DataBaseDemo dbm = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();
	private String DATABASE_NAME = null;
	private SQLiteDatabase mSqlDatabase = null;
	private Cursor c = null;
	Bundle b = new Bundle();
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_copy_watch);
		createView();


	}

	private void createView() {
		GridView gridView = (GridView)findViewById(R.id.all_cop_itemsview);
		List<HashMap<String, Object>> lstImageItem
		= new ArrayList<HashMap<String, Object>>();
		lstImageItem.add(getMap(R.drawable.copywatch, "�������߳���"));
		lstImageItem.add(getMap(R.drawable.copywatch, "��ҵ���߳���"));
		//lstImageItem.add(getMap(R.drawable.byhand, "�ֹ�����"));
		lstImageItem.add(getMap(R.drawable.minyong, "���ý���"));
		lstImageItem.add(getMap(R.drawable.gongye, "��ҵ����"));

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

		
		public void onItemClick(AdapterView<?> adaView, View view, int location,
				long arg3) {
			HashMap<String, Object> item = 
					(HashMap<String, Object>)adaView.getItemAtPosition(location);
			String s = (String)item.get("itemText");
			Log.d("�����ˣ�", s);
			
 			Intent i = null;
			if(s.equals("�������߳���"))	{
				
				
				i = new Intent(AllCopyWatch.this, CopyPeo.class);				
			}else if(s.equals("��ҵ���߳���"))	{
				i = new Intent(AllCopyWatch.this, IndWirCop.class);
			}else if(s.equals("�ֹ�����"))	{
				i = new Intent(AllCopyWatch.this, ManCopIndex.class);
			}else if(s.equals("���ý���"))	{
				i = new Intent(AllCopyWatch.this, PeoRecPlaceSelect.class);
			}else if(s.equals("��ҵ����"))	{
				i = new Intent(AllCopyWatch.this, IndRecPlaceSelect.class);
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
