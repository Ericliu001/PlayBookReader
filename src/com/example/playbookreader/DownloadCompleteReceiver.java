package com.example.playbookreader;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class DownloadCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		File update = new File(Environment.DIRECTORY_DOWNLOADS, DownloadCheckService.UPDATE_FILENAME);
		if (update.exists()) {
			context.startService(new Intent(context, DownloadInstallService.class));
		}
	
	}

}
