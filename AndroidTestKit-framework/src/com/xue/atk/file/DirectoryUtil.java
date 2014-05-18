package com.xue.atk.file;

import java.io.File;

import com.android.ddmlib.Log;

public class DirectoryUtil {
    
    private static final String TAG = "DirectoryUtil";
    
    public static String getRootDirectory() {
        String rootPath = null;
        String path = System.getProperty("java.class.path").split(":")[0];
        String path2 = getRootDirectory();
        Log.d(TAG, "path2:"+path2);

        Log.d(TAG, "path:" + path);
        int index = path.lastIndexOf(File.separator);
        
        Log.d(TAG, "index:" + index);
        rootPath = path.substring(0, index + 1);
        Log.d(TAG, "mRootPath:" + rootPath);
        return rootPath;
    }

}
