package com.xue.atk.replay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.xue.atk.file.EventFile;
import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;
import com.xue.atk.view.BaseList;
import com.xue.atk.view.CellPanel;
import com.xue.atk.view.ListCellIface;

public class RCellPanel extends CellPanel {

    private static final long serialVersionUID = 1L;
    

    private static final int DEFAULT_WIDTH = ATK.BASE_TAB_VIEW_WIDTH - 20;
    private static final int DEFAULT_HEIGHT = 30;

    private static final int BUTTON_SIDE_LENGHT = DEFAULT_HEIGHT;

    private static final int SPINNER_WIDTH = 60;
    private static final int SPINNER_HEIGHT = DEFAULT_HEIGHT;

    private static final int LABEL_WIDTH = DEFAULT_WIDTH - BUTTON_SIDE_LENGHT - SPINNER_WIDTH;
    private static final int LABEL_HEIGHT = DEFAULT_HEIGHT;

    private String bean;

    private JLabel label;

    private boolean selected;
    
    private EventFile eventFile;


    public RCellPanel() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public RCellPanel(int width, int height) {

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));

        this.setSize(new Dimension(width, height));

        this.setOpaque(false);

    }

    @Override
    public JComponent getListCell(BaseList list, Object value) {

        this.eventFile = (EventFile) value;

        this.bean = value.toString();

        label = new JLabel(bean);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));

        final JSpinner jSpinner = new JSpinner();
        jSpinner.setModel(new SpinnerNumberModel(eventFile.getTime(), 1, null, 1));
        jSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        jSpinner.setMaximumSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        jSpinner.setBorder(BorderFactory.createLineBorder(new Color(252, 233, 161)));

        for (Component c : jSpinner.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).setBorder(BorderFactory.createLineBorder(new Color(252, 233, 161)));
               c.setBackground(Color.WHITE);
            }
        }

        jSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                // TODO Auto-generated method stub
                eventFile.setTime((Integer) jSpinner.getValue());
            }
        });

        this.add(label, BorderLayout.WEST);
        this.add(jSpinner, BorderLayout.CENTER);
        return this;
    }

    @Override
    public void setSelect(boolean iss) {
        this.selected = iss;
    }

    public boolean getSelect() {
        return selected;
    }
}