package com.xue.atk.util;

import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Util {

    public static final String IMAGE_URL_PREFIX = "/com/xue/atk/res/";

    public static String getImageURL(String imageName) {
        return IMAGE_URL_PREFIX + imageName;
    }

    public static ImageIcon getImageIcon(String iconName) {
        return new ImageIcon(ClassLoader.class.getResource(getImageURL(iconName)));
    }

    public Icon getIcon(String path) {
        URL url = ClassLoader.class.getResource(path);
        return new ImageIcon(url);
    }

    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return icon;
    }

    public static boolean isContainChinese(String str) {
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] >= (byte) 0x81 && bytes[i] <= (byte) 0xfe) {
                return true;
            }
        }
        return false;
    }
}
