package com.xue.aer.view;

import java.awt.Point;

import javax.swing.JLabel;

import com.xue.aer.event.ViewLocationChangeListener;

public class MenuItem {

    private JLabel mIconLabel;

    private MenuView mMenuView;

    private ViewLocationChangeListener listener;

    public MenuItem(JLabel iconLabel, MenuView menuView) {
        this.mIconLabel = iconLabel;
        this.mMenuView = menuView;
    }

    public JLabel getIconLabel() {
        return mIconLabel;
    }

    public void setIconLabel(JLabel mIconLabel) {
        this.mIconLabel = mIconLabel;
    }

    public MenuView getmenuView() {
        return mMenuView;
    }

    public void setmenuView(MenuView mMenuView) {
        this.mMenuView = mMenuView;
    }

    public void showMenuView() {
        mMenuView.showView();
    }

    public void hideMenuView() {
        mMenuView.hideView();
    }

    public void addViewLocationChangeListener(ViewLocationChangeListener l) {
        this.listener = l;
    }
    
    public void locationChanged(Point p){
        listener.locationChaged(p);
    }

}
