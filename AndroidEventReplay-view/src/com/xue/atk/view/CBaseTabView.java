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

    public CBaseTabView(String tabName) {
        super(tabName);
        setLayout(new GridLayout());
        initUI();
    }

    public void initUI() {
        mLeftPanl = new JPanel();
        mCenterPanl = new JPanel();
        mRightPanl = new JPanel();

        mLeftPanl.setBackground(Color.WHITE);
        mCenterPanl.setBackground(Color.WHITE);
        mRightPanl.setBackground(Color.WHITE);

        mLeftPanl.setPreferredSize(new Dimension(ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT));

        mCenterPanl.setPreferredSize(new Dimension(ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT));

        mRightPanl.setPreferredSize(new Dimension(ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT));

       this.add(mLeftPanl);
       this.add(mCenterPanl);
       this.add(mRightPanl);

    }
}
