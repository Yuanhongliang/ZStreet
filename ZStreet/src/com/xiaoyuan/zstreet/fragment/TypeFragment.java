package com.xiaoyuan.zstreet.fragment;

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
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.SearchResActivity;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseFragment;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.TextUtils;

/**
 * 分类界面的Fragment 查看商品的分类以及进行相应的查询
 * 
 * @author Administrator
 * 
 */
public class TypeFragment extends BaseFragment implements BaseInterface {

	// 搜索的输入框
	@ViewInject(R.id.frag_type_sear_et)
	private EditText et;
	
	//搜索按钮
	@ViewInject(R.id.frag_type_sear_img)
	private ImageView search;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();// 初始化控件
		initDatas();// 初始化数据
		initOper();// 初始化操作
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initOper() {

	}

	// 点击搜索品牌按钮的搜索事件
	@OnClick(R.id.frag_type_sear_img)
	public void onSearchClick(View v) {
		// 设置点击搜索时隐藏输入法
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		DialogUtils.showLoadingDialog(getActivity());
		String str = et.getText().toString().trim();
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		query.addWhereEqualTo("pinpai", str);
		String titleStr = TextUtils.subText(str);
		findData(query, titleStr);
		
	}

	// 点击商品分类进行搜索
	@OnClick({ R.id.frag_type_book, R.id.frag_type_huazhangpin,
			R.id.frag_type_nanxie, R.id.frag_type_nanzhuang,
			R.id.frag_type_nvxie, R.id.frag_type_nvzhuang,
			R.id.frag_type_shuma, R.id.frag_type_xiangbao,
			R.id.frag_type_yundong })
	public void onItemClick(View v) {
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		String title = null;
		DialogUtils.showLoadingDialog(getActivity());
		switch (v.getId()) {
		case R.id.frag_type_yundong:
			query.addWhereEqualTo("type", "运动");
			title = "运动";
			break;
		case R.id.frag_type_nanzhuang:
			query.addWhereEqualTo("type", "男装");
			title = "男装";
			break;
		case R.id.frag_type_nanxie:
			query.addWhereEqualTo("type", "男鞋");
			title = "男鞋";
			break;
		case R.id.frag_type_nvzhuang:
			query.addWhereEqualTo("type", "女装");
			title = "女装";
			break;
		case R.id.frag_type_nvxie:
			query.addWhereEqualTo("type", "女鞋");
			title = "女鞋";
			break;
		case R.id.frag_type_huazhangpin:
			query.addWhereEqualTo("type", "化妆品");
			title = "化妆品";
			break;
		case R.id.frag_type_book:
			query.addWhereEqualTo("type", "图书");
			title = "图书";
			break;
		case R.id.frag_type_xiangbao:
			query.addWhereEqualTo("type", "箱包");
			title = "箱包";
			break;
		case R.id.frag_type_shuma:
			query.addWhereEqualTo("type", "数码");
			title = "数码";
			break;
		}
		findData(query , title);
		
	}
	/**
	 * 查询的操作
	 * @param query 
	 * @param title 传出去的 标题
	 */
	private void findData(BmobQuery<GoodsBean> query, final String title) {
		query.findObjects(getActivity(), new FindListener<GoodsBean>() {

			@Override
			public void onSuccess(List<GoodsBean> arg0) {
				DialogUtils.dismissDialog();
				if(arg0!=null&&arg0.size()>0){
					
					MyApplication.putData("searchResult", arg0);
					Intent intent = new Intent(getActivity(),SearchResActivity.class);
					intent.putExtra("title", title);
					startActivity(intent);
					
				}else{
					toastShort("sorry,没有相关数据");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("查询失败"+arg1);
				DialogUtils.dismissDialog();
			}
		});
	}

	@Override
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_type, null);
		ViewUtils.inject(this, v);
		return v;
	}

}
