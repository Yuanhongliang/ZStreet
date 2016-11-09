package com.xiaoyuan.zstreet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.InputMethodUtil;
import com.xiaoyuan.zstret.anim.Techniques;
import com.xiaoyuan.zstret.anim.YoYo;

/**
 * 登录界面的Activity
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity implements BaseInterface {

	@ViewInject(R.id.act_login_username)
	private EditText username;
	@ViewInject(R.id.act_login_password)
	private EditText password;
	@ViewInject(R.id.act_login_name_lin)
	private LinearLayout name_lin;
	@ViewInject(R.id.act_login_pass_lin)
	private LinearLayout pass_lin;

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
		setContentView(R.layout.act_login);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
	}

	@OnClick(R.id.act_login_back)
	public void onBackClick(View v) {
		finish();
	}

	@OnClick(R.id.act_login_log)
	public void onLoginClick(View v) {
		InputMethodUtil.hideInputMethod(getAct());
		String nameStr = username.getText().toString().trim();
		String passStr = password.getText().toString().trim();
		if ("".equals(nameStr) || "".equals(passStr)) {// 非空验证
			toastShort("用户名或者密码不能为空");
			return;
		}
		UserBean ub = new UserBean();
		ub.setUsername(nameStr);
		ub.setPassword(passStr);
		ub.login(getAct(), new SaveListener() {

			@Override
			public void onSuccess() {
				toastShort("登录成功");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				YoYo.with(Techniques.Shake).duration(1000).playOn(name_lin);
				YoYo.with(Techniques.Shake).duration(1000).playOn(pass_lin);
				toastShort("登录失败" + arg1);
			}
		});
	}

	@OnClick(R.id.act_login_register)
	public void onRegClick(View v) {
		startActivity(new Intent(getAct(), RegisterActivity.class));
	}

	@OnClick(R.id.act_login_forgetPass)
	public void onForgetClick(View v) {
		startActivity(new Intent(getAct(), FindPassActivity.class));
	}

	@Override
	public void initOper() {

	}

	@Override
	protected void onStart() {
		super.onStart();
		String nameStr = (String) MyApplication.getData("reg_username", true);
		String passStr = (String) MyApplication.getData("reg_password", true);
		if (nameStr != null && passStr != null) {
			username.setText(nameStr);
			password.setText(passStr);
		}
	}

}
