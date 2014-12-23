package com.glshop.net.ui.basic.fragment.contract;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.mycontract.ContractEvaluationListActivity;
import com.glshop.net.utils.DateUtils;

/**
 * @Description : 已结束的合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class EndedContractInfoFragment extends BaseContractInfoFragment {

	private TextView mTvEndDatetime;

	private TextView mTvEndReason;

	@Override
	protected void initView() {
		mContractStatus = getView(R.id.ll_contract_status);

		mTvEndDatetime = getView(R.id.tv_contract_status_info);
		mTvEndReason = getView(R.id.tv_contract_status_remarks_content);
		mTvContractId = getView(R.id.tv_contract_id);

		getView(R.id.ll_contract_status_info_remarks).setVisibility(View.VISIBLE);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
		getView(R.id.btn_evaluation).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mContractInfo != null) {
			mContractStatus.setContractInfo(mContractInfo);
			mTvContractId.setText(mContractInfo.contractId);
			showContractStatus();
		} else {
			showToast("合同内容不能为空!");
			finish();
		}
	}

	/**
	 * 显示结束时间及结束原因
	 */
	private void showContractStatus() {
		String datetime = "";
		switch (mContractInfo.lifeCycle) {
		case NORMAL_FINISHED: // 正常结束
			datetime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, mContractInfo.updateTime);
			mTvEndDatetime.setText(String.format(getString(R.string.contract_status_finished), datetime));
			getView(R.id.ll_contract_status_info_remarks).setVisibility(View.GONE);
			break;
		case SINGLECANCEL_FINISHED: // 单方取消结束
			datetime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, mContractInfo.updateTime);
			mTvEndDatetime.setText(String.format(getString(R.string.contract_status_user_canceled), datetime));
			mTvEndReason.setText("单方强制取消");
			getView(R.id.btn_evaluation).setVisibility(View.GONE);
			break;
		case DUPLEXCANCEL_FINISHED: // 双方取消结束
			datetime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, mContractInfo.updateTime);
			mTvEndDatetime.setText(String.format(getString(R.string.contract_status_user_canceled), datetime));
			mTvEndReason.setText("双方协商取消");
			getView(R.id.btn_evaluation).setVisibility(View.GONE);
			break;
		case EXPIRATION_FINISHED: // 终止结束
			datetime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, mContractInfo.updateTime);
			mTvEndDatetime.setText(String.format(getString(R.string.contract_status_system_canceled), datetime));
			mTvEndReason.setText("平台终止结束");
			getView(R.id.btn_evaluation).setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_evaluation:
			Intent intent = new Intent(mContext, ContractEvaluationListActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			break;
		}
	}

}
