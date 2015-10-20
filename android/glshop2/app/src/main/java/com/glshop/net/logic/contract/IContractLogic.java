package com.glshop.net.logic.contract;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractConfirmType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 我的合同业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:14:33
 */
public interface IContractLogic extends ILogic {

	/**
	 * 获取我的合同列表
	 * @param type
	 */
	public void getContracts(ContractType type, int pageIndex, int pageSize, DataReqType reqType);

	/**
	 * 获取待确认的合同详情
	 * @param contractId
	 */
	public void getUncfmContractInfo(String contractId);

	/**
	 * 获取进行中和已结束的合同详情
	 * @param contractId
	 */
	public void getContractInfo(String invoker, String contractId, boolean isGetModel);

	/**
	 * 获取已结束的合同详情
	 * @param contractId
	 */
	public void getEndedContractInfo(String contractId);

	/**
	 * 获取合同模板详情
	 * @param contractId
	 */
	public void getContractModel(String contractId);

	/**
	 * 获取合同地址详情
	 */
	public void getContractAddrInfo(String buyId);

	/**
	 * 获取合同操作历史记录列表
	 * @param contractId
	 */
	public void getContractOprHistory(String contractId);

	/**
	 * 待确认合同的确认
	 * @param contractId
	 */
	public void agreeContractSign(String contractId);

	/**
	 * 合同线上支付
	 * @param contractId
	 * @param smsCode
	 * @param pwd
	 */
	public void payContractOnline(String contractId, String smsCode, String pwd);

	/**
	 * 合同线下支付
	 * @param contractId
	 */
	public void payContractOffline(String contractId);

	/**
	 * 合同验收或同意议价
	 */
	public void acceptanceContract(String contractId, int type);

	/**
	 * 提交合同议价
	 */
	public void contractNegotiate(NegotiateInfoModel info);

	/**
	 * 取消合同
	 */
	public void cancelContract(String contractId);

	/**
	 * 多取消合同
	 */
	public void multiCancelContract(String inovker, String contractId, ContractCancelType type);

	/**
	 * 同意取消合同
	 */
	public void agreeCancelContract(String contractId);

	/**
	 * 联系客服
	 */
	public void contactCustomService(String contractId);

	/**
	 * 确认卸货
	 */
	public void confirmDischarge(String contractId);

	/**
	 * 确认收货
	 */
	public void confirmReceipt(String contractId);

	/**
	 * 多确认操作(包括货款确认、确认同意、申请仲裁)
	 */
	public void multiConfirmContract(String contractId, ContractConfirmType type, String disUnitPrice, String disAmount,String finalAmount);

	/**
	 * 合同评价
	 */
	public void contractEvaluate(EvaluationInfoModel info);

	/**
	 * 获取待付货款的合同列表
	 * @param type
	 */
	public void getToPayContracts(DataReqType reqType);

	/**
	 * 获取合同评价列表
	 */
	public void getContractEvaluationList(String contractId);

	/**
	 * 获取企业评价列表
	 */
	public void getCompanyEvaluationList(String companyId, DataReqType reqType);

}
