/**
 *
 */
package com.appabc.datas.service.order;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 询单SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 下午4:07:39
 */
public interface IOrderFindService extends IBaseService<TOrderFind>{
	
	/**
	 * 更新当前ID下所有子询单的状态为失效
	 * @param parentId
	 * @param operator
	 * @throws ServiceException
	 */
	public void updateChildOrderFindCloseInvalidByParentId(String parentId,String operator) throws ServiceException;
	
	/**
	 * 发布询单
	 * @param ofBean
	 * @param opiBean
	 * @param addressid 企业卸货地址ID
	 */
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid) throws ServiceException ;
	
	/**
	 * 根据询单ID获取详情信息，包括商品信息和卸货地址(如果与询单发布者CID不同，输出的信息可能会加密。requestCid为空将不做加密处理)
	 * @param fid
	 * @param requestCid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid, String requestCid);
	
	/**
	 * @param fid
	 * @param userid 操作人ID
	 * @return
	 */
	public String cancel(String fid, String userid);
	
	/**
	 * 更新询单
	 * @param ofBean
	 * @param opiBean
	 * @param addressid 卸货地址ID
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid);
	
	/**
	 * 获取企业询单总数
	 * @param cid
	 * @return
	 */
	public int getTotalByCid(String cid);
	
	/**
	 * 查询我的供求信息列表
	 * @param qContext
	 * @return
	 */
	public QueryContext<TOrderFind> queryMyListForPagination(QueryContext<TOrderFind> qContext);
	
	/**
	 * 询单自动匹配列表（分页查询）
	 * @param fid
	 * @return
	 */
	public QueryContext<MatchingBean> queryMatchingObjectByCidForPagination(QueryContext<MatchingBean> qContext, String fid) throws ServiceException ;
	
	/**
	 * 商品标题拼组
	 * @param oai
	 * @return
	 */
	public String spellOrderFindTitle(OrderAllInfor oai);

}
