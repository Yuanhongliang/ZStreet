package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;

public class CommentBean extends BmobObject {

	
	private static final long serialVersionUID = 1L;

	
	//商品ID
	private String goodsId;
	
	//用户ID
	private String userId;
	//用户名
	private String username;
	//评论内容
	private String content;
	//星级
	private Integer stars;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String context) {
		this.content = context;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	
	
}
