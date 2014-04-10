package com.xue.aer.util;

import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Util {

    public static final String IMAGE_URL_PREFIX = "/com/xue/aer/res/";

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

    public ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return icon;
    }
}
