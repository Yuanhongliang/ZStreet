package com.ksfc.framework.lib.widget.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ksfc.framework.lib.widget.imgpager.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xiaoyuan.zstreet.R;

public class CircleImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private String thumbUrl;
	private DisplayImageOptions defaultOptions;

	public static CircleImageDetailFragment newInstance(String imageUrl) {
		final CircleImageDetailFragment f = new CircleImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// http://123.57.137.163:9090/friends/1504/27/15/thumb/7f863c5b-0a3d-4997-a753-71d791caaae5.jpg
		thumbUrl = getArguments() != null ? getArguments().getString("url") : null;
		try {
			if (thumbUrl != null && thumbUrl.contains("/thumb")) {
				String[] split = thumbUrl.split("/thumb");
				if (split != null && split.length == 2) {
					mImageUrl = split[0] + split[1];
				} else {
					mImageUrl = thumbUrl;
				}
			}else{
				mImageUrl = thumbUrl;
			}
		} catch (Exception e) {
			e.printStackTrace();
			mImageUrl = thumbUrl;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		defaultOptions = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.img_no_exist)
//				.showImageOnFail(R.drawable.img_no_exist).
//						.considerExifParams(true)
//				.cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();
		/*if (thumbUrl != null && thumbUrl.contains("/thumb")) {
			File file = ImageLoader.getInstance().getDiscCache().get(thumbUrl);
			if (file != null && file.exists()) {
				Bitmap bm = Util.loadBitmapFromFile(file, 800, 800);
				if (bm != null) {
					mImageView.setImageBitmap(bm);
				}
			}
		}*/
		loadBig();
		/*
		 * ImageLoader.getInstance().displayImage(thumbUrl, mImageView, new
		 * SimpleImageLoadingListener() {
		 * 
		 * @Override public void onLoadingFailed(String imageUri, View view,
		 * FailReason failReason) { loadBig(); }
		 * 
		 * @Override public void onLoadingComplete(String imageUri, View view,
		 * Bitmap loadedImage) { loadBig(); }
		 * 
		 * @Override public void onLoadingCancelled(String imageUri, View view)
		 * { //loadBig(); } });
		 */

	}

	private void loadBig() {
		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, defaultOptions,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						String message = "";
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "ÎÄ¼þ´íÎó";
							break;
						case DECODING_ERROR:
							message = "±àÂë´íÎó";
							break;
						case NETWORK_DENIED:
							message = "ÍøÂçÎÊÌâ";
							break;
						case OUT_OF_MEMORY:
							message = "ÄÚ´æÒç³ö";
							break;
						case UNKNOWN:
							message = "Î´Öª´íÎó";
							break;
						}
						// Toast.makeText(getActivity(), message,
						// Toast.LENGTH_SHORT).show();
						progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						progressBar.setVisibility(View.GONE);
						mAttacher.update();
					}
				});
	}

}
