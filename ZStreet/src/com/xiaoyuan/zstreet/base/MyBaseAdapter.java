package com.xiaoyuan.zstreet.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * ���ϳ�ȡ��Adapter�� 
 * @author Administrator
 *
 */
public abstract class MyBaseAdapter extends BaseAdapter {
	/**
	 * ��������Դ�ĳ���
	 */
	@Override
	public abstract int getCount();
	
	/**
	 * ����position�±������
	 */
	@Override
	public abstract Object getItem(int position);
	/**
	 * ����position�±�
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * ����position�±��Ӧ��View
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
