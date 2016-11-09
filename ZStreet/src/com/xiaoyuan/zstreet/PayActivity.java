package com.xiaoyuan.zstreet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import c.b.BP;
import c.b.PListener;
import c.b.QListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.adapter.PayGoodsAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.AddressBean;
import com.xiaoyuan.zstreet.bean.CartBean;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.bean.OrderBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.PreferenceManager;

/**
 * 支付页面的Activity
 * 
 * @author Administrator
 * 
 */
public class PayActivity extends BaseActivity implements BaseInterface {

	// 显示收货地址的布局
	@ViewInject(R.id.act_pay_lin)
	private LinearLayout lin;
	// 收货地址
	@ViewInject(R.id.act_pay_add)
	private TextView address;
	// 收货人姓名
	@ViewInject(R.id.act_pay_name)
	private TextView name;
	// 收货人联系方式
	@ViewInject(R.id.act_pay_phone)
	private TextView phone;
	// 显示要付款商品的LV
	@ViewInject(R.id.act_pay_lv)
	private ListView lv;

	// 合计金额
	@ViewInject(R.id.act_pay_totalMoney)
	private TextView totalMoney;

	// 单个购买商品
	private CartBean cb;
	// 收货地址对象
	private AddressBean ab;
	// 多个购买商品
	private List<CartBean> cbs;
	// 收藏商品的Adapter
	private PayGoodsAdapter adapter;
	// 总金额
	private float money;

	// 当前用户
	private UserBean ub;

