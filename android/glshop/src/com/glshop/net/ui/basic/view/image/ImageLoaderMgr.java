package com.glshop.net.ui.basic.view.image;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.glshop.net.R;
import com.glshop.platform.net.http.image.ImageLoaderListener;
import com.glshop.platform.net.http.image.ImageLoaderManager;

/**
 * @Description : 图片加载(先使用此类简单实现UI，后续业务实现时再使用SDK图片加载替换)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 上午10:33:22
 */
public class ImageLoaderMgr {

	private static ImageLoaderMgr mInstance;

	private ExecutorService localExcutor = Executors.newFixedThreadPool(10);

	/** 图片默认背景 */
	protected static final int IMAGE_DEFAULT = R.drawable.ic_default_pic;

	/** 图片加载背景 */
	protected static final int IMAGE_LOADING = R.drawable.ic_default_pic;

	/** 图片加载失败背景 */
	protected static final int IMAGE_FAILED = R.drawable.ic_error_pic;

	private ImageLoaderMgr() {

	}

	public static synchronized ImageLoaderMgr getInstance() {
		if (mInstance == null) {
			mInstance = new ImageLoaderMgr();
		}
		return mInstance;
	}

	public void displayLocal(final BrowserImageView view, final int resID) {
		localExcutor.execute(new Runnable() {

			@Override
			public void run() {
				view.getHandler().post(new Runnable() {

					@Override
					public void run() {
						view.setImageResource(resID);
					}
				});
			}
		});
	}

	public void displayLocal(final Context context, ImageView view, final String file) {
		view.setBackgroundDrawable(context.getResources().getDrawable(IMAGE_DEFAULT));
		ImageLoaderManager.getIntance().displayLocal(this, file, view, IMAGE_DEFAULT, IMAGE_FAILED, new ImageLoaderListener() {

			@Override
			public void onLoadFinish(Object operation, ImageView imageView, Bitmap loadedImage) {
				if (loadedImage != null) {
					imageView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), loadedImage));
				} else {
					imageView.setBackgroundDrawable(context.getResources().getDrawable(IMAGE_FAILED));
				}
			}
		});
	}
}
