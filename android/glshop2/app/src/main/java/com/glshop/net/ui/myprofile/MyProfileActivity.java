package com.glshop.net.ui.myprofile;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.mypurse.PurseRechargeActivity;
import com.glshop.net.ui.user.FindPwdActivity;
import com.glshop.net.utils.EnumUtil;
import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.profile.data.model.MyProfileInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的资料详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MyProfileActivity extends BasicActivity {

	private static final String TAG = "MyProfileActivity";

	private ScrollView mScrollView;

	private TextView mTvProfileName;
	private TextView mTvProfileAccount;

	private TextView mTvAuthStatus;
	private Button mBtnDoAuth;
	private Button mBtnRepeatAuth;
	private Button mBtnViewAuth;

	private TextView mTvDepositStatus;
	private Button mBtnRecharge;

	private TextView mTvContactName;
	private TextView mTvContactTelephone;
	private TextView mTvContactFixedPhone;
	private TextView mTvCompanyIntroTitle;
	private TextView mTvCompanyIntro;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;
	private ImageView mIvItemCompanyPic1;
	private ImageView mIvItemCompanyPic2;
	private ImageView mIvItemCompanyPic3;
	
	private ImageButton iconAuth;
	private ImageButton iconDeposit;

	private TextView mTvAddrDetail;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	/** 注销用户确认对话框 */
	private ConfirmDialog mExitConfirmDialog;

	/** 是否从钱包界面进入个人资料页面 */
	private boolean isFromPurse = false;

	/** 用户资料信息 */
	private MyProfileInfoModel info;

	private IProfileLogic mProfileLogic;
	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		initView();
		initData();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Logger.e(TAG, "onNewIntent");
		mScrollView.scrollTo(0, 0);
		mProfileLogic.getMyProfileInfo(getCompanyId());
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_my_profile_info);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_profile);
		mScrollView = getView(R.id.ll_my_profile_container);
		
		iconAuth = getView(R.id.tv_auth_icon);
		iconDeposit = getView(R.id.tv_deposit_icon);
		
		
		mTvProfileName = getView(R.id.profile_name);
		mTvProfileAccount = getView(R.id.profile_account);

		mTvAuthStatus = getView(R.id.tv_auth_status);
		mBtnDoAuth = getView(R.id.btn_to_auth);
		mBtnRepeatAuth = getView(R.id.btn_repeat_auth);
		mBtnViewAuth = getView(R.id.btn_view_auth_detail);

		mTvDepositStatus = getView(R.id.tv_deposit_status);
		mBtnRecharge = getView(R.id.btn_to_recharge);

		mTvContactName = getView(R.id.tv_contact_name);
		mTvContactTelephone = getView(R.id.tv_contact_telephone);
		mTvContactFixedPhone = getView(R.id.tv_contact_fixed_phone);
		mTvAddrDetail = getView(R.id.tv_default_addr_detail);
		mTvCompanyIntroTitle = getView(R.id.tv_company_intro_title);
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

		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_contact_mgr).setOnClickListener(this);
		getView(R.id.btn_addr_mgr).setOnClickListener(this);
		getView(R.id.btn_company_mgr).setOnClickListener(this);
		getView(R.id.btn_to_auth).setOnClickListener(this);
		getView(R.id.btn_repeat_auth).setOnClickListener(this);
		getView(R.id.btn_view_auth_detail).setOnClickListener(this);
		getView(R.id.btn_to_recharge).setOnClickListener(this);
		getView(R.id.btn_modify_pwd).setOnClickListener(this);
		getView(R.id.btn_exit).setOnClickListener(this);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_company_pic_3).setOnClickListener(this);
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getMyProfileInfo(getCompanyId());
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getMyProfileInfo(getCompanyId());
	}

	private void updateUI() {
		if (StringUtils.isNotEmpty(getCompanyName())) {
			mTvProfileName.setText(getCompanyName());
		} else {
			mTvProfileName.setVisibility(View.GONE);
		}
		if(!TextUtils.isEmpty(info.companyName)){
			mTvProfileName.setText(info.companyName);
			GlobalConfig.getInstance().setCompanyName(info.companyName);
			mTvProfileName.setVisibility(View.VISIBLE);
		}
		mTvProfileAccount.setText(GlobalConfig.getInstance().getUserAccount());
		updateAuthInfo();
		updateDepositInfo();
		updateContactInfo();
		updateDischargeAddrInfo();
		updateCompanyIntroInfo();
		updateMgrButton();
	}

	/**
	 * 更新认证状态信息
	 */
	private void updateAuthInfo() {
		if (info.authStatusType != null) {
			switch (info.authStatusType) {
			case AUTHING:
				mTvAuthStatus.setText(EnumUtil.parseAuthType(this, info.authStatusType));
				mBtnDoAuth.setVisibility(View.GONE);
				mBtnRepeatAuth.setVisibility(View.GONE);
				mBtnViewAuth.setVisibility(View.GONE);
				mTvCompanyIntroTitle.setText(R.string.no_auth_type_company_intro);
				break;
			case UN_AUTH:
			case AUTH_FAILED:
				mTvAuthStatus.setText(EnumUtil.parseAuthType(this, info.authStatusType));
				mBtnDoAuth.setVisibility(View.VISIBLE);
				mBtnRepeatAuth.setVisibility(View.GONE);
				mBtnViewAuth.setVisibility(View.GONE);
				mTvCompanyIntroTitle.setText(R.string.no_auth_type_company_intro);
				break;
			case AUTH_SUCCESS:
				mTvAuthStatus.setText(EnumUtil.parseAuthType(this, info.authStatusType));
				mBtnDoAuth.setVisibility(View.GONE);
				mBtnRepeatAuth.setVisibility(View.GONE); // 已认证则屏蔽重新认证入口
				mBtnViewAuth.setVisibility(View.VISIBLE);
				iconAuth.setImageResource(R.drawable.ic_auth);
				//显示类型
				if (info.profileType != null) {
					switch (info.profileType) {
					case COMPANY:
						mTvCompanyIntroTitle.setText(R.string.auth_type_company_intro);
						break;
					case PEOPLE:
//						mBtnRepeatAuth.setVisibility(View.VISIBLE); // 已认证则屏蔽重新认证入口
						mTvCompanyIntroTitle.setText(R.string.auth_type_person_intro);
						break;
					case SHIP:
						mTvCompanyIntroTitle.setText(R.string.auth_type_ship_intro);
						break;
					}
				}
				break;
			}
		} else {
			mTvCompanyIntroTitle.setText(R.string.no_auth_type_company_intro);
		}
	}

	/**
	 * 更新保证金状态信息
	 */
	private void updateDepositInfo() {
		if (info.depositStatusType != null) {
			switch (info.depositStatusType) {
			case RECHARGE_SUCCESS:
				iconDeposit.setImageResource(R.drawable.ic_deposit);
				mTvDepositStatus.setText("已缴纳");
				mBtnRecharge.setVisibility(View.GONE);
				break;
			case UN_RECHARGE:
				mTvDepositStatus.setText("未缴纳");
				mBtnRecharge.setVisibility(View.VISIBLE);
				break;
			}
		}
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
		if (info != null && info.defaultAddr != null) {
			AddrInfoModel addr = info.defaultAddr;
			if (StringUtils.isNEmpty(addr.areaName) && StringUtils.isNEmpty(addr.deliveryAddrDetail)) {
				mTvAddrDetail.setText(getString(R.string.data_empty));
			} else {
				mTvAddrDetail.setText(addr.areaName + addr.deliveryAddrDetail);
			}
			mItemPortWaterDepth.setContentText(addr.uploadPortWaterDepth != 0 ? String.valueOf(addr.uploadPortWaterDepth) : getString(R.string.data_empty));
			mItemPortShipTon.setContentText(addr.shippingTon != 0 ? String.valueOf(addr.shippingTon) : getString(R.string.data_empty));

			List<ImageInfoModel> imgUrl = addr.addrImageList;
			if (BeanUtils.isEmpty(imgUrl)) {
				getView(R.id.ll_addr_pic).setVisibility(View.GONE);
			} else {
				getView(R.id.ll_addr_pic).setVisibility(View.VISIBLE);
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemAddrPic1.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(0) != null ? imgUrl.get(0).cloudThumbnailUrl : "", mIvItemAddrPic1, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				if (count >= 2) {
					mIvItemAddrPic2.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(1) != null ? imgUrl.get(1).cloudThumbnailUrl : "", mIvItemAddrPic2, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemAddrPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemAddrPic3.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(2) != null ? imgUrl.get(2).cloudThumbnailUrl : "", mIvItemAddrPic3, IMAGE_DEFAULT, IMAGE_FAILED);
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
				getView(R.id.ll_company_pic).setVisibility(View.GONE);
			} else {
				getView(R.id.ll_company_pic).setVisibility(View.VISIBLE);
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
		if (info.authStatusType != null) {
			getView(R.id.btn_contact_mgr).setVisibility(info.authStatusType == AuthStatusType.AUTH_SUCCESS ? View.VISIBLE : View.INVISIBLE);
			getView(R.id.btn_addr_mgr).setVisibility(info.authStatusType == AuthStatusType.AUTH_SUCCESS ? View.VISIBLE : View.INVISIBLE);
			getView(R.id.btn_company_mgr).setVisibility(info.authStatusType == AuthStatusType.AUTH_SUCCESS ? View.VISIBLE : View.INVISIBLE);
		} else {
			getView(R.id.btn_contact_mgr).setVisibility(View.INVISIBLE);
			getView(R.id.btn_addr_mgr).setVisibility(View.INVISIBLE);
			getView(R.id.btn_company_mgr).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ProfileMessageType.MSG_GET_MY_PROFILE_INFO_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_GET_MY_PROFILE_INFO_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.UserMessageType.MSG_LOGOUT_SUCCESS:
			onLogoutSuccess();
			break;
		case GlobalMessageType.UserMessageType.MSG_LOGOUT_FAILED:
			onLogoutSuccess();
			//onLogoutFailed(message);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE:
			mProfileLogic.getMyProfileInfo(getCompanyId());
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			info = (MyProfileInfoModel) respInfo.data;
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

	private void onLogoutSuccess() {
		closeSubmitDialog();
		Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_SHOP);
		intent.putExtra(GlobalAction.UserAction.EXTRA_IS_USER_LOGOUT, true);
		startActivity(intent);
	}

	private void onLogoutFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.ProfileMessageType.MSG_GET_MY_PROFILE_INFO_FAILED:
				showToast(R.string.error_req_get_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_modify_pwd: // 修改密码
			intent = new Intent(this, FindPwdActivity.class);
			intent.putExtra(GlobalAction.UserAction.EXTRA_USER_ACCOUNT, getUserPhone());
			intent.putExtra(GlobalAction.UserAction.EXTRA_IS_MODIFY_PWD, true);
			startActivity(intent);
			break;
		case R.id.btn_exit: // 注销
			showExitDialog();
			break;
		case R.id.btn_to_auth: // 首次认证
			intent = new Intent(this, ProfileAuthActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_repeat_auth: // 重新认证
			intent = new Intent(this, ProfileAuthActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_AUTH_INFO, getAuthInfo());
			startActivity(intent);
			break;
		case R.id.btn_view_auth_detail: // 认证详情查看
			if (info != null && info.profileType != null) {
				switch (info.profileType) {
				case COMPANY:
					intent = new Intent(this, CompanyAuthInfoActivity.class);
					intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO, info.companyAuthInfo);
					break;
				case PEOPLE:
					intent = new Intent(this, PersonalAuthInfoActivity.class);
					intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO, info.personalAuthInfo);
					intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_INFO, info);
					break;
				case SHIP:
					intent = new Intent(this, ShipAuthInfoActivity.class);
					intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_USER_AUTH_INFO, info.shipAuthInfo);
					break;
				}
				startActivity(intent);
				//browseImage(info.authImgInfo);
			}
			break;
		case R.id.btn_to_recharge: // 缴纳保证金
			intent = new Intent(this, PurseRechargeActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_contact_mgr: // 联系人管理
			intent = new Intent(this, ContactListActivity.class);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTACT);
			break;
		case R.id.btn_addr_mgr: // 卸货地址管理
			intent = new Intent(this, DischargeAddrMgrActivity.class);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_SELECT_DISCHARGE_ADDRESS);
			break;
		case R.id.btn_company_mgr: // 企业管理
			intent = new Intent(this, CompanyInfoEditActivity.class);
			if (info.defaultCompanyIntro != null) {
				CompanyIntroInfoModel introInfo = (CompanyIntroInfoModel) info.defaultCompanyIntro.clone();
				intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_INTRO_INFO, introInfo);
			}
			if(mTvCompanyIntroTitle!=null){
				intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_TYPE_INFO, mTvCompanyIntroTitle.getText().toString());
			}
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_COMPANY_INTRO);
			break;
		case R.id.iv_common_back: // 返回
			if (getParent() instanceof MainActivity) {
				if (isFormPurse()) {
					// 若从我的钱包进入到我的资料页面，按back键则回到我的钱包
					((MainActivity) getParent()).switchTabView(TabStatus.MY_PURSE);
				} else {
					((MainActivity) getParent()).switchTabView(TabStatus.SHOP);
				}
			} else {
				finish();
			}
			break;
		case R.id.iv_item_addr_pic_1: // 卸货地址照片浏览
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(0));
			}
			break;
		case R.id.iv_item_addr_pic_2: // 卸货地址照片浏览
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(1));
			}
			break;
		case R.id.iv_item_addr_pic_3: // 卸货地址照片浏览
			if (info.defaultAddr != null && BeanUtils.isNotEmpty(info.defaultAddr.addrImageList)) {
				browseImage(info.defaultAddr.addrImageList.get(2));
			}
			break;
		case R.id.iv_item_company_pic_1: // 企业照片浏览
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(0));
			}
			break;
		case R.id.iv_item_company_pic_2: // 企业照片浏览
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(1));
			}
			break;
		case R.id.iv_item_company_pic_3: // 企业照片浏览
			if (info.defaultCompanyIntro != null && BeanUtils.isNotEmpty(info.defaultCompanyIntro.imgList)) {
				browseImage(info.defaultCompanyIntro.imgList.get(2));
			}
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

	private void showExitDialog() {
		if (mExitConfirmDialog != null && mExitConfirmDialog.isShowing()) {
			mExitConfirmDialog.dismiss();
		}

		mExitConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mExitConfirmDialog.setContent(getString(R.string.user_exit_warning_tips));
		mExitConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog(getString(R.string.do_request_ing));
				mUserLogic.logout(GlobalConfig.getInstance().getUserAccount());
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mExitConfirmDialog.show();
	}

	public void setIsFormPurse(boolean isFromPurse) {
		this.isFromPurse = isFromPurse;
	}

	public boolean isFormPurse() {
		return isFromPurse;
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTACT:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					ContactInfoModel contactInfo = (ContactInfoModel) data.getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_CONTACT_INFO);
					if (contactInfo != null && info != null) {
						info.defaultContact = contactInfo;
						updateContactInfo();
					}
				}
			}
			break;
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_DISCHARGE_ADDRESS:
			if (resultCode == Activity.RESULT_OK) {
				// 暂时先做刷新处理
				mProfileLogic.getMyProfileInfo(getCompanyId());
			}
			break;
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_COMPANY_INTRO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					CompanyIntroInfoModel introInfo = (CompanyIntroInfoModel) data.getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_INTRO_INFO);
					if (introInfo != null && info != null) {
						info.defaultCompanyIntro = introInfo;
						updateCompanyIntroInfo();
					}
				}
			}
			break;
		}
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
