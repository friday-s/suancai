package com.xue.atk.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.android.ddmlib.IDevice;
import com.xue.atk.manager.ADBManager;
import com.xue.atk.res.ATK;
import com.xue.atk.service.IDeviceChangedCallBack;

public class BottomBar extends JPanel {

    private JLabel mStateLabel;

    private JComboBox mDevicesComboBox;

    private boolean focusable = true;

    private DefaultComboBoxModel mBoxModel;

    public BottomBar() {
        initUI();
    }

    public void initUI() {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        mDevicesComboBox = new JComboBox();
        // mDevicesComboBox.setBounds(0, 0, 150, 25);
        // mDevicesComboBox.setPreferredSize(new Dimension(150, 25));
        mDevicesComboBox.setMaximumRowCount(10);

        mDevicesComboBox.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.GRAY);
                listBox.setBackground(Color.WHITE);
                listBox.setSelectionBackground(Color.WHITE);
                listBox.setSelectionForeground(Color.BLACK);
                // listBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));
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
        mStateLabel.setText(ATK.DEVICE);
        mStateLabel.setForeground(Color.BLUE);

        mBoxModel = new DefaultComboBoxModel();

        for (IDevice device : ADBManager.getADBManager().getDevices()) {
            mBoxModel.addElement(device);
        }

        mDevicesComboBox.setModel(mBoxModel);
        mDevicesComboBox.addItemListener(mItemListener);

        this.add(mStateLabel);
        this.add(mDevicesComboBox);

        springLayout.putConstraint(SpringLayout.EAST, mDevicesComboBox, 1, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, mDevicesComboBox, 0, SpringLayout.SOUTH,
                this);

        springLayout.putConstraint(SpringLayout.EAST, mStateLabel, -5, SpringLayout.WEST,
                mDevicesComboBox);

        springLayout.putConstraint(SpringLayout.SOUTH, mStateLabel, 0, SpringLayout.SOUTH, this);

        ADBManager.getADBManager().addCallBack(mCallBack);

    }

    private boolean checkExist(IDevice device) {
        for (int i = 0; i < mBoxModel.getSize(); i++) {
            if (device.equals(mBoxModel.getElementAt(i))) {
                return true;
            }
        }
        return false;
    }

    private ItemListener mItemListener = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            // TODO Auto-generated method stub
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ADBManager.getADBManager().setCurrentDevice(
                        (IDevice) mDevicesComboBox.getSelectedItem());
            }
        }
    };

    private IDeviceChangedCallBack mCallBack = new IDeviceChangedCallBack() {

        @Override
        public void deviceConnected(IDevice device) {
            // TODO Auto-generated method stub
            if (!checkExist(device)) {
                mBoxModel.addElement(device);
            }
        }

        @Override
        public void deviceDisonnected(IDevice device) {
            // TODO Auto-generated method stub
            if (checkExist(device)) {
                mBoxModel.removeElement(device);
            }
        }
    };

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        this.focusable = focusable;
    }

}
