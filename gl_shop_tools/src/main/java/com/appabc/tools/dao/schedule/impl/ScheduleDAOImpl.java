/**  
 * com.appabc.tools.dao.schedule.impl.ScheduleDAOImpl.java  
 *   
 * 2014年11月3日 下午3:05:12  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.dao.schedule.impl;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.bean.ScheduleInfoBean;
import com.appabc.tools.dao.schedule.IScheduleDAO;
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
 * @Create_Date  : 2014年11月3日 下午3:05:12
 */
@Repository
public class ScheduleDAOImpl extends BaseJdbcDao<ScheduleInfoBean> implements IScheduleDAO {

	private static final String INSERT_SQL = " INSERT INTO T_SCHEDULE_INFO (SCH_NAME,JOB_NAME,JOB_GROUP,JOBCLASSNAME,TRIGGERNAME,TRIGGERGROUP,TRIGGERCLASSNAME,ISVALID,CREATEDATE,REMARK) VALUES (:name,:jobName,:jobGroup,:jobClassName,:triggerName,:triggerGroup,:triggerClassName,:isValid,:createDate,:desc) ";
	private static final String UPDATE_SQL = " UPDATE T_SCHEDULE_INFO SET SCH_NAME = :name,JOB_NAME = :jobName,JOB_GROUP = :jobGroup,JOBCLASSNAME = :jobClassName,TRIGGERNAME = :triggerName,TRIGGERGROUP = :triggerGroup,TRIGGERCLASSNAME = :triggerClassName,ISVALID = :isValid,CREATEDATE = :createDate,REMARK = :desc WHERE SCHID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_SCHEDULE_INFO WHERE SCHID = :id ";
	private static final String SELECT_SQL = " SELECT SCHID,SCH_NAME,JOB_NAME,JOB_GROUP,JOBCLASSNAME,TRIGGERNAME,TRIGGERGROUP,TRIGGERCLASSNAME,ISVALID,CREATEDATE,REMARK FROM T_SCHEDULE_INFO ";
	
	private String dynamicJoinSqlWithEntity(ScheduleInfoBean entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "SCHID", entity.getId());
		addNameParamerSqlWithProperty(sql, "name", "SCH_NAME", entity.getName());
		addNameParamerSqlWithProperty(sql, "jobName", "JOB_NAME", entity.getJobName());
		addNameParamerSqlWithProperty(sql, "jobGroup", "JOB_GROUP", entity.getJobGroup());
		addNameParamerSqlWithProperty(sql, "jobClassName", "JOBCLASSNAME", entity.getJobClassName());
		addNameParamerSqlWithProperty(sql, "triggerName", "TRIGGERNAME", entity.getTriggerName());
		//here need to set the limit time 
		addNameParamerSqlWithProperty(sql, "triggerGroup", "TRIGGERGROUP", entity.getTriggerGroup());
		addNameParamerSqlWithProperty(sql, "triggerClassName", "TRIGGERCLASSNAME", entity.getTriggerClassName());
		//LIFECYCLE = :lifecycle
		addNameParamerSqlWithProperty(sql, "isValid", "ISVALID", entity.getIsValid());
		addNameParamerSqlWithProperty(sql, "createDate", "CREATEDATE", entity.getCreateDate());
		addNameParamerSqlWithProperty(sql, "desc", "REMARK", entity.getDesc());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(ScheduleInfoBean entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(ScheduleInfoBean entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(ScheduleInfoBean entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(ScheduleInfoBean entity) {
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
	public ScheduleInfoBean query(ScheduleInfoBean entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public ScheduleInfoBean query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<ScheduleInfoBean> queryForList(ScheduleInfoBean entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<ScheduleInfoBean> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ScheduleInfoBean> queryListForPagination(
			QueryContext<ScheduleInfoBean> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public ScheduleInfoBean mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ScheduleInfoBean bean = new ScheduleInfoBean();
		
		bean.setId(rs.getString("SCHID"));
		bean.setName(rs.getString("SCH_NAME"));
		bean.setDesc(rs.getString("REMARK"));
		bean.setJobName(rs.getString("JOB_NAME"));
		bean.setJobGroup(rs.getString("JOB_GROUP"));
		bean.setJobClassName(rs.getString("JOBCLASSNAME"));
		bean.setTriggerName(rs.getString("TRIGGERNAME"));
		bean.setTriggerGroup(rs.getString("TRIGGERGROUP"));
		bean.setTriggerClassName(rs.getString("TRIGGERCLASSNAME"));
		bean.setIsValid(rs.getBoolean("ISVALID"));
		bean.setCreateDate(rs.getTimestamp("CREATEDATE"));
		
		return bean;
	}

}
