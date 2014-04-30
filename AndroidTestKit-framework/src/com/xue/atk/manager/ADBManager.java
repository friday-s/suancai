package com.xue.atk.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.xue.atk.service.ADBService;
import com.xue.atk.service.IDeviceChangedCallBack;

public class ADBManager {

    private static final String TAG = "ADBManager";

    private static ADBManager mADBManager;

    private ADBService mADBService;

    private ADBManager() {

    }

    public void initManager() {
        mADBService = new ADBService();
    }

    public static ADBManager getManager() {
        if (mADBManager == null) {
            mADBManager = new ADBManager();
        }
        return mADBManager;
    }

    public List<IDevice> getDevices() {
        return mADBService.getDevices();
    }

    public void addCallBack(IDeviceChangedCallBack callBack) {
        mADBService.addCallBack(callBack);
    }

    public IDevice getCurrentDevice() {
        return mADBService.getCurrentDevice();
    }

    public void setCurrentDevice(IDevice mCurrentDevice) {
        mADBService.setCurrentDevice(mCurrentDevice);
    }

    public void executeShellCommand(String command) {
        mADBService.executeShellCommand(command);
    }

    public void executeShellCommand(IDevice device, String command) {
        mADBService.executeShellCommand(device, command);
    }

    public void execADBCommand(String command) {
        mADBService.execADBCommand(command);
    }

    public void execADBCommand(IDevice device, String command) {
        mADBService.execADBCommand(device, command);
    }
    
    public void terminateADBCommand() {
        mADBService.terminateADBCommand();
    }
}
