package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;

/**
 * @Description : 卸货地址列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class AddrListAdapter extends BasicAdapter<AddrInfoModel> {

	public AddrListAdapter(Context context, List<AddrInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_addr_list_item, null);
		}

		AddrInfoModel model = getItem(position);

		TextView mTvAddrContent = ViewHolder.get(convertView, R.id.tv_addr_content);
		mTvAddrContent.setText(String.valueOf(model.deliveryAddrDetail));

		TextView mTvSelectedAddr = ViewHolder.get(convertView, R.id.tv_selected_addr);
		View llSelectedAddr = ViewHolder.get(convertView, R.id.ll_selected_addr);

		if (model.isDefaultAddr) {
			mTvSelectedAddr.setVisibility(View.VISIBLE);
			llSelectedAddr.setVisibility(View.VISIBLE);
		} else {
			mTvSelectedAddr.setVisibility(View.GONE);
			llSelectedAddr.setVisibility(View.GONE);
		}

		return convertView;
	}
}
