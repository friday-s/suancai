package com.xue.atk.file;

import java.io.File;

public class FileScanner {

    private static final String PATH = "."+File.separator+"events"+File.separator;
    
    private File mFile;

    public FileScanner() {
        mFile = new File(PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    public String[] getEventList(String projectName) {
        return new File(PATH,projectName).list();
    }

    public String[] getProjectList() {
        return mFile.list();
    }

}
