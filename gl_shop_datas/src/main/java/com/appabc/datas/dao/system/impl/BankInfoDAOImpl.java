/**  
 * com.appabc.pay.dao.impl.BankInfoDAOImpl.java  
 *   
 * 2014年9月19日 下午9:16:10  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appabc.bean.pvo.TBankInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IBankInfoDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午9:16:10
 */

@Component(value="IBankInfoDAO")
public class BankInfoDAOImpl extends BaseJdbcDao<TBankInfo> implements IBankInfoDAO {

	private static final String INSERT_SQL = " INSERT INTO T_BANK_INFO (BIID,BANKCARDNUM,CARDUSER,BLANKTYPE,BANKNAME,CREATETIME,CREATER,UPDATER,UPATETIME) VALUES (:id,:bankcardnum,:carduser,:blanktype,:bankname,:createtime,:creater,:updater,:upatetime) ";
	private static final String UPDATE_SQL = " UPDATE T_BANK_INFO SET BANKCARDNUM = :bankcardnum,CARDUSER = :carduser,BLANKTYPE = :blanktype,BANKNAME = :bankname,CREATETIME = :createtime,CREATER = :creater,UPDATER = :updater,UPATETIME = :upatetime WHERE BIID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_BANK_INFO WHERE BIID = :id ";
	private static final String SELECT_SQL = " SELECT BIID,BANKCARDNUM,CARDUSER,BLANKTYPE,BANKNAME,CREATETIME,CREATER,UPDATER,UPATETIME FROM T_BANK_INFO ";
	
	private String dynamicJoinSqlWithEntity(TBankInfo entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "BIID", entity.getId());
		addNameParamerSqlWithProperty(sql, "bankcardnum", "BANKCARDNUM", entity.getBankcardnum());
		addNameParamerSqlWithProperty(sql, "carduser", "CARDUSER", entity.getCarduser());
		addNameParamerSqlWithProperty(sql, "blanktype", "BLANKTYPE", entity.getBlanktype());
		addNameParamerSqlWithProperty(sql, "bankname", "BANKNAME", entity.getBankname());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "creater", "CREATER", entity.getCreater());
		addNameParamerSqlWithProperty(sql, "updater", "UPDATER", entity.getUpdater());
		addNameParamerSqlWithProperty(sql, "upatetime", "UPATETIME", entity.getUpatetime());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TBankInfo entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TBankInfo entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TBankInfo entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TBankInfo entity) {
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
	public TBankInfo query(TBankInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TBankInfo query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE LOGID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TBankInfo> queryForList(TBankInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TBankInfo> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TBankInfo> queryListForPagination(
			QueryContext<TBankInfo> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TBankInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TBankInfo bean = new TBankInfo();
		
		bean.setId(rs.getString("BIID"));
		bean.setBankcardnum(rs.getString("BANKCARDNUM"));
		bean.setCarduser(rs.getString("CARDUSER"));
		bean.setBlanktype(rs.getString("BLANKTYPE"));
		bean.setBankname(rs.getString("BANKNAME"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setCreater(rs.getString("CREATER"));
		bean.setUpatetime(rs.getTimestamp("UPATETIME"));
		bean.setUpdater(rs.getString("UPDATER"));
		
		return bean;
	}

}
