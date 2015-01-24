package org.openintents.plaphoons.uitl;

import android.content.res.Configuration;

public class Helper {

	public static boolean isTablet(Configuration configuration) {
		int cur = configuration.screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;
		if (cur == Configuration.SCREENLAYOUT_SIZE_UNDEFINED)
			return false;
		return cur >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
