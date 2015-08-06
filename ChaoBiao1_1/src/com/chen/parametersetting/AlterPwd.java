package com.chen.parametersetting;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.mainActivity.AllMenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * ��Ҫ���ܣ��޸�����
 * @author Administrator
 *
 */
public class AlterPwd extends Activity{
	DataBaseDemo db = new DataBaseDemo(this);
	private SQLiteDatabase sqlDb = null;
	private int user_id=1;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.par_set_alterpwd);
		createView();
	}
	/*
	 * �����ڲ���������ݸ�ֵ
	 */
	private interface Admin{
		String Table_Name = "Admin";
		String Id = "_id";
		String Name = "name";
		String Pwd = "password";
	}
	
	
	private void createView() {
		Button alter = (Button)findViewById(R.id.par_set_alter);
		Button back = (Button)findViewById(R.id.par_set_back);
		final EditText pwd1 = (EditText)findViewById(R.id.par_set_pwdOneEdit);
		final EditText pwd2 = (EditText)findViewById(R.id.par_set_pwdTwoEdit);
		
		//��ѯ��ǰ�û�������
		String select = Admin.Id+"="+user_id;
		sqlDb = db.getWritableDatabase();
		Cursor cursor = sqlDb.query(Admin.Table_Name, new String[]{Admin.Id,Admin.Name}, select, null, null, null, null);
		cursor.moveToFirst();
		final int alertId = cursor.getInt(0);
		alter.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String spwd1 = pwd1.getText().toString().trim();
				String spwd2 = pwd2.getText().toString().trim();
				
System.out.println("�޸ĺ������ǣ�"+spwd2);
				//�޸�����
				ContentValues value = new ContentValues();
				value.put(Admin.Pwd, spwd2);
				final String whereClause = Admin.Id+"="+alertId;
				
				if(!spwd1.equals("") && !spwd2.equals("")){
					if(spwd1.equals(spwd2)){
						sqlDb.update(Admin.Table_Name, value, whereClause, null);
						showAlertDialog(AlterPwd.this, "�޸ĳɹ�");
					}else{

						showMessDialog(AlterPwd.this, "ǰ�����벻ͳһ�����������");
					}
				}else{
					showMessDialog(AlterPwd.this, "��ϢΪ��");
					
				}
				
			}
		});
		
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(AlterPwd.this, AllMenuItem.class);
				startActivity(i);
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
	
	private void showAlertDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("��ʾ");
		builder.setMessage(mes);
		EmptyOnClickListener e1 = new EmptyOnClickListener();
		builder.setPositiveButton("Ok", e1);
		builder.create().show();
		
	}
	public class EmptyOnClickListener implements android.content.DialogInterface.OnClickListener{
		public void onClick(DialogInterface dialog, int which) {
			Intent i=new Intent(AlterPwd.this, AllMenuItem.class);
			startActivity(i);
		}
		
	}
}
