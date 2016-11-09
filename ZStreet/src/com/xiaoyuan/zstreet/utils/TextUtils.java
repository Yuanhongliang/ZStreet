package com.xiaoyuan.zstreet.utils;

import android.content.Context;

/**
 * �����ı��Ĺ�����
 * @author Administrator
 *
 */
public class TextUtils {

	/**
	 * �����������������ȡǰ5����
	 * @param text Ҫ��ȡ���ַ���
	 * @return ��ȡ��ϵ��ַ���
	 */
	public static String subText(String text){
		if(text.length()>5){
			text = text.substring(0,5)+"...";
		}
		return text;
	}
	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context,float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
