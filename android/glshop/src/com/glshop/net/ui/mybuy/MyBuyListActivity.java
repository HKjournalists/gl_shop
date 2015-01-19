package com.glshop.net.ui.mybuy;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalConstants.ViewBuyInfoType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.buy.MyBuyListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.findbuy.BuyInfoActivity;
import com.glshop.net.ui.findbuy.PubModeSelectActivity;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.MyBuyFilterType;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 我的供求主页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MyBuyListActivity extends BasicActivity implements OnItemClickListener, IMenuCallback {

	private static final String TAG = "MyBuyListActivity";

	private PullRefreshListView mLvMyBuyList;
	private MyBuyListAdapter mAdapter;
	private View mDropdownMenu;
	private MenuDialog menuBuyFilter;

	private MyBuyFilterType mFilterType = MyBuyFilterType.ALL;

	private boolean hasNextPage = true;

	private IBuyLogic mBuyLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_my_buy_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.my_buy);
		getView(R.id.ll_dropdown_menu).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mDropdownMenu = getView(R.id.ll_dropdown_menu);

		Button btnPublish = getView(R.id.btn_commmon_action);
		btnPublish.setVisibility(View.VISIBLE);
		btnPublish.setText(R.string.publish_buy_message);
		Drawable icon = getResources().getDrawable(R.drawable.ic_buy_pub);
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
		btnPublish.setCompoundDrawables(icon, null, null, null);
		btnPublish.setOnClickListener(this);

		mLvMyBuyList = getView(R.id.lv_my_buy_list);
		mLvMyBuyList.setIsRefreshable(true);
		mLvMyBuyList.hideFootView();

		setOnRefreshListener(mLvMyBuyList);
		setOnScrollListener(mLvMyBuyList);

		mLvMyBuyList.setOnItemClickListener(this);

		mAdapter = new MyBuyListAdapter(this, null);
		mLvMyBuyList.setAdapter(mAdapter);

		mNormalDataView = mLvMyBuyList;
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getMyBuys(mFilterType, getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getMyBuys(mFilterType, getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mBuyLogic.getMyBuys(mFilterType, getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
	}

	@Override
	protected void onScrollMore() {
		Logger.e(TAG, "---onLoadMore---");
		//mLvMyBuyList.showLoading();
		//mBuyLogic.getMyBuys(mFilterType, getCompanyId(), pageIndex, PAGE_SIZE, DataReqType.MORE);

		if (hasNextPage) {
			mLvMyBuyList.showLoading();
			mBuyLogic.getMyBuys(mFilterType, getCompanyId(), pageIndex, PAGE_SIZE, DataReqType.MORE);
		} else {
			mLvMyBuyList.showLoadFinish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.BuyMessageType.MSG_GET_MYBUYS_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_GET_MYBUYS_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST:
			onRefresh();
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo != null) {
			DataReqType type = DataReqType.convert(respInfo.intArg1);
			int size = (Integer) respInfo.data;

			if (type == DataReqType.INIT && size == 0) {
				updateDataStatus(DataStatus.EMPTY);
			} else {
				updateDataStatus(DataStatus.NORMAL);
				if (type == DataReqType.REFRESH) {
					pageIndex = DEFAULT_INDEX;
					if (BeanUtils.isEmpty(DataCenter.getInstance().getData(DataType.MY_BUY_LIST))) {
						updateDataStatus(DataStatus.EMPTY);
					}
				}
				mAdapter.setList(DataCenter.getInstance().getData(DataType.MY_BUY_LIST));
				mLvMyBuyList.onRefreshSuccess();
				if (type == DataReqType.INIT) {
					mLvMyBuyList.setSelection(0);
				}
				//if (type == DataReqType.INIT || type == DataReqType.MORE) {
				if (size > 0) {
					pageIndex++;
				}
				hasNextPage = size >= PAGE_SIZE;
				if (hasNextPage) {
					mLvMyBuyList.showLoading();
				} else {
					mLvMyBuyList.showLoadFinish();
				}
				//}
			}
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		if (respInfo != null) {
			updateDataStatus(DataStatus.ERROR);
			handleErrorAction(respInfo);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dropdown_menu:
			showBuyFilterMenu();
			break;
		case R.id.btn_commmon_action:
			pubBuyInfo();
			break;
		case R.id.iv_common_back:
			if (getParent() instanceof MainActivity) {
				((MainActivity) getParent()).switchTabView(TabStatus.SHOP);
			} else {
				finish();
			}
			break;
		}
	}

	private void showBuyFilterMenu() {
		closeDialog(menuBuyFilter);
		List<MenuItemInfo> menus = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.my_buy_filter_type)));
		mDropdownMenu.setEnabled(false);
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_up);
		menuBuyFilter = new MenuDialog(this, menus, this, true, menus.get(mFilterType.toValue()));
		menuBuyFilter.setMenuType(GlobalMessageType.MenuType.SELECT_MY_BUY_FILTER_TYPE);
		menuBuyFilter.setTitle(getString(R.string.menu_title_select_mybuy_filter));
		menuBuyFilter.show();
	}

	private void pubBuyInfo() {
		//TODO check user info(auth & purse)
		Intent intent = new Intent(this, PubModeSelectActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvMyBuyList.getHeaderViewsCount()));
		MyBuySummaryInfoModel buyInfo = mAdapter.getItem(position - mLvMyBuyList.getHeaderViewsCount());
		Intent intent = new Intent(this, BuyInfoActivity.class);
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_ID, buyInfo.publishBuyId);
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_VIEW_BUY_INFO_TYPE, ViewBuyInfoType.MYBUY.toValue());
		startActivity(intent);
	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_MY_BUY_FILTER_TYPE:
			Logger.e(TAG, "Click Menu index = " + position);
			if (position != mFilterType.toValue()) {
				mFilterType = MyBuyFilterType.convert(position);
				onReloadData();
			}
			restoreFilterIcon();
		}
	}

	@Override
	public void onConfirm(int type, Object obj) {
		restoreFilterIcon();
	}

	@Override
	public void onCancel(int type) {
		restoreFilterIcon();
	}

	private void restoreFilterIcon() {
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_down);
		mDropdownMenu.setEnabled(true);
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
