package com.njfsoft_utils.core;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;


public class SharedPrefs {
 
public SharedPreferences configSettings;
public SharedPreferences.Editor configEditor;
Activity  main;



    public SharedPrefs(Activity theMain) {
        main = theMain;
        configSettings = theMain.getPreferences(Context.MODE_WORLD_WRITEABLE);
	  configEditor = configSettings.edit();
    }

	public String getConfValString(String theKey) {
		String strTheVal = "noQvalue";
		try {
			strTheVal = configSettings.getString(theKey, "noQvalue");
		} catch (Exception err) {
			System.out.println("Error.getConValString: " + err);
		}
		return strTheVal;
	}

	public Integer getConfValInt(String theKey) {
		int intTheVal = 1234;
		try {
			intTheVal = configSettings.getInt(theKey, 0);
		} catch (Exception err) {
			System.out.println("Error.getConValString: " + err);
		}
		return intTheVal;
	}


	public void putConfValString(String theKey, String theVal) {
		configEditor = configSettings.edit();
		configEditor.putString(theKey, theVal);
		configEditor.commit();
		// currConfBundle = getConfBundle();
	}

	public void putConfValInt(String theKey, Integer theVal) {
		configEditor = configSettings.edit();
		configEditor.putInt(theKey, theVal);
		configEditor.commit();
		//  currConfBundle = getConfBundle();
	}




}   // end class SharedPrefs
