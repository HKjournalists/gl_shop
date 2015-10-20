package com.appabc.datas.cms.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.TaskArbitrationInfo;
import com.appabc.bean.bo.TaskContractInfo;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.cms.dao.IContractDAO;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.tool.ContractCostDetailUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月1日 上午10:17:49
 */

@Repository
public class ContractDAOImpl extends BaseJdbcDao<TaskContractInfo> implements IContractDAO {

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TaskContractInfo entity) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TaskContractInfo entity) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TaskContractInfo entity) {

	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TaskContractInfo entity) {

	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {

	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TaskContractInfo query(TaskContractInfo entity) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TaskContractInfo query(Serializable id) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TaskContractInfo> queryForList(TaskContractInfo entity) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TaskContractInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskContractInfo> queryListForPagination(QueryContext<TaskContractInfo> qContext) {
		
		return null;
	}

	private RowMapper<TaskArbitrationInfo> rowMapper = new RowMapper<TaskArbitrationInfo>() {
		public TaskArbitrationInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
			TaskArbitrationInfo bean = new TaskArbitrationInfo();
			bean.setId(rs.getString("AID"));
			bean.setLid(rs.getString("LID"));
			bean.setCreater(rs.getString("CREATER"));
			bean.setCreatetime(rs.getTimestamp("CREATETIME"));
			bean.setRemark(rs.getString("REMARK"));
			bean.setDealer(rs.getString("DEALER"));
			bean.setDealtime(rs.getTimestamp("DEALTIME"));
			bean.setDealresult(rs.getString("DEALRESULT"));
			ContractArbitrationStatus status = ContractArbitrationStatus.enumOf(rs.getInt("STATUS"));
			bean.setStatus(status);
			//"TOI.OID,TOI.SELLERID,TOI.BUYERID,TOI.REMARK AS TITLE, SU.USERNAME, SU.REALNAME ,TU.CID, TU.USERNAME AS TUSERNAME, TU.PHONE, TCI.CTYPE"
			
			bean.setOid(rs.getString("OID"));
			bean.setSellerid(rs.getString("SELLERID"));
			bean.setBuyerid(rs.getString("BUYERID"));
			bean.setTitle(rs.getString("TITLE"));
			bean.setTusername(rs.getString("TUSERNAME"));
			CompanyType tctype = CompanyType.enumOf(rs.getString("CTYPE"));
			bean.setTctype(tctype);
			bean.setTphone(rs.getString("PHONE"));
			bean.setSrealname(rs.getString("REALNAME"));
			bean.setSusername(rs.getString("USERNAME"));
			bean.setCid(rs.getString("CID"));
			
			return bean;
		}
	};
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.IContractDAO#getContractArbitrationList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskArbitrationInfo> getContractArbitrationList(QueryContext<TaskArbitrationInfo> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder(" SELECT OPERS.OID,OINFO.SELLERID,OINFO.BUYERID,OFIND.TITlE,SU.USERNAME, SU.REALNAME ,TU.CID, TU.USERNAME AS TUSERNAME, TU.PHONE,TCI.CTYPE,OINFO.FID,TOA.* FROM SYS_TASKS SYST  ");
		sql.append(" LEFT JOIN T_ORDER_ARBITRATION TOA ON SYST.OBJECT_ID = TOA.AID LEFT JOIN T_ORDER_OPERATIONS OPERS ON OPERS.LID = TOA.LID  ");
		sql.append(" LEFT JOIN T_ORDER_INFO OINFO ON OINFO.OID = OPERS.OID LEFT JOIN T_ORDER_FIND OFIND ON OFIND.FID = OINFO.FID ");
		sql.append(" LEFT JOIN T_COMPANY_INFO TCI ON TCI.ID = TOA.CREATER LEFT JOIN SYS_USERS SU ON SU.ID = TOA.DEALER ");
		sql.append(" LEFT JOIN T_USER TU ON TU.CID = TOA.CREATER ");
		sql.append(" WHERE 1 = 1  ");
		
		//here sql is old version for this logic
		/* * 
		 SELECT TOI.OID,TOI.SELLERID,TOI.BUYERID,TOI.REMARK AS TITLE, SU.USERNAME, SU.REALNAME ,TU.CID, TU.USERNAME AS TUSERNAME, TU.PHONE, TCI.CTYPE, TOA.* FROM T_ORDER_ARBITRATION TOA 
		 LEFT JOIN SYS_TASKS SYST ON SYST.OBJECT_ID = TOA.AID 
		 LEFT JOIN T_ORDER_INFO TOI ON (TOA.CREATER = TOI.BUYERID OR TOA.CREATER = TOI.SELLERID) 
		 LEFT JOIN SYS_USERS SU ON SU.ID = TOA.DEALER LEFT JOIN T_USER TU ON TU.CID = TOA.CREATER 
		 LEFT JOIN T_COMPANY_INFO TCI ON TCI.ID = TOA.CREATER  
		 WHERE TOA.STATUS = 1  AND SYST.TYPE = 42
		 GROUP BY TOA.AID DESC ORDER BY CREATETIME DESC
		 * */
		
		List<Object> listArgs = new ArrayList<Object>();
		
		Object taskType = qContext.getParameter("taskType");
		int type = taskType == null ? null : ((TaskType)taskType).getValue();
		addStandardSqlWithParameter(sql, " SYST.TYPE ", type, listArgs);
		
		Object status = qContext.getParameter("status");
		String cstatus = status == null ? null : ((ContractStatus)status).getVal();
		addStandardSqlWithParameter(sql, " TOI.STATUS ", cstatus, listArgs);
		
		Object generator = qContext.getParameter("generator");
		if(generator != null){
			addStandardSqlWithParameter(sql, " SYST.OWNER ", (Integer)generator, listArgs);
		}
		
		qContext.setParamList(listArgs);
		
		sql.append(" ORDER BY SYST.CREATE_TIME DESC ");
		
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<TaskArbitrationInfo> list = getJdbcTemplate().query(sql.toString(), args, rowMapper);
			QueryResult<TaskArbitrationInfo> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<TaskArbitrationInfo> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<TaskArbitrationInfo> list = getJdbcTemplate().query(pageSql, args, rowMapper);
			qr.setResult(list);
		}
		return qContext;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.IContractDAO#getConfirmContractOrderList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskContractInfo> getConfirmContractOrderList(QueryContext<TaskContractInfo> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SYST.ID,SYST.TYPE,SYST.OBJECT_ID,SYST.OWNER,SYST.FINISHED,SYST.FINISH_TIME,SYST.CREATE_TIME,SYST.UPDATE_TIME, ");
		sql.append(" SYSU.ID AS OSUID,SYSU.USERNAME AS OSUUSERNAME,SYSU.REALNAME AS OSUREALNAME,STU.USERNAME AS SUSERNAME,STU.PHONE AS SPHONE,BTU.USERNAME AS BUSERNAME,BTU.PHONE AS BPHONE, ");
		sql.append(" TOI.OID,TOI.FID,TOI.SELLERID,TOI.BUYERID,TOI.CREATIME,TOI.STATUS,TOI.OTYPE,TOI.LIFECYCLE,TOI.REMARK FROM SYS_TASKS SYST ");
		sql.append(" LEFT JOIN T_ORDER_INFO TOI ON SYST.OBJECT_ID = TOI.OID LEFT JOIN SYS_USERS SYSU ON SYSU.ID = SYST.OWNER  ");
		sql.append(" LEFT JOIN T_USER STU ON STU.CID = TOI.SELLERID LEFT JOIN T_USER BTU ON BTU.CID = TOI.BUYERID  ");
		sql.append(" WHERE 1 = 1 ");
		
		List<Object> listArgs = new ArrayList<Object>();
		
		Object taskType = qContext.getParameter("taskType");
		int type = taskType == null ? null : ((TaskType)taskType).getValue();
		addStandardSqlWithParameter(sql, " SYST.TYPE ", type, listArgs);
		
		Object status = qContext.getParameter("status");
		String cstatus = status == null ? null : ((ContractStatus)status).getVal();
		addStandardSqlWithParameter(sql, " TOI.STATUS ", cstatus, listArgs);
		
		Object generator = qContext.getParameter("generator");
		if(generator != null){
			addStandardSqlWithParameter(sql, " SYST.OWNER ", (Integer)generator, listArgs);
		}
		//int owner = generator == null ? null : ((int)generator);
		
		qContext.setParamList(listArgs);
		
		sql.append(" ORDER BY SYST.CREATE_TIME DESC ");
		
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<TaskContractInfo> list = getJdbcTemplate().query(sql.toString(), args, this);
			QueryResult<TaskContractInfo> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<TaskContractInfo> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<TaskContractInfo> list = getJdbcTemplate().query(pageSql, args, this);
			qr.setResult(list);
		}
		return qContext;
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TaskContractInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
		TaskContractInfo bean = new TaskContractInfo();
		
		bean.setId(rs.getString("ID"));
		bean.setBuyerid(rs.getString("BUYERID"));
		bean.setBuyerPhone(rs.getString("BPHONE"));
		bean.setSellerid(rs.getString("SELLERID"));
		bean.setSellerPhone(rs.getString("SPHONE"));
		bean.setOid(rs.getString("OID"));
		bean.setOperUserName(rs.getString("OSUREALNAME"));
		bean.setRemark(rs.getString("REMARK"));
		bean.setCreatime(rs.getTimestamp("CREATE_TIME"));
		bean.setOtype(ContractType.enumOf(rs.getString("OTYPE")));
		bean.setStatus(ContractStatus.enumOf(rs.getString("STATUS")));
		bean.setLifecycle(ContractLifeCycle.enumOf(rs.getString("LIFECYCLE")));
		
		Date creatime = rs.getTimestamp("CREATIME");
		long t = DateUtil.getDifferTimeMillisTwoDate(creatime, DateUtil.getNowDate());
		long d = (long)RandomUtil.mulRound(ContractCostDetailUtil.getContractDraftConfirmLimitNumWD(),(60 * 60 * 1000));
		if(t>d){
			bean.setLimitTimeStr("0");
		}else{
			int minutes = (int) Math.abs((d-t) / ( 60 * 1000));
			bean.setLimitTimeStr(String.valueOf(minutes));
		}
		return bean;
	}

}
