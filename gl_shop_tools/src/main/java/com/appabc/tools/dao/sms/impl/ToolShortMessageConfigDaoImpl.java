/**
 *
 */
package com.appabc.tools.dao.sms.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.sms.IToolShortMessageConfigDao;

/**
 * @Description : 短信账号配置信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月4日 下午5:53:00
 */
@Repository
public class ToolShortMessageConfigDaoImpl extends BaseJdbcDao<TShortMessageConfig> implements IToolShortMessageConfigDao{
	
	private static final String INSERTSQL = " insert into T_SHORT_MESSAGE_CONFIG (SUSER, SPWD, SURL, STATUS, TYPE, UPDATER, UPDATETIIME, TEMPLATEID, TEMPLATETYPE, TEMPLATEPARAM, TOKENURL) values (:suser, :spwd, :surl, :status, :type, :updater, :updatetiime, :templateid, :templatetype,:templateparam,:tokenurl) ";
	private static final String UPDATESQL = " update T_SHORT_MESSAGE_CONFIG set SUSER = :suser, SPWD = :spwd, SURL = :surl, STATUS = :status, TYPE = :type, UPDATER = :updater, UPDATETIIME = :updatetiime, TEMPLATEID=:templateid, TEMPLATETYPE=:templatetype, TEMPLATEPARAM=:templateparam, TOKENURL :tokenurl where SMCID = :smcid ";
	private static final String DELETESQLBYID = " DELETE FROM T_SHORT_MESSAGE_CONFIG where SMCID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_SHORT_MESSAGE_CONFIG WHERE SMCID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_SHORT_MESSAGE_CONFIG WHERE 1=1 "; 

	public void save(TShortMessageConfig entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TShortMessageConfig entity) {
		return null;
	}

	public void update(TShortMessageConfig entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TShortMessageConfig entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TShortMessageConfig query(TShortMessageConfig entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		if(entity.getStatus() != null){
			sql.append(" AND STATUS = :status");
		}
		if(entity.getType() != null){
			sql.append(" AND TYPE = :type");
		}
		if(entity.getType() != null){
			
			sql.append(" AND TYPE = :type");
		}
		if(entity.getTemplatetype() != null && entity.getTemplatetype().equals("")){
			sql.append(" AND TEMPLATETYPE = :templatetype");
		}
		List<TShortMessageConfig> list =  super.queryForList(sql.toString(), entity);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	public TShortMessageConfig query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TShortMessageConfig> queryForList(TShortMessageConfig entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		if(entity.getStatus() != null){
			sql.append(" AND STATUS = :status");
		}
		if(entity.getType() != null){
			sql.append(" AND TYPE = :type");
		}
		if(entity.getTemplatetype() != null && entity.getTemplatetype().equals("")){
			sql.append(" AND TEMPLATETYPE = :templatetype");
		}
		
		return super.queryForList(sql.toString(), entity);
	}

	public List<TShortMessageConfig> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TShortMessageConfig> queryListForPagination(
			QueryContext<TShortMessageConfig> qContext) {
		return null;
	}

	public TShortMessageConfig mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TShortMessageConfig t = new TShortMessageConfig();
		
		t.setId(rs.getString("SMCID"));
		t.setSpwd(rs.getString("SPWD"));
		t.setStatus(rs.getInt("STATUS"));
		t.setSurl(rs.getString("SURL"));
		t.setSuser(rs.getString("SUSER"));
		t.setTemplateparam(rs.getString("TEMPLATEPARAM"));
		t.setTemplateid(rs.getString("TEMPLATEID"));
		t.setTemplatetype(rs.getString("TEMPLATETYPE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetiime(rs.getTimestamp("UPDATETIIME"));
		t.setTokenurl(rs.getString("TOKENURL"));
		
		return t;
	}

}
