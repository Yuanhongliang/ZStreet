package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * ��ҳ���ʼ��ViewPager��Adapter
 * @author Administrator
 *
 */
public class HomeVpAdapter extends PagerAdapter {

	//Ҫ��ʾ��View
	private List<View> views;
	
	public HomeVpAdapter(List<View> views) {
		this.views = views;
	}
	//Ҫ��ʾ�ĵ�����
	@Override
	public int getCount() {
		return views.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	/**
	 * ����Ҫ���ߵ�View
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
		container.removeView(views.get(position));
	}
	/**
	 * ��ʼ��Ҫ��ʾ��View
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup) views.get(position).getParent();
		if (p != null) {
			p.removeAllViews();
		}
		container.addView(views.get(position));
		return views.get(position);
	}

}
