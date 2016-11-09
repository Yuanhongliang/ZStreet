package com.xiaoyuan.zstreet.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * ������������
 * @author Administrator
 *
 */
public class MyCache implements ImageCache {

	private LruCache<String, Bitmap> lru;

	public MyCache() {
		super();
		// maxSize: ����ռ�Ĵ�С
		lru = new LruCache<String, Bitmap>(10  * 1024 * 1024) {

			/**
			 * ���㵥��ͼƬ ��Ҫռ�û���ռ�Ĵ�С
			 */
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getRowBytes() * value.getHeight();
			}

		};
	}
	/**
	 * ���һ��ͼƬ
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return lru.get(url);
	}
	
	/**
	 * �洢һ��ͼƬ
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		lru.put(url, bitmap);
	}

}
