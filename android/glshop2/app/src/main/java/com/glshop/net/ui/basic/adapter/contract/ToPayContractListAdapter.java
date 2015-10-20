package com.glshop.net.ui.basic.adapter.contract;

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
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.mycontract.ContractInfoActivityV2;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.contract.data.model.ToPayContractInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的待付货款合同列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class ToPayContractListAdapter extends BasicAdapter<ToPayContractInfoModel> implements OnClickListener {

	public ToPayContractListAdapter(Context context, List<ToPayContractInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_to_pay_contract_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		ToPayContractInfoModel model = getItem(position);

		TextView mTvPayWaitTime = ViewHolder.get(convertView, R.id.tv_pay_wait_time);
		mTvPayWaitTime.setText(DateUtils.getWaitTime(model.payExpireTime));

		ViewHolder.get(convertView, R.id.iv_buy_type_icon).setVisibility(model.buyType == BuyType.BUYER ? View.VISIBLE : View.GONE);
		ViewHolder.get(convertView, R.id.iv_sell_type_icon).setVisibility(model.buyType == BuyType.SELLER ? View.VISIBLE : View.GONE);

		TextView mTvBuyProductSpec = ViewHolder.get(convertView, R.id.tv_buy_product_spec);
		mTvBuyProductSpec.setText(model.productName);

		TextView mTvUnitPrice = ViewHolder.get(convertView, R.id.tv_buy_unit_price);
		mTvUnitPrice.setText(StringUtils.getDefaultNumber(model.unitPrice));

		TextView mTvPriceUnit = ViewHolder.get(convertView, R.id.tv_price_unit);
		if (model.unitType == ProductUnitType.CUTE) {
			mTvPriceUnit.setText(mContext.getString(R.string.unit_price_cute_v1) + ", ");
		} else {
			mTvPriceUnit.setText(mContext.getString(R.string.unit_price_ton_v1) + ", ");
		}

		TextView mTvBuyAmount = ViewHolder.get(convertView, R.id.tv_buy_amount);
		mTvBuyAmount.setText(StringUtils.getDefaultNumber(model.amount));

		TextView mTvAmountUnit = ViewHolder.get(convertView, R.id.tv_amount_unit);
		if (model.unitType == ProductUnitType.CUTE) {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_cube_v1));
		} else {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_ton_v1));
		}

		TextView mTvToPayMoney = ViewHolder.get(convertView, R.id.tv_to_pay_money);
		mTvToPayMoney.setText(StringUtils.getCashNumber(model.toPayMoney));

		Button mBtnViewBuyInfo = ViewHolder.get(convertView, R.id.btn_to_pay_now);
		mBtnViewBuyInfo.setTag(position);
		mBtnViewBuyInfo.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			ToPayContractInfoModel contractInfo = getItem(position);
			Intent intent = new Intent(mContext, ContractInfoActivityV2.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);
		}
	}

}
