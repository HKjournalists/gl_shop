package com.glshop.net.ui.setting;

import android.os.Bundle;
import android.os.Message;

import com.glshop.net.R;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 流程说明界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ProcedureIntroActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_procedure_intro);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

}
