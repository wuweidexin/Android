package com.chen.peowirless;

import java.util.ArrayList;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.industrywirless.IndWirCopShow;
import com.chen.industrywirless.IndWirInputData;
import com.chen.util.GetSQL;
import com.ds.bluetooth.ClientActivity;
import com.ds.bluetooth.CopWatBluActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
/**
 * ��Ҫ���ܣ����ϸ�ҳ�洫��������С����¥�š���Ԫ
 * �����ҳ���в�ѯ������ط����û���ͬʱʵ��
 * ���еĲ�ѯ���������ط�������Ȳ���
 * @author chen
 *
 */
public class PeoWirCopShow extends Activity{
	DataBaseDemo dbm = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();

	private SQLiteDatabase mSqlDatabase = null;

	private ListView lv;
	private TextView textv;
	private TextView ts;
	private int menuStartId = Menu.FIRST;
	private SimpleCursorAdapter adapter = null;
	Intent intent = null;
	Button back;
	Cursor cursorAll = null;
	Bundle b = null;
	public static final int SEARCH = Menu.FIRST,
			OPENVALUE = Menu.FIRST + 1,
			CLOSEVALUE = Menu.FIRST + 2,
			DATAINPUT = Menu.FIRST + 3,
			COPYWATCH = Menu.FIRST + 4,
			SINGLECOPY = Menu.FIRST + 5;
	public static final int FIRST = Menu.FIRST;
	public static final int SECOND = Menu.FIRST + 1; 
	String 	area_id = null,
			biotope_id = null,
			build_id = null,
			unit_id = null,
			address = null,
			lever = null,
			cus_id = null,
			wat_id = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peo_wireless_cop);
		creatView();
	}

	/*
	 * ������ͼ
	 */
	private void creatView() {
		ts = (TextView)findViewById(R.id.peo_wir_tv);
		textv = (TextView)findViewById(R.id.peo_wir_sview);
		lv = (ListView)findViewById(R.id.peo_list_view);
		
		//CopyPeo�л�ȡ��������ֵ
		intent = getIntent();
		b = intent.getBundleExtra("db");
		address = b.getString("address");
		area_id = b.getString("area_id");
		biotope_id = b.getString("biotope_id");
		build_id = b.getString("build_id");
		unit_id = b.getString("unit_id");
		lever = b.getString("lever");
		ts.setText(address);
		//����ListView��ͷ��
		lv.addHeaderView(LayoutInflater.from(this).inflate( R.layout.peo_table_title, null),null,false);
		showData();
		registerForContextMenu(lv);//ע��listView���������Ĳ˵�
		back = (Button)findViewById(R.id.peo_wir_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(PeoWirCopShow.this,CopyPeo.class);
				startActivity(intent);
			}
		});
	}

	//��ʾ���ݵ�ListView��
	@SuppressWarnings("deprecation")
	private void showData() {
		
		//System.out.println(area_id+"|"+biotope_id+"|"+build_id+"|"+unit_id+"|"+lever);
		//��ѯ��ҳ��ѡ��ĵ�ַ���û�
		String qCustomerBy =getSql.getqCustomerBy();
		String[] s = {area_id,biotope_id,build_id,unit_id,lever};
		//���в�ѯ
		cursorAll =  dbm.queryData(qCustomerBy, s);
		if(cursorAll != null){
			adapter = new SimpleCursorAdapter(
					this,
					R.layout.list, 
					cursorAll, 
					new String[]{"doorplatenum","name",
							"num","state","isreadwatch"}, 
							new int[]{
							R.id.peo_wir_tv1,
							R.id.peo_wir_tv2,
							R.id.peo_wir_tv3,
							R.id.peo_wir_tv4,
							R.id.peo_wir_tv5
					}
					);
			
			lv.setAdapter(adapter);
			textv.setText("��ǰ�û�����"+cursorAll.getCount());
		}else{
			textv.setText("��ǰ�û�����0");
		}
	}


	


	/*
	 * ����ǰ�Menu�������Ĳ˵�
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(Menu.NONE, FIRST, Menu.NONE, "ȫ���û�");
		menu.add(Menu.NONE, SECOND, Menu.NONE, "δ�����û�");
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		return ((optionMenu(item))||super.onOptionsItemSelected(item));
	}

	public void reshowData(String sql){
		String[] s = {area_id,biotope_id,build_id,unit_id,lever};
		cursorAll = dbm.queryData(sql, s);
		if(cursorAll != null){
			adapter = new SimpleCursorAdapter(
					this,
					R.layout.list, 
					cursorAll, 
					new String[]{"doorplatenum","name",
							"num","state","isreadwatch"}, 
							new int[]{
							R.id.peo_wir_tv1,
							R.id.peo_wir_tv2,
							R.id.peo_wir_tv3,
							R.id.peo_wir_tv4,
							R.id.peo_wir_tv5
					}
					);
			
			lv.setAdapter(adapter);
			textv.setText("��ǰ�û�����"+cursorAll.getCount());
		}else{
			textv.setText("��ǰ�û�����0");
		}
	}
	private boolean optionMenu(MenuItem item) {
		int po = item.getItemId();
		System.out.println("xuanzeweizi"+po);
		switch(po){
		case FIRST:
			String sql = getSql.getqCustomerBy();
			reshowData(sql);
			return true;
			
		case SECOND:
			String sql1 = getSql.getqCusUnRead();
			reshowData(sql1);
			return true;
		
		}
		return false;
	}

	/*
	 * ����������Ĳ˵�������ʽ
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		popularMenu(menu);
		
		
		//getContextMenuInfo()

	}
	//���������Ĳ˵�
	private void popularMenu(ContextMenu menu) {

		menu.add(Menu.NONE, SEARCH, Menu.NONE, "��ѯ");


		menu.add(Menu.NONE, OPENVALUE, Menu.NONE, "����");


		menu.add(Menu.NONE, CLOSEVALUE, Menu.NONE, "�ط�");


		menu.add(Menu.NONE, DATAINPUT, Menu.NONE, "����¼��");


		menu.add(Menu.NONE, COPYWATCH, Menu.NONE, "����");

	}


	/*
	 * ����������Ĳ˵�������Ĵ���ʽ
	 * (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	public boolean onContextItemSelected(MenuItem item){
		return (
				applyMenuChoice(item)||
				super.onContextItemSelected(item)
				);

	}


	private boolean applyMenuChoice(MenuItem item) {
		ContentValues cv = null;
		int menuId = item.getItemId();//��ȡѡ�������Ĳ˵��е���һ�����
		//ͨ���������д����ȡѡ����е�ID
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//cursorAll.moveToFirst();
		cursorAll.moveToPosition(info.position-1);//���α��Ƶ�ѡ���λ�����ڵ�����
		//��ȡ��ǰ�α������еĵڶ������ݣ���watch��ID��
		String w_id = cursorAll.getString(1);
		String c_name = cursorAll.getString(8);
		//list��������ȫ��Ĵ洢�û�ID������ID��С��ID����ԪID��¥ID
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			list.add(cursorAll.getString(i));
		}
		//System.out.println("��ѡ���λ�õı��ID�ǣ�"+ cursorAll.getString(1));
		switch(menuId){
		case SEARCH:
			//ȫ�飬������PeoWirelessCopSearch���������
			intent = new Intent(PeoWirCopShow.this, PeoWirelessCopSearch.class);
			b = new Bundle();
			b.putStringArrayList("list", list);
			intent.putExtra("db", b);
			startActivity(intent);
			return true;
		case OPENVALUE:
			//����״̬Ϊ����
			cv = new ContentValues();
			cv.put("state", "����");
			String[] OpenS = {w_id};
			dbm.updateData("watch", cv, "_id=?", OpenS);
			showData();
			return true;
		case CLOSEVALUE:
			//����״̬Ϊ�ط�
			cv = new ContentValues();
			cv.put("state", "�ط�");
			String[] CloseS = {w_id};
			dbm.updateData("watch", cv, "_id=?", CloseS);
			showData();
			return true;
		case DATAINPUT:
			intent = new Intent(PeoWirCopShow.this, IndWirInputData.class);
			b = new Bundle();
			b.putString("w_id", w_id);
			b.putString("c_name",c_name);
			intent.putExtra("db", b);
			startActivity(intent);
			return true;
		case COPYWATCH:
			Bundle b = new Bundle();
			b.putString("copywatch", "���ó���");
			intent = new Intent(PeoWirCopShow.this, CopWatBluActivity.class);
			intent.putExtra("copybundle", b);
			startActivity(intent);
			return true;

		}
		return false;
	}



}
