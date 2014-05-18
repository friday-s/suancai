package com.xue.atk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class SoftwareInfoView extends MenuItemView implements ActionListener {

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
        infoLabel.setText(ATK.SOFTWARE_INFORMATION);
        infoLabel.setBounds(0, 0, mWidth, mHeight);
        
        CButton updateBtn = new CButton(Util.getImageIcon("update_btn_up.png"),Util.getImageIcon("update_btn_down.png"));
        updateBtn.setBounds(20, mHeight-50, 350, 35);
        updateBtn.addActionListener(this);
        
        imagePanel.add(imageLabel);
        infoPanel.add(infoLabel);
        infoPanel.add(updateBtn);

        mPanel.add(imagePanel,BorderLayout.NORTH);
        mPanel.add(infoPanel,BorderLayout.SOUTH);

 
        addContent(mPanel);
 

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 AlertDialog dialog = new AlertDialog(this.getRootPane().getParent(), 400, 250,
	                AlertDialog.MSG_DIALOG);
	        dialog.setMessage(new String[]{"The current version is the latest."});
	        dialog.onCreate();
	        dialog.setVisible(true);
	}

  

}
