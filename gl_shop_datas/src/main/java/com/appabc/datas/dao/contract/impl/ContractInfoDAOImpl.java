package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.contract.IContractInfoDAO;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月1日 下午6:52:45
 */

@Repository
public class ContractInfoDAOImpl extends BaseJdbcDao<TOrderInfo> implements
		IContractInfoDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_INFO (OID,FID,SELLERID,BUYERID,PRICE,TOTALNUM,CREATIME,CREATER,LIMITTIME,TOTALAMOUNT,AMOUNT,STATUS,OTYPE,REMARK,UPDATER,UPDATETIME,LIFECYCLE) VALUES (:id,:fid,:sellerid,:buyerid,:price,:totalnum,:creatime,:creater,:limittime,:totalamount,:amount,:status,:otype,:remark,:updater,:updatetime,:lifecycle) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_INFO SET FID = :fid,SELLERID = :sellerid,BUYERID = :buyerid,PRICE = :price,TOTALNUM = :totalnum,LIMITTIME = :limittime,TOTALAMOUNT = :totalamount,AMOUNT = :amount,STATUS = :status,OTYPE = :otype,REMARK = :remark,UPDATER = :updater,UPDATETIME = :updatetime,LIFECYCLE = :lifecycle WHERE OID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_INFO WHERE OID = :id ";
	private static final String SELECT_SQL = " SELECT OID,FID,SELLERID,BUYERID,PRICE,TOTALNUM,CREATIME,CREATER,LIMITTIME,TOTALAMOUNT,AMOUNT,STATUS,OTYPE,REMARK,UPDATER,UPDATETIME,LIFECYCLE FROM T_ORDER_INFO ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_ORDER_INFO WHERE 1=1 ";

	private RowMapper<ContractInfoBean> rowMapper = new RowMapper<ContractInfoBean>() {
		public ContractInfoBean mapRow(ResultSet rs,
				int rowNum) throws SQLException {
			ContractInfoBean bean = new ContractInfoBean();

			bean.setSaleType(OrderTypeEnum.enumOf(rs.getInt("SALETYPE")));
			bean.setProductName(rs.getString("PRODUCTNAME"));
			bean.setSellerName(rs.getString("SELLERNAME"));
			bean.setBuyerName(rs.getString("BUYERNAME"));
			bean.setId(rs.getString("OID"));
			bean.setFid(rs.getString("FID"));
			bean.setBuyerid(rs.getString("BUYERID"));
			bean.setPrice(rs.getFloat("PRICE"));
			bean.setLimittime(rs.getTimestamp("LIMITTIME"));
			bean.setOtype(ContractType.enumOf(rs.getString("OTYPE")));
			bean.setSellerid(rs.getString("SELLERID"));
			bean.setStatus(ContractStatus.enumOf(rs.getString("STATUS")));
			bean.setAmount(rs.getFloat("AMOUNT"));
			bean.setCreater(rs.getString("CREATER"));
			bean.setCreatime(rs.getTimestamp("CREATIME"));
			bean.setTotalamount(rs.getFloat("TOTALAMOUNT"));
			bean.setTotalnum(rs.getFloat("TOTALNUM"));
			bean.setRemark(rs.getString("REMARK"));
			bean.setUpdater(rs.getString("UPDATER"));
			bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
			bean.setLifecycle(ContractLifeCycle.enumOf(rs.getString("LIFECYCLE")));

			return bean;
		}
	};

	private String dynamicJoinSqlWithEntity(TOrderInfo entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "OID", entity.getId());
		addNameParamerSqlWithProperty(sql, "fid", "FID", entity.getFid());
		addNameParamerSqlWithProperty(sql, "sellerid", "SELLERID", entity.getSellerid());
		addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", entity.getBuyerid());
		addNameParamerSqlWithProperty(sql, "price", "PRICE", entity.getPrice());
		addNameParamerSqlWithProperty(sql, "totalnum", "TOTALNUM", entity.getPrice());
		//here need to set the limit time
		addNameParamerSqlWithProperty(sql, "totalamount", "TOTALAMOUNT", entity.getTotalamount());
		addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		//LIFECYCLE = :lifecycle
		addNameParamerSqlWithProperty(sql, "lifecycle", "LIFECYCLE", entity.getLifecycle());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TOrderInfo entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TOrderInfo entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TOrderInfo entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TOrderInfo entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.BaseBean)
	 */
	public TOrderInfo query(TOrderInfo entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)
	 */
	public TOrderInfo query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE OID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TOrderInfo> queryForList(TOrderInfo entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)
	 */
	public List<TOrderInfo> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderInfo> queryListForPagination(
			QueryContext<TOrderInfo> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "lifecycle", "LIFECYCLE", qContext.getParameters().get("lifecycle"));
		addNameParamerSqlWithProperty(sql, "status", "STATUS", qContext.getParameters().get("status"));
		addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", qContext.getParameters().get("cid"));
		//addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", qContext.getParameters().get("cid"));
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public TOrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderInfo tOrder = new TOrderInfo();
		tOrder.setId(rs.getString("OID"));
		tOrder.setFid(rs.getString("FID"));
		tOrder.setBuyerid(rs.getString("BUYERID"));
		tOrder.setPrice(rs.getFloat("PRICE"));
		tOrder.setLimittime(rs.getTimestamp("LIMITTIME"));
		tOrder.setOtype(ContractType.enumOf(rs.getString("OTYPE")));
		tOrder.setSellerid(rs.getString("SELLERID"));
		tOrder.setStatus(ContractStatus.enumOf(rs.getString("STATUS")));
		tOrder.setAmount(rs.getFloat("AMOUNT"));
		tOrder.setCreater(rs.getString("CREATER"));
		tOrder.setCreatime(rs.getTimestamp("CREATIME"));
		tOrder.setTotalamount(rs.getFloat("TOTALAMOUNT"));
		tOrder.setTotalnum(rs.getFloat("TOTALNUM"));
		tOrder.setRemark(rs.getString("REMARK"));
		tOrder.setUpdater(rs.getString("UPDATER"));
		tOrder.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		tOrder.setLifecycle(ContractLifeCycle.enumOf(rs.getString("LIFECYCLE")));
		return tOrder;
	}

	private StringBuilder buildBaseExContractInfoBeanSql(String cid){
		StringBuilder sql = new StringBuilder(" SELECT	CASE toi.SELLERID WHEN '" +cid+ "' THEN	'2'  ELSE 	'1' END AS SALETYPE,topi.PNAME as PRODUCTNAME,sellerTci.CNAME as SELLERNAME, ");
		sql.append(" buyerTci.CNAME as BUYERNAME, toi.OID as OID, toi.FID as FID, toi.SELLERID as SELLERID, toi.BUYERID as BUYERID, toi.PRICE as PRICE, toi.TOTALNUM as TOTALNUM, ");
		sql.append(" toi.CREATIME as CREATIME, toi.CREATER as CREATER, toi.LIMITTIME as LIMITTIME, toi.TOTALAMOUNT as TOTALAMOUNT, toi.AMOUNT as AMOUNT, toi.`STATUS` as STATUS, ");
		sql.append(" toi.OTYPE as OTYPE, toi.REMARK as REMARK, toi.UPDATER as UPDATER, toi.UPDATETIME as UPDATETIME, toi.LIFECYCLE as LIFECYCLE FROM T_ORDER_INFO toi ");
		sql.append(" LEFT JOIN T_ORDER_PRODUCT_INFO topi ON toi.FID = topi.FID LEFT JOIN T_COMPANY_INFO sellerTci ON toi.SELLERID = sellerTci.ID LEFT JOIN T_COMPANY_INFO buyerTci ON toi.BUYERID = buyerTci.ID WHERE 1 = 1 ");
		return sql;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractInfoListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<ContractInfoBean> queryContractInfoListForPagination(
			QueryContext<ContractInfoBean> qContext) {
		qContext.setOrderColumn("toi.UPDATETIME");
		String cid = qContext.getParameter("cid").toString();
		Object obj = qContext.getParameter("isUnPayContractList");
		boolean isUnPayContractList = false;
		if(obj != null){
			isUnPayContractList = BooleanUtils.toBoolean(obj.toString());
		}
		StringBuilder sql = buildBaseExContractInfoBeanSql(cid);
		if(isUnPayContractList){			
			sql.append(" AND toi.BUYERID = '"+cid+"' ");
		}else{
			sql.append(" AND (toi.SELLERID = '"+cid+"' or toi.BUYERID = '"+cid+"' ) ");
		}
		//sql.append(" AND (CASE toi.SELLERID WHEN '" +cid+ "' THEN	toi.SELLERID = '"+cid+"'  ELSE 	toi.BUYERID = '"+cid+"' END) ");
		String status = String.valueOf(qContext.getParameter("status"));
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, " toi.LIFECYCLE ", qContext.getParameters().get("lifecycle"), args);
		this.addStandardSqlWithParameter(sql, " toi.STATUS ", qContext.getParameters().get("status"), args);
		qContext.setParamList(args);
		if(StringUtils.isNotEmpty(status) && StringUtils.equalsIgnoreCase(status, ContractStatus.FINISHED.getVal())){
			sql.append(" AND toi.LIFECYCLE <> "+ContractLifeCycle.TIMEOUT_FINISHED.getVal()+"  ");
			sql.append(" AND toi.LIFECYCLE <> "+ContractLifeCycle.MANUAL_RESTORE.getVal()+"  ");
		}
		log.info("the sql str is : " + sql);
		if (StringUtils.isNotEmpty(qContext.getOrderColumn())) {
			sql.append(" ORDER BY ");
			sql.append(qContext.getOrderColumn());
			sql.append(" ");
			sql.append(qContext.getOrder());
		}
		log.info("the sql str before page is : " + sql);
		if (qContext.getPage().getPageIndex() < 0) {
			List<ContractInfoBean> list = getJdbcTemplate().query(sql.toString(),CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
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
			List<ContractInfoBean> list = getJdbcTemplate().query(pageSql,CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
			qContext.getQueryResult().setResult(list);
		}
		return qContext;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractInfoWithId(java.lang.String)
	 */
	@Override
	public ContractInfoBean queryContractInfoWithId(String cid,String contractId) {
		StringBuilder sql = buildBaseExContractInfoBeanSql(cid);
		sql.append(" AND (toi.SELLERID = '"+cid+"' or toi.BUYERID = '"+cid+"' ) ");
		sql.append(" AND toi.OID = '"+contractId+"' ");
		log.info("the queryContractInfoWithId sql str is  : " + sql.toString());
		return getJdbcTemplate().queryForObject(sql.toString(), rowMapper);
	}

	/* (non-Javadoc)获得企业合同数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#countByCid(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int countByCid(String cid, String lifecycle, String status) {
		
		if(StringUtils.isNotEmpty(cid)){
			StringBuilder sql = new StringBuilder(COUNT_SQL);
			List<Object> args = new ArrayList<Object>();
			
			sql.append(" AND (BUYERID=? OR SELLERID=?)");
			args.add(cid);
			args.add(cid);
			
			if(StringUtils.isNotEmpty(lifecycle)){
				sql.append(" AND LIFECYCLE=?");
				args.add(lifecycle);
			}
			if(StringUtils.isNotEmpty(status)){
				sql.append(" AND STATUS=?");
				args.add(status);
			}
			
			return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
		}
		
		return 0;
	}

	/* (non-Javadoc)获取买家和卖家共同交易比数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#countByBuyerAndSeller(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int countByBuyerAndSeller(String buyerid, String sellerid) {
		StringBuffer sql = new StringBuffer(COUNT_SQL);

		List<Object> args = new ArrayList<Object>();

		sql.append(" AND BUYERID=? AND SELLERID=? ");
		args.add(buyerid);
		args.add(sellerid);

		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

	/* (non-Javadoc)获取询单被撮合的次数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#getMatchingNumByFid(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getMatchingNumByFid(String fid) {
		
		if(StringUtils.isNotEmpty(fid)){
			StringBuilder sql = new StringBuilder(COUNT_SQL);
			List<Object> args = new ArrayList<Object>();
			
			sql.append(" AND FID=?");
			args.add(fid);
			
			return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
		}
		return 0;
	}
}
