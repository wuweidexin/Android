package com.chen.testbluetoothClient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import com.chen.testbluetooh.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Main extends Activity {

	private static final String TAG = "CLIENT";
	private static final boolean D = true;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothDevice device = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;

	private static String address = "00:12:FE:00:70:66";
	private int REQUEST_ENABLE_BT = 0;

	TextView t1 = null ,t2 = null;
	EditText et1 = null;
	Button b1 = null, b2 = null, b3 = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);

		t1 = (TextView)findViewById(R.id.textView1);
		t2 = (TextView)findViewById(R.id.textView2);

		et1 = (EditText)findViewById(R.id.editText1);

		
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				findBluetooh();
				String s = "Bluetooh Address:	"+mBluetoothAdapter.getAddress();
				t1.setText(s);

			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				findBluetooh();
				device = mBluetoothAdapter.getRemoteDevice(address);
				if(device != null){
					new ConnectThread(device).start();
				}else{
					Log.d("Execption", "Device is null");
				}
			}


		});

	}
	
	public void findBluetooh(){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if(mBluetoothAdapter == null){
			Log.d("提示", "没有找到本地蓝牙设备，可能驱动等出现问题");
		}

		if(! mBluetoothAdapter.enable()){
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, REQUEST_ENABLE_BT);
		}
	}
}


