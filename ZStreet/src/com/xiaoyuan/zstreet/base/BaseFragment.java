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
 * ���ϳ�ȡ�ĸ���Fragment ʹ�ø��ӷ���
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
	 * ���ݿؼ�id���ض�Ӧ�Ŀؼ�����
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�����
	 */
	public TextView findTv(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * ���ݿؼ�id���ض�Ӧ�Ŀؼ�����
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�����
	 */
	public Button findBut(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * ���ݿؼ�id���ض�Ӧ�Ŀؼ�����
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�����
	 */
	public ImageView findImg(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * ���ݿؼ�id���ض�Ӧ�Ŀؼ�����
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�����
	 */
	public EditText findEt(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * ���ݿؼ�id���ض�Ӧ�Ŀؼ�����
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�����
	 */
	public LinearLayout findLin(int id) {
		return (LinearLayout) findViewById(id);
	}

	/**
	 * ��˾��Ӧ���������
	 * 
	 * @param text
	 *            ��˾����
	 */
	public void toastShort(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ʹ��Log cat��ӡ����
	 * 
	 * @param text
	 *            Ҫ��ӡ������
	 */
	public void LogE(String text) {
		Log.e(TAG, text);
	}
}
