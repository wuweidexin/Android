package com.chen.industrywirless;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllMenuItem;
import com.chen.peowirless.PeoWirCopShow;
import com.chen.peowirless.PeoWirelessCopSearch;
import com.chen.peowirless.PeoWirelessInputData;
import com.chen.util.GetSQL;
import com.ds.bluetooth.CopWatBluActivity;
/**
 * ��Ҫ���ܣ����������û�չʾ����ϸ��ѯ���������ط�������¼�롢�����Ƿ��Ѷ�����û���ѯ
 * @author chen
 *
 */
public class IndWirCopShow extends Activity{
	DataBaseDemo dbm = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();
	private ListView lv = null;
	private TextView textv = null,
			ts = null;
	private Button back;
	Cursor cursorAll = null;
	Bundle b = null;

	String 	area_id = null,
			biotope_id = null,
			biotopeSelect = null,
			areaSelect = null;

	//����˵���Ҫ�ĳ�ʼ�˵���ID
	private int menuStartId = Menu.FIRST;
	private SimpleCursorAdapter adapter = null;
	public static final int FIRST = Menu.FIRST;
	public static final int SECOND = Menu.FIRST + 1; 
	public static final int THIRD = Menu.FIRST + 2;
	public static final int FOURTH = Menu.FIRST + 3;
	public static final int FIFTH = Menu.FIRST + 4;
	public static final int SINGLECOPY = Menu.FIRST + 5;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ind_wir_copshow);
		creatView();

	}
	/*
	 * ��Activity��ʼ��ȫ����ͼ
	 */
	private void creatView() {


		back = (Button)findViewById(R.id.ind_wir_back);
		back = (Button)findViewById(R.id.ind_wir_back);
		lv = (ListView)findViewById(R.id.ind_list_view);
		ts = (TextView)findViewById(R.id.ind_wir_tv);
		textv = (TextView)findViewById(R.id.ind_wir_sview);
		//��ListView��ӱ�ͷ
		lv.addHeaderView(LayoutInflater.from(this).inflate( R.layout.ind_table_title1, null),null,false);
		registerForContextMenu(lv);//��ListViewע�������Ĳ˵�
		
		
		//��ȡ���ϸ�Activity�д�����������С����ID������
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("db");
		area_id = b.getString("areaIdSelect");
		biotope_id = b.getString("biotopeIdSelect");
		biotopeSelect = b.getString("biotopeSelect");
		areaSelect = b.getString("areaSelect");
		
		//�ѵ�ַ���õ�����ĵ�ַ��TextView��
		ts.setText("��ַ�ǣ�"+areaSelect+"  "+biotopeSelect );
		showData();//չʾ����
		//���ذ�ť���¼�����
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(IndWirCopShow.this, IndWirCop.class);
				startActivity(intent);
			}
		});

	}

	//����ͼ����ʾ�������
	@SuppressWarnings("deprecation")
	private void showData() {
		


		//��ѯIndWirCop��ѡ��ĵ�ַ���û�
		String sql = getSql.getqIndAll();
		
		String[] s = {area_id,biotope_id};
		//���÷����������ݲ�ѯ
		cursorAll =  dbm.queryData(sql, s);
		if(cursorAll != null){
			adapter = new SimpleCursorAdapter(
					this,
					R.layout.indwirlist, 
					cursorAll, 
					new String[]{"name","num",
					"state","isreadwatch"}, 
					new int[]{
							R.id.ind_wirlist_tv1,
							R.id.ind_wirlist_tv2,
							R.id.ind_wirlist_tv3,
							R.id.ind_wirlist_tv4
					});
			lv.setAdapter(adapter);//�����ݵ�ListView��
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
		//new MenuInflater(this).inflate(R.menu.ind_wir_menu, menu);
		menu.add(Menu.NONE, FIRST, Menu.NONE, "ȫ���û�");
		menu.add(Menu.NONE, SECOND, Menu.NONE, "δ�����û�");
		return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item){
		return ((optionMenu(item))||super.onOptionsItemSelected(item));
	}
	public void reshowData(String sql){
		String[] s = {area_id,biotope_id};
		//���÷����������ݲ�ѯ
		cursorAll =  dbm.queryData(sql, s);
		if(cursorAll != null){
			adapter = new SimpleCursorAdapter(
					this,
					R.layout.indwirlist, 
					cursorAll, 
					new String[]{"name","num",
					"state","isreadwatch"}, 
					new int[]{
							R.id.ind_wirlist_tv1,
							R.id.ind_wirlist_tv2,
							R.id.ind_wirlist_tv3,
							R.id.ind_wirlist_tv4
					});
			lv.setAdapter(adapter);//�����ݵ�ListView��
			textv.setText("��ǰ�û�����"+cursorAll.getCount());
		}else{
			textv.setText("��ǰ�û�����0");
		}
	}
	private boolean optionMenu(MenuItem item) {
		
		int po = item.getItemId();
		System.out.println("xuanzeweizi********************************"+po);
		switch(po){
		case FIRST:
			System.out.println("xuanzeweizi******************ȫ���û�**************");
			String sql = getSql.getqIndAll();
			reshowData(sql);
			return true;
			
		case SECOND:
			System.out.println("xuanzeweizi******************δ�����û�**************");
			String sql1 = getSql.getqIndUnRead();
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

	}
	/*
	 * ���������Ĳ˵���ϸ
	 */
	private void popularMenu(ContextMenu menu) {

		menu.add(Menu.NONE, FIRST, Menu.NONE, "��ѯ");


		menu.add(Menu.NONE, SECOND, Menu.NONE, "����");


		menu.add(Menu.NONE, THIRD, Menu.NONE, "�ط�");


		menu.add(Menu.NONE, FOURTH, Menu.NONE, "����¼��");


		menu.add(Menu.NONE, FIFTH, Menu.NONE, "����");

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

	/*
	 * �����Ĳ˵���ʵ����
	 */
	private boolean applyMenuChoice(MenuItem item) {
		ContentValues cv = null;
		//��ȡ�����Ĳ˵���ѡ��Ĳ�����ID
		int menuId = item.getItemId();
		Intent intent = null;
		//��ȡѡ��ListView�е���һ�У���λ��
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		cursorAll.moveToPosition(info.position-1);//cursor��0��ʼ��ʹ��λ��-1
		//System.out.println("�����Ĳ˵��л�ȡ��Id�ǣ�"+ info.position);
		//��cursor�л�ȡindustryuser��ID��watch��ID
		String i_id  = cursorAll.getString(0);
		String w_id = cursorAll.getString(3);
		String i_name = cursorAll.getString(4);
		//list��������ȫ��Ĵ洢�û�ID������ID��С��ID����ԪID��¥ID
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			list.add(cursorAll.getString(i));
		}
		//System.out.println("��ѡ���λ�õ��û��ǣ�"+ cursorAll.getString(1));

		switch(menuId){
		case FIRST:
			//��IndWirCopSearch�д������ݣ��������ȫ����Ϣ��ѯ
			intent = new Intent(IndWirCopShow.this, IndWirCopSearch.class);
			b = new Bundle();
			b.putStringArrayList("list", list);
			intent.putExtra("db", b);
			startActivity(intent);
			return true;
		case SECOND:
			//��������
			cv = new ContentValues();
			cv.put("state", "����");
			String[] OpenS = {w_id};
			dbm.updateData("watch", cv, "_id=?", OpenS);
			showData();
			return true;
		case THIRD:
			//�ط�����
			cv = new ContentValues();
			cv.put("state", "�ط�");
			String[] closeS = {w_id};
			dbm.updateData("watch", cv, "_id=?", closeS);
			showData();
			return true;
		case FOURTH:
			intent = new Intent(IndWirCopShow.this, IndWirInputData.class);
			b = new Bundle();
			b.putString("w_id", w_id);
			b.putString("i_name",i_name);
			intent.putExtra("db", b);
			System.out.println("��ѡ���˵����");
			startActivity(intent);
			return true;
		case FIFTH:
			Bundle b = new Bundle();
			b.putString("copywatch", "��ҵ����");
			intent = new Intent(IndWirCopShow.this, CopWatBluActivity.class);
			intent.putExtra("copybundle", b);
			startActivity(intent);
			return true;
		

		}
		return false;
	}

}
