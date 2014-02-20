package com.example.playbookreader;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.view.Menu;

public class Preferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.pref_display);
		}
	}
	
	@Override
	public void onBuildHeaders(List<Header> target) {
		// TODO Auto-generated method stub
		super.onBuildHeaders(target);
		loadHeadersFromResource(R.xml.preference_headers, target);
	}


}
