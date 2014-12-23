/**
 *
 */
package com.appabc.datas.dao.order.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.OrderFindInfo.MatchingTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.enums.SqlInfoEnums;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 询单（供求信息）DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 下午3:08:20
 */
@Repository
public class OrderFindDaoImpl extends BaseJdbcDao<TOrderFind> implements IOrderFindDao {
	
	private Logger logger  = Logger.getLogger(this.getClass());

	@Autowired
	private PrimaryKeyGenerator pkg;

	private static final String BASE_SQL = " SELECT * FROM T_ORDER_FIND WHERE 1=1 ";
	private static final String INSERTSQL = " insert into T_ORDER_FIND (FID, CID, TITlE, TYPE, ADDRESSTYPE, PRICE, TOTALNUM, NUM, STARTTIME,  ENDTIME, MOREAREA, AREA, CREATER, CREATIME, LIMITIME, STATUS, REMARK, PARENTID,UPDATER,UPDATETIME,OVERALLSTATUS ) values (:id, :cid, :title, :type, :addresstype, :price, :totalnum, :num, :starttime, :endtime, :morearea, :area, :creater, :creatime, :limitime, :status, :remark, :parentid,:updater,:updatetime,:overallstatus ) ";
	private static final String UPDATESQL = " update T_ORDER_FIND set CID = :cid, TITlE = :title, TYPE = :type, ADDRESSTYPE = :addresstype, PRICE = :price, TOTALNUM = :totalnum, NUM = :num, STARTTIME = :starttime, ENDTIME = :endtime, MOREAREA = :morearea, AREA = :area, CREATER = :creater, CREATIME = :creatime, LIMITIME = :limitime, STATUS = :status, REMARK = :remark, PARENTID = :parentid, UPDATER=:updater, UPDATETIME=:updatetime,OVERALLSTATUS=:overallstatus where FID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_FIND WHERE FID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_FIND WHERE FID = :id ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_ORDER_FIND WHERE 1=1 ";
	// 找买找卖查询SQL
	private static final String SELECT_ORDER_LIST_SQL = "SELECT of.FID,of.CID,of.TITlE,of.TYPE,of.PRICE,of.TOTALNUM,of.CREATIME,of.STARTTIME,of.LIMITIME,of.AREA,opi.PNAME,opi.PTYPE,opi.UNIT,of.STATUS,opi.PCODE,of.MOREAREA from T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi ";
	// 询单详情SQL
	private static final String SELECT_ORDER_INFO_SQL = " SELECT of.FID, of.CID, of.TITlE, of.TYPE, of.PRICE, of.TOTALNUM, of.NUM, of.STARTTIME, of.ENDTIME, of.MOREAREA, of.LIMITIME, of.AREA, of.REMARK,of.ADDRESSTYPE,of.CREATIME,of.UPDATETIME, of.STATUS, opi.ID AS OPIID, opi.PID, opi.PNAME, opi.PTYPE, opi.PSIZE, opi.PCOLOR, opi.PADDRESS, opi.UNIT, opi.REMARK AS PREMARK,opi.PCODE FROM T_ORDER_FIND of, T_ORDER_PRODUCT_INFO opi WHERE of.FID = opi.FID ";
	//询单自动匹配（有交易意向+货物匹配）
	private static final String SELECT_MATCHING_SQL = "select t.*,ci.CNAME,ci.CTYPE,u.USERNAME,u.PHONE from ( "
													+" SELECT ofi.ID as ITEMID,ofi.FID,ofi.UPDATER as CID,'' as TITLE,'' as OFTYPE,0 as MATCHINGTYPE FROM T_ORDER_FIND_ITEM ofi where ofi.FID=:fid AND ofi.STATUS=0 "
													+" UNION ALL "
													+" SELECT '' as ITEMID,of.FID,of.CID,of.TITlE,of.TYPE as OFTYPE,1 as MATCHINGTYPE FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi,( "
													+" 	SELECT of.CID,of.TYPE,opi.PID FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi WHERE of.FID=:fid AND of.FID=opi.FID "
													+" 	) as a WHERE of.FID=opi.FID and of.TYPE<>a.TYPE and opi.PID=a.PID AND of.OVERALLSTATUS=0 "
													+" ) as t "
													+" LEFT JOIN T_COMPANY_INFO ci  "
													+" ON ci.ID = t.CID "
													+" LEFT JOIN T_USER u "
													+" ON u.CID = ci.ID";
	public void save(TOrderFind entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("ORDERFID"));
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderFind entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TOrderFind entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderFind entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderFind query(TOrderFind entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TOrderFind query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderFind> queryForList(TOrderFind entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	private String dynamicJoinSqlWithEntity(TOrderFind bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "FID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "title", "TITLE", bean.getTitle());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", bean.getType());
		this.addNameParamerSqlWithProperty(sql, "addresstype", "ADDRESSTYPE", bean.getAddresstype());
		this.addNameParamerSqlWithProperty(sql, "price", "PRICE", bean.getPrice());
		this.addNameParamerSqlWithProperty(sql, "totalnum", "TOTALNUM", bean.getTotalnum());
		this.addNameParamerSqlWithProperty(sql, "num", "NUM", bean.getNum());
		this.addNameParamerSqlWithProperty(sql, "morearea", "MOREAREA", bean.getMorearea());
		this.addNameParamerSqlWithProperty(sql, "area", "AREA", bean.getArea());
		this.addNameParamerSqlWithProperty(sql, "creater", "CREATER", bean.getCreater());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "parentid", "PARENTID", bean.getParentid());
		this.addNameParamerSqlWithProperty(sql, "overallstatus", "OVERALLSTATUS", bean.getOverallstatus());

		return sql.toString();
	}

