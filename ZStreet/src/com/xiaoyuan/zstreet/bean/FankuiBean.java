package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;

public class FankuiBean extends BmobObject {

	private static final long serialVersionUID = 1L;
	private String content;
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
