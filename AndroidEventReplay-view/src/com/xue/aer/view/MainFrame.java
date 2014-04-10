package com.xue.aer.view;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import com.xue.aer.res.AER;

public class MainFrame extends JFrame {

    private static final Integer LAYER_ONE = new Integer(200);

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

        setContentPane(layeredPane);

        TitleBar titleBar = new TitleBar(this);
        titleBar.setBounds(0, 0, AER.TITLE_BAR_WIDTH, AER.TITLE_BAR_HEIGHT);
        titleBar.setBackground(Color.WHITE);
        layeredPane.add(titleBar, LAYER_ONE);

        MenuBar toolBar = new MenuBar(this);
        toolBar.setBounds(0, AER.TITLE_BAR_HEIGHT, AER.TOOL_BAR_WIDTH, AER.TOOL_BAR_HEIGHT);
        toolBar.setBackground(Color.WHITE);
        layeredPane.add(toolBar, LAYER_ONE);

        TabHost tabHost = new TabHost();
        tabHost.setBounds(AER.TAB_HOST_LOCATION_X, AER.TAB_HOST_LOCATION_Y, AER.TAB_HOST_WIDTH,
                AER.TAB_HOST_HEIGHT);
        layeredPane.add(tabHost, LAYER_ONE);

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
        layeredPane.add(bottomBar, LAYER_ONE);

    }

    public void setLocation(Point p) {
        super.setLocation(p);

    }

}
