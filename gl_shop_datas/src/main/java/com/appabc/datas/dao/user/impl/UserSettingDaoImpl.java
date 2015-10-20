/**
 *
 */
package com.appabc.datas.dao.user.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TUserSetting;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.user.IUserSettingDao;

/**
 * @Description : 用户设置DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月7日 下午4:40:02
 */
@Repository
public class UserSettingDaoImpl extends BaseJdbcDao<TUserSetting> implements IUserSettingDao {
	
	private static final String INSERTSQL = " INSERT INTO T_USER_SETTING (CID,AUTHREMINDTIME) VALUES (:cid,:authremindtime) ";
	private static final String UPDATESQL = " UPDATE T_USER_SETTING SET CID = :cid,AUTHREMINDTIME = :authremindtime WHERE ID = :id ";
	private static final String DELETESQL = " DELETE FROM T_USER_SETTING WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_USER_SETTING WHERE ID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_USER_SETTING WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void save(TUserSetting entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TUserSetting entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void update(TUserSetting entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TUserSetting entity) {
		super.delete(DELETESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		super.delete(DELETESQL, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TUserSetting query(TUserSetting entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	@Override
	public TUserSetting query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TUserSetting> queryForList(TUserSetting entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	@Override
	public List<TUserSetting> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TUserSetting> queryListForPagination(
			QueryContext<TUserSetting> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public TUserSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
		TUserSetting t = new TUserSetting();

		t.setId(rs.getString("ID"));
		t.setCid(rs.getString("CID"));
		t.setAuthremindtime(rs.getTimestamp("AUTHREMINDTIME"));

		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TUserSetting bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0) {
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "authremindtime", "AUTHREMINDTIME", bean.getAuthremindtime());
		return sql.toString();
	}

}
