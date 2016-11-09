package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;

public class OrderBean extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8777711786044192338L;
	
	private String goodsId;//��ƷID
	
	private String addressId;//�ͻ���ַ��ID
	
	private String userId;//�û���Id
	
	private String goodsName;//��Ʒ����
	
	private int goodsNum;//��Ʒ������
	
	private float totalPrice;//�������
	
	private boolean received;//�Ƿ��ջ���
	
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
}
