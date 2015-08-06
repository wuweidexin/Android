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
 * ��Ҫ���ܣ������û�ȫ�飨PeoWirCopShow�����Ĳ˵��еĲ�ѯ��
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
	 * ������ͼ
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

		
		//���ҵ�ǰ���е�ȼ��ʹ�������id
		String findAirUse = getSql.getFindAirUse();
		String[] selectionArg = {wat_id};
		cursorAirUse = dbd.queryData(findAirUse, selectionArg);
		ArrayList<String> liAirUse = null;
		if(cursorAirUse != null){
			cursorAirUse.moveToFirst();
			liAirUse = new ArrayList<String>();
		}
		
		//��ѯѡ���ȫ����Ϣ
		String[] all = {area_id,biotope_id,build_id,unit_id,lever,wat_id};
		String qAllCusMess =getSql.getqAllCusMess();
		ArrayList<String> liAll = null;
		cursorAll = dbd.queryData(qAllCusMess, all);
		if(cursorAll != null){
			liAll = new ArrayList<String>();
			cursorAll.moveToFirst();
		}
		//��������Ϣ��ӵ�list�У�����չʾ
		List<String> listShow = new ArrayList<String>();
		listShow.add("�û���ַ��"+cursorAll.getString(0)+" ¥   "+cursorAll.getString(1)
				+" ��Ԫ    "+cursorAll.getString(2) +"	��   "+cursorAll.getString(3) + "  ��");
		listShow.add("�û�����"+cursorAll.getString(4));
		listShow.add("�Ǳ�ţ�"+cursorAll.getString(5));
		listShow.add("�ۼ���������"+cursorAll.getString(6));
		listShow.add("ʣ���δ֪");
		listShow.add("ʱ�䣺"+cursorAll.getString(7));
		listShow.add("״̬��"+cursorAll.getString(8));
		listShow.add("���ۣ�10");
		listShow.add("����������"+cursorAll.getString(9));
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
