package com.example.testormlite;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class Util {
	public static DatabaseHelpers databaseHelper;
	public static DatabaseHelpers getHelper(Context context){
		if(databaseHelper == null){
			databaseHelper = OpenHelperManager.getHelper(context, 
    				DatabaseHelpers.class);
    	}
		return databaseHelper;
	}
	
}
