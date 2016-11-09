package com.xiaoyuan.zstreet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.FankuiBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.InputMethodUtil;

public class FankuiActivity extends BaseActivity implements BaseInterface {

	private EditText et;

	private TextView current;

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
		setContentView(R.layout.act_fankuijianyi);
		ViewUtils.inject(getAct());
		et = findEt(R.id.act_fankui_et);
		current = findTv(R.id.act_fankui_current);
	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initOper() {
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int len = et.getText().toString().trim().length();
				if (len >= 100) {
					toastShort("最多100个字哦~");
					return;
				}
				current.setText(len + "/");
			}
		});
	}

	@OnClick(R.id.act_fankui_submit)
	public void onSubmit(View v) {
		InputMethodUtil.hideInputMethod(getAct());
		FankuiBean bean = new FankuiBean();
		bean.setUserId(BmobUser.getCurrentUser(getAct(), UserBean.class)
				.getObjectId());
		bean.setContent(et.getText().toString().trim());
		bean.save(getAct(), new SaveListener() {

			@Override
			public void onSuccess() {
				toastShort("您的建议我们已经收到 .");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("反馈失败" + arg1);
			}
		});
	}

	@OnClick(R.id.act_fankui_back)
	public void onBack(View v) {
		finish();
	}

}
