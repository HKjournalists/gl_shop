package com.glshop.net.ui.myprofile;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 企业联系人列表界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContactListActivity extends BasicActivity {

	private Button mBtnSaveContact;

	private TextView mTvContactName;
	private TextView mTvContactTelephone;
	private TextView mTvContactLandline;

	private EditText mEtContactName;
	private EditText mEtContactTelephone;
	private EditText mEtContactLandline;

	private ContactInfoModel mContactInfo;

	private boolean isEditMode = true;

	private IProfileLogic mProfileLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_contact_list);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.contact_mgr);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mBtnSaveContact = getView(R.id.btn_commmon_action);
		//mBtnSaveContact.setVisibility(View.VISIBLE);
		mBtnSaveContact.setText(R.string.save);
		mBtnSaveContact.setOnClickListener(this);

		mTvContactName = getView(R.id.tv_contact_name);
		mTvContactTelephone = getView(R.id.tv_contact_phone);
		mTvContactLandline = getView(R.id.tv_contact_landline);

		mEtContactName = getView(R.id.et_contact_name);
		mEtContactTelephone = getView(R.id.et_contact_phone);
		mEtContactLandline = getView(R.id.et_contact_landline);

		getView(R.id.btn_edit_contact).setOnClickListener(this);
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getContactList(getCompanyId());
	}

	private void updateUI() {
		mTvContactName.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
		mTvContactTelephone.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
		mTvContactLandline.setVisibility(isEditMode ? View.GONE : View.VISIBLE);

		mEtContactName.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
		mEtContactTelephone.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
		mEtContactLandline.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

		if (mContactInfo != null) {
			mTvContactName.setText(mContactInfo.name);
			mTvContactTelephone.setText(mContactInfo.telephone);
			mTvContactLandline.setText(mContactInfo.fixPhone);

			mEtContactName.setText(mContactInfo.name);
			mEtContactTelephone.setText(mContactInfo.telephone);
			mEtContactLandline.setText(mContactInfo.fixPhone);

			requestSelection(mEtContactName);
			requestSelection(mEtContactTelephone);
			requestSelection(mEtContactLandline);
		} else {
			mEtContactName.setText("");
			mEtContactTelephone.setText("");
			mEtContactLandline.setText("");
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getContactList(getCompanyId());
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ProfileMessageType.MSG_GET_CONTACT_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_GET_CONTACT_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_UPDATE_CONTACT_INFO_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_UPDATE_CONTACT_INFO_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			ArrayList<ContactInfoModel> data = (ArrayList<ContactInfoModel>) respInfo.data;
			if (BeanUtils.isNotEmpty(data)) {
				mContactInfo = data.get(0);
			}
		}
		updateDataStatus(DataStatus.NORMAL);
		mBtnSaveContact.setVisibility(View.VISIBLE);
		updateUI();
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		showToast(R.string.save_success);

		// 更新联系人信息
		Intent intent = new Intent();
		intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_CONTACT_INFO, mContactInfo);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.ProfileMessageType.MSG_GET_CONTACT_LIST_FAILED:
				showToast(R.string.error_req_get_info);
				break;
			case GlobalMessageType.ProfileMessageType.MSG_UPDATE_CONTACT_INFO_FAILED:
				showToast(R.string.error_req_submit_info);
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
		case R.id.btn_commmon_action: // 保存
			saveContactInfo();
			break;
		case R.id.btn_edit_contact: // 编辑
			if (!isEditMode) {
				isEditMode = true;
				updateUI();
			}
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

	private void saveContactInfo() {
		if (checkArgs()) {
			ActivityUtil.hideKeyboard(this);
			List<ContactInfoModel> contactList = new ArrayList<ContactInfoModel>();
			if (isEditMode) {
				if (mContactInfo == null) {
					mContactInfo = new ContactInfoModel();
				}

				mContactInfo.isDefault = true;
				mContactInfo.name = mEtContactName.getText().toString().trim();
				mContactInfo.telephone = mEtContactTelephone.getText().toString().trim();
				mContactInfo.fixPhone = mEtContactLandline.getText().toString().trim();
				contactList.add(mContactInfo);

				showSubmitDialog();
				mProfileLogic.updateContactInfo(getCompanyId(), contactList);
			} else {
				finish();
			}
		}
	}

	private boolean checkArgs() {
		if (isEditMode) {
			if (StringUtils.isEmpty(mEtContactName.getText().toString().trim())) {
				showToast("联系人姓名不能为空!");
				return false;
			} else if (StringUtils.isEmpty(mEtContactTelephone.getText().toString().trim())) {
				showToast("联系人手机号不能为空!");
				return false;
			}
		}
		return true;
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
	}

}
