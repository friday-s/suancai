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

    public CButton(ImageIcon iconUp, ImageIcon iconDown) {

        this.setIcon(iconUp);
        this.setBounds(0, 0, WIDGET_WIDTH, WIDGET_HEIGHT);
        this.mIconUp = iconUp;
        this.mIconDown = iconDown;
    }

    public CButton(ImageIcon icon, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void pressDown() {
        this.setIcon(mIconDown);
    }

    public void pressUp() {
        this.setIcon(mIconUp);
    }

}
