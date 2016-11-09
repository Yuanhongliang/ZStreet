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
 * �������ڣ���������Խ������ݵĻ������ʼ������
 * 
 * @author Administrator
 * 
 */
public class MyApplication extends Application {

	private static Map<String, Object> map;// �����洢���ݵļ���
	private OnFinishListener listener; // ��ѯ�ɹ��Ļص��ӿ�
	private onGetUserListener onGetUserListener;//��ѯ�û��ɹ��Ļص��ӿ�
	public static RequestQueue queue; //�������
	public void setOnGetUserListener(onGetUserListener onGetUserListener) {
		this.onGetUserListener = onGetUserListener;
	}
	private static UserBean user; //�����û�

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
	 * �����û��Ĳ���
	 */
	private void findUser() {
		BmobQuery<UserBean> query = new BmobQuery<UserBean>();
		UserBean ub = BmobUser.getCurrentUser(MyApplication.this,
				UserBean.class);
		//�����ǰ�û�Ϊ��ֱ�ӷ���
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
	 * ��ʼ��Ҫ��ʾ������
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
				Log.e("ZS", "����ʧ��" + arg1);
			}
		});
	}

	/**
	 * ��ʼ��Bmob
	 */
	private void initBmob() {
		map = new HashMap<String, Object>();
		Bmob.initialize(this, "98b9357214572970748eb716c627d5be");
		BmobSMS.initialize(this, "98b9357214572970748eb716c627d5be");
		BP.init(this, "98b9357214572970748eb716c627d5be");
	}

	/**
	 * �򻺴��з�������
	 * 
	 * @param key
	 *            ���ݵļ�
	 * @param value
	 *            ���ݵ�ֵ
	 */
	public static void putData(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * �ӻ������ó�����
	 * 
	 * @param key
	 *            ���ݵļ�
	 * @param isDelete
	 *            �Ƿ�ɾ��
	 * @return Ҫ�õ�����
	 */
	public static Object getData(String key, boolean isDelete) {
		Object obj = map.get(key);
		if (isDelete) {
			map.remove(key);
		}
		return obj;
	}
	/**
	 * ��õ�ǰ�û�
	 * @return ��ǰ�û�
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
