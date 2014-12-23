package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.mycontract.ContractBillDetailActivity;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;

/**
 * @Description : 第二次议价列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class SecondNegotiateAdapter extends BasicAdapter<NegotiateInfoModel> {

	private ContractInfoModel mContractInfo;

	public SecondNegotiateAdapter(Context context, List<NegotiateInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NegotiateInfoModel model = getItem(position);
		convertView = View.inflate(mContext, model.isMine ? R.layout.layout_second_negotiate_item_mine : R.layout.layout_second_negotiate_item_another, null);

		TextView tvDatetime = (TextView) convertView.findViewById(R.id.negotiate_date);
		tvDatetime.setText(model.oprTime);

		TextView tvNegUnitPrice = (TextView) convertView.findViewById(R.id.tv_neg_unit_price);
		tvNegUnitPrice.setText(String.valueOf(model.negUnitPrice));

		TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_final_amount);
		tvAmount.setText(String.valueOf(model.negAmount));

		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.tv_total_price);
		tvTotalPrice.setText(String.valueOf(model.unitPrice * model.tradeAmount));

		TextView tvNegTotalPrice = (TextView) convertView.findViewById(R.id.tv_final_total_price);
		tvNegTotalPrice.setText(String.valueOf(model.negUnitPrice * model.negAmount));

		convertView.findViewById(R.id.btn_bill_info_detail).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ContractBillDetailActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_BILL_INFO, model);
				mContext.startActivity(intent);
			}

		});

		return convertView;
	}

	public void setContractInfo(ContractInfoModel info) {
		mContractInfo = info;
	}

}
