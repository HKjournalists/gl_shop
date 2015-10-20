package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.glshop.net.R;

/**
 * @Description : 自定义评价控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class EvaluationRatingBar extends LinearLayout implements OnClickListener {

	private int rate = 5;

	private RelativeLayout rlRate1;
	private RelativeLayout rlRate2;
	private RelativeLayout rlRate3;
	private RelativeLayout rlRate4;
	private RelativeLayout rlRate5;

	private final int mUnSelectedBgRes = R.drawable.btn_normal_bg;
	private final int mSelectedBgRes = R.drawable.btn_orange_bg_normal;

	public EvaluationRatingBar(Context context) {
		this(context, null);
	}

	public EvaluationRatingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_evaluation_ratingbar, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, lp);

		rlRate1 = getView(R.id.ll_rate_1);
		rlRate2 = getView(R.id.ll_rate_2);
		rlRate3 = getView(R.id.ll_rate_3);
		rlRate4 = getView(R.id.ll_rate_4);
		rlRate5 = getView(R.id.ll_rate_5);

		rlRate1.setOnClickListener(this);
		rlRate2.setOnClickListener(this);
		rlRate3.setOnClickListener(this);
		rlRate4.setOnClickListener(this);
		rlRate5.setOnClickListener(this);

		updateRateStatus();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_rate_1:
			rate = 1;
			break;
		case R.id.ll_rate_2:
			rate = 2;
			break;
		case R.id.ll_rate_3:
			rate = 3;
			break;
		case R.id.ll_rate_4:
			rate = 4;
			break;
		case R.id.ll_rate_5:
			rate = 5;
			break;
		}
		updateRateStatus();
	}

	private void updateRateStatus() {
		rlRate1.findViewById(R.id.btn_rate_bg).setBackgroundResource(rate == 1 ? mSelectedBgRes : mUnSelectedBgRes);
		rlRate2.findViewById(R.id.btn_rate_bg).setBackgroundResource(rate == 2 ? mSelectedBgRes : mUnSelectedBgRes);
		rlRate3.findViewById(R.id.btn_rate_bg).setBackgroundResource(rate == 3 ? mSelectedBgRes : mUnSelectedBgRes);
		rlRate4.findViewById(R.id.btn_rate_bg).setBackgroundResource(rate == 4 ? mSelectedBgRes : mUnSelectedBgRes);
		rlRate5.findViewById(R.id.btn_rate_bg).setBackgroundResource(rate == 5 ? mSelectedBgRes : mUnSelectedBgRes);

		rlRate1.findViewById(R.id.iv_rate_selected_bg).setVisibility(rate == 1 ? View.VISIBLE : View.GONE);
		rlRate2.findViewById(R.id.iv_rate_selected_bg).setVisibility(rate == 2 ? View.VISIBLE : View.GONE);
		rlRate3.findViewById(R.id.iv_rate_selected_bg).setVisibility(rate == 3 ? View.VISIBLE : View.GONE);
		rlRate4.findViewById(R.id.iv_rate_selected_bg).setVisibility(rate == 4 ? View.VISIBLE : View.GONE);
		rlRate5.findViewById(R.id.iv_rate_selected_bg).setVisibility(rate == 5 ? View.VISIBLE : View.GONE);
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getRate() {
		return rate;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

}
