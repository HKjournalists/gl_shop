/**  
 * com.appabc.pay.dao.impl.PayDetailDAOImpl.java  
 *   
 * 2014年9月17日 上午11:23:46  
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
import com.appabc.pay.bean.TPayDetail;
import com.appabc.pay.dao.IPayDetailDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:23:46
 */

public class PayDetailDAOImpl extends BaseJdbcDao<TPayDetail> implements IPayDetailDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PAY_DETAIL (PAYDETAILID,PAYACCOUNTID,PAYFLOWID,PAYORGNUM,PAYTRADESTATUS,BUZTRADENUM,PARAMSCONTENT,PAYOPERATETYPE,BUZNOTIFYURL,TRADETIME,CREATETIME,UPDATETIME,CREATOR) VALUES (:id,:payaccountid,:payflowid,:payorgnum,:paytradestatus,:buztradenum,:paramscontent,:payoperatetype,:buznotifyurl,:tradetime,:createtime,:updatetime,:creator) ";
	private static final String UPDATE_SQL = " UPDATE T_PAY_DETAIL SET PAYACCOUNTID = :payaccountid,PAYFLOWID = :payflowid,PAYORGNUM = :payorgnum,PAYTRADESTATUS = :paytradestatus,BUZTRADENUM = :buztradenum,PARAMSCONTENT = :paramscontent,PAYOPERATETYPE = :payoperatetype,BUZNOTIFYURL = :buznotifyurl,TRADETIME = :tradetime,CREATETIME = :createtime,UPDATETIME = :updatetime,CREATOR = :creator WHERE PAYDETAILID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PAY_DETAIL WHERE PAYDETAILID = :id ";
	private static final String SELECT_SQL = " SELECT PAYDETAILID,PAYACCOUNTID,PAYFLOWID,PAYORGNUM,PAYTRADESTATUS,BUZTRADENUM,PARAMSCONTENT,PAYOPERATETYPE,BUZNOTIFYURL,TRADETIME,CREATETIME,UPDATETIME,CREATOR FROM T_PAY_DETAIL ";
	
	private String dynamicJoinSqlWithEntity(TPayDetail entity,StringBuffer sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		
		this.addNameParamerSqlWithProperty(sql, "id", "PAYDETAILID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "payaccountid", "PAYACCOUNTID", entity.getPayaccountid());
		this.addNameParamerSqlWithProperty(sql, "payflowid", "PAYFLOWID", entity.getPayflowid());
		this.addNameParamerSqlWithProperty(sql, "payorgnum", "PAYORGNUM", entity.getPayorgnum());
		this.addNameParamerSqlWithProperty(sql, "paytradestatus", "PAYTRADESTATUS", entity.getPaytradestatus());
		this.addNameParamerSqlWithProperty(sql, "buztradenum", "BUZTRADENUM", entity.getBuztradenum());
		this.addNameParamerSqlWithProperty(sql, "paramscontent", "PARAMSCONTENT", entity.getParamscontent());
		this.addNameParamerSqlWithProperty(sql, "payoperatetype", "PAYOPERATETYPE", entity.getPayoperatetype());
		this.addNameParamerSqlWithProperty(sql, "buznotifyurl", "BUZNOTIFYURL", entity.getBuznotifyurl());
		this.addNameParamerSqlWithProperty(sql, "tradetime", "TRADETIME", entity.getTradetime());
		this.addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", entity.getUpdatetime());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		
		return sql.toString();
	}
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPayDetail entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPayDetail entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPayDetail entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPayDetail entity) {
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
	public TPayDetail query(TPayDetail entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPayDetail query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PAYDETAILID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPayDetail> queryForList(TPayDetail entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPayDetail> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPayDetail> queryListForPagination(
			QueryContext<TPayDetail> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPayDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPayDetail entity = new TPayDetail();
		
		entity.setId(rs.getString("PAYDETAILID"));
		entity.setPayaccountid(rs.getString("PAYACCOUNTID"));
		entity.setPayflowid(rs.getString("PAYFLOWID"));
		entity.setPayorgnum(rs.getString("PAYORGNUM"));
		entity.setPaytradestatus(rs.getString("PAYTRADESTATUS"));
		entity.setBuztradenum(rs.getString("BUZTRADENUM"));
		entity.setParamscontent(rs.getString("PARAMSCONTENT"));
		entity.setPayoperatetype(rs.getInt("PAYOPERATETYPE"));
		entity.setBuznotifyurl(rs.getString("BUZNOTIFYURL"));
		entity.setTradetime(rs.getTimestamp("TRADETIME"));
		entity.setCreatetime(rs.getTimestamp("CREATETIME"));
		entity.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		entity.setCreator(rs.getString("CREATOR"));
		
		return entity;
	}

}
