package com.chen.recordbuilding;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllMenuItem;
import com.chen.util.GetSQL;
import com.chen.util.TestString;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
/**
 * 主要功能：工业用户新增、删除等
 * @author chen
 *
 */
public class IndustryRecordbuilding extends Activity{
	GetSQL getSql = new GetSQL();
	TestString t = new TestString();

	Intent intent = null;
	String areaId = null;
	String biotopeId = null;
	String watchNextNumS = null;
	String indNameS = null;

	Cursor cursor = null;
	DataBaseDemo dbd = new DataBaseDemo(this);
	TextView areaShow = null;
	TextView biotopeShow = null;
	ListView listView = null;
	EditText watchNum = null;
	EditText industryName = null;
	Button addB = null,back = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ind_recordbuilding);
		createView();
	}
	/*
	 * 抽象类初始化字符串
	 */
	private interface IndRecBui{
		String IndustryName = "户主";
		String WatchNum = "表名";

	}

	/*
	 * 创建界面视图
	 */
	private void createView() {
		intent = getIntent();
		Bundle b = intent.getBundleExtra("db");
		areaId = b.getString("areaIdSelect");
		String areaName = b.getString("areaSelect");
		biotopeId = b.getString("biotopeIdSelect");
		String biotopeName = b.getString("biotopeSelect");
		System.out.println("小区名："+biotopeName+"区域名："+areaName);

		areaShow = (TextView)findViewById(R.id.ind_recbui_bAreaShow);
		areaShow.setText(areaName);
		biotopeShow = (TextView)findViewById(R.id.ind_recbui_bBiotopeShow);
		biotopeShow.setText(biotopeName);
		watchNum=(EditText)findViewById(R.id.ind_recbui_bWatchNumEdit);
		industryName=(EditText)findViewById(R.id.ind_recbui_bIndustryEdit);
		//获取燃气表中最大的Id，即新数据中的Id，也为Watch中的num
		String maxWatchNumSql = "select max(num) from watch";
		cursor = dbd.queryData(maxWatchNumSql, null);
		cursor.moveToFirst();
		int maxWatNum = cursor.getInt(0);
		watchNextNumS = String.valueOf(maxWatNum+1);
		watchNum.setText(watchNextNumS);

		//ListView显示数据
		showList(areaId,biotopeId);
		addInd();

		back = (Button)findViewById(R.id.ind_recbui_bExit);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(IndustryRecordbuilding.this, AllMenuItem.class);
				startActivity(intent);
			}
		});
	}
	/*
	 * 新增工业用户
	 */
	private void addInd() {
		addB = (Button)findViewById(R.id.ind_recbui_bAdd);

		addB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				indNameS = industryName.getText().toString().trim();
				System.out.println(indNameS);
				//验证输入是否为空
				if(!indNameS.equals("")&& t.match(indNameS)){
					try{
						//新建用户表前，首先新建燃气使用量表
						String insertAirCom = getSql.getInsertAircomsumption();
						String[] insAirComS = {"0","0","0","0","0","0","0","0","0","0","0","0","1"};
						dbd.insertData(insertAirCom, insAirComS);

						//获取燃气使用量表中最大的Id，即最新建的表
						cursor = dbd.queryData("select max(_id) from aircomsumption", null);
						cursor.moveToFirst();
						String airComId = String.valueOf(cursor.getInt(0));

						//新建燃气表数据
						String insertWatch = getSql.getInsertWatch();
						//获取系统时间
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String dateS = format.format(date);
						String[] watchS = {watchNextNumS,airComId,"0","0","0",dateS,"无","0","1"};
						dbd.insertData(insertWatch, watchS);

						//新建用户档案
						String insertCusSql = getSql.getInsertIndustryuser();

						String[] cusSelection = {indNameS,watchNextNumS,"0",areaId,biotopeId,"1"};
						dbd.insertData(insertCusSql, cusSelection);
						showMessDialog(IndustryRecordbuilding.this,"建档成功");
					}catch (Exception e) {
						
					}
					
				}else{
					showMessDialog(IndustryRecordbuilding.this,"信息为空或输入为非汉字");
				}
			}
		});

	}
	/*
	 *弹出的Dialog，显示是否删除 
	 */
	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("提示");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.setNegativeButton("返回", null);
		builder.create().show();

	}

	/*
	 * ListView中显示数据
	 */
	@SuppressWarnings("deprecation")
	public void showList(String areaId,String biotopeId){

		listView = (ListView)findViewById(R.id.ind_recbui_bListView);
		String listSql =getSql.getListSql();
		String[] selection = {areaId,biotopeId};
		cursor = dbd.queryData(listSql, selection);
		if(cursor != null){
			cursor.moveToFirst();
			SimpleCursorAdapter sca = new SimpleCursorAdapter(
					this,
					R.layout.ind_record_list,
					cursor,
					new String[]{
							IndRecBui.IndustryName,
							IndRecBui.WatchNum,

					},
					new int[]{
							R.id.ind_rec_list_tv1,
							R.id.ind_rec_list_tv2
					}
					);
			TextView t = new TextView(this);
			t.setText("用户名");

			TextView t1 = new TextView(this);
			t1.setText("表号");
			System.out.println("头部有："+listView.getHeaderViewsCount());
			//View v = new View();

			listView.addHeaderView(LayoutInflater.from(this).inflate( R.layout.ind_table_title, null),null,false);
			listView.setAdapter(sca);
		}

		//监听长点击数据项，弹出是否删除当前数据
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			int location = -1;
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				location = arg2-1;
				deleteDialog(IndustryRecordbuilding.this,"删除数据");
				return false;
			}
			/*
			 * 删除Dialog
			 */
			private void deleteDialog(
					Context ctx, String mes) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setTitle("警告");
				builder.setMessage(mes);
				builder.setNegativeButton("返回", null);//返回不进行任何操作
				//确定删除数据将进行数据删除
				builder.setPositiveButton("确定", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						ContentValues cv = null;
						try {
							cv = new ContentValues();
							cv.put("live", "0");
							cursor.moveToFirst();
							cursor.moveToPosition(location);

							//获取燃气使用量表的_id，并update删除标志位
							String[] air_id = {String.valueOf(cursor.getInt(2))};

							int i = dbd.updateData("aircomsumption", cv, "_id=?", air_id);

							//获取燃气表的_id，并update删除标志位

							String[] wat_id = {String.valueOf(cursor.getInt(1))};	
							dbd.updateData("watch", cv, "_id=?", wat_id);

							//获取工业用户表的_id，并update其删除标志位

							String[] ind_id = {String.valueOf(cursor.getInt(0))};		
							dbd.updateData("industryuser", cv, "_id=?", ind_id);

							showMessDialog(IndustryRecordbuilding.this,"删除数据成功");
						} catch (Exception e) {
							showMessDialog(IndustryRecordbuilding.this,"删除数据出错");
							e.printStackTrace();
							return;
						}

					}
				});
				builder.create().show();

			}
		});

	}
}
