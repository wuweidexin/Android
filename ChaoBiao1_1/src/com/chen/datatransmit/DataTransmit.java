package com.chen.datatransmit;

import com.chen.chaobiao1_1.R;
import com.ds.bluetoothUtil.BluetoothTools;
import com.ds.bluetoothUtil.TransmitBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * ��Ҫ���ܣ����ݴ���
 * @author chen
 *
 */
public class DataTransmit extends Activity{
	Button download;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dat_tra_datatrasmit);
		download =(Button)findViewById(R.id.dat_tra_download); 
		
		download.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TransmitBean data = new TransmitBean();
				data.setMsg("��Ҫ��������");
				Intent sendDataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_SERVICE);
				sendDataIntent.putExtra(BluetoothTools.DATA, data);
				sendBroadcast(sendDataIntent);
				
			}
		});
	}
}
