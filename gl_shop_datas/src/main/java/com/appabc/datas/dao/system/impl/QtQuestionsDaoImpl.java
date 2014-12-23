/**
 *
 */
package com.appabc.datas.dao.system.impl;

import com.appabc.bean.pvo.TQtQuestions;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IQtQuestionsDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 常见问题
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午5:01:17
 */
@Repository
public class QtQuestionsDaoImpl extends BaseJdbcDao<TQtQuestions> implements IQtQuestionsDao {

	private static final String INSERTSQL = " insert into T_QT_QUESTIONS (QID, QUETSION, ANSWERS, TYPE, UPDATER, UPDATETIME ) values (:qid, :quetsion, :answers, :type, :updater, :updatetime ) ";
	private static final String UPDATESQL = " update T_QT_QUESTIONS set QUETSION = :quetsion, ANSWERS = :answers, TYPE = :type, UPDATER = :updater, UPDATETIME = :updatetime where QID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_QT_QUESTIONS WHERE QID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_QT_QUESTIONS WHERE QID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_QT_QUESTIONS WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TQtQuestions entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TQtQuestions entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TQtQuestions entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TQtQuestions entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TQtQuestions query(TQtQuestions entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TQtQuestions query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TQtQuestions> queryForList(TQtQuestions entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TQtQuestions> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TQtQuestions> queryListForPagination(
			QueryContext<TQtQuestions> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TQtQuestions mapRow(ResultSet rs, int rowNum) throws SQLException {
		TQtQuestions t = new TQtQuestions();

		t.setId(rs.getString("QID"));
		t.setAnswers(rs.getString("ANSWERS"));
		t.setQuetsion(rs.getString("QUETSION"));
		t.setType(rs.getString("TYPE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TQtQuestions bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "QID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "answers", "ANSWERS", bean.getAnswers());
		this.addNameParamerSqlWithProperty(sql, "quetsion", "QUETSION", bean.getQuetsion());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", bean.getType());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		return sql.toString();
	}

}
