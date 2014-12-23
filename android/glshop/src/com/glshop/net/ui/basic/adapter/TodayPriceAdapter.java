package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;

/**
 * @Description : 主页今日价格适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午10:56:28
 */
public class TodayPriceAdapter extends BasicAdapter<TodayPriceModel> {

	public TodayPriceAdapter(Context context, List<TodayPriceModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_today_price_item, null);
		}

		TodayPriceModel model = getItem(position);

		TextView mTvProductSpec = ViewHolder.get(convertView, R.id.tv_product_spec);
		mTvProductSpec.setText(String.valueOf(model.productName));

		TextView mTvTodayPrice = ViewHolder.get(convertView, R.id.tv_product_today_price);
		mTvTodayPrice.setText(String.valueOf(model.todayPrice));

		return convertView;
	}

}
