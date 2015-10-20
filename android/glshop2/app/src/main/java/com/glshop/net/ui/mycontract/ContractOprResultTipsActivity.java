package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同操作结果页面，包括取消合同、咨询客服等。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-29 下午5:55:44
 */
public class ContractOprResultTipsActivity extends BaseContractInfoActivity {

	/**
	 * 操作提示信息
	 */
	private TipInfoModel mTipInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_tips);
		initView();
		initData();
	}

	private void initView() {
		((Button) findViewById(R.id.btn_tips_action_1)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_tips_action_2)).setOnClickListener(this);
		((ImageView) findViewById(R.id.iv_common_back)).setOnClickListener(this);
	}

	private void initData() {
		if (getIntent().hasExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO)) {
			mTipInfo = (TipInfoModel) getIntent().getSerializableExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO);
		}

		((TextView) findViewById(R.id.tv_commmon_title)).setText(mTipInfo.operatorTipTitle);
		((TextView) findViewById(R.id.tv_commmon_tips_type_title)).setText(mTipInfo.operatorTipTypeTitle);
		((TextView) findViewById(R.id.tv_commmon_tips_content)).setText(mTipInfo.operatorTipContent);

		if (StringUtils.isNotEmpty(mTipInfo.operatorTipActionText1)) {
			((Button) findViewById(R.id.btn_tips_action_1)).setText(mTipInfo.operatorTipActionText1);
		} else {
			((Button) findViewById(R.id.btn_tips_action_1)).setVisibility(View.GONE);
		}

		if (StringUtils.isNotEmpty(mTipInfo.operatorTipActionText2)) {
			((Button) findViewById(R.id.btn_tips_action_2)).setText(mTipInfo.operatorTipActionText2);
		} else {
			((Button) findViewById(R.id.btn_tips_action_2)).setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_common_back:
			doBackAction();
			break;
		case R.id.btn_tips_action_1:
			doUserAction();
			break;
		}
	}

	private void doBackAction() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
		cleanStack();
	}

	private void doUserAction() {
		cleanStack();
		Intent intent = new Intent(this, ContractInfoActivityV2.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo != null ? mContractInfo.contractId : mContractId);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		doBackAction();
		super.onBackPressed();
	}

}
