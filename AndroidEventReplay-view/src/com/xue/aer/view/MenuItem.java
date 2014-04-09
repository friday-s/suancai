package com.xue.aer.view;

import javax.swing.JLabel;

public class MenuItem {

    private JLabel mIconLabel;

    private MenuView mToolView;

    public MenuItem(JLabel iconLabel, MenuView toolView) {
        this.mIconLabel = iconLabel;
        this.mToolView = toolView;
    }
    
    public JLabel getIconLabel() {
        return mIconLabel;
    }

    public void setIconLabel(JLabel mIconLabel) {
        this.mIconLabel = mIconLabel;
    }

    public MenuView getToolView() {
        return mToolView;
    }

    public void setToolView(MenuView mToolView) {
        this.mToolView = mToolView;
    }

}
