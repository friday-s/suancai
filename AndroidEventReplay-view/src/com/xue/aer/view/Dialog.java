package com.xue.aer.view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;

public class Dialog extends JFrame {

    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 350;

    private int mDialogWidth = DEFAULT_WIDTH;
    private int mDialogHeight = DEFAULT_HEIGHT;

    protected JFrame mContext;
    private JLayeredPane layeredPane;

    private JLabel background;
    private ImageIcon backgroundIcon;

    public Dialog(JFrame owner) {
        this.mContext = owner;
        initPanel();

    }

    public Dialog(JFrame owner, int width, int height) {
        this.mContext = owner;
        this.mDialogWidth = width;
        this.mDialogHeight = height;
        initPanel();
    }

    private void initPanel() {
        this.setUndecorated(true);

        this.setBackground(new Color(0, 0, 0, 0));
        // this.setLocationRelativeTo(mContext);
        this.setSize(mDialogWidth, mDialogHeight);
        this.setLocation(mContext.getX() + (AER.WIDTH - mDialogWidth) / 2, mContext.getY()
                + (AER.HEIGHT - mDialogHeight) / 2);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setBounds(0, 0, mDialogWidth, mDialogHeight);

        this.add(layeredPane);

        background = new JLabel();
        backgroundIcon = Util.getImageIcon("dlg_bg.png");

        background.setIcon(scaleImage(backgroundIcon,mDialogWidth,mDialogHeight));
        background.setBounds(0, 0, mDialogWidth, mDialogHeight);

        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);

    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (width != DEFAULT_WIDTH || height != DEFAULT_HEIGHT) {
            icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            System.out.println("mDialogWidth:" + width);
            System.out.println("mDialogHeight:" + height);
        }
        return icon;
    }

    public void setViewBounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
       // background.setBounds(0, 0, width, height);

    }

    public void packBackground() {
        backgroundIcon = Util.getImageIcon("dlg_bg.png");
        background.setIcon(scaleImage(backgroundIcon,mDialogWidth+5,mDialogHeight));
        background.setBounds(0, 0, mDialogWidth, mDialogHeight);
    }

    public int getDialogWidth() {
        return mDialogWidth;
    }

    public void setDialogWidth(int mDialogWidth) {
        this.mDialogWidth = mDialogWidth;
    }

    public int getDialogHeight() {
        return mDialogHeight;
    }

    public void setDialogHeight(int mDialogHeight) {
        this.mDialogHeight = mDialogHeight;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

}
