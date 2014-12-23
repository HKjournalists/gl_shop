package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;

/**
 * @Description : 合同状态自定义控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class ContractStatusView extends LinearLayout {

	private static final String TAG = "ContractStatusView";

	private ContractInfoModel mContractInfo;

	private TextView mTvBuyerTitle;
	private TextView mTvSellerTitle;

	private View mBuyerFreezeDeposit;
	private View mSellerFreezeDeposit;
	private View mBuyerPay;
	private View mSellerSendProduct;
	private View mBuyerSimpleCheck;
	private View mSellerWiatSimapleChcek;
	private View mBuyerFullCheck;
	private View mSellerWiatFullChcek;
	private View mBuyerConfirmReceipt;
	private View mSellerConfirmDischarge;

	private View mSimpleCheckDoubleConfirm;
	private View mFullCheckDoubleConfirm;
	private View mContractSettlement;
	private View mContractFinished;

	private View[] mBuyerActionView = new View[5];
	private View[] mSellerActionView = new View[5];
	private View[] mDoubleActionView = new View[4];

	public ContractStatusView(Context context) {
		this(context, null);
	}

	public ContractStatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initData(attrs);
	}

	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_contract_status, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, lp);

		mTvBuyerTitle = getView(R.id.tv_buyer_title);
		mTvSellerTitle = getView(R.id.tv_seller_title);

		mBuyerFreezeDeposit = getView(R.id.btn_buyer_freeze_deposit);
		mSellerFreezeDeposit = getView(R.id.btn_seller_freeze_deposit);
		mBuyerPay = getView(R.id.btn_buyer_pay);
		mSellerSendProduct = getView(R.id.btn_seller_send_product);
		mBuyerSimpleCheck = getView(R.id.btn_buyer_simple_check);
		mSellerWiatSimapleChcek = getView(R.id.btn_buyer_wait_simple_check);
		mBuyerFullCheck = getView(R.id.btn_buyer_full_check);
		mSellerWiatFullChcek = getView(R.id.btn_seller_wait_full_check);
		mBuyerConfirmReceipt = getView(R.id.btn_buyer_confirm_receipt);
		mSellerConfirmDischarge = getView(R.id.btn_seller_confirm_discharge);

		mSimpleCheckDoubleConfirm = getView(R.id.btn_simple_check_double_confirm);
		mFullCheckDoubleConfirm = getView(R.id.btn_full_check_double_confirm);
		mContractSettlement = getView(R.id.btn_contract_settlement);
		mContractFinished = getView(R.id.btn_contract_finished);

		mBuyerActionView[0] = mBuyerFreezeDeposit;
		mBuyerActionView[1] = mBuyerPay;
		mBuyerActionView[2] = mBuyerSimpleCheck;
		mBuyerActionView[3] = mBuyerFullCheck;
		mBuyerActionView[4] = mBuyerConfirmReceipt;

		mSellerActionView[0] = mSellerFreezeDeposit;
		mSellerActionView[1] = mSellerSendProduct;
		mSellerActionView[2] = mSellerWiatSimapleChcek;
		mSellerActionView[3] = mSellerWiatFullChcek;
		mSellerActionView[4] = mSellerConfirmDischarge;

		mDoubleActionView[0] = mSimpleCheckDoubleConfirm;
		mDoubleActionView[1] = mFullCheckDoubleConfirm;
		mDoubleActionView[2] = mContractSettlement;
		mDoubleActionView[3] = mContractFinished;
	}

	private void initData(AttributeSet attrs) {

	}

	public void setContractInfo(ContractInfoModel info) {
		mContractInfo = info;
		updateStatus();
	}

	private void updateStatus() {
		if (mContractInfo != null) {
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
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
			case IN_THE_PAYMENT: // 付款中
			case SENT_GOODS: // 已发货
				updateStatusUI(1, 1, -1);
				break;
			case PAYED_FUNDS: // 已付款
				updateStatusUI(2, 2, -1);
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				updateStatusUI(2, 2, 0);
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
			case FULL_TAKEOVERING: // 全量验收中
				updateStatusUI(3, 3, 1);
				break;
			case FULL_TAKEOVERED: // 全量验收通过
				updateStatusUI(4, 4, 2);
				break;
			case UNINSTALLED_GOODS: // 已经卸货
				updateStatusUI(4, 5, 2);
				break;
			case RECEIVED_GOODS: // 已经收货
				updateStatusUI(5, 5, 3);
				break;
			case NORMAL_FINISHED: // 正常结束
			case SINGLECANCEL_FINISHED: // 单方取消结束
			case DUPLEXCANCEL_FINISHED: // 双方取消结束
			case EXPIRATION_FINISHED: // 终止结束
				updateStatusUI(5, 5, 4);
				break;
			default:
				updateStatusUI(0, 0, -1);
				break;
			}
		}
	}

	private void updateStatusUI(int lIndex, int mIndex, int rIndex) {
		updateBuyerStatus(lIndex);
		updateSellerStatus(mIndex);
		updateSysStatus(rIndex);
	}

	private void updateBuyerStatus(int index) {
		for (int i = 0; i < mBuyerActionView.length; i++) {
			if (index < 0) {
				mBuyerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_buyer_unfinished);
				((Button) mBuyerActionView[i]).setTextColor(getResources().getColor(R.color.gray));
			} else if (index >= mBuyerActionView.length) {
				mBuyerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_buyer_finished);
			} else {
				if (i < index) {
					mBuyerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_buyer_finished);
				} else if (i == index) {
					mBuyerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_buyer_current);
				} else {
					mBuyerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_buyer_unfinished);
					((Button) mBuyerActionView[i]).setTextColor(getResources().getColor(R.color.gray));
				}
			}
		}
	}

	private void updateSellerStatus(int index) {
		for (int i = 0; i < mSellerActionView.length; i++) {
			if (index < 0) {
				mSellerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_seller_unfinished);
				((Button) mSellerActionView[i]).setTextColor(getResources().getColor(R.color.gray));
			} else if (index >= mSellerActionView.length) {
				mSellerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_seller_finished);
			} else {
				if (i < index) {
					mSellerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_seller_finished);
				} else if (i == index) {
					mSellerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_seller_current);
				} else {
					mSellerActionView[i].setBackgroundResource(R.drawable.bg_contract_status_seller_unfinished);
					((Button) mSellerActionView[i]).setTextColor(getResources().getColor(R.color.gray));
				}
			}
		}
	}

	private void updateSysStatus(int index) {
		for (int i = 0; i < mDoubleActionView.length; i++) {
			if (index < 0) {
				mDoubleActionView[i].setBackgroundResource(R.drawable.contract_status_devider_big_circle_unfinished);
				((Button) mDoubleActionView[i]).setTextColor(getResources().getColor(R.color.gray));
			} else if (index >= mDoubleActionView.length) {
				mDoubleActionView[i].setBackgroundResource(R.drawable.contract_status_devider_big_circle_finished);
			} else {
				if (i < index) {
					mDoubleActionView[i].setBackgroundResource(R.drawable.contract_status_devider_big_circle_finished);
				} else if (i == index) {
					mDoubleActionView[i].setBackgroundResource(R.drawable.contract_status_devider_big_circle_current);
				} else {
					mDoubleActionView[i].setBackgroundResource(R.drawable.contract_status_devider_big_circle_unfinished);
					((Button) mDoubleActionView[i]).setTextColor(getResources().getColor(R.color.gray));
				}
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
