package com.glshop.net.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.MessageListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 消息列表页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MessageListActivity extends BasicActivity implements OnItemClickListener {

	private static final String TAG = "MessageListActivity";

	private PullRefreshListView mLvMassageList;
	private MessageListAdapter mAdapter;

	private boolean hasNextPage = true;

	private boolean isMsgReaded = false;

	private int unReadTotalSize = 0;

	private IMessageLogic mMessageLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_message_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.message_center);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mLvMassageList = getView(R.id.lv_message_list);
		mLvMassageList.setIsRefreshable(true);
		mLvMassageList.hideFootView();

		setOnRefreshListener(mLvMassageList);
		setOnScrollListener(mLvMassageList);
		mLvMassageList.setOnItemClickListener(this);

		mAdapter = new MessageListAdapter(this, null);
		mLvMassageList.setAdapter(mAdapter);

		mNormalDataView = mLvMassageList;
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mMessageLogic.getMessageListFromServer(getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mMessageLogic.getMessageListFromServer(getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mMessageLogic.getMessageListFromServer(getCompanyId(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
	}

	@Override
	protected void onScrollMore() {
		Logger.e(TAG, "---onLoadMore & HasNextPage = " + hasNextPage);
		if (hasNextPage) {
			mLvMassageList.showLoading();
			mMessageLogic.getMessageListFromServer(getCompanyId(), pageIndex, PAGE_SIZE, DataReqType.MORE);
		} else {
			mLvMassageList.showLoadFinish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo != null) {
			DataReqType type = DataReqType.convert(respInfo.intArg1);
			int size = (Integer) respInfo.data;
			unReadTotalSize = respInfo.intArg2;

			if (type == DataReqType.INIT && size == 0) {
				updateDataStatus(DataStatus.EMPTY);
			} else if (type == DataReqType.REFRESH && size == 0 && BeanUtils.isEmpty(DataCenter.getInstance().getData(DataType.MESSAGE_LIST))) {
				updateDataStatus(DataStatus.EMPTY);
			} else {
				updateDataStatus(DataStatus.NORMAL);
				mAdapter.setList(DataCenter.getInstance().getData(DataType.MESSAGE_LIST));
				mLvMassageList.onRefreshSuccess();
				if (type == DataReqType.INIT) {
					mLvMassageList.setSelection(0);
				}
				if (type == DataReqType.INIT || type == DataReqType.MORE) {
					if (size > 0) {
						pageIndex++;
					}
					Logger.e(TAG, "Size = " + size + ", PageSize= " + PAGE_SIZE);
					hasNextPage = size >= PAGE_SIZE;
					if (hasNextPage) {
						mLvMassageList.showLoading();
					} else {
						mLvMassageList.showLoadFinish();
					}
				}
			}
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		if (respInfo != null) {
			handleErrorAction(respInfo);
			DataReqType type = DataReqType.convert(respInfo.intArg1);
			if (type == DataReqType.MORE) {
				mLvMassageList.showLoadFail();
			} else {
				updateDataStatus(DataStatus.ERROR);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			checkReadStatus();
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvMassageList.getHeaderViewsCount()));
		MessageInfoModel info = mAdapter.getItem(position - mLvMassageList.getHeaderViewsCount());
		if (info.status == MessageStatus.UNREADED) {
			isMsgReaded = true;
		}
		Intent intent = new Intent(this, MessageDetailActivity.class);
		intent.putExtra(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_INFO, info);
		startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_VIEW_SYSTEM_MESSAGE);
		info.status = MessageStatus.READED;
		info.isReported = true;
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_VIEW_SYSTEM_MESSAGE:
			// TODO 
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		checkReadStatus();
	}

	private void checkReadStatus() {
		if (isMsgReaded || unReadTotalSize != GlobalConfig.getInstance().getUnreadMsgNum()) {
			MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.MsgCenterMessageType.MSG_REFRESH_UNREAD_MSG_NUM);
		}
	}

	@Override
	protected void initLogics() {
		mMessageLogic = LogicFactory.getLogicByClass(IMessageLogic.class);
	}

}
