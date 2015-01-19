package com.glshop.net.ui.basic.fragment.buy.pub;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PubBuyIndicatorType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.menu.UnitDropMenuAdapter;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.basic.view.dialog.menu.PopupMenu;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.ui.myprofile.AreaAddrSelectActivity;
import com.glshop.net.ui.myprofile.DischargeAddrSelectActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.DateUtils;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布买卖交易信息Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public class PubTradeInfoFragmentV2 extends BasePubInfoFragmentV2 implements IMenuCallback {

	protected static final String TAG = "PubTradeInfoFragmentV2";

	//　交易信息
	private BuyTextItemView mItemTradeArea;
	private BuyTextItemView mItemDischargeAddrType;
	private BuyTextItemView mItemDischargeAddrSelect;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	private MenuDialog menuDischargeAddrType;

	private View llItemUnitSwitch;
	private TextView mTvUnitType;
	private Button mBtnStartDate;
	private Button mBtnEndDate;
	private View mIvUnitMenuIcon;

	private EditText mEtItemAmount;
	private EditText mEtItemUnitPrice;
	private EditText mEtItemBuyRemarks;

	// 卸货地址
	private View llItemAddrDetail;
	private TextView mTvAddrDetail;
	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;

	private ProductUnitType mUnitType = ProductUnitType.TON;

	private String mTradeBeginDate;
	private String mTradeEndDate;

	public PubTradeInfoFragmentV2() {

	}

	@Override
	protected void initView() {
		mItemTradeArea = getView(R.id.ll_trade_area_item);
		mItemDischargeAddrType = getView(R.id.ll_item_discharge_address_type);
		mItemDischargeAddrSelect = getView(R.id.ll_item_select_discharge_addr);
		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);
		mBtnStartDate = getView(R.id.btn_trade_start_date);
		mBtnEndDate = getView(R.id.btn_trade_end_date);
		llItemUnitSwitch = getView(R.id.ll_item_unit_switch);
		mTvUnitType = getView(R.id.tv_nuit_type);
		mEtItemAmount = getView(R.id.et_item_amount);
		mEtItemUnitPrice = ((BuyEditItemView) getView(R.id.ll_item_unit_price)).getEditText();
		mEtItemBuyRemarks = getView(R.id.et_item_product_remark);
		llItemAddrDetail = getView(R.id.ll_item_addr);
		mTvAddrDetail = getView(R.id.tv_addr_detail);
		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);
		mIvUnitMenuIcon = getView(R.id.iv_unit_dropdown);

		mItemTradeArea.setOnClickListener(this);
		mItemDischargeAddrType.setOnClickListener(this);
		mItemDischargeAddrSelect.setOnClickListener(this);
		llItemUnitSwitch.setOnClickListener(this);
		mBtnStartDate.setOnClickListener(this);
		mBtnEndDate.setOnClickListener(this);

		getView(R.id.iv_item_addr_pic_1).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_2).setOnClickListener(this);
		getView(R.id.iv_item_addr_pic_3).setOnClickListener(this);
		getView(R.id.btn_pub_next).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		updateBuyUI();
	}

	@Override
	protected void updateBuyUI() {
		// 更新价格及时间信息
		mUnitType = mPubInfo.unitType;
		updateUnitTypeUI();
		mEtItemAmount.setText(mPubInfo.tradeAmount != 0 ? String.valueOf(mPubInfo.tradeAmount) : "");
		mEtItemUnitPrice.setText(mPubInfo.unitPrice != 0 ? String.valueOf(mPubInfo.unitPrice) : "");

		// 更新交易日期范围
		mTradeBeginDate = mPubInfo.tradeBeginDate;
		mTradeEndDate = mPubInfo.tradeEndDate;
		updateTradeDatetime();

		// 更新交易地域
		mTradeAreaCode = mPubInfo.tradeAreaCode;
		mTradeAreaName = mPubInfo.tradeAreaName;
		updateTradeAreaInfo();

		// 更新交易地址指定方式
		mDeliveryAddrType = mPubInfo.deliveryAddrType;
		updateDischargeAddrTypeUI();

		// 更新卸货地址详情
		mSelectAddrInfo = mPubInfo.addrInfo;
		updateAddrInfo();

		// 更新买卖备注
		mEtItemBuyRemarks.setText(mPubInfo.buyRemarks);

		// 文本定位
		requestSelection(mEtItemAmount);
		requestSelection(mEtItemUnitPrice);
		requestSelection(mEtItemBuyRemarks);
	}

	/**
	 * 更新发布时间
	 */
	private void updateTradeDatetime() {
		if (StringUtils.isNotEmpty(mTradeBeginDate)) {
			String dateTime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeBeginDate);
			mBtnStartDate.setText(dateTime);
			//mBtnStartDate.setTextColor(mContext.getResources().getColor(R.color.black));
		} else {
			mBtnStartDate.setText(mContext.getString(R.string.data_select));
			//mBtnStartDate.setTextColor(mContext.getResources().getColor(R.color.gray));
		}
		if (StringUtils.isNotEmpty(mTradeEndDate)) {
			String dateTime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeEndDate);
			mBtnEndDate.setText(dateTime);
			//mBtnStartDate.setTextColor(mContext.getResources().getColor(R.color.black));
		} else {
			mBtnEndDate.setText(mContext.getString(R.string.data_select));
			//mBtnStartDate.setTextColor(mContext.getResources().getColor(R.color.gray));
		}
	}

	/**
	 * 更新交易地域信息
	 */
	private void updateTradeAreaInfo() {
		if (StringUtils.isNotEmpty(mTradeAreaCode)) {
			if (StringUtils.isNEmpty(mTradeAreaName)) {
				mTradeAreaName = getTradeAreaName();
			}
			mItemTradeArea.setContentText(mTradeAreaName);
		} else {
			mItemTradeArea.setContentText(mContext.getString(R.string.data_select));
		}
	}

	private String getTradeAreaName() {
		StringBuffer areaName = new StringBuffer();
		if (StringUtils.isNotEmpty(mTradeAreaCode)) {
			List<AreaInfoModel> areaInfoList = mSysCfgLogic.getParentAreaInfo(mTradeAreaCode);
			if (BeanUtils.isNotEmpty(areaInfoList)) {
				for (int i = 0; i < areaInfoList.size(); i++) {
					areaName.append(/*(i == 0 ? "" : ".") + */areaInfoList.get(i).name);
				}
			}
		}
		return areaName.toString();
	}

	/**
	 * 更新交易地址方式
	 */
	private void updateDischargeAddrTypeUI() {
		if (mDeliveryAddrType == null) {
			mSelectAddrInfo = null;
			mItemDischargeAddrType.setContentText(mContext.getString(R.string.data_select));
			mItemDischargeAddrType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_item_middle));
			mItemDischargeAddrSelect.setVisibility(View.VISIBLE);
			getView(R.id.item_devider_discharge_addr).setVisibility(View.VISIBLE);
			llItemAddrDetail.setVisibility(View.GONE);
		} else if (mDeliveryAddrType == DeliveryAddrType.ME_DECIDE) {
			mItemDischargeAddrType.setContentText(mContext.getString(R.string.discharge_addr_type_me_decide));
			mItemDischargeAddrType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_item_middle));
			mItemDischargeAddrSelect.setVisibility(View.VISIBLE);
			getView(R.id.item_devider_discharge_addr).setVisibility(View.VISIBLE);
			if (mSelectAddrInfo != null && StringUtils.isNotEmpty(mSelectAddrInfo.addrId)) {
				llItemAddrDetail.setVisibility(View.VISIBLE);
			} else {
				llItemAddrDetail.setVisibility(View.GONE);
			}
		} else if (mDeliveryAddrType == DeliveryAddrType.ANOTHER_DECIDE) {
			mSelectAddrInfo = null;
			mItemDischargeAddrType.setContentText(mContext.getString(R.string.discharge_addr_type_another_decide));
			mItemDischargeAddrType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_item_bottom));
			mItemDischargeAddrSelect.setVisibility(View.GONE);
			mItemDischargeAddrSelect.setContentText(mContext.getString(R.string.data_select));
			getView(R.id.item_devider_discharge_addr).setVisibility(View.GONE);
			llItemAddrDetail.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		ActivityUtil.hideKeyboard(getActivity());
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_trade_area_item:
			clearFocus();
			intent = new Intent(mContext, AreaAddrSelectActivity.class);
			intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_CODE, mTradeAreaCode);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_SELECT_TRADE_ADDRESS);
			break;
		case R.id.ll_item_discharge_address_type:
			showDischargeAddrTypeMenu();
			break;
		case R.id.ll_item_select_discharge_addr:
			clearFocus();
			intent = new Intent(mContext, DischargeAddrSelectActivity.class);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_SELECT_DISCHARGE_ADDRESS);
			break;
		case R.id.ll_item_unit_switch:
			showUnitMenu();
			break;
		case R.id.btn_trade_start_date:
			showChooseDateDialog((Button) v);
			break;
		case R.id.btn_trade_end_date:
			showChooseDateDialog((Button) v);
			break;
		case R.id.btn_pub_next:
			if (checkArgs()) {
				clearFocus();
				if (mCallback != null) {
					mCallback.switchPubIndicator(PubBuyIndicatorType.PREVIEW, true);
				}
			}
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
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isNEmpty(mEtItemAmount.getText().toString().trim())) {
			showToast("购买量不能为空!");
			return false;
		} else if (!StringUtils.checkNumber(mEtItemAmount.getText().toString().trim())) {
			showToast("购买量不能为0!");
			return false;
		} else if (StringUtils.isNEmpty(mEtItemUnitPrice.getText().toString().trim())) {
			showToast("单价不能为空!");
			return false;
		} else if (!StringUtils.checkNumber(mEtItemUnitPrice.getText().toString().trim())) {
			showToast("单价不能为0!");
			return false;
		} else if (!checkTradeDate()) {
			return false;
		} else if (StringUtils.isNEmpty(mTradeAreaCode)) {
			showToast("请选择交易地域!");
			return false;
		} else if (mDeliveryAddrType == null) {
			showToast("请选择交货地址指定方式!");
			return false;
		} else if (mDeliveryAddrType == DeliveryAddrType.ME_DECIDE && !isAddrValide(mSelectAddrInfo)) {
			showToast("请选择详细交货地址!");
			return false;
		}
		return true;
	}

	private boolean checkTradeDate() {
		if (StringUtils.isNEmpty(mTradeBeginDate)) {
			showToast("请选择交易开始时间!");
			return false;
		} else if (StringUtils.isNEmpty(mTradeEndDate)) {
			showToast("请选择交易结束时间!");
			return false;
		} else {
			long startTime = DateUtils.covertDate2Long(DateUtils.COMMON_DATE_FORMAT, mTradeBeginDate);
			long endTime = DateUtils.covertDate2Long(DateUtils.COMMON_DATE_FORMAT, mTradeEndDate);
			if (startTime > endTime) {
				showToast("交易开始时间不能大于结束时间!");
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public BuyInfoModel getBuyInfo(boolean isUpdate) {
		if (!isUpdate) {
			if (isAdded()) {
				return mPubInfo;
			} else {
				BuyInfoModel pubInfo = (BuyInfoModel) this.getArguments().getSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO);
				if (pubInfo == null) {
					pubInfo = new BuyInfoModel();
					pubInfo.companyId = getCompanyId();
				}
				return pubInfo;
			}
		}
		if (mPubInfo != null) {
			// 数量及价格信息
			if (StringUtils.isNotEmpty(mEtItemUnitPrice.getText().toString().trim())) {
				mPubInfo.unitPrice = Float.parseFloat(mEtItemUnitPrice.getText().toString().trim());
			}
			if (StringUtils.isNotEmpty(mEtItemAmount.getText().toString().trim())) {
				mPubInfo.tradeAmount = Float.parseFloat(mEtItemAmount.getText().toString().trim());
			}
			// 单位
			mPubInfo.unitType = mUnitType;

			// 发布时间信息
			mPubInfo.tradeBeginDate = /*DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, mBtnStartDate.getText().toString())*/mTradeBeginDate;
			mPubInfo.tradeEndDate = /*DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, mBtnEndDate.getText().toString())*/mTradeEndDate;

			// 地域信息
			mPubInfo.isMoreArea = false;
			mPubInfo.tradeAreaCode = mTradeAreaCode;
			mPubInfo.tradeAreaName = mTradeAreaName;
			mPubInfo.deliveryAddrType = mDeliveryAddrType;
			mPubInfo.addrInfo = mSelectAddrInfo;

			// 买卖备注
			mPubInfo.buyRemarks = mEtItemBuyRemarks.getText().toString().trim();
		}
		return mPubInfo;
	}

	private void showUnitMenu() {
		List<String> menus = Arrays.asList(getResources().getStringArray(R.array.unit_type));
		llItemUnitSwitch.setEnabled(false);

		Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate180);
		rotateAnimation.setFillAfter(true);
		final Animation reverseAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_reverse180);
		rotateAnimation.setFillAfter(true);
		mIvUnitMenuIcon.startAnimation(rotateAnimation);

		final int index = getCurUnitTypeIndex();
		UnitDropMenuAdapter adapter = new UnitDropMenuAdapter(mContext, menus, menus.get(index));
		PopupMenu menu = new PopupMenu(mContext, menus, llItemUnitSwitch.getWidth(), adapter, new PopupMenu.IMenuCallback() {

			@Override
			public void onMenuItemClick(int position) {
				//Logger.e(TAG, "Click Menu position = " + position);
				if (position == 0) {
					mUnitType = ProductUnitType.TON;
				} else if (position == 1) {
					mUnitType = ProductUnitType.CUTE;
				}
				updateUnitTypeUI();
			}

			@Override
			public void onDismiss() {
				//Logger.d(TAG, "onDismiss");
				mIvUnitMenuIcon.startAnimation(reverseAnimation);
				llItemUnitSwitch.setEnabled(true);
			}

		});
		menu.showAsDropDown(llItemUnitSwitch);
	}

	/**
	 * 更新单位类型
	 */
	private void updateUnitTypeUI() {
		if (mUnitType == ProductUnitType.CUTE) {
			mTvUnitType.setText(getString(R.string.unit_cube_v2));
		} else {
			mTvUnitType.setText(getString(R.string.unit_ton_v2));
		}
	}

	private int getCurUnitTypeIndex() {
		if (mUnitType == ProductUnitType.TON) {
			return 0;
		} else if (mUnitType == ProductUnitType.CUTE) {
			return 1;
		} else {
			return 0;
		}
	}

	private void showDischargeAddrTypeMenu() {
		closeDialog(menuDischargeAddrType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.discharge_address_type)));
		menuDischargeAddrType = new MenuDialog(mContext, menu, this, true, new MenuItemInfo(mItemDischargeAddrType.getContentText()));
		menuDischargeAddrType.setMenuType(GlobalMessageType.MenuType.SELECT_DISCHARGE_ADDR_TYPE);
		menuDischargeAddrType.setTitle(getString(R.string.business_discharge_address_type));
		menuDischargeAddrType.show();
	}

	/**
	 * 显示选择日期对话框
	 * @param dateWidget
	 */
	private void showChooseDateDialog(final Button dateWidget) {
		String strDate = "";
		if (dateWidget == mBtnStartDate) {
			if (StringUtils.isNotEmpty(mTradeBeginDate)) {
				strDate = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeBeginDate);
			}
		} else if (dateWidget == mBtnEndDate) {
			if (StringUtils.isNotEmpty(mTradeEndDate)) {
				strDate = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mTradeEndDate);
			}
		}
		if (StringUtils.isNEmpty(strDate)) {
			strDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, System.currentTimeMillis());
		}

		int year = DateUtils.getYearFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int month = DateUtils.getMonthFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int day = DateUtils.getDayFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		//Logger.e(TAG, "Y = " + year + ", M = " + month + ", D = " + day);

		DatePickerDialog dateDialog = new DatePickerDialog(mContext, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				//Logger.e(TAG, "Y = " + year + ", M = " + monthOfYear + ", D = " + dayOfMonth);
				String datetime = DateUtils.getStrDate(DateUtils.PUB_DATE_FORMAT, year, monthOfYear, dayOfMonth);
				dateWidget.setText(datetime);
				//dateWidget.setTextColor(mContext.getResources().getColor(R.color.black));
				if (dateWidget == mBtnStartDate) {
					mTradeBeginDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, datetime);
					//mTradeBeginDate = datetime;
				} else if (dateWidget == mBtnEndDate) {
					mTradeEndDate = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT, datetime);
					//mTradeEndDate = datetime;
				}
			}
		}, year, DateUtils.convertCNMonth2EN(month), day);
		dateDialog.show();
	}

	private void clearFocus() {
		mEtItemAmount.clearFocus();
		mEtItemUnitPrice.clearFocus();
		mEtItemBuyRemarks.clearFocus();
	}

	/**
	 * 更新卸货地址信息
	 */
	private void updateAddrInfo() {
		if (mDeliveryAddrType != DeliveryAddrType.ANOTHER_DECIDE) {
			if (isAddrValide(mSelectAddrInfo)) {
				mItemDischargeAddrSelect.setContentText(getString(R.string.data_selected));
				llItemAddrDetail.setVisibility(View.VISIBLE);
				// 显示完整地址信息
				if (StringUtils.isNEmpty(mSelectAddrInfo.areaName) && StringUtils.isNEmpty(mSelectAddrInfo.deliveryAddrDetail)) {
					mTvAddrDetail.setText(getString(R.string.data_empty));
				} else {
					mTvAddrDetail.setText(mSelectAddrInfo.areaName + mSelectAddrInfo.deliveryAddrDetail);
				}
				mItemPortWaterDepth.setContentText(mSelectAddrInfo.uploadPortWaterDepth != 0 ? String.valueOf(mSelectAddrInfo.uploadPortWaterDepth) : getString(R.string.data_no_input));
				mItemPortShipTon.setContentText(mSelectAddrInfo.shippingTon != 0 ? String.valueOf(mSelectAddrInfo.shippingTon) : getString(R.string.data_no_input));

				// 显示地址图片信息
				List<ImageInfoModel> imgUrl = mSelectAddrInfo.addrImageList;
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
			} else {
				llItemAddrDetail.setVisibility(View.GONE);
			}
		} else {
			llItemAddrDetail.setVisibility(View.GONE);
		}
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
						mItemTradeArea.setContentText(mTradeAreaName);
					}
				}
			}
			break;
		case GlobalMessageType.ActivityReqCode.REQ_SELECT_DISCHARGE_ADDRESS:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mSelectAddrInfo = (AddrInfoModel) data.getSerializableExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_INFO);
					updateAddrInfo();
				}
			}
			break;
		}
	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_DISCHARGE_ADDR_TYPE:
			mDeliveryAddrType = DeliveryAddrType.convert(position + 1);
			updateDischargeAddrTypeUI();
			break;
		}
	}

	@Override
	public void onConfirm(int type, Object obj) {

	}

	@Override
	public void onCancel(int type) {

	}

}
