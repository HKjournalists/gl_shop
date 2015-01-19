package com.glshop.net.ui.myprofile;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.mycontract.CompanyEvaluationListActivity;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.profile.data.model.OtherProfileInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 交易另一方的资料详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class OtherProfileActivity extends BasicActivity {

	private TextView mTvCompanyName;
	private TextView mTvTradeNumber;
	private TextView mTvTurnoverRate;
	private RatingBar mRbSatisfaction;
	private RatingBar mRbCredit;

	private TextView mTvContactName;
	private TextView mTvContactTelephone;
	private TextView mTvContactFixedPhone;
	private TextView mTvCompanyIntro;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;
	private ImageView mIvItemCompanyPic1;
	private ImageView mIvItemCompanyPic2;
	private ImageView mIvItemCompanyPic3;

	private TextView mTvAddrDetail;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	private String companyId;

	private OtherProfileInfoModel info;

	private IProfileLogic mProfileLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_profile);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_other_profile_info);

		((TextView) getView(R.id.tv_commmon_title)).setText("对方资料");

		mTvCompanyName = getView(R.id.profile_name);
		mTvTradeNumber = getView(R.id.tv_trade_number);
		mTvTurnoverRate = getView(R.id.tv_turnover_rate);
		mRbSatisfaction = getView(R.id.rb_satisfaction);
		mRbCredit = getView(R.id.rb_credit);

		mTvContactName = getView(R.id.tv_contact_name);
		mTvContactTelephone = getView(R.id.tv_contact_telephone);
		mTvContactFixedPhone = getView(R.id.tv_contact_fixed_phone);
		mTvAddrDetail = getView(R.id.tv_default_addr_detail);
		mTvCompanyIntro = getView(R.id.tv_company_intro);

		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);

		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);
		mIvItemCompanyPic1 = getView(R.id.iv_item_company_pic_1);
		mIvItemCompanyPic2 = getView(R.id.iv_item_company_pic_2);
		mIvItemCompanyPic3 = getView(R.id.iv_item_company_pic_3);

		getView(R.id.iv_contact_name_star).setVisibility(View.GONE);
		getView(R.id.iv_contact_phone_star).setVisibility(View.GONE);
		getView(R.id.iv_contact_landline_star).setVisibility(View.GONE);
		getView(R.id.btn_contact_mgr).setVisibility(View.GONE);
		getView(R.id.btn_addr_mgr).setVisibility(View.GONE);
		getView(R.id.btn_company_mgr).setVisibility(View.GONE);

		getView(R.id.btn_view_evaluation).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_3).setOnClickListener(this);
	}

	private void initData() {
		companyId = (String) getIntent().getStringExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_ID);
		if (StringUtils.isNotEmpty(companyId)) {
			updateDataStatus(DataStatus.LOADING);
			mProfileLogic.getOtherProfileInfo(companyId);
		} else {
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getOtherProfileInfo(companyId);
	}

	private void updateUI() {
		mTvCompanyName.setText(StringUtils.isNotEmpty(info.companyName) ? info.companyName : getString(R.string.default_company_name));
		updateAuthInfo();
		updateEvaInfo();
		updateContactInfo();
		updateDischargeAddrInfo();
		updateCompanyIntroInfo();
		updateMgrButton();
	}

	/**
	 * 更新认证状态信息
	 */
	private void updateAuthInfo() {
		/*switch (info.authStatusType) {
		case AUTHING:
			mTvAuthStatus.setText("审核中");
			mBtnDoAuth.setVisibility(View.GONE);
			mBtnRepeatAuth.setVisibility(View.GONE);
			mBtnViewAuth.setVisibility(View.GONE);
			break;
		case UN_AUTH:
		case AUTH_FAILED:
			mTvAuthStatus.setText("未认证");
			mBtnDoAuth.setVisibility(View.VISIBLE);
			mBtnRepeatAuth.setVisibility(View.GONE);
			mBtnViewAuth.setVisibility(View.GONE);
			break;
		case AUTH_SUCCESS:
			mTvAuthStatus.setText("已认证");
			mBtnDoAuth.setVisibility(View.GONE);
			mBtnRepeatAuth.setVisibility(View.VISIBLE);
			mBtnViewAuth.setVisibility(View.VISIBLE);
			break;
		}*/
	}

	/**
	 * 更新评价信息
	 */
	private void updateEvaInfo() {
		mTvTradeNumber.setText(String.valueOf(info.tradeNumber));
		mTvTurnoverRate.setText(StringUtils.getPercent(info.turnoverRate));
		mRbSatisfaction.setRating(info.satisfactionPer);
		mRbCredit.setRating(info.sincerityPer);
	}

	/**
	 * 更新联系人信息
	 */
	private void updateContactInfo() {
		if (info != null && info.defaultContact != null) {
			ContactInfoModel contact = info.defaultContact;
			mTvContactName.setText(StringUtils.isNotEmpty(contact.name) ? contact.name : getString(R.string.data_empty));
			mTvContactTelephone.setText(StringUtils.isNotEmpty(contact.telephone) ? contact.telephone : getString(R.string.data_empty));
			mTvContactFixedPhone.setText(StringUtils.isNotEmpty(contact.fixPhone) ? contact.fixPhone : getString(R.string.data_empty));
		}
	}

	/**
	 * 更新卸货地址信息
	 */
	private void updateDischargeAddrInfo() {
		if (info != null) {
			AddrInfoModel addr = info.defaultAddr;
			mTvAddrDetail.setText(StringUtils.isNotEmpty(addr.deliveryAddrDetail) ? addr.deliveryAddrDetail : getString(R.string.data_empty));
			mItemPortWaterDepth.setContentText(addr.uploadPortWaterDepth != 0 ? String.valueOf(addr.uploadPortWaterDepth) : getString(R.string.data_empty));
			mItemPortShipTon.setContentText(addr.shippingTon != 0 ? String.valueOf(addr.shippingTon) : getString(R.string.data_empty));

			List<ImageInfoModel> imgUrl = addr.addrImageList;
			if (BeanUtils.isEmpty(imgUrl)) {
				mIvItemAddrPic1.setVisibility(View.VISIBLE);
				mIvItemAddrPic2.setVisibility(View.INVISIBLE);
				mIvItemAddrPic3.setVisibility(View.INVISIBLE);
			} else {
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemAddrPic1.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(0).cloudThumbnailUrl, mIvItemAddrPic1, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				if (count >= 2) {
					mIvItemAddrPic2.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(1).cloudThumbnailUrl, mIvItemAddrPic2, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemAddrPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemAddrPic3.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(2).cloudThumbnailUrl, mIvItemAddrPic3, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemAddrPic3.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/**
	 * 更新企业介绍信息
	 */
	private void updateCompanyIntroInfo() {
		if (info != null && info.defaultCompanyIntro != null) {
			CompanyIntroInfoModel intro = info.defaultCompanyIntro;
			mTvCompanyIntro.setText(StringUtils.isNotEmpty(intro.introduction) ? intro.introduction : getString(R.string.data_empty));

			List<ImageInfoModel> imgUrl = intro.imgList;
			if (BeanUtils.isEmpty(imgUrl)) {
				mIvItemCompanyPic1.setVisibility(View.VISIBLE);
				mIvItemCompanyPic2.setVisibility(View.INVISIBLE);
				mIvItemCompanyPic3.setVisibility(View.INVISIBLE);
			} else {
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemCompanyPic1.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(0).cloudThumbnailUrl, mIvItemCompanyPic1, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				if (count >= 2) {
					mIvItemCompanyPic2.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(1).cloudThumbnailUrl, mIvItemCompanyPic2, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemCompanyPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemCompanyPic3.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(2).cloudThumbnailUrl, mIvItemCompanyPic3, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemCompanyPic3.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/**
	 * 更新管理按钮状态
	 */
	private void updateMgrButton() {
		getView(R.id.btn_contact_mgr).setVisibility(View.INVISIBLE);
		getView(R.id.btn_addr_mgr).setVisibility(View.INVISIBLE);
		getView(R.id.btn_company_mgr).setVisibility(View.INVISIBLE);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ProfileMessageType.MSG_GET_OTHER_PROFILE_INFO_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_GET_OTHER_PROFILE_INFO_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			info = (OtherProfileInfoModel) respInfo.data;
			if (info != null) {
				updateDataStatus(DataStatus.NORMAL);
				updateUI();
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_view_evaluation:
			Intent intent = new Intent(this, CompanyEvaluationListActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_ID, companyId);
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		case R.id.iv_item_addr_pic_1:
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(0));
			}
			break;
		case R.id.iv_item_addr_pic_2:
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(1));
			}
			break;
		case R.id.iv_item_addr_pic_3:
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(2));
			}
			break;
		case R.id.iv_item_company_pic_1:
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(0));
			}
			break;
		case R.id.iv_item_company_pic_2:
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(1));
			}
			break;
		case R.id.iv_item_company_pic_3:
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(2));
			}
		}

	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
	}

}
