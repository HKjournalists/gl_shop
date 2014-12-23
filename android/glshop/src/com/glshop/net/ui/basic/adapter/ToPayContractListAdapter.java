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
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.mypurse.SelectRechargeTypeActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
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

		TextView mTvContractID = ViewHolder.get(convertView, R.id.tv_contract_id);
		mTvContractID.setText(model.contractId);

		TextView mTvWatiTime = ViewHolder.get(convertView, R.id.tv_contract_rest_time);
		String waitTime = DateUtils.getWaitTime(model.expireTime);
		//Logger.e("ContractListFragment", "Time = " + waitTime);
		mTvWatiTime.setText(waitTime);

		String summary = "";
		if (model.buyType == BuyType.BUYER) {
			summary = mContext.getString(R.string.buy) + model.sellCompanyName + model.productName;
		} else {
			summary = mContext.getString(R.string.sell) + model.sellCompanyName + model.productName;
		}
		TextView mTvContractTitle = ViewHolder.get(convertView, R.id.tv_contract_title);
		mTvContractTitle.setText(summary);

		TextView mTvContractAmount = ViewHolder.get(convertView, R.id.tv_contract_amount);
		mTvContractAmount.setText(model.amount);

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
			Intent intent = new Intent(mContext, SelectRechargeTypeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.PAYMENT.toValue());
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, Float.parseFloat(contractInfo.toPayMoney));
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);
		}
	}

}
