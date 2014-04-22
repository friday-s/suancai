package com.xue.atk.view;

import javax.swing.JComponent;

/**
 * Cell�Ľӿ��࣬���ڹ���BaseList�е�cell BaseList Demo ��Ҫ��
 * 
 * @author wei.xue
 * @date 2013-01-18
 */
public interface ListCellIface {

    public JComponent getListCell(BaseList list, Object value);

    public void setSelect(boolean iss);

    public boolean getSelect();
}
