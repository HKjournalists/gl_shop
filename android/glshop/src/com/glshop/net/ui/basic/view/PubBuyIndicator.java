package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.PubBuyIndicatorType;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 发布买卖信息页面指示器控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class PubBuyIndicator extends LinearLayout {

	private static final String TAG = "PubBuyIndicator";

	private PubBuyIndicatorType mIndicatorType = PubBuyIndicatorType.PRODUCT;

	private View mViewTab1;
	private View mViewTab2;
	private View mViewTab3;
	private View mViewTab4;

	private View mIvTab1;
	private View mIvTab2;
	private View mIvTab3;

	private TextView mTvTabIndex1;
	private TextView mTvTabIndex2;
	private TextView mTvTabIndex3;

	private TextView mTvTabTitle1;
	private TextView mTvTabTitle2;
	private TextView mTvTabTitle3;

	public PubBuyIndicator(Context context) {
		this(context, null);
	}

	public PubBuyIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initData(attrs);
	}

	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_pub_buy_indicator, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(view, lp);

		mViewTab1 = getView(R.id.ll_tab_1);
		mViewTab2 = getView(R.id.ll_tab_2);
		mViewTab3 = getView(R.id.ll_tab_3);
		mViewTab4 = getView(R.id.ll_tab_4);

		mIvTab1 = getView(R.id.iv_tab_1);
		mIvTab2 = getView(R.id.iv_tab_2);
		mIvTab3 = getView(R.id.iv_tab_3);

		mTvTabIndex1 = getView(R.id.tv_tab_index_1);
		mTvTabIndex2 = getView(R.id.tv_tab_index_2);
		mTvTabIndex3 = getView(R.id.tv_tab_index_3);

		mTvTabTitle1 = getView(R.id.tv_tab_title_1);
		mTvTabTitle2 = getView(R.id.tv_tab_title_2);
		mTvTabTitle3 = getView(R.id.tv_tab_title_3);

		updateIndicatorUI();
	}

	private void initData(AttributeSet attrs) {
		//TODO
	}

	public void setIndicatorType(PubBuyIndicatorType type) {
		mIndicatorType = type;
		updateIndicatorUI();
	}

	public PubBuyIndicatorType getIndicatorType() {
		return mIndicatorType;
	}

	public void updateIndicatorUI() {
		Resources res = this.getResources();
		if (mIndicatorType != null) {
			Logger.i(TAG, "IndicatorIndex = " + mIndicatorType.toValue());
			switch (mIndicatorType) {
			case PRODUCT:
				mViewTab1.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab2.setBackgroundColor(res.getColor(R.color.gray));
				mViewTab3.setBackgroundColor(res.getColor(R.color.gray));
				mViewTab4.setBackgroundColor(res.getColor(R.color.gray));
				mIvTab1.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mIvTab2.setBackgroundResource(R.drawable.bg_pub_indicator_normal);
				mIvTab3.setBackgroundResource(R.drawable.bg_pub_indicator_normal);
				mTvTabTitle1.setTextColor(res.getColor(R.color.black));
				mTvTabTitle2.setTextColor(res.getColor(R.color.gray));
				mTvTabTitle3.setTextColor(res.getColor(R.color.gray));
				break;
			case TRADE:
				mViewTab1.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab2.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab3.setBackgroundColor(res.getColor(R.color.gray));
				mViewTab4.setBackgroundColor(res.getColor(R.color.gray));
				mIvTab1.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mIvTab2.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mIvTab3.setBackgroundResource(R.drawable.bg_pub_indicator_normal);
				mTvTabTitle1.setTextColor(res.getColor(R.color.black));
				mTvTabTitle2.setTextColor(res.getColor(R.color.black));
				mTvTabTitle3.setTextColor(res.getColor(R.color.gray));
				break;
			case PREVIEW:
				mViewTab1.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab2.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab3.setBackgroundColor(res.getColor(R.color.green_deal));
				mViewTab4.setBackgroundColor(res.getColor(R.color.green_deal));
				mIvTab1.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mIvTab2.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mIvTab3.setBackgroundResource(R.drawable.bg_pub_indicator_selected);
				mTvTabTitle1.setTextColor(res.getColor(R.color.black));
				mTvTabTitle2.setTextColor(res.getColor(R.color.black));
				mTvTabTitle3.setTextColor(res.getColor(R.color.black));
				break;
			}
		}
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
