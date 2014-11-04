/**  
 * com.appabc.pay.dao.impl.OfflineDAOImpl.java  
 *   
 * 2014年9月29日 上午11:21:18  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.dao.IOfflinePayDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月29日 上午11:21:18
 */

public class OfflinePayDAOImpl extends BaseJdbcDao<TOfflinePay> implements IOfflinePayDAO {

	private static final String INSERT_SQL = " INSERT INTO T_OFFLINE_PAY (OPID,PID,OID,OTYPE,TOTAL,AMOUNT,PTYPE,CREATER,CREATTIME,STATUS,DEALER,DEALTIME,DEALRESULT,BTYPE) VALUES (:id,:pid,:oid,:otype,:total,:amount,:ptype,:creater,:creattime,:status,:dealer,:dealtime,:dealresult,:btype) ";
	private static final String UPDATE_SQL = " UPDATE T_OFFLINE_PAY SET PID = :pid,OID = :oid,OTYPE = :otype,TOTAL = :total,AMOUNT = :amount,PTYPE = :ptype,CREATER = :creater,CREATTIME = :creattime,STATUS = :status,DEALER = :dealer,DEALTIME = :dealtime,DEALRESULT = :dealresult,BTYPE = :btype WHERE OPID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_OFFLINE_PAY WHERE OPID = :id ";
	private static final String SELECT_SQL = " SELECT OPID,PID,OID,OTYPE,TOTAL,AMOUNT,PTYPE,CREATER,CREATTIME,STATUS,DEALER,DEALTIME,DEALRESULT,BTYPE FROM T_OFFLINE_PAY ";
	
	private String dynamicJoinSqlWithEntity(TOfflinePay entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "OPID", entity.getId());
		addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		addNameParamerSqlWithProperty(sql, "total", "TOTAL", entity.getTotal());
		addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		addNameParamerSqlWithProperty(sql, "ptype", "PTYPE", entity.getPtype());
		addNameParamerSqlWithProperty(sql, "creater", "CREATER", entity.getCreater());
		addNameParamerSqlWithProperty(sql, "creattime", "CREATTIME", entity.getCreattime());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "dealer", "DEALER", entity.getDealer());
		addNameParamerSqlWithProperty(sql, "dealtime", "DEALTIME", entity.getDealtime());
		addNameParamerSqlWithProperty(sql, "dealresult", "DEALRESULT", entity.getDealresult());
		addNameParamerSqlWithProperty(sql, "btype", "BTYPE", entity.getBtype());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TOfflinePay entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TOfflinePay entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TOfflinePay entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOfflinePay entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TOfflinePay query(TOfflinePay entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TOfflinePay query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE OPID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOfflinePay> queryForList(TOfflinePay entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TOfflinePay> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOfflinePay> queryListForPagination(
			QueryContext<TOfflinePay> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TOfflinePay mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOfflinePay bean = new TOfflinePay();
		
		bean.setId(rs.getString("OPID"));
		bean.setPid(rs.getString("PID"));
		bean.setOid(rs.getString("OID"));
		bean.setOtype(rs.getString("OTYPE"));
		bean.setTotal(rs.getFloat("TOTAL"));
		bean.setAmount(rs.getFloat("AMOUNT"));
		bean.setPtype(rs.getString("PTYPE"));
		bean.setCreater(rs.getString("CREATER"));
		bean.setCreattime(rs.getTimestamp("CREATTIME"));
		bean.setStatus(rs.getString("STATUS"));
		bean.setDealer(rs.getString("DEALER"));
		bean.setDealresult(rs.getString("DEALRESULT"));
		bean.setDealtime(rs.getTimestamp("DEALTIME"));
		bean.setBtype(rs.getString("BTYPE"));
		
		return bean;
	}

}
