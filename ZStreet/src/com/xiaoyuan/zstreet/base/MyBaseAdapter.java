package com.xiaoyuan.zstreet.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 向上抽取的Adapter类 
 * @author Administrator
 *
 */
public abstract class MyBaseAdapter extends BaseAdapter {
	/**
	 * 返回数据源的长度
	 */
	@Override
	public abstract int getCount();
	
	/**
	 * 返回position下标的数据
	 */
	@Override
	public abstract Object getItem(int position);
	/**
	 * 返回position下标
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * 返回position下标对应的View
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
