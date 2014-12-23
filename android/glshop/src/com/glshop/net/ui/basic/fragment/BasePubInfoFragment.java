package com.glshop.net.ui.basic.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布信息BaseFragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public abstract class BasePubInfoFragment extends BasicFragment {

	/** Fragment layout ID */
	private int layoutId = 0;

	/** 买卖信息 */
	protected BuyInfoModel mPubInfo;

	/** 货物信息 */
	protected ProductInfoModel mProductInfo = new ProductInfoModel();

	/** 卸货地址信息 */
	protected AddrInfoModel mSelectAddrInfo = new AddrInfoModel();

	/** 是否处于修改模式 */
	protected boolean isModifyMode = false;

	/** Fragment calllback */
	protected PubFragmentCallback mCallback;

	/** 提交进度对话框 */
	protected CommonProgressDialog mSubmitDialog;

	protected BuyType mBuyType;
	protected ProductType mProductType = ProductType.SAND;
	protected String mProdcutSandSpecId;
	protected String mProdcutStoneSpecId;
	protected String mTradeAreaCode;
	protected DeliveryAddrType mDeliveryAddrType = DeliveryAddrType.ME_DECIDE;

	protected ISysCfgLogic mSysCfgLogic;
	protected IBuyLogic mBuyLogic;

	public BasePubInfoFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(layoutId, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		layoutId = bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID);
		isModifyMode = bundle.getBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, false);
		mPubInfo = (BuyInfoModel) bundle.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO);
		if (mPubInfo == null) {
			mPubInfo = (BuyInfoModel) bundle.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_PREV_BUY_INFO);
		}
		if (mPubInfo == null) {
			mPubInfo = new BuyInfoModel();
			mPubInfo.buyType = mBuyType;
			mPubInfo.companyId = getCompanyId();
		}
	}

	public void updateBuyInfo(BuyInfoModel buyInfo) {
		mPubInfo = buyInfo;
		updateBuyUI();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof PubFragmentCallback) {
			mCallback = (PubFragmentCallback) activity;
		}
	}

	/**
	 * 获取默认发布起始时间
	 */
	protected String getDefaultBeginTime() {
		String dateTime = "";
		if (StringUtils.isNotEmpty(mPubInfo.tradeBeginDate)) {
			dateTime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeBeginDate);
		} else {
			dateTime = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, System.currentTimeMillis());
		}
		return dateTime;
	}

	/**
	 * 获取默认发布结束时间
	 */
	protected String getDefaultEndTime() {
		String dateTime = "";
		if (StringUtils.isNotEmpty(mPubInfo.tradeEndDate)) {
			dateTime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.PUB_DATE_FORMAT, mPubInfo.tradeEndDate);
		} else {
			dateTime = DateUtils.convertDate2String(DateUtils.PUB_DATE_FORMAT, System.currentTimeMillis() + 7 * DateUtils.TIME_UNIT_DAY * DateUtils.TIME_UNIT_SECOND);
		}
		return dateTime;
	}

	/**
	 * 显示选择日期对话框
	 * @param dateWidget
	 */
	protected void showChooseDateDialog(final Button dateWidget) {
		String strDate = dateWidget.getText().toString();
		int year = DateUtils.getYearFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int month = DateUtils.getMonthFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		int day = DateUtils.getDayFromStrDate(DateUtils.PUB_DATE_FORMAT, strDate);
		//Logger.e(TAG, "Y = " + year + ", M = " + month + ", D = " + day);

		DatePickerDialog dateDialog = new DatePickerDialog(mContext, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				//Logger.e(TAG, "Y = " + year + ", M = " + monthOfYear + ", D = " + dayOfMonth);
				dateWidget.setText(DateUtils.getStrDate(DateUtils.PUB_DATE_FORMAT, year, monthOfYear, dayOfMonth));
			}
		}, year, DateUtils.convertCNMonth2EN(month), day);
		dateDialog.show();
	}

	/**
	 * 显示提交进度对话框
	 */
	protected void showSubmitDialog() {
		closeSubmitDialog();
		mSubmitDialog = new CommonProgressDialog(mContext, getString(R.string.submiting_request));
		mSubmitDialog.show();
	}

	/**
	 * 关闭提交进度对话框
	 */
	protected void closeSubmitDialog() {
		if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
			mSubmitDialog.dismiss();
		}
	}

	/**
	 * 获取焦点
	 * @param view
	 */
	protected void requestFocus(View view) {
		((EditText) view).requestFocus();
		((EditText) view).requestFocusFromTouch();
	}

	/**
	 * 关闭菜单对话框
	 * @param menu
	 */
	protected void closeMenuDialog(Dialog menu) {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
	}

	@Override
	protected void initLogics() {
		mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

	/**
	 * 初始化View
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 界面更新
	 */
	protected abstract void updateBuyUI();

	/**
	 * 获取发布信息
	 * @return
	 */
	public abstract BuyInfoModel getBuyInfo();

	/**
	 * 发布Fragment回调接口
	 */
	public interface PubFragmentCallback {

		/**
		 * 切换发布界面(包括发布出售、求购信息)
		 * @param type
		 */
		public void switchPubType(BuyType type, boolean init);

	}

}
