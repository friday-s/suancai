package com.xue.aer;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.res.AER;
import com.xue.util.Util;

public class ToolBar extends JPanel implements ActionListener {

	private static final int TOOL_BAR_WIDTH = AER.WIDTH;
	private static final int TOOL_BAR_HEIGHT = 25;

	private static final int TOOL_BAR_OPTION_WIDTH = 20;
	private static final int TOOL_BAR_OPTION_HEIGHT = 20;

	public ToolBar(int x, int y) {
		setSize(TOOL_BAR_WIDTH, TOOL_BAR_HEIGHT);
		setLocation(x, y);
		// setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		setLayout(null);
		setBackground(Color.WHITE);
		initOptions();
	}

	private void initOptions() {
		ImageIcon settingsIcon = Util.getImageIcon("action_settings.png");
		settingsIcon.setImage(settingsIcon.getImage().getScaledInstance(TOOL_BAR_OPTION_WIDTH,
				TOOL_BAR_OPTION_HEIGHT, Image.SCALE_DEFAULT));
		JLabel settings = new JLabel();
		settings.setBounds(0, 0, TOOL_BAR_OPTION_HEIGHT, TOOL_BAR_OPTION_HEIGHT);

		settings.setIcon(settingsIcon);
		settings.setBackground(Color.WHITE);

		this.add(settings);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
