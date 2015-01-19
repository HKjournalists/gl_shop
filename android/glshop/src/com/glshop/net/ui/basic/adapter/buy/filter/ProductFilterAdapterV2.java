package com.glshop.net.ui.basic.adapter.buy.filter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;

/**
 * @Description : 简单筛选复选菜单菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class ProductFilterAdapterV2 extends BaseProductFilterAdapter {

	public ProductFilterAdapterV2(Context context, OnItemSelectChangeListener listener, List<ProductCfgInfoModel> list) {
		super(context, listener, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_filter_product_list_item_v2, null);
		}

		convertView.setTag(R.id.ll_list_item, position);
		convertView.setOnClickListener(this);

		ProductCfgInfoModel model = getItem(position);

		TextView mTvContent = ViewHolder.get(convertView, R.id.tv_item_content);
		mTvContent.setText(model.mSubCategoryName + SysCfgUtils.getSize(model));

		ImageView mIvSelectStatus = ViewHolder.get(convertView, R.id.iv_menu_select_status);
		mIvSelectStatus.setVisibility(model.isSelectedForUI ? View.VISIBLE : View.GONE);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_list_item:
			int position = (Integer) v.getTag(R.id.ll_list_item);
			ProductCfgInfoModel info = getItem(position);
			info.isSelectedForUI = !info.isSelectedForUI;
			ImageView mIvSelectStatus = (ImageView) v.findViewById(R.id.iv_menu_select_status);
			mIvSelectStatus.setVisibility(info.isSelectedForUI ? View.VISIBLE : View.GONE);
			if (mListener != null) {
				mListener.onItemSelectChanged(mListView, info, position);
			}
			break;
		}
	}

}