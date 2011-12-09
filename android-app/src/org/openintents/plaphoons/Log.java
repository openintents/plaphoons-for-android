package org.openintents.plaphoons;

public class Log {
	private static final String TAG = "Plaphoons";

	public static void v(String msg) {

		android.util.Log.v(TAG, msg);
	}
}
