package com.glshop.net.ui.myprofile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.ProfileMessageType;
import com.glshop.net.logic.model.ImageStatusInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 新增卸货地点界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class DischargeAddrAddActivity extends BasicActivity {

	private AddrInfoModel mAddrInfo;
	private boolean isUpdateMode = false;

	private Button mBtnSaveAddr;

	private EditText mEtDetaialAddr;
	private BuyEditItemView mItemPortWaterDepth;
	private BuyEditItemView mItemPortShipWaterDepth;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;

	private ImageView mUploadImageView;

	private ConfirmDialog mConfirmDialog;

	/** 请求标识 */
	private String mInvoker = String.valueOf(System.currentTimeMillis());

	private int mUploadedPicCount = 0;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<FileInfo> mFileList;

	private IProfileLogic mProfileLogic;
	private ITransferLogic mTransferLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discharge_addr_add);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_discharge_address_add);

		mBtnSaveAddr = getView(R.id.btn_commmon_action);
		mEtDetaialAddr = getView(R.id.et_discharge_address);
		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipWaterDepth = getView(R.id.ll_item_port_shipping_water_depth);

		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);

		mBtnSaveAddr.setVisibility(View.VISIBLE);
		mBtnSaveAddr.setText(R.string.save);
		mBtnSaveAddr.setOnClickListener(this);

		mIvItemAddrPic1.setOnClickListener(this);
		mIvItemAddrPic2.setOnClickListener(this);
		mIvItemAddrPic3.setOnClickListener(this);

		getView(R.id.btn_delete_addr).setOnClickListener(this);
		getView(R.id.btn_set_default_addr).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		initImgStatusInfo();
		mAddrInfo = (AddrInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_INFO);
		if (mAddrInfo != null) {
			isUpdateMode = true;
			((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_discharge_address_update);
			mEtDetaialAddr.setText(mAddrInfo.deliveryAddrDetail);
			mItemPortWaterDepth.setContentText(String.valueOf(mAddrInfo.uploadPortWaterDepth));
			mItemPortShipWaterDepth.setContentText(String.valueOf(mAddrInfo.uploadPortShippingWaterDepth));

			getView(R.id.btn_delete_addr).setVisibility(View.VISIBLE);
			getView(R.id.btn_set_default_addr).setVisibility(View.VISIBLE);

			updateImageData();
		}
	}

	/**
	 * 显示地址图片信息
	 */
	private void updateImageData() {
		List<ImageInfoModel> imgUrl = mAddrInfo.addrImageList;
		updateImgStatusInfo();
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
		mUploadedPicCount = BeanUtils.isEmpty(imgUrl) ? 0 : imgUrl.size();
		updateImgStatusUI();
	}

	private void initImgStatusInfo() {
		for (int i = 0; i < 3; i++) {
			mImgStatusList[i] = new ImageStatusInfo();
		}
	}

	private void updateImgStatusInfo() {
		List<ImageInfoModel> imgUrl = mAddrInfo.addrImageList;
		if (BeanUtils.isNotEmpty(imgUrl)) {
			for (int i = 0; i < imgUrl.size(); i++) {
				if (imgUrl.get(i) != null) {
					mImgStatusList[i].cloudId = imgUrl.get(i).cloudId;
				}
			}
		}
	}

	private void updateImgStatusUI() {
		mIvItemAddrPic1.setVisibility(View.VISIBLE);
		mIvItemAddrPic2.setVisibility(mUploadedPicCount >= 1 ? View.VISIBLE : View.INVISIBLE);
		mIvItemAddrPic3.setVisibility(mUploadedPicCount >= 2 ? View.VISIBLE : View.INVISIBLE);
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
		case ProfileMessageType.MSG_ADD_ADDR_SUCCESS: // 新增成功
			onAddAddrSuccess();
			break;
		case ProfileMessageType.MSG_ADD_ADDR_FAILED: // 新增失败
			onAddAddrFailed(respInfo);
			break;
		case ProfileMessageType.MSG_UPDATE_ADDR_SUCCESS: // 更新成功
			onUpdateAddrSuccess();
			break;
		case ProfileMessageType.MSG_UPDATE_ADDR_FAILED: // 更新失败
			onUpdateAddrFailed(respInfo);
			break;
		case ProfileMessageType.MSG_DELETE_ADDR_SUCCESS: // 删除成功
			onDeleteAddrSuccess();
			break;
		case ProfileMessageType.MSG_DELETE_ADDR_FAILED: // 删除失败
			onDeleteAddrFailed(respInfo);
			break;
		case ProfileMessageType.MSG_SET_DEFAULT_ADDR_SUCCESS: // 设置默认成功
			onSetDefaultAddrSuccess();
			break;
		case ProfileMessageType.MSG_SET_DEFAULT_ADDR_FAILED: // 设置默认失败
			onSetDefaultAddrFailed(respInfo);
			break;
		}
	}

	private void onUploadSuccess(RespInfo respInfo) {
		String invoker = String.valueOf(respInfo.invoker);
		if (mInvoker.equals(invoker)) {
			//List<String> ids = (ArrayList<String>) data.getSerializable(CommonAction.EXTRA_KEY_RESP_DATA);
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
			saveAddrInfo(ids);
		}
	}

	private void onUploadFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onAddAddrSuccess() {
		closeSubmitDialog();
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onAddAddrFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onUpdateAddrSuccess() {
		closeSubmitDialog();
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onUpdateAddrFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onDeleteAddrSuccess() {
		closeSubmitDialog();
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onDeleteAddrFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onSetDefaultAddrSuccess() {
		closeSubmitDialog();
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onSetDefaultAddrFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			ActivityUtil.hideKeyboard(this);
			submitAddrInfo();
			break;
		case R.id.btn_delete_addr:
			showConfirmDialog();
			break;
		case R.id.btn_set_default_addr:
			showSubmitDialog();
			mProfileLogic.setDefaultAddrInfo(mAddrInfo.addrId);
			break;
		case R.id.iv_item_addr_pic_1:
		case R.id.iv_item_addr_pic_2:
		case R.id.iv_item_addr_pic_3:
			mUploadImageView = (ImageView) v;
			showUploadPicTypeMenu();
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	private void submitAddrInfo() {
		String detailAddr = mEtDetaialAddr.getText().toString().trim();
		if (StringUtils.isNotEmpty(detailAddr)) {
			if (needToUploadImage()) {
				// 有图片更新，则先上传图片
				uploadImage();
			} else {
				// 无图片更新，则直接提交
				List<String> ids = new ArrayList<String>();
				// 获取原有未修改的图片ID
				for (ImageStatusInfo info : mImgStatusList) {
					if (!info.isModified && StringUtils.isNotEmpty(info.cloudId)) {
						ids.add(info.cloudId);
					}
				}
				saveAddrInfo(ids);
			}
		} else {
			showToast(R.string.error_empty_detail_addr);
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

	private void saveAddrInfo(List<String> ids) {
		Logger.e(TAG, "ImageIds = " + ids);

		if (!needToUploadImage()) {
			showSubmitDialog();
		}

		if (isUpdateMode) {
			mProfileLogic.updateAddrInfo(getAddrInfo(ids));
		} else {
			mProfileLogic.addAddrInfo(getAddrInfo(ids));
		}
	}

	private AddrInfoModel getAddrInfo(List<String> ids) {
		if (mAddrInfo == null) {
			mAddrInfo = new AddrInfoModel();
		}
		mAddrInfo.companyId = getCompanyId();
		mAddrInfo.deliveryAddrDetail = mEtDetaialAddr.getText().toString().trim();
		if (StringUtils.isNotEmpty(mItemPortWaterDepth.getContentText().trim())) {
			mAddrInfo.uploadPortWaterDepth = Float.parseFloat(mItemPortWaterDepth.getContentText());
		}
		if (StringUtils.isNotEmpty(mItemPortShipWaterDepth.getContentText().trim())) {
			mAddrInfo.uploadPortShippingWaterDepth = Float.parseFloat(mItemPortShipWaterDepth.getContentText());
		}
		// 添加图片ID
		mAddrInfo.addrImageList = ImageInfoUtils.getImageInfo(ids);
		return mAddrInfo;
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
		if (mUploadImageView == mIvItemAddrPic1) {
			return 0;
		} else if (mUploadImageView == mIvItemAddrPic2) {
			return 1;
		} else if (mUploadImageView == mIvItemAddrPic3) {
			return 2;
		} else {
			return 0;
		}
	}

	private void showConfirmDialog() {
		if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
			mConfirmDialog.dismiss();
		}

		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.confirmed_delete_addr_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(Object obj) {
				showSubmitDialog();
				mProfileLogic.deleteAddrInfo(mAddrInfo.addrId);
			}

			@Override
			public void onCancel() {

			}
		});
		mConfirmDialog.show();
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
		mTransferLogic = LogicFactory.getLogicByClass(ITransferLogic.class);
	}

}
