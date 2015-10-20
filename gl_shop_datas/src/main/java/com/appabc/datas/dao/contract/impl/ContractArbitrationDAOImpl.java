package com.appabc.datas.dao.contract.impl;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.contract.IContractArbitrationDAO;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create Date : 2014年9月2日 下午3:01:55
 */

@Repository
public class ContractArbitrationDAOImpl extends BaseJdbcDao<TOrderArbitration>
		implements IContractArbitrationDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_ARBITRATION (AID,LID,CREATER,CREATETIME,REMARK,DEALER,DEALTIME,DEALRESULT,STATUS) VALUES (:id,:lid,:creater,:createtime,:remark,:dealer,:dealtime,:dealresult,:status) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_ARBITRATION SET LID = :lid,CREATER = :creater,CREATETIME = :createtime,REMARK = :remark,DEALER = :dealer,DEALTIME = :dealtime,DEALRESULT = :dealresult,STATUS = :status WHERE AID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_ARBITRATION WHERE AID = :id ";
	private static final String SELECT_SQL = " SELECT AID,LID,CREATER,CREATETIME,REMARK,DEALER,DEALTIME,DEALRESULT,STATUS FROM T_ORDER_ARBITRATION ";
	private static final String SELECTARBITRATION_SQL = " SELECT TCI.CNAME,TCI.CTYPE,TOI.REMARK AS COTITLE,TOO.OID,TOA.* FROM T_ORDER_ARBITRATION TOA LEFT JOIN T_COMPANY_INFO TCI ON TCI.ID = TOA.CREATER LEFT JOIN T_ORDER_OPERATIONS TOO ON TOO.LID = TOA.LID LEFT JOIN T_ORDER_INFO TOI ON TOI.OID = TOO.OID WHERE 1 = 1 ";

	private String dynamicJoinSqlWithEntity(TOrderArbitration bean,
			StringBuilder sql) {
		if (bean == null || sql == null || sql.length() <= 0) {
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "AID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "lid", "LID", bean.getLid());
		this.addNameParamerSqlWithProperty(sql, "creater", "CREATER",
				bean.getCreater());
		// CREATETIME = :createtime
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK",
				bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "dealer", "DEALER",
				bean.getDealer());
		// DEALTIME = :dealtime
		this.addNameParamerSqlWithProperty(sql, "dealresult", "DEALRESULT",
				bean.getDealresult());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS",
				bean.getStatus());
		return sql.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void save(TOrderArbitration entity) {
		super.save(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.
	 * common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TOrderArbitration entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void update(TOrderArbitration entity) {
		super.update(UPDATE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void delete(TOrderArbitration entity) {
		super.delete(DELETE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.
	 * BaseBean)
	 */
	public TOrderArbitration query(TOrderArbitration entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)
	 */
	public TOrderArbitration query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE AID = :id  ");
		return super.query(sql.toString(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public List<TOrderArbitration> queryForList(TOrderArbitration entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super
				.queryForList(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)
	 */
	public List<TOrderArbitration> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.
	 * common.base.QueryContext)
	 */
	public QueryContext<TOrderArbitration> queryListForPagination(
			QueryContext<TOrderArbitration> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	public TOrderArbitration mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderArbitration bean = new TOrderArbitration();

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
		
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.datas.dao.contract.IContractArbitrationDAO#queryArbitrationForList
	 * (java.lang.String)
	 */
	public List<TOrderArbitration> queryArbitrationForList(String contractId) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE LID IN (SELECT LID from T_ORDER_OPERATIONS where OID = ? ) ORDER BY CREATETIME ASC ");
		return super.queryForList(sql.toString(),
				Collections.singletonList(contractId));
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractArbitrationDAO#queryContractArbitrationInfoForList(com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus)  
	 */
	@Override
	public List<ContractArbitrationBean> queryContractArbitrationInfoForList(
			ContractArbitrationStatus status) {
		if(status == null){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SELECTARBITRATION_SQL);
		sql.append(" AND TOA.STATUS = ? ORDER BY TOA.CREATETIME DESC");
		return this.getJdbcTemplate().query(sql.toString(), rowMapper, Collections.singletonList(status.getVal()).toArray());
	}
	
	private RowMapper<ContractArbitrationBean> rowMapper = new RowMapper<ContractArbitrationBean>() {
		public ContractArbitrationBean mapRow(ResultSet rs,
				int rowNum) throws SQLException {
			ContractArbitrationBean bean = new ContractArbitrationBean();
			
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
			bean.setCname(rs.getString("CNAME"));
			bean.setCoTitle(rs.getString("COTITLE"));
			CompanyType ctype = CompanyType.enumOf(rs.getString("CTYPE"));
			bean.setCtype(ctype);
			bean.setOid(rs.getString("OID"));
			
			return bean;
		}
	};

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractArbitrationDAO#getContractArbitrationInfoListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ContractArbitrationBean> getContractArbitrationInfoListForPagination(
			QueryContext<ContractArbitrationBean> qContext) {
		if (qContext == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SELECTARBITRATION_SQL);
		//sql.append(" AND TOA.STATUS = ?  ORDER BY TOA.CREATETIME DESC ");
		List<Object> args = new ArrayList<Object>();
		String status = String.valueOf(qContext.getParameter("status"));
		this.addStandardSqlWithParameter(sql, " TOA.STATUS ", status, args);
		qContext.setParamList(args);
		sql.append(" ORDER BY TOA.CREATETIME DESC ");
		
		log.debug("The Sql Str Before Page Is : " + sql + " ; And The Value Is : "+qContext.getParameters());
		if (qContext.getPage().getPageIndex() < 0) {
			List<ContractArbitrationBean> list = getJdbcTemplate().query(sql.toString(),CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
			qContext.getQueryResult().setResult(list);
			qContext.getQueryResult().setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("the count sql str is  : " + countSql);
			// 获取记录总数
			@SuppressWarnings("deprecation")
			int count = getJdbcTemplate().queryForInt(countSql,CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray());
			qContext.getQueryResult().setTotalSize(count);
			qContext.getPage().setTotalSize(count);

			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),qContext.getPage());
			log.info("the page sql str is  : " + pageSql);
			// 获取分页后的记录数量
			List<ContractArbitrationBean> list = getJdbcTemplate().query(pageSql,CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
			qContext.getQueryResult().setResult(list);
		}
		return qContext;
	}

}
