package com.xue.atk.service;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;

public class ADBService {

    private static final String TAG = "ADBService";

    private ADB mADB;
    // private IDevice[] mDevices;

    private List<IDevice> mDevices;

    private IDevice mCurrentDevice;
    private int mCurrentDeviceEventNum;


    private List<IDeviceChangedCallBack> mCallBackList;

    public ADBService() {
        
        mADB = new ADB();
        if (!mADB.initialize()) {
            Log.w(TAG, "Try using system environment variables adb");
            mADB.setAdbLocation(null);
            if (!mADB.initialize()){
                Log.e(TAG, "Could not find adb");
            }
        }

        mDevices = Arrays.asList(mADB.getDevices());

        if (mDevices != null && mDevices.size() > 0) {
            mCurrentDevice = mDevices.get(0);

        }

        mADB.addDeviceChangeListener(mDeviceChangeListener);
        mCallBackList = new ArrayList<IDeviceChangedCallBack>();

    }

    public void calcEventNum() throws IOException{

        if (mCurrentDevice != null) {

            mCurrentDevice.executeShellCommand("ls dev/input", mEventNumReceiver);

        }
    }

    private IShellOutputReceiver mEventNumReceiver = new IShellOutputReceiver() {

        private int count =0;
        
        @Override
        public void addOutput(byte[] data, int offset, int length) {
            // TODO Auto-generated method stub
            
            String str = null;
            try {
                str = new String(data, offset, length, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                str = new String(data, offset, length);
            }

            for (String s :str.split("\r\n")){
                if(s.indexOf("event")!= -1 ){
                    count++;
                }
            }
     
        }

        @Override
        public void flush() {
            // TODO Auto-generated method stub
            mCurrentDeviceEventNum = count;
            count =0;
            Log.i(TAG, "mCurrentDeviceEventNum:" + mCurrentDeviceEventNum);
        }

        @Override
        public boolean isCancelled() {
            // TODO Auto-generated method stub
            return false;
        }
    };

    public int getCurrentDeviceEventNum() {
        
        return mCurrentDeviceEventNum;
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
        //calcEventNum();
    }

    private IDeviceChangeListener mDeviceChangeListener = new IDeviceChangeListener() {

        @Override
        public void deviceConnected(IDevice device) {
            // TODO Auto-generated method stub
            Log.i(TAG, "connected device:" + device.toString());
            mDevices = Arrays.asList(mADB.getDevices());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceConnected(device);
            }
        }

        @Override
        public void deviceDisconnected(IDevice device) {
            // TODO Auto-generated method stub
            Log.i(TAG, "disconnected device:" + device.toString());
            mDevices = Arrays.asList(mADB.getDevices());
            for (IDeviceChangedCallBack callBack : mCallBackList) {
                callBack.deviceDisonnected(device);
            }
        }

        @Override
        public void deviceChanged(IDevice device, int changeMask) {
            // TODO Auto-generated method stub
            Log.i(TAG, "deviceChanged:" + device + " changeMask:"+changeMask);
        }
    };

    private Exception mException;

    public Exception getException() {
        return mException;
    }

    public int executeShellCommand(final String command) {
        final IDevice device = getCurrentDevice();
        return executeShellCommand(device, command);
    }

    public int executeShellCommand(final IDevice device, final String command) {
        return execute(device, command);
    }

    private int execute(IDevice device, String command) {
        try {
            device.executeShellCommand(command, mMultiLineReceiver);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mException = e;
            return -1;
        }

        return 1;
    }

    public void terminateShellCommand() {

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
                Log.i(TAG, "test :" + s);
            }
        }
    };

    private Process p;

    private List<Process> totalProcess = new ArrayList<Process>();

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public int execADBCommand(String command) {
        IDevice device = getCurrentDevice();
        return execADBCommand(device, command);
    }

    public int execADBCommand(IDevice device, final String command) {

        String adbLocation = getAdbLocation();
        String deviceSN = device.toString();

        final String c = adbLocation + " -s " + deviceSN + " " + command;

        return executeADB(c);

    }

    private int executeADB(String command) {

        try {
            p = Runtime.getRuntime().exec(command);
            totalProcess.add(p);
            new StreamGobbler(p.getErrorStream(), "ERROR").start();
            new StreamGobbler(p.getInputStream(), "OUTPUT").start();

            try {
                if (p.waitFor() < 0) {
                    Log.e(TAG, "command \"" + command + "\" return=" + p.exitValue());
                    mException = new Exception(errorMsg);
                    return -1;
                }
                
                if (errorMsg!=null && errorMsg.indexOf("failed") != -1){
                    mException = new Exception(errorMsg);
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

    public void terminateADBCommand() {

        for (int i = 0; i < totalProcess.size(); i++) {
            totalProcess.get(i).destroy();
            totalProcess.set(i, null);
        }
        totalProcess.clear();

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
                    Log.d(TAG, type + ">" + line);
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
