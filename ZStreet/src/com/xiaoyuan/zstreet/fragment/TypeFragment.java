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
 * ��������Fragment �鿴��Ʒ�ķ����Լ�������Ӧ�Ĳ�ѯ
 * 
 * @author Administrator
 * 
 */
public class TypeFragment extends BaseFragment implements BaseInterface {

	// �����������
	@ViewInject(R.id.frag_type_sear_et)
	private EditText et;
	
	//������ť
	@ViewInject(R.id.frag_type_sear_img)
	private ImageView search;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();// ��ʼ���ؼ�
		initDatas();// ��ʼ������
		initOper();// ��ʼ������
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

	// �������Ʒ�ư�ť�������¼�
	@OnClick(R.id.frag_type_sear_img)
	public void onSearchClick(View v) {
		// ���õ������ʱ�������뷨
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		DialogUtils.showLoadingDialog(getActivity());
		String str = et.getText().toString().trim();
		BmobQuery<GoodsBean> query = new BmobQuery<GoodsBean>();
		query.addWhereEqualTo("pinpai", str);
		String titleStr = TextUtils.subText(str);
		findData(query, titleStr);
		
	}

	// �����Ʒ�����������
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
			query.addWhereEqualTo("type", "�˶�");
			title = "�˶�";
			break;
		case R.id.frag_type_nanzhuang:
			query.addWhereEqualTo("type", "��װ");
			title = "��װ";
			break;
		case R.id.frag_type_nanxie:
			query.addWhereEqualTo("type", "��Ь");
			title = "��Ь";
			break;
		case R.id.frag_type_nvzhuang:
			query.addWhereEqualTo("type", "Ůװ");
			title = "Ůװ";
			break;
		case R.id.frag_type_nvxie:
			query.addWhereEqualTo("type", "ŮЬ");
			title = "ŮЬ";
			break;
		case R.id.frag_type_huazhangpin:
			query.addWhereEqualTo("type", "��ױƷ");
			title = "��ױƷ";
			break;
		case R.id.frag_type_book:
			query.addWhereEqualTo("type", "ͼ��");
			title = "ͼ��";
			break;
		case R.id.frag_type_xiangbao:
			query.addWhereEqualTo("type", "���");
			title = "���";
			break;
		case R.id.frag_type_shuma:
			query.addWhereEqualTo("type", "����");
			title = "����";
			break;
		}
		findData(query , title);
		
	}
	/**
	 * ��ѯ�Ĳ���
	 * @param query 
	 * @param title ����ȥ�� ����
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
					toastShort("sorry,û���������");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("��ѯʧ��"+arg1);
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
