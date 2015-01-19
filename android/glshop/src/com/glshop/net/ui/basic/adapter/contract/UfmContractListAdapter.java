package com.glshop.net.ui.basic.adapter.contract;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.mycontract.UfmContractInfoActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractDraftStatus;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;

/**
 * @Description : 我的合同列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class UfmContractListAdapter extends BasicAdapter<ContractSummaryInfoModel> implements OnClickListener {

	public UfmContractListAdapter(Context context, List<ContractSummaryInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_ufm_contract_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		ContractSummaryInfoModel model = getItem(position);

		// 起草倒计时
		TextView mTvWaitTime = ViewHolder.get(convertView, R.id.tv_contract_limit_time);
		if (model.lifeCycle == ContractLifeCycle.TIMEOUT_FINISHED || model.lifeCycle == ContractLifeCycle.DRAFTING_CANCEL) { // 起草超时结束
			mTvWaitTime.setText("3小时有效期限已失效");
			mTvWaitTime.setTextColor(mContext.getResources().getColor(R.color.gray));
		} else {
			mTvWaitTime.setText(DateUtils.getWaitTime(model.draftExpireTime));
			mTvWaitTime.setTextColor(mContext.getResources().getColor(R.color.red));
		}

		// 货物信息
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
		mTvBuyAmount.setText(String.valueOf(model.amount));

		TextView mTvAmountUnit = ViewHolder.get(convertView, R.id.tv_amount_unit);
		if (model.unitType == ProductUnitType.CUTE) {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_cube_v1));
		} else {
			mTvAmountUnit.setText(mContext.getString(R.string.unit_ton_v1));
		}

		Button mBtnViewContractInfo = ViewHolder.get(convertView, R.id.btn_confirm);
		mBtnViewContractInfo.setTag(position);
		mBtnViewContractInfo.setOnClickListener(this);

		ImageView mIvViewContractInfo = ViewHolder.get(convertView, R.id.iv_view_info);
		mIvViewContractInfo.setTag(position);
		mIvViewContractInfo.setOnClickListener(this);

		// 起草状态
		TextView mTvStatusCategory = ViewHolder.get(convertView, R.id.tv_contract_status_category);
		TextView mTvStatusDetail = ViewHolder.get(convertView, R.id.tv_contract_status_detail);
		if (model.lifeCycle == ContractLifeCycle.TIMEOUT_FINISHED) { // 起草超时结束
			mTvStatusCategory.setText(mContext.getString(R.string.status_invalid) + ",");
			mTvStatusDetail.setText(mContext.getString(R.string.ufm_contract_invalide_1));
			mBtnViewContractInfo.setVisibility(View.GONE);
			mIvViewContractInfo.setVisibility(View.VISIBLE);
		} else if (model.lifeCycle == ContractLifeCycle.DRAFTING_CANCEL) { // 起草取消结束
			mTvStatusCategory.setText(mContext.getString(R.string.status_invalid) + ",");
			mTvStatusDetail.setText(mContext.getString(R.string.ufm_contract_invalide_2));
			mBtnViewContractInfo.setVisibility(View.GONE);
			mIvViewContractInfo.setVisibility(View.VISIBLE);
		} else {
			mTvStatusCategory.setText(mContext.getString(R.string.status_valid) + ",");
			mTvStatusDetail.setText(getDraftStatus(model));
		}

		if (model.lifeCycle == ContractLifeCycle.TIMEOUT_FINISHED || model.lifeCycle == ContractLifeCycle.DRAFTING_CANCEL) { // 起草超时结束
			if (model.buyType == BuyType.BUYER) {
				ViewHolder.get(convertView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
			} else {
				ViewHolder.get(convertView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
			}
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvStatusDetail.setTextColor(mContext.getResources().getColor(R.color.black));

			mBtnViewContractInfo.setVisibility(View.GONE);
			mIvViewContractInfo.setVisibility(View.VISIBLE);
		} else {
			if (model.buyType == BuyType.BUYER) {
				ViewHolder.get(convertView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_green_bg_normal);
			} else {
				ViewHolder.get(convertView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_orange_bg_normal);
			}
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.blue));
			mTvStatusDetail.setTextColor(mContext.getResources().getColor(R.color.red));

			if (isNeedToConfirm(model)) {
				mBtnViewContractInfo.setVisibility(View.VISIBLE);
				mIvViewContractInfo.setVisibility(View.GONE);
			} else {
				mBtnViewContractInfo.setVisibility(View.GONE);
				mIvViewContractInfo.setVisibility(View.VISIBLE);
			}
		}

		return convertView;
	}

	private String getDraftStatus(ContractSummaryInfoModel info) {
		String status = "";
		ContractDraftStatus myStatus;
		ContractDraftStatus otherStatus;
		if (info.buyType == BuyType.BUYER) {
			myStatus = info.buyerDraftStatus;
			otherStatus = info.sellerDraftStatus;
		} else {
			myStatus = info.sellerDraftStatus;
			otherStatus = info.buyerDraftStatus;
		}
		if (myStatus == ContractDraftStatus.NOTHING && otherStatus == ContractDraftStatus.NOTHING) {
			status = mContext.getString(R.string.ufm_contract_valide_1);
		} else if (myStatus == ContractDraftStatus.NOTHING && otherStatus == ContractDraftStatus.CONFIRMED) {
			status = mContext.getString(R.string.ufm_contract_valide_2);
		} else if (myStatus == ContractDraftStatus.CONFIRMED && otherStatus == ContractDraftStatus.NOTHING) {
			status = mContext.getString(R.string.ufm_contract_valide_3);
		}
		return status;
	}

	private boolean isNeedToConfirm(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER && info.buyerDraftStatus == ContractDraftStatus.NOTHING) {
			return true;
		} else if (info.buyType == BuyType.SELLER && info.sellerDraftStatus == ContractDraftStatus.NOTHING) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			ContractSummaryInfoModel contractInfo = getItem(position);
			Intent intent = new Intent(mContext, UfmContractInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);
		}
	}

}
