package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;

/**
 * @Description : 自定义列表显示控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class ListViewV2 extends LinearLayout {

	/**
	 * 列表内容容器
	 */
	private ViewGroup mContainer;

	/**
	 * 列表内容适配器
	 */
	private BasicAdapter mAdapter;

	public ListViewV2(Context context) {
		this(context, null);
	}

	public ListViewV2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_container, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mContainer, lp);
	}

	public void setAdapter(BasicAdapter adapter) {
		this.mAdapter = adapter;
		if (mAdapter != null) {
			mAdapter.registerDataSetObserver(mDataSetObserver);
		} else {
			mAdapter.unregisterDataSetObserver(mDataSetObserver);
		}
		updateView();
	}

	/**
	 * 显示列表
	 */
	private void updateView() {
		final int count = mAdapter.getCount();
		int curChildCount = mContainer.getChildCount();
		if (curChildCount > count) {
			for (int i = curChildCount - 1; i > count - 1; i--) {
				mContainer.removeViewAt(i);
			}
		}

		curChildCount = mContainer.getChildCount();
		for (int i = 0; i < count; i++) {
			if (i < curChildCount && curChildCount > 0) {
				mAdapter.getView(i, mContainer.getChildAt(i), null);
			} else {
				addListItem(mAdapter.getView(i, null, null));
			}
		}
	}

	/**
	 * 添加列表项
	 */
	private void addListItem(View view) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContainer.addView(view, lp);
	}

	private DataSetObserver mDataSetObserver = new DataSetObserver() {

		@Override
		public void onChanged() {
			updateView();
		}

	};

}
