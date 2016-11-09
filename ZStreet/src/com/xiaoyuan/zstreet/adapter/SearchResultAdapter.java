package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.zstreet.GoodsDetailActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.utils.ImageLoaderUtils;

/**
 * 展示搜索结果的Adapter
 * @author Administrator
 *
 */
public class SearchResultAdapter extends MyBaseAdapter {

	private Context context ;
	
	private List<GoodsBean> datas;
	
	/**
	 * 
	 * @param context 传入的上下文
	 * @param datas 展示的数据源
	 */
	public SearchResultAdapter(Context context, List<GoodsBean> datas) {
		
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder vh = null;
		if(v==null){
			//加载布局
			v = LayoutInflater.from(context).inflate(R.layout.item_homelv, null);
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
		}
		final GoodsBean gb = datas.get(position);
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
		
		return v;
	}
	
	
	class ViewHolder{
		ImageView goodsImg;
		TextView goodsName, money,sell,rest,pinpai;
	}

}
