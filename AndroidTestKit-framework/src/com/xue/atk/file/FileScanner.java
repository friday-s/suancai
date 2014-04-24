package com.xue.atk.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    private static final String PATH = "." + File.separator + "events" + File.separator;

    private File mFile;

    public FileScanner() {
        mFile = new File(PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    public List<Object> getEventList(String projectName) {

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
