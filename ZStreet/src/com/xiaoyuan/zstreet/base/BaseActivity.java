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
 * 向上抽取的Activity 规范代码
 * 
 * @author Administrator
 * 
 */
public class BaseActivity extends Activity {

	/**
	 * 根据控件ID找到ImageView
	 * 
	 * @param id
	 *            控件ID
	 * @return ImageView控件
	 */
	public ImageView findImg(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * 根据控件ID找到TextView
	 * 
	 * @param id
	 *            控件ID
	 * @return TextView控件
	 */
	public TextView findTv(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * 根据控件ID找到Button
	 * 
	 * @param id
	 *            控件ID
	 * @return Button控件
	 */
	public Button findBut(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * 根据控件ID找到EditText
	 * 
	 * @param id
	 *            控件ID
	 * @return EditText控件
	 */
	public EditText findEt(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * 根据控件ID找到LinearLayout
	 * 
	 * @param id
	 *            控件ID
	 * @return LinearLayout控件
	 */
	public LinearLayout findLin(int id) {
		return (LinearLayout) findViewById(id);
	}

	/**
	 * 返回当前的Activity
	 * 
	 * @return 当前Activity
	 */
	public BaseActivity getAct() {
		return this;
	}

	/**
	 * 在控制台打印文字
	 * 
	 * @param text
	 *            要打印的文字
	 */
	public void logE(String text) {
		Log.e("Zstreet", text);
	}
	/**
	 * 吐司文字
	 * @param text 要吐司的文字
	 */
	public void toastShort(String text){
		Toast.makeText(getAct(), text, Toast.LENGTH_SHORT).show();
	}

}
