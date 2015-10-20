package com.glshop.net.ui.tips;

import android.content.Intent;

import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.MainActivity;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 用户通用操作结果提示页面，点击后应用跳转到主页面(包括其子Tab页面)、或对应的页面。。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-29 下午5:55:44
 */
public class OperatorTipsActivity extends BasicTipsActivity {

	@Override
	protected void doAction1() {
		if (mTipInfo != null) {
			Class<?> targetIntent = null;
			if (mTipInfo.operatorTipActionClass1 != null) {
				targetIntent = mTipInfo.operatorTipActionClass1;
			} else {
				targetIntent = MainActivity.class;
			}
			Intent intent = new Intent(this, targetIntent);

			if (StringUtils.isNotEmpty(mTipInfo.operatorTipAction1)) {
				intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, mTipInfo.operatorTipAction1);
			}

			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtras(getIntent().getExtras());
			startActivity(intent);
		}
		finish();
	}

	@Override
	protected void doAction2() {
		if (mTipInfo != null) {
			Class<?> targetIntent = null;
			if (mTipInfo.operatorTipActionClass2 != null) {
				targetIntent = mTipInfo.operatorTipActionClass2;
			} else {
				targetIntent = MainActivity.class;
			}
			Intent intent = new Intent(this, targetIntent);

			if (StringUtils.isNotEmpty(mTipInfo.operatorTipAction2)) {
				intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, mTipInfo.operatorTipAction2);
			}

			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtras(getIntent().getExtras());
			startActivity(intent);
		}
		finish();
	}

}
