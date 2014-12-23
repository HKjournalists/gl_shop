/**
 *
 */
package com.appabc.datas.dao.company.impl;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.IAuthRecordDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public final static AuthRecordRowMapper ROW_MAPPER = new AuthRecordRowMapper();

	private static final String INSERTSQL = " insert into T_AUTH_RECORD (CID, IMGID, AUTHSTATUS, DEALSTATUS, CREATEDATE, AUTHOR, AUTHRESULT, AUTHDATE, REMARK, TYPE, ABID) values (:cid, :imgid, :authstatus, :dealstatus, :createdate, :author, :authresult, :authdate, :remark, :type, :abid) ";
	private static final String UPDATESQL = " update T_AUTH_RECORD set CID = :cid, IMGID = :imgid, AUTHSTATUS = :authstatus, DEALSTATUS = :dealstatus, CREATEDATE = :createdate, AUTHOR = :author, AUTHRESULT = :authresult, AUTHDATE = :authdate, REMARK = :remark, TYPE = :type, ABID=:abid where AUTHID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_AUTH_RECORD WHERE 1=1 ";

	private static final String BASE_SQL = " SELECT * FROM T_AUTH_RECORD WHERE 1=1 ";

	public void save(TAuthRecord entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TAuthRecord entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TAuthRecord entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TAuthRecord entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TAuthRecord query(TAuthRecord entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public TAuthRecord query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TAuthRecord> queryForList(TAuthRecord entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TAuthRecord> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TAuthRecord> queryListForPagination(
			QueryContext<TAuthRecord> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TAuthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ROW_MAPPER.mapRow(rs, rowNum);
	}

	private String dynamicJoinSqlWithEntity(TAuthRecord bean,StringBuilder sql){
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

	private static class AuthRecordRowMapper implements RowMapper<TAuthRecord> {
		@Override
		public TAuthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
			TAuthRecord t = new TAuthRecord();

			t.setId(rs.getString("AUTHID"));
			t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("AUTHSTATUS")));
			t.setAuthdate(rs.getTimestamp("AUTHDATE"));
			t.setAuthor(rs.getString("AUTHOR"));
			t.setAuthresult(rs.getString("AUTHRESULT"));
			t.setCid(rs.getString("CID"));
			t.setCreatedate(rs.getTimestamp("CREATEDATE"));
			t.setDealstatus(rs.getInt("DEALSTATUS"));
			t.setImgid(rs.getInt("IMGID"));
			t.setRemark(rs.getString("REMARK"));
			t.setType(AuthRecordType.enumOf(rs.getInt("TYPE")));
			t.setAbid(rs.getString("ABID"));

			return t;
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.company.IAuthRecordDao#getCountByCidAndAuthstauts(java.lang.String, com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getCountByCidAndAuthstauts(String cid, AuthRecordStatus austatus) {
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		List<Object> args = new ArrayList<Object>();
		
		if(austatus != null){
			sql.append(" AND AUTHSTATUS=?");
			args.add(austatus.getVal());
		}
		sql.append(" AND CID=?");
		args.add(cid);

		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

}
