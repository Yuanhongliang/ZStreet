package com.xiaoyuan.zstreet.base;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���ϳ�ȡ��Activity �淶����
 * 
 * @author Administrator
 * 
 */
public class BaseActivity extends Activity {

	/**
	 * ���ݿؼ�ID�ҵ�ImageView
	 * 
	 * @param id
	 *            �ؼ�ID
	 * @return ImageView�ؼ�
	 */
	public ImageView findImg(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * ���ݿؼ�ID�ҵ�TextView
	 * 
	 * @param id
	 *            �ؼ�ID
	 * @return TextView�ؼ�
	 */
	public TextView findTv(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * ���ݿؼ�ID�ҵ�Button
	 * 
	 * @param id
	 *            �ؼ�ID
	 * @return Button�ؼ�
	 */
	public Button findBut(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * ���ݿؼ�ID�ҵ�EditText
	 * 
	 * @param id
	 *            �ؼ�ID
	 * @return EditText�ؼ�
	 */
	public EditText findEt(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * ���ݿؼ�ID�ҵ�LinearLayout
	 * 
	 * @param id
	 *            �ؼ�ID
	 * @return LinearLayout�ؼ�
	 */
	public LinearLayout findLin(int id) {
		return (LinearLayout) findViewById(id);
	}

	/**
	 * ���ص�ǰ��Activity
	 * 
	 * @return ��ǰActivity
	 */
	public BaseActivity getAct() {
		return this;
	}

	/**
	 * �ڿ���̨��ӡ����
	 * 
	 * @param text
	 *            Ҫ��ӡ������
	 */
	public void logE(String text) {
		Log.e("Zstreet", text);
	}
	/**
	 * ��˾����
	 * @param text Ҫ��˾������
	 */
	public void toastShort(String text){
		Toast.makeText(getAct(), text, Toast.LENGTH_SHORT).show();
	}

}
