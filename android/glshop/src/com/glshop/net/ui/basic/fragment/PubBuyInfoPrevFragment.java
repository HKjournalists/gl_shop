package com.glshop.net.ui.basic.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.TipActionBackType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.model.ImageStatusInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.view.AreaListView;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布买卖信息预览Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public class PubBuyInfoPrevFragment extends BasePubInfoFragment {

	private static final String TAG = "PubBuyInfoPrevFragment";

	private BuyTextItemView mItemBuyType;
	private BuyTextItemView mItemProductType;
	private BuyTextItemView mItemProductSpec;
	private BuyTextItemView mItemColor;
	private BuyTextItemView mItemArea;
	private BuyTextItemView mItemSedimentPerc;
	private BuyTextItemView mItemSedimentBlockPerc;
	private BuyTextItemView mItemWaterPerc;
	private BuyTextItemView mItemAppearanceDensity;
	private BuyTextItemView mItemStackingPerc;
	private BuyTextItemView mItemSturdinessPerc;

	private BuyTextItemView mItemUnitPrice;
	private BuyTextItemView mItemAmountSingleArea;
	private BuyTextItemView mItemAmountMoreArea;

	private TextView mTvStartDate;
	private TextView mTvEndDate;
	private BuyTextItemView mItemTradeArea;
	private BuyTextItemView mItemDischargeAddrType;

	private TextView mTvProductRemarks;
	private TextView mTvBuyRemarks;
	private TextView mTvBuyRemarksTitle;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;
	private ImageView mIvItemProductPic1;
	private ImageView mIvItemProductPic2;
	private ImageView mIvItemProductPic3;

	// 卸货地址
	private View llAddrInfo;
	private TextView mTvAddrDetail;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipWaterDepth;

	/** 多地域显示 */
	private AreaListView mAreaView;

	/** 请求标识 */
	private String mInvoker = String.valueOf(System.currentTimeMillis());

	private List<ImageInfoModel> mProductImageList;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<FileInfo> mFileList;

	private IBuyLogic mBuyLogic;
	private ITransferLogic mTransferLogic;

	public PubBuyInfoPrevFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		getView(R.id.btn_sumbit_pub).setOnClickListener(this);
		mAreaView = getView(R.id.ll_area_view);

		mItemBuyType = getView(R.id.ll_item_buy_type_prev);
		mItemProductType = getView(R.id.ll_item_product_type);
		mItemProductSpec = getView(R.id.ll_item_product_spec);
		mItemColor = getView(R.id.ll_item_product_color);
		mItemArea = getView(R.id.ll_item_product_area);
		mItemSedimentPerc = getView(R.id.ll_item_product_sediment_perc);
		mItemSedimentBlockPerc = getView(R.id.ll_item_product_sediment_block_perc);
		mItemWaterPerc = getView(R.id.ll_item_product_water_perc);
		mItemAppearanceDensity = getView(R.id.ll_item_product_appearance_density);
		mItemStackingPerc = getView(R.id.ll_item_product_stacking_perc);
		mItemSturdinessPerc = getView(R.id.ll_item_product_sturdiness_perc);

		mTvStartDate = getView(R.id.tv_trade_start_date);
		mTvEndDate = getView(R.id.btn_trade_end_date);
		mItemTradeArea = getView(R.id.ll_trade_area_item);
		mItemDischargeAddrType = getView(R.id.ll_item_discharge_address_type);
		mTvProductRemarks = getView(R.id.tv_product_remarks_content);
		mTvBuyRemarksTitle = getView(R.id.tv_buy_remarks_title);
		mTvBuyRemarks = getView(R.id.tv_buy_remarks_content);

		mItemAmountSingleArea = getView(R.id.ll_item_trade_amount_single_area);
		mItemAmountMoreArea = getView(R.id.ll_item_trade_amount_more_area);
		mItemUnitPrice = getView(R.id.ll_item_unit_price);

		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);

		mIvItemProductPic1 = getView(R.id.iv_item_product_pic_1);
		mIvItemProductPic2 = getView(R.id.iv_item_product_pic_2);
		mIvItemProductPic3 = getView(R.id.iv_item_product_pic_3);

		llAddrInfo = getView(R.id.ll_item_addr);
		mTvAddrDetail = getView(R.id.tv_addr_detail);
		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipWaterDepth = getView(R.id.ll_item_port_shipping_water_depth);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_3).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mPubInfo != null) {
			if (mPubInfo.buyType == BuyType.BUYER) {
				// 求购预览页不显示实物照片信息
				getView(R.id.ll_product_photo).setVisibility(View.GONE);
				mItemAmountSingleArea.setContentText(String.valueOf(mPubInfo.tradeAmount));
				mItemUnitPrice.setContentText(String.valueOf(mPubInfo.unitPrice));
			} else {
				// 出售预览页显示实物照片信息、多地域价格信息
				getView(R.id.ll_product_photo).setVisibility(View.VISIBLE);
				mItemBuyType.setContentText(getString(R.string.publish_type_sell));
				mTvBuyRemarksTitle.setText(getString(R.string.business_sell_remarks));

				if (mPubInfo.isMoreArea) {
					// 显示多地域信息
					getView(R.id.ll_single_area_list).setVisibility(View.GONE);
					getView(R.id.ll_more_area_list).setVisibility(View.VISIBLE);
					mItemAmountMoreArea.setContentText(String.valueOf(mPubInfo.tradeAmount));
					mAreaView.setData(mPubInfo.areaInfoList);
				} else {
					// 显示单地域信息
					getView(R.id.ll_single_area_list).setVisibility(View.VISIBLE);
					getView(R.id.ll_more_area_list).setVisibility(View.GONE);
					mItemAmountSingleArea.setContentText(String.valueOf(mPubInfo.tradeAmount));
					mItemUnitPrice.setContentText(String.valueOf(mPubInfo.unitPrice));
				}

				// 实物信息
				mProductImageList = mPubInfo.productImgList;
				updateProductImgData();
				updateProductImageUI();
			}

			// 货物规格信息
			mItemProductType.setContentText(mPubInfo.productName);
			mItemProductSpec.setContentText(mPubInfo.specName);
			mItemColor.setContentText(mPubInfo.productInfo.color);
			mItemArea.setContentText(mPubInfo.productInfo.area);
			mItemSedimentPerc.setContentText(String.valueOf(mPubInfo.productInfo.sedimentPercentage.mRealSize));
			mItemSedimentBlockPerc.setContentText(String.valueOf(mPubInfo.productInfo.sedimentBlockPercentage.mRealSize));
			mItemWaterPerc.setContentText(/*String.valueOf(mPubInfo.productInfo.waterPercentage.mRealSize)*/"0");
			mItemAppearanceDensity.setContentText(String.valueOf(mPubInfo.productInfo.appearanceDensity.mRealSize));
			mItemStackingPerc.setContentText(String.valueOf(mPubInfo.productInfo.stackingPercentage.mRealSize));
			mItemSturdinessPerc.setContentText(String.valueOf(mPubInfo.productInfo.sturdinessPercentage.mRealSize));
			mTvProductRemarks.setText(mPubInfo.productInfo.remarks);

			// 交易地域及时间信息
			mItemTradeArea.setContentText(SysCfgUtils.getAreaName(mContext, mPubInfo.tradeArea));
			mTvStartDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeBeginDate));
			mTvEndDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeEndDate));
			if (mPubInfo.deliveryAddrType == DeliveryAddrType.ME_DECIDE) {
				mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_me_decide));
				// 己方指定则显示卸货地址详情
				llAddrInfo.setVisibility(View.VISIBLE);
				updateDischargeAddrInfo();
			} else {
				mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_another_decide));
				llAddrInfo.setVisibility(View.GONE);
			}

			// 货物备注信息
			if (StringUtils.isNotEmpty(mPubInfo.productInfo.remarks)) {
				mTvProductRemarks.setText(mPubInfo.productInfo.remarks);
			} else {
				mTvProductRemarks.setText(R.string.buy_no_remarks);
			}

			// 买卖备注信息
			if (StringUtils.isNotEmpty(mPubInfo.buyRemarks)) {
				mTvBuyRemarks.setText(mPubInfo.buyRemarks);
			} else {
				mTvBuyRemarks.setText(R.string.buy_no_remarks);
			}
		}
	}

	@Override
	protected void updateBuyUI() {
		// nothing todo
	}

	@Override
	public BuyInfoModel getBuyInfo() {
		return mPubInfo;
	}

	/**
	 * 显示卸货地址信息
	 */
	private void updateDischargeAddrInfo() {
		if (mPubInfo != null) {
			AddrInfoModel addrInfo = mPubInfo.addrInfo;
			if (addrInfo != null) {
				mTvAddrDetail.setText(StringUtils.isNotEmpty(addrInfo.deliveryAddrDetail) ? addrInfo.deliveryAddrDetail : getString(R.string.data_empty));
				mItemPortWaterDepth.setContentText(addrInfo.uploadPortWaterDepth != 0 ? String.valueOf(addrInfo.uploadPortWaterDepth) : getString(R.string.data_empty));
				mItemPortShipWaterDepth.setContentText(addrInfo.uploadPortShippingWaterDepth != 0 ? String.valueOf(addrInfo.uploadPortShippingWaterDepth) : getString(R.string.data_empty));

				List<ImageInfoModel> imgUrl = addrInfo.addrImageList;
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
			} else {
				mTvAddrDetail.setText(getString(R.string.data_empty));
				mItemPortWaterDepth.setContentText(getString(R.string.data_empty));
				mItemPortShipWaterDepth.setContentText(getString(R.string.data_empty));
				mIvItemAddrPic1.setVisibility(View.VISIBLE);
				mIvItemAddrPic2.setVisibility(View.INVISIBLE);
				mIvItemAddrPic3.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void updateProductImgData() {
		for (int i = 0; i < 3; i++) {
			mImgStatusList[i] = new ImageStatusInfo();
		}
		List<ImageInfoModel> imgUrl = mProductImageList;
		if (BeanUtils.isNotEmpty(imgUrl)) {
			for (int i = 0; i < imgUrl.size(); i++) {
				if (imgUrl.get(i) != null) {
					mImgStatusList[i].cloudId = imgUrl.get(i).cloudId;
					File localFile = imgUrl.get(i).localFile;
					if (localFile != null && StringUtils.isNotEmpty(localFile.getAbsolutePath())) {
						mImgStatusList[i].filePath = imgUrl.get(i).localFile.getAbsolutePath();
						mImgStatusList[i].isModified = true;
					}
				}
			}
		}
	}

	/**
	 * 显示实物信息
	 */
	private void updateProductImageUI() {
		if (mPubInfo != null) {
			List<ImageInfoModel> imgUrl = mPubInfo.productImgList;
			if (BeanUtils.isEmpty(imgUrl)) {
				mIvItemProductPic1.setVisibility(View.VISIBLE);
				mIvItemProductPic2.setVisibility(View.INVISIBLE);
				mIvItemProductPic3.setVisibility(View.INVISIBLE);
			} else {
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemProductPic1.setVisibility(View.VISIBLE);
					ImageInfoModel imgInfo = imgUrl.get(0);
					if (imgInfo.localFile != null && StringUtils.isNotEmpty(imgInfo.localFile.getAbsolutePath())) {
						ImageLoaderManager.getIntance().displayLocal(this, imgInfo.localFile.getAbsolutePath(), mIvItemProductPic1, IMAGE_DEFAULT, IMAGE_FAILED);
					} else {
						ImageLoaderManager.getIntance().display(this, imgInfo.cloudThumbnailUrl, mIvItemProductPic1, IMAGE_DEFAULT, IMAGE_FAILED);
					}
				}
				if (count >= 2) {
					mIvItemProductPic2.setVisibility(View.VISIBLE);
					ImageInfoModel imgInfo = imgUrl.get(1);
					if (imgInfo.localFile != null && StringUtils.isNotEmpty(imgInfo.localFile.getAbsolutePath())) {
						ImageLoaderManager.getIntance().displayLocal(this, imgInfo.localFile.getAbsolutePath(), mIvItemProductPic2, IMAGE_DEFAULT, IMAGE_FAILED);
					} else {
						ImageLoaderManager.getIntance().display(this, imgInfo.cloudThumbnailUrl, mIvItemProductPic2, IMAGE_DEFAULT, IMAGE_FAILED);
					}
				} else {
					mIvItemProductPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemProductPic3.setVisibility(View.VISIBLE);
					ImageInfoModel imgInfo = imgUrl.get(2);
					if (imgInfo.localFile != null && StringUtils.isNotEmpty(imgInfo.localFile.getAbsolutePath())) {
						ImageLoaderManager.getIntance().displayLocal(this, imgInfo.localFile.getAbsolutePath(), mIvItemProductPic3, IMAGE_DEFAULT, IMAGE_FAILED);
					} else {
						ImageLoaderManager.getIntance().display(this, imgInfo.cloudThumbnailUrl, mIvItemProductPic3, IMAGE_DEFAULT, IMAGE_FAILED);
					}
				} else {
					mIvItemProductPic3.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case CommonMessageType.MSG_FILE_UPLOAD_SUCCESS: // 图片上传成功
			onUploadSuccess(respInfo);
			break;
		case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			onUploadFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_PUB_BUY_INFO_SUCCESS:
			onPubSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_PUB_BUY_INFO_FAILED:
			onPubFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_UPDATE_PUB_BUY_INFO_SUCCESS:
			onUpdateSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_UPDATE_PUB_BUY_INFO_FAILED:
			onUpdateFailed(respInfo);
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
			saveAction(ids);
		}
	}

	private void onUploadFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onPubSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST);
		Intent intent = new Intent(mContext, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.pub_buy_success);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_pub_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_pub_success_content);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_pub_cancel_action_text);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_BUY;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		getActivity().finish();
	}

	private void onPubFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onUpdateSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST);
		Intent intent = new Intent(mContext, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.pub_buy_success);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_pub_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_pub_success_content);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_pub_cancel_action_text);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_BUY;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		getActivity().finish();
	}

	private void onUpdateFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sumbit_pub:
			doSubmitAction();
			break;
		case R.id.iv_item_addr_pic_1:
			if (mPubInfo.addrInfo != null && BeanUtils.isNotEmpty(mPubInfo.addrInfo.addrImageList)) {
				browseImage(mPubInfo.addrInfo.addrImageList.get(0));
			}
			break;
		case R.id.iv_item_addr_pic_2:
			if (mPubInfo.addrInfo != null && BeanUtils.isNotEmpty(mPubInfo.addrInfo.addrImageList)) {
				browseImage(mPubInfo.addrInfo.addrImageList.get(1));
			}
			break;
		case R.id.iv_item_addr_pic_3:
			if (mPubInfo.addrInfo != null && BeanUtils.isNotEmpty(mPubInfo.addrInfo.addrImageList)) {
				browseImage(mPubInfo.addrInfo.addrImageList.get(2));
			}
			break;
		case R.id.iv_item_product_pic_1:
			if (BeanUtils.isNotEmpty(mPubInfo.productImgList)) {
				browseImage(mPubInfo.productImgList.get(0));
			}
			break;
		case R.id.iv_item_product_pic_2:
			if (BeanUtils.isNotEmpty(mPubInfo.productImgList)) {
				browseImage(mPubInfo.productImgList.get(1));
			}
			break;
		case R.id.iv_item_product_pic_3:
			if (BeanUtils.isNotEmpty(mPubInfo.productImgList)) {
				browseImage(mPubInfo.productImgList.get(2));
			}
			break;
		}
	}

	private void doSubmitAction() {
		if (mPubInfo.buyType == BuyType.SELLER) {
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
				saveAction(ids);
			}
		} else if (mPubInfo.buyType == BuyType.BUYER) {
			saveAction(null);
		}
	}

	/**
	 * 判断是否有图片更新
	 * @return
	 */
	private boolean needToUploadImage() {
		if (mPubInfo.buyType == BuyType.SELLER) {
			for (ImageStatusInfo info : mImgStatusList) {
				if (info != null && info.isModified) {
					return true;
				}
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
	 * 发布或更新操作
	 * @param ids
	 */
	private void saveAction(List<String> ids) {
		mPubInfo.productImgList = ImageInfoUtils.getImageInfo(ids);

		if (!needToUploadImage()) {
			showSubmitDialog();
		}

		if (isModifyMode && StringUtils.isNotEmpty(mPubInfo.buyId)) {
			mBuyLogic.updateBuyInfo(mPubInfo);
		} else {
			mBuyLogic.pubBuyInfo(mPubInfo);
		}
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
		mTransferLogic = LogicFactory.getLogicByClass(ITransferLogic.class);
	}

}
