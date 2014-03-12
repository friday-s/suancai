package com.xue.aer;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleBar extends JPanel {

	// private Image mTitleIcon;
	private JLabel mTitleIcon;
	private JLabel mTitleName;

	public TitleBar() {
		initView();
	}

	public TitleBar(Icon icon, String name) {
		initView();
		setTitleIcon(icon);
		setTitleName(name);
	}

	private void initView() {
		mTitleIcon = new JLabel();
		mTitleName = new JLabel();
		setLayout(new BorderLayout());
		setVisible(true);
		setBounds(0, 0, 200, 80);
		add(mTitleIcon, BorderLayout.WEST);
		add(mTitleName,BorderLayout.EAST);
	}

	public void setTitleName(String name) {
		mTitleName.setText(name);
	}

	public void setTitleIcon(Icon icon) {
		mTitleIcon.setIcon(icon);

	}

}
