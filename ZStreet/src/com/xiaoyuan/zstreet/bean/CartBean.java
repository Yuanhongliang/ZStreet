package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;
/**
 * ���ﳵ��Bean
 * @author Administrator
 *
 */
public class CartBean extends BmobObject {
	private static final long serialVersionUID = -7335414532610858352L;

	private String userId;//�û�ID
	
	private String goodsId;//��ƷID
	
	private String imgUrl; //��Ʒ����ͼ
	
	private String goodsName;//��Ʒ����
	
	private Float goodsPrice;//��Ʒ����
	
	private String goodsSize;//��Ʒ����
	
	private String goodsColor;//��ɫ
	
	private int goodsNum = 1;//��Ʒ����
	
	private boolean selected = true;//�Ƿ�ѡ��

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
