package com.appabc.datas.dao.urge.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.UrgeInfo.UrgeStatus;
import com.appabc.bean.enums.UrgeInfo.UrgeType;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.urge.IUrgeDepositDao;

/**
 * @Description : 催促保证金Dao类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月14日 上午10:10:03
 */
@Repository
public class UrgeDepositDaoImpl extends BaseJdbcDao<TUrgeVerify> implements IUrgeDepositDao {

	private static final String queryFieldSql="SELECT suser.REALNAME AS REALNAME, task.ID AS TASKID,task.OBJECT_ID AS ID, "
			+ "company.ID AS USERID,company.CTYPE AS COMPANYTYPE,company.AUTHSTATUS AS AUTHSTATUS,"
			+ "tuser.USERNAME AS USERNAME,company.CNAME AS USERREALNAME,tpi.AMOUNT AS AMOUNT,"
			+ "company.CREATEDATE AS REGDATE,systemlog.CREATETIME AS LASTLOGINDATE,buy_order.BUYNUM AS BUYNUM,"
			+ "sell_order.SELLNUM AS SELLNUM,auth_order.AUTHNUM AS AUTHNUM,verify_order.UID AS UID,"
			+ "verify_order.VERIFYNUM AS VERIFYNUM,verify_order.LASTVERIFYDATE AS LASTVERIFYDATE,"
			+ "verify_order.USERTYPE,verify_order.REGISTREASON,verify_order.REMARK,verify_order.RECORD,verify_order.UTYPE AS UTYPE,verify_order.URGESTATUS AS URGESTATUS";
	private static final String queryCountNumSql=" LEFT JOIN T_PASSBOOK_INFO tpi ON task.OBJECT_ID=tpi.PASSID"
			+" LEFT JOIN T_COMPANY_INFO company on tpi.CID=company.ID "
			+" LEFT JOIN T_USER  tuser ON  company.ID=tuser.CID"
			+" LEFT JOIN (SELECT BUSINESSID,CREATETIME FROM (SELECT BUSINESSID,CREATETIME FROM T_SYSTEM_LOG WHERE BUSINESSTYPE=101 ORDER BY CREATETIME DESC) AS SLOG GROUP BY BUSINESSID) AS systemlog  ON tuser.USERNAME=systemlog.BUSINESSID"
			+" LEFT JOIN (SELECT COUNT(0) BUYNUM,CID FROM T_ORDER_FIND WHERE TYPE=1 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS buy_order ON buy_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) SELLNUM,CID FROM T_ORDER_FIND WHERE TYPE=2 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS sell_order ON sell_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) AUTHNUM,CID FROM T_AUTH_RECORD WHERE TYPE=1 GROUP BY CID) AS auth_order ON auth_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) VERIFYNUM ,UID, CREATETIME LASTVERIFYDATE,CID,USERREALNAME,USERTYPE,REGISTREASON,REMARK,RECORD,UTYPE,URGESTATUS FROM (SELECT * FROM T_URGE_VERIFY ORDER BY UID DESC) verify_all GROUP BY CID) verify_order ON verify_order.CID=task.OBJECT_ID"
			+" LEFT JOIN (SELECT ID,REALNAME FROM SYS_USERS) AS suser on suser.ID=task.OWNER";

	private static final String SELECT_RECORD="SELECT * FROM T_URGE_VERIFY tuv LEFT JOIN (SELECT ID,REALNAME FROM SYS_USERS) AS suser on suser.ID=tuv.CREATER ";
	
