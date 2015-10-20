package com.glshop.net.ui.mypurse;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.ProtocolConstants;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.browser.BrowseProtocolActivity;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 钱包充值(包括保证金和货款充值)界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseRechargeActivity extends BasicActivity {

	private LinearLayout llCompanyRecharge;
	private CheckedTextView mCkbTvAgreeProtocol;

	private TextView mTvCompanyDeposit;
	private TextView protollText;
	private EditText mDepostNum;

	private MenuDialog menuRechargeType;

	private PurseType purseType = PurseType.DEPOSIT;
	private ProfileType profileType;
	private boolean isAuther = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_recharge);
		initView();
		initData();
	}

	private void initView() {
		llCompanyRecharge = getView(R.id.ll_company_recharge);
		mTvCompanyDeposit = getView(R.id.tv_recharge_money);
		mCkbTvAgreeProtocol = getView(R.id.chkTv_agree_protocol);
		mDepostNum = getView(R.id.et_deposit_num);

		setTextWatcher(mDepostNum);
		
		
		protollText = (TextView) findViewById(R.id.tv_protocol_detail);
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_recharge);
		getView(R.id.ll_agree_protocol).setOnClickListener(this);
		getView(R.id.tv_protocol_detail).setOnClickListener(this);
		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		profileType = GlobalConfig.getInstance().getProfileType();
		if (profileType == null) {
			//profileType = ProfileType.COMPANY;
		} else {
			isAuther = true;
		}

		if (purseType == PurseType.DEPOSIT) {
			mTvCompanyDeposit.setText(StringUtils.getCashNumber(String.valueOf(GlobalConfig.getInstance().getDepositBalance())));
			((TextView) getView(R.id.tv_recharge_tips)).setText(R.string.deposit_recharge_security_tips);
		} else {
			mTvCompanyDeposit.setText(StringUtils.getCashNumber(String.valueOf(GlobalConfig.getInstance().getPaymentBalance())));
			findViewById(R.id.tv_rechange_tiltx).setVisibility(View.GONE);
			((TextView) getView(R.id.tv_recharge_tips)).setText(R.string.payment_recharge_security_tips);
			protollText.setText(R.string.pay_protocol_url);
		}


		findViewById(R.id.et_deposit_num_layout).setVisibility(View.VISIBLE);

		if (purseType == PurseType.DEPOSIT) {
			((TextView) getView(R.id.et_deposit_label)).setText(Html.fromHtml(getString(R.string.recharge_deposit_type)));
		} else {
			((TextView) getView(R.id.et_deposit_label)).setText(Html.fromHtml(getString(R.string.recharge_payment_type)));
			mDepostNum.setText("");
		}

	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.ll_agree_protocol:
			mCkbTvAgreeProtocol.toggle();
			getView(R.id.btn_next_step).setEnabled(mCkbTvAgreeProtocol.isChecked());
			break;
		case R.id.tv_protocol_detail:
			intent = new Intent(this, BrowseProtocolActivity.class);
			if (purseType == PurseType.DEPOSIT) {
				intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, getString(R.string.recharge_protocol_url));
				intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, ProtocolConstants.RECHARGE_PROTOCOL_URL);
			}else{
				intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, getString(R.string.pay_protocol_url));
				intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, ProtocolConstants.PAY_PROTOCOL_URL);
			}
			startActivity(intent);
			break;

		case R.id.btn_next_step:
			double rechargeMoney = getEnterMoney();
			if (rechargeMoney == 0) {
				showToast("充值金额不能为0！");
				return;
			}
			intent = new Intent(this, SelectRechargeTypeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, rechargeMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.RECHARGE.toValue());
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}


	private double getEnterMoney() {
		double rechargeMoney = 0;
		if (StringUtils.isNotEmpty(mDepostNum.getText().toString().trim())) {
			rechargeMoney = Double.parseDouble(mDepostNum.getText().toString().trim());
		} else {
			showToast("充值金额不能为空！");
			return 0;
		}
		return rechargeMoney;
	}
}
