package com.glshop.net.ui.basic.view.image;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.ui.browser.BrowsePictureActivity;

public class BrowserViewPager extends ViewPager {

	private GestureDetector mGestureDetector;

	private BrowserImageView imageView;
	// 按下时间和坐标
	private long press_time;
	private int press_x;
	private int press_y;

	int pointerCount;
	// 2次触摸响应的x距离/Y距离
	float distanceX, distanceY;

	final int SLEEP_TIME = 3000;

	public BrowserViewPager(Context context) {
		super(context);
		init();
	}

	public BrowserViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	public void scrollTo(int x, int y) {
		// TODO Auto-generated method stub
		if (scroll) {
			if (getScrollX() != x) {
				x = (int) (getScrollX() + distanceX);
			}
			scroll = false;
		}
		super.scrollTo(x, y);
	}

	// 按下时的x/y坐标
	float x, y;
	// 图片的实时宽，高
	float width, height;
	// 图片实时的上下左右坐标
	float left, right, top, bottom;
	float values[] = new float[9];

	int p = 0;
	boolean scroll = false;

	private void init() {
		mGestureDetector = new GestureDetector(getContext(), new MyGestureDetector());

		this.setOnTouchListener(new OnTouchListener() {
			float baseValue;
			float originalScale;

			@Override
			public boolean onTouch(View o_view, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					//每次触摸,取消自动浏览
					if (mHandler != null) {
						//mHandler.removeMessages(GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_NEXT);
					}

					imageView = ((BrowserExpandImageView) findViewById(getCurrentItem())).image;

					press_time = System.currentTimeMillis();
					press_x = (int) event.getX(0);
					press_y = (int) event.getY(0);

					baseValue = 0;
					pointerCount = 1;
					originalScale = imageView.getScale();

					distanceX = 0;
					distanceY = 0;
					x = event.getX(0);
					y = event.getY(0);
					return true;
				}
				// 图片实时高宽
				width = imageView.getScale() * imageView.getImageWidth();
				height = imageView.getScale() * imageView.getImageHeight();

				Matrix matrix = imageView.getImageMatrix();
				matrix.getValues(values);
				// 图片的边界坐标
				left = values[Matrix.MTRANS_X];
				right = left + width;
				top = values[Matrix.MTRANS_Y];
				bottom = top + height;

				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					// 屏幕已经滑动,不缩放图片
					if (getScrollX() % BrowsePictureActivity.screenWidth != 0) {
						return false;
					}
					// 计算和上一次触摸响应的距离
					distanceX = x - event.getX(0);
					x = event.getX(0);
					distanceY = y - event.getY(0);
					y = event.getY(0);
					// 多点触控，离开手指后，不响应
					if (pointerCount == 2 && event.getPointerCount() == 1) {
						return true;
					}
					// 只有一个手指拖动
					if (event.getPointerCount() == 1) {
						// 多点触摸缩放图片时，防止左右滑屏的误操作
						if (height <= BrowsePictureActivity.screenHeight && (System.currentTimeMillis() - press_time < 200)
								&& (Math.abs(event.getX(0) - press_x) < Math.abs(event.getY(0) - press_y) * 5 / 4)) {
							return true;
						}

						// 如果图片当前大小<屏幕大小，直接处理滑屏事件
						if (width <= BrowsePictureActivity.screenWidth && height <= BrowsePictureActivity.screenHeight) {
							return false;
						}

						/**
						 * ---------------上下边界处理,不允许超过上下边界-----------
						 */
						// 高度能够全部显示,不做上下拖动
						if (height <= BrowsePictureActivity.screenHeight) {
							distanceY = 0;
						} else {
							// 向下拖动
							if (distanceY < 0) {
								if (top - distanceY > 0) {
									distanceY = top;
								}
							} else {
								// 向上拖动
								if (bottom - distanceY < BrowsePictureActivity.screenHeight) {
									distanceY = bottom - BrowsePictureActivity.screenHeight;
								}
							}
							imageView.postTranslate(0, -distanceY);
						}
						/**
						 * -------------------左右边界处理----------------
						 */
						// 向左滑动
						if (distanceX > 0) {
							// 右边界没有超过屏幕宽度,无需处理
							if (right <= BrowsePictureActivity.screenWidth) {
								p = Math.round(event.getX(0) - press_x);
								scroll = true;
								return false;
							}
							// 防止位移过度
							if (right - distanceX < BrowsePictureActivity.screenWidth) {
								distanceX = right - BrowsePictureActivity.screenWidth;
							}
							imageView.postTranslate(-distanceX, 0);
							return true;
						}
						// 向右滑动
						if (distanceX < 0) {
							if (left >= 0) {
								p = (int) (event.getX(0) - press_x);
								scroll = true;
								return false;
							}
							if (left - distanceX > 0) {
								distanceX = left;
							}
							imageView.postTranslate(-distanceX, 0);
							return true;
						}
					}

					// 2个手指触摸,需要缩放图片
					if (event.getPointerCount() == 2) {
						float x = event.getX(0) - event.getX(1);
						float y = event.getY(0) - event.getY(1);
						float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
						if (baseValue == 0) {
							baseValue = value;
						} else {
							float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
							// scale the image
							imageView.zoomTo(originalScale * scale, (event.getX(0) + event.getX(1)) / 2, (event.getY(0) + event.getY(1)) / 2);
						}
					}
					// 记录手指数量
					pointerCount = event.getPointerCount();
					// 多点触控时,不响应左右滑屏
					if (event.getPointerCount() >= 2) {
						return true;
					}
				}

				if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {

					//每次手指离开，开始自动浏览
					if (mHandler != null) {
						mHandler.sendEmptyMessageDelayed(GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_NEXT, SLEEP_TIME);
					}

					// 如果图片缩小了,则还原到初始的缩放比例
					if (imageView.getScaleRate() > imageView.getScale()) {
						imageView.zoomTo(imageView.getScaleRate(), BrowsePictureActivity.screenWidth / 2, BrowsePictureActivity.screenHeight / 2, 200f);
					}

					// 向左滑动
					if (distanceX > 0) {
						// 右边界没有超过屏幕宽度,无需处理
						if (right <= BrowsePictureActivity.screenWidth) {
							return false;
						}
						return true;
					}
					// 向右滑动
					if (distanceX < 0) {
						if (left >= 0) {
							return false;
						}
						return true;
					}
				}
				return false;
			}

		});
	}

	private Handler mHandler;

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	private void sendEmptyMsg(int what) {
		if (mHandler != null) {
			mHandler.sendEmptyMessage(what);
		}
	}

	class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onDoubleTap(MotionEvent event) {
			//Logger.e("", "ScaleRate = " + imageView.getScaleRate() + ", Scale = " + imageView.getScale());
			if (Math.abs(imageView.getScaleRate() - imageView.getScale()) < 0.1f) { // 放大
				imageView.zoomTo(imageView.mMaxZoom, event.getX(), event.getY(), 300f);
			} else if (imageView.getScaleRate() < imageView.getScale()) { // 缩小至原始大小
				imageView.zoomTo(imageView.getScaleRate(), BrowsePictureActivity.screenWidth / 2, BrowsePictureActivity.screenHeight / 2, 300f);
			}
			return super.onDoubleTap(event);
		}

		// 单击
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			sendEmptyMsg(GlobalMessageType.CommonMessageType.MSG_IMAGE_BROWSE_SINGLE_CLICK);
			return true;
		}
	}
}
