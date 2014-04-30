package com.xue.atk.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xue.atk.file.EventFile;

public class FileScannerService {

    private static final String PATH = "." + File.separator + "events" + File.separator;

    private File mFile;

    public FileScannerService() {
        mFile = new File(PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    public List<Object> getEventList(String projectName) {

        if (projectName ==null){
            return null;
        }
        List<Object> array = null;

        String[] s = new File(PATH, projectName).list();
        if (s.length != 0) {
            array = new ArrayList<Object>();
            for (int i = 0; i < s.length; i++) {
                EventFile event = new EventFile();
                event.setName(s[i]);
                event.setPath(PATH + projectName);
                array.add(event);
            }
        }

        return array;
    }

    public String[] getProjectList() {
        return mFile.list();
    }
    

    
}
