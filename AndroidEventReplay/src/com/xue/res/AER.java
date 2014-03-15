package com.xue.res;

import java.awt.Toolkit;

public class AER {
    
    public static final int SCREEN_WIDTH  = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    public static final int LOCATION_X = (SCREEN_WIDTH -WIDTH)/2;
    public static final int LOCATION_Y = (int) (SCREEN_HEIGHT*0.15);

    public static final int TAB_ICON_WIDTH = 50;
    public static final int TAB_ICON_HEIGHT = 50;

    public static final String APP_NAME = "Android Event Replay";
    public static final String APP_NAME_SHORT = "AER";

    public static final String[] MENU_BAR = { "File","Settings", "Help" };

    public static final String[] TABBED_PANE = { "record", "replay" };
    
    public static final String EXIT_CONTENT="Do you want to quit?";
    
}
