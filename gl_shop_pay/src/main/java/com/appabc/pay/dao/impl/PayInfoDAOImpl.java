/**  
 * com.appabc.pay.dao.impl.PayInfoDAOImpl.java  
 *   
 * 2014年9月17日 上午11:22:14  
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
import com.appabc.pay.bean.TPayInfo;
import com.appabc.pay.dao.IPayInfoDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:22:14
 */

public class PayInfoDAOImpl extends BaseJdbcDao<TPayInfo> implements IPayInfoDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PAY_INFO (PAYACCOUNTID,PARTNERID,PAYKEY,GATEWAYURL,SERVICENAME,SIGNTYPE,SELLERACCOUNT,PAYORGNAME,PAYORGCODE,IMGURL,STATUS,CREATETIME,UPDATETIME,CREATOR) VALUES (:id,:partnerid,:paykey,:gatewayurl,:servicename,:signtype,:selleraccount,:payorgname,:payorgcode,:imgurl,:status,:createtime,:updatetime,:creator) ";
	private static final String UPDATE_SQL = " UPDATE T_PAY_INFO SET PARTNERID = :partnerid,PAYKEY = :paykey,GATEWAYURL = :gatewayurl,SERVICENAME = :servicename,SIGNTYPE = :signtype,SELLERACCOUNT = :selleraccount,PAYORGNAME = :payorgname,PAYORGCODE = :payorgcode,IMGURL = :imgurl,STATUS = :status, CREATETIME = :createtime,UPDATETIME = :updatetime,CREATOR = :creator WHERE PAYACCOUNTID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PAY_INFO WHERE PAYACCOUNTID = :id ";
	private static final String SELECT_SQL = " SELECT PAYACCOUNTID,PARTNERID,PAYKEY,GATEWAYURL,SERVICENAME,SIGNTYPE,SELLERACCOUNT,PAYORGNAME,PAYORGCODE,IMGURL,STATUS,CREATETIME,UPDATETIME,CREATOR FROM T_PAY_INFO ";
	
	private String dynamicJoinSqlWithEntity(TPayInfo entity,StringBuffer sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		
		this.addNameParamerSqlWithProperty(sql, "id", "PAYACCOUNTID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "partnerid", "PARTNERID", entity.getPartnerid());
		this.addNameParamerSqlWithProperty(sql, "paykey", "PAYKEY", entity.getPaykey());
		this.addNameParamerSqlWithProperty(sql, "gatewayurl", "GATEWAYURL", entity.getGatewayurl());
		this.addNameParamerSqlWithProperty(sql, "servicename", "SERVICENAME", entity.getServicename());
		this.addNameParamerSqlWithProperty(sql, "signtype", "SIGNTYPE", entity.getSigntype());
		this.addNameParamerSqlWithProperty(sql, "selleraccount", "SELLERACCOUNT", entity.getSelleraccount());
		this.addNameParamerSqlWithProperty(sql, "payorgname", "PAYORGNAME", entity.getPayorgname());
		this.addNameParamerSqlWithProperty(sql, "payorgcode", "PAYORGCODE", entity.getPayorgcode());
		this.addNameParamerSqlWithProperty(sql, "imgurl", "IMGURL", entity.getImgurl());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", entity.getUpdatetime());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPayInfo entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPayInfo entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPayInfo entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPayInfo entity) {
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
	public TPayInfo query(TPayInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPayInfo query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PAYACCOUNTID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPayInfo> queryForList(TPayInfo entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPayInfo> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPayInfo> queryListForPagination(
			QueryContext<TPayInfo> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPayInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPayInfo entity = new TPayInfo();
		
		entity.setId(rs.getString("PAYACCOUNTID"));
		entity.setPartnerid(rs.getString("PARTNERID"));
		entity.setPaykey(rs.getString("PAYKEY"));
		entity.setGatewayurl(rs.getString("GATEWAYURL"));
		entity.setServicename(rs.getString("SERVICENAME"));
		entity.setSigntype(rs.getString("SIGNTYPE"));
		entity.setSelleraccount(rs.getString("SELLERACCOUNT"));
		entity.setPayorgcode(rs.getString("PAYORGCODE"));
		entity.setPayorgname(rs.getString("PAYORGNAME"));
		entity.setImgurl(rs.getString("IMGURL"));
		entity.setStatus(rs.getInt("STATUS"));
		entity.setCreatetime(rs.getTimestamp("CREATETIME"));
		entity.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		entity.setCreator(rs.getString("CREATOR"));
		
		return entity;
	}

}
