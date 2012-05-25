package org.openintents.plaphoons.domain;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class TalkInfo {
	String image;
	Drawable imageDrawable;
	public String title;
	public String text;
	String link;
	public int color;
	Typeface font;
	
	public TalkInfoCollection child;
	public String childFilename;

	public int drawable;
	public String drawablePath;
	public int soundId;

	public TalkInfo(String title) {
		this(title, title, Color.BLACK, null, 0);
	}

	public TalkInfo(String title, String text) {
		this(title, text, Color.BLACK, null, 0);

	}

	public TalkInfo() {
		this(null, null, Color.BLACK, null, 0);
	}

	public TalkInfo(String title, int color) {
		this(title, title, color, null, 0);

	}

	public TalkInfo(String title, int color, TalkInfoCollection personnes) {
		this(title, title, color, personnes, 0);

	}

	public TalkInfo(String title, String text, int color) {
		this(title, text, color, null, 0);
	}

	public TalkInfo(String title, int color, int drawable) {
		this(title, title, color, null, drawable);
	}

	public TalkInfo(String title, String text, int color,
			TalkInfoCollection child) {
		this(title, text, color, child, 0);
	}

	public TalkInfo(String title, String text, int color,
			TalkInfoCollection child, int drawable) {
		this(title, text, color, child, drawable, null);
	}

	public TalkInfo(String title, String text, int color,
			TalkInfoCollection child, int drawable, String drawablePath) {
		this.title = title;
		this.text = text;
		this.color = color;
		this.child = child;
		this.drawable = drawable;
		this.drawablePath = drawablePath;
	}

	public boolean isTextWav() {
		return text.endsWith(".wav");
	}

}
