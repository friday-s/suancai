package com.xue.atk.replay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.xue.atk.file.DirectoryUtil;
import com.xue.atk.file.EventFile;
import com.xue.atk.manager.ADBManager;
import com.xue.atk.manager.FileScannerManager;
import com.xue.atk.res.ATK;
import com.xue.atk.service.IDeviceChangedCallBack;
import com.xue.atk.util.Util;
import com.xue.atk.view.AlertDialog;
import com.xue.atk.view.BaseList;
import com.xue.atk.view.CBaseTabView;
import com.xue.atk.view.CButton;
import com.xue.atk.view.CellPanel;
import com.xue.atk.view.ListSource;
import com.xue.atk.view.ProgressBar;

public class ReplayView extends CBaseTabView implements ItemListener, ActionListener,SourceRefreshCallBack {

    private static final String TAG = "ReplayView";
    
    private static final int DIVIDE_LINE_WIDTH = 70;
    private static final int DIVIDE_LINE_HEIGHT = 1;

    public static final String DEVICE_TMP_PATH = "/data/local/tmp/";

    public static final String EVENT_PATH = "sdcard/events";

    private static final int PROGRESSBAR_SIDE_LENGHT = 25;
    
    private  String mLibsPath;

    private CButton mRecordBtn;
    private CButton mReplayBtn;

    private ImageIcon mRecordIconUp;
    private ImageIcon mRecordIconDown;
    private ImageIcon mReplayIconUp;
    private ImageIcon mReplayIconDown;
    private ImageIcon mReplayStopIconUp;
    private ImageIcon mReplayStopIconDown;
    private ImageIcon mRecordStopIconUp;
    private ImageIcon mRecordStopIconDown;

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
    
    private  DefaultComboBoxModel mProjectModel;
    private Object mCurrentComboSelected;

    public ReplayView(String tabName) {
        super(tabName);
        mLibsPath = DirectoryUtil.getRootDirectory() + "libs" + File.separator;;
        
        initView();
    }

