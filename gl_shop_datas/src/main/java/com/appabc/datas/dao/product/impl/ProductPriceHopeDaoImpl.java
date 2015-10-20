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

import com.appabc.bean.pvo.TProductPriceHope;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.dao.product.IProductPriceHopeDao;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月28日 上午10:02:57
 */
@Repository
public class ProductPriceHopeDaoImpl extends BaseJdbcDao<TProductPriceHope> implements IProductPriceHopeDao{

	private static final String INSERTSQL = " insert into T_PRODUCT_PRICE_HOPE (PID, BASEPRICE, PRICEMIN, PRICEMAX, UNIT, STARTTIME, ENDTIME, TIMETYPE, AREA, UPDATER, UPDATETIME) values (:pid, :baseprice, :pricemin, :pricemax, :unit, :starttime, :endtime, :timetype, :area, :updater, :updatetime) ";
	private static final String UPDATESQL = " update T_PRODUCT_PRICE_HOPE set PID = :pid, BASEPRICE = :baseprice, PRICEMIN = :pricemin, PRICEMAX = :pricemax, UNIT = :unit, STARTTIME = :starttime, ENDTIME = :endtime, TIMETYPE = :timetype, AREA = :area, UPDATER = :updater, UPDATETIME = :updatetime where ID = :id";
	private static final String DELETESQLBYID = " DELETE FROM T_PRODUCT_PRICE_HOPE WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PRODUCT_PRICE_HOPE WHERE ID = :id ";
	// 商品当天价格+未来1周价格+未来2周价格
	private static final String SELEC_TPRICE_HOPE_1_2 = " SELECT t.PID as pid,t.PNAME AS pname,t.PTYPE AS ptype,a.todayPrice,b.basePrice1,c.basePrice2 FROM ("
			+ "SELECT tpi.PNAME,tpi.PTYPE,tpi.PID FROM T_PRODUCT_INFO tpi WHERE tpi.PCODE=?) AS t "
//			+ "LEFT JOIN (SELECT tpp.PRICE AS todayPrice,ti.PID FROM T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti WHERE TO_DAYS(tpp.datepoint)=TO_DAYS(NOW()) AND tpp.AREA=? AND ti.PCODE=? AND tpp.PID=ti.PID) AS a ON t.PID=a.PID "
			+ "LEFT JOIN (SELECT e.* FROM (SELECT tpp.PRICE AS todayPrice,ti.PID FROM T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti WHERE tpp.datepoint<=NOW() AND tpp.AREA=? AND ti.PCODE=? AND tpp.PID=ti.PID ORDER BY DATEPOINT DESC) e GROUP BY PID) AS a ON t.PID=a.PID "
			+ "LEFT JOIN (SELECT tpph.BASEPRICE AS basePrice1,ti.PID FROM T_PRODUCT_PRICE_HOPE tpph,T_PRODUCT_INFO ti WHERE tpph.TIMETYPE='1' AND tpph.AREA=? AND ti.PCODE=? AND tpph.PID=ti.PID AND TO_DAYS(tpph.STARTTIME)=TO_DAYS(?) AND TO_DAYS(tpph.ENDTIME)=TO_DAYS(?) ) AS b ON t.PID=b.PID "
			+ "LEFT JOIN (SELECT tpph.BASEPRICE AS basePrice2,ti.PID FROM T_PRODUCT_PRICE_HOPE tpph,T_PRODUCT_INFO ti WHERE tpph.TIMETYPE='2' AND tpph.AREA=? AND ti.PCODE=? AND tpph.PID=ti.PID AND TO_DAYS(tpph.STARTTIME)=TO_DAYS(?) AND TO_DAYS(tpph.ENDTIME)=TO_DAYS(?) ) AS c ON t.PID=c.PID ORDER BY t.PNAME ";

	// 商品价格预测，最近一天+最近一周的(只为了保证客户端有数据，不保证数据的准确性)
	private static final String SELEC_TPRICE_HOPE_1_2_MAX = "SELECT  A.PID AS pid, A.PNAME AS pname, A.PTYPE AS ptype, D.todayPrice, B.BASEPRICE basePrice1, C.BASEPRICE basePrice2"
			+" FROM T_PRODUCT_INFO A"
			+" LEFT JOIN ( SELECT HH.* FROM ( SELECT pi.PNAME, pph.PID, pph.BASEPRICE, pph.PRICEMIN, pph.PRICEMAX FROM T_PRODUCT_PRICE_HOPE pph, T_PRODUCT_INFO pi WHERE pi.PID = pph.PID AND STARTTIME <= DATE_ADD(NOW(), INTERVAL 7 DAY)  AND pi.PCODE = ? AND pph.AREA = ? AND pph.TIMETYPE = 1 ORDER BY pph.STARTTIME DESC ) HH GROUP BY PID"
			+" ) B ON A.PID = B.PID"
			+" LEFT JOIN ( SELECT HH.* FROM ( SELECT pi.PNAME, pph.PID, pph.BASEPRICE, pph.PRICEMIN, pph.PRICEMAX FROM T_PRODUCT_PRICE_HOPE pph, T_PRODUCT_INFO pi WHERE pi.PID = pph.PID AND STARTTIME <= DATE_ADD(NOW(), INTERVAL 14 DAY) AND pi.PCODE = ? AND pph.AREA = ? AND pph.TIMETYPE = 2 ORDER BY pph.STARTTIME DESC ) HH GROUP BY PID"
			+" ) C ON A.PID = C.PID"
			+" LEFT JOIN ( SELECT mt.todayPrice, mt.PID FROM ( SELECT tpp.datepoint, tpp.PID, tpp.ID, tpp.PRICE AS todayPrice FROM T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti WHERE tpp.datepoint <= NOW() AND tpp.PID = ti.PID AND ti.PCODE = ? AND tpp.AREA = ? ORDER BY tpp.datepoint DESC ) mt GROUP BY mt.PID ) D ON D.PID = A.PID"
			+" WHERE PCODE = ?";

