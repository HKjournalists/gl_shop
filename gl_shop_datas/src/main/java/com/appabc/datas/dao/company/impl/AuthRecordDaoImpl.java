/**
 *
 */
package com.appabc.datas.dao.company.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.IAuthRecordDao;

/**
 * @Description : 认证记录DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:59:03
 */
@Repository
public class AuthRecordDaoImpl extends BaseJdbcDao<TAuthRecord> implements IAuthRecordDao {
	
	private static final String INSERTSQL = " insert into T_AUTH_RECORD (CID, IMGID, AUTHSTATUS, DEALSTATUS, CREATEDATE, AUTHOR, AUTHRESULT, AUTHDATE, REMARK, TYPE, ABID) values (:cid, :imgid, :authstatus, :dealstatus, :createdate, :author, :authresult, :authdate, :remark, :type, :abid) ";
	private static final String UPDATESQL = " update T_AUTH_RECORD set CID = :cid, IMGID = :imgid, AUTHSTATUS = :authstatus, DEALSTATUS = :dealstatus, CREATEDATE = :createdate, AUTHOR = :author, AUTHRESULT = :authresult, AUTHDATE = :authdate, REMARK = :remark, TYPE = :type, ABID=:abid where AUTHID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_AUTH_RECORD WHERE 1=1 "; 

	public void save(TAuthRecord entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TAuthRecord entity) {
		return null;
	}

	public void update(TAuthRecord entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TAuthRecord entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TAuthRecord query(TAuthRecord entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public TAuthRecord query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TAuthRecord> queryForList(TAuthRecord entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TAuthRecord> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TAuthRecord> queryListForPagination(
			QueryContext<TAuthRecord> qContext) {
		return null;
	}

	public TAuthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		TAuthRecord t = new TAuthRecord();
		
		t.setId(rs.getString("AUTHID"));
		t.setAuthstatus(rs.getString("AUTHSTATUS"));
		t.setAuthdate(rs.getTimestamp("AUTHDATE"));
		t.setAuthor(rs.getString("AUTHOR"));
		t.setAuthresult(rs.getString("AUTHRESULT"));
		t.setCid(rs.getString("CID"));
		t.setCreatedate(rs.getTimestamp("CREATEDATE"));
		t.setDealstatus(rs.getInt("DEALSTATUS"));
		t.setImgid(rs.getInt("IMGID"));
		t.setRemark(rs.getString("REMARK"));
		t.setType(rs.getInt("TYPE"));
		t.setAbid(rs.getString("ABID"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TAuthRecord bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "AUTHID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "authstatus", "AUTHSTATUS", bean.getAuthstatus());
		this.addNameParamerSqlWithProperty(sql, "author", "AUTHOR", bean.getAuthor());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "dealstatus", "DEALSTATUS", bean.getDealstatus());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", bean.getType());
		this.addNameParamerSqlWithProperty(sql, "abid", "ABID", bean.getAbid());
		return sql.toString();
	}

}
