package com.xue.atk.view;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.xue.atk.res.ATK;

public class ReplayView extends CBaseTabView {

	public ReplayView(String tabName) {
		super(tabName);
		initView();
	}
	
	 public void initView() {
	        
	        
	        int beginX = 20;
	        int beginY = 20;
	        mLeftPanl.setLayout(null);
	        for (String s : ATK.RECORD_LEFT_CONTENT) {
	            JLabel label = new JLabel(s);
	            label.setBounds(beginX, beginY, ATK.RECORD_LABEL_WIDTH, ATK.RECORD_LABEL_HEIGHT);
	           // label.setLocation(beginX, beginY);
	            mLeftPanl.add(label);
	            JTextField text = new JTextField();
	            text.setBounds(beginX+ATK.RECORD_LABEL_WIDTH, beginY, ATK.RECORD_LABEL_WIDTH, ATK.RECORD_LABEL_HEIGHT);
	            mLeftPanl.add(text);
	            beginY+=35;
	        }

	    }

}
