package com.xue.aer.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MenuItemView extends JLayeredPane {

    private JPanel bgPanel;

    private int mMenuItemViewWidth = 450;
    private int mMenuItemViewHeight = 300;

    public MenuItemView() {
        initView();
    }

    private void initView() {
        this.setLayout(null);
        bgPanel = new JPanel();

    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (width != mMenuItemViewWidth || height != mMenuItemViewHeight) {
            icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
        return icon;
    }

}
