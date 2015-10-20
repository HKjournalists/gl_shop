package com.glshop.net.logic.contract;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractConfirmType;
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
import com.glshop.platform.api.contract.MultiCancelContractReq;
import com.glshop.platform.api.contract.MultiConfirmContractReq;
import com.glshop.platform.api.contract.data.AgreeContractSignResult;
import com.glshop.platform.api.contract.data.GetCompanyEvaListResult;
import com.glshop.platform.api.contract.data.GetContractEvaListResult;
import com.glshop.platform.api.contract.data.GetContractInfoResult;
import com.glshop.platform.api.contract.data.GetContractModelResult;
import com.glshop.platform.api.contract.data.GetContractsResult;
import com.glshop.platform.api.contract.data.GetOprHistoryResult;
import com.glshop.platform.api.contract.data.GetToPayContractsResult;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.api.contract.data.model.ToPayContractInfoModel;
import com.glshop.platform.api.profile.GetContractAddrInfoReq;
import com.glshop.platform.api.profile.data.GetAddrInfoResult;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

import java.util.List;

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
						if (BeanUtils.isNotEmpty(result.datas)) {
							for (ContractSummaryInfoModel info : result.datas) {
								// 解析货物详细规格名称
								String productName = SysCfgUtils.getProductFullName(mcontext, info.productCode, info.productSubCode, info.productSpecId);
								if (StringUtils.isNotEmpty(productName)) {
									info.productName = productName;
								}
							}
						}

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
	public void getContractInfo(final String invoker, String contractId, boolean isGetModel) {
		GetContractInfoReq req = new GetContractInfoReq(this, new IReturnCallback<GetContractInfoResult>() {

			@Override
			public void onReturn(Object invokerObj, ResponseEvent event, GetContractInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetContractInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(invoker, result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						ContractInfoModel info = result.data;
						// 解析货物详细规格名称
						String productName = SysCfgUtils.getProductFullName(mcontext, info.productCode, info.productSubCode, info.productSpecId);
						if (StringUtils.isNotEmpty(productName)) {
							result.data.productName = productName;
						}

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
		req.isGetModel = isGetModel;
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
	public void getContractAddrInfo(String buyId) {
		GetContractAddrInfoReq req = new GetContractAddrInfoReq(this, new IReturnCallback<GetAddrInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetAddrInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetAddrInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_CONTRACT_ADDR_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = GlobalMessageType.ProfileMessageType.MSG_GET_CONTRACT_ADDR_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyId = buyId;
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
	public void multiCancelContract(final String invoker, String contractId, final ContractCancelType type) {
		MultiCancelContractReq req = new MultiCancelContractReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invokerObj, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "MultiCancelContractResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.invoker = invoker;
					respInfo.intArg1 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.cancelType = type;
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
	public void multiConfirmContract(String contractId, final ContractConfirmType type, String disUnitPrice, String disAmount,String finalAmount) {
		MultiConfirmContractReq req = new MultiConfirmContractReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "ConfirmReceiptResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = ContractMessageType.MSG_MULTI_COMFIRM_CONTRACT_SUCCESS;
					} else {
						message.what = ContractMessageType.MSG_MULTI_COMFIRM_CONTRACT_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.contractId = contractId;
		req.confirmType = type;
		req.disUnitPrice = disUnitPrice;
		req.disAmount = disAmount;
		req.amount=finalAmount;
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
						if (BeanUtils.isNotEmpty(result.datas)) {
							for (ToPayContractInfoModel info : result.datas) {
								// 解析货物详细规格名称
								String productName = SysCfgUtils.getProductFullName(mcontext, info.productCode, info.productSubCode, info.productSpecId);
								if (StringUtils.isNotEmpty(productName)) {
									info.productName = productName;
								}
							}
						}

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
