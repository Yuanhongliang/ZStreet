package com.xiaoyuan.zstreet.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.util.Log;
import c.b.BP;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.bean.UserBean;

/**
 * 程序的入口，在这里可以进行数据的缓存与初始化操作
 * 
 * @author Administrator
 * 
 */
public class MyApplication extends Application {

	private static Map<String, Object> map;// 用来存储数据的集合
	private OnFinishListener listener; // 查询成功的回调接口
	private onGetUserListener onGetUserListener;//查询用户成功的回调接口
	public static RequestQueue queue; //请求队列
	public void setOnGetUserListener(onGetUserListener onGetUserListener) {
		this.onGetUserListener = onGetUserListener;
	}
	private static UserBean user; //缓存用户

	public void setListener(OnFinishListener listener) {
		this.listener = listener;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initBmob();
		initData();
		findUser();
		queue = Volley.newRequestQueue(this);
	}

	/**
	 * 查找用户的操作
	 */
	private void findUser() {
		BmobQuery<UserBean> query = new BmobQuery<UserBean>();
		UserBean ub = BmobUser.getCurrentUser(MyApplication.this,
				UserBean.class);
		//如果当前用户为空直接返回
		if(ub==null){
			return;
		}
		query.getObject(this, ub.getObjectId(), new GetListener<UserBean>() {

			@Override
			public void onFailure(int arg0, String arg1) {
			}

			@Override
			public void onSuccess(UserBean arg0) {
				user = arg0;
				if(onGetUserListener!=null){
					onGetUserListener.onGetUser(user);
				}
			}
		});

	}

	/**
	 * 初始化要显示的数据
	 */
	private void initData() {
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		query.setLimit(3);
		query.order("-createdAt");
		query.findObjects(this, new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> arg0) {
				putData("initDatas", arg0);
				if (listener != null) {
					listener.onFinish(arg0);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("ZS", "加载失败" + arg1);
			}
		});
	}

	/**
	 * 初始化Bmob
	 */
	private void initBmob() {
		map = new HashMap<String, Object>();
		Bmob.initialize(this, "98b9357214572970748eb716c627d5be");
		BmobSMS.initialize(this, "98b9357214572970748eb716c627d5be");
		BP.init(this, "98b9357214572970748eb716c627d5be");
	}

	/**
	 * 向缓存中放入数据
	 * 
	 * @param key
	 *            数据的键
	 * @param value
	 *            数据的值
	 */
	public static void putData(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * 从缓存中拿出数据
	 * 
	 * @param key
	 *            数据的键
	 * @param isDelete
	 *            是否删除
	 * @return 要拿的数据
	 */
	public static Object getData(String key, boolean isDelete) {
		Object obj = map.get(key);
		if (isDelete) {
			map.remove(key);
		}
		return obj;
	}
	/**
	 * 获得当前用户
	 * @return 当前用户
	 */
	public UserBean getUser() {
		findUser();
		return user;
	}

	public interface OnFinishListener {
		void onFinish(List<GoodsBean> goods);
	}
	public interface onGetUserListener{
		void onGetUser(UserBean ub);
	}

}
