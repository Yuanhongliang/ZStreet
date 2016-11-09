package com.xiaoyuan.zstreet.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.LoginActivity;
import com.xiaoyuan.zstreet.PayActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.adapter.CartLvAdapter;
import com.xiaoyuan.zstreet.adapter.CartLvAdapter.onItemViewClickListener;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseFragment;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.CartBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;

/**
 * 购物车界面的Fragment
 * 
 * @author Administrator
 * 
 */
public class CartFragment extends BaseFragment implements BaseInterface {

	// 登录按钮
	@ViewInject(R.id.frag_cart_login)
	private Button login;

	// 购物车ListView
	@ViewInject(R.id.frag_cart_lv)
	private ListView lv;

	// 一共的件数
	@ViewInject(R.id.frag_cart_count)
	private TextView allCount;

	// 一共的钱
	@ViewInject(R.id.frag_cart_money)
	private TextView allMoney;

	// 全选按钮
	@ViewInject(R.id.frag_cart_chooseall)
	private ImageView choose;

	// 用户已登录的页面
	@ViewInject(R.id.frag_cart_haslogin)
	private LinearLayout hasLogin;

	// 用户未登录的页面
	@ViewInject(R.id.frag_cart_notlogin)
	private LinearLayout notLogin;
	//
	private CartLvAdapter adapter;
	// 当前登录的用户
	private UserBean ub;

	// 总金额
	private float totalMoney = 0.0f;

	// 总数量
	private int totalNum = 0;

