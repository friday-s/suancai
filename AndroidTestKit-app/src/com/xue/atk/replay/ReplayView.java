package com.xue.atk.replay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.android.ddmlib.IDevice;
import com.xue.atk.file.EventFile;
import com.xue.atk.manager.ADBManager;
import com.xue.atk.manager.FileScannerManager;
import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;
import com.xue.atk.view.AlertDialog;
import com.xue.atk.view.BaseList;
import com.xue.atk.view.CBaseTabView;
import com.xue.atk.view.CButton;
import com.xue.atk.view.CellPanel;
import com.xue.atk.view.ListSource;
import com.xue.atk.view.ProgressBar;

public class ReplayView extends CBaseTabView implements ItemListener, ActionListener {

    private static final int DIVIDE_LINE_WIDTH = 70;
    private static final int DIVIDE_LINE_HEIGHT = 1;

    public static final String DEVICE_TMP_PATH = File.separator + "data" + File.separator + "local"
            + File.separator + "tmp" + File.separator;
    public static final String LIBS_PATH = "." + File.separator + "libs" + File.separator;
    public static final String EVENT_PATH = File.separator + "sdcard" + File.separator + "events";

    private static final int PROGRESSBAR_SIDE_LENGHT = 25;

    private CButton mRecordBtn;
    private CButton mReplayBtn;

    private ImageIcon mRecordIconUp;
    private ImageIcon mRecordIconDown;
    private ImageIcon mReplayIconUp;
    private ImageIcon mReplayIconDown;
    private ImageIcon mStopIconUp;
    private ImageIcon mStopIconDown;

    private JLabel mDivideLineLabel;
    private ProgressBar mProgress;

    private JComboBox mProjectComboBox;

    private ListSource mLListSource;
    private BaseList mLTransferList;

    private ListSource mRListSource;
    private BaseList mRTransferList;

    private boolean isRunning;

    private Thread mRecordThread;
    private Thread mReplayThread;
    
    private Clock mClock;
    private JLabel mCurrentEvent;
    private JLabel mCurrentEventTimes;
    
    private JCheckBox mBackgroundCheckbox;
    private JCheckBox mMultideviceCheckbox;

    public ReplayView(String tabName) {
        super(tabName);
        initView();
    }

    public void initView() {

        mRecordIconUp = Util.getImageIcon("record_btn.png");
        mRecordIconDown = Util.getImageIcon("record_btn_down.png");
        mReplayIconUp = Util.getImageIcon("replay_btn.png");
        mReplayIconDown = Util.getImageIcon("replay_btn_down.png");
        mStopIconUp = Util.getImageIcon("stop_up.png");
        mStopIconDown = Util.getImageIcon("stop_down.png");

        initLeftView();
        initCenterView();
        initRightView();
    }

    private void initLeftView() {
        mLeftPanel.setLayout(null);
        JLabel label = new JLabel("Project:");
        label.setBounds(0, 0, 80, 25);

        mLeftPanel.add(label);

        mProjectComboBox = new JComboBox();
        mProjectComboBox.setBounds(label.getWidth(), 0, mLeftPanel.getWidth() - label.getWidth(),
                25);
        mProjectComboBox.setMaximumRowCount(20);
        mProjectComboBox.addItemListener(this);

        mProjectComboBox.setUI(new BasicComboBoxUI() {
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

        mLeftPanel.add(mProjectComboBox);

        mProjectComboBox.setModel(new DefaultComboBoxModel(FileScannerManager.getManager()
                .getProjectList()));
        mProjectComboBox.setSelectedItem(0);

        mLListSource = new ListSource();

        Object obj = mProjectComboBox.getSelectedItem();
        List<Object> source = FileScannerManager.getManager().getEventList(
                obj == null ? null : obj.toString());
        mLListSource.setSources(source);
        mLTransferList = new BaseList();
        mLTransferList.setBounds(0, 0, mLeftPanel.getWidth(),
                mLeftPanel.getHeight() - label.getHeight());

        mLTransferList.setCellIface(new CellPanel());
        mLTransferList.setSource(mLListSource);

        JScrollPane scrollpane = new JScrollPane(mLTransferList);
        scrollpane.setBounds(0, mProjectComboBox.getHeight(), mLeftPanel.getWidth(),
                mLeftPanel.getHeight() - mProjectComboBox.getHeight());
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setBorder(null);
        mLeftPanel.add(scrollpane);

        JPopupMenu pop = new JPopupMenu();
        pop.setBackground(Color.WHITE);
        pop.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225)));

