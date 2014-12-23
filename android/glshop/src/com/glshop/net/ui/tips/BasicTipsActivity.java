package com.glshop.net.ui.tips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 用户通用操作结果提示页面基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-29 下午4:58:03
 */
public abstract class BasicTipsActivity extends BasicActivity {

	/**
	 * 操作提示信息
	 */
	protected TipInfoModel mTipInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_tips);
		initView();
		initData();
	}

	private void initView() {
		getView(R.id.btn_tips_action_1).setOnClickListener(this);
		getView(R.id.btn_tips_action_2).setOnClickListener(this);
		getView(R.id.ll_security_tips).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
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

		if (StringUtils.isNotEmpty(mTipInfo.operatorTipWarning)) {
			((TextView) findViewById(R.id.tv_security_tips)).setText(mTipInfo.operatorTipWarning);
			getView(R.id.ll_security_tips).setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			doBackAction();
			break;
		case R.id.btn_tips_action_1:
			doAction1();
			break;
		case R.id.btn_tips_action_2:
			doAction2();
			break;
		case R.id.ll_security_tips:
			doWarningAction();
			break;
		}
	}

	protected void doWarningAction() {
		// TODO
	}

	protected void doBackAction() {
		switch (mTipInfo.backType) {
		case DO_ACTION1:
			doAction1();
			break;
		case DO_ACTION2:
			doAction2();
			break;
		case FINISH:
			finish();
			break;
		case TO_MAINPAGE:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtras(getIntent().getExtras());
			startActivity(intent);
			finish();
			break;
		default:
			finish();
			break;
		}
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	public void onBackPressed() {
		doBackAction();
		super.onBackPressed();
	}

	protected abstract void doAction1();

	protected abstract void doAction2();

}
