package com.xue.aer.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.xue.aer.res.AER;

public class BottomBar extends JPanel {

    private static final int USB_CONNECTED = 0;
    private static final int USB_DISCONNECTED = 1;
    private static final String CONNECTED = "connected";
    private static final String DISCONNECTED = "disconnected";

    private JLabel mUsbStateLabel;
    private JLabel mDeviceSNLabel;

    public BottomBar() {
        initUI();
    }

    public void initUI() {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        mUsbStateLabel = new JLabel();
        mDeviceSNLabel = new JLabel();

        switch (checkUSBState()) {
        case USB_CONNECTED:
            mDeviceSNLabel.setVisible(true);
            mDeviceSNLabel.setText(AER.DEVICE_SN);
            mUsbStateLabel.setText(AER.USB_STATE + CONNECTED);
            mUsbStateLabel.setForeground(Color.GREEN);
            break;
        case USB_DISCONNECTED:
            mDeviceSNLabel.setVisible(false);
            mUsbStateLabel.setText(AER.USB_STATE + DISCONNECTED);
            mUsbStateLabel.setForeground(Color.RED);
            break;
        default:
            break;
        }

        this.add(mUsbStateLabel);
        this.add(mDeviceSNLabel);

        springLayout.putConstraint(SpringLayout.EAST, mUsbStateLabel, 1, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, mUsbStateLabel, 0, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, mDeviceSNLabel, 1, SpringLayout.WEST,
                mUsbStateLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, mDeviceSNLabel, 0, SpringLayout.SOUTH, this);

    }

    private int checkUSBState() {
        return 1;
    }
}