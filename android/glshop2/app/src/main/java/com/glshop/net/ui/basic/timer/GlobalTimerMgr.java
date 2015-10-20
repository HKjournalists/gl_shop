package com.glshop.net.ui.basic.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description : 全局定时器管理，更新倒计时时间
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午3:00:27
 */
public class GlobalTimerMgr {

	private static GlobalTimerMgr mInstance;

	/**计时器计时间隔*/
	private static final int TIME_UNIT = 30 * 1000;

	private Timer mTimer;

	private TimerTask mTask;

	private List<ITimerListener> mListeners = new ArrayList<ITimerListener>();

	private Object lock = new Object();

	private GlobalTimerMgr() {
		mTimer = new Timer();
	}

	public synchronized static GlobalTimerMgr getInstance() {
		if (mInstance == null) {
			mInstance = new GlobalTimerMgr();
		}
		return mInstance;
	}

	/**
	 * 添加定时器监听
	 * @param listener
	 */
	public void addTimerListener(ITimerListener listener) {
		synchronized (lock) {
			if (!mListeners.contains(listener)) {
				mListeners.add(listener);
			}
			checkTimerStatus();
		}
	}

	/**
	 * 删除定时器监听
	 * @param listener
	 */
	public void removeTimerListener(ITimerListener listener) {
		synchronized (lock) {
			if (mListeners.contains(listener)) {
				mListeners.remove(listener);
			}
			checkTimerStatus();
		}
	}

	/**
	 * 删除所有定时器监听
	 * @param listener
	 */
	public void cleanAllTimerListener() {
		synchronized (lock) {
			mListeners.clear();
			checkTimerStatus();
		}
	}

	private void checkTimerStatus() {
		if (mListeners.size() == 0) {
			stopTimer();
		} else {
			if (mTask == null) {
				startTimer();
			}
		}
	}

	private void startTimer() {
		stopTimer();
		mTask = new TimerTask() {

			@Override
			public void run() {
				updateTimer();
			}
		};
		mTimer.scheduleAtFixedRate(mTask, 0, TIME_UNIT);
	}

	private void stopTimer() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
	}

	private void updateTimer() {
		synchronized (lock) {
			if (mListeners.size() != 0) {
				for (int i = mListeners.size() - 1; i >= 0; i--) {
					ITimerListener listener = mListeners.get(i);
					if (listener != null) {
						listener.onTimerTick();
					}
				}
			}
		}
	}

	/**
	 * 定时器监听
	 */
	public interface ITimerListener {

		/**
		 * 回调接口
		 */
		public void onTimerTick();

	}
}
