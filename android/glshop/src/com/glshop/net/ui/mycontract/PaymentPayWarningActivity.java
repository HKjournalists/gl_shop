package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 保证金或货款账户支付余额不足提示界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PaymentPayWarningActivity extends BaseContractInfoActivity {

	private TextView mTvToPayWarningContent;
	private TextView mTvToPayWarningHint;

	private PurseType purseType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_pay_warning);
		initView();
		initData();
	}

	private void initView() {
		//((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_payment);

		mTvToPayWarningContent = getView(R.id.tv_pay_money_warning);
		mTvToPayWarningHint = getView(R.id.tv_pay_money_warning_hint);

		getView(R.id.btn_recharge).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		double needToRecharge = getIntent().getDoubleExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, 0);
		String recharge = StringUtils.getDefaultNumber(needToRecharge);
		if (purseType == PurseType.DEPOSIT) {
			((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
			((TextView) getView(R.id.btn_recharge)).setText(R.string.recharge_deposit);
			getView(R.id.ll_security_tips).setVisibility(View.GONE);

			String tips = getString(R.string.contract_sign_warning_content, recharge);
			String highlightTips = recharge;
			mTvToPayWarningContent.setText(StringUtils.getHighlightText(tips, highlightTips, getResources().getColor(R.color.red)));
		} else {
			((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_payment);
			((TextView) getView(R.id.btn_recharge)).setText(R.string.recharge_payment);

			String tips = getString(R.string.contract_payment_pay_warning_content, recharge);
			String highlightTips = recharge;
			mTvToPayWarningContent.setText(StringUtils.getHighlightText(tips, highlightTips, getResources().getColor(R.color.red)));
			if (mContractInfo != null) {
				mTvToPayWarningHint.setText(getString(R.string.contract_payment_pay_warning_hint, DateUtils.getWaitTime(mContractInfo.payExpireTime)));
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_recharge:
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

}
