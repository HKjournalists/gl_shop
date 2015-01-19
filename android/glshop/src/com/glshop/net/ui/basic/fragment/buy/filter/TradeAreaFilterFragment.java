package com.glshop.net.ui.basic.fragment.buy.filter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.adapter.buy.filter.FilterAdapterV1;
import com.glshop.net.ui.basic.adapter.buy.filter.FilterAdapterV2;
import com.glshop.net.ui.basic.adapter.buy.filter.FilterAdapterV1.OnItemSelectChangeListener;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 交易地域筛选Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class TradeAreaFilterFragment extends BaseFilterFragment implements OnItemClickListener, OnItemSelectChangeListener {

	private static final String TAG = "TradeAreaFilterFragment";

	private CheckedTextView mCkbSelectAll;

	private ListView mLvList1;
	private ListView mLvList2;
	private ListView mLvList3;

	private FilterAdapterV1 mAdapter1;
	private FilterAdapterV1 mAdapter2;
	private FilterAdapterV2 mAdapter3;

	private List<String> mProvinceCodeList;
	private List<String> mDistrictCodeList;

	public TradeAreaFilterFragment() {

	}

	@Override
	protected void initView() {
		mCkbSelectAll = getView(R.id.chkTv_select_all);
		mLvList1 = getView(R.id.lv_menu_list_1);
		mLvList2 = getView(R.id.lv_menu_list_2);
		mLvList3 = getView(R.id.lv_menu_list_3);

		mLvList1.setOnItemClickListener(this);
		mLvList2.setOnItemClickListener(this);
		mLvList3.setOnItemClickListener(this);

		getView(R.id.ll_select_all_area).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		updateListUI(null, 0);
		updateGlobalSelectStatus();
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
				mFilterInfo.provinceCodeList = getSelectedProvinceCodeList();
				mFilterInfo.districtCodeList = getSelectedDistrictCodeList();
			}
		}
		return mFilterInfo;
	}

	private List<String> getSelectedProvinceCodeList() {
		mProvinceCodeList = new ArrayList<String>();
		if (!mCkbSelectAll.isChecked()) {
			List<AreaInfoModel> areaList = getLocalAreaList();
			if (BeanUtils.isNotEmpty(areaList)) {
				for (AreaInfoModel info : areaList) {
					if (info != null && info.isSelectedForUI) {
						mProvinceCodeList.add(info.code);
					}
				}
			}
		}
		return mProvinceCodeList;
	}

	private List<String> getSelectedDistrictCodeList() {
		mDistrictCodeList = new ArrayList<String>();
		if (!mCkbSelectAll.isChecked()) {
			List<AreaInfoModel> areaList = getLocalAreaList();
			if (BeanUtils.isNotEmpty(areaList)) {
				for (AreaInfoModel info : areaList) {
					if (info != null && info.isSelectedForUI) {
						getSelectedDistrictCodeList(mDistrictCodeList, info, 1);
					}
				}
			}
		}
		return mDistrictCodeList;
	}

	private void getSelectedDistrictCodeList(List<String> selectedlist, AreaInfoModel areaInfo, int depth) {
		if (selectedlist != null && areaInfo != null && depth < 3) {
			if (BeanUtils.isNotEmpty(areaInfo.childList)) {
				for (AreaInfoModel info : areaInfo.childList) {
					if (depth == 1) {
						getSelectedDistrictCodeList(selectedlist, info, depth + 1);
					} else if (depth == 2 && !info.isSelectedForUI) {
						selectedlist.add(info.code);
					}
				}
			} else {
				if (depth == 1) {
					// 暂不处理
				} else if (depth == 2) {
					if (areaInfo.isSelectedForUI) {
						// 暂不处理
					} else {
						String areaCode = DataConstants.SysCfgCode.AREA_TOP_CODE + "|" + areaInfo.parent.code + "|" + areaInfo.code;
						List<AreaInfoModel> areaList = mSysCfgLogic.getChildAreaInfo(areaCode);
						if (BeanUtils.isNotEmpty(areaList)) {
							for (AreaInfoModel info : areaList) {
								if (info != null && !info.isSelectedForUI) {
									selectedlist.add(info.code);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_select_all_area:
			mCkbSelectAll.toggle();
			if (mCkbSelectAll.isChecked()) {
				doGlobalSelectAction(false);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		handelItemClick(parent, position);
	}

	@Override
	public void onItemSelectChanged(View parent, AreaInfoModel info, int position) {
		updateLinkStatus(info);
		handelItemClick(parent, position);
	}

	public void handelItemClick(View parent, int position) {
		if (parent == mLvList1) {
			mAdapter1.setFocusedInfo(mAdapter1.getItem(position));
			updateListUI(mAdapter1.getItem(position), 1);
		} else if (parent == mLvList2) {
			mAdapter2.setFocusedInfo(mAdapter2.getItem(position));
			updateListUI(mAdapter2.getItem(position), 2);
		} else if (parent == mLvList3) {
			AreaInfoModel info = mAdapter3.getItem(position);
			info.isSelectedForUI = !info.isSelectedForUI;
			updateParentStatus(info);
			updateGlobalSelectStatus();
			refreshAdapter();
		}
	}

	private void updateListUI(AreaInfoModel areaInfo, int depth) {
		StringBuffer areaCode = new StringBuffer(DataConstants.SysCfgCode.AREA_TOP_CODE);
		switch (depth) {
		case 0: // 更新省、市、区列表
			List<AreaInfoModel> provinceList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
			mAdapter1 = new FilterAdapterV1(mContext, this, provinceList);
			mAdapter1.setListView(mLvList1);
			if (BeanUtils.isNotEmpty(provinceList)) {
				mAdapter1.setFocusedInfo(provinceList.get(0));
				areaCode.append("|" + provinceList.get(0).code);
				List<AreaInfoModel> cityList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
				mAdapter2 = new FilterAdapterV1(mContext, this, cityList);
				mAdapter2.setListView(mLvList2);
				if (BeanUtils.isNotEmpty(cityList)) {
					mAdapter2.setFocusedInfo(cityList.get(0));
					areaCode.append("|" + cityList.get(0).code);
					List<AreaInfoModel> districtList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
					mAdapter3 = new FilterAdapterV2(mContext, districtList);
				}
			}
			mLvList1.setAdapter(mAdapter1);
			mLvList2.setAdapter(mAdapter2);
			mLvList3.setAdapter(mAdapter3);
			break;
		case 1: // 更新市、区列表
			areaCode.append(areaCode + "|" + areaInfo.code);
			List<AreaInfoModel> cityList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
			mAdapter2 = new FilterAdapterV1(mContext, this, cityList);
			mAdapter2.setListView(mLvList2);
			if (BeanUtils.isNotEmpty(cityList)) {
				mAdapter2.setFocusedInfo(cityList.get(0));
				areaCode.append("|" + cityList.get(0).code);
				List<AreaInfoModel> districtList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
				mAdapter3 = new FilterAdapterV2(mContext, districtList);
			} else {
				mAdapter2 = null;
				mAdapter3 = null;
			}
			mLvList2.setAdapter(mAdapter2);
			mLvList3.setAdapter(mAdapter3);
			break;
		case 2: // 更新区列表
			areaCode.append(areaCode + "|" + areaInfo.parent.code + "|" + areaInfo.code);
			List<AreaInfoModel> districtList = mSysCfgLogic.getChildAreaInfo(areaCode.toString());
			mAdapter3 = new FilterAdapterV2(mContext, districtList);
			mLvList3.setAdapter(mAdapter3);
			break;
		case 3:
			// TODO
			break;
		}
	}

	private void updateLinkStatus(AreaInfoModel areaInfo) {
		// Step1. 更新子地域节点状态
		updateChildStatus(areaInfo, areaInfo.isSelectedForUI);
		// Step2. 更新父地域节点状态
		updateParentStatus(areaInfo);
		// Step3. 更新全新按钮状态
		updateGlobalSelectStatus();
		// Step4. 筛选界面
		refreshAdapter();
	}

	private void updateChildStatus(AreaInfoModel areaInfo, boolean isSelectedForUI) {
		if (areaInfo != null && BeanUtils.isNotEmpty(areaInfo.childList)) {
			List<AreaInfoModel> childList = areaInfo.childList;
			for (AreaInfoModel info : childList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, areaInfo.isSelectedForUI);
			}
		}
	}

	private void updateParentStatus(AreaInfoModel areaInfo) {
		if (areaInfo != null && areaInfo.parent != null) {
			List<AreaInfoModel> childList = areaInfo.parent.childList;
			if (BeanUtils.isNotEmpty(childList)) {
				int selectedCount = 0;
				for (AreaInfoModel info : childList) {
					if (info != null && info.isSelectedForUI) {
						selectedCount++;
					}
				}
				if (selectedCount == 0) {
					if (areaInfo.parent.isSelectedForUI) {
						areaInfo.parent.isSelectedForUI = false;
						updateParentStatus(areaInfo.parent);
					}
				} else if (selectedCount == 1) {
					if (!areaInfo.parent.isSelectedForUI) {
						areaInfo.parent.isSelectedForUI = true;
						updateParentStatus(areaInfo.parent);
					}
				}
			}
		}
	}

	private void updateGlobalSelectStatus() {
		List<AreaInfoModel> areaList = getLocalAreaList();
		if (BeanUtils.isNotEmpty(areaList)) {
			boolean hasSelectedItem = false;
			for (AreaInfoModel info : areaList) {
				if (info != null && info.isSelectedForUI) {
					hasSelectedItem = true;
					break;
				}
			}
			mCkbSelectAll.setChecked(!hasSelectedItem);
		}
	}

	private void refreshAdapter() {
		if (mAdapter1 != null) {
			mAdapter1.notifyDataSetChanged();
		}
		if (mAdapter2 != null) {
			mAdapter2.notifyDataSetChanged();
		}
		if (mAdapter3 != null) {
			mAdapter3.notifyDataSetChanged();
		}
	}

	private void doGlobalSelectAction(boolean isSelectedForUI) {
		List<AreaInfoModel> areaList = getLocalAreaList();
		if (BeanUtils.isNotEmpty(areaList)) {
			for (AreaInfoModel info : areaList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, isSelectedForUI);
			}
		}
		refreshAdapter();
	}

	@Override
	public void onFilterReset() {
		if (isAdded() && isInited) {
			mCkbSelectAll.setChecked(true);
		} else {
			Bundle filterArgs = getArguments();
			mFilterInfo = (BuyFilterInfoModelV2) filterArgs.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
			if (mFilterInfo != null) {
				mFilterInfo.provinceCodeList = null;
				mFilterInfo.districtCodeList = null;
			}
			filterArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mFilterInfo);
			setArguments(filterArgs);
		}
		doGlobalSelectAction(false);
	}

	@Override
	public void onFilterCancel() {
		if (isAdded() && isInited) {
			List<AreaInfoModel> areaList = getLocalAreaList();
			if (BeanUtils.isNotEmpty(areaList)) {
				updateAreaList(areaList, false);
			}
		}
	}

	@Override
	public void onFilterSave() {
		if (isAdded() && isInited) {
			List<AreaInfoModel> areaList = getLocalAreaList();
			if (BeanUtils.isNotEmpty(areaList)) {
				updateAreaList(areaList, true);
			}
		}
	}

	private void updateAreaList(List<AreaInfoModel> list, boolean isPersist) {
		if (BeanUtils.isNotEmpty(list)) {
			for (AreaInfoModel info : list) {
				if (isPersist) {
					info.isSelectedForDB = info.isSelectedForUI;
				} else {
					info.isSelectedForUI = info.isSelectedForDB;
				}
				updateAreaList(info.childList, isPersist);
			}
		}
	}

	private List<AreaInfoModel> getLocalAreaList() {
		if (mSysCfgLogic == null) {
			mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
		}
		return mSysCfgLogic.getLocalAreaList();
	}

	@Override
	public boolean checkArgs() {
		// TODO
		return true;
	}

}
