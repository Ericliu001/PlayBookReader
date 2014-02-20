package com.example.playbookreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class SimpleContentActivity extends FragmentActivity {

	public static final String EXTRA_FILE = "file";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
			String file = getIntent().getStringExtra(EXTRA_FILE);
			
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, ContentFragment.newInstance(file)).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
