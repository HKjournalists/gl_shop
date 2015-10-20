package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.glshop.net.R;

/**
 * @Description : 自定义通用页面指示器控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午9:53:54
 */
public class CustomPageIndicator extends LinearLayout {

	private Context mContext;

	/**
	 * 指示器中页码数目
	 */
	private int pageCount = 0;

	/**
	 * 指示器中页码间隔
	 */
	private int pageMargin = 0;

	/**
	 * 当前页的页码背景
	 */
	private Drawable mSelectedDrawable;

	/**
	 * 非当前页的页码背景
	 */
	private Drawable mUnSelectedDrawable;

	public CustomPageIndicator(Context context) {
		this(context, null);
	}

	public CustomPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mContext = context;

		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PageIndicator);
		final int N = array.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.PageIndicator_selected_page_bg:
				mSelectedDrawable = array.getDrawable(attr);
				break;
			case R.styleable.PageIndicator_unselected_page_bg:
				mUnSelectedDrawable = array.getDrawable(attr);
				break;
			case R.styleable.PageIndicator_pagePadding:
				pageMargin = array.getDimensionPixelSize(attr, -1);
				break;
			}
		}
		array.recycle();
	}

	/**
	 * 更新页码指示器
	 */
	private void updatePageIndicator() {
		if (pageCount > 0) {

			if (mUnSelectedDrawable == null || mSelectedDrawable == null) {
				return;
			}

			removeAllViews();
			for (int i = 0; i < pageCount; i++) {
				ImageView view = new ImageView(mContext);
				view.setBackgroundDrawable(mUnSelectedDrawable);
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				if (i != 0) {
					params.leftMargin = pageMargin;
				}
				addView(view, params);
			}
		}
	}

	/**
	 * 设置当前页的页码背景
	 * @param drawable
	 */
	public void setSelectedDrawable(Drawable drawable) {
		mSelectedDrawable = drawable;
		updatePageIndicator();
	}

	/**
	 * 设置非当前页的页码背景
	 * @param drawable
	 */
	public void setUnSelectedDrawable(Drawable drawable) {
		mUnSelectedDrawable = drawable;
		updatePageIndicator();
	}

	/**
	 * 设置指示器中页码数目
	 * @param count 页面数目
	 */
	public void setPageCount(int count) {
		if (count > 0) {
			pageCount = count;
			updatePageIndicator();
		}
	}
	
	/**
	 * 获取页面数目
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 设置指示器中页码间隔
	 * @param margin 间隔
	 */
	public void setPageMargin(int margin) {
		pageMargin = margin;
		updatePageIndicator();
	}

	/**
	 * 设置第index个页面为当前页
	 * @param index 当前页码
	 */
	public void setPageSelectedIndex(int index) {

		if (mUnSelectedDrawable == null || mSelectedDrawable == null) {
			return;
		}

		if (index >= 0 && index < pageCount) {
			for (int i = 0; i < pageCount; i++) {
				View view = getChildAt(i);
				if (view != null && view instanceof ImageView) {
					if (index == i) {
						view.setBackgroundDrawable(mSelectedDrawable);
					} else {
						view.setBackgroundDrawable(mUnSelectedDrawable);
					}
				}
			}
		}
	}

}
