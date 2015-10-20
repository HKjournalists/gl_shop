package com.glshop.net.ui.mycontract;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.utils.StringUtils;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 结算账单详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractBillDetailActivity extends BasicActivity {

	private TextView mTvCountDownTime;
	private TextView mTvContractStatusTips;
	private TextView mTvRemarks;

	private ContractInfoModel contractInfo;
	private NegotiateInfoModel billInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_bill_detail);
		initView();
		initData();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.my_contract);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		mTvContractStatusTips = getView(R.id.tv_contract_status_info);
		mTvRemarks = getView(R.id.tv_neg_reason);

		findViewById(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		contractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		billInfo = (NegotiateInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_BILL_INFO);

		if (contractInfo != null) {
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(contractInfo.expireTime)));
			double preNegUnitPrice = 0;
			if (BeanUtils.isNotEmpty(contractInfo.firstNegotiateList)) {
				NegotiateInfoModel preNegInfo = contractInfo.firstNegotiateList.get(0);
				if (preNegInfo != null) {
					preNegUnitPrice = preNegInfo.negUnitPrice;
				}
			}

			if (preNegUnitPrice == 0) {
				preNegUnitPrice = contractInfo.unitPrice;
			}

			((BuyTextItemView) getView(R.id.tv_trade_amount)).setContentText(String.valueOf(contractInfo.tradeAmount)); // 合同货物总量
			((BuyTextItemView) getView(R.id.tv_unit_price)).setContentText(String.valueOf(contractInfo.unitPrice)); // 合同原始单价
			((BuyTextItemView) getView(R.id.tv_simple_check_unit_price)).setContentText(String.valueOf(preNegUnitPrice)); // 抽样检查单价
			((TextView) getView(R.id.tv_total_price)).setText(String.valueOf(contractInfo.finalPayMoney)); // 合同总价
		}

		if (billInfo != null) {
			String statusInfo = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, billInfo.oprTime);
			mTvContractStatusTips.setText(statusInfo + (billInfo.isMine ? "我的意见" : "对方的意见"));
			mTvRemarks.setText(StringUtils.isNotEmpty(billInfo.reason) ? billInfo.reason : getString(R.string.data_empty)); // 原因
			((BuyTextItemView) getView(R.id.item_neg_trade_amount)).setContentText(String.valueOf(billInfo.negAmount)); // 实际总卸货量
			((TextView) getView(R.id.tv_neg_amount)).setText(String.valueOf(billInfo.negAmount)); // 实际总卸货量
			((BuyTextItemView) getView(R.id.item_neg_unit_price)).setContentText(String.valueOf(billInfo.negUnitPrice)); // 结算单价
			((TextView) getView(R.id.tv_neg_unit_price)).setText(String.valueOf(billInfo.negUnitPrice)); // 结算单价
			((TextView) getView(R.id.tv_neg_total_price)).setText(String.valueOf(billInfo.negUnitPrice * billInfo.negAmount)); // 最终价格
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}
}
