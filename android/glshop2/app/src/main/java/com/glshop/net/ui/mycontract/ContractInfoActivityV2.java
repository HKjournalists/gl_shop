package com.glshop.net.ui.mycontract;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ContractInfoType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter.TabInfo;
import com.glshop.net.ui.basic.fragment.contract.info.ContractOprInfoFragment;
import com.glshop.net.ui.basic.fragment.contract.info.ContractStatusInfoFragment;
import com.glshop.net.ui.basic.view.TabIndicator;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的合同主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractInfoActivityV2 extends BaseContractInfoActivity implements ViewPager.OnPageChangeListener {

	private static final String TAB_CONTRACT_OPERATION = "fragment-tab-operation";
	private static final String TAB_CONTRACT_STATUS = "fragment-tab-status";

	private static final int FRAGMENT_CONTAINER = R.id.contract_viewpager;

	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;

	private ContractOprInfoFragment mFragmentOperation;
	private ContractStatusInfoFragment mFragmentStatus;

	private ContractInfoType contractInfoType = ContractInfoType.OPERATION;
	private ContractInfoModel mContractInfo;

	private RadioButton mRdoBtnOperation;
	private RadioButton mRdoBtnStatus;

	private TabIndicator mIndicator;

	private ImageButton mBtnDelete;
	private ConfirmDialog mDeleteDialog;

	private String mContractId;
	private boolean isGetModel;
	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_info_v2);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);

		initLoadView();
		mNormalDataView = getView(R.id.ll_contract_info);

		mBtnDelete = getView(R.id.btn_commmon_action);
		mBtnDelete.setImageResource(R.drawable.selector_btn_delete);
		mBtnDelete.setOnClickListener(this);

		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.rdoBtn_operation).setOnClickListener(this);
		getView(R.id.rdoBtn_status).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mRdoBtnOperation = getView(R.id.rdoBtn_operation);
		mRdoBtnStatus = getView(R.id.rdoBtn_status);
		mViewPager = getView(FRAGMENT_CONTAINER);
		mIndicator = getView(R.id.tab_pager_indicator);
	}

	private void initData() {
		mContractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		isGetModel = getIntent().getBooleanExtra(GlobalAction.ContractAction.EXTRA_KEY_IS_GET_CONTRACT_MODEL, false);
		if (StringUtils.isNotEmpty(mContractId)) {
			updateDataStatus(DataStatus.LOADING);
			mInvoker = String.valueOf(System.currentTimeMillis());
			mContractLogic.getContractInfo(mInvoker, mContractId, isGetModel);
		} else {
			showToast("合同ID不能为空!");
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mInvoker = String.valueOf(System.currentTimeMillis());
		mContractLogic.getContractInfo(mInvoker, mContractId, isGetModel);
	}

	private void updateUI() {
		/*if (savedState != null) {
			contractInfoType = ContractInfoType.convert(savedState.getInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB));
			updateTabState();
			Logger.e(TAG, "TabIndex = " + contractInfoType.toValue());
		}*/

		updateBuyInfo();
		updateActionBar();

		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();

		mFragmentOperation = getFragment(TAB_CONTRACT_OPERATION);
		mFragmentStatus = getFragment(TAB_CONTRACT_STATUS);

		if (mFragmentOperation == null) {
			mFragmentOperation = new ContractOprInfoFragment();
			mFragmentOperation.setArguments(createFragmentArgs(R.layout.fragment_contract_opr_info));
		}
		if (mFragmentStatus == null) {
			mFragmentStatus = new ContractStatusInfoFragment();
			mFragmentStatus.setArguments(createFragmentArgs(R.layout.fragment_contract_status_info));
		}

		transaction.hide(mFragmentOperation);
		transaction.hide(mFragmentStatus);

		transaction.commitAllowingStateLoss();

		List<TabInfo> tabs = new ArrayList<TabInfo>();
		tabs.add(new TabInfo(0, getTabTitle(), TAB_CONTRACT_OPERATION, ContractOprInfoFragment.class, mFragmentOperation));
		tabs.add(new TabInfo(1, getString(R.string.contract_tab_status), TAB_CONTRACT_STATUS, ContractStatusInfoFragment.class, mFragmentStatus));

		mIndicator.init(contractInfoType.toValue(), tabs, mViewPager);

		mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOffscreenPageLimit(1);
		switchTab();
	}

	private void updateBuyInfo() {
		TextView tvProductSpec = getView(R.id.tv_buy_product_spec);
		tvProductSpec.setText(mContractInfo.productName);

		TextView tvUnitPrice = getView(R.id.tv_buy_unit_price);
		tvUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));

		TextView mTvBuyAmount = getView(R.id.tv_buy_amount);
		mTvBuyAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
	}

	private String getTabTitle() {
		String title = getString(R.string.contract_info_tab_title1);
		if (mContractInfo.myContractType == ContractType.ENDED) {
			mBtnDelete.setVisibility(View.VISIBLE);
			title = getString(R.string.contract_info_tab_title4);
		} else {
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
			case PAYED_FUNDS: // 已付款
				title = getString(R.string.contract_info_tab_title1);
				break;
			case CONFIRMING_GOODS_FUNDS: // 货款确认中
				if (mContractInfo.buyType == BuyType.BUYER) {
					title = getString(R.string.contract_info_tab_title2);
				} else {
					title = getString(R.string.contract_info_tab_title1);
				}
				break;
			case ARBITRATING: // 仲裁中 
				title = getString(R.string.contract_info_tab_title3);
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
			case SINGLECANCEL_FINISHED: // 取消结束
			case ARBITRATED: // 仲裁结束
			case NORMAL_FINISHED: // 正常结束
				title = getString(R.string.contract_info_tab_title4);
				break;
			default:
				// TODO
				break;
			}
		}
		return title;
	}

	private void updateActionBar() {
		if (mContractInfo.myContractType == ContractType.ENDED) {
			mBtnDelete.setVisibility(View.VISIBLE);
		}
	}

	private Bundle createFragmentArgs(int layoutId) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, layoutId);
		args.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		return args;
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		if (respInfo != null) {
			if (message.what == ContractMessageType.MSG_REFRESH_CONTRACT_INFO) {
				onRefreshInfo(respInfo);
				return;
			} else {
				if (isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
					switch (message.what) {
					case ContractMessageType.MSG_GET_CONTRACT_INFO_SUCCESS:
						onGetContractInfoSuccess(respInfo);
						break;
					case ContractMessageType.MSG_GET_CONTRACT_INFO_FAILED:
						onGetContractInfoFailed(respInfo);
						break;
					case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_SUCCESS:
						onMultiCancelSuccess(respInfo);
						break;
					case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED:
						onMultiCancelFailed(respInfo);
						break;
					}
				}
			}
		}
	}

	private void onGetContractInfoSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mContractInfo = (ContractInfoModel) respInfo.data;
			if (mContractInfo != null) {
				if (!gotoModelPage(mContractInfo)) {
					updateUI();
					updateDataStatus(DataStatus.NORMAL);
				}
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private boolean gotoModelPage(ContractInfoModel info) {
		if (isGetModel && info.modelInfo != null) {
			if (info.myContractType == ContractType.UNCONFIRMED
					|| (info.myContractType == ContractType.DELETION && (info.lifeCycle == ContractLifeCycle.DRAFTING || info.lifeCycle == ContractLifeCycle.DRAFTING_CANCEL
							|| info.lifeCycle == ContractLifeCycle.TIMEOUT_FINISHED || info.lifeCycle == ContractLifeCycle.MANUAL_RESTORE))) {
				Intent intent = new Intent(this, UfmContractInfoActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_MODEL_INFO, info.modelInfo);
				startActivity(intent);
				finish();
				cleanStack();
				return true;
			}
		}
		return false;
	}

	private void onGetContractInfoFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private void onMultiCancelSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		if (respInfo.intArg1 == ContractCancelType.DELETE.toValue()) {
			Intent intent = new Intent(this, OperatorTipsActivity.class);
			TipInfoModel tipInfo = new TipInfoModel();
			tipInfo.operatorTipTitle = getString(R.string.my_contract);
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_delete_contract_success);
			tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
			tipInfo.operatorTipActionClass1 = MainActivity.class;
			tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT;
			intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
			startActivity(intent);
			finish();
		}
	}

	private void onMultiCancelFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onRefreshInfo(RespInfo respInfo) {
		if (isCurrentActivity() && respInfo != null) {
			String newContractId = respInfo.strArg1;
			Logger.e(TAG, "CurContractId = " + mContractId + ", NewContractId = " + newContractId);
			if (isSameContract(mContractId, newContractId)) {
				Logger.e(TAG, "Contract id is match, so refresh the current contract page!");
				Intent intent = new Intent(this, ContractInfoActivityV2.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, newContractId);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_IS_GET_CONTRACT_MODEL, true);
				startActivity(intent);
				finish();
				return;
			} else {
				Logger.e(TAG, "Contract id is not match, so ignore this action!");
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_contract_model_info:
			intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			startActivity(intent);
			break;
		case R.id.rdoBtn_operation:
			if (contractInfoType != ContractInfoType.OPERATION) {
				contractInfoType = ContractInfoType.OPERATION;
				switchTab();
			}
			break;
		case R.id.rdoBtn_status:
			if (contractInfoType != ContractInfoType.STATUS) {
				contractInfoType = ContractInfoType.STATUS;
				switchTab();
			}
			break;
		case R.id.btn_commmon_action:
			showDeleteDialog();
			break;
		case R.id.iv_common_back:
			if (getParent() instanceof MainActivity) {
				((MainActivity) getParent()).switchTabView(TabStatus.SHOP);
			} else {
				finish();
			}
			break;
		}
	}

	private void switchTab() {
		mViewPager.setCurrentItem(contractInfoType.toValue());
	}

	private void updateTabState() {
		mRdoBtnOperation.setChecked(contractInfoType == ContractInfoType.OPERATION);
		mRdoBtnStatus.setChecked(contractInfoType == ContractInfoType.STATUS);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mIndicator.onScrolled((mViewPager.getWidth() + mViewPager.getPageMargin()) * position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		contractInfoType = ContractInfoType.convert(position);
		updateTabState();
		mIndicator.onSwitched(position);
	}

	private void showDeleteDialog() {
		closeDialog(mDeleteDialog);
		mDeleteDialog = new ConfirmDialog(this, R.style.dialog);
		mDeleteDialog.setContent(getString(R.string.delete_contract_warning_tip));
		mDeleteDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				doDeleteAction();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mDeleteDialog.show();
	}

	/**
	 * 删除合同操作
	 */
	protected void doDeleteAction() {
		showSubmitDialog();
		mInvoker = String.valueOf(System.currentTimeMillis());
		mContractLogic.multiCancelContract(mInvoker, mContractInfo.contractId, ContractCancelType.DELETE);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB, contractInfoType.toValue());
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
