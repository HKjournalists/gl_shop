package com.glshop.net.ui.basic.fragment.buy.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalErrorMessage;
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
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
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
public class PubBuyInfoPrevFragmentV2 extends BasePubInfoFragmentV2 {

	protected static final String TAG = "PubBuyInfoPrevFragmentV2";

	// 货物信息
	private BuyTextItemView mItemBuyType;
	private BuyTextItemView mItemProductType;
	private BuyTextItemView mItemProductCategory;
	private BuyTextItemView mItemProductSpec;
	private BuyTextItemView mItemColor;
	private BuyTextItemView mItemArea;

	// 货物规格属性
	private BuyTextItemView mItemSedimentPerc;
	private BuyTextItemView mItemSedimentBlockPerc;
	private BuyTextItemView mItemWaterPerc;
	private BuyTextItemView mItemCrunchPerc;
	private BuyTextItemView mItemNeedlePlatePerc;
	private BuyTextItemView mItemAppearanceDensity;
	private BuyTextItemView mItemStackingPerc;
	private BuyTextItemView mItemSturdinessPerc;

	// 货物单价及数量
	private BuyTextItemView mItemUnitPrice;
	private BuyTextItemView mItemAmount;

	// 交易信息
	private TextView mTvStartDate;
	private TextView mTvEndDate;
	private BuyTextItemView mItemTradeArea;
	private BuyTextItemView mItemDischargeAddrType;
	private TextView mTvProductRemarks;
	private TextView mTvBuyRemarks;

	// 卸货地址图片
	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;
	// 实物照片
	private ImageView mIvItemProductPic1;
	private ImageView mIvItemProductPic2;
	private ImageView mIvItemProductPic3;

	// 卸货地址
	private View llAddrInfo;
	private TextView mTvAddrDetail;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	private List<ImageInfoModel> mProductImageList;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<FileInfo> mFileList;

	private IBuyLogic mBuyLogic;
	private ITransferLogic mTransferLogic;

	public PubBuyInfoPrevFragmentV2() {

	}

	@Override
	protected void initView() {
		mItemBuyType = getView(R.id.ll_item_buy_type);
		mItemProductType = getView(R.id.ll_item_product_type);
		mItemProductCategory = getView(R.id.ll_item_product_category);
		mItemProductSpec = getView(R.id.ll_item_product_sepc);
		mItemColor = getView(R.id.ll_item_product_color);
		mItemArea = getView(R.id.ll_item_product_area);
		mTvStartDate = getView(R.id.tv_trade_start_date);
		mTvEndDate = getView(R.id.btn_trade_end_date);
		mItemTradeArea = getView(R.id.ll_trade_area_item);
		mItemDischargeAddrType = getView(R.id.ll_item_discharge_address_type);
		mTvProductRemarks = getView(R.id.tv_product_remarks_content);
		mTvBuyRemarks = getView(R.id.tv_buy_remarks_content);

		mItemAmount = getView(R.id.ll_item_trade_amount);
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
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);

