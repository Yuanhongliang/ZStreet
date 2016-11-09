package com.xiaoyuan.zstreet;

import java.io.File;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseFragment;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.fragment.CartFragment;
import com.xiaoyuan.zstreet.fragment.HomeFragment;
import com.xiaoyuan.zstreet.fragment.MyFragment;
import com.xiaoyuan.zstreet.fragment.TypeFragment;
import com.xiaoyuan.zstreet.utils.DialogUtils;

/**
 * 主界面ACtivity 承载四个fragment
 * @author Administrator
 * 
 */
public class HomeActivity extends BaseActivity implements BaseInterface {

	// 盛放Fragment的布局
	@ViewInject(R.id.act_home_frame)
	private FrameLayout frame;
	// 首页
	@ViewInject(R.id.act_home_home)
	private LinearLayout home;
	// 分类
	@ViewInject(R.id.act_home_type)
	private LinearLayout type;
	// 购物车
	@ViewInject(R.id.act_home_cart)
	private LinearLayout cart;
	// 个人中心
	@ViewInject(R.id.act_home_my)
	private LinearLayout my;

	private ImageView imgs[] = new ImageView[4];
	private TextView tvs[] = new TextView[4];

	private int imgsId[] = { R.id.act_home_homeimg, R.id.act_home_typeimg,
			R.id.act_home_cartimg, R.id.act_home_myimg };
	private int tvsId[] = { R.id.act_home_hometv, R.id.act_home_typetv,
			R.id.act_home_carttv, R.id.act_home_mytv };
	private int imgIdOff[] = { R.drawable.home1, R.drawable.type1,
			R.drawable.cart1, R.drawable.my1 };
	private int imgIdOn[] = { R.drawable.home, R.drawable.type,
			R.drawable.cart, R.drawable.my };
	// 四个Fragment
	private BaseFragment[] frags = new BaseFragment[4];
	private FragmentManager manager;

	// 可滑动的菜单
	private ResideMenu menu;
	// 设置标记判断用户是否要退出
	private boolean state = false;

	public ResideMenu getMenu() {
		return menu;
	}

	/**
	 * 点击菜单的相应事件
	 */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == item1) {
				// 修改密码
				startActivity(new Intent(getAct(), ChangePassActivity.class));
			} else if (v == item2) {
				// 清除缓存
				DialogUtils.showLoadingDialog(getAct());
				File cache = getCacheDir();
				if(cache!=null){
					cache.delete();
				}
				DialogUtils.dismissDialog();
				toastShort("清除缓存成功");
			} else if (v == item3) {
				// 反馈建议
				startActivity(new Intent(getAct(), FankuiActivity.class));
			} else if (v == item4) {
				// 退出登录
				BmobUser.logOut(getAct());
				((MyFragment)frags[3]).showUserView(false);	
				((CartFragment)frags[2]).showUserView(false);	
			}
			menu.closeMenu();
		}
	};

	private ResideMenuItem item1;
	private ResideMenuItem item4;
	private ResideMenuItem item3;
	private ResideMenuItem item2;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initViews(); // 初始化控件
		initDatas(); // 初始化数据
		initOper(); // 初始化对控件的操作
	}

	@Override
	public void initViews() {
		setContentView(R.layout.act_home);// 加载布局
		ViewUtils.inject(getAct());// 初始化ViewUtils模块
		for (int i = 0; i < 4; i++) {
			imgs[i] = findImg(imgsId[i]);
			tvs[i] = findTv(tvsId[i]);
		}
	}

	@Override
	public void initDatas() {
		manager = getFragmentManager();
		frags[0] = new HomeFragment();
		frags[1] = new TypeFragment();
		frags[2] = new CartFragment();
		frags[3] = new MyFragment();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.act_home_frame, frags[0]);
		transaction.commit();

	}

	@Override
	public void initOper() {
		// 初始化菜单
		menu = new ResideMenu(this);
		menu.setUse3D(true);// 使用3D效果
		menu.setBackground(R.drawable.background);
		menu.attachToActivity(this);// 将菜单绑定到Activity
		menu.setScaleValue(0.6f);// 设置缩放程度

		// create menu items;

		item1 = new ResideMenuItem(this, R.drawable.changepass, "修改密码");
		item2 = new ResideMenuItem(this, R.drawable.cache, "清除缓存");
		item3 = new ResideMenuItem(this, R.drawable.suggest, "反馈建议");
		item4 = new ResideMenuItem(this, R.drawable.logout, "退出登录");

		item1.setOnClickListener(listener);
		item2.setOnClickListener(listener);
		item3.setOnClickListener(listener);
		item4.setOnClickListener(listener);

		menu.addMenuItem(item1, ResideMenu.DIRECTION_RIGHT);
		menu.addMenuItem(item2, ResideMenu.DIRECTION_RIGHT);
		menu.addMenuItem(item3, ResideMenu.DIRECTION_RIGHT);
		menu.addMenuItem(item4, ResideMenu.DIRECTION_RIGHT);
		menu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

	}

	// 点击底部四个选项的监听事件
	@OnClick({ R.id.act_home_home, R.id.act_home_type, R.id.act_home_cart,
			R.id.act_home_my })
	public void onClick(View v) {
		FragmentTransaction transaction = manager.beginTransaction();
		switch (v.getId()) {
		case R.id.act_home_home:
			transaction.replace(R.id.act_home_frame, frags[0]);
			updateView(0);
			break;
		case R.id.act_home_type:
			transaction.replace(R.id.act_home_frame, frags[1]);
			updateView(1);
			break;
		case R.id.act_home_cart:
			transaction.replace(R.id.act_home_frame, frags[2]);
			updateView(2);
			break;
		case R.id.act_home_my:
			transaction.replace(R.id.act_home_frame, frags[3]);
			updateView(3);
			break;
		}
		transaction.commit();
	}

	/**
	 * 修改底部点击时的视图
	 * 
	 * @param i
	 *            被点击的item
	 */
	private void updateView(int j) {
		for (int i = 0; i < 4; i++) {
			if (i == j) {
				imgs[i].setImageResource(imgIdOn[i]);
				tvs[i].setTextColor(Color.parseColor("#ff303c"));
			} else {
				imgs[i].setImageResource(imgIdOff[i]);
				tvs[i].setTextColor(Color.parseColor("#272636"));
			}
		}
	}

	// 返回键的监听
	@Override
	public void onBackPressed() {
		if (!state) {
			toastShort("再次按返回键退出");
			state = true;
			new CountDownTimer(3000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
				}

				@Override
				public void onFinish() {
					state = false;
				}
			}.start();
		} else {
			super.onBackPressed();
		}

	}

}
