package com.appabc.datas.dao.urge.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.enums.SqlInfoEnums.OrderBySort;
import com.appabc.bean.enums.UrgeInfo.UrgeStatus;
import com.appabc.bean.enums.UrgeInfo.UrgeType;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.urge.IUrgeVerifyDao;

/**
 * @Description : 催促认证Dao实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午2:49:07
 */

@Repository
public class UrgeVerifyDaoImpl extends BaseJdbcDao<TUrgeVerify> implements IUrgeVerifyDao {

	private static final String QUERYNUM=" LEFT JOIN  T_COMPANY_INFO company ON task.OBJECT_ID=company.ID"
			+" LEFT JOIN  T_USER  tuser ON  company.ID=tuser.CID"
			+" LEFT JOIN (SELECT BUSINESSID,CREATETIME FROM (SELECT BUSINESSID,CREATETIME FROM T_SYSTEM_LOG WHERE BUSINESSTYPE=101 ORDER BY CREATETIME DESC) AS SLOG GROUP BY BUSINESSID) AS systemlog  ON tuser.USERNAME=systemlog.BUSINESSID" 
			+" LEFT JOIN (SELECT COUNT(0) BUYNUM,CID FROM T_ORDER_FIND WHERE TYPE=1 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS buy_order ON buy_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) SELLNUM,CID FROM T_ORDER_FIND WHERE TYPE=2 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS sell_order ON sell_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) AUTHNUM,CID FROM T_AUTH_RECORD WHERE TYPE=1 GROUP BY CID) AS auth_order ON auth_order.CID=company.ID"
			+" LEFT JOIN (SELECT COUNT(0) VERIFYNUM ,UID, CREATETIME LASTVERIFYDATE,CID,USERREALNAME,USERTYPE,REGISTREASON,REMARK,RECORD,UTYPE,URGESTATUS FROM (SELECT * FROM T_URGE_VERIFY ORDER BY UID DESC) verify_all GROUP BY CID) verify_order ON verify_order.CID=company.ID"
			+" LEFT JOIN (SELECT ID,REALNAME FROM SYS_USERS) AS suser on suser.ID=task.OWNER";
	private static final String INSERT_SQL="INSERT INTO T_URGE_VERIFY (UTYPE,CREATER,CREATETIME,CID,USERREALNAME,USERTYPE,REGISTREASON,REMARK,RECORD,URGESTATUS) VALUES(:utype,:creater,:createtime,:cid,:userrealname,:usertype,:registreason,:remark,:record,:urgestatus)";

	private static final String SELECT_URGE_VERIFY_INFO="SELECT suser.REALNAME AS REALNAME, task.ID AS TASKID, company.ID AS USERID,tuser.USERNAME AS USERNAME,company.CNAME AS USERREALNAME,company.CREATEDATE AS REGDATE,systemlog.CREATETIME AS LASTLOGINDATE,buy_order.BUYNUM AS BUYNUM,sell_order.SELLNUM AS SELLNUM,auth_order.AUTHNUM AS AUTHNUM,verify_order.UID AS UID,verify_order.VERIFYNUM AS VERIFYNUM,verify_order.LASTVERIFYDATE AS LASTVERIFYDATE,verify_order.USERTYPE,verify_order.REGISTREASON,verify_order.REMARK,verify_order.RECORD"
			+" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=21 AND FINISHED=0) task"
			+QUERYNUM;
	
	private static final String SELECT_URGE_VERIFY_NO_INFO="SELECT suser.REALNAME AS REALNAME, task.ID AS TASKID, company.ID AS USERID,tuser.USERNAME AS USERNAME,company.CNAME AS USERREALNAME,company.CREATEDATE AS REGDATE,systemlog.CREATETIME AS LASTLOGINDATE,buy_order.BUYNUM AS BUYNUM,sell_order.SELLNUM AS SELLNUM,auth_order.AUTHNUM AS AUTHNUM,verify_order.UID AS UID,verify_order.VERIFYNUM AS VERIFYNUM,verify_order.LASTVERIFYDATE AS LASTVERIFYDATE,verify_order.USERTYPE,verify_order.REGISTREASON,verify_order.REMARK,verify_order.RECORD"
			+" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=21 AND FINISHED=1) task"
			+QUERYNUM;
	private static final String SELECT_URGE_VERIFY_DETIAL="SELECT  suser.REALNAME AS REALNAME, task.ID AS TASKID,company.ID AS USERID,tuser.USERNAME AS USERNAME,company.CREATEDATE AS REGDATE,systemlog.CREATETIME AS LASTLOGINDATE,buy_order.BUYNUM AS BUYNUM,sell_order.SELLNUM AS SELLNUM,auth_order.AUTHNUM AS AUTHNUM,verify_order.VERIFYNUM AS VERIFYNUM,verify_order.LASTVERIFYDATE AS LASTVERIFYDATE,verify_order.UID,verify_order.USERREALNAME,verify_order.USERTYPE,verify_order.REGISTREASON,verify_order.REMARK,verify_order.RECORD"
			+" FROM (SELECT * FROM SYS_TASKS WHERE TYPE=21) task"
			+QUERYNUM;
	
	private static final String SELECT_RECORD="SELECT * FROM T_URGE_VERIFY tuv LEFT JOIN (SELECT ID,REALNAME FROM SYS_USERS) AS suser on suser.ID=tuv.CREATER ";
	
