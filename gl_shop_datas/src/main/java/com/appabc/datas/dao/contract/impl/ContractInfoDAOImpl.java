package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.enums.ContractInfo.ContractDraftStageBuyerSellerDoType;
import com.appabc.bean.enums.ContractInfo.ContractEvaluationType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.bean.enums.ContractInfo.ContractWebCmsTradeType;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.tool.ContractCostDetailUtil;

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

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_INFO (OID,FID,SELLERID,BUYERID,PRICE,TOTALNUM,CREATIME,CREATER,LIMITTIME,TOTALAMOUNT,AMOUNT,SETTLEAMOUNT,STATUS,OTYPE,REMARK,UPDATER,UPDATETIME,LIFECYCLE) VALUES (:id,:fid,:sellerid,:buyerid,:price,:totalnum,:creatime,:creater,:limittime,:totalamount,:amount,:settleamount,:status,:otype,:remark,:updater,:updatetime,:lifecycle) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_INFO SET FID = :fid,SELLERID = :sellerid,BUYERID = :buyerid,PRICE = :price,TOTALNUM = :totalnum,LIMITTIME = :limittime,TOTALAMOUNT = :totalamount,AMOUNT = :amount,SETTLEAMOUNT = :settleamount,STATUS = :status,OTYPE = :otype,REMARK = :remark,UPDATER = :updater,UPDATETIME = :updatetime,LIFECYCLE = :lifecycle WHERE OID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_INFO WHERE OID = :id ";
	private static final String SELECT_SQL = " SELECT OID,FID,SELLERID,BUYERID,PRICE,TOTALNUM,CREATIME,CREATER,LIMITTIME,TOTALAMOUNT,AMOUNT,SETTLEAMOUNT,STATUS,OTYPE,REMARK,UPDATER,UPDATETIME,LIFECYCLE FROM T_ORDER_INFO ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_ORDER_INFO WHERE 1=1 ";

	private RowMapper<ContractInfoBean> rowMapper = new RowMapper<ContractInfoBean>() {
		public ContractInfoBean mapRow(ResultSet rs,
				int rowNum) throws SQLException {
			ContractInfoBean bean = new ContractInfoBean();
			
			ContractLifeCycle lifeCycle = ContractLifeCycle.enumOf(rs.getString("LIFECYCLE"));
			ContractStatus status = ContractStatus.enumOf(rs.getString("STATUS"));
			ContractType type = ContractType.enumOf(rs.getString("OTYPE"));
			OrderTypeEnum orderType = OrderTypeEnum.enumOf(rs.getInt("SALETYPE"));
			ContractOperateType operateType = ContractOperateType.enumOf(rs.getString("OPTYPE"));
			String oid = rs.getString("OID");
			
			bean.setSaleType(orderType);
			bean.setProductName(rs.getString("PRODUCTNAME"));
			bean.setProductId(rs.getString("PRODUCTID"));
			bean.setProductCode(rs.getString("PRODUCTCODE"));
			bean.setProductType(rs.getString("PRODUCTTYPE"));
			bean.setSellerName(rs.getString("SELLERNAME"));
			bean.setBuyerName(rs.getString("BUYERNAME"));
			bean.setId(oid);
			bean.setFid(rs.getString("FID"));
			bean.setBuyerid(rs.getString("BUYERID"));
			bean.setPrice(rs.getDouble("PRICE"));
			bean.setLimittime(rs.getTimestamp("LIMITTIME"));
			bean.setOtype(type);
			bean.setSellerid(rs.getString("SELLERID"));
			bean.setStatus(status);
			bean.setAmount(rs.getDouble("AMOUNT"));
			bean.setSettleamount(rs.getDouble("SETTLEAMOUNT"));
			bean.setCreater(rs.getString("CREATER"));
			bean.setCreatime(rs.getTimestamp("CREATIME"));
			bean.setTotalamount(rs.getDouble("TOTALAMOUNT"));
			bean.setTotalnum(rs.getDouble("TOTALNUM"));
			bean.setRemark(rs.getString("REMARK"));
			bean.setUpdater(rs.getString("UPDATER"));
			bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
			bean.setLifecycle(lifeCycle);
			bean.setOperator(rs.getString("OPOR"));
			bean.setOperationTime(rs.getTimestamp("OPTIME"));
			bean.setOperateType(operateType);
			bean.setMyContractType(ContractStatus.enumOf(rs.getString("MYCONTRACTTYPE")));
			if(lifeCycle == ContractLifeCycle.DRAFTING || lifeCycle == ContractLifeCycle.DRAFTING_CANCEL || lifeCycle == ContractLifeCycle.TIMEOUT_FINISHED){
				bean.setBuyerDraftStatus(ContractDraftStageBuyerSellerDoType.enumOf(rs.getString("BUYTYPE")));
				bean.setSellerDraftStatus(ContractDraftStageBuyerSellerDoType.enumOf(rs.getString("SELLTYPE")));
				bean.setDraftLimitTime(DateUtil.getDateMoveByHours(rs.getTimestamp("CREATIME"), ContractCostDetailUtil.getContractDraftConfirmLimitNumWD()));
			}
			if(status == ContractStatus.DOING && type == ContractType.SIGNED && (lifeCycle == ContractLifeCycle.SINGED || lifeCycle == ContractLifeCycle.IN_THE_PAYMENT)){
				bean.setPayGoodsLimitTime(ContractCostDetailUtil.getPayGoodsLimitTime(rs.getTimestamp("UPDATETIME"), bean.getLimittime()));
			}
			if(status == ContractStatus.DOING && type == ContractType.SIGNED && lifeCycle == ContractLifeCycle.CONFIRMING_GOODS_FUNDS){
				bean.setAgreeFinalEstimeLimitTime(DateUtil.getDate(rs.getTimestamp("UPDATETIME"), ContractCostDetailUtil.getContractAgreeFinalEstimateLimitNum()));
			}
			if(status == ContractStatus.FINISHED){
				bean.setSellerEvaluation(ContractEvaluationType.enumOf(rs.getString("SEVAL")));
				bean.setBuyerEvaluation(ContractEvaluationType.enumOf(rs.getString("BEVAL")));
			}
			//填充买家的最后一次操作信息
			bean.setBuyerLid(rs.getString("BLID"));
			bean.setBuyerOperator(rs.getString("BOPERATOR"));
			bean.setBuyerOperationtime(rs.getTimestamp("BOPERATIONTIME"));
			bean.setBuyerOperatorType(ContractOperateType.enumOf(rs.getString("BTYPE")));
			bean.setBuyerOperatorStatus(ContractLifeCycle.enumOf(rs.getString("BORDERSTATUS")));
			bean.setBuyerOperatorOldStatus(ContractLifeCycle.enumOf(rs.getString("BOLDSTATUS")));
			//填充卖家的最后一次操作信息
			bean.setSellerLid(rs.getString("SLID"));
			bean.setSellerOperator(rs.getString("SOPERATOR"));
			bean.setSellerOperationtime(rs.getTimestamp("SOPERATIONTIME"));
			bean.setSellerOperatorType(ContractOperateType.enumOf(rs.getString("STYPE")));
			bean.setSellerOperatorStatus(ContractLifeCycle.enumOf(rs.getString("SORDERSTATUS")));
			bean.setSellerOperatorOldStatus(ContractLifeCycle.enumOf(rs.getString("SOLDSTATUS")));
			return bean;
		}
	};
	
	private String buildBaseExContractInfoMineBeanSql(String cid){
		StringBuilder sql = new StringBuilder(" SELECT * FROM	( SELECT * FROM	( ");
		sql.append(" SELECT D.*,too.TYPE AS OPTYPE ,too.OPERATOR AS OPOR,too.OPERATIONTIME AS OPTIME,too.ORDERSTATUS AS OPORDERSTATUS,too.OLDSTATUS AS OPOLDSTATUS,	");
		sql.append(" CASE STOO.TYPE WHEN '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' THEN '"+ContractEvaluationType.EVALUATION.getVal()+"' ELSE '"+ContractEvaluationType.NOTHING.getVal()+"' END AS SEVAL, ");
		sql.append(" CASE BTOO.TYPE WHEN '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' THEN '"+ContractEvaluationType.EVALUATION.getVal()+"' ELSE '"+ContractEvaluationType.NOTHING.getVal()+"' END AS BEVAL, ");
		sql.append(" CASE btoop.TYPE WHEN '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CANCEL.getVal()+"' WHEN '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CONFIRM.getVal()+"' ELSE '"+ContractDraftStageBuyerSellerDoType.NOTHING.getVal()+"' END AS BUYTYPE, ");
		sql.append(" CASE stoop.TYPE WHEN '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CANCEL.getVal()+"' WHEN '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CONFIRM.getVal()+"' ELSE '"+ContractDraftStageBuyerSellerDoType.NOTHING.getVal()+"' END AS SELLTYPE, ");
		sql.append(" CASE D.SELLERID WHEN '" +cid+ "' THEN '"+OrderTypeEnum.ORDER_TYPE_SELL.getVal()+"' ELSE '"+OrderTypeEnum.ORDER_TYPE_BUY.getVal()+"' END AS SALETYPE, ");
		sql.append(" topi.PNAME AS PRODUCTNAME,topi.PID AS PRODUCTID,topi.PCODE AS PRODUCTCODE,topi.PTYPE AS PRODUCTTYPE,sellerTci.CNAME AS SELLERNAME,buyerTci.CNAME AS BUYERNAME,mine.STATUS AS MYCONTRACTTYPE ");
		sql.append(" FROM ORDERINFOSELLERBUYERLASTOPERATOR D ");
		sql.append(" LEFT JOIN T_ORDER_PRODUCT_INFO topi ON D.FID = topi.FID LEFT JOIN T_COMPANY_INFO sellerTci ON D.SELLERID = sellerTci.ID LEFT JOIN T_COMPANY_INFO buyerTci ON D.BUYERID = buyerTci.ID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS btoop ON btoop.OID = D.OID AND (btoop.TYPE = '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' OR btoop.TYPE = '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"') AND btoop.OPERATOR = D.BUYERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS stoop ON stoop.OID = D.OID AND (stoop.TYPE = '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' OR stoop.TYPE = '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"') AND stoop.OPERATOR = D.SELLERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS STOO ON STOO.OID = D.OID AND STOO.TYPE = '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' AND STOO.OPERATOR = D.SELLERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS BTOO ON BTOO.OID = D.OID AND BTOO.TYPE = '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' AND BTOO.OPERATOR = D.BUYERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS too ON too.OID = D.OID AND too.ORDERSTATUS = D.LIFECYCLE	");
		sql.append(" LEFT JOIN T_ORDER_MINE mine ON D.OID = mine.OID AND mine.CID = '" +cid+ "'	");
		sql.append(" #PARAMETER  ");
		sql.append(" ORDER BY D.UPDATETIME DESC,too.OPERATIONTIME DESC) C GROUP BY C.OID ) E ORDER BY E.UPDATETIME DESC,E.CREATIME DESC,E.STATUS ASC	");
		return sql.toString();
	}
	
	private String buildBaseExContractInfoBeanSql(String cid){
		StringBuilder sql = new StringBuilder(" SELECT * FROM	( SELECT * FROM	( ");
		sql.append(" SELECT D.*,too.TYPE AS OPTYPE ,too.OPERATOR AS OPOR,too.OPERATIONTIME AS OPTIME,too.ORDERSTATUS AS OPORDERSTATUS,too.OLDSTATUS AS OPOLDSTATUS,	");
		sql.append(" CASE STOO.TYPE WHEN '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' THEN '"+ContractEvaluationType.EVALUATION.getVal()+"' ELSE '"+ContractEvaluationType.NOTHING.getVal()+"' END AS SEVAL, ");
		sql.append(" CASE BTOO.TYPE WHEN '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' THEN '"+ContractEvaluationType.EVALUATION.getVal()+"' ELSE '"+ContractEvaluationType.NOTHING.getVal()+"' END AS BEVAL, ");
		sql.append(" CASE btoop.TYPE WHEN '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CANCEL.getVal()+"' WHEN '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CONFIRM.getVal()+"' ELSE '"+ContractDraftStageBuyerSellerDoType.NOTHING.getVal()+"' END AS BUYTYPE, ");
		sql.append(" CASE stoop.TYPE WHEN '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CANCEL.getVal()+"' WHEN '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"' THEN '"+ContractDraftStageBuyerSellerDoType.CONFIRM.getVal()+"' ELSE '"+ContractDraftStageBuyerSellerDoType.NOTHING.getVal()+"' END AS SELLTYPE, ");
		sql.append(" CASE D.SELLERID WHEN '" +cid+ "' THEN '"+OrderTypeEnum.ORDER_TYPE_SELL.getVal()+"' ELSE '"+OrderTypeEnum.ORDER_TYPE_BUY.getVal()+"' END AS SALETYPE, ");
		sql.append(" topi.PNAME AS PRODUCTNAME,topi.PID AS PRODUCTID,topi.PCODE AS PRODUCTCODE,topi.PTYPE AS PRODUCTTYPE,sellerTci.CNAME AS SELLERNAME,buyerTci.CNAME AS BUYERNAME,D.STATUS AS MYCONTRACTTYPE ");
		sql.append(" FROM ORDERINFOSELLERBUYERLASTOPERATOR D ");
		sql.append(" LEFT JOIN T_ORDER_PRODUCT_INFO topi ON D.FID = topi.FID LEFT JOIN T_COMPANY_INFO sellerTci ON D.SELLERID = sellerTci.ID LEFT JOIN T_COMPANY_INFO buyerTci ON D.BUYERID = buyerTci.ID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS btoop ON btoop.OID = D.OID AND (btoop.TYPE = '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' OR btoop.TYPE = '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"') AND btoop.OPERATOR = D.BUYERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS stoop ON stoop.OID = D.OID AND (stoop.TYPE = '"+ContractOperateType.CANCEL_CONTRACT.getVal()+"' OR stoop.TYPE = '"+ContractOperateType.CONFRIM_CONTRACT.getVal()+"') AND stoop.OPERATOR = D.SELLERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS STOO ON STOO.OID = D.OID AND STOO.TYPE = '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' AND STOO.OPERATOR = D.SELLERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS BTOO ON BTOO.OID = D.OID AND BTOO.TYPE = '"+ContractOperateType.EVALUATION_CONTRACT.getVal()+"' AND BTOO.OPERATOR = D.BUYERID ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS too ON too.OID = D.OID AND too.ORDERSTATUS = D.LIFECYCLE	");
		sql.append(" #PARAMETER  ");
		sql.append(" ORDER BY D.UPDATETIME DESC,too.OPERATIONTIME DESC) C GROUP BY C.OID ) E ORDER BY E.UPDATETIME DESC,E.CREATIME DESC,E.STATUS ASC	");
		return sql.toString();
	}
	
	private String dynamicJoinSqlWithEntity(TOrderInfo entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		if(sql.indexOf("WHERE 1=1") == -1 ) sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "OID", entity.getId());
		addNameParamerSqlWithProperty(sql, "fid", "FID", entity.getFid());
		addNameParamerSqlWithProperty(sql, "sellerid", "SELLERID", entity.getSellerid());
		addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", entity.getBuyerid());
		addNameParamerSqlWithProperty(sql, "price", "PRICE", entity.getPrice());
		addNameParamerSqlWithProperty(sql, "totalnum", "TOTALNUM", entity.getPrice());
		//here need to set the limit time
		addNameParamerSqlWithProperty(sql, "totalamount", "TOTALAMOUNT", entity.getTotalamount());
		addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		addNameParamerSqlWithProperty(sql, "settleamount", "SETTLEAMOUNT", entity.getSettleamount());
		
		//LIFECYCLE = :lifecycle
		addNameParamerSqlWithProperty(sql, "lifecycle", "LIFECYCLE", entity.getLifecycle());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		return sql.toString();
	}

	private QueryContext<ContractInfoBean> paginationQueryContractInfoBean(String sql,QueryContext<ContractInfoBean> qContext){
		if(StringUtils.isEmpty(sql) ||  qContext == null){
			return null;
		}
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<ContractInfoBean> list = getJdbcTemplate().query(sql, args, rowMapper);
			QueryResult<ContractInfoBean> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql);
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<ContractInfoBean> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql,pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<ContractInfoBean> list = getJdbcTemplate().query(pageSql, args, rowMapper);
			qr.setResult(list);
		}
		return qContext;
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
		addNameParamerSqlWithProperty(sql, "lifecycle", "LIFECYCLE", qContext.getParameter("lifecycle"));
		addNameParamerSqlWithProperty(sql, "status", "STATUS", qContext.getParameter("status"));
		addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", qContext.getParameter("cid"));
		//addNameParamerSqlWithProperty(sql, "buyerid", "BUYERID", qContext.getParameter("cid"));
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
		tOrder.setPrice(rs.getDouble("PRICE"));
		tOrder.setLimittime(rs.getTimestamp("LIMITTIME"));
		tOrder.setOtype(ContractType.enumOf(rs.getString("OTYPE")));
		tOrder.setSellerid(rs.getString("SELLERID"));
		tOrder.setStatus(ContractStatus.enumOf(rs.getString("STATUS")));
		tOrder.setAmount(Double.valueOf(RandomUtil.format(rs.getDouble("AMOUNT"))));
		tOrder.setSettleamount(rs.getDouble("SETTLEAMOUNT"));
		tOrder.setCreater(rs.getString("CREATER"));
		tOrder.setCreatime(rs.getTimestamp("CREATIME"));
		tOrder.setTotalamount(rs.getDouble("TOTALAMOUNT"));
		tOrder.setTotalnum(rs.getFloat("TOTALNUM"));
		tOrder.setRemark(rs.getString("REMARK"));
		tOrder.setUpdater(rs.getString("UPDATER"));
		tOrder.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		tOrder.setLifecycle(ContractLifeCycle.enumOf(rs.getString("LIFECYCLE")));
		return tOrder;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractInfoListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<ContractInfoBean> queryContractInfoListForPagination(
			QueryContext<ContractInfoBean> qContext) {
		String cid = qContext.getParameter("cid").toString();
		Object obj = qContext.getParameter("isUnPayContractList");
		boolean isUnPayContractList = false;
		if(obj != null){
			isUnPayContractList = BooleanUtils.toBoolean(obj.toString());
		}
		String sqlEx = buildBaseExContractInfoBeanSql(cid);
		StringBuilder sqlExSb = new StringBuilder("  WHERE 1 = 1   ");
		if(isUnPayContractList){			
			sqlExSb.append(" AND D.BUYERID = '"+cid+"' ");
		}else{
			sqlExSb.append(" AND (D.SELLERID = '"+cid+"' or D.BUYERID = '"+cid+"' ) ");
		}
		//sql.append(" AND (CASE toi.SELLERID WHEN '" +cid+ "' THEN	toi.SELLERID = '"+cid+"'  ELSE 	toi.BUYERID = '"+cid+"' END) ");
		String status = String.valueOf(qContext.getParameter("status"));
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sqlExSb, " D.LIFECYCLE ", qContext.getParameter("lifecycle"), args);
		this.addStandardSqlWithParameter(sqlExSb, " D.STATUS ", qContext.getParameter("status"), args);
		qContext.setParamList(args);
		if(StringUtils.isNotEmpty(status) && ContractStatus.enumOf(status) == ContractStatus.FINISHED){
			sqlExSb.append(" AND D.LIFECYCLE <> "+ContractLifeCycle.TIMEOUT_FINISHED.getVal()+"  ");
			sqlExSb.append(" AND D.LIFECYCLE <> "+ContractLifeCycle.MANUAL_RESTORE.getVal()+"  ");
		}
		log.info("the sql str is : " + sqlEx);
		String sql = sqlEx.replaceAll("#PARAMETER", sqlExSb.toString());
		log.info("the sql str before page is : " + sql);
		return paginationQueryContractInfoBean(sql, qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractListOfMineForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ContractInfoBean> queryContractListOfMineForPagination(QueryContext<ContractInfoBean> qContext) {
		String cid = qContext.getParameter("cid").toString();
		String sqlEx = buildBaseExContractInfoMineBeanSql(cid);
		StringBuilder sqlExSb = new StringBuilder();
		sqlExSb.append(" WHERE 1 = 1 ");
		String status = String.valueOf(qContext.getParameter("status"));
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sqlExSb, " mine.LIFECYCLE ", qContext.getParameter("lifecycle"), args);
		this.addStandardSqlWithParameter(sqlExSb, " mine.STATUS ", qContext.getParameter("status"), args);
		qContext.setParamList(args);
		if(StringUtils.isNotEmpty(status) && ContractStatus.enumOf(status) == ContractStatus.FINISHED){
			sqlExSb.append(" AND mine.LIFECYCLE <> "+ContractLifeCycle.TIMEOUT_FINISHED.getVal()+"  ");
			sqlExSb.append(" AND mine.LIFECYCLE <> "+ContractLifeCycle.MANUAL_RESTORE.getVal()+"  ");
		}
		log.info("the sql str is : " + sqlExSb);
		String sql = sqlEx.replaceAll("#PARAMETER", sqlExSb.toString());
		log.info("the sql str before page is : " + sql);
		return paginationQueryContractInfoBean(sql,qContext);
	}
	
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractListOfMineForPaginationToWebCms(com.appabc.common.base.QueryContext)  
	 */
	@Override
	@Deprecated
	public QueryContext<ContractInfoBean> queryContractListOfMineForPaginationToWebCms(QueryContext<ContractInfoBean> qContext) {
		if(qContext == null){
			return null;
		}
		String cid = (String)qContext.getParameter("cid");
		String sqlEx = buildBaseExContractInfoMineBeanSql(cid);
		StringBuilder sqlExSb = new StringBuilder();
		sqlExSb.append(" WHERE 1 = 1 ");
		String type = (String)qContext.getParameter("type");
		ContractWebCmsTradeType cwctt = ContractWebCmsTradeType.enumOf(type);
		if(cwctt == ContractWebCmsTradeType.HISTORY){
			sqlExSb.append(" AND (mine.STATUS " + SQLExpressionEnum.EQ.getText() +" "+ ContractStatus.DRAFT.getVal());
			sqlExSb.append(" AND (mine.LIFECYCLE " + SQLExpressionEnum.EQ.getText() +" "+ContractLifeCycle.TIMEOUT_FINISHED.getVal());
			sqlExSb.append(" OR mine.LIFECYCLE " + SQLExpressionEnum.EQ.getText() +" "+ContractLifeCycle.MANUAL_RESTORE.getVal()+" )) ");
			sqlExSb.append(" OR mine.STATUS " + SQLExpressionEnum.EQ.getText() +" "+ContractStatus.FINISHED.getVal());
			sqlExSb.append(" OR mine.STATUS " + SQLExpressionEnum.EQ.getText() +" "+ContractStatus.DELETION.getVal());
		}else if(cwctt == ContractWebCmsTradeType.PRESENT){
			sqlExSb.append(" AND (mine.STATUS " + SQLExpressionEnum.EQ.getText() +" "+ContractStatus.DRAFT.getVal());
			sqlExSb.append(" AND mine.LIFECYCLE " + SQLExpressionEnum.NOTEQ.getText() +" "+ContractLifeCycle.TIMEOUT_FINISHED.getVal());
			sqlExSb.append(" AND mine.LIFECYCLE " + SQLExpressionEnum.NOTEQ.getText() +" "+ContractLifeCycle.MANUAL_RESTORE.getVal()+" ) ");
			sqlExSb.append(" OR mine.STATUS " + SQLExpressionEnum.EQ.getText() +" "+ContractStatus.DOING.getVal()+"  ");
		}
		log.info("the sql str is : " + sqlExSb);
		String sql = sqlEx.replaceAll("#PARAMETER", sqlExSb.toString());
		log.info("the sql str before page is : " + sql);
		return paginationQueryContractInfoBean(sql,qContext);
	}
	
	@Override
	public QueryContext<TOrderInfo> queryContractListForPaginationOfUserToWebCms(QueryContext<TOrderInfo> qContext) {
		if(qContext == null){
			return null;
		}
		String cid = (String)qContext.getParameter("cid");
		StringBuilder sql = new StringBuilder(SELECT_SQL);
		sql.append(" WHERE 1=1");
		String type = (String)qContext.getParameter("type");
		ContractWebCmsTradeType cwctt = ContractWebCmsTradeType.enumOf(type);
		if(cwctt == ContractWebCmsTradeType.HISTORY){
			sql.append(" AND (STATUS =")
				.append(ContractStatus.FINISHED.getVal())
				.append(" OR STATUS =")
				.append(ContractStatus.DELETION.getVal())
				.append(")");
		}else if(cwctt == ContractWebCmsTradeType.PRESENT){
			sql.append(" AND (STATUS =")
			.append(ContractStatus.DRAFT.getVal())
			.append(" OR STATUS =")
			.append(ContractStatus.DOING.getVal())
			.append(" OR STATUS =")
			.append(ContractStatus.PAUSE.getVal())
			.append(")");
		}
		
		sql.append(" AND (BUYERID='").append(cid).append("' OR SELLERID='").append(cid).append("')");
		sql.append(" ORDER BY UPDATETIME DESC");
		log.info("the sql str before page is : " + sql);
		return super.queryListForPagination(sql.toString(), qContext);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryContractInfoWithId(java.lang.String)
	 */
	@Override
	public ContractInfoBean queryContractInfoWithId(String cid,String contractId) {
		String sqlEx = buildBaseExContractInfoMineBeanSql(cid);
		StringBuilder sqlExSb = new StringBuilder();
		sqlExSb.append(" WHERE 1 = 1  ");
		sqlExSb.append(" AND (D.SELLERID = '"+cid+"' or D.BUYERID = '"+cid+"' ) ");
		sqlExSb.append(" AND D.OID = '"+contractId+"' ");
		String sql = sqlEx.replaceAll("#PARAMETER", sqlExSb.toString());
		log.info("the queryContractInfoWithId sql str is  : " + sql);
		List<ContractInfoBean> result = getJdbcTemplate().query(sql, rowMapper);
		return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
		//return getJdbcTemplate().queryForObject(sql, rowMapper);
	}

	/* (non-Javadoc)获得企业合同数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#countByCid(java.lang.String, java.lang.String)
	 */
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
			return getJdbcTemplate().queryForObject(sql.toString(), args.toArray(), Integer.class);
		}
		
		return 0;
	}

	/* (non-Javadoc)获取买家和卖家共同交易比数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#countByBuyerAndSeller(java.lang.String, java.lang.String)
	 */
	@Override
	public int countByBuyerAndSeller(String buyerid, String sellerid) {
		StringBuffer sql = new StringBuffer(COUNT_SQL);

		List<Object> args = new ArrayList<Object>();

		sql.append(" AND OTYPE=? AND BUYERID=? AND SELLERID=? ");
		args.add(ContractType.SIGNED.getVal()); // 已签约的
		args.add(buyerid);
		args.add(sellerid);

		return getJdbcTemplate().queryForObject(sql.toString(), args.toArray(), Integer.class);
	}

	/* (non-Javadoc)获取询单被撮合的次数
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#getMatchingNumByFid(java.lang.String)
	 */
	@Override
	public int getMatchingNumByFid(String fid) {
		
		if(StringUtils.isNotEmpty(fid)){
			StringBuilder sql = new StringBuilder(COUNT_SQL);
			List<Object> args = new ArrayList<Object>();
			
			sql.append(" AND FID=?");
			args.add(fid);
			
			return getJdbcTemplate().queryForObject(sql.toString(), args.toArray(), Integer.class);
		}
		return 0;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractArbitrationDAO#queryContractWithAID(java.lang.String)  
	 */
	@Override
	public TOrderInfo queryContractWithAID(String aid) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TOI.OID AS OID, TOI.FID AS FID,TOI.SELLERID AS SELLERID,TOI.BUYERID AS BUYERID,TOI.PRICE AS PRICE,TOI.TOTALNUM AS TOTALNUM,TOI.CREATIME AS CREATIME, ");
		sql.append(" TOI.CREATER AS CREATER,TOI.LIMITTIME AS LIMITTIME,TOI.TOTALAMOUNT AS TOTALAMOUNT,TOI.AMOUNT AS AMOUNT,TOI.SETTLEAMOUNT AS SETTLEAMOUNT,TOI.STATUS AS STATUS,TOI.OTYPE AS OTYPE,TOI.REMARK AS REMARK, ");
		sql.append(" TOI.UPDATER AS UPDATER,TOI.UPDATETIME AS UPDATETIME,TOI.LIFECYCLE AS LIFECYCLE FROM T_ORDER_INFO TOI ");
		sql.append(" LEFT JOIN T_ORDER_OPERATIONS TOO ON TOO.OID = TOI.OID LEFT JOIN T_ORDER_ARBITRATION TOA ON TOA.LID = TOO.LID ");
		sql.append(" WHERE TOA.AID = ? ");
		List<TOrderInfo> result = this.getJdbcTemplate().query(sql.toString(), this, Collections.singletonList(aid).toArray());
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryCount(com.appabc.bean.pvo.TOrderInfo)
	 */
	@Override
	public int queryCount(TOrderInfo entity) {
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
		Number number = super.getNamedParameterJdbcTemplate().queryForObject(dynamicJoinSqlWithEntity(entity, new StringBuilder(COUNT_SQL)).toString(), paramSource, Integer.class);  
	    return (number != null ? number.intValue() : 0);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.contract.IContractInfoDAO#queryCountOfFinished()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int queryCountOfFinished() {
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		sql.append(" AND STATUS = ").append(ContractStatus.FINISHED.getVal());
		sql.append(" AND LIFECYCLE IN (")
			.append(ContractLifeCycle.FINALESTIMATE_FINISHED.getVal()).append(",")
			.append(ContractLifeCycle.NORMAL_FINISHED.getVal()).append(",")
			.append(ContractLifeCycle.SINGLECANCEL_FINISHED.getVal()).append(",")
			.append(ContractLifeCycle.ARBITRATED.getVal()).append(",")
			.append(ContractLifeCycle.BUYER_UNPAY_FINISHED.getVal())
			.append(")");
		return super.getJdbcTemplate().queryForInt(sql.toString());
	}

}
