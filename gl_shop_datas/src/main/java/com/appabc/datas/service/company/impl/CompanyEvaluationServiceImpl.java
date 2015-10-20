package com.appabc.datas.service.company.impl;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.company.ICompanyEvaluationDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.PrimaryKeyGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create Date : 2014年9月11日 下午8:18:15
 */

@Service(value="ICompanyEvaluationService")
public class CompanyEvaluationServiceImpl extends
		BaseService<TCompanyEvaluation> implements ICompanyEvaluationService {

	@Autowired
	private ICompanyEvaluationDAO ICompanyEvaluationDAO;

	@Autowired
	private IContractOperationDAO IContractOperationDAO;

	@Autowired
	private IContractInfoDAO IContractInfoDAO;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;

	@Autowired
	private PrimaryKeyGenerator PKGenerator;
	
	private String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return PKGenerator.getPKey(bid);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TCompanyEvaluation entity) {
		ICompanyEvaluationDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TCompanyEvaluation entity) {
		ICompanyEvaluationDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TCompanyEvaluation entity) {
		ICompanyEvaluationDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		ICompanyEvaluationDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TCompanyEvaluation query(TCompanyEvaluation entity) {
		return ICompanyEvaluationDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TCompanyEvaluation query(Serializable id) {
		return ICompanyEvaluationDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TCompanyEvaluation> queryForList(TCompanyEvaluation entity) {
		return ICompanyEvaluationDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TCompanyEvaluation> queryForList(Map<String, ?> args) {
		return ICompanyEvaluationDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TCompanyEvaluation> queryListForPagination(
			QueryContext<TCompanyEvaluation> qContext) {
		return ICompanyEvaluationDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.datas.service.company.ICompanyEvaluationService#toEvaluateContract
	 * (java.lang.String)
	 */
	public void toEvaluateContract(String operator,String operatorName,
			TCompanyEvaluation bean) throws ServiceException{
		if(StringUtils.isEmpty(operator) || StringUtils.isEmpty(operatorName) || bean == null){
			String msg = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,msg);
		}
		TOrderInfo contract = IContractInfoDAO.query(bean.getOid());
		if(contract == null){
			String msg = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, msg);
		}
		boolean isNotLifeCycle = contract.getStatus() == ContractStatus.FINISHED && contract.getLifecycle() == ContractLifeCycle.NORMAL_FINISHED;
		if(!isNotLifeCycle){
			String msg = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, msg);
		}
		String buyerId = contract.getBuyerid();
		String sellerId = contract.getSellerid();
		if (!StringUtils.equalsIgnoreCase(buyerId, bean.getCid()) && !StringUtils.equalsIgnoreCase(sellerId, bean.getCid())) {
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR,"你不能评价当前合同");
		}
		Date now = DateUtil.getNowDate();
		int hours = DateUtil.getDifferHoursWithTwoDate(contract.getUpdatetime(), now);
		if(hours>ContractCostDetailUtil.getContractEvaluatioinLimitNum()){
			throw new ServiceException(ServiceErrorCode.CONTRACT_TIME_OUT_EVALUATE_ERROR,"超出合同评价时间范围,系统已经自动评价完成");
		}
		TCompanyEvaluation entity = new TCompanyEvaluation();
		entity.setOid(bean.getOid());
		entity.setCreater(operator);
		List<TCompanyEvaluation> res = this.queryForList(entity);
		if(CollectionUtils.isNotEmpty(res)){
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN,"您已经评价过,不能重复评价");
		}
		/*contract.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		contract.setStatus(ContractStatus.FINISHED);
		contract.setUpdater(operator);
		contract.setUpdatetime(now);
		IContractInfoDAO.update(contract);*/

		TOrderOperations operate = new TOrderOperations();
		operate.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operate.setOid(bean.getOid());
		operate.setOperator(operator);
		operate.setOperationtime(now);
		operate.setType(ContractOperateType.EVALUATION_CONTRACT);
		operate.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED);
		operate.setOldstatus(contract.getLifecycle());
		StringBuffer result = new StringBuffer(operatorName);
		result.append(MessagesUtil.getMessage("CONTRACTEVALUATESUCCESSTIPS"));
		operate.setResult(result.toString());
		operate.setRemark(result.toString());
		IContractOperationDAO.save(operate);

		bean.setCratedate(now);
		this.add(bean);
		log.info("The Company Evaluation is : ");
		log.info(bean);
		
		//计算评价相关的交易满意度,交易诚信度
		iCompanyRankingService.calculateTradeEvaluationRate(bean.getCid());
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyEvaluationService#getEvaluationContractList(com.appabc.bean.bo.CompanyEvaluationInfo)
	 */
	@Override
	public List<CompanyEvaluationInfo> getEvaluationContractList(
			CompanyEvaluationInfo cei) {
		return ICompanyEvaluationDAO.queryEvaluationContractList(cei);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyEvaluationService#queryEvaluationListByCompany(com.appabc.bean.bo.CompanyEvaluationInfo)
	 */
	@Override
	public List<CompanyEvaluationInfo> queryEvaluationListByCompany(
			CompanyEvaluationInfo cei) {
		return ICompanyEvaluationDAO.queryEvaluationListByCompany(cei);
	}

}
