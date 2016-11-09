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
 * 添加收货地址的Activity
 * @author Administrator
 *
 */
public class AddAddressActivity extends BaseActivity implements BaseInterface {

	
	//选择地址的三级联动
	@ViewInject(R.id.act_add_lin)
	private LinearLayout lin;
	//选择地址的按钮
	@ViewInject(R.id.act_add_choose)
	private TextView choose;
	//填写街道
	@ViewInject(R.id.act_add_street)
	private EditText street;
	//收货人姓名
	@ViewInject(R.id.act_add_name)
	private EditText name;
	//收货人手机
	@ViewInject(R.id.act_add_phonenumber)
	private EditText phone;
	//省
	@ViewInject(R.id.act_add_loop1)
	private LoopView loop1;
	//市
	@ViewInject(R.id.act_add_loop2)
	private LoopView loop2;
	//区/县
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
	
	//选择市区的监听
	@OnClick(R.id.act_add_choose)
	public void onChooseClick(View v){
		lin.setVisibility(View.VISIBLE);
	}
	
	//返回键的监听
	@OnClick(R.id.act_add_back)
	public void onBackClick(View v){
		finish();
	}
	
	//取消省市区的选择
	@OnClick(R.id.act_add_no)
	public void onNoClick(View v){
		lin.setVisibility(View.INVISIBLE);
	}
	
	//确认省市区的选择
	@OnClick(R.id.act_add_yes)
	public void onYesClick(View v){
		if(shengInt<1||shiInt<1||quInt<1){
			toastShort("请选择完整地址");
			return;
		}
		//拼接省市区
		address = AddressData.PROVINCES[shengInt]+" | "
				+AddressData.CITIES[shengInt][shiInt]+" | "+AddressData.COUNTIES[shengInt][shiInt][quInt];
		choose.setText(address);
		lin.setVisibility(View.INVISIBLE);
	}
	
	@OnClick(R.id.act_add_submit)
	public void onAddClick(View v){
		String detAddress = street.getText().toString().trim();
		if(address==null||address.equals("")||detAddress==null||detAddress.equals("")){
			toastShort("地址信息请填写完整");
			return;
		}
		String phone_Str = phone.getText().toString().trim();
		if(!phone_Str.matches("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$")){
			toastShort("输入的手机号码不正确，请检查");
			return;
		} 
		String nameStr = name.getText().toString().trim();
		if(nameStr==null||nameStr.equals("")){
			toastShort("填写姓名");
			return;
		}
		//向数据库中添加数据
		AddressBean ab = new AddressBean();
		ab.setuId(ub.getObjectId());
		ab.setAddress(address+" | "+detAddress);
		ab.setName(nameStr);
		ab.setPhoneNumber(phone_Str);
		ab.save(getAct(), new SaveListener() {
			
			@Override
			public void onSuccess() {
				toastShort("添加成功");
				finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("添加失败"+arg1);
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
		//设置第一个LoopView的选中时监听
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
		//设置第二个LoopView的选中时监听
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
		//设置第三个LoopView的选中时监听
		loop3.setItems(Arrays.asList(AddressData.COUNTIES[0][0]));
		loop3.setListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(int index) {
				quInt = index;
			}
		});

	}

}
