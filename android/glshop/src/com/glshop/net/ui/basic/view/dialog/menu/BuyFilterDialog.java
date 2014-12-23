package com.glshop.net.ui.basic.view.dialog.menu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.BuyFilterType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.adapter.menu.MenuAdapterV2;
import com.glshop.net.ui.basic.adapter.menu.MenuAdapterV3;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 找买找卖过滤菜单对话框
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class BuyFilterDialog extends BaseMenuDialog<Object, Object> {

	private Button mBtnMenuArea;
	private Button mBtnMenuDate;
	private Button mBtnMenuProduct;

	private Button mBtnTab1;
	private Button mBtnTab2;

	private View llSubTabMenu;

	/** 一级菜单 */
	private ListView mLvFirst;
	/** 二级菜单 */
	private ListView mLvSecond;

	private BuyFilterType mFilterType = BuyFilterType.TRADE_PRODUCT;

	private MenuAdapterV3 mProductSandSpecAdapter;
	private MenuAdapterV3 mProductStoneSpecAdapter;
	private MenuAdapterV2 mMonthAdapter;
	private MenuAdapterV3 mDayAdapter;
	private MenuAdapterV3 mAreaAdapter;

	private List<MenuItemInfo> mAreaList = new ArrayList<MenuItemInfo>();
	private List<MenuItemInfo> mSandSpecList = new ArrayList<MenuItemInfo>();
	private List<MenuItemInfo> mStoneSpecList = new ArrayList<MenuItemInfo>();
	private List<MenuItemInfo> mMonthList = new ArrayList<MenuItemInfo>();
	private List<MenuItemInfo> mDayList = new ArrayList<MenuItemInfo>();

	private ProductType productType = ProductType.SAND;

	/** 过滤信息 */
	private BuyFilterInfoModel mFilterInfo;
	/** 地域编号 */
	public String areaCode = "";
	/** 交易时间：年份*/
	public String year = "";
	/** 交易时间：月份 */
	public String month = "";
	/** 交易时间：天数 */
	public String day = "";
	/** 货物类型 */
	public String pType = "";
	/** 货物编号*/
	public String pID = "";

	public BuyFilterDialog(Context context, IMenuCallback callback, List<AreaInfoModel> areaList, List<ProductCfgInfoModel> productList, BuyFilterInfoModel filterInfo) {
		super(context, null, callback);
		mFilterInfo = filterInfo;
		initBuyFilterInfo();
		initAreaMenuList(areaList);
		initProductMenuList(productList);
		initDateMenuList();

		mAreaAdapter = new MenuAdapterV3(context, mAreaList, true, null);
		mMonthAdapter = new MenuAdapterV2(context, mMonthList, true, null);
		mDayAdapter = new MenuAdapterV3(context, mDayList, true, null);
		mProductSandSpecAdapter = new MenuAdapterV3(context, mSandSpecList, true, null);
		mProductStoneSpecAdapter = new MenuAdapterV3(context, mStoneSpecList, true, null);

		updateLeftTabStatus();
		updateTopSubTabStatus(0);
		updateContent();
		updateUI();
	}

	@Override
	protected void initView(Context context) {
		mView = View.inflate(context, R.layout.dialog_buy_filter_menu, null);
		mTvTitle = getView(R.id.dialog_tv_title);
		mBtnConfirm = getView(R.id.dialog_btn_confirm);
		mBtnCancel = getView(R.id.dialog_btn_cancel);

		mBtnMenuArea = getView(R.id.btn_filter_menu_area);
		mBtnMenuDate = getView(R.id.btn_filter_menu_date);
		mBtnMenuProduct = getView(R.id.btn_filter_menu_product);
		mBtnTab1 = getView(R.id.btn_filter_tab_1);
		mBtnTab2 = getView(R.id.btn_filter_tab_2);

		llSubTabMenu = getView(R.id.ll_sub_tab);

		mLvFirst = getView(R.id.lv_menu_list_first);
		mLvSecond = getView(R.id.lv_menu_list_second);

		mBtnMenuArea.setOnClickListener(this);
		mBtnMenuDate.setOnClickListener(this);
		mBtnMenuProduct.setOnClickListener(this);
		mBtnTab1.setOnClickListener(this);
		mBtnTab2.setOnClickListener(this);

		mLvFirst.setOnItemClickListener(this);
		mLvSecond.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		//LinearLayout.LayoutParams lp = (LayoutParams) mLvFirst.getLayoutParams();
		//mLvFirst.setLayoutParams(lp);
		//lp.weight = 2;
		switch (v.getId()) {
		case R.id.btn_filter_menu_area:
			mFilterType = BuyFilterType.TRADE_AREA;
			updateLeftTabStatus();
			updateContent();
			break;
		case R.id.btn_filter_menu_date:
			mFilterType = BuyFilterType.TRADE_DATE;
			updateLeftTabStatus();
			updateContent();
			break;
		case R.id.btn_filter_menu_product:
			mFilterType = BuyFilterType.TRADE_PRODUCT;
			updateLeftTabStatus();
			updateContent();
			break;
		case R.id.btn_filter_tab_1:
			updateTopSubTabStatus(0);
			break;
		case R.id.btn_filter_tab_2:
			updateTopSubTabStatus(1);
			break;
		}
	}

	private void updateLeftTabStatus() {
		int selectedTabStatus = R.drawable.bg_item_middle_press;
		int unSelectedTabStatus = R.drawable.bg_item_middle;
		mBtnMenuArea.setBackgroundResource(mFilterType == BuyFilterType.TRADE_AREA ? selectedTabStatus : unSelectedTabStatus);
		mBtnMenuDate.setBackgroundResource(mFilterType == BuyFilterType.TRADE_DATE ? selectedTabStatus : unSelectedTabStatus);
		mBtnMenuProduct.setBackgroundResource(mFilterType == BuyFilterType.TRADE_PRODUCT ? selectedTabStatus : unSelectedTabStatus);

		int selectedColor = this.getContext().getResources().getColor(R.color.orange);
		int unSelectedColor = this.getContext().getResources().getColor(R.color.gray);
		mBtnMenuArea.setTextColor(mFilterType == BuyFilterType.TRADE_AREA ? selectedColor : unSelectedColor);
		mBtnMenuDate.setTextColor(mFilterType == BuyFilterType.TRADE_DATE ? selectedColor : unSelectedColor);
		mBtnMenuProduct.setTextColor(mFilterType == BuyFilterType.TRADE_PRODUCT ? selectedColor : unSelectedColor);

		llSubTabMenu.setVisibility(mFilterType == BuyFilterType.TRADE_PRODUCT ? View.VISIBLE : View.GONE);
		getView(R.id.item_devider_line).setVisibility(mFilterType == BuyFilterType.TRADE_PRODUCT ? View.VISIBLE : View.GONE);
	}

	private void updateTopSubTabStatus(int tabIndex) {
		int selectedTabStatus = R.drawable.bg_list_item_press;
		int unSelectedTabStatus = getContext().getResources().getColor(R.color.white);

		if (tabIndex == 0) {
			mBtnTab1.setBackgroundResource(selectedTabStatus);
			mBtnTab2.setBackgroundColor(unSelectedTabStatus);
			productType = ProductType.SAND;
		} else {
			mBtnTab1.setBackgroundColor(unSelectedTabStatus);
			mBtnTab2.setBackgroundResource(selectedTabStatus);
			productType = ProductType.STONE;
		}

		int selectedColor = this.getContext().getResources().getColor(R.color.orange);
		int unSelectedColor = this.getContext().getResources().getColor(R.color.gray);
		mBtnTab1.setTextColor(tabIndex == 0 ? selectedColor : unSelectedColor);
		mBtnTab2.setTextColor(tabIndex == 1 ? selectedColor : unSelectedColor);

		updateContent();
	}

	private void updateContent() {
		switch (mFilterType) {
		case TRADE_AREA:
			mLvFirst.setAdapter(mAreaAdapter);
			mLvFirst.setVisibility(View.VISIBLE);
			mLvSecond.setVisibility(View.GONE);
			break;
		case TRADE_DATE:
			mLvFirst.setAdapter(mMonthAdapter);
			mLvSecond.setAdapter(mDayAdapter);
			mLvFirst.setVisibility(View.VISIBLE);
			mLvSecond.setVisibility(View.VISIBLE);
			break;
		case TRADE_PRODUCT:
			if (productType == ProductType.SAND) {
				mLvFirst.setAdapter(mProductSandSpecAdapter);
			} else {
				mLvFirst.setAdapter(mProductStoneSpecAdapter);
			}
			mLvFirst.setVisibility(View.VISIBLE);
			mLvSecond.setVisibility(View.GONE);
			break;
		}
	}

	private void initBuyFilterInfo() {
		areaCode = mFilterInfo.areaCode;
		year = mFilterInfo.year;
		month = mFilterInfo.month;
		day = mFilterInfo.day;
		pType = mFilterInfo.productType;
		pID = mFilterInfo.productID;
	}

	private BuyFilterInfoModel getBuyFilterInfo() {
		BuyFilterInfoModel info = new BuyFilterInfoModel();
		info.orderType = mFilterInfo.orderType;
		info.orderStatus = mFilterInfo.orderStatus;
		info.areaCode = areaCode;
		info.year = year;
		info.month = month;
		info.day = day;
		info.productType = pType;
		info.productID = pID;
		return info;
	}

	private void updateUI() {
		// 初始化默认地域选项
		if (StringUtils.isNotEmpty(areaCode)) {
			for (MenuItemInfo info : mAreaList) {
				if (areaCode.equals(info.menuCode)) {
					mAreaAdapter.setSelectedMenu(info);
					break;
				}
			}
		}

		// 初始化默认货物信息
		if (StringUtils.isNotEmpty(pType)) {
			updateTopSubTabStatus(0);
			for (MenuItemInfo info : mSandSpecList) {
				if (pType.equals(info.menuCode)) {
					mProductSandSpecAdapter.setSelectedMenu(info);
					break;
				}
			}
		} else if (StringUtils.isNotEmpty(pID)) {
			updateTopSubTabStatus(1);
			for (MenuItemInfo info : mStoneSpecList) {
				if (pID.equals(info.menuID)) {
					mProductStoneSpecAdapter.setSelectedMenu(info);
					break;
				}
			}
		} else {
			updateTopSubTabStatus(0);
		}

		// 初始化默认日期
		if (StringUtils.isNotEmpty(month)) {
			for (MenuItemInfo info : mMonthList) {
				if (month.equals(info.menuCode)) {
					mMonthAdapter.setSelectedMenu(info);
					break;
				}
			}
			if (StringUtils.isNotEmpty(day)) {
				for (MenuItemInfo info : mDayList) {
					if (day.equals(info.menuCode)) {
						mDayAdapter.setSelectedMenu(info);
						break;
					}
				}
			}
		}

	}

	private void initAreaMenuList(List<AreaInfoModel> areaList) {
		if (BeanUtils.isNotEmpty(areaList)) {
			for (int i = 0; i < areaList.size(); i++) {
				AreaInfoModel info = areaList.get(i);
				mAreaList.add(new MenuItemInfo(info.name, info.code));
			}
		}
	}

	private void initProductMenuList(List<ProductCfgInfoModel> productList) {
		if (BeanUtils.isNotEmpty(productList)) {
			mSandSpecList.clear();
			mStoneSpecList.clear();
			mSandSpecList.addAll(SysCfgUtils.getSandTopCategoryMenu(productList));
			mStoneSpecList.addAll(SysCfgUtils.getStoneCategoryMenu(productList));
		}
	}

	private void initDateMenuList() {
		/*Calendar calendar = Calendar.getInstance();
		year = String.valueOf(calendar.get(Calendar.YEAR));
		month = String.valueOf(DateUtils.convertENMonth2CN(calendar.get(Calendar.MONTH)));
		day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));*/
		//Logger.e("", "Y = " + year + ", M = " + month + ", D = " + day);

		for (int i = 0; i < 12; i++) {
			String mouth = String.valueOf(i + 1);
			mMonthList.add(new MenuItemInfo(mouth + "月", mouth));
		}

		updateDayData();
	}

	private void updateDayData() {
		if (StringUtils.isNotEmpty(year)) {
			String curMonth = "0";
			if (StringUtils.isNotEmpty(month)) {
				curMonth = month;
			}
			final int dayCount = getDayCount(Integer.parseInt(year), Integer.parseInt(curMonth));
			mDayList.clear();
			for (int i = 0; i < dayCount; i++) {
				String day = String.valueOf(i + 1);
				mDayList.add(new MenuItemInfo(day + "日", day));
			}
		}
	}

	private int getDayCount(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		calendar.roll(Calendar.DATE, false);
		return calendar.get(Calendar.DATE);
	}

	@Override
	protected boolean checkResult() {
		return true;
	}

	@Override
	protected Object getResult() {
		return getBuyFilterInfo();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mFilterType == BuyFilterType.TRADE_AREA) {
			if (mAreaList.get(position) == mAreaAdapter.getSelectedMenu()) {
				areaCode = "";
				mAreaAdapter.setSelectedMenu(null);
			} else {
				areaCode = mAreaList.get(position).menuCode;
				mAreaAdapter.setSelectedMenu(mAreaList.get(position));
			}
		} else if (mFilterType == BuyFilterType.TRADE_DATE) {
			if (parent == mLvFirst) {
				if (mMonthList.get(position) == mMonthAdapter.getSelectedMenu()) {
					month = "";
					day = "";
					mMonthAdapter.setSelectedMenu(null);
					mDayAdapter.setSelectedMenu(null);
				} else {
					month = mMonthList.get(position).menuCode;
					updateDayData();
					mMonthAdapter.setSelectedMenu(mMonthList.get(position));

					mDayAdapter.setList(mDayList);
					mDayAdapter.setSelectedMenu(null);
					mLvSecond.setAdapter(mDayAdapter);
					mLvSecond.setSelection(0);
				}
			} else {
				if (mDayList.get(position) == mDayAdapter.getSelectedMenu()) {
					day = "";
					mDayAdapter.setSelectedMenu(null);
				} else if (mMonthAdapter.getSelectedMenu() != null) {
					day = mDayList.get(position).menuCode;
					mDayAdapter.setSelectedMenu(mDayList.get(position));
				}
			}
		} else if (mFilterType == BuyFilterType.TRADE_PRODUCT) {
			if (productType == ProductType.SAND) {
				pID = "";
				mProductStoneSpecAdapter.setSelectedMenu(null);
				if (mSandSpecList.get(position) == mProductSandSpecAdapter.getSelectedMenu()) {
					mProductSandSpecAdapter.setSelectedMenu(null);
					pType = "";
				} else {
					mProductSandSpecAdapter.setSelectedMenu(mSandSpecList.get(position));
					pType = mSandSpecList.get(position).menuCode;
				}
			} else {
				pType = "";
				mProductSandSpecAdapter.setSelectedMenu(null);
				if (mStoneSpecList.get(position) == mProductStoneSpecAdapter.getSelectedMenu()) {
					mProductStoneSpecAdapter.setSelectedMenu(null);
					pID = "";
				} else {
					mProductStoneSpecAdapter.setSelectedMenu(mStoneSpecList.get(position));
					pID = mStoneSpecList.get(position).menuID;
				}
			}
		}
	}

}
