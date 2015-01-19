package com.glshop.net.ui.basic.adapter.profile;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 地域列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class AreaAddrListAdapter extends BasicAdapter<AreaInfoModel> {

	private String mSelectedArea = "";

	public AreaAddrListAdapter(Context context, List<AreaInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_area_addr_list_item, null);
		}

		AreaInfoModel model = getItem(position);

		TextView mTvAddrContent = ViewHolder.get(convertView, R.id.tv_addr_content);
		mTvAddrContent.setText(model.name);

		View itemBg = ViewHolder.get(convertView, R.id.ll_addr_content);
		if (StringUtils.isNotEmpty(mSelectedArea) && mSelectedArea.equals(model.code)) {
			itemBg.setBackgroundResource(R.drawable.bg_list_item_press);
		} else {
			itemBg.setBackgroundResource(R.drawable.selector_list_item);
		}

		return convertView;
	}

	public void setSelectedAddr(String areaCode) {
		mSelectedArea = areaCode;
	}

}
