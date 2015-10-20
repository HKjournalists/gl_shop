package com.glshop.net.ui.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.platform.api.DataConstants.SexType;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.MyProfileInfoModel;
import com.glshop.platform.api.profile.data.model.PersonalAuthInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 个人认证详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PersonalAuthInfoActivity extends BasicActivity {

	private static final String TAG = "PersonalAuthInfoActivity";

	private BuyTextItemView mItemPersonName;
	private BuyTextItemView mItemPersonSex;
	private BuyTextItemView mItemBirthDate;
	private BuyTextItemView mItemAddress;
	private BuyTextItemView mItemIDNo;
	private BuyTextItemView mItemSignOrg;
	private BuyTextItemView mItemLimitRange;

	private PersonalAuthInfoModel mAuthDetailInfo;
	/** 用户资料信息 */
	private MyProfileInfoModel info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_auth_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.auth_detail_info);

		mItemPersonName = getView(R.id.ll_item_personal_name);
		mItemPersonSex = getView(R.id.ll_item_personal_sex);
		mItemBirthDate = getView(R.id.ll_item_birth_date);
		mItemAddress = getView(R.id.ll_item_addr);
		mItemIDNo = getView(R.id.ll_item_id_no);
		mItemSignOrg = getView(R.id.ll_item_sign_org);
		mItemLimitRange = getView(R.id.ll_item_limit_range);

		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_reauth).setOnClickListener(this);
	}

	private void initData() {
		mAuthDetailInfo = (PersonalAuthInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO);
		info = (MyProfileInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_INFO);
		if(info!=null){
			getView(R.id.btn_reauth).setVisibility(View.VISIBLE);
		}
		
		if (mAuthDetailInfo != null) {
			mItemPersonName.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.personalName) ? mAuthDetailInfo.personalName : getString(R.string.data_empty));
			if (mAuthDetailInfo.setType == SexType.FEMALE) {
				mItemPersonSex.setContentText(getString(R.string.sex_female));
			} else {
				mItemPersonSex.setContentText(getString(R.string.sex_male));
			}
			mItemBirthDate.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.birthDate) ? mAuthDetailInfo.birthDate : getString(R.string.data_empty));
			mItemAddress.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.address) ? mAuthDetailInfo.address : getString(R.string.data_empty));
			mItemIDNo.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.idNo) ? mAuthDetailInfo.idNo : getString(R.string.data_empty));
			mItemSignOrg.setContentText(StringUtils.isNotEmpty(mAuthDetailInfo.signOrg) ? mAuthDetailInfo.signOrg : getString(R.string.data_empty));
			if (StringUtils.isNotEmpty(mAuthDetailInfo.limitStartRange) && StringUtils.isNotEmpty(mAuthDetailInfo.limitEndRange)) {
				mItemLimitRange.setContentText(mAuthDetailInfo.limitStartRange + "-" + mAuthDetailInfo.limitEndRange);
			} else {
				mItemLimitRange.setContentText(getString(R.string.data_empty));
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		case R.id.btn_reauth:
			Intent intent = new Intent(this, ProfileAuthActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_AUTH_INFO, getAuthInfo());
			startActivity(intent);
			break;
		}
	}
	
	private AuthInfoModel getAuthInfo() {
		AuthInfoModel authInfo = new AuthInfoModel();
		authInfo.profileType = info.profileType;
		if (info.profileImgList != null) {
			authInfo.profileImgList = info.profileImgList;
		}
		if (info.defaultContact != null) {
			authInfo.contactInfo = (ContactInfoModel) info.defaultContact.clone();
		}
		if (info.defaultAddr != null) {
			authInfo.addrInfo = (AddrInfoModel) info.defaultAddr.clone();
		}
		if (info.defaultCompanyIntro != null) {
			authInfo.introInfo = (CompanyIntroInfoModel) info.defaultCompanyIntro.clone();
		}
		return authInfo;
	}

}
