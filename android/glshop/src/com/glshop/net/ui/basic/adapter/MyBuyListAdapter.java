package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ViewBuyInfoType;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.findbuy.BuyInfoActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;

/**
 * @Description : 我的供求列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class MyBuyListAdapter extends BasicAdapter<MyBuySummaryInfoModel> implements OnClickListener {

	public MyBuyListAdapter(Context context, List<MyBuySummaryInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_my_buy_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == 0) {
			llContainer.setPadding(padding, 1, padding, 0);
		} else if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		MyBuySummaryInfoModel model = getItem(position);

		TextView mTvBuyTitle = ViewHolder.get(convertView, R.id.tv_buy_title);
		mTvBuyTitle.setText(model.productName);

		ViewHolder.get(convertView, R.id.iv_buy_type_icon).setVisibility(model.buyType == BuyType.BUYER ? View.VISIBLE : View.GONE);
		ViewHolder.get(convertView, R.id.iv_sell_type_icon).setVisibility(model.buyType == BuyType.SELLER ? View.VISIBLE : View.GONE);

		TextView mTvUnitPrice = ViewHolder.get(convertView, R.id.tv_buy_unit_price);
		mTvUnitPrice.setText(String.valueOf(model.unitPrice));

		TextView mTvBuyAmount = ViewHolder.get(convertView, R.id.tv_buy_amount);
		mTvBuyAmount.setText(String.valueOf(model.tradeAmount));

		TextView mTvTradeArea = ViewHolder.get(convertView, R.id.tv_buy_trade_area);
		mTvTradeArea.setText(/*SysCfgUtils.getAreaName(mContext, model.tradeArea)*/model.tradeArea);

		TextView mTvTradeDate = ViewHolder.get(convertView, R.id.tv_buy_trade_date);
		mTvTradeDate.setText(DateUtils.getDefaultDate(model.tradeBeginDate));

		TextView mTvWatiTime = ViewHolder.get(convertView, R.id.tv_buy_wait_time);
		String waitTime = DateUtils.getWaitTime(model.tradeEndDate);
		mTvWatiTime.setText(waitTime);

		TextView mTvBuyStatus = ViewHolder.get(convertView, R.id.tv_contract_status_summary);
		mTvBuyStatus.setText(model.buyStatus == BuyStatus.VALID ? R.string.status_valid : R.string.status_invalid);

		Button mBtnViewBuyInfo = ViewHolder.get(convertView, R.id.btn_view_buy_info);
		mBtnViewBuyInfo.setTag(position);
		mBtnViewBuyInfo.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			MyBuySummaryInfoModel buyInfo = getItem(position);
			Intent intent = new Intent(mContext, BuyInfoActivity.class);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_ID, buyInfo.publishBuyId);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_VIEW_BUY_INFO_TYPE, ViewBuyInfoType.MYBUY.toValue());
			mContext.startActivity(intent);
		}
	}
}
