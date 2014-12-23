package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;

/**
 * @Description : 第一次议价列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class FirstNegotiateAdapter extends BasicAdapter<NegotiateInfoModel> {

	public FirstNegotiateAdapter(Context context, List<NegotiateInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NegotiateInfoModel model = getItem(position);
		convertView = View.inflate(mContext, model.isMine ? R.layout.layout_first_negotiate_item_mine : R.layout.layout_first_negotiate_item_another, null);

		TextView tvDatetime = (TextView) convertView.findViewById(R.id.negotiate_date);
		tvDatetime.setText(model.oprTime);

		TextView tvUnitPrice = (TextView) convertView.findViewById(R.id.tv_unit_price);
		tvUnitPrice.setText(String.valueOf(model.unitPrice));

		TextView tvNegUnitPrice = (TextView) convertView.findViewById(R.id.tv_neg_unit_price);
		tvNegUnitPrice.setText(String.valueOf(model.negUnitPrice));

		TextView tvTradeAmount = (TextView) convertView.findViewById(R.id.tv_trade_amount);
		tvTradeAmount.setText(String.valueOf(model.tradeAmount));

		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.tv_total_price);
		tvTotalPrice.setText(String.valueOf(model.unitPrice * model.tradeAmount));

		TextView tvNegAmount = (TextView) convertView.findViewById(R.id.tv_neg_total_price);
		tvNegAmount.setText(String.valueOf(model.negUnitPrice * model.negAmount));

		TextView tvNegTotalPrice = (TextView) convertView.findViewById(R.id.tv_neg_change_total_price);
		tvNegTotalPrice.setText(String.valueOf(model.unitPrice * model.tradeAmount - model.negUnitPrice * model.negAmount));

		TextView tvNegReason = (TextView) convertView.findViewById(R.id.tv_neg_reason);
		tvNegReason.setText(model.reason);
		return convertView;
	}
}