	private static final String BASE_SQL = " SELECT * FROM T_PRODUCT_PRICE_HOPE WHERE 1=1 ";

	public List<Map<String, Object>> queryHopePrice(String area, String pcode){

		Date startTime1 = DateUtil.getFirstDayOfNWeek(null, 1);// 未来1周星期一
		Date endTime1 = DateUtil.getLastDayOfNWeek(null, 1);// 未来1周星期日
		Date startTime2 = DateUtil.getFirstDayOfNWeek(null, 2);// 未来2周星期一
		Date endTime2 = DateUtil.getLastDayOfNWeek(null, 2);// 未来2周星期日

		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(SELEC_TPRICE_HOPE_1_2, pcode, area, pcode, area, pcode, startTime1, endTime1, area, pcode, startTime2, endTime2);
		if(list==null || list.get(0).get("basePrice1")==null){
			list = this.getJdbcTemplate().queryForList(SELEC_TPRICE_HOPE_1_2_MAX, pcode, area, pcode, area, pcode, area, pcode);
		}

		return list;
	}

	public void save(TProductPriceHope entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TProductPriceHope entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TProductPriceHope entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductPriceHope entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TProductPriceHope query(TProductPriceHope entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TProductPriceHope query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TProductPriceHope> queryForList(TProductPriceHope entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		if(entity.getArea() != null && !entity.getArea().isEmpty()){

			sql.append(" AND AREA = :area");
		}
		return super.queryForList(sql.toString(), entity);
	}

	public List<TProductPriceHope> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TProductPriceHope> queryListForPagination(
			QueryContext<TProductPriceHope> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TProductPriceHope mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		TProductPriceHope t = new TProductPriceHope();

		t.setId(rs.getString("ID"));
		t.setArea(rs.getString("AREA"));
		t.setBaseprice(rs.getFloat("BASEPRICE"));
		t.setEndtime(rs.getTimestamp("ENDTIME"));
		t.setPid(rs.getString("PID"));
		t.setPricemax(rs.getFloat("PRICEMAX"));
		t.setPricemin(rs.getFloat("PRICEMIN"));
		t.setStarttime(rs.getTimestamp("STARTTIME"));
		t.setTimetype(rs.getString("TIMETYPE"));
		t.setUnit(rs.getString("UNIT"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TProductPriceHope bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "area", "AREA", bean.getArea());
		this.addNameParamerSqlWithProperty(sql, "baseprice", "BASEPRICE", bean.getBaseprice());
		this.addNameParamerSqlWithProperty(sql, "endtime", "ENDTIME", bean.getEndtime());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", bean.getPid());
		this.addNameParamerSqlWithProperty(sql, "pricemax", "PRICEMAX", bean.getPricemax());
		this.addNameParamerSqlWithProperty(sql, "pricemin", "PRICEMIN", bean.getPricemin());
		this.addNameParamerSqlWithProperty(sql, "starttime", "STARTTIME", bean.getStarttime());
		this.addNameParamerSqlWithProperty(sql, "timetype", "TIMETYPE", bean.getTimetype());
		this.addNameParamerSqlWithProperty(sql, "unit", "UNIT", bean.getUnit());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", bean.getUpdatetime());
		return sql.toString();
	}
	
	@Override
	public List<TProductPriceHope> queryListByDay(TProductPriceHope entity, Date day) {
		if(entity==null) entity = new TProductPriceHope();
		
		StringBuilder sql = new StringBuilder(BASE_SQL);
		sql = new StringBuilder(dynamicJoinSqlWithEntity(entity, sql));
		if(day != null){
			
			Date queryStartTime1 = DateUtil.getFirstDayOfNWeek(day, 1);// 未来1周星期一
			Date queryEndTime1 = DateUtil.getLastDayOfNWeek(day, 1);// 未来1周星期日
			Date queryStartTime2 = DateUtil.getFirstDayOfNWeek(day, 2);// 未来2周星期一
			Date queryEndTime2 = DateUtil.getLastDayOfNWeek(day, 2);// 未来2周星期日
			
			entity.setQueryStartTime1(queryStartTime1);
			entity.setQueryEndTime1(queryEndTime1);
			entity.setQueryStartTime2(queryStartTime2);
			entity.setQueryEndTime2(queryEndTime2);
			
			
			sql.append(" AND ( (TIMETYPE=1 AND TO_DAYS(STARTTIME)=TO_DAYS(:queryStartTime1) AND TO_DAYS(ENDTIME)=TO_DAYS(:queryEndTime1) ) OR (TIMETYPE=2 AND TO_DAYS(STARTTIME)=TO_DAYS(:queryStartTime2) AND TO_DAYS(ENDTIME)=TO_DAYS(:queryEndTime2) ))");
//			entity.setDatepoint(day);
		}
		return super.queryForList(sql.toString(), entity);
	}

}