        for (String s : ATK.REPLAY_LEFT_VIEW_POP) {
            JMenuItem item = new JMenuItem(s);
            item.addActionListener(this);
            item.setBackground(Color.WHITE);
            pop.add(item);
        }

        mLTransferList.setRightClickPopup(pop);

    }

    private void initCenterView() {
        mCenterPanel.setLayout(null);

        JLayeredPane pane = new JLayeredPane();
        pane.setLayout(null);
        pane.setBounds(0, 0, mCenterPanel.getWidth(), mCenterPanel.getHeight());

        mCenterPanel.add(pane);

      /*  mDivideLineLabel = new JLabel();
        ImageIcon divideIcon = Util.scaleImage(Util.getImageIcon("tabbar_select.png"),
                mCenterPanel.getWidth(), DIVIDE_LINE_HEIGHT);
        mDivideLineLabel.setIcon(divideIcon);
        mDivideLineLabel.setBounds((pane.getWidth() - divideIcon.getIconWidth()) / 2,
                (pane.getHeight() - divideIcon.getIconHeight()) / 2, divideIcon.getIconWidth(),
                divideIcon.getIconHeight());*/

        mRecordBtn = new CButton(mRecordIconUp, mRecordIconDown);
        mRecordBtn.setBounds(0, pane.getHeight() - mRecordIconUp.getIconHeight()-5,
                mRecordIconUp.getIconWidth(), mRecordIconUp.getIconHeight());
        mRecordBtn.addActionListener(this);

        mReplayBtn = new CButton(mReplayIconUp, mReplayIconDown);
        mReplayBtn.setBounds(pane.getWidth() - mReplayIconUp.getIconWidth(), pane.getHeight()
                - mReplayIconUp.getIconHeight()-5, mReplayIconUp.getIconWidth(),
                mReplayIconUp.getIconHeight());
        mReplayBtn.addActionListener(this);

       // pane.add(mDivideLineLabel, JLayeredPane.DEFAULT_LAYER);

        pane.add(mRecordBtn, JLayeredPane.PALETTE_LAYER);
        pane.add(mReplayBtn, JLayeredPane.PALETTE_LAYER);

        /* load images */
        ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
        for (int i = 1; i < 13; i++) {
            ImageIcon icon = Util.scaleImage(Util.getImageIcon("progress_bars" + i + ".png"),
                    PROGRESSBAR_SIDE_LENGHT, PROGRESSBAR_SIDE_LENGHT);
            icons.add(icon);
        }

        mProgress = new ProgressBar();
        mProgress.setProgressImages(icons);
        mProgress.setLocation((pane.getWidth()-mProgress.getWidth())/2, pane.getHeight()-mProgress.getHeight()-5);

        pane.add(mProgress);
        
        
        mClock = new Clock(pane.getWidth(), 60);
        mClock.setLocation(0, 0);
        pane.add(mClock, JLayeredPane.PALETTE_LAYER);
        
        mCurrentEvent = new JLabel();
        mCurrentEvent.setBounds(0, mClock.getHeight(), pane.getWidth(), 50);
        mCurrentEventTimes = new JLabel();
        mCurrentEventTimes.setBounds(0, mClock.getHeight()+mCurrentEvent.getHeight(), pane.getWidth(), 50);
        pane.add(mCurrentEvent, JLayeredPane.PALETTE_LAYER);
        pane.add(mCurrentEventTimes, JLayeredPane.PALETTE_LAYER);

        
        mMultideviceCheckbox = new JCheckBox("  Multi-device Replay");
        mMultideviceCheckbox.setBackground(Color.WHITE);
        mMultideviceCheckbox.setFocusPainted(false);
        mMultideviceCheckbox.setBounds(0, mProgress.getY()-40, pane.getWidth(), 30);
        
        mBackgroundCheckbox = new JCheckBox("  Background Replay");
        mBackgroundCheckbox.setBackground(Color.WHITE);
        mBackgroundCheckbox.setFocusPainted(false);
        mBackgroundCheckbox.setBounds(0, mMultideviceCheckbox.getY()-30, pane.getWidth(), 30);
        
        
        pane.add(mMultideviceCheckbox, JLayeredPane.PALETTE_LAYER);
        pane.add(mBackgroundCheckbox, JLayeredPane.PALETTE_LAYER);
        
    }

    private void initRightView() {
        mRightPanel.setLayout(null);

        JLabel label = new JLabel("Replay Event:");
        label.setBounds(0, 0, 120, 25);
        mRightPanel.add(label);

        mRListSource = new ListSource();

        mRTransferList = new BaseList();
        mRTransferList.setBounds(0, 0, mRightPanel.getWidth(),
                mRightPanel.getHeight() - label.getHeight());

        mRTransferList.setCellIface(new RCellPanel());
        mRTransferList.setSource(mRListSource);

        JScrollPane scrollpane = new JScrollPane(mRTransferList);
        scrollpane.setBounds(0, label.getHeight(), mRightPanel.getWidth(), mRightPanel.getHeight()
                - label.getHeight());
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setBorder(null);

        mRightPanel.add(scrollpane);

        JPopupMenu pop = new JPopupMenu();

        pop.setBackground(Color.WHITE);
        pop.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225)));

        for (String s : ATK.REPLAY_RIGHT_VIEW_POP) {
            JMenuItem item = new JMenuItem(s);
            item.addActionListener(this);
            item.setBackground(Color.WHITE);

            pop.add(item);
        }

        mRTransferList.setRightClickPopup(pop);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getStateChange() == ItemEvent.SELECTED) {

            List<Object> source = FileScannerManager.getManager().getEventList(
                    mProjectComboBox.getSelectedItem().toString());

            mLListSource.setSources(source);

            mLListSource.notifySourceRefreshEvent();
        }
    }

    private void showDialog(String msg) {
        AlertDialog dialog = new AlertDialog(this.getRootPane().getParent(), 400, 250,
                AlertDialog.MSG_DIALOG);
        dialog.setMessage(msg);
        dialog.onCreate();
        dialog.setVisible(true);
    }

    private Runnable mRecordRunnable = new Runnable() {
        public void run() {
            if (ADBManager.getManager().execADBCommand(
                    "push " + LIBS_PATH + "event_record" + " " + DEVICE_TMP_PATH) < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                mRecordBtn.doClick();
                return;
            }
            System.out.println("push file");

            if (ADBManager.getManager().execADBCommand(
                    "shell chmod 555 " + DEVICE_TMP_PATH + "event_record") < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                mRecordBtn.doClick();
                return;
            }
            System.out.println("chmod file");

            if (ADBManager.getManager().execADBCommand(
                    "shell " + DEVICE_TMP_PATH + "event_record "
                            + ADBManager.getManager().getCurrentDeviceEventNum()) < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                mRecordBtn.doClick();
                return;
            }
            System.out.println("execute");
        }
    };

    private Runnable mReplayRunnable = new Runnable() {
        public void run() {

            if (mMultideviceCheckbox.isSelected()) {
                /* multi background */
                if (mBackgroundCheckbox.isSelected()) {
                    final EventFile event = (EventFile) mRListSource.getAllCell().get(0);
                    for (final IDevice device : ADBManager.getManager().getDevices()) {

                        if (ADBManager.getManager().execADBCommand(device,
                                "push " + LIBS_PATH + "event_replay_bg" + " " + DEVICE_TMP_PATH) < 0) {
                            showDialog(ADBManager.getManager().getErrorMsg());
                            mReplayBtn.doClick();
                            return;
                        }

                        if (ADBManager.getManager().execADBCommand(device,
                                "shell chmod 555 " + DEVICE_TMP_PATH + "event_replay_bg") < 0) {
                            showDialog(ADBManager.getManager().getErrorMsg());
                            mReplayBtn.doClick();
                            return;
                        }

                        if (ADBManager.getManager().execADBCommand(device,
                                "push " + event.getCompletePath() + " " + EVENT_PATH) < 0) {
                            showDialog(ADBManager.getManager().getErrorMsg());
                            mReplayBtn.doClick();
                            return;
                        }

                    }

                    for (final IDevice device : ADBManager.getManager().getDevices()) {
                        new Thread() {
                            public void run() {
                                if (ADBManager.getManager().execADBCommand(
                                        device,
                                        "shell "
                                                + DEVICE_TMP_PATH
                                                + "event_replay_bg "
                                                + ADBManager.getManager()
                                                        .getCurrentDeviceEventNum() + " "
                                                + event.getTime() + " &") < 0) {
                                    showDialog(ADBManager.getManager().getErrorMsg());
                                    mReplayBtn.doClick();
                                    return;
                                }
                            }
                        }.start();
                    }

                } else {/* multi foreground */

                    for (int i = 0; i < mRListSource.getAllCell().size() && isRunning; i++) {

                        final EventFile event = (EventFile) mRListSource.getAllCell().get(i);
                        mCurrentEvent.setText(ATK.CURRENT_EVENT+event.getName());
                        /* push file */
                        for (final IDevice device : ADBManager.getManager().getDevices()) {

                            if (ADBManager.getManager().execADBCommand(device,
                                    "push " + LIBS_PATH + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
                                showDialog(ADBManager.getManager().getErrorMsg());
                                mReplayBtn.doClick();
                                return;
                            }

                            if (ADBManager.getManager().execADBCommand(device,
                                    "shell chmod 555 " + DEVICE_TMP_PATH + "event_replay") < 0) {
                                showDialog(ADBManager.getManager().getErrorMsg());
                                mReplayBtn.doClick();
                                return;
                            }

                            if (ADBManager.getManager().execADBCommand(device,
                                    "push " + event.getCompletePath() + " " + EVENT_PATH) < 0) {
                                showDialog(ADBManager.getManager().getErrorMsg());
                                mReplayBtn.doClick();
                                return;
                            }
                        }

                        /* begin multi foreground replay */
                        for (int j = 0; j < event.getTime() && isRunning; j++) {

                            mCurrentEventTimes.setText(ATK.CURRENT_TIME + String.valueOf(j + 1));

                            List<Thread> threads = new ArrayList<Thread>();
                            for (final IDevice device : ADBManager.getManager().getDevices()) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        if (ADBManager.getManager().execADBCommand(
                                                device,
                                                "shell "
                                                        + DEVICE_TMP_PATH
                                                        + "event_replay "
                                                        + ADBManager.getManager()
                                                                .getCurrentDeviceEventNum()) < 0) {
                                            showDialog(ADBManager.getManager().getErrorMsg());
                                            mReplayBtn.doClick();
                                            return;
                                        }
                                    }
                                };
                                thread.start();
                                threads.add(thread);
                            }

                            for (Thread thread : threads) {
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

            } else {

                /* single background replay */
                if (mBackgroundCheckbox.isSelected()) {
                    if (ADBManager.getManager().execADBCommand(
                            "push " + LIBS_PATH + "event_replay_bg" + " " + DEVICE_TMP_PATH) < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    if (ADBManager.getManager().execADBCommand(
                            "shell chmod 555 " + DEVICE_TMP_PATH + "event_replay_bg") < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    final EventFile event = (EventFile) mRListSource.getAllCell().get(0);
                    if (ADBManager.getManager().execADBCommand(
                            "push " + event.getCompletePath() + " " + EVENT_PATH) < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    if (ADBManager.getManager().execADBCommand(
                            "shell " + DEVICE_TMP_PATH + "event_replay_bg "
                                    + ADBManager.getManager().getCurrentDeviceEventNum() + " "
                                    + event.getTime() + " &") < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                } else { /* single foreground replay */
                    if (ADBManager.getManager().execADBCommand(
                            "push " + LIBS_PATH + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    if (ADBManager.getManager().execADBCommand(
                            "shell chmod 555 " + DEVICE_TMP_PATH + "event_replay") < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    for (int i = 0; i < mRListSource.getAllCell().size() && isRunning; i++) {
                        final EventFile event = (EventFile) mRListSource.getAllCell().get(i);
                        
                        mCurrentEvent.setText(ATK.CURRENT_EVENT+event.getName());
                        
                        if (ADBManager.getManager().execADBCommand(
                                "push " + event.getCompletePath() + " " + EVENT_PATH) < 0) {
                            showDialog(ADBManager.getManager().getErrorMsg());
                            mReplayBtn.doClick();
                            return;
                        }

                        for (int j = 0; j < event.getTime() && isRunning; j++) {

                            mCurrentEventTimes.setText(ATK.CURRENT_TIME + String.valueOf(j + 1));

                            if (ADBManager.getManager().execADBCommand(
                                    "shell " + DEVICE_TMP_PATH + "event_replay "
                                            + ADBManager.getManager().getCurrentDeviceEventNum()) < 0) {
                                showDialog(ADBManager.getManager().getErrorMsg());
                                mReplayBtn.doClick();
                                return;
                            }
                        }
                    }
                }
            }

            mReplayBtn.setIconDrawable(mReplayIconUp, mReplayIconDown);
            mProgress.stop();
            mClock.stopTiming();
            isRunning = false;
            mRecordBtn.setEnabled(true);

        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (!focusable) {
            return;
        }

        if (e.getSource().equals(mRecordBtn) && mRecordBtn.isEnabled()) {
            if (ADBManager.getManager().getCurrentDevice() == null) {

                showDialog(ATK.DEVICE_NOT_FOUND_CONTENT);

            } else {
                if (isRunning) {

                    mRecordBtn.setIconDrawable(mRecordIconUp, mRecordIconDown);

                    mProgress.stop();
                    isRunning = false;

                    mReplayBtn.setEnabled(true);
                    ADBManager.getManager().terminateADBCommand();
                    mClock.stopTiming();

                    SaveDialog dialog = new SaveDialog(this.getRootPane().getParent(), 400, 250);
                    dialog.onCreate();
                    dialog.setVisible(true);

                } else {

                    mRecordBtn.setIconDrawable(mStopIconUp, mStopIconDown);
                    mReplayBtn.setEnabled(false);
                    isRunning = true;
                    mCurrentEvent.setText("");
                    mCurrentEventTimes.setText("");

                    mProgress.start();
                    mClock.startTiming();
                    new Thread(mRecordRunnable).start();

                }
            }

            return;
        }
        if (e.getSource().equals(mReplayBtn) && mReplayBtn.isEnabled()) {

            if (ADBManager.getManager().getCurrentDevice() == null) {

                showDialog(ATK.DEVICE_NOT_FOUND_CONTENT);

            } else {
                if (isRunning) {
                    mReplayBtn.setIconDrawable(mReplayIconUp, mReplayIconDown);
                    mProgress.stop();
                    isRunning = false;

                    mRecordBtn.setEnabled(true);
                    ADBManager.getManager().terminateADBCommand();
                    mClock.stopTiming();

                } else {

                    if (mRListSource.getAllCell().size() < 1) {
                        showDialog(ATK.ERROR_REPLAY_EVENT_EMPTY);
                        return;
                    }

                    mReplayBtn.setIconDrawable(mStopIconUp, mStopIconDown);
                    mRecordBtn.setEnabled(false);
                    isRunning = true;

                    mProgress.start();
                    mClock.startTiming();
                    new Thread(mReplayRunnable).start();
                }
            }

            return;

        }

        if (e.getActionCommand().equals(ATK.REPLAY_LEFT_VIEW_POP[0])) {
            Object o = mLTransferList.getSelectCell();
            mRListSource.addCell(((EventFile) o).clone());
            mRListSource.notifySourceRefreshEvent();
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_LEFT_VIEW_POP[1])) {
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_LEFT_VIEW_POP[2])) {
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_RIGHT_VIEW_POP[0])) {
            mRListSource.moveUp(mRTransferList.getSelectIndex());
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_RIGHT_VIEW_POP[1])) {
            mRListSource.moveDown(mRTransferList.getSelectIndex());
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_RIGHT_VIEW_POP[2])) {
            mRListSource.removeCell(mRTransferList.getSelectIndex());
            return;
        }

    }

}
