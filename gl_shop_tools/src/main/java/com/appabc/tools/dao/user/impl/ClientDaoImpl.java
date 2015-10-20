/**
 *
 */
package com.appabc.tools.dao.user.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.ClientEnum.ChannelType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TClient;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.user.IClientDao;

/**
 * @Description : 客户端DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月11日 下午5:59:01
 */
@Repository
public class ClientDaoImpl extends BaseJdbcDao<TClient> implements IClientDao{

	private static final String INSERTSQL = " insert into T_CLIENT (CLIENTID, CLIENTTYPE, USERNAME, BADGE, UPDATETIME, CHANNELTYPE) values (:clientid, :clienttype, :username, :badge, :updatetime, :channeltype) ";
	private static final String UPDATESQL = " update T_CLIENT set CLIENTID = :clientid, CLIENTTYPE = :clienttype, USERNAME = :username, BADGE = :badge, UPDATETIME = :updatetime, CHANNELTYPE=:channeltype where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_CLIENT WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_CLIENT WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_CLIENT WHERE 1=1 ";

	public void save(TClient entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TClient entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TClient entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TClient entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TClient query(TClient entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TClient query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TClient> queryForList(TClient entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TClient> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TClient> queryListForPagination(
			QueryContext<TClient> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TClient mapRow(ResultSet rs, int rowNum) throws SQLException {
		TClient t = new TClient();

		t.setId(rs.getString("ID"));
		t.setBadge(rs.getInt("BADGE"));
		t.setClientid(rs.getString("CLIENTID"));
		t.setClienttype(ClientTypeEnum.enumOf(rs.getString("CLIENTTYPE")));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setUsername(rs.getString("USERNAME"));
		t.setChanneltype(ChannelType.enumOf(rs.getInt("CHANNELTYPE")));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TClient bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "badge", "BADGE", bean.getBadge());
		this.addNameParamerSqlWithProperty(sql, "clientid", "CLIENTID", bean.getClientid());
		this.addNameParamerSqlWithProperty(sql, "clienttype", "CLIENTTYPE", bean.getClienttype());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", bean.getUpdatetime());
		this.addNameParamerSqlWithProperty(sql, "username", "USERNAME", bean.getUsername());
		this.addNameParamerSqlWithProperty(sql, "channeltype", "CHANNELTYPE", bean.getChanneltype());
		return sql.toString();
	}

}
