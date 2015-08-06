package com.example.testbluetoothserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;



public class Server extends Thread{
	private BluetoothAdapter adapterServer;
	private  BluetoothServerSocket mmServerSocket; 
	private BluetoothSocket socket = null; 
	private InputStream cwjInStream = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	String NAME = "Lenovo A65";

	public void Server(){
		this.adapterServer = BluetoothAdapter.getDefaultAdapter();
	}


	public void run() {   
		try {
			mmServerSocket = adapterServer.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Keep listening until exception occurs or a socket is returned   
		while (true) {   
			try {   
				socket = mmServerSocket.accept();   
				Log.d("Server", "*******Receive the Client");
				WRMess(socket);
			} catch (IOException e) {   
				e.printStackTrace();
			}   
			// If a connection was accepted   
			if (socket != null) {   
				// Do work to manage the connection (in a separate thread)   
				//manageConnectedSocket(socket);   
				//mmServerSocket.close();   
				break;   
			}   
		}

	}   

	/** Will cancel the listening socket, and cause the thread to finish */   
	public void cancel() {   
		try {   
			mmServerSocket.close();   
		} catch (IOException e) { }   
	}   

	public void WRMess(BluetoothSocket socket){
		InputStream inStream = null;
		byte[] buffer = new byte[1024];  // buffer store for the stream           
		String bytes = null; // bytes returned from read()           
		// Keep listening to the InputStream until an exception occurs           

		try {                   
			// Read from the InputStream
			socket.getInputStream();
			inStream.read(buffer); 

			// Send the obtained bytes to the UI Activity                   
			if(!new String(bytes).equals("")){
				System.out.println("**********"+buffer.toString());
			}
		} catch (IOException e) {                   
			e.printStackTrace();
		}
	}

}
