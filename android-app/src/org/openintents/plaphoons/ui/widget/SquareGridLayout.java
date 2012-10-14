package org.openintents.plaphoons.ui.widget;

import org.openintents.plaphoons.sample.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that arranges views into a grid of same-sized squares.
 * 
 * This source code contained in this file is in the Public Domain.
 * 
 * @author Tom Gibara
 * 
 */

public class SquareGridLayout extends ViewGroup {

	// fields

	/**
	 * Records the number of views on each side of the square (ie. the number of
	 * rows and columns)
	 */

	private int mSize = 1;

	/**
	 * Records the size of the square in pixels (excluding padding). This is set
	 * during {@link #onMeasure(int, int)}
	 */

	private int mSquareDimensions;

	private int mColumns = 6;

	private int mRows = 4;

	private int mDivisor;

	// constructors

	/**
	 * Constructor used to create layout programatically.
	 */

	public SquareGridLayout(Context context) {
		super(context);
	}

	/**
	 * Constructor used to inflate layout from XML. It extracts the size from
	 * the attributes and sets it.
	 */

	/*
	 * This requires a resource to be defined like this:
	 * 
	 * <resources> <declare-styleable name="SquareGridLayout"> <attr name="size"
	 * format="integer"/> </declare-styleable> </resources>
	 * 
	 * So that the attribute can be set like this:
	 * 
	 * <com.tomgibara.android.util.SquareGridLayout
	 * xmlns:android="http://schemas.android.com/apk/res/android" xmlns:util=
	 * "http://schemas.android.com/apk/res/com.tomgibara.android.background"
	 * util:size="3" />
	 */

	public SquareGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.SquareGridLayout);
		setSize(a.getInt(R.styleable.SquareGridLayout_size, 1));
		a.recycle();
	}

	// accessors

	/**
	 * Sets the number of views on each side of the square.
	 * 
	 * @param size
	 *            the size of grid (at least 1)
	 */

	public void setSize(int size) {
		if (size < 1)
			throw new IllegalArgumentException("size must be positive");
		if (mSize != size) {
			mSize = size;
			requestLayout();
		}
	}

	// View methods

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// breakdown specs
		final int mw = MeasureSpec.getMode(widthMeasureSpec);
		final int mh = MeasureSpec.getMode(heightMeasureSpec);
		final int sw = MeasureSpec.getSize(widthMeasureSpec);
		final int sh = MeasureSpec.getSize(heightMeasureSpec);

		// compute padding
		final int pw = getPaddingLeft() + getPaddingRight();
		final int ph = getPaddingTop() + getPaddingBottom();

		// compute largest size of square (both with and without padding)
		final int s;
		final int sp;
		final int d;
		if (mw == MeasureSpec.UNSPECIFIED && mh == MeasureSpec.UNSPECIFIED) {
			throw new IllegalArgumentException(
					"Layout must be constrained on at least one axis");
		} else if (mw == MeasureSpec.UNSPECIFIED) {
			s = sh;
			sp = s - ph;
			d = mRows;
		} else if (mh == MeasureSpec.UNSPECIFIED) {
			s = sw;
			sp = s - pw;
			d = mColumns;
		} else {
			if ((sw - pw) / mColumns < (sh - ph) / mRows) {
				s = sw;
				sp = s - pw;
				d = mColumns;
			} else {
				s = sh;
				sp = s - ph;
				d = mRows;
			}
		}

		// guard against giving the children a -ve measure spec due to excessive
		// padding
		final int spp = Math.max(sp, 0);

		int spec = MeasureSpec.makeMeasureSpec(spp / d - 10,
				MeasureSpec.EXACTLY);
		// pass on our rigid dimensions to our children
		final int size = mSize;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mColumns; x++) {
				final View child = getChildAt(y * mColumns + x);
				if (child == null)
					continue;
				// measure each child
				// we could try to accommodate oversized children, but we don't
				measureChildWithMargins(child, spec, 0, spec, 0);
			}
		}

		// record our dimensions
		setMeasuredDimension(mw == MeasureSpec.EXACTLY ? sw : (sp / d) * mRows
				+ pw, mh == MeasureSpec.EXACTLY ? sh : (sp / d) * mColumns + ph);
		mSquareDimensions = sp;
		mDivisor = d;
	}

	// ViewGroup methods

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		// recover the previously computed square dimensions for efficiency
		final int s = mSquareDimensions;
		final int d = mDivisor;

		{
			// adjust for our padding
			final int pl = getPaddingLeft();
			final int pt = getPaddingTop();
			final int pr = getPaddingRight();
			final int pb = getPaddingBottom();

			// allocate any extra spare space evenly
			l = pl + (r - pr - l - pl - ((s / d) * mColumns)) / 2;
			t = pt + (b - pb - t - pb - ((s / d) * mRows)) / 2;
		}

		final int size = mSize;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mColumns; x++) {
				View child = getChildAt(y * mColumns + x);
				// optimization: we are moving through the children in order
				// when we hit null, there are no more children to layout so
				// return
				if (child == null)
					return;
				// get the child's layout parameters so that we can honour their
				// margins
				MarginLayoutParams lps = (MarginLayoutParams) child
						.getLayoutParams();
				// we don't support gravity, so the arithmetic is simplified
				child.layout(l + (s * x) / d + 5, t + (s * y) / d + 5, l
						+ (s * (x + 1)) / d - 5, t + (s * (y + 1)) / d - 5);
			}
		}
	}

	public void setSize(int rows, int columns) {
		mRows = rows + 1;
		mColumns = columns;
		invalidate();

	}

}
