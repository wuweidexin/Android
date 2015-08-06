package com.chen.testbluetoothClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

public class ConnectThread extends Thread{
	private final BluetoothDevice btDevice;
	private  BluetoothSocket btSocket;
	private InputStream cwjInStream = null;
	private OutputStream cwjOutStream = null;
	private BluetoothAdapter mBluetoothAdapter = null;

	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	@SuppressLint("NewApi")
	public ConnectThread(BluetoothDevice device) {
		this.btDevice = device;
		BluetoothSocket tmp = null;
		try {
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			
		} catch (Exception e) {

		}
		btSocket = tmp;

	}
	public void run(){
		//mBluetoothAdapter.cancelDiscovery();
		
		try {
			btSocket.connect();
			System.out.println("执行到这里了*****************************");
			cwjInStream = btSocket.getInputStream();
			cwjOutStream = btSocket.getOutputStream();
			Log.d("Connnect", "********SUCCESS");
			//new InOutThread(btSocket);
			byte[] buf = {1,2,3,4};
			cwjOutStream.write(buf);
			
//			byte[] buffer = new byte[1024];  // buffer store for the stream           
//			String bytes = null; // bytes returned from read()           
//			// Keep listening to the InputStream until an exception occurs           
//			while (true) {               
//				try {                   
//					// Read from the InputStream                   
//					cwjInStream.read(buffer); 
//					
//					// Send the obtained bytes to the UI Activity                   
//					if(!new String(bytes).equals("")){
//						
//					}
//				} catch (IOException e) {                   
//					break;               
//				}           
//			}      
		} catch (Exception e) {
			try {
				btSocket.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//			finally{
//			try {
//				cwjInStream.close();
//				cwjOutStream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return;
	}
	



	
	
	
}
