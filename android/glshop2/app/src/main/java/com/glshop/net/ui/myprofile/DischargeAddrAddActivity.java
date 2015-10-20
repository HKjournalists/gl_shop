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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
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
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
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
	private CheckedTextView mCkbTvSetDefault;

	private EditText mEtDetailAddr;
	private BuyTextItemView mItemTradeArea;
	private BuyEditItemView mItemPortWaterDepth;
	private BuyEditItemView mItemPortShipTon;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;

	private ImageView mUploadImageView;

	private ConfirmDialog mConfirmDialog;

	private int mUploadedPicCount = 0;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<FileInfo> mFileList;

	protected String mTradeAreaCode;
	protected String mTradeAreaName;

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
		mCkbTvSetDefault = getView(R.id.chkTv_set_default);
		mItemTradeArea = getView(R.id.ll_item_trade_area);
		mEtDetailAddr = getView(R.id.et_discharge_address);
		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);

		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);

		mBtnSaveAddr.setVisibility(View.VISIBLE);
		mBtnSaveAddr.setText(R.string.save);
		mBtnSaveAddr.setOnClickListener(this);

		mItemTradeArea.setOnClickListener(this);
		mIvItemAddrPic1.setOnClickListener(this);
		mIvItemAddrPic2.setOnClickListener(this);
		mIvItemAddrPic3.setOnClickListener(this);

		getView(R.id.ll_item_set_default).setOnClickListener(this);
		getView(R.id.btn_delete_addr).setOnClickListener(this);
		getView(R.id.btn_set_default_addr).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
		
		setTextWatcher(mItemPortWaterDepth.getEditText());
		setTextWatcher(mItemPortShipTon.getEditText());
	}

	private void initData() {
		initImgStatusInfo();
		mAddrInfo = (AddrInfoModel) getIntent().getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_INFO);
		if (mAddrInfo != null) {
			isUpdateMode = true;
			((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_discharge_address_update);
			mEtDetailAddr.setText(mAddrInfo.deliveryAddrDetail);
			mItemPortWaterDepth.setContentText(String.valueOf(mAddrInfo.uploadPortWaterDepth));
			mItemPortShipTon.setContentText(String.valueOf(mAddrInfo.shippingTon));

			mItemPortShipTon.setBackgroundResource(R.drawable.selector_item_bottom);
			getView(R.id.item_devider_set_default).setVisibility(View.GONE);
			getView(R.id.ll_item_set_default).setVisibility(View.GONE);

			getView(R.id.btn_delete_addr).setVisibility(View.VISIBLE);
			getView(R.id.btn_set_default_addr).setVisibility(mAddrInfo.isDefaultAddr ? View.GONE : View.VISIBLE);

			mTradeAreaCode = mAddrInfo.areaCode;
			if (StringUtils.isNotEmpty(mTradeAreaCode)) {
				mItemTradeArea.setTitle(mAddrInfo.areaName);
				mItemTradeArea.setContentText("");
			}

			updateImageData();

			requestSelection(mEtDetailAddr);
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
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			case ProfileMessageType.MSG_ADD_ADDR_FAILED: // 新增失败
			case ProfileMessageType.MSG_UPDATE_ADDR_FAILED: // 更新失败
			case ProfileMessageType.MSG_DELETE_ADDR_FAILED: // 删除失败
			case ProfileMessageType.MSG_SET_DEFAULT_ADDR_FAILED: // 设置默认失败
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
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
		case R.id.ll_item_set_default:
			mCkbTvSetDefault.toggle();
			break;
		case R.id.btn_set_default_addr:
			showSubmitDialog();
			mProfileLogic.setDefaultAddrInfo(mAddrInfo.addrId);
			break;
		case R.id.ll_item_trade_area:
			Intent intent = new Intent(this, AreaAddrSelectActivity.class);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_CODE, mTradeAreaCode);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_SELECT_TRADE_ADDRESS);
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
		if (checkArgs()) {
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
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isNEmpty(mTradeAreaCode)) {
			showToast("请选择省市区地址!");
			return false;
		} else if (StringUtils.isNEmpty(mEtDetailAddr.getText().toString().trim())) {
			showToast("请输入详细的卸货地址!");
			return false;
		} else if (StringUtils.isNEmpty(mItemPortWaterDepth.getContentText().trim())) {
			showToast("请输入卸货地港口水深度!");
			return false;
		} else if (StringUtils.isNEmpty(mItemPortShipTon.getContentText().trim())) {
			showToast("请输入可停泊载重船吨位!");
			return false;
		}
		return true;
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
		mAddrInfo.areaCode = mTradeAreaCode;
		mAddrInfo.deliveryAddrDetail = mEtDetailAddr.getText().toString().trim();
		if (StringUtils.isNotEmpty(mItemPortWaterDepth.getContentText().trim())) {
			mAddrInfo.uploadPortWaterDepth = Double.parseDouble(mItemPortWaterDepth.getContentText());
		}
		if (StringUtils.isNotEmpty(mItemPortShipTon.getContentText().trim())) {
			mAddrInfo.shippingTon = Double.parseDouble(mItemPortShipTon.getContentText());
		}
		// 添加图片ID
		mAddrInfo.addrImageList = ImageInfoUtils.getImageInfo(ids);

		// 设置为默认交易地址
		if (StringUtils.isNEmpty(mAddrInfo.addrId)) {
			mAddrInfo.isDefaultAddr = mCkbTvSetDefault.isChecked();
		}

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
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				mProfileLogic.deleteAddrInfo(mAddrInfo.addrId);
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mConfirmDialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_SELECT_TRADE_ADDRESS:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mTradeAreaCode = data.getStringExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_CODE);
					mTradeAreaName = data.getStringExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_NAME);
					if (StringUtils.isNotEmpty(mTradeAreaName)) {
						mItemTradeArea.setTitle(mTradeAreaName);
						mItemTradeArea.setContentText("");
					}
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
	
	/**
	 * 添加文本监听，限制文本输入
	 * @param View
	 */
	protected void setTextWatcher(final EditText view) {
		if (view != null) {
			view.addTextChangedListener(new TextWatcher() {

				private int editStart;
				private int editEnd;

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					editStart = view.getSelectionStart();
					editEnd = view.getSelectionEnd();
					String data = view.getEditableText().toString();
					if (StringUtils.isDouble(data) && !StringUtils.checkDecimal(data, 2)) {
						s.delete(editStart - 1, editEnd);
						int tempSelection = editStart;
						view.setText(s);
						view.setSelection(tempSelection);
					}
				}
			});
		}
	}

}
