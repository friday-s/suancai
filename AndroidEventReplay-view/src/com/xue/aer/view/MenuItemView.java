package com.xue.aer.view;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.xue.aer.util.Util;

public class MenuItemView extends JLayeredPane {

    private static final int BACK_BTN_WIDTH = 50;
    private static final int BACK_BTN_HEIGHT = 50;

    private JLabel bgLabel;

    private CButton back;

    /* default size */
    private int mWidth = 400;
    private int mHeight = 300;

    public MenuItemView() {
        initView();
    }

    public MenuItemView(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        initView();
    }

    private void initView() {
        this.setLayout(null);
        this.setBounds(0, 0, mWidth, mHeight);

        bgLabel = new JLabel();
        bgLabel.setBounds(0, 0, mWidth, mHeight);
        bgLabel.setIcon(Util.scaleImage(Util.getImageIcon("dlg_bg.png"), mWidth, mHeight));
        this.add(bgLabel, JLayeredPane.DEFAULT_LAYER);

        // back = new JLabel();
        // back.setBounds(0, 0, BACK_BTN_WIDTH, BACK_BTN_HEIGHT);
        // back.setIcon(Util.scaleImage(Util.getImageIcon("right_normal.png"),
        // BACK_BTN_WIDTH,
        // BACK_BTN_HEIGHT));
        // this.add(back, JLayeredPane.PALETTE_LAYER);

        back = new CButton(Util.getImageIcon("right_normal.png"),
                Util.getImageIcon("right_click.png"));
        back.setBounds(0, 0, BACK_BTN_WIDTH, BACK_BTN_HEIGHT);

        this.add(back, JLayeredPane.PALETTE_LAYER);

    }

    public void pressBack() {
        back.pressDown();
    }

    public void releaseBack() {
        back.pressUp();
    }

    public void showView() {
        this.setVisible(true);

    }

    public void hideView() {
        this.setVisible(false);
    }

}
