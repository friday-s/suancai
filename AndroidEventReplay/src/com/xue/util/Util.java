package com.xue.util;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Util {

	public static final String IMAGE_URL_PREFIX = "/com/xue/res/";

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
}
