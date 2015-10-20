package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.order.IOrderFindMatchDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.order.IOrderFindMatchService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.service.IPassPayService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年4月30日 下午5:27:52
 */

@Service
public class OrderFindMatchServiceImpl extends BaseService<TOrderFindMatch> implements IOrderFindMatchService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderFindMatchDAO iOrderFindMatchDAO;
	
	@Autowired
	private IOrderFindService orderFindService;
	
	@Autowired
	private IPassPayService iPassPayService;
	
	/**
	 * @Description : 获取配置消息根据CODE
	 * @param code
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		return this.getMessage(code, "datas");
	}
	
	/**
	 * @Description : 获取配置消息根据CODE和local
	 * @param code;localTag
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code,String localTag){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		if(StringUtils.isNotEmpty(localTag)){
			return MessagesUtil.getMessage(code, Locale.forLanguageTag(localTag));
		} else {
			return MessagesUtil.getMessage(code, Locale.forLanguageTag("datas"));
		}
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TOrderFindMatch entity) {
		logger.info("save the TOrderFindMatch info: "+entity.getTitle());
		iOrderFindMatchDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TOrderFindMatch entity) {
		iOrderFindMatchDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TOrderFindMatch entity) {
		iOrderFindMatchDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iOrderFindMatchDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TOrderFindMatch query(TOrderFindMatch entity) {
		return iOrderFindMatchDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TOrderFindMatch query(Serializable id) {
		return iOrderFindMatchDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TOrderFindMatch> queryForList(TOrderFindMatch entity) {
		return iOrderFindMatchDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TOrderFindMatch> queryForList(Map<String, ?> args) {
		return iOrderFindMatchDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderFindMatch> queryListForPagination(
			QueryContext<TOrderFindMatch> qContext) {
		return iOrderFindMatchDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#findOrderFindMatchInfo(com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)  
	 */
	@Override
	public List<TOrderFindMatch> findOrderFindMatchInfo(String owner,OrderFindMatchStatusEnum status) {
		if(StringUtils.isEmpty(owner) || status == null){
			return null;
		}
		return iOrderFindMatchDAO.queryOrderFindMatchInfoWithStatus(owner,status);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#saveOrderFindMatchInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum, java.lang.String)  
	 */
	@Override
	public TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,
			String target, OrderFindMatchOpTypeEnum opType, String operator)
			throws ServiceException {
		return this.saveOrderFindMatchInfo(bean, target, opType, OrderFindMatchStatusEnum.SAVE, operator);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#saveOrderFindMatchInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum, java.lang.String)  
	 */
	@Override
	public TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,
			String target, OrderFindMatchOpTypeEnum opType,
			OrderFindMatchStatusEnum status, String operator)
			throws ServiceException {
		return this.saveOrderFindMatchInfo(bean, target, opType, status, operator,StringUtils.EMPTY);
	}
	

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#saveOrderFindMatchInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum, java.lang.String, java.lang.String[])  
	 */
	@Override
	public TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,
			String target, OrderFindMatchOpTypeEnum opType, String operator,
			String... strs) throws ServiceException {
		return this.saveOrderFindMatchInfo(bean, target, opType, OrderFindMatchStatusEnum.SAVE, operator, strs);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#saveOrUpdateFindMatchInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum, java.lang.String, java.lang.String[])  
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateFindMatchInfo(OrderFindAllBean bean,TOrderFindMatch tofm) throws ServiceException {
		if(tofm == null || bean == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderFind orderFind = bean.getOfBean();
		if(orderFind == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//这里需要去检查用户的认证信息,是否已经认证.
		/*AuthRecordStatus cArs = this.iCompanyInfoService.getAuthStatusByCid(cid);
		if(cArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		AuthRecordStatus fcArs = this.iCompanyInfoService.getAuthStatusByCid(orderFind.getCid());
		if(fcArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}*/
		if(bean.getOfBean().getMorearea() == OrderMoreAreaEnum.ORDER_MORE_AREA_YES){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_MORE_AREA_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_IS_PARENT_ORDER));
		}
		if(StringUtils.equalsIgnoreCase(tofm.getTarget(), orderFind.getCid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_SELLER_BUYER_IS_SAME_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTACT_SELLER_BUYER_IS_SAME_ERROR));
		}
		if(StringUtils.isEmpty(tofm.getId())){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//orderFindService.updateOrderAllInfo(bean.getOfBean(), bean.getOpiBean(), bean.getPpcList(), bean.getOaBean() == null ? StringUtils.EMPTY : bean.getOaBean().getId());
		orderFindService.updateOrderAllInfo(bean);
		modify(tofm);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#saveOrderFindMatchInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum, java.lang.String, java.lang.String[])  
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,
			String target, OrderFindMatchOpTypeEnum opType,
			OrderFindMatchStatusEnum status, String operator, String... strs)
			throws ServiceException {
		if(StringUtils.isEmpty(target) || opType == null || status == null || bean == null || StringUtils.isEmpty(operator)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderFind orderFind = bean.getOfBean();
		if(orderFind == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//这里需要去检查用户的认证信息,是否已经认证.
		/*AuthRecordStatus cArs = this.iCompanyInfoService.getAuthStatusByCid(cid);
		if(cArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		AuthRecordStatus fcArs = this.iCompanyInfoService.getAuthStatusByCid(orderFind.getCid());
		if(fcArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}*/
		if(bean.getOfBean().getMorearea() == OrderMoreAreaEnum.ORDER_MORE_AREA_YES){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_MORE_AREA_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_IS_PARENT_ORDER));
		}
		if(StringUtils.equalsIgnoreCase(target, orderFind.getCid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_SELLER_BUYER_IS_SAME_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTACT_SELLER_BUYER_IS_SAME_ERROR));
		}
		String parentId = orderFind.getId();
		Float guaranty = iPassPayService.getGuarantyTotal(target);
		//将询单拷贝一份草稿.
		String childId = orderFindService.contractMatchingOfOrderFindSaveDraft(bean);
		
		TOrderFindMatch t = new TOrderFindMatch();
		t.setOwner(orderFind.getCid());
		t.setTarget(target);
		t.setGuaranty(guaranty.doubleValue());
		t.setOpType(opType);
		t.setStatus(status);
		t.setOpfid(parentId);
		t.setOcfid(childId);
		if(strs != null && strs.length == 1){
			t.setTitle(strs[0]);
		} else if (strs != null && strs.length == 2){
			t.setTitle(strs[0]);
			t.setTfid(strs[1]);
		} else if (strs != null && strs.length == 3){
			t.setTitle(strs[0]);
			t.setTfid(strs[1]);
			t.setRemark(strs[2]);
		}
		add(t);
		return t;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#rollbackOrderFindMatchInfoByFid(java.lang.String)  
	 */
	@Override
	public void rollbackOrderFindMatchInfoByFid(String parentFid)
			throws ServiceException {
		if(StringUtils.isEmpty(parentFid)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//
		TOrderFindMatch entity = new TOrderFindMatch();
		entity.setOpfid(parentFid);
		List<TOrderFindMatch> rs = iOrderFindMatchDAO.queryForList(entity);
		for(TOrderFindMatch bean : rs){
			bean.setStatus(OrderFindMatchStatusEnum.SAVE);
			iOrderFindMatchDAO.update(bean);
		}
		//iOrderFindMatchDAO.updateOrderFindMatchStatusByPFid(parentFid, OrderFindMatchStatusEnum.CREATION);
		//
		orderFindService.recoverAllDraftsByParentid(parentFid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#rollbackOrderFindMatchInfoByRid(java.lang.String)  
	 */
	@Override
	public void rollbackOrderFindMatchInfoByRid(String rid)
			throws ServiceException {
		if(StringUtils.isEmpty(rid)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//
		TOrderFindMatch bean = this.query(rid);
		//
		bean.setStatus(OrderFindMatchStatusEnum.SAVE);
		iOrderFindMatchDAO.update(bean);
		//iOrderFindMatchDAO.updateOrderFindMatchStatusByRid(rid, OrderFindMatchStatusEnum.CREATION);
		//
		orderFindService.recoverDraftsById(bean.getOcfid());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#vitiateOrderFindMatchInfoByFid(java.lang.String)  
	 */
	@Override
	public void vitiateOrderFindMatchInfoByFid(String parentFid)
			throws ServiceException {
		if(StringUtils.isEmpty(parentFid)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//
		TOrderFindMatch entity = new TOrderFindMatch();
		entity.setOpfid(parentFid);
		List<TOrderFindMatch> rs = iOrderFindMatchDAO.queryForList(entity);
		for(TOrderFindMatch bean : rs){
			bean.setStatus(OrderFindMatchStatusEnum.SUCCESS);
			iOrderFindMatchDAO.update(bean);
		}
		//
		//iOrderFindMatchDAO.updateOrderFindMatchStatusByPFid(parentFid,OrderFindMatchStatusEnum.SUCCESS);
		//
		orderFindService.cancelAllDraftsByParentid(parentFid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#vitiateOrderFindMatchInfoByRid(java.lang.String)  
	 */
	@Override
	public void vitiateOrderFindMatchInfoByRid(String rid)
			throws ServiceException {
		if(StringUtils.isEmpty(rid)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//
		TOrderFindMatch bean = this.query(rid);
		bean.setStatus(OrderFindMatchStatusEnum.SUCCESS);
		iOrderFindMatchDAO.update(bean);
		//
		//iOrderFindMatchDAO.updateOrderFindMatchStatusByRid(rid, OrderFindMatchStatusEnum.SUCCESS);
		//
		orderFindService.cancelDraftsById(bean.getOcfid());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#cancelOrderFindMatchInfoByRid(java.lang.String)  
	 */
	@Override
	public void cancelOrderFindMatchInfoByRid(String rid)
			throws ServiceException {
		if(StringUtils.isEmpty(rid)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderFindMatch bean = this.query(rid);
		bean.setStatus(OrderFindMatchStatusEnum.CANCEL);
		this.modify(bean);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#hasOrderFindMatchInfoWithParentFid(java.lang.String)  
	 */
	@Override
	public boolean hasOrderFindMatchInfoWithParentFid(String parentFid) {
		if(StringUtils.isEmpty(parentFid)){
			return false;
		}
		TOrderFindMatch entity = new TOrderFindMatch();
		entity.setOpfid(parentFid);
		List<TOrderFindMatch> rs = iOrderFindMatchDAO.queryForList(entity);
		if(CollectionUtils.isEmpty(rs)){
			return false;
		} else {			
			return true;
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#rollbackOrderFindInfoWithContract(java.lang.String)  
	 */
	@Override
	public void rollbackOrderFindInfoWithContract(String contractFid)
			throws ServiceException {
		if(StringUtils.isEmpty(contractFid)){
			return ;
		}
		//通过合同记录的询单ID找到父询单，看看是否有保存记录信息，直接回滚.
		String parentFid = orderFindService.getParentFidByChildFid(contractFid);
		boolean b = hasOrderFindMatchInfoWithParentFid(parentFid);
		if(b){
			rollbackOrderFindMatchInfoByFid(parentFid);
		}
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#findOrderFindMatchExInfo(java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)  
	 */
	@Override
	public List<TOrderFindMatchEx> findOrderFindMatchExInfo(String owner,
			OrderFindMatchStatusEnum status) {
		if(StringUtils.isEmpty(owner) || status == null){
			return null;
		}
		return iOrderFindMatchDAO.queryOrderFindMatchExInfoWithStatus(owner, status);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindMatchService#findOrderFindMatchExInfoForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderFindMatchEx> findOrderFindMatchExInfoForPagination(QueryContext<TOrderFindMatchEx> qContext) {
		if(qContext == null){
			return null;
		}
		return iOrderFindMatchDAO.findOrderFindMatchExInfoForPagination(qContext);
	}

}
