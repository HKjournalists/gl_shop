package com.glshop.net.ui.myprofile;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.platform.api.profile.data.model.CompanyAuthInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 企业认证详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class CompanyAuthInfoActivity extends BasicActivity {

	private static final String TAG = "CompanyAuthInfoActivity";

	private BuyTextItemView mItemCompanyName;
	private BuyTextItemView mItemRegisteAddr;
	private BuyTextItemView mItemRegisteDatetime;
	private BuyTextItemView mItemRegisteNo;
	private BuyTextItemView mItemLawPeople;
	private BuyTextItemView mItemRegisteOrg;
	private BuyTextItemView mItemCompanyType;

	private CompanyAuthInfoModel mAuthDetailInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_auth_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.auth_detail_info);

		mItemCompanyName = getView(R.id.ll_item_company_name);
		mItemRegisteAddr = getView(R.id.ll_item_registe_addr);
		mItemRegisteDatetime = getView(R.id.ll_item_registe_datetime);
		mItemRegisteNo = getView(R.id.ll_item_registe_no);
		mItemLawPeople = getView(R.id.ll_item_company_law_people);
		mItemRegisteOrg = getView(R.id.ll_item_register_org);
		mItemCompanyType = getView(R.id.ll_item_register_company_type);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mAuthDetailInfo = (CompanyAuthInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO);
		if (mAuthDetailInfo != null) {
			mItemCompanyName.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.companyName) ? mAuthDetailInfo.companyName : getString(R.string.data_empty));
			mItemRegisteAddr.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.registeAddr) ? mAuthDetailInfo.registeAddr : getString(R.string.data_empty));
			mItemRegisteDatetime.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.registeDatetime) ? mAuthDetailInfo.registeDatetime : getString(R.string.data_empty));
			mItemRegisteNo.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.registeNo) ? mAuthDetailInfo.registeNo : getString(R.string.data_empty));
			mItemLawPeople.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.lawPeople) ? mAuthDetailInfo.lawPeople : getString(R.string.data_empty));
			mItemRegisteOrg.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.registerOrg) ? mAuthDetailInfo.registerOrg : getString(R.string.data_empty));
			mItemCompanyType.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.companyType) ? mAuthDetailInfo.companyType : getString(R.string.data_empty));
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
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
