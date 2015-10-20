/**
 *
 */
package com.appabc.datas.dao.company.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.dao.company.ICompanyInfoDao;
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

	public final static RowMapper<TCompanyInfo> ROW_MAPPER = new CompanyRowMapper();

	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_INFO WHERE 1=1 ";
	private static final String INSERTSQL = " insert into T_COMPANY_INFO (ID, CNAME, MARK, CONTACT, CPHONE, CTYPE, AUTHSTATUS, STATUS, CREATEDATE, UPDATEDATE, UPDATER, TEL, BAILSTATUS) values (:id, :cname, :mark, :contact, :cphone, :ctype, :authstatus, :status, :createdate, :updatedate, :updater, :tel, :bailstatus) ";
	private static final String UPDATESQL = " update T_COMPANY_INFO set CNAME = :cname, MARK = :mark, CONTACT = :contact, CPHONE = :cphone, CTYPE = :ctype, AUTHSTATUS = :authstatus, STATUS = :status, CREATEDATE = :createdate, UPDATEDATE = :updatedate, UPDATER = :updater,TEL = :tel, BAILSTATUS = :bailstatus  where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_INFO WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_INFO WHERE ID = :id ";
	private static final String COUNT_SQL = "SELECT COUNT(0) cou FROM T_COMPANY_INFO WHERE 1=1";

	//根据企业ID查询已认证通过的企业详情
	private static final String SELECT_AUTH_COPN_INFO = " select ci.*,ar.AUTHID,ar.IMGID,ar.AUTHSTATUS from "
														+" (SELECT ID,CNAME,MARK,CONTACT,CPHONE,TEL,CTYPE,STATUS,CREATEDATE,UPDATEDATE,UPDATER,BAILSTATUS FROM T_COMPANY_INFO WHERE ID=?) as ci "
														+" LEFT JOIN T_AUTH_RECORD ar ON ci.ID = ar.CID AND  ar.TYPE = ? AND ci.id = ? ORDER BY ar.CREATEDATE DESC LIMIT 0,1";
	// 新添加的用户，需要放任务后台任务列表中的记录
	private static final String QUERY_NEW_TASK_LIST = "SELECT tci.* FROM T_COMPANY_INFO tci WHERE NOT EXISTS ( SELECT st.OBJECT_ID FROM SYS_TASKS st WHERE tci.ID = st.OBJECT_ID  AND st.TYPE = ? )  AND tci.AUTHSTATUS=0";
	// 新失效的用户，需要删除的后台任务列表
	private static final String QUERY_INVALID_TASK_LIST = "SELECT tci.* FROM T_COMPANY_INFO tci WHERE EXISTS ( SELECT st.OBJECT_ID FROM SYS_TASKS st WHERE tci.ID = st.OBJECT_ID  AND st.TYPE = ? ) AND tci.AUTHSTATUS !=0";
	
	@Autowired
	private PrimaryKeyGenerator pkg;

	public void save(TCompanyInfo entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("COMPANYINFOID"));
		entity.setCreatedate(Calendar.getInstance().getTime());
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyInfo entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TCompanyInfo entity) {
		entity.setUpdatedate(Calendar.getInstance().getTime());
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyInfo entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyInfo query(TCompanyInfo entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TCompanyInfo query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyInfo> queryForList(TCompanyInfo entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TCompanyInfo> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TCompanyInfo> queryListForPagination(
			QueryContext<TCompanyInfo> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ROW_MAPPER.mapRow(rs, rowNum);
	}

	private String dynamicJoinSqlWithEntity(TCompanyInfo bean,StringBuilder sql){
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
		Object params[] = {cid, AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal(), cid};
		List<CompanyAllInfo> oaiList = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper<CompanyAllInfo>() {
			public CompanyAllInfo mapRow(ResultSet rs,
										 int rowNum) throws SQLException {
				CompanyAllInfo t = new CompanyAllInfo();

				t.setId(rs.getString("ID"));
				t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("AUTHSTATUS")));
				t.setBailstatus(CompanyBailStatus.enumOf(rs.getString("BAILSTATUS")));
				t.setCname(rs.getString("CNAME"));
				t.setContact(rs.getString("CONTACT"));
				t.setCphone(rs.getString("CPHONE"));
				t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
				t.setMark(rs.getString("MARK"));
				t.setTel(rs.getString("TEL"));
				t.setCreatedate(rs.getTimestamp("CREATEDATE"));

				t.setAuthid(rs.getString("AUTHID"));
				t.setAuthimgid(rs.getString("IMGID"));
				
				return t;
			}
		});

		if(oaiList != null && oaiList.size()>0){
			return oaiList.get(0);
		}

		return null;

	}
	
	@Override
	public TCompanyInfo queryAuthCmpInfo(String cid) {
		
		StringBuffer sql = new StringBuffer(SELECT_AUTH_COPN_INFO);
		/*****过滤条件******************************/
		List<Object> args = new ArrayList<Object>();
		args.add(cid);
		args.add(AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal());
		args.add(cid);
		
		return super.query(sql.toString(), args);
	}
	
	private static class CompanyRowMapper implements RowMapper<TCompanyInfo> {
		@Override
		public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			TCompanyInfo t = new TCompanyInfo();

			t.setId(rs.getString("ID"));
			t.setCname(rs.getString("CNAME"));
			t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("AUTHSTATUS")));
			t.setContact(rs.getString("CONTACT"));
			t.setCphone(rs.getString("CPHONE"));
			t.setCreatedate(rs.getTimestamp("CREATEDATE"));
			t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
			t.setMark(rs.getString("MARK"));
			t.setStatus(rs.getString("STATUS"));
			t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
			t.setUpdater(rs.getString("UPDATER"));
			t.setTel(rs.getString("TEL"));
			t.setBailstatus(CompanyBailStatus.enumOf(rs.getString("BAILSTATUS")));
			return t;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.company.ICompanyInfoDao#queryCount(com.appabc.bean.pvo.TCompanyInfo)
	 */
	@Override
	public int queryCount(TCompanyInfo entity) {
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
		Number number = super.getNamedParameterJdbcTemplate().queryForObject(dynamicJoinSqlWithEntity(entity, new StringBuilder(COUNT_SQL)).toString(), paramSource, Integer.class);  
	    return (number != null ? number.intValue() : 0);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.company.ICompanyInfoDao#queryCmpInfoByUserPhone(java.lang.String)  
	 */
	@Override
	public TCompanyInfo queryCmpInfoByUserPhone(String phone) {
		StringBuilder sql = new StringBuilder(" SELECT TCI.* FROM T_USER TU LEFT JOIN T_COMPANY_INFO TCI ON TCI.ID = TU.CID WHERE 1 = 1 ");
		if(!StringUtils.isEmpty(phone)){
			sql.append(" AND PHONE = ?  ");
		}
		List<TCompanyInfo> res = this.queryForList(sql.toString(), Collections.singletonList(phone));
		return CollectionUtils.isEmpty(res) ? null : res.get(0);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.company.ICompanyInfoDao#queryCmpInfoListByUserPhones(java.lang.String)  
	 */
	@Override
	public List<TCompanyInfo> queryCmpInfoListByUserPhones(String phones) {
		StringBuilder sql = new StringBuilder(" SELECT TU.USERNAME,TU.PHONE,TCI.* FROM T_USER TU LEFT JOIN T_COMPANY_INFO TCI ON TCI.ID = TU.CID WHERE 1 = 1 ");
		if(StringUtils.isNotEmpty(phones)){
			sql.append(" AND PHONE in ( "+phones+" )  ");
		}
		List<TCompanyInfo> res = getJdbcTemplate().query(sql.toString(), new RowMapper<TCompanyInfo>() {

			@Override
			public TCompanyInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				TCompanyInfo t = new TCompanyInfo();

				t.setId(rs.getString("ID"));
				t.setCname(rs.getString("CNAME"));
				t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("AUTHSTATUS")));
				t.setContact(rs.getString("CONTACT"));
				t.setCphone(rs.getString("PHONE"));
				t.setCreatedate(rs.getTimestamp("CREATEDATE"));
				t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
				t.setMark(rs.getString("MARK"));
				t.setStatus(rs.getString("STATUS"));
				t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
				t.setUpdater(rs.getString("UPDATER"));
				t.setTel(rs.getString("TEL"));
				t.setBailstatus(CompanyBailStatus.enumOf(rs.getString("BAILSTATUS")));
				return t;
			}
			
		});
		return res;
	}

	public List<TCompanyInfo> queryNewListForTask() {
		StringBuilder sql = new StringBuilder(QUERY_NEW_TASK_LIST);
		List<Object> args = new ArrayList<Object>();
		args.add(TaskType.UrgeVerify.getValue());
		return super.queryForList(sql.toString(), args);
	}
	
	@Override
	public List<TCompanyInfo> queryInvalidListForTask() {
		StringBuilder sql = new StringBuilder(QUERY_INVALID_TASK_LIST);
		List<Object> args = new ArrayList<Object>();
		args.add(TaskType.UrgeVerify.getValue());
		return super.queryForList(sql.toString(), args);
	}

}
