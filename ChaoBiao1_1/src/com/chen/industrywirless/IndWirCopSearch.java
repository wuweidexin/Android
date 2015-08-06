package com.chen.industrywirless;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
/**
 * 主要功能：工业用户数据全查
 * @author chen
 *
 */
public class IndWirCopSearch extends ListActivity{
	DataBaseDemo dbd = new DataBaseDemo(this);
	String 	area_id = null,
			biotope_id = null,
			ind_id = null,
			wat_id = null;
	Cursor cursorAll = null,
			cursorAirUse = null;
	Bundle b = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		creatView();
	}
	/*
	 * 初始化视图
	 */
	private void creatView() {
		//从PeoWirCopShow中获取Intent传过来的数据
		Intent intent = getIntent();
		b = intent.getBundleExtra("db");
		ArrayList<String> list = new ArrayList<String>();
		list = b.getStringArrayList("list");
		ind_id = list.get(0);
		area_id = list.get(1);
		biotope_id = list.get(2);
		wat_id = list.get(3);
		
		//对天然气使用表进行全查
		String findAirUse = "SELECT air.* FROM watch w,aircomsumption air WHERE air._id=w.aircomid AND w._id = ?";
		String[] selectionArg = {wat_id};
		cursorAirUse = dbd.queryData(findAirUse, selectionArg);
		ArrayList<String> liAirUse = null;
		if(cursorAirUse != null){
			cursorAirUse.moveToFirst();
			//将查到的数据全部加到list中以便很好的显示
			liAirUse = new ArrayList<String>();
			for (int j = 1; j < 13; j++) {
				cursorAirUse.getString(j);
//System.out.println("*"+cursorAirUse.getString(j));
			}
		}
		
		//查询watch、industryuser中的相关信息
		String[] all = {ind_id,area_id,biotope_id};
		String sql ="SELECT DISTINCT a.name,b.name,i.name,w.num,w.totaluse,w.time,w.state,w.frequency  " +
				"FROM industryuser i, watch w , area a, biotope b " +
				"WHERE i.areanum = a._id AND i.biotopenum = b._id AND i.watId = w._id   " +
				"AND b.area = a._id  AND i._id = ? AND a._id = ? AND b._id = ?;";
		ArrayList<String> liAll = null;
		cursorAll = dbd.queryData(sql, all);
		//相关查询结果也全部加入list中
		if(cursorAll != null){
			liAll = new ArrayList<String>();
			cursorAll.moveToFirst();
			for (int m = 0; m < cursorAll.getColumnCount(); m++) {
				System.out.println("执行到这里"+cursorAll.getString(m));
			}
		}
		//下面是显示数据
		List<String> listShow = new ArrayList<String>();
		listShow.add("用户地址："+cursorAll.getString(0)+" 区域   "+cursorAll.getString(1)
				+" 小区    ");
		listShow.add("用户名："+cursorAll.getString(2));
		listShow.add("仪表号："+cursorAll.getString(3));
		listShow.add("累计用气量："+cursorAll.getString(4));
		listShow.add("剩余金额：未知");
		listShow.add("时间："+cursorAll.getString(5));
		listShow.add("状态："+cursorAll.getString(6));
		listShow.add("单价：10");
		listShow.add("购气次数："+cursorAll.getString(7));
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
