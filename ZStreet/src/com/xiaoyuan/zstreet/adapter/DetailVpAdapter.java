package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 商品详情展示图片的Adapter
 * @author Administrator
 *
 */
public class DetailVpAdapter extends PagerAdapter {

	private List<ImageView> imgs;
	
	
	/**
	 * 展示图片的Adapter
	 * @param imgs 要展示的数据源
	 */
	public DetailVpAdapter(List<ImageView> imgs) {
		super();
		this.imgs = imgs;
	}

	
	
	//返回数据总长度
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.size();
	}

	
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	
	//要销毁的item
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
		container.removeView(imgs.get(position));
		
	}

	//初始化Item
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(imgs.get(position));
		return imgs.get(position);
	}

	
}
