package com.ksfc.framework.lib.widget.image;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ksfc.framework.lib.widget.imgpager.PhotoView;
import com.ksfc.framework.lib.widget.imgpager.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiaoyuan.zstreet.R;

/**
 * 查看大图
 * 
 * @author Bruce.Wang
 * 
 */
public class BigPicActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";

	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;
	private List<String> urls;

	public static void open(Context context, int position, List<String> urls) {
		Intent intent = new Intent(context, BigPicActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(BigPicActivity.EXTRA_IMAGE_URLS, (Serializable) urls);
		intent.putExtra(BigPicActivity.EXTRA_IMAGE_INDEX, position);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_detail_pager);

		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		urls = (List<String>) getIntent()
				.getSerializableExtra(EXTRA_IMAGE_URLS);

		mPager = (HackyViewPager) findViewById(R.id.pager);

		// initPageFragmentAdapter();

		initPagerAdapter();

		indicator = (TextView) findViewById(R.id.indicator);

		indicator.setText("");
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				// CharSequence text = getString(R.string.big_pic_indicator,
				// arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText("");
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	/**
	 * imageViewAdapter
	 */
	private void initPagerAdapter() {
		ImagePagerUrlAdapter urlAdapter = new ImagePagerUrlAdapter();
		mPager.setAdapter(urlAdapter);
	}

	/**
	 * fragmentPagerAdapter
	 */
	private void initPageFragmentAdapter() {
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(
				getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public List<String> fileList;

		public ImagePagerAdapter(FragmentManager fm, List fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return CircleImageDetailFragment.newInstance(url);
		}

	}

	/** ----------------- imageView -------------------------- */
	private class ImagePagerUrlAdapter extends PagerAdapter {

		private PhotoViewAttacher mAttacher;

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return urls.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View inflate = View.inflate(getApplication(),
					R.layout.item_pager_image, null);
			final PhotoView imageView = (PhotoView) inflate
					.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) inflate
					.findViewById(R.id.loading);

			ImageLoader.getInstance().init(
					ImageLoaderConfiguration
							.createDefault(getApplicationContext()));
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					// .resetViewBeforeLoading(true)
					.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new FadeInBitmapDisplayer(300)).build();
			ImageLoader.getInstance().displayImage(urls.get(position),
					imageView, options, new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String s, View view) {

						}

						@Override
						public void onLoadingFailed(String s, View view,
								FailReason failReason) {

						}

						@Override
						public void onLoadingComplete(String s, View view,
								final Bitmap bitmap) {

							// imageView.setOnLongClickListener(new
							// View.OnLongClickListener() {
							//
							// @Override
							// public boolean onLongClick(View v) {
							// savePhotoDialog = new
							// SavePhotoDialog(BigPicActivity.this);
							// savePhotoDialog.show();
							// savePhotoDialog.setOnSelectClick(new
							// SavePhotoDialog.OnSelectClick() {
							// @Override
							// public void onOKClick() {
							// String path =
							// ImageUtils.saveBitmap(bitmap,System.currentTimeMillis()+"");
							// if(!TextUtils.isEmpty(path)){
							// Toast.makeText(BigPicActivity.this,"图片保存成功",0).show();
							// }
							// }
							// });
							// return false;
							// }
							// });
						}

						@Override
						public void onLoadingCancelled(String s, View view) {

						}
					});
			// imageView.setOnClickListener(new View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// BigPicActivity.this.finish();
			// }
			// });
			imageView
					.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View arg0, float arg1, float arg2) {
							BigPicActivity.this.finish();
						}
					});
			((ViewPager) view).addView(inflate, 0); // 将图片增加到ViewPager
			return inflate;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}