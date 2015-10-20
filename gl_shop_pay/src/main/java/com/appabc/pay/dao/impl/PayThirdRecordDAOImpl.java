/**  
 * com.appabc.pay.dao.impl.PayThirdRecordDAOImpl.java  
 *   
 * 2015年3月3日 上午9:45:21  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.KeyHolder;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TPayThirdOrgRecord;
import com.appabc.pay.dao.IPayThirdRecordDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午9:45:21
 */

public class PayThirdRecordDAOImpl extends BaseJdbcDao<TPayThirdOrgRecord> implements
		IPayThirdRecordDAO {
	
	private static final String INSERT_SQL = " INSERT INTO T_PAY_THIRDORG_RECORD (ID,OID,PARAMSCONTENT,TYPE,TRADETIME,CREATETIME,UPDATETIME,CREATOR) VALUES (:id,:oid,:paramsContent,:type,:tradeTime,:createTime,:updateTime,:creator) ";
	private static final String UPDATE_SQL = " UPDATE T_PAY_THIRDORG_RECORD SET OID = :oid,PARAMSCONTENT = :paramsContent,TYPE = :type,TRADETIME = :tradeTime,CREATETIME = :createTime,UPDATETIME = :updateTime,CREATOR = :creator WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PAY_THIRDORG_RECORD WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,OID,PARAMSCONTENT,TYPE,TRADETIME,CREATETIME,UPDATETIME,CREATOR FROM T_PAY_THIRDORG_RECORD ";

	private String dynamicJoinSqlWithEntity(TPayThirdOrgRecord entity, StringBuilder sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		this.addNameParamerSqlWithProperty(sql, "paramsContent", "PARAMSCONTENT", entity.getParamsContent());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", entity.getType());
		this.addNameParamerSqlWithProperty(sql, "tradeTime", "TRADETIME", entity.getTradeTime());
		this.addNameParamerSqlWithProperty(sql, "createTime", "CREATETIME", entity.getCreateTime());
		this.addNameParamerSqlWithProperty(sql, "updateTime", "UPDATETIME", entity.getUpdateTime());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TPayThirdOrgRecord entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TPayThirdOrgRecord entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TPayThirdOrgRecord entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPayThirdOrgRecord entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPayThirdOrgRecord query(TPayThirdOrgRecord entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TPayThirdOrgRecord query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPayThirdOrgRecord> queryForList(TPayThirdOrgRecord entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPayThirdOrgRecord> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPayThirdOrgRecord> queryListForPagination(QueryContext<TPayThirdOrgRecord> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TPayThirdOrgRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPayThirdOrgRecord entity = new TPayThirdOrgRecord();
		
		entity.setId(rs.getString("ID"));
		entity.setOid(rs.getString("OID"));
		entity.setParamsContent(rs.getString("PARAMSCONTENT"));
		entity.setType(rs.getInt("TYPE"));
		entity.setTradeTime(rs.getTimestamp("TRADETIME"));
		entity.setCreateTime(rs.getTimestamp("CREATETIME"));
		entity.setUpdateTime(rs.getTimestamp("UPDATETIME"));
		entity.setCreator(rs.getString("CREATOR"));
		
		return entity;
	}

}
