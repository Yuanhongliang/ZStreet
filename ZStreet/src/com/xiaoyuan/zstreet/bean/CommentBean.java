package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;

public class CommentBean extends BmobObject {

	
	private static final long serialVersionUID = 1L;

	
	//��ƷID
	private String goodsId;
	
	//�û�ID
	private String userId;
	//�û���
	private String username;
	//��������
	private String content;
	//�Ǽ�
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
