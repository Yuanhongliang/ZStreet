package com.xiaoyuan.zstreet.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.HomeActivity;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.SearchResActivity;
import com.xiaoyuan.zstreet.adapter.HomeLvAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.application.MyApplication.OnFinishListener;
import com.xiaoyuan.zstreet.base.BaseFragment;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.myview.XListView;
import com.xiaoyuan.zstreet.myview.XListView.IXListViewListener;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.TextUtils;

/**
 * ��ҳ���Fragment ��ʾ������Ʒ
 * 
 * @author Administrator
 * 
 */
public class HomeFragment extends BaseFragment implements BaseInterface {

	// �����������
	@ViewInject(R.id.frag_home_sear_et)
	private EditText et;
	// ���ص�ListView
	@ViewInject(R.id.frag_home_lv)
	private XListView lv;

	// ������ListView��������
	private HomeLvAdapter lvAdapter;
	// ͼƬ�ֲ�����ͼ
	private List<View> views;
	// ͼƬ�ֲ�����Դ
	private int[] pagerId = { R.drawable.ad1, R.drawable.ad2, R.drawable.ad3,
			R.drawable.ad4, R.drawable.ad5 };
	// ����Ա
	private List<GoodsBean> goods;
	// ���ص�����
	private int count;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();// ��ʼ���ؼ�
		initDatas();// ��ʼ������
		initOper();// ��ʼ������
	}

	// ��ѯ��Ʒ�İ�ť
	@OnClick(R.id.frag_home_sear_img)
	public void onSearchClick(View v) {
		// ���õ������ʱ�������뷨
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		String str = et.getText().toString().trim();
		if (str == null || str.equals("")) {
			return;// ��������Ϊ���򷵻�
		}
		DialogUtils.showLoadingDialog(getActivity());
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		query.addWhereContains("goodsName", str);
		final String titleStr = TextUtils.subText(str);
		query.findObjects(getActivity(), new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> data) {
				DialogUtils.dismissDialog();
				if (data != null && data.size() > 0) {
					MyApplication.putData("searchResult", data);
					Intent intent = new Intent(getActivity(),
							SearchResActivity.class);
					intent.putExtra("title", titleStr);
					startActivity(intent);

				} else {
					toastShort("sorry,û���������");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("��ѯʧ��" + arg1);
				DialogUtils.dismissDialog();
			}
		});

	}

	@Override
	public void initViews() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		views = new ArrayList<View>();
		for (int i = 0; i < 5; i++) {
			// ����5���ֲ���ͼƬ
			View v = getActivity().getLayoutInflater().inflate(
					R.layout.item_pageritem, null);
			ImageView img = (ImageView) v.findViewById(R.id.item_pageritem_img);
			img.setImageResource(pagerId[i]);
			views.add(v);
		}
		// �ӻ�����ȡ������
		goods = (List<GoodsBean>) MyApplication.getData("initDatas", true);
		if (lvAdapter == null) {
			lvAdapter = new HomeLvAdapter(getActivity(), views, goods);
			if(goods!=null)count=goods.size();
			lv.setAdapter(lvAdapter);
		}
		((MyApplication) getActivity().getApplication())
				.setListener(new OnFinishListener() {

					@Override
					public void onFinish(List<GoodsBean> goods) {
						if (goods != null && HomeFragment.this.goods == null) {
							LogE("�ص�");
							count = goods.size();
							lvAdapter.addGoods(goods);
						}
					}
				});
	}

	@Override
	public void initOper() {

		if (goods != null && lvAdapter == null) {
			count = goods.size();
			lvAdapter = new HomeLvAdapter(getActivity(), views, goods);
			lv.setAdapter(lvAdapter);
		}
		// ���ÿ��Լ��ظ���
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// ����ˢ�µĲ���
				pullRefresh();
			}

			@Override
			public void onLoadMore() {
				// �������صĲ���
				pullLoadMore();
			}
		});
		((HomeActivity) getActivity()).getMenu().addIgnoredView(lv);
	}

	// ����ˢ�µĲ���
	private void pullRefresh() {
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		// ��������
		query.order("-createdAt");
		query.setLimit(3);
		query.findObjects(getActivity(), new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> arg0) {
				goods = arg0;
				count = arg0.size();
				lvAdapter.setGoods(goods);
				// �ֶ�ֹͣˢ��
				lv.stopRefresh();
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("ˢ��ʧ��" + arg1);
				// �ֶ�ֹͣˢ��
				lv.stopRefresh();
			}
		});
	}

	// ���ظ���Ĳ���
	private void pullLoadMore() {
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		query.order("-createdAt");
		query.setLimit(3);
		query.setSkip(count);
		query.findObjects(getActivity(), new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> arg0) {
				count += arg0.size();
				lvAdapter.addGoods(arg0);
				lv.stopLoadMore();
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("ˢ��ʧ��" + arg1);
				lv.stopLoadMore();
			}
		});
	}

	// ����HomeFragment�Ĳ���
	@Override
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_home, null);
		ViewUtils.inject(this, v);
		return v;
	}

}
