package com.xue.atk.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;

import com.xue.atk.replay.ReplayView;
import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class MainFrame extends JFrame {

    // public JPanel contentPane;

    public JLayeredPane layeredPane;

    /**
     * Create the frame.
     */
    public MainFrame() {
        layeredPane = new JLayeredPane();

        setUndecorated(true);

        // contentPane = new JPanel();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        layeredPane.setLayout(null);

        setLayeredPane(layeredPane);

        TitleBar titleBar = new TitleBar(this);
        titleBar.setBounds(0, 0, ATK.TITLE_BAR_WIDTH, ATK.TITLE_BAR_HEIGHT);
        titleBar.setBackground(Color.WHITE);
        layeredPane.add(titleBar, JLayeredPane.DEFAULT_LAYER);

        MenuBar menuBar = new MenuBar(this);
        menuBar.setBounds(0, ATK.TITLE_BAR_HEIGHT, ATK.TOOL_BAR_WIDTH, ATK.TOOL_BAR_HEIGHT);
        menuBar.setBackground(Color.WHITE);
        layeredPane.add(menuBar, JLayeredPane.DEFAULT_LAYER);

        CButton info = new CButton(Util.getImageIcon("about_up_24.png"),
                Util.getImageIcon("about_down_24.png"));
        SoftwareInfoView infoView = new SoftwareInfoView();
        MenuItem infoItem = new MenuItem(this, info, infoView);
        menuBar.addMenuItem(infoItem);

        CButton settings = new CButton(Util.getImageIcon("settings_up_25.png"),
                Util.getImageIcon("settings_down_25.png"));
        MenuItemView settingsView = new MenuItemView();
        MenuItem settingsItem = new MenuItem(this, settings, settingsView);
        menuBar.addMenuItem(settingsItem);

        TabHost tabHost = new TabHost(this);
        tabHost.setBounds(ATK.TAB_HOST_LOCATION_X, ATK.TAB_HOST_LOCATION_Y, ATK.TAB_HOST_WIDTH,
                ATK.TAB_HOST_HEIGHT);
        layeredPane.add(tabHost, JLayeredPane.DEFAULT_LAYER);

        ReplayView recordView = new ReplayView("Replay");
        recordView.setBackground(Color.GRAY);
        tabHost.addTab(recordView);
        

        CBaseTabView view2 = new CBaseTabView("ScreenMonitor");
        view2.mCenterPanel.setLayout(null);
        JLabel label2 = new JLabel("Stay Tuned");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setFont(new Font(null,1,20));
        label2.setForeground(Color.RED);
        label2.setBounds(0, 0, view2.mCenterPanel.getWidth(), view2.mCenterPanel.getHeight());
        view2.mCenterPanel.add(label2);
        tabHost.addTab(view2);
        
        CBaseTabView view3 = new CBaseTabView("MemoryMonitor");
        view3.mCenterPanel.setLayout(null);
        JLabel label3 = new JLabel("Stay Tuned");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setFont(new Font(null,1,20));
        label3.setForeground(Color.RED);
        label3.setBounds(0, 0, view3.mCenterPanel.getWidth(), view3.mCenterPanel.getHeight());
        view3.mCenterPanel.add(label3);
        tabHost.addTab(view3);
        
        CBaseTabView view4 = new CBaseTabView("More");
        view4.mCenterPanel.setLayout(null);
        JLabel label4 = new JLabel("Stay Tuned");
        label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setFont(new Font(null,1,20));
        label4.setForeground(Color.RED);
        label4.setBounds(0, 0, view4.mCenterPanel.getWidth(), view4.mCenterPanel.getHeight());
        view4.mCenterPanel.add(label4);
        tabHost.addTab(view4);

        BottomBar bottomBar = new BottomBar();
        bottomBar.setBounds(ATK.BOTTOM_BAR_X, ATK.BOTTOM_BAR_Y, ATK.BOTTOM_BAR_WIDTH,
                ATK.BOTTOM_BAR_HEIGHT);
        bottomBar.setBackground(Color.WHITE);
        layeredPane.add(bottomBar, JLayeredPane.DEFAULT_LAYER);

    }
    

    public void setLocation(Point p) {
        super.setLocation(p);

    }

}
