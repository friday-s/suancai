package com.xue.aer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

    private static final int SELECT_INDEX_HEIGHT = 10;

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

        private ScrollThread mScrollThread;

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

            mTabStrings.add(name);

            mIndexBarWidth = AER.TAB_BAR_WIDTH / mTabStrings.size();
            ImageIcon image = Util.getImageIcon("tabbar_select.png");
            image.setImage(image.getImage().getScaledInstance(mIndexBarWidth, SELECT_INDEX_HEIGHT,
                    Image.SCALE_SMOOTH));
            mTabSelect.setSize(mIndexBarWidth, SELECT_INDEX_HEIGHT);
            mTabSelect.setIcon(image);
            mTabSelect.setLocation(0, 0);

        }

        class ScrollThread extends Thread {

            private final int DURATION = 10;
            private int direction;
            private int part;

            // private int step;

            public void setAction(int direction) {
                this.direction = direction;
            }

            public void setPart(int part) {
                this.part = part;
            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int beginX = mTabSelect.getX();
                int endX = 0;
                int step = part * DURATION;
                // int step = 10;
                switch (direction) {
                case MOVE_RIGHT:
                    endX = beginX + part * mIndexBarWidth;
                    while (beginX < endX) {
                        mTabSelect.setLocation(beginX += step, 0);
                        System.out.println("beginX:" + beginX);
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;
                case MOVE_LEFT:
                    endX = mCurrentIndex * mIndexBarWidth - part * mIndexBarWidth;
                    while (beginX > endX) {
                        mTabSelect.setLocation(beginX -= step, 0);
                        System.out.println("beginX:" + beginX);
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    break;
                default:
                    break;
                }
                mCurrentIndex = mSelectIndex;

                Component[] components = mNamePanel.getComponents();
                for (int i = 0; i < components.length; i++) {
                    if (i == mCurrentIndex) {
                        components[i].setFont(new Font(mTabStrings.get(i), 1, 20));
                        components[i].setForeground(Color.BLUE);
                    } else {
                        components[i].setFont(new Font(mTabStrings.get(i), 1, 12));
                        components[i].setForeground(Color.BLACK);
                    }
                }

            }
        }

        private void onTab() {
            if (mScrollThread != null && mScrollThread.isAlive()) {
                try {
                    mScrollThread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            mScrollThread = new ScrollThread();

            if (mSelectIndex > mCurrentIndex) {

                mScrollThread.setAction(MOVE_RIGHT);
                mScrollThread.setPart(mSelectIndex - mCurrentIndex);

                mScrollThread.start();

            } else if (mSelectIndex < mCurrentIndex) {
                mScrollThread.setAction(MOVE_LEFT);
                mScrollThread.setPart(mCurrentIndex - mSelectIndex);
                mScrollThread.start();

            } else {

            }

        }

        private MouseListener mMouseListener = new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
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
