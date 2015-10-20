package com.glshop.net.ui.basic.adapter.buy.filter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;

/**
 * @Description : 带Checkbox的筛选复选菜单菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class ProductFilterAdapterV1 extends BaseProductFilterAdapter {

	public ProductFilterAdapterV1(Context context, OnItemSelectChangeListener listener, List<ProductCfgInfoModel> list) {
		super(context, listener, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_filter_product_list_item_v1, null);
		}

		convertView.setTag(R.id.ll_list_item, position);
		convertView.setOnClickListener(this);

		ProductCfgInfoModel model = getItem(position);

		if (mFocusedInfo != null && mFocusedInfo.mCategoryCode.equals(model.mCategoryCode)) {
			ViewHolder.get(convertView, R.id.ll_list_item).setBackgroundResource(R.drawable.bg_list_item_press);
		} else {
			ViewHolder.get(convertView, R.id.ll_list_item).setBackgroundResource(R.drawable.selector_list_item);
		}

		TextView mTvContent = ViewHolder.get(convertView, R.id.tv_item_content);
		mTvContent.setText(model.mCategoryName + SysCfgUtils.getSize(model));

		CheckedTextView ckbView = ViewHolder.get(convertView, R.id.chkTv_select);
		ckbView.setChecked(model.isSelectedForUI);

		View llCkbView = ViewHolder.get(convertView, R.id.ll_selected_status);
		llCkbView.setTag(position);
		llCkbView.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		ProductCfgInfoModel info = null;
		int position = 0;
		switch (v.getId()) {
		case R.id.ll_selected_status:
			CheckedTextView ckbView = (CheckedTextView) v.findViewById(R.id.chkTv_select);
			ckbView.toggle();
			position = (Integer) v.getTag();
			info = getItem(position);
			info.isSelectedForUI = ckbView.isChecked();
			if (mListener != null) {
				mListener.onItemSelectChanged(mListView, info, position);
			}
			break;
		case R.id.ll_list_item:
			position = (Integer) v.getTag(R.id.ll_list_item);
			if (mListener != null) {
				mListener.onItemClick(mListView, position);
			}
			break;
		}
	}

}