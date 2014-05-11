package com.xue.atk.res;

import java.awt.Toolkit;

public class ATK {

    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
            .getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize()
            .getHeight();

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    public static final int LOCATION_X = (SCREEN_WIDTH - WIDTH) / 2;
    public static final int LOCATION_Y = (int) (SCREEN_HEIGHT * 0.15);

    public static final int TITLE_BAR_WIDTH = WIDTH;
    public static final int TITLE_BAR_HEIGHT = 25;

    public static final int TOOL_BAR_WIDTH = WIDTH;
    public static final int TOOL_BAR_HEIGHT = 26;

    public static final int TOOL_VIEW_WIDTH = 350;
    public static final int TOOL_VIEW_HEIGHT = HEIGHT / 2;

    public static final int BOTTOM_BAR_WIDTH = WIDTH;
    public static final int BOTTOM_BAR_HEIGHT = 20;
    public static final int BOTTOM_BAR_X = 0;
    public static final int BOTTOM_BAR_Y = HEIGHT - BOTTOM_BAR_HEIGHT;

    public static final int TAB_HOST_WIDTH = WIDTH;
    public static final int TAB_HOST_HEIGHT = HEIGHT - TITLE_BAR_HEIGHT - TOOL_BAR_HEIGHT- BOTTOM_BAR_HEIGHT;
    public static final int TAB_HOST_LOCATION_X = 0;
    public static final int TAB_HOST_LOCATION_Y = TITLE_BAR_HEIGHT + TOOL_BAR_HEIGHT;

    public static final int TAB_BAR_WIDTH = WIDTH;
    public static final int TAB_BAR_HEIGHT = 40;

    public static final int TAB_VIEW_WIDTH = WIDTH+2;
    public static final int TAB_VIEW_HEIGHT = TAB_HOST_HEIGHT - TAB_BAR_HEIGHT;
    public static final int TAB_VIEW_X = 0;
    public static final int TAB_VIEW_Y = TAB_BAR_HEIGHT + 1;
    
    public static final int SOFTWARE_INFO_VIEW_WIDTH = 300;
    public static final int SOFTWARE_INFO_VIEW_HEIGHT = 200;
    
    public static final int RECORD_LABEL_WIDTH = 100;
    public static final int RECORD_LABEL_HEIGHT = 30;
    
    public static final int BASE_TAB_VIEW_WIDTH = WIDTH/3+1;
    public static final int BASE_TAB_VIEW_HEIGHT = TAB_VIEW_HEIGHT;

    public static final String APP_NAME = "Android TestKit";
    public static final String APP_NAME_SHORT = "ATK";

    public static final String[] MENU_BAR = { "File", "Settings", "Help" };

    public static final String[] TABBED_PANE = { "record", "replay" };

    public static final String EXIT_CONTENT = "Do you want to quit?";
    public static final String DEVICE_NOT_FOUND_CONTENT = "Device not found!";
    
    public static final String[] REPLAY_RIGHT_VIEW_POP = {"Up","Down","Remove"};
    public static final String[] REPLAY_LEFT_VIEW_POP = {"Add to replay list","Detail","Delete"};

    public static final String USB_STATE = "USB:";
    public static final String DEVICE = "Device:";
    
    public static final String[] SAVE_DIALOG_LABEL = {"Project:","Name:"};
    public static final String ERROR_USE_CHINESE = "Can not use Chinese.";
    public static final String ERROR_CONTENT_EMPTY = "Can not be empty.";
    public static final String ERROR_REPLAY_EVENT_EMPTY = "Replay list is empty.";
    
    public static final String CURRENT_EVENT="Current Event: ";
    public static final String CURRENT_TIME="Current Time: ";
    
    public static final String SOFTWARE_INFORMATION = 
                                                        "<html>"
                                                        +APP_NAME + " v2.0"+"<br>"
                                                        +"Build:XXXXX" + "<br>"
                                                        +"Email:175427603@qq.com" + "<br>"
                                                        +"</html>";

    
    public static final String[] RECORD_LEFT_CONTENT = {"Project:","ID:","Name:","Description:"};

}
