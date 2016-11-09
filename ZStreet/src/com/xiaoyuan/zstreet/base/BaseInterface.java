package com.xiaoyuan.zstreet.base;

/**
 * 向上抽取的接口，方便执行各种操作
 * 
 * @author Administrator
 * 
 */
public interface BaseInterface {
	
	
	/**
	 * 初始化要操作的控件
	 */
	void initViews();
	/**
	 * 初始化要操作的数据
	 */
	void initDatas();

	/**
	 * 初始化对控件的操作
	 */
	void initOper();

}