	@Override
	public void save(TUrgeVerify entity) {
		super.save(INSERT_SQL, entity);
	}


	@Override
	public KeyHolder saveAutoGenerateKey(TUrgeVerify entity) {
		return null;
	}

	@Override
	public void update(TUrgeVerify entity) {
	
	}

	@Override
	public void delete(TUrgeVerify entity) {
		
	}

	@Override
	public void delete(Serializable id) {
	
	}

	@Override
	public TUrgeVerify query(TUrgeVerify entity) {
		
		return null;
	}

	@Override
	public TUrgeVerify query(Serializable id) {
		
		return null;
	}

	@Override
	public List<TUrgeVerify> queryForList(TUrgeVerify entity) {
		
		return null;
	}


	@Override
	public List<TUrgeVerify> queryForList(Map<String, ?> args) {
	
		return null;
	}


	@Override
	public QueryContext<TUrgeVerify> queryListForPagination(
			QueryContext<TUrgeVerify> qContext) {
		return null;
	}

	public UrgeVerifyInfo queryVerifyInfoByTaskId(String taskid) {
		StringBuilder querySql = new StringBuilder(SELECT_URGE_VERIFY_DETIAL);
		String where =" where task.ID="+taskid;
		querySql.append(where);
		return getJdbcTemplate().queryForObject(querySql.toString(),rowMapper);
	}

	public List<TUrgeVerify> queryRecordByCompanyId(String  companyId){
		StringBuilder querySql = new StringBuilder(SELECT_RECORD);
		String where =" where CID="+companyId;
		querySql.append(where);
		return super.queryForList(querySql.toString(), new TUrgeVerify());	
		//return (List<TUrgeVerify>) getJdbcTemplate().queryForObject(querySql.toString(), rowMapper);
	}

	public TUrgeVerify mapRow(ResultSet rs, int rowNum) throws SQLException {
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
	
	
	private RowMapper<UrgeVerifyInfo> rowMapper = new RowMapper<UrgeVerifyInfo>() {
		public UrgeVerifyInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
			UrgeVerifyInfo t=new UrgeVerifyInfo();			
	        t.setTaskid(rs.getString("TASKID"));        
			t.setUid(rs.getString("UID"));
			t.setUserrealname(rs.getString("USERREALNAME"));
			t.setUsertype(rs.getString("USERTYPE"));
			t.setRegistreason(rs.getString("REGISTREASON"));
			t.setRemark(rs.getString("REMARK"));
			t.setRecord(rs.getString("RECORD"));
	        t.setOwner(rs.getString("REALNAME"));
			t.setUserid(rs.getString("USERID"));
			t.setUsername(rs.getString("USERNAME"));
			t.setRegdate(rs.getTimestamp("REGDATE"));
			t.setLastlogindate(rs.getTimestamp("LASTLOGINDATE"));
			t.setBuynum(rs.getInt("BUYNUM"));
			t.setSellnum(rs.getInt("SELLNUM"));
			t.setAuthnum(rs.getInt("AUTHNUM"));
			t.setVerifynum(rs.getInt("VERIFYNUM"));
			t.setLastverifydate(rs.getTimestamp("LASTVERIFYDATE"));
			return t;
		}
	};
			
	public QueryContext<UrgeVerifyInfo> getVerifyList(QueryContext<UrgeVerifyInfo> qContext) {
		if(qContext == null){
			return null;
		}
		
		StringBuilder sql = new StringBuilder(SELECT_URGE_VERIFY_INFO);
		if(qContext.getParameter("registtimeorder") != null){
			sql.append(" ORDER BY REGDATE "+OrderBySort.enumOf(Integer.valueOf(qContext.getParameter("registtimeorder").toString())).getText());
		}
		else if(qContext.getParameter("verifytimeorder") != null){
			sql.append(" ORDER BY LASTVERIFYDATE "+OrderBySort.enumOf(Integer.valueOf(qContext.getParameter("verifytimeorder").toString())).getText());
		}
		else
		{
			sql.append(" ORDER BY LASTVERIFYDATE DESC");
		}
		
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(sql.toString(), args, rowMapper);
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
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(pageSql, args, rowMapper);
			qr.setResult(list);
		}
		return qContext;
	}

	@Override
	public QueryContext<UrgeVerifyInfo> getVerifyNoList(
			QueryContext<UrgeVerifyInfo> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder(SELECT_URGE_VERIFY_NO_INFO);
		if(qContext.getParameter("registtimeorder") != null){
			sql.append(" ORDER BY REGDATE "+OrderBySort.enumOf(Integer.valueOf(qContext.getParameter("registtimeorder").toString())).getText());
		}
		else if(qContext.getParameter("verifytimeorder") != null){
			sql.append(" ORDER BY LASTVERIFYDATE "+OrderBySort.enumOf(Integer.valueOf(qContext.getParameter("verifytimeorder").toString())).getText());
		}
		else
		{
			sql.append(" ORDER BY LASTVERIFYDATE DESC");
		}
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(sql.toString(), args, rowMapper);
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
			List<UrgeVerifyInfo> list = getJdbcTemplate().query(pageSql, args, rowMapper);
			qr.setResult(list);
		}
		return qContext;
	}
}
