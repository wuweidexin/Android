package com.chen.industrywirless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chen.chaobiao1_1.R;
import com.chen.peowirless.CopyPeo;
/**
 * ��Ҫ���ܣ�ѡ������С���š�¥��
 * @author chen
 *
 */
public class IndWirCopLogin extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ind_wir_coplogin);
		createView();
	}
	/*
	 * ��ʼ����ͼ
	 */
	private void createView() {
		Button copy = (Button)findViewById(R.id.ind_wir_submit);
		
		copy.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndWirCopLogin.this, IndWirCopShow.class);
				startActivity(intent);
			}
			
		});
	}

}
