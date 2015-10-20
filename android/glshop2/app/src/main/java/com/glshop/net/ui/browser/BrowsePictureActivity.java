package com.glshop.net.ui.browser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.image.BrowserExpandImageView;
import com.glshop.net.ui.basic.view.image.BrowserViewPager;
import com.glshop.net.ui.basic.view.image.ImageLoaderMgr;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.http.image.ImageLoaderListener;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 图片浏览
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class BrowsePictureActivity extends BasicActivity {

	private static final String TAG = "BrowsePictureActivity";

	// 标题栏
	private View llTitleBar;

	// 进度条
	private View llProgressBar;

	// 图片最大宽度
	public static int screenWidth;

	// 图片最大高度
	public static int screenHeight;

	// 浏览容器
	private BrowserViewPager viewPager;

	// 浏览适配器
	private ImageAdapter imageAdapter;

	// 当前项
	private int currentItem = 0;

	// 图片信息列表
	private List<ImageInfoModel> mImgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_picture);
		screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight() - ActivityUtil.getStatusHeight(this);
		initView();
	}

	private void initView() {
		mImgList = (ArrayList<ImageInfoModel>) getIntent().getSerializableExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PICTURE_INFO);
		if (BeanUtils.isNotEmpty(mImgList)) {
			llTitleBar = getView(R.id.ll_common_title);
			llProgressBar = getView(R.id.load_progress_bar);

			viewPager = (BrowserViewPager) findViewById(R.id.browser_viewpager);
			imageAdapter = new ImageAdapter(this, mImgList);
			viewPager.setAdapter(imageAdapter);
			viewPager.setHandler(getHandler());
			viewPager.setCurrentItem(currentItem);
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					if (viewPager.findViewById(currentItem) != null) {
						BrowserExpandImageView imageView = (BrowserExpandImageView) (viewPager.findViewById(currentItem));
						imageView.image.requestImage();
					}
					currentItem = position;
				}

				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});

			getHandler().sendEmptyMessageDelayed(GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_SINGLE_CLICK, 1000);

			((ImageView) findViewById(R.id.iv_common_back)).setOnClickListener(this);

		} else {
			showToast("图片地址不能为空!");
			finish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		switch (message.what) {
		case GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_SINGLE_CLICK:
			updateTitleBar(!(llTitleBar.getVisibility() == View.VISIBLE));
			break;
		case GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_NEXT:
			// TODO
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	/**
	 * 更新标题栏状态
	 * @param isShow
	 */
	private void updateTitleBar(boolean isShow) {
		if (isShow) {
			llTitleBar.setVisibility(View.VISIBLE);
			llTitleBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.common_titlebar_show));
		} else {
			llTitleBar.setVisibility(View.GONE);
			llTitleBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.common_titlebar_hidden));
		}
	}

	public class ImageAdapter extends PagerAdapter {

		private Context mContext;

		private List<ImageInfoModel> imageList;

		/** 默认缩略图 **/
		private Bitmap defaultBitmap;

		/** 加载失败缩略图 **/
		private Bitmap failBitmp;

		public ImageAdapter(Context context, List<ImageInfoModel> list) {
			mContext = context;
			imageList = list;
			defaultBitmap = BitmapFactory.decodeResource(context.getResources(), IMAGE_DEFAULT);
			failBitmp = BitmapFactory.decodeResource(context.getResources(), IMAGE_FAILED);
		}

		@Override
		public int getCount() {
			return imageList == null ? 0 : imageList.size();
		}

		public ImageInfoModel getItem(int position) {
			if (imageList != null && position >= 0 && position < imageList.size()) {
				return imageList.get(position);
			}
			return null;
		}

		@Override
		public Object instantiateItem(View collection, final int position) {
			BrowserExpandImageView layout = new BrowserExpandImageView(mContext);
			layout.setId(position);
			((ViewPager) collection).addView(layout);
			setImage(layout, position);
			return layout;
		}

		public void setImage(BrowserExpandImageView layout, final int position) {
			//final ImageView image = layout.image;
			//image.setImageBitmap(failBitmp);
			//ImageLoaderMgr.getInstance().loadImage(layout.image, failBitmp);

			ImageInfoModel info = getItem(position);
			if (info.resourceId > 0) {
				//layout.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), info.resourceId));
				Logger.e(TAG, "Load local resource: resID = " + info.resourceId);
				ImageLoaderMgr.getInstance().displayLocal(layout.image, info.resourceId);
			} else if (info.localFile != null) {
				Logger.e(TAG, "Load local image: file = " + info.localFile.getAbsolutePath());
				llProgressBar.setVisibility(View.VISIBLE);
				ImageLoaderManager.getIntance().displayLocal(this, info.localFile.getAbsolutePath(), layout.image, IMAGE_DEFAULT, IMAGE_FAILED, new ImageLoaderListener() {

					@Override
					public void onLoadFinish(Object operation, ImageView imageView, Bitmap loadedImage) {
						llProgressBar.setVisibility(View.GONE);
						if (loadedImage != null) {
							imageView.setImageBitmap(loadedImage);
						} else {
							imageView.setImageBitmap(failBitmp);
						}
					}
				});
			} else {
				Logger.e(TAG, "Load net image: url = " + info.cloudUrl);
				llProgressBar.setVisibility(View.VISIBLE);
				ImageLoaderManager.getIntance().display(this, info.cloudUrl, layout.image, IMAGE_DEFAULT, IMAGE_FAILED, new ImageLoaderListener() {

					@Override
					public void onLoadFinish(Object operation, ImageView imageView, Bitmap loadedImage) {
						llProgressBar.setVisibility(View.GONE);
						if (loadedImage != null) {
							imageView.setImageBitmap(loadedImage);
						} else {
							imageView.setImageBitmap(failBitmp);
						}
					}
				});
			}
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
