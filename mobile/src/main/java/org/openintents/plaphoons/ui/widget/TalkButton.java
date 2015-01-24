package org.openintents.plaphoons.ui.widget;

import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.sample.R;

import com.htc.pen.PenEvent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TalkButton extends LinearLayout {

	public static interface ClickHandler {

		public void onButtonClick(TalkInfoCollection tiCollection, TalkButton b);

	}

	public TalkInfo mTalkInfo;

	public TalkButton(Context context, TalkInfo talkInfo, String plaRootDir) {
		super(context);
		setTalkInfo(talkInfo);
		updateData(plaRootDir);

	}

	public TalkButton(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
	}

	public TalkButton(Context ctx) {
		super(ctx);
	}

	public void setTalkInfo(TalkInfo talkInfo) {
		mTalkInfo = talkInfo;

	}

	public void updateData(String plaRootDir) {
		// Log.v("updating " + mTalkInfo.title);
		TextView t = (TextView) findViewById(R.id.button_text);
		t.setText(mTalkInfo.title);

		ImageView i = (ImageView) findViewById(R.id.button_image);

		if (mTalkInfo.drawablePath != null
				&& mTalkInfo.drawablePath.length() > 0) {
			Bitmap bm = getBitmap(plaRootDir + "/" + mTalkInfo.drawablePath);
			i.setImageBitmap(bm);
		} else if (mTalkInfo.drawable != 0) {
			Bitmap bm = getBitmap(mTalkInfo.drawable);
			i.setImageBitmap(bm);
		} else {
			i.setImageResource(0);
		}

		if (mTalkInfo.color == Color.BLACK) {
			setBackgroundResource(R.drawable.picture_frame_black);
		} else if (mTalkInfo.color == Color.MAGENTA) {
			setBackgroundResource(R.drawable.picture_frame_magenta);
		} else if (mTalkInfo.color == Color.YELLOW) {
			setBackgroundResource(R.drawable.picture_frame_yellow);
		} else if (mTalkInfo.color == Color.GREEN) {
			setBackgroundResource(R.drawable.picture_frame_green);
		} else if (mTalkInfo.color == Color.rgb(255, 165, 0)) {
			setBackgroundResource(R.drawable.picture_frame_orange);
		} else if (mTalkInfo.color == Color.BLUE) {
			setBackgroundResource(R.drawable.picture_frame_blue);
		} else if (mTalkInfo.color == Color.WHITE) {
			setBackgroundResource(R.drawable.picture_frame_white);
		} else {
			setBackgroundColor(mTalkInfo.color);
		}

	}

	private Bitmap getBitmap(int drawable) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();

		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable,
				bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / 150.0f);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / 150.0f);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeResource(getResources(), drawable,
				bmpFactoryOptions);
		return bitmap;
	}

	private Bitmap getBitmap(String filepath) {

		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();

		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filepath, bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / 150.0f);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / 150.0f);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(filepath, bmpFactoryOptions);
		return bitmap;
	}

	public void setClickHandler(boolean mHasDigitizer,
			final TalkInfoCollection tiCollection,
			final ClickHandler clickHandler) {

		if (mHasDigitizer) {
			setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					// handle pen event:

					if (PenEvent.isPenEvent(event)) {
						int action = PenEvent.PenAction(event);
						int button = PenEvent.PenButton(event);

						switch (action) {
						case PenEvent.PEN_ACTION_DOWN:
							clickHandler.onButtonClick(tiCollection,
									TalkButton.this);
							break;
						case PenEvent.PEN_ACTION_UP:

							break;
						case PenEvent.PEN_ACTION_MOVE:

							break;

						}

					} else {
						// handle touch event
					}
					return true;
				}
			});

		} else {
			setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					clickHandler.onButtonClick(tiCollection, TalkButton.this);
				}
			});
		}

	}
}
