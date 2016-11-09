package com.xiaoyuan.zstreet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.utils.DialogUtils;
/**
 * 找回密码的Activity
 * @author Administrator
 *
 */
public class FindPassActivity extends BaseActivity implements BaseInterface {

	//输入的手机号
	@ViewInject(R.id.act_find_phonenumber)
	private EditText phone;
	
	//输入的验证码
	@ViewInject(R.id.act_find_code)
	private EditText code;
	// 获取验证码的按钮
	@ViewInject(R.id.act_find_getCode)
	private Button getCode;
	
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
		setContentView(R.layout.act_findpass);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
	}

	@Override
	public void initOper() {

	}
	
	//获取验证码的监听
	@OnClick(R.id.act_find_getCode)
	public void onGetClick(View v){
		String phone_Str = phone.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("输入的手机号码不合法，请检查");
			return;
		} 
		getCode.setClickable(false);
		getCode.setBackgroundResource(R.drawable.et_unable_shape);
		new Mytask().execute();
		BmobSMS.requestSMSCode(getAct(), phone_Str, "ZStreet模板",new RequestSMSCodeListener() {

		    @Override
		    public void done(Integer smsId,BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//验证码发送成功
		            Log.i("bmob", "短信id："+smsId);//用于查询本次短信发送详情
		        }
		    }
		});
	}
	
	@OnClick(R.id.act_find_find)
	public void onFindClick(View v){
		String phone_Str = phone.getText().toString().trim();
		String code_Str = code.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("输入的手机号码不合法，请检查");
			return;
		}	
		DialogUtils.showLoadingDialog(getAct());
		BmobUser.resetPasswordBySMSCode(getAct(), code_Str,code_Str, new ResetPasswordByCodeListener() {

			@Override
			public void done(cn.bmob.v3.exception.BmobException ex) {
				if(ex==null){
		            Log.i("smile", "密码重置成功");
		            DialogUtils.dismissDialog();
		            toastShort("密码重置成功,使用验证码登录即可");
		            finish();
		        }else{
		        	toastShort("重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
		            Log.i("smile", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
		        }
			}
		});
		
	}
	
	
	/**
	 * 继承AsyncTask实现获取验证码的计时功能
	 * @author Administrator
	 *
	 */
	private class Mytask extends AsyncTask<Void, Integer, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			for(int i=60;i>=0;i--){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				publishProgress(i);
			}
			return null;
		}
		//没隔一秒打印一次进度
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			String text = values[0]+"s";
			getCode.setText(text);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			getCode.setClickable(true);
			getCode.setText("获取验证码");
			getCode.setBackgroundResource(R.drawable.et_shape);
		}
		
	};
	
	
	//返回按钮
	@OnClick(R.id.act_find_back)
	public void onBackClick(View v){
		finish();
	}
}
