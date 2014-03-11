package com.xue.aer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import com.xue.res.AER;
import com.xue.util.Util;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private boolean isDragged = false;
	private Point loc = null;
	private Point tmp = null;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setUndecorated(true);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		// contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		TitleBar bar  = new TitleBar(Util.getImageIcon("android.png"),"afgjfjf");
		contentPane.add(bar);
		//contentPane.add(initMenuBar(AER.MENU_BAR));
		//
		// JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		//
		// tabbedPane.setBackground(Color.white);

		/*
		 * JPanel panel1 = new JPanel(); JPanel panel2 = new JPanel();
		 * tabbedPane.addTab("tab1", panel1); tabbedPane.setEnabledAt(0, true);
		 * tabbedPane.setTitleAt(0, "tab1"); tabbedPane.addTab("tab2", panel2);
		 * tabbedPane.setEnabledAt(1, true); tabbedPane.setTitleAt(1, "tab2");
		 */
		// contentPane.add(initTabledPane(AER.TABBED_PANE));

		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isDragged = false;
				// 为指定的光标设置光标图像
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			// 鼠标按键在组件上按下并拖动时调用。
			public void mouseDragged(MouseEvent e) {
				if (isDragged) {
					loc = new Point(getLocation().x + e.getX() - tmp.x, getLocation().y + e.getY()
							- tmp.y);
					setLocation(loc);
				}
			}
		});
	}

	private JTabbedPane initTabledPane(String[] tabs) {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBorder(new LineBorder(Color.WHITE));
		tabbedPane.setBounds(0, 25, 980, 420);

		ImageIcon image = Util.getImageIcon("android.png");
		image.setImage(image.getImage().getScaledInstance(AER.TAB_ICON_WIDTH, AER.TAB_ICON_HEIGHT,
				Image.SCALE_DEFAULT));

		for (String tab : tabs) {
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(Color.WHITE));
			panel.setBackground(Color.WHITE);

			// tabbedPane.addTab("", image, panel,tab);
			tabbedPane.addTab(tab, panel);
		}
		return tabbedPane;
	}

	private JMenuBar initMenuBar(String[] items) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, AER.WIDTH, 21);
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		menuBar.setBackground(Color.WHITE);
		for (String item : items) {
			JMenuItem menuItem = new JMenuItem(item);
			menuItem.setBackground(Color.WHITE);
			menuItem.setForeground(Color.BLUE);
			menuBar.add(menuItem);
		}
		return menuBar;

	}
}
