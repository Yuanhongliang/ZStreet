package com.xiaoyuan.zstreet;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.CommentBean;
import com.xiaoyuan.zstreet.bean.OrderBean;
import com.xiaoyuan.zstreet.bean.UserBean;

/**
 * ��Ʒ���۵�ACtivity
 * @author Administrator
 *
 */
public class CommentActivity extends BaseActivity implements BaseInterface {

	//������
	@ViewInject(R.id.rating)
	private RatingBar bar;
	//��Ʒ��
	@ViewInject(R.id.act_comm_name)
	private TextView name;
	//�������۵�����
	@ViewInject(R.id.act_comm_et)
	private EditText et;
	//��������
	private OrderBean ob;
	//��ǰ���û�����
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
		setContentView(R.layout.act_comment);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
		ob = (OrderBean) MyApplication.getData("order", true);
		ub = ((MyApplication)getApplication()).getUser();
	}

	@Override
	public void initOper() {
		bar.setMax(5);
		bar.setNumStars(5);
		name.setText(ob.getGoodsName());
	}
	
	@OnClick(R.id.act_comment_back)
	public void onBackClick(View v){
		finish();
	}
	
	@OnClick(R.id.act_comm_but)
	public void onSureClick(View v){
		String content = et.getText().toString().trim();
		if(content==null||content.equals("")){
			toastShort("���ݲ����ǿ�Ŷ~");
			return;
		}
		CommentBean cb = new CommentBean();
		cb.setGoodsId(ob.getGoodsId());
		cb.setUserId(ub.getObjectId());
		cb.setUsername(ub.getUsername());
		cb.setContent(content);
		cb.setStars((int) bar.getRating());
		cb.save(getAct(), new SaveListener() {
			
			@Override
			public void onSuccess() {
				toastShort("���۳ɹ���");
				finish();
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("����ʧ��"+arg1);
			}
		});
	}

}
