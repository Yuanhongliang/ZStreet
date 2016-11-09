package com.xiaoyuan.zstreet.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * ��Ʒ��JavaBean��
 * 
 * @author Administrator
 * 
 */
public class GoodsBean extends BmobObject {

	private static final long serialVersionUID = -7024602313269861256L;

	private String goodsName; // ��Ʒ����
	private List<BmobFile> goodsImgs;// ��ƷͼƬ
	private Float price;// ��Ʒ�۸�
	private String tag;// ��ǩ
	private int rest;// ���
	private int sell;//����
	private String type;// ��Ʒ����
	private String pinpai;// ��ƷƷ��

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
