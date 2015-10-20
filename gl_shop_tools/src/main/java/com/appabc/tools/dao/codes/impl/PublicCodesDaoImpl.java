/**
 *
 */
package com.appabc.tools.dao.codes.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.codes.IPublicCodesDao;

/**
 * @Description : 公共代码集DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午12:07:28
 */
@Repository
public class PublicCodesDaoImpl extends BaseJdbcDao<TPublicCodes> implements IPublicCodesDao{

	private static final String INSERTSQL = " insert into T_PUBLIC_CODES (CODE, VAL, NAME, PCODE, ORDERNO, ISHIDDEN, UPDATER, UPDATETIME) values (:code, :val, :name, :pcode,:orderno,:ishidden,:updater,:updatetime) ";
	private static final String UPDATESQL = " update T_PUBLIC_CODES set CODE = :code, VAL = :val, NAME = :name, PCODE = :pcode,ORDERNO=:orderno,ISHIDDEN=:ishidden,UPDATER=:updater,UPDATETIME=:updatetime where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_PUBLIC_CODES WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PUBLIC_CODES WHERE ID = :id ";
	
	private static final String QUERY_MAX_VALUE = " SELECT MAX(VAL) FROM T_PUBLIC_CODES WHERE 1=1 ";

	private static final String BASE_SQL = " SELECT * FROM T_PUBLIC_CODES WHERE 1=1 ";

	public void save(TPublicCodes entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TPublicCodes entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TPublicCodes entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TPublicCodes entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TPublicCodes query(TPublicCodes entity) {
		return super.query(dynamicJoinSqlWithEntity(entity, new StringBuilder(BASE_SQL)), entity);
	}

	public TPublicCodes query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TPublicCodes> queryForList(TPublicCodes entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity, new StringBuilder(BASE_SQL)) +" ORDER BY ORDERNO", entity);
	}

	public List<TPublicCodes> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TPublicCodes> queryListForPagination(
			QueryContext<TPublicCodes> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(), new StringBuilder(BASE_SQL)), qContext);
	}

	public TPublicCodes mapRow(ResultSet rs, int rowNum) throws SQLException {

		TPublicCodes t = new TPublicCodes();

		t.setId(rs.getString("ID"));
		t.setCode(rs.getString("CODE"));
		t.setName(rs.getString("NAME"));
		t.setPcode(rs.getString("PCODE"));
		t.setVal(rs.getString("VAL"));
		t.setOrderno(rs.getInt("ORDERNO"));
		t.setIshidden(rs.getInt("ISHIDDEN"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));

		return t;
	}
	
	private String dynamicJoinSqlWithEntity(TPublicCodes bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "code", "CODE", bean.getCode());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", bean.getName());
		this.addNameParamerSqlWithProperty(sql, "pcode", "PCODE", bean.getPcode());
		this.addNameParamerSqlWithProperty(sql, "val", "VAL", bean.getVal());
		this.addNameParamerSqlWithProperty(sql, "orderno", "ORDERNO", bean.getOrderno());
		this.addNameParamerSqlWithProperty(sql, "ishidden", "ISHIDDEN", bean.getIshidden());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", bean.getUpdatetime());
		return sql.toString();
	}
	
	@Override
	public String getMaxValue(TPublicCodes entity) {
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
		String maxVal = super.getNamedParameterJdbcTemplate().queryForObject(dynamicJoinSqlWithEntity(entity, new StringBuilder(QUERY_MAX_VALUE)).toString(), paramSource, String.class);  
	    return maxVal;
	}
	
	@Override
	public List<TPublicCodes> queryListInNoDel(String code){
		
		TPublicCodes entity = new TPublicCodes();
		
		StringBuilder sql = new StringBuilder(BASE_SQL);
		sql.append(" AND ISHIDDEN != 2  AND CODE=:code");
		sql.append(" ORDER BY ORDERNO");
		
		entity.setCode(code);
		
		return super.queryForList(sql.toString(), entity);
	}

}
