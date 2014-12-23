/**
 *
 */
package com.appabc.datas.service.codes.impl;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.codes.IPublicCodesDao;
import com.appabc.datas.service.codes.IPublicCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 公共代码SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午2:39:50
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PublicCodesServiceImpl implements IPublicCodesService{
	
	@Autowired
	private IPublicCodesDao publicCodesDao;

	public void add(TPublicCodes entity) {
		this.publicCodesDao.save(entity);
	}

	public void modify(TPublicCodes entity) {
		this.publicCodesDao.update(entity);
	}

	public void delete(TPublicCodes entity) {
		this.publicCodesDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.publicCodesDao.delete(id);
	}

	public TPublicCodes query(TPublicCodes entity) {
		return this.publicCodesDao.query(entity);
	}

	public TPublicCodes query(Serializable id) {
		return this.publicCodesDao.query(id);
	}

	public List<TPublicCodes> queryForList(TPublicCodes entity) {
		return this.publicCodesDao.queryForList(entity);
	}

	public List<TPublicCodes> queryForList(Map<String, ?> args) {
		return this.publicCodesDao.queryForList(args);
	}

	public QueryContext<TPublicCodes> queryListForPagination(
			QueryContext<TPublicCodes> qContext) {
		return this.publicCodesDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)根据CODE查询
	 * @see com.appabc.datas.service.codes.IPublicCodesService#queryListByCode(java.lang.String)
	 */
	public List<TPublicCodes> queryListByCode(String code) {
		TPublicCodes entity = new TPublicCodes();
		entity.setCode(code);
		return this.publicCodesDao.queryForList(entity);
	}

}
