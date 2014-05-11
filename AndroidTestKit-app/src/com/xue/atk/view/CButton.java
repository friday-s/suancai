package com.xue.atk.view;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CButton extends JButton {

    public CButton(ImageIcon iconUp, ImageIcon iconDown) {
        this();
        setIconDrawable(iconUp, iconDown);
    }

    public CButton() {
        super();
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
    }

    public void setIconDrawable(ImageIcon iconUp, ImageIcon iconDown) {
        this.setIcon(iconUp);
        this.setPressedIcon(iconDown);
    }

}
