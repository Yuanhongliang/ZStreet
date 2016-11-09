package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.CartBean;
import com.xiaoyuan.zstreet.utils.ImageLoaderUtils;

/**
 * 付款界面显示要付款商品
 * 
 * @author Administrator
 * 
 */
public class PayGoodsAdapter extends MyBaseAdapter {

	private Context context;
	
	private List<CartBean> cbs;
	
	/**
	 * 
	 * @param context 上下文
	 * @param cbs 数据源
	 */
	public PayGoodsAdapter(Context context, List<CartBean> cbs) {
		
		this.context = context;
		this.cbs = cbs;
	}

	@Override
	public int getCount() {
		return cbs.size();
	}

	@Override
	public Object getItem(int position) {
		return cbs.get(position);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		v = LayoutInflater.from(context).inflate(R.layout.item_paylv, null);
		
		TextView name = (TextView) v.findViewById(R.id.item_paylv_name);
		TextView price = (TextView) v.findViewById(R.id.item_pay_price);
		TextView num = (TextView) v.findViewById(R.id.item_pay_num);
		TextView color = (TextView) v.findViewById(R.id.item_pay_color);
		TextView size = (TextView) v.findViewById(R.id.item_pay_size);
		ImageView img = (ImageView) v.findViewById(R.id.item_paylv_img);
		CartBean cb = cbs.get(position);
		name.setText(cb.getGoodsName());
		price.setText("¥"+cb.getGoodsPrice());
		num.setText(cb.getGoodsNum()+"");
		color.setText(cb.getGoodsColor());
		size.setText(cb.getGoodsSize());
		ImageLoaderUtils.loadSmallImg(img, cb.getImgUrl());
		return v;
	}

}
