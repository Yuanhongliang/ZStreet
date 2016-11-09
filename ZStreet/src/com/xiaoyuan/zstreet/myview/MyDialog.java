package com.xiaoyuan.zstreet.myview;


import com.xiaoyuan.zstreet.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
/**
 * �Զ���Dialog����ʽ
 * @author Administrator
 *
 */
public class MyDialog extends ProgressDialog {

	public MyDialog(Context context) {
		super(context);
	}
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.myloading);  
        setScreenBrightness();  
        this.setOnShowListener(new OnShowListener(){  
  
                @Override  
                public void onShow(DialogInterface dialog) {  
                    ImageView image = (ImageView) MyDialog.this.findViewById(R.id.loading_img);  
                    Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);  
                    anim.setRepeatCount(Animation.INFINITE); // ����INFINITE����Ӧֵ-1�������ظ�����Ϊ�����  
                    anim.setDuration(1000);                  // ���øö����ĳ���ʱ�䣬���뵥λ  
                    anim.setInterpolator(new LinearInterpolator()); // ����һ������������в�������������ɴӶ�����һ����ʼ�������м�Ĳ��䲿��  
                    image.startAnimation(anim);  
                }  
            });  
    }  
  
    private void setScreenBrightness() {  
        Window window = getWindow();
		WindowManager.LayoutParams lp = window .getAttributes();  
		/** 
		 *  �˴���������ֵ��dimAmount����ڰ�������Ҳ���ǻ谵�Ķ��٣�����Ϊ0�������ȫ������ 
		 *  ��Χ��0.0��1.0 
		 */  
        lp.dimAmount = 0; 
        window.setAttributes(lp);
    }  
}
