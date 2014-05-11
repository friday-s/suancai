/*
 * Copyright (C) 2009-2011 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xue.atk.service;

import java.io.File;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;

public class ADB {

    private static final String TAG = "ADB";

    private AndroidDebugBridge mAndroidDebugBridge;

    private String adbLocation;

    public boolean initialize() {

        boolean success = true;

        // String adbLocation =
        // System.getProperty("com.android.screenshot.bindir");
      

        String osName = System.getProperties().getProperty("os.name");
        String osArch = System.getProperties().getProperty("os.arch");

        Log.i(TAG, "OS:"+osName+" "+osArch);
        System.out.println( "OS:"+osName+" "+osArch);

        if (osName.indexOf("Linux") != -1 && osArch.indexOf("64") != -1) {
            adbLocation = "." + File.separator + "adb" + File.separator + "linuxX64";
        }else if(osName.indexOf("Windows") != -1){
        	adbLocation = "." + File.separator + "adb" + File.separator + "windows";
        }

        if (success) {
            if ((adbLocation != null) && (adbLocation.length() != 0)) {
                adbLocation += File.separator + "adb";
            } else {
                adbLocation = "adb";
            }
            AndroidDebugBridge.init(false);
            mAndroidDebugBridge = AndroidDebugBridge.createBridge(adbLocation, true);

            if (mAndroidDebugBridge == null) {
                success = false;
            }
        }

        if (success) {
            int count = 0;
            while (mAndroidDebugBridge.hasInitialDeviceList() == false) {
                try {
                    Thread.sleep(100);
                    count++;
                } catch (InterruptedException e) {
                }
                if (count > 100) {
                    success = false;
                    break;
                }
            }
        }

        if (!success) {
            terminate();
        }

        return success;
    }
    
    public String getAdbLocation() {
        return adbLocation;
    }
    
    
    public void addDeviceChangeListener(IDeviceChangeListener listener){
        mAndroidDebugBridge.addDeviceChangeListener(listener);
        
    }
    
    public AndroidDebugBridge getAndroidDebugBridge(){
    	return this.mAndroidDebugBridge;
    }
    
    public void terminate() {
        AndroidDebugBridge.terminate();
    }

    public IDevice[] getDevices() {
        IDevice[] devices = null;
        if (mAndroidDebugBridge != null) {
            devices = mAndroidDebugBridge.getDevices();
        }
        return devices;
    }

}
