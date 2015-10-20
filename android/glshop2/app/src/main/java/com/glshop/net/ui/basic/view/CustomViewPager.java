package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Description : 自定义ViewPager 可以控制是否滑动
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午9:53:54
 */
public class CustomViewPager extends ViewPager {

	private boolean isScrollable = true;

	/**
	 * @param context
	 */
	public CustomViewPager(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isScrollable) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return isScrollable;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isScrollable) {
			return super.onTouchEvent(arg0);
		} else {
			return isScrollable;
		}
	}

	public boolean isScrollable() {
		return isScrollable;
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

}
