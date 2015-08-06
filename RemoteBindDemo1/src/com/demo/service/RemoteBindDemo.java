package com.demo.service;


import com.demo.service.IMyCountService;
import com.demo.service.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
/*
 * 测试Service的远程调用未能成功，希望什么时候有时间测试一下，关联的工程是serviceDemo2
 */
public class RemoteBindDemo extends Activity{

	private IMyCountService remotecountService;
	private ServiceConnection serviceConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			remotecountService = IMyCountService.Stub.asInterface(service);
			try {
				Log.v("CountService", "on service connected, count is" + remotecountService.getCount());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			remotecountService = null;
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.bindService(new Intent("com.demo.service.RemoteCountService"), 
				this.serviceConnection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		this.unbindService(serviceConnection);
		super.onDestroy();
	}

}
