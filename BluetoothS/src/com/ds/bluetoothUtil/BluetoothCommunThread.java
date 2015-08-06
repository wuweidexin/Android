package com.ds.bluetoothUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ds.bluetooth.ServerActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * ����ͨѶ�߳�
 * 
 *
 */
public class BluetoothCommunThread extends Thread {

	private Handler serviceHandler;		//��Serviceͨ�ŵ�Handler
	private BluetoothSocket socket;
	private ObjectInputStream inStream;		//����������
	private ObjectOutputStream outStream;	//���������
	public volatile boolean isRun = true;	//���б�־λ
	ServerActivity s = new ServerActivity();
	/**
	 * ���캯��
	 * @param handler ���ڽ�����Ϣ
	 * @param socket
	 */
	@SuppressLint("NewApi")
	public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
		this.serviceHandler = handler;
		this.socket = socket;
		try {
			this.outStream = new ObjectOutputStream(socket.getOutputStream());
			this.inStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//��������ʧ����Ϣ
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void run() {
		while (true) {
			if (!isRun) {
				break;
			}
			try {

				Object obj = inStream.readObject();
				if(!obj.equals("")){

					System.out.println("���ó���***********************************");
					Object o = "�յ���";
					
					Message msg = serviceHandler.obtainMessage();
					msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
					msg.obj = o;
					msg.sendToTarget();
				}
				//				}else{

				//���ͳɹ���ȡ���������Ϣ����Ϣ��obj����Ϊ��ȡ���Ķ���
				Log.d("���ó���","**********************"+obj.toString());
				Message msg = serviceHandler.obtainMessage();
				msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
				msg.obj = obj;
				msg.sendToTarget();
				//				}


			} catch (Exception ex) {
				//��������ʧ����Ϣ
				serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
				ex.printStackTrace();
				return;
			}
		}

		//�ر���
		if (inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * д��һ�������л��Ķ���
	 * @param obj
	 */
	public void writeObject(Object obj) {
		try {
			outStream.flush();
			outStream.writeObject(obj);
			outStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
