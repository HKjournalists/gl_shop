package com.glshop.net.utils;

import android.content.Context;

public final class ScreenUtil {
	private static final float ADD = 0.5f;

	private ScreenUtil() {
	}

	/**
	 * 将dip转为 pix(像素)
	 * 
	 * @param context
	 *            context
	 * @param dpValue
	 *            dp值
	 * @return int
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + ADD);
	}

	/**
	 * 将 pix(像素)转为 dip
	 * 
	 * @param context
	 *            context
	 * @param pxValue
	 *            dp值
	 * @return int
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + ADD);
	}
}
