package com.appabc.tools.dao.user.impl;

import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.enums.UserInfo.UserStatus;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.user.IToolUserDao;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年8月23日 下午3:49:55
 */

@Repository
public class ToolUserDaoImpl extends BaseJdbcDao<TUser> implements IToolUserDao {

	private static final String INSERTSQL = " INSERT INTO T_USER (CID,USERNAME,PASSWORD,NICK,PHONE,LOGO,STATUS,CREATEDATE,UPDATEDATE,CLIENTID,CLIENTTYPE) VALUES (:cid,:username,:password,:nick,:phone,:logo,:status,:createdate,:updatedate,:clientid,:clienttype) ";
	private static final String UPDATESQL = " UPDATE T_USER SET CID = :cid,USERNAME = :username,PASSWORD = :password,NICK = :nick,PHONE = :phone,LOGO = :logo,STATUS = :status,CREATEDATE = :createdate,UPDATEDATE = :updatedate,CLIENTID=:clientid,CLIENTTYPE=:clienttype WHERE ID = :id ";
	private static final String DELETESQL = " DELETE FROM T_USER WHERE ID = :id ";
	private static final String SELECTSQL = " SELECT * FROM T_USER where 1=1 ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_USER WHERE ID = :id ";
	private static final String SELECTSQLBYUSERANDPASS = " SELECT * FROM T_USER WHERE PASSWORD = :password AND USERNAME = :username ";
	
	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		TUser user = new TUser();
		user.setId(rs.getString("ID"));
		user.setCid(rs.getString("CID"));
		user.setNick(rs.getString("NICK"));
		user.setUsername(rs.getString("USERNAME"));
		user.setPhone(rs.getString("PHONE"));
		user.setCreatedate(rs.getTimestamp("CREATEDATE"));
		user.setLogo(rs.getString("LOGO"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setStatus(UserStatus.enumOf(rs.getString("STATUS")));
		user.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		user.setClientid(rs.getString("CLIENTID"));
		user.setClienttype(ClientTypeEnum.enumOf(rs.getString("CLIENTTYPE")));
		return user;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TUser entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TUser entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TUser entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TUser entity) {
		super.delete(DELETESQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.lang.String)  
	 */
	public void delete(Serializable id) {
		super.delete(DELETESQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TUser query(TUser entity) {
		return super.query(dynamicJoinSqlWithEntity(entity, new StringBuilder(SELECTSQL)).toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.lang.String)  
	 */
	public TUser query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TUser> queryForList(TUser entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity, new StringBuilder(SELECTSQL)).toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TUser> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECTSQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TUser> queryListForPagination(QueryContext<TUser> qContext) {
		return super.queryListForPagination(SELECTSQL, qContext);
	}

	/* (non-Javadoc)根据用户名密码查询用户
	 * @see com.appabc.datas.dao.user.IUserDao#queryByNameAndPass(java.lang.String, java.lang.String)
	 */
	public TUser queryByNameAndPass(String username, String password) {
		
		Map<String, String> args = new HashMap<String, String>();
		args.put("username", username);
		args.put("password", password);
		
		List<TUser> userList = super.queryForList(SELECTSQLBYUSERANDPASS, args);
		if(userList != null && userList.size()>0){
			return userList.get(0);
		}
		
		return null;
	}
	
	private String dynamicJoinSqlWithEntity(TUser bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "username", "USERNAME", bean.getUsername());
		this.addNameParamerSqlWithProperty(sql, "password", "PASSWORD", bean.getPassword());
		this.addNameParamerSqlWithProperty(sql, "nick", "NICK", bean.getNick());
		this.addNameParamerSqlWithProperty(sql, "phone", "PHONE", bean.getPhone());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "clientid", "CLIENTID", bean.getClientid());
		this.addNameParamerSqlWithProperty(sql, "clienttype", "CLIENTTYPE", bean.getClienttype());
		return sql.toString();
	}

}
