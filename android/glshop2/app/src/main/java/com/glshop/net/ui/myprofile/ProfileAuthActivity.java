package com.glshop.net.ui.myprofile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ProtocolConstants;
import com.glshop.net.common.GlobalConstants.TipActionBackType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.logic.model.ImageStatusInfo;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.browser.BrowseProtocolActivity;
import com.glshop.net.ui.mypurse.PurseRechargeActivity;
import com.glshop.net.ui.tips.AuthOperatorTipsActivity;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 认证界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ProfileAuthActivity extends BasicActivity implements IMenuCallback {

	private BuyTextItemView mItemAuthType;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	private TextView mTvCompanyIntroTitle;
	private TextView mTvCompanyPhotoTitle;

	private CheckedTextView mCkbTvAgreeProtocol;
	private MenuDialog menuAuthType;

	private TextView mTvContactName;
	private TextView mTvContactTelephone;
	private TextView mTvContactFixedPhone;

	private TextView mTvAddrDetail;
	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;

	private EditText mEtCompanyIntro;
	private View llItemPersonAuthPic;
	private ImageView mIvItemCompanyAuthPic;
	private ImageView mIvItemPersonAuthPic1;
	private ImageView mIvItemPersonAuthPic2;
	private ImageView mIvItemCompanyPic1;
	private ImageView mIvItemCompanyPic2;
	private ImageView mIvItemCompanyPic3;

	private ImageView mUploadImageView;

	private AuthInfoModel mAuthInfo;
	private ProfileType mProfileType;
	private List<ImageInfoModel> mProfileImgInfoList;
	private ContactInfoModel mDefaultContactInfo;
	private AddrInfoModel mSelectAddrInfo;
	private CompanyIntroInfoModel mCompanyIntroInfo;

	private int mUploadedPicCount = 0;
	private ImageStatusInfo[] mProfileImgStatusList = new ImageStatusInfo[2]; // 认证身份图片信息
	private ImageStatusInfo[] mCompanyImgStatusList = new ImageStatusInfo[3]; // 企业介绍图片信息
	private List<FileInfo> mFileList;
	private static final String TAG_PROFILE = "tag_profile";
	private static final String TAG_COMPANY = "tag_company";

	private boolean isRepeatAuth = false;

	private IProfileLogic mProfileLogic;
	private ITransferLogic mTransferLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_auth);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.do_auth);
		((TextView) getView(R.id.tv_contract_countdown_time)).setText(R.string.auth_tips);

		mItemAuthType = getView(R.id.ll_item_auth_type);
		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);
		mCkbTvAgreeProtocol = getView(R.id.chkTv_agree_protocol);
		mEtCompanyIntro = getView(R.id.et_company_intro);

		mTvContactName = getView(R.id.tv_contact_name);
		mTvContactTelephone = getView(R.id.tv_contact_telephone);
		mTvContactFixedPhone = getView(R.id.tv_contact_fixphone);

		mTvCompanyIntroTitle = getView(R.id.tv_company_intro_title);
		mTvCompanyPhotoTitle = getView(R.id.tv_company_photo_title);

		mTvAddrDetail = getView(R.id.tv_addr_detail);
		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);

		llItemPersonAuthPic = getView(R.id.ll_item_person_auth_pic);
		mIvItemCompanyAuthPic = getView(R.id.iv_item_auth_pic);
		mIvItemPersonAuthPic1 = getView(R.id.iv_item_auth_pic1);
		mIvItemPersonAuthPic2 = getView(R.id.iv_item_auth_pic2);
		mIvItemCompanyPic1 = getView(R.id.iv_item_company_pic_1);
		mIvItemCompanyPic2 = getView(R.id.iv_item_company_pic_2);
		mIvItemCompanyPic3 = getView(R.id.iv_item_company_pic_3);

		mIvItemCompanyAuthPic.setOnClickListener(this);
		mIvItemPersonAuthPic1.setOnClickListener(this);
		mIvItemPersonAuthPic2.setOnClickListener(this);
		mIvItemCompanyPic1.setOnClickListener(this);
		mIvItemCompanyPic2.setOnClickListener(this);
		mIvItemCompanyPic3.setOnClickListener(this);

		getView(R.id.btn_edit_contact).setOnClickListener(this);
		getView(R.id.btn_edit_addr).setOnClickListener(this);
		getView(R.id.ll_item_auth_type).setOnClickListener(this);
		getView(R.id.ll_agree_protocol).setOnClickListener(this);
		getView(R.id.btn_submit_auth).setOnClickListener(this);
		getView(R.id.tv_protocol_detail).setOnClickListener(this);
		getView(R.id.tv_view_image_demo).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
		
		findViewById(R.id.auth_company_root).setVisibility(View.GONE);
		findViewById(R.id.auth_addr_root).setVisibility(View.GONE);
	}

	private void initData() {
		initImgStatusInfo();
		mAuthInfo = (AuthInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_AUTH_INFO);
		if (mAuthInfo != null) {
			if (mAuthInfo.profileType != null) {
				mProfileType = mAuthInfo.profileType;
			}
			if (mAuthInfo.profileImgList != null) {
				mProfileImgInfoList = mAuthInfo.profileImgList;
			}
			if (mAuthInfo.contactInfo != null) {
				mDefaultContactInfo = mAuthInfo.contactInfo;
			}
			if (mAuthInfo.addrInfo != null) {
				mSelectAddrInfo = mAuthInfo.addrInfo;
			}
			if (mAuthInfo.introInfo != null) {
				mCompanyIntroInfo = mAuthInfo.introInfo;
			}
			updateProfileType();
			updateProfileImgInfo();
			updateAddrInfo();
			updateContactInfo();
			updateCompanyIntroInfo();
		}
	}

	/**
	 * 更新身份类型信息
	 */
	private void updateProfileType() {
		if (isRepeatAuth) {
			mItemAuthType.setActionIconVisible(false);
			mItemAuthType.setClickable(false);
		}
		if (mProfileType != null) {
			switch (mProfileType) {
			case COMPANY:
				mItemAuthType.setContentText(getString(R.string.auth_type_company));
				mTvCompanyIntroTitle.setText(R.string.auth_type_company_intro);
				mTvCompanyPhotoTitle.setText(R.string.auth_type_company_photo);
				mIvItemCompanyAuthPic.setVisibility(View.VISIBLE);
				llItemPersonAuthPic.setVisibility(View.GONE);
				break;
			case PEOPLE:
				mItemAuthType.setContentText(getString(R.string.auth_type_people));
				mTvCompanyIntroTitle.setText(R.string.auth_type_person_intro);
				mTvCompanyPhotoTitle.setText(R.string.auth_type_person_photo);
				mIvItemCompanyAuthPic.setVisibility(View.GONE);
				llItemPersonAuthPic.setVisibility(View.VISIBLE);
				break;
			case SHIP:
				mItemAuthType.setContentText(getString(R.string.auth_type_ship));
				mTvCompanyIntroTitle.setText(R.string.auth_type_ship_intro);
				mTvCompanyPhotoTitle.setText(R.string.auth_type_ship_photo);
				mIvItemCompanyAuthPic.setVisibility(View.VISIBLE);
				llItemPersonAuthPic.setVisibility(View.GONE);
				break;
			}
		}
		updateProfileImgInfo();
	}

	/**
	 * 更新认证图片信息
	 */
	private void updateProfileImgInfo() {
		if (mProfileType != null && mAuthInfo!=null && mAuthInfo.profileType == mProfileType) {
			updateProfileImgStatusInfo();
			switch (mProfileType) {
			case COMPANY:
			case SHIP:
				mIvItemCompanyAuthPic.setVisibility(View.VISIBLE);
				llItemPersonAuthPic.setVisibility(View.GONE);
				if (BeanUtils.isNotEmpty(mProfileImgInfoList)) {
					ImageLoaderManager.getIntance().display(this, mProfileImgInfoList.get(0).cloudThumbnailUrl, mIvItemCompanyAuthPic, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				break;
			case PEOPLE:
				mIvItemCompanyAuthPic.setVisibility(View.GONE);
				llItemPersonAuthPic.setVisibility(View.VISIBLE);
				if (BeanUtils.isNotEmpty(mProfileImgInfoList)) {
					ImageLoaderManager.getIntance().display(this, mProfileImgInfoList.get(0).cloudThumbnailUrl, mIvItemPersonAuthPic1, IMAGE_DEFAULT, IMAGE_FAILED);
					if (mProfileImgInfoList.size() > 1) {
						ImageLoaderManager.getIntance().display(this, mProfileImgInfoList.get(1).cloudThumbnailUrl, mIvItemPersonAuthPic2, IMAGE_DEFAULT, IMAGE_FAILED);
					}
				}
				break;
			}
		}
	}

	/**
	 * 更新联系人信息
	 */
	private void updateContactInfo() {
		if (mDefaultContactInfo != null) {
			mTvContactName.setText(StringUtils.isNotEmpty(mDefaultContactInfo.name) ? mDefaultContactInfo.name : getString(R.string.data_no_input));
			mTvContactTelephone.setText(StringUtils.isNotEmpty(mDefaultContactInfo.telephone) ? mDefaultContactInfo.telephone : getString(R.string.data_no_input));
			mTvContactFixedPhone.setText(StringUtils.isNotEmpty(mDefaultContactInfo.fixPhone) ? mDefaultContactInfo.fixPhone : getString(R.string.data_no_input));
		}
	}

	/**
	 * 更新卸货地址信息
	 */
	private void updateAddrInfo() {
		if (mSelectAddrInfo != null) {
			mTvAddrDetail.setText(StringUtils.isNotEmpty(mSelectAddrInfo.deliveryAddrDetail) ? mSelectAddrInfo.deliveryAddrDetail : getString(R.string.data_no_input));
			mItemPortWaterDepth.setContentText(mSelectAddrInfo.uploadPortWaterDepth != 0 ? String.valueOf(mSelectAddrInfo.uploadPortWaterDepth) : getString(R.string.data_no_input));
			mItemPortShipTon.setContentText(mSelectAddrInfo.shippingTon != 0 ? String.valueOf(mSelectAddrInfo.shippingTon) : getString(R.string.data_no_input));

			List<ImageInfoModel> imgUrl = mSelectAddrInfo.addrImageList;
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
		if (mCompanyIntroInfo != null) {
			mEtCompanyIntro.setText(StringUtils.isNotEmpty(mCompanyIntroInfo.introduction) ? mCompanyIntroInfo.introduction : getString(R.string.data_no_input));
			updateCompanyImgStatusInfo();
			List<ImageInfoModel> imgUrl = mCompanyIntroInfo.imgList;
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
				mUploadedPicCount = BeanUtils.isEmpty(imgUrl) ? 0 : imgUrl.size();
				updateCompanyImgStatusUI();
			}
		}
	}

	private void initImgStatusInfo() {
		for (int i = 0; i < mProfileImgStatusList.length; i++) {
			ImageStatusInfo info = new ImageStatusInfo();
			info.obj = TAG_PROFILE;
			mProfileImgStatusList[i] = info;
		}
		for (int i = 0; i < mCompanyImgStatusList.length; i++) {
			ImageStatusInfo info = new ImageStatusInfo();
			info.obj = TAG_COMPANY;
			mCompanyImgStatusList[i] = info;
		}
	}

	private void updateProfileImgStatusInfo() {
		if (mProfileType != null && BeanUtils.isNotEmpty(mProfileImgInfoList)) {
			switch (mProfileType) {
			case COMPANY:
			case SHIP:
				mProfileImgStatusList[0].cloudId = mProfileImgInfoList.get(0).cloudId;
				break;
			case PEOPLE:
				mProfileImgStatusList[0].cloudId = mProfileImgInfoList.get(0).cloudId;
				if (mProfileImgInfoList.size() > 1) {
					mProfileImgStatusList[1].cloudId = mProfileImgInfoList.get(1).cloudId;
				}
				break;
			}
		}
	}

	private void updateCompanyImgStatusInfo() {
		List<ImageInfoModel> imgUrl = mCompanyIntroInfo.imgList;
		if (BeanUtils.isNotEmpty(imgUrl)) {
			for (int i = 0; i < imgUrl.size(); i++) {
				if (imgUrl.get(i) != null) {
					mCompanyImgStatusList[i].cloudId = imgUrl.get(i).cloudId;
				}
			}
		}
	}

	private void updateCompanyImgStatusUI() {
		mIvItemCompanyPic1.setVisibility(View.VISIBLE);
		mIvItemCompanyPic2.setVisibility(mUploadedPicCount >= 1 ? View.VISIBLE : View.INVISIBLE);
		mIvItemCompanyPic3.setVisibility(mUploadedPicCount >= 2 ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case CommonMessageType.MSG_FILE_UPLOAD_SUCCESS: // 图片上传成功
			onUploadSuccess(respInfo);
			break;
		case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			onUploadFailed(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_SUBMIT_AUTH_INFO_SUCCESS: // 认证成功
			onSubmitSuccess();
			break;
		case GlobalMessageType.ProfileMessageType.MSG_SUBMIT_AUTH_INFO_FAILED: // 认证失败
			onSubmitFailed(respInfo);
			break;
		}
	}

	private void showProfileTypeMenu() {
		closeMenuDialog(menuAuthType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.auth_type)));
		menuAuthType = new MenuDialog(this, menu, this, true, new MenuItemInfo(mItemAuthType.getContentText()));
		menuAuthType.setMenuType(GlobalMessageType.MenuType.SELECT_AUTH_TYPE);
		menuAuthType.setTitle(getString(R.string.menu_title_select_auth_type));
		menuAuthType.show();
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
		case R.id.ll_item_auth_type:
			showProfileTypeMenu();
			break;
		case R.id.btn_edit_contact:
			intent = new Intent(this, ContactListActivity.class);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTACT);
			break;
		case R.id.btn_edit_addr:
			intent = new Intent(this, DischargeAddrSelectActivity.class);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_SELECT_DISCHARGE_ADDRESS);
			break;
		case R.id.ll_agree_protocol:
			mCkbTvAgreeProtocol.toggle();
			getView(R.id.btn_submit_auth).setEnabled(mCkbTvAgreeProtocol.isChecked());
			break;
		case R.id.tv_protocol_detail:
			intent = new Intent(this, BrowseProtocolActivity.class);
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, getString(R.string.user_auth_protocol_url));
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, ProtocolConstants.USER_AUTH_PROTOCOL_URL);
			startActivity(intent);
			break;
		case R.id.btn_submit_auth:
			submitAuthInfo();
			break;
		case R.id.iv_item_auth_pic:
		case R.id.iv_item_auth_pic1:
		case R.id.iv_item_auth_pic2:
		case R.id.iv_item_company_pic_1:
		case R.id.iv_item_company_pic_2:
		case R.id.iv_item_company_pic_3:
			mUploadImageView = (ImageView) v;
			showUploadPicTypeMenu();
			break;
		case R.id.tv_view_image_demo:
			if (mProfileType != null) {
				ImageInfoModel info = new ImageInfoModel();
				switch (mProfileType) {
				case COMPANY:
					info.resourceId = R.drawable.bg_demo_profile_company;
					break;
				case PEOPLE:
					info.resourceId = R.drawable.bg_demo_profile_people;
					break;
				case SHIP:
					info.resourceId = R.drawable.bg_demo_profile_ship;
					break;
				}
				browseImage(info);
			} else {
				showToast("请先选择认证类型!");
			}
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void submitAuthInfo() {
		if (checkArgs()) {
			if (needToUploadImage()) {// 有图片更新，则先上传图片
				uploadImage();
			} else {// 无图片更新，则直接提交
				List<String> profileIds = new ArrayList<String>();
				// 获取原有未修改的图片ID
				for (ImageStatusInfo info : mProfileImgStatusList) {
					if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
						profileIds.add(info.cloudId);
					}
				}
				List<String> companyIds = new ArrayList<String>();
				// 获取原有未修改的图片ID
				for (ImageStatusInfo info : mCompanyImgStatusList) {
					if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
						companyIds.add(info.cloudId);
					}
				}
				saveAuthInfo(profileIds, companyIds);
			}
		}
	}

	private boolean checkArgs() {
		if (!checkProfileImg()) {
			return false;
		} else if (mDefaultContactInfo == null) {
			showToast("联系人信息为空!");
			return false;
		}
		return true;
	}

	private boolean checkProfileImg() {
		if (mProfileType == null) {
			showToast("请选择认证类型!");
			return false;
		} else if ((mProfileType == ProfileType.COMPANY || mProfileType == ProfileType.SHIP)) {
			if (StringUtils.isEmpty(mProfileImgStatusList[0].filePath) && StringUtils.isEmpty(mProfileImgStatusList[0].cloudId)) {
				showToast("认证图片不能为空!");
				return false;
			}
		} else if (mProfileType == ProfileType.PEOPLE) {
			if (StringUtils.isEmpty(mProfileImgStatusList[0].filePath) && StringUtils.isEmpty(mProfileImgStatusList[0].cloudId)) {
				showToast("身份证正面图片不能为空!");
				return false;
			} else if (StringUtils.isEmpty(mProfileImgStatusList[1].filePath) && StringUtils.isEmpty(mProfileImgStatusList[1].cloudId)) {
				showToast("身份反面图片不能为空!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否有图片更新
	 * @return
	 */
	private boolean needToUploadImage() {
		for (ImageStatusInfo info : mProfileImgStatusList) {
			if (info.isModified) {
				return true;
			}
		}
		for (ImageStatusInfo info : mCompanyImgStatusList) {
			if (info.isModified) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 上传图片
	 */
	private void uploadImage() {
		mFileList = new ArrayList<FileInfo>();
		// 添加待上传的身份认证图片信息
		for (ImageStatusInfo info : mProfileImgStatusList) {
			if (info.isModified && StringUtils.isNotEmpty(info.filePath)) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.file = new File(info.filePath);
				fileInfo.obj = info.obj;
				mFileList.add(fileInfo);
			}
		}
		// 添加待上传的企业介绍图片信息
		for (ImageStatusInfo info : mCompanyImgStatusList) {
			if (info.isModified && StringUtils.isNotEmpty(info.filePath)) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.file = new File(info.filePath);
				fileInfo.obj = info.obj;
				mFileList.add(fileInfo);
			}
		}
		mInvoker = String.valueOf(System.currentTimeMillis());
		mTransferLogic.uploadFile(mInvoker, mFileList);
		showSubmitDialog();
	}

	private void onUploadSuccess(RespInfo respInfo) {
		String invoker = String.valueOf(respInfo.invoker);
		if (mInvoker.equals(invoker)) {
			List<String> profileIds = new ArrayList<String>();
			// 获取原有未修改的认证图片ID
			for (ImageStatusInfo info : mProfileImgStatusList) {
				if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
					profileIds.add(info.cloudId);
				}
			}
			// 获取新修改的认证图片ID
			for (FileInfo file : mFileList) {
				if (StringUtils.isNotEmpty(file.cloudId) && TAG_PROFILE.equals(file.obj)) {
					profileIds.add(file.cloudId);
				}
			}

			List<String> companyIds = new ArrayList<String>();
			// 获取原有未修改的企业介绍图片ID
			for (ImageStatusInfo info : mCompanyImgStatusList) {
				if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
					companyIds.add(info.cloudId);
				}
			}
			// 获取新修改的企业介绍图片ID
			for (FileInfo file : mFileList) {
				if (StringUtils.isNotEmpty(file.cloudId) && TAG_COMPANY.equals(file.obj)) {
					companyIds.add(file.cloudId);
				}
			}
			saveAuthInfo(profileIds, companyIds);
		}
	}

	private void saveAuthInfo(List<String> profileIdList, List<String> companyIdList) {
		if (BeanUtils.isNotEmpty(profileIdList)) {
			if (!isRepeatAuth) {
				mAuthInfo = new AuthInfoModel();
			}

			mAuthInfo.profileType = mProfileType;

			// 认证图片信息
			mAuthInfo.profileImgList = ImageInfoUtils.getImageInfo(profileIdList);

			// 联系人信息
			mAuthInfo.contactInfo = mDefaultContactInfo;

			// 卸货地址信息
			mAuthInfo.addrInfo = mSelectAddrInfo;

			// 企业介绍信息
			mCompanyIntroInfo = new CompanyIntroInfoModel();
			mCompanyIntroInfo.companyId = getCompanyId();
			mCompanyIntroInfo.introduction = mEtCompanyIntro.getText().toString();
			if (BeanUtils.isNotEmpty(companyIdList)) {
				// 企业介绍图片信息
				mCompanyIntroInfo.imgList = ImageInfoUtils.getImageInfo(companyIdList);
			}

			if (!needToUploadImage()) {
				showSubmitDialog();
			}

			mAuthInfo.introInfo = mCompanyIntroInfo;
			mProfileLogic.submitAuthInfo(mAuthInfo);
		} else {
			Logger.e(TAG, "args error: image id empty");
			showToast("参数错误，图片信息为空");
		}
	}

	private void onUploadFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onSubmitSuccess() {
		closeSubmitDialog();

		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE);

		Intent intent = new Intent(this, AuthOperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.user_auth_success);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_auth_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_auth_success_content);

		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_auth_success_action1_text);
		tipInfo.operatorTipActionClass1 = PurseRechargeActivity.class;
		//tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;

		tipInfo.operatorTipActionText2 = getString(R.string.operator_tips_auth_success_action2_text);
		tipInfo.operatorTipActionClass2 = MainActivity.class;
		//tipInfo.operatorTipAction2 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;

		tipInfo.operatorTipWarning = getString(R.string.operator_tips_auth_success_warning);
		tipInfo.backType = TipActionBackType.DO_ACTION2;

		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			case GlobalMessageType.ProfileMessageType.MSG_SUBMIT_AUTH_INFO_FAILED: // 认证失败
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onConfirm(int type, Object obj) {

	}

	@Override
	public void onCancel(int type) {

	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_AUTH_TYPE:
			mItemAuthType.setContentText(menuAuthType.getAdapter().getItem(position).menuText);
			ProfileType profileType = ProfileType.convert(position);
			if (mProfileType != profileType) {
				mProfileType = profileType;
				updateProfileType();
				resetImageInfo(mProfileImgStatusList[0], mProfileImgStatusList[1]);
				resetProfileImageView();
			}
			break;
		}
	}

	private void resetImageInfo(ImageStatusInfo... infoList) {
		if (BeanUtils.isNotEmpty(infoList)) {
			for (ImageStatusInfo info : infoList) {
				if (info != null) {
					info.cloudId = "";
					info.filePath = "";
					info.isModified = false;
				}
			}
		}
	}

	private void resetProfileImageView() {
		mIvItemCompanyAuthPic.setImageBitmap(null);
		mIvItemCompanyAuthPic.setBackgroundResource(R.drawable.ic_upload_pic);
		mIvItemPersonAuthPic1.setImageBitmap(null);
		mIvItemPersonAuthPic1.setBackgroundResource(R.drawable.ic_upload_profile_1);
		mIvItemPersonAuthPic2.setImageBitmap(null);
		mIvItemPersonAuthPic2.setBackgroundResource(R.drawable.ic_upload_profile_2);
	}

	@Override
	protected void onPictureSelect(Bitmap bitmap, String file) {
		Logger.e(TAG, "Bitmap = " + bitmap + ", file = " + file);
		if (mUploadImageView != null) {
			mUploadImageView.setImageBitmap(null);
			mUploadImageView.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
			if (isProfileImage()) {
				ImageStatusInfo imageInfo = null;
				if (mUploadImageView == mIvItemCompanyAuthPic) {
					imageInfo = mProfileImgStatusList[0];
				} else if (mUploadImageView == mIvItemPersonAuthPic1) {
					imageInfo = mProfileImgStatusList[0];
				} else if (mUploadImageView == mIvItemPersonAuthPic2) {
					imageInfo = mProfileImgStatusList[1];
				}
				if (imageInfo != null) {
					imageInfo.filePath = file;
					if (!imageInfo.isModified) {
						imageInfo.isModified = true;
					}
				}
			} else {
				int index = getSelectUploadIndex();
				ImageStatusInfo imageInfo = mCompanyImgStatusList[index];
				imageInfo.filePath = file;
				if (!imageInfo.isModified) {
					imageInfo.isModified = true;
					if (StringUtils.isEmpty(imageInfo.cloudId) && mUploadedPicCount < 3) {
						mUploadedPicCount++;
					}
					updateCompanyImgStatusUI();
				}
			}
		}
	}

	private boolean isProfileImage() {
		return mUploadImageView == mIvItemCompanyAuthPic || mUploadImageView == mIvItemPersonAuthPic1 || mUploadImageView == mIvItemPersonAuthPic2;
	}

	private int getSelectUploadIndex() {
		if (mUploadImageView == mIvItemCompanyPic1) {
			return 0;
		} else if (mUploadImageView == mIvItemCompanyPic2) {
			return 1;
		} else if (mUploadImageView == mIvItemCompanyPic3) {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTACT:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mDefaultContactInfo = (ContactInfoModel) data.getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_CONTACT_INFO);
					updateContactInfo();
				}
			}
			break;
		case GlobalMessageType.ActivityReqCode.REQ_SELECT_DISCHARGE_ADDRESS:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mSelectAddrInfo = (AddrInfoModel) data.getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_INFO);
					updateAddrInfo();
				}
			}
			break;
		}
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
		mTransferLogic = LogicFactory.getLogicByClass(ITransferLogic.class);
	}

}
