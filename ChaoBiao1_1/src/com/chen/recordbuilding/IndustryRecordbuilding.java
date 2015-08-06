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
 * ��Ҫ���ܣ���ҵ�û�������ɾ����
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
	 * �������ʼ���ַ���
	 */
	private interface IndRecBui{
		String IndustryName = "����";
		String WatchNum = "����";

	}

	/*
	 * ����������ͼ
	 */
	private void createView() {
		intent = getIntent();
		Bundle b = intent.getBundleExtra("db");
		areaId = b.getString("areaIdSelect");
		String areaName = b.getString("areaSelect");
		biotopeId = b.getString("biotopeIdSelect");
		String biotopeName = b.getString("biotopeSelect");
		System.out.println("С������"+biotopeName+"��������"+areaName);

		areaShow = (TextView)findViewById(R.id.ind_recbui_bAreaShow);
		areaShow.setText(areaName);
		biotopeShow = (TextView)findViewById(R.id.ind_recbui_bBiotopeShow);
		biotopeShow.setText(biotopeName);
		watchNum=(EditText)findViewById(R.id.ind_recbui_bWatchNumEdit);
		industryName=(EditText)findViewById(R.id.ind_recbui_bIndustryEdit);
		//��ȡȼ����������Id�����������е�Id��ҲΪWatch�е�num
		String maxWatchNumSql = "select max(num) from watch";
		cursor = dbd.queryData(maxWatchNumSql, null);
		cursor.moveToFirst();
		int maxWatNum = cursor.getInt(0);
		watchNextNumS = String.valueOf(maxWatNum+1);
		watchNum.setText(watchNextNumS);

		//ListView��ʾ����
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
	 * ������ҵ�û�
	 */
	private void addInd() {
		addB = (Button)findViewById(R.id.ind_recbui_bAdd);

		addB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				indNameS = industryName.getText().toString().trim();
				System.out.println(indNameS);
				//��֤�����Ƿ�Ϊ��
				if(!indNameS.equals("")&& t.match(indNameS)){
					try{
						//�½��û���ǰ�������½�ȼ��ʹ������
						String insertAirCom = getSql.getInsertAircomsumption();
						String[] insAirComS = {"0","0","0","0","0","0","0","0","0","0","0","0","1"};
						dbd.insertData(insertAirCom, insAirComS);

						//��ȡȼ��ʹ������������Id�������½��ı�
						cursor = dbd.queryData("select max(_id) from aircomsumption", null);
						cursor.moveToFirst();
						String airComId = String.valueOf(cursor.getInt(0));

						//�½�ȼ��������
						String insertWatch = getSql.getInsertWatch();
						//��ȡϵͳʱ��
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String dateS = format.format(date);
						String[] watchS = {watchNextNumS,airComId,"0","0","0",dateS,"��","0","1"};
						dbd.insertData(insertWatch, watchS);

						//�½��û�����
						String insertCusSql = getSql.getInsertIndustryuser();

						String[] cusSelection = {indNameS,watchNextNumS,"0",areaId,biotopeId,"1"};
						dbd.insertData(insertCusSql, cusSelection);
						showMessDialog(IndustryRecordbuilding.this,"�����ɹ�");
					}catch (Exception e) {
						
					}
					
				}else{
					showMessDialog(IndustryRecordbuilding.this,"��ϢΪ�ջ�����Ϊ�Ǻ���");
				}
			}
		});

	}
	/*
	 *������Dialog����ʾ�Ƿ�ɾ�� 
	 */
	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("��ʾ");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.setNegativeButton("����", null);
		builder.create().show();

	}

	/*
	 * ListView����ʾ����
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
			t.setText("�û���");

			TextView t1 = new TextView(this);
			t1.setText("���");
			System.out.println("ͷ���У�"+listView.getHeaderViewsCount());
			//View v = new View();

			listView.addHeaderView(LayoutInflater.from(this).inflate( R.layout.ind_table_title, null),null,false);
			listView.setAdapter(sca);
		}

		//�������������������Ƿ�ɾ����ǰ����
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			int location = -1;
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				location = arg2-1;
				deleteDialog(IndustryRecordbuilding.this,"ɾ������");
				return false;
			}
			/*
			 * ɾ��Dialog
			 */
			private void deleteDialog(
					Context ctx, String mes) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setTitle("����");
				builder.setMessage(mes);
				builder.setNegativeButton("����", null);//���ز������κβ���
				//ȷ��ɾ�����ݽ���������ɾ��
				builder.setPositiveButton("ȷ��", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						ContentValues cv = null;
						try {
							cv = new ContentValues();
							cv.put("live", "0");
							cursor.moveToFirst();
							cursor.moveToPosition(location);

							//��ȡȼ��ʹ�������_id����updateɾ����־λ
							String[] air_id = {String.valueOf(cursor.getInt(2))};

							int i = dbd.updateData("aircomsumption", cv, "_id=?", air_id);

							//��ȡȼ�����_id����updateɾ����־λ

							String[] wat_id = {String.valueOf(cursor.getInt(1))};	
							dbd.updateData("watch", cv, "_id=?", wat_id);

							//��ȡ��ҵ�û����_id����update��ɾ����־λ

							String[] ind_id = {String.valueOf(cursor.getInt(0))};		
							dbd.updateData("industryuser", cv, "_id=?", ind_id);

							showMessDialog(IndustryRecordbuilding.this,"ɾ�����ݳɹ�");
						} catch (Exception e) {
							showMessDialog(IndustryRecordbuilding.this,"ɾ�����ݳ���");
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
