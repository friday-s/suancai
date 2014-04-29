package com.xue.atk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.xue.atk.manager.ADBManager;


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
            Log.d(TAG, "Could not find adb.");
        }

        mDevices = Arrays.asList(mADB.getDevices());

        if (mDevices != null && mDevices.size() > 0) {
            mCurrentDevice = mDevices.get(0);
        }

        mADB.addDeviceChangeListener(mDeviceChangeListener);
        mCallBackList = new ArrayList<IDeviceChangedCallBack>();

    }
    
    public String getAdbLocation() {
        return mADB.getAdbLocation();
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
            mDevices = Arrays.asList(mADB.getDevices());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceConnected(device);
            }
            
        }

        @Override
        public void deviceDisconnected(IDevice device) {
            // TODO Auto-generated method stub
            Log.d(TAG, "disconnected device:" + device.toString());
            mDevices = Arrays.asList(mADB.getDevices());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceDisonnected(device);
            }
            
        }

        @Override
        public void deviceChanged(IDevice device, int changeMask) {
            // TODO Auto-generated method stub

        }

    };
    
    public void executeShellCommand(final String command) {
        final IDevice device = getCurrentDevice();
        executeShellCommand(device, command);
    }

    public void executeShellCommand(final IDevice device, final String command) {

        new Thread() {
            public void run() {
                execute(device, command);
            }
        }.start();

    }

    private void execute(IDevice device, String command) {
        try {
            device.executeShellCommand(command, mMultiLineReceiver);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private MultiLineReceiver mMultiLineReceiver = new MultiLineReceiver() {

        @Override
        public boolean isCancelled() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void processNewLines(String[] lines) {
            // TODO Auto-generated method stub
            for (String s : lines) {
                System.out.println(s);
                Log.i(TAG, s);
            }
        }
    };

    private Process p;
    private Thread thread_replay;
    public static String errorMsg;

    public void execADBCommand(String command) {
        IDevice device = ADBManager.getADBManager().getCurrentDevice();
        execADBCommand(device, command);
    }

    public void execADBCommand(IDevice device, final String command) {

        String adbLocation = getAdbLocation();
        String deviceSN = device.toString();

        final String c = adbLocation + " -s " + deviceSN + " " + command;
        new Thread() {
            public void run() {
                executeADB(c);
            }
        }.start();
    }

    private int executeADB(String command) {
        
        System.out.println("command:"+command);
        Log.e(TAG, "command:"+command);
        try {
            p = Runtime.getRuntime().exec(command);

            new StreamGobbler(p.getErrorStream(), "ERROR").start();
            new StreamGobbler(p.getInputStream(), "OUTPUT").start();

            try {
                // 返回结果可能有风险存在
                if (p.waitFor() < 0) {
                    System.err.println("command \"" + command + "\" return=" + p.exitValue());
                    Log.e(TAG, "command \"" + command + "\" return=" + p.exitValue());
                    return -1;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return -1;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public void stop() {
        p.destroy();
        if (thread_replay != null && thread_replay.isAlive()) {
            thread_replay.stop();
        }
    }

    class StreamGobbler extends Thread {

        private InputStream is;
        private String type;
        private BufferedReader br;

        public StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {

            // TODO Auto-generated method stub
            try {
                br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.err.println(type + ">" + line);
                    Log.e(TAG, type + ">" + line);
                    errorMsg = line;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
