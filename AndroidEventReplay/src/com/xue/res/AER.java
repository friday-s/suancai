package com.xue.res;

import java.awt.Toolkit;

public class AER {
    
    public static final int SCREEN_WIDTH  = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    public static final int LOCATION_X = (SCREEN_WIDTH -WIDTH)/2;
    public static final int LOCATION_Y = (int) (SCREEN_HEIGHT*0.15);

    public static final int TITLE_BAR_WIDTH = WIDTH;
    public static final int TITLE_BAR_HEIGHT = 25;
    
    public static final int TOOL_BAR_WIDTH = WIDTH;
    public static final int TOOL_BAR_HEIGHT = 26;
    
    public static final int TAB_HOST_WIDTH = WIDTH;
    public static final int TAB_HOST_HEIGHT = HEIGHT-TITLE_BAR_HEIGHT-TOOL_BAR_HEIGHT;
    public static final int TAB_HOST_LOCATION_X = 0;
    public static final int TAB_HOST_LOCATION_Y = TITLE_BAR_HEIGHT+TOOL_BAR_HEIGHT;
    
    public static final int TAB_BAR_WIDTH = WIDTH;
    public static final int TAB_BAR_HEIGHT = 50;
    
    public static final int TAB_VIEW_WIDTH = WIDTH;
    public static final int TAB_VIEW_HEIGHT = TAB_HOST_HEIGHT-TAB_BAR_HEIGHT;
    

    public static final String APP_NAME = "Android Event Replay";
    public static final String APP_NAME_SHORT = "AER";

    public static final String[] MENU_BAR = { "File","Settings", "Help" };

    public static final String[] TABBED_PANE = { "record", "replay" };
    
    public static final String EXIT_CONTENT="Do you want to quit?";
    
}
