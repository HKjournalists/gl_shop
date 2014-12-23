package com.glshop.net.ui.mycontract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.utils.StringUtils;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 修改结算单价界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ModifyFinalUnitPriceActivity extends BasicActivity {

	private ContractInfoModel mContractInfo;

	private TextView mTvCountDownTime;
	private BuyEditItemView mItemEtUnitPrice;

	private float mUnitPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_final_unit_price);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		mItemEtUnitPrice = getView(R.id.ll_item_final_unit_price);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.ll_item_modify_price_reason).setOnClickListener(this);
		getView(R.id.btn_submit_modify_price_reason).setOnClickListener(this);
	}

	private void initData() {
		mUnitPrice = getIntent().getFloatExtra(GlobalAction.ContractAction.EXTRA_KEY_EDIT_UNIT_PRICE, 0);
		mContractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		if (mContractInfo != null) {
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
			float simpleCheckNegUnitPrice = 0;
			if (BeanUtils.isNotEmpty(mContractInfo.firstNegotiateList)) {
				NegotiateInfoModel preNegInfo = mContractInfo.firstNegotiateList.get(0);
				if (preNegInfo != null) {
					simpleCheckNegUnitPrice = preNegInfo.negUnitPrice;
				}
			}
			if (simpleCheckNegUnitPrice == 0) {
				simpleCheckNegUnitPrice = mContractInfo.unitPrice;
			}
			((BuyTextItemView) getView(R.id.ll_item_trade_unit_price)).setContentText(String.valueOf(mContractInfo.unitPrice));
			((BuyTextItemView) getView(R.id.ll_item_simple_check_unit_price)).setContentText(String.valueOf(simpleCheckNegUnitPrice));
		}
		if (mUnitPrice != 0) {
			mItemEtUnitPrice.setContentText(String.valueOf(mUnitPrice));
			EditText etUnitPrice = mItemEtUnitPrice.getEditText();
			if (StringUtils.isNotEmpty(etUnitPrice.getText().toString())) {
				etUnitPrice.setSelection(etUnitPrice.getText().toString().length());
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_item_modify_price_reason:
			intent = new Intent(this, SelectModPriceReasonActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_submit_modify_price_reason:
			if (checkInput()) {
				intent = new Intent();
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_EDIT_UNIT_PRICE, Float.parseFloat(mItemEtUnitPrice.getContentText()));
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
			break;

		case R.id.iv_common_back:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

	private boolean checkInput() {
		if (StringUtils.isEmpty(mItemEtUnitPrice.getContentText())) {
			showToast("结算单价不能为空!");
			return false;
		} else if (!StringUtils.checkNumber(mItemEtUnitPrice.getContentText())) {
			showToast("结算单价不能为0!");
			return false;
		}
		return true;
	}

}
