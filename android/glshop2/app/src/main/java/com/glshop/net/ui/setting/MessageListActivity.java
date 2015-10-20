package com.glshop.net.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter;
import com.glshop.net.ui.basic.fragment.message.MessageActivityFragment;
import com.glshop.net.ui.basic.fragment.message.MessageAdvisoryFragment;
import com.glshop.net.ui.basic.fragment.message.MessageAllFragment;
import com.glshop.net.ui.basic.fragment.message.MessageSystemFragment;
import com.glshop.net.ui.basic.fragment.message.MessageTransactionFragment;
import com.glshop.net.ui.basic.view.CustomViewPager;
import com.glshop.net.ui.basic.view.TabIndicator;
import com.glshop.net.ui.basic.view.dialog.BaseDialog;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.base.ui.BaseFragment;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 消息列表页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MessageListActivity extends BasicFragmentActivity implements OnPageChangeListener,BaseFragment.OnFragmentInteractionListener {

	private static final String TAG = "MessageListActivity";

	private static final String TAB_ALL = "fragment-tab-all";
	private static final String TAB_TRANSACTION = "fragment-tab-transaction";
	private static final String TAB_ADVISORY= "fragment-tab-advisory";
	private static final String TAB_SYSTEM= "fragment-tab-system";
	private static final String TAB_ACTIVITY= "fragment-tab-activity";


	private MessageAllFragment messageAllFragment;
	private MessageTransactionFragment messageTransactionFragment;
	private MessageAdvisoryFragment messageAdvisoryFragment;
	private MessageSystemFragment messageSystemFragment;
	private MessageActivityFragment messageActivityFragment;
	private DataConstants.SystemMsgType systemMsgType= DataConstants.SystemMsgType.ALL;

	private boolean isMsgReaded = false;

	private int unReadTotalSize = 0;


	private CustomViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TabIndicator mIndicator;
	private Button btn_commmon_action;
	/**是否处于编辑状态*/
	private boolean editorState=false;
	private boolean select_all=false;
	private Button btn_selectall;
	private ImageButton iv_back;

	private LinearLayout ll_bottom;

	private Button btn_del,btn_tag;
	private ConfirmDialog confirmDialog;
	/**选中可操作的消息*/
	private List<MessageInfoModel> selectData;

	private IMessageLogic mMessageLogic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_message_list);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.message_center);
		btn_selectall=getView(R.id.bt_common_back);
		btn_selectall.setOnClickListener(this);
		btn_commmon_action=getView(R.id.btn_commmon_action);
		btn_commmon_action.setVisibility(View.VISIBLE);
		btn_commmon_action.setText("编辑");
		btn_commmon_action.setOnClickListener(this);
		iv_back=getView(R.id.iv_common_back);
		iv_back.setOnClickListener(this);
		mViewPager = getView(R.id.msg_viewpager);
		mIndicator=getView(R.id.tab_pager_indicator);
		ll_bottom=getView(R.id.ll_bottom);
		btn_del=getView(R.id.btn_del);
		btn_tag=getView(R.id.btn_tag);
		btn_del.setOnClickListener(this);
		btn_tag.setOnClickListener(this);
	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			systemMsgType = DataConstants.SystemMsgType.convert(savedState.getInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB));
			Logger.e(TAG, "TabIndex = " + systemMsgType.toValue());
		}
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();

		messageAllFragment = getFragment(TAB_ALL);
		messageTransactionFragment = getFragment(TAB_TRANSACTION);
		messageAdvisoryFragment = getFragment(TAB_ADVISORY);
		messageSystemFragment = getFragment(TAB_SYSTEM);
		messageActivityFragment = getFragment(TAB_ACTIVITY);

		if (messageAllFragment == null) {
			messageAllFragment = new MessageAllFragment();
		}
		if (messageTransactionFragment == null) {
			messageTransactionFragment = new MessageTransactionFragment();
		}
		if (messageAdvisoryFragment == null) {
			messageAdvisoryFragment = new MessageAdvisoryFragment();
		}
		if (messageSystemFragment == null) {
			messageSystemFragment = new MessageSystemFragment();
		}
		if (messageActivityFragment == null) {
			messageActivityFragment = new MessageActivityFragment();
		}
		transaction.hide(messageAllFragment);
		transaction.hide(messageTransactionFragment);
		transaction.hide(messageAdvisoryFragment);
		transaction.hide(messageSystemFragment);
		transaction.hide(messageActivityFragment);

		transaction.commitAllowingStateLoss();

		List<TabPagerAdapter.TabInfo> tabs = new ArrayList<TabPagerAdapter.TabInfo>();
		tabs.add(new TabPagerAdapter.TabInfo(0, getString(R.string.message_type_all_title), TAB_ALL, MessageAllFragment.class, messageAllFragment));
		tabs.add(new TabPagerAdapter.TabInfo(1, getString(R.string.message_type_transaction_title), TAB_TRANSACTION, MessageTransactionFragment.class, messageTransactionFragment));
		tabs.add(new TabPagerAdapter.TabInfo(2, getString(R.string.message_type_advisory_title), TAB_ADVISORY, MessageAdvisoryFragment.class, messageAdvisoryFragment));
		tabs.add(new TabPagerAdapter.TabInfo(3, getString(R.string.message_type_system_title), TAB_SYSTEM, MessageSystemFragment.class, messageSystemFragment));
		tabs.add(new TabPagerAdapter.TabInfo(4, getString(R.string.message_type_active_title), TAB_ACTIVITY, MessageActivityFragment.class, messageActivityFragment));

		mIndicator.init(systemMsgType.toValue(), tabs, mViewPager);

		mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOffscreenPageLimit(1);
		switchTab();
	}
	private void switchTab() {
		mViewPager.setCurrentItem(systemMsgType.toValue());
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB, systemMsgType.toValue());
	}
	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
	}



	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
			case GlobalMessageType.MsgCenterMessageType.MSG_DEL_MSG_SUCCESS:
				onDelSuccess(respInfo);
				break;
			case GlobalMessageType.MsgCenterMessageType.MSG_DEL_MSG_FAILED:
				onDelFailed(respInfo);
				break;
			case GlobalMessageType.MsgCenterMessageType.MSG_REPORT_MESSAGE_READED_SUCCESS:
				onReportSuccess(respInfo);
				break;
			case GlobalMessageType.MsgCenterMessageType.MSG_REPORT_MESSAGE_READED_FAILED:
				onReportFailed(respInfo);
				break;
		}
	}

	/**
	 * 设置已读成功
	 * @param respInfo
	 */
	private void onReportSuccess(RespInfo respInfo){
		select_all=false;
		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.onReloadData();
		}
	}
	/**
	 * 设置已读失败
	 * @param respInfo
	 */
	private void onReportFailed(RespInfo respInfo){

		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.onReloadData();
		}

	}
	/**
	 * 删除消息成功
	 * @param respInfo
	 */
	private void onDelSuccess(RespInfo respInfo){
		showToast("删除成功");
		select_all=false;
		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.onReloadData();
		}
	}
	/**
	 * 删除消息失败
	 * @param respInfo
	 */
	private void onDelFailed(RespInfo respInfo){
		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.onReloadData();
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.onReloadData();
		}
	}
	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_LIST_FAILED:
				showToast(R.string.error_req_get_list);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
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
			case R.id.btn_commmon_action:
				if (editorState){
					previewView();
					if (BeanUtils.isNotEmpty(selectData))
						selectData.clear();
				}else {
					editorView();
				}
				break;
			case R.id.bt_common_back:
				//全选
				if (select_all) {
					select_all = false;
				}
				else {
					select_all = true;
				}
				ResultItem resultItem=new ResultItem();
				resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EDITOR, true);
				resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_ALL,select_all);
				if (DataConstants.SystemMsgType.ALL==systemMsgType){
					messageAllFragment.ToAllFragment(resultItem);
				}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
					messageTransactionFragment.ToAllFragment(resultItem);
				}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
					messageAdvisoryFragment.ToAllFragment(resultItem);
				}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
					messageActivityFragment.ToAllFragment(resultItem);
				}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
					messageSystemFragment.ToAllFragment(resultItem);
				}
				break;
			case R.id.btn_del:
				confirmDialog=new ConfirmDialog(this, R.style.dialog);
				confirmDialog.setContent(getResources().getString(R.string.dialog_content_del_msg));
				confirmDialog.setCallback(new BaseDialog.IDialogCallback() {
					@Override
					public void onConfirm(int type, Object obj) {
						if (BeanUtils.isNotEmpty(selectData)) {
							Logger.d(TAG, "删除数据 selectData=" + selectData.size());
								mMessageLogic.delMessage(selectData);
						}
					}

					@Override
					public void onCancel(int type) {

					}
				});
				confirmDialog.show();
				break;
			case R.id.btn_tag:
				confirmDialog=new ConfirmDialog(this, R.style.dialog);
				confirmDialog.setContent(getResources().getString(R.string.dialog_content_tag_msg));
				confirmDialog.setCallback(new BaseDialog.IDialogCallback() {
					@Override
					public void onConfirm(int type, Object obj) {
						if (BeanUtils.isNotEmpty(selectData)) {
							Logger.d(TAG, "标记数据 selectData=" + selectData.size());
							mMessageLogic.reportMessage(selectData);
						}
					}

					@Override
					public void onCancel(int type) {

					}
				});
				confirmDialog.show();
				break;
			default:
				break;
		}
	}

	/**
	 * 编辑状态
	 */
	private void editorView(){
		editorState=true;

		btn_commmon_action.setText("取消");
		btn_selectall.setText("全选");
		btn_selectall.setVisibility(View.VISIBLE);
		iv_back.setVisibility(View.GONE);
		ll_bottom.setVisibility(View.VISIBLE);
		ResultItem resultItem=new ResultItem();
		resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EDITOR, true);
		resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_NOT, true);

		mViewPager.setScrollable(false);
		mIndicator.setCanClick(false);

		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.ToAllFragment(resultItem);
		}
	}

	/**
	 * 预览状态
	 */
	private void previewView(){
		editorState=false;
		select_all=false;
		btn_commmon_action.setText("编辑");
		btn_selectall.setVisibility(View.GONE);
		iv_back.setVisibility(View.VISIBLE);
		ll_bottom.setVisibility(View.GONE);
		ResultItem resultItem=new ResultItem();
		resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EDITOR, false);
		resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_NOT, true);
		mViewPager.setScrollable(true);
		mIndicator.setCanClick(true);
		if (DataConstants.SystemMsgType.ALL==systemMsgType){
			messageAllFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.TRANSACTION==systemMsgType){
			messageTransactionFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.ADVISORY==systemMsgType){
			messageAdvisoryFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.ACTIVE==systemMsgType){
			messageActivityFragment.ToAllFragment(resultItem);
		}else if(DataConstants.SystemMsgType.SYSTEM==systemMsgType){
			messageSystemFragment.ToAllFragment(resultItem);
		}
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

