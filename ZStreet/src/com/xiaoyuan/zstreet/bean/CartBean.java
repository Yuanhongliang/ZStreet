package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;
/**
 * 购物车的Bean
 * @author Administrator
 *
 */
public class CartBean extends BmobObject {
	private static final long serialVersionUID = -7335414532610858352L;

	private String userId;//用户ID
	
	private String goodsId;//商品ID
	
	private String imgUrl; //商品缩略图
	
	private String goodsName;//商品名字
	
	private Float goodsPrice;//商品单价
	
	private String goodsSize;//商品尺码
	
	private String goodsColor;//颜色
	
	private int goodsNum = 1;//商品数量
	
	private boolean selected = true;//是否选中

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
