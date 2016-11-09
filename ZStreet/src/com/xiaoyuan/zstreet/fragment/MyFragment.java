package com.xiaoyuan.zstreet.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import c.b.BP;
import c.b.PListener;
import c.b.QListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.daimajia.androidviewhover.BlurLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.special.ResideMenu.ResideMenu;
import com.xiaoyuan.zstreet.HomeActivity;
import com.xiaoyuan.zstreet.LoginActivity;
import com.xiaoyuan.zstreet.OrderActivity;
import com.xiaoyuan.zstreet.PayPasswordActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.SearchResActivity;
import com.xiaoyuan.zstreet.ShowAddressActivity;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.application.MyApplication.onGetUserListener;
import com.xiaoyuan.zstreet.base.BaseFragment;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.bean.OrderBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.InputMethodUtil;
import com.xiaoyuan.zstret.anim.Techniques;

/**
 * 个人中心界面的Fragment
 * 
 * @author Administrator
 * 
 */
public class MyFragment extends BaseFragment implements BaseInterface {
	// 登录按钮
	@ViewInject(R.id.frag_my_login)
	private Button login;
	// 用户名
	private TextView username;
	// 余额
	@ViewInject(R.id.frag_my_money)
	private TextView money;
	// 眼睛
	@ViewInject(R.id.frag_my_eye)
	private ImageView eye;
	// 用户没有登录的界面
	@ViewInject(R.id.frag_my_lin1)
	private LinearLayout lin1;
	// 用户已经登录显示的页面
	@ViewInject(R.id.frag_my_lin2)
	private LinearLayout lin2;
	// 当前登录的用户
	private UserBean ub;
	// 充值的订单号
	private String orderId;
	// 充值的金额
	private Float yueMoney;

