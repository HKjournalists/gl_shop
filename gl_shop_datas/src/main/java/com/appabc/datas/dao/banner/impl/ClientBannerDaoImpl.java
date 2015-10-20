package com.appabc.datas.dao.banner.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.ClientBannerInfo;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.banner.IClientBannerDao;



/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2015年8月4日 上午10:48:40
 */
@Repository
public class ClientBannerDaoImpl extends BaseJdbcDao<TClientBanner> implements
		IClientBannerDao {

	private static final String SELECT_SQL = " SELECT BANNER.BID AS BID,BANNER.BTYPE AS BTYPE,BANNER.BNAME AS BNAME,BANNER.SORTIMGID AS SORTIMGID, "
			+ "BANNER.THUMEDID AS THUMEDID, BANNER.ORDERNO AS ORDERNO,BANNER.CREATER AS CREATER,BANNER.CREATETIME AS CREATETIME,"
			+ "BANNER.UPDATER AS UPDATER,BANNER.UPDATETIME AS UPDATETIME,BANNER.TARGETURL AS TARGETURL,BANNER.REMARK AS REMARK,"
			+ "IMAGES.FNAME AS FNAME,IMAGES.FTYPE AS FTYPE,IMAGES.FSIZE AS FSIZE,IMAGES.FPATH AS FPATH,"
			+ "IMAGES.FULLPATH AS FULLPATH,IMAGES.CREATEDATE AS CREATEDATE FROM T_CLIENT_BANNER BANNER LEFT JOIN T_UPLOAD_IMAGES IMAGES "
			+ "ON BANNER.SORTIMGID=IMAGES.ID";
	private static final String INSERT_SQL = "  INSERT  T_CLIENT_BANNER (BTYPE, BNAME, SORTIMGID, THUMEDID, ORDERNO, CREATER, CREATETIME, UPDATETIME, UPDATER,TARGETURL, REMARK) VALUES (:btype, :bname, :sortimgid, :thumedid, :orderno, :creater, :createtime, :updatetime, :updater,:targeturl, :remark)";
	private static final String UPDATE_SQL = " UPDATE T_CLIENT_BANNER SET BNAME = :bname,SORTIMGID = :sortimgid,THUMEDID = :thumedid,ORDERNO = :orderno,UPDATETIME = :updatetime,UPDATER = :updater,TARGETURL= :targeturl,REMARK = :remark WHERE BID = :bid ";
	private static final String DELETE_SQL = " DELETE FROM T_CLIENT_BANNER WHERE BID = :bid ";
	private static final String GETMAXORDERNO_SQL = "SELECT MAX(ORDERNO) FROM T_CLIENT_BANNER WHERE BTYPE = ? ";

	private RowMapper<ClientBannerInfo> rowMapperEx = new RowMapper<ClientBannerInfo>() {
		@Override
		public ClientBannerInfo mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ClientBannerInfo bean = new ClientBannerInfo();
			bean.setBid(rs.getString("BID"));
			bean.setBtype(ClientType.enumOf(rs.getInt("BTYPE")));
			bean.setBname(rs.getString("BNAME"));
			bean.setSortimgid(rs.getString("SORTIMGID"));
			bean.setThumedid(rs.getString("THUMEDID"));
			bean.setOrderno(rs.getInt("ORDERNO"));
			bean.setCreater(rs.getString("CREATER"));
			bean.setCreatetime(rs.getTimestamp("CREATETIME"));
			bean.setUpdater(rs.getString("UPDATER"));
			bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
			bean.setTargeturl(rs.getString("TARGETURL"));
			bean.setRemark(rs.getString("REMARK"));
			bean.setFname(rs.getString("FNAME"));
			bean.setFpath(rs.getString("FPATH"));
			bean.setFsize(rs.getLong("FSIZE"));
			bean.setFtype(rs.getString("FTYPE"));
			bean.setFullpath(rs.getString("FULLPATH"));
			bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
			return bean;
		}
	};
	
	private RowMapper<ClientBannerInfo> rowMapperEx1 = new RowMapper<ClientBannerInfo>() {
		@Override
		public ClientBannerInfo mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ClientBannerInfo bean = new ClientBannerInfo();
			bean.setTargeturl(rs.getString("TARGETURL"));
			bean.setFullpath(rs.getString("FULLPATH"));		
			return bean;
		}
	};

	public void save(TClientBanner entity) {
		super.save(INSERT_SQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TClientBanner entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	public void update(TClientBanner entity) {
		super.update(UPDATE_SQL, entity);
	}

	public void delete(TClientBanner entity) {
		super.delete(DELETE_SQL, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	public TClientBanner query(TClientBanner entity) {
		return super.query(SELECT_SQL, entity);
	}

	public TClientBanner query(Serializable id) {
		return super.query(SELECT_SQL, id);
	}

	public List<TClientBanner> queryForList(TClientBanner entity) {
		return super.queryForList(SELECT_SQL, entity);
	}

	public List<TClientBanner> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	@Override
	public QueryContext<TClientBanner> queryListForPagination(
			QueryContext<TClientBanner> qContext) {

		return null;
	}

	public TClientBanner mapRow(ResultSet rs, int rowNum) throws SQLException {
		TClientBanner bean = new TClientBanner();
		bean.setBid(rs.getString("BID"));
		bean.setBtype(ClientType.enumOf(rs.getInt("BTYPE")));
		bean.setBname(rs.getString("BNAME"));
		bean.setSortimgid(rs.getString("SORTIMGID"));
		bean.setThumedid(rs.getString("THUMEDID"));
		bean.setOrderno(rs.getInt("ORDERNO"));
		bean.setCreater(rs.getString("CREATER"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setUpdater(rs.getString("UPDATER"));
		bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		bean.setTargeturl(rs.getString("TARGETURL"));
		bean.setRemark(rs.getString("REMARK"));
		return bean;
	}

	protected String dynamicJoinSqlWithEntity(TClientBanner bean,StringBuilder sql) {
		if (bean == null || sql == null || sql.length() <= 0) {
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "btype", "BTYPE",bean.getBtype());
		return sql.toString();
	}

	@SuppressWarnings("deprecation")
	public int getMaxOrderno(ClientType osType) {
		if (osType != null) {
			return super.getJdbcTemplate().queryForInt(GETMAXORDERNO_SQL, osType.getVal());
		} else {
			return -1;
		}
	}

	public List<ClientBannerInfo> queryClientBannerInfoList(
			Map<String, Object> args) {
		if (args == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);

		sql.append(" WHERE 1 = 1 ");
		Object btype = args.get("btype");
		List<Object> list = new ArrayList<Object>();
		if (btype != null) {
			addStandardSqlWithParameter(sql, "BANNER.BTYPE", (int) btype, list);
		}
		sql.append(" ORDER BY BANNER.ORDERNO ASC");
		log.debug("The Sql Str Is : " + sql.toString());
		return getJdbcTemplate().query(sql.toString(), list.toArray(),
				rowMapperEx);
	}
	
	public List<ClientBannerInfo>  queryBannerLittleInfoList(Integer btype)
	{
		if(btype==null)
		{
			return null;
		}
		String queryBannerLittleInfoSql=" SELECT BANNER.TARGETURL ,IMAGES.FULLPATH FROM T_CLIENT_BANNER BANNER LEFT JOIN T_UPLOAD_IMAGES IMAGES ON BANNER.SORTIMGID=IMAGES.ID WHERE BTYPE="+btype+" AND TARGETURL !='' AND FULLPATH !='' ORDER BY BANNER.ORDERNO ASC ";
		log.debug("The Sql Str Is : " + queryBannerLittleInfoSql);
		return getJdbcTemplate().query(queryBannerLittleInfoSql, rowMapperEx1);
	}
}
