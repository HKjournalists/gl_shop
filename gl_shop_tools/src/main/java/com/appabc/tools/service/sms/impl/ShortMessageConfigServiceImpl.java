/**
 *
 */
package com.appabc.tools.service.sms.impl;

import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.common.base.QueryContext;
import com.appabc.tools.dao.sms.IShortMessageConfigDao;
import com.appabc.tools.service.sms.IShortMessageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 短信账号配置信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月4日 下午6:42:27
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ShortMessageConfigServiceImpl implements IShortMessageConfigService{
	
	@Autowired
	private IShortMessageConfigDao shortMessageConfigDao;

	public void add(TShortMessageConfig entity) {
		this.shortMessageConfigDao.save(entity);
	}

	public void modify(TShortMessageConfig entity) {
		this.shortMessageConfigDao.update(entity);
	}

	public void delete(TShortMessageConfig entity) {
		this.shortMessageConfigDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.shortMessageConfigDao.delete(id);
	}

	public TShortMessageConfig query(TShortMessageConfig entity) {
		return this.shortMessageConfigDao.query(entity);
	}

	public TShortMessageConfig query(Serializable id) {
		return this.shortMessageConfigDao.query(id);
	}

	public List<TShortMessageConfig> queryForList(TShortMessageConfig entity) {
		return this.shortMessageConfigDao.queryForList(entity);
	}

	public List<TShortMessageConfig> queryForList(Map<String, ?> args) {
		return this.shortMessageConfigDao.queryForList(args);
	}

	public QueryContext<TShortMessageConfig> queryListForPagination(
			QueryContext<TShortMessageConfig> qContext) {
		return this.shortMessageConfigDao.queryListForPagination(qContext);
	}

}