//	@Override
//	protected int[] getDataType() {
//		return new int[] { DataType.MESSAGE_LIST };
//	}

	@Override
	protected void initLogics() {
		mMessageLogic = LogicFactory.getLogicByClass(IMessageLogic.class);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mIndicator.onScrolled((mViewPager.getWidth() + mViewPager.getPageMargin()) * position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
			case 0:
				systemMsgType= DataConstants.SystemMsgType.ALL;
				break;
			case 1:
				systemMsgType= DataConstants.SystemMsgType.TRANSACTION;
				break;
			case 2:
				systemMsgType= DataConstants.SystemMsgType.ADVISORY;
				break;
			case 3:
				systemMsgType= DataConstants.SystemMsgType.SYSTEM;
				break;
			case 4:
				systemMsgType= DataConstants.SystemMsgType.ACTIVE;
				break;
			default:
				systemMsgType= DataConstants.SystemMsgType.ALL;
				break;
		}
		Logger.d(TAG,"systemMsgType"+systemMsgType.toValue());
		mIndicator.onSwitched(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onFragmentInteraction(ResultItem uri) {
		boolean empty=uri.getBoolean(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EMPYT,false);
		Logger.d(TAG, "empty=" + empty);
		if (!empty) {
			selectData = (List<MessageInfoModel>) uri.get(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_EDITOR);
			btn_commmon_action.setVisibility(View.VISIBLE);
		}
		else {
			btn_commmon_action.setVisibility(View.GONE);
			previewView();
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Logger.d(TAG, "onRestart()" + DataCenter.getInstance().getData(DataCenter.DataType.MESSAGE_ALL_LIST).size());

	}
}
