package com.glshop.net.ui.basic.view.cycleview;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.glshop.platform.utils.Logger;

/**
 * @Description : 自定义可循环滚动的ViewPager
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class CycleViewPager extends ViewPager {

	/**
	 * 动画间隔时间
	 */
	private long mAnimationInterval = 5000;

	/**
	 * 是否可循环滚动
	 */
	private boolean isCycleMoveEnabled = false;

	/**
	 * 定时器
	 */
	private Timer mTimer;

	/**
	 * 定时器任务
	 */
	private TimerTask mTask;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int index = getCurrentItem();
			Logger.d("CycleViewPager","index="+index);
			setCurrentItem(index + 1, true);
		}

	};

	public CycleViewPager(Context context) {
		super(context);
		init();
	}

	public CycleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mTimer = new Timer();
	}

	/**
	 * 设置是否可循环滚动
	 * @param isCycle
	 */
	public void setCycleMoveEnabled(boolean isCycle) {
		isCycleMoveEnabled = isCycle;
	}

	/**
	 * 设置动画间隔时间
	 * @param interval
	 */
	public void setAnimationInteval(long interval) {
		mAnimationInterval = interval;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isCycleMoveEnabled) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				stopAnimation();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				startAnimation();
				break;
			default:
				// todo nothing
				break;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 开启动画
	 */
	public void startAnimation() {
		if (isCycleMoveEnabled) {
			stopTimer();
			if (mTask == null) {
				mTask = new TimerTask() {

					@Override
					public void run() {
						mHandler.sendEmptyMessage(0);
					}
				};
				mTimer.scheduleAtFixedRate(mTask, mAnimationInterval, mAnimationInterval);
			}
		}
	}

	/**
	 * 停止动画
	 */
	public void stopAnimation() {
		if (isCycleMoveEnabled) {
			stopTimer();
		}
	}

	private void stopTimer() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
	}

}
