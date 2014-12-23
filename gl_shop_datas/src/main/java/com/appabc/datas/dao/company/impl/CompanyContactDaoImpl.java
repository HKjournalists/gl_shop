/**
 *
 */
package com.appabc.datas.dao.company.impl;

import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyContactDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 企业联系人DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午7:45:53
 */
@Repository
public class CompanyContactDaoImpl extends BaseJdbcDao<TCompanyContact> implements ICompanyContactDao {

	private static final String INSERTSQL = " insert into T_COMPANY_CONTACT (CID, CNAME, CPHONE, TEL, STATUS, CREATETIME, CREATER)  values (:cid, :cname, :cphone, :tel, :status, :createtime, :creater) ";
	private static final String UPDATESQL = " update T_COMPANY_CONTACT set CID = :cid, CNAME = :cname, CPHONE = :cphone, TEL = :tel, STATUS = :status, CREATETIME = :createtime, CREATER = :creater where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_CONTACT WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_CONTACT WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_CONTACT WHERE 1=1 ";

	public void save(TCompanyContact entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyContact entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TCompanyContact entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyContact entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyContact query(TCompanyContact entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)) + " order by ID desc limit 0,1 ", entity);
	}

	public TCompanyContact query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyContact> queryForList(TCompanyContact entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TCompanyContact> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TCompanyContact> queryListForPagination(
			QueryContext<TCompanyContact> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TCompanyContact mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyContact t = new TCompanyContact();

		t.setId(rs.getString("ID"));
		t.setCid(rs.getString("CID"));
		t.setCname(rs.getString("CNAME"));
		t.setCphone(rs.getString("CPHONE"));
		t.setCreater(rs.getString("CREATER"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		t.setStatus(rs.getInt("STATUS"));
		t.setTel(rs.getString("TEL"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TCompanyContact bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cname", "CNAME", bean.getCname());
		this.addNameParamerSqlWithProperty(sql, "cphone", "CPHONE", bean.getCphone());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "creater", "CREATER", bean.getCreater());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "tel", "TEL", bean.getTel());
		return sql.toString();
	}

}
