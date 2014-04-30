package com.xue.atk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.xue.atk.res.ATK;

public class CBaseTabView extends TabView {

    protected JPanel mLeftPanel;
    protected JPanel mCenterPanel;
    protected JPanel mRightPanel;

    protected boolean focusable = true;
    
    public CBaseTabView(String tabName) {
        super(tabName);
        setLayout(null);
        initUI();
    }

    public void initUI() {

        mLeftPanel = new JPanel();
        mCenterPanel = new JPanel();
        mRightPanel = new JPanel();

        mLeftPanel.setBackground(Color.WHITE);
        mCenterPanel.setBackground(Color.WHITE);
        mRightPanel.setBackground(Color.WHITE);


        mLeftPanel.setBounds(0, 0, ATK.BASE_TAB_VIEW_WIDTH, ATK.BASE_TAB_VIEW_HEIGHT);
        mCenterPanel.setBounds(ATK.BASE_TAB_VIEW_WIDTH, 0, ATK.BASE_TAB_VIEW_WIDTH,
                ATK.BASE_TAB_VIEW_HEIGHT);
        mRightPanel.setBounds(ATK.BASE_TAB_VIEW_WIDTH * 2, 0, ATK.BASE_TAB_VIEW_WIDTH,
                ATK.BASE_TAB_VIEW_HEIGHT);

        this.add(mLeftPanel);
        this.add(mCenterPanel);
        this.add(mRightPanel);

    }
    
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
    }
}
