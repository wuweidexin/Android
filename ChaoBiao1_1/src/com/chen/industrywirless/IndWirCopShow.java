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
 * 主要功能：民用无线用户展示、详细查询、开阀、关阀、数据录入、抄表、是否已读表的用户查询
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

	//构造菜单需要的初始菜单项ID
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
	 * 给Activity初始化全局视图
	 */
	private void creatView() {


		back = (Button)findViewById(R.id.ind_wir_back);
		back = (Button)findViewById(R.id.ind_wir_back);
		lv = (ListView)findViewById(R.id.ind_list_view);
		ts = (TextView)findViewById(R.id.ind_wir_tv);
		textv = (TextView)findViewById(R.id.ind_wir_sview);
		//给ListView添加表头
		lv.addHeaderView(LayoutInflater.from(this).inflate( R.layout.ind_table_title1, null),null,false);
		registerForContextMenu(lv);//给ListView注册上下文菜单
		
		
		//获取从上个Activity中传过来的区域、小区的ID和名称
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("db");
		area_id = b.getString("areaIdSelect");
		biotope_id = b.getString("biotopeIdSelect");
		biotopeSelect = b.getString("biotopeSelect");
		areaSelect = b.getString("areaSelect");
		
		//把地址设置到界面的地址的TextView中
		ts.setText("地址是："+areaSelect+"  "+biotopeSelect );
		showData();//展示数据
		//返回按钮的事件处理
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(IndWirCopShow.this, IndWirCop.class);
				startActivity(intent);
			}
		});

	}

	//在视图中显示表格数据
	@SuppressWarnings("deprecation")
	private void showData() {
		


		//查询IndWirCop中选择的地址的用户
		String sql = getSql.getqIndAll();
		
		String[] s = {area_id,biotope_id};
		//调用方法进行数据查询
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
			lv.setAdapter(adapter);//绑定数据到ListView中
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
		//new MenuInflater(this).inflate(R.menu.ind_wir_menu, menu);
		menu.add(Menu.NONE, FIRST, Menu.NONE, "全部用户");
		menu.add(Menu.NONE, SECOND, Menu.NONE, "未抄表用户");
		return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item){
		return ((optionMenu(item))||super.onOptionsItemSelected(item));
	}
	public void reshowData(String sql){
		String[] s = {area_id,biotope_id};
		//调用方法进行数据查询
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
			lv.setAdapter(adapter);//绑定数据到ListView中
			textv.setText("当前用户数："+cursorAll.getCount());
		}else{
			textv.setText("当前用户数：0");
		}
	}
	private boolean optionMenu(MenuItem item) {
		
		int po = item.getItemId();
		System.out.println("xuanzeweizi********************************"+po);
		switch(po){
		case FIRST:
			System.out.println("xuanzeweizi******************全部用户**************");
			String sql = getSql.getqIndAll();
			reshowData(sql);
			return true;
			
		case SECOND:
			System.out.println("xuanzeweizi******************未抄表用户**************");
			String sql1 = getSql.getqIndUnRead();
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

	}
	/*
	 * 创建上下文菜单详细
	 */
	private void popularMenu(ContextMenu menu) {

		menu.add(Menu.NONE, FIRST, Menu.NONE, "查询");


		menu.add(Menu.NONE, SECOND, Menu.NONE, "开阀");


		menu.add(Menu.NONE, THIRD, Menu.NONE, "关阀");


		menu.add(Menu.NONE, FOURTH, Menu.NONE, "数据录入");


		menu.add(Menu.NONE, FIFTH, Menu.NONE, "抄表");

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

	/*
	 * 上下文菜单的实现类
	 */
	private boolean applyMenuChoice(MenuItem item) {
		ContentValues cv = null;
		//获取上下文菜单中选择的操作的ID
		int menuId = item.getItemId();
		Intent intent = null;
		//获取选择ListView中的哪一行，即位置
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		cursorAll.moveToPosition(info.position-1);//cursor从0开始，使用位置-1
		//System.out.println("上下文菜单中获取的Id是："+ info.position);
		//从cursor中获取industryuser的ID和watch的ID
		String i_id  = cursorAll.getString(0);
		String w_id = cursorAll.getString(3);
		String i_name = cursorAll.getString(4);
		//list用来进行全查的存储用户ID、区域ID、小区ID、单元ID、楼ID
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			list.add(cursorAll.getString(i));
		}
		//System.out.println("你选择的位置的用户是："+ cursorAll.getString(1));

		switch(menuId){
		case FIRST:
			//往IndWirCopSearch中传入数据，进行相关全部信息查询
			intent = new Intent(IndWirCopShow.this, IndWirCopSearch.class);
			b = new Bundle();
			b.putStringArrayList("list", list);
			intent.putExtra("db", b);
			startActivity(intent);
			return true;
		case SECOND:
			//开阀操作
			cv = new ContentValues();
			cv.put("state", "开阀");
			String[] OpenS = {w_id};
			dbm.updateData("watch", cv, "_id=?", OpenS);
			showData();
			return true;
		case THIRD:
			//关阀操作
			cv = new ContentValues();
			cv.put("state", "关阀");
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
			System.out.println("你选择了第四项：");
			startActivity(intent);
			return true;
		case FIFTH:
			Bundle b = new Bundle();
			b.putString("copywatch", "工业抄表");
			intent = new Intent(IndWirCopShow.this, CopWatBluActivity.class);
			intent.putExtra("copybundle", b);
			startActivity(intent);
			return true;
		

		}
		return false;
	}

}
