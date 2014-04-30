/**
 * 
 */
package com.xue.atk.view;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;



/**
 * @author wei.xue
 * @date 2013-01-18
 */
public class BaseModel {
	private Vector<BaseList> repository = new Vector<BaseList>();
	private BaseList bl;
	// ע����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void addSourceRefreshListener(BaseList list) {
		repository.addElement(list);// �ⲽҪע��ͬ������
	}

	// �������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void notifySourceRefreshEvent() {
		Enumeration<BaseList> en = repository.elements();// �ⲽҪע��ͬ������
		while (en.hasMoreElements()) {
			bl = (BaseList) en.nextElement();
			bl.sourceRefreshEvent();
		}
	}
	// ɾ����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void removeSourceRefreshListener(BaseList srl) {
		repository.remove(srl);// �ⲽҪע��ͬ������
	}
}
