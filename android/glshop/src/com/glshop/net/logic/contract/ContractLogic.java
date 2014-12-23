package com.glshop.net.logic.contract;

import java.util.List;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.AgreeCancelContractReq;
import com.glshop.platform.api.contract.AgreeUncfmContractReq;
import com.glshop.platform.api.contract.CancelContractReq;
import com.glshop.platform.api.contract.ConfirmDischargeReq;
import com.glshop.platform.api.contract.ConfirmReceiptReq;
import com.glshop.platform.api.contract.ContactCustomServiceReq;
import com.glshop.platform.api.contract.ContractAcceptanceReq;
import com.glshop.platform.api.contract.ContractNegotiateReq;
import com.glshop.platform.api.contract.ContractOfflinePayReq;
import com.glshop.platform.api.contract.ContractOnlinePayReq;
import com.glshop.platform.api.contract.EvaluateContractReq;
import com.glshop.platform.api.contract.GetCompanyEvaListReq;
import com.glshop.platform.api.contract.GetContractEvaListReq;
import com.glshop.platform.api.contract.GetContractInfoReq;
import com.glshop.platform.api.contract.GetContractModelReq;
import com.glshop.platform.api.contract.GetContractsReq;
import com.glshop.platform.api.contract.GetOprHistoryReq;
import com.glshop.platform.api.contract.GetToPayContractsReq;
import com.glshop.platform.api.contract.data.AgreeContractSignResult;
import com.glshop.platform.api.contract.data.GetCompanyEvaListResult;
import com.glshop.platform.api.contract.data.GetContractEvaListResult;
import com.glshop.platform.api.contract.data.GetContractInfoResult;
import com.glshop.platform.api.contract.data.GetContractModelResult;
import com.glshop.platform.api.contract.data.GetContractsResult;
import com.glshop.platform.api.contract.data.GetOprHistoryResult;
import com.glshop.platform.api.contract.data.GetToPayContractsResult;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的合同业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:15:06
 */
public class ContractLogic extends BasicLogic implements IContractLogic {

	/**
	 * @param context
	 */
	public ContractLogic(Context context) {
		super(context);
	}

