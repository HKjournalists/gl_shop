package com.glshop.net.ui.findbuy;

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
import com.glshop.net.common.GlobalConstants.ViewBuyInfoType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.AreaListView;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.myprofile.OtherProfileActivity;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 买卖信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class BuyInfoActivity extends BasicActivity {

	private static final String TAG = "BuyInfoActivity";

	private String pubBuyId;

	private BuyInfoModel mBuyInfo;

	private ViewBuyInfoType viewType;

	private TextView mTvTitle;
	private TextView mTvBuyTitle;
	private TextView mTvCreateTime;

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

	private TextView mTvCompanyName;
	private RatingBar mRbSatisfaction;
	private RatingBar mRbCredit;
	private TextView mTvTurnoverRate;

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
	private BuyTextItemView mItemPortShipWaterDepth;

	private AreaListView mAreaView;

	private ConfirmDialog mConfirmDialog;

	private IBuyLogic mBuyLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_info);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_buy_info_detail);

		viewType = ViewBuyInfoType.convert(getIntent().getIntExtra(GlobalAction.BuyAction.EXTRA_KEY_VIEW_BUY_INFO_TYPE, ViewBuyInfoType.FINDBUY.toValue()));
		mTvTitle = getView(R.id.tv_commmon_title);
		mTvBuyTitle = getView(R.id.tv_buy_info_title);
		mTvCreateTime = getView(R.id.tv_pub_datetime);
		mAreaView = getView(R.id.ll_area_view);
		mItemProductType = getView(R.id.ll_item_product_type);
		mItemProductSpec = getView(R.id.ll_item_product_spec);
		mItemColor = getView(R.id.ll_item_product_color);
		mItemArea = getView(R.id.ll_item_product_area);
		mTvStartDate = getView(R.id.tv_trade_start_date);
		mTvEndDate = getView(R.id.btn_trade_end_date);
		mItemTradeArea = getView(R.id.ll_trade_area_item);
		mItemDischargeAddrType = getView(R.id.ll_item_discharge_address_type);
		mTvProductRemarks = getView(R.id.tv_product_remarks_content);
		mTvBuyRemarks = getView(R.id.tv_buy_remarks_content);

		mItemAmountSingleArea = getView(R.id.ll_item_trade_amount_single_area);
		mItemAmountMoreArea = getView(R.id.ll_item_trade_amount_more_area);
		mItemUnitPrice = getView(R.id.ll_item_unit_price);

		mTvCompanyName = getView(R.id.tv_company_name);
		mRbSatisfaction = getView(R.id.rb_satisfaction);
		mRbCredit = getView(R.id.rb_credit);
		mTvTurnoverRate = getView(R.id.tv_turnover_rate);

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

		mItemSedimentPerc = getView(R.id.ll_item_product_sediment_perc);
		mItemSedimentBlockPerc = getView(R.id.ll_item_product_sediment_block_perc);
		mItemWaterPerc = getView(R.id.ll_item_product_water_perc);
		mItemAppearanceDensity = getView(R.id.ll_item_product_appearance_density);
		mItemStackingPerc = getView(R.id.ll_item_product_stacking_perc);
		mItemSturdinessPerc = getView(R.id.ll_item_product_sturdiness_perc);

		getView(R.id.ll_buy_company_profile).setOnClickListener(this);
		getView(R.id.btn_want_to_deal).setOnClickListener(this);
		getView(R.id.btn_undo_pub).setOnClickListener(this);
		getView(R.id.btn_modify_pub).setOnClickListener(this);
		getView(R.id.btn_repub).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_product_pic_3).setOnClickListener(this);
	}

	private void initData() {
		switch (viewType) {
		case FINDBUY:
			mTvTitle.setText(R.string.publish_type_buy);
			break;
		case MYBUY:
			mTvTitle.setText(R.string.my_buy);
			break;
		case MYCONTRACT:
			mTvTitle.setText(R.string.publish_type_buy);
			break;
		}

		pubBuyId = getIntent().getStringExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_ID);
		Logger.e(TAG, "PubBuyId = " + pubBuyId);

		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getBuyInfo(pubBuyId);
	}

	/**
	 * 更新UI
	 */
	private void updateUI() {
		updateBuyTitle();
		if (mBuyInfo.buyType == BuyType.BUYER) {
			// 求购页面不显示实物照片信息
			getView(R.id.ll_product_photo).setVisibility(View.GONE);
			mItemAmountSingleArea.setContentText(String.valueOf(mBuyInfo.tradeAmount));
			mItemUnitPrice.setContentText(String.valueOf(mBuyInfo.unitPrice));
		} else {
			// 出售页面显示实物照片信息、多地域价格信息
			getView(R.id.ll_product_photo).setVisibility(View.VISIBLE);
			getView(R.id.ll_single_area_list).setVisibility(mBuyInfo.isMoreArea ? View.GONE : View.VISIBLE);
			getView(R.id.ll_more_area_list).setVisibility(mBuyInfo.isMoreArea ? View.VISIBLE : View.GONE);
			if (mBuyInfo.isMoreArea) {
				// 显示多地域信息
				mAreaView.setData(mBuyInfo.areaInfoList);
			} else {
				// 显示单地域信息
				mItemAmountSingleArea.setContentText(String.valueOf(mBuyInfo.tradeAmount));
				mItemUnitPrice.setContentText(String.valueOf(mBuyInfo.unitPrice));
			}

			// 实物信息
			updateProductImage();
		}

		// 货物规格信息
		if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(mBuyInfo.productCode)) {
			mItemProductType.setContentText(getString(R.string.product_type_stone));
		} else {
			mItemProductType.setContentText(getString(R.string.product_type_sand));
		}

		if (StringUtils.isNotEmpty(mBuyInfo.productName)) {
			mItemProductSpec.setContentText(mBuyInfo.productName);
		} else {
			mItemProductSpec.setContentText(mBuyInfo.specName);
		}
		mItemColor.setContentText(mBuyInfo.productInfo.color);
		mItemArea.setContentText(mBuyInfo.productInfo.area);

		if (mBuyInfo.productInfo.sedimentPercentage != null) {
			mItemSedimentPerc.setContentText(String.valueOf(mBuyInfo.productInfo.sedimentPercentage.mRealSize));
		}

		if (mBuyInfo.productInfo.sedimentBlockPercentage != null) {
			mItemSedimentBlockPerc.setContentText(String.valueOf(mBuyInfo.productInfo.sedimentBlockPercentage.mRealSize));
		}

		if (mBuyInfo.productInfo.waterPercentage != null) {
			mItemWaterPerc.setContentText(String.valueOf(mBuyInfo.productInfo.waterPercentage.mRealSize));
		}

		if (mBuyInfo.productInfo.appearanceDensity != null) {
			mItemAppearanceDensity.setContentText(String.valueOf(mBuyInfo.productInfo.appearanceDensity.mRealSize));
		}

		if (mBuyInfo.productInfo.stackingPercentage != null) {
			mItemStackingPerc.setContentText(String.valueOf(mBuyInfo.productInfo.stackingPercentage.mRealSize));
		}

		if (mBuyInfo.productInfo.sturdinessPercentage != null) {
			mItemSturdinessPerc.setContentText(String.valueOf(mBuyInfo.productInfo.sturdinessPercentage.mRealSize));
		}

		mTvProductRemarks.setText(mBuyInfo.productInfo.remarks);

		// 交易地域及时间信息
		mTvCreateTime.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT_V2, mBuyInfo.tradePubDate));
		mItemTradeArea.setContentText(SysCfgUtils.getAreaName(this, mBuyInfo.tradeArea));
		mTvStartDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mBuyInfo.tradeBeginDate));
		mTvEndDate.setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mBuyInfo.tradeEndDate));
		if (mBuyInfo.deliveryAddrType == DeliveryAddrType.ME_DECIDE) {
			mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_me_decide));
			// 己方指定则显示卸货地址详情
			llAddrInfo.setVisibility(View.VISIBLE);
			updateDischargeAddrInfo();
		} else {
			mItemDischargeAddrType.setContentText(getString(R.string.discharge_addr_type_another_decide));
			llAddrInfo.setVisibility(View.GONE);
		}

		// 货物备注信息
		if (StringUtils.isNotEmpty(mBuyInfo.productInfo.remarks)) {
			mTvProductRemarks.setText(mBuyInfo.productInfo.remarks);
		} else {
			mTvProductRemarks.setText(R.string.buy_no_remarks);
		}

		// 买卖备注信息
		if (StringUtils.isNotEmpty(mBuyInfo.buyRemarks)) {
			mTvBuyRemarks.setText(mBuyInfo.buyRemarks);
		} else {
			mTvBuyRemarks.setText(R.string.buy_no_remarks);
		}

		// 企业信息
		mTvCompanyName.setText(StringUtils.isNotEmpty(mBuyInfo.publisherCompany) ? mBuyInfo.publisherCompany : getString(R.string.default_company_name));
		mTvTurnoverRate.setText(StringUtils.getPercent(mBuyInfo.publisherTurnoverRate));
		mRbSatisfaction.setRating(mBuyInfo.publisherSatisfaction);
		mRbCredit.setRating(mBuyInfo.publisherCredit);

		// 更新按钮
		updateActionBar();
	}

	/**
	 * 更新买卖标题
	 */
	private void updateBuyTitle() {
		StringBuffer title = new StringBuffer();
		if (mBuyInfo.buyType == BuyType.BUYER) {
			title.append(getString(R.string.buy));
		} else {
			title.append(getString(R.string.sell));
		}
		if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(mBuyInfo.productCode)) {
			title.append(getString(R.string.product_type_stone));
		} else {
			title.append(getString(R.string.product_type_sand));
		}
		title.append(mBuyInfo.tradeAmount + getString(R.string.product_unit_ton_hint));
		//mTvBuyTitle.setText(title.toString());
	}

	/**
	 * 显示可操作按钮
	 */
	private void updateActionBar() {
		if (viewType == ViewBuyInfoType.FINDBUY) {
			if (StringUtils.isNotEmpty(mBuyInfo.companyId) && mBuyInfo.companyId.equals(getCompanyId())) {
				getView(R.id.btn_want_to_deal).setVisibility(View.GONE);
			} else {
				getView(R.id.btn_want_to_deal).setVisibility(View.VISIBLE);
			}
		} else {
			getView(R.id.btn_want_to_deal).setVisibility(View.GONE);
		}
		getView(R.id.btn_undo_pub).setVisibility(viewType == ViewBuyInfoType.MYBUY && mBuyInfo.buyStatus == BuyStatus.VALID ? View.VISIBLE : View.GONE);
		getView(R.id.ll_buy_info_status).setVisibility(viewType == ViewBuyInfoType.MYBUY ? View.VISIBLE : View.GONE);
		getView(R.id.ll_company_profile).setVisibility(viewType == ViewBuyInfoType.MYBUY ? View.GONE : View.VISIBLE);
		getView(R.id.btn_modify_pub).setVisibility(viewType == ViewBuyInfoType.MYBUY && mBuyInfo.buyStatus == BuyStatus.VALID ? View.VISIBLE : View.GONE);
		getView(R.id.btn_repub).setVisibility(viewType == ViewBuyInfoType.MYBUY && mBuyInfo.buyStatus != BuyStatus.VALID ? View.VISIBLE : View.GONE);

		if (mBuyInfo.isClicked) {
			getView(R.id.btn_want_to_deal).setVisibility(View.GONE);
		}
	}

	/**
	 * 显示卸货地址信息
	 */
	private void updateDischargeAddrInfo() {
		if (mBuyInfo != null && mBuyInfo.addrInfo != null) {
			AddrInfoModel addrInfo = mBuyInfo.addrInfo;
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
		}
	}

	/**
	 * 显示实物信息
	 */
	private void updateProductImage() {
		if (mBuyInfo != null) {
			List<ImageInfoModel> imgUrl = mBuyInfo.productImgList;
			if (BeanUtils.isEmpty(imgUrl)) {
				mIvItemProductPic1.setVisibility(View.VISIBLE);
				mIvItemProductPic2.setVisibility(View.INVISIBLE);
				mIvItemProductPic3.setVisibility(View.INVISIBLE);
			} else {
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemProductPic1.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(0).cloudThumbnailUrl, mIvItemProductPic1, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				if (count >= 2) {
					mIvItemProductPic2.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(1).cloudThumbnailUrl, mIvItemProductPic2, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemProductPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemProductPic3.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(2).cloudThumbnailUrl, mIvItemProductPic3, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemProductPic3.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getBuyInfo(pubBuyId);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.BuyMessageType.MSG_GET_BUY_INFO_SUCCESS:
			onGetBuyInfoSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_GET_BUY_INFO_FAILED:
			onGetBuyInfoFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_WANT_TO_DEAL_SUCCESS:
			onSubmitWantToDealSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_WANT_TO_DEAL_FAILED:
			onSubmitWantToDealFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_UNDO_PUB_BUY_INFO_SUCCESS:
			onUndoPubSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_UNDO_PUB_BUY_INFO_FAILED:
			onUndoPubFailed(respInfo);
			break;
		}
	}

	private void onGetBuyInfoSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mBuyInfo = (BuyInfoModel) respInfo.data;
			if (mBuyInfo != null) {
				updateUI();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetBuyInfoFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private void onSubmitWantToDealSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.find_buy);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_submit_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_pub_want_to_deal);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_pub_want_to_deal_action_text);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_FIND_BUY;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitWantToDealFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onUndoPubSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST); // 更新列表
		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_buy);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_pub_cancel_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_pub_cancel_content);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_pub_cancel_action_text);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_BUY;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onUndoPubFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_buy_company_profile:
			intent = new Intent(this, OtherProfileActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_ID, mBuyInfo.companyId);
			startActivity(intent);
			break;
		case R.id.btn_want_to_deal:
			wantToDeal();
			break;
		case R.id.btn_undo_pub:
			showConfirmDialog();
			break;
		case R.id.btn_modify_pub:
			intent = new Intent(this, PubBuyInfoActivity.class);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, true);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, getBuyInfo(false));
			startActivity(intent);
			break;
		case R.id.btn_repub:
			intent = new Intent(this, PubBuyInfoActivity.class);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, true);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, getBuyInfo(true));
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		case R.id.iv_item_addr_pic_1:
			if (mBuyInfo.addrInfo != null && BeanUtils.isNotEmpty(mBuyInfo.addrInfo.addrImageList)) {
				browseImage(mBuyInfo.addrInfo.addrImageList.get(0));
			}
			break;
		case R.id.iv_item_addr_pic_2:
			if (mBuyInfo.addrInfo != null && BeanUtils.isNotEmpty(mBuyInfo.addrInfo.addrImageList)) {
				browseImage(mBuyInfo.addrInfo.addrImageList.get(1));
			}
			break;
		case R.id.iv_item_addr_pic_3:
			if (mBuyInfo.addrInfo != null && BeanUtils.isNotEmpty(mBuyInfo.addrInfo.addrImageList)) {
				browseImage(mBuyInfo.addrInfo.addrImageList.get(2));
			}
			break;
		case R.id.iv_item_product_pic_1:
			if (mBuyInfo.productImgList != null && BeanUtils.isNotEmpty(mBuyInfo.productImgList)) {
				browseImage(mBuyInfo.productImgList.get(0));
			}
			break;
		case R.id.iv_item_product_pic_2:
			if (mBuyInfo.productImgList != null && BeanUtils.isNotEmpty(mBuyInfo.productImgList)) {
				browseImage(mBuyInfo.productImgList.get(1));
			}
			break;
		case R.id.iv_item_product_pic_3:
			if (mBuyInfo.productImgList != null && BeanUtils.isNotEmpty(mBuyInfo.productImgList)) {
				browseImage(mBuyInfo.productImgList.get(2));
			}
			break;
		}
	}

	private BuyInfoModel getBuyInfo(boolean isRepub) {
		BuyInfoModel info = (BuyInfoModel) mBuyInfo.clone();
		if (isRepub) {
			info.buyId = null;
		}
		return info;
	}

	private void wantToDeal() {
		if (isLogined()) {
			showSubmitDialog(getString(R.string.submiting_request));
			mBuyLogic.wantToDeal(mBuyInfo.buyId);
		} else {
			showToast(R.string.user_not_login);
		}
	}

	private void undoPub() {
		showSubmitDialog(getString(R.string.submiting_request));
		mBuyLogic.undoPubBuyInfo(mBuyInfo.buyId);
	}

	private void showConfirmDialog() {
		if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
			mConfirmDialog.dismiss();
		}

		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.cancel_pub_warning_tips));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(Object obj) {
				undoPub();
			}

			@Override
			public void onCancel() {

			}
		});
		mConfirmDialog.show();
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
