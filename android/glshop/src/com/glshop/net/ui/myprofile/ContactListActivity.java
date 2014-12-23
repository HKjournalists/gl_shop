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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
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

	private LinearLayout llFirstContact;
	private LinearLayout llSecondContact;

	private Button mBtnSaveContact;

	private ContactInfoModel mFirstContact;
	private ContactInfoModel mSecondContact;

	private boolean isFirstEdit = false;
	private boolean isSecondEdit = false;

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

		llFirstContact = getView(R.id.ll_first_contact);
		llSecondContact = getView(R.id.ll_second_contact);

		((TextView) llFirstContact.findViewById(R.id.tv_contact_title)).setText(R.string.first_contact_info);
		((TextView) llSecondContact.findViewById(R.id.tv_contact_title)).setText(R.string.second_contact_info);

		llFirstContact.findViewById(R.id.btn_edit_contact).setOnClickListener(this);
		llSecondContact.findViewById(R.id.btn_edit_contact).setOnClickListener(this);
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getContactList(getCompanyId());
	}

	private void updateUI() {
		updateContactUI(llFirstContact, isFirstEdit, mFirstContact);
		updateContactUI(llSecondContact, isSecondEdit, mSecondContact);
	}

	private void updateContactUI(View view, boolean isEdit, ContactInfoModel info) {
		view.findViewById(R.id.et_contact_name).setVisibility(isEdit ? View.VISIBLE : View.GONE);
		view.findViewById(R.id.et_contact_phone).setVisibility(isEdit ? View.VISIBLE : View.GONE);
		view.findViewById(R.id.et_contact_landline).setVisibility(isEdit ? View.VISIBLE : View.GONE);

		view.findViewById(R.id.tv_contact_name).setVisibility(isEdit ? View.GONE : View.VISIBLE);
		view.findViewById(R.id.tv_contact_phone).setVisibility(isEdit ? View.GONE : View.VISIBLE);
		view.findViewById(R.id.tv_contact_landline).setVisibility(isEdit ? View.GONE : View.VISIBLE);

		if (info != null) {
			((TextView) view.findViewById(R.id.tv_contact_name)).setText(info.name);
			((TextView) view.findViewById(R.id.tv_contact_phone)).setText(info.telephone);
			((TextView) view.findViewById(R.id.tv_contact_landline)).setText(info.fixPhone);

			((EditText) view.findViewById(R.id.et_contact_name)).setText(info.name);
			((EditText) view.findViewById(R.id.et_contact_phone)).setText(info.telephone);
			((EditText) view.findViewById(R.id.et_contact_landline)).setText(info.fixPhone);
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
			int size = data.size();
			if (size == 1) {
				mFirstContact = data.get(0);
				mFirstContact.isDefault = true;
			}
			if (size >= 2) {
				for (ContactInfoModel info : data) {
					if (info.isDefault) {
						mFirstContact = info;
					} else {
						mSecondContact = info;
					}
				}
				if (mFirstContact == null) {
					mFirstContact = data.get(0);
					mFirstContact.isDefault = true;
					mSecondContact = data.get(1);
				}
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
		//MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE);
		showToast(R.string.save_success);

		Intent intent = new Intent();
		intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_CONTACT_INFO, mFirstContact);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action: // 保存
			saveContactInfo();
			break;
		case R.id.btn_edit_contact: // 编辑
			if (v == llFirstContact.findViewById(R.id.btn_edit_contact)) {
				if (!isFirstEdit) {
					isFirstEdit = true;
					updateContactUI(llFirstContact, isFirstEdit, mFirstContact);
				}
			} else {
				if (!isSecondEdit) {
					isSecondEdit = true;
					updateContactUI(llSecondContact, isSecondEdit, mSecondContact);
				}
			}
			break;
		case R.id.iv_common_back:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		}
	}

	private void saveContactInfo() {
		if (checkArgs()) {
			List<ContactInfoModel> contactList = new ArrayList<ContactInfoModel>();
			if (isFirstEdit) {
				if (mFirstContact == null) {
					mFirstContact = new ContactInfoModel();
				}
				mFirstContact.isDefault = true;
				mFirstContact.name = ((EditText) llFirstContact.findViewById(R.id.et_contact_name)).getText().toString().trim();
				mFirstContact.telephone = ((EditText) llFirstContact.findViewById(R.id.et_contact_phone)).getText().toString().trim();
				mFirstContact.fixPhone = ((EditText) llFirstContact.findViewById(R.id.et_contact_landline)).getText().toString().trim();
			}
			if (isSecondEdit) {
				if (mSecondContact == null) {
					mSecondContact = new ContactInfoModel();
				}
				mSecondContact.isDefault = false;
				mSecondContact.name = ((EditText) llSecondContact.findViewById(R.id.et_contact_name)).getText().toString().trim();
				mSecondContact.telephone = ((EditText) llSecondContact.findViewById(R.id.et_contact_phone)).getText().toString().trim();
				mSecondContact.fixPhone = ((EditText) llSecondContact.findViewById(R.id.et_contact_landline)).getText().toString().trim();
			}
			if (isFirstEdit || isSecondEdit) {
				if (mFirstContact != null) {
					contactList.add(mFirstContact);
				}
				if (mSecondContact != null) {
					contactList.add(mSecondContact);
				}
				showSubmitDialog();
				mProfileLogic.updateContactInfo(getCompanyId(), contactList);
			} else {
				finish();
			}
		}
	}

	private boolean checkArgs() {
		if (isFirstEdit) {
			if (StringUtils.isEmpty(((EditText) llFirstContact.findViewById(R.id.et_contact_name)).getText().toString().trim())) {
				showToast("第一联系人姓名不能为空!");
				return false;
			} else if (StringUtils.isEmpty(((EditText) llFirstContact.findViewById(R.id.et_contact_phone)).getText().toString().trim())) {
				showToast("第一联系人手机号不能为空!");
				return false;
			}
		}
		if (isSecondEdit) {
			if (StringUtils.isEmpty(((EditText) llSecondContact.findViewById(R.id.et_contact_name)).getText().toString().trim())) {
				showToast("第二联系人姓名不能为空!");
				return false;
			} else if (StringUtils.isEmpty(((EditText) llSecondContact.findViewById(R.id.et_contact_phone)).getText().toString().trim())) {
				showToast("第二联系人手机号不能为空!");
				return false;
			}
		}
		if (isSecondEdit && !isFirstEdit && mFirstContact == null) {
			showToast("第一联系人信息不能为空!");
			return false;
		}
		return true;
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
	}

}