    public void initView() {

		mRecordIconUp = Util.getImageIcon("record_btn.png");
		mRecordIconDown = Util.getImageIcon("record_btn_down.png");
		mReplayIconUp = Util.getImageIcon("replay_btn.png");
		mReplayIconDown = Util.getImageIcon("replay_btn_down.png");
		mReplayStopIconUp = Util.getImageIcon("stop_up.png");
		mReplayStopIconDown = Util.getImageIcon("stop_down.png");
		mRecordStopIconUp = Util.getImageIcon("lstop_up.png");
		mRecordStopIconDown = Util.getImageIcon("lstop_down.png");

        initLeftView();
        initCenterView();
        initRightView();
        
        ADBManager.getManager().addCallBack(mCallBack);
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

        mProjectModel = new DefaultComboBoxModel(FileScannerManager.getManager()
                .getProjectList());
        mProjectComboBox.setModel(mProjectModel);
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

            refreshCurrentSource();
        }
    }
    
    private void refreshCurrentSource(){
        List<Object> source = FileScannerManager.getManager().getEventList(
                mProjectComboBox.getSelectedItem().toString());

        mLListSource.setSources(source);

        mLListSource.notifySourceRefreshEvent();
    }

    private void showDialog(String msg) {
        AlertDialog dialog = new AlertDialog(this.getRootPane().getParent(), 400, 250,
                AlertDialog.MSG_DIALOG);
        dialog.setMessage(new String[]{msg});
        dialog.onCreate();
        dialog.setVisible(true);
    }
    
    private void showDialog(String[] msg) {
        AlertDialog dialog = new AlertDialog(this.getRootPane().getParent(), 400, 250,
                AlertDialog.MSG_DIALOG);
        dialog.setMessage(msg);
        dialog.onCreate();
        dialog.setVisible(true);
    }
    
    private void stopRecordState(){
        mRecordBtn.setIconDrawable(mRecordIconUp, mRecordIconDown);

        mProgress.stop();
        isRunning = false;

        mReplayBtn.setEnabled(true);
        ADBManager.getManager().terminateADBCommand();
        mClock.stopTiming();
    }
    
    private void stopReplayState(){
        mReplayBtn.setIconDrawable(mReplayIconUp, mReplayIconDown);
        mProgress.stop();
        mClock.stopTiming();
        isRunning = false;
        mRecordBtn.setEnabled(true);
    }

    private Runnable mRecordRunnable = new Runnable() {
        public void run() {
            try {
                
                ADBManager.getManager().calcEventNum();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                showDialog(e.getMessage());
                stopRecordState();
                return;
            }
            
            if (ADBManager.getManager().execADBCommand(
                    "push " + mLibsPath + "event_record" + " " + DEVICE_TMP_PATH) < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                stopRecordState();
                return;
            }

            if (ADBManager.getManager().execADBCommand(
                    "shell chmod 555 " + DEVICE_TMP_PATH + "event_record") < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                stopRecordState();
                return;
            }

            if (ADBManager.getManager().execADBCommand(
                    "shell " + DEVICE_TMP_PATH + "event_record "
                            + ADBManager.getManager().getCurrentDeviceEventNum()) < 0) {
                showDialog(ADBManager.getManager().getErrorMsg());
                mRecordBtn.doClick();
                return;
            }

        }
    };

    private Runnable mReplayRunnable = new Runnable() {
        public void run() {
            try {
                ADBManager.getManager().calcEventNum();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                showDialog(e.getMessage());
                mReplayBtn.doClick();
                return;
            }

            if (mMultideviceCheckbox.isSelected()) {
                /* multi background */
                if (mBackgroundCheckbox.isSelected()) {
                    final EventFile event = (EventFile) mRListSource.getAllCell().get(0);
                    for (final IDevice device : ADBManager.getManager().getDevices()) {

                        if (ADBManager.getManager().execADBCommand(device,
                                "push " + mLibsPath + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
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

                    for (final IDevice device : ADBManager.getManager().getDevices()) {
                        new Thread() {
                            public void run() {
                                if (ADBManager.getManager().execADBCommand(
                                        device,
                                        "shell "
                                                + DEVICE_TMP_PATH
                                                + "event_replay "
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
                                    "push " + mLibsPath + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
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
                            "push " + mLibsPath + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
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

                    final EventFile event = (EventFile) mRListSource.getAllCell().get(0);
                    if (ADBManager.getManager().execADBCommand(
                            "push " + event.getCompletePath() + " " + EVENT_PATH) < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                    if (ADBManager.getManager().execADBCommand(
                            "shell " + DEVICE_TMP_PATH + "event_replay "
                                    + ADBManager.getManager().getCurrentDeviceEventNum() + " "
                                    + event.getTime() + " &") < 0) {
                        showDialog(ADBManager.getManager().getErrorMsg());
                        mReplayBtn.doClick();
                        return;
                    }

                } else { /* single foreground replay */
                    if (ADBManager.getManager().execADBCommand(
                            "push " + mLibsPath + "event_replay" + " " + DEVICE_TMP_PATH) < 0) {
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

            stopReplayState();
        }
    };
    
    private IDeviceChangedCallBack mCallBack = new IDeviceChangedCallBack() {

        @Override
        public void deviceConnected(IDevice device) {
            // TODO Auto-generated method stub

        }

        @Override
        public void deviceDisonnected(IDevice device) {
            // TODO Auto-generated method stub
            if (isRunning && !mBackgroundCheckbox.isSelected()) {
                if (device.equals(ADBManager.getManager().getCurrentDevice())) {
                    Log.w(TAG, device.toString()+" disconnectd,update status");
                    showDialog(ATK.ERROR_DEVICE_DISCONNECTED + " ("+device.toString()+")");
                    stopRecordState();
                    stopReplayState();
                }
            }
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
                    
                    mCurrentComboSelected= mProjectModel.getElementAt(mProjectComboBox.getSelectedIndex());
                    
                    SaveDialog dialog = new SaveDialog(this.getRootPane().getParent(),this, 400, 250);
                    dialog.onCreate();
                    dialog.setVisible(true);

                } else {

                    mRecordBtn.setIconDrawable(mRecordStopIconUp, mRecordStopIconDown);
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

                    mReplayBtn.setIconDrawable(mReplayStopIconUp, mReplayStopIconUp);
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
            Object o = mLTransferList.getSelectCell();
            File file = new File(((EventFile) o).getCompletePath());
 
            String name = "Name:" + file.getName();
            String lenght = "Lenght:" + file.length();
            String lastModified ="Last modified:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified());
         
            showDialog(new String[]{name,lenght,lastModified});
          
            return;
        }
        if (e.getActionCommand().equals(ATK.REPLAY_LEFT_VIEW_POP[2])) {
            
            final Object o = mLTransferList.getSelectCell();
            final File file = new File(((EventFile) o).getCompletePath());
             boolean result = false;
            AlertDialog dialog = new AlertDialog(this.getRootPane().getParent(), 400, 250,
                    AlertDialog.EXIT_DIALOG){
                public void actionPerformed(ActionEvent e) {
                    
                 // TODO Auto-generated method stub
                    if (e.getSource().equals(mPositiveBtn)) {
                        try {
                          boolean result = file.delete();
                          Log.i(TAG, "delete file "+ o.toString() + "   result:"+result);
                            if (result){
                                mLListSource.removeCell(o);
                                mLListSource.notifySourceRefreshEvent();
                            }
                            
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        mContext.setEnabled(true);
                        this.dispose();

                        return;
                    }
                    if (e.getSource().equals(mNegativeBtn)) {
                        mContext.setEnabled(true);
                        this.dispose();

                        return;
                    }
                }
                
                
            };
            
            dialog.setMessage(new String[]{ATK.DELETE_CONTENT + file.getName()+"?"});
            dialog.onCreate();
            dialog.setVisible(true);
            
            

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

    @Override
    public void refreshSource() {
        // TODO Auto-generated method stub
        mProjectModel = new DefaultComboBoxModel(FileScannerManager.getManager()
                .getProjectList());
        mProjectComboBox.setModel(mProjectModel);
        mProjectComboBox.setSelectedItem(mCurrentComboSelected);
        refreshCurrentSource();
    }
}
