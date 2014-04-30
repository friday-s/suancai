package com.xue.atk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * �Զ���JList,ÿ��cell����һ��component,���component�Ϳ������ⶨ����,������һ��JPanel�
 * �չʾ BaseList Demo ������
 * 
 * @author wei.xue
 * @date 2013-01-18
 */
public class BaseList extends JComponent {
    private static final long serialVersionUID = 1L;

    private ListSource source;

    private int mLastIndex = -1;
    private int mTempIndex = 0;

    private ListCellIface celliface;

    private int selectIndex = -1;

    private List<JComponent> mTotalCell = new ArrayList<JComponent>();

    private Color selectColor = new Color(252, 233, 161);

    private Color passColor = new Color(196, 227, 248);

    private JPopupMenu mPopupMenu;

    public ListSource getSource() {
        return source;
    }

    /**
     * ����BaseList��Դ
     * 
     * @param source
     *            ListSource���Ͳ���
     */
    public void setSource(ListSource source) {
        if (source == null) {
            return;
        } else {
            this.source.removeSourceRefreshListener(this);
            this.source = null;
        }
        this.source = source;
        this.source.addSourceRefreshListener(this);
        this.refreshData();
    }

    /**
     * ����Ҫ��ʾ�Ŀؼ�
     * 
     * @param cell
     */
    public void setCellIface(ListCellIface cell) {
        this.celliface = cell;
    }

    public Color getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(Color selectColor) {
        this.selectColor = selectColor;
    }

    public Color getPassColor() {
        return passColor;
    }

    public void setPassColor(Color passColor) {
        this.passColor = passColor;
    }

    /**
     * ѡ��ĳһ��ʱִ�д˷���
     * 
     * @param selectIndex
     */
    public void setSelectIndex(int selectIndex) {

        if (selectIndex < mTotalCell.size() && selectIndex > -1) {

            if (mLastIndex >= 0) {
                mTotalCell.get(mLastIndex).setOpaque(false);
                mTotalCell.get(mLastIndex).setBackground(null);
            }

            mTotalCell.get(selectIndex).setOpaque(true);
            mTotalCell.get(selectIndex).setBackground(getSelectColor());

            if (celliface != null) {
                if (mLastIndex >= 0) {
                    ((ListCellIface) mTotalCell.get(mLastIndex)).setSelect(false);
                }
                ((ListCellIface) mTotalCell.get(selectIndex)).setSelect(true);

                mLastIndex = selectIndex;
            }
        }

        this.selectIndex = selectIndex;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public Object getSelectCell() {
        return source.getCell(selectIndex);
    }

    public BaseList() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        source = new ListSource();
    }

    /**
     * ˢ�����
     */
    public void refreshData() {
        // if (source.getAllCell().size() != 0) {
        // // ����
        // Collections.sort(source.getAllCell(), source.getComparator());
        // System.out.println(source.getComparator());
        // }
        this.removeAll();
        this.mTotalCell.clear();

        if (source.getAllCell() != null) {

            for (int i = 0; i < source.getAllCell().size(); i++) {
                JComponent cell = null;
                if (celliface != null) {
                    try {
                        celliface = celliface.getClass().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (celliface == null) {
                    cell = new JLabel(source.getAllCell().get(i).toString());
                    cell.setMaximumSize(new Dimension(200, 30));
                    cell.setPreferredSize(new Dimension(0, 30));

                } else {
                    cell = celliface.getListCell(this, source.getAllCell().get(i));
                }

                cell.addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e) {

                    }

                    public void mouseEntered(MouseEvent e) {
                        for (int i = 0; i < mTotalCell.size(); i++) {

                            if (e.getSource().equals(mTotalCell.get(i))) {

                                if (!((ListCellIface) mTotalCell.get(i)).getSelect()) {

                                    mTotalCell.get(i).setOpaque(true);
                                    mTotalCell.get(i).setBackground(getPassColor());
                                    mTempIndex = i;

                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // TODO Auto-generated method stub
                        if (!((ListCellIface) mTotalCell.get(mTempIndex)).getSelect()) {
                            mTotalCell.get(mTempIndex).setOpaque(false);
                            mTotalCell.get(mTempIndex).setBackground(null);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // TODO Auto-generated method stub

                        for (int i = 0; i < mTotalCell.size(); i++) {
                            if (e.getSource().equals(mTotalCell.get(i))) {
                                setSelectIndex(i);

                                break;
                            }
                        }

                        if (e.getSource() == mTotalCell.get(selectIndex)
                                && e.getButton() == MouseEvent.BUTTON3) {
                            if (mPopupMenu != null) {
                                mPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    }
                });

                this.mTotalCell.add(cell);
                this.add(cell);
            }
        }

        this.revalidate();
        this.repaint();
    }

    public void setRightClickPopup(JPopupMenu pop) {
        this.mPopupMenu = pop;
    }

    public List<JComponent> getmTotalCell() {
        return this.mTotalCell;
    }

    /**
     * Դ��ݸ���
     * 
     * @param event
     */
    public void sourceRefreshEvent() {
        this.refreshData();
        mLastIndex = -1;
    }
}