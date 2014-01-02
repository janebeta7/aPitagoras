package com.janebeta7.aPitagoras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MediaScannerReceiver extends BroadcastReceiver {
	 private static final String LOGTAG = "LogsJanebeta7";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("android.intent.action.MEDIA_SCANNER_FINISHED")) {
			// update finish
			Log.i(LOGTAG, "MediaScannerReceiver >onReceive()");
		}
	}

	public void setRestart(boolean r) {
	}
}