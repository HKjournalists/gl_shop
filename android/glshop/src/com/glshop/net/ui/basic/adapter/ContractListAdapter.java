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
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.ui.mycontract.ContractInfoActivity;
import com.glshop.net.ui.mycontract.UfmContractInfoActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;

/**
 * @Description : 我的合同列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class ContractListAdapter extends BasicAdapter<ContractSummaryInfoModel> implements OnClickListener {

	private ContractType type = ContractType.UNCONFIRMED;

	public ContractListAdapter(Context context, List<ContractSummaryInfoModel> list) {
		super(context, list);
	}

	public ContractListAdapter(Context context, List<ContractSummaryInfoModel> list, ContractType type) {
		super(context, list);
		this.type = type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_contract_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		ContractSummaryInfoModel model = getItem(position);

		TextView mTvContractID = ViewHolder.get(convertView, R.id.tv_contract_id);
		mTvContractID.setText(model.contractId);

		TextView mTvContractStartTime = ViewHolder.get(convertView, R.id.tv_contract_create_time);
		mTvContractStartTime.setText(DateUtils.getDefaultDate(model.createTime));

		ViewHolder.get(convertView, R.id.iv_buy_type_icon).setVisibility(model.buyType == BuyType.BUYER ? View.VISIBLE : View.GONE);
		ViewHolder.get(convertView, R.id.iv_sell_type_icon).setVisibility(model.buyType == BuyType.SELLER ? View.VISIBLE : View.GONE);

		TextView mTvContractTitle = ViewHolder.get(convertView, R.id.tv_contract_title);
		String summary = "";
		if (model.buyType == BuyType.BUYER) {
			summary = model.sellCompanyName + model.productName;
		} else {
			summary = "给" + model.sellCompanyName + model.productName;
		}
		mTvContractTitle.setText(summary);

		TextView mTvContractAmount = ViewHolder.get(convertView, R.id.tv_contract_amount);
		mTvContractAmount.setText(model.amount);

		TextView mTvWatiTime = ViewHolder.get(convertView, R.id.tv_contract_rest_time);
		String waitTime = DateUtils.getWaitTime(model.expireTime);
		//Logger.e("ContractListFragment", "Time = " + waitTime);
		mTvWatiTime.setText(waitTime);

		if (type == ContractType.UNCONFIRMED) {
			ViewHolder.get(convertView, R.id.tv_contract_status_title).setVisibility(View.GONE);
			ViewHolder.get(convertView, R.id.tv_contract_status_summary).setVisibility(View.GONE);
		} else if (type == ContractType.ONGOING) {
			updateStatus((TextView) ViewHolder.get(convertView, R.id.tv_contract_status_summary), model);
		} else if (type == ContractType.ENDED) {
			ViewHolder.get(convertView, R.id.ll_contract_rest_time).setVisibility(View.GONE);
			((TextView) ViewHolder.get(convertView, R.id.tv_contract_status_summary)).setText(mContext.getString(R.string.contract_status_end));
		}

		Button mBtnViewBuyInfo = ViewHolder.get(convertView, R.id.btn_contract_status_detail);
		mBtnViewBuyInfo.setTag(position);
		mBtnViewBuyInfo.setOnClickListener(this);

		return convertView;
	}

	private void updateStatus(TextView view, ContractSummaryInfoModel info) {
		String status = "冻结保证金";
		if (info.buyType == BuyType.BUYER) {
			switch (info.lifeCycle) {
			case SINGED:
			case IN_THE_PAYMENT:
				status = "向平台付款";
				break;
			case PAYED_FUNDS:
				status = "已付款";
				break;
			case SIMPLE_CHECKING:
				status = "抽样验收中";
				break;
			case SIMPLE_CHECKED:
				status = "抽样验收通过";
				break;
			case FULL_TAKEOVERING:
				status = "全量验收中";
				break;
			case FULL_TAKEOVERED:
				status = "全量验收通过";
				break;
			case UNINSTALLED_GOODS:
				status = "已经卸货";
				break;
			case RECEIVED_GOODS:
				status = "已经收货";
				break;
			default:
				status = "未知";
				break;
			}
		} else {
			switch (info.lifeCycle) {
			case SINGED:
			case IN_THE_PAYMENT:
			case PAYED_FUNDS:
			case SENT_GOODS:
				status = "按时送货";
				break;
			case SIMPLE_CHECKING:
				status = "抽样验收中";
				break;
			case SIMPLE_CHECKED:
				status = "抽样验收通过";
				break;
			case FULL_TAKEOVERING:
				status = "全量验收中";
				break;
			case FULL_TAKEOVERED:
				status = "全量验收通过";
				break;
			case UNINSTALLED_GOODS:
				status = "已经卸货";
				break;
			default:
				status = "未知";
				break;
			}
		}
		view.setText(status);
	}

	@Override
	public void onClick(View v) {
		int position = ((Integer) v.getTag()).intValue();
		if (position != -1) {
			ContractSummaryInfoModel contractInfo = getItem(position);
			Intent intent = null;
			switch (type) {
			case UNCONFIRMED:
				intent = new Intent(mContext, UfmContractInfoActivity.class);
				break;
			case ONGOING:
			case ENDED:
				intent = new Intent(mContext, ContractInfoActivity.class);
				break;
			default:
				intent = new Intent(mContext, ContractInfoActivity.class);
				break;
			}
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
			mContext.startActivity(intent);

		}
	}

}
