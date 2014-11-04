/**
 *
 */
package com.appabc.datas.service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TQtQuestions;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.system.IQtQuestionsDao;
import com.appabc.datas.service.system.IQtQuestionsService;

/**
 * @Description : 常见问题
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午5:01:56
 */
@Service
public class QtQuestionsServiceImpl implements IQtQuestionsService{
	
	@Autowired
	private IQtQuestionsDao qtQuestionsDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TQtQuestions entity) {
		qtQuestionsDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TQtQuestions entity) {
		qtQuestionsDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TQtQuestions entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		qtQuestionsDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TQtQuestions query(TQtQuestions entity) {
		return qtQuestionsDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TQtQuestions query(Serializable id) {
		return qtQuestionsDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TQtQuestions> queryForList(TQtQuestions entity) {
		return qtQuestionsDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TQtQuestions> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TQtQuestions> queryListForPagination(
			QueryContext<TQtQuestions> qContext) {
		return null;
	}

}
