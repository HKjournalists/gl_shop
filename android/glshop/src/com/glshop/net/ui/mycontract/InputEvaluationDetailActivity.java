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
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;

/**
 * @Description : 输入评价描述界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class InputEvaluationDetailActivity extends BasicActivity {

	private EvaluationInfoModel mEvaInfo;

	private EditText mEtEvaContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_evaluation_detail);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		mEtEvaContent = getView(R.id.et_eva_description);
		getView(R.id.btn_done_input).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mEvaInfo = (EvaluationInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_EVALUATION_DESCRIPTION);
		mEtEvaContent.setText(mEvaInfo.content);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_done_input:
			intent = new Intent();
			mEvaInfo.content = mEtEvaContent.getText().toString();
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_EVALUATION_DESCRIPTION, mEvaInfo);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;

		case R.id.btn_contract_info_detail:
			intent = new Intent(this, ContractDetailActivity.class);
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

}
