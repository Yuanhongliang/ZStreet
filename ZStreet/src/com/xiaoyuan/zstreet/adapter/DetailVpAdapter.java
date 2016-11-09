package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ��Ʒ����չʾͼƬ��Adapter
 * @author Administrator
 *
 */
public class DetailVpAdapter extends PagerAdapter {

	private List<ImageView> imgs;
	
	
	/**
	 * չʾͼƬ��Adapter
	 * @param imgs Ҫչʾ������Դ
	 */
	public DetailVpAdapter(List<ImageView> imgs) {
		super();
		this.imgs = imgs;
	}

	
	
	//���������ܳ���
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

	
	//Ҫ���ٵ�item
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
		container.removeView(imgs.get(position));
		
	}

	//��ʼ��Item
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(imgs.get(position));
		return imgs.get(position);
	}

	
}
