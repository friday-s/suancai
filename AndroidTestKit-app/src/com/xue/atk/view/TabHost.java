package com.xue.atk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class TabHost extends JPanel {

    private static final int SELECT_INDEX_HEIGHT = 10;

    private final int MOVE_RIGHT = 1;
    private final int MOVE_LEFT = 0;

    private JPanel mTabBar;
    private JPanel mTabView;

    private JPanel mNamePanel;
    private JPanel mScrollPanel;
    private JLabel mTabSelect;
    private ArrayList<String> mTabStrings;

    private ArrayList<TabView> mTabViews;

    private int mSelectIndex;
    private int mCurrentIndex = 0;
    private int mLastIndex = 0;

    private int mIndexBarWidth;

    private ScrollThread mScrollThread;
    
    private boolean focusable = true;
    
    private Window mWindow;

    public TabHost(Window window) {
        this.mWindow = window;
        initTabHost();
    }

    public void addTab(TabView tabView) {

        JLabel label = new JLabel(tabView.getTabName());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.addMouseListener(mMouseListener);
        mNamePanel.add(label);

        mTabStrings.add(tabView.getTabName());

        mIndexBarWidth = ATK.TAB_BAR_WIDTH / mTabStrings.size();
        ImageIcon image = Util.getImageIcon("tabbar_select.png");
        image.setImage(image.getImage().getScaledInstance(mIndexBarWidth, SELECT_INDEX_HEIGHT,
                Image.SCALE_SMOOTH));
        mTabSelect.setSize(mIndexBarWidth, SELECT_INDEX_HEIGHT);
        mTabSelect.setIcon(image);
        mTabSelect.setLocation(0, 0);

        mTabViews.add(tabView);

        /* default view */
        Component[] components = mNamePanel.getComponents();
        components[0].setFont(new Font(mTabStrings.get(0), 1, 20));
        components[0].setForeground(Color.BLUE);
        TabView defaultView = mTabViews.get(0);
        defaultView.setBounds(0, 0, ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT);
        mTabView.add(defaultView);

    }

    private void initTabHost() {
        this.setLayout(null);
        
        mTabStrings = new ArrayList<String>();
        mTabViews = new ArrayList<TabView>();

        mTabBar = new JPanel();
       
        mTabBar.setLayout(new BorderLayout());
        //mTabBar.setMaximumSize(new Dimension(AER.TAB_BAR_WIDTH, AER.TAB_BAR_HEIGHT));
        //mTabBar.setPreferredSize(new Dimension(AER.TAB_BAR_WIDTH, AER.TAB_BAR_HEIGHT));
        mTabBar.setBackground(Color.WHITE);
        mTabBar.setBounds(0, 0, ATK.TAB_BAR_WIDTH, ATK.TAB_BAR_HEIGHT);
        this.add(mTabBar);

        mTabView = new JPanel();
        mTabView.setLayout(null);
        //mTabView.setMaximumSize(new Dimension(AER.TAB_VIEW_WIDTH, AER.TAB_VIEW_HEIGHT));
        //mTabView.setPreferredSize(new Dimension(AER.TAB_VIEW_WIDTH, AER.TAB_VIEW_HEIGHT));
        mTabView.setBounds(ATK.TAB_VIEW_X, ATK.TAB_VIEW_Y, ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT);

        this.add(mTabView);

        mNamePanel = new JPanel();
        mNamePanel.setLayout(new GridLayout());
        mNamePanel.setPreferredSize(new Dimension(ATK.TAB_BAR_WIDTH,
                (int) (ATK.TAB_BAR_HEIGHT * 0.8)));
        mNamePanel.setBackground(Color.WHITE);

        mScrollPanel = new JPanel();
        mScrollPanel.setPreferredSize(new Dimension(ATK.TAB_BAR_WIDTH,
                (int) (ATK.TAB_BAR_HEIGHT * 0.1)));
        mScrollPanel.setLayout(null);
        mScrollPanel.setBackground(Color.WHITE);
        
        mTabSelect = new JLabel();

        mScrollPanel.add(mTabSelect);

        mTabBar.add(mNamePanel, BorderLayout.NORTH);
        mTabBar.add(mScrollPanel, BorderLayout.SOUTH);

    }
    
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
        mTabViews.get(mCurrentIndex).setFocusable(focusable);
    }

    class ScrollThread extends Thread {

        private final int DURATION = 50;
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
            int barBeginX = mTabSelect.getX();
            int barEndX = 0;
            int barStep = part * mIndexBarWidth / DURATION;

            int currentViewBeginX = 0;
            int currentViewEndX = 0;
            int lastViewBeginX = 0;
            int lastViewEndX = 0;
            int viewStep = ATK.TAB_VIEW_WIDTH / DURATION;

            TabView currentTabView = mTabViews.get(mSelectIndex);
            currentTabView.setBounds(direction == 0 ? -ATK.TAB_VIEW_WIDTH : ATK.TAB_VIEW_WIDTH, 0,
                    ATK.TAB_VIEW_WIDTH, ATK.TAB_VIEW_HEIGHT);
            mTabView.add(currentTabView);

            TabView lastTabView = mTabViews.get(mLastIndex);

            boolean isReached =false;
            
            // int step = 10;
            switch (direction) {
            case MOVE_RIGHT:
                barEndX = barBeginX + part * mIndexBarWidth;
                currentViewBeginX = ATK.TAB_VIEW_WIDTH;
                lastViewBeginX = 0;
                currentViewEndX = 0;
                lastViewEndX = -currentViewBeginX;
                while (!isReached) {
                    barBeginX += barStep;
                    if (barBeginX > barEndX) {
                        barBeginX = barEndX;
                    }
                 
                    lastViewBeginX -= viewStep;
                    if (lastViewBeginX < lastViewEndX) {
                        lastViewBeginX = lastViewEndX;
                    }
                    
                    currentViewBeginX -= viewStep;
                    if (currentViewBeginX < currentViewEndX) {
                        currentViewBeginX = currentViewEndX;
                    }
                    
                    if (barBeginX == barEndX && lastViewBeginX == lastViewEndX
                            && currentViewBeginX == currentViewEndX) {
                        isReached = true;
                    }

                    mTabSelect.setLocation(barBeginX, 0);
                    lastTabView.setLocation(lastViewBeginX, 0);
                    currentTabView.setLocation(currentViewBeginX, 0);
                    

                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case MOVE_LEFT:
                barEndX = mCurrentIndex * mIndexBarWidth - part * mIndexBarWidth;
                currentViewBeginX = -ATK.TAB_VIEW_WIDTH;
                lastViewBeginX = 0;
                currentViewEndX = 0;
                lastViewEndX = -currentViewBeginX;
                while (!isReached) {
                    barBeginX -= barStep;
                    if (barBeginX < barEndX){
                        barBeginX = barEndX;
                    }
                    
                    lastViewBeginX += viewStep;
                    if (lastViewBeginX > lastViewEndX) {
                        lastViewBeginX = lastViewEndX;
                    }
                    
                    currentViewBeginX += viewStep;
                    if (currentViewBeginX > currentViewEndX) {
                        currentViewBeginX = currentViewEndX;
                    }
                    
                    if (barBeginX == barEndX && lastViewBeginX == lastViewEndX
                            && currentViewBeginX == currentViewEndX) {
                        isReached = true;
                    }
                    
                    mTabSelect.setLocation(barBeginX, 0);
                    lastTabView.setLocation(lastViewBeginX, 0);
                    currentTabView.setLocation(currentViewBeginX, 0);
                    
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

            components[mCurrentIndex].setFont(new Font(mTabStrings.get(mCurrentIndex), 1, 20));
            components[mCurrentIndex].setForeground(Color.BLUE);

            components[mLastIndex].setFont(new Font(mTabStrings.get(mLastIndex), 1, 12));
            components[mLastIndex].setForeground(Color.BLACK);

            mTabView.remove(lastTabView);//remove the last one
            //mTabView.remove(0);

            mLastIndex = mCurrentIndex;
        }
    }

    private void onTabLayout() {
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
            if (!focusable){
                return;
            }
            
            for (int i = 0; i < mTabStrings.size(); i++) {
                if (mTabStrings.get(i).equals(((JLabel) e.getComponent()).getText())) {
                    mSelectIndex = i;
                    onTabLayout();
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
