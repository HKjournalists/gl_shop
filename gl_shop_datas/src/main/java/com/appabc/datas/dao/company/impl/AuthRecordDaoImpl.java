/**
 *
 */
package com.appabc.datas.dao.company.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.cms.vo.task.TaskType;
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

	public final static AuthRecordRowMapper ROW_MAPPER = new AuthRecordRowMapper();

	private static final String INSERTSQL = " insert into T_AUTH_RECORD (CID, IMGID, AUTHSTATUS, DEALSTATUS, CREATEDATE, AUTHOR, AUTHRESULT, AUTHDATE, REMARK, TYPE, ABID) values (:cid, :imgid, :authstatus, :dealstatus, :createdate, :author, :authresult, :authdate, :remark, :type, :abid) ";
	private static final String UPDATESQL = " update T_AUTH_RECORD set CID = :cid, IMGID = :imgid, AUTHSTATUS = :authstatus, DEALSTATUS = :dealstatus, CREATEDATE = :createdate, AUTHOR = :author, AUTHRESULT = :authresult, AUTHDATE = :authdate, REMARK = :remark, TYPE = :type, ABID=:abid where AUTHID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_AUTH_RECORD WHERE AUTHID = :id ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_AUTH_RECORD WHERE 1=1 ";
	
	// 认证记录日志
	private static final String SELECT_AUTH_LOG = " select t.* FROM ( SELECT a.* FROM ( SELECT null AS AUTHID,uv.CID,null AS IMGID,null AS AUTHSTATUS,null AS DEALSTATUS,uv.CREATETIME AS CREATEDATE,null AS AUTHOR,CONCAT(su.USERNAME,'催促用户提交认证') AS AUTHRESULT,null AUTHDATE,null AS REMARK, null AS TYPE,null AS ABID FROM T_URGE_VERIFY uv LEFT JOIN SYS_USERS su ON uv.CREATER=su.ID  WHERE CID=? ) a UNION ALL SELECT * FROM T_AUTH_RECORD WHERE TYPE=1 AND CID=? ) t ORDER BY t.CREATEDATE";

	private static final String BASE_SQL = " SELECT * FROM T_AUTH_RECORD WHERE 1=1 ";
	
	//查询新认证的记录（未添加到任务表中[SYS_TASKS]的数据）
	private static final String QUERY_NEW_TASK_LIST = " SELECT ar.* FROM T_AUTH_RECORD ar WHERE NOT EXISTS ( SELECT st.OBJECT_ID FROM SYS_TASKS st WHERE ar.AUTHID = st.OBJECT_ID AND st.TYPE = ? ) AND ar.AUTHSTATUS = ? AND ar.TYPE= ? ";
	// 用户身份认证，已审核列表
	private static final String SELECT_AUTHRECORD_OF_INSTEAD = "SELECT st.ID,st.`OWNER`,st.CID,su.REALNAME,tu.USERNAME,tu.CREATEDATE REGTIME,ci.CNAME,ci.CTYPE,ar.AUTHID,ar.AUTHDATE,ar.AUTHSTATUS FROM (SELECT * FROM SYS_TASKS WHERE 2=2 AND TYPE=62 and FINISHED=1 ) st"
			+" LEFT JOIN SYS_USERS su ON su.id=st.`OWNER` "
			+" LEFT JOIN T_USER tu ON tu.CID=st.CID"
			+" LEFT JOIN T_COMPANY_INFO ci ON ci.ID=st.CID"
			+" LEFT JOIN T_AUTH_RECORD ar ON ar.AUTHID=st.OBJECT_ID"
			+" ORDER BY st.CREATE_TIME DESC";

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
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.company.IAuthRecordDao#queryListForPaginationByTypeAndAuthstatus(com.appabc.common.base.QueryContext, com.appabc.bean.enums.AuthRecordInfo.AuthRecordType, com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus)
	 */
	@Override
	public QueryContext<TAuthRecord> queryListForPaginationByTypeAndAuthstatus(
			QueryContext<TAuthRecord> qContext, AuthRecordType authType,
			AuthRecordStatus authStatus) {
		StringBuilder sql = new StringBuilder();
		if(authType != null){
			qContext.addParameter("authType", authType.getVal());
			if(AuthRecordType.AUTH_RECORD_TYPE_COMPANY.equals(authType)){
				sql.append(" SELECT ar.*, ci.CNAME, ci.CTYPE, u.USERNAME, u.CREATEDATE AS REGTIME, null as CARDUSER ")
				.append(" FROM ( SELECT * FROM T_AUTH_RECORD WHERE 1=1 ");
				
				if(authStatus != null){
					sql.append(" AND AUTHSTATUS = :authStatus ");
					qContext.addParameter("authStatus", authStatus.getVal());
				}
				
				sql.append(" AND TYPE = :authType ) AS ar ")
				.append(" LEFT JOIN T_COMPANY_INFO ci ON ar.CID = ci.id ")
				.append(" LEFT JOIN T_USER u ON u.CID = ci.ID ")
				.append(" ORDER BY ar.CREATEDATE ");
				
			} else if(AuthRecordType.AUTH_RECORD_TYPE_BANK.equals(authType)){
				sql.append(" SELECT ar.*, ci.CNAME, ci.CTYPE, u.USERNAME,u.CREATEDATE AS REGTIME, ab.CARDUSER ")
					.append(" FROM ( SELECT * FROM T_AUTH_RECORD WHERE 1=1 ");
				
				if(authStatus != null){
					sql.append(" AND AUTHSTATUS = :authStatus ");
					qContext.addParameter("authStatus", authStatus.getVal());
				}
					
				sql.append(" AND TYPE = :authType ) AS ar ")
					.append(" LEFT JOIN T_ACCEPT_BANK ab ON ab.ID = ar.ABID ")
					.append(" LEFT JOIN T_COMPANY_INFO ci ON ab.CID = ci.id ")
					.append(" LEFT JOIN T_USER u ON u.CID = ci.ID ")
					.append(" ORDER BY ar.CREATEDATE ");
			}
		}else {
			sql.append(BASE_SQL);
			if(authStatus != null){
				sql.append(" AND AUTHSTATUS = :authStatus ");
				qContext.addParameter("authStatus", authStatus.getVal());
			}
		}
//		System.out.println(sql);
		
		return queryListForPagination(sql.toString(), null, qContext, new RowMapper<TAuthRecord>() {
			public TAuthRecord mapRow(ResultSet rs,
					int rowNum) throws SQLException {
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
				
				try {t.setCname(rs.getString("CNAME"));} catch (Exception e) {e.printStackTrace();}
				try {t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));} catch (Exception e) {}
				try {t.setUsername(rs.getString("USERNAME"));} catch (Exception e) {}
				try {t.setRegtime(rs.getTimestamp("REGTIME"));} catch (Exception e) {}
				try {t.setCarduser(rs.getString("CARDUSER"));} catch (Exception e) {}

				return t;
			}
		});
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
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.company.IAuthRecordDao#queryNewListForCmpAuth()
	 */
	@Override
	public List<TAuthRecord> queryNewListForNotInTask() {
		StringBuilder sql = new StringBuilder(QUERY_NEW_TASK_LIST);
		
		List<Object> args = new ArrayList<Object>();
		args.add(TaskType.VerifyInfo.getValue());
		args.add(AuthRecordStatus.AUTH_STATUS_CHECK_ING.getVal());
		args.add(AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal());
		
		return super.queryForList(sql.toString(), args);
	}
	
	/* (non-Javadoc) 用户身份认证，已审核列表。
	 * @see com.appabc.datas.dao.company.IAuthRecordDao#queryParentAuthRecordOfInsteadListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TAuthRecord> queryParentAuthRecordOfInsteadListForPagination(QueryContext<TAuthRecord> qContext) {
		
		String operatorSql = "2=2";
		Object operatorId = qContext.getParameter("operatorId");
		if(operatorId != null){
			operatorSql = "`OWNER` = :operatorId";
		}
		
		StringBuilder sql = new StringBuilder(SELECT_AUTHRECORD_OF_INSTEAD.replace("2=2", operatorSql));
		
		return queryListForPagination(sql.toString(), null, qContext, new RowMapper<TAuthRecord>() {
			public TAuthRecord mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				
				TAuthRecord t = new TAuthRecord();
				
//				t.setId(rs.getString("ID"));
				t.setId(rs.getString("AUTHID"));
				t.setCid(rs.getString("CID"));
				t.setCname(rs.getString("CNAME"));
				t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
				t.setAuthor(rs.getString("REALNAME"));
				t.setUsername(rs.getString("USERNAME"));
				t.setRegtime(rs.getTimestamp("REGTIME"));
				t.setAuthdate(rs.getTimestamp("AUTHDATE"));
				t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("AUTHSTATUS")));
				
				return t;
			}
		});
	}
	
	@Override
	public List<TAuthRecord> queryAuthLogListByCid(String cid) {
		StringBuilder sql = new StringBuilder(SELECT_AUTH_LOG);
		List<Object> args = new ArrayList<Object>();
		args.add(cid);
		args.add(cid);
		
		return super.queryForList(sql.toString(), args);
	}

}
