package com.xue.aer.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;

public class MenuBar extends JPanel implements MouseListener {

    private static final int TOOL_BAR_OPTION_WIDTH = 26;
    private static final int TOOL_BAR_OPTION_HEIGHT = 26;

    private JLabel mAboutLabel;
    private JLabel mSettingsLabel;

    private JFrame mContext;

    private MenuView2 softwareInfoView;

    public MenuBar(JFrame jframe) {
        // setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        mContext = jframe;
        setLayout(null);
        initOptions();
    }

    private void initOptions() {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        mAboutLabel = new JLabel();
        // mAboutLabel.setBounds(0, 0, TOOL_BAR_OPTION_HEIGHT,
        // TOOL_BAR_OPTION_HEIGHT);
        mAboutLabel.setIcon(Util.getImageIcon("about_up_24.png"));
        mAboutLabel.setBackground(Color.WHITE);
        mAboutLabel.addMouseListener(this);
        this.add(mAboutLabel);

        mSettingsLabel = new JLabel();
        // mSettingsLabel.setBounds(0, 0, TOOL_BAR_OPTION_HEIGHT,
        // TOOL_BAR_OPTION_HEIGHT);
        mSettingsLabel.setIcon(Util.getImageIcon("settings_up_25.png"));
        mSettingsLabel.setBackground(Color.WHITE);
        mSettingsLabel.addMouseListener(this);
        this.add(mSettingsLabel);

        springLayout.putConstraint(SpringLayout.EAST, mAboutLabel, -3, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.EAST, mSettingsLabel, -5, SpringLayout.WEST,
                mAboutLabel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // System.out.println("mouseClicked");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("mousePressed");
        if (e.getSource() == mAboutLabel) {
            mAboutLabel.setIcon(Util.getImageIcon("about_down_24.png"));
            
        }
        if (e.getSource() == mSettingsLabel) {
            mSettingsLabel.setIcon(Util.getImageIcon("settings_down_25.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("mouseReleased");
        if (e.getSource() == mAboutLabel) {
            mAboutLabel.setIcon(Util.getImageIcon("about_up_24.png"));
        }
        if (e.getSource() == mSettingsLabel) {
            mSettingsLabel.setIcon(Util.getImageIcon("settings_up_25.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("mouseExited");
    }

}
