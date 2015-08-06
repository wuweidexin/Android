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
 * ��Ҫ���ܣ���ҵ�û�����ȫ��
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
	 * ��ʼ����ͼ
	 */
	private void creatView() {
		//��PeoWirCopShow�л�ȡIntent������������
		Intent intent = getIntent();
		b = intent.getBundleExtra("db");
		ArrayList<String> list = new ArrayList<String>();
		list = b.getStringArrayList("list");
		ind_id = list.get(0);
		area_id = list.get(1);
		biotope_id = list.get(2);
		wat_id = list.get(3);
		
		//����Ȼ��ʹ�ñ����ȫ��
		String findAirUse = "SELECT air.* FROM watch w,aircomsumption air WHERE air._id=w.aircomid AND w._id = ?";
		String[] selectionArg = {wat_id};
		cursorAirUse = dbd.queryData(findAirUse, selectionArg);
		ArrayList<String> liAirUse = null;
		if(cursorAirUse != null){
			cursorAirUse.moveToFirst();
			//���鵽������ȫ���ӵ�list���Ա�ܺõ���ʾ
			liAirUse = new ArrayList<String>();
			for (int j = 1; j < 13; j++) {
				cursorAirUse.getString(j);
//System.out.println("*"+cursorAirUse.getString(j));
			}
		}
		
		//��ѯwatch��industryuser�е������Ϣ
		String[] all = {ind_id,area_id,biotope_id};
		String sql ="SELECT DISTINCT a.name,b.name,i.name,w.num,w.totaluse,w.time,w.state,w.frequency  " +
				"FROM industryuser i, watch w , area a, biotope b " +
				"WHERE i.areanum = a._id AND i.biotopenum = b._id AND i.watId = w._id   " +
				"AND b.area = a._id  AND i._id = ? AND a._id = ? AND b._id = ?;";
		ArrayList<String> liAll = null;
		cursorAll = dbd.queryData(sql, all);
		//��ز�ѯ���Ҳȫ������list��
		if(cursorAll != null){
			liAll = new ArrayList<String>();
			cursorAll.moveToFirst();
			for (int m = 0; m < cursorAll.getColumnCount(); m++) {
				System.out.println("ִ�е�����"+cursorAll.getString(m));
			}
		}
		//��������ʾ����
		List<String> listShow = new ArrayList<String>();
		listShow.add("�û���ַ��"+cursorAll.getString(0)+" ����   "+cursorAll.getString(1)
				+" С��    ");
		listShow.add("�û�����"+cursorAll.getString(2));
		listShow.add("�Ǳ�ţ�"+cursorAll.getString(3));
		listShow.add("�ۼ���������"+cursorAll.getString(4));
		listShow.add("ʣ���δ֪");
		listShow.add("ʱ�䣺"+cursorAll.getString(5));
		listShow.add("״̬��"+cursorAll.getString(6));
		listShow.add("���ۣ�10");
		listShow.add("����������"+cursorAll.getString(7));
		listShow.add("1�·��ۼ���������"+cursorAirUse.getString(0));
		listShow.add("2�·��ۼ���������"+cursorAirUse.getString(1));
		listShow.add("3�·��ۼ���������"+cursorAirUse.getString(2));
		listShow.add("4�·��ۼ���������"+cursorAirUse.getString(3));
		listShow.add("5�·��ۼ���������"+cursorAirUse.getString(4));
		listShow.add("6�·��ۼ���������"+cursorAirUse.getString(5));
		listShow.add("7�·��ۼ���������"+cursorAirUse.getString(6));
		listShow.add("8�·��ۼ���������"+cursorAirUse.getString(7));
		listShow.add("9�·��ۼ���������"+cursorAirUse.getString(8));
		listShow.add("10�·��ۼ���������"+cursorAirUse.getString(9));
		listShow.add("11�·��ۼ���������"+cursorAirUse.getString(10));
		listShow.add("12�·��ۼ���������"+cursorAirUse.getString(11));
		setListAdapter(new ArrayAdapter<String>(
				this,android.R.layout.simple_expandable_list_item_1, listShow));
		
	}

}
