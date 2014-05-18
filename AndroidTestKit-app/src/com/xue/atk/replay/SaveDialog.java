package com.xue.atk.replay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.xue.atk.manager.ADBManager;
import com.xue.atk.manager.FileScannerManager;
import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;
import com.xue.atk.view.CButton;
import com.xue.atk.view.Dialog;
import com.xue.atk.view.DlgProgess;

public class SaveDialog extends Dialog implements ActionListener {

    private static final int TEXTAREA_WIDTH = 190;
    private static final int TEXTAREA_HEIGHT = 30;

    private static final int BTN_WIDTH = 95;
    private static final int BTN_HEIGHT = 40;

    private static final int LABEL_WIDTH = 80;
    private static final int LABEL_HEIGHT = TEXTAREA_HEIGHT;

    private static final int BEGIN_X = 60;

    private JLabel mIconLable;
    private CButton mPositiveBtn;
    private CButton mNegativeBtn;

    private JTextField mTextField;
    private JComboBox mJComboBox;
    private JLabel mErrorLabel;
    
    private SourceRefreshCallBack mCallback;

    public SaveDialog(Component owner, SourceRefreshCallBack callback,int width, int height) {
        super(owner, width, height);
        // TODO Auto-generated constructor stub
        this.mCallback = callback;
    }

    public void onCreate() {

        mIconLable = new JLabel();
        ImageIcon icon = Util.getImageIcon("warn.png");
        mIconLable.setBounds((getDialogWidth() - icon.getIconWidth()) / 2,
                icon.getIconHeight() / 2, icon.getIconWidth(), icon.getIconHeight());

        mIconLable.setIcon(Util.getImageIcon("warn.png"));

        mPositiveBtn = new CButton(Util.getImageIcon("ok_up.png"), Util.getImageIcon("ok_down.png"));
        mPositiveBtn.setBounds(getDialogWidth() / 2 + 20, getDialogHeight() - BTN_HEIGHT - 20,
                BTN_WIDTH, BTN_HEIGHT);
        mPositiveBtn.addActionListener(this);

        mNegativeBtn = new CButton(Util.getImageIcon("cancel_up.png"),
                Util.getImageIcon("cancel_down.png"));
        mNegativeBtn.setBounds(getDialogWidth() / 2 - BTN_WIDTH - 20, getDialogHeight()
                - BTN_HEIGHT - 20, BTN_WIDTH, BTN_HEIGHT);
        mNegativeBtn.addActionListener(this);

        getLayeredPane().add(mPositiveBtn, new Integer(300));
        getLayeredPane().add(mNegativeBtn, new Integer(300));
        this.setAlwaysOnTop(true);
        mContext.setEnabled(false);

        getLayeredPane().add(mIconLable, new Integer(300));

        createEditView();
    }

    private void createEditView() {

        int y = mIconLable.getY() + mIconLable.getHeight() + 5;

        mJComboBox = new JComboBox();
        mJComboBox.setBounds(BEGIN_X + LABEL_WIDTH, y, TEXTAREA_WIDTH, TEXTAREA_HEIGHT);
        mJComboBox.setMaximumRowCount(20);
        mJComboBox.setEditable(true);
        mJComboBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        // mJComboBox.addItemListener(this);

        mJComboBox.setUI(new BasicComboBoxUI() {
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
        getLayeredPane().add(mJComboBox, new Integer(300));

        for (String s : ATK.SAVE_DIALOG_LABEL) {
            JLabel label = new JLabel(s);
            label.setBounds(BEGIN_X, y, LABEL_WIDTH, LABEL_HEIGHT);
            getLayeredPane().add(label, new Integer(300));
            y += LABEL_HEIGHT + 5;
        }

        mTextField = new JTextField();
        mTextField.setLocale(Locale.US);
        // mTextField.setInputVerifier(new InputVerifier());
        mTextField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        mTextField.setBounds(BEGIN_X + LABEL_WIDTH, y - LABEL_HEIGHT - 5, TEXTAREA_WIDTH,
                TEXTAREA_HEIGHT);
        getLayeredPane().add(mTextField, new Integer(300));

        mErrorLabel = new JLabel();
        mErrorLabel.setForeground(Color.RED);
        mErrorLabel.setBounds(BEGIN_X, y, 200, 30);
        getLayeredPane().add(mErrorLabel, new Integer(300));

        mJComboBox.setModel(new DefaultComboBoxModel(FileScannerManager.getManager()
                .getProjectList()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(mPositiveBtn)) {

            if (mJComboBox.getSelectedItem()==null || mJComboBox.getSelectedItem().toString().trim().isEmpty()
                    || mTextField.getText() ==null || mTextField.getText().trim().isEmpty()
                    || mJComboBox.getSelectedItem().toString().trim().indexOf(" ") >= 0
                    || mTextField.getText().trim().indexOf(" ") >= 0) {
                mErrorLabel.setText(ATK.ERROR_CONTENT_EMPTY);
            } else if (Util.isContainChinese(mJComboBox.getSelectedItem().toString())
                    || Util.isContainChinese(mTextField.getText())) {
                mErrorLabel.setText(ATK.ERROR_USE_CHINESE);
            } else {
                String target = FileScannerManager.getManager().getEventPath()
                        + mJComboBox.getSelectedItem().toString().trim();
                FileScannerManager.getManager().checkPath(target);
                final String command = "pull " + ReplayView.EVENT_PATH + " " + target
                        + File.separator + mTextField.getText();

                Thread thread = new Thread() {
                    public void run() {
                        ADBManager.getManager().execADBCommand(command);
                    }
                };

                DlgProgess.show(this, thread);
                mContext.setEnabled(true);
                mCallback.refreshSource();
                this.dispose();
            }
            return;
        }
        if (e.getSource().equals(mNegativeBtn)) {
        	mContext.setEnabled(true);
            this.dispose();
            return;
        }
    }

}