	@Override
	public void getContracts(final ContractType type, final int pageIndex, final int pageSize, final DataReqType reqType) {
		GetContractsReq req = new GetContractsReq(this, new IReturnCallback<GetContractsResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetContractsResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractsResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = reqType.toValue();
					respInfo.intArg2 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<ContractSummaryInfoModel> data = new ArrayList<ContractSummaryInfoModel>();
						//for (int i = pageIndex; i < pageSize; i++) {
						//for (int i = 0; i < 2; i++) {
						for (int i = pageIndex * pageSize; i < ((pageIndex + 1) * pageSize); i++) {
							ContractSummaryInfoModel info = new ContractSummaryInfoModel();
							info.buyType = BuyType.BUYER;
							info.summary = "出售给靖江商业公司黄砂";
							info.amount = "500";
							if (type == ContractType.UNCONFIRMED) {
								info.contractId = String.valueOf(10000 + i);
							} else if (type == ContractType.ONGOING) {
								info.contractId = String.valueOf(20000 + i);
							} else {
								info.contractId = String.valueOf(30000 + i);
							}
							info.createTime = "2014-10-10 01:02:03";
							info.expireTime = "2014-12-20 00:00:00";
							data.add(info);
						}
						result.datas = data;*/
						//End add

						message.what = ContractMessageType.MSG_GET_CONTRACTS_SUCCESS;
						respInfo.data = result.datas == null ? 0 : result.datas.size();

						int dataType = DataType.UFM_CONTRACT_LIST;
						switch (type) {
						case UNCONFIRMED:
							dataType = DataType.UFM_CONTRACT_LIST;
							break;
						case ONGOING:
							dataType = DataType.ONGOING_CONTRACT_LIST;
							break;
						case ENDED:
							dataType = DataType.ENDED_CONTRACT_LIST;
							break;
						}
						DataCenter.getInstance().addData(result.datas, dataType, reqType);
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACTS_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractType = type;
		req.pageIndex = pageIndex;
		req.pageSize = pageSize;
		req.exec();
	}

	@Override
	public void getUncfmContractInfo(String contractId) {
		getContractModel(contractId);
	}

	@Override
	public void getContractInfo(final String invoker, String contractId) {
		GetContractInfoReq req = new GetContractInfoReq(this, new IReturnCallback<GetContractInfoResult>() {

			@Override
			public void onReturn(Object obj, ResponseEvent event, GetContractInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(invoker, result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						ContractInfoModel info = result.data;
						covertNegotiateInfo(info.firstNegotiateList);
						covertNegotiateInfo(info.secondNegotiateList);
						message.what = ContractMessageType.MSG_GET_CONTRACT_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACT_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void getEndedContractInfo(String contractId) {
		GetContractInfoReq req = new GetContractInfoReq(this, new IReturnCallback<GetContractInfoResult>() {

			@Override
			public void onReturn(Object obj, ResponseEvent event, GetContractInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_GET_CONTRACT_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACT_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void getContractModel(String contractId) {
		GetContractModelReq req = new GetContractModelReq(this, new IReturnCallback<GetContractModelResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetContractModelResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractModelResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*ContractModelInfoModel info = new ContractModelInfoModel();
						info.contractId = "123456";
						info.contractName = "购买黄砂1000吨";
						info.firstPartyName = "某某某";
						info.secondPartyName = "晋江***公司";
						result.data = info;*/
						//End add

						message.what = ContractMessageType.MSG_GET_CONTRACT_MODEL_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACT_MODEL_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void getContractOprHistory(String contractId) {
		GetOprHistoryReq req = new GetOprHistoryReq(this, new IReturnCallback<GetOprHistoryResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetOprHistoryResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetOprHistoryResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<ContractOprInfoModel> data = new ArrayList<ContractOprInfoModel>();
						for (int i = 0; i < 10; i++) {
							ContractOprInfoModel info = new ContractOprInfoModel();
							info.summary = "冻结保证金";
							info.dateTime = "2014-10-10 01:02:03";
							data.add(info);
						}
						result.datas = data;*/
						//End add

						message.what = ContractMessageType.MSG_GET_CONTRACT_OPR_HISTORY_SUCCESS;
						respInfo.data = result.datas;
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACT_OPR_HISTORY_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void agreeContractSign(String contractId) {
		AgreeUncfmContractReq req = new AgreeUncfmContractReq(this, new IReturnCallback<AgreeContractSignResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, AgreeContractSignResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "AgreeUncfmContractResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_AGREE_CONTRACT_SIGN_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = ContractMessageType.MSG_AGREE_CONTRACT_SIGN_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void payContractOnline(String contractId, String smsCode, String pwd) {
		ContractOnlinePayReq req = new ContractOnlinePayReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "OnlinePayContractResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_PAY_ONLINE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_PAY_ONLINE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.smsVerifyCode = smsCode;
		req.password = pwd;
		req.exec();
	}

	@Override
	public void payContractOffline(String contractId) {
		ContractOfflinePayReq req = new ContractOfflinePayReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "OfflinePayContractResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_PAY_OFFLINE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_PAY_OFFLINE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void acceptanceContract(String contractId, int type) {
		ContractAcceptanceReq req = new ContractAcceptanceReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ContractAcceptanceResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_ACCEPTANCE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_ACCEPTANCE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.type = type;
		req.exec();
	}

	@Override
	public void contractNegotiate(NegotiateInfoModel info) {
		ContractNegotiateReq req = new ContractNegotiateReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ContractNegotiateResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_NEGOTIATE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_NEGOTIATE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void cancelContract(String contractId) {
		CancelContractReq req = new CancelContractReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "CancelContractResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_CANCEL_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_CANCEL_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void agreeCancelContract(String contractId) {
		AgreeCancelContractReq req = new AgreeCancelContractReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "AgreeCancelContractResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_AGREE_CONTRACT_CANCEL_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_AGREE_CONTRACT_CANCEL_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void contactCustomService(String contractId) {
		ContactCustomServiceReq req = new ContactCustomServiceReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ContactCustomServiceResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTACT_CUSTOM_SERVICE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTACT_CUSTOM_SERVICE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void confirmDischarge(String contractId) {
		ConfirmDischargeReq req = new ConfirmDischargeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ConfirmDischargeResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_COMFIRM_DISCHARGE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_COMFIRM_DISCHARGE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void confirmReceipt(String contractId) {
		ConfirmReceiptReq req = new ConfirmReceiptReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ConfirmReceiptResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_COMFIRM_RECEIPT_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_COMFIRM_RECEIPT_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void contractEvaluate(EvaluationInfoModel info) {
		EvaluateContractReq req = new EvaluateContractReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "EvaluateContractResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_EVALUATE_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_EVALUATE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void getToPayContracts(final DataReqType reqType) {
		GetToPayContractsReq req = new GetToPayContractsReq(this, new IReturnCallback<GetToPayContractsResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetToPayContractsResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetToPayContractsResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<ToPayContractInfoModel> data = new ArrayList<ToPayContractInfoModel>();
						for (int i = 0; i < 30; i++) {
							ToPayContractInfoModel info = new ToPayContractInfoModel();
							info.buyType = BuyType.BUYER;
							info.summary = "出售给靖江商业公司黄砂";
							info.amount = "500";
							info.contractId = "123456";
							info.createTime = "2014-10-10 01:02:03";
							info.expireTime = "2014-12-20 00:00:00";
							info.toPayMoney = "200,000,00";
							data.add(info);
						}
						result.datas = data;*/
						//End add

						//message.arg2 = type.toValue();
						//message.obj = result.datas;

						message.what = ContractMessageType.MSG_GET_TO_PAY_CONTRACTS_SUCCESS;
						respInfo.data = result.datas;

						DataCenter.getInstance().addData(result.datas, DataType.TO_PAY_CONTRACT_LIST, reqType);
					} else {
						message.what = ContractMessageType.MSG_GET_TO_PAY_CONTRACTS_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.exec();
	}

	@Override
	public void getContractEvaluationList(String contractId) {
		GetContractEvaListReq req = new GetContractEvaListReq(this, new IReturnCallback<GetContractEvaListResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetContractEvaListResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractEvaListResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<EvaluationInfoModel> data = new ArrayList<EvaluationInfoModel>();
						for (int i = 0; i < 30; i++) {
							EvaluationInfoModel info = new EvaluationInfoModel();
							info.id = "123456";
							info.content = "质量很好";
							info.dateTime = "2014-10-10 01:02:03";
							data.add(info);
						}
						result.datas = data;*/
						//End add

						message.what = ContractMessageType.MSG_GET_CONTRACTS_EVALUATION_SUCCESS;
						respInfo.data = result.datas;
					} else {
						message.what = ContractMessageType.MSG_GET_CONTRACTS_EVALUATION_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.exec();
	}

	@Override
	public void getCompanyEvaluationList(String companyId, final DataReqType reqType) {
		GetCompanyEvaListReq req = new GetCompanyEvaListReq(this, new IReturnCallback<GetCompanyEvaListResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetCompanyEvaListResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetCompanyEvaListResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {

						//Add test data
						/*List<EvaluationInfoModel> data = new ArrayList<EvaluationInfoModel>();
						for (int i = 0; i < 5; i++) {
							EvaluationInfoModel info = new EvaluationInfoModel();
							info.id = "123456";
							info.user = "江苏靖江有限公司";
							info.content = "货物质量不错，送货及时。货物质量不错，送货及时。货物质量不错，送货及时。";
							info.dateTime = "2014-10-10 01:02:03";
							info.isSingleLine = true;
							data.add(info);
						}
						result.datas = data;*/
						//End add

						message.what = ContractMessageType.MSG_GET_COMPANY_EVALUATION_SUCCESS;
						respInfo.data = result.datas;

						DataCenter.getInstance().addData(result.datas, DataType.EVALUATION_LIST, reqType);
					} else {
						message.what = ContractMessageType.MSG_GET_COMPANY_EVALUATION_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.companyId = companyId;
		req.exec();
	}

	private void covertNegotiateInfo(List<NegotiateInfoModel> list) {
		if (BeanUtils.isNotEmpty(list)) {
			for (NegotiateInfoModel negInfo : list) {
				if (StringUtils.isNotEmpty(negInfo.operator) && negInfo.operator.equals(GlobalConfig.getInstance().getCompanyId())) {
					negInfo.isMine = true;
				} else {
					negInfo.isMine = false;
				}
			}
		}
	}

}
