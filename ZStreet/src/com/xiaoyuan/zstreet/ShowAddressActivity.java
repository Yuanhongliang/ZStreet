package com.xiaoyuan.zstreet;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.adapter.AddressAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.AddressBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;


/**
 * չʾ�û����ջ���ַ��Activity
 * @author Administrator
 *
 */
public class ShowAddressActivity extends BaseActivity implements BaseInterface {

	
	@ViewInject(R.id.act_showadd_lv)
	private ListView lv;//չʾ�ջ���ַ��ListView 
	//չʾ�ջ���ַ������
	private List<AddressBean> datas;
	//չʾ�ջ����ݵ�Adapter
	private AddressAdapter adapter;
	
	//IntentЯ����������
	private int code = -1;
	
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
		setContentView(R.layout.act_show_address);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
		code = getIntent().getIntExtra("code", -1);
	}

	@Override
	public void initOper() {
		DialogUtils.showLoadingDialog(getAct());
		
		//����ɾ���ջ���ַ
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				DialogUtils.showAlertDialog(getAct(), "ɾ����", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AddressBean ab = new AddressBean();
						ab.setObjectId(datas.get(position).getObjectId());
						ab.delete(getAct(), new DeleteListener() {
							
							@Override
							public void onSuccess() {
								toastShort("ɾ���ɹ�");
								datas.remove(position);
								adapter.notifyDataSetChanged();
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								toastShort("ɾ��ʧ��"+arg1);
							}
						});
					}
				});
				return true;
			}
		});
		//���ѡ���ջ���ַ
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(code==1){
					MyApplication.putData("address", datas.get(position));
					finish();
				}
			}
		});
		
	}
	
	//�رյ�ǰActivity
	@OnClick(R.id.act_showadd_back)
	public void onBackClick(View v){
		finish();
	}
	
	//����ջ���ַ�İ�ť
	@OnClick(R.id.act_showadd_add)
	public void onAddClick(View v){
		startActivity(new Intent(getAct(), AddAddressActivity.class));
	}
	
	//ÿ��OnStratʱ���²�ѯ�ջ���ַ��֤��ȷ
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		UserBean ub = BmobUser.getCurrentUser(getAct(), UserBean.class);
		BmobQuery<AddressBean> query = new BmobQuery<AddressBean>();
		query.addWhereEqualTo("uId", ub.getObjectId());
		query.findObjects(getAct(), new FindListener<AddressBean>() {
			
			@Override
			public void onSuccess(List<AddressBean> arg0) {
				DialogUtils.dismissDialog();
				datas = arg0; 
				if(datas!=null&&datas.size()>0){
					adapter = new AddressAdapter(datas,getAct());
					lv.setAdapter(adapter);
				}else{
					toastShort("��û������ջ���ַ����ȥ��Ӱ�");
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				DialogUtils.dismissDialog();
				toastShort("��ѯʧ��"+arg1);
			}
		});
		
	}
	
	

}
