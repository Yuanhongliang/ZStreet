package com.xiaoyuan.zstreet.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 建立缓存区域
 * @author Administrator
 *
 */
public class MyCache implements ImageCache {

	private LruCache<String, Bitmap> lru;

	public MyCache() {
		super();
		// maxSize: 缓存空间的大小
		lru = new LruCache<String, Bitmap>(10  * 1024 * 1024) {

			/**
			 * 计算单张图片 需要占用缓存空间的大小
			 */
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getRowBytes() * value.getHeight();
			}

		};
	}
	/**
	 * 获得一张图片
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return lru.get(url);
	}
	
	/**
	 * 存储一张图片
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		lru.put(url, bitmap);
	}

}
