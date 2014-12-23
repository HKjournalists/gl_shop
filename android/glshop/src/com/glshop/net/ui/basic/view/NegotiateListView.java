package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;

/**
 * @Description : 自定义议价记录列表显示控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class NegotiateListView extends LinearLayout {

	/**
	 * 议价列表
	 */
	private ViewGroup mContainer;

	/**
	 * 议价列表内容适配器
	 */
	private BasicAdapter<NegotiateInfoModel> mAdapter;

	public NegotiateListView(Context context) {
		this(context, null);
	}

	public NegotiateListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_negotiate_container, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mContainer, lp);
	}

	public void setAdapter(BasicAdapter<NegotiateInfoModel> adapter) {
		this.mAdapter = adapter;
		updateView();
	}

	/**
	 * 显示议价列表
	 */
	private void updateView() {
		final int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			addNegotiateItem(mAdapter.getView(i, null, null));
		}
	}

	/**
	 * 添加议价记录
	 */
	private void addNegotiateItem(View view) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContainer.addView(view, lp);
	}

}
