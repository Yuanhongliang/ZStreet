package com.xiaoyuan.zstreet.bean;

import cn.bmob.v3.BmobObject;
/**
 * 收货地址的JavaBean
 * @author Administrator
 *
 */
public class AddressBean extends BmobObject {
	private static final long serialVersionUID = 679994366084093616L;

	private String address;//收货地址
	
	private String phoneNumber;//手机号
	
	private String name;//姓名
	
	private String uId;//用户ID

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}
	
	
	
}
