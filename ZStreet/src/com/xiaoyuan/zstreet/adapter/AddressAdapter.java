package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.AddressBean;

/**
 * 展示收货地址的Adapter
 * @author Administrator
 *
 */
public class AddressAdapter extends MyBaseAdapter {

	/**
	 * 要展示的数据源
	 */
	private List<AddressBean> datas;
	/**
	 * 上下文
	 */
	private Context context;
	
	
	
	public AddressAdapter(List<AddressBean> datas, Context context) {
		
		this.datas = datas;
		this.context = context;
	}

	//获取总数量
	@Override
	public int getCount() {
		return datas.size();
	}

	//获取position下标的item
	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	
	//获取position下标的View
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder vh;
		if(v==null){
			v = LayoutInflater.from(context).inflate(R.layout.item_address, null);
			vh = new ViewHolder();
			vh.address = (TextView) v.findViewById(R.id.item_address_address);
			vh.name = (TextView) v.findViewById(R.id.item_address_name);
			vh.phone = (TextView) v.findViewById(R.id.item_address_phone);
			v.setTag(vh);
		}else{
			vh = (ViewHolder) v.getTag();
		}
		//为控件设定值
		vh.address.setText(datas.get(position).getAddress());
		vh.name.setText(datas.get(position).getName());
		vh.phone.setText(datas.get(position).getPhoneNumber());
		return v;
	}
	
	class ViewHolder{
		TextView address,name,phone;
	}

}
