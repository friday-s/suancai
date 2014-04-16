package com.xue.atk;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import com.xue.atk.res.ATK;
import com.xue.atk.service.ADBService;
import com.xue.atk.util.Util;
import com.xue.atk.view.MainFrame;

public class AndroidEventReplay {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            if (checkPidInProcess(AndroidEventReplay.class.getSimpleName())) {
                System.out.println(AndroidEventReplay.class.getSimpleName()
                        + " is already running.");
                return;
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AndroidEventReplay window = new AndroidEventReplay();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ADBService adbService = new ADBService();
        adbService.start();
    }

    /**
     * Create the application.
     */
    public AndroidEventReplay() {
        // JFrame.setDefaultLookAndFeelDecorated(false);
        // try {
        // UIManager
        // .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (ClassNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (InstantiationException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IllegalAccessException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (UnsupportedLookAndFeelException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new MainFrame();
        frame.setTitle(ATK.APP_NAME);
        frame.setIconImage(Util.getImageIcon("android.png").getImage());
        frame.setBounds(ATK.LOCATION_X, ATK.LOCATION_Y, ATK.WIDTH, ATK.HEIGHT);
        frame.setBackground(Color.WHITE);
    }

    private static boolean checkPidInProcess(String pid) throws Exception {
        InputStream in = Runtime.getRuntime().exec("jps").getInputStream();
        BufferedReader b = new BufferedReader(new InputStreamReader(in));
        String line = null;
        int count = 0;
        while ((line = b.readLine()) != null) {
            if (line.indexOf(pid) > 0) {
                count++;
            }
        }
        return count <= 1 ? false : true;
    }
}
