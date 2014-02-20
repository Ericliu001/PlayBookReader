package com.example.playbookreader;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.playbookreader.ModelFragment.OnSetupPager;

public class MainActivity extends FragmentActivity implements OnSetupPager {
	private static final String MODEL = "model";
	private static final String PREF_LAST_POSITION = "lastPosition";
	private static final String PREF_SAVE_LAST_POSITION = "saveLastPosition";
	private static final String PREF_KEEP_SCREEN_ON = "keepScreenOn";
	
	private ViewPager pager = null;
	private ContentsAdapter adapter = null;
	private SharedPreferences prefs = null;
	private ModelFragment model = null;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getSupportFragmentManager().findFragmentByTag(MODEL) == null) {
			model = new ModelFragment();
			getSupportFragmentManager().beginTransaction()
					.add(model, MODEL).commit();
		}else {
			model = (ModelFragment) getSupportFragmentManager().findFragmentByTag(MODEL);
		}

		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.pager);
		getActionBar().setHomeButtonEnabled(true);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (prefs != null) {
			pager.setKeepScreenOn(prefs.getBoolean(PREF_KEEP_SCREEN_ON, false));
		}
		
		IntentFilter f = new IntentFilter(DownloadInstallService.ACTION_UPDATE_READY);
		f.setPriority(1000);
		registerReceiver(onUpdate, f);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(onUpdate);
		
		if (prefs != null) {
			int position = pager.getCurrentItem();
			prefs.edit().putInt(PREF_LAST_POSITION, position).apply();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			pager.setCurrentItem(0, false);
			return true;

		case R.id.android:
			Intent i = new Intent(this, SimpleContentActivity.class);
			i.putExtra(SimpleContentActivity.EXTRA_FILE,
					"file:///android_asset/misc/androiddevelopers.html");
			startActivity(i);
			return true;
		case R.id.vogella:
			i = new Intent(this, SimpleContentActivity.class);
			i.putExtra(SimpleContentActivity.EXTRA_FILE,
					"file:///android_asset/misc/vogella.html");
			startActivity(i);

			return true;

		case R.id.action_settings:
			startActivity(new Intent(this, Preferences.class));

			return true;
			
		case R.id.notes:
			i = new Intent(this, NoteActivity.class);
			i.putExtra(NoteActivity.EXTRA_POSITION, pager.getCurrentItem());
			startActivity(i);
			return true;
			
		case R.id.update:
			startService(new Intent(this, DownloadCheckService.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setupPager(SharedPreferences prefs, BookContents contents) {
		// TODO Auto-generated method stub
		this.prefs = prefs;
		adapter = new ContentsAdapter(this, contents);
		pager.setAdapter(adapter);

		findViewById(R.id.progressBar1).setVisibility(View.GONE);
		findViewById(R.id.pager).setVisibility(View.VISIBLE);
		
		if (prefs.getBoolean(PREF_SAVE_LAST_POSITION, false)) {
			pager.setCurrentItem(prefs.getInt(PREF_LAST_POSITION, 0));
		}
		pager.setKeepScreenOn(prefs.getBoolean(PREF_KEEP_SCREEN_ON, false));
	}

	
	private BroadcastReceiver onUpdate = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			model.updateBook();
			abortBroadcast();
		}
	};

}
