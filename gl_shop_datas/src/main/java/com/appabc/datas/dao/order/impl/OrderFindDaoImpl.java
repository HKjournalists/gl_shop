/**
 *
 */
package com.appabc.datas.dao.order.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.SQLExpressionEnum;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.datas.enums.OrderFindInfo.OrderMoreAreaEnum;
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
	
	@Autowired
	private PrimaryKeyGenerator pkg;
	
	private static final String BASE_SQL = " SELECT * FROM T_ORDER_FIND WHERE 1=1 "; 
	private static final String INSERTSQL = " insert into T_ORDER_FIND (FID, CID, TITlE, TYPE, ADDRESSTYPE, PRICE, TOTALNUM, NUM, STARTTIME,  ENDTIME, MOREAREA, AREA, CREATER, CREATIME, LIMITIME, STATUS, REMARK, PARENTID,UPDATER,UPDATETIME,OVERALLSTATUS ) values (:id, :cid, :title, :type, :addresstype, :price, :totalnum, :num, :starttime, :endtime, :morearea, :area, :creater, :creatime, :limitime, :status, :remark, :parentid,:updater,:updatetime,:overallstatus ) ";
	private static final String UPDATESQL = " update T_ORDER_FIND set CID = :cid, TITlE = :title, TYPE = :type, ADDRESSTYPE = :addresstype, PRICE = :price, TOTALNUM = :totalnum, NUM = :num, STARTTIME = :starttime, ENDTIME = :endtime, MOREAREA = :morearea, AREA = :area, CREATER = :creater, CREATIME = :creatime, LIMITIME = :limitime, STATUS = :status, REMARK = :remark, PARENTID = :parentid, UPDATER=:updater, UPDATETIME=:updatetime,OVERALLSTATUS=:overallstatus where FID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_FIND WHERE FID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_FIND WHERE FID = :id ";
	// 找买找卖查询SQL
	private static final String SELECT_ORDER_LIST_SQL = "SELECT of.FID,of.CID,of.TITlE,of.TYPE,of.PRICE,of.TOTALNUM,of.STARTTIME,of.LIMITIME,of.AREA,opi.PNAME,opi.PTYPE,opi.UNIT,of.STATUS from T_ORDER_FIND of,T_ORDER_PRODUCT_INFO opi ";
	// 询单详情SQL
	private static final String SELECT_ORDER_INFO_SQL = " SELECT of.FID, of.CID, of.TITlE, of.TYPE, of.PRICE, of.TOTALNUM, of.NUM, of.STARTTIME, of.ENDTIME, of.MOREAREA, of.LIMITIME, of.AREA, of.REMARK, opi.ID AS OPIID, opi.PNAME, opi.PTYPE, opi.PSIZE, opi.PCOLOR, opi.PADDRESS, opi.UNIT, opi.REMARK AS PREMARK, of.STATUS FROM T_ORDER_FIND of, T_ORDER_PRODUCT_INFO opi WHERE of.FID = opi.FID ";
	
	public void save(TOrderFind entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("ORDERFID"));
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderFind entity) {
		return null;
	}

	public void update(TOrderFind entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderFind entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderFind query(TOrderFind entity) {
		return null;
	}

	public TOrderFind query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderFind> queryForList(TOrderFind entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}
	
	private String dynamicJoinSqlWithEntity(TOrderFind bean,StringBuffer sql){
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
		return null;
	}

	public QueryContext<TOrderFind> queryListForPagination(
			QueryContext<TOrderFind> qContext) {
		
		StringBuffer sql = new StringBuffer(SELECT_ORDER_LIST_SQL);
		/*****过滤条件******************************/
		sql.append("where 1=1 ");
		
		addNameParamerSqlWithProperty(sql, "pid",  "opi.PID", qContext.getParameters().get("pid")); // 商品ID,查询某种商品
		addNameParamerSqlWithProperty(sql, "area",  "of.AREA", qContext.getParameters().get("area")); //靖江段code等……
		addNameParamerSqlWithProperty(sql, "type",  "of.TYPE", qContext.getParameters().get("type")); // 类型: 买或卖（0或1）
		addNameParamerSqlWithProperty(sql, "cid",  "of.CID", qContext.getParameters().get("cid")); // 企业ID，我的供求列表必带条件
		if(qContext.getParameters().get("queryDate") != null && !qContext.getParameters().get("queryDate").toString().trim().equals("")){ // 有效时间查询
			addNameParamerSqlWithProperty(sql, "queryDate",  "of.STARTTIME", qContext.getParameters().get("queryDate").toString(), SQLExpressionEnum.LT.getText());
			addNameParamerSqlWithProperty(sql, "queryDate",  "of.LIMITIME", qContext.getParameters().get("queryDate").toString(), SQLExpressionEnum.GT.getText());
		}
		if(qContext.getParameters().get("queryMethod") != null && qContext.getParameters().get("queryMethod").toString().trim().equals("getOrderList")){ // 查询方法判断
			sql.append(" AND (of.FID = opi.FID OR of.PARENTID=opi.FID) AND MOREAREA="+OrderMoreAreaEnum.ORDER_MORE_AREA_NO.getVal());// 找买找卖，过滤父询单
		}else{
			sql.append(" AND of.FID = opi.FID "); // 我的供求，过滤子询单(子询单的商品依赖父询单，会自动过滤)
		}
		addNameParamerSqlWithProperty(sql, "overallstatus",  "of.OVERALLSTATUS", qContext.getParameters().get("overallstatus")); // 状态,(有效，失效)，找买找卖查询时必须为有效
		/******排序**********************/
		if(qContext.getParameters().get("orderPrice") != null  && !qContext.getParameters().get("orderPrice").toString().trim().equals("")){ // 价格排序
			qContext.setOrderColumn("of.PRICE");
			qContext.setOrder(qContext.getParameters().get("orderPrice").toString()); // ASC,DESC
		}else if(qContext.getParameters().get("orderEffTime") != null  && !qContext.getParameters().get("orderEffTime").toString().trim().equals("")){ // 发布时间
			qContext.setOrderColumn("of.STARTTIME");
			qContext.setOrder(qContext.getParameters().get("orderCredit").toString()); // ASC,DESC
		}else if(qContext.getParameters().get("orderCredit") != null  && !qContext.getParameters().get("orderCredit").toString().trim().equals("")){ // 诚信度
		
		}else{ // 默认按开始时间倒序
			qContext.setOrderColumn("of.STARTTIME");
			qContext.setOrder("DESC");;
		}
		
		return queryListForPagination(sql.toString(), qContext, new RowMapper<TOrderFind>() {
					public TOrderFind mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						TOrderFind t = new TOrderFind();
						
						t.setId(rs.getString("FID"));
						t.setCid(rs.getString("CID"));
						t.setTitle(rs.getString("TITlE"));
						t.setType(rs.getInt("TYPE"));
						t.setPrice(rs.getFloat("PRICE"));
						t.setTotalnum(rs.getFloat("TOTALNUM"));
						t.setStarttime(rs.getTimestamp("STARTTIME"));
						t.setLimitime(rs.getTimestamp("LIMITIME"));
						t.setArea(rs.getString("AREA"));
						t.setPname(rs.getString("PNAME"));
						t.setPtype(rs.getString("PTYPE"));
						t.setUnit(rs.getString("UNIT"));
						t.setStatus(rs.getInt("STATUS"));

						return t;
					}
				});
		
		
	}

	public TOrderFind mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderFind t = new TOrderFind();
		
		t.setId(rs.getString("FID"));
		t.setAddresstype(rs.getInt("ADDRESSTYPE"));
		t.setArea(rs.getString("AREA"));
		t.setCid(rs.getString("CID"));
		t.setCreater(rs.getString("CREATER"));
		t.setCreatime(rs.getTimestamp("CREATIME"));
		t.setEndtime(rs.getTimestamp("ENDTIME"));
		t.setLimitime(rs.getTimestamp("LIMITIME"));
		t.setMorearea(rs.getString("MOREAREA"));
		t.setNum(rs.getFloat("NUM"));
		t.setParentid(rs.getString("PARENTID"));
		t.setPrice(rs.getFloat("PRICE"));
		t.setRemark(rs.getString("REMARK"));
		t.setStarttime(rs.getTimestamp("STARTTIME"));
		t.setStatus(rs.getInt("STATUS"));
		t.setTitle(rs.getString("TITLE"));
		t.setTotalnum(rs.getFloat("TOTALNUM"));
		t.setType(rs.getInt("TYPE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setOverallstatus(rs.getInt("OVERALLSTATUS"));
		
		return t;
	}

	/**
	 * 根据询单详情查询
	 * @param fid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid) {
		
		StringBuffer sql = new StringBuffer(SELECT_ORDER_INFO_SQL);
		
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
				t.setTotalnum(rs.getInt("TOTALNUM"));
				t.setNum(rs.getInt("NUM"));
				t.setStarttime(rs.getTimestamp("STARTTIME"));
				t.setEndtime(rs.getTimestamp("ENDTIME"));
				t.setLimitime(rs.getTimestamp("LIMITIME"));
				t.setMorearea(rs.getString("MOREAREA"));
				t.setArea(rs.getString("AREA"));
				t.setRemark(rs.getString("REMARK"));
				t.setStatus(rs.getInt("STATUS"));
				
				t.setOpiid(rs.getInt("OPIID"));
				t.setPname(rs.getString("PNAME"));
				t.setPtype(rs.getString("PTYPE"));
				t.setPsize(rs.getString("PSIZE"));
				t.setPcolor(rs.getString("PCOLOR"));
				t.setPaddress(rs.getString("PADDRESS"));
				t.setUnit(rs.getString("UNIT"));
				t.setPremark(rs.getString("PREMARK"));

				return t;
			}
		});
		
		if(oaiList != null && oaiList.size()>0){
			return oaiList.get(0);
		}
		
		return null;
	}

	
}
