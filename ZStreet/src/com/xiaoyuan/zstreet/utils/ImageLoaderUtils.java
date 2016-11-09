package com.xiaoyuan.zstreet.utils;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.xiaoyuan.zstreet.R;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.cache.MyCache;
/**
 * 加载图片的工具类
 * @author Administrator
 *
 */
public class ImageLoaderUtils {
	
	private static ImageLoader loader = new ImageLoader(MyApplication.queue, new MyCache());
	
	public static ImageLoader getLoader() {
		return loader;
	}

	public static void setLoader(ImageLoader loader) {
		ImageLoaderUtils.loader = loader;
	}

	private static ImageListener listener;
	/**
	 * 加载小图片
	 * @param img 要显示的控件
	 * @param url 图片url
	 */
	public static void loadSmallImg(ImageView img,String url){
		listener = ImageLoader.getImageListener(img, R.drawable.wait,R.drawable.wait);
		loader.get(url, listener, 200, 200);
	}
	
	/**
	 * 加载大图片
	 * @param img 要显示的控件
	 * @param url 图片url
	 */
	public static void loadBigImg(ImageView img,String url){
		listener = ImageLoader.getImageListener(img, R.drawable.wait,R.drawable.wait);
		loader.get(url, listener, 500, 500);
	}
}
