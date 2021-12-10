package com.u21.a0903_onlinemusic.view;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LRCTextView extends RelativeLayout {
	private TextView tvDefault;
	private TextView tvSelect;
	private String lrc = "";

	/**
	 *
	 * 
	 * @param lrc
	 */
	public void setLrc(String lrc) {
		this.lrc = lrc;
		tvDefault.setText(lrc);
		tvSelect.setText(lrc);
	}

	public LRCTextView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
		super(context, attrs, defStyleAttr);
		init();
	}

	public LRCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LRCTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LRCTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
		tvDefault = new TextView(getContext());
		tvDefault.setText(lrc);
		tvDefault.setTextColor(Color.parseColor("#9e9e9e"));
		tvDefault.setEllipsize(null);
		tvDefault.setSingleLine();
		tvDefault.setTextSize(16);

		tvSelect = new TextView(getContext());
		tvSelect.setTextColor(Color.parseColor("#000000"));
		tvSelect.setText(lrc);
		tvSelect.setEllipsize(null);
		tvSelect.setSingleLine();
		tvSelect.setTextSize(16);
		addView(tvDefault);
		addView(tvSelect);
		tvSelect.setWidth(0);
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if (visibility == View.VISIBLE) {
			postDelayed(new Runnable() {
				@Override
				public void run() {
					setPercent(percent);
				}
			}, 10);
		}
	}

	private float percent;

	/**
	 *
	 * 
	 * @param percent
	 */
	public void setPercent(float percent) {
		this.percent = percent;
		setSelectWidth((int) (getSelectWidth() * percent));
	}

	private int getSelectWidth() {
		return tvDefault.getWidth();
	}

	private void setSelectWidth(int pixels) {
		if (pixels <= getSelectWidth()) {
			tvSelect.setWidth(pixels);
		}
	}
}
