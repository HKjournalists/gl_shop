/**
 *
 */
package com.appabc.datas.dao.company.impl;

import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyAuthDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 企业认证信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午9:27:03
 */
@Repository
public class CompanyAuthDaoImpl extends BaseJdbcDao<TCompanyAuth> implements ICompanyAuthDao {

	private static final String INSERTSQL = " insert into T_COMPANY_AUTH (AUTHID, CNAME, ADDRESS, RDATE, LPERSON, ORGID, CTYPE, CRATEDATE, UPDATEDATE, REGNO) values (:authid, :cname, :address, :rdate, :lperson, :orgid, :ctype, :cratedate, :updatedate,:regno) ";
	private static final String UPDATESQL = " update T_COMPANY_AUTH set AUTHID = :authid, CNAME = :cname, ADDRESS = :address, RDATE = :rdate, LPERSON = :lperson, ORGID = :orgid, CTYPE = :ctype, CRATEDATE = :cratedate, UPDATEDATE = :updatedate,REGNO=:regno where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_AUTH WHERE AUTHID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_AUTH WHERE AUTHID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_AUTH WHERE 1=1 ";


	public void save(TCompanyAuth entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyAuth entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TCompanyAuth entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyAuth entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyAuth query(TCompanyAuth entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)) + " ORDER BY CRATEDATE DESC", entity);
	}

	public TCompanyAuth query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyAuth> queryForList(TCompanyAuth entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TCompanyAuth> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TCompanyAuth> queryListForPagination(
			QueryContext<TCompanyAuth> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TCompanyAuth mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyAuth t = new TCompanyAuth();

		t.setId(rs.getString("ID"));
		t.setAuthid(rs.getInt("authid"));
		t.setAddress(rs.getString("address"));
		t.setCname(rs.getString("cname"));
		t.setCratedate(rs.getTimestamp("cratedate"));
		t.setCtype(rs.getString("ctype"));
		t.setLperson(rs.getString("lperson"));
		t.setOrgid(rs.getString("orgid"));
		t.setRdate(rs.getString("rdate"));
		t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		t.setRegno(rs.getString("REGNO"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TCompanyAuth bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "authid", "AUTHID", bean.getAuthid());
		this.addNameParamerSqlWithProperty(sql, "address", "ADDRESS", bean.getAddress());
		this.addNameParamerSqlWithProperty(sql, "cname", "CNAME", bean.getCname());
		this.addNameParamerSqlWithProperty(sql, "ctype", "CTYPE", bean.getCtype());
		this.addNameParamerSqlWithProperty(sql, "lperson", "LPERSON", bean.getLperson());
		this.addNameParamerSqlWithProperty(sql, "orgid", "ORGID", bean.getOrgid());
		this.addNameParamerSqlWithProperty(sql, "rdate", "RDATE", bean.getRdate());
		this.addNameParamerSqlWithProperty(sql, "regno", "REGNO", bean.getRegno());
		return sql.toString();
	}

}
