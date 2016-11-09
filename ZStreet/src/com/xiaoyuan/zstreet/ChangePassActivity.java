package com.xiaoyuan.zstreet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;


/**
 * 修改密码的Activity
 * @author Administrator
 *
 */
public class ChangePassActivity extends BaseActivity implements BaseInterface {

	//旧密码
	@ViewInject(R.id.act_change_old)
	private EditText oldPass;
	//新密码
	@ViewInject(R.id.act_change_new)
	private EditText newPass;
	
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
		setContentView(R.layout.act_changepass);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
	}

	@Override
	public void initOper() {
	}
	
	//确认修改
	@OnClick(R.id.act_change_change)
	public void onChangeClick(View v){
		String oldStr = oldPass.getText().toString().trim();
		String newStr = newPass.getText().toString().trim();
		if (oldStr.equals("") || newStr.equals("")) {
			toastShort("输入不可为空");
			return;
		}
		BmobUser.updateCurrentUserPassword(getAct(), oldStr, newStr,
			new UpdateListener() {

				@Override
				public void onSuccess() {
					toastShort("修改成功,请重新登录!");
					startActivity(new Intent(getAct(), LoginActivity.class));
					finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					toastShort("修改失败:" + arg1);
				}
			});
		
	}
	
	//返回按钮
	@OnClick(R.id.act_change_back)
	public void onBackClick(View v){
		finish();
	}
	

}
