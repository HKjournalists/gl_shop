package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractMineDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月24日 下午6:21:07
 */

@Service(value="IContractMineService")
public class ContractMineServiceImpl extends ContractBaseService<TOrderMine> implements
		IContractMineService {

	@Autowired
	private IContractMineDAO iContractMineDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TOrderMine entity) {
		iContractMineDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TOrderMine entity) {
		iContractMineDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TOrderMine entity) {
		iContractMineDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iContractMineDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TOrderMine query(TOrderMine entity) {
		return iContractMineDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TOrderMine query(Serializable id) {
		return iContractMineDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TOrderMine> queryForList(TOrderMine entity) {
		return iContractMineDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TOrderMine> queryForList(Map<String, ?> args) {
		return iContractMineDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderMine> queryListForPagination(
			QueryContext<TOrderMine> qContext) {
		return iContractMineDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractMineService#queryOrderMineWithCidOid(java.lang.String, java.lang.String)  
	 */
	@Override
	public TOrderMine queryOrderMineWithCidOid(String oid, String cid) {
		return iContractMineDAO.queryOrderMineWithCidOid(oid, cid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractMineService#updateMineContractWithCid(java.lang.String, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractStatus, com.appabc.bean.enums.ContractInfo.ContractLifeCycle, java.lang.String)  
	 */
	@Override
	public boolean saveOrUpdateMineContractWithCidOid(String oid, String cid,
			ContractStatus status, ContractLifeCycle lifeCycle, String operator)
			throws ServiceException {
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(oid) || status == null || lifeCycle == null || StringUtils.isEmpty(operator)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		return iContractMineDAO.saveOrUpdateMineContractWithCidOid(oid, cid, status, lifeCycle, operator);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractMineService#jobAutoSetupContractExpireDayFinish(com.appabc.bean.pvo.TOrderInfo)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoSetupContractExpireDayFinish(TOrderInfo bean,String cid)
			throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid)){
			return ;
		}
		TOrderMine buyMineBean = queryOrderMineWithCidOid(bean.getId(), bean.getBuyerid());
		if((buyMineBean != null && buyMineBean.getStatus() == ContractStatus.FINISHED) || StringUtils.equalsIgnoreCase(ToolsConstant.SYSTEMCID, buyMineBean.getUpdater())){
			return ;
		}
		TOrderMine sellMineBean = queryOrderMineWithCidOid(bean.getId(), bean.getSellerid());
		if((sellMineBean != null && sellMineBean.getStatus() == ContractStatus.FINISHED) || StringUtils.equalsIgnoreCase(ToolsConstant.SYSTEMCID, sellMineBean.getUpdater())){
			return ;
		}
		StringBuilder sbm = new StringBuilder(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_OUT_OF_LIMITTIMETIPS, Locale.forLanguageTag("datas")));
		saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.FINISHED, bean.getLifecycle(), cid);
		saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.FINISHED, bean.getLifecycle(), cid);
		log.info(" Moved the Contract id is : "+bean.getId()+" and the Contract Life Cycle is : "+bean.getLifecycle()+" and the Contract status is : "+ContractStatus.FINISHED+" to the Contract Expire Day List. ");
		//send the system message and xmpp message
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_OTHERS, bean.getId(), bean.getSellerid(), new SystemMessageContent(sbm.toString()));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_OTHERS, bean.getId(), bean.getBuyerid(), new SystemMessageContent(sbm.toString()));
	}

}
