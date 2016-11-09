package com.xiaoyuan.zstreet.utils;

import android.content.Context;

/**
 * 处理文本的工具类
 * @author Administrator
 *
 */
public class TextUtils {

	/**
	 * 如果文字数量过长截取前5个字
	 * @param text 要截取的字符串
	 * @return 截取完毕的字符串
	 */
	public static String subText(String text){
		if(text.length()>5){
			text = text.substring(0,5)+"...";
		}
		return text;
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context,float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
