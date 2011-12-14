package org.openintents.plaphoons.ui;

import org.openintents.plaphoons.sample.R;

import android.view.View;	

public abstract class HoneycombHelper {
	static void fullScreen(MainActivity mainActivity) {
		View v = mainActivity.findViewById(R.id.talker_layout);
		v.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		mainActivity.getActionBar().hide();


	}
}
