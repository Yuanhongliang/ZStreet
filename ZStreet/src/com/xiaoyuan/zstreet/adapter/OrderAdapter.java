package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

import com.xiaoyuan.zstreet.CommentActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.OrderBean;

/**
 * 订单列表的Adapter
 * @author Administrator
 *
 */
public class OrderAdapter extends MyBaseAdapter {

	
	/**
	 * 传入的上下文
	 */
	private Context context;
	/**
	 * 要显示的数据源
	 */
	private List<OrderBean> orders;

	public OrderAdapter(Context context, List<OrderBean> orders) {

		this.context = context;
		this.orders = orders;
	}
	
	@Override
	public int getCount() {

		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orders.get(position);
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder vh;
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_order, null);
			vh = new ViewHolder();
			vh.but = (Button) v.findViewById(R.id.item_order_but);
			vh.name = (TextView) v.findViewById(R.id.item_order_goodsname);
			vh.num = (TextView) v.findViewById(R.id.item_order_goodsnum);
			vh.money = (TextView) v.findViewById(R.id.item_order_money);
			vh.state = (TextView) v.findViewById(R.id.item_order_state);
			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}
		Log.i("log",orders.get(position)+"");
		vh.name.setText(orders.get(position).getGoodsName());
		vh.num.setText(orders.get(position).getGoodsNum() + "");
		vh.money.setText(orders.get(position).getTotalPrice() + "");
		vh.but.setText(orders.get(position).isReceived() ? "发表评论" : "确认收货");
		vh.state.setText(orders.get(position).isReceived() ? "已签收" : "派送中");
		setListener(vh.state,vh.but, orders.get(position));

		return v;
	}

	private void setListener(final TextView state, final Button but, final OrderBean orderBean) {
		// 确认收货（发表评论）的监听事件
		but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 确认收货
				if (!orderBean.isReceived()) {
					OrderBean ob = new OrderBean();
					ob.setObjectId(orderBean.getObjectId());
					ob.setGoodsNum(orderBean.getGoodsNum());
					ob.setTotalPrice(orderBean.getTotalPrice());
					ob.setReceived(true);
					ob.update(context, new UpdateListener() {

						@Override
						public void onSuccess() {
							but.setText("发表评论");
							state.setText("已签收");
							orderBean.setReceived(true);
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.e("aaa", "失败");
						}
					});
					//发表评论
				}else{
					MyApplication.putData("order", orderBean);
					context.startActivity(new Intent(context, CommentActivity.class));
				}
			}
		});
	}

	class ViewHolder {
		TextView name, num, money, state;
		Button but;
	}

}
