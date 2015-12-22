package org.openintents.plaphoons;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

import java.util.Locale;

import org.openintents.plaphoons.sample.R;
import org.openintents.preferences.LocalePreference;

public class PlaphoonsApplication extends Application implements OnInitListener {

	public TextToSpeech mTts = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mTts = new TextToSpeech(this, this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		if (mTts != null) {
			mTts.shutdown();
		}

	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			LocalePreference.init(this.mTts);
			setLocaleFromPerefences();
		} else {
			Toast.makeText(getApplicationContext(), "Error while initializing TextToSpeech Engine", Toast.LENGTH_LONG).show();
		}
	}

	private void setLocaleFromPerefences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String localeString = prefs.getString("locale", null);
		setLocale(localeString, false);
	}

	public void setLocale(String localeString, boolean updateOnly) {
		Locale locale;

		if (localeString != null) {
			int pos = localeString.indexOf(',');
			String language = localeString.substring(0, pos);
			String country = localeString.substring(pos + 1);
			locale = new Locale(language, country);
		} else {
			if (updateOnly) {
				locale = null;
			} else {
				locale = Locale.getDefault();
			}
		}

		if (locale != null) {
			switch (mTts.isLanguageAvailable(locale)) {
			case TextToSpeech.LANG_AVAILABLE:
			case TextToSpeech.LANG_COUNTRY_AVAILABLE:
			case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
				mTts.setLanguage(locale);
				if (updateOnly) {
					Toast.makeText(
							this,
							getString(R.string.language_changed,
									locale.getDisplayLanguage(locale)),
							Toast.LENGTH_SHORT).show();
				}
				break;

			case TextToSpeech.LANG_MISSING_DATA:

				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(installIntent);
				break;
			case TextToSpeech.LANG_NOT_SUPPORTED:
				// TODO
				break;
			}
		}
	}

}
