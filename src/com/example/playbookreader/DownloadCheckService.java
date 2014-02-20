package com.example.playbookreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;

public class DownloadCheckService extends IntentService {

	private static final String UPDATE_URL = "http://misc.commonsware.com/empublite-update.json";
	private static final String UPDATE_BASEDIR = "updates";
	public static final String PREF_PENDING_UPDATE = "pendingUpdateDir";
	public static final String UPDATE_FILENAME = "book.zip";

	public DownloadCheckService() {

		super("DownloadCheckService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		BufferedReader reader = null;

		try {
			URL url = new URL(UPDATE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setReadTimeout(15000);
			conn.connect();

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuilder buf = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				buf.append(line + "\n");
			}

			checkDownloadInfo(buf.toString());

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void checkDownloadInfo(String raw) throws JSONException {
		JSONObject json = new JSONObject(raw);
		String version = json.names().getString(0);
		File localCopy = new File(getUpdateBaseDir(this), version);

		if (!localCopy.exists()) {
			PreferenceManager
					.getDefaultSharedPreferences(this)
					.edit()
					.putString(PREF_PENDING_UPDATE, localCopy.getAbsolutePath())
					.apply();
			String url = json.getString(version);
			DownloadManager mgr = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

			DownloadManager.Request req = new DownloadManager.Request(
					Uri.parse(url));
			req.setAllowedNetworkTypes(
					DownloadManager.Request.NETWORK_MOBILE
							| DownloadManager.Request.NETWORK_WIFI)
					.setTitle("Nonsense update the book")
					.setDescription(
							"Alright, we are updating a new version of this book.")
					.setDestinationInExternalPublicDir(
							Environment.DIRECTORY_DOWNLOADS, UPDATE_FILENAME);
			mgr.enqueue(req);

		}

	}

	private File getUpdateBaseDir(Context context) {

		return new File(context.getFilesDir(), UPDATE_BASEDIR);
	}

}
