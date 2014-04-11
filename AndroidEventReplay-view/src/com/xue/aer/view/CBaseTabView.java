package com.xue.aer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.xue.aer.res.AER;

public class CBaseTabView extends TabView {

    private JPanel mLeftPanl;
    private JPanel mCenterPanl;
    private JPanel mRightPanl;

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

        mLeftPanl.setPreferredSize(new Dimension(AER.TAB_VIEW_WIDTH, AER.TAB_VIEW_HEIGHT));

        mCenterPanl.setPreferredSize(new Dimension(AER.TAB_VIEW_WIDTH, AER.TAB_VIEW_HEIGHT));

        mRightPanl.setPreferredSize(new Dimension(AER.TAB_VIEW_WIDTH, AER.TAB_VIEW_HEIGHT));

       this.add(mLeftPanl);
       this.add(mCenterPanl);
       this.add(mRightPanl);

    }
}
