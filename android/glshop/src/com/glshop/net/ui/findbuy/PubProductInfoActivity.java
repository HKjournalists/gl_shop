package com.glshop.net.ui.findbuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布买卖信息货物规格输入页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PubProductInfoActivity extends BasicActivity {

	private static final String TAG = "PubProductInfoActivity";

	private BuyEditItemView mItemColor;
	private BuyEditItemView mItemArea;
	private BuyEditItemView mItemSedimentPerc;
	private BuyEditItemView mItemSedimentBlockPerc;
	private BuyEditItemView mItemWaterPerc;
	private BuyEditItemView mItemCrunchPerc;
	private BuyEditItemView mItemNeedlePlatePerc;
	private BuyEditItemView mItemAppearanceDensity;
	private BuyEditItemView mItemStackingPerc;
	private BuyEditItemView mItemSturdinessPerc;
	private EditText mEtProductRemarks;

	private ProductType mProductType;
	private ProductInfoModel mProductInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_product_info);
		initView();
		initData();
	}

	private void initView() {
		mItemColor = getView(R.id.ll_item_product_color);
		mItemArea = getView(R.id.ll_item_product_area);
		mItemSedimentPerc = getView(R.id.ll_item_product_sediment_perc);
		mItemSedimentBlockPerc = getView(R.id.ll_item_product_sediment_block_perc);
		mItemWaterPerc = getView(R.id.ll_item_product_water_perc);
		mItemCrunchPerc = getView(R.id.ll_item_product_crunch_perc);
		mItemNeedlePlatePerc = getView(R.id.ll_item_product_needle_plate_perc);
		mItemAppearanceDensity = getView(R.id.ll_item_product_appearance_density);
		mItemStackingPerc = getView(R.id.ll_item_product_stacking_perc);
		mItemSturdinessPerc = getView(R.id.ll_item_product_sturdiness_perc);
		mEtProductRemarks = getView(R.id.et_item_product_remark);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.publish_buy_message);
		getView(R.id.btn_commmon_action).setVisibility(View.VISIBLE);
		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mProductType = ProductType.convert(getIntent().getIntExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_TYPE, ProductType.SAND.toValue()));
		mProductInfo = (ProductInfoModel) getIntent().getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_INFO);

		if (mProductType == ProductType.SAND) {
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

		if (mProductInfo != null) {
			mItemColor.setContentText(mProductInfo.color);
			mItemArea.setContentText(mProductInfo.area);
			initPropInfo(mItemSedimentPerc, mProductInfo.sedimentPercentage);
			initPropInfo(mItemSedimentBlockPerc, mProductInfo.sedimentBlockPercentage);
			initPropInfo(mItemWaterPerc, mProductInfo.waterPercentage);
			initPropInfo(mItemCrunchPerc, mProductInfo.crunchPercentage);
			initPropInfo(mItemNeedlePlatePerc, mProductInfo.needlePlatePercentage);
			initPropInfo(mItemAppearanceDensity, mProductInfo.appearanceDensity);
			initPropInfo(mItemStackingPerc, mProductInfo.stackingPercentage);
			initPropInfo(mItemSturdinessPerc, mProductInfo.sturdinessPercentage);
			mEtProductRemarks.setText(mProductInfo.remarks);
		}
	}

	private void initPropInfo(BuyEditItemView item, ProductPropInfoModel info) {
		if (info != null) {
			if (StringUtils.isNotEmpty(String.valueOf(info.mRealSize)) && info.mRealSize != 0) {
				item.setContentText(String.valueOf(info.mRealSize));
			} else {
				item.setContentText(String.valueOf(info.mDefaultSize));
			}
		} else {
			item.setContentText("0");
		}
	}

	private void saveProductInfo() {
		if (mProductInfo == null) {
			mProductInfo = new ProductInfoModel();
		}
		mProductInfo.color = mItemColor.getContentText();
		mProductInfo.area = mItemArea.getContentText();

		if (mProductInfo.sedimentPercentage != null && StringUtils.isNotEmpty(mItemSedimentPerc.getContentText())) {
			mProductInfo.sedimentPercentage.mRealSize = Float.parseFloat(mItemSedimentPerc.getContentText());
		}
		if (mProductInfo.sedimentBlockPercentage != null && StringUtils.isNotEmpty(mItemSedimentBlockPerc.getContentText())) {
			mProductInfo.sedimentBlockPercentage.mRealSize = Float.parseFloat(mItemSedimentBlockPerc.getContentText());
		}
		if (mProductInfo.waterPercentage != null && StringUtils.isNotEmpty(mItemWaterPerc.getContentText())) {
			mProductInfo.waterPercentage.mRealSize = Float.parseFloat(mItemWaterPerc.getContentText());
		}
		if (mProductInfo.crunchPercentage != null && StringUtils.isNotEmpty(mItemCrunchPerc.getContentText())) {
			mProductInfo.crunchPercentage.mRealSize = Float.parseFloat(mItemCrunchPerc.getContentText());
		}
		if (mProductInfo.needlePlatePercentage != null && StringUtils.isNotEmpty(mItemNeedlePlatePerc.getContentText())) {
			mProductInfo.needlePlatePercentage.mRealSize = Float.parseFloat(mItemNeedlePlatePerc.getContentText());
		}
		if (mProductInfo.appearanceDensity != null && StringUtils.isNotEmpty(mItemAppearanceDensity.getContentText())) {
			mProductInfo.appearanceDensity.mRealSize = Float.parseFloat(mItemAppearanceDensity.getContentText());
		}
		if (mProductInfo.stackingPercentage != null && StringUtils.isNotEmpty(mItemStackingPerc.getContentText())) {
			mProductInfo.stackingPercentage.mRealSize = Float.parseFloat(mItemStackingPerc.getContentText());
		}
		if (mProductInfo.sturdinessPercentage != null && StringUtils.isNotEmpty(mItemSturdinessPerc.getContentText())) {
			mProductInfo.sturdinessPercentage.mRealSize = Float.parseFloat(mItemSturdinessPerc.getContentText());
		}

		mProductInfo.remarks = mEtProductRemarks.getText().toString();
	}

	private boolean checkArgs() {
		String color = mItemColor.getContentText();
		String area = mItemArea.getContentText();
		if (StringUtils.isNEmpty(color)) {
			showToast(R.string.error_empty_product_color);
			return false;
		} else if (StringUtils.isNEmpty(area)) {
			showToast(R.string.error_empty_product_area);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			if (checkArgs()) {
				saveProductInfo();
				ActivityUtil.hideKeyboard(this);
				Intent intent = new Intent();
				intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_INFO, mProductInfo);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
			break;

		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

}
