package com.xue.atk.file;

public class EventFile implements Cloneable {

    private String name;
    private String path;
    private int time = 1;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return name;
    }

    public Object clone() {
        EventFile o = null;
        try {
            o = (EventFile) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return o;

    }

}
