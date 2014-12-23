package com.glshop.net.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 常见问题详细页
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PlatformQADetailActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_platform_qa_detail);
		initView();
		initData();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.platform_qa);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		String title = getIntent().getStringExtra(GlobalAction.SettingAction.EXTRA_KEY_QA_TITLE);
		String content = getIntent().getStringExtra(GlobalAction.SettingAction.EXTRA_KEY_QA_CONTENT);
		((TextView) findViewById(R.id.tv_qa_detail_title)).setText(title);
		((TextView) findViewById(R.id.tv_qa_detail_content)).setText(content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
