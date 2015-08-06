package com.example.testbluetoothserver;

import com.example.testbluetoohserver.R;

import android.os.Bundle;
import android.app.Activity;


public class Main extends Activity {   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new Server().start();
	}
	
}  

