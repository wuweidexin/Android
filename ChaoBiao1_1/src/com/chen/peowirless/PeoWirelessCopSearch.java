package com.chen.peowirless;

import java.util.ArrayList;
import java.util.List;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.util.GetSQL;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
/**
 * 主要功能：民用用户全查（PeoWirCopShow上下文菜单中的查询）
 * @author chen
 *
 */
public class PeoWirelessCopSearch extends ListActivity{

	DataBaseDemo dbd = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();
	String 	area_id = null,
			biotope_id = null,
			build_id = null,
			unit_id = null,
			address = null,
			lever = null,
			cus_id = null,
			wat_id = null;
	Cursor cursorAll = null,
			cursorAirUse = null;	
	
	Bundle b = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.peo_wir_search);
		creatView();
	}
	/*
	 * 创建视图
	 */
	private void creatView() {
		Intent intent = getIntent();
		b = intent.getBundleExtra("db");
		ArrayList<String> list = new ArrayList<String>();
		list = b.getStringArrayList("list");
		cus_id = list.get(0);
		wat_id = list.get(1);
		area_id = list.get(2);
		biotope_id = list.get(3);
		build_id = list.get(4);
		unit_id = list.get(5);
		lever = list.get(6);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("****"+list.get(i));
		}

		
		//查找当前表中的燃气使用量表的id
		String findAirUse = getSql.getFindAirUse();
		String[] selectionArg = {wat_id};
		cursorAirUse = dbd.queryData(findAirUse, selectionArg);
		ArrayList<String> liAirUse = null;
		if(cursorAirUse != null){
			cursorAirUse.moveToFirst();
			liAirUse = new ArrayList<String>();
		}
		
		//查询选择的全部信息
		String[] all = {area_id,biotope_id,build_id,unit_id,lever,wat_id};
		String qAllCusMess =getSql.getqAllCusMess();
		ArrayList<String> liAll = null;
		cursorAll = dbd.queryData(qAllCusMess, all);
		if(cursorAll != null){
			liAll = new ArrayList<String>();
			cursorAll.moveToFirst();
		}
		//把所有信息添加到list中，进行展示
		List<String> listShow = new ArrayList<String>();
		listShow.add("用户地址："+cursorAll.getString(0)+" 楼   "+cursorAll.getString(1)
				+" 单元    "+cursorAll.getString(2) +"	层   "+cursorAll.getString(3) + "  室");
		listShow.add("用户名："+cursorAll.getString(4));
		listShow.add("仪表号："+cursorAll.getString(5));
		listShow.add("累计用气量："+cursorAll.getString(6));
		listShow.add("剩余金额：未知");
		listShow.add("时间："+cursorAll.getString(7));
		listShow.add("状态："+cursorAll.getString(8));
		listShow.add("单价：10");
		listShow.add("购气次数："+cursorAll.getString(9));
		listShow.add("1月份累计用气量："+cursorAirUse.getString(0));
		listShow.add("2月份累计用气量："+cursorAirUse.getString(1));
		listShow.add("3月份累计用气量："+cursorAirUse.getString(2));
		listShow.add("4月份累计用气量："+cursorAirUse.getString(3));
		listShow.add("5月份累计用气量："+cursorAirUse.getString(4));
		listShow.add("6月份累计用气量："+cursorAirUse.getString(5));
		listShow.add("7月份累计用气量："+cursorAirUse.getString(6));
		listShow.add("8月份累计用气量："+cursorAirUse.getString(7));
		listShow.add("9月份累计用气量："+cursorAirUse.getString(8));
		listShow.add("10月份累计用气量："+cursorAirUse.getString(9));
		listShow.add("11月份累计用气量："+cursorAirUse.getString(10));
		listShow.add("12月份累计用气量："+cursorAirUse.getString(11));
		setListAdapter(new ArrayAdapter<String>(
				this,android.R.layout.simple_expandable_list_item_1, listShow));
		
	}
	
}
