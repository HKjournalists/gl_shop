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

import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyShippingDao;

/**
 * @Description : 船舶认证信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午8:40:07
 */
@Repository
public class CompanyShippingDaoImpl extends BaseJdbcDao<TCompanyShipping> implements ICompanyShippingDao {
	
	private static final String INSERTSQL = " insert into T_COMPANY_SHIPPING (AUTHID, SNAME, PREGISTRY, SNO, SORG, SOWNER, SBUSINESSER, STYPE, SCREATETIME, STOTAL, SLOAD, SLENGTH, SWIDTH, SDEEP, SOVER, SMATERIALl, UPDATEDATE) values (:authid, :sname, :pregistry, :sno, :sorg, :sowner, :sbusinesser, :stype, :screatetime, :stotal, :sload, :slength, :swidth, :sdeep, :sover, :smateriall, :updatedate) ";
	private static final String UPDATESQL = " update T_COMPANY_SHIPPING set AUTHID = :authid, SNAME = :sname, PREGISTRY = :pregistry, SNO = :sno, SORG = :sorg, SOWNER = :sowner, SBUSINESSER = :sbusinesser, STYPE = :stype, SCREATETIME = :screatetime, STOTAL = :stotal, SLOAD = :sload, SLENGTH = :slength, SWIDTH = :swidth, SDEEP = :sdeep, SOVER = :sover, SMATERIALl = :smateriall, UPDATEDATE = :updatedate where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_SHIPPING WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_SHIPPING WHERE ID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_SHIPPING WHERE 1=1 "; 

	public void save(TCompanyShipping entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyShipping entity) {
		return null;
	}

	public void update(TCompanyShipping entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyShipping entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyShipping query(TCompanyShipping entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public TCompanyShipping query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyShipping> queryForList(TCompanyShipping entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TCompanyShipping> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyShipping> queryListForPagination(
			QueryContext<TCompanyShipping> qContext) {
		return null;
	}

	public TCompanyShipping mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyShipping t = new TCompanyShipping();
		
		t.setId(rs.getString("ID"));
		t.setAuthid(rs.getInt("authid"));
		t.setPregistry(rs.getString("pregistry"));
		t.setSbusinesser(rs.getString("sbusinesser"));
		t.setScreatetime(rs.getString("screatetime"));
		t.setSdeep(rs.getFloat("sdeep"));
		t.setSlength(rs.getFloat("slength"));
		t.setSload(rs.getFloat("sload"));
		t.setSmateriall(rs.getFloat("smateriall"));
		t.setSname(rs.getString("sname"));
		t.setSno(rs.getString("sno"));
		t.setSorg(rs.getString("sorg"));
		t.setSover(rs.getFloat("sover"));
		t.setSowner(rs.getString("sowner"));
		t.setStotal(rs.getFloat("stotal"));
		t.setStype(rs.getString("stype"));
		t.setSwidth(rs.getFloat("swidth"));
		t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TCompanyShipping bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "authid", "AUTHID", bean.getAuthid());
		this.addNameParamerSqlWithProperty(sql, "pregistry", "PREGISTRY", bean.getPregistry());
		this.addNameParamerSqlWithProperty(sql, "sbusinesser", "SBUSINESSER", bean.getSbusinesser());
		this.addNameParamerSqlWithProperty(sql, "screatetime", "SCREATETIME", bean.getScreatetime());
		this.addNameParamerSqlWithProperty(sql, "sdeep", "SDEEP", bean.getSdeep());
		this.addNameParamerSqlWithProperty(sql, "slength", "SLENGTH", bean.getSlength());
		this.addNameParamerSqlWithProperty(sql, "sload", "SLOAD", bean.getSload());
		this.addNameParamerSqlWithProperty(sql, "smateriall", "SMATERIALL", bean.getSmateriall());
		this.addNameParamerSqlWithProperty(sql, "sname", "SNAME", bean.getSname());
		this.addNameParamerSqlWithProperty(sql, "sno", "SNO", bean.getSno());
		this.addNameParamerSqlWithProperty(sql, "sorg", "SORG", bean.getSorg());
		this.addNameParamerSqlWithProperty(sql, "sover", "SOVER", bean.getSover());
		this.addNameParamerSqlWithProperty(sql, "sowner", "SOWNER", bean.getSowner());
		this.addNameParamerSqlWithProperty(sql, "stotal", "STOTAL", bean.getStotal());
		this.addNameParamerSqlWithProperty(sql, "stype", "STYPE", bean.getStype());
		this.addNameParamerSqlWithProperty(sql, "swidth", "SWIDTH", bean.getSwidth());
		return sql.toString();
	}


}
