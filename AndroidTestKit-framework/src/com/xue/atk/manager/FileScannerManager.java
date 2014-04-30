package com.xue.atk.manager;

import java.util.List;

import com.xue.atk.service.FileScannerService;

public class FileScannerManager {

    private static final String TAG = "FileScannerManager";

    private static FileScannerManager mFileScannerManager;
    private FileScannerService mFileScannerService;

    private FileScannerManager() {

    }

    public void initManager() {
        mFileScannerService = new FileScannerService();
    }

    public static FileScannerManager getManager() {
        if (mFileScannerManager == null) {
            mFileScannerManager = new FileScannerManager();
        }

        return mFileScannerManager;
    }
    
    public List<Object> getEventList(String projectName){
        return mFileScannerService.getEventList(projectName);
    }

    public String[] getProjectList() {
        return mFileScannerService.getProjectList();
    }
}
