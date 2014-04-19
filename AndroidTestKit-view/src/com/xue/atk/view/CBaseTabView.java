package com.xue.atk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.xue.atk.res.ATK;

public class CBaseTabView extends TabView {

    protected JPanel mLeftPanl;
    protected JPanel mCenterPanl;
    protected JPanel mRightPanl;

    protected boolean focusable = true;
    
    public CBaseTabView(String tabName) {
        super(tabName);
        setLayout(null);
        initUI();
    }

    public void initUI() {

        mLeftPanl = new JPanel();
        mCenterPanl = new JPanel();
        mRightPanl = new JPanel();

        mLeftPanl.setBackground(Color.WHITE);
        mCenterPanl.setBackground(Color.WHITE);
        mRightPanl.setBackground(Color.WHITE);


        mLeftPanl.setBounds(0, 0, ATK.BASE_TAB_VIEW_WIDTH, ATK.BASE_TAB_VIEW_HEIGHT);
        mCenterPanl.setBounds(ATK.BASE_TAB_VIEW_WIDTH, 0, ATK.BASE_TAB_VIEW_WIDTH,
                ATK.BASE_TAB_VIEW_HEIGHT);
        mRightPanl.setBounds(ATK.BASE_TAB_VIEW_WIDTH * 2, 0, ATK.BASE_TAB_VIEW_WIDTH,
                ATK.BASE_TAB_VIEW_HEIGHT);

        this.add(mLeftPanl);
        this.add(mCenterPanl);
        this.add(mRightPanl);

    }
    
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
    }
}
