package com.glshop.net.ui.myprofile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.platform.api.profile.data.model.ShipAuthInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 船舶认证详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ShipAuthInfoActivity extends BasicActivity {

	private static final String TAG = "ShipAuthInfoActivity";

	private BuyTextItemView mItemShipName;
	private BuyTextItemView mItemShipPort;
	private BuyTextItemView mItemShipNo;
	private BuyTextItemView mItemShipCheckOrg;
	private BuyTextItemView mItemShipOwner;
	private BuyTextItemView mItemShipManager;
	private BuyTextItemView mItemShipType;
	private BuyTextItemView mItemShipCreateDate;
	private BuyTextItemView mItemShipTotalAmount;
	private BuyTextItemView mItemShipLoad;
	private BuyTextItemView mItemShipLength;
	private BuyTextItemView mItemShipWidth;
	private BuyTextItemView mItemShipHeight;
	private BuyTextItemView mItemShipTotalWaterHeight;
	private BuyTextItemView mItemShipMaterial;

	private ShipAuthInfoModel mAuthDetailInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_auth_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.auth_detail_info);

		mItemShipName = getView(R.id.ll_item_ship_name);
		mItemShipPort = getView(R.id.ll_item_ship_port);
		mItemShipNo = getView(R.id.ll_item_ship_no);
		mItemShipCheckOrg = getView(R.id.ll_item_ship_check_org);
		mItemShipOwner = getView(R.id.ll_item_ship_owner);
		mItemShipManager = getView(R.id.ll_item_ship_manager);
		mItemShipType = getView(R.id.ll_item_ship_type);
		mItemShipCreateDate = getView(R.id.ll_item_ship_create_date);
		mItemShipTotalAmount = getView(R.id.ll_item_ship_total_amount);
		mItemShipLoad = getView(R.id.ll_item_ship_load);
		mItemShipLength = getView(R.id.ll_item_ship_length);
		mItemShipWidth = getView(R.id.ll_item_ship_width);
		mItemShipHeight = getView(R.id.ll_item_ship_height);
		mItemShipTotalWaterHeight = getView(R.id.ll_item_ship_total_water_height);
		mItemShipMaterial = getView(R.id.ll_item_ship_material);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mAuthDetailInfo = (ShipAuthInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO);
		if (mAuthDetailInfo != null) {
			mItemShipName.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipName) ? mAuthDetailInfo.shipName : getString(R.string.data_empty));
			mItemShipPort.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipPort) ? mAuthDetailInfo.shipPort : getString(R.string.data_empty));
			mItemShipNo.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipNo) ? mAuthDetailInfo.shipNo : getString(R.string.data_empty));
			mItemShipCheckOrg.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipCheckOrg) ? mAuthDetailInfo.shipCheckOrg : getString(R.string.data_empty));
			mItemShipOwner.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipOwner) ? mAuthDetailInfo.shipOwner : getString(R.string.data_empty));
			mItemShipManager.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipOperator) ? mAuthDetailInfo.shipOperator : getString(R.string.data_empty));
			mItemShipType.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipType) ? mAuthDetailInfo.shipType : getString(R.string.data_empty));
			mItemShipCreateDate.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipCreateDate) ? mAuthDetailInfo.shipCreateDate : getString(R.string.data_empty));
			mItemShipTotalAmount.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipTotalAmount) ? mAuthDetailInfo.shipTotalAmount : getString(R.string.data_empty));
			mItemShipLoad.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipLoad) ? mAuthDetailInfo.shipLoad : getString(R.string.data_empty));
			mItemShipLength.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipLength) ? mAuthDetailInfo.shipLength : getString(R.string.data_empty));
			mItemShipWidth.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipWidth) ? mAuthDetailInfo.shipWidth : getString(R.string.data_empty));
			mItemShipHeight.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipHeight) ? mAuthDetailInfo.shipHeight : getString(R.string.data_empty));
			mItemShipTotalWaterHeight.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipTotalWaterHeight) ? mAuthDetailInfo.shipTotalWaterHeight : getString(R.string.data_empty));
			mItemShipMaterial.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.shipMaterial) ? mAuthDetailInfo.shipMaterial : getString(R.string.data_empty));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