	public List<TOrderFind> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)找买找卖&我的供求分页查询
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderFind> queryListForPagination(
			QueryContext<TOrderFind> qContext) {

		StringBuilder sql = new StringBuilder(SELECT_ORDER_LIST_SQL);
		/*****过滤条件******************************/
		sql.append("where 1=1 ");

		addNameParamerSqlWithProperty(sql, "pid",  "opi.PID", qContext.getParameter("pid")); // 商品ID,查询某种商品
		addNameParamerSqlWithProperty(sql, "ptype",  "opi.PTYPE", qContext.getParameter("ptype")); // 商品种类
		addNameParamerSqlWithProperty(sql, "area",  "of.AREA", qContext.getParameter("area")); //靖江段code等……
		addNameParamerSqlWithProperty(sql, "type",  "of.TYPE", qContext.getParameter("type")); // 类型: 买或卖（0或1）
		addNameParamerSqlWithProperty(sql, "pcode",  "opi.PCODE", qContext.getParameter("pcode")); // 类型: 黄砂、石子编码
		addNameParamerSqlWithProperty(sql, "cid",  "of.CID", qContext.getParameter("cid")); // 企业ID，【我的供求列表】必带条件

//		if(qContext.getParameter("queryDate") != null && !qContext.getParameter("queryDate").toString().trim().equals("")){ // 有效时间查询
//			addNameParamerSqlWithProperty(sql, "queryDate",  "of.STARTTIME", qContext.getParameter("queryDate").toString(), SQLExpressionEnum.LT.getText());
//			addNameParamerSqlWithProperty(sql, "queryDate",  "of.LIMITIME", qContext.getParameter("queryDate").toString(), SQLExpressionEnum.GT.getText());
//		}
		if(qContext.getParameter("queryDate") != null && !qContext.getParameter("queryDate").toString().trim().equals("")){ // 发布时间
			addNameParamerSqlWithProperty(sql, "queryDate",  "of.CREATIME", qContext.getParameter("queryDate").toString(), SQLExpressionEnum.GT.getText());
		}
		
		addNameParamerSqlWithProperty(sql, "overallstatus",  "of.OVERALLSTATUS", qContext.getParameter("overallstatus")); // 状态,(有效，失效)，找买找卖查询时必须为有效
		
		String sqlOrder = " ORDER BY ";
		if(qContext.getParameter("queryMethod") != null && qContext.getParameter("queryMethod").toString().trim().equals("getOrderList")){ // 查询方法判断
			sql.append(" AND (of.FID = opi.FID OR of.PARENTID=opi.FID) AND MOREAREA="+OrderMoreAreaEnum.ORDER_MORE_AREA_NO.getVal());// 找买找卖，过滤父询单
		}else{
			sql.append(" AND of.FID = opi.FID "); // 我的供求，过滤子询单(子询单的商品依赖父询单，会自动过滤)
			sqlOrder += " of.STATUS ASC, ";
		}
		
		/****排序*************************************/
		sql.append(sqlOrder);
		if(qContext.getParameter("orderPrice") != null  && !qContext.getParameter("orderPrice").toString().trim().equals("")){ // 价格排序
			sql.append(" of.PRICE ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderPrice").toString())));
		}else if(qContext.getParameter("orderEffTime") != null  && !qContext.getParameter("orderEffTime").toString().trim().equals("")){ // 发布时间
			sql.append(" of.STARTTIME ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderEffTime").toString())));
		}else if(qContext.getParameter("orderCredit") != null  && !qContext.getParameter("orderCredit").toString().trim().equals("")){ // 诚信度
			sql.insert(0, "SELECT ta.* FROM (");
			sql.append(") ta LEFT JOIN T_COMPANY_EVALUATION tce ON tce.CID= ta.CID ");

			sql.append(" tce.CREDIT ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderCredit").toString())));
		}else{ // 默认按开始时间倒序
			sql.append(" of.CREATIME DESC ");
		}
		return queryListForPagination(sql.toString(), qContext, new RowMapper<TOrderFind>() {
					public TOrderFind mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						TOrderFind t = new TOrderFind();

						t.setId(rs.getString("FID"));
						t.setCid(rs.getString("CID"));
						t.setTitle(rs.getString("TITlE"));
						t.setType(OrderTypeEnum.enumOf(rs.getInt("TYPE")));
						t.setPrice(rs.getFloat("PRICE"));
						t.setTotalnum(rs.getFloat("TOTALNUM"));
						t.setStarttime(rs.getTimestamp("STARTTIME"));
						t.setLimitime(rs.getTimestamp("LIMITIME"));
						t.setArea(rs.getString("AREA"));
						t.setPname(rs.getString("PNAME"));
						t.setPtype(rs.getString("PTYPE"));
						t.setUnit(rs.getString("UNIT"));
						t.setCreatime(rs.getTimestamp("CREATIME"));
						t.setStatus(OrderStatusEnum.enumOf(rs.getInt("STATUS")));
						t.setPcode(rs.getString("PCODE"));
						t.setMorearea(OrderMoreAreaEnum.enumOf(rs.getString("MOREAREA")));

						return t;
					}
				});


	}

	public TOrderFind mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderFind t = new TOrderFind();

		t.setId(rs.getString("FID"));
		t.setAddresstype(OrderAddressTypeEnum.enumOf(rs.getInt("ADDRESSTYPE")));
		t.setArea(rs.getString("AREA"));
		t.setCid(rs.getString("CID"));
		t.setCreater(rs.getString("CREATER"));
		t.setCreatime(rs.getTimestamp("CREATIME"));
		t.setEndtime(rs.getTimestamp("ENDTIME"));
		t.setLimitime(rs.getTimestamp("LIMITIME"));
		t.setMorearea(OrderMoreAreaEnum.enumOf(rs.getString("MOREAREA")));
		t.setNum(rs.getFloat("NUM"));
		t.setParentid(rs.getString("PARENTID"));
		t.setPrice(rs.getFloat("PRICE"));
		t.setRemark(rs.getString("REMARK"));
		t.setStarttime(rs.getTimestamp("STARTTIME"));
		t.setStatus(OrderStatusEnum.enumOf(rs.getInt("STATUS")));
		t.setTitle(rs.getString("TITLE"));
		t.setTotalnum(rs.getFloat("TOTALNUM"));
		t.setType(OrderTypeEnum.enumOf(rs.getInt("TYPE")));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setOverallstatus(OrderOverallStatusEnum.enumOf(rs.getInt("OVERALLSTATUS")));

		return t;
	}

	/**
	 * 根据询单详情查询
	 * @param fid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid) {

		StringBuilder sql = new StringBuilder(SELECT_ORDER_INFO_SQL);

		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "of.FID", fid, args);

		List<OrderAllInfor> oaiList = this.getJdbcTemplate().query(sql.toString(), args.toArray(), new RowMapper<OrderAllInfor>() {
			public OrderAllInfor mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OrderAllInfor t = new OrderAllInfor();

				t.setId(rs.getString("FID"));
				t.setCid(rs.getString("CID"));
				t.setTitle(rs.getString("TITlE"));
				t.setType(rs.getInt("TYPE"));
				t.setPrice(rs.getFloat("PRICE"));
				t.setTotalnum(rs.getFloat("TOTALNUM"));
				t.setNum(rs.getFloat("NUM"));
				t.setStarttime(rs.getTimestamp("STARTTIME"));
				t.setEndtime(rs.getTimestamp("ENDTIME"));
				t.setLimitime(rs.getTimestamp("LIMITIME"));
				t.setMorearea(rs.getString("MOREAREA"));
				t.setArea(rs.getString("AREA"));
				t.setRemark(rs.getString("REMARK"));
				t.setStatus(rs.getInt("STATUS"));
				t.setAddresstype(OrderAddressTypeEnum.enumOf(rs.getInt("ADDRESSTYPE")));
				t.setCreatime(rs.getTimestamp("CREATIME"));
				t.setUpdatetime(rs.getTimestamp("UPDATETIME"));

				t.setOpiid(rs.getInt("OPIID"));
				t.setPid(rs.getString("PID"));
				t.setPname(rs.getString("PNAME"));
				t.setPtype(rs.getString("PTYPE"));
				t.setPsizeid(rs.getString("PSIZE"));
				t.setPcolor(rs.getString("PCOLOR"));
				t.setPaddress(rs.getString("PADDRESS"));
				t.setUnit(UnitEnum.enumOf(rs.getString("UNIT")));
				t.setPremark(rs.getString("PREMARK"));
				t.setPcode(rs.getString("PCODE"));

				return t;
			}
		});

		if(oaiList != null && oaiList.size()>0){
			return oaiList.get(0);
		}

		return null;
	}
	
	/* (non-Javadoc)询单自动匹配列表
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryMatchingObjectByCidForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderFind> queryMatchingObjectByCidForPagination(QueryContext<TOrderFind> qContext) {
		if(qContext.getParameter("fid")==null){
			logger.info("query Failure，fid is null.");
			return qContext;
		}
		StringBuilder sql = new StringBuilder(SELECT_MATCHING_SQL);
		
		return queryListForPagination(sql.toString(), qContext, new RowMapper<TOrderFind>() {
			public TOrderFind mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				TOrderFind of = new TOrderFind();
				
				MatchingBean t = new MatchingBean();
				
				t.setFid(rs.getString("FID"));
				t.setCid(rs.getString("CID"));
				t.setTitle(rs.getString("TITlE"));
				t.setCname(rs.getString("CNAME"));
				t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
				t.setItemid(rs.getString("ITEMID"));
				t.setOftype(OrderTypeEnum.enumOf(rs.getShort("OFTYPE")));
				t.setPhone(rs.getString("PHONE"));
				t.setUsername(rs.getString("USERNAME"));
				t.setMatchingtype(MatchingTypeEnum.enumOf(rs.getInt("MATCHINGTYPE")));
				of.setMatchingBean(t);
				return of;
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindDao#countByCid(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int countByCid(String cid) {
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		List<Object> args = new ArrayList<Object>();

		sql.append(" AND CID=?");
		args.add(cid);

		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.order.IOrderFindDao#updateChildOrderFindCloseInvalidByParentId(java.lang.String, java.lang.String)  
	 */
	@Override
	public boolean updateChildOrderFindCloseInvalidByParentId(String parentId,
			String operator) {
		StringBuilder sql = new StringBuilder(" update T_ORDER_FIND set STATUS = ? , UPDATER = ? , UPDATETIME = ? , OVERALLSTATUS = ?  where PARENTID = ?   ");
		List<Object> args = new ArrayList<Object>();
		args.add(OrderStatusEnum.ORDER_STATUS_CLOSE);
		args.add(operator);
		args.add(new Date());
		args.add(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		args.add(parentId);
		int result = getJdbcTemplate().update(sql.toString(), args);
		return result != 0;
	}
}
