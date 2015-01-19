package com.glshop.net.ui.basic.fragment.buy.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PubBuyIndicatorType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.ImageStatusInfo;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.findbuy.ProductSpecEditActivityV2;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布买卖货物信息Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public class PubProductInfoFragmentV2 extends BasePubInfoFragmentV2 implements IMenuCallback {

	protected static final String TAG = "PubProductInfoFragmentV2";

	// 货物信息
	private BuyTextItemView mItemBuyType; // 买卖类型
	private BuyTextItemView mItemProductType; // 货物信息
	private BuyTextItemView mItemProductCategory; // 货物分类
	private BuyTextItemView mItemProductSpec; // 货物规格
	private BuyTextItemView mItemProductSpecDetail; // 货物详细规格
	private BuyEditItemView mItemProductColor;
	private BuyEditItemView mItemProductArea;
	private EditText mEtItemProductRemarks;

	// 选择菜单
	private MenuDialog menuBuyType;
	private MenuDialog menuProductType;
	private MenuDialog menuProductCategory;
	private MenuDialog menuProductSpec;

	// 实物照片
	private ImageView mIvItemProductPic1;
	private ImageView mIvItemProductPic2;
	private ImageView mIvItemProductPic3;

	// 上传照片信息
	private ImageView mUploadImageView;
	private int mUploadedPicCount = 0;
	private ImageStatusInfo[] mImgStatusList = new ImageStatusInfo[3];
	private List<ImageInfoModel> mProductImageList;

	private List<ProductCfgInfoModel> mPorductList;

	public PubProductInfoFragmentV2() {

	}

	@Override
	protected void initView() {
		mItemBuyType = getView(R.id.ll_item_buy_type);
		mItemProductType = getView(R.id.ll_item_product_type);
		mItemProductCategory = getView(R.id.ll_item_product_category);
		mItemProductSpec = getView(R.id.ll_item_product_spec);
		mItemProductSpecDetail = getView(R.id.ll_item_product_spec_detail);
		mItemProductColor = getView(R.id.ll_item_product_color);
		mItemProductArea = getView(R.id.ll_item_product_area);
		mEtItemProductRemarks = getView(R.id.et_item_product_remark);

		mIvItemProductPic1 = getView(R.id.iv_item_product_pic_1);
		mIvItemProductPic2 = getView(R.id.iv_item_product_pic_2);
		mIvItemProductPic3 = getView(R.id.iv_item_product_pic_3);

		mItemBuyType.setOnClickListener(this);
		mItemProductType.setOnClickListener(this);
		mItemProductCategory.setOnClickListener(this);
		mItemProductSpec.setOnClickListener(this);
		mItemProductSpecDetail.setOnClickListener(this);
		mIvItemProductPic1.setOnClickListener(this);
		mIvItemProductPic2.setOnClickListener(this);
		mIvItemProductPic3.setOnClickListener(this);
		getView(R.id.btn_pub_next).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		initImgStatusInfo();
		mPorductList = mSysCfgLogic.getLocalProductList();
		if (isModifyMode && mPubInfo != null) {
			mItemBuyType.setClickable(false);
			mItemBuyType.setActionIconVisible(false);
		}
		updateBuyUI();
	}

	@Override
	protected void updateBuyUI() {
		// 显示发布类型及规格信息
		updateProductSpecInfo();
		mProductPropInfo = mPubInfo.productPropInfo;

		// 颜色、产地
		mItemProductColor.setContentText(mPubInfo.productColor);
		mItemProductArea.setContentText(mPubInfo.productArea);

		// 更新实物图片
		mProductImageList = mPubInfo.productImgList;
		updateProductImageUI();
		updateProductImgStatusInfo();

		// 货物备注
		mEtItemProductRemarks.setText(mPubInfo.productRemarks);

		// 温馨提示
		if (mBuyType == BuyType.BUYER) {
			((TextView) getView(R.id.tv_pub_security_tips)).setText(R.string.business_buyer_tips);
		} else {
			((TextView) getView(R.id.tv_pub_security_tips)).setText(R.string.business_seller_tips);
		}

		// 文本定位
		requestSelection(mItemProductColor.getEditText());
		requestSelection(mItemProductArea.getEditText());
		requestSelection(mEtItemProductRemarks);
	}

	private void updateProductSpecInfo() {
		// 显示发布类型
		mBuyType = mPubInfo.buyType;
		if (mBuyType == BuyType.BUYER) {
			mItemBuyType.setContentText(mContext.getString(R.string.publish_type_buy));
		} else if (mBuyType == BuyType.SELLER) {
			mItemBuyType.setContentText(mContext.getString(R.string.publish_type_sell));
		} else {
			mItemBuyType.setContentText(mContext.getString(R.string.data_select));
		}

		// 显示货物信息
		if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(mPubInfo.productTypeCode)) {
			mProductType = ProductType.SAND;
			mItemProductType.setContentText(mPubInfo.productTypeName);
		} else if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(mPubInfo.productTypeCode)) {
			mProductType = ProductType.STONE;
			mItemProductType.setContentText(mPubInfo.productTypeName);
		} else {
			mProductType = null;
			mItemProductType.setContentText(mContext.getString(R.string.data_select));
		}

		// 显示货物分类及规格
		mProductCategoryCode = mPubInfo.productCategoryCode;
		mProductSpecId = mPubInfo.productSpecId;
		if (mProductType == ProductType.SAND) {
			mItemProductCategory.setVisibility(View.VISIBLE);
			getView(R.id.item_devider_category).setVisibility(View.VISIBLE);
			// 显示分类及规格
			mItemProductCategory.setContentText(getProductCategoryName());
			mItemProductSpec.setContentText(getProductSpecName());
		} else if (mProductType == ProductType.STONE) {
			mItemProductCategory.setVisibility(View.GONE);
			getView(R.id.item_devider_category).setVisibility(View.GONE);
			// 显示分类及规格
			mItemProductCategory.setContentText(mContext.getString(R.string.data_select));
			mItemProductSpec.setContentText(getProductSpecName());
		} else {
			mItemProductCategory.setVisibility(View.VISIBLE);
			getView(R.id.item_devider_category).setVisibility(View.VISIBLE);
			// 显示分类及规格
			mItemProductCategory.setContentText(mContext.getString(R.string.data_select));
			mItemProductSpec.setContentText(mContext.getString(R.string.data_select));
		}
	}

	private String getProductCategoryName() {
		String productCategoryName = "";
		if (StringUtils.isNotEmpty(mProductCategoryCode)) {
			List<MenuItemInfo> categoryList = SysCfgUtils.getSandTopCategoryMenu(mPorductList);
			if (BeanUtils.isNotEmpty(categoryList)) {
				for (MenuItemInfo info : categoryList) {
					if (mProductCategoryCode.equals(info.menuCode)) {
						productCategoryName = info.menuText;
						break;
					}
				}
			}
		}
		return productCategoryName;
	}

	private String getProductSpecName() {
		String productSpecName = "";
		List<MenuItemInfo> specList = null;
		if (mProductType == ProductType.SAND && StringUtils.isNotEmpty(mProductCategoryCode) && StringUtils.isNotEmpty(mProductSpecId)) {
			specList = SysCfgUtils.getSandSubCategoryMenu(mProductCategoryCode, mPorductList);
		} else if (mProductType == ProductType.STONE && StringUtils.isNotEmpty(mProductSpecId)) {
			specList = SysCfgUtils.getStoneCategoryMenu(mPorductList);
		}
		if (BeanUtils.isNotEmpty(specList)) {
			for (MenuItemInfo info : specList) {
				if (mProductSpecId.equals(info.menuID)) {
					productSpecName = info.menuText;
					break;
				}
			}
		}
		return productSpecName;
	}

	private void initImgStatusInfo() {
		for (int i = 0; i < 3; i++) {
			mImgStatusList[i] = new ImageStatusInfo();
		}
	}

	private void updateProductImgStatusInfo() {
		List<ImageInfoModel> imgUrl = mProductImageList;
		if (BeanUtils.isNotEmpty(imgUrl)) {
			for (int i = 0; i < imgUrl.size(); i++) {
				if (imgUrl.get(i) != null) {
					mImgStatusList[i].cloudId = imgUrl.get(i).cloudId;
				}
			}
		}
	}

	private void updateProductImageUI() {
		if (isModifyMode && mBuyType == BuyType.SELLER) {
			getView(R.id.ll_item_product_pic_info).setVisibility(View.VISIBLE);
		}
		if (mProductImageList != null) {
			List<ImageInfoModel> imgUrl = mProductImageList;
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
		} else {
			updateProductImgStatusUI();
		}
	}

	private void updateProductImgStatusUI() {
		mIvItemProductPic1.setVisibility(View.VISIBLE);
		mIvItemProductPic2.setVisibility(mUploadedPicCount >= 1 ? View.VISIBLE : View.INVISIBLE);
		mIvItemProductPic3.setVisibility(mUploadedPicCount >= 2 ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		ActivityUtil.hideKeyboard(getActivity());
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_item_buy_type:
			showBuyTypeMenu();
			break;
		case R.id.ll_item_product_type:
			showProductTypeMenu();
			break;
		case R.id.ll_item_product_category:
			if (mProductType != null) {
				showProductCategoryMenu();
			} else {
				showToast("请先选择货物信息!");
			}
			break;
		case R.id.ll_item_product_spec:
			if (mProductType == null) {
				showToast("请先选择货物信息!");
			} else if (mProductType == ProductType.SAND && StringUtils.isNEmpty(mProductCategoryCode)) {
				showToast("请先选择货物分类!");
			} else {
				showProductSpecMenu();
			}
			break;
		case R.id.ll_item_product_spec_detail:
			if (checkSpecArgs()) {
				clearFocus();
				intent = new Intent(mContext, ProductSpecEditActivityV2.class);
				intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_TYPE, mProductType.toValue());
				intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_INFO, mProductPropInfo);
				startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_INPUT_PRODUCT_INFO);
			}
			break;
		case R.id.iv_item_product_pic_1:
		case R.id.iv_item_product_pic_2:
		case R.id.iv_item_product_pic_3:
			mUploadImageView = (ImageView) v;
			showUploadPicTypeMenu();
			break;
		case R.id.btn_pub_next:
			if (checkArgs()) {
				clearFocus();
				if (mCallback != null) {
					mCallback.switchPubIndicator(PubBuyIndicatorType.TRADE, true);
				}
			}
			break;
		}
	}

	private boolean checkSpecArgs() {
		if (mProductType == null) {
			showToast("请选择货物信息!");
			return false;
		} else if (mProductType == ProductType.SAND && StringUtils.isNEmpty(mProductCategoryCode)) {
			showToast("请选择货物分类!");
			return false;
		} else if (StringUtils.isNEmpty(mProductSpecId)) {
			showToast("请选择货物规格!");
			return false;
		}
		return true;
	}

	private boolean checkArgs() {
		if (mBuyType == null) {
			showToast("请选择发布信息类型!");
			return false;
		} else if (mProductType == null) {
			showToast("请选择货物信息!");
			return false;
		} else if (mProductType == ProductType.SAND && StringUtils.isNEmpty(mProductCategoryCode)) {
			showToast("请选择货物分类!");
			return false;
		} else if (StringUtils.isNEmpty(mProductSpecId)) {
			showToast("请选择货物规格!");
			return false;
		} else if (StringUtils.isNEmpty(mItemProductColor.getContentText().toString().trim())) {
			showToast("请填写货物颜色!");
			return false;
		} else if (StringUtils.isNEmpty(mItemProductArea.getContentText().toString().trim())) {
			showToast("请填写货物产地!");
			return false;
		} else if (mProductPropInfo == null) {
			showToast("请填写货物详细规格!");
			return false;
		}
		return true;
	}

	@Override
	public BuyInfoModel getBuyInfo(boolean isUpdate) {
		if (!isUpdate) {
			return mPubInfo;
		}
		if (mPubInfo != null) {
			mPubInfo.buyType = mBuyType;

			// 货物规格信息
			if (mProductType == ProductType.SAND) {
				mPubInfo.productTypeName = getString(R.string.product_type_sand);
				mPubInfo.productTypeCode = DataConstants.SysCfgCode.TYPE_PRODUCT_SAND;
				mPubInfo.productCategoryCode = mProductCategoryCode;
			} else {
				mPubInfo.productTypeName = getString(R.string.product_type_stone);
				mPubInfo.productTypeCode = DataConstants.SysCfgCode.TYPE_PRODUCT_STONE;
				mPubInfo.productCategoryCode = null;
			}
			mPubInfo.productSpecId = mProductSpecId;
			mPubInfo.productSpecName = mItemProductSpec.getContentText();

			// 颜色、产地
			mPubInfo.productColor = mItemProductColor.getContentText().toString().trim();
			mPubInfo.productArea = mItemProductArea.getContentText().toString().trim();

			// 货物属性信息
			mPubInfo.productPropInfo = mProductPropInfo;

			// 实物照片信息
			if (mProductImageList == null) {
				mProductImageList = new ArrayList<ImageInfoModel>();
			}
			// 删除待上传图片
			for (int i = mProductImageList.size() - 1; i >= 0; i--) {
				ImageInfoModel imgInfo = mProductImageList.get(i);
				if (StringUtils.isEmpty(imgInfo.cloudId)) {
					mProductImageList.remove(i);
				}
			}
			List<ImageInfoModel> uploadImgList = new ArrayList<ImageInfoModel>();
			for (ImageStatusInfo statusInfo : mImgStatusList) {
				if (statusInfo.isModified && StringUtils.isNotEmpty(statusInfo.filePath)) {
					ImageInfoModel imageInfo = null;
					// 从初始实物列表中查找
					for (ImageInfoModel imgInfo : mProductImageList) {
						if (StringUtils.isNotEmpty(imgInfo.cloudId) && imgInfo.cloudId.equals(statusInfo.cloudId)) {
							imageInfo = imgInfo;
							break;
						}
					}
					if (imageInfo != null) {
						imageInfo.localFile = new File(statusInfo.filePath);
					} else {
						ImageInfoModel uploadInfo = new ImageInfoModel();
						uploadInfo.localFile = new File(statusInfo.filePath);
						uploadImgList.add(uploadInfo);
					}
				}
			}
			// 重新添加待上传图片
			mProductImageList.addAll(uploadImgList);
			mPubInfo.productImgList = mProductImageList;

			// 货物备注
			mPubInfo.productRemarks = mEtItemProductRemarks.getText().toString();
		}
		return mPubInfo;
	}

	private void showBuyTypeMenu() {
		closeDialog(menuBuyType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.buy_type)));
		menuBuyType = new MenuDialog(mContext, menu, this, true, new MenuItemInfo(mItemBuyType.getContentText()));
		menuBuyType.setMenuType(GlobalMessageType.MenuType.SELECT_BUY_TYPE);
		menuBuyType.setTitle(getString(R.string.menu_title_select_buy_type));
		menuBuyType.show();
	}

	private void showProductTypeMenu() {
		closeDialog(menuProductType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.product_type)));
		menuProductType = new MenuDialog(mContext, menu, this, true, new MenuItemInfo(mItemProductType.getContentText()));
		menuProductType.setMenuType(GlobalMessageType.MenuType.SELECT_PRODUCT_TYPE);
		menuProductType.setTitle(getString(R.string.menu_title_select_product_type));
		menuProductType.show();
	}

	private void showProductCategoryMenu() {
		closeDialog(menuProductCategory);
		List<MenuItemInfo> menuList = SysCfgUtils.getSandTopCategoryMenu(mPorductList);
		menuProductCategory = new MenuDialog(mContext, menuList, this, true, new MenuItemInfo(mItemProductCategory.getContentText()));
		menuProductCategory.setMenuType(GlobalMessageType.MenuType.SELECT_PRODUCT_CATEGORY);
		menuProductCategory.setTitle(getString(R.string.menu_title_select_product_category));
		menuProductCategory.show();
	}

	private void showProductSpecMenu() {
		closeDialog(menuProductSpec);
		List<MenuItemInfo> menuList = null;
		if (mProductType == ProductType.SAND) {
			menuList = SysCfgUtils.getSandSubCategoryMenu(mProductCategoryCode, mPorductList);
		} else if (mProductType == ProductType.STONE) {
			menuList = SysCfgUtils.getStoneCategoryMenu(mPorductList);
		}
		menuProductSpec = new MenuDialog(mContext, menuList, this, true, new MenuItemInfo(mItemProductSpec.getContentText()));
		menuProductSpec.setMenuType(GlobalMessageType.MenuType.SELECT_PRODUCT_SPEC);
		menuProductSpec.setTitle(getString(R.string.menu_title_select_product_spec));
		menuProductSpec.show();
	}

	private void clearFocus() {
		mItemProductColor.getEditText().clearFocus();
		mItemProductArea.getEditText().clearFocus();
		mEtItemProductRemarks.clearFocus();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_INPUT_PRODUCT_INFO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mProductPropInfo = (ProductInfoModel) data.getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_INFO);
				}
			}
			break;
		}
	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_BUY_TYPE: // 选择发布类型
			mItemBuyType.setContentText(menuBuyType.getAdapter().getItem(position).menuText);
			BuyType selectType = position == 0 ? BuyType.BUYER : BuyType.SELLER;
			getView(R.id.ll_item_product_pic_info).setVisibility(selectType == BuyType.BUYER ? View.GONE : View.VISIBLE);
			if (mBuyType != null && mBuyType != selectType) {
				if (mCallback != null) {
					mCallback.switchPubBuyType(selectType);
				}
			}
			mBuyType = selectType;
			break;
		case GlobalMessageType.MenuType.SELECT_PRODUCT_TYPE: // 选择货物信息
			mItemProductType.setContentText(menuProductType.getAdapter().getItem(position).menuText);
			ProductType selectProductType = position == 0 ? ProductType.SAND : ProductType.STONE;
			if (mProductType != selectProductType) {
				mProductCategoryCode = null;
				mProductSpecId = null;
				mProductPropInfo = null;
				if (selectProductType == ProductType.SAND) {
					mItemProductCategory.setVisibility(View.VISIBLE);
					getView(R.id.item_devider_category).setVisibility(View.VISIBLE);
				} else {
					mItemProductCategory.setVisibility(View.GONE);
					getView(R.id.item_devider_category).setVisibility(View.GONE);
				}
				mItemProductCategory.setContentText(mContext.getString(R.string.data_select));
				mItemProductSpec.setContentText(mContext.getString(R.string.data_select));
			}
			mProductType = selectProductType;
			break;
		case GlobalMessageType.MenuType.SELECT_PRODUCT_CATEGORY: // 选择货物分类
			mItemProductCategory.setContentText(menuProductCategory.getAdapter().getItem(position).menuText);
			mProductCategoryCode = menuProductCategory.getAdapter().getItem(position).menuCode;
			break;
		case GlobalMessageType.MenuType.SELECT_PRODUCT_SPEC: // 选择货物规格
			mItemProductSpec.setContentText(menuProductSpec.getAdapter().getItem(position).menuText);
			mProductSpecId = menuProductSpec.getAdapter().getItem(position).menuID;
			mProductPropInfo = SysCfgUtils.getProductPropInfo(mProductType, mProductSpecId, mPorductList);
			break;
		}
	}

	@Override
	public void onConfirm(int type, Object obj) {

	}

	@Override
	public void onCancel(int type) {

	}

	@Override
	protected void onPictureSelect(Bitmap bitmap, String file) {
		Logger.e(TAG, "Bitmap = " + bitmap + ", file = " + file);
		clearFocus();
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
				updateProductImgStatusUI();
			}
		}
	}

	private int getSelectUploadIndex() {
		if (mUploadImageView == mIvItemProductPic1) {
			return 0;
		} else if (mUploadImageView == mIvItemProductPic2) {
			return 1;
		} else if (mUploadImageView == mIvItemProductPic3) {
			return 2;
		} else {
			return 0;
		}
	}

}
