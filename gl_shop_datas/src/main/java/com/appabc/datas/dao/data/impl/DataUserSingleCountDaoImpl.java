/**
 *
 */
package com.appabc.datas.dao.data.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.DataUserSingleCount;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.data.IDataUserSingleCountDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月22日 下午5:34:39
 */
@Repository
public class DataUserSingleCountDaoImpl extends BaseJdbcDao<DataUserSingleCount> implements IDataUserSingleCountDao {
	
	/**
	 * 单个用户信息统计SQL
	 */
	private static final String SELECT_DATA_USER_SINGLE_COUNT = "SELECT A.* ,cc.CNAME AS CONTACTNAME,cc.CPHONE AS PHONE,cc.TEL , pi1.AMOUNT GUARANTY,pi2.AMOUNT DEPOSIT ,( CASE ar.AUTHSTATUS WHEN '1' THEN '通过' WHEN '2' THEN '审核中' ELSE '未认证' END ) AS AUTHSTATUS ,ar.AUTHRESULT ,IFNULL(buy_order.BUYNUM,0) AS BUYNUM ,IFNULL(sell_order.SELLNUM,0) AS SELLNUM ,(IFNULL(buy_contract_end.NUM_END,0)+IFNULL(sell_contract_end.NUM_END,0)) AS CONTRACTNUMEND ,(IFNULL(buy_contract_ing.NUM_ING,0)+IFNULL(sell_contract_ing.NUM_ING,0)) AS CONTRACTNUMING from ( SELECT u.USERNAME,u.CREATEDATE as REGDATE,ci.CNAME,ci.CTYPE,u.CID FROM T_USER u,T_COMPANY_INFO ci WHERE 1=1 AND ci.ID = u.CID AND u.`STATUS`=0 ) AS A LEFT JOIN T_COMPANY_CONTACT cc ON cc.CID=A.CID AND cc.`STATUS`=1 LEFT JOIN T_PASSBOOK_INFO AS pi1 ON A.CID=pi1.CID AND pi1.PASSTYPE=0 LEFT JOIN T_PASSBOOK_INFO AS pi2 ON A.CID=pi2.CID AND pi2.PASSTYPE=1 LEFT JOIN (SELECT t.* FROM (SELECT * FROM T_AUTH_RECORD WHERE TYPE=1 ORDER BY CREATEDATE DESC) t GROUP BY t.CID ) AS ar ON ar.CID = A.CID LEFT JOIN (SELECT COUNT(0) BUYNUM,CID FROM T_ORDER_FIND WHERE TYPE=1 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS buy_order ON buy_order.CID=A.CID LEFT JOIN (SELECT COUNT(0) SELLNUM,CID FROM T_ORDER_FIND WHERE TYPE=2 AND OVERALLSTATUS=0 AND OVERALLTYPE=0 GROUP BY CID) AS sell_order ON sell_order.CID=A.CID LEFT JOIN (SELECT COUNT(0) NUM_END,BUYERID AS CID FROM T_ORDER_INFO WHERE `STATUS`=3 AND LIFECYCLE IN (15,16,17,23,24) GROUP BY BUYERID) AS buy_contract_end ON buy_contract_end.CID=A.CID LEFT JOIN (SELECT COUNT(0) NUM_END,SELLERID AS CID FROM T_ORDER_INFO WHERE `STATUS`=3 AND LIFECYCLE IN (15,16,17,23,24) GROUP BY SELLERID) AS sell_contract_end ON sell_contract_end.CID=A.CID LEFT JOIN (SELECT COUNT(0) NUM_ING,BUYERID AS CID FROM T_ORDER_INFO WHERE `STATUS`=1 GROUP BY BUYERID) AS buy_contract_ing ON buy_contract_ing.CID=A.CID LEFT JOIN (SELECT COUNT(0) NUM_ING,SELLERID AS CID FROM T_ORDER_INFO WHERE `STATUS`=1 GROUP BY SELLERID) AS sell_contract_ing ON sell_contract_ing.CID=A.CID";
	
	private static final String COUNT_SQL_OF_DATA_USER_SINGLE_COUNT = "SELECT COUNT(0) FROM T_USER u,T_COMPANY_INFO ci WHERE 1=1 AND ci.ID = u.CID AND u.`STATUS`=0";
	
	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void save(DataUserSingleCount entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(DataUserSingleCount entity) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void update(DataUserSingleCount entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(DataUserSingleCount entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public DataUserSingleCount query(DataUserSingleCount entity) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	@Override
	public DataUserSingleCount query(Serializable id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<DataUserSingleCount> queryForList(DataUserSingleCount entity) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	@Override
	public List<DataUserSingleCount> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<DataUserSingleCount> queryListForPagination(
			QueryContext<DataUserSingleCount> qContext) {
		return super.queryListForPagination(SELECT_DATA_USER_SINGLE_COUNT, COUNT_SQL_OF_DATA_USER_SINGLE_COUNT, qContext);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.data.IDataUserSingleCountDao#queryListForPaginationOfDataUserSingleCount(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<DataUserSingleCount> queryListForPaginationOfDataUserSingleCount(
			QueryContext<DataUserSingleCount> qContext) {
		
		StringBuilder querySql = new StringBuilder("SELECT B.* FROM (");
		StringBuilder whereSql = new StringBuilder("1=1");
		
		if(qContext.getParameter("ctype") != null){
			whereSql.append(" AND ci.CTYPE=:ctype");
		}
		if(qContext.getParameter("authstatus") != null){
			whereSql.append(" AND ci.AUTHSTATUS=:authstatus");
		}
		if(qContext.getParameter("username") != null){
			whereSql.append(" AND u.USERNAME=:username");
		}
		
		querySql.append(SELECT_DATA_USER_SINGLE_COUNT.replace("1=1", whereSql.toString()));
		querySql.append(") B ORDER BY B.CONTRACTNUMING DESC");
		
		String countSql = COUNT_SQL_OF_DATA_USER_SINGLE_COUNT.replace("1=1", whereSql.toString());
		
		return super.queryListForPagination(querySql.toString(), countSql, qContext);
	}
	
	@Override
	public List<DataUserSingleCount> queryAllForList() {
		StringBuilder querySql = new StringBuilder("SELECT B.* FROM (");
		querySql.append(SELECT_DATA_USER_SINGLE_COUNT);
		querySql.append(") B ORDER BY B.CONTRACTNUMING DESC");
		return super.queryForList(querySql.toString(), new DataUserSingleCount());
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public DataUserSingleCount mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DataUserSingleCount t = new DataUserSingleCount();

		t.setAuthresult(rs.getString("AUTHRESULT"));
		t.setAuthstatus(rs.getString("AUTHSTATUS"));
		t.setBuynum(rs.getInt("BUYNUM"));
		t.setCid(rs.getString("CID"));
		t.setCname(rs.getString("CNAME"));
		t.setContactname(rs.getString("CONTACTNAME"));
		t.setContractnumend(rs.getInt("CONTRACTNUMEND"));
		t.setContractnuming(rs.getInt("CONTRACTNUMING"));
		t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
		t.setDeposit(rs.getDouble("DEPOSIT"));
		t.setGuaranty(rs.getDouble("GUARANTY"));
		t.setPhone(rs.getString("PHONE"));
		t.setRegdate(rs.getTimestamp("REGDATE"));
		t.setSellnum(rs.getInt("SELLNUM"));
		t.setTel(rs.getString("TEL"));
		t.setUsername(rs.getString("USERNAME"));

		return t;
	}
	

}
