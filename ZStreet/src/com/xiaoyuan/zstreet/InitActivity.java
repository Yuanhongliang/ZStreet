package com.xiaoyuan.zstreet;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.myview.shimmer.Shimmer;
import com.xiaoyuan.zstreet.myview.shimmer.ShimmerTextView;

/**
 * 初始化界面的Activity
 * 
 * @author Administrator
 * 
 */
public class InitActivity extends BaseActivity implements BaseInterface {

	
	private Shimmer shimmer;
	
	@ViewInject(R.id.act_init_tv)
	private ShimmerTextView tv;
	
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
		setContentView(R.layout.act_init);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
		shimmer = new Shimmer();
	}

	@Override
	public void initOper() {
		shimmer.setDuration(1500);
		shimmer.start(tv);
		//停顿三秒后进入主界面
		new CountDownTimer(3000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
			}
			
			@Override
			public void onFinish() {
				shimmer.cancel();
				//动画结束后开启新的Activity
				startActivity(new Intent(getAct(), HomeActivity.class));
				finish();
			}
		}.start();
		
	}

}
