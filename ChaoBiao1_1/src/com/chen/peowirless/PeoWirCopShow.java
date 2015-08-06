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
 * 主要功能：从上个页面传过来区域、小区、楼号、单元
 * 在这个页面中查询出这个地方的用户，同时实现
 * 其中的查询、开阀、关阀、抄表等操作
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
	 * 创建视图
	 */
	private void creatView() {
		ts = (TextView)findViewById(R.id.peo_wir_tv);
		textv = (TextView)findViewById(R.id.peo_wir_sview);
		lv = (ListView)findViewById(R.id.peo_list_view);
		
		//CopyPeo中获取传过来的值
		intent = getIntent();
		b = intent.getBundleExtra("db");
		address = b.getString("address");
		area_id = b.getString("area_id");
		biotope_id = b.getString("biotope_id");
		build_id = b.getString("build_id");
		unit_id = b.getString("unit_id");
		lever = b.getString("lever");
		ts.setText(address);
		//设置ListView的头部
		lv.addHeaderView(LayoutInflater.from(this).inflate( R.layout.peo_table_title, null),null,false);
		showData();
		registerForContextMenu(lv);//注册listView进行上下文菜单
		back = (Button)findViewById(R.id.peo_wir_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(PeoWirCopShow.this,CopyPeo.class);
				startActivity(intent);
			}
		});
	}

	//显示数据到ListView中
	@SuppressWarnings("deprecation")
	private void showData() {
		
		//System.out.println(area_id+"|"+biotope_id+"|"+build_id+"|"+unit_id+"|"+lever);
		//查询上页中选择的地址的用户
		String qCustomerBy =getSql.getqCustomerBy();
		String[] s = {area_id,biotope_id,build_id,unit_id,lever};
		//进行查询
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
			textv.setText("当前用户数："+cursorAll.getCount());
		}else{
			textv.setText("当前用户数：0");
		}
	}


	


	/*
	 * 这个是按Menu键出来的菜单
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(Menu.NONE, FIRST, Menu.NONE, "全部用户");
		menu.add(Menu.NONE, SECOND, Menu.NONE, "未抄表用户");
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
			textv.setText("当前用户数："+cursorAll.getCount());
		}else{
			textv.setText("当前用户数：0");
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
	 * 这个是上下文菜单创建方式
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		popularMenu(menu);
		
		
		//getContextMenuInfo()

	}
	//创建上下文菜单
	private void popularMenu(ContextMenu menu) {

		menu.add(Menu.NONE, SEARCH, Menu.NONE, "查询");


		menu.add(Menu.NONE, OPENVALUE, Menu.NONE, "开阀");


		menu.add(Menu.NONE, CLOSEVALUE, Menu.NONE, "关阀");


		menu.add(Menu.NONE, DATAINPUT, Menu.NONE, "数据录入");


		menu.add(Menu.NONE, COPYWATCH, Menu.NONE, "抄表");

	}


	/*
	 * 这个是上下文菜单单机项的处理方式
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
		int menuId = item.getItemId();//获取选择上下文菜单中的那一项操作
		//通过下面两行代码获取选择的行的ID
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//cursorAll.moveToFirst();
		cursorAll.moveToPosition(info.position-1);//将游标移到选择的位置所在的行上
		//获取当前游标所在行的第二个数据，即watch的ID号
		String w_id = cursorAll.getString(1);
		String c_name = cursorAll.getString(8);
		//list用来进行全查的存储用户ID、区域ID、小区ID、单元ID、楼ID
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			list.add(cursorAll.getString(i));
		}
		//System.out.println("你选择的位置的表的ID是："+ cursorAll.getString(1));
		switch(menuId){
		case SEARCH:
			//全查，并且往PeoWirelessCopSearch中添加数据
			intent = new Intent(PeoWirCopShow.this, PeoWirelessCopSearch.class);
			b = new Bundle();
			b.putStringArrayList("list", list);
			intent.putExtra("db", b);
			startActivity(intent);
			return true;
		case OPENVALUE:
			//更新状态为开阀
			cv = new ContentValues();
			cv.put("state", "开阀");
			String[] OpenS = {w_id};
			dbm.updateData("watch", cv, "_id=?", OpenS);
			showData();
			return true;
		case CLOSEVALUE:
			//更新状态为关阀
			cv = new ContentValues();
			cv.put("state", "关阀");
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
			b.putString("copywatch", "民用抄表");
			intent = new Intent(PeoWirCopShow.this, CopWatBluActivity.class);
			intent.putExtra("copybundle", b);
			startActivity(intent);
			return true;

		}
		return false;
	}



}
