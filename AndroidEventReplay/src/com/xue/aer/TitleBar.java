package com.xue.aer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import com.xue.res.AER;
import com.xue.util.Util;

public class TitleBar extends JPanel {

	private static final int TITLE_BAR_ICON_WIDTH = 20;
	private static final int TITLE_BAR_ICON_HEIGHT = 20;


	private static final int TITLE_BAR_PAD_HEIGHT = (AER.TITLE_BAR_HEIGHT - TITLE_BAR_ICON_HEIGHT) / 2;

	private JFrame mContext;
	// private Image mTitleLabel;
	private JLabel mTitleLabel;
	private JLabel mTitleName;
	private JLabel mCloseLabel;

	private boolean isDragged = false;
	private Point loc = null;
	private Point tmp = null;

	public TitleBar(JFrame context) {
		this.mContext = context;
		initView();
		setTitleIcon(Util.getImageIcon("android_20.png"));
		setTitleName(AER.APP_NAME);
		setCloseIcon(Util.getImageIcon("close_up_20.png"));
	}

	public TitleBar(JFrame context, ImageIcon icon, String name) {
		this.mContext = context;
		initView();
		setTitleIcon(icon);
		setTitleName(name);
	}

	public TitleBar(JFrame context, ImageIcon icon, String name, ImageIcon closeIcon) {
		this.mContext = context;
		initView();
		setTitleIcon(icon);
		setTitleName(name);
		setCloseIcon(closeIcon);
	}

	private void initView() {
		mTitleLabel = new JLabel();
		mTitleName = new JLabel();
		mCloseLabel = new JLabel();

		mCloseLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				setCloseIcon(Util.getImageIcon("close_up_20.png"));
				AlertDialog dialog = new AlertDialog(mContext, 400, 250, AlertDialog.EXIT_DIALOG);
				dialog.setMessage(AER.EXIT_CONTENT);
				dialog.onCreate();
				dialog.setVisible(true);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				setCloseIcon(Util.getImageIcon("close_down_20.png"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		setVisible(true);
		setBounds(0, 0, AER.TITLE_BAR_WIDTH, AER.TITLE_BAR_HEIGHT);
		this.setBackground(Color.WHITE);

		add(mTitleLabel);
		add(mTitleName);
		add(mCloseLabel);

		springLayout.putConstraint(SpringLayout.WEST, mTitleLabel, 1, SpringLayout.WEST, this);
		springLayout
				.putConstraint(SpringLayout.WEST, mTitleName, 1, SpringLayout.EAST, mTitleLabel);
		springLayout.putConstraint(SpringLayout.EAST, mCloseLabel, -2, SpringLayout.EAST, this);

		springLayout.putConstraint(SpringLayout.NORTH, mTitleLabel, TITLE_BAR_PAD_HEIGHT,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, mTitleName, TITLE_BAR_PAD_HEIGHT,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, mCloseLabel, TITLE_BAR_PAD_HEIGHT,
				SpringLayout.NORTH, this);

		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isDragged = false;
				// Ϊָ���Ĺ�����ù��ͼ��
				mContext.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				mContext.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			// ��갴��������ϰ��²��϶�ʱ���á�
			public void mouseDragged(MouseEvent e) {
				if (isDragged) {
					loc = new Point(mContext.getLocation().x + e.getX() - tmp.x, mContext
							.getLocation().y + e.getY() - tmp.y);
					mContext.setLocation(loc);
				}
			}
		});
	}

	public void setCloseIcon(ImageIcon icon) {
		// icon.setImage(icon.getImage().getScaledInstance(TITLE_BAR_ICON_WIDTH,
		// TITLE_BAR_ICON_HEIGHT, Image.SCALE_DEFAULT));
		mCloseLabel.setIcon(icon);
	}

	public void setTitleName(String name) {
		mTitleName.setText(name);
	}

	public void setTitleIcon(ImageIcon icon) {
		// icon.setImage(icon.getImage().getScaledInstance(TITLE_BAR_ICON_WIDTH,
		// TITLE_BAR_ICON_HEIGHT, Image.SCALE_DEFAULT));
		mTitleLabel.setIcon(icon);
	}
}
