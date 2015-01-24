package org.openintents.plaphoons.ui;

import android.annotation.SuppressLint;
import android.view.View;

public abstract class HoneycombHelper {
	@SuppressLint("NewApi")
	static void fullScreen(MainActivity mainActivity) {
		View rootView = mainActivity.getWindow().getDecorView();
		rootView.setSystemUiVisibility(View.STATUS_BAR_VISIBLE);
		rootView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
	}
}
