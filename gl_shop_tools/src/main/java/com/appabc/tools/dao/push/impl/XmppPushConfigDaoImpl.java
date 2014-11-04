/**
 *
 */
package com.appabc.tools.dao.push.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPushConfig;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.push.IPushConfigDao;

/**
 * @Description : 消息推送账号信息配置DAO
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午5:19:05
 */
@Repository
public class XmppPushConfigDaoImpl extends BaseJdbcDao<TPushConfig> implements IPushConfigDao {
	
	private static final String INSERTSQL = " insert into T_PUSH_CONFIG (APPID, APPKEY, APPSECRET, MASTERSECRET, URL, STATUS, TYPE, UPDATER, UPDATETIIME, PORT, CERTIFICATEPATH) values (:appid, :appkey, :appsecret, :mastersecret, :url, :status, :type, :updater, :updatetiime, :port, :certificatepath) ";
	private static final String UPDATESQL = " update T_PUSH_CONFIG set APPID = :appid, APPKEY = :appkey, APPSECRET = :appsecret, MASTERSECRET = :mastersecret, URL = :url, STATUS = :status, TYPE = :type, UPDATER = :updater, UPDATETIIME = :updatetiime, PORT=:port, CERTIFICATEPATH=:certificatepath  where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_PUSH_CONFIG WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PUSH_CONFIG WHERE ID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_PUSH_CONFIG WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void save(TPushConfig entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	
	public KeyHolder saveAutoGenerateKey(TPushConfig entity) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void update(TPushConfig entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void delete(TPushConfig entity) {
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
	
	public TPushConfig query(TPushConfig entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	
	public TPushConfig query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	
	public List<TPushConfig> queryForList(TPushConfig entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	
	public List<TPushConfig> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	
	public QueryContext<TPushConfig> queryListForPagination(
			QueryContext<TPushConfig> qContext) {
		return null;
	}

	public TPushConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPushConfig t = new TPushConfig();
		
		t.setId(rs.getString("ID"));
		t.setAppid(rs.getString("APPID"));
		t.setAppkey(rs.getString("APPKEY"));
		t.setAppsecret(rs.getString("APPSECRET"));
		t.setMastersecret(rs.getString("MASTERSECRET"));
		t.setStatus(rs.getInt("STATUS"));
		t.setType(rs.getString("TYPE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetiime(rs.getTimestamp("UPDATETIIME"));
		t.setUrl(rs.getString("URL"));
		t.setPort(rs.getInt("PORT"));
		t.setCertificatepath(rs.getString("CERTIFICATEPATH"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TPushConfig bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "appid", "APPID", bean.getAppid());
		this.addNameParamerSqlWithProperty(sql, "appkey", "APPKEY", bean.getAppkey());
		this.addNameParamerSqlWithProperty(sql, "appsecret", "APPSECRET", bean.getAppsecret());
		this.addNameParamerSqlWithProperty(sql, "mastersecret", "MASTERSECRET", bean.getMastersecret());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", bean.getType());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "updatetiime", "UPDATETIIME", bean.getUpdatetiime());
		this.addNameParamerSqlWithProperty(sql, "url", "URL", bean.getUrl());
		this.addNameParamerSqlWithProperty(sql, "port", "PORT", bean.getPort());
		this.addNameParamerSqlWithProperty(sql, "certificatepath", "CERTIFICATEPATH", bean.getCertificatepath());
		return sql.toString();
	}


}
