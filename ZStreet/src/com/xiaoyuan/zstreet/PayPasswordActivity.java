package com.xiaoyuan.zstreet;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.PreferenceManager;

public class PayPasswordActivity extends BaseActivity implements BaseInterface {

	// ��������
	@ViewInject(R.id.act_payword_set)
	private TextView setWord;

	// �һ�����
	@ViewInject(R.id.act_payword_getback)
	private TextView getBackWord;

	// �޸�����
	@ViewInject(R.id.act_payword_update)
	private TextView updateWord;

	private SharedPreferences sp;

	private UserBean ub;

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
		setContentView(R.layout.act_payword);
		ViewUtils.inject(getAct());
		ub = BmobUser.getCurrentUser(getAct(), UserBean.class);
	}

	@Override
	public void initDatas() {
		sp = PreferenceManager.getInstence(getAct()).getPreference();
		String word = sp.getString("password", "");
		if (!word.equals("")) {
			setWord.setVisibility(View.GONE);
		}
	}

	@Override
	public void initOper() {
		setWord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getAct(), R.layout.view_edit_password,
						null);
				final EditText et = (EditText) view
						.findViewById(R.id.edit_pass_et);
				Button but = (Button) view.findViewById(R.id.edit_pass_but);
				but.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String word = et.getText().toString().trim();
						if (word == null || word.equals("")) {
							toastShort("����������");
							return;
						}
						if (word.length() != 6) {
							toastShort("������6λ����");
							return;
						}
						Editor edit = sp.edit();
						edit.putString("password", word);
						edit.commit();
						setWord.setVisibility(View.GONE);
						toastShort("�������óɹ�");
						DialogUtils.dismissDialog();
					}
				});
				DialogUtils.showMyViewDialog(getAct(), view, true);
			}
		});
		updateWord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getAct(), R.layout.view_edit_password,
						null);
				final EditText et = (EditText) view
						.findViewById(R.id.edit_pass_et);
				Button but = (Button) view.findViewById(R.id.edit_pass_but);
				et.setHint("�����������");
				but.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String word = et.getText().toString().trim();
						if (word == null || word.equals("")) {
							toastShort("����������");
							return;
						}
						if (word.length() != 6) {
							toastShort("������6λ����");
							return;
						}
						String currentWord = sp.getString("password", "");
						if (currentWord.equals(word)) {
							DialogUtils.dismissDialog();
							setNewWord();
						} else {
							toastShort("�����������");
						}
					}
				});
				DialogUtils.showMyViewDialog(getAct(), view, true);
			}
		});

		getBackWord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getAct(), R.layout.view_edit_password,
						null);
				final EditText et = (EditText) view
						.findViewById(R.id.edit_pass_et);
				Button but = (Button) view.findViewById(R.id.edit_pass_but);
				et.setHint("������ע����ֻ���");
				but.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String phone = et.getText().toString().trim();
						if (phone == null || phone.equals("")) {
							toastShort("�������ֻ���");
							return;
						}
						if (!phone.equals(ub.getMobilePhoneNumber())) {
							toastShort("�ֻ�����������");
							return;
						}
						DialogUtils.dismissDialog();
						setNewWord();
					}
				});
				DialogUtils.showMyViewDialog(getAct(), view, true);
			}
		});

	}

	private void setNewWord() {
		View view = View.inflate(getAct(), R.layout.view_edit_password, null);
		final EditText et = (EditText) view.findViewById(R.id.edit_pass_et);
		et.setHint("������������");
		Button but = (Button) view.findViewById(R.id.edit_pass_but);
		but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String word = et.getText().toString().trim();
				if (word == null || word.equals("")) {
					toastShort("����������");
					return;
				}
				if (word.length() != 6) {
					toastShort("������6λ����");
					return;
				}
				Editor edit = sp.edit();
				edit.putString("password", word);
				edit.commit();
				toastShort("��������ɹ�");
				DialogUtils.dismissDialog();
			}
		});
		DialogUtils.showMyViewDialog(getAct(), view, true);
	}

	@OnClick(R.id.act_payword_back)
	public void onBackClick(View v) {
		finish();
	}

}
