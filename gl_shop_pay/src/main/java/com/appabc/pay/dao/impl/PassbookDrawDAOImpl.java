/**  
 * com.appabc.pay.dao.impl.PassbookDrawDAOImpl.java  
 *   
 * 2014年9月17日 上午11:13:50  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;

import com.appabc.bean.enums.PurseInfo.ExtractStatus;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.dao.IPassbookDrawDAO;

/**
 * @Description : pass book draw DAO IMPL
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date : 2014年9月17日 上午11:13:50
 */

public class PassbookDrawDAOImpl extends BaseJdbcDao<TPassbookDraw> implements
		IPassbookDrawDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_DRAW (TID,AID,AMOUNT,CREATETIME,DEALTIME,DEALER,DEALSTATUS,PID,STATUS,MARK) VALUES (:id,:aid,:amount,:createtime,:dealtime,:dealer,:dealstatus,:pid,:status,:mark) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_DRAW SET AID = :aid,AMOUNT = :amount,CREATETIME = :createtime,DEALTIME = :dealtime,DEALER = :dealer,DEALSTATUS = :dealstatus,PID = :pid,STATUS = :status,MARK = :mark WHERE TID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_DRAW WHERE TID = :id ";
	private static final String SELECT_SQL = " SELECT TID,AID,AMOUNT,CREATETIME,DEALTIME,DEALER,DEALSTATUS,PID,STATUS,MARK FROM T_PASSBOOK_DRAW ";
	
	private String dynamicJoinSqlWithEntity(TPassbookDraw entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "TID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "aid", "AID", entity.getAid());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		this.addNameParamerSqlWithProperty(sql, "dealtime", "DEALTIME", entity.getDealtime());
		this.addNameParamerSqlWithProperty(sql, "dealer", "DEALER", entity.getDealer());
		this.addNameParamerSqlWithProperty(sql, "dealstatus", "DEALSTATUS", entity.getDealstatus());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "mark", "MARK", entity.getMark());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookDraw entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookDraw entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookDraw entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookDraw entity) {
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
	public TPassbookDraw query(TPassbookDraw entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookDraw query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE TID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookDraw> queryForList(TPassbookDraw entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookDraw> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookDraw> queryListForPagination(
			QueryContext<TPassbookDraw> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookDraw mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPassbookDraw entity = new TPassbookDraw();
		
		entity.setId(rs.getString("TID"));
		entity.setAid(rs.getString("AID"));
		entity.setAmount(rs.getFloat("AMOUNT"));
		entity.setCreatetime(rs.getTimestamp("CREATETIME"));
		entity.setDealtime(rs.getTimestamp("DEALTIME"));
		entity.setDealer(rs.getString("DEALER"));
		entity.setDealstatus(rs.getString("DEALSTATUS"));
		entity.setStatus(ExtractStatus.enumOf(rs.getInt("STATUS")));
		entity.setPid(rs.getString("PID"));
		entity.setMark(rs.getString("MARK"));
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPassbookDrawDAO#getTPassbookDrawByPassId(java.lang.String)  
	 */
	public List<TPassbookDraw> getTPassbookDrawByPassId(String passId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT draw.TID AS TID, draw.AID AS AID,draw.AMOUNT AS AMOUNT,draw.CREATETIME AS CREATETIME, draw.DEALTIME AS DEALTIME, draw.DEALER AS DEALER, draw.DEALSTATUS AS DEALSTATUS,draw.PID AS PID,draw.STATUS AS STATUS,draw.MARK AS MARK FROM T_PASSBOOK_DRAW draw ");
		sql.append(" LEFT JOIN T_PASSBOOK_PAY pay ON draw.PID = pay.PID ");
		sql.append(" WHERE pay.PASSID = ? ");
		return super.queryForList(sql.toString(), Collections.singletonList(passId));
	}

}
