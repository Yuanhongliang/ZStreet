package com.xiaoyuan.zstreet.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
/**
 * 用户的JavaBean
 * @author Administrator
 *
 */
public class UserBean extends BmobUser {

	private static final long serialVersionUID = -1398311366684019514L;
	//用户收藏的商品ID
	private List<String> collectIds;
	//用户余额
	private Float yue;
	//用户的配送地址
	private List<String> address;
	
	private String payNumber;
	

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public List<String> getCollectIds() {
		if(collectIds==null){
			collectIds = new ArrayList<String>();
		}
		return collectIds;
	}

	public void setCollectIds(List<String> collectIds) {
		this.collectIds = collectIds;
	}

	public Float getYue() {
		if(yue==null){
			yue=0.0f;
		}
		return yue;
	}

	public void setYue(Float yue) {
		this.yue = yue;
	}

	public List<String> getAddress() {
		if(address==null){
			address = new ArrayList<String>();
		}
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}
	
	
	
}
