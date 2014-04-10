package com.xue.aer.view;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;

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
        titleBar.setBounds(0, 0, AER.TITLE_BAR_WIDTH, AER.TITLE_BAR_HEIGHT);
        titleBar.setBackground(Color.WHITE);
        layeredPane.add(titleBar, JLayeredPane.DEFAULT_LAYER);

        MenuBar menuBar = new MenuBar(this);
        menuBar.setBounds(0, AER.TITLE_BAR_HEIGHT, AER.TOOL_BAR_WIDTH, AER.TOOL_BAR_HEIGHT);
        menuBar.setBackground(Color.WHITE);
        layeredPane.add(menuBar, JLayeredPane.DEFAULT_LAYER);
        
        CButton about = new CButton(Util.getImageIcon("about_up_24.png"),Util.getImageIcon("about_down_24.png"));
        MenuItemView aboutView = new MenuItemView();
        MenuItem adoutItem = new MenuItem(this,about,aboutView);
        menuBar.addMenuItem(adoutItem);
        
        CButton settings = new CButton(Util.getImageIcon("settings_up_25.png"),Util.getImageIcon("settings_down_25.png"));
        MenuItemView settingsView = new MenuItemView();
        MenuItem settingsItem = new MenuItem(this,settings,settingsView);
        menuBar.addMenuItem(settingsItem);

        TabHost tabHost = new TabHost();
        tabHost.setBounds(AER.TAB_HOST_LOCATION_X, AER.TAB_HOST_LOCATION_Y, AER.TAB_HOST_WIDTH,
                AER.TAB_HOST_HEIGHT);
        layeredPane.add(tabHost, JLayeredPane.DEFAULT_LAYER);

        RecordView recordView = new RecordView("record");
        recordView.setBackground(Color.GRAY);
        ReplayView replayView = new ReplayView("replay");
        replayView.setBackground(Color.RED);
        ReplayView replayView2 = new ReplayView("More");
        replayView2.setBackground(Color.BLUE);
        tabHost.addTab(recordView);
        tabHost.addTab(replayView);
        tabHost.addTab(replayView2);

        BottomBar bottomBar = new BottomBar();
        bottomBar.setBounds(AER.BOTTOM_BAR_X, AER.BOTTOM_BAR_Y, AER.BOTTOM_BAR_WIDTH,
                AER.BOTTOM_BAR_HEIGHT);
        bottomBar.setBackground(Color.WHITE);
        layeredPane.add(bottomBar, JLayeredPane.DEFAULT_LAYER);

    }
    

    public void setLocation(Point p) {
        super.setLocation(p);

    }

}
