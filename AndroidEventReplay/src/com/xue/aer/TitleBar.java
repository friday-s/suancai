package com.xue.aer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.xue.res.AER;

public class TitleBar extends JPanel {

    private static final int TITLE_BAR_ICON_WIDTH = 20;
    private static final int TITLE_BAR_ICON_HEIGHT = 20;

    private static final int TITLE_BAR_WIDTH = AER.WIDTH;
    private static final int TITLE_BAR_HEIGHT = 25;
    private static final int TITLE_BAR_PAD_HEIGHT=(TITLE_BAR_HEIGHT-TITLE_BAR_ICON_HEIGHT)/2;
    
    private JFrame mContext;
    // private Image mTitleIcon;
    private JLabel mTitleIcon;
    private JLabel mTitleName;
    private JLabel mCloseIcon;

    private boolean isDragged = false;
    private Point loc = null;
    private Point tmp = null;
    

    public TitleBar(JFrame context) {
        this.mContext = context;
        initView();
    }

    public TitleBar(JFrame context, ImageIcon icon, String name) {
        this.mContext = context;
        initView();
        setTitleIcon(icon);
        setTitleName(name);
    }

    public TitleBar(JFrame context, ImageIcon icon, String name,ImageIcon closeIcon) {
        this.mContext = context;
        initView();
        setTitleIcon(icon);
        setTitleName(name);
        setCloseIcon(closeIcon);
    }

    private void initView() {
        mTitleIcon = new JLabel();
        mTitleName = new JLabel();
        mCloseIcon = new JLabel();
        
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        setVisible(true);
        setBounds(0, 0, TITLE_BAR_WIDTH, TITLE_BAR_HEIGHT);
        this.setBackground(Color.CYAN);

        add(mTitleIcon);
        add(mTitleName);
        add(mCloseIcon);

        
        springLayout.putConstraint(SpringLayout.WEST, mTitleIcon, 1,
                SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.WEST, mTitleName, 1,
                SpringLayout.EAST, mTitleIcon);
        springLayout.putConstraint(SpringLayout.EAST, mCloseIcon,-2,
                SpringLayout.EAST, this);
        
        springLayout.putConstraint(SpringLayout.NORTH, mTitleIcon,TITLE_BAR_PAD_HEIGHT,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.NORTH, mTitleName,TITLE_BAR_PAD_HEIGHT,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.NORTH, mCloseIcon,TITLE_BAR_PAD_HEIGHT,
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
                    loc = new Point(mContext.getLocation().x + e.getX() - tmp.x,
                            mContext.getLocation().y + e.getY() - tmp.y);
                    mContext.setLocation(loc);
                }
            }
        });
    }
    
    public void setCloseIcon(ImageIcon icon){
        icon.setImage(icon.getImage().getScaledInstance(TITLE_BAR_ICON_WIDTH,
                TITLE_BAR_ICON_HEIGHT, Image.SCALE_DEFAULT));
        mCloseIcon.setIcon(icon);
    }

    public void setTitleName(String name) {
        mTitleName.setText(name);
    }

    public void setTitleIcon(ImageIcon icon) {
        icon.setImage(icon.getImage().getScaledInstance(TITLE_BAR_ICON_WIDTH,
                TITLE_BAR_ICON_HEIGHT, Image.SCALE_DEFAULT));
        mTitleIcon.setIcon(icon);
    }
}
