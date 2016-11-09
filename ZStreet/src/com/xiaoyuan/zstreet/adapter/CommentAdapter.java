package com.xiaoyuan.zstreet.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.CommentBean;

/**
 * 显示评论列表的Adapter
 * 
 * @author Administrator
 * 
 */
public class CommentAdapter extends MyBaseAdapter {

	private Context context;

	public CommentAdapter(Context context, List<CommentBean> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	private List<CommentBean> datas;

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder vh;
		if(v==null){
			v = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
			vh = new ViewHolder();
			vh.name = (TextView) v.findViewById(R.id.item_comm_name);
			vh.date = (TextView) v.findViewById(R.id.item_comm_date);
			vh.content = (TextView) v.findViewById(R.id.item_comm_content);
			vh.rating =  (RatingBar) v.findViewById(R.id.item_comm_rating);
			v.setTag(vh);
		}else{
			vh = (ViewHolder) v.getTag();
		}
		vh.name.setText(datas.get(position).getUsername());
		vh.content.setText(datas.get(position).getContent());
		vh.date.setText(datas.get(position).getCreatedAt());
		vh.rating.setRating(datas.get(position).getStars());
		return v;
	}

	class ViewHolder {
		TextView name, date, content;
		RatingBar rating;
	}

}
