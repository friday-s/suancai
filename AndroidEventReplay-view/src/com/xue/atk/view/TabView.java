package com.xue.atk.view;

import javax.swing.JPanel;

public abstract class TabView extends JPanel {

    private String mTabName;

    public TabView(String tabName) {
        this.mTabName = tabName;
    }

    public String getTabName() {
        return mTabName;
    }

    public void setTabName(String mTabName) {
        this.mTabName = mTabName;
    }

}
