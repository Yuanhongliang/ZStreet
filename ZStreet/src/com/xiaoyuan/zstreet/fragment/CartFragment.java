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
 * ���ﳵ�����Fragment
 * 
 * @author Administrator
 * 
 */
public class CartFragment extends BaseFragment implements BaseInterface {

	// ��¼��ť
	@ViewInject(R.id.frag_cart_login)
	private Button login;

	// ���ﳵListView
	@ViewInject(R.id.frag_cart_lv)
	private ListView lv;

	// һ���ļ���
	@ViewInject(R.id.frag_cart_count)
	private TextView allCount;

	// һ����Ǯ
	@ViewInject(R.id.frag_cart_money)
	private TextView allMoney;

	// ȫѡ��ť
	@ViewInject(R.id.frag_cart_chooseall)
	private ImageView choose;

	// �û��ѵ�¼��ҳ��
	@ViewInject(R.id.frag_cart_haslogin)
	private LinearLayout hasLogin;

	// �û�δ��¼��ҳ��
	@ViewInject(R.id.frag_cart_notlogin)
	private LinearLayout notLogin;
	//
	private CartLvAdapter adapter;
	// ��ǰ��¼���û�
	private UserBean ub;

	// �ܽ��
	private float totalMoney = 0.0f;

	// ������
	private int totalNum = 0;

