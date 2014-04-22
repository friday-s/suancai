package com.xue.atk.view;

import java.awt.BorderLayout;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

/**
 * cell����ʽPanel��ʵ��ListCellIface
 * 
 * @author wei.xue
 * @date 2013-01-18
 */
public class CellPanel extends JPanel implements ListCellIface {

    public static final int STYPE_1 = 1;
    public static final int STYLE_2 = 2;

    private static final long serialVersionUID = 1L;
    private int index = 0;
    private CellPanel per2CellPanel = this;
    private BaseList baseList;
    private String bean;
    private JLabel label;
    
    private boolean selected;
    
    
    private static final int DEFAULT_WIDTH = ATK.BASE_TAB_VIEW_WIDTH-25;
    private static final int DEFAULT_HEIGHT = 30;
    
    private static final int BUTTON_SIDE_LENGHT = 30;

    public CellPanel() {
        this(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
    
    public CellPanel(int width ,int height){

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        
        this.setSize(new Dimension(width, height));

        this.setOpaque(false);
    }

    @Override
    public JComponent getListCell(BaseList list, Object value) {
        String valuelist = (String) value;
        this.bean = valuelist;
        this.baseList = list;

        label = new JLabel(bean);
      //  label.setMaximumSize(new Dimension(DEFAULT_WIDTH-BUTTON_SIDE_LENGHT,BUTTON_SIDE_LENGHT));
      //  label.setPreferredSize(new Dimension(DEFAULT_WIDTH-BUTTON_SIDE_LENGHT,BUTTON_SIDE_LENGHT));

        CButton button = new CButton(Util.getImageIcon("right_normal.png"),
                Util.getImageIcon("right_click.png"));
        button.setMaximumSize(new Dimension(BUTTON_SIDE_LENGHT,BUTTON_SIDE_LENGHT));

        // final JSpinner jSpinner = new JSpinner();
        // jSpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        // jSpinner.setPreferredSize(new Dimension(60,30));
        // jSpinner.setMaximumSize(new Dimension(60,30));
        // jSpinner.addChangeListener(new ChangeListener() {
        //
        // @Override
        // public void stateChanged(ChangeEvent arg0) {
        // // TODO Auto-generated method stub
        // System.out.println(jSpinner.getValue());
        // }
        // });
        // remove.setBounds(100, 0, 60, 25);
        // remove.addMouseListener(new MouseAdapter() {
        // public void mouseClicked(MouseEvent e) {
        //
        // if(e.getButton()==MouseEvent.BUTTON1){
        // per2CellPanel.baseList.getSource().removeCell(bean);
        // }
        // }
        // });
        this.add(label, BorderLayout.WEST);
        this.add(button, BorderLayout.EAST);

        return this;
    }

    @Override
    public void setSelect(boolean iss) {
        this.selected = iss;
    }
    
    public boolean getSelect(){
        return selected;
    }
}