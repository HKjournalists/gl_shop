package com.glshop.net.logic.purse;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.platform.api.DataConstants.PayeeStatus;
import com.glshop.platform.api.DataConstants.PurseDealFilterType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.AddPayeeReq;
import com.glshop.platform.api.purse.DeletePayeeReq;
import com.glshop.platform.api.purse.GetDealInfoReq;
import com.glshop.platform.api.purse.GetDealsReq;
import com.glshop.platform.api.purse.GetPayeeListReq;
import com.glshop.platform.api.purse.GetPurseInfoReq;
import com.glshop.platform.api.purse.RollOutReq;
import com.glshop.platform.api.purse.RollOutToDepositReq;
import com.glshop.platform.api.purse.RptOfflineRechargeReq;
import com.glshop.platform.api.purse.SetDefaultPayeeReq;
import com.glshop.platform.api.purse.UpdatePayeeReq;
import com.glshop.platform.api.purse.data.GetDealInfoResult;
import com.glshop.platform.api.purse.data.GetDealsResult;
import com.glshop.platform.api.purse.data.GetPayeeListResult;
import com.glshop.platform.api.purse.data.GetPurseInfoResult;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.api.purse.data.model.RolloutInfoModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 我的钱包业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:17:44
 */
public class PurseLogic extends BasicLogic implements IPurseLogic {

	/**
	 * @param context
	 */
	public PurseLogic(Context context) {
		super(context);
	}

