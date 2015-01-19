/**
 *
 */
package com.appabc.tools.dao.codes.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPublicCodes;
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

	private static final String INSERTSQL = " insert into T_PUBLIC_CODES (CODE, VAL, NAME, PCODE,ORDERNO,ISHIDDEN) values (:code, :val, :name, :pcode,:orderno,:ishidden) ";
	private static final String UPDATESQL = " update T_PUBLIC_CODES set CODE = :code, VAL = :val, NAME = :name, PCODE = :pcode,ORDERNO=:orderno,ISHIDDEN=:ishidden where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_PUBLIC_CODES WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PUBLIC_CODES WHERE ID = :id ";

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
		StringBuilder sql = new StringBuilder(BASE_SQL);
		if(entity.getCode() !=null && !entity.getCode().isEmpty()){
			sql.append(" AND CODE=:code");
		}
		if(entity.getPcode() !=null && !entity.getPcode().isEmpty()){
			sql.append(" AND PCODE=:pcode");
		}
		if(entity.getName() !=null && !entity.getName().isEmpty()){
			sql.append(" AND NAME=:name");
		}
		if(entity.getVal() !=null && !entity.getVal().isEmpty()){
			sql.append(" AND VAL=:val");
		}
		if(entity.getOrderno() !=null){
			sql.append(" AND ORDERNO=:orderno");
		}
		if(entity.getIshidden() !=null){
			sql.append(" AND ISHIDDEN=:ishidden");
		}

		return super.query(sql.toString(), entity);
	}

	public TPublicCodes query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TPublicCodes> queryForList(TPublicCodes entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		if(entity.getCode() !=null && !entity.getCode().isEmpty()){
			sql.append(" AND CODE=:code");
		}
		if(entity.getPcode() !=null && !entity.getPcode().isEmpty()){
			sql.append(" AND PCODE=:pcode");
		}
		if(entity.getName() !=null && !entity.getName().isEmpty()){
			sql.append(" AND NAME=:name");
		}
		if(entity.getVal() !=null && !entity.getVal().isEmpty()){
			sql.append(" AND VAL=:val");
		}
		if(entity.getOrderno() !=null){
			sql.append(" AND ORDERNO=:orderno");
		}
		if(entity.getIshidden() !=null){
			sql.append(" AND ISHIDDEN=:ishidden");
		}

		return super.queryForList(sql.toString(), entity);
	}

	public List<TPublicCodes> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TPublicCodes> queryListForPagination(
			QueryContext<TPublicCodes> qContext) {
		return super.queryListForPagination(SELECTSQLBYID, qContext);
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

		return t;
	}

}
