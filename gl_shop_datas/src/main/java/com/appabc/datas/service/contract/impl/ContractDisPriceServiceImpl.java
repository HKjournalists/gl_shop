package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.dao.contract.IContractDisPriceDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.enums.ContractInfo.ContractDisPriceType;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:45:55
 */

@Service
public class ContractDisPriceServiceImpl extends BaseService<TOrderDisPrice>
		implements IContractDisPriceService {

	@Autowired
	private IContractDisPriceDAO iContractDisPriceDAO;

	@Autowired
	private IContractOperationDAO iContractOperationDAO;

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderDisPrice entity) {
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTDISPRICEID"));
		iContractDisPriceDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderDisPrice entity) {
		iContractDisPriceDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderDisPrice entity) {
		iContractDisPriceDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractDisPriceDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderDisPrice query(TOrderDisPrice entity) {
		return iContractDisPriceDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderDisPrice query(Serializable id) {
		return iContractDisPriceDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderDisPrice> queryForList(TOrderDisPrice entity) {
		return iContractDisPriceDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderDisPrice> queryForList(Map<String, ?> args) {
		return iContractDisPriceDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderDisPrice> queryListForPagination(
			QueryContext<TOrderDisPrice> qContext) {
		return iContractDisPriceDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#
	 * validateGoodsDisPrice(com.appabc.bean.pvo.TOrderDisPrice)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void validateGoodsDisPrice(String contractId, String operatorName,TOrderDisPrice bean) {
		TOrderInfo contractInfo = iContractInfoDAO.query(contractId);
		contractInfo.setStatus(ContractStatus.DOING.getValue());
		if (contractInfo.getLifecycle().equalsIgnoreCase(
				ContractLifeCycle.PAYED_FUNDS.getValue())
				|| StringUtils.equalsIgnoreCase(contractInfo.getLifecycle(),
						ContractLifeCycle.SENT_GOODS.getValue())
				|| StringUtils.equalsIgnoreCase(contractInfo.getLifecycle(),
						ContractLifeCycle.SIMPLE_CHECKING.getValue())){
			contractInfo.setLifecycle(ContractLifeCycle.SIMPLE_CHECKING.getValue());
			bean.setType(RandomUtil.str2int(ContractDisPriceType.SAMPLE_CHECK.getValue()));
		}else if(contractInfo.getLifecycle().equalsIgnoreCase(ContractLifeCycle.SIMPLE_CHECKED.getValue()) || contractInfo.getLifecycle().equalsIgnoreCase(ContractLifeCycle.FULL_TAKEOVERING.getValue())){
			contractInfo.setLifecycle(ContractLifeCycle.FULL_TAKEOVERING.getValue());
			bean.setType(RandomUtil.str2int(ContractDisPriceType.FULL_TAKEOVER.getValue()));
		}
		iContractInfoDAO.update(contractInfo);
		// step 1 : make sure the begin amount and number
		List<TOrderDisPrice> result = iContractDisPriceDAO
				.queryForList(contractId);
		if (result != null && result.size() > 0) {
			TOrderDisPrice price = result.get(result.size() - 1);
			bean.setBeginamount(price.getEndamount());
			bean.setBeginnum(price.getEndnum());
		} else {
			TOrderInfo o = iContractInfoDAO.query(contractId);
			bean.setBeginamount(o.getPrice());
			bean.setBeginnum(o.getTotalnum());
		}
		// step 2 : save the record to operation table
		TOrderOperations operationBean = new TOrderOperations();
		operationBean.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operationBean.setOid(contractId);
		operationBean.setOperator(bean.getCanceler());
		operationBean.setOperationtime(bean.getCanceltime());
		operationBean.setType(ContractOperateType.DIS_PRICE.getValue());
		operationBean.setOrderstatus(contractInfo.getLifecycle());
		StringBuffer sb = new StringBuffer(operatorName);
		sb.append(MessagesUtil.getMessage("CONTRACT_DIS_PRICE_TIPS"));
		operationBean.setResult(sb.toString());
		operationBean.setRemark(sb.toString());
		operationBean.setPlid(bean.getLid());
		iContractOperationDAO.save(operationBean);
		// step 3 : save the record to DisPrice table
		bean.setLid(operationBean.getId());
		bean.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTDISPRICEID"));
		iContractDisPriceDAO.save(bean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#
	 * getGoodsDisPriceInfo(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public List<TContractDisPriceOperation> getGoodsDisPriceHisList(String contractId,
			String operateId, String disPriceId,String disPriceType) {
		return iContractDisPriceDAO.queryGoodsDisPriceHisList(contractId, operateId, disPriceId, disPriceType);
	}

}
