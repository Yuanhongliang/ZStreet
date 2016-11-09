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
 * 查看订单的Activity
 * @author Administrator
 *
 */
public class OrderActivity extends BaseActivity implements BaseInterface {

	
	//显示订单的列表
	@ViewInject(R.id.act_order_lv)
	private QQListView lv;
	//显示订单的数据源
	private List<OrderBean> orders;
	//将数据源绑定到视图的Adapter
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
		//初始化视图
		setContentView(R.layout.act_showorder);
		ViewUtils.inject(getAct());
		field = ExplosionField.attach2Window(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		//从缓存中拿出数据源
		orders = (List<OrderBean>) MyApplication.getData("orders", true);
	}

	@Override
	public void initOper() {
		adapter = new OrderAdapter(getAct(),orders);
		lv.setAdapter(adapter);
		//监听删除按钮的监听
		lv.setDelButtonClickListener(new DelButtonClickListener() {
			
			@Override
			public void clickHappend(final int position) {
				//从数据库中删除
				OrderBean ob = new OrderBean();
				ob.setObjectId(orders.get(position).getObjectId());
				ob.delete(getAct(), new DeleteListener() {
					
					@Override
					public void onSuccess() {
						//更新UI
						field.explode(lv.getChildAt(position));
						orders.remove(position);
						toastShort("删除成功");
						adapter.notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						toastShort("删除订单失败"+arg1);
					}
				});
			}
		});
		
	}
	
	//返回键
	@OnClick(R.id.act_order_back)
	public void onBackClick(View v){
		finish();
	}
	
}
