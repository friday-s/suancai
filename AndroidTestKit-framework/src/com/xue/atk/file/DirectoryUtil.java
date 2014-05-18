package com.xue.atk.file;

import java.io.File;

import com.android.ddmlib.Log;

public class DirectoryUtil {

	private static final String TAG = "DirectoryUtil";

	private static String rootPath = null;

	public static String getRootDirectory() {
		if (rootPath == null) {
			String path = null;
			if (System.getProperties().getProperty("os.name").indexOf("Linux") != -1) {

				path = System.getProperty("java.class.path").split(":")[0];

			} else if (System.getProperties().getProperty("os.name").indexOf("Windows") != -1) {
				path = System.getProperty("java.class.path").split(";")[0];
			} else {
				Log.e(TAG, "The system does not support");
			}
			Log.d(TAG, "path:" + path);
			int index = path.lastIndexOf(File.separator);
			Log.d(TAG, "index:" + index);
			rootPath = path.substring(0, index + 1);
		}
		Log.d(TAG, "rootPath:" + rootPath);
		return rootPath;
	}

}
