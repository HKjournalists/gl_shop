package com.appabc.datas.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.bean.pvo.TSystemLog;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.ISystemLogDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午2:41:44
 */

@Component(value="ISystemLogDAO")
public class SystemLogDAOImpl extends BaseJdbcDao<TSystemLog> implements ISystemLogDAO {

	private static final String INSERT_SQL = " INSERT INTO T_SYSTEM_LOG (BUSINESSID,BUSINESSTYPE,LOGCONTENT,LOGTYPE,LOGLEVEL,LOGSTATUS,CREATETIME,CREATER) VALUES (:businessid,:businesstype,:logcontent,:logtype,:loglevel,:logstatus,:createtime,:creater) ";
	private static final String UPDATE_SQL = " UPDATE T_SYSTEM_LOG SET BUSINESSID = :businessid,BUSINESSTYPE = :businesstype,LOGCONTENT = :logcontent,LOGTYPE = :logtype,LOGLEVEL = :loglevel,LOGSTATUS = :logstatus,CREATETIME = :createtime,CREATER= :creater WHERE LOGID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_SYSTEM_LOG WHERE LOGID = :id ";
	private static final String SELECT_SQL = " SELECT LOGID,BUSINESSID,BUSINESSTYPE,LOGCONTENT,LOGTYPE,LOGLEVEL,LOGSTATUS,CREATETIME,CREATER FROM T_SYSTEM_LOG ";
	private static final String BASE_SQL = " SELECT * FROM T_SYSTEM_LOG ";
	
	private static final String COUNT_LOGIN_USER_OF_DATE = "SELECT COUNT(0) from (SELECT sl.BUSINESSID from T_SYSTEM_LOG sl WHERE sl.BUSINESSTYPE IN('101','105') and sl.LOGLEVEL=3 AND CREATETIME<:endTime AND CREATETIME>:startTime GROUP BY sl.BUSINESSID) a";
	
	private String dynamicJoinSqlWithEntity(TSystemLog entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "LOGID", entity.getId());
		addNameParamerSqlWithProperty(sql, "businessid", "BUSINESSID", entity.getBusinessid());
		addNameParamerSqlWithProperty(sql, "businesstype", "BUSINESSTYPE", entity.getBusinesstype());
		addNameParamerSqlWithProperty(sql, "logcontent", "LOGCONTENT", entity.getLogcontent());
		addNameParamerSqlWithProperty(sql, "logtype", "LOGTYPE", entity.getLogtype());
		addNameParamerSqlWithProperty(sql, "loglevel", "LOGLEVEL", entity.getLoglevel());
		addNameParamerSqlWithProperty(sql, "logstatus", "LOGSTATUS", entity.getLogstatus());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getLogstatus());
		addNameParamerSqlWithProperty(sql, "creater", "CREATER", entity.getLogstatus());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TSystemLog entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TSystemLog entity) {
		entity.setCreatetime(Calendar.getInstance().getTime());
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TSystemLog entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TSystemLog entity) {
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
	public TSystemLog query(TSystemLog entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TSystemLog query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE LOGID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TSystemLog> queryForList(TSystemLog entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TSystemLog> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TSystemLog> queryListForPagination(
			QueryContext<TSystemLog> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TSystemLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		TSystemLog bean = new TSystemLog();
		
		bean.setId(rs.getString("LOGID"));
		bean.setBusinessid(rs.getString("BUSINESSID"));
		bean.setBusinesstype(LogBusinessType.enumOf(rs.getString("BUSINESSTYPE")));
		bean.setLogcontent(rs.getString("LOGCONTENT"));
		bean.setLogtype(rs.getInt("LOGTYPE"));
		bean.setLoglevel(LogLevel.enumOf(rs.getInt("LOGLEVEL")));
		bean.setLogstatus(rs.getInt("LOGSTATUS"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setCreater(rs.getString("CREATER"));
		
		return bean;
	}
	
	public int queryCountLoginUserOfDate(Date date){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		
		paramMap.put("startTime", c.getTime());
		
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		paramMap.put("endTime", c.getTime());
		
		Number number = super.getNamedParameterJdbcTemplate().queryForObject(COUNT_LOGIN_USER_OF_DATE, paramMap, Integer.class);  
	    return (number != null ? number.intValue() : 0);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.system.ISystemLogDAO#queryRecentOfOneRecord(com.appabc.bean.pvo.TSystemLog)
	 */
	@Override
	public TSystemLog queryRecentOfOneRecord(TSystemLog entity) {
		String sql = dynamicJoinSqlWithEntity(entity, new StringBuilder(BASE_SQL)) + " ORDER BY CREATETIME DESC LIMIT 0,1";
		return super.query(sql.toString(), entity);
	}

}
