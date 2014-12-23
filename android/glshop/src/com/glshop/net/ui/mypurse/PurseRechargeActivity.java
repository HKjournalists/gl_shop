package com.glshop.net.ui.mypurse;

import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.ProtocolConstants;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.browser.BrowseProtocolActivity;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 钱包充值(包括保证金和货款充值)界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseRechargeActivity extends BasicActivity implements IMenuCallback {

	private LinearLayout llRechargeType;
	private LinearLayout llShipRecharge;
	private LinearLayout llCompanyRecharge;
	private CheckedTextView mCkbTvAgreeProtocol;

	private TextView mTvRechargeType;
	private TextView mTvCompanyDeposit;
	private EditText mEtWeight;

	private MenuDialog menuRechargeType;

	private PurseType purseType = PurseType.DEPOSIT;
	private ProfileType profileType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_recharge);
		initView();
		initData();
	}

	private void initView() {
		llRechargeType = getView(R.id.ll_recharge_type);
		llShipRecharge = getView(R.id.ll_ship_recharge);
		llCompanyRecharge = getView(R.id.ll_company_recharge);
		mTvRechargeType = getView(R.id.tv_recharge_type);
		mTvCompanyDeposit = getView(R.id.tv_deposit);
		mCkbTvAgreeProtocol = getView(R.id.chkTv_agree_protocol);
		mEtWeight = getView(R.id.et_weight);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_recharge);
		getView(R.id.ll_agree_protocol).setOnClickListener(this);
		getView(R.id.tv_protocol_detail).setOnClickListener(this);
		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		profileType = GlobalConfig.getInstance().getProfileType();
		if (profileType == null) {
			profileType = ProfileType.COMPANY;
			llRechargeType.setOnClickListener(this);
		} else {
			getView(R.id.iv_profile_edit).setVisibility(View.GONE);
		}

		if (profileType == ProfileType.SHIP) {
			llShipRecharge.setVisibility(View.VISIBLE);
			llCompanyRecharge.setVisibility(View.GONE);
		} else {
			llShipRecharge.setVisibility(View.GONE);
			llCompanyRecharge.setVisibility(View.VISIBLE);
			mTvCompanyDeposit.setText(StringUtils.getCashNumber(String.valueOf(SysCfgUtils.getCompanyDeposit(this))));
		}
		if (purseType == PurseType.DEPOSIT) {
			((TextView) getView(R.id.tv_recharge_tips)).setText(R.string.deposit_recharge_security_tips);
		} else {
			((TextView) getView(R.id.tv_recharge_tips)).setText(R.string.payment_recharge_security_tips);
		}
		updateRechargeType();
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

	private void showBuyTypeMenu() {
		closeMenuDialog(menuRechargeType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.profile_type)));
		menuRechargeType = new MenuDialog(this, menu, this, true, new MenuItemInfo(mTvRechargeType.getText().toString()));
		menuRechargeType.setMenuType(GlobalMessageType.MenuType.SELECT_AUTH_TYPE);
		menuRechargeType.setTitle(getString(R.string.menu_title_select_auth_type));
		menuRechargeType.show();
	}

	private void closeMenuDialog(Dialog menu) {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_recharge_type:
			showBuyTypeMenu();
			break;

		case R.id.ll_agree_protocol:
			mCkbTvAgreeProtocol.toggle();
			getView(R.id.btn_next_step).setEnabled(mCkbTvAgreeProtocol.isChecked());
			break;
		case R.id.tv_protocol_detail:
			intent = new Intent(this, BrowseProtocolActivity.class);
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, "协议内容");
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, ProtocolConstants.RECHARGE_PROTOCOL_URL);
			startActivity(intent);
			break;

		case R.id.btn_next_step:
			float rechargeMoney = 0;
			if (profileType == ProfileType.SHIP) {
				if (StringUtils.isNotEmpty(mEtWeight.getText().toString().trim())) {
					rechargeMoney = SysCfgUtils.calRechargeMoney(this, profileType, Float.parseFloat(mEtWeight.getText().toString().trim()));
					if (rechargeMoney == 0) {
						rechargeMoney = SysCfgUtils.getCompanyDeposit(this);
					}
				} else {
					showToast("重量不能为空");
					return;
				}
			} else {
				rechargeMoney = SysCfgUtils.getCompanyDeposit(this);
			}
			intent = new Intent(this, SelectRechargeTypeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, rechargeMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.RECHARGE.toValue());
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	public void onConfirm(Object obj) {

	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_AUTH_TYPE:
			String selectType = menuRechargeType.getAdapter().getItem(position).menuText;
			mTvRechargeType.setText(selectType);
			profileType = ProfileType.convert(position);
			if (profileType == ProfileType.SHIP) {
				llShipRecharge.setVisibility(View.VISIBLE);
				llCompanyRecharge.setVisibility(View.GONE);
				mEtWeight.setText("");
			} else {
				llShipRecharge.setVisibility(View.GONE);
				llCompanyRecharge.setVisibility(View.VISIBLE);
				mTvCompanyDeposit.setText(StringUtils.getCashNumber(String.valueOf(SysCfgUtils.getCompanyDeposit(this))));
			}
			break;
		}
	}

	private void updateRechargeType() {
		if (profileType != null) {
			switch (profileType) {
			case COMPANY:
				mTvRechargeType.setText(getString(R.string.profile_type_company));
				break;
			case PEOPLE:
				mTvRechargeType.setText(getString(R.string.profile_type_people));
				break;
			case SHIP:
				mTvRechargeType.setText(getString(R.string.profile_type_ship));
				break;
			}
		}
	}

}
