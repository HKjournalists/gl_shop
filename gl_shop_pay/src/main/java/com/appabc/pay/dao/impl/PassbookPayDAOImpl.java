/**  
 * com.appabc.pay.dao.impl.PassbookPayDAPImpl.java  
 *   
 * 2014年9月17日 上午11:15:48  
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
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.dao.IPassbookPayDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:15:48
 */

public class PassbookPayDAOImpl extends BaseJdbcDao<TPassbookPay> implements
		IPassbookPayDAO {
	
	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_PAY (PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,NEEDAMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,CREATEDATE,UPDATEDATE,CREATOR,DEVICES,REMARK) VALUES (:id,:passid,:oid,:otype,:payno,:name,:amount,:needamount,:direction,:paytype,:patytime,:status,:createdate,:updatedate,:creator,:devices,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_PAY SET PASSID = :passid,OID = :oid,OTYPE = :otype,PAYNO = :payno,NAME = :name,AMOUNT = :amount,NEEDAMOUNT = :needamount,DIRECTION = :direction,PAYTYPE = :paytype,PATYTIME = :patytime,STATUS = :status,CREATEDATE = :createdate,UPDATEDATE = :updatedate,CREATOR = :creator,DEVICES = :devices,REMARK = :remark  WHERE PID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_PAY WHERE PID = :id ";
	private static final String SELECT_SQL = " SELECT PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,NEEDAMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,CREATEDATE,UPDATEDATE,CREATOR,DEVICES,REMARK FROM T_PASSBOOK_PAY ";
	
	private String dynamicJoinSqlWithEntity(TPassbookPay entity,StringBuffer sql){
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
		this.addNameParamerSqlWithProperty(sql, "needamount", "NEEDAMOUNT", entity.getNeedamount());
		this.addNameParamerSqlWithProperty(sql, "direction", "DIRECTION", entity.getDirection());
		this.addNameParamerSqlWithProperty(sql, "paytype", "PAYTYPE", entity.getPaytype());
		this.addNameParamerSqlWithProperty(sql, "patytime", "PATYTIME", entity.getPatytime());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "createdate", "CREATEDATE", entity.getCreatedate());
		this.addNameParamerSqlWithProperty(sql, "updatedate", "UPDATEDATE", entity.getUpdatedate());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		this.addNameParamerSqlWithProperty(sql, "devices", "DEVICES", entity.getDevices());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookPay entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookPay entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookPay entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookPay entity) {
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
	public TPassbookPay query(TPassbookPay entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookPay query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookPay> queryForList(TPassbookPay entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookPay> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookPay> queryListForPagination(
			QueryContext<TPassbookPay> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookPay mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPassbookPay entity = new TPassbookPay();
		
		entity.setId(rs.getString("PID"));
		entity.setPassid(rs.getString("PASSID"));
		entity.setOid(rs.getString("OID"));
		entity.setOtype(rs.getString("OTYPE"));
		entity.setPayno(rs.getString("PAYNO"));
		entity.setName(rs.getString("NAME"));
		entity.setAmount(rs.getFloat("AMOUNT"));
		entity.setNeedamount(rs.getFloat("NEEDAMOUNT"));
		entity.setDirection(rs.getInt("DIRECTION"));
		entity.setPaytype(rs.getString("PAYTYPE"));
		entity.setPatytime(rs.getTimestamp("PATYTIME"));
		entity.setStatus(rs.getString("STATUS"));
		entity.setCreatedate(rs.getTimestamp("CREATEDATE"));
		entity.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		entity.setCreator(rs.getString("CREATOR"));
		entity.setDevices(rs.getString("DEVICES"));
		entity.setRemark(rs.getString("REMARK"));
		
		return entity;
	}

}
