package com.appabc.datas.dao.contract.impl;

import com.appabc.bean.pvo.TOrderArbitrationResult;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.IContractArbitrationResultDAO;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date  : 2014年9月2日 下午3:11:01
 */

@Repository
public class ContractArbitrationResultDAOImpl extends BaseJdbcDao<TOrderArbitrationResult> implements
		IContractArbitrationResultDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_ARBITRATION_RESULT (RID,AID,PID,RTYPE,RCONTENT,QYTYPE,QYID,AMOUNT,PAYID) VALUES (:id,:aid,:pid,:rtype,:rcontent,:qytype,:qyid,:amount,:payid) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_ARBITRATION_RESULT SET AID = :aid,PID = :pid,RTYPE = :rtype,RCONTENT = :rcontent,QYTYPE = :qytype,QYID = :qyid,AMOUNT = :amount,PAYID = :payid WHERE RID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_ARBITRATION_RESULT WHERE RID = :id ";
	private static final String SELECT_SQL = " SELECT RID,AID,PID,RTYPE,RCONTENT,QYTYPE,QYID,AMOUNT,PAYID FROM T_ORDER_ARBITRATION_RESULT ";
	
	private String dynamicJoinSqlWithEntity(TOrderArbitrationResult entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "RID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "aid", "AID", entity.getAid());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		this.addNameParamerSqlWithProperty(sql, "rtype", "RTYPE", entity.getRtype());
		this.addNameParamerSqlWithProperty(sql, "rcontent", "RCONTENT", entity.getRcontent());
		this.addNameParamerSqlWithProperty(sql, "qytype", "QYTYPE", entity.getQytype());
		this.addNameParamerSqlWithProperty(sql, "qyid", "QYID", entity.getQyid());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "payid", "PAYID", entity.getPayid());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TOrderArbitrationResult entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TOrderArbitrationResult entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TOrderArbitrationResult entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOrderArbitrationResult entity) {
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
	public TOrderArbitrationResult query(TOrderArbitrationResult entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)  
	 */
	public TOrderArbitrationResult query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE RID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOrderArbitrationResult> queryForList(
			TOrderArbitrationResult entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)  
	 */
	public List<TOrderArbitrationResult> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOrderArbitrationResult> queryListForPagination(
			QueryContext<TOrderArbitrationResult> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TOrderArbitrationResult mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderArbitrationResult bean = new TOrderArbitrationResult();
		
		bean.setId(rs.getString("RID"));
		bean.setAid(rs.getString("AID"));
		bean.setPid(rs.getString("PID"));
		bean.setRtype(rs.getString("RTYPE"));
		bean.setRcontent(rs.getString("RCONTENT"));
		bean.setQytype(rs.getString("QYTYPE"));
		bean.setQyid(rs.getString("QYID"));
		bean.setAmount(rs.getFloat("AMOUNT"));
		bean.setPayid(rs.getString("PAYID"));
		
		return bean;
	}

}
