package com.xiaoyuan.zstreet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import cn.bmob.v3.BmobUser;

import com.xiaoyuan.zstreet.bean.UserBean;

public class PreferenceManager {

	private static PreferenceManager instence;
	private static SharedPreferences shared;

	private PreferenceManager() {
	}

	public static PreferenceManager getInstence(Context context) {
		if (instence == null) {
			synchronized (PreferenceManager.class) {
				if (instence == null) {
					instence = new PreferenceManager();
				}
			}
		}

		if (shared == null) {
			synchronized (SharedPreferences.class) {
				if (shared == null) {
					shared = context.getSharedPreferences(BmobUser
							.getCurrentUser(context, UserBean.class)
							.getUsername(), Context.MODE_PRIVATE);
				}
			}
		}

		return instence;
	}

	public SharedPreferences getPreference() {
		return shared;
	}
}
