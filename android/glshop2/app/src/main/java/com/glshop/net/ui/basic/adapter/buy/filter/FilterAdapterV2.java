package com.glshop.net.ui.basic.adapter.buy.filter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;

/**
 * @Description : 简单筛选复选菜单菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class FilterAdapterV2 extends BasicAdapter<AreaInfoModel> {

	public FilterAdapterV2(Context context, List<AreaInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_filter_list_item_v2, null);
		}

		AreaInfoModel model = getItem(position);

		TextView mTvContent = ViewHolder.get(convertView, R.id.tv_item_content);
		mTvContent.setText(model.name);

		ImageView mIvSelectStatus = ViewHolder.get(convertView, R.id.iv_menu_select_status);
		mIvSelectStatus.setVisibility(model.isSelectedForUI ? View.VISIBLE : View.GONE);
		return convertView;
	}

}