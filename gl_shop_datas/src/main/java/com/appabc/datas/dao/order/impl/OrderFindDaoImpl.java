/**
 *
 */
package com.appabc.datas.dao.order.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindInsteadBean;
import com.appabc.bean.bo.OrderFindQueryParamsBean;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.OrderFindInfo.MatchingTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.enums.SqlInfoEnums;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.spring.BeanLocator;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.tools.utils.AreaManager;
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
	private static final String INSERTSQL = " insert into T_ORDER_FIND (FID, CID, TITlE, TYPE, ADDRESSTYPE, PRICE, TOTALNUM, NUM, STARTTIME,  ENDTIME, MOREAREA, AREA, CREATER, CREATIME, LIMITIME, STATUS, REMARK, PARENTID,UPDATER,UPDATETIME,OVERALLSTATUS,CONTRACTID,MATCHINGNUM,OVERALLTYPE ) values (:id, :cid, :title, :type, :addresstype, :price, :totalnum, :num, :starttime, :endtime, :morearea, :area, :creater, :creatime, :limitime, :status, :remark, :parentid,:updater,:updatetime,:overallstatus,:contractid,:matchingnum,:overalltype ) ";
	private static final String UPDATESQL = " update T_ORDER_FIND set CID = :cid, TITlE = :title, TYPE = :type, ADDRESSTYPE = :addresstype, PRICE = :price, TOTALNUM = :totalnum, NUM = :num, STARTTIME = :starttime, ENDTIME = :endtime, MOREAREA = :morearea, AREA = :area, CREATER = :creater, CREATIME = :creatime, LIMITIME = :limitime, STATUS = :status, REMARK = :remark, PARENTID = :parentid, UPDATER=:updater, UPDATETIME=:updatetime,OVERALLSTATUS=:overallstatus,CONTRACTID=:contractid,MATCHINGNUM=:matchingnum,OVERALLTYPE=:overalltype where FID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_FIND WHERE FID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_FIND WHERE FID = :id ";
	private static final String COUNT_SQL = "SELECT COUNT(0) cou FROM T_ORDER_FIND WHERE 1=1";
	// 找买找卖查询SQL
	private static final String SELECT_ORDER_LIST_SQL = " SELECT of.FID,of.CID,of.TITlE,of.TYPE,of.PRICE,of.TOTALNUM,of.CREATIME,of.UPDATETIME,of.STARTTIME,of.ENDTIME,of.LIMITIME,of.AREA,of.STATUS,of.OVERALLSTATUS,of.MOREAREA,of.CONTRACTID,of.MATCHINGNUM,of.CREATER,opi.PID,opi.PNAME,opi.PTYPE,opi.UNIT,opi.PCODE from T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi ";
	// 询单详情SQL
	private static final String SELECT_ORDER_INFO_SQL = " SELECT of.FID, of.CID, of.TITlE, of.TYPE, of.PRICE, of.TOTALNUM, of.NUM, of.STARTTIME, of.ENDTIME, of.MOREAREA, of.LIMITIME, of.AREA, of.REMARK,of.ADDRESSTYPE,of.CREATIME,of.UPDATETIME, of.STATUS,of.CONTRACTID,of.MATCHINGNUM,of.OVERALLTYPE, opi.ID AS OPIID, opi.PID, opi.PNAME, opi.PTYPE, opi.PSIZE, opi.PCOLOR, opi.PADDRESS, opi.UNIT, opi.REMARK AS PREMARK,opi.PCODE FROM T_ORDER_FIND of, T_ORDER_PRODUCT_INFO opi WHERE of.FID = opi.FID ";
	// 询单自动匹配（有交易意向）
