package com.xiaoyuan.zstreet;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.adapter.SearchResultAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.GoodsBean;

public class SearchResActivity extends BaseActivity implements BaseInterface {

	@ViewInject(R.id.act_search_title)
	private TextView title;

	@ViewInject(R.id.act_search_lv)
	private ListView lv;
	
	private List<GoodsBean> datas;
	
	private String titleStr;

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
		setContentView(R.layout.act_search_result);
		ViewUtils.inject(getAct());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		datas = (List<GoodsBean>) MyApplication.getData("searchResult", true);
		titleStr = getIntent().getStringExtra("title");
		lv.setAdapter(new SearchResultAdapter(getAct(),datas));
	}

	@Override
	public void initOper() {
		if(titleStr!=null){
			title.setText(titleStr);
		}
	}
	
	
	@OnClick(R.id.act_search_back)
	public void onBackClick(View v){
		finish();
	}

}
