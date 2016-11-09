package com.xiaoyuan.zstreet.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.myview.MyDialog;

/**
 * 显示Dialog的工具类
 * 
 * @author Administrator
 * 
 */
public class DialogUtils {

	private static ProgressDialog progress;

	private static AlertDialog alert;

	/**
	 * 显示提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示信息
	 * @param listener
	 *            确认的监听
	 */
	public static void showAlertDialog(Context context, String msg,
			OnClickListener listener) {

		alert = new AlertDialog.Builder(context, R.style.ios_dialog)
				.setTitle("提示").setMessage(msg)
				.setPositiveButton("确认", listener).setCancelable(true)
				.setNegativeButton("取消", null).create();
		alert.show();
	}

	/**
	 * 显示加载中的对话框
	 * 
	 * @param context
	 */
	public static void showLoadingDialog(Context context) {
		progress = new MyDialog(context);
		progress.show();
	}

	/**
	 * 使用自定义View的对话框(没有确认取消按钮)
	 * 
	 * @param context
	 *            上下文
	 * @param v
	 *            要显示的View
	 * @param cancelable
	 *            是否可以取消
	 */
	public static void showMyViewDialog(Context context, View v,
			boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				R.style.ios_dialog);
		builder.setCancelable(cancelable);
		alert = builder.create();
		alert.setView(v, 0, 0, 0, 0);
		alert.show();
	}

	/**
	 * 取消对话框
	 */
	public static void dismissDialog() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
			progress = null;
		}
		if (alert != null && alert.isShowing()) {
			alert.dismiss();
			alert = null;
		}
	}
}
