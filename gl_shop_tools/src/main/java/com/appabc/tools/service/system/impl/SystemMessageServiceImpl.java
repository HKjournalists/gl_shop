package com.appabc.tools.service.system.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.bo.SystemMessageEx;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.tools.dao.system.ISystemMessageDAO;
import com.appabc.tools.service.system.ISystemMessageService;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月22日 上午10:56:31
 */

@Service(value = "ISystemMessageService")
public class SystemMessageServiceImpl extends BaseService<TSystemMessage>
		implements ISystemMessageService {

	@Autowired
	private ISystemMessageDAO iSystemMessageDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TSystemMessage entity) {
		entity.setCreatetime(Calendar.getInstance().getTime());
		iSystemMessageDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TSystemMessage entity) {
		iSystemMessageDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TSystemMessage entity) {
		iSystemMessageDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iSystemMessageDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TSystemMessage query(TSystemMessage entity) {
		return iSystemMessageDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TSystemMessage query(Serializable id) {
		return iSystemMessageDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TSystemMessage> queryForList(TSystemMessage entity) {
		return iSystemMessageDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TSystemMessage> queryForList(Map<String, ?> args) {
		return iSystemMessageDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TSystemMessage> queryListForPagination(
			QueryContext<TSystemMessage> qContext) {
		return iSystemMessageDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)获取企业未读消息的数
	 * @see com.appabc.tools.service.system.ISystemMessageService#getUnreadMsgCountByCid(java.lang.String)
	 */
	@Override
	public int getUnreadMsgCountByCid(String cid) {
		TSystemMessage entity = new TSystemMessage();
		entity.setQyid(cid);
		entity.setStatus(MsgStatus.STATUS_IS_READ_NO);
		return this.iSystemMessageDAO.getCountByEntity(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.tools.service.system.ISystemMessageService#queryMessageExListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<SystemMessageEx> queryMessageExListForPagination(
			QueryContext<SystemMessageEx> qContext) {
		return iSystemMessageDAO.queryMessageExListForPagination(qContext);
	}

	@Override
	public QueryContext<TSystemMessage> queryListByTypeForPagination(
			QueryContext<TSystemMessage> qContext) {
		// TODO Auto-generated method stub
		return iSystemMessageDAO.queryListByTypeForPagination(qContext);
	}

}
