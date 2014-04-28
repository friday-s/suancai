package com.xue.atk.manager;

import com.android.ddmlib.IDevice;
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
    
    public IDevice[] getDevices() {
        return mADBService.getDevices();
    }
    
    public void addCallBack(IDeviceChangedCallBack callBack){
        mADBService.addCallBack(callBack);
    }
    
    public IDevice getCurrentDevice() {
        return mADBService.getCurrentDevice();
    }

    public void setCurrentDevice(IDevice mCurrentDevice) {
        mADBService.setCurrentDevice(mCurrentDevice);
    }

}
