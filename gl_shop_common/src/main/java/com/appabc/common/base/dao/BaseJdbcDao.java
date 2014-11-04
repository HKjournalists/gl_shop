package com.appabc.common.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;

/**
 * @Description : BaseJdbcDao
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create Date : 2014年8月22日 下午6:47:55
 */
public abstract class BaseJdbcDao<T extends BaseBean> extends
		NamedParameterJdbcDaoSupport implements RowMapper<T>,
/* PreparedStatementCallback<T>, */IBaseDao<T> {

	protected LogUtil log = LogUtil.getLogUtil(this.getClass());

	@Resource
	public void setJb(JdbcTemplate jb) {
		super.setJdbcTemplate(jb);
	}

	public void addStandardSqlWithParameter(StringBuffer sql,
			String dbColumnName, Object value, List<Object> args) {
		this.addStandardSqlWithParameter(sql, dbColumnName, value, args, null);
	}
	
	public void addStandardSqlWithParameter(StringBuffer sql,
			String dbColumnName, Object value, List<Object> args,String sqlExpression){
		if (sql == null || StringUtils.isEmpty(dbColumnName) || value == null) {
			return;
		}
		if(StringUtils.isEmpty(sqlExpression)){
			sqlExpression = SQLExpressionEnum.EQ.getText();
		}
		boolean isNullValFlag = true;
		if (value != null && (value instanceof String ? StringUtils.isNotEmpty(String.valueOf(value)) : true)) {
			isNullValFlag = false;
		}
		if (!isNullValFlag) {
			sql.append(" AND ");
			sql.append(dbColumnName.trim());
			if(sqlExpression.equalsIgnoreCase(SQLExpressionEnum.LIKE.getText())){
				//City LIKE '%lon%'
				sql.append(" ");
				sql.append(SQLExpressionEnum.LIKE.getText());
				sql.append(" '%'|| ? ||'%' ");
			}else{
				sql.append(" ");
				sql.append(sqlExpression);
				sql.append(" ? ");
			}
			args.add(value);
		}
		log.info("addStandardSqWithParameter is : " + sql.toString());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.BaseJdbcDao#addNameParamerSqlWithProperty(
	 * java.lang.StringBuffer, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void addNameParamerSqlWithProperty(StringBuffer sql,
			String propertyName, String dbColumnName, Object value) {
		this.addNameParamerSqlWithProperty(sql, propertyName, dbColumnName, value, null);
	}
	
	public void addNameParamerSqlWithProperty(StringBuffer sql,
			String propertyName, String dbColumnName, Object value,String sqlExpression){
		if (value == null || sql == null || StringUtils.isEmpty(propertyName)
				|| StringUtils.isEmpty(dbColumnName)) {
			return;
		}
		if(StringUtils.isEmpty(sqlExpression)){
			sqlExpression = SQLExpressionEnum.EQ.getText();
		}
		boolean isNullValFlag = true;
		if (value.getClass() == String.class
				&& StringUtils.isNotEmpty(String.valueOf(value))) {
			isNullValFlag = false;
		} else if (value != null) {
			isNullValFlag = false;
		}
		if (!isNullValFlag) {
			sql.append(" AND ");
			sql.append(dbColumnName.trim());
			if(sqlExpression.equalsIgnoreCase(SQLExpressionEnum.LIKE.getText())){
				//City LIKE '%lon%'
				sql.append(" ");
				sql.append(SQLExpressionEnum.LIKE.getText());
				sql.append(" '%'|| :" + propertyName.trim()+" ||'%' ");
			}else{
				sql.append(" ");
				sql.append(sqlExpression);
				sql.append(" :" + propertyName.trim());
			}
		}
		log.info("addNameParamerSqlWithProperty is : " + sql.toString());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#create(java.lang.String,
	 * java.lang.Object)
	 */
	public void save(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the save to db object is null!");
		log.info("the sql str is : " + sql);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource,
				generatedKeyHolder);
		// 获取数据库的ID并回设
		if (generatedKeyHolder.getKey() != null) {
			entity.setId(String.valueOf(generatedKeyHolder.getKey().intValue()));
		}
		log.info(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(java.lang.String
	 * , com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the save to db object is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource,
				generatedKeyHolder);
		log.info(result);
		return generatedKeyHolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#update(java.lang.String,
	 * java.lang.Object)
	 */
	public void update(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the update object is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource);
		log.info(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.lang.String,
	 * java.lang.Object)
	 */
	public void delete(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the delete object is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource);
		log.info(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.lang.String,
	 * java.lang.String)
	 */
	public void delete(String sql, Serializable id) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(id, "the delete id is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramMap = new MapSqlParameterSource("id", id);
		int result = getNamedParameterJdbcTemplate().update(sql, paramMap);
		log.info(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.lang.String,
	 * java.lang.String)
	 */
	public T query(String sql, Serializable id) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(id, "the read id is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
		List<T> result = getNamedParameterJdbcTemplate().query(sql, paramSource, this);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}else{
			return null;
		}
		//return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource,this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.lang.String,
	 * java.lang.Object)
	 */
	public T query(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the read object is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		List<T> result = getNamedParameterJdbcTemplate().query(sql, paramSource, this);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}else{
			return null;
		}
		//return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource,this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.lang.String,
	 * java.lang.List)
	 */
	public T query(String sql, List<?> args){
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(args, "the read object is null!");
		log.info("the sql str is : " + sql);
		List<T> result = getJdbcTemplate().query(sql, args.toArray(), this);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}else{
			return null;
		}
		//return getJdbcTemplate().queryForObject(sql, args.toArray(), this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#readAll(java.lang.String,
	 * java.lang.Object)
	 */
	public List<T> queryForList(String sql, T entity) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notNull(entity, "the read object is null!");
		log.info("the sql str is : " + sql);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		return getNamedParameterJdbcTemplate().query(sql, paramSource, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.lang.String,
	 * java.util.Map)
	 */
	public List<T> queryForList(String sql, Map<String, ?> args) {
		Assert.hasText(sql, "the sql str is null!");
		Assert.notEmpty(args, "the read object is null!");
		log.info("the sql str is : " + sql);
		return getNamedParameterJdbcTemplate().query(sql, args, this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.lang.String,
	 * java.util.List)
	 */
	public List<T> queryForList(String sql,List<?> args){
		Assert.hasText(sql, "the sql str is null!");
		Assert.notEmpty(args, "the queryForList object is null!");
		log.info("the sql str is : " + sql);
		return getJdbcTemplate().query(sql, args.toArray(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPagination(java.lang.String
	 * , com.appabc.common.base.QueryContext)
	 */
	public QueryContext<T> queryListForPagination(String sql,
			QueryContext<T> qContext) {
		return this.queryListForPagination(sql, qContext, this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPagination(java.lang.String
	 * , com.appabc.common.base.QueryContext,RowMapper<T> rowMapper)
	 */
	public QueryContext<T> queryListForPagination(String sql,
			QueryContext<T> qContext,RowMapper<T> rowMapper){
		Assert.hasText(sql, "the sql str is null!");
		if (StringUtils.isNotEmpty(qContext.getOrderColumn())) {
			StringBuffer sb = new StringBuffer(sql);
			sb.append(" ORDER BY ");
			sb.append(qContext.getOrderColumn());
			sb.append(" ");
			sb.append(qContext.getOrder());
			sql = sb.toString();
		}
		log.info("the sql str before page is : " + sql);
		if (qContext.getPage().getPageIndex() < 0) {
			List<T> list = getNamedParameterJdbcTemplate().query(sql,
					qContext.getParameters(), rowMapper);
			qContext.getQueryResult().setResult(list);
			qContext.getQueryResult().setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler
					.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql);
			log.info("the count sql str is  : " + countSql);
			// 获取记录总数
			@SuppressWarnings("deprecation")
			int count = getNamedParameterJdbcTemplate().queryForInt(countSql,
					qContext.getParameters());
			qContext.getQueryResult().setTotalSize(count);
			qContext.getPage().setTotalSize(count);

			String pageSql = iSQLGenerator.generatePageSql(sql,
					qContext.getPage());
			log.info("the page sql str is  : " + pageSql);
			// 获取分页后的记录数量
			List<T> list = getNamedParameterJdbcTemplate().query(pageSql,
					qContext.getParameters(), rowMapper);
			qContext.getQueryResult().setResult(list);
		}
		return qContext;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPaginationForStandardSQL(java.lang.String
	 * , com.appabc.common.base.QueryContext)
	 */
	public QueryContext<T> queryListForPaginationForStandardSQL(String sql,
			QueryContext<T> qContext) {
		Assert.hasText(sql, "the sql str is null!");
		if (StringUtils.isNotEmpty(qContext.getOrderColumn())) {
			StringBuffer sb = new StringBuffer(sql);
			sb.append(" ORDER BY ");
			sb.append(qContext.getOrderColumn());
			sb.append(" ");
			sb.append(qContext.getOrder());
			sql = sb.toString();
		}
		log.info("the sql str before page is : " + sql);
		if (qContext.getPage().getPageIndex() < 0) {
			List<T> list = getJdbcTemplate().query(
					sql,
					CollectionUtils.isEmpty(qContext.getParamList()) ? null
							: qContext.getParamList().toArray(), this);
			qContext.getQueryResult().setResult(list);
			qContext.getQueryResult().setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler
					.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql);
			log.info("the count sql str is  : " + countSql);
			// 获取记录总数
			@SuppressWarnings("deprecation")
			int count = getJdbcTemplate().queryForInt(
					countSql,
					CollectionUtils.isEmpty(qContext.getParamList()) ? null
							: qContext.getParamList().toArray());
			qContext.getQueryResult().setTotalSize(count);
			qContext.getPage().setTotalSize(count);

			String pageSql = iSQLGenerator.generatePageSql(sql,
					qContext.getPage());
			log.info("the page sql str is  : " + pageSql);
			// 获取分页后的记录数量
			List<T> list = getJdbcTemplate().query(
					pageSql,
					CollectionUtils.isEmpty(qContext.getParamList()) ? null
							: qContext.getParamList().toArray(), this);
			qContext.getQueryResult().setResult(list);
		}
		return qContext;
	}

}
