package org.openintents.plaphoons;

import org.openintents.plaphoons.sample.BuildConfig;

public class Log {
    private static final String TAG = "Plaphoons";

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.v(TAG, msg);
        }
    }
}
