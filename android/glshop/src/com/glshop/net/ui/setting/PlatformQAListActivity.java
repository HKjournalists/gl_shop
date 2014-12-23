package com.glshop.net.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 常见问题与解答界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PlatformQAListActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_platform_qa_list);
		initView();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.platform_qa);
		getView(R.id.iv_common_back).setOnClickListener(this);

		getView(R.id.ll_platform_qa_1).setOnClickListener(this);
		getView(R.id.ll_platform_qa_2).setOnClickListener(this);
		getView(R.id.ll_platform_qa_3).setOnClickListener(this);
		getView(R.id.ll_platform_qa_4).setOnClickListener(this);
		getView(R.id.ll_platform_qa_5).setOnClickListener(this);
		getView(R.id.ll_platform_qa_6).setOnClickListener(this);
		getView(R.id.ll_platform_qa_7).setOnClickListener(this);
		getView(R.id.ll_platform_qa_8).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		case R.id.ll_platform_qa_1:
			showDetail(getString(R.string.platform_qa_1_title), getString(R.string.platform_qa_1_content));
			break;
		case R.id.ll_platform_qa_2:
			showDetail(getString(R.string.platform_qa_2_title), getString(R.string.platform_qa_2_content));
			break;
		case R.id.ll_platform_qa_3:
			showDetail(getString(R.string.platform_qa_3_title), getString(R.string.platform_qa_3_content));
			break;
		case R.id.ll_platform_qa_4:
			showDetail(getString(R.string.platform_qa_4_title), getString(R.string.platform_qa_4_content));
			break;
		case R.id.ll_platform_qa_5:
			showDetail(getString(R.string.platform_qa_5_title), getString(R.string.platform_qa_5_content));
			break;
		case R.id.ll_platform_qa_6:
			showDetail(getString(R.string.platform_qa_6_title), getString(R.string.platform_qa_6_content));
			break;
		case R.id.ll_platform_qa_7:
			showDetail(getString(R.string.platform_qa_7_title), getString(R.string.platform_qa_7_content));
			break;
		case R.id.ll_platform_qa_8:
			showDetail(getString(R.string.platform_qa_8_title), getString(R.string.platform_qa_8_content));
			break;
		}
	}

	private void showDetail(String title, String content) {
		Intent intent = new Intent(this, PlatformQADetailActivity.class);
		intent.putExtra(GlobalAction.SettingAction.EXTRA_KEY_QA_TITLE, title);
		intent.putExtra(GlobalAction.SettingAction.EXTRA_KEY_QA_CONTENT, content);
		startActivity(intent);
	}

}
