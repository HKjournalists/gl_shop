package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.utils.DateUtils;
import com.glshop.net.utils.EnumUtil;
import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 钱包交易流水列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class DealListAdapter extends BasicAdapter<DealSummaryInfoModel> {

	public DealListAdapter(Context context, List<DealSummaryInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_deal_list_item, null);
		}

		DealSummaryInfoModel model = getItem(position);

		TextView tvDealType = ViewHolder.get(convertView, R.id.tv_deal_type);
		tvDealType.setText(EnumUtil.parseDealType(model.oprType));

		TextView tvDealTime = ViewHolder.get(convertView, R.id.tv_deal_time);
		tvDealTime.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.DEAL_DATE_FORMAT, model.dealTime));

		TextView tvDealBalance = ViewHolder.get(convertView, R.id.tv_deal_balance);
		tvDealBalance.setText(StringUtils.getCashNumber(String.valueOf(model.balance)));

		TextView tvDealMoney = ViewHolder.get(convertView, R.id.tv_deal_money);
		//tvDealMoney.setText(String.valueOf(model.dealMoney));

		//TextView tvDealDirection = ViewHolder.get(convertView, R.id.tv_direction_type);
		if (model.directionType == DealDirectionType.IN) {
			tvDealMoney.setText("+" + StringUtils.getCashNumber(String.valueOf(model.dealMoney)));
			tvDealMoney.setTextColor(mContext.getResources().getColor(R.color.green_deal));
		} else {
			tvDealMoney.setText("-" + StringUtils.getCashNumber(String.valueOf(model.dealMoney)));
			tvDealMoney.setTextColor(mContext.getResources().getColor(R.color.orange));
		}

		return convertView;
	}

}
