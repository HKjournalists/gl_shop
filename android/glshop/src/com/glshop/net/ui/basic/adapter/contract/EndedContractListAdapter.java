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
import com.glshop.net.ui.mycontract.ContractInfoActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractOprType;
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
public class EndedContractListAdapter extends BasicAdapter<ContractSummaryInfoModel> implements OnClickListener {

	public EndedContractListAdapter(Context context, List<ContractSummaryInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_ended_contract_list_item, null);
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

		if (model.buyType == BuyType.BUYER) {
			ViewHolder.get(convertView, R.id.iv_buy_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
		} else {
			ViewHolder.get(convertView, R.id.iv_sell_type_icon).setBackgroundResource(R.drawable.btn_bg_disable);
		}
		mTvUnitPrice.setTextColor(mContext.getResources().getColor(R.color.black));
		mTvBuyAmount.setTextColor(mContext.getResources().getColor(R.color.black));

		ImageView mIvViewContractInfo = ViewHolder.get(convertView, R.id.iv_view_info);
		mIvViewContractInfo.setTag(position);
		mIvViewContractInfo.setOnClickListener(this);

		updateStatus(model, convertView);

		return convertView;
	}

	private void updateStatus(ContractSummaryInfoModel info, View statusView) {
		TextView mTvStatusDetail = ViewHolder.get(statusView, R.id.tv_contract_status_detail);
		TextView mTvNextStatus = ViewHolder.get(statusView, R.id.tv_ended_contract_next_status);
		String statusDetail = "";
		String nextStatus = "";

		if (info.buyType == BuyType.BUYER) {
			switch (info.lifeCycle) {
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ended_contract_buyer_invalid_1);
				nextStatus = mContext.getString(R.string.ended_contract_buyer_action_1);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				if (isMimeCanceled(info)) {
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
				if (isMimeDoneEva(info)) {
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
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_4);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				if (isMimeCanceled(info)) {
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
				if (isMimeDoneEva(info)) {
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
	}

	private boolean isMimeDoneEva(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER) {
			if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
				return true;
			}
		} else if (info.buyType == BuyType.SELLER) {
			if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
				return true;
			}
		}
		return false;
	}

	private boolean isMimeCanceled(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER) {
			if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.CANCEL_CONTRACT) {
				return true;
			}
		} else if (info.buyType == BuyType.SELLER) {
			if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.CANCEL_CONTRACT) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			ContractSummaryInfoModel contractInfo = getItem(position);
			Intent intent = new Intent(mContext, ContractInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);

		}
	}

}