//	private static final String SELECT_MATCHING_APPLY_SQL = "select t.*,ci.CNAME,ci.CTYPE,u.USERNAME,u.PHONE from ( "
//													+" SELECT ofi.ID as ITEMID,ofi.FID,ofi.UPDATER as CID,'' as TITLE,'' as OFTYPE,0 as MATCHINGTYPE FROM T_ORDER_FIND_ITEM ofi where ofi.FID=:fid AND ofi.STATUS=0 "
//													+" UNION ALL "
//													+" SELECT '' as ITEMID,of.FID,of.CID,of.TITlE,of.TYPE as OFTYPE,1 as MATCHINGTYPE FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi,( "
//													+" 	SELECT of.CID,of.TYPE,opi.PID FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi WHERE of.FID=:fid AND of.FID=opi.FID "
//													+" 	) as a WHERE of.FID=opi.FID and of.TYPE<>a.TYPE and opi.PID=a.PID AND of.OVERALLSTATUS=0 "
//													+" ) as t "
//													+" LEFT JOIN T_COMPANY_INFO ci  "
//													+" ON ci.ID = t.CID "
//													+" LEFT JOIN T_USER u "
//													+" ON u.CID = ci.ID";
	// 有意向匹配信息
	private static final String SQL_MATCHING_APPLY = "SELECT ofi.ID as ITEMID,ofi.FID,ofi.UPDATER as CID,'' as TITLE,'' as OFTYPE,0 as MATCHINGTYPE,null as PUSHCREATIME,ofi.CREATETIME as ITEMCREATETIME FROM T_ORDER_FIND_ITEM ofi where ofi.FID=:fid AND ofi.STATUS=0";
	// 询单对应匹配信息
	private static final String SQL_MATCHING_ORDER_FIND = "SELECT '' as ITEMID,of.FID,of.CID,of.TITlE,of.TYPE as OFTYPE,1 as MATCHINGTYPE,of.CREATIME as PUSHCREATIME,null as ITEMCREATETIME FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi,( SELECT of.CID,of.TYPE,opi.PID FROM T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi WHERE 1=1 AND of.FID=:fid AND of.FID=opi.FID ) as a WHERE 1=1 AND a.CID <> of.CID AND of.FID=opi.FID and of.TYPE<>a.TYPE and opi.PID=a.PID AND of.OVERALLSTATUS=0 ";
	// 询单发布者对应身份匹配信息
	private static final String SQL_MATCHING_IDENTITY = "SELECT '' AS ITEMID,'' AS FID,ci.ID AS CID,'' AS TITlE,'' OFTYPE,2 AS MATCHINGTYPE,a.CREATIME as PUSHCREATIME,null as ITEMCREATETIME FROM T_COMPANY_INFO ci, ( SELECT ci.CTYPE,of.CREATIME FROM T_ORDER_FIND of, T_COMPANY_INFO ci WHERE of.FID = :fid AND of.CID = ci.ID ) AS a WHERE ci.CTYPE <> a.CTYPE AND ci.AUTHSTATUS = 1";
	
	// 新发布的询单，需要放任务后台任务列表中的记录
	private static final String QUERY_NEW_TASK_LIST = "SELECT tof.* FROM T_ORDER_FIND tof WHERE NOT EXISTS ( SELECT st.OBJECT_ID FROM SYS_TASKS st WHERE tof.FID = st.OBJECT_ID AND st.FINISHED=? AND st.TYPE = ? ) AND tof.PARENTID IS NULL AND tof.`STATUS`=? AND tof.OVERALLSTATUS = ?";
	// 新失效的询单，需要删除的后台任务列表
	private static final String QUERY_INVALID_TASK_LIST = "SELECT tof.* FROM T_ORDER_FIND tof WHERE EXISTS ( SELECT st.OBJECT_ID FROM SYS_TASKS st WHERE tof.FID = st.OBJECT_ID AND st.FINISHED=0 AND st.TYPE = ? ) AND tof.PARENTID IS NULL AND tof.OVERALLSTATUS = ?";
	// 查询父询单
	private static final String QUERY_THE_PARENT_ORDER_FIND = " SELECT * FROM T_ORDER_FIND WHERE STATUS = ? and OVERALLSTATUS = ? and PARENTID IS NULL ";
	// 代发询单列表
	private static final String SELECT_ORDERFIND_OF_INSTEAD = "SELECT st.ID,st.OBJECT_ID FID,st.`OWNER`,st.CID,st.CREATE_TIME,su.REALNAME,of.TITlE,ci.CNAME,ci.CTYPE,tu.USERNAME FROM (SELECT * FROM SYS_TASKS WHERE 2=2 AND TYPE=23 and FINISHED=1 ) st"
			+" LEFT JOIN SYS_USERS su ON su.id=st.`OWNER` "
			+" LEFT JOIN T_ORDER_FIND of ON of.FID=st.OBJECT_ID "
			+" LEFT JOIN T_COMPANY_INFO ci ON ci.ID=st.CID "
			+" LEFT JOIN T_USER tu ON tu.CID=st.CID "
			+" ORDER BY st.CREATE_TIME DESC";
	
	public void save(TOrderFind entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("ORDERFID"));
		entity.setCreatime(Calendar.getInstance().getTime());
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderFind entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("ORDERFID"));
		entity.setCreatime(Calendar.getInstance().getTime());
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TOrderFind entity) {
		entity.setUpdatetime(Calendar.getInstance().getTime());
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
		this.addNameParamerSqlWithProperty(sql, "contractid", "CONTRACTID", bean.getContractid());
		this.addNameParamerSqlWithProperty(sql, "matchingnum", "MATCHINGNUM", bean.getMatchingnum());
		this.addNameParamerSqlWithProperty(sql, "overalltype", "OVERALLTYPE", bean.getOveralltype());
		

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
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
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
		t.setContractid(rs.getString("CONTRACTID"));
		t.setMatchingnum(rs.getInt("MATCHINGNUM"));
		t.setOveralltype(OrderOverallTypeEnum.enumOf(rs.getInt("OVERALLTYPE")));

		return t;
	}

	/**
	 * 
	 * @param fid
	 * @return
	 */
	/* (non-Javadoc)根据询单ID详情查询
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryInfoById(java.lang.String)
	 */
	public OrderAllInfor queryInfoById(String fid) {

		StringBuilder sql = new StringBuilder(SELECT_ORDER_INFO_SQL);

		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "of.FID", fid, args);

		List<OrderAllInfor> oaiList = this.getJdbcTemplate().query(sql.toString(), args.toArray(), new RowMapper<OrderAllInfor>() {
			public OrderAllInfor mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				
				AreaManager areaManager = (AreaManager) BeanLocator.getBean("AreaManager");
				
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
				t.setAreaFullName(areaManager.getFullAreaName(rs.getString("AREA")));
				t.setContractid(rs.getString("CONTRACTID"));
				t.setMatchingnum(rs.getInt("MATCHINGNUM"));

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
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryMatchingObjectByCidForPagination(com.appabc.common.base.QueryContext, boolean, boolean, boolean)
	 */
	public QueryContext<TOrderFind> queryMatchingObjectByCidForPagination(QueryContext<TOrderFind> qContext, boolean isApply, boolean isOrderFid, boolean isIdentity) {
		if(qContext.getParameter("fid")==null){
			logger.info("query Failure，fid is null.");
			return qContext;
		}
		if(isApply==false && isOrderFid==false && isIdentity==false ){
			logger.info("无撮合类型，不能时行匹配！");
			return qContext;
		}
		
		String sqlHead = "select t.*,ci.CNAME,ci.CTYPE,u.USERNAME,u.PHONE from ( ";
		String sqlTail = " ) as t LEFT JOIN T_COMPANY_INFO ci ON ci.ID = t.CID LEFT JOIN T_USER u ON u.CID = ci.ID and u.STATUS=0";
		
		StringBuilder sql = new StringBuilder(sqlHead);
		
		if(isApply){
			sql.append(SQL_MATCHING_APPLY);
			// 排除已处理数据(已保存列表和取消列表)
			sql.append(" AND ofi.UPDATER NOT IN(SELECT ofm.TARGET from T_ORDER_FIND_MATCH ofm WHERE ofm.OPFID=:fid)");
		}
		if(isOrderFid){
			if(isApply){
				sql.append(" UNION ALL ");
			}
			sql.append(SQL_MATCHING_ORDER_FIND);
			// 排除已处理数据(已保存列表和取消列表)
			sql.append(" AND of.CID NOT IN(SELECT ofm.TARGET from T_ORDER_FIND_MATCH ofm WHERE ofm.OPFID=:fid)");
		}
		if(isIdentity){
			if(isApply || isOrderFid){
				sql.append(" UNION ALL ");
			}
			sql.append(SQL_MATCHING_IDENTITY);
			// 排除已处理数据(已保存列表和取消列表)
			sql.append(" AND ci.ID NOT IN(SELECT ofm.TARGET from T_ORDER_FIND_MATCH ofm WHERE ofm.OPFID=:fid)");
		}
		sql.append(sqlTail);
//		System.out.println(sql);
		return queryListForPagination(sql.toString(), null, qContext, new RowMapper<TOrderFind>() {
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
				t.setfItemTime(rs.getTimestamp("ITEMCREATETIME"));
				t.setfPulishTime(rs.getTimestamp("PUSHCREATIME"));
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
	
	
	/* (non-Javadoc)供求列表
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryOrderListForPagination(com.appabc.common.base.QueryContext, com.appabc.bean.bo.OrderFindQueryParamsBean)
	 */
	@Override
	public QueryContext<TOrderFind> queryOrderListForPagination(
			QueryContext<TOrderFind> qContext, OrderFindQueryParamsBean ofqParam) {
			
		StringBuilder sql = new StringBuilder(SELECT_ORDER_LIST_SQL);
		sql.append(" WHERE 1=1 ");
		
		/*****区域筛选条件begin************************************/
		if((ofqParam.getAreaCodeArea() != null && ofqParam.getAreaCodeArea().length>0) || (ofqParam.getAreaCodeProvince() != null && ofqParam.getAreaCodeProvince().length>0)){
			sql.append("AND EXISTS( SELECT H.AREA FROM ( select A.AREA from T_ORDER_FIND A Left Join T_ORDER_ADDRESS B on A.FID = B.FID where 1=1 ");
			if(ofqParam.getAreaCodeArea() != null && ofqParam.getAreaCodeArea().length>0){ // 区，排除区
				logger.debug("区域筛选条件,区，排除区");
				String areaCodesql = spellSqlAreaCodeArea(ofqParam.getAreaCodeArea());
				if(StringUtils.isNotEmpty(areaCodesql)){
					sql.append(" AND NOT EXISTS( ").append(areaCodesql).append(" ) ");
				}
			}
			
			if(ofqParam.getAreaCodeProvince() != null && ofqParam.getAreaCodeProvince().length>0){ // 省，属于省
				logger.debug("区域筛选条件,省，属于省");
				String areaCodesql = spellSqlAreaCodeProvince(ofqParam.getAreaCodeProvince());
				if(StringUtils.isNotEmpty(areaCodesql)){
					sql.append(" AND EXISTS( ").append(areaCodesql).append(" ) ");
				}
			}
			
			sql.append(" ) H WHERE H.AREA=of.AREA ) ");
		}
		
		/*****区域筛选条件end**************************************/
		
		/*****商品筛选条件begin************************************/
		if(ofqParam.getPids() != null && ofqParam.getPids().length>0){
			String pidsParamSql = spellSqlPidsParams(ofqParam.getPids());
			logger.debug("商品筛选条件,pids="+pidsParamSql);
			if(StringUtils.isNotEmpty(pidsParamSql)) {
				sql.append(" AND opi.PID IN (").append(pidsParamSql).append(") ");
				qContext.addParameter("pid", pidsParamSql);
			}
		}
		/*****商品筛选条件end**************************************/
		
		/*****有效时间筛选条件being********************************/
		if(ofqParam.getStartTime() != null && ofqParam.getEndTime() != null){
			sql.append(" AND ( (of.STARTTIME>=:startTime AND of.STARTTIME<=:endTime) OR (of.ENDTIME>=:startTime AND of.ENDTIME<=:endTime) OR (of.STARTTIME<:startTime AND of.ENDTIME>:endTime) )");
			qContext.addParameter("startTime", ofqParam.getStartTime());
			qContext.addParameter("endTime", ofqParam.getEndTime());
//			公式 ：(a1>=b1 and a1<=b2) or (a2>=b1 and a2<=b2) or (a1<b1 and a2>b2)
		}
		/*****有效时间筛选条件end**********************************/
		
		/*****询单类型筛选条件begin********************************/
		addNameParamerSqlWithProperty(sql, "type",  "of.TYPE", ofqParam.getType()); // 类型: 买或卖（1或2）
		addNameParamerSqlWithProperty(sql, "overallstatus",  "of.OVERALLSTATUS", qContext.getParameter("overallstatus")); // 状态,(有效，失效)，找买找卖查询时必须为有效
		/*****询单类型筛选条件end**********************************/

		sql.append(" AND of.FID = opi.FID AND of.PARENTID IS NULL");
		
		if(qContext.getParameter("queryMethod")!=null && qContext.getParameter("queryMethod").toString().trim().equals("getMyOrderList")){ // 我的供求列表
			sql.append(" AND of.STATUS <> :status");
			qContext.addParameter("status", OrderStatusEnum.ORDER_STATUS_DELETE.getVal());
			addNameParamerSqlWithProperty(sql, "cid",  "of.CID", qContext.getParameter("cid")); // 企业ID，【我的供求列表】必带条件
		}
		
		/******LEFT JOIN诚信度查询******************************/
		sql.insert(0, "SELECT ta.*,tcr.CREDIT FROM (");
		sql.append(") ta LEFT JOIN T_COMPANY_RANKING tcr ON tcr.CID= ta.CID ");
		
		/*****排序***********************************************/
		StringBuilder sqlOrder = new StringBuilder(" ORDER BY ");
		if(qContext.getParameter("queryMethod") != null && qContext.getParameter("queryMethod").toString().trim().equals("getMyOrderList")){ // 查询方法判断
			sqlOrder.append(" ta.OVERALLSTATUS ASC, ");
		}
		
		if(qContext.getParameter("orderPrice") != null  && !qContext.getParameter("orderPrice").toString().trim().equals("")){ // 价格排序
			sqlOrder.append(" ta.PRICE ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderPrice").toString()))).append(",");
		}
//		if(qContext.getParameter("orderEffTime") != null  && !qContext.getParameter("orderEffTime").toString().trim().equals("")){ // 有效期
//			sqlOrder.append(" ta.ENDTIME ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderEffTime").toString()))).append(",");
//		}
		if(qContext.getParameter("orderCredit") != null  && !qContext.getParameter("orderCredit").toString().trim().equals("")){ // 诚信度
			sqlOrder.append(" tcr.CREDIT ").append(SqlInfoEnums.OrderBySort.getOrderText(Integer.parseInt(qContext.getParameter("orderCredit").toString()))).append(",");
		}
		sqlOrder.append(" ta.UPDATETIME DESC ");// 默认按修改时间倒序
		
		sql.append(sqlOrder);
		/****添加诚信度的查询*********************/

		return queryListForPagination(sql.toString(), null, qContext, new RowMapper<TOrderFind>() {
			public TOrderFind mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				
				AreaManager areaManager = (AreaManager) BeanLocator.getBean("AreaManager");
				
				TOrderFind t = new TOrderFind();

				t.setId(rs.getString("FID"));
				t.setCid(rs.getString("CID"));
				t.setTitle(rs.getString("TITlE"));
				t.setType(OrderTypeEnum.enumOf(rs.getInt("TYPE")));
				t.setPrice(rs.getFloat("PRICE"));
				t.setTotalnum(rs.getFloat("TOTALNUM"));
				t.setStarttime(rs.getTimestamp("STARTTIME"));
				t.setEndtime(rs.getTimestamp("ENDTIME"));
				t.setLimitime(rs.getTimestamp("LIMITIME"));
				t.setArea(rs.getString("AREA"));
				t.setPname(rs.getString("PNAME"));
				t.setPtype(rs.getString("PTYPE"));
				t.setUnit(UnitEnum.enumOf(rs.getString("UNIT")));
				t.setCreatime(rs.getTimestamp("CREATIME"));
				t.setStatus(OrderStatusEnum.enumOf(rs.getInt("STATUS")));
				t.setPid(rs.getString("PID"));
				t.setPcode(rs.getString("PCODE"));
				t.setMorearea(OrderMoreAreaEnum.enumOf(rs.getString("MOREAREA")));
				t.setAreaFullName(areaManager.getFullAreaName(rs.getString("AREA")));
				t.setContractid("CONTRACTID");
				t.setCredit(rs.getFloat("CREDIT"));
				t.setMatchingnum(rs.getInt("MATCHINGNUM"));
				t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
				t.setCreater(rs.getString("CREATER"));

				return t;
			}
		});
	}
	
	/**
	 * SQL拼组,区，排除区
	 * @param areaCodes
	 * @return
	 */
	private String spellSqlAreaCodeArea(String[] areaCodes){
		StringBuilder sql = new StringBuilder();
		if(areaCodes != null && areaCodes.length>0){
			sql.append(" select ACODE FROM ( ");
			for (int i = 0; i < areaCodes.length; i++) {
				if(i>0) sql.append(" union ");
				if(StringUtils.isNotEmpty(areaCodes[i])) sql.append("select ").append(areaCodes[i]).append(" As ACODE");
			}
			sql.append(" ) C where C.ACODE = A.AREA ");
		}
		
		return sql.toString();
	}
	
	/**
	 * SQL拼组,省，属于省
	 * @param AreaCodeArea
	 * @return
	 */
	private String spellSqlAreaCodeProvince(String[] areaCodes){
		StringBuilder sql = new StringBuilder();
		if(areaCodes != null && areaCodes.length>0){
			sql.append(" select E.VAL FROM ( select G.VAL from T_PUBLIC_CODES G where G.PCODE in ( select VAL from T_PUBLIC_CODES F where F.CODE = 'AREA' AND F.PCODE in (");
			for (int i = 0; i < areaCodes.length; i++) {
				if(i>0) sql.append(",");
				if(StringUtils.isNotEmpty(areaCodes[i])) sql.append("'").append(areaCodes[i]).append("'");
			}
			sql.append(") ) ) E where E.VAL = A.AREA ");
		}
		
		return sql.toString();
	}
	
	/**
	 * 商品ID参数拼写
	 * @param pids
	 * @return
	 */
	private String spellSqlPidsParams(String[] pids){
		StringBuilder sql = new StringBuilder();
		if(pids != null && pids.length>0){
			for (int i = 0; i < pids.length; i++) {
				if(i>0) sql.append(",");
				if(StringUtils.isNotEmpty(pids[i])) sql.append("'").append(pids[i]).append("'");
			}
		}
		
		return sql.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryNewListForTask()
	 */
	@Override
	public List<TOrderFind> queryNewListForTask() {
		StringBuilder sql = new StringBuilder(QUERY_NEW_TASK_LIST);
		
		List<Object> args = new ArrayList<Object>();
		args.add(0); // FINISHED =1 ：询单撮合成功为1；询单列表有效，任务列表有该询单ID，并FINISHED=1，说明该询单生产的合同签订失败回滚了；需要重新加入撮合列表.
		args.add(TaskType.MatchOrderRequest.getValue());
		args.add(OrderStatusEnum.ORDER_STATUS_YES.getVal());
		args.add(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.getVal());
		
		return super.queryForList(sql.toString(), args);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryInvalidListForTask()
	 */
	@Override
	public List<TOrderFind> queryInvalidListForTask() {
		StringBuilder sql = new StringBuilder(QUERY_INVALID_TASK_LIST);
		
		List<Object> args = new ArrayList<Object>();
		args.add(TaskType.MatchOrderRequest.getValue());
		args.add(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID.getVal());
		
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryParentOrderFindByStatusAndOverallStatus(com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum, com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum)  
	 */
	@Override
	public List<TOrderFind> queryParentOrderFindByStatusAndOverallStatus(OrderStatusEnum ose, OrderOverallStatusEnum oose) {
		if(ose == null){
			ose = OrderStatusEnum.ORDER_STATUS_YES;
		}
		if(oose == null){
			oose = OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE;
		}
		StringBuilder sql = new StringBuilder(QUERY_THE_PARENT_ORDER_FIND);
		
		List<Object> args = new ArrayList<Object>();
		args.add(ose.getVal());
		args.add(oose.getVal());
		
		return super.queryForList(sql.toString(), args);
	}
	
	@Override
	public int addMatchingNum(String fid) {
		int matchingNum = 0;
		
		TOrderFind entity = this.query(fid);
		
		if (entity !=  null){
			if (entity.getMatchingnum() == null){
				matchingNum = 1; 
			}else{ 
				matchingNum = entity.getMatchingnum() + 1;
			}
			entity.setMatchingnum(matchingNum);
			
			update(entity);
		}
		
		return matchingNum;
	}
	
	@Override
	public QueryContext<TOrderFind> queryParentOrderFindOfInsteadListForPagination(QueryContext<TOrderFind> qContext) {
		
		String operatorSql = "2=2";
		Object operatorId = qContext.getParameter("operatorId");
		if(operatorId != null){
			operatorSql = "`OWNER` = " + operatorId;
		}
		
		StringBuilder sql = new StringBuilder(SELECT_ORDERFIND_OF_INSTEAD.replace("2=2", operatorSql));
		
		return queryListForPagination(sql.toString(), null, qContext, new RowMapper<TOrderFind>() {
			public TOrderFind mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				TOrderFind of = new TOrderFind();
				
				OrderFindInsteadBean t = new OrderFindInsteadBean();
				
				t.setCid(rs.getString("CID"));
				t.setCname(rs.getString("CNAME"));
				t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
				t.setFid(rs.getString("FID"));
				t.setId(rs.getString("ID"));
				t.setOperationTime(rs.getTimestamp("CREATE_TIME"));
				t.setOperator(rs.getString("REALNAME"));
				t.setOperatorId(rs.getString("OWNER"));
				t.setTitle(rs.getString("TITlE"));
				t.setUsername(rs.getString("USERNAME"));
				
				of.setOrderFindInsteadBean(t);
				return of;
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindDao#queryCount(com.appabc.bean.pvo.TCompanyInfo)
	 */
	@Override
	public int queryCount(TOrderFind entity) {
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
		Number number = super.getNamedParameterJdbcTemplate().queryForObject(dynamicJoinSqlWithEntity(entity, new StringBuilder(COUNT_SQL)).toString(), paramSource, Integer.class);  
	    return (number != null ? number.intValue() : 0);
	}
	
}
