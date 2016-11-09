package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * 主页面初始化ViewPager的Adapter
 * @author Administrator
 *
 */
public class HomeVpAdapter extends PagerAdapter {

	//要显示的View
	private List<View> views;
	
	public HomeVpAdapter(List<View> views) {
		this.views = views;
	}
	//要显示的的数量
	@Override
	public int getCount() {
		return views.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	/**
	 * 销毁要划走的View
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
		container.removeView(views.get(position));
	}
	/**
	 * 初始化要显示的View
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
