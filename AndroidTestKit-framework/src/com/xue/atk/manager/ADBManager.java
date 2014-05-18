package com.xue.atk.manager;

import java.io.IOException;

import java.util.List;

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

    public int executeShellCommand(String command) {
        return mADBService.executeShellCommand(command);
    }

    public int executeShellCommand(IDevice device, String command) {
        return mADBService.executeShellCommand(device, command);
    }

    public int execADBCommand(String command) {
        return mADBService.execADBCommand(command);
    }

    public int execADBCommand(IDevice device, String command) {
        return mADBService.execADBCommand(device, command);
    }

    public int getCurrentDeviceEventNum() {
        return mADBService.getCurrentDeviceEventNum();
    }

    public void calcEventNum() throws IOException {
        mADBService.calcEventNum();
    }

    public String getErrorMsg() {
        return getException().getMessage();
    }

    public Exception getException() {
        return mADBService.getException();
    }

    public void terminateADBCommand() {
        mADBService.terminateADBCommand();
    }
}
