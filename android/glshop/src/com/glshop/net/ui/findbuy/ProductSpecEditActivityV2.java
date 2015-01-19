package com.glshop.net.ui.findbuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * @Description : 货物规格编辑页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ProductSpecEditActivityV2 extends BasicActivity {

	private static final String TAG = "ProductSpecEditActivityV2";

	private BuyEditItemView mItemSedimentPerc;
	private BuyEditItemView mItemSedimentBlockPerc;
	private BuyEditItemView mItemWaterPerc;
	private BuyEditItemView mItemCrunchPerc;
	private BuyEditItemView mItemNeedlePlatePerc;
	private BuyEditItemView mItemAppearanceDensity;
	private BuyEditItemView mItemStackingPerc;
	private BuyEditItemView mItemSturdinessPerc;

	private ProductType mProductType;
	private ProductInfoModel mProductInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_spec_edit);
		initView();
		initData();
	}

	private void initView() {
		mItemSedimentPerc = getView(R.id.ll_item_product_sediment_perc);
		mItemSedimentBlockPerc = getView(R.id.ll_item_product_sediment_block_perc);
		mItemWaterPerc = getView(R.id.ll_item_product_water_perc);
		mItemCrunchPerc = getView(R.id.ll_item_product_crunch_perc);
		mItemNeedlePlatePerc = getView(R.id.ll_item_product_needle_plate_perc);
		mItemAppearanceDensity = getView(R.id.ll_item_product_appearance_density);
		mItemStackingPerc = getView(R.id.ll_item_product_stacking_perc);
		mItemSturdinessPerc = getView(R.id.ll_item_product_sturdiness_perc);

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
			initPropInfo(mItemSedimentPerc, mProductInfo.sedimentPercentage);
			initPropInfo(mItemSedimentBlockPerc, mProductInfo.sedimentBlockPercentage);
			initPropInfo(mItemWaterPerc, mProductInfo.waterPercentage);
			initPropInfo(mItemCrunchPerc, mProductInfo.crunchPercentage);
			initPropInfo(mItemNeedlePlatePerc, mProductInfo.needlePlatePercentage);
			initPropInfo(mItemAppearanceDensity, mProductInfo.appearanceDensity);
			initPropInfo(mItemStackingPerc, mProductInfo.stackingPercentage);
			initPropInfo(mItemSturdinessPerc, mProductInfo.sturdinessPercentage);
		}
	}

	private void initPropInfo(BuyEditItemView item, ProductPropInfoModel info) {
		if (info != null) {
			if (StringUtils.isNotEmpty(info.mRealSize)) {
				item.setContentText(info.mRealSize);
			} else {
				item.setContentHint(getString(R.string.data_no_input));
			}
			requestSelection(item.getEditText());
		}
	}

	private void saveProductInfo() {
		if (mProductInfo == null) {
			mProductInfo = new ProductInfoModel();
		}
		if (mProductInfo.sedimentPercentage != null) {
			mProductInfo.sedimentPercentage.mRealSize = mItemSedimentPerc.getContentText();
		}
		if (mProductInfo.sedimentBlockPercentage != null) {
			mProductInfo.sedimentBlockPercentage.mRealSize = mItemSedimentBlockPerc.getContentText();
		}
		if (mProductType == ProductType.SAND && mProductInfo.waterPercentage != null) {
			mProductInfo.waterPercentage.mRealSize = mItemWaterPerc.getContentText();
		}
		if (mProductType == ProductType.STONE && mProductInfo.crunchPercentage != null) {
			mProductInfo.crunchPercentage.mRealSize = mItemCrunchPerc.getContentText();
		}
		if (mProductType == ProductType.STONE && mProductInfo.needlePlatePercentage != null) {
			mProductInfo.needlePlatePercentage.mRealSize = mItemNeedlePlatePerc.getContentText();
		}
		if (mProductInfo.appearanceDensity != null) {
			mProductInfo.appearanceDensity.mRealSize = mItemAppearanceDensity.getContentText();
		}
		if (mProductInfo.stackingPercentage != null) {
			mProductInfo.stackingPercentage.mRealSize = mItemStackingPerc.getContentText();
		}
		if (mProductInfo.sturdinessPercentage != null) {
			mProductInfo.sturdinessPercentage.mRealSize = mItemSturdinessPerc.getContentText();
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isNEmpty(mItemSedimentPerc.getContentText())) {
			showToast("请输入含泥量!");
			return false;
		} else if (Float.parseFloat(mItemSedimentPerc.getContentText()) == 0) {
			showToast("含泥量不能为0!");
			return false;
		} else if (StringUtils.isNEmpty(mItemSedimentBlockPerc.getContentText())) {
			showToast("请输入泥块泥量!");
			return false;
		} else if (Float.parseFloat(mItemSedimentBlockPerc.getContentText()) == 0) {
			showToast("泥块泥量不能为0!");
			return false;
		} else if (mProductType == ProductType.SAND && StringUtils.isNEmpty(mItemWaterPerc.getContentText())) {
			showToast("请输入含水量!");
			return false;
		} else if (mProductType == ProductType.SAND && !StringUtils.isNEmpty(mItemWaterPerc.getContentText()) && Float.parseFloat(mItemWaterPerc.getContentText()) == 0) {
			showToast("含水量不能为0!");
			return false;
		} else if (mProductType == ProductType.STONE && StringUtils.isNEmpty(mItemCrunchPerc.getContentText())) {
			showToast("请输入压碎值指标!");
			return false;
		} else if (mProductType == ProductType.STONE && !StringUtils.isNEmpty(mItemCrunchPerc.getContentText()) && Float.parseFloat(mItemCrunchPerc.getContentText()) == 0) {
			showToast("压碎值指标不能为0!");
			return false;
		} else if (mProductType == ProductType.STONE && StringUtils.isNEmpty(mItemNeedlePlatePerc.getContentText())) {
			showToast("请输入针片状颗粒含量!");
			return false;
		} else if (mProductType == ProductType.STONE && !StringUtils.isNEmpty(mItemNeedlePlatePerc.getContentText()) && Float.parseFloat(mItemNeedlePlatePerc.getContentText()) == 0) {
			showToast("针片状颗粒含量不能为0!");
			return false;
		} else if (StringUtils.isNEmpty(mItemAppearanceDensity.getContentText())) {
			showToast("请输入表观密度!");
			return false;
		} else if (Float.parseFloat(mItemAppearanceDensity.getContentText()) == 0) {
			showToast("表观密度不能为0!");
			return false;
		} else if (StringUtils.isNEmpty(mItemStackingPerc.getContentText())) {
			showToast("请输入堆积密度!");
			return false;
		} else if (Float.parseFloat(mItemStackingPerc.getContentText()) == 0) {
			showToast("堆积密度不能为0!");
			return false;
		} else if (StringUtils.isNEmpty(mItemSturdinessPerc.getContentText())) {
			showToast("请输入坚固性指标!");
			return false;
		} else if (Float.parseFloat(mItemSturdinessPerc.getContentText()) == 0) {
			showToast("坚固性指不能为0!");
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			if (/*checkArgs()*/true) {
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
