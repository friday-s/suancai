package com.xue.atk.view;

import javax.swing.ImageIcon;

import javax.swing.JLabel;

public class CButton extends JLabel {

    private static final int WIDGET_WIDTH = 26;
    private static final int WIDGET_HEIGHT = 26;

    private int mWidth;
    private int mHeight;

    private ImageIcon mIconUp;
    private ImageIcon mIconDown;

    private String mUpStr;
    private String mDownStr;

    private boolean isNormal;

    public CButton(ImageIcon iconUp, ImageIcon iconDown) {

        this.setIcon(iconUp);
        this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
        this.mIconUp = iconUp;
        this.mIconDown = iconDown;
    }

    public CButton(String up, String down) {
        this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
        this.mUpStr = up;
        this.mDownStr = down;
        isNormal = true;
    }

    public void pressDown() {
        if (isNormal) {
            this.setText(mDownStr);
        } else {
            this.setIcon(mIconDown);
        }
    }

    public void pressUp() {
        if (isNormal) {
            this.setText(mUpStr);
        } else {
            this.setIcon(mIconUp);
        }
    }

}
