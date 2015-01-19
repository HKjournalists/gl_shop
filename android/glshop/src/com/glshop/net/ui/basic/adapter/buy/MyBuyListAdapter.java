package com.glshop.net.ui.basic.adapter.buy;

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
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.findbuy.BuyInfoActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
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
		boolean isValide = model.buyStatus == BuyStatus.VALID;

		TextView mTvBeginTradeDate = ViewHolder.get(convertView, R.id.tv_buy_begin_trade_date);
		mTvBeginTradeDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.TRADE_DATE_FORMAT_V2, model.tradeBeginDate));

		TextView mTvEndTradeDate = ViewHolder.get(convertView, R.id.tv_buy_end_trade_date);
		mTvEndTradeDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.TRADE_DATE_FORMAT_V2, model.tradeEndDate));

		ViewHolder.get(convertView, R.id.iv_buy_type_icon).setVisibility(model.buyType == BuyType.BUYER ? View.VISIBLE : View.GONE);
		ViewHolder.get(convertView, R.id.iv_sell_type_icon).setVisibility(model.buyType == BuyType.SELLER ? View.VISIBLE : View.GONE);

		TextView mTvBuyProductSpec = ViewHolder.get(convertView, R.id.tv_buy_product_spec);
		mTvBuyProductSpec.setText(model.productName);

		TextView mTvUnitPrice = ViewHolder.get(convertView, R.id.tv_buy_unit_price);
		mTvUnitPrice.setText(String.valueOf(model.unitPrice));

		TextView mTvPriceUnit = ViewHolder.get(convertView, R.id.tv_price_unit);
		if (model.unitType == ProductUnitType.CUTE) {
			mTvPriceUnit.setText(mContext.getString(R.string.unit_price_cute_v1) + ", ");
		} else {
			mTvPriceUnit.setText(mContext.getString(R.string.unit_price_ton_v1) + ", ");
		}

		TextView mTvBuyAmount = ViewHolder.get(convertView, R.id.tv_buy_amount);
		mTvBuyAmount.setText(String.valueOf(model.tradeAmount));

		TextView mTvAmountUnit = ViewHolder.get(convertView, R.id.tv_amount_unit);
		if (model.unitType == ProductUnitType.CUTE) {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_cube_v1));
		} else {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_ton_v1));
		}

		TextView mTvTradeArea = ViewHolder.get(convertView, R.id.tv_buy_trade_area);
		mTvTradeArea.setText(model.tradeAreaName);

		TextView mTvTradePubDate = ViewHolder.get(convertView, R.id.tv_buy_pub_datetime);
		mTvTradePubDate.setText(DateUtils.getDefaultDate(model.tradePubDate));

		TextView mTvBuyStatus = ViewHolder.get(convertView, R.id.tv_contract_status_summary);
		mTvBuyStatus.setText(isValide ? R.string.status_valid : R.string.status_invalid);

		Button mBtnViewBuyInfo = ViewHolder.get(convertView, R.id.btn_view_buy_info);
		mBtnViewBuyInfo.setTag(position);
		mBtnViewBuyInfo.setOnClickListener(this);

		if (isValide) {
			if (model.buyType == BuyType.BUYER) {
				ViewHolder.get(convertView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_green_bg_normal);
			} else {
				ViewHolder.get(convertView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_orange_bg_normal);
			}
			mTvBeginTradeDate.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvEndTradeDate.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.blue));
		} else {
			if (model.buyType == BuyType.BUYER) {
				ViewHolder.get(convertView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
			} else {
				ViewHolder.get(convertView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
			}
			mTvBeginTradeDate.setTextColor(mContext.getResources().getColor(R.color.gray));
			mTvEndTradeDate.setTextColor(mContext.getResources().getColor(R.color.gray));
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.black));
		}

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
