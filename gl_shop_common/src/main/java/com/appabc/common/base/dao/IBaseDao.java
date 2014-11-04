package com.appabc.common.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : BASE DAO INTERFACE
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 
 * @Create_Date : 2014年8月22日 下午6:13:43
 */

public interface IBaseDao<T extends BaseBean> {

	/**  
	 * save (增加一个实体到数据库中，如果数据库主键是自动增长，保持成功后，会将主键填充到实体中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void save(T entity);
	
	/**  
	 * saveAutoGenerateKey (增加一个实体到数据库中，并返回自动增长的主键信息)  
	 * @param entity  
	 * @author Bill huang 
	 * @return KeyHolder  
	 * @exception   
	 * @since  1.0.0  
	 */
	KeyHolder saveAutoGenerateKey(T entity);

	/**  
	 * update (更新一个实体到数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void update(T entity);

	/**  
	 * delete (删除一个实体从数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void delete(T entity);
	
	/**  
	 * delete (通过id删除一个实体从数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void delete(Serializable id);

	/**  
	 * query (查询一个实体从数据库中) 
	 * @param entity  
	 * @author Bill huang 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T query(T entity);
	
	/**  
	 * query (通过ID查询一个实体从数据库中) 
	 * @param entity  
	 * @author Bill huang 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T query(Serializable id);

	/**  
	 * queryForList (通过实体信息查询一个实体集合从数据库中) 
	 * @param entity  
	 * @author Bill huang 
	 * @return List<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	List<T> queryForList(T entity);

	/**  
	 * queryForList (通过参数查询一个实体集合从数据库中) 
	 * @param entity  
	 * @author Bill huang 
	 * @return List<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	List<T> queryForList(Map<String, ?> args);

	/**  
	 * queryListForPagination (通过查询对象查询实体集合从数据库中，经过分页信息) 
	 * @param qContext  
	 * @author Bill huang 
	 * @return QueryContext<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	QueryContext<T> queryListForPagination(QueryContext<T> qContext);

}