	private static final String INSERT_SQL="INSERT INTO T_URGE_VERIFY (UTYPE,CREATER,CREATETIME,CID,USERREALNAME,USERTYPE,REGISTREASON,REMARK,RECORD,URGESTATUS) VALUES(:utype,:creater,:createtime,:cid,:userrealname,:usertype,:registreason,:remark,:record,:urgestatus)";
	private static final String DELETE_SQL="DELETE FROM T_URGE_VERIFY WHERE UID = :id";
	private static final String UPDATE_SQL="UPDATE T_URGE_VERIFY SET UTYPE = :utype,CREATER = :create,CREATETIME = :createtime,CID = :cid,USERREALNAME = :userrealname,USERTYPE = :usertype,REGISTREASON = :registreason,REMARK = :remark,RECORD = :record,URGESTATUS = :urgestatus";
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		super.save(INSERT_SQL, entity);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		super.update(UPDATE_SQL, entity);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		super.delete(DELETE_SQL, entity);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		super.delete(DELETE_SQL, id);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TUrgeVerify query(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TUrgeVerify query(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TUrgeVerify> queryForList(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TUrgeVerify> queryForList(Map<String, ?> args) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TUrgeVerify> queryListForPagination(
			QueryContext<TUrgeVerify> qContext) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TUrgeVerify mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		TUrgeVerify t=new TUrgeVerify();
		t.setUid(rs.getString("UID"));
		t.setUtype(UrgeType.enumOf(rs.getString("UTYPE")));
		t.setUrgestatus(UrgeStatus.enumOf(rs.getString("URGESTATUS")));
		t.setCreater(rs.getString("CREATER"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setCid(rs.getString("CID"));
		t.setUsertype(rs.getString("USERTYPE"));
		t.setRegistreason(rs.getString("REGISTREASON"));
		t.setRemark(rs.getString("REMARK"));
		t.setRecord(rs.getString("RECORD"));
		t.setOwner(rs.getString("REALNAME"));
		return t;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.urge.IUrgeVerifyDao#getDepositList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<UrgeVerifyInfo> getDepositList(
			QueryContext<UrgeVerifyInfo> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder(queryFieldSql);
		sql.append(" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=22 AND FINISHED=0) task");
		sql.append(queryCountNumSql);
    	String registtime = qContext.getParameters().get("registtime") != null ? (String)qContext.getParameters().get("registtime") : StringUtils.EMPTY;
    	String verifytime = qContext.getParameters().get("verifytime") != null ? (String)qContext.getParameters().get("verifytime") : StringUtils.EMPTY;
    	if(StringUtils.isNotEmpty(verifytime)){
    		if(StringUtils.equalsIgnoreCase("ALL", verifytime)){
    			sql.append(" ORDER BY LASTVERIFYDATE DESC ");
    		}else{
    			sql.append(" ORDER BY LASTVERIFYDATE "+verifytime);
    		}
    	}else if(StringUtils.isNotEmpty(registtime)){
    		if(StringUtils.equalsIgnoreCase("ALL", registtime)){
    			sql.append(" ORDER BY REGDATE DESC ");
    		}else{
    			sql.append(" ORDER BY REGDATE "+registtime);
    		}
    	}else{
    		sql.append(" ORDER BY LASTVERIFYDATE DESC ");
    	}
		
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(sql.toString(), args, rowMapper1);
			QueryResult<UrgeVerifyInfo> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<UrgeVerifyInfo> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(pageSql, args, rowMapper1);
			qr.setResult(list);
		}
		return qContext;
	}


	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.urge.IUrgeVerifyDao#getNoDepositList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<UrgeVerifyInfo> getNoDepositList(
			QueryContext<UrgeVerifyInfo> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder(queryFieldSql);
		sql.append(" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=22 AND FINISHED=1) task");
		sql.append(queryCountNumSql);
		
		String registtime = qContext.getParameters().get("registtime") != null ? (String)qContext.getParameters().get("registtime") : StringUtils.EMPTY;
    	String verifytime = qContext.getParameters().get("verifytime") != null ? (String)qContext.getParameters().get("verifytime") : StringUtils.EMPTY;
    	if(StringUtils.isNotEmpty(verifytime)){
    		if(StringUtils.equalsIgnoreCase("ALL", verifytime)){
    			sql.append(" ORDER BY LASTVERIFYDATE DESC ");
    		}else{
    			sql.append(" ORDER BY LASTVERIFYDATE "+verifytime);
    		}
    	}else if(StringUtils.isNotEmpty(registtime)){
    		if(StringUtils.equalsIgnoreCase("ALL", registtime)){
    			sql.append(" ORDER BY REGDATE DESC ");
    		}else{
    			sql.append(" ORDER BY REGDATE "+registtime);
    		}
    	}else{
    		sql.append(" ORDER BY LASTVERIFYDATE DESC ");
    	}
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(sql.toString(), args, rowMapper1);
			QueryResult<UrgeVerifyInfo> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<UrgeVerifyInfo> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(pageSql, args, rowMapper1);
			qr.setResult(list);
		}
		return qContext;
	}
	
	private RowMapper<UrgeVerifyInfo> rowMapper1 = new RowMapper<UrgeVerifyInfo>() {
		public UrgeVerifyInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
			UrgeVerifyInfo t=new UrgeVerifyInfo();
			t.setId(rs.getString("ID"));
	        t.setTaskid(rs.getString("TASKID"));        
			t.setUid(rs.getString("UID"));
			t.setUserrealname(rs.getString("USERREALNAME"));
			t.setUsertype(rs.getString("USERTYPE"));
			t.setRegistreason(rs.getString("REGISTREASON"));
			t.setRemark(rs.getString("REMARK"));
			t.setRecord(rs.getString("RECORD"));
	        t.setOwner(rs.getString("REALNAME"));
			t.setUserid(rs.getString("USERID"));
			String utype=rs.getString("UTYPE");
			if(utype!=null)
			{
				t.setUtype(UrgeType.enumOf(utype));
			}
			String urgestatus=rs.getString("URGESTATUS");
			if(urgestatus!=null)
			{
				t.setUrgestatus(UrgeStatus.enumOf(urgestatus));
			}
			t.setUsername(rs.getString("USERNAME"));
			t.setRegdate(rs.getTimestamp("REGDATE"));
			t.setLastlogindate(rs.getTimestamp("LASTLOGINDATE"));
			t.setBuynum(rs.getInt("BUYNUM"));
			t.setSellnum(rs.getInt("SELLNUM"));
			t.setAuthnum(rs.getInt("AUTHNUM"));
			t.setVerifynum(rs.getInt("VERIFYNUM"));
			t.setLastverifydate(rs.getTimestamp("LASTVERIFYDATE"));
			t.setCompanyType(CompanyType.getText(rs.getString("COMPANYTYPE")));
			t.setAuthStatus(AuthRecordStatus.getText(rs.getString("AUTHSTATUS")));
			t.setPassAmount(rs.getString("AMOUNT"));
			return t;
		}
	};

	


	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.urge.IUrgeDepositDao#queryDepositInfoByTaskId(java.lang.String)  
	 */
	@Override
	public UrgeVerifyInfo queryDepositInfoByTaskId(String taskid) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(queryFieldSql);
		sql.append(" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=22) task");
		sql.append(queryCountNumSql);
		String where =" where task.ID="+taskid;
		sql.append(where);
		return getJdbcTemplate().queryForObject(sql.toString(),rowMapper1);
	}


	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.urge.IUrgeDepositDao#queryRecordByTypeAndId(java.lang.String, java.lang.String)  
	 */
	@Override
	public List<TUrgeVerify> queryRecordByTypeAndId(String utype, String id) {
		// TODO Auto-generated method stub
		StringBuilder querySql = new StringBuilder(SELECT_RECORD);
		String where =" where UTYPE="+ utype +" and CID="+"'"+id+"'";
		querySql.append(where);
		return super.queryForList(querySql.toString(), new TUrgeVerify());	
		
	}
}
