package com.glshop.net.ui.basic.adapter.buy.filter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;

/**
 * @Description : 自定义选菜单适配器基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class BaseProductFilterAdapter extends BasicAdapter<ProductCfgInfoModel> implements OnClickListener {

	protected View mListView;

	protected ProductCfgInfoModel mFocusedInfo;

	protected OnItemSelectChangeListener mListener;

	public BaseProductFilterAdapter(Context context, OnItemSelectChangeListener listener, List<ProductCfgInfoModel> list) {
		super(context, list);
		this.mListener = listener;
	}

	@Override
	public void onClick(View v) {

	}

	public void setListView(View v) {
		mListView = v;
	}

	public void setFocusedInfo(ProductCfgInfoModel info) {
		mFocusedInfo = info;
		notifyDataSetChanged();
	}

	public interface OnItemSelectChangeListener {

		void onItemSelectChanged(View view, ProductCfgInfoModel info, int position);

		void onItemClick(View view, int position);

	}

}