package com.glshop.net.ui.basic.fragment.buy.filter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.buy.filter.ProductFilterAdapterV1;
import com.glshop.net.ui.basic.adapter.buy.filter.ProductFilterAdapterV2;
import com.glshop.net.ui.basic.adapter.buy.filter.BaseProductFilterAdapter.OnItemSelectChangeListener;
import com.glshop.net.ui.basic.view.ListViewV2;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 货物规格筛选Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class ProductFilterFragment extends BaseFilterFragment implements OnItemSelectChangeListener {

	private static final String TAG = "ProductFilterFragment";

	private CheckedTextView mCkbSelectAll;
	private CheckedTextView mCkbSelectAllSand;
	private CheckedTextView mCkbSelectAllStone;

	private ListViewV2 mLvList1;
	private ListViewV2 mLvList2;
	private ListViewV2 mLvList3;

	private ProductFilterAdapterV1 mSandAdapter1;
	private ProductFilterAdapterV2 mSandAdapter2;
	private ProductFilterAdapterV1 mStoneAdapter;

	private List<ProductCfgInfoModel> mTypeCategoryList;

	private List<String> mProductIdList;

	public ProductFilterFragment() {

	}

	@Override
	protected void initView() {
		mCkbSelectAll = getView(R.id.chkTv_select_all);
		mCkbSelectAllSand = getView(R.id.chkTv_select_sand);
		mCkbSelectAllStone = getView(R.id.chkTv_select_stone);

		mLvList1 = getView(R.id.lv_menu_list_1);
		mLvList2 = getView(R.id.lv_menu_list_2);
		mLvList3 = getView(R.id.lv_menu_list_3);

		getView(R.id.ll_select_all_product).setOnClickListener(this);
		getView(R.id.ll_selected_status_sand).setOnClickListener(this);
		getView(R.id.ll_selected_status_stone).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		mTypeCategoryList = mSysCfgLogic.getLocalProductCategoryList();
		if (BeanUtils.isNotEmpty(mTypeCategoryList) && mTypeCategoryList.size() == 2) {
			mSandAdapter1 = new ProductFilterAdapterV1(mContext, this, mTypeCategoryList.get(0).childList);
			mSandAdapter2 = new ProductFilterAdapterV2(mContext, this, mTypeCategoryList.get(0).childList.get(0).childList);
			mStoneAdapter = new ProductFilterAdapterV1(mContext, this, mTypeCategoryList.get(1).childList);

			mSandAdapter1.setListView(mLvList1);
			mSandAdapter2.setListView(mLvList2);
			mStoneAdapter.setListView(mLvList3);

			mLvList1.setAdapter(mSandAdapter1);
			mLvList2.setAdapter(mSandAdapter2);
			mLvList3.setAdapter(mStoneAdapter);

			if (BeanUtils.isNotEmpty(mSandAdapter1.getList())) {
				mSandAdapter1.setFocusedInfo(mSandAdapter1.getItem(0));
			}
		}

		if (mFilterInfo != null) {
			mProductIdList = mFilterInfo.productIdList;
			if (BeanUtils.isEmpty(mProductIdList)) {
				mCkbSelectAll.setChecked(true);
				doGlobalSelectAction(false);
			} else {
				mCkbSelectAll.setChecked(false);
				refreshTopCategoryUI();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_select_all_product:
			mCkbSelectAll.toggle();
			if (mCkbSelectAll.isChecked()) {
				doGlobalSelectAction(false);
			}
			break;
		case R.id.ll_selected_status_sand:
			mCkbSelectAllSand.toggle();
			ProductCfgInfoModel topSandTypeInfo = mTypeCategoryList.get(0);
			topSandTypeInfo.isSelectedForUI = mCkbSelectAllSand.isChecked();
			updateLinkStatus(topSandTypeInfo);
			break;
		case R.id.ll_selected_status_stone:
			mCkbSelectAllStone.toggle();
			ProductCfgInfoModel topStoneTypeInfo = mTypeCategoryList.get(1);
			topStoneTypeInfo.isSelectedForUI = mCkbSelectAllStone.isChecked();
			updateLinkStatus(topStoneTypeInfo);
			break;
		}
	}

	@Override
	public void onItemClick(View view, int position) {
		if (view == mLvList1) {
			List<ProductCfgInfoModel> categoryList = mSandAdapter1.getItem(position).childList;
			mSandAdapter2 = new ProductFilterAdapterV2(mContext, this, categoryList);
			mSandAdapter2.setListView(mLvList2);
			mLvList2.setAdapter(mSandAdapter2);
			mSandAdapter1.setFocusedInfo(mSandAdapter1.getItem(position));
		}
		//refreshAdapter();
	}

	@Override
	public void onItemSelectChanged(View view, ProductCfgInfoModel info, int position) {
		updateLinkStatus(info);
		onItemClick(view, position);
		if (view == mLvList1) {

		} else if (view == mLvList2) {

		} else if (view == mLvList3) {

		}
	}

	private void updateLinkStatus(ProductCfgInfoModel productInfo) {
		// Step1. 更新子地域节点状态
		updateChildStatus(productInfo, productInfo.isSelectedForUI);
		// Step2. 更新父地域节点状态
		updateParentStatus(productInfo);
		// Step3. 更新全新按钮状态
		updateGlobalSelectStatus();
		// Step4. 筛选界面
		refreshAdapter();
	}

	private void updateChildStatus(ProductCfgInfoModel productInfo, boolean isSelectedForUI) {
		if (productInfo != null && BeanUtils.isNotEmpty(productInfo.childList)) {
			List<ProductCfgInfoModel> childList = productInfo.childList;
			for (ProductCfgInfoModel info : childList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, productInfo.isSelectedForUI);
			}
		}
	}

	private void updateParentStatus(ProductCfgInfoModel productInfo) {
		if (productInfo != null && productInfo.parent != null) {
			List<ProductCfgInfoModel> childList = productInfo.parent.childList;
			if (BeanUtils.isNotEmpty(childList)) {
				int selectedCount = 0;
				for (ProductCfgInfoModel info : childList) {
					if (info != null && info.isSelectedForUI) {
						selectedCount++;
					}
				}
				if (selectedCount == 0) {
					if (productInfo.parent.isSelectedForUI) {
						productInfo.parent.isSelectedForUI = false;
						updateParentStatus(productInfo.parent);
					}
				} else if (selectedCount == 1) {
					if (!productInfo.parent.isSelectedForUI) {
						productInfo.parent.isSelectedForUI = true;
						updateParentStatus(productInfo.parent);
					}
				}
			}
		}
	}

	private void updateGlobalSelectStatus() {
		if (BeanUtils.isNotEmpty(mTypeCategoryList)) {
			boolean hasSelectedItem = false;
			for (ProductCfgInfoModel info : mTypeCategoryList) {
				if (info != null && info.isSelectedForUI) {
					hasSelectedItem = true;
					break;
				}
			}
			mCkbSelectAll.setChecked(!hasSelectedItem);
		}
	}

	private void doGlobalSelectAction(boolean isSelectedForUI) {
		if (BeanUtils.isNotEmpty(mTypeCategoryList)) {
			for (ProductCfgInfoModel info : mTypeCategoryList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, isSelectedForUI);
			}
		}
		refreshAdapter();
	}

	private void refreshAdapter() {
		refreshTopCategoryUI();
		if (mSandAdapter1 != null) {
			mSandAdapter1.notifyDataSetChanged();
		}
		if (mSandAdapter2 != null) {
			mSandAdapter2.notifyDataSetChanged();
		}
		if (mStoneAdapter != null) {
			mStoneAdapter.notifyDataSetChanged();
		}
	}

	private void refreshTopCategoryUI() {
		ProductCfgInfoModel topSandTypeInfo = mTypeCategoryList.get(0);
		mCkbSelectAllSand.setChecked(topSandTypeInfo.isSelectedForUI);
		ProductCfgInfoModel topStoneTypeInfo = mTypeCategoryList.get(1);
		mCkbSelectAllStone.setChecked(topStoneTypeInfo.isSelectedForUI);

		if (mCkbSelectAllSand.isChecked()) {
			getView(R.id.ll_selected_status_sand).setBackgroundResource(R.drawable.bg_list_item_press);
		} else {
			getView(R.id.ll_selected_status_sand).setBackgroundResource(R.drawable.selector_list_item);
		}
		if (mCkbSelectAllStone.isChecked()) {
			getView(R.id.ll_selected_status_stone).setBackgroundResource(R.drawable.bg_list_item_press);
		} else {
			getView(R.id.ll_selected_status_stone).setBackgroundResource(R.drawable.selector_list_item);
		}
	}

	@Override
	protected void updateFilterUI() {

	}

	@Override
	public BuyFilterInfoModelV2 getFilterInfo(boolean isUpdate) {
		if (!isAdded() && !isInited) {
			return getDefaultInfo();
		} else {
			if (isUpdate) {
				mFilterInfo.productIdList = getSelectedProductIds();
			}
		}
		return mFilterInfo;
	}

	private List<String> getSelectedProductIds() {
		mProductIdList = new ArrayList<String>();
		if (!mCkbSelectAll.isChecked()) {
			getSelectedProductIds(mProductIdList, mTypeCategoryList);
		}
		return mProductIdList;
	}

	private void getSelectedProductIds(List<String> selectedlist, List<ProductCfgInfoModel> productList) {
		if (selectedlist != null && BeanUtils.isNotEmpty(productList)) {
			for (ProductCfgInfoModel info : productList) {
				if (info != null && info.isSelectedForUI) {
					if (BeanUtils.isNEmpty(info.childList)) {
						if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
							selectedlist.add(info.mSubCategoryId);
						} else if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(info.mTypeCode)) {
							selectedlist.add(info.mCategoryId);
						}
					} else {
						getSelectedProductIds(selectedlist, info.childList);
					}
				}
			}
		}
	}

	@Override
	public void onFilterReset() {
		if (isAdded() && isInited) {
			mCkbSelectAll.setChecked(true);
			doGlobalSelectAction(false);
		} else {
			Bundle filterArgs = getArguments();
			mFilterInfo = (BuyFilterInfoModelV2) filterArgs.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
			if (mFilterInfo != null) {
				mFilterInfo.productIdList = null;
			}
			filterArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mFilterInfo);
			setArguments(filterArgs);
		}
	}

	@Override
	public void onFilterCancel() {
		if (BeanUtils.isNotEmpty(mTypeCategoryList)) {
			updateProductList(mTypeCategoryList, false);
		}
	}

	@Override
	public void onFilterSave() {
		if (BeanUtils.isNotEmpty(mTypeCategoryList)) {
			updateProductList(mTypeCategoryList, true);
		}
	}

	private void updateProductList(List<ProductCfgInfoModel> list, boolean isPersist) {
		if (BeanUtils.isNotEmpty(list)) {
			for (ProductCfgInfoModel info : list) {
				if (isPersist) {
					info.isSelectedForDB = info.isSelectedForUI;
				} else {
					info.isSelectedForUI = info.isSelectedForDB;
				}
				updateProductList(info.childList, isPersist);
			}
		}
	}

	@Override
	public boolean checkArgs() {
		// TODO
		return true;
	}
}
