package com.appabc.datas.dao.system.impl;

import com.appabc.bean.pvo.TQtFq;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IQtFqDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Description : 意见反馈
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 上午11:26:05
 */
@Repository
public class QtFqDaoImpl extends BaseJdbcDao<TQtFq> implements IQtFqDao {

	private static final String INSERTSQL = " insert into T_QT_FQ (FID, QUETSION, ASKID, CREATETIME, DEVICES, STATUS) values (:fid, :quetsion, :askid, :createtime, :devices, :status) ";
	private static final String UPDATESQL = " update T_QT_FQ set QUETSION = :quetsion, ASKID = :askid, CREATETIME = :createtime, DEVICES = :devices, STATUS = :status  where FID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_QT_FQ WHERE FID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_QT_FQ WHERE FID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_SYSTEM_PARAMS WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TQtFq entity) {
		entity.setCreatetime(Calendar.getInstance().getTime());
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TQtFq entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TQtFq entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TQtFq entity) {
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
	public TQtFq query(TQtFq entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TQtFq query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TQtFq> queryForList(TQtFq entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TQtFq> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TQtFq> queryListForPagination(
			QueryContext<TQtFq> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TQtFq mapRow(ResultSet rs, int rowNum) throws SQLException {
		TQtFq t = new TQtFq();

		t.setId(rs.getString("FID"));
		t.setAskid(rs.getString("ASKID"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		t.setDevices(rs.getString("DEVICES"));
		t.setQuetsion(rs.getString("QUETSION"));
		t.setStatus(rs.getInt("STATUS"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TQtFq bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "FID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "askid", "ASKID", bean.getAskid());
		this.addNameParamerSqlWithProperty(sql, "devices", "DEVICES", bean.getDevices());
		this.addNameParamerSqlWithProperty(sql, "quetsion", "QUETSION", bean.getQuetsion());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		return sql.toString();
	}
}
