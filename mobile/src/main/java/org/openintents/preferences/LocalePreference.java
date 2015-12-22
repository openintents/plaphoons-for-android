package org.openintents.preferences;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;

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
			ArrayList<CharSequence> entryList = new ArrayList<>();
			ArrayList<CharSequence> entryValueList = new ArrayList<>();

			int count = 0;
			Locale l;
			String country;
			for (int i = 0; i < length; i++) {
				l = Locale.getAvailableLocales()[i];
                if (invalidLocale(tts, l)){
                    continue;
                }
				switch (tts.isLanguageAvailable(l)) {
				case TextToSpeech.LANG_AVAILABLE:
				case TextToSpeech.LANG_COUNTRY_AVAILABLE:
				case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
					country = l.getDisplayCountry(l);
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

    private static boolean invalidLocale(TextToSpeech tts, Locale l) {
        try {
            l.getISO3Country();
			tts.isLanguageAvailable(l);
            return false;
        } catch (MissingResourceException e) {
            return true;
		} catch (IllegalArgumentException e) {
			return true;
		}

	}

    @Override
	protected void onPrepareDialogBuilder(Builder builder) {
		setEntryValues(entryValues);
		setEntries(entries);
		super.onPrepareDialogBuilder(builder);
	}

}
