package com.xiaoyuan.zstreet;

import java.util.List;

import android.os.Bundle;
import android.view.View;

import cn.bmob.v3.listener.DeleteListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.adapter.OrderAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.OrderBean;
import com.xiaoyuan.zstreet.explodefield.ExplosionField;
import com.xiaoyuan.zstreet.myview.QQListView;
import com.xiaoyuan.zstreet.myview.QQListView.DelButtonClickListener;

/**
 * �鿴������Activity
 * @author Administrator
 *
 */
public class OrderActivity extends BaseActivity implements BaseInterface {

	
	//��ʾ�������б�
	@ViewInject(R.id.act_order_lv)
	private QQListView lv;
	//��ʾ����������Դ
	private List<OrderBean> orders;
	//������Դ�󶨵���ͼ��Adapter
	private OrderAdapter adapter;
	
	private ExplosionField field;
	
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
		//��ʼ����ͼ
		setContentView(R.layout.act_showorder);
		ViewUtils.inject(getAct());
		field = ExplosionField.attach2Window(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		//�ӻ������ó�����Դ
		orders = (List<OrderBean>) MyApplication.getData("orders", true);
	}

	@Override
	public void initOper() {
		adapter = new OrderAdapter(getAct(),orders);
		lv.setAdapter(adapter);
		//����ɾ����ť�ļ���
		lv.setDelButtonClickListener(new DelButtonClickListener() {
			
			@Override
			public void clickHappend(final int position) {
				//�����ݿ���ɾ��
				OrderBean ob = new OrderBean();
				ob.setObjectId(orders.get(position).getObjectId());
				ob.delete(getAct(), new DeleteListener() {
					
					@Override
					public void onSuccess() {
						//����UI
						field.explode(lv.getChildAt(position));
						orders.remove(position);
						toastShort("ɾ���ɹ�");
						adapter.notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						toastShort("ɾ������ʧ��"+arg1);
					}
				});
			}
		});
		
	}
	
	//���ؼ�
	@OnClick(R.id.act_order_back)
	public void onBackClick(View v){
		finish();
	}
	
}
