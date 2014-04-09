package com.xue.aer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.aer.event.ViewLocationChangeListener;
import com.xue.aer.res.AER;

public class MenuView extends Dialog implements MouseListener, ViewLocationChangeListener{

    private static final int SHOW_FLAG = 0;
    private static final int HIDE_FLAG = 1;

    private AnimationThread animationThread;

    private boolean isShow = false;

    private JPanel actionBar;
    private JLabel back;
    


    public MenuView(JFrame owner) {
        super(owner, 1, AER.TOOL_VIEW_HEIGHT);
        // TODO Auto-generated constructor stub
        initView();
    }

    private void initView() {
        setLocation(mContext.getX() + AER.WIDTH, mContext.getY()
                + (AER.HEIGHT - AER.TOOL_VIEW_HEIGHT) / 2);
        this.setVisible(true);
        this.setAlwaysOnTop(true);

        actionBar = new JPanel();
        actionBar.setLayout(new BorderLayout());
        back = new JLabel("back");
        back.setPreferredSize(new Dimension(50, 50));
        back.addMouseListener(this);
        actionBar.add(back, BorderLayout.WEST);
        actionBar.setOpaque(false);

        getLayeredPane().add(actionBar, new Integer(300));
        actionBar.setBounds(0, 0, AER.TOOL_VIEW_WIDTH, 50);
        
    }
    
    public void setLocation(int x,int y){
        super.setLocation(x, y);
    }

    public void showView() {
        if (animationThread != null && animationThread.isAlive()) {
            return;
        }
        System.out.println("show");
        setVisible(true);
        animationThread = new AnimationThread();
        animationThread.setFlag(SHOW_FLAG);
        animationThread.start();

    }

    public void hideView() {
        animationThread = new AnimationThread();
        animationThread.setFlag(HIDE_FLAG);
        animationThread.start();
    }

    private class AnimationThread extends Thread {

        private static final int STEP = 20;

        private int flag;

        public void setFlag(int flag) {
            this.flag = flag;
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

                    setViewBounds(tempX, y, width, height);
                    setDialogWidth(width);

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

                while (width > 1) {

                    tempX += STEP;
                    if (tempX > x + AER.TOOL_VIEW_WIDTH) {
                        tempX = x + AER.TOOL_VIEW_WIDTH;
                    }

                    width -= STEP;
                    if (width < 1) {
                        width = 1;
                    }

                    setViewBounds(tempX, y, width, height);
                    setDialogWidth(width);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                isShow = false;
                setVisible(false);
                break;
            default:
                break;
            }

            packBackground();
        }

    }
    

    public boolean isShow() {
        return isShow;
    }

    public void dispose() {
        super.dispose();
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
        if (e.getSource() == back) {
            hideView();
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

    @Override
    public void locationChaged(Point p) {
        // TODO Auto-generated method stub
        
    }

}
