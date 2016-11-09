package com.xiaoyuan.zstreet.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.myview.MyDialog;

/**
 * ��ʾDialog�Ĺ�����
 * 
 * @author Administrator
 * 
 */
public class DialogUtils {

	private static ProgressDialog progress;

	private static AlertDialog alert;

	/**
	 * ��ʾ��ʾ�Ի���
	 * 
	 * @param context
	 *            ������
	 * @param msg
	 *            ��ʾ��Ϣ
	 * @param listener
	 *            ȷ�ϵļ���
	 */
	public static void showAlertDialog(Context context, String msg,
			OnClickListener listener) {

		alert = new AlertDialog.Builder(context, R.style.ios_dialog)
				.setTitle("��ʾ").setMessage(msg)
				.setPositiveButton("ȷ��", listener).setCancelable(true)
				.setNegativeButton("ȡ��", null).create();
		alert.show();
	}

	/**
	 * ��ʾ�����еĶԻ���
	 * 
	 * @param context
	 */
	public static void showLoadingDialog(Context context) {
		progress = new MyDialog(context);
		progress.show();
	}

	/**
	 * ʹ���Զ���View�ĶԻ���(û��ȷ��ȡ����ť)
	 * 
	 * @param context
	 *            ������
	 * @param v
	 *            Ҫ��ʾ��View
	 * @param cancelable
	 *            �Ƿ����ȡ��
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
	 * ȡ���Ի���
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
