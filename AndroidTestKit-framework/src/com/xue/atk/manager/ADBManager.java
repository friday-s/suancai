package com.xue.atk.manager;

import com.xue.atk.service.ADBService;
import com.xue.atk.service.IDeviceChangedCallBack;

public class ADBManager {

    private static ADBManager mADBManager;

    private ADBService mADBService;

    private ADBManager() {

    }

    public void initManager() {
        mADBService = new ADBService();
    }

    public static ADBManager getADBManager() {
        if (mADBManager == null) {
            mADBManager = new ADBManager();
        }

        return mADBManager;
    }
    
    public void addCallBack(IDeviceChangedCallBack callBack){
        mADBService.addCallBack(callBack);
    }

}
