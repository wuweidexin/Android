package com.chen.intentdemo4;

import java.io.IOException;

import com.chen.intentdemo4.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
/*
 * 通过意图演示建立快捷方式
 */
public class ShortCut extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.play);
		final Intent i = this.getIntent();
		final String action = i.getAction();
		if(Intent.ACTION_CREATE_SHORTCUT.equals(action))
		{
			setupShortcut();
			finish();
			return;
		}
		
	}

	private void setupShortcut() {
		Intent si = new Intent(Intent.ACTION_MAIN);
		si.setClassName(this, "com.chen.intent.activity_main");
		Intent i = new Intent();
		i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, si);
		i.putExtra(Intent.EXTRA_SHORTCUT_NAME, "shortcut");
		Parcelable ir = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
		i.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ir);
		setResult(RESULT_OK,i);
		
	}


}
