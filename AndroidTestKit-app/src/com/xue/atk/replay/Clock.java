package com.xue.atk.replay;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Clock extends JPanel {

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 50;

    private JLabel mTimeLabel;

    private int mHour;
    private int mMinute;
    private int mSecond;

    private Timer mTimer;

    public Clock() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Clock(int width, int height) {
        this.setBounds(0, 0, width, height);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        mTimeLabel = new JLabel();
        mTimeLabel.setBounds(0, 0, width, height);

        mTimeLabel.setHorizontalAlignment(JLabel.CENTER);

        mTimeLabel.setVerticalAlignment(JLabel.CENTER);
        this.add(mTimeLabel);

        mTimeLabel.setText("00:00:00");

    }

    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    public void startTiming() {
        mTimer = new Timer();
        mTimer.schedule(new CTask(), 1000, 1000);
    }

    public void pauseTiming() {
        mTimer.cancel();
    }

    public void stopTiming() {
        pauseTiming();
        clear();
    }

    public void clear() {
        mSecond = 0;
        mMinute = 0;
        mHour = 0;
    }

    class CTask extends TimerTask {

        public void run() {
            if (++mSecond > 59) {
                mMinute++;
                mSecond = 0;
            }
            if (mMinute > 59) {
                mHour++;
                mMinute = 0;
            }

            mTimeLabel.setText(intToTwoBitString(mHour) + ":" + intToTwoBitString(mMinute) + ":"
                    + intToTwoBitString(mSecond));
        }
    }

    private String intToTwoBitString(int src) {
        return String.format("%02d", src);
    }
}
