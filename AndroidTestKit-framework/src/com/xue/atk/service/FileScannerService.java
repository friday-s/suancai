package com.xue.atk.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.android.ddmlib.Log;
import com.xue.atk.file.DirectoryUtil;
import com.xue.atk.file.EventFile;

public class FileScannerService {

    private static final String TAG = "FileScannerService";

    private String mEventsPath;

    private File mFile;

    public FileScannerService() {

        String path = DirectoryUtil.getRootDirectory();

        mEventsPath = path + "events" + File.separator;

        mFile = new File(mEventsPath);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
        
        Log.i(TAG, "initialize the file scanner service");
    }

    public String getEventPath() {
        return mEventsPath;
    }

    public void checkPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public List<Object> getEventList(String projectName) {

        if (projectName == null) {
            return null;
        }
        List<Object> array = null;

        String[] s = new File(mEventsPath, projectName).list();
        if (s != null && s.length != 0) {
            array = new ArrayList<Object>();
            for (int i = 0; i < s.length; i++) {
                EventFile event = new EventFile();
                event.setName(s[i]);
                event.setPath(mEventsPath + projectName);
                array.add(event);
            }
        }

        return array;
    }

    public String[] getProjectList() {
        return mFile.list();
    }

}
