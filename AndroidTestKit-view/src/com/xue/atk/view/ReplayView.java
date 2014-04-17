package com.xue.atk.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class ReplayView extends CBaseTabView {

    private static final int DIVIDE_LINE_WIDTH = 70;
    private static final int DIVIDE_LINE_HEIGHT = 1;

    private CButton mRecordBtn;
    private CButton mReplayBtn;

    private JLabel mDivideLineLabel;
    private ProgressBar mProgress;

    public ReplayView(String tabName) {
        super(tabName);
        initView();
    }

    public void initView() {

        int beginX = 20;
        int beginY = 20;
        mLeftPanl.setLayout(null);
        for (String s : ATK.RECORD_LEFT_CONTENT) {
            JLabel label = new JLabel(s);
            label.setBounds(beginX, beginY, ATK.RECORD_LABEL_WIDTH, ATK.RECORD_LABEL_HEIGHT);
            // label.setLocation(beginX, beginY);
            mLeftPanl.add(label);
            JTextField text = new JTextField();
            text.setBounds(beginX + ATK.RECORD_LABEL_WIDTH, beginY, ATK.RECORD_LABEL_WIDTH,
                    ATK.RECORD_LABEL_HEIGHT);
            mLeftPanl.add(text);
            beginY += 35;
        }

        initCenterView();
    }

    private void initCenterView() {
        mCenterPanl.setLayout(null);

        JLayeredPane pane = new JLayeredPane();
        pane.setLayout(null);
        pane.setBounds(0, 0, mCenterPanl.getWidth(), mCenterPanl.getHeight());

        mCenterPanl.add(pane);

        mDivideLineLabel = new JLabel();
        ImageIcon divideIcon = Util.scaleImage(Util.getImageIcon("tabbar_select.png"),
                mCenterPanl.getWidth(), DIVIDE_LINE_HEIGHT);
        mDivideLineLabel.setIcon(divideIcon);
        mDivideLineLabel.setBounds((pane.getWidth() - divideIcon.getIconWidth()) / 2,
                (pane.getHeight() - divideIcon.getIconHeight()) / 2, divideIcon.getIconWidth(),
                divideIcon.getIconHeight());

        System.out.println((pane.getHeight() - divideIcon.getIconHeight()) / 2);
        mRecordBtn = new CButton("record", "stop",18);
        mRecordBtn.setBounds(mDivideLineLabel.getX(), mDivideLineLabel.getY() - 30,
                DIVIDE_LINE_WIDTH, 50);

        mReplayBtn = new CButton("replay", "stop",18);
        mReplayBtn.setBounds(mDivideLineLabel.getX(), mDivideLineLabel.getY(), DIVIDE_LINE_WIDTH,
                20);
        
        pane.add(mDivideLineLabel, JLayeredPane.DEFAULT_LAYER);

        pane.add(mRecordBtn, JLayeredPane.PALETTE_LAYER);
        pane.add(mReplayBtn, JLayeredPane.PALETTE_LAYER);

        /* load images */
        ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
        for (int i = 1; i < 13; i++) {
            ImageIcon icon = Util.scaleImage(Util.getImageIcon("progress_bars" + i + ".png"), 25,
                    25);
            icons.add(icon);
        }
        
        mProgress = new ProgressBar();
        mProgress.setProgressImages(icons);
        mProgress.setLocation(mDivideLineLabel.getX()+mDivideLineLabel.getWidth()-25, mDivideLineLabel.getY());
        mProgress.start();

        pane.add(mProgress);
    }

}
