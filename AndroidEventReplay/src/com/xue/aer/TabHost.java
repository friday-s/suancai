package com.xue.aer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.res.AER;
import com.xue.util.Util;

public class TabHost extends JPanel {
	
	private static final int SELECT_INDEX_HEIGHT =10;

	class TabBar extends JPanel {

		private final int MOVE_RIGHT = 1;
		private final int MOVE_LEFT = 0;

		private JPanel mNamePanel;
		private JPanel mScrollPanel;
		private JLabel mTabSelect;
		private ArrayList<String> mTabStrings;

		private int mSelectIndex;
		private int mCurrentIndex;

		private int mIndexBarWidth;

		public TabBar() {

			mTabStrings = new ArrayList<String>();

			this.setLayout(new BorderLayout());
			this.setMaximumSize(new Dimension(AER.TAB_BAR_WIDTH, AER.TAB_BAR_HEIGHT));
			this.setPreferredSize(new Dimension(AER.TAB_BAR_WIDTH, AER.TAB_BAR_HEIGHT));

			mNamePanel = new JPanel();
			mNamePanel.setLayout(new GridLayout());
			mNamePanel.setPreferredSize(new Dimension(AER.TAB_BAR_WIDTH,
					(int) (AER.TAB_BAR_HEIGHT * 0.6)));

			mScrollPanel = new JPanel();
			mScrollPanel.setPreferredSize(new Dimension(AER.TAB_BAR_WIDTH,
					(int) (AER.TAB_BAR_HEIGHT * 0.3)));
			mScrollPanel.setLayout(null);
			mTabSelect = new JLabel();

			mScrollPanel.add(mTabSelect);

			this.add(mNamePanel, BorderLayout.NORTH);
			this.add(mScrollPanel, BorderLayout.SOUTH);

		}

		public void addTab(String name) {
			JLabel label = new JLabel(name);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.addMouseListener(mMouseListener);
			mNamePanel.add(label);
			System.out.println("add name :" + name);
			mTabStrings.add(name);

			mIndexBarWidth = AER.TAB_BAR_WIDTH / mTabStrings.size();
			ImageIcon image = Util.getImageIcon("tabbar_select.png");
			image.setImage(image.getImage().getScaledInstance(mIndexBarWidth, SELECT_INDEX_HEIGHT,
					Image.SCALE_SMOOTH));
			mTabSelect.setSize(mIndexBarWidth, SELECT_INDEX_HEIGHT);
			mTabSelect.setIcon(image);
			mTabSelect.setLocation(0, 0);

		}

		class MyTimerTask extends TimerTask{
			
			private final int DURATION = 20;
			private int direction;
			private int part;
			//private int step;

			public void setAction(int direction) {
				this.direction = direction;
			}
			
			public void setPart(int part){
				this.part = part;
			}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int beginX = mTabSelect.getY();
				System.out.println("beginX:"+beginX);
				
				int endX = part*mIndexBarWidth;
				System.out.println("endX:"+endX);
				
				int step = endX/DURATION;
				System.out.println("step:"+step);
				switch (direction) {
				case MOVE_RIGHT:
					while(beginX < endX)
					mTabSelect.setLocation(beginX+=step, 0);
					System.out.println("beginX:"+beginX);
	
					//mTabSelect.invalidate();
					break;
				case MOVE_LEFT:
					break;
				default:
					break;
				}
				mCurrentIndex = mSelectIndex;
			}
		}
	
		private void onTab() {

			MyTimerTask timerTask = new MyTimerTask();
			Timer timer = new Timer();
			if (mSelectIndex > mCurrentIndex) {
				timerTask.setAction(MOVE_RIGHT);
				timerTask.setPart(mSelectIndex-mCurrentIndex);
				timer.schedule(timerTask, 0);

			} else if (mSelectIndex < mCurrentIndex) {

			} else {

			}

		

		}

		private MouseListener mMouseListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(((JLabel) e.getComponent()).getSize().width);

				for (int i = 0; i < mTabStrings.size(); i++) {
					if (mTabStrings.get(i).equals(((JLabel) e.getComponent()).getText())) {
						mSelectIndex = i;
						onTab();
						onLayout(i);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

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
		};

	}

	private TabBar mTabBar;

	public TabHost() {
		mTabBar = new TabBar();
		this.add(mTabBar);

	}

	public void addTabView(TabView tabView) {
		mTabBar.addTab(tabView.getTabName());
		// addTabTitle(panel);
		// addTabView(panel);
	}

	private void onLayout(int index) {

	}

	private void addTabTitle(JPanel panel) {
	}

	private void addTabView(JPanel panel) {

	}

}
