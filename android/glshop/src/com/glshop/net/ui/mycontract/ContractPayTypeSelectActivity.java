package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;

/**
 * @Description : 合同货款支付方式选择界面，包括三种支付方式。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractPayTypeSelectActivity extends BaseContractInfoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_pay_type_select);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_test_pay).setOnClickListener(this);
	}

	private void initData() {

	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_test_pay:
			Intent intent = new Intent(this, ContractInfoActivity.class);
			mContractInfo = new ContractInfoModel();
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			finish();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}
}
