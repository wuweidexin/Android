package com.ds.bluetooth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.chaobiao1_1.R;
import com.ds.bluetoothUtil.BluetoothClientService;
import com.ds.bluetoothUtil.BluetoothTools;
import com.ds.bluetoothUtil.TransmitBean;

public class CopWatBluActivity extends Activity {

	private TextView serversText;
	private EditText chatEditText;
	private EditText sendEditText;
	private Button sendBtn;
	private Button startSearchBtn;
	private Button selectDeviceBtn;

	private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();

	//�㲥������
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
				//δ�����豸
				serversText.append("not found device\r\n");

			} else if (BluetoothTools.ACTION_FOUND_DEVICE.equals(action)) {
				//��ȡ���豸����
				BluetoothDevice device = (BluetoothDevice)intent.getExtras().get(BluetoothTools.DEVICE);
				deviceList.add(device);
				serversText.append(device.getName() + "\r\n");

			} else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
				//���ӳɹ�
				serversText.append("���ӳɹ�");
				sendBtn.setEnabled(true);

			} else if (BluetoothTools.ACTION_DATA_TO_GAME.equals(action)) {
				//��������
				TransmitBean data = (TransmitBean)intent.getExtras().getSerializable(BluetoothTools.DATA);
				String msg = "from remote " + new Date().toLocaleString() + " :\r\n" + data.getMsg() + "\r\n";
				chatEditText.append(msg);

			} 
		}
	};


	@Override
	protected void onStart() {
		//����豸�б�
		deviceList.clear();

		//������̨service
		Intent startService = new Intent(CopWatBluActivity.this, BluetoothClientService.class);
		startService(startService);

		//ע��BoradcasrReceiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);
		intentFilter.addAction(BluetoothTools.ACTION_FOUND_DEVICE);
		intentFilter.addAction(BluetoothTools.ACTION_DATA_TO_GAME);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_SUCCESS);

		registerReceiver(broadcastReceiver, intentFilter);

		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cop_wat_blu);

		serversText = (TextView)findViewById(R.id.cwb_clientServersText);
		chatEditText = (EditText)findViewById(R.id.cwb_clientChatEditText);

		sendBtn = (Button)findViewById(R.id.cwb_clientSendMsgBtn);
		startSearchBtn = (Button)findViewById(R.id.cwb_startSearchBtn);
		selectDeviceBtn = (Button)findViewById(R.id.cwb_selectDeviceBtn);

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//������Ϣ
				String peo_mess = "���ó���";
				String ind_mess = "��ҵ����";
				String mess = null;
				//������Ϣ
				Intent i = getIntent();
				Bundle b = i.getBundleExtra("copybundle");
				String s = b.getString("copywatch");
				if(s.equals("���ó���")){
					mess = peo_mess;
				}else{
					mess = ind_mess;
				}
				
				
				TransmitBean data = new TransmitBean();
				data.setMsg(mess);
				Intent sendDataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_SERVICE);
				sendDataIntent.putExtra(BluetoothTools.DATA, data);
				sendBroadcast(sendDataIntent);

			}
		});

		startSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//��ʼ����
				Intent startSearchIntent = new Intent(BluetoothTools.ACTION_START_DISCOVERY);
				sendBroadcast(startSearchIntent);
			}
		});

		selectDeviceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//ѡ���һ���豸
				Intent selectDeviceIntent = new Intent(BluetoothTools.ACTION_SELECTED_DEVICE);
				selectDeviceIntent.putExtra(BluetoothTools.DEVICE, deviceList.get(0));
				sendBroadcast(selectDeviceIntent);
			}
		});
	}

	@Override
	protected void onStop() {
		//�رպ�̨Service
		Intent startService = new Intent(BluetoothTools.ACTION_STOP_SERVICE);
		sendBroadcast(startService);

		unregisterReceiver(broadcastReceiver);
		super.onStop();
	}
}
