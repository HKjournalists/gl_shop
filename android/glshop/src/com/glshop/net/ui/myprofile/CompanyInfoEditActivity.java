package com.glshop.net.ui.myprofile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.logic.model.ImageStatusInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 企业信息管理界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class CompanyInfoEditActivity extends BasicActivity {

	private static final String TAG = "CompanyInfoEditActivity";

	private CompanyIntroInfoModel info;

	private Button mBtnSaveAddr;

	private EditText mEtIntroDetail;

	private ImageView mIvItemCompanyPic1;
	private ImageView mIvItemCompanyPic2;
	private ImageView mIvItemCompanyPic3;

	private ImageView mUploadImageView;

	private int mUploadedPicCount = 0;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<FileInfo> mFileList;

	private IProfileLogic mProfileLogic;
	private ITransferLogic mTransferLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_info_edit);
		initView();
		initData();
	}

	private void initView() {
		mBtnSaveAddr = getView(R.id.btn_commmon_action);
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.company_info_mgr);

		mBtnSaveAddr.setVisibility(View.VISIBLE);
		mBtnSaveAddr.setText(R.string.save);
		mBtnSaveAddr.setOnClickListener(this);

		mEtIntroDetail = getView(R.id.et_company_intro);

		mIvItemCompanyPic1 = getView(R.id.iv_item_product_pic_1);
		mIvItemCompanyPic2 = getView(R.id.iv_item_product_pic_2);
		mIvItemCompanyPic3 = getView(R.id.iv_item_product_pic_3);

		mIvItemCompanyPic1.setOnClickListener(this);
		mIvItemCompanyPic2.setOnClickListener(this);
		mIvItemCompanyPic3.setOnClickListener(this);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		info = (CompanyIntroInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_INTRO_INFO);
		if (info != null) {
			initImgStatusInfo();
			mEtIntroDetail.setText(info.introduction);

			updateImgStatusInfo();
			List<ImageInfoModel> imgUrl = info.imgList;
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
			mUploadedPicCount = BeanUtils.isEmpty(imgUrl) ? 0 : imgUrl.size();
			updateImgStatusUI();
		} else {
			showToast("Error");
			finish();
		}
	}

	private void initImgStatusInfo() {
		for (int i = 0; i < 3; i++) {
			mImgStatusList[i] = new ImageStatusInfo();
		}
	}

	private void updateImgStatusInfo() {
		List<ImageInfoModel> imgUrl = info.imgList;
		if (BeanUtils.isNotEmpty(imgUrl)) {
			for (int i = 0; i < imgUrl.size(); i++) {
				if (imgUrl.get(i) != null) {
					mImgStatusList[i].cloudId = imgUrl.get(i).cloudId;
				}
			}
		}
	}

	private void updateImgStatusUI() {
		mIvItemCompanyPic1.setVisibility(View.VISIBLE);
		mIvItemCompanyPic2.setVisibility(mUploadedPicCount >= 1 ? View.VISIBLE : View.INVISIBLE);
		mIvItemCompanyPic3.setVisibility(mUploadedPicCount >= 2 ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case CommonMessageType.MSG_FILE_UPLOAD_SUCCESS: // 图片上传成功
			onUploadSuccess(respInfo);
			break;
		case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			onUploadFailed(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_UPDATE_COMPANY_INTRO_INFO_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_UPDATE_COMPANY_INTRO_INFO_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	private void onUploadSuccess(RespInfo respInfo) {
		String invoker = String.valueOf(respInfo.invoker);
		if (mInvoker.equals(invoker)) {
			List<String> ids = new ArrayList<String>();
			// 获取原有未修改的图片ID
			for (ImageStatusInfo info : mImgStatusList) {
				if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
					ids.add(info.cloudId);
				}
			}
			// 获取新修改的图片ID
			for (FileInfo file : mFileList) {
				if (StringUtils.isNotEmpty(file.cloudId)) {
					ids.add(file.cloudId);
				}
			}
			saveCompanyIntroInfo(ids);
		}
	}

	private void onUploadFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE);
		Intent intent = new Intent();
		intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_INTRO_INFO, info);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			submitCompanyIntroInfo();
			break;
		case R.id.iv_item_product_pic_1:
		case R.id.iv_item_product_pic_2:
		case R.id.iv_item_product_pic_3:
			mUploadImageView = (ImageView) v;
			showUploadPicTypeMenu();
			break;
		case R.id.iv_common_back:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

	private void submitCompanyIntroInfo() {
		showSubmitDialog();
		if (needToUploadImage()) {
			uploadImage();
		} else {
			// 获取原有未修改的图片ID
			List<String> ids = new ArrayList<String>();
			for (ImageStatusInfo info : mImgStatusList) {
				if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
					ids.add(info.cloudId);
				}
			}
			saveCompanyIntroInfo(ids);
		}
	}

	/**
	 * 判断是否有图片更新
	 * @return
	 */
	private boolean needToUploadImage() {
		for (ImageStatusInfo info : mImgStatusList) {
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
		for (ImageStatusInfo info : mImgStatusList) {
			if (info.isModified && StringUtils.isNotEmpty(info.filePath)) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.file = new File(info.filePath);
				mFileList.add(fileInfo);
			}
		}
		mInvoker = String.valueOf(System.currentTimeMillis());
		mTransferLogic.uploadFile(mInvoker, mFileList);
		showSubmitDialog();
	}

	/**
	 * 保存企业介绍信息
	 * @param ids
	 */
	private void saveCompanyIntroInfo(List<String> ids) {
		if (info == null) {
			info = new CompanyIntroInfoModel();
			info.companyId = getCompanyId();
		}
		info.introduction = mEtIntroDetail.getText().toString();
		info.imgList = ImageInfoUtils.getImageInfo(ids);

		if (!needToUploadImage()) {
			showSubmitDialog();
		}

		mProfileLogic.updateCompanyIntroInfo(info);
	}

	@Override
	protected void onPictureSelect(Bitmap bitmap, String file) {
		Logger.e(TAG, "Bitmap = " + bitmap + ", file = " + file);
		if (mUploadImageView != null) {
			mUploadImageView.setImageBitmap(null);
			mUploadImageView.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
			int index = getSelectUploadIndex();
			ImageStatusInfo imageInfo = mImgStatusList[index];
			imageInfo.filePath = file;
			if (!imageInfo.isModified) {
				imageInfo.isModified = true;
				if (StringUtils.isEmpty(imageInfo.cloudId) && mUploadedPicCount < 3) {
					mUploadedPicCount++;
				}
				updateImgStatusUI();
			}
		}
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
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
		mTransferLogic = LogicFactory.getLogicByClass(ITransferLogic.class);
	}

}
