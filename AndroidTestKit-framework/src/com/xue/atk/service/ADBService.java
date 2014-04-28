package com.xue.atk.service;

import java.util.ArrayList;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;



public class ADBService {
    
    private ADB mADB;
    private IDevice[] mDevices;
    private IDevice mDevice;
    
    private List<IDeviceChangedCallBack> mCallBackList;
    
    public ADBService(){

        mADB = new ADB();
        if (!mADB.initialize()){
            System.out.println("Could not find adb.");
        }

        mDevices = mADB.getDevices();
        
        for (IDevice d:mDevices){
            System.out.println("device:"+d.toString());
       
        }

        mADB.addDeviceChangeListener(mDeviceChangeListener);
        mCallBackList = new ArrayList<IDeviceChangedCallBack>();

    }
    
    public void addCallBack(IDeviceChangedCallBack callBack){
        mCallBackList.add(callBack);
    }
    
    
    private IDeviceChangeListener mDeviceChangeListener = new IDeviceChangeListener(){

  		@Override
  		public void deviceConnected(IDevice device) {
  			// TODO Auto-generated method stub
  		    System.out.println(device.getState());
  		    for (IDeviceChangedCallBack callBack :mCallBackList){
  		      callBack.updateView();
  		    }
  		}

  		@Override
  		public void deviceDisconnected(IDevice device) {
  			// TODO Auto-generated method stub
  		  for (IDeviceChangedCallBack callBack :mCallBackList){
              callBack.updateView();
            }
  		}

  		@Override
  		public void deviceChanged(IDevice device, int changeMask) {
  			// TODO Auto-generated method stub
  			
  		}
      	
      };
}
