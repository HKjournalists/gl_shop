/**
 *
 */
package com.appabc.datas.dao.push.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPushResult;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.push.IPushResultDao;

/**
 * @Description : 消息推送结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午8:40:34
 */
@Repository
public class PushResultDaoImpl extends BaseJdbcDao<TPushResult> implements IPushResultDao {
	
	private static final String INSERTSQL = " insert into T_PUSH_RESULT (MSGTYPE, MSGTITLE, MSGCONTENT, PUSHTYPE, PUSHTARGET, PUSHSTATUS, PUSHTIME, RESULTCODE, RESULTCONTENT, REMARK) values (:msgtype, :msgtitle, :msgcontent, :pushtype, :pushtarget, :pushstatus, :pushtime, :resultcode, :resultcontent, :remark) ";
	private static final String UPDATESQL = " update T_PUSH_RESULT set MSGTYPE = :msgtype, MSGTITLE = :msgtitle, MSGCONTENT = :msgcontent, PUSHTYPE = :pushtype, PUSHTARGET = :pushtarget, PUSHSTATUS = :pushstatus, PUSHTIME = :pushtime, RESULTCODE = :resultcode, RESULTCONTENT = :resultcontent, REMARK = :remark where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_PUSH_RESULT WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PUSH_RESULT WHERE ID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_PUSH_RESULT WHERE 1=1 ";


	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void save(TPushResult entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	
	public KeyHolder saveAutoGenerateKey(TPushResult entity) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void update(TPushResult entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void delete(TPushResult entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	
	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	
	public TPushResult query(TPushResult entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	
	public TPushResult query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	
	public List<TPushResult> queryForList(TPushResult entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	
	public List<TPushResult> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	
	public QueryContext<TPushResult> queryListForPagination(
			QueryContext<TPushResult> qContext) {
		return null;
	}

	public TPushResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPushResult t = new TPushResult();
		
		t.setId(rs.getString("ID"));
		t.setMsgcontent(rs.getString("MSGCONTENT"));
		t.setMsgtitle(rs.getString("MSGTITLE"));
		t.setMsgtype(rs.getInt("MSGTYPE"));
		t.setPushstatus(rs.getInt("PUSHSTATUS"));
		t.setPushtarget(rs.getString("PUSHTARGET"));
		t.setPushtime(rs.getTimestamp("PUSHTIME"));
		t.setPushtype(rs.getInt("PUSHTYPE"));
		t.setRemark(rs.getString("REMARK"));
		t.setResultcode(rs.getString("RESULTCODE"));
		t.setResultcontent(rs.getString("RESULTCONTENT"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TPushResult bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "msgcontent", "MSGCONTENT", bean.getMsgcontent());
		this.addNameParamerSqlWithProperty(sql, "msgtitle", "MSGTITLE", bean.getMsgtitle());
		this.addNameParamerSqlWithProperty(sql, "msgtype", "MSGTYPE", bean.getMsgtype());
		this.addNameParamerSqlWithProperty(sql, "pushstatus", "PUSHSTATUS", bean.getPushstatus());
		this.addNameParamerSqlWithProperty(sql, "pushtarget", "PUSHTARGET", bean.getPushtarget());
		this.addNameParamerSqlWithProperty(sql, "pushtime", "PUSHTIME", bean.getPushtime());
		this.addNameParamerSqlWithProperty(sql, "pushtype", "PUSHTYPE", bean.getPushtype());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "resultcode", "RESULTCODE", bean.getResultcode());
		this.addNameParamerSqlWithProperty(sql, "resultcontent", "RESULTCONTENT", bean.getResultcontent());
		return sql.toString();
	}

}
