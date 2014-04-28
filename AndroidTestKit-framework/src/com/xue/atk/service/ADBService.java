package com.xue.atk.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;

public class ADBService {

    private static final String TAG = "ADBService";

    private ADB mADB;
  //  private IDevice[] mDevices;
    
    private List<IDevice> mDevices;

    private IDevice mCurrentDevice;

    private List<IDeviceChangedCallBack> mCallBackList;

    public ADBService() {

        mADB = new ADB();
        if (!mADB.initialize()) {
            System.out.println("Could not find adb.");
        }

        mDevices = Arrays.asList(mADB.getDevices());

        for (IDevice d : mDevices) {

            System.out.println("device:" + d.toString());

        }
        if (mDevices != null && mDevices.size() > 0) {
            mCurrentDevice = mDevices.get(0);
        }

        mADB.addDeviceChangeListener(mDeviceChangeListener);
        mCallBackList = new ArrayList<IDeviceChangedCallBack>();

    }

    public void addCallBack(IDeviceChangedCallBack callBack) {
        mCallBackList.add(callBack);
    }

    public List<IDevice> getDevices() {
        return mDevices;
    }

    public IDevice getCurrentDevice() {
        return mCurrentDevice;
    }

    public void setCurrentDevice(IDevice mCurrentDevice) {
        this.mCurrentDevice = mCurrentDevice;
    }

    private IDeviceChangeListener mDeviceChangeListener = new IDeviceChangeListener() {

        @Override
        public void deviceConnected(IDevice device) {
            // TODO Auto-generated method stub
            Log.d(TAG, "connected device:" + device.toString());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceConnected(device);
            }
        }

        @Override
        public void deviceDisconnected(IDevice device) {
            // TODO Auto-generated method stub
            Log.d(TAG, "disconnected device:" + device.toString());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceDisonnected(device);
            }
        }

        @Override
        public void deviceChanged(IDevice device, int changeMask) {
            // TODO Auto-generated method stub

        }

    };
}
