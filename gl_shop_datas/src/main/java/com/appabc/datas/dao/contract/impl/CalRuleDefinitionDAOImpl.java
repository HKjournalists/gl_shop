package com.appabc.datas.dao.contract.impl;

import com.appabc.bean.pvo.TCalRuleDefinition;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.ICalRuleDefinitionDAO;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午4:01:51
 */

@Repository
public class CalRuleDefinitionDAOImpl extends BaseJdbcDao<TCalRuleDefinition> implements
		ICalRuleDefinitionDAO {

	private static final String INSERT_SQL = " INSERT INTO T_CAL_RULE_DEFINITION (RULEID,RULE,NAME,EXPRESSION,CREATOR,CREATEDATE,REMARK) VALUES (:id,:rule,:name,:expression,:creator,:createdate,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_CAL_RULE_DEFINITION SET RULE = :rule,NAME = :name,EXPRESSION = :expression,CREATOR = :creator,CREATEDATE = :createdate,REMARK = :remark WHERE RULEID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_CAL_RULE_DEFINITION WHERE RULEID = :id ";
	private static final String SELECT_SQL = " SELECT RULEID,RULE,NAME,EXPRESSION,CREATOR,CREATEDATE,REMARK FROM T_CAL_RULE_DEFINITION ";
	
	private String dynamicJoinSqlWithEntity(TCalRuleDefinition bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "RULEID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "rule", "RULE", bean.getRule());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", bean.getName());
		this.addNameParamerSqlWithProperty(sql, "expression", "EXPRESSION", bean.getExpression());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", bean.getCreator());
		//CREATEDATE = :createdate
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TCalRuleDefinition entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TCalRuleDefinition entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TCalRuleDefinition entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TCalRuleDefinition entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.BaseBean)  
	 */
	public TCalRuleDefinition query(TCalRuleDefinition entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)  
	 */
	public TCalRuleDefinition query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE RULEID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TCalRuleDefinition> queryForList(TCalRuleDefinition entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)  
	 */
	public List<TCalRuleDefinition> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCalRuleDefinition> queryListForPagination(
			QueryContext<TCalRuleDefinition> qContext) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1=1  ");//CREATOR = :creator
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", qContext.getParameter("creator"));
		this.addNameParamerSqlWithProperty(sql, "rule", "RULE", qContext.getParameter("rule"), SQLExpressionEnum.GT.getText());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", qContext.getParameter("name"), SQLExpressionEnum.LIKE.getText());
		this.addNameParamerSqlWithProperty(sql, "expression", "EXPRESSION", qContext.getParameter("expression"), SQLExpressionEnum.NOTEQ.getText());
		
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TCalRuleDefinition mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TCalRuleDefinition bean = new TCalRuleDefinition();
		
		bean.setId(rs.getString("RULEID"));
		bean.setRule(rs.getString("RULE"));
		bean.setName(rs.getString("NAME"));
		bean.setExpression(rs.getString("EXPRESSION"));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
		bean.setRemark(rs.getString("REMARK"));
		
		return bean;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.ICalRuleDefinitionDAO#queryListForPaginationForSQL(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCalRuleDefinition> queryListForPaginationForSQL(
			QueryContext<TCalRuleDefinition> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1=1  ");//CREATOR = :creator
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "CREATOR", qContext.getParameter("creator"), args);
		this.addStandardSqlWithParameter(sql, "RULE", qContext.getParameter("rule"), args,SQLExpressionEnum.GT.getText());
		this.addStandardSqlWithParameter(sql, "NAME", qContext.getParameter("name"), args, SQLExpressionEnum.LIKE.getText());
		this.addStandardSqlWithParameter(sql, "EXPRESSION", qContext.getParameter("expression"), args, SQLExpressionEnum.NOTEQ.getText());
		
		qContext.setParamList(args);
		return super.queryListForPaginationForStandardSQL(sql.toString(), qContext);
	}

}
