package com.xue.atk.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class ReplayView extends CBaseTabView {

    private CButton mRecordBtn;
    private CButton mReplayBtn;
    private JLabel mProgressLabel;

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
            text.setBounds(beginX + ATK.RECORD_LABEL_WIDTH, beginY, ATK.RECORD_LABEL_WIDTH,
                    ATK.RECORD_LABEL_HEIGHT);
            mLeftPanl.add(text);
            beginY += 35;
        }

        
        initCenterView();
    }

    private void initCenterView() {
        mCenterPanl.setLayout(null);

        JLayeredPane pane = new JLayeredPane();
        pane.setLayout(null);
        pane.setBounds(0, 0, mCenterPanl.getWidth(), mCenterPanl.getHeight());
        
        mCenterPanl.add(pane);
        
        
        mProgressLabel = new JLabel();
        ImageIcon icon = Util.getImageIcon("progress_bars1.png");
        mProgressLabel.setIcon(icon);
        mProgressLabel.setBounds((pane.getWidth()-icon.getIconWidth())/2, (pane.getHeight()-icon.getIconHeight())/2, icon.getIconWidth() , icon.getIconHeight());
        mRecordBtn = new CButton("record", "stop");
        mReplayBtn = new CButton("replay", "stop");
        
        pane.add(mProgressLabel,JLayeredPane.DEFAULT_LAYER);
        System.out.println("mCenterPanl.getWidth():"+mCenterPanl.getWidth());
        System.out.println("pane.getWidth():"+pane.getWidth());
        System.out.println("icon.getIconWidth():"+icon.getIconWidth());
       
    }

}
