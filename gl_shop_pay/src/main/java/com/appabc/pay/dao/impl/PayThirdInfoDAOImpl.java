/**  
 * com.appabc.pay.dao.impl.PayThirdInfoDAOImpl.java  
 *   
 * 2015年3月2日 下午3:16:47  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.KeyHolder;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayInstitution;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.dao.IPayThirdInfoDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月2日 下午3:16:47
 */

public class PayThirdInfoDAOImpl extends BaseJdbcDao<TPayThirdOrgInfo> implements
		IPayThirdInfoDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PAY_THIRDORG_INFO (PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,DEVICES,REMARK,TN,TNTIME,QUERYID,PAYORG,ACCNO) VALUES (:id,:passid,:oid,:otype,:payno,:name,:amount,:direction,:paytype,:patytime,:status,:devices,:remark,:tn,:tnTime,:queryId,:payInstitution,:payAccountNo) ";
	private static final String UPDATE_SQL = " UPDATE T_PAY_THIRDORG_INFO SET PASSID = :passid,OID = :oid,OTYPE = :otype,PAYNO = :payno,NAME = :name,AMOUNT = :amount,DIRECTION = :direction,PAYTYPE = :paytype,PATYTIME = :patytime,STATUS = :status, DEVICES = :devices,REMARK = :remark,TN = :tn,TNTIME = :tnTime,QUERYID = :queryId,PAYORG = :payInstitution,ACCNO = :payAccountNo WHERE PID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PAY_THIRDORG_INFO WHERE PID = :id ";
	private static final String SELECT_SQL = " SELECT PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,DIRECTION,PAYTYPE,PATYTIME,STATUS,DEVICES,REMARK,TN,TNTIME,QUERYID,PAYORG,ACCNO FROM T_PAY_THIRDORG_INFO ";
	
	private String dynamicJoinSqlWithEntity(TPayThirdOrgInfo entity,StringBuilder sql){
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
		this.addNameParamerSqlWithProperty(sql, "tn", "TN", entity.getTn());
		this.addNameParamerSqlWithProperty(sql, "tnTime", "TNTIME", entity.getTnTime());
		this.addNameParamerSqlWithProperty(sql, "queryId", "QUERYID", entity.getQueryId());
		this.addNameParamerSqlWithProperty(sql, "payInstitution", "PAYORG", entity.getPayInstitution());
		this.addNameParamerSqlWithProperty(sql, "payAccountNo", "ACCNO", entity.getPayAccountNo());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TPayThirdOrgInfo entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TPayThirdOrgInfo entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TPayThirdOrgInfo entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPayThirdOrgInfo entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPayThirdOrgInfo query(TPayThirdOrgInfo entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TPayThirdOrgInfo query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForList(TPayThirdOrgInfo entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPayThirdOrgInfo> queryListForPagination(
			QueryContext<TPayThirdOrgInfo> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TPayThirdOrgInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPayThirdOrgInfo entity = new TPayThirdOrgInfo();
		
		entity.setId(rs.getString("PID"));
		entity.setPassid(rs.getString("PASSID"));
		entity.setOid(rs.getString("OID"));
		entity.setOtype(TradeType.enumOf(rs.getString("OTYPE")));
		entity.setPayno(rs.getString("PAYNO"));
		entity.setName(rs.getString("NAME"));
		entity.setAmount(rs.getDouble("AMOUNT"));
		entity.setDirection(PayDirection.enumOf(rs.getInt("DIRECTION")));
		entity.setPaytype(PayWay.enumOf(rs.getString("PAYTYPE")));
		entity.setPatytime(rs.getTimestamp("PATYTIME"));
		entity.setStatus(TradeStatus.enumOf(rs.getString("STATUS")));
		entity.setDevices(DeviceType.enumOf(rs.getString("DEVICES")));
		entity.setRemark(rs.getString("REMARK"));
		entity.setTn(rs.getString("TN"));
		entity.setTnTime(rs.getString("TNTIME"));
		entity.setQueryId(rs.getString("QUERYID"));
		entity.setPayAccountNo(rs.getString("ACCNO"));
		entity.setPayInstitution(PayInstitution.enumOf(rs.getString("PAYORG")));
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPayThirdInfoDAO#queryForListWithStatus(com.appabc.bean.enums.PurseInfo.TradeStatus)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForListWithStatus(TradeStatus status) {
		if(status == null){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "STATUS", status.getVal(), args);
		sql.append(" ORDER BY PATYTIME DESC ");
		return super.queryForList(sql.toString(), args);
	}

}
