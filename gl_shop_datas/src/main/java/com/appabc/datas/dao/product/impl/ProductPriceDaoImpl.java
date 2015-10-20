/**
 *
 */
package com.appabc.datas.dao.product.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.product.IProductPriceDao;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月28日 下午5:10:07
 */
@Repository
public class ProductPriceDaoImpl extends BaseJdbcDao<TProductPrice> implements IProductPriceDao{

	private static final String INSERTSQL = " INSERT INTO T_PRODUCT_PRICE (PID, PRICE, UNIT, DATEPOINT, AREA, UPDATER, UPDATETIME) VALUES (:pid, :price, :unit, :datepoint, :area, :updater, :updatetime)";
	private static final String UPDATESQL = " UPDATE T_PRODUCT_PRICE SET PID = :pid, PRICE = :price, UNIT = :unit, DATEPOINT = :datepoint, AREA = :area, UPDATER = :updater, UPDATETIME = :updatetime WHERE ID = :id";
	private static final String DELETESQLBYID = " DELETE FROM T_PRODUCT_PRICE WHERE ID = :id";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PRODUCT_PRICE WHERE ID = :id";

	// 获得今日报价和昨天的价格
//	private static final String SELECT_TODAY_AND_YESTERDAY_PRICE_SQL = " select a.PID as pid, a.PNAME as pname,a.PTYPE as ptype,a.todayPrice,b.yesterdayPrice from ((select tpp.PRICE as todayPrice,ti.PNAME,ti.PTYPE,ti.PID from T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti where TO_DAYS(tpp.datepoint)=TO_DAYS(NOW()) and tpp.AREA=? and ti.PCODE=? and tpp.PID=ti.PID ) as a LEFT JOIN (select tpp.PRICE as yesterdayPrice,ti.PID from T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti where TO_DAYS(tpp.datepoint)=TO_DAYS(date_sub(current_date(),interval 1 day)) and tpp.AREA=? and ti.PCODE=? and tpp.PID=ti.PID) as b ON a.pid=b.pid) ORDER BY a.PNAME";
//	private static final String SELECT_TODAY_AND_YESTERDAY_PRICE_SQL_BEFORE_DAY = " select a.PID as pid, a.PNAME as pname,a.PTYPE as ptype,a.todayPrice,b.yesterdayPrice from ((select tpp.PRICE as todayPrice,ti.PNAME,ti.PTYPE,ti.PID from T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti where TO_DAYS(tpp.datepoint)=TO_DAYS(?) and tpp.AREA=? and ti.PCODE=? and tpp.PID=ti.PID ) as a LEFT JOIN (select tpp.PRICE as yesterdayPrice,ti.PID from T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti where TO_DAYS(tpp.datepoint)=TO_DAYS(date_sub(current_date(),interval 1 day)) and tpp.AREA=? and ti.PCODE=? and tpp.PID=ti.PID) as b ON a.pid=b.pid) ORDER BY a.PNAME";
	private static final String SELECT_MAX_TODAY_BEFORE_DAY_PRICE = "SELECT a.PID AS pid, a.PNAME AS pname, a.PTYPE AS ptype, a.todayPrice, 0 AS yesterdayPrice FROM ( SELECT tpp.datepoint, tpp.PID, tpp.ID, ti.PNAME, ti.PTYPE, tpp.PRICE AS todayPrice FROM T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti WHERE tpp.datepoint <= NOW() AND tpp.PID = ti.PID AND ti.PCODE = ? AND tpp.AREA = ? ORDER BY tpp.datepoint DESC ) a GROUP BY a.PID";
	
	// 今天之前最近的一天
//	private static final String SELECT_MAX_TODAY = "select max(dd.datepoint) DATEPOINT from T_PRODUCT_PRICE as dd where dd.datepoint < NOW()";

	private static final String BASE_SQL = " SELECT * FROM T_PRODUCT_PRICE WHERE 1=1 ";


	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.product.IProductPriceDao#queryTodayPrice(java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode){

		/*List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(SELECT_TODAY_AND_YESTERDAY_PRICE_SQL, area, pcode, area, pcode);
		if(list == null || list.size()==0 || list.get(0)==null){
			Map<String,Object> map = this.getJdbcTemplate().queryForMap(SELECT_MAX_TODAY);
			if(map != null){
				list =  this.getJdbcTemplate().queryForList(SELECT_TODAY_AND_YESTERDAY_PRICE_SQL_BEFORE_DAY,map.get("DATEPOINT"), area, pcode, area, pcode);
			}
		}*/
		return this.getJdbcTemplate().queryForList(SELECT_MAX_TODAY_BEFORE_DAY_PRICE, pcode, area);
	}

	public void save(TProductPrice entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TProductPrice entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TProductPrice entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductPrice entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TProductPrice query(TProductPrice entity) {
		return super.query(dynamicJoinSqlWithEntity(entity, new StringBuilder(BASE_SQL)), entity);
	}

	public TProductPrice query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TProductPrice> queryForList(TProductPrice entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity, new StringBuilder(BASE_SQL)), entity);
	}

	public List<TProductPrice> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TProductPrice> queryListForPagination(
			QueryContext<TProductPrice> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TProductPrice mapRow(ResultSet rs, int rowNum) throws SQLException {

		TProductPrice t = new TProductPrice();

		t.setId(rs.getString("ID"));
		t.setArea(rs.getString("AREA"));
		t.setDatepoint(rs.getTimestamp("DATEPOINT"));
		t.setPid(rs.getString("PID"));
		t.setPrice(rs.getFloat("PRICE"));
		t.setUnit(UnitEnum.enumOf(rs.getString("UNIT")));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TProductPrice bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "area", "AREA", bean.getArea());
		this.addNameParamerSqlWithProperty(sql, "datepoint", "DATEPOINT", bean.getDatepoint());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", bean.getPid());
		this.addNameParamerSqlWithProperty(sql, "price", "PRICE", bean.getPrice());
		this.addNameParamerSqlWithProperty(sql, "unit", "UNIT", bean.getUnit());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", bean.getUpdatetime());
		return sql.toString();
	}
	
	@Override
	public List<TProductPrice> queryListByDay(TProductPrice entity, Date day) {
		if(entity==null) entity = new TProductPrice();
		
		StringBuilder sql = new StringBuilder(BASE_SQL);
		sql = new StringBuilder(dynamicJoinSqlWithEntity(entity, sql));
		if(day != null){
			sql.append(" AND TO_DAYS(DATEPOINT)=TO_DAYS(:datepoint) ");
			entity.setDatepoint(day);
		}
		return super.queryForList(sql.toString(), entity);
	}

}
