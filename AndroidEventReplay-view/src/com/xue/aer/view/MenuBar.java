package com.xue.aer.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;


public class MenuBar extends JPanel implements MouseListener {

    private static final int TOOL_BAR_OPTION_WIDTH = 26;
    private static final int TOOL_BAR_OPTION_HEIGHT = 26;

    private JFrame mContext;

    private ArrayList<MenuItem> mMenuItems;

    private SpringLayout springLayout;

    private AnimationThread animationThread;

    public MenuBar(JFrame jframe) {
        // setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        mContext = jframe;
        setLayout(null);
        init();
    }

    private void init() {

        mMenuItems = new ArrayList<MenuItem>();

        springLayout = new SpringLayout();
        setLayout(springLayout);
    }

    public void addMenuItem(MenuItem item) {

        mMenuItems.add(item);
        this.add(item.getWidget());
        item.addMouseListener(this);

        if (mMenuItems.size() == 1) {
            springLayout.putConstraint(SpringLayout.EAST, mMenuItems.get(0).getWidget(), -3,
                    SpringLayout.EAST, this);
        } else {
            springLayout.putConstraint(SpringLayout.EAST, mMenuItems.get(mMenuItems.size() - 1)
                    .getWidget(), -5, SpringLayout.WEST, mMenuItems.get(mMenuItems.size() - 2)
                    .getWidget());
        }

    }

    private void attachBarForm(MenuItem item) {
        MenuBarForm form = new MenuBarForm(this);
        mContext.getLayeredPane().add(form, new Integer(200));
        form.attachItemView(item.getMenuItemView());
        form.showItemView();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        for (MenuItem item : mMenuItems) {
            if (item.getWidget() == e.getSource()) {
                System.out.println("disply form");
                attachBarForm(item);
            }
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

    class MenuBarForm extends JLayeredPane {

        private MenuBar bar;
        private int mFormWidth;
        private int mFormHeight;

        private MenuItemView mView;

        public MenuBarForm(MenuBar bar) {

            this.bar = bar;
            mFormWidth = bar.mContext.getWidth();
            mFormHeight = bar.mContext.getHeight();

            this.setLayout(null);
            this.setBounds(0, 0, mFormWidth, mFormHeight);
            this.setVisible(true);
            JLabel bg = new JLabel();
            bg.setIcon(Util.scaleImage(Util.getImageIcon("shadow.png"), mFormWidth, mFormHeight));
            bg.setBounds(0, 0, mFormWidth, mFormHeight);
            this.add(bg, JLayeredPane.DEFAULT_LAYER);

        }

        public void showItemView() {
            animationThread = new AnimationThread();
            animationThread.setFlag(SHOW_FLAG);
            animationThread.setItemView(mView);
            animationThread.start();
        }

        public void hideItemView() {

        }

        public void attachItemView(MenuItemView view) {
            this.mView = view;
            this.add(mView, JLayeredPane.PALETTE_LAYER);
        }
    }

    private static final int SHOW_FLAG = 0;
    private static final int HIDE_FLAG = 1;

    private class AnimationThread extends Thread {

        private static final int STEP = 20;

        private int flag;
        private MenuItemView view;

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public void setItemView(MenuItemView view) {
            this.view = view;
        }

        public void run() {

            int width = getWidth();
            int height = getHeight();
            int x = getX();
            int tempX = x;
            int y = getY();

            switch (flag) {
            case SHOW_FLAG:

                while (width < AER.TOOL_VIEW_WIDTH) {

                    tempX -= STEP;
                    if (tempX < x - AER.TOOL_VIEW_WIDTH) {
                        tempX = x - AER.TOOL_VIEW_WIDTH;
                    }

                    width += STEP;
                    if (width > AER.TOOL_VIEW_WIDTH) {
                        width = AER.TOOL_VIEW_WIDTH;
                    }

                    view.setBounds(tempX, y, width, height);
                    // view.setDialogWidth(width);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                break;
            case HIDE_FLAG:

                while (width > 1) {

                    tempX += STEP;
                    if (tempX > x + AER.TOOL_VIEW_WIDTH) {
                        tempX = x + AER.TOOL_VIEW_WIDTH;
                    }

                    width -= STEP;
                    if (width < 1) {
                        width = 1;
                    }

                    view.setBounds(tempX, y, width, height);
                    // view.setDialogWidth(width);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                break;
            default:
                break;
            }

        }

    }

}
