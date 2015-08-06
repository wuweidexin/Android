package com.chen.datatransmit;

import com.chen.chaobiao1_1.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;
/**
 * ��Ҫ���ܣ�չʾ����汾
 * @author chen
 *
 */
public class ReadVersion extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String version = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dat_read_version);
		//��ȡ��������
		PackageManager pm = this.getPackageManager();//contextΪ��ǰActivity������ 
		try {
			//��ȡ����Ϣ
			PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
			//��ȡ�汾����
			version = pi.versionName;
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView t = (TextView)findViewById(R.id.dat_read_version);
		t.setText("��ǰ�汾�ǣ�"+version);
	}
}
