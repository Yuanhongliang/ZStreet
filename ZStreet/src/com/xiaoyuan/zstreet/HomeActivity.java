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
 * ������ACtivity �����ĸ�fragment
 * @author Administrator
 * 
 */
public class HomeActivity extends BaseActivity implements BaseInterface {

	// ʢ��Fragment�Ĳ���
	@ViewInject(R.id.act_home_frame)
	private FrameLayout frame;
	// ��ҳ
	@ViewInject(R.id.act_home_home)
	private LinearLayout home;
	// ����
	@ViewInject(R.id.act_home_type)
	private LinearLayout type;
	// ���ﳵ
	@ViewInject(R.id.act_home_cart)
	private LinearLayout cart;
	// ��������
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
	// �ĸ�Fragment
	private BaseFragment[] frags = new BaseFragment[4];
	private FragmentManager manager;

	// �ɻ����Ĳ˵�
	private ResideMenu menu;
	// ���ñ���ж��û��Ƿ�Ҫ�˳�
	private boolean state = false;

	public ResideMenu getMenu() {
		return menu;
	}

	/**
	 * ����˵�����Ӧ�¼�
	 */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == item1) {
				// �޸�����
				startActivity(new Intent(getAct(), ChangePassActivity.class));
			} else if (v == item2) {
				// �������
				DialogUtils.showLoadingDialog(getAct());
				File cache = getCacheDir();
				if(cache!=null){
					cache.delete();
				}
				DialogUtils.dismissDialog();
				toastShort("�������ɹ�");
			} else if (v == item3) {
				// ��������
				startActivity(new Intent(getAct(), FankuiActivity.class));
			} else if (v == item4) {
				// �˳���¼
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
		initViews(); // ��ʼ���ؼ�
		initDatas(); // ��ʼ������
		initOper(); // ��ʼ���Կؼ��Ĳ���
	}

	@Override
	public void initViews() {
		setContentView(R.layout.act_home);// ���ز���
		ViewUtils.inject(getAct());// ��ʼ��ViewUtilsģ��
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
		// ��ʼ���˵�
		menu = new ResideMenu(this);
		menu.setUse3D(true);// ʹ��3DЧ��
		menu.setBackground(R.drawable.background);
		menu.attachToActivity(this);// ���˵��󶨵�Activity
		menu.setScaleValue(0.6f);// �������ų̶�

		// create menu items;

		item1 = new ResideMenuItem(this, R.drawable.changepass, "�޸�����");
		item2 = new ResideMenuItem(this, R.drawable.cache, "�������");
		item3 = new ResideMenuItem(this, R.drawable.suggest, "��������");
		item4 = new ResideMenuItem(this, R.drawable.logout, "�˳���¼");

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

	// ����ײ��ĸ�ѡ��ļ����¼�
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
	 * �޸ĵײ����ʱ����ͼ
	 * 
	 * @param i
	 *            �������item
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

	// ���ؼ��ļ���
	@Override
	public void onBackPressed() {
		if (!state) {
			toastShort("�ٴΰ����ؼ��˳�");
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
