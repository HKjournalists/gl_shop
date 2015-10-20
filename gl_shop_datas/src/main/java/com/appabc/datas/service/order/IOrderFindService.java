/**
 *
 */
package com.appabc.datas.service.order;

import java.util.List;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.OrderFindInsteadBean;
import com.appabc.bean.bo.OrderFindQueryParamsBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.pvo.TOrderAddress;
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
	 * 根据状态查询父询单信息
	 * @param ose
	 * @param oose
	 * @throws null
	 * @return List<TOrderFind>
	 */
	public List<TOrderFind> getParentOrderFindByStatusAndOverallStatus(OrderStatusEnum ose,OrderOverallStatusEnum oose);
	
	/**
	 * 更新当前ID下所有子询单的状态为失效
	 * @param parentId
	 * @param operator
	 * @throws ServiceException
	 */
	public void updateChildOrderFindCloseInvalidByParentId(String parentId,String operator) throws ServiceException;
		/**
	 * 发布询单
	 * @param ofBean 询单基本信息
	 * @param opiBean 商品基本信息
	 * @param ppcList 商品属性值
	 * @param oa 卸货地址
	 * @param oa 原始询单ID
	 * @throws ServiceException
	 */
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean, List<ProductPropertyContentBean> ppcList, TOrderAddress oa, String originalFid) throws ServiceException;
	
	/**
	 * 撮合合同时保存询单信息
	 * @param ofaBean
	 * @return
	 * @throws ServiceException
	 */
	public String contractMatchingOfOrderFindSave(OrderFindAllBean ofaBean) throws ServiceException;
	
	/**
	 * 根据询单ID获取详情信息，包括商品信息和卸货地址(如果与询单发布者CID不同，输出的信息可能会加密。requestCid为空将不做加密处理)
	 * @param fid
	 * @param requestCid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid, String requestCid);
	
	/**
	 * 询单取消
	 * @param fid
	 * @param userid 操作人ID
	 * @return
	 * @throws ServiceException 
	 */
	public String cancel(String fid, String userid) throws ServiceException;
	
	/**
	 * 更新询单
	 * @param ofBean
	 * @param opiBean
	 * @param ppcList 商品属性值
	 * @param addressid 卸货地址ID
	 * @throws ServiceException 
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderProductInfo opiBean, List<ProductPropertyContentBean> ppcList, String addressid) throws ServiceException;
	
	/**
	 * 更新询单
	 * @param ofBean
	 * @param opiBean
	 * @param ppcList 商品属性值
	 * @param addressid 卸货地址ID
	 * @throws ServiceException 
	 */
	public void updateOrderAllInfo(OrderFindAllBean orderAllInfo) throws ServiceException;
	
	/**
	 * 获取企业询单总数
	 * @param cid
	 * @return
	 */
	public int getTotalByCid(String cid);
	
	/**
	 * 查询我的供求信息列表
	 * @param qContext
	 * @param cid 企业ID
	 * @return
	 * @throws ServiceException
	 */
	public QueryContext<TOrderFind> queryMyListForPagination(QueryContext<TOrderFind> qContext, String cid) throws ServiceException;
	
	/**
	 * 询单自动匹配列表（分页查询）
	 * @param qContext
	 * @param fid 询单ID
	 * @param isApply 是否查询交易意向申请列表
	 * @param isOrderFid 是否按询单商品对应关系匹配（货物对应货物，买对应卖）
	 * @param isIdentity 是否已企业身份匹配（对应关系待定）
	 * @return
	 * @throws ServiceException
	 */
	public QueryContext<MatchingBean> queryMatchingObjectByFidForPagination(QueryContext<MatchingBean> qContext, String fid,  boolean isApply, boolean isOrderFid, boolean isIdentity) throws ServiceException ;
	
	/**
	 * 商品标题拼组
	 * @param oai
	 * @return
	 */
	public String spellOrderFindTitle(OrderAllInfor oai);
	
	/**
	 * 复制一份询单（包括询单基本信息、商品信息，卸货地址）
	 * @param fid
	 * @return newFid 
	 */
	@Deprecated
	public String copyOrderAllInforByFid(String fid);
	
	/**
	 * 2个询单建立父子关系
	 * @param parentId 询单父ID
	 * @param childId 询单子ID
	 * @throws ServiceException 
	 */
	public void buildRelationshipsOfOrderFind(String parentId, String childId) throws ServiceException;
	
	/**
	 * 建立合同与询单的关系，并将询单失效
	 * @param contractId
	 * @param fid
	 * @throws ServiceException 
	 */
	public void buildRelationshipsOfContractAndOrderFind(String contractId, String fid) throws ServiceException;
	
	/**
	 * 根据合同ID，将生成合同的询单进行回滚
	 * @param contractId
	 */
	public void rollbackOrderFindByContractid(String contractId);
	
	/**
	 * 找买找卖分页查询
	 * @param qContext
	 * @param ofqParam 查询参数
	 * @param requestCid 商户端请求者的企业ID，未登录用户为Null
	 * @return
	 */
	public QueryContext<TOrderFind> queryOrderListForPagination(
			QueryContext<TOrderFind> qContext, OrderFindQueryParamsBean ofqParam, String requestCid);

	/**
	 * 客服代发供求信息
	 * @param ofBean 询单基本信息
	 * @param opiBean 商品基本信息
	 * @param ppcList 商品属性值
	 * @param addressId 卸货地址ID，对应企业的卸货地址表(T_COMPANY_ADDRESS)ID
	 * @throws ServiceException
	 */
	public void orderPublishSubstitute(TOrderFind ofBean, TOrderProductInfo opiBean,
			List<ProductPropertyContentBean> ppcList, String addressId)
			throws ServiceException;
	
	/**
	 * 把已失效的询单做删除标记
	 * @param fid 询单ID
	 * @param userid 操作人
	 * @throws ServiceException
	 */
	public void delMark(String fid, String userid) throws ServiceException;

	/**
	 * 新发布的询单列表
	 * @return
	 */
	public List<TOrderFind> queryNewListForTask();

	/**
	 * 后台任务列表中无效的询单
	 * @return
	 */
	public List<TOrderFind> queryInvalidListForTask();
	
	void jobAutoTimeoutOrderFindWithSystem(TOrderFind bean,String cid) throws ServiceException;
	
	/**
	 * 根据合同ID查询与之关联的父询单ID
	 * @param contractId
	 * @return
	 * @throws ServiceException 
	 */
	public String getFidByContractId(String contractId) throws ServiceException;

	/**
	 * 撮合次数增加1
	 * @param fid 询单ID
	 * @return
	 */
	int addMatchingNum(String fid);

	/**
	 * 撮合合同，保存询单草稿
	 * @param ofaBean
	 * @return
	 * @throws ServiceException
	 */
	String contractMatchingOfOrderFindSaveDraft(OrderFindAllBean ofaBean)
			throws ServiceException;
	/**
	 * 根据子询单ID获取其父询单ID
	 * @param childFid
	 * @return
	 * @throws ServiceException
	 */
	String getParentFidByChildFid(String childFid) throws ServiceException;
	
	/**
	 * (批量取消所有草稿) 根据询单父ID，把该询单的所有【合同草稿询单】设置为失效
	 * @param parentFid
	 * @throws ServiceException
	 */
	void cancelAllDraftsByParentid(String parentFid) throws ServiceException;
	
	/**
	 * (批量恢复所有草稿) 根据询单父ID，把该询单的所有【合同草稿询单】设置为有效
	 * @param parentFid
	 * @throws ServiceException
	 */
	void recoverAllDraftsByParentid(String parentFid) throws ServiceException;

	/**
	 * 失效单个草稿询单
	 * @param fid
	 * @throws ServiceException
	 */
	void cancelDraftsById(String fid) throws ServiceException;

	/**
	 * 恢复单个草稿询单
	 * @param fid
	 * @throws ServiceException
	 */
	void recoverDraftsById(String fid) throws ServiceException;
	
	/**
	 * 分页查询已代发供求列表
	 * @param qContext
	 * @return
	 */
	QueryContext<OrderFindInsteadBean> queryParentOrderFindOfInsteadListForPagination(
			QueryContext<OrderFindInsteadBean> qContext);

	public int queryCount(TOrderFind entity);
	
}
