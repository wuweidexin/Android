package com.chen.recordbuilding;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.industrywirless.IndWirCop;
import com.chen.mainActivity.AllMenuItem;
import com.chen.util.GetSQL;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
/**
 * ���ý���
 * @author chen
 *
 */
public class PeoRecordbuilding extends Activity{
	DataBaseDemo dbd = new DataBaseDemo(this);
	GetSQL getSql = new GetSQL();
	Intent intent = null;
	TextView areaShow = null,
			biotopeShow = null;
	ListView listView = null;
	EditText watchNum = null,
			customerName = null,
			buildingNum = null,
			unitNum = null,
			leverNum = null,
			doorplateNum = null;
	Button 	back = null;
	String 	watchNextNumS = null,
			cusNameS = null,
			buiNumS = null,
			uniNumS = null,
			levNumS = null,
			dooNumS = null,
			areaId = null,
			biotopeId = null,
			buildId = null,
			unitId = null,
			leverId = null;
	Cursor cursor = null,cursorSele = null;
	Button addB = null;
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peo_recordbuilding);
		creatView();

	}
	/*
	 * �������ʼ���ַ���
	 */
	private interface PeoRecBui{
		String DoorplateNum = "doorplatenum";
		String WatchNum = "wat_num";
		String BuildingNum = "¥��";
		String UnitNum = "��Ԫ";
		String LeverNum = "¥��";
		String CustomerName = "name";
		String State = "state";
	}

	/*
	 * ������ͼ
	 */

	private void creatView() {
		intent = getIntent();
		Bundle b = intent.getBundleExtra("db");
		areaId = b.getString("areaIdSelect");
		String areaName = b.getString("areaSelect");
		biotopeId = b.getString("biotopeIdSelect");
		String biotopeName = b.getString("biotopeSelect");
		//System.out.println("С������"+biotopeName+"��������"+areaName);

		areaShow = (TextView)findViewById(R.id.peo_recbui_bAreaShow);
		areaShow.setText(areaName);
		biotopeShow = (TextView)findViewById(R.id.peo_recbui_bBiotopeShow);
		biotopeShow.setText(biotopeName);

		watchNum=(EditText)findViewById(R.id.peo_recbui_bWatchNumEdit);
		customerName=(EditText)findViewById(R.id.peo_recbui_bCustomerEdit);

		buildingNum=(EditText)findViewById(R.id.peo_recbui_bBuildingNumEdit);
		buildingNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		unitNum=(EditText)findViewById(R.id.peo_recbui_bUnitNumEdit);
		unitNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		leverNum=(EditText)findViewById(R.id.peo_recbui_bLeverNumEdit);
		leverNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		doorplateNum=(EditText)findViewById(R.id.peo_recbui_bDoorplateNumEdit);
		doorplateNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		//ListView��ʾ����
		showList(areaId,biotopeId);

		String maxWatchNumSql = "select max(num) from watch";
		cursor = dbd.queryData(maxWatchNumSql, null);
		cursor.moveToFirst();
		int maxWatNum = cursor.getInt(0);
		//System.out.println("�����"+maxWatNum);
		watchNextNumS = String.valueOf(maxWatNum+1);
		watchNum.setText(watchNextNumS);
		//�����û�
		addCus();

		back = (Button)findViewById(R.id.peo_recbui_bExit);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PeoRecordbuilding.this, AllMenuItem.class);
				startActivity(intent);
			}
		});

	}
	/**
	 * �����û��ķ���
	 */
	private void addCus() {
		//�����Զ��ı��

		addB = (Button)findViewById(R.id.peo_recbui_bAdd);

		addB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cusNameS = customerName.getText().toString().trim();
				buiNumS = buildingNum.getText().toString().trim();
				uniNumS = unitNum.getText().toString().trim();
				levNumS = leverNum.getText().toString().trim();
				dooNumS = doorplateNum.getText().toString().trim();
				//System.out.println(buiNumS+"|"+uniNumS+"|"+levNumS+"|"+dooNumS);
				//��֤�����Ƿ�Ϊ��
				if(		!cusNameS.equals("")&&
						!buiNumS.equals("")&&
						!uniNumS.equals("")&&
						!levNumS.equals("")&&
						!dooNumS.equals("")		){

					String qBiotopeId = getSql.getqBiotopeId();				
					String[] sql1S = {areaId,biotopeId,buiNumS}; 
					cursor = dbd.queryData(qBiotopeId, sql1S);
					//�ж�¥���Ƿ����
					if(cursor.equals(null)){
						showMessDialog(PeoRecordbuilding.this,"¥�Ų����ڣ�������������");
						return;
					}else{
						cursor.moveToFirst();
						buildId = String.valueOf(cursor.getInt(0));
					}
					String qUnitId = getSql.getqUnitId();
					String[] sql2S = {areaId,biotopeId,buiNumS,uniNumS}; 
					cursor = dbd.queryData(qUnitId, sql2S);
					if(cursor ==null){
						showMessDialog(PeoRecordbuilding.this,"��Ԫ�����ڣ�������������");
						return;
					}else{
						cursor.moveToFirst();
						unitId = String.valueOf(cursor.getInt(0));
					}

					String qLeverId = getSql.getqLeverId();
					String[] sql3S = {areaId,biotopeId,buiNumS,uniNumS,levNumS}; 

					cursor = dbd.queryData(qLeverId, sql3S);
					if(cursor==null){
						showMessDialog(PeoRecordbuilding.this,"¥�㲻���ڣ�������������");
						return;
					}else{
						cursor.moveToFirst();
						leverId = String.valueOf(cursor.getInt(0));
					}
					try {

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
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String dateS = format.format(date);
						String[] watchS = {watchNextNumS,airComId,"0","0","0",dateS,"��","0","1"};
						dbd.insertData(insertWatch, watchS);

						//�½��û�����
						String insertCusSql = getSql.getInsertCustomer();

						String[] cusSelection = {cusNameS,watchNextNumS,"0",areaId,biotopeId,buildId,unitId,leverId,dooNumS,"1"};
						dbd.insertData(insertCusSql, cusSelection);

						showMessDialog(PeoRecordbuilding.this,"��Ϣ¼��ɹ�");
						System.out.println("¥��"+buiNumS+"��Ԫ"+uniNumS+"¥��"+levNumS+"���ƺ�"+dooNumS);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}else{
					showMessDialog(PeoRecordbuilding.this,"������Ϣ������");
				}
			}
		});


	}

	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("��ʾ");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.create().show();

	}
	/*
	 * ��ѯ���ݿ⣬��ListView����ʾ��ǰ����С���еĿͻ�
	 */
	@SuppressWarnings("deprecation")
	public void showList(String areaId,String biotopeId){
		String listSqlCustomer = getSql.getListSqlCustomer();
		String[] selection = {areaId,biotopeId};
		cursorSele = dbd.queryData(listSqlCustomer, selection);
		if (cursorSele != null) {
			cursorSele.moveToFirst();

			for (int i = 0; i < 7; i++) {
				System.out.println("�û����ǣ�"+cursorSele.getString(i));
			}

			SimpleCursorAdapter sca = new SimpleCursorAdapter(
					this,
					R.layout.peo_record_list,
					cursorSele,
					new String[]{
							PeoRecBui.DoorplateNum,
							PeoRecBui.CustomerName,
							PeoRecBui.WatchNum,
							PeoRecBui.State

					},
					new int[]{
							R.id.peo_rec_list_tv1,
							R.id.peo_rec_list_tv2,
							R.id.peo_rec_list_tv3,
							R.id.peo_rec_list_tv4
					}
					);

			listView = (ListView)findViewById(R.id.peo_recbui_bListView);
			listView.addHeaderView(LayoutInflater.from(this).inflate( R.layout.peo_table_title1, null),null,false);
			listView.setAdapter(sca);

			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				int lo = -1;
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					lo = arg2-1;

					deleteDialog(PeoRecordbuilding.this,"ɾ������");
					return true;
				}
				/*
				 * ɾ�����ݺ���
				 */
				private void deleteDialog(
						Context ctx, String mes) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
					builder.setTitle("����");
					builder.setMessage(mes);

					builder.setNegativeButton("����", null);//���ز������κβ���
					//ȷ��ɾ�����ݽ���������ɾ��
					builder.setPositiveButton("ȷ��", new OnClickListener() {
						ContentValues cv = null;

						public void onClick(DialogInterface dialog, int which) {
							try {

								//�ƶ��α굽ѡ�����
								cursorSele.moveToPosition(lo);
								//��ȡȼ��ʹ�������_id����updateɾ����־λ
								cv = new ContentValues();
								cv.put("live", "0");
								String[] air_id = {String.valueOf(cursorSele.getInt(2))};							
								dbd.updateData("aircomsumption", cv, "_id=?", air_id);
								//��ȡȼ�����_id����updateɾ����־λ
								cv = new ContentValues();
								cv.put("live", "0");
								String[] wat_id = {String.valueOf(cursorSele.getInt(1))};	
								dbd.updateData("watch", cv, "_id=?", wat_id);

								//��ȡ��ҵ�û����_id����update��ɾ����־λ
								cv = new ContentValues();
								cv.put("live", "0");
								String[] ind_id = {String.valueOf(cursorSele.getInt(0))};	
								dbd.updateData("customer", cv, "_id=?", ind_id);

								showMessDialog(PeoRecordbuilding.this,"ɾ�����ݳɹ�");
							} catch (Exception e) {
								showMessDialog(PeoRecordbuilding.this,"ɾ�����ݳ���");
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

}
