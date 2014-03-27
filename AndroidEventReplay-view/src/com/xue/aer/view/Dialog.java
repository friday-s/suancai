package com.xue.aer.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;

public class Dialog extends JFrame {

	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;

	private int mDialogWidth = DEFAULT_WIDTH;
	private int mDialogHeight = DEFAULT_HEIGHT;

	private JFrame mContext;
	private JLayeredPane layeredPane;

	public Dialog(JFrame owner) {
		this.mContext = owner;
		
		
	}

	public Dialog(JFrame owner, int width, int height) {
		this.mContext = owner;
		this.mDialogWidth = width;
		this.mDialogHeight = height;
	
	}

	public void initPanel() {
		this.setUndecorated(true);
		 
		this.setBackground(new Color(0,0,0,0));
		this.setLocationRelativeTo(mContext);
		this.setSize(mDialogWidth, mDialogHeight);
		this.setLocation(AER.LOCATION_X + (AER.WIDTH - mDialogWidth) / 2, AER.LOCATION_Y
				+ (AER.HEIGHT - mDialogHeight) / 2);
	
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		layeredPane.setBackground(Color.WHITE);
		layeredPane.setBounds(0, 0, mDialogWidth, mDialogHeight);
		
		this.add(layeredPane);

		JLabel label = new JLabel();
		ImageIcon icon = Util.getImageIcon("dlg_bg.png");

		if (mDialogWidth != DEFAULT_WIDTH && mDialogHeight != DEFAULT_HEIGHT) {
			icon.setImage(icon.getImage().getScaledInstance(mDialogWidth, mDialogHeight,
					Image.SCALE_SMOOTH));
		}
		label.setIcon(icon);
		label.setBounds(0, 0, mDialogWidth, mDialogHeight);

		layeredPane.add(label,new Integer(200));

	}

	public int getDialogWidth() {
		return mDialogWidth;
	}

	public void setDialogWidth(int mDialogWidth) {
		this.mDialogWidth = mDialogWidth;
	}

	public int getDialogHeight() {
		return mDialogHeight;
	}

	public void setDialogHeight(int mDialogHeight) {
		this.mDialogHeight = mDialogHeight;
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}


}