	@Override
	public void getPurseInfo() {
		GetPurseInfoReq req = new GetPurseInfoReq(this, new IReturnCallback<GetPurseInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetPurseInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetPurseInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_GET_PURSE_INFO_SUCCESS;
						respInfo.data = result.data;
						if (result.data != null) {
							GlobalConfig.getInstance().setDepositStatus(result.data.isDepositEnough); // 更新保证金状态
							GlobalConfig.getInstance().setDepositBalance(result.data.depositBalance); // 更新保证金余额
							GlobalConfig.getInstance().setPaymentStatus(result.data.isPaymentEnough); // 更新货款状态
							GlobalConfig.getInstance().setPaymentBalance(result.data.paymentBalance); // 更新货款余额
						}
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_GET_PURSE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.exec();
	}

	@Override
	public void getDealList(final PurseType type, final PurseDealFilterType filterType, final int pageIndex, final int pageSize, final DataReqType reqType) {
		GetDealsReq req = new GetDealsReq(this, new IReturnCallback<GetDealsResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetDealsResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetDealsResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = reqType.toValue();
					respInfo.intArg2 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<DealSummaryInfoModel> data = new ArrayList<DealSummaryInfoModel>();
						for (int i = 0; i < Common.PAGE_SIZE; i++) {
							DealSummaryInfoModel info = new DealSummaryInfoModel();
							info.id = "3366998866332211";
							info.purseType = PurseType.DEPOSIT;
							info.dealTime = "2014-10-10 01:02:03";
							info.dealType = DepositDealType.IN_FROM_PAYMENT.toValue();
							info.balance = 8000f;
							info.dealMoney = 2000;
							data.add(info);
						}
						result.datas = data;*/
						//End add

						message.what = GlobalMessageType.PurseMessageType.MSG_GET_DEALS_SUCCESS;
						respInfo.data = result.datas == null ? 0 : result.datas.size();

						DataCenter.getInstance().addData(result.datas, DataType.PURSE_DEAL_LIST, reqType);
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_GET_DEALS_FAILED;
					}
					sendMessage(message);
				}
			}
		});

		req.type = type;
		req.filterType = filterType;
		req.pageIndex = pageIndex;
		req.pageSize = pageSize;
		req.exec();
	}

	@Override
	public void getDealInfo(String id) {
		GetDealInfoReq req = new GetDealInfoReq(this, new IReturnCallback<GetDealInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetDealInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetDealInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*DealInfoModel info = new DealInfoModel();
						info.id = "3366998866332211";
						info.purseType = PurseType.DEPOSIT;
						info.dealTime = "2014-10-10 01:02:03";
						info.directionType = DealDirectionType.IN;
						info.balance = 8000f;
						info.dealMoney = 2000;
						result.info = info;*/
						//End add

						message.what = GlobalMessageType.PurseMessageType.MSG_GET_DEAL_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_GET_DEAL_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.id = id;
		req.exec();
	}

	@Override
	public void getPayeeList(String companyId, final PayeeStatus status, final DataReqType reqType) {
		GetPayeeListReq req = new GetPayeeListReq(this, new IReturnCallback<GetPayeeListResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetPayeeListResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetPayeeListResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<PayeeInfoModel> data = new ArrayList<PayeeInfoModel>();
						for (int i = 0; i < Common.PAGE_SIZE; i++) {
							PayeeInfoModel info = new PayeeInfoModel();
							info.id = "1000" + i;
							info.name = "张三";
							info.bankName = "招商银行";
							info.subBank = "南山支行";
							info.card = "6226 0123 4567 369";

							if (i % 3 == 0 && i != 0) {
								info.status = PayeeStatus.AUTHING;
							} else if (i % 7 == 0 && i != 0) {
								info.status = PayeeStatus.AUTH_FAILED;
							}

							data.add(info);
						}
						result.datas = data;*/
						//End add

						if (BeanUtils.isNotEmpty(result.datas)) {
							for (PayeeInfoModel info : result.datas) {
								info.bankName = SysCfgUtils.getBankName(mcontext, info.bankCode);
							}
						}

						if (status != null) {
							message.what = GlobalMessageType.PurseMessageType.MSG_GET_SELECT_PAYEE_LIST_SUCCESS;
							//message.obj = result.datas;
							DataCenter.getInstance().addData(result.datas, DataType.PAYEE_SELECT_LIST, reqType);
						} else {
							message.what = GlobalMessageType.PurseMessageType.MSG_GET_MGR_PAYEE_LIST_SUCCESS;
							//message.obj = result.datas;
							DataCenter.getInstance().addData(result.datas, DataType.PAYEE_MANAGER_LIST, reqType);
						}
					} else {
						if (status != null) {
							message.what = GlobalMessageType.PurseMessageType.MSG_GET_SELECT_PAYEE_LIST_FAILED;
						} else {
							message.what = GlobalMessageType.PurseMessageType.MSG_GET_MGR_PAYEE_LIST_FAILED;
						}
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.status = status;
		req.exec();
	}

	@Override
	public void addPayeeInfo(PayeeInfoModel info, String smsCode) {
		AddPayeeReq req = new AddPayeeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "AddPayeeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_ADD_PAYEE_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_ADD_PAYEE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.smsCode = smsCode;
		req.info = info;
		req.exec();
	}

	@Override
	public void updatePayeeInfo(PayeeInfoModel info, String smsCode) {
		UpdatePayeeReq req = new UpdatePayeeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UpdatePayeeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_UPDATE_PAYEE_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_UPDATE_PAYEE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.smsCode = smsCode;
		req.info = info;
		req.exec();
	}

	@Override
	public void deletePayeeInfo(String payeeId) {
		DeletePayeeReq req = new DeletePayeeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "DeletePayeeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_DELETE_PAYEE_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_DELETE_PAYEE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.payeeId = payeeId;
		req.exec();
	}

	@Override
	public void setDefaultPayeeInfo(String payeeId) {
		SetDefaultPayeeReq req = new SetDefaultPayeeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "SetDefaultPayeeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_SET_DEFAULT_PAYEE_INFO_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_SET_DEFAULT_PAYEE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.payeeId = payeeId;
		req.exec();
	}

	@Override
	public void rollOut(RolloutInfoModel rollInfo, String smsCode, String password) {
		RollOutReq req = new RollOutReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "RollOutResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_ROLL_OUT_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_ROLL_OUT_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = rollInfo;
		req.smsCode = smsCode;
		req.password = password;
		req.exec();

	}

	@Override
	public void rollOutToDeposit(String money, String smsCode, String password) {
		RollOutToDepositReq req = new RollOutToDepositReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "RollOutToDepositResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_ROLLOUT_DEPOSIT_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_ROLLOUT_DEPOSIT_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.money = money;
		req.smsCode = smsCode;
		req.password = password;
		req.exec();
	}

	@Override
	public void purseRecharegeOffline(PurseType type) {
		RptOfflineRechargeReq req = new RptOfflineRechargeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "RptOfflineRechargeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = GlobalMessageType.PurseMessageType.MSG_RECHARGE_OFFLINE_SUCCESS;
					} else {
						message.what = GlobalMessageType.PurseMessageType.MSG_RECHARGE_OFFLINE_SUCCESS;
					}
					sendMessage(message);
				}
			}
		});
		req.purseType = type;
		req.exec();
	}

}