	// ���ﳵ������
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
		// ��ȡ���صĻ����û�
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
					// ΪListView�������Դ
					if (arg0 != null && arg0.size() > 0) {
						adapter = new CartLvAdapter(getActivity(), arg0);
						lv.setAdapter(adapter);
						totalNum = 0;
						totalMoney = 0;
						// ���������ݵ��ܽ��
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
					toastShort("��ѯʧ��" + arg1);
				}
			});
		}
	}

	// ȫѡ��ť�ļ���
	private OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// �ı�ͼ��
			choose.setImageResource(R.drawable.allchoose);
			// �л������¼�
			choose.setOnClickListener(listener2);
			totalMoney = 0;
			totalNum = 0;
			// �������е�Item״̬Ϊȫѡ
			for (int i = 0; i < datas.size(); i++) {
				// ����������ӵõ��ܽ��
				totalNum += datas.get(i).getGoodsNum();
				totalMoney += datas.get(i).getGoodsPrice()
						* datas.get(i).getGoodsNum();
				// ����i�±��itemΪѡ��״̬
				datas.get(i).setSelected(true);
				// ����ͼƬΪѡ��
				adapter.getImgs().get(i).setImageResource(R.drawable.allchoose);
			}
			// �����ܽ��
			allMoney.setText(totalMoney + "");
			// ����������
			allCount.setText(totalNum + "");
		}
	};

	// ȡ��ȫѡ��ť�ļ���
	private OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// �ı�ͼ��
			choose.setImageResource(R.drawable.notchoose);
			// �л������¼�
			choose.setOnClickListener(listener1);
			allMoney.setText(0.0 + "");
			allCount.setText(0 + "");
			// �������е�Item״̬Ϊȫ��ѡ
			for (int i = 0; i < datas.size(); i++) {
				datas.get(i).setSelected(false);
				adapter.getImgs().get(i).setImageResource(R.drawable.notchoose);
			}
		}
	};

	@Override
	public void initOper() {
		// ��¼��ť
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����¼��ť��ת��LoginActivity
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});

	}

	// ����itemѡ�������Լ�δѡ�еļ���
	private void setListener() {

		if (ub != null) {
			// ȫѡ��ť�ļ���
			choose.setOnClickListener(listener2);
			// �Ӳ����пؼ��ļ���
			adapter.setListener(new onItemViewClickListener() {
				// ���������ļ���
				@Override
				public void onJianClick(TextView num, TextView money,
						int position) {
					int i = Integer.parseInt(num.getText().toString().trim());
					if (i > 1) {
						// ��������С��1
						datas.get(position).setGoodsNum(i - 1);
						num.setText((i - 1) + "");
						money.setText(datas.get(position).getGoodsPrice()
								* (i - 1) + "");
						// ���itemѡ��״̬ʱ���޸��ܽ���������
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
					// �����ӿؼ�����������
					num.setText((i + 1) + "");
					money.setText(datas.get(position).getGoodsPrice() * (i + 1)
							+ "");
					// ���itemѡ��״̬ʱ���޸��ܽ���������
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
					// �����ǰ״̬Ϊѡ��
					if (datas.get(position).isSelected()) {
						// ��ѡ��״̬�ı�δѡ
						datas.get(position).setSelected(false);
						// �޸�ͼ��
						img.setImageResource(R.drawable.notchoose);
						// �޸��ܽ�������������ȥ��Ӧ�ģ�
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
						// ��ѡ��״̬�ı�ѡ��
						datas.get(position).setSelected(true);
						img.setImageResource(R.drawable.allchoose);
						// �޸��ܽ��������������϶�Ӧ�ģ�
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

	// ��հ�ť�Ĳ���
	@OnClick(R.id.frag_cart_clear)
	public void onClearClick(View v) {

		if (datas == null || datas.size() == 0) {
			toastShort("û��������");
			return;
		}
		// ��װҪɾ���Ķ���
		List<BmobObject> objs = new ArrayList<BmobObject>();
		for (int i = 0; i < datas.size(); i++) {
			CartBean cb = new CartBean();
			cb.setObjectId(datas.get(i).getObjectId());
			objs.add(cb);
		}
		// ����ɾ������
		new BmobObject().deleteBatch(getActivity(), objs, new DeleteListener() {

			@Override
			public void onSuccess() {
				// ����UI
				datas.clear();
				adapter.notifyDataSetChanged();
				totalMoney = 0.0f;
				totalNum = 0;
				allMoney.setText(0.0 + "");
				allCount.setText(0 + "");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("����ɾ��ʧ��" + arg1);
			}
		});
	}

	// ���㰴ť�Ĳ���
	@OnClick(R.id.frag_cart_pay)
	public void onPayClick(View v) {
		// ������ﳵΪ��ֱ�ӷ���
		if (datas == null || datas.size() <= 0) {
			return;
		}
		// ���ѡ����ƷΪ��ֱ�ӷ���
		List<CartBean> cbs = new ArrayList<CartBean>();
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).isSelected()) {
				cbs.add(datas.get(i));
			}
		}
		if (cbs == null || cbs.size() <= 0) {
			toastShort("��û��ѡ����Ʒ��~");
			return;
		}
		// ���������󴫵ݸ�֧��ҳ��
		MyApplication.putData("cbs", cbs);
		Intent intent = new Intent();
		intent.putExtra("money", totalMoney);
		// �ڹ��ﳵ������ɾ��
		deleteCart(cbs);
		intent.setClass(getActivity(), PayActivity.class);
		startActivity(intent);
	}

	/**
	 * ���ﳵ����ɹ�ʱɾ�����ݿ��е�����
	 */
	protected void deleteCart(final List<CartBean> cbs) {
		// ��װҪɾ���Ķ���
		List<BmobObject> objs = new ArrayList<BmobObject>();
		for (int i = 0; i < cbs.size(); i++) {
			CartBean cb = new CartBean();
			cb.setObjectId(cbs.get(i).getObjectId());
			objs.add(cb);
		}
		deleteFromCart(objs, cbs);
	}

	/**
	 * �����ݿ⽫���ﳵ������ɾ��
	 * 
	 * @param objs
	 * @param num
	 * @param mon
	 * @param cbs
	 */
	private void deleteFromCart(List<BmobObject> objs, final List<CartBean> cbs) {
		// ����ɾ������
		new BmobObject().deleteBatch(getActivity(), objs, new DeleteListener() {

			@Override
			public void onSuccess() {
				// ����UI
				datas.removeAll(cbs);
				adapter.notifyDataSetChanged();
				totalMoney = 0.0f;
				totalNum = 0;
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("ZStreet", "ɾ��ʧ��" + arg1);
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

	// �ж��û��Ƿ��¼
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		UserBean user = BmobUser.getCurrentUser(getActivity(), UserBean.class);
		if (user == null) {
			// ���δ��¼������Ϊδ��¼��ͼ
			hasLogin.setVisibility(View.INVISIBLE);
			notLogin.setVisibility(View.VISIBLE);
		} else {
			// ����Ѿ���¼������Ϊ�ѵ�¼��ͼ
			hasLogin.setVisibility(View.VISIBLE);
			notLogin.setVisibility(View.INVISIBLE);
			allMoney.setText(totalMoney + "");
			allCount.setText(totalNum + "");
			// ÿ�η��ع��ﳵʱ��Ĭ��ȫѡ
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
