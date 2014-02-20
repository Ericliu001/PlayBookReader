package com.example.playbookreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.DeletedContacts;
import android.util.Log;

public class ModelFragment extends android.support.v4.app.Fragment {
	private BookContents contents = null;
	private ContentsLoadTask contentsTask = null;
	private SharedPreferences prefs = null;
	private PrefsLoadTask prefsTask = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		deliverModel();
	}

	public interface OnSetupPager {

		void setupPager(SharedPreferences prefs, BookContents contents);
	}

	synchronized private void deliverModel() {
		// TODO Auto-generated method stub
		if (contents != null && prefs != null) {
			if (getActivity() instanceof OnSetupPager) {
				((OnSetupPager) getActivity()).setupPager(prefs, contents);
			} else {
				throw new ClassCastException();
			}
		} else {

			if (prefs == null && prefsTask == null) {
				prefsTask = new PrefsLoadTask();
				prefsTask.execute(getActivity().getApplicationContext()); // can't
																			// use
																			// activity
																			// context
																			// because
																			// it
																			// might
																			// not
																			// exist.
			} else if (contents == null && contentsTask == null) {
				updateBook();
			}
		}
	}

	public void updateBook() {
		contentsTask = new ContentsLoadTask();
		contentsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				getActivity().getApplicationContext());
	}

	private class ContentsLoadTask extends AsyncTask<Context, Void, Void> {
		private BookContents localContents = null;
		private Exception e = null;

		@SuppressWarnings("resource")
		@Override
		protected Void doInBackground(Context... params) {

			String updateDir = prefs.getString(
					DownloadInstallService.PREF_UPDATE_DIR, null);

			try {
				StringBuilder buf = new StringBuilder();
				InputStream json = null;

				if (updateDir != null && new File(updateDir).exists()) {
					json = new FileInputStream(new File(new File(updateDir),
							"contents.json"));
				} else {
					json = params[0].getAssets().open("book/contents.json");
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(
						json));
				String str;

				while ((str = in.readLine()) != null) {
					buf.append(str);
				}
				in.close();

				if (updateDir != null && new File(updateDir).exists()) {
					localContents = new BookContents(new JSONObject(
							buf.toString()), new File(updateDir));
				} else {

					localContents = new BookContents(new JSONObject(
							buf.toString()));
				}

			} catch (Exception e) {
				this.e = e;
			}

			String prevUpdateDir = prefs.getString(
					DownloadInstallService.PREF_PREV_UPDATE, null);

			if (prevUpdateDir != null) {
				File toBeDeleted = new File(prevUpdateDir);

				if (toBeDeleted.exists()) {
					deleteDir(toBeDeleted);
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (e == null) {
				ModelFragment.this.contents = localContents;
				ModelFragment.this.contentsTask = null;
				deliverModel();
			} else {
				Log.e("eric", "Exception loading contents");
			}

		}

	}

	private class PrefsLoadTask extends AsyncTask<Context, Void, Void> {
		SharedPreferences localPrefs = null;

		@Override
		protected Void doInBackground(Context... params) {
			// TODO Auto-generated method stub
			localPrefs = PreferenceManager
					.getDefaultSharedPreferences(params[0]);
			localPrefs.getAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			ModelFragment.this.prefs = localPrefs;
			ModelFragment.this.prefsTask = null;
			deliverModel();
		}

	}

	public boolean deleteDir(File dir) {
		if (dir.exists() && dir.isDirectory()) {
			File[] children = dir.listFiles();

			for (File child : children) {
				boolean ok = deleteDir(child);
				if (!ok) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
