package com.xue.atk.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CButton extends JLabel {

	private static final int WIDGET_WIDTH = 30;
	private static final int WIDGET_HEIGHT = 30;

	private int mWidth;
	private int mHeight;



	private ImageIcon mIconUp;
	private ImageIcon mIconDown;

	private String mUpStr;
	private String mDownStr;

	private boolean isNormal;

	private int mTextSize = 12;
	private int mTextStyle = 0;

	private boolean enable = true;

	public CButton(ImageIcon iconUp, ImageIcon iconDown) {

		this.setIcon(iconUp);
		this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
		this.mIconUp = iconUp;
		this.mIconDown = iconDown;
	}

	public CButton(String up, String down) {
		this.setText(up);
		this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
		this.mUpStr = up;
		this.mDownStr = down;
		isNormal = true;
	}

	public CButton(String up, String down, int size) {
		this.setText(up);
		this.setFont(new Font(null, mTextStyle, size));
		this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
		this.mUpStr = up;
		this.mDownStr = down;
		isNormal = true;
	}

	public void setFontSize(int size) {
		this.mTextSize = size;
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.enable = enabled;
	}

	public boolean getEnabled() {
		return enable;
	}

	public void pressDown() {
		if (!enable) {
			return;
		}

		if (isNormal) {
			this.setText(mDownStr);
			this.setFont(new Font(mDownStr, mTextStyle, mTextSize));
		} else {
			this.setIcon(mIconDown);
		}
	}

	public void pressUp() {
		if (!enable) {
			return;
		}
		if (isNormal) {
			this.setText(mUpStr);
			this.setFont(new Font(mUpStr, mTextStyle, mTextSize));
		} else {
			this.setIcon(mIconUp);
		}
	}
	
	public ImageIcon getIconUp() {
		return mIconUp;
	}

	public void setIconUp(ImageIcon mIconUp) {
		this.mIconUp = mIconUp;
	}

	public ImageIcon getIconDown() {
		return mIconDown;
	}

	public void setIconDown(ImageIcon mIconDown) {
		this.mIconDown = mIconDown;
	}

}
