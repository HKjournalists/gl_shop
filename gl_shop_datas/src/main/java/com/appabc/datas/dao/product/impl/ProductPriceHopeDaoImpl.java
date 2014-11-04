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
	private static final String SELEC_TPRICE_HOPE_1_2 = " SELECT t.PNAME AS pname,t.PTYPE AS ptype,a.todayPrice,b.basePrice1,c.basePrice2 FROM ("
			+ "SELECT tpi.PNAME,tpi.PTYPE,tpi.PID FROM T_PRODUCT_INFO tpi WHERE tpi.PCODE=?) AS t "
			+ "LEFT JOIN (SELECT tpp.PRICE AS todayPrice,ti.PID FROM T_PRODUCT_PRICE tpp, T_PRODUCT_INFO ti WHERE TO_DAYS(tpp.datepoint)=TO_DAYS(NOW()) AND tpp.AREA=? AND ti.PCODE=? AND tpp.PID=ti.PID) AS a ON t.PID=a.PID "
			+ "LEFT JOIN (SELECT tpph.BASEPRICE AS basePrice1,ti.PID FROM T_PRODUCT_PRICE_HOPE tpph,T_PRODUCT_INFO ti WHERE tpph.TIMETYPE='1' AND tpph.AREA=? AND ti.PCODE=? AND tpph.PID=ti.PID AND TO_DAYS(tpph.STARTTIME)=TO_DAYS(?) AND TO_DAYS(tpph.ENDTIME)=TO_DAYS(?) ) AS b ON t.PID=b.PID "
			+ "LEFT JOIN (SELECT tpph.BASEPRICE AS basePrice2,ti.PID FROM T_PRODUCT_PRICE_HOPE tpph,T_PRODUCT_INFO ti WHERE tpph.TIMETYPE='2' AND tpph.AREA=? AND ti.PCODE=? AND tpph.PID=ti.PID AND TO_DAYS(tpph.STARTTIME)=TO_DAYS(?) AND TO_DAYS(tpph.ENDTIME)=TO_DAYS(?) ) AS c ON t.PID=c.PID ORDER BY t.PNAME ";
	
	
	private static final String BASE_SQL = " SELECT * FROM T_PRODUCT_PRICE_HOPE WHERE 1=1 "; 

	public List<Map<String, Object>> queryHopePrice(String area, String pcode){
		
		Date startTime1 = DateUtil.getFirstDayOfNWeek(1);// 未来1周星期一
		Date endTime1 = DateUtil.getLastDayOfNWeek(1);// 未来1周星期日		
		Date startTime2 = DateUtil.getFirstDayOfNWeek(2);// 未来2周星期一
		Date endTime2 = DateUtil.getLastDayOfNWeek(2);// 未来2周星期日		
		
		return this.getJdbcTemplate().queryForList(SELEC_TPRICE_HOPE_1_2, pcode, area, pcode, area, pcode, startTime1, endTime1, area, pcode, startTime2, endTime2);
	} 
	
	public void save(TProductPriceHope entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TProductPriceHope entity) {
		return null;
	}

	public void update(TProductPriceHope entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductPriceHope entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TProductPriceHope query(TProductPriceHope entity) {
		return null;
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
		return null;
	}

	public QueryContext<TProductPriceHope> queryListForPagination(
			QueryContext<TProductPriceHope> qContext) {
		return null;
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

}
