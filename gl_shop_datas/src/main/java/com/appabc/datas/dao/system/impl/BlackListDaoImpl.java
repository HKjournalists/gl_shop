/**
 *
 */
package com.appabc.datas.dao.system.impl;

import com.appabc.bean.pvo.TBlackList;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IBlackListDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 黑名单
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午5:32:42
 */
@Repository
public class BlackListDaoImpl extends BaseJdbcDao<TBlackList> implements IBlackListDao {

	private static final String INSERTSQL = " insert into T_BLACK_LIST (BLID, USERNAME, IPADDRESS, STATUS, NUM, UPDATETIME, CREATER, CREATETIME) values (:blid, :username, :ipaddress, :status, :num, :updatetime, :creater, :createtime) ";
	private static final String UPDATESQL = " update T_BLACK_LIST set USERNAME = :username, IPADDRESS = :ipaddress, STATUS = :status, NUM = :num, UPDATETIME = :updatetime, CREATER = :creater, CREATETIME = :createtime where BLID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_BLACK_LIST WHERE BLID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_BLACK_LIST WHERE BLID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_BLACK_LIST WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TBlackList entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TBlackList entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TBlackList entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TBlackList entity) {
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
	public TBlackList query(TBlackList entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TBlackList query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TBlackList> queryForList(TBlackList entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TBlackList> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TBlackList> queryListForPagination(
			QueryContext<TBlackList> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TBlackList mapRow(ResultSet rs, int rowNum) throws SQLException {
		TBlackList t = new TBlackList();

		t.setId(rs.getString("BLID"));
		t.setCreater(rs.getString("CREATER"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		t.setIpaddress(rs.getString("IPADDRESS"));
		t.setNum(rs.getInt("NUM"));
		t.setStatus(rs.getInt("STATUS"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setUsername(rs.getString("USERNAME"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TBlackList bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "BLID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "creater", "CREATER", bean.getCreater());
		this.addNameParamerSqlWithProperty(sql, "ipaddress", "IPADDRESS", bean.getIpaddress());
		this.addNameParamerSqlWithProperty(sql, "num", "NUM", bean.getNum());
		this.addNameParamerSqlWithProperty(sql, "username", "USERNAME", bean.getUsername());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		return sql.toString();
	}

}
