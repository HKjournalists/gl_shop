package com.appabc.common.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : base service
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月22日 下午7:08:14
 */

public interface IBaseService<T extends BaseBean> {
	
	/**  
	 * add (增加一个实体到数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void add(T entity);
	
	/**  
	 * modify (修改后更新一个实体到数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void modify(T entity);
	
	/**  
	 * delete (删除一个实体到数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void delete(T entity);
	
	/**  
	 * delete (根据ID删除一个实体到数据库中)  
	 * @param id  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	void delete(Serializable id);

	/**  
	 * query (根据实体部分信息查询一个实体到数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T query(T entity);
	
	/**  
	 * query (根据ID信息查询一个实体到数据库中)  
	 * @param id  
	 * @author Bill huang 
	 * @return T  
	 * @exception   
	 * @since  1.0.0  
	 */
	T query(Serializable id);
	
	/**  
	 * queryForList (根据实体信息查询实体列表从数据库中)  
	 * @param entity  
	 * @author Bill huang 
	 * @return List<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	List<T> queryForList(T entity);

	/**  
	 * queryForList (根据过滤条件查询实体列表从数据库中)  
	 * @param args  
	 * @author Bill huang 
	 * @return List<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	List<T> queryForList(Map<String, ?> args);
	
	/**  
	 * queryListForPagination (根据查询对象查询实体列表从数据库中)  
	 * @param qContext  
	 * @author Bill huang 
	 * @return QueryContext<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	QueryContext<T> queryListForPagination(QueryContext<T> qContext);

}
