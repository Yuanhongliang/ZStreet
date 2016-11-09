package com.xiaoyuan.zstreet;

import java.util.Arrays;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.AddressBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.myview.loopview.LoopView;
import com.xiaoyuan.zstreet.myview.loopview.OnItemSelectedListener;
import com.xiaoyuan.zstreet.utils.AddressData;


/**
 * ����ջ���ַ��Activity
 * @author Administrator
 *
 */
public class AddAddressActivity extends BaseActivity implements BaseInterface {

	
	//ѡ���ַ����������
	@ViewInject(R.id.act_add_lin)
	private LinearLayout lin;
	//ѡ���ַ�İ�ť
	@ViewInject(R.id.act_add_choose)
	private TextView choose;
	//��д�ֵ�
	@ViewInject(R.id.act_add_street)
	private EditText street;
	//�ջ�������
	@ViewInject(R.id.act_add_name)
	private EditText name;
	//�ջ����ֻ�
	@ViewInject(R.id.act_add_phonenumber)
	private EditText phone;
	//ʡ
	@ViewInject(R.id.act_add_loop1)
	private LoopView loop1;
	//��
	@ViewInject(R.id.act_add_loop2)
	private LoopView loop2;
	//��/��
	@ViewInject(R.id.act_add_loop3)
	private LoopView loop3;
	
	private UserBean ub;
	
	private int shengInt = -1;
	private int shiInt = -1;
	private int quInt = -1;
	
	private String address;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initViews();
		initDatas();
		initOper();
	}

	@Override
	public void initViews() {
		setContentView(R.layout.act_add_address);
		ViewUtils.inject(getAct());
	}

	@Override
	public void initDatas() {
		ub = BmobUser.getCurrentUser(getAct(), UserBean.class);
	}
	
	//ѡ�������ļ���
	@OnClick(R.id.act_add_choose)
	public void onChooseClick(View v){
		lin.setVisibility(View.VISIBLE);
	}
	
	//���ؼ��ļ���
	@OnClick(R.id.act_add_back)
	public void onBackClick(View v){
		finish();
	}
	
	//ȡ��ʡ������ѡ��
	@OnClick(R.id.act_add_no)
	public void onNoClick(View v){
		lin.setVisibility(View.INVISIBLE);
	}
	
	//ȷ��ʡ������ѡ��
	@OnClick(R.id.act_add_yes)
	public void onYesClick(View v){
		if(shengInt<1||shiInt<1||quInt<1){
			toastShort("��ѡ��������ַ");
			return;
		}
		//ƴ��ʡ����
		address = AddressData.PROVINCES[shengInt]+" | "
				+AddressData.CITIES[shengInt][shiInt]+" | "+AddressData.COUNTIES[shengInt][shiInt][quInt];
		choose.setText(address);
		lin.setVisibility(View.INVISIBLE);
	}
	
	@OnClick(R.id.act_add_submit)
	public void onAddClick(View v){
		String detAddress = street.getText().toString().trim();
		if(address==null||address.equals("")||detAddress==null||detAddress.equals("")){
			toastShort("��ַ��Ϣ����д����");
			return;
		}
		String phone_Str = phone.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("������ֻ����벻��ȷ������");
			return;
		} 
		String nameStr = name.getText().toString().trim();
		if(nameStr==null||nameStr.equals("")){
			toastShort("��д����");
			return;
		}
		//�����ݿ����������
		AddressBean ab = new AddressBean();
		ab.setuId(ub.getObjectId());
		ab.setAddress(address+" | "+detAddress);
		ab.setName(nameStr);
		ab.setPhoneNumber(phone_Str);
		ab.save(getAct(), new SaveListener() {
			
			@Override
			public void onSuccess() {
				toastShort("��ӳɹ�");
				finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("���ʧ��"+arg1);
			}
		});
		
	}

	@Override
	public void initOper() {
		loop1.setTextSize(16);
		loop1.setViewPadding(40, 20, 40, 20);
		loop1.setNotLoop();
		loop1.setItems(Arrays.asList(AddressData.PROVINCES));
		loop1.setInitPosition(0);
		//���õ�һ��LoopView��ѡ��ʱ����
		loop1.setListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(int index) {
				shengInt = index;
				loop2.setInitPosition(0);
				loop2.setItems(Arrays.asList(AddressData.CITIES[index]));
			}
		});
		loop2.setNotLoop();
		loop2.setItems(Arrays.asList(AddressData.CITIES[0]));
		loop2.setTextSize(16);
		loop2.setViewPadding(40, 20, 40, 20);
		//���õڶ���LoopView��ѡ��ʱ����
		loop2.setListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(int index) {
				shiInt = index;
				loop3.setInitPosition(0);
				loop3.setItems(Arrays.asList(AddressData.COUNTIES[shengInt][index]));
			}
		});
		loop3.setNotLoop();
		loop3.setTextSize(16);
		loop3.setViewPadding(40, 20, 40, 20);
		//���õ�����LoopView��ѡ��ʱ����
		loop3.setItems(Arrays.asList(AddressData.COUNTIES[0][0]));
		loop3.setListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(int index) {
				quInt = index;
			}
		});

	}

}
