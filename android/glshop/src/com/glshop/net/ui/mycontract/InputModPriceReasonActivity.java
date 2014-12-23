package com.glshop.net.ui.mycontract;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 输入修改结算单价原因界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class InputModPriceReasonActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_modify_unit_price_reason);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_done_input).setOnClickListener(this);
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_done_input:
			finish();
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}
}
