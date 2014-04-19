package com.xue.atk.view;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.xue.atk.util.Util;

public class MenuBar extends JPanel implements MouseListener {

    private static final int SHOW_FLAG = 0;
    private static final int HIDE_FLAG = 1;

    private JFrame mContext;

    private ArrayList<MenuItem> mMenuItems;

    private SpringLayout springLayout;

    private MenuBarForm form;

    private boolean focusable = true;

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

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
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

        form = new MenuBarForm(this);

        mContext.getLayeredPane().add(form, new Integer(200));
        setLayerFocusable(JLayeredPane.DEFAULT_LAYER,false);

        form.attachItemView(item.getMenuItemView());
        form.showItemView();

    }

    private void setLayerFocusable(int layer,boolean focusable) {
        Component[] components = mContext.getLayeredPane().getComponentsInLayer(layer);
        for (Component c : components) {
            c.setFocusable(focusable);
        }
    }
    

    private void disposeBarForm() {

        if (form != null) {

            mContext.getLayeredPane().remove(form);

            form = null;
            mContext.getLayeredPane().repaint();

            setLayerFocusable(JLayeredPane.DEFAULT_LAYER,true);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    	 if (!focusable) {
             return;
         }
    	for (MenuItem item : mMenuItems) {
            if (item.getWidget() == e.getSource()) {
            	item.getWidget().pressDown();
            }
    	}
    	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        if (!focusable) {
            return;
        }

        for (MenuItem item : mMenuItems) {
            if (item.getWidget() == e.getSource()) {
            	item.getWidget().pressUp();
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
        private JLabel bg;

        private int mFormWidth;
        private int mFormHeight;

        private CAnimation mAnimation;

        private MenuItemView mView;

        public MenuBarForm(MenuBar bar) {

            this.bar = bar;
            mFormWidth = bar.mContext.getWidth();
            mFormHeight = bar.mContext.getHeight();

            this.setLayout(null);
            this.setBounds(0, 0, mFormWidth, mFormHeight);
            this.setVisible(true);
            bg = new JLabel();
            bg.setIcon(Util.scaleImage(Util.getImageIcon("shadow.png"), mFormWidth, mFormHeight));
            bg.setBounds(0, 0, mFormWidth, mFormHeight);

            this.add(bg, JLayeredPane.DEFAULT_LAYER);

            mAnimation = new CAnimation();

        }

        public void showItemView() {

            mAnimation.setFlag(SHOW_FLAG);
            mAnimation.setItemView(mView);
            new Thread(mAnimation).start();

        }

        public void hideItemView() {

            mAnimation.setFlag(HIDE_FLAG);
            mAnimation.setItemView(mView);
            new Thread(mAnimation).start();
        }

        public void attachItemView(MenuItemView view) {
            this.mView = view;
            mView.setLocation(mFormWidth, (mFormHeight - mView.getHeight()) / 2);
            mView.addMouseListener(mMouseListener);
            this.add(mView, JLayeredPane.PALETTE_LAYER);

        }

        private void removeItemView() {
            this.remove(mView);
            this.remove(bg);
            mView.removeMouseListener(mMouseListener);
        }

        private MouseListener mMouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                mView.pressBack();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                System.out.println("release the back");
                mView.releaseBack();
                hideItemView();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        };

        class CAnimation implements Runnable {

            private static final int STEP = 20;

            private int flag;
            private MenuItemView view;

            private boolean isShow;

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public void setItemView(MenuItemView view) {
                this.view = view;
            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = view.getX() + 5;
                int y = view.getY();
                int endX = 0;

                switch (flag) {
                case SHOW_FLAG:

                    endX = x - view.getWidth();
                    while (x > endX) {

                        x -= STEP;
                        if (x < endX) {
                            x = endX;
                        }
                        view.setLocation(x, y);

                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    isShow = true;
                    break;
                case HIDE_FLAG:
                    endX = x + view.getWidth();
                    while (x < endX) {
                        x += STEP;
                        if (x > endX) {
                            x = endX;
                        }
                        view.setLocation(x, y);

                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    disposeBarForm();
                    removeItemView();

                    break;
                default:
                    break;
                }

            }

            public boolean isShow() {
                return isShow;
            }
        }
    }
}
