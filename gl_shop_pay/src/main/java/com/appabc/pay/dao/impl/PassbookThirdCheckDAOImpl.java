/**  
 * com.appabc.pay.dao.impl.PassbookThirdCheckDAOImpl.java  
 *   
 * 2014年9月18日 下午12:00:53  
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
import com.appabc.pay.bean.TPassbookThirdCheck;
import com.appabc.pay.dao.IPassbookThirdCheckDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月18日 下午12:00:53
 */
public class PassbookThirdCheckDAOImpl extends BaseJdbcDao<TPassbookThirdCheck> implements
		IPassbookThirdCheckDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_THIRD_CHECK (PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,DEVICES,REMARK) VALUES (:id,:passid,:oid,:otype,:payno,:name,:amount,:direction,:paytype,:patytime,:status,:devices,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_THIRD_CHECK SET PASSID = :passid,OID = :oid,OTYPE = :otype,PAYNO = :payno,NAME = :name,AMOUNT = :amount,DIRECTION = :direction,PAYTYPE = :paytype,PATYTIME = :patytime,STATUS = :status, DEVICES = :devices,REMARK = :remark WHERE PID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_THIRD_CHECK WHERE PID = :id ";
	private static final String SELECT_SQL = " SELECT PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,DEVICES,REMARK FROM T_PASSBOOK_THIRD_CHECK ";
	
	private String dynamicJoinSqlWithEntity(TPassbookThirdCheck entity,StringBuffer sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		
		this.addNameParamerSqlWithProperty(sql, "id", "PID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "passid", "PASSID", entity.getPassid());
		this.addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		this.addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		this.addNameParamerSqlWithProperty(sql, "payno", "PAYNO", entity.getPayno());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", entity.getName());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "direction", "DIRECTION", entity.getDirection());
		this.addNameParamerSqlWithProperty(sql, "paytype", "PAYTYPE", entity.getPaytype());
		this.addNameParamerSqlWithProperty(sql, "patytime", "PATYTIME", entity.getPatytime());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "devices", "DEVICES", entity.getDevices());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookThirdCheck entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookThirdCheck entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookThirdCheck entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookThirdCheck entity) {
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
	public TPassbookThirdCheck query(TPassbookThirdCheck entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookThirdCheck query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookThirdCheck> queryForList(TPassbookThirdCheck entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookThirdCheck> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookThirdCheck> queryListForPagination(
			QueryContext<TPassbookThirdCheck> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookThirdCheck mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TPassbookThirdCheck bean = new TPassbookThirdCheck();
		
		bean.setId(rs.getString("PID"));
		bean.setPassid(rs.getString("PASSID"));
		bean.setOid(rs.getString("OID"));
		bean.setOtype(rs.getString("OTYPE"));
		bean.setPayno(rs.getString("PAYNO"));
		bean.setName(rs.getString("NAME"));
		bean.setAmount(rs.getFloat("AMOUNT"));
		bean.setDirection(rs.getInt("DIRECTION"));
		bean.setPaytype(rs.getString("PAYTYPE"));
		bean.setPatytime(rs.getTimestamp("PATYTIME"));
		bean.setStatus(rs.getString("STATUS"));
		bean.setDevices(rs.getString("DEVICES"));
		bean.setRemark(rs.getString("REMARK"));
		
		return bean;
	}

}
