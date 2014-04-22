package com.xue.atk.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * �Զ����ԴList ��������һ��List���ⲿͨ��setSources(List sources)������Դ BaseList Demo ������
 * 
 * @ClassName ListSource
 * @author wei.xue
 * @date 2013-01-18
 */
public class ListSource extends BaseModel {
	private Comparator<Object> comparator = null;
	private List<Object> sources = new ArrayList<Object>();

	// ͨ��list�������Դ
	public void setSources(List<Object> sources) {
		this.sources = sources;
	}

	// ���һ����Ԫ
	public void addCell(Object obj) {
		sources.add(obj);
		notifySourceRefreshEvent(sources);
	}

	// �������ɾ��һ����Ԫ
	public void removeCell(int index) {
		System.out.println("ɾ��:" + index);
		if (sources.size() > 0 && index < sources.size() && index >= 0) {
			sources.remove(index);
			notifySourceRefreshEvent(sources);
		}
	}

	// ���ֵɾ��һ����Ԫ
	public void removeCell(Object value) {
		sources.remove(value);
		notifySourceRefreshEvent(sources);
	}

	// ����һ����Ԫ
	public void setCell(int index, Object obj) {
		sources.set(index, obj);
		notifySourceRefreshEvent(sources);
	}

	// ��ȡһ����Ԫ����Ϣ
	public Object getCell(int index) {
		return sources.get(index);
	}

	// ��ȡ���е�Ԫ��Ϣ
	public List<Object> getAllCell() {
		return sources;
	}

	// �Ƴ�����
	public void removeAll() {
		sources.clear();
		notifySourceRefreshEvent(sources);
	}

	// ����
	public void moveUp(int index) {
		if (index > 0 && index < sources.size()) {
			Object temp = sources.get(index - 1);
			sources.set(index - 1, sources.get(index));
			sources.set(index, temp);
			notifySourceRefreshEvent(sources);
		}
	}

	// ����
	public void moveDown(int index) {
		if (index < sources.size() - 1 && index > 0) {
			Object temp = sources.get(index + 1);
			sources.set(index + 1, sources.get(index));
			sources.set(index, temp);
			notifySourceRefreshEvent(sources);
		}
	}

	public void setSort(Comparator<Object> comparator) {
		this.comparator = comparator;
		notifySourceRefreshEvent(sources);
	}

	public Comparator<Object> getComparator() {
		return comparator;
	}
}
