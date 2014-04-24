package com.xue.atk.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.xue.atk.res.ATK;

public class BottomBar extends JPanel {

    private static final int USB_CONNECTED = 1;
    private static final int USB_DISCONNECTED = 0;
    private static final String CONNECTED = "connected";
    private static final String DISCONNECTED = "disconnected";

    private JLabel mStateLabel;
 
    
    private JComboBox mDevicesComboBox;
    
    private boolean focusable = true;

    public BottomBar() {
        initUI();
    }

    public void initUI() {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        
        
        mDevicesComboBox = new JComboBox();
        mDevicesComboBox.setBounds(0, 0, 150,25);
        mDevicesComboBox.setMaximumRowCount(10);
    

        mDevicesComboBox.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.GRAY);
                listBox.setBackground(Color.WHITE);
                listBox.setSelectionBackground(Color.WHITE);
                listBox.setSelectionForeground(Color.BLACK);
            }

            protected JButton createArrowButton() {
                JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, Color.WHITE,
                        Color.WHITE, UIManager.getColor("ComboBox.buttonDarkShadow"), Color.WHITE) {
                    public void paint(Graphics g) {
                        super.paint(g);
                        int h = getSize().width;
                        int w = getSize().height;
                        g.setColor(Color.WHITE);
                        g.drawLine(0, h - 1, w - 1, h - 1);
                        g.drawLine(w - 1, h - 1, w - 1, 0);
                    }
                };
                button.setName("ComboBox.arrowButton");
                return button;
            }
        });
        
        

        mStateLabel = new JLabel();


        switch (checkUSBState()) {
        case USB_CONNECTED:

            mDevicesComboBox.setVisible(true);
           // mDeviceSNLabel.setText(ATK.DEVICE_SN);
            mStateLabel.setText(CONNECTED);
            mStateLabel.setForeground(Color.GREEN);
            break;
        case USB_DISCONNECTED:

            mDevicesComboBox.setVisible(false);
            mStateLabel.setText(ATK.USB_STATE + DISCONNECTED);
            mStateLabel.setForeground(Color.RED);
            break;
        default:
            break;
        }

        this.add(mStateLabel);
        this.add(mDevicesComboBox);

        springLayout.putConstraint(SpringLayout.EAST, mStateLabel, 1, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, mStateLabel, 0, SpringLayout.SOUTH, this);
    
        springLayout.putConstraint(SpringLayout.EAST, mDevicesComboBox, 1, SpringLayout.WEST,
                mStateLabel);
        
        springLayout.putConstraint(SpringLayout.SOUTH, mDevicesComboBox, 0, SpringLayout.SOUTH, this);

    }
    
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
    }
    

    private int checkUSBState() {
        return 1;
    }
}
