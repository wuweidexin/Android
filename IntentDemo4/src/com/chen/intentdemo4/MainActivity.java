package com.chen.intentdemo4;

import com.chen.intentdemo4.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button buttonc = (Button)findViewById(R.id.createb);
		Button buttond = (Button)findViewById(R.id.delectb);
		
		buttonc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
				i.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
				i.putExtra("duplicate", false);
				String action = "com.adroid.test";
				Intent resi = new Intent(MainActivity.this, this.getClass());
				i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, resi);
				sendBroadcast(i);
			
			}
		});
		
		buttond.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
			
				Intent i = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
				i.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
				i.putExtra("duplicate", false);
				String action = "com.adroid.test";
				Intent resi = new Intent(MainActivity.this, this.getClass());
				i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, resi);
				sendBroadcast(i);
			}
		});
	}

	

}
