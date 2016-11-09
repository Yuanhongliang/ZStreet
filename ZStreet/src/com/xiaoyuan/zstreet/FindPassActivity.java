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
 * �һ������Activity
 * @author Administrator
 *
 */
public class FindPassActivity extends BaseActivity implements BaseInterface {

	//������ֻ���
	@ViewInject(R.id.act_find_phonenumber)
	private EditText phone;
	
	//�������֤��
	@ViewInject(R.id.act_find_code)
	private EditText code;
	// ��ȡ��֤��İ�ť
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
	
	//��ȡ��֤��ļ���
	@OnClick(R.id.act_find_getCode)
	public void onGetClick(View v){
		String phone_Str = phone.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("������ֻ����벻�Ϸ�������");
			return;
		} 
		getCode.setClickable(false);
		getCode.setBackgroundResource(R.drawable.et_unable_shape);
		new Mytask().execute();
		BmobSMS.requestSMSCode(getAct(), phone_Str, "ZStreetģ��",new RequestSMSCodeListener() {

		    @Override
		    public void done(Integer smsId,BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//��֤�뷢�ͳɹ�
		            Log.i("bmob", "����id��"+smsId);//���ڲ�ѯ���ζ��ŷ�������
		        }
		    }
		});
	}
	
	@OnClick(R.id.act_find_find)
	public void onFindClick(View v){
		String phone_Str = phone.getText().toString().trim();
		String code_Str = code.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("������ֻ����벻�Ϸ�������");
			return;
		}	
		DialogUtils.showLoadingDialog(getAct());
		BmobUser.resetPasswordBySMSCode(getAct(), code_Str,code_Str, new ResetPasswordByCodeListener() {

			@Override
			public void done(cn.bmob.v3.exception.BmobException ex) {
				if(ex==null){
		            Log.i("smile", "�������óɹ�");
		            DialogUtils.dismissDialog();
		            toastShort("�������óɹ�,ʹ����֤���¼����");
		            finish();
		        }else{
		        	toastShort("����ʧ�ܣ�code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
		            Log.i("smile", "����ʧ�ܣ�code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
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
			getCode.setText("��ȡ��֤��");
			getCode.setBackgroundResource(R.drawable.et_shape);
		}
		
	};
	
	
	//���ذ�ť
	@OnClick(R.id.act_find_back)
	public void onBackClick(View v){
		finish();
	}
}
