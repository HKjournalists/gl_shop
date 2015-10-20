package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.PurseInfoModel;

/**
 * @Description : 钱包余额详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseBalanceActivity extends BasicActivity {

	private TextView mTvRechargeTips;

	private PurseInfoModel purseInfo;

	private PurseType purseType = PurseType.DEPOSIT;
	
	private Button payBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_balance);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_purse);
		mTvRechargeTips = getView(R.id.tv_balance_tips);
		
		payBtn = (Button) findViewById(R.id.btn_pay_deposits);
		getView(R.id.btn_pay_deposits).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		purseInfo = (PurseInfoModel) getIntent().getSerializableExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_INFO);
		if (purseType == PurseType.DEPOSIT) {
			if (purseInfo.depositBalance <= 0) {
				mTvRechargeTips.setText(R.string.pay_deposits_tips_empty);
			} else {
				mTvRechargeTips.setText(R.string.pay_deposits_tips_not_enough);
			}
		} else {
			payBtn.setText(getString(R.string.pay_payment_pay));
			mTvRechargeTips.setText(R.string.pay_payment_tips_empty);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pay_deposits:
			Intent intent = new Intent(this, PurseRechargeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			startActivity(intent);
			finish();
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
