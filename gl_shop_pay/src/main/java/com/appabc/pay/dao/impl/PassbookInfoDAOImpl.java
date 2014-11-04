/**  
 * com.appabc.pay.dao.impl.PassbookInfoDAOImpl.java  
 *   
 * 2014年9月17日 上午10:30:56  
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
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.dao.IPassbookInfoDAO;

/**
 * @Description : pass book info DAO IMPL
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date : 2014年9月17日 上午10:30:56
 */

public class PassbookInfoDAOImpl extends BaseJdbcDao<TPassbookInfo> implements
		IPassbookInfoDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_INFO (PASSID,CID,PASSTYPE,AMOUNT,CREATETIME,REMARK) VALUES (:id,:cid,:passtype,:amount,:createtime,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_INFO SET CID = :cid,PASSTYPE = :passtype,AMOUNT = :amount,CREATETIME = :createtime,REMARK = :remark WHERE PASSID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_INFO WHERE PASSID = :id ";
	private static final String SELECT_SQL = " SELECT PASSID,CID,PASSTYPE,AMOUNT,CREATETIME,REMARK FROM T_PASSBOOK_INFO ";
	
	private String dynamicJoinSqlWithEntity(TPassbookInfo entity,StringBuffer sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "PASSID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		this.addNameParamerSqlWithProperty(sql, "passtype", "PASSTYPE", entity.getPasstype());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookInfo entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookInfo entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookInfo entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookInfo entity) {
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
	public TPassbookInfo query(TPassbookInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookInfo query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PASSID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookInfo> queryForList(TPassbookInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookInfo> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookInfo> queryListForPagination(
			QueryContext<TPassbookInfo> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPassbookInfo entity = new TPassbookInfo();
		
		entity.setId(rs.getString("PASSID"));
		entity.setCid(rs.getString("CID"));
		entity.setPasstype(rs.getString("PASSTYPE"));
		entity.setAmount(rs.getFloat("AMOUNT"));
		entity.setCreatetime(rs.getTimestamp("CREATETIME"));
		entity.setRemark(rs.getString("REMARK"));
		
		return entity;
	}

}
