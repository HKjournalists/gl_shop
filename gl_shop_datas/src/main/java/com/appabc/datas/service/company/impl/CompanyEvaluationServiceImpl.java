package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

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
	private PrimaryKeyGenerator PKGenerator;
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
		Date now = new Date();
		TOrderInfo contract = IContractInfoDAO.query(bean.getOid());
		if(DateUtil.getDifferDayWithTwoDate(contract.getCreatime(), now)>3){
			throw new ServiceException("超出合同评价时间范围,系统已经自动评价完成");
		}
		TCompanyEvaluation entity = new TCompanyEvaluation();
		entity.setOid(bean.getOid());
		entity.setCreater(operator);
		List<TCompanyEvaluation> res = this.queryForList(entity);
		if(res!=null&&res.size()>0){
			throw new ServiceException("您已经评价过,不能重复评价");
		}
		contract.setLifecycle(ContractLifeCycle.NORMAL_FINISHED.getValue());
		contract.setStatus(ContractStatus.FINISHED.getValue());
		contract.setUpdater(operator);
		contract.setUpdatetime(now);
		IContractInfoDAO.update(contract);
		
		TOrderOperations operate = new TOrderOperations();
		operate.setId(PKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operate.setOid(bean.getOid());
		operate.setOperator(operator);
		operate.setOperationtime(now);
		operate.setType(ContractOperateType.EVALUATION_CONTRACT.getValue());
		operate.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED.getValue());
		StringBuffer result = new StringBuffer(operatorName);
		result.append(MessagesUtil.getMessage("CONTRACTEVALUATESUCCESSTIPS"));
		operate.setResult(result.toString());
		operate.setRemark(result.toString());
		IContractOperationDAO.save(operate);
		
		bean.setCratedate(now);
		String buyerId = contract.getBuyerid();
		String sellerId = contract.getSellerid();
		if (StringUtils.equalsIgnoreCase(buyerId, bean.getCid()) || StringUtils.equalsIgnoreCase(sellerId, bean.getCid())) {
			//bean.setCid(sellerId);
			this.add(bean);
		}else{
			throw new ServiceException("你不能评价当前合同");
		}
	}

}
