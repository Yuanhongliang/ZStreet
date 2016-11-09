package com.xiaoyuan.zstreet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.zstreet.GoodsDetailActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.myview.AutoScrollViewPager;
import com.xiaoyuan.zstreet.utils.ImageLoaderUtils;

/**
 * 主页面Fragment的适配器
 * 
 * @author Administrator
 * 
 */
public class HomeLvAdapter extends BaseAdapter {

	private AutoScrollViewPager vp;
	private List<View> views;
	private Context context;
	private List<GoodsBean> goods;
	
	

	public HomeLvAdapter(Context context, List<View> views,
			List<GoodsBean> goods) {
		super();
		this.context = context;
		this.views = views;
		this.goods = goods;
		if(goods==null){
			this.goods = new ArrayList<GoodsBean>();
		}
	}
	//返回LV总共的数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods.size() + 1;
	}
	//返回position下标的数量
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
			return vp;
		} else {
			return goods.get(position - 1);
		}
	}
	//返回position下标的ID
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//返回position下标的View
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
		ViewHolder vh;
		if (position == 0) {
			v = LayoutInflater.from(context).inflate(R.layout.item_viewpager,
					null);
			vp = (AutoScrollViewPager) v.findViewById(R.id.item_viewpager);
			vp.setAdapter(new HomeVpAdapter(views));
			vp.startAutoScroll();
		} else {
			if(v==null){
				vh = new ViewHolder();
				v = LayoutInflater.from(context).inflate(R.layout.item_homelv,null);
				vh.goodsImg = (ImageView) v.findViewById(R.id.item_homelv_img);
				vh.goodsName = (TextView) v.findViewById(R.id.item_homelv_name);
				vh.money = (TextView) v.findViewById(R.id.item_homelv_money);
				vh.sell = (TextView) v.findViewById(R.id.item_homelv_sell);
				vh.rest = (TextView) v.findViewById(R.id.item_homelv_rest);
				vh.pinpai = (TextView) v.findViewById(R.id.item_homelv_pinpai);
				v.setTag(vh);
			}else{
				vh = (ViewHolder) v.getTag();
				if(vh==null){//判断如果加载到了ViewPager时就重新填充布局
					vh = new ViewHolder();
					v = LayoutInflater.from(context).inflate(R.layout.item_homelv,null);
					vh.goodsImg = (ImageView) v.findViewById(R.id.item_homelv_img);
					vh.goodsName = (TextView) v.findViewById(R.id.item_homelv_name);
					vh.money = (TextView) v.findViewById(R.id.item_homelv_money);
					vh.sell = (TextView) v.findViewById(R.id.item_homelv_sell);
					vh.rest = (TextView) v.findViewById(R.id.item_homelv_rest);
					vh.pinpai = (TextView) v.findViewById(R.id.item_homelv_pinpai);
					v.setTag(vh);
				}
			}
			final GoodsBean gb = goods.get(position-1);
			vh.goodsName.setText(gb.getGoodsName());
			vh.money.setText(gb.getPrice()+"");
			vh.rest.setText(gb.getRest()+"");
			vh.sell.setText(gb.getSell()+"");
			vh.pinpai.setText(gb.getPinpai());
			ImageLoaderUtils.loadSmallImg(vh.goodsImg, gb.getGoodsImgs().get(0).getFileUrl(context));
			//点击商品界面进入商品详情
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MyApplication.putData("goodDetail", gb);
					context.startActivity(new Intent(context, GoodsDetailActivity.class));
				}
			});
		}
		return v;
	}
	
	class ViewHolder{
		ImageView goodsImg;
		TextView goodsName, money,sell,rest,pinpai;
	}
	
	
	public void setGoods(List<GoodsBean> goods) {
		this.goods = goods;
		notifyDataSetChanged();
	}
	
	public void addGoods(List<GoodsBean> data){
		goods.addAll(data);
		Log.e("size", data.size()+"个");
		notifyDataSetChanged();
	}
	
	
	
	
	
	
}
