package com.xue.atk.service;

import com.android.ddmlib.IDevice;

public interface IDeviceChangedCallBack {
    

    public void deviceConnected(IDevice device);
    
    public void deviceDisonnected(IDevice device);
    
}
