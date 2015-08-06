package com.chen.mainActivity;

import com.chen.chaobiao1_1.R;
import com.chen.dao.DataBaseDemo;
import com.chen.util.GetSQL;
import com.chen.util.TestString;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends Activity {
	DataBaseDemo dbm = null;
	GetSQL getSql = new GetSQL();
	TestString t = new TestString();
	private String DATABASE_NAME = null;
	private SQLiteDatabase mSqlDatabase = null;
	EditText username,pwd;
	Button submit,cancle;
	String name,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DATABASE_NAME = getSql.getDATABASE_NAME();
		login();
		initData();
	}

	private void login() {
		
		username = (EditText)findViewById(R.id.mai_editName);
		pwd = (EditText)findViewById(R.id.mai_editPwd);
		submit = (Button)findViewById(R.id.mai_submit);
		cancle = (Button)findViewById(R.id.mai_cancle);
		
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				name = username.getText().toString().trim();
				password = pwd.getText().toString().trim();
				
				if(name.equals("")||password.equals("")){
					showMessDialog(MainActivity.this,"用户名和密码不能为空");
				}else{
					if(t.matchNum(name)&&t.matchNum(password)){
						String[] selectionArg = {name};
						Cursor cursor = dbm.queryData("select password from admin where name = ?", selectionArg);
						cursor.moveToFirst();
						if(cursor != null){
							if(password.equals(cursor.getString(0))){
								Intent intent = new Intent(MainActivity.this, AllMenuItem.class);
								startActivity(intent);
							}else{
								showMessDialog(MainActivity.this,"密码错误");
							}
						}else{
							showMessDialog(MainActivity.this,"用不存在");
						}
						
					}else{
						showMessDialog(MainActivity.this,"用户名和密码输入不合法，需是数字");
					}
				}

			}
		});
		
	}
	private void showMessDialog(Context ctx,CharSequence mes){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("提示");
		builder.setMessage(mes);
		builder.setPositiveButton("Ok", null);
		builder.create().show();

	}
	public void initData(){
		dbm = new DataBaseDemo(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	
}
