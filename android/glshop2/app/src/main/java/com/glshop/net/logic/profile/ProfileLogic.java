package com.glshop.net.logic.profile;

import java.util.List;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.AddAddrReq;
import com.glshop.platform.api.profile.DeleteAddrReq;
import com.glshop.platform.api.profile.GetAddrInfoReq;
import com.glshop.platform.api.profile.GetAddrListReq;
import com.glshop.platform.api.profile.GetContactListReq;
import com.glshop.platform.api.profile.GetMyProfileInfoReq;
import com.glshop.platform.api.profile.GetOtherProfileInfoReq;
import com.glshop.platform.api.profile.SetDefaultAddrReq;
import com.glshop.platform.api.profile.SubmitAuthInfoReq;
import com.glshop.platform.api.profile.UpdateAddrReq;
import com.glshop.platform.api.profile.UpdateCompanyIntroReq;
import com.glshop.platform.api.profile.UpdateContactReq;
import com.glshop.platform.api.profile.data.GetAddrInfoResult;
import com.glshop.platform.api.profile.data.GetAddrListResult;
import com.glshop.platform.api.profile.data.GetContactListResult;
import com.glshop.platform.api.profile.data.GetMyProfileInfoResult;
import com.glshop.platform.api.profile.data.GetOtherProfileInfoResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 用户资料接口业务实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-28 下午3:49:48
 */
public class ProfileLogic extends BasicLogic implements IProfileLogic {

	/**
	 * @param context
	 */
	public ProfileLogic(Context context) {
		super(context);
	}

	@Override
	public void getAddrList(String companyId) {
		GetAddrListReq req = new GetAddrListReq(this, new IReturnCallback<GetAddrListResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetAddrListResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetAddrListResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_DISCHARGE_ADDR_LIST_SUCCESS;
						respInfo.data = result.datas;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_DISCHARGE_ADDR_LIST_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.exec();
	}

	@Override
	public void getAddrInfo(String addrId) {
		GetAddrInfoReq req = new GetAddrInfoReq(this, new IReturnCallback<GetAddrInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetAddrInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetAddrInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_DISCHARGE_ADDR_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_DISCHARGE_ADDR_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.addrId = addrId;
		req.exec();
	}

	@Override
	public void addAddrInfo(AddrInfoModel info) {
		AddAddrReq req = new AddAddrReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "AddAddrResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_ADD_ADDR_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_ADD_ADDR_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void updateAddrInfo(AddrInfoModel info) {
		UpdateAddrReq req = new UpdateAddrReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UpdateAddrResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_ADDR_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_ADDR_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void deleteAddrInfo(String id) {
		DeleteAddrReq req = new DeleteAddrReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "DeleteAddrResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_DELETE_ADDR_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_DELETE_ADDR_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.addrId = id;
		req.exec();
	}

	@Override
	public void setDefaultAddrInfo(String id) {
		SetDefaultAddrReq req = new SetDefaultAddrReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "SetDefaultAddrResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_SET_DEFAULT_ADDR_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_SET_DEFAULT_ADDR_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.addrId = id;
		req.exec();
	}

	@Override
	public void getContactList(String companyId) {
		GetContactListReq req = new GetContactListReq(this, new IReturnCallback<GetContactListResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetContactListResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContactListResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_CONTACT_LIST_SUCCESS;
						respInfo.data = result.datas;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_CONTACT_LIST_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.exec();
	}

	@Override
	public void updateContactInfo(String companyId, List<ContactInfoModel> list) {
		UpdateContactReq req = new UpdateContactReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UpdateContactListResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_CONTACT_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_CONTACT_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.infoList = list;
		req.exec();
	}

	@Override
	public void updateCompanyIntroInfo(CompanyIntroInfoModel info) {
		UpdateCompanyIntroReq req = new UpdateCompanyIntroReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UpdateCompanyIntroResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_COMPANY_INTRO_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_UPDATE_COMPANY_INTRO_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void getMyProfileInfo(String companyId) {
		GetMyProfileInfoReq req = new GetMyProfileInfoReq(this, new IReturnCallback<GetMyProfileInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetMyProfileInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetMyProfileInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_MY_PROFILE_INFO_SUCCESS;
						respInfo.data = result.data;
						if (result.data != null) {
							GlobalConfig.getInstance().setAuth(result.data.authStatusType == AuthStatusType.AUTH_SUCCESS); // 更新认证状态
							GlobalConfig.getInstance().setProfleType(result.data.profileType); // 更新用户身份
						}
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_MY_PROFILE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.exec();
	}

	@Override
	public void getOtherProfileInfo(String companyId) {
		GetOtherProfileInfoReq req = new GetOtherProfileInfoReq(this, new IReturnCallback<GetOtherProfileInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetOtherProfileInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetOtherProfileInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_OTHER_PROFILE_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_OTHER_PROFILE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.exec();
	}

	@Override
	public void submitAuthInfo(AuthInfoModel info) {
		SubmitAuthInfoReq req = new SubmitAuthInfoReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "SubmitAuthInfoResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_SUBMIT_AUTH_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_SUBMIT_AUTH_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

}
