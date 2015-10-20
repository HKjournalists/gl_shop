package com.glshop.net.ui.basic.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description : 倒计时定时器管理，用于倒计时显示
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 下午5:37:47
 */
public class CountdownTimerMgr {

	private static CountdownTimerMgr mTimerMgr;

	/**计时器计时间隔*/
	public static final int TIME_UNIT = 1000;

	/**倒计时总时间,单位：秒*/
	public static final int WAIT_NEXT_TIME = 60;

	/**倒计时总时间*/
	private long mTotalTime = 0;

	/**已运行的时间*/
	private long mFinishedTime = 0;

	private Timer mTimer;

	private TimerTask mTask;

	private ITimerCallback mCallback;

	private CountdownTimerMgr() {
		mTimer = new Timer();
	}

	public synchronized static CountdownTimerMgr getInstance() {
		if (mTimerMgr == null) {
			mTimerMgr = new CountdownTimerMgr();
		}
		return mTimerMgr;
	}

	public synchronized void startTimer(long time, ITimerCallback callback) {
		mTotalTime = time;
		mFinishedTime = 0;
		mCallback = callback;
		initTask();
		onTaskStart();
		mTimer.scheduleAtFixedRate(mTask, 0, TIME_UNIT);
	}

	public synchronized void stopTimer() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
	}

	private void initTask() {

		stopTimer();

		mTask = new TimerTask() {

			@Override
			public void run() {

				mFinishedTime += TIME_UNIT;
				if (mFinishedTime >= mTotalTime) {
					cancel();
					onTaskEnd();
				} else {
					onTaskUpdate((int) ((mTotalTime - mFinishedTime) / TIME_UNIT));
				}

			}
		};
	}

	private void onTaskStart() {
		if (mCallback != null) {
			mCallback.onStart();
		}
	}

	private void onTaskUpdate(int progress) {
		if (mCallback != null) {
			mCallback.onProgress(progress);
		}
	}

	private void onTaskEnd() {
		if (mCallback != null) {
			mCallback.onEnd();
		}
	}

	/**
	 * 倒计时回调接口定义
	 */
	public interface ITimerCallback {

		/**
		 * 开始倒计时
		 */
		public void onStart();

		/**
		 * 当前倒计时进度
		 */
		public void onProgress(int progress);

		/**
		 * 倒计时结束
		 */
		public void onEnd();

	}

}
