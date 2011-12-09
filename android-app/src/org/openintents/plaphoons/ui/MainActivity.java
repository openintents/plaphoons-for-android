/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openintents.plaphoons.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.openintents.plaphoons.PlaFileParser;
import org.openintents.plaphoons.SampleTalkCollection;
import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.R;
import org.openintents.plaphoons.ui.widget.SquareGridLayout;
import org.openintents.plaphoons.ui.widget.TalkButton;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public class MainActivity extends Activity implements OnInitListener {

	private static final int NOTIFICATION_DEFAULT = 1;
	private static final String ACTION_DIALOG = "org.openintents.plaphoons.action.DIALOG";

	private View mActionBarView;
	private String[] mToggleLabels = { "Show Titles", "Hide Titles" };
	private int mLabelIndex = 1;
	private int mThemeId = -1;

	private TalkInfoCollection mPanel;
	private final int REQUEST_CODE_DATA_CHECK = 1000;
	private final int REQUEST_CODE_PICK_FILE = 2000;
	private TextToSpeech mTts;
	private PlaFileParser mParser;
	private String mPlaRootDir;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null
				&& savedInstanceState.getInt("theme", -1) != -1) {
			mThemeId = savedInstanceState.getInt("theme");
			this.setTheme(mThemeId);
		}

		setContentView(R.layout.main);

		getActionBar().hide();

		View v = findViewById(R.id.talker_layout);
		// TODO full screen on phones
		v.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		mPanel = SampleTalkCollection.acceuil;

		mParser = new PlaFileParser();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		// read .pla file
		mPlaRootDir = prefs.getString("pladir", Environment
				.getExternalStorageDirectory().getAbsolutePath());
		String plaFilename = prefs.getString("plafile", null);

		if (plaFilename == null || plaFilename.length() == 0) {
			
			// FIXME 
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivity(i);
						
		} else {

			try {
				mPanel = mParser.parseFile(plaFilename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// If talk panel is not saved to the savedInstanceState,
			// 0 is returned by default.
			if (savedInstanceState != null) {
				String talkPanel = savedInstanceState.getString("talkpanel");
				// findTalkInfoCollection(talkPanel);
			}

			Intent checkIntent = new Intent();
			checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
			startActivityForResult(checkIntent, REQUEST_CODE_DATA_CHECK);
		}
	}

	public void open(final TalkInfoCollection tiCollection,
			TalkInfoCollection parent) {

		/* Find viewGroup defined in main.xml */
		SquareGridLayout viewGroup = (SquareGridLayout) findViewById(R.id.talker_layout);
		viewGroup.setSize(tiCollection.rows, tiCollection.columns);

		viewGroup.removeAllViews();
		LayoutInflater li = getLayoutInflater();
		for (int i = 0; i < tiCollection.rows; i++) {
			for (int j = 0; j < tiCollection.columns; j++) {

				TalkButton tb = (TalkButton) li.inflate(R.layout.talkbutton,
						null);
				TalkInfo ti;
				if (i >= tiCollection.infos.length
						|| j >= tiCollection.infos[i].length) {
					ti = new TalkInfo();
				} else {
					ti = tiCollection.infos[i][j];
				}
				tb.setTalkInfo(ti);
				tb.updateData(mPlaRootDir);
				tb.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						TalkButton tb = (TalkButton) view;
						TalkInfo talkInfo = tb.mTalkInfo;
						if (talkInfo.text != null) {
							HashMap<String, String> params = new HashMap<String, String>();
							mTts.speak(talkInfo.text, TextToSpeech.QUEUE_ADD,
									params);
						}

						if (talkInfo.child != null) {
							MainActivity.this
									.open(talkInfo.child, tiCollection);
						} else if (talkInfo.childFilename != null
								&& talkInfo.childFilename.length() > 0) {
							try {
								TalkInfoCollection child = mParser
										.parseFile(mPlaRootDir + "/"
												+ talkInfo.childFilename);
								MainActivity.this.open(child, tiCollection);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				});

				MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				lp.setMargins(0, 0, 0, 0);
				/* Add Button to row. */
				viewGroup.addView(tb, lp);

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.open:
			Intent intent = new Intent(
					"org.openintents.intent.action.PICK_FILE");

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_DATA_CHECK) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				mTts = new TextToSpeech(this, this);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	PendingIntent getDialogPendingIntent(String dialogText) {
		return PendingIntent.getActivity(
				this,
				dialogText.hashCode(), // Otherwise previous PendingIntents with
										// the same
										// requestCode may be overwritten.
				new Intent(ACTION_DIALOG).putExtra(Intent.EXTRA_TEXT,
						dialogText).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("theme", mThemeId);
	}

	@Override
	public void onInit(int status) {
		if (mTts.isLanguageAvailable(Locale.FRANCE) >= 0) {
			mTts.setLanguage(Locale.FRANCE);
			open(mPanel, null);
		} else {
			// TOOD
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTts != null) {
			mTts.shutdown();
		}
	}
}
