package com.xiaoyuan.zstreet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.base.MyBaseAdapter;
import com.xiaoyuan.zstreet.bean.CartBean;
import com.xiaoyuan.zstreet.utils.ImageLoaderUtils;
/**
 * ���ﳵ�б��Adapter
 * @author Administrator
 *
 */
public class CartLvAdapter extends MyBaseAdapter {

	private Context context;//������
	private List<CartBean> data;//����Դ
	private List<ImageView> imgs;//ѡ�пؼ��ļ���
	
	
	public List<ImageView> getImgs() {
		return imgs;
	}

	private onItemViewClickListener listener;
	
	public void setListener(onItemViewClickListener listener) {
		this.listener = listener;
	}

	public CartLvAdapter(Context context, List<CartBean> arg0) {
		this.context = context;
		this.data = arg0;
		imgs = new ArrayList<ImageView>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		final ViewHolder vh;
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_cart, null);
			vh = new ViewHolder();
			vh.choose = (ImageView) v.findViewById(R.id.item_cart_choose);
			vh.goodsImg = (ImageView) v.findViewById(R.id.item_cart_img);
			vh.jian = (Button) v.findViewById(R.id.item_cart_jian);
			vh.jia = (Button) v.findViewById(R.id.item_cart_jia);
			vh.goodsName = (TextView) v.findViewById(R.id.item_cart_info);
			vh.goodsColor = (TextView) v.findViewById(R.id.item_cart_color);
			vh.num = (TextView) v.findViewById(R.id.item_cart_num);
			vh.goodsSize = (TextView) v.findViewById(R.id.item_cart_size);
			vh.money = (TextView) v.findViewById(R.id.item_cart_money);
			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}
		
		CartBean cb = data.get(position);
		vh.goodsName.setText(cb.getGoodsName());
		vh.goodsColor.setText(cb.getGoodsColor());
		vh.goodsSize.setText(cb.getGoodsSize());
		vh.money.setText(cb.getGoodsPrice()+"");
		vh.num.setText(cb.getGoodsNum()+"");
		ImageLoaderUtils.loadSmallImg(vh.goodsImg, cb.getImgUrl());
		if(data.get(position).isSelected()){
			//״̬Ϊѡ�л���δѡ��
			vh.choose.setImageResource(R.drawable.allchoose);
		}else{
			vh.choose.setImageResource(R.drawable.notchoose);
		}
		//��ͼƬ�ؼ����뼯�Ϸ��ظ�Fragment���޸�
		imgs.add(vh.choose);
		//���������ļ���
		vh.jia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onJiaClick(vh.num, vh.money, position);
				}
			}
		});
		//���������ļ���
		vh.jian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onJianClick(vh.num, vh.money, position);
				}
			}
		});
		//ѡ��ť�ļ���
		vh.choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onChooseClick(vh.choose, position);
				}
			}
		});
		return v;
	}

	
	public interface onItemViewClickListener{
		/**
		 * ���������ļ���
		 * @param num item�������ؼ�
		 * @param money item�Ľ��ؼ�
		 * @param position item���±�
		 */
		void onJianClick(TextView num,TextView money,int position);
		/**
		 * ���������ļ���
		 * @param num item�������ؼ�
		 * @param money item�Ľ��ؼ�
		 * @param position item���±�
		 */
		void onJiaClick(TextView num,TextView money,int position);
		/**
		 * ѡ����δѡ�еļ���
		 * @param img �����Ŀؼ�
		 * @param position item���±�
		 */
		void onChooseClick(ImageView img,int position);
	}

	class ViewHolder {
		ImageView choose, goodsImg;
		Button jia, jian;
		TextView goodsName, goodsColor, goodsSize, num, money;
	}

}
