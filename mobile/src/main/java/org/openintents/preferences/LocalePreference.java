package org.openintents.preferences;

import java.util.ArrayList;
import java.util.Locale;

import org.openintents.plaphoons.PlaphoonsApplication;

import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.preference.ListPreference;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.AttributeSet;

public class LocalePreference extends ListPreference {

	private static CharSequence[] entries;
	private static CharSequence[] entryValues;

	public LocalePreference(Context context) {
		super(context);
	}

	public LocalePreference(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public static void init(TextToSpeech tts) {
		if (entries == null) {
			int length = Locale.getAvailableLocales().length;
			ArrayList<CharSequence> entryList = new ArrayList<CharSequence>();
			ArrayList<CharSequence> entryValueList = new ArrayList<CharSequence>();

			int count = 0;
			for (int i = 0; i < length; i++) {
				Locale l = Locale.getAvailableLocales()[i];

				switch (tts.isLanguageAvailable(l)) {
				case TextToSpeech.LANG_AVAILABLE:
				case TextToSpeech.LANG_COUNTRY_AVAILABLE:
				case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
					String country = l.getDisplayCountry(l);
					if (TextUtils.isEmpty(country)) {
						entryList.add(l.getDisplayLanguage(l));
					} else {
						entryList.add(l.getDisplayLanguage(l) + " (" + country
								+ ")");
					}
					entryValueList.add(l.getLanguage() + "," + l.getCountry());
					count++;
				}
			}

			entries = entryList.toArray(new CharSequence[count]);
			entryValues = entryValueList.toArray(new CharSequence[count]);
		}

	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		setEntryValues(entryValues);
		setEntries(entries);
		super.onPrepareDialogBuilder(builder);
	}

}
