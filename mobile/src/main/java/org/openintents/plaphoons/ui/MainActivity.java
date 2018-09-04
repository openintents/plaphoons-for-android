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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import org.openintents.plaphoons.Log;
import org.openintents.plaphoons.PlaFileParser;
import org.openintents.plaphoons.PlaphoonsApplication;
import org.openintents.plaphoons.SampleTalkCollection;
import org.openintents.plaphoons.SampleTalkCollectionPhone;
import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.sample.R;
import org.openintents.plaphoons.ui.widget.FlowLayout;
import org.openintents.plaphoons.ui.widget.SquareGridLayout;
import org.openintents.plaphoons.ui.widget.TalkButton;
import org.openintents.plaphoons.ui.widget.TalkButton.ClickHandler;
import org.openintents.plaphoons.uitl.Helper;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements OnLoadCompleteListener,
        ClickHandler {

    private static final int NOTIFICATION_DEFAULT = 1;
    private static final String ACTION_DIALOG = "org.openintents.plaphoons.action.DIALOG";
    private static final int REQUEST_CODE_DATA_CHECK = 1000;
    private static final int REQUEST_CODE_PREFERENCES = 2000;
    protected String mEncoding;
    protected boolean mHasDigitizer;
    private View mActionBarView;
    private String[] mToggleLabels = {"Show Titles", "Hide Titles"};
    private int mLabelIndex = 1;
    private TalkInfoCollection mPanel;
    private PlaFileParser mParser;
    private String mPlaRootDir;
    private String mPlaFilename;
    private String mCurrentPlaFilename;
    private boolean mUseSample;
    private ArrayList<String> mParentStack = new ArrayList<>();
    private SoundPool mSoundPool;
    private int mPendingSound;
    private EditText mText;
    private int mQueueMode;
    private List<TalkInfo> mTextStack = new ArrayList<>();
    private boolean mPlayControlOnly;
    private FlowLayout mTextAsImages;
    private boolean mShowTextAsImages;
    private WebView mWebView;
    private AbstractCollection<PrintJob> mPrintJobs = new ArrayList<>();
    private boolean mPrintText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        HoneycombHelper.fullScreen(this);


        mHasDigitizer = getPackageManager().hasSystemFeature(
                "android.hardware.touchscreen.pen");

        mText = (EditText) findViewById(R.id.text);
        mTextAsImages = (FlowLayout) findViewById(R.id.text_as_images);

        mParser = new PlaFileParser();
        setValuesFromSavedInstanceState(savedInstanceState);

        mSoundPool = new SoundPool(1, AudioManager.STREAM_VOICE_CALL, 0);
        mSoundPool.setOnLoadCompleteListener(this);

    }

    private void setValuesFromSavedInstanceState(Bundle savedInstanceState) {
        // If talk panel is not saved to the savedInstanceState,
        // 0 is returned by default.
        if (savedInstanceState != null) {
            mCurrentPlaFilename = savedInstanceState
                    .getString("currentPlaFile");
            mParentStack = savedInstanceState.getStringArrayList("parentStack");
        }

    }

    private void setValuesFromPreferences() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        // read .pla file
        mPlaRootDir = prefs.getString("pladir", Environment
                .getExternalStorageDirectory().getAbsolutePath());
        mPlaFilename = prefs.getString("plafile", null);
        mUseSample = prefs.getBoolean("usesample", false);
        mEncoding = prefs.getString("encoding", "iso-8859-1");
        mQueueMode = (prefs.getBoolean("queuemodedrop", true) ? TextToSpeech.QUEUE_FLUSH
                : TextToSpeech.QUEUE_ADD);
        mPlayControlOnly = prefs.getBoolean("playcontrolonly", false);
        mShowTextAsImages = prefs.getBoolean("showtextasimages", false);
        mPrintText = prefs.getBoolean("printtext", false);
    }

    public void showPanel(final TalkInfoCollection tiCollection,
                          TalkInfoCollection parent) {
        Log.v("show " + tiCollection);


        if (tiCollection.showTextBox) {
            mText.setVisibility(mShowTextAsImages ? View.GONE : View.VISIBLE);
            mTextAsImages.setVisibility(mShowTextAsImages ? View.VISIBLE : View.GONE);
        } else {
            mText.setVisibility(View.GONE);
            mText.setVisibility(View.GONE);
        }

        /* Find viewGroup defined in main.xml */
        SquareGridLayout viewGroup = (SquareGridLayout) findViewById(R.id.talker_layout);
        viewGroup.setSize(tiCollection.rows + (tiCollection.showTextBox ? 1 : 0), tiCollection.columns);

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

                tb.setClickHandler(mHasDigitizer, tiCollection, this);

                MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp.setMargins(0, 0, 0, 0);
                /* Add Button to row. */
                viewGroup.addView(tb, lp);

            }
        }

        if (tiCollection.showTextBox) {
            if (tiCollection.columns > 2) {
                TalkButton tb = (TalkButton) li.inflate(R.layout.talkbutton,
                        null);
                tb.setTalkInfo(new TalkInfo(getString(R.string.home),
                        Color.WHITE, R.drawable.home));
                tb.updateData(null);
                tb.setClickHandler(mHasDigitizer, tiCollection,
                        new TalkButton.ClickHandler() {

                            @Override
                            public void onButtonClick(
                                    TalkInfoCollection tiCollection,
                                    TalkButton b) {
                                home();

                            }
                        });
                tb.setBackgroundResource(R.drawable.picture_frame_control);
                MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp.setMargins(0, 0, 0, 0);
                /* Add Button to row. */
                viewGroup.addView(tb, lp);
            }

            TalkButton tb = (TalkButton) li.inflate(R.layout.talkbutton, null);
            tb.setTalkInfo(new TalkInfo(getString(R.string.play), Color.WHITE,
                    R.drawable.play));
            tb.updateData(null);
            tb.setClickHandler(mHasDigitizer, tiCollection,
                    new TalkButton.ClickHandler() {

                        @Override
                        public void onButtonClick(
                                TalkInfoCollection tiCollection, TalkButton b) {
                            playText();

                        }
                    });

            tb.setBackgroundResource(R.drawable.picture_frame_control);
            MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, 0);
            /* Add Button to row. */
            viewGroup.addView(tb, lp);

            tb = (TalkButton) li.inflate(R.layout.talkbutton, null);
            tb.setTalkInfo(new TalkInfo(getString(R.string.delete),
                    Color.WHITE, R.drawable.delete));
            tb.updateData(null);
            tb.setClickHandler(mHasDigitizer, tiCollection,
                    new TalkButton.ClickHandler() {

                        @Override
                        public void onButtonClick(
                                TalkInfoCollection tiCollection, TalkButton b) {
                            deleteText();

                        }
                    });
            tb.setBackgroundResource(R.drawable.picture_frame_control);
            lp = new ViewGroup.MarginLayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, 0);
            /* Add Button to row. */
            viewGroup.addView(tb, lp);
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

            case R.id.preferences:
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PREFERENCES);

                return true;
            case R.id.about:
                try {
                    AboutDialogBuilder.create(this).show();
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.launcher:
                try {
                    Intent target = new Intent();
                    target.setAction(Intent.ACTION_MAIN);
                    target.addCategory(Intent.CATEGORY_HOME);
                    Intent chooser = Intent.createChooser(target,
                            getString(R.string.choose_launcher));
                    startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValuesFromPreferences();

        // TODO decide whether to show or open the panel
        openPanel();
    }

    private void openPanel() {
        Log.v("openPanel");


        new AsyncTask<TalkInfoCollection, Void, TalkInfoCollection>() {

            @Override
            protected TalkInfoCollection doInBackground(
                    TalkInfoCollection... params) {

                if (TextUtils.isEmpty(mCurrentPlaFilename)) {
                    mCurrentPlaFilename = mPlaFilename;
                }

                TalkInfoCollection panel = null;

                if (mUseSample || TextUtils.isEmpty(mCurrentPlaFilename)) {
                    if (Helper.isTablet(getResources().getConfiguration())) {
                        panel = SampleTalkCollection.acceuil;
                    } else {
                        panel = SampleTalkCollectionPhone.acceuil;
                    }

                } else {

                    String fullFilePath = mPlaRootDir + "/"
                            + mCurrentPlaFilename;
                    Log.v("open " + fullFilePath);

                    try {
                        panel = mParser.parseFile(fullFilePath, mEncoding);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (panel != null) {
                        for (TalkInfo[] tis : panel.infos) {
                            for (TalkInfo ti : tis) {
                                if (ti.isTextWav()) {
                                    String wavFilePath = mPlaRootDir + "/"
                                            + ti.text;

                                    Log.v("open " + wavFilePath);

                                    ti.soundId = mSoundPool
                                            .load(wavFilePath, 1);
                                }
                            }
                        }
                    }
                }

                panel.showTextBox = true;
                return panel;
            }

            protected void onPostExecute(TalkInfoCollection result) {
                if (result == null) {
                    Toast.makeText(MainActivity.this,
                            R.string.parsing_pla_file_failed, Toast.LENGTH_LONG)
                            .show();
                } else {
                    mPanel = result;
                    showPanel(mPanel, null);
                }

            }

        }.execute();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_DATA_CHECK) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success

            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent
                        .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        } else if (requestCode == REQUEST_CODE_PREFERENCES) {
            mCurrentPlaFilename = null;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentPlaFile", mCurrentPlaFilename);
        outState.putStringArrayList("parentStack", mParentStack);
    }

    @Override
    public void onLoadComplete(SoundPool sp, int id, int status) {
        Log.v("open " + id + " " + status);

        if (mPendingSound == id) {
            sp.play(id, 1, 1, 0, 0, 1);
        }

    }

    @Override
    public void onButtonClick(TalkInfoCollection tiCollection, TalkButton tb) {

        TalkInfo talkInfo = tb.mTalkInfo;

        if (talkInfo.text != null) {

            if (talkInfo.child == null || talkInfo.childFilename == null
                    || talkInfo.childFilename.length() == 0) {
                mText.getText().append(talkInfo.text + " ");
                mTextStack.add(talkInfo);
                TalkButton talkButton = (TalkButton) getLayoutInflater().inflate(R.layout.talkbutton,
                        null);
                talkButton.setTalkInfo(talkInfo);
                talkButton.updateData(mPlaRootDir);
                mTextAsImages.addView(talkButton);
            }

            if (!mPlayControlOnly) {
                if (talkInfo.isTextWav()) {
                    final String fullFilePath = mPlaRootDir + "/"
                            + talkInfo.text;
                    Log.v("open " + fullFilePath);

                    if (talkInfo.soundId > 0) {
                        int soundId = mSoundPool.play(talkInfo.soundId, 1, 1,
                                0, 0, 1);
                        if (soundId == 0) {

                            soundId = mSoundPool.load(fullFilePath, 1);
                            talkInfo.soundId = soundId;
                            mPendingSound = soundId;
                        }
                    } else {
                        final int soundId = mSoundPool.load(fullFilePath, 1);
                        talkInfo.soundId = soundId;
                        mPendingSound = soundId;
                    }

                } else {
                    HashMap<String, String> params = new HashMap<>();

                    try {
                        ((PlaphoonsApplication) getApplication()).mTts.speak(
                                talkInfo.text, mQueueMode, params);
                    } catch (Exception ex) {
                        // missing data, install it
                        Intent installIntent = new Intent();
                        installIntent
                                .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    }
                }
            }
        }

        if (talkInfo.child != null) {
            // handle click on sample
            // TODO cache children and handle here as well
            MainActivity.this.showPanel(talkInfo.child, tiCollection);

        } else if (talkInfo.childFilename != null
                && talkInfo.childFilename.length() > 0) {

            mParentStack.add(mCurrentPlaFilename);
            mCurrentPlaFilename = talkInfo.childFilename;
            openPanel();

        }
    }

    private void home() {
        mCurrentPlaFilename = null;
        openPanel();
    }

    private void playText() {
        HashMap<String, String> params = new HashMap<String, String>();

        try {
            ((PlaphoonsApplication) getApplication()).mTts.speak(mText
                    .getText().toString(), mQueueMode, params);
        } catch (Exception ex) {
            // missing data, install it
            Intent installIntent = new Intent();
            installIntent
                    .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
        if (mPrintText && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            printText();
        }
    }

    private void printText() {
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.v("page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        // Generate an HTML document on the fly:
        StringBuilder htmlDocument = new StringBuilder("<html><body><div style=\"overflow:auto\">");

        for (int i = 0; i < mTextAsImages.getChildCount(); i++) {
            TalkButton btn = (TalkButton) mTextAsImages.getChildAt(i);
            htmlDocument.append("<div style=\"height:150px;width:150px;float:left;\">");
            htmlDocument.append("<img style=\"max-height:100%;max-width:100%;\" src=\"file://")
                    .append(mPlaRootDir).append("/").append(btn.mTalkInfo.drawablePath).append("\"/>");
            htmlDocument.append("</div>");

        }
        htmlDocument.append("</div><p>").append(mText.getText()).append("</p>");
        htmlDocument.append("</body></html>");
        webView.loadDataWithBaseURL(mPlaRootDir, htmlDocument.toString(), "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager)
                getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        mPrintJobs.add(printJob);
    }

    private void deleteText() {
        mText.setText("");
        mTextAsImages.removeAllViews();
        mTextStack.clear();
    }

}
