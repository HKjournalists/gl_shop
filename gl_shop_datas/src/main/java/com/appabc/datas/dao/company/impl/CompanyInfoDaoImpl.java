/**
 *
 */
package com.appabc.datas.dao.company.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.enums.AuthRecordInfo;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 公司信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:29:34
 */
@Repository
public class CompanyInfoDaoImpl extends BaseJdbcDao<TCompanyInfo> implements ICompanyInfoDao{
	
	@Autowired
	private PrimaryKeyGenerator pkg;
	
	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_INFO WHERE 1=1 "; 
	private static final String INSERTSQL = " insert into T_COMPANY_INFO (ID, CNAME, MARK, CONTACT, CPHONE, CTYPE, AUTHSTATUS, STATUS, CREATEDATE, UPDATEDATE, UPDATER, TEL, BAILSTATUS) values (:id, :cname, :mark, :contact, :cphone, :ctype, :authstatus, :status, :createdate, :updatedate, :updater,tel,bailstatus) ";
	private static final String UPDATESQL = " update T_COMPANY_INFO set CNAME = :cname, MARK = :mark, CONTACT = :contact, CPHONE = :cphone, CTYPE = :ctype, AUTHSTATUS = :authstatus, STATUS = :status, CREATEDATE = :createdate, UPDATEDATE = :updatedate, UPDATER = :updater,TEL = :tel, BAILSTATUS = :bailstatus  where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_INFO WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_INFO WHERE ID = :id ";
	
	//根据企业ID查询已认证通过的企业详情
	private static final String SELECT_AUTH_COPN_INFO = " select ci.ID,ci.CNAME,ci.MARK,ci.CONTACT,ci.CPHONE,ci.TEL,ci.CTYPE,ci.AUTHSTATUS,ci.BAILSTATUS,ar.IMGID from T_COMPANY_INFO ci, T_AUTH_RECORD ar ";

	public void save(TCompanyInfo entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("COMPANYINFOID"));
		entity.setCreatedate(Calendar.getInstance().getTime());
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyInfo entity) {
		return null;
	}

	public void update(TCompanyInfo entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyInfo entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyInfo query(TCompanyInfo entity) {
		return null;
	}

	public TCompanyInfo query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyInfo> queryForList(TCompanyInfo entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TCompanyInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyInfo> queryListForPagination(
			QueryContext<TCompanyInfo> qContext) {
		return null;
	}

	public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyInfo t = new TCompanyInfo();
		
		t.setId(rs.getString("ID"));
		t.setCname(rs.getString("CNAME"));
		t.setAuthstatus(rs.getString("AUTHSTATUS"));
		t.setContact(rs.getString("CONTACT"));
		t.setCphone(rs.getString("CPHONE"));
		t.setCreatedate(rs.getTimestamp("CREATEDATE"));
		t.setCtype(rs.getString("CTYPE"));
		t.setMark(rs.getString("MARK"));
		t.setStatus(rs.getString("STATUS"));
		t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setTel(rs.getString("TEL"));
		t.setBailstatus(rs.getString("BAILSTATUS"));
		
		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TCompanyInfo bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cname", "CNAME", bean.getCname());
		this.addNameParamerSqlWithProperty(sql, "authstatus", "AUTHSTATUS", bean.getAuthstatus());
		this.addNameParamerSqlWithProperty(sql, "contact", "CONTACT", bean.getContact());
		this.addNameParamerSqlWithProperty(sql, "cphone", "CPHONE", bean.getCphone());
		this.addNameParamerSqlWithProperty(sql, "ctype", "CTYPE", bean.getCtype());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "tel", "TEL", bean.getTel());
		this.addNameParamerSqlWithProperty(sql, "bailstatus", "BAILSTATUS", bean.getBailstatus());
		return sql.toString();
	}

	/* (non-Javadoc)根据企业ID查询已认证通过的企业详情
	 * @see com.appabc.datas.dao.company.ICompanyInfoDao#queryAuthCompanyInfo(java.lang.String)
	 */
	public CompanyAllInfo queryAuthCompanyInfo(String cid) {
		
		StringBuffer sql = new StringBuffer(SELECT_AUTH_COPN_INFO);
		/*****过滤条件******************************/
		sql.append(" where ar.AUTHSTATUS=? AND ar.CID=ci.ID  and ci.id=? ");
		
		Object params[] = {AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES.getVal(), cid};

		List<CompanyAllInfo> oaiList = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper<CompanyAllInfo>() {
			public CompanyAllInfo mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				CompanyAllInfo t = new CompanyAllInfo();
				
				t.setId(rs.getString("ID"));
				t.setAuthstatus(rs.getString("AUTHSTATUS"));
				t.setBailstatus(rs.getString("BAILSTATUS"));
				t.setCname(rs.getString("CNAME"));
				t.setContact(rs.getString("CONTACT"));
				t.setCphone(rs.getString("CPHONE"));
				t.setCtype(rs.getString("CTYPE"));
				t.setImgid(rs.getInt("IMGID"));
				t.setMark(rs.getString("MARK"));
				t.setTel(rs.getString("TEL"));

				return t;
			}
		});
		
		if(oaiList != null && oaiList.size()>0){
			return oaiList.get(0);
		}
		
		return null;
		
	}

}