		mItemSedimentPerc = getView(R.id.ll_item_product_sediment_perc);
		mItemSedimentBlockPerc = getView(R.id.ll_item_product_sediment_block_perc);
		mItemWaterPerc = getView(R.id.ll_item_product_water_perc);
		mItemCrunchPerc = getView(R.id.ll_item_product_crunch_perc);
		mItemNeedlePlatePerc = getView(R.id.ll_item_product_needle_plate_perc);
		mItemAppearanceDensity = getView(R.id.ll_item_product_appearance_density);
		mItemStackingPerc = getView(R.id.ll_item_product_stacking_perc);
		mItemSturdinessPerc = getView(R.id.ll_item_product_sturdiness_perc);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_3).setOnClickListener(this);
		getView(R.id.btn_sumbit_pub).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mPubInfo != null) {
			updateBuyUI();
		}
	}

	@Override
	protected void updateBuyUI() {
		if (mPubInfo != null) {
			if (mPubInfo.buyType == BuyType.BUYER) {
				mItemBuyType.setContentText(mContext.getString(R.string.publish_type_buy));
				// 求购页面不显示实物照片信息
				getView(R.id.ll_product_photo).setVisibility(View.GONE);
				getView(R.id.ll_item_devider_product_photo).setVisibility(View.GONE);
			} else {
				mItemBuyType.setContentText(mContext.getString(R.string.publish_type_sell));
				// 出售页面显示实物照片信息、多地域价格信息
				getView(R.id.ll_product_photo).setVisibility(View.VISIBLE);
				getView(R.id.ll_item_devider_product_photo).setVisibility(View.VISIBLE);
				// 实物信息
				mProductImageList = mPubInfo.productImgList;
				updateProductImgData();
				updateProductImageUI();
			}

			// 显示货物信息
			updateProductInfo();
			// 显示规格属性
			updateProductPropInfo();

			// 显示数量及单价
			mItemAmount.setContentText(String.valueOf(mPubInfo.tradeAmount));
			mItemUnitPrice.setContentText(String.valueOf(mPubInfo.unitPrice));
			if (mPubInfo.buyType == BuyType.BUYER) {
				mItemAmount.setTitle(getString(R.string.business_product_purchases));
			} else {
				mItemAmount.setTitle(getString(R.string.business_product_sales_volume));
			}
			// 显示单位
			if (mPubInfo.unitType == ProductUnitType.CUTE) {
				mItemAmount.setSecondTitle(getString(R.string.unit_cute_v4));
				mItemUnitPrice.setSecondTitle(getString(R.string.unit_price_cute_v2));
			} else {
				mItemAmount.setSecondTitle(getString(R.string.unit_ton_v4));
				mItemUnitPrice.setSecondTitle(getString(R.string.unit_price_ton_v2));
			}

			// 交易地域及时间信息
			mItemTradeArea.setContentText(mPubInfo.tradeAreaName);
			mTvStartDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeBeginDate));
			mTvEndDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeEndDate));
			if (mPubInfo.deliveryAddrType == DeliveryAddrType.ME_DECIDE) {
				mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_me_decide));
				// 己方指定则显示卸货地址详情
				llAddrInfo.setVisibility(View.VISIBLE);
				getView(R.id.item_devider_discharge_addr).setVisibility(View.VISIBLE);
				updateDischargeAddrInfo();
			} else {
				mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_another_decide));
				llAddrInfo.setVisibility(View.GONE);
				getView(R.id.item_devider_discharge_addr).setVisibility(View.GONE);
			}

			// 货物备注信息
			if (StringUtils.isNotEmpty(mPubInfo.productRemarks)) {
				mTvProductRemarks.setText(mPubInfo.productRemarks);
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
	public BuyInfoModel getBuyInfo(boolean isUpdate) {
		return mPubInfo;
	}

	/**
	 * 显示货物信息
	 */
	private void updateProductInfo() {
		if (mPubInfo != null) {
			if (mPubInfo.productPropInfo != null) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(mPubInfo.productTypeCode)) {
					mItemProductType.setContentText(getString(R.string.product_type_stone));
				} else {
					mItemProductType.setContentText(getString(R.string.product_type_sand));
				}

				if (mPubInfo.productSepcInfo != null) {
					if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(mPubInfo.productTypeCode)) {
						mItemProductCategory.setVisibility(View.GONE);
						getView(R.id.ll_item_devider_categroy).setVisibility(View.GONE);
						String size = SysCfgUtils.getSize(mPubInfo.productSepcInfo);
						mItemProductSpec.setContentText(mPubInfo.productSepcInfo.mCategoryName + (StringUtils.isNotEmpty(size) ? size + "mm" : ""));
					} else {
						mItemProductCategory.setContentText(mPubInfo.productSepcInfo.mCategoryName);
						mItemProductSpec.setContentText(mPubInfo.productSepcInfo.mSubCategoryName + SysCfgUtils.getSize(mPubInfo.productSepcInfo) + "mm");
					}
				} else {
					mItemProductCategory.setVisibility(View.GONE);
					getView(R.id.ll_item_devider_categroy).setVisibility(View.GONE);
					mItemProductSpec.setContentText(mPubInfo.productSpecName);
				}
			}

			// 颜色&产地
			if (StringUtils.isNotEmpty(mPubInfo.productColor)) {
				mItemColor.setContentText(mPubInfo.productColor);
			} else {
				mItemColor.setContentText(getString(R.string.data_no_input));
				mItemColor.setContentColor(getResources().getColor(R.color.gray));
			}
			if (StringUtils.isNotEmpty(mPubInfo.productArea)) {
				mItemArea.setContentText(mPubInfo.productArea);
			} else {
				mItemArea.setContentText(getString(R.string.data_no_input));
				mItemArea.setContentColor(getResources().getColor(R.color.gray));
			}
		}
	}

	/**
	 * 显示规格属性
	 */
	private void updateProductPropInfo() {
		if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(mPubInfo.productTypeCode)) {
			mItemWaterPerc.setVisibility(View.VISIBLE);
			mItemCrunchPerc.setVisibility(View.GONE);
			mItemNeedlePlatePerc.setVisibility(View.GONE);
			getView(R.id.item_devider_water).setVisibility(View.VISIBLE);
			getView(R.id.item_devider_crunch).setVisibility(View.GONE);
			getView(R.id.item_devider_needle_plate).setVisibility(View.GONE);
		} else {
			mItemWaterPerc.setVisibility(View.GONE);
			mItemCrunchPerc.setVisibility(View.VISIBLE);
			mItemNeedlePlatePerc.setVisibility(View.VISIBLE);
			getView(R.id.item_devider_water).setVisibility(View.GONE);
			getView(R.id.item_devider_crunch).setVisibility(View.VISIBLE);
			getView(R.id.item_devider_needle_plate).setVisibility(View.VISIBLE);
		}

		if (mPubInfo != null && mPubInfo.productPropInfo != null) {
			initPropInfo(mItemSedimentPerc, mPubInfo.productPropInfo.sedimentPercentage);
			initPropInfo(mItemSedimentBlockPerc, mPubInfo.productPropInfo.sedimentBlockPercentage);
			initPropInfo(mItemWaterPerc, mPubInfo.productPropInfo.waterPercentage);
			initPropInfo(mItemCrunchPerc, mPubInfo.productPropInfo.crunchPercentage);
			initPropInfo(mItemNeedlePlatePerc, mPubInfo.productPropInfo.needlePlatePercentage);
			initPropInfo(mItemAppearanceDensity, mPubInfo.productPropInfo.appearanceDensity);
			initPropInfo(mItemStackingPerc, mPubInfo.productPropInfo.stackingPercentage);
			initPropInfo(mItemSturdinessPerc, mPubInfo.productPropInfo.sturdinessPercentage);
		}
	}

	private void initPropInfo(BuyTextItemView item, ProductPropInfoModel info) {
		if (info != null) {
			if (StringUtils.isNotEmpty(info.mRealSize)) {
				item.setContentText(info.mRealSize);
			} else {
				item.setContentText(getString(R.string.data_no_input));
			}
		} else {
			item.setContentText(getString(R.string.data_no_input));
		}
	}

	/**
	 * 显示卸货地址信息
	 */
	private void updateDischargeAddrInfo() {
		if (mPubInfo != null) {
			AddrInfoModel addrInfo = mPubInfo.addrInfo;
			if (isAddrValide(addrInfo)) {
				// 显示完整地址信息
				if (StringUtils.isNEmpty(addrInfo.areaName) && StringUtils.isNEmpty(addrInfo.deliveryAddrDetail)) {
					mTvAddrDetail.setText(getString(R.string.data_empty));
				} else {
					mTvAddrDetail.setText(addrInfo.areaName + addrInfo.deliveryAddrDetail);
				}
				mItemPortWaterDepth.setContentText(addrInfo.uploadPortWaterDepth != 0 ? String.valueOf(addrInfo.uploadPortWaterDepth) : getString(R.string.data_empty));
				mItemPortShipTon.setContentText(addrInfo.shippingTon != 0 ? String.valueOf(addrInfo.shippingTon) : getString(R.string.data_empty));

				// 显示地址图片信息
				List<ImageInfoModel> imgUrl = addrInfo.addrImageList;
				if (BeanUtils.isEmpty(imgUrl)) {
					getView(R.id.ll_addr_pic).setVisibility(View.GONE);
				} else {
					getView(R.id.ll_addr_pic).setVisibility(View.VISIBLE);
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
				getView(R.id.ll_product_photo).setVisibility(View.GONE);
				getView(R.id.ll_item_devider_product_photo).setVisibility(View.GONE);
			} else {
				getView(R.id.ll_product_photo).setVisibility(View.VISIBLE);
				getView(R.id.ll_item_devider_product_photo).setVisibility(View.VISIBLE);

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
		MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST_WITH_RESET_FILTER, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
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
		finish();
	}

	private void onPubFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003005))) {
					showToast(getString(R.string.error_code_100003005, GlobalConstants.CfgConstants.MAX_UNIT_PRICE));
				} else if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003006))) {
					showToast(getString(R.string.error_code_100003006, GlobalConstants.CfgConstants.MAX_AMOUNT));
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
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
		finish();
	}

	private void onUpdateFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003005))) {
					showToast(getString(R.string.error_code_100003005, GlobalConstants.CfgConstants.MAX_UNIT_PRICE));
				} else if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003006))) {
					showToast(getString(R.string.error_code_100003006, GlobalConstants.CfgConstants.MAX_AMOUNT));
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			case GlobalMessageType.BuyMessageType.MSG_PUB_BUY_INFO_FAILED:
			case GlobalMessageType.BuyMessageType.MSG_UPDATE_PUB_BUY_INFO_FAILED:
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
