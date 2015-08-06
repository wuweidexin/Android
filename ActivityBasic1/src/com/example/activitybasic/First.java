package com.example.activitybasic;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
/*
 * 关于Activity的生命周期,这个版本是添加按钮的版本
 * 
 * 测试全屏显示
 * 
 * 定制标题，见title.xml中
 * 
 * 为窗口应用风格,见AndroidMainfest.xml
 */
public class First extends Activity {

	static final String Activity_ID = "First";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         *这里是加入全屏的代码
         */
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_first);
        
       //加入标题代码
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
      
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.i(Activity_ID, "oncreate has been called");
        
        
        Button finish = (Button)findViewById(R.id.testfinish);
        finish.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
				
			}
        });
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	 Log.i(Activity_ID, "onDestroy has been called");
    }
    
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	 Log.i(Activity_ID, "onPause has been called");
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume(); 
    	Log.i(Activity_ID, "onResume has been called");
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	 Log.i(Activity_ID, "onStart has been called");
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	 Log.i(Activity_ID, "onStop has been called");
    }
    
    
    
}
