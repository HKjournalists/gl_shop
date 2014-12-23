package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.ui.basic.view.ListItemView;
import com.glshop.net.ui.mycontract.BaseContractInfoActivity;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 选择充值或支付方式界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class SelectRechargeTypeActivity extends BaseContractInfoActivity {

	private TextView mTvTitle;
	private TextView mTvPayMoneyType;
	private TextView mTvPayMoney;
	private TextView mTvPayType;

	private ListItemView mItemOnline;
	private ListItemView mItemBank;
	private ListItemView mItemOffline;

	private float needPayMoney;
	private PurseType purseType = PurseType.DEPOSIT;
	private PursePayType payType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_select_recharge_type);
		initView();
		initData();
	}

	private void initView() {
		mTvTitle = getView(R.id.tv_commmon_title);
		mTvPayMoneyType = getView(R.id.tv_to_pay_money_type);
		mTvPayMoney = getView(R.id.tv_to_pay_money);
		mTvPayType = getView(R.id.tv_select_recharge_type);
		mItemOnline = getView(R.id.ll_recharge_online);
		mItemBank = getView(R.id.ll_recharge_bank);
		mItemOffline = getView(R.id.ll_recharge_offline);

		getView(R.id.iv_common_back).setOnClickListener(this);

		mItemOnline.setOnClickListener(this);
		mItemBank.setOnClickListener(this);
		mItemOffline.setOnClickListener(this);
	}

	private void initData() {
		needPayMoney = getIntent().getFloatExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, 0);
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		payType = PursePayType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.RECHARGE.toValue()));
		switch (payType) {
		case PAYMENT:
			mTvTitle.setText(R.string.purse_payment);
			mItemOnline.setTitle(getString(R.string.payment_type_online));
			mTvPayMoneyType.setText(getString(R.string.purse_to_pay_payment));
			mTvPayMoney.setText(StringUtils.getCashNumber(String.valueOf(needPayMoney)));
			break;
		case RECHARGE:
			mTvTitle.setText(R.string.purse_recharge);
			mItemOnline.setTitle(getString(R.string.recharge_type_online));
			mTvPayMoneyType.setText(getString(R.string.purse_to_recharge));
			mTvPayMoney.setText(StringUtils.getCashNumber(String.valueOf(needPayMoney)));
			break;
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_recharge_online:
			switch (payType) {
			case PAYMENT:
				// 货款账户支付
				intent = new Intent(this, PaymentSubmitActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
				startActivity(intent);
				break;
			case RECHARGE:
				// TODO 调用网银API充值
				break;
			}
			break;

		case R.id.ll_recharge_bank:
			intent = new Intent(this, RechargeByBankActivity.class);
			if (payType == PursePayType.PAYMENT) {
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			} else {
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			}
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, payType.toValue());
			startActivity(intent);
			break;

		case R.id.ll_recharge_offline:
			intent = new Intent(this, RechargeByOfflineActivity.class);
			if (payType == PursePayType.PAYMENT) {
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			} else {
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			}
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, payType.toValue());
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
