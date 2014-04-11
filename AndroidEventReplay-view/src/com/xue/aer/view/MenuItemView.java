package com.xue.aer.view;

import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.xue.aer.util.Util;

public class MenuItemView extends JLayeredPane {

    private static final int BACK_BTN_WIDTH = 50;
    private static final int BACK_BTN_HEIGHT = 50;

    private JLabel bgLabel;

    private CButton back;

    /* default size */
    protected int mWidth = 400;
    protected int mHeight = 300;

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

        back = new CButton(Util.getImageIcon("right_normal.png"),
                Util.getImageIcon("right_click.png"));
        back.setBounds(0, 0, BACK_BTN_WIDTH, BACK_BTN_HEIGHT);

        this.add(back, JLayeredPane.PALETTE_LAYER);

    }

    public void addContent(JPanel panel) {
        panel.setSize(mWidth, mHeight-BACK_BTN_HEIGHT-3);
        panel.setLocation(3, BACK_BTN_HEIGHT);
        this.add(panel, JLayeredPane.PALETTE_LAYER);
       
    }
    
    public void addMouseListener(MouseListener l){
        back.addMouseListener(l);
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
