package org.openintents.plaphoons.ui;

import org.openintents.plaphoons.sample.R;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {
	protected static final int REQUEST_CODE_PICK_FILE = 1000;
	private EditTextPreference mPladirPref;
	private EditTextPreference mPlafilePref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		Intent settingsIntent = new Intent();
		settingsIntent.setData(Uri.parse("package:" + getPackageName()));				
		settingsIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		settingsIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.applications.InstalledAppDetails"));

		addPreferencesFromIntent(settingsIntent);
		
		mPladirPref = (EditTextPreference) findPreference("pladir");
		mPlafilePref = (EditTextPreference) findPreference("plafile");

		String pladir = sp.getString("pladir", null);
		if (pladir == null || pladir.length() == 0) {			
			mPladirPref.setText(Environment.getExternalStorageDirectory()
					.toString());
		}

		final EditTextPreference mPlaChoose = (EditTextPreference) findPreference("plachoose");
		mPlaChoose
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						Intent intent = new Intent(
								"org.openintents.action.PICK_FILE");
						if (mPladirPref.getText() != null
								&& mPladirPref.getText().length() > 0) {
							intent.setData(Uri.parse("file://"
									+ mPladirPref.getText() + "/"
									+ mPlafilePref.getText()));
						} else {
							intent.setData(Uri.fromFile(Environment
									.getExternalStorageDirectory()));
						}
						
						try {
							startActivityForResult(intent,
									REQUEST_CODE_PICK_FILE);
						} catch (ActivityNotFoundException e) {
							Toast.makeText(PreferencesActivity.this, R.string.install_filemanger,
									Toast.LENGTH_LONG).show();
						}
						
						mPlaChoose.getDialog().cancel();
						
						return true;
					}
				});			
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String plaDir = data.getData().getPath();
			String plaFile = data.getData().getLastPathSegment();
			plaDir = plaDir.substring(0, plaDir.length() - plaFile.length() -1);

			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("pladir", plaDir);
			editor.putString("plafile", plaFile);
			editor.commit();
			
			mPladirPref.setText(plaDir);			
			mPlafilePref.setText(plaFile);
		}

	}
}
