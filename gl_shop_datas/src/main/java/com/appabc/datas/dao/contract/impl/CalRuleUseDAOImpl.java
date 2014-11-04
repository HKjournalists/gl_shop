package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TCalRuleUse;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.ICalRuleUseDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午3:56:54
 */

@Repository
public class CalRuleUseDAOImpl extends BaseJdbcDao<TCalRuleUse> implements ICalRuleUseDAO {

	private static final String INSERT_SQL = " INSERT INTO T_CAL_RULE_USE (FID,RULEID,STARTDATE,ENDDATE,ORDERBY,CREATEDATE,CREATOR,REMARK) VALUES (:id,:ruleid,:startdate,:enddate,:orderby,:createdate,:creator,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_CAL_RULE_USE SET RULEID = :ruleid,STARTDATE = :startdate,ENDDATE = :enddate,ORDERBY = :orderby,CREATEDATE = :createdate,CREATOR = :creator,REMARK = :remark WHERE FID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_CAL_RULE_USE WHERE FID = :id ";
	private static final String SELECT_SQL = " SELECT FID,RULEID,STARTDATE,ENDDATE,ORDERBY,CREATEDATE,CREATOR,REMARK FROM T_CAL_RULE_USE ";
	
	private String dynamicJoinSqlWithEntity(TCalRuleUse bean, StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "FID", bean.getId());
		//STARTDATE = :startdate
		//ENDDATE = :enddate
		//CREATEDATE = :createdate
		this.addNameParamerSqlWithProperty(sql, "ruleid", "RULEID", bean.getRuleid());
		this.addNameParamerSqlWithProperty(sql, "orderby", "ORDERBY", bean.getOrderby());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", bean.getCreator());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TCalRuleUse entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TCalRuleUse entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TCalRuleUse entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TCalRuleUse entity) {
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
	public TCalRuleUse query(TCalRuleUse entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)  
	 */
	public TCalRuleUse query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE FID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TCalRuleUse> queryForList(TCalRuleUse entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)  
	 */
	public List<TCalRuleUse> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCalRuleUse> queryListForPagination(
			QueryContext<TCalRuleUse> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TCalRuleUse mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCalRuleUse bean = new TCalRuleUse();
		
		bean.setId(rs.getString("FID"));
		bean.setRuleid(rs.getString("RULEID"));
		bean.setStartdate(rs.getTimestamp("STARTDATE"));
		bean.setEnddate(rs.getTimestamp("ENDDATE"));
		bean.setOrderby(rs.getString("ORDERBY"));
		bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setRemark(rs.getString("REMARK"));
		
		return bean;
	}

}
