package com.glshop.net.ui.basic.fragment.buy.filter;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 交易日期范围筛选Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class TradeDateFilterFragment extends BaseFilterFragment {

	private static final String TAG = "TradeDateFilterFragment";

	private CheckedTextView mCkbSelectAll;
	private CheckedTextView mCkbSelectCustom;
	private TextView mTvStartDatetime;
	private TextView mTvEndDatetime;

	private String mTradeBeginDate;
	private String mTradeEndDate;

	public TradeDateFilterFragment() {

	}

	@Override
	protected void initView() {
		mCkbSelectAll = getView(R.id.chkTv_select_all);
		mCkbSelectCustom = getView(R.id.chkTv_select_custom_datetime);
		mTvStartDatetime = getView(R.id.tv_filter_start_datetime);
		mTvEndDatetime = getView(R.id.tv_filter_end_datetime);

		getView(R.id.chkTv_select_all).setOnClickListener(this);
		getView(R.id.chkTv_select_custom_datetime).setOnClickListener(this);
		getView(R.id.ll_filter_start_datetime).setOnClickListener(this);
		getView(R.id.ll_filter_end_datetime).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mFilterInfo != null) {
			mTradeBeginDate = mFilterInfo.tradeStartDate;
			mTradeEndDate = mFilterInfo.tradeEndDate;
		}
		boolean isSelectedAll = !(StringUtils.isNotEmpty(mTradeBeginDate) && StringUtils.isNotEmpty(mTradeEndDate));
		handleGlobalSelectAction(isSelectedAll);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chkTv_select_all:
			mCkbSelectAll.toggle();
			handleGlobalSelectAction(mCkbSelectAll.isChecked());
			break;
		case R.id.chkTv_select_custom_datetime:
			mCkbSelectCustom.toggle();
			handleGlobalSelectAction(!mCkbSelectCustom.isChecked());
			break;
		case R.id.ll_filter_start_datetime:
		case R.id.ll_filter_end_datetime:
			showChooseDateDialog(v);
			break;
		}
	}

	@Override
	protected void updateFilterUI() {

	}

	@Override
	public BuyFilterInfoModelV2 getFilterInfo(boolean isUpdate) {
		if (!isAdded() && !isInited) {
			return getDefaultInfo();
		} else {
			if (isUpdate) {
				if (mCkbSelectAll.isChecked()) {
					mFilterInfo.tradeStartDate = "";
					mFilterInfo.tradeEndDate = "";
				} else {
					mFilterInfo.tradeStartDate = mTradeBeginDate;
					mFilterInfo.tradeEndDate = mTradeEndDate;
				}
			}
		}
		return mFilterInfo;
	}

	private void handleGlobalSelectAction(boolean isGlobalSelected) {
		mCkbSelectAll.setChecked(isGlobalSelected);
		mCkbSelectCustom.setChecked(!isGlobalSelected);
		if (isGlobalSelected) {
			mTvStartDatetime.setText(mContext.getString(R.string.data_select));
			mTvEndDatetime.setText(mContext.getString(R.string.data_select));
			mTradeBeginDate = "";
			mTradeEndDate = "";
		} else {
			if (StringUtils.isNotEmpty(mTradeBeginDate)) {
				mTvStartDatetime.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeBeginDate));
			} else {
				mTvStartDatetime.setText(mContext.getString(R.string.data_select));
			}
			if (StringUtils.isNotEmpty(mTradeEndDate)) {
				mTvEndDatetime.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeEndDate));
			} else {
				mTvEndDatetime.setText(mContext.getString(R.string.data_select));
			}
		}
	}

	/**
	 * 显示选择日期对话框
	 * @param dateWidget
	 */
	private void showChooseDateDialog(final View dateWidget) {
		String strDate = "";
		if (dateWidget.getId() == R.id.ll_filter_start_datetime) {
			if (StringUtils.isNotEmpty(mTradeBeginDate)) {
				strDate = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeBeginDate);
			}
		} else if (dateWidget.getId() == R.id.ll_filter_end_datetime) {
			if (StringUtils.isNotEmpty(mTradeEndDate)) {
				strDate = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeEndDate);
			}
		}
		if (StringUtils.isNEmpty(strDate)) {
			strDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, System.currentTimeMillis());
		}

		int year = DateUtils.getYearFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int month = DateUtils.getMonthFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int day = DateUtils.getDayFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		//Logger.e(TAG, "Y = " + year + ", M = " + month + ", D = " + day);

		DatePickerDialog dateDialog = new DatePickerDialog(mContext, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				//Logger.e(TAG, "Y = " + year + ", M = " + monthOfYear + ", D = " + dayOfMonth);
				String datetime = DateUtils.getStrDate(DateUtils.PUB_DATE_FORMAT, year, monthOfYear, dayOfMonth);
				if (dateWidget.getId() == R.id.ll_filter_start_datetime) {
					//mTvStartDatetime.setText(datetime);
					mTradeBeginDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, datetime);
					handleGlobalSelectAction(false);
				} else if (dateWidget.getId() == R.id.ll_filter_end_datetime) {
					//mTvEndDatetime.setText(datetime);
					mTradeEndDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, datetime);
					handleGlobalSelectAction(false);
				}
			}
		}, year, DateUtils.convertCNMonth2EN(month), day);
		dateDialog.show();
	}

	@Override
	public void onFilterReset() {
		if (isAdded() && isInited) {
			handleGlobalSelectAction(true);
		} else {
			Bundle filterArgs = getArguments();
			mFilterInfo = (BuyFilterInfoModelV2) filterArgs.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
			if (mFilterInfo != null) {
				mFilterInfo.tradeStartDate = "";
				mFilterInfo.tradeEndDate = "";
			}
			filterArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mFilterInfo);
			setArguments(filterArgs);
		}
	}

	@Override
	public void onFilterCancel() {
		// nothing to do
	}

	@Override
	public void onFilterSave() {
		// nothing to do
	}

	@Override
	public boolean checkArgs() {
		if (isAdded() && isInited) {
			if (mCkbSelectCustom.isChecked()) {
				if (StringUtils.isNEmpty(mTradeBeginDate)) {
					showToast("请选择开始时间!");
					return false;
				} else if (StringUtils.isNEmpty(mTradeEndDate)) {
					showToast("请选择结束时间!");
					return false;
				} else {
					long startTime = DateUtils.covertDate2Long(DateUtils.COMMON_DATE_FORMAT, mTradeBeginDate);
					long endTime = DateUtils.covertDate2Long(DateUtils.COMMON_DATE_FORMAT, mTradeEndDate);
					if (startTime > endTime) {
						showToast("开始时间不能大于结束时间!");
						return false;
					} else {
						return true;
					}
				}
			}
		}
		return true;
	}

}
