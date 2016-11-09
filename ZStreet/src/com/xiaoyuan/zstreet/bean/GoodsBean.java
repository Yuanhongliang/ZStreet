package com.xiaoyuan.zstreet.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 商品的JavaBean类
 * 
 * @author Administrator
 * 
 */
public class GoodsBean extends BmobObject {

	private static final long serialVersionUID = -7024602313269861256L;

	private String goodsName; // 商品名称
	private List<BmobFile> goodsImgs;// 商品图片
	private Float price;// 商品价格
	private String tag;// 标签
	private int rest;// 库存
	private int sell;//销量
	private String type;// 商品分类
	private String pinpai;// 商品品牌

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public List<BmobFile> getGoodsImgs() {
		return goodsImgs;
	}

	public void setGoodsImgs(List<BmobFile> goodsImgs) {
		this.goodsImgs = goodsImgs;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}


	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPinpai() {
		return pinpai;
	}

	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

}
