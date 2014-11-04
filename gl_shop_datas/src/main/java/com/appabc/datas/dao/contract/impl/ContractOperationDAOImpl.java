package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.IContractOperationDAO;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月2日 下午2:37:48
 */

@Repository
public class ContractOperationDAOImpl extends BaseJdbcDao<TOrderOperations>
		implements IContractOperationDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_OPERATIONS (LID,OID,OPERATOR,OPERATIONTIME,TYPE,RESULT,PLID,REMARK,ORDERSTATUS) VALUES (:id,:oid,:operator,:operationtime,:type,:result,:plid,:remark,:orderstatus) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_OPERATIONS SET OID = :oid,OPERATOR = :operator,OPERATIONTIME = :operationtime,TYPE = :type,RESULT = :result,PLID = :plid,REMARK = :remark,ORDERSTATUS = :orderstatus WHERE LID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_OPERATIONS WHERE LID = :id ";
	private static final String SELECT_SQL = " SELECT LID,OID,OPERATOR,OPERATIONTIME,TYPE,RESULT,ORDERSTATUS,PLID,REMARK FROM T_ORDER_OPERATIONS ";

	private String dynamicJoinSqlWithEntity(TOrderOperations entity,
			StringBuffer sql) {
		if (entity == null || sql == null || sql.length() <= 0) {
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "LID", entity.getId());
		addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		addNameParamerSqlWithProperty(sql, "operator", "OPERATOR",
				entity.getOperator());
		addNameParamerSqlWithProperty(sql, "type", "TYPE", entity.getType());
		addNameParamerSqlWithProperty(sql, "result", "RESULT",
				entity.getResult());
		addNameParamerSqlWithProperty(sql, "orderstatus", "ORDERSTATUS", entity.getOrderstatus());
		addNameParamerSqlWithProperty(sql, "plid", "PLID", entity.getPlid());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK",
				entity.getRemark());
		return sql.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void save(TOrderOperations entity) {
		super.save(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.
	 * common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TOrderOperations entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void update(TOrderOperations entity) {
		super.update(UPDATE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void delete(TOrderOperations entity) {
		super.delete(DELETE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.
	 * BaseBean)
	 */
	public TOrderOperations query(TOrderOperations entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)
	 */
	public TOrderOperations query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE LID = :id  ");
		return super.query(sql.toString(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public List<TOrderOperations> queryForList(TOrderOperations entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super
				.queryForList(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)
	 */
	public List<TOrderOperations> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.
	 * common.base.QueryContext)
	 */
	public QueryContext<TOrderOperations> queryListForPagination(
			QueryContext<TOrderOperations> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	public TOrderOperations mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderOperations too = new TOrderOperations();

		too.setId(rs.getString("LID"));
		too.setOid(rs.getString("OID"));
		too.setOperator(rs.getString("OPERATOR"));
		too.setOperationtime(rs.getTimestamp("OPERATIONTIME"));
		too.setType(rs.getString("TYPE"));
		too.setResult(rs.getString("RESULT"));
		too.setPlid(rs.getString("PLID"));
		too.setRemark(rs.getString("REMARK"));
		too.setOrderstatus(rs.getString("ORDERSTATUS"));

		return too;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.datas.dao.contract.IContractOperationDAO#queryForListMap(java
	 * .util.Map)
	 */
	public List<TOrderOperations> queryForList(String contractId, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "OID", contractId, args);
		this.addStandardSqlWithParameter(sql, "TYPE", type, args);
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractOperationDAO#queryForList(java.lang.String)  
	 */
	public List<TOrderOperations> queryForList(String contractId) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "OID", contractId, args);
		sql.append(" ORDER BY OPERATIONTIME ASC ");
		return super.queryForList(sql.toString(), args);
	}
	
	public List<TOrderOperations> queryForListWithOIDAndOper(String contractId,String operator){
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "OID", contractId, args);
		this.addStandardSqlWithParameter(sql, "OPERATOR", operator, args);
		sql.append(" ORDER BY OPERATIONTIME DESC ");
		return super.queryForList(sql.toString(), args);
	}

}