	// 支付成功的订单ID
	private String orderId;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initViews();
		initDatas();
		initOper();
	}

	@Override
	public void initViews() {
		setContentView(R.layout.act_pay);
		ViewUtils.inject(getAct());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		// 从缓存中拿出对象
		ub = ((MyApplication) getApplication()).getUser();
		// 立即购买的数据
		cb = (CartBean) MyApplication.getData("CartBean", true);
		// 购物侧购买的数据
		cbs = (List<CartBean>) MyApplication.getData("cbs", true);
		// 获取收货地址
		getAddress();
	}

	// 查询用户的收货地址
	private void getAddress() {
		DialogUtils.showLoadingDialog(getAct());
		// TODO Auto-generated method stub
		BmobQuery<AddressBean> query = new BmobQuery<AddressBean>();
		query.addWhereEqualTo("uId", ub.getObjectId());
		query.findObjects(getAct(), new FindListener<AddressBean>() {

			@Override
			public void onSuccess(List<AddressBean> datas) {
				DialogUtils.dismissDialog();
				if (datas != null && datas.size() > 0) {
					ab = datas.get(0);
					// 如果拿到地址就显示到控件上
					address.setText(ab.getAddress());
					name.setText(ab.getName());
					phone.setText(ab.getPhoneNumber());
				} else {
					toastShort("还没有添加收货地址，快去添加吧");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				DialogUtils.dismissDialog();
			}
		});
	}

	@Override
	public void initOper() {
		// 直接商品购买的情况
		if (cb != null) {
			// 模拟一个集合设置数据源
			List<CartBean> cbs = new ArrayList<CartBean>();
			cbs.add(cb);
			adapter = new PayGoodsAdapter(getAct(), cbs);
			lv.setAdapter(adapter);
			money = cb.getGoodsNum() * cb.getGoodsPrice();
			totalMoney.setText("¥" + money);
		}
		// 购物车购买的情况
		if (cbs != null) {
			adapter = new PayGoodsAdapter(getAct(), cbs);
			lv.setAdapter(adapter);
			money = getIntent().getFloatExtra("money", 0);
			totalMoney.setText("¥" + money);
		}
	}

	// 返回键的监听
	@OnClick(R.id.act_pay_back)
	public void onBackClick(View v) {
		finish();
	}

	// 选择地址的监听
	@OnClick(R.id.act_pay_lin)
	public void onChooseAddress(View v) {
		Intent intent = new Intent(getAct(), ShowAddressActivity.class);
		intent.putExtra("code", 1);
		startActivityForResult(intent, 1);
	}

	// 付款按钮的监听
	@OnClick(R.id.act_pay_pay)
	public void onPayClick(View v) {
		// 选择支付方式
		AlertDialog alert = new AlertDialog.Builder(getAct()).setTitle("支付方式")
				.setPositiveButton("余额支付", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						DialogUtils.showLoadingDialog(getAct());
						payByYue();
					}
				}).setNegativeButton("支付宝支付", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						payByAli();
					}
				}).setNeutralButton("微信支付", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						payByWX();
					}

				}).setCancelable(true).create();

		alert.show();
	}

	// 微信支付
	private void payByWX() {
		BP.pay("付款", "ZStreet商品购买", 0.02, false, new PListener() {

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
					DialogUtils.showAlertDialog(getAct(), "缺少支付插件，是否添加",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									installBmobPayPlugin("bp.db");
								}
							});
				}
			}
		});
	}

	void installBmobPayPlugin(String fileName) {
		try {
			InputStream is = getAssets().open(fileName);
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

	// 支付宝支付
	protected void payByAli() {
		BP.pay("付款", "ZStreet商品购买", 0.02, true, new PListener() {

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
					DialogUtils.showAlertDialog(getAct(), "缺少支付插件，是否添加",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									installBmobPayPlugin("bp.db");
								}
							});
				}
				toastShort("支付失败" + arg1);
			}
		});
	}

	// 查询订单是否成功
	protected void searchOrder(String orderId2) {
		BP.query(orderId2, new QListener() {

			@Override
			public void succeed(String arg0) {
				if (arg0.equals("SUCCESS")) {
					if (cb != null) {
						insertOrder(cb);
					} else if (cbs != null) {
						// 将集合组装成数组插入数据库
						CartBean array[] = new CartBean[cbs.size()];
						for (int i = 0; i < cbs.size(); i++) {
							array[i] = cbs.get(i);
						}
						insertOrder(array);
					}
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

	// 余额支付
	protected void payByYue() {

		final String rightWord = PreferenceManager.getInstence(getAct())
				.getPreference().getString("password", "");
		if (TextUtils.isEmpty(rightWord)) {
			toastShort("还没有设置支付密码，快去添加吧");
			DialogUtils.dismissDialog();
			return;
		}
		View view = View.inflate(getAct(), R.layout.view_edit_password, null);
		final EditText et = (EditText) view.findViewById(R.id.edit_pass_et);
		Button but = (Button) view.findViewById(R.id.edit_pass_but);
		but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String word = et.getText().toString().trim();
				if (word == null || word.equals("")) {
					toastShort("请输入密码");
					return;
				}
				if (word.length() != 6) {
					toastShort("请输入6位密码");
					return;
				}
				if (!rightWord.equals(word)) {
					toastShort("密码输入错误");
					return;
				} else {
					buyByYue();
				}
				DialogUtils.dismissDialog();
			}

			private void buyByYue() {
				if (ub.getYue() < money) {
					toastShort("余额不足，请充值或选择其他方式");
					return;
				}
				UserBean newUb = new UserBean();
				newUb.setYue(ub.getYue() - money);
				ub.setYue(ub.getYue() - money);
				newUb.update(getAct(), ub.getObjectId(), new UpdateListener() {

					@Override
					public void onSuccess() {
						if (cb != null) {
							// 立即购买的情况
							insertOrder(cb);
						} else if (cbs != null) {
							// 将集合组装成数组插入数据库
							CartBean array[] = new CartBean[cbs.size()];
							for (int i = 0; i < cbs.size(); i++) {
								array[i] = cbs.get(i);
							}
							insertOrder(array);
						}
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						toastShort("购买失败！" + arg1);
					}
				});
			}
		});
		DialogUtils.showMyViewDialog(getAct(), view, true);
	}

	/**
	 * 添加订单到数据库
	 */
	protected void insertOrder(CartBean... cbs) {

		for (int i = 0; i < cbs.length; i++) {
			CartBean cb = cbs[i];
			OrderBean ob = new OrderBean();
			final GoodsBean gb = new GoodsBean();
			// 将库存和销量更改
			gb.setObjectId(cb.getGoodsId());
			gb.increment("sell", cb.getGoodsNum());
			gb.increment("rest", -cb.getGoodsNum());
			ob.setAddressId(ab.getObjectId());
			ob.setGoodsId(cb.getGoodsId());
			ob.setGoodsName(cb.getGoodsName());
			ob.setGoodsNum(cb.getGoodsNum());
			ob.setUserId(ub.getObjectId());
			ob.setReceived(false);
			ob.setTotalPrice(cb.getGoodsNum() * cb.getGoodsPrice());
			ob.save(getAct(), new SaveListener() {

				@Override
				public void onSuccess() {
					DialogUtils.dismissDialog();
					toastShort("购买成功,去我的订单查看吧!");
					gb.update(getAct(), new UpdateListener() {

						@Override
						public void onSuccess() {
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							logE("更改数字失败" + arg1);
						}
					});
					finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					DialogUtils.dismissDialog();
					logE("失败" + arg1);
				}
			});
		}
	}

	// 选择地址返回时携带数据
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		ab = (AddressBean) MyApplication.getData("address", true);
		// 如果拿到地址就显示到控件上
		if (ab != null) {
			logE(ab.getAddress());
			address.setText(ab.getAddress());
			name.setText(ab.getName());
			phone.setText(ab.getPhoneNumber());
		}
	}

}