	@ViewInject(R.id.blur)
	private BlurLayout blur;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		initDatas();
		initOper();

	}

	// 初始化View控件
	@Override
	public void initViews() {

		View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_blur,
				null);
		username = (TextView) v.findViewById(R.id.frag_my_username);
		v.findViewById(R.id.frag_my_imgicon).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						toastShort("pretty cool ,right?");
					}
				});
		blur.setHoverView(v);
		blur.addChildAppearAnimator(v, R.id.frag_my_username,
				Techniques.FadeInUp);
		blur.addChildDisappearAnimator(v, R.id.frag_my_username,
				Techniques.FadeOutDown);
		blur.addChildAppearAnimator(v, R.id.frag_my_imgicon,
				Techniques.DropOut, 1200);
		blur.addChildDisappearAnimator(v, R.id.frag_my_imgicon,
				Techniques.FadeOutUp);
		blur.setBlurDuration(500);

	}

	// 初始化数据
	@Override
	public void initDatas() {
		((MyApplication) getActivity().getApplication()).getUser();
		((MyApplication) getActivity().getApplication())
				.setOnGetUserListener(new onGetUserListener() {

					@Override
					public void onGetUser(UserBean ub) {
						MyFragment.this.ub = ub;
						// 设置余额是否可见
						eye.setOnClickListener(listener1);
						// 显示用户的余额
						money.setText(ub.getYue() + "元");
						// 显示用户名
						username.setText(ub.getUsername());

					}
				});
	}

	// 初始化对控件的操作
	@Override
	public void initOper() {

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击登录按钮跳转到登录Activity
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	// 点击使余额不可见
	private OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			eye.setImageResource(R.drawable.eyeon);
			money.setText("******");// 将金额改为***
			eye.setOnClickListener(listener2);// 切换监听事件
		}
	};
	// 点击使余额可见
	private OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			eye.setImageResource(R.drawable.eyeoff);
			money.setText(ub.getYue() + "元");// 将金额改为可见
			eye.setOnClickListener(listener1);// 切换监听事件
		}
	};

	@OnClick(R.id.frag_my_menu)
	public void onMenuClick(View v) {
		// 打开右侧的菜单
		((HomeActivity) getActivity()).getMenu().openMenu(
				ResideMenu.DIRECTION_RIGHT);
	}

	// 充值按钮的操作
	@OnClick(R.id.frag_my_chongzhi)
	public void onChongzhiClick(View v) {
		View dialog = getActivity().getLayoutInflater().inflate(
				R.layout.dialogview, null);
		final EditText et = (EditText) dialog
				.findViewById(R.id.dialogview_money);
		Button ali = (Button) dialog.findViewById(R.id.dialogview_ali);
		Button wx = (Button) dialog.findViewById(R.id.dialogview_wx);
		ali.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodUtil.hideInputMethod(getActivity());
				if (et.getText().toString().trim().equals("")) {
					toastShort("请输入充值金额");
					return;
				}
				yueMoney = Float.parseFloat(et.getText().toString().trim());
				if (yueMoney <= 0) {
					toastShort("充值金额不合适");
					return;
				}
				BP.pay("充值操作", "余额充值", 0.02, true, new PListener() {

					@Override
					public void unknow() {
						toastShort("未知原因，支付失败");
					}

					@Override
					public void succeed() {
						searchOrder(orderId);
					}

					@Override
					public void orderId(String arg0) {
						orderId = arg0;
					}

					@Override
					public void fail(int arg0, String arg1) {
						if (arg0 == -3) {
							DialogUtils.showAlertDialog(getActivity(),
									"缺少支付插件，是否添加",	
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											installBmobPayPlugin("bp.db");
										}
									});
						}
					}
				});
				DialogUtils.dismissDialog();
			}
		});

		/**
		 * 微信支付的点击事件
		 */
		wx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodUtil.hideInputMethod(getActivity());
				if (et.getText().toString().trim().equals("")) {
					toastShort("请输入充值金额");
					return;
				}
				yueMoney = Float.parseFloat(et.getText().toString().trim());
				BP.pay("充值操作", "余额充值", 0.02, false, new PListener() {

					@Override
					public void unknow() {
						toastShort("未知原因，支付失败");
					}

					@Override
					public void succeed() {
						searchOrder(orderId);
					}

					@Override
					public void orderId(String arg0) {
						orderId = arg0;
					}

					@Override
					public void fail(int arg0, String arg1) {
						if (arg0 == -3) {
							DialogUtils.showAlertDialog(getActivity(),
									"缺少微信支付插件，是否添加",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											installBmobPayPlugin("bp.db");
										}
									});
						}
					}
				});
				DialogUtils.dismissDialog();
			}
		});
		DialogUtils.showMyViewDialog(getActivity(), dialog, true);
	}

	void installBmobPayPlugin(String fileName) {
		try {
			InputStream is = getActivity().getAssets().open(fileName);
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + fileName + ".apk");
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.parse("file://" + file),
					"application/vnd.android.package-archive");
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询订单是否支付成功
	private void searchOrder(String orderId) {
		BP.query(orderId, new QListener() {

			@Override
			public void succeed(String arg0) {
				if (arg0.equals("SUCCESS")) {
					insertDataBase();
				} else {
					toastShort("订单支付失败!");
				}
			}

			@Override
			public void fail(int arg0, String arg1) {
				toastShort("查询订单失败:" + arg1);
			}
		});
	}

	// 订单支付成功时添加数据库
	protected void insertDataBase() {
		UserBean newUb = new UserBean();
		newUb.setYue(ub.getYue() + yueMoney);
		newUb.update(getActivity(), ub.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				toastShort("充值成功！");
				ub.setYue(ub.getYue() + yueMoney);
				money.setText((ub.getYue() + yueMoney) + "元");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("充值失败！" + arg1);
			}
		});
	}

	// 查看我的订单
	@OnClick(R.id.frag_my_order)
	public void onOrderClick(View v) {
		DialogUtils.showLoadingDialog(getActivity());
		BmobQuery<OrderBean> query = new BmobQuery<OrderBean>();
		query.addWhereEqualTo("userId", ub.getObjectId());
		query.findObjects(getActivity(), new FindListener<OrderBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				LogE("查询失败" + arg1);
				DialogUtils.dismissDialog();
			}

			@Override
			public void onSuccess(List<OrderBean> arg0) {
				DialogUtils.dismissDialog();
				if (arg0 != null && arg0.size() > 0) {
					MyApplication.putData("orders", arg0);
					startActivity(new Intent(getActivity(), OrderActivity.class));
				} else {
					toastShort("您还没有订单哦！");
				}
			}
		});

	}

	// 查看我的收藏
	@OnClick(R.id.frag_my_favorite)
	public void onFavoClick(View v) {
		if (ub.getCollectIds().size() <= 0) {
			toastShort("没有收藏过的商品哦~");
			return;
		}
		DialogUtils.showLoadingDialog(getActivity());
		List<BmobQuery<GoodsBean>> queries = new ArrayList<BmobQuery<GoodsBean>>();
		for (int i = 0; i < ub.getCollectIds().size(); i++) {
			BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
			query.addWhereEqualTo("objectId", ub.getCollectIds().get(i));
			queries.add(query);
		}
		BmobQuery<GoodsBean> mainQuery = new BmobQuery<GoodsBean>();
		// 或查询把所有用户收藏的ID添加进入查询条件
		mainQuery.or(queries);
		mainQuery.findObjects(getActivity(), new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> arg0) {
				DialogUtils.dismissDialog();
				if (arg0 != null && arg0.size() > 0) {
					MyApplication.putData("searchResult", arg0);
					Intent intent = new Intent(getActivity(),
							SearchResActivity.class);
					intent.putExtra("title", "我的收藏");
					startActivity(intent);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				DialogUtils.dismissDialog();
				toastShort("查询失败" + arg1);
			}
		});
	}

	// 查看待付款的商品
	@OnClick(R.id.frag_my_daifukuan)
	public void onDaiFuKuanClick(View v) {
		startActivity(new Intent(getActivity(), PayPasswordActivity.class));
	}

	// 查看收货地址
	@OnClick(R.id.frag_my_address)
	public void onAddressClick(View v) {
		startActivity(new Intent(getActivity(), ShowAddressActivity.class));
	}

	@Override
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 填充我的界面布局
		View v = inflater.inflate(R.layout.frag_my, null);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ub = ((MyApplication) getActivity().getApplication()).getUser();
		UserBean current = BmobUser.getCurrentUser(getActivity(),
				UserBean.class);
		showUserView(current != null);
	}

	public void showUserView(boolean show) {
		if (show) {
			lin1.setVisibility(View.INVISIBLE);
			lin2.setVisibility(View.VISIBLE);
		} else {
			lin1.setVisibility(View.VISIBLE);
			lin2.setVisibility(View.INVISIBLE);
		}
	}

}
