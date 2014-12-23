package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 选择修改结算单价原因界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class SelectModPriceReasonActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_mod_unit_price_reason);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.ll_other_reason).setOnClickListener(this);
		getView(R.id.btn_done).setOnClickListener(this);
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_other_reason:
			Intent intent = new Intent(this, InputModPriceReasonActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_done:
			finish();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}
}
