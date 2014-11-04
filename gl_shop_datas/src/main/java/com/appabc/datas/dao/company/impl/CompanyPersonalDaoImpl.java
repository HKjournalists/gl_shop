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

import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyPersonalDao;

/**
 * @Description : 个人认证信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午9:43:26
 */
@Repository
public class CompanyPersonalDaoImpl extends BaseJdbcDao<TCompanyPersonal> implements ICompanyPersonalDao {
	
	private static final String INSERTSQL = " insert into T_COMPANY_PERSONAL (AUTHID, CPNAME, SEX, IDENTIFICATION, ORIGO, CRATEDATE, UPDATEDATE, REMARK ) values (:authid, :cpname, :sex, :identification, :origo, :cratedate, :updatedate, :remark ) ";
	private static final String UPDATESQL = " update T_COMPANY_PERSONAL set AUTHID = :authid, CPNAME = :cpname, SEX = :sex, IDENTIFICATION = :identification, ORIGO = :origo, CRATEDATE = :cratedate, UPDATEDATE = :updatedate, REMARK = :remark  where CPID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_PERSONAL WHERE CPID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_PERSONAL WHERE CPID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_PERSONAL WHERE 1=1 "; 

	public void save(TCompanyPersonal entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyPersonal entity) {
		return null;
	}

	public void update(TCompanyPersonal entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyPersonal entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyPersonal query(TCompanyPersonal entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public TCompanyPersonal query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyPersonal> queryForList(TCompanyPersonal entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TCompanyPersonal> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyPersonal> queryListForPagination(
			QueryContext<TCompanyPersonal> qContext) {
		return null;
	}

	public TCompanyPersonal mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyPersonal t = new TCompanyPersonal();
		
		t.setId(rs.getString("CPID"));
		t.setAuthid(rs.getInt("AUTHID"));
		t.setCpname(rs.getString("CPNAME"));
		t.setCratedate(rs.getTimestamp("CRATEDATE"));
		t.setIdentification(rs.getString("IDENTIFICATION"));
		t.setOrigo(rs.getString("ORIGO"));
		t.setRemark(rs.getString("REMARK"));
		t.setSex(rs.getInt("SEX"));
		t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TCompanyPersonal bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "CPID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "authid", "AUTHID", bean.getAuthid());
		this.addNameParamerSqlWithProperty(sql, "cpname", "CPNAME", bean.getCpname());
		this.addNameParamerSqlWithProperty(sql, "identification", "IDENTIFICATION", bean.getIdentification());
		this.addNameParamerSqlWithProperty(sql, "origo", "ORIGO", bean.getOrigo());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "sex", "SEX", bean.getSex());
		return sql.toString();
	}

}
