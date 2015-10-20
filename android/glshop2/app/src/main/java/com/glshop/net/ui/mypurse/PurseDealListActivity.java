package com.glshop.net.ui.mypurse;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.DealListAdapter;
import com.glshop.net.ui.basic.adapter.menu.DropMenuAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.dialog.menu.PopupMenu;
import com.glshop.net.ui.basic.view.dialog.menu.PopupMenu.IMenuCallback;
import com.glshop.platform.api.DataConstants.PurseDealFilterType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 保证金和货款收支列表界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseDealListActivity extends BasicActivity implements OnItemClickListener {

	private PullRefreshListView mLvDealList;
	private DealListAdapter mAdapter;

	private View mDropdownMenu;

	private PurseType purseType;

	private PurseDealFilterType mFilterType = PurseDealFilterType.ALL;

	private boolean hasNextPage = true;

	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_deal_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.ll_dropdown_menu).setOnClickListener(this);
		mDropdownMenu = getView(R.id.ll_dropdown_menu);

		mLvDealList = getView(R.id.lv_deal_list);
		mLvDealList.setIsRefreshable(true);
		mLvDealList.hideFootView();

		setOnRefreshListener(mLvDealList);
		setOnScrollListener(mLvDealList);

		mLvDealList.setOnItemClickListener(this);

		mAdapter = new DealListAdapter(this, null);
		mLvDealList.setAdapter(mAdapter);

		mNormalDataView = mLvDealList;
	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.DEPOSIT.toValue()));
		if (purseType == PurseType.DEPOSIT) {
			((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.deposit_deal_list_title);
		} else {
			((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.payment_deal_list_title);
		}

		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getDealList(purseType, mFilterType, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getDealList(purseType, mFilterType, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mPurseLogic.getDealList(purseType, mFilterType, DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
	}

	@Override
	protected void onScrollMore() {
		Logger.e(TAG, "---onLoadMore---");
		if (hasNextPage) {
			mLvDealList.showLoading();
			mPurseLogic.getDealList(purseType, mFilterType, pageIndex, PAGE_SIZE, DataReqType.MORE);
		} else {
			mLvDealList.showLoadFinish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_GET_DEALS_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_GET_DEALS_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo != null) {
			DataReqType type = DataReqType.convert(respInfo.intArg1);
			int size = (Integer) respInfo.data;
			Logger.e(TAG, "PageIndex = " + pageIndex + ", ListSize = " + size);
			if (type == DataReqType.INIT && size == 0) {
				updateDataStatus(DataStatus.EMPTY);
			} else if (type == DataReqType.REFRESH && size == 0 && BeanUtils.isEmpty(DataCenter.getInstance().getData(DataType.PURSE_DEAL_LIST))) {
				updateDataStatus(DataStatus.EMPTY);
			} else {
				updateDataStatus(DataStatus.NORMAL);
				mAdapter.setList(DataCenter.getInstance().getData(DataType.PURSE_DEAL_LIST));
				mLvDealList.onRefreshSuccess();
				if (type == DataReqType.INIT) {
					mLvDealList.setSelection(0);
				}
				if (type == DataReqType.INIT || type == DataReqType.MORE) {
					if (size > 0) {
						pageIndex++;
					}
					hasNextPage = size >= PAGE_SIZE;
					if (hasNextPage) {
						mLvDealList.showLoading();
					} else {
						mLvDealList.showLoadFinish();
					}
				}
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
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.PurseMessageType.MSG_GET_DEALS_FAILED:
				showToast(R.string.error_req_get_list);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvDealList.getHeaderViewsCount()));
		DealSummaryInfoModel info = mAdapter.getItem(position - mLvDealList.getHeaderViewsCount());
		Intent intent = new Intent(this, PurseDealInfoActivity.class);
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, purseType.toValue());
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_ID, info.id);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dropdown_menu:
			showDropdownMenu();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void showDropdownMenu() {
		List<String> menus = Arrays.asList(getResources().getStringArray(R.array.purse_filter_type));
		mDropdownMenu.setEnabled(false);
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_up);
		//Logger.e(TAG, "Menu Width = " + mDropdownMenu.getWidth());
		DropMenuAdapter adapter = new DropMenuAdapter(this, menus, menus.get(mFilterType.toValue() + 1));
		PopupMenu menu = new PopupMenu(this, menus, mDropdownMenu.getWidth(), adapter, new IMenuCallback() {

			@Override
			public void onMenuItemClick(int index) {
				Logger.e(TAG, "Click Menu position = " + index);
				if (index != mFilterType.toValue() + 1) {
					mFilterType = PurseDealFilterType.convert(index - 1);
					onReloadData();
				}
			}

			@Override
			public void onDismiss() {
				Logger.d(TAG, "onDismiss");
				mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_down);
				mDropdownMenu.setEnabled(true);
			}
		});
		menu.showAsDropDown(mDropdownMenu);
	}

	@Override
	protected int[] getDataType() {
		return new int[] { DataType.PURSE_DEAL_LIST };
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}

}
