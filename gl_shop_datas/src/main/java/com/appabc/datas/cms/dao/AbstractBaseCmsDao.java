package com.appabc.datas.cms.dao;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng
 * @version     : 1.0
 * Create Date  : Oct 21, 2014 11:44:23 AM
 */
public abstract class AbstractBaseCmsDao<T> extends NamedParameterJdbcDaoSupport {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public void setDs(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

    public abstract T get(Serializable id);

	public abstract boolean create(T entity);

	public abstract boolean update(T entity);

	public abstract boolean delete(T entity);

	public int countBy(Map<String, Object> params) {
		return 0;
	}

	public List<T> queryForList(Map<String, Object> params) {
		return queryForList(params, -1, -1);
	}

	public List<T> queryForList(Map<String, Object> params, int start, int count) {
		return queryForList(params, start, count, null);
	}

	public abstract List<T> queryForList(Map<String, Object> params, int start, int count, String[] orderBy);

	protected String generateSqlWithParams(final String[] fields, final String[] tables, Map<String, Object> params) {
		return generateSqlWithParams(fields, tables, params, -1, -1, null);
	}

	protected String generateSqlWithParams(final String[] fields, final String[] tables, Map<String, Object> params,
										int start, int count, String[] orderBy) {
		if (params == null) {
			params = new HashMap<>();
		}

    	StringBuilder sb = new StringBuilder();
    	sb.append("select ").append(StringUtils.join(fields, ", "))
    			.append(" from ").append(StringUtils.join(tables, ", "));

    	if (!params.isEmpty()) {
    		sb.append(" where ");
    		List<String> whereClause = new LinkedList<String>();
            for (String propName : params.keySet()) {
                whereClause.add(propName + "=:" + propName);
            }
            sb.append(StringUtils.join(whereClause, " and "));
    	}

    	if (!ArrayUtils.isEmpty(orderBy)) {
    		sb.append(" order by ");
    		sb.append(StringUtils.join(orderBy, ", "));
    	}

    	if (count > 0) {
    		sb.append(" limit :limit ");
    		params.put("limit", count);
    	}

    	if (start >= 0) {
            sb.append(" offset :offset");
            params.put("offset", start);
    	}

    	if (logger.isDebugEnabled()) {
    		logger.debug(sb.toString());
    	}

    	return sb.toString();
	}

}
