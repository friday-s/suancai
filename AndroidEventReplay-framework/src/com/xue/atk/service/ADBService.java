package com.xue.atk.service;

import com.android.ddmlib.IDevice;



public class ADBService extends Thread {

    private int adbSate;
    
    private ADB mADB;
    private IDevice[] mDevices;
    private IDevice mDevice;
    
    public ADBService(){
        System.out.println(ADBService.class.getName());
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

    public void run() {

    }

}
