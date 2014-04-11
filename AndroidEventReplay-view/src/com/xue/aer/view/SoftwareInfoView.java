package com.xue.aer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.aer.res.AER;
import com.xue.aer.util.Util;

public class SoftwareInfoView extends MenuItemView {

    private JPanel mPanel;

    public SoftwareInfoView() {
        super();
        initView();
    }

    public void initView() {

        mPanel = new JPanel();
        mPanel.setLayout(new BorderLayout());
        

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(null);
        imagePanel.setPreferredSize(new Dimension(mWidth,(int) (mHeight*0.2)));
        

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(null);
        infoPanel.setPreferredSize(new Dimension(mWidth,mHeight));


        JLabel imageLabel = new JLabel();
        ImageIcon icon = Util.getImageIcon("android.png");
        imageLabel.setIcon(icon);
      
        imageLabel.setBounds((mWidth-icon.getIconWidth())/2,0,icon.getIconWidth(), icon.getIconHeight());
        

        JLabel infoLabel = new JLabel();
        infoLabel.setVerticalAlignment(JLabel.CENTER);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setText(AER.SOFTWARE_INFORMATION);
        infoLabel.setBounds(0, 0, mWidth, mHeight);
        
        

        imagePanel.add(imageLabel);
        infoPanel.add(infoLabel);

        mPanel.add(imagePanel,BorderLayout.NORTH);
        mPanel.add(infoPanel,BorderLayout.SOUTH);

 
        addContent(mPanel);
 

    }

  

}
