package com.appabc.common.base.dao;

import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	public void addStandardSqlWithParameter(StringBuilder sql,
			String dbColumnName, Object value, List<Object> args) {
		this.addStandardSqlWithParameter(sql, dbColumnName, value, args, null);
	}

	public void addStandardSqlWithParameter(StringBuilder sql,
			String dbColumnName, Object value, List<Object> args,String sqlExpression){
		if (sql == null || StringUtils.isEmpty(dbColumnName) || value == null) {
			return;
		}
		if(StringUtils.isEmpty(sqlExpression)){
			sqlExpression = SQLExpressionEnum.EQ.getText();
		}
		boolean isNullValFlag = true;
		if (value instanceof String ? StringUtils.isNotEmpty(String.valueOf(value)) : true) {
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
		log.debug("addStandardSqWithParameter is : " + sql.toString());
	}
	
	public void addStandardSqlWithDateQuery(StringBuilder sql,String dbColumnName,Date start,Date end){
		if (sql == null || StringUtils.isEmpty(dbColumnName) || start == null || end == null) {
			return;
		}
		ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
		String whereSqlCause = iSQLGenerator.convertDateToWhereCauseSql(dbColumnName, start, end);
		sql.append(" AND ");
		sql.append(whereSqlCause);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.BaseJdbcDao#addNameParamerSqlWithProperty(
	 * java.lang.StringBuffer, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void addNameParamerSqlWithProperty(StringBuilder sql,
			String propertyName, String dbColumnName, Object value) {
		this.addNameParamerSqlWithProperty(sql, propertyName, dbColumnName, value, null);
	}

	public void addNameParamerSqlWithProperty(StringBuilder sql,
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
		log.debug("addNameParamerSqlWithProperty is : " + sql.toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#create(java.lang.String,
	 * java.lang.Object)
	 */
	public void save(String sql, T entity) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Save To DB Object Is Null!");
		log.debug("The Sql Str Is : " + sql+ " ; And The Value Is : "+entity);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource,
				generatedKeyHolder);
		// 获取数据库的ID并回设
		if (generatedKeyHolder.getKey() != null) {
			entity.setId(String.valueOf(generatedKeyHolder.getKey().intValue()));
		}
		log.debug(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(java.lang.String
	 * , com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(String sql, T entity) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Save To DB Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+entity);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(
				entity);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource,
				generatedKeyHolder);
		log.debug(result);
		return generatedKeyHolder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#update(java.lang.String,
	 * java.lang.Object)
	 */
	public void update(String sql, T entity) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Update Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+entity);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource);
		log.debug(result);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#update(java.lang.String,
	 * java.util.Map)
	 */
	public void update(String sql, Map<String,?> args) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(args, "The Update Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+args);
		int result = getNamedParameterJdbcTemplate().update(sql, args);
		log.debug(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.lang.String,
	 * java.lang.Object)
	 */
	public void delete(String sql, T entity) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Delete Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+entity);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(
				entity);
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource);
		log.debug(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.lang.String,
	 * java.lang.String)
	 */
	public void delete(String sql, Serializable id) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(id, "The Delete Id Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+id);
		SqlParameterSource paramMap = new MapSqlParameterSource("id", id);
		int result = getNamedParameterJdbcTemplate().update(sql, paramMap);
		log.debug(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.lang.String,
	 * java.lang.String)
	 */
	public T query(String sql, Serializable id) {
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(id, "The Read Id Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+id);
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
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Read Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+entity);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
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
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(args, "The Read Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+args);
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
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notNull(entity, "The Read Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+entity);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(
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
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notEmpty(args, "The Read Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+args);
		return getNamedParameterJdbcTemplate().query(sql, args, this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.lang.String,
	 * java.util.List)
	 */
	public List<T> queryForList(String sql,List<?> args){
		Assert.hasText(sql, "The Sql Str Is Null!");
		Assert.notEmpty(args, "The QueryForList Object Is Null!");
		log.debug("The Sql Str Is : " + sql + " ; And The Value Is : "+args);
		return getJdbcTemplate().query(sql, args.toArray(), this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPagination(java.lang.String
	 * , com.appabc.common.base.QueryContext)
	 */
	public QueryContext<T> queryListForPagination(String sql,QueryContext<T> qContext) {
		return this.queryListForPagination(sql, null, qContext, this);
	}
	
	public QueryContext<T> queryListForPagination(String sql, String countSql, QueryContext<T> qContext) {
		return this.queryListForPagination(sql, countSql, qContext, this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPagination(java.lang.String
	 * , com.appabc.common.base.QueryContext,RowMapper<T> rowMapper)
	 */
	public QueryContext<T> queryListForPagination(String sql,String countSql, QueryContext<T> qContext,RowMapper<T> rowMapper){
		Assert.hasText(sql, "The Sql Str Is null!");

		if (StringUtils.isNotEmpty(qContext.getOrderColumn())) {
			StringBuilder sb = new StringBuilder(sql);
			sb.append(" ORDER BY ");
			sb.append(qContext.getOrderColumn());
			sb.append(" ");
			sb.append(qContext.getOrder());
			sql = sb.toString();
		}
		log.debug("The Sql Str Before Page Is : " + sql + " ; And The Value Is : "+qContext.getParameters());
		/****BeanParameter中的数据不完整，不能做为参数查询，某些业务的自定义参数和分页参数不在BeanParameter中*******/
//		SqlParameterSource parameterSource;
//		if (qContext.getBeanParameter() != null) {
//			parameterSource = new MultiTypeBeanPropertySqlParameterSource(qContext.getBeanParameter());
//			System.out.println("BeanParameter");
//		} else {
//			parameterSource = new MapSqlParameterSource(qContext.getParameters());
//		}
		SqlParameterSource parameterSource = new MapSqlParameterSource(qContext.getParameters());
		if (qContext.getPage().getPageIndex() < 0) {
			List<T> list = getNamedParameterJdbcTemplate().query(sql, parameterSource, rowMapper);
			QueryResult<T> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			if(StringUtils.isEmpty(countSql)){
				countSql = iSQLGenerator.generateCountSql(sql);
			}
			log.debug("The Count Sql Is  : " + countSql);
			QueryResult<T> qr = qContext.getQueryResult();
			PageModel page = qContext.getPage();
			// 获取记录总数
			int count = getNamedParameterJdbcTemplate().queryForObject(countSql, parameterSource, Integer.class);
			qr.setTotalSize(count);
			page.setTotalSize(count);

			if(page.getPageIndex()<=page.getTotalPage()){
				String pageSql = iSQLGenerator.generatePageSql(sql,page);
				log.debug("The Page Sql Is  : " + pageSql);
				// 获取分页后的记录数量
				List<T> list = getNamedParameterJdbcTemplate().query(pageSql, parameterSource, rowMapper);
				qr.setResult(list);
			}
		}
		return qContext;
	}

	public QueryContext<T> queryListForPaginationForStandardSQL(String sql,QueryContext<T> qContext){
		return this.queryListForPaginationForStandardSQL(sql, qContext, this);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPaginationForStandardSQL(java.lang.String
	 * , com.appabc.common.base.QueryContext)
	 */
	public QueryContext<T> queryListForPaginationForStandardSQL(String sql,QueryContext<T> qContext,RowMapper<T> rowMapper) {
		Assert.hasText(sql, "The Sql Is Null!");
		if (StringUtils.isNotEmpty(qContext.getOrderColumn())) {
			StringBuffer sb = new StringBuffer(sql);
			sb.append(" ORDER BY ");
			sb.append(qContext.getOrderColumn());
			sb.append(" ");
			sb.append(qContext.getOrder());
			sql = sb.toString();
		}
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		log.debug("The Sql Before Page Is : " + sql + " ; And The Value Is : "+args);
		if (qContext.getPage().getPageIndex() < 0) {
			List<T> list = getJdbcTemplate().query(sql, args, rowMapper);
			QueryResult<T> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql);
			log.debug("The Count Sql Is  : " + countSql);
			QueryResult<T> qr = qContext.getQueryResult();
			PageModel page = qContext.getPage();
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			page.setTotalSize(count);

			if(page.getPageIndex()<=page.getTotalPage()){
				String pageSql = iSQLGenerator.generatePageSql(sql,page);
				log.debug("The Page Sql Is  : " + pageSql);
				// 获取分页后的记录数量
				List<T> list = getJdbcTemplate().query(pageSql, args, rowMapper);
				qr.setResult(list);
			}
		}
		return qContext;
	}
	
	

}
