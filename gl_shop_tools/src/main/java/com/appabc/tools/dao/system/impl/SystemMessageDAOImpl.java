package com.appabc.tools.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.system.ISystemMessageDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午2:39:11
 */

@Component(value="ISystemMessageDAO")
public class SystemMessageDAOImpl extends BaseJdbcDao<TSystemMessage> implements
		ISystemMessageDAO {

	private static final String INSERT_SQL = " INSERT INTO T_SYSTEM_MESSAGE (MSGID,QYID,CONTENT,TYPE,BUSINESSID,BUSINESSTYPE,STATUS,CREATETIME,READTIME) VALUES (:id,:qyid,:content,:type,:businessid,:businesstype,:status,:createtime,:readtime) ";
	private static final String UPDATE_SQL = " UPDATE T_SYSTEM_MESSAGE SET QYID = :qyid,CONTENT = :content,TYPE = :type,BUSINESSID = :businessid,BUSINESSTYPE = :businesstype,STATUS = :status,CREATETIME = :createtime,READTIME= :readtime WHERE MSGID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_SYSTEM_MESSAGE WHERE MSGID = :id ";
	private static final String SELECT_SQL = " SELECT MSGID,QYID,CONTENT,TYPE,BUSINESSID,BUSINESSTYPE,STATUS,CREATETIME,READTIME FROM T_SYSTEM_MESSAGE ";
	private static final String COUNT_SQL = " SELECT COUNT(0) AS COUNTNUM FROM T_SYSTEM_MESSAGE WHERE 1=1 ";
	
	private String dynamicJoinSqlWithEntity(TSystemMessage entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "MSGID", entity.getId());
		addNameParamerSqlWithProperty(sql, "qyid", "QYID", entity.getQyid());
		addNameParamerSqlWithProperty(sql, "content", "CONTENT", entity.getContent());
		addNameParamerSqlWithProperty(sql, "type", "TYPE", entity.getType());
		addNameParamerSqlWithProperty(sql, "businessid", "BUSINESSID", entity.getBusinessid());
		addNameParamerSqlWithProperty(sql, "businesstype", "BUSINESSTYPE", entity.getBusinesstype());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "readtime", "READTIME", entity.getReadtime());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TSystemMessage entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TSystemMessage entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TSystemMessage entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TSystemMessage entity) {
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
	public TSystemMessage query(TSystemMessage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TSystemMessage query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE MSGID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TSystemMessage> queryForList(TSystemMessage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TSystemMessage> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TSystemMessage> queryListForPagination(
			QueryContext<TSystemMessage> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		
		dynamicJoinSqlWithEntity(qContext.getBeanParameter(), sql);
		addNameParamerSqlWithProperty(sql, "cid", "QYID", qContext.getParameter("cid"));
		qContext.setOrder("DESC");
		qContext.setOrderColumn("CREATETIME");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TSystemMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
		TSystemMessage bean = new TSystemMessage();
		
		bean.setId(rs.getString("MSGID"));
		bean.setQyid(rs.getString("QYID"));
		bean.setContent(rs.getString("CONTENT"));
		bean.setType(MsgType.enumOf(rs.getInt("TYPE")));
		bean.setBusinessid(rs.getString("BUSINESSID"));
		bean.setBusinesstype(MsgBusinessType.enumOf(rs.getString("BUSINESSTYPE")));
		bean.setStatus(MsgStatus.enumOf(rs.getInt("STATUS")));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setReadtime(rs.getTimestamp("READTIME"));
		
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.system.ISystemMessageDAO#getCountByEntity(com.appabc.bean.pvo.TSystemMessage)
	 */
	@SuppressWarnings("deprecation")
	public int getCountByEntity(TSystemMessage entity) {
		
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		
		List<Object> args = new ArrayList<Object>();
		
		super.addStandardSqlWithParameter(sql, "QYID", entity.getQyid(), args);
		super.addStandardSqlWithParameter(sql, "STATUS", entity.getStatus(), args);
		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

}
