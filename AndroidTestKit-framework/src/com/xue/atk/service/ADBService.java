package com.xue.atk.service;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;



public class ADBService {

    private int adbSate;
    
    private ADB mADB;
    private IDevice[] mDevices;
    private IDevice mDevice;
    
    public ADBService(){

        mADB = new ADB();
        if (!mADB.initialize()){
            System.out.println("Could not find adb.");
        }
        
        mDevices = mADB.getDevices();
        for (IDevice d:mDevices){
            System.out.println("device:"+d.toString());
          //  System.out.println("device:"+d.get);
        }
        
    }
    
    private IDeviceChangeListener mDeviceChangeListener = new IDeviceChangeListener(){

  		@Override
  		public void deviceConnected(IDevice device) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void deviceDisconnected(IDevice device) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void deviceChanged(IDevice device, int changeMask) {
  			// TODO Auto-generated method stub
  			
  		}
      	
      };
}
