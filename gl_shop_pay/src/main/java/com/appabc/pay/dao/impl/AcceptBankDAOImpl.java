package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.dao.IAcceptBankDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 下午8:36:22
 */

public class AcceptBankDAOImpl extends BaseJdbcDao<TAcceptBank> implements IAcceptBankDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ACCEPT_BANK (ID,CID,AUTHID,BANKCARD,BANKACCOUNT,CARDUSER,CARDUSERID,BANKTYPE,BANKNAME,ADDR,REMARK,CREATETIME,UPDATETIME,CREATOR) VALUES (:id,:cid,:authid,:bankcard,:bankaccount,:carduser,:carduserid,:banktype,:bankname,:addr,:remark,:createtime,:updatetime,:creator) ";
	private static final String UPDATE_SQL = " UPDATE T_ACCEPT_BANK SET CID = :cid,AUTHID = :authid,BANKCARD = :bankcard,BANKACCOUNT = :bankaccount,CARDUSER = :carduser,CARDUSERID = :carduserid,BANKTYPE = :banktype,BANKNAME = :bankname,ADDR = :addr,REMARK = :remark,CREATETIME = :createtime,UPDATETIME = :updatetime,CREATOR = :creator WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ACCEPT_BANK WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,CID,AUTHID,BANKCARD,BANKACCOUNT,CARDUSER,CARDUSERID,BANKTYPE,BANKNAME,ADDR,REMARK,CREATETIME,UPDATETIME,CREATOR FROM T_ACCEPT_BANK ";
	
	  
	
	private String dynamicJoinSqlWithEntity(TAcceptBank entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "authid", "AUTHID", entity.getAuthid());
		addNameParamerSqlWithProperty(sql, "bankcard", "BANKCARD", entity.getBankcard());
		addNameParamerSqlWithProperty(sql, "bankaccount", "BANKACCOUNT", entity.getBankaccount());
		addNameParamerSqlWithProperty(sql, "carduser", "CARDUSER", entity.getCarduser());
		addNameParamerSqlWithProperty(sql, "carduserid", "CARDUSERID", entity.getCarduserid());
		addNameParamerSqlWithProperty(sql, "banktype", "BANKTYPE", entity.getBanktype());
		addNameParamerSqlWithProperty(sql, "bankname", "BANKNAME", entity.getBankname());
		addNameParamerSqlWithProperty(sql, "addr", "ADDR", entity.getAddr());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", entity.getUpdatetime());
		addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TAcceptBank entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TAcceptBank entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TAcceptBank entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TAcceptBank entity) {
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
	public TAcceptBank query(TAcceptBank entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TAcceptBank query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TAcceptBank> queryForList(TAcceptBank entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TAcceptBank> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TAcceptBank> queryListForPagination(
			QueryContext<TAcceptBank> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TAcceptBank mapRow(ResultSet rs, int rowNum) throws SQLException {
		TAcceptBank bean = new TAcceptBank();
		
		bean.setId(rs.getString("ID"));
		bean.setCid(rs.getString("CID"));
		bean.setAuthid(rs.getInt("AUTHID"));
		bean.setBankcard(rs.getString("BANKCARD"));
		bean.setBankaccount(rs.getString("BANKACCOUNT"));
		bean.setCarduser(rs.getString("CARDUSER"));
		bean.setCarduserid(rs.getString("CARDUSERID"));
		bean.setBanktype(rs.getString("BANKTYPE"));
		bean.setBankname(rs.getString("BANKNAME"));
		bean.setAddr(rs.getString("ADDR"));
		bean.setRemark(rs.getString("REMARK"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		bean.setCreator(rs.getString("CREATOR"));
		
		return bean;
	}

}
