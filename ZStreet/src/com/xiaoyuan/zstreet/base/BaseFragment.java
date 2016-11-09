package com.xiaoyuan.zstreet.base;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 向上抽取的父类Fragment 使用更加方便
 * @author Administrator
 *
 */
public abstract class BaseFragment extends Fragment {
	
	private static final String TAG = "ZStreet";
	private View fragmentView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(fragmentView==null){
			fragmentView = getFragmentView(inflater,container,savedInstanceState);
		}
		ViewGroup p = (ViewGroup) fragmentView.getParent();
		if (p != null) {
			p.removeAllViews();
		}
		return fragmentView;
	}
	
	public abstract View getFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	
	public View findViewById(int id) {
		return getActivity().findViewById(id);
	}
	

	/**
	 * 根据控件id返回对应的控件对象
	 * 
	 * @param id
	 *            控件id
	 * @return 控件对象
	 */
	public TextView findTv(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * 根据控件id返回对应的控件对象
	 * 
	 * @param id
	 *            控件id
	 * @return 控件对象
	 */
	public Button findBut(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * 根据控件id返回对应的控件对象
	 * 
	 * @param id
	 *            控件id
	 * @return 控件对象
	 */
	public ImageView findImg(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * 根据控件id返回对应的控件对象
	 * 
	 * @param id
	 *            控件id
	 * @return 控件对象
	 */
	public EditText findEt(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * 根据控件id返回对应的控件对象
	 * 
	 * @param id
	 *            控件id
	 * @return 控件对象
	 */
	public LinearLayout findLin(int id) {
		return (LinearLayout) findViewById(id);
	}

	/**
	 * 吐司对应传入的内容
	 * 
	 * @param text
	 *            吐司内容
	 */
	public void toastShort(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 使用Log cat打印内容
	 * 
	 * @param text
	 *            要打印的内容
	 */
	public void LogE(String text) {
		Log.e(TAG, text);
	}
}
