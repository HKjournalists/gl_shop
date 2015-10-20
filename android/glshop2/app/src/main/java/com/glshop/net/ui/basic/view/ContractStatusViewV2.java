package com.glshop.net.ui.basic.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.utils.ContractUtil;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 合同状态自定义控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class ContractStatusViewV2 extends LinearLayout {

	private static final String TAG = "ContractStatusViewV2";

	private static final int STATUS_PASS = 0;
	private static final int STATUS_CUR = 1;
	private static final int STATUS_NEXT = 2;

	private ContractInfoModel mContractInfo;

	private TextView mTvBuyerTitle;
	private TextView mTvSellerTitle;
	private ViewGroup mMiddleStatusList;
	private View llStatusEva;
	private Button mTvContractEnded;
	private Button mTvContractEva;
	private View mEvaDevider;

	private List<StatusInfo> statusInfos = new ArrayList<StatusInfo>();

	private Context mContext;

	public ContractStatusViewV2(Context context) {
		this(context, null);
	}

	public ContractStatusViewV2(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
		initData(attrs);
	}

	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_contract_status_v2, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, lp);

		mTvBuyerTitle = getView(R.id.tv_buyer_title);
		mTvSellerTitle = getView(R.id.tv_seller_title);
		mMiddleStatusList = getView(R.id.ll_status_container);

		llStatusEva = getView(R.id.ll_status_eva);
		mTvContractEnded = getView(R.id.btn_contract_settlement);
		mTvContractEva = getView(R.id.btn_contract_finished);
		mEvaDevider = getView(R.id.devider_status_eva);
	}

	private void initData(AttributeSet attrs) {
		// TODO
	}

	public void setContractInfo(ContractInfoModel info) {
		mContractInfo = info;
		updateStatus();
	}

	private void updateStatus() {
		if (mContractInfo != null) {
			updateTopStatusUI();
			updateMiddleStatusUI();
			updateBottomStatusUI();
		}
	}

	private void updateTopStatusUI() {
		if (mContractInfo.buyType == BuyType.BUYER) {
			mTvBuyerTitle.setText("买家(我)");
			mTvBuyerTitle.setTextColor(getResources().getColor(R.color.orange));
			mTvSellerTitle.setText("卖家");
			mTvSellerTitle.setTextColor(getResources().getColor(R.color.black));
		} else {
			mTvBuyerTitle.setText("买家");
			mTvBuyerTitle.setTextColor(getResources().getColor(R.color.black));
			mTvSellerTitle.setText("卖家(我)");
			mTvSellerTitle.setTextColor(getResources().getColor(R.color.orange));
		}
	}

	private void updateMiddleStatusUI() {
		statusInfos.add(mkStatusInfo(R.string.contract_status_view_freeze_deposit, STATUS_PASS, R.string.contract_status_view_freeze_deposit, STATUS_PASS));
		switch (mContractInfo.lifeCycle) {
		case SINGED: // 已签订
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_CUR, R.string.contract_status_view_to_send, STATUS_CUR));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_NEXT, R.string.contract_status_view_seller_to_confirm, STATUS_NEXT));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_pay, STATUS_NEXT, R.string.contract_status_view_platform_normal_settle_accounts, STATUS_NEXT));
			break;
		case PAYED_FUNDS: // 已付款
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_CUR, R.string.contract_status_view_seller_to_confirm, STATUS_CUR));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_pay, STATUS_NEXT, R.string.contract_status_view_platform_normal_settle_accounts, STATUS_NEXT));
			break;
		case CONFIRMING_GOODS_FUNDS: // 货款确认中
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_CUR, R.string.contract_status_view_seller_to_confirm, STATUS_CUR));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_pay, STATUS_NEXT, R.string.contract_status_view_platform_normal_settle_accounts, STATUS_NEXT));
			break;
		case ARBITRATING: // 仲裁中
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			if ((ContractUtil.isMineApplyArbitrate(mContractInfo) && isBuyer()) || (!ContractUtil.isMineApplyArbitrate(mContractInfo) && !isBuyer())) {
				statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_unnormal_settle_accounts, STATUS_CUR, R.string.contract_status_view_freeze_trade, STATUS_CUR));
			} else {
				statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
				statusInfos.add(mkStatusInfo(R.string.contract_status_view_freeze_trade, STATUS_CUR, R.string.contract_status_view_platform_unnormal_settle_accounts, STATUS_CUR));
			}
			break;
		case NORMAL_FINISHED: // 正常结束
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_pay, STATUS_PASS, R.string.contract_status_view_platform_normal_settle_accounts, STATUS_PASS));
			break;
		case SINGLECANCEL_FINISHED: // 单方取消结束
			if ((ContractUtil.isMineCanceled(mContractInfo) && isBuyer()) || (!ContractUtil.isMineCanceled(mContractInfo) && !isBuyer())) {
				if (mContractInfo.buyerStatus.preLifeCycle != null) {
					if (mContractInfo.buyerStatus.preLifeCycle == ContractLifeCycle.DRAFTING || mContractInfo.buyerStatus.preLifeCycle == ContractLifeCycle.SINGED) { // 已签订
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_my_canceled, STATUS_PASS, R.string.contract_status_view_by_ended, STATUS_PASS));
					} else if (mContractInfo.buyerStatus.preLifeCycle == ContractLifeCycle.PAYED_FUNDS) { // 已付款
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
						//statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_my_canceled, STATUS_PASS, R.string.contract_status_view_by_ended, STATUS_PASS));
					} else if (mContractInfo.buyerStatus.preLifeCycle == ContractLifeCycle.CONFIRMING_GOODS_FUNDS) { // 货款确认中
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
						statusInfos.add(mkStatusInfo(R.string.contract_status_view_my_canceled, STATUS_PASS, R.string.contract_status_view_by_ended, STATUS_PASS));
					}
				}
			} else {
				if (mContractInfo.buyerStatus.preLifeCycle == ContractLifeCycle.DRAFTING || mContractInfo.buyerStatus.lifeCycle == ContractLifeCycle.SINGED) { // 已签订
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_by_ended, STATUS_PASS, R.string.contract_status_view_my_canceled, STATUS_PASS));
				} else if (mContractInfo.buyerStatus.lifeCycle == ContractLifeCycle.PAYED_FUNDS) { // 已付款
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
					//statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_by_ended, STATUS_PASS, R.string.contract_status_view_my_canceled, STATUS_PASS));
				} else if (mContractInfo.buyerStatus.lifeCycle == ContractLifeCycle.CONFIRMING_GOODS_FUNDS) { // 货款确认中
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_buyer_to_confirm, STATUS_PASS, R.string.contract_status_view_seller_to_confirm, STATUS_PASS));
					statusInfos.add(mkStatusInfo(R.string.contract_status_view_by_ended, STATUS_PASS, R.string.contract_status_view_my_canceled, STATUS_PASS));
				}
			}
			break;
		case BUYER_UNPAY_FINISHED: // 买家未付款结束
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_unpayed_ended, STATUS_PASS, R.string.contract_status_view_by_ended, STATUS_PASS));
			break;
		case ARBITRATED: // 仲裁结束
			statusInfos.add(mkStatusInfo(R.string.contract_status_view_to_pay, STATUS_PASS, R.string.contract_status_view_to_send, STATUS_PASS));
			if ((ContractUtil.isMineApplyArbitrate(mContractInfo) && isBuyer()) || (!ContractUtil.isMineApplyArbitrate(mContractInfo) && !isBuyer())) {
				statusInfos.add(mkStatusInfo(R.string.contract_status_view_platform_unnormal_settle_accounts, STATUS_PASS, R.string.contract_status_view_freeze_trade, STATUS_PASS));
			} else {
				statusInfos.add(mkStatusInfo(R.string.contract_status_view_freeze_trade, STATUS_PASS, R.string.contract_status_view_platform_unnormal_settle_accounts, STATUS_PASS));
			}
			break;
		default:
			// TODO
			break;
		}
		addMiddleStatusUI();
	}

	private void addMiddleStatusUI() {
		if (BeanUtils.isNotEmpty(statusInfos)) {
			for (int i = 0; i < statusInfos.size(); i++) {
				StatusInfo info = statusInfos.get(i);
				if (info != null) {
					View statusView = LayoutInflater.from(getContext()).inflate(R.layout.layout_contract_status_item, null);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

					Button buyerText = (Button) statusView.findViewById(R.id.btn_buyer_status);
					buyerText.setText(info.buyStatusTxt);

					Button sellerText = (Button) statusView.findViewById(R.id.btn_seller_status);
					sellerText.setText(info.sellStatusTxt);

					updateBuyerStatusIcon(buyerText, info.buyIconStatus);
					updateSellerStatusIcon(sellerText, info.sellIconStatus);
					mMiddleStatusList.addView(statusView, lp);
				}
			}
		}
	}

	private void updateBuyerStatusIcon(Button statusBg, int type) {
		if (type == STATUS_PASS) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_buyer_finished);
		} else if (type == STATUS_CUR) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_buyer_current);
		} else if (type == STATUS_NEXT) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_buyer_unfinished);
			statusBg.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	private void updateSellerStatusIcon(Button statusBg, int type) {
		if (type == STATUS_PASS) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_seller_finished);
		} else if (type == STATUS_CUR) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_seller_current);
		} else if (type == STATUS_NEXT) {
			statusBg.setBackgroundResource(R.drawable.bg_contract_status_seller_unfinished);
			statusBg.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	private void updateBottomStatusUI() {
		switch (mContractInfo.lifeCycle) {
		case SINGED: // 已签订
		case PAYED_FUNDS: // 已付款
		case CONFIRMING_GOODS_FUNDS: // 货款确认中
			llStatusEva.setVisibility(View.VISIBLE);
			mEvaDevider.setVisibility(View.VISIBLE);
			mTvContractEnded.setBackgroundResource(R.drawable.contract_status_devider_big_circle_unfinished);
			mTvContractEnded.setTextColor(getResources().getColor(R.color.gray));
			break;
		case ARBITRATING: // 申请仲裁中
			llStatusEva.setVisibility(View.GONE);
			mEvaDevider.setVisibility(View.GONE);
			mTvContractEnded.setBackgroundResource(R.drawable.contract_status_devider_big_circle_unfinished);
			mTvContractEnded.setTextColor(getResources().getColor(R.color.gray));
			break;
		case NORMAL_FINISHED: // 正常结束
			llStatusEva.setVisibility(View.VISIBLE);
			mEvaDevider.setVisibility(View.VISIBLE);
			mTvContractEnded.setBackgroundResource(R.drawable.contract_status_devider_big_circle_finished);
			mTvContractEnded.setTextColor(getResources().getColor(R.color.white));
			if (isMineDoneEva(mContractInfo)) {
				mTvContractEva.setBackgroundResource(R.drawable.contract_status_devider_big_circle_finished);
				mTvContractEva.setTextColor(getResources().getColor(R.color.white));
			} else {
				mTvContractEva.setBackgroundResource(R.drawable.contract_status_devider_big_circle_current);
				mTvContractEva.setTextColor(getResources().getColor(R.color.white));
			}
			break;
		default:
			llStatusEva.setVisibility(View.GONE);
			mEvaDevider.setVisibility(View.GONE);
			mTvContractEnded.setBackgroundResource(R.drawable.contract_status_devider_big_circle_finished);
			mTvContractEnded.setTextColor(getResources().getColor(R.color.white));
			break;
		}
	}

	private StatusInfo mkStatusInfo(int buyStatusTxt, int buyIconStatus, int sellStatusTxt, int sellIconStatus) {
		return new StatusInfo(mContext.getString(buyStatusTxt), buyIconStatus, mContext.getString(sellStatusTxt), sellIconStatus);
	}

	private boolean isBuyer() {
		return mContractInfo.buyType == BuyType.BUYER;
	}

	private boolean isMineDoneEva(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER && info.isBuyerEva) {
			return true;
		} else if (info.buyType == BuyType.SELLER && info.isSellerEva) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	class StatusInfo {

		public String buyStatusTxt;

		public String sellStatusTxt;

		public int buyIconStatus;

		public int sellIconStatus;

		public StatusInfo(String buyStatusTxt, int buyIconStatus, String sellStatusTxt, int sellIconStatus) {
			this.buyStatusTxt = buyStatusTxt;
			this.buyIconStatus = buyIconStatus;
			this.sellStatusTxt = sellStatusTxt;
			this.sellIconStatus = sellIconStatus;
		}
	}

}
