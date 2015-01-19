package com.glshop.net.ui.myprofile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.ScrollPositionInfo;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.profile.AreaAddrListAdapter;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 选择地域(省、市、区)列表界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class AreaAddrSelectActivity extends BasicActivity implements OnItemClickListener {

	private ListView mLvAddr;
	private AreaAddrListAdapter mAdapter;

	private String mDefaultAreaCode = "";
	private List<AreaInfoModel> mDefualtAreaList = null;

	private Stack<List<AreaInfoModel>> mNavListStack = new Stack<List<AreaInfoModel>>();
	private Stack<ScrollPositionInfo> mNavPositionStack = new Stack<ScrollPositionInfo>();
	private Stack<AreaInfoModel> mSelectStack = new Stack<AreaInfoModel>();
	private List<AreaInfoModel> mCurAreaList = null;

	private ISysCfgLogic mSysCfgLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_addr_select);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.lv_area_addr_list);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_discharge_area_addr_select);

		mLvAddr = getView(R.id.lv_area_addr_list);
		mAdapter = new AreaAddrListAdapter(this, null);
		mLvAddr.setAdapter(mAdapter);
		mLvAddr.setOnItemClickListener(this);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mDefaultAreaCode = getIntent().getStringExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_CODE);
		if (StringUtils.isNotEmpty(mDefaultAreaCode)) {
			mDefualtAreaList = mSysCfgLogic.getParentAreaInfo(mDefaultAreaCode);
		}
		loadAreaList(DataConstants.SysCfgCode.AREA_TOP_CODE);
	}

	@Override
	protected void onReloadData() {
		loadAreaList(DataConstants.SysCfgCode.AREA_TOP_CODE);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ProfileMessageType.MSG_GET_TRADE_ADDR_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_GET_TRADE_ADDR_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			ArrayList<AreaInfoModel> data = (ArrayList<AreaInfoModel>) respInfo.data;
			if (BeanUtils.isNotEmpty(data)) {
				mAdapter.setList(data, true);
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private boolean loadAreaList(String areaCode) {
		//updateDataStatus(DataStatus.LOADING);
		ArrayList<AreaInfoModel> data = (ArrayList<AreaInfoModel>) mSysCfgLogic.getChildAreaInfo(areaCode);
		mCurAreaList = data;
		if (BeanUtils.isNotEmpty(data)) {
			mAdapter.setList(data, true);
			mAdapter.setSelectedAddr(getSelectedAreaCode());
			mLvAddr.setSelection(0);
			updateDataStatus(DataStatus.NORMAL);
			return true;
		} else {
			//updateDataStatus(DataStatus.EMPTY);
			return false;
		}
	}

	private String getSelectedAreaCode() {
		String selectedArea = "";
		if (BeanUtils.isNotEmpty(mDefualtAreaList)) {
			if (mSelectStack.isEmpty()) {
				selectedArea = mDefualtAreaList.get(0).code;
			} else {
				int depth = mSelectStack.size();
				if (depth < mDefualtAreaList.size()) {
					if (mSelectStack.peek().code.equals(mDefualtAreaList.get(depth - 1).code)) {
						selectedArea = mDefualtAreaList.get(depth).code;
					}
				}
			}
		}
		return selectedArea;
	}

	private String getSelectAreaName() {
		StringBuffer selectedAreaName = new StringBuffer();
		if (!mSelectStack.isEmpty()) {
			for (int i = 0; i < mSelectStack.size(); i++) {
				selectedAreaName.append(/*(i == 0 ? "" : ".") + */mSelectStack.get(i).name);
			}
		}
		return selectedAreaName.toString();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvAddr.getHeaderViewsCount()));
		AreaInfoModel info = mAdapter.getItem(position - mLvAddr.getHeaderViewsCount());
		mNavListStack.add(mCurAreaList);
		mSelectStack.add(info);
		saveScrollPosition();
		if (!loadAreaList(getFullAreaCode())) {
			Intent data = new Intent();
			data.putExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_CODE, info.code);
			String areaName = getSelectAreaName();
			data.putExtra(GlobalAction.BuyAction.EXTRA_KEY_TRADE_AREA_NAME, getSelectAreaName());
			Logger.e(TAG, "Selected AreaCode = " + info.code + ", AreaName = " + areaName);
			setResult(Activity.RESULT_OK, data);
			finish();
		}
	}

	private String getFullAreaCode() {
		StringBuffer sb = new StringBuffer(DataConstants.SysCfgCode.AREA_TOP_CODE);
		if (!mSelectStack.isEmpty()) {
			for (int i = 0; i < mSelectStack.size(); i++) {
				sb.append("|" + mSelectStack.get(i).code);
			}
		}
		return sb.toString();
	}

	/**
	 * 保存列表滚动位置信息
	 */
	private void saveScrollPosition() {
		ScrollPositionInfo position = new ScrollPositionInfo();
		position.setFirstVisiblePosition(mLvAddr.getFirstVisiblePosition());

		View v = mLvAddr.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();
		position.setTopDistance(top);

		mNavPositionStack.add(position);
	}

	/**
	 * 恢复列表滚动位置信息
	 */
	private void restoreScrollPosition() {
		ScrollPositionInfo position = mNavPositionStack.pop();
		if (position != null) {
			mAdapter.notifyDataSetChanged();
			mLvAddr.setSelectionFromTop(position.getFirstVisiblePosition(), position.getTopDistance());
			//Logger.e(TAG, "ScrollPositionInfo = " + position);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			doBackAction();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		doBackAction();
	}

	private void doBackAction() {
		if (mNavListStack.isEmpty()) {
			finish();
		} else {
			List<AreaInfoModel> data = mNavListStack.pop();
			mCurAreaList = data;
			//mAdapter.setList(data, true);

			mAdapter = new AreaAddrListAdapter(this, data);
			mLvAddr.setAdapter(mAdapter);

			updateDataStatus(DataStatus.NORMAL);
			if (!mSelectStack.isEmpty()) {
				AreaInfoModel info = mSelectStack.pop();
				if (info != null) {
					mAdapter.setSelectedAddr(info.code);
				}
			}
			restoreScrollPosition();
		}
	}

	@Override
	protected void initLogics() {
		mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
	}
}
