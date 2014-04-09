package com.xue.aer.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.xue.aer.util.AWTUtilitiesWrapper;
import com.xue.aer.util.Util;

public class AlertDialog extends Dialog implements MouseListener {

	private static final int ICON_WIDTH = 50;
	private static final int ICON_HEIGHT = 50;

	private static final int BTN_WIDTH = 95;
	private static final int BTN_HEIGHT = 40;

	private static final int MSG_WIDTH = 30;
	private static final int MSG_HEIGHT = 30;

	public static final int EXIT_DIALOG = 1;
	public static final int MSG_DIALOG = 2;

	private int mFlag;
	private String message;

	private JLabel mIconLable;
	private JLabel mPositiveBtn;
	private JLabel mNegativeBtn;
	private JLabel mMsgLabel;
	

	public AlertDialog(JFrame owner) {
		super(owner);
	
	}

	public AlertDialog(JFrame owner, int width, int height, int flag) {
		super(owner,width,height);
		// TODO Auto-generated constructor stub
		setFlag(flag);
	}

	public void setFlag(int flag) {
		this.mFlag = flag;
	}

	public void onCreate() {
		mIconLable = new JLabel();
		mIconLable.setBounds((getDialogWidth() - ICON_WIDTH) / 2, ICON_WIDTH / 2, ICON_WIDTH,
				ICON_HEIGHT);

		mMsgLabel = new JLabel();
		mMsgLabel.setText(message);
		mMsgLabel.setBounds(getDialogWidth() / 2 - BTN_WIDTH - 20, getDialogHeight() / 2-20,
				getDialogWidth(), MSG_HEIGHT);
		
		getLayeredPane().add(mMsgLabel, new Integer(300));
		
		switch (mFlag) {
		case EXIT_DIALOG:
			mIconLable.setIcon(Util.getImageIcon("warn.png"));
			getLayeredPane().add(mIconLable, new Integer(300));

			mPositiveBtn = new JLabel();
			mPositiveBtn.setIcon(Util.getImageIcon("ok_up.png"));
			mPositiveBtn.setBounds(getDialogWidth() / 2 + 20, getDialogHeight() - BTN_HEIGHT - 20,
					BTN_WIDTH, BTN_HEIGHT);
			mPositiveBtn.addMouseListener(this);

			mNegativeBtn = new JLabel();
			mNegativeBtn.setIcon(Util.getImageIcon("cancel_up.png"));
			mNegativeBtn.setBounds(getDialogWidth() / 2 - BTN_WIDTH - 20, getDialogHeight()
					- BTN_HEIGHT - 20, BTN_WIDTH, BTN_HEIGHT);
			mNegativeBtn.addMouseListener(this);

			getLayeredPane().add(mPositiveBtn, new Integer(300));
			getLayeredPane().add(mNegativeBtn, new Integer(300));
			this.setAlwaysOnTop(true);
			mContext.setEnabled(false);

			break;
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == mPositiveBtn) {
			mPositiveBtn.setIcon(Util.getImageIcon("ok_up.png"));
			this.dispose();
			mContext.dispose();
			System.exit(0);
			return;
		}

		if (e.getSource() == mNegativeBtn) {
			mNegativeBtn.setIcon(Util.getImageIcon("cancel_up.png"));
			this.dispose();
			mContext.setEnabled(true);
			return;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == mPositiveBtn) {
			mPositiveBtn.setIcon(Util.getImageIcon("ok_down.png"));
			return;
		}

		if (e.getSource() == mNegativeBtn) {
			mNegativeBtn.setIcon(Util.getImageIcon("cancel_down.png"));
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == mPositiveBtn) {
			mPositiveBtn.setIcon(Util.getImageIcon("ok_up.png"));
			return;
		}

		if (e.getSource() == mNegativeBtn) {
			mNegativeBtn.setIcon(Util.getImageIcon("cancel_up.png"));
			return;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
