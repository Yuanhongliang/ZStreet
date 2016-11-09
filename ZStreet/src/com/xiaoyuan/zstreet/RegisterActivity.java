package com.xiaoyuan.zstreet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.UserBean;

public class RegisterActivity extends BaseActivity implements BaseInterface {
	
	//�û��������
	@ViewInject(R.id.act_reg_username)
	private EditText username;
	//���������
	@ViewInject(R.id.act_reg_password)
	private EditText password;
	//�ֻ��������
	@ViewInject(R.id.act_reg_phone)
	private EditText phone;
	//��֤�������
	@ViewInject(R.id.act_reg_iden)
	private EditText code;
	//��ȡ��֤�밴ť
	@ViewInject(R.id.act_reg_getcode)
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
		setContentView(R.layout.act_register);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {

	}
	//���ذ�ť�Ĳ���
	@OnClick(R.id.act_reg_back)
	public void onBackClick(View v){
		finish();
	}
	
	@Override
	public void initOper() {

		
		//��ȡ��֤����Ӽ��� �ж��ֻ����Ƿ�Ϸ�
		getCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phone_Str = phone.getText().toString().trim();
				if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
					toastShort("������ֻ����벻�Ϸ�������");
					return;
				} 
				getCode.setClickable(false);
				getCode.setBackgroundResource(R.drawable.et_unable_shape);
				new Mytask().execute();
				BmobSMS.requestSMSCode(getAct(), phone_Str, "ZStreetģ��", new RequestSMSCodeListener() {
					
					@Override
					public void done(Integer arg0, BmobException arg1) {
						if(arg1==null){
							toastShort("���ͳɹ�");
						}else{
							toastShort("����ʧ��"+arg1.getLocalizedMessage());
						}
					}
				});
			}
		});
		
	}
	//ע�ᰴť�Ĳ���
	@OnClick(R.id.act_reg_reg)
	public void onRegClick(View v) {
		final String phone_Str = phone.getText().toString().trim();
		String code_Str = code.getText().toString().trim();
		final String name_Str = username.getText().toString().trim();
		final String pass_Str = password.getText().toString().trim();
		if("".equals(name_Str)||"".equals(pass_Str)){
			toastShort("�û����������벻��Ϊ��");
			return;
		}
		if("".equals(code_Str)){
			toastShort("��֤�벻��Ϊ��");
			return;
		}
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("������ֻ����벻�Ϸ�������");
			return;
		}
		/**
		 * ��֤�������֤���Ƿ���ȷ
		 */
		BmobSMS.verifySmsCode(getAct(),phone_Str, code_Str, new VerifySMSCodeListener() {

		    @Override
		    public void done(BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//������֤������֤�ɹ�
		        	UserBean ub = new UserBean();
		        	ub.setUsername(name_Str);
		        	ub.setPassword(pass_Str);
		        	ub.setMobilePhoneNumber(phone_Str);
		        	ub.setMobilePhoneNumberVerified(true);
		        	ub.signUp(getAct(),new SaveListener() {
						
						@Override
						public void onSuccess() {
							toastShort("ע��ɹ���");
							MyApplication.putData("reg_username", phone_Str);
							MyApplication.putData("reg_password", pass_Str);
							finish();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							toastShort("ע��ʧ��");
						}
					});
		        	
		        	
		        }else{
		        	logE(ex.getErrorCode()+ex.getLocalizedMessage());
		            switch (ex.getErrorCode()) {
					case 9016:
						toastShort("����������");
						break;
					
		        	case 9019:
		        		toastShort("��֤���ʽ����");
		        		break;
		        	case 207:
		        		toastShort("��֤�����");
		        		break;
		            }
		        }
		    }
		});
	}
	
	
	
	/**
	 * �̳�AsyncTaskʵ�ֻ�ȡ��֤��ļ�ʱ����
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
		//û��һ���ӡһ�ν���
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
			getCode.setText("��ȡ");
			getCode.setBackgroundResource(R.drawable.et_shape);
		}
		
	};
	
	

}