	// 购物车的数据
	protected List<CartBean> datas;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		initDatas();
		initOper();
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initDatas() {
		// 获取本地的缓存用户
		ub = ((MyApplication) getActivity().getApplication()).getUser();
		if (ub != null) {
			DialogUtils.showLoadingDialog(getActivity());
			BmobQuery<CartBean> query = new BmobQuery<CartBean>();
			query.addWhereEqualTo("userId", ub.getObjectId());
			query.findObjects(getActivity(), new FindListener<CartBean>() {

				@Override
				public void onSuccess(List<CartBean> arg0) {
					DialogUtils.dismissDialog();
					datas = arg0;
					// 为ListView添加数据源
					if (arg0 != null && arg0.size() > 0) {
						adapter = new CartLvAdapter(getActivity(), arg0);
						lv.setAdapter(adapter);
						totalNum = 0;
						totalMoney = 0;
						// 计算获得数据的总金额
						for (int i = 0; i < arg0.size(); i++) {
							totalNum += arg0.get(i).getGoodsNum();
							totalMoney += arg0.get(i).getGoodsPrice()
									* arg0.get(i).getGoodsNum();
						}
						allMoney.setText(totalMoney + "");
						allCount.setText(totalNum + "");
						setListener();
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					toastShort("查询失败" + arg1);
				}
			});
		}
	}

	// 全选按钮的监听
	private OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 改变图标
			choose.setImageResource(R.drawable.allchoose);
			// 切换监听事件
			choose.setOnClickListener(listener2);
			totalMoney = 0;
			totalNum = 0;
			// 设置所有的Item状态为全选
			for (int i = 0; i < datas.size(); i++) {
				// 遍历集合相加得到总金额
				totalNum += datas.get(i).getGoodsNum();
				totalMoney += datas.get(i).getGoodsPrice()
						* datas.get(i).getGoodsNum();
				// 设置i下标的item为选中状态
				datas.get(i).setSelected(true);
				// 更改图片为选中
				adapter.getImgs().get(i).setImageResource(R.drawable.allchoose);
			}
			// 设置总金额
			allMoney.setText(totalMoney + "");
			// 设置总数量
			allCount.setText(totalNum + "");
		}
	};

	// 取消全选按钮的监听
	private OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 改变图标
			choose.setImageResource(R.drawable.notchoose);
			// 切换监听事件
			choose.setOnClickListener(listener1);
			allMoney.setText(0.0 + "");
			allCount.setText(0 + "");
			// 设置所有的Item状态为全不选
			for (int i = 0; i < datas.size(); i++) {
				datas.get(i).setSelected(false);
				adapter.getImgs().get(i).setImageResource(R.drawable.notchoose);
			}
		}
	};

	@Override
	public void initOper() {
		// 登录按钮
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击登录按钮跳转到LoginActivity
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});

	}

	// 设置item选择数量以及未选中的监听
	private void setListener() {

		if (ub != null) {
			// 全选按钮的监听
			choose.setOnClickListener(listener2);
			// 子布局中控件的监听
			adapter.setListener(new onItemViewClickListener() {
				// 减少数量的监听
				@Override
				public void onJianClick(TextView num, TextView money,
						int position) {
					int i = Integer.parseInt(num.getText().toString().trim());
					if (i > 1) {
						// 数量不可小于1
						datas.get(position).setGoodsNum(i - 1);
						num.setText((i - 1) + "");
						money.setText(datas.get(position).getGoodsPrice()
								* (i - 1) + "");
						// 如果item选中状态时才修改总金额和总数量
						if (datas.get(position).isSelected()) {
							totalMoney = Float.parseFloat(allMoney.getText()
									.toString().trim())
									- datas.get(position).getGoodsPrice();
							allMoney.setText(totalMoney + "");
							totalNum = Integer.parseInt(allCount.getText()
									.toString().trim()) - 1;
							allCount.setText(totalNum + "");
						}
					}
				}

				@Override
				public void onJiaClick(TextView num, TextView money,
						int position) {
					int i = Integer.parseInt(num.getText().toString().trim());
					datas.get(position).setGoodsNum(i + 1);
					// 设置子控件的数量与金额
					num.setText((i + 1) + "");
					money.setText(datas.get(position).getGoodsPrice() * (i + 1)
							+ "");
					// 如果item选中状态时才修改总金额和总数量
					if (datas.get(position).isSelected()) {
						totalMoney = Float.parseFloat(allMoney.getText()
								.toString().trim())
								+ datas.get(position).getGoodsPrice();
						allMoney.setText(totalMoney + "");
						totalNum = Integer.parseInt(allCount.getText()
								.toString().trim()) + 1;
						allCount.setText(totalNum + "");
					}
				}

				@Override
				public void onChooseClick(ImageView img, int position) {
					// 如果当前状态为选择
					if (datas.get(position).isSelected()) {
						// 将选中状态改变未选
						datas.get(position).setSelected(false);
						// 修改图标
						img.setImageResource(R.drawable.notchoose);
						// 修改总金额和总数量（减去对应的）
						totalMoney = Float.parseFloat(allMoney.getText()
								.toString().trim())
								- datas.get(position).getGoodsPrice()
								* datas.get(position).getGoodsNum();
						allMoney.setText(totalMoney + "");
						totalNum = Integer.parseInt(allCount.getText()
								.toString().trim())
								- datas.get(position).getGoodsNum();
						allCount.setText(totalNum + "");

					} else {
						// 将选中状态改变选中
						datas.get(position).setSelected(true);
						img.setImageResource(R.drawable.allchoose);
						// 修改总金额和总数量（加上对应的）
						totalMoney = Float.parseFloat(allMoney.getText()
								.toString().trim())
								+ datas.get(position).getGoodsPrice()
								* datas.get(position).getGoodsNum();
						allMoney.setText(totalMoney + "");
						totalNum = Integer.parseInt(allCount.getText()
								.toString().trim())
								+ datas.get(position).getGoodsNum();
						allCount.setText(totalNum + "");
					}
				}
			});
		}
	}

	// 清空按钮的操作
	@OnClick(R.id.frag_cart_clear)
	public void onClearClick(View v) {

		if (datas == null || datas.size() == 0) {
			toastShort("没有数据了");
			return;
		}
		// 组装要删除的对象
		List<BmobObject> objs = new ArrayList<BmobObject>();
		for (int i = 0; i < datas.size(); i++) {
			CartBean cb = new CartBean();
			cb.setObjectId(datas.get(i).getObjectId());
			objs.add(cb);
		}
		// 批量删除数据
		new BmobObject().deleteBatch(getActivity(), objs, new DeleteListener() {

			@Override
			public void onSuccess() {
				// 更新UI
				datas.clear();
				adapter.notifyDataSetChanged();
				totalMoney = 0.0f;
				totalNum = 0;
				allMoney.setText(0.0 + "");
				allCount.setText(0 + "");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("批量删除失败" + arg1);
			}
		});
	}

	// 结算按钮的操作
	@OnClick(R.id.frag_cart_pay)
	public void onPayClick(View v) {
		// 如果购物车为空直接返回
		if (datas == null || datas.size() <= 0) {
			return;
		}
		// 如果选中商品为空直接返回
		List<CartBean> cbs = new ArrayList<CartBean>();
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).isSelected()) {
				cbs.add(datas.get(i));
			}
		}
		if (cbs == null || cbs.size() <= 0) {
			toastShort("还没有选择商品呢~");
			return;
		}
		// 将购物侧对象传递给支付页面
		MyApplication.putData("cbs", cbs);
		Intent intent = new Intent();
		intent.putExtra("money", totalMoney);
		// 在购物车对象中删除
		deleteCart(cbs);
		intent.setClass(getActivity(), PayActivity.class);
		startActivity(intent);
	}

	/**
	 * 购物车购买成功时删除数据库中的数据
	 */
	protected void deleteCart(final List<CartBean> cbs) {
		// 组装要删除的对象
		List<BmobObject> objs = new ArrayList<BmobObject>();
		for (int i = 0; i < cbs.size(); i++) {
			CartBean cb = new CartBean();
			cb.setObjectId(cbs.get(i).getObjectId());
			objs.add(cb);
		}
		deleteFromCart(objs, cbs);
	}

	/**
	 * 从数据库将购物车的数据删除
	 * 
	 * @param objs
	 * @param num
	 * @param mon
	 * @param cbs
	 */
	private void deleteFromCart(List<BmobObject> objs, final List<CartBean> cbs) {
		// 批量删除数据
		new BmobObject().deleteBatch(getActivity(), objs, new DeleteListener() {

			@Override
			public void onSuccess() {
				// 更新UI
				datas.removeAll(cbs);
				adapter.notifyDataSetChanged();
				totalMoney = 0.0f;
				totalNum = 0;
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("ZStreet", "删除失败" + arg1);
			}
		});
	}

	@Override
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_cart, null);
		ViewUtils.inject(this, v);
		return v;
	}

	// 判断用户是否登录
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		UserBean user = BmobUser.getCurrentUser(getActivity(), UserBean.class);
		if (user == null) {
			// 如果未登录，设置为未登录视图
			hasLogin.setVisibility(View.INVISIBLE);
			notLogin.setVisibility(View.VISIBLE);
		} else {
			// 如果已经登录，设置为已登录视图
			hasLogin.setVisibility(View.VISIBLE);
			notLogin.setVisibility(View.INVISIBLE);
			allMoney.setText(totalMoney + "");
			allCount.setText(totalNum + "");
			// 每次返回购物车时都默认全选
			choose.setImageResource(R.drawable.allchoose);
		}

	}

	public void showUserView(boolean show) {
		if (show) {
			notLogin.setVisibility(View.INVISIBLE);
			hasLogin.setVisibility(View.VISIBLE);
		} else {
			notLogin.setVisibility(View.VISIBLE);
			hasLogin.setVisibility(View.INVISIBLE);
		}
	}

}
