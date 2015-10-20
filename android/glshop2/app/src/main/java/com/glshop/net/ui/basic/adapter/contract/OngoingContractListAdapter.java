package com.glshop.net.ui.basic.adapter.contract;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.mycontract.ContractInfoActivityV2;
import com.glshop.net.utils.ContractUtil;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的合同列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class OngoingContractListAdapter extends BasicAdapter<ContractSummaryInfoModel> implements OnClickListener {

	public OngoingContractListAdapter(Context context, List<ContractSummaryInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_ongoing_contract_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		ContractSummaryInfoModel model = getItem(position);

		TextView mTvEndDateTime = ViewHolder.get(convertView, R.id.tv_contract_end_datetime);
		mTvEndDateTime.setText(DateUtils.getDefaultDate(model.expireTime));

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

		ImageView mIvViewContractInfo = ViewHolder.get(convertView, R.id.iv_view_info);
		mIvViewContractInfo.setTag(position);
		mIvViewContractInfo.setOnClickListener(this);

		updateStatus(model, convertView);

		return convertView;
	}

	private void updateStatus(ContractSummaryInfoModel info, View statusView) {
		TextView mTvDateTimeTitle = ViewHolder.get(statusView, R.id.tv_contract_datetime_title);
		TextView mTvEndDateTime = ViewHolder.get(statusView, R.id.tv_contract_end_datetime);
		TextView mTvStatusCategory = ViewHolder.get(statusView, R.id.tv_contract_status_category);
		TextView mTvStatusDetail = ViewHolder.get(statusView, R.id.tv_contract_status_detail);
		TextView mTvNextStatus = ViewHolder.get(statusView, R.id.tv_ongoing_contract_next_status);
		String statusDetail = "";
		String nextStatus = "";

		if (info.buyType == BuyType.BUYER) {
			switch (info.lifeCycle) {
			case SINGED: // 已签订
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_0);
				break;
			case PAYED_FUNDS: // 已付款
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_1);
				break;
			case CONFIRMING_GOODS_FUNDS: // 货款确认中
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_2);
				break;
			case ARBITRATING: // 仲裁中 
				if (ContractUtil.isMineApplyArbitrate(info)) {
					statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_5);
				} else {
					statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_6);
				}
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_6);
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_3);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				if (ContractUtil.isMineCanceled(info)) {
					statusDetail = mContext.getString(R.string.ended_contract_buyer_invalid_2);
					nextStatus = mContext.getString(R.string.ended_contract_buyer_action_1);
				} else {
					statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_2);
					nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_4);
				}
				break;
			case ARBITRATED: // 仲裁结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case NORMAL_FINISHED: // 正常结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_4);
				if (ContractUtil.isMineDoneEva(info)) {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_8);
				} else {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_4);
				}
				break;
			default:
				// TODO
				break;
			}
		} else if (info.buyType == BuyType.SELLER) {
			switch (info.lifeCycle) {
			case SINGED: // 已签订
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_1);
				break;
			case PAYED_FUNDS: // 已付款
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_2);
				break;
			case CONFIRMING_GOODS_FUNDS: // 货款确认中
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_3);
				break;
			case ARBITRATING: // 仲裁中 
				if (ContractUtil.isMineApplyArbitrate(info)) {
					statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_6);
				} else {
					statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_5);
				}
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_6);
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_4);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				if (ContractUtil.isMineCanceled(info)) {
					statusDetail = mContext.getString(R.string.ended_contract_buyer_invalid_2);
					nextStatus = mContext.getString(R.string.ended_contract_buyer_action_1);
				} else {
					statusDetail = mContext.getString(R.string.ended_contract_seller_invalid_2);
					nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_4);
				}
				break;
			case ARBITRATED: // 仲裁结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case NORMAL_FINISHED: // 正常结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_4);
				if (ContractUtil.isMineDoneEva(info)) {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_8);
				} else {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_4);
				}
				break;
			default:
				// TODO
				break;
			}
		}

		mTvStatusDetail.setText(statusDetail);
		mTvNextStatus.setText(nextStatus);

		if (ContractUtil.isValid(info)) {
			mTvDateTimeTitle.setText(mContext.getString(R.string.contract_delivery_datetime));
			mTvStatusCategory.setText(mContext.getString(R.string.status_valid) + ", ");
		} else {
			//mTvDateTimeTitle.setText(mContext.getString(R.string.contract_end_datetime));
			//mTvEndDateTime.setText(DateUtils.getDefaultDate(info.updateTime));
			mTvStatusCategory.setText(mContext.getString(R.string.status_invalid) + ", ");
			if (ContractUtil.isAutoMoveMode(info)) {
				mTvDateTimeTitle.setText("");
				mTvEndDateTime.setText(mContext.getString(R.string.contract_move_watitime, DateUtils.getContractMoveWaitTime(info.expireTime)));
			} else {
				mTvDateTimeTitle.setText(mContext.getString(R.string.contract_end_datetime));
				mTvEndDateTime.setText(DateUtils.getDefaultDate(info.updateTime));
			}
		}

		// 无效置灰
		TextView mTvUnitPrice = ViewHolder.get(statusView, R.id.tv_buy_unit_price);
		TextView mTvBuyAmount = ViewHolder.get(statusView, R.id.tv_buy_amount);
		TextView mTvBuyAmountUnit = ViewHolder.get(statusView, R.id.tv_price_unit);
		TextView mTvUnitPriceUnit = ViewHolder.get(statusView, R.id.tv_amount_unit);
		if (ContractUtil.isValid(info)) {
//			if (info.buyType == BuyType.BUYER) {
//				ViewHolder.get(statusView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_green_bg_normal);
//			} else {
//				ViewHolder.get(statusView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_orange_bg_normal);
//			}
			mTvEndDateTime.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvStatusDetail.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvNextStatus.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvBuyAmountUnit.setTextColor(mContext.getResources().getColor(R.color.red));
			mTvUnitPriceUnit.setTextColor(mContext.getResources().getColor(R.color.red));
		} else {
//			if (info.buyType == BuyType.BUYER) {
//				ViewHolder.get(statusView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
//			} else {
//				ViewHolder.get(statusView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
//			}
			mTvEndDateTime.setTextColor(mContext.getResources().getColor(R.color.gray));
			mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvStatusDetail.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvNextStatus.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvBuyAmountUnit.setTextColor(mContext.getResources().getColor(R.color.black));
			mTvUnitPriceUnit.setTextColor(mContext.getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			ContractSummaryInfoModel contractInfo = getItem(position);
			Intent intent = new Intent(mContext, ContractInfoActivityV2.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);
		}
	}

}
