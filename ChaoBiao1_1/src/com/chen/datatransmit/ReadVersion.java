package com.chen.datatransmit;

import com.chen.chaobiao1_1.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 主要功能：展示软件版本
 * @author chen
 *
 */
public class ReadVersion extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String version = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dat_read_version);
		//获取包管理器
		PackageManager pm = this.getPackageManager();//context为当前Activity上下文 
		try {
			//获取包信息
			PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
			//获取版本名称
			version = pi.versionName;
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView t = (TextView)findViewById(R.id.dat_read_version);
		t.setText("当前版本是："+version);
	}
}
