package com.ds.bluetoothUtil;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

/**
 * �����������߳�
 *
 *
 */
public class BluetoothServerConnThread extends Thread {
	
	private Handler serviceHandler;		//����ͬServiceͨ�ŵ�Handler
	private BluetoothAdapter adapter;
	private BluetoothSocket socket;		//����ͨ�ŵ�Socket
	private BluetoothServerSocket serverSocket;
	
	/**
	 * ���캯��
	 * @param handler
	 */
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	public BluetoothServerConnThread(Handler handler) {
		this.serviceHandler = handler;
		adapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void run() {
		
		try {
			serverSocket = adapter.listenUsingRfcommWithServiceRecord("Server", BluetoothTools.PRIVATE_UUID);
			socket = serverSocket.accept();
		} catch (Exception e) {
			//��������ʧ����Ϣ
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			e.printStackTrace();
			return;
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (socket != null) {
			//�������ӳɹ���Ϣ����Ϣ��obj�ֶ�Ϊ���ӵ�socket
			Message msg = serviceHandler.obtainMessage();
			msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
			msg.obj = socket;
			msg.sendToTarget();
		} else {
			//��������ʧ����Ϣ
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			return;
		}
	}
	
	
}