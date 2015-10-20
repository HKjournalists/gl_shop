package com.appabc.datas.dao.contract;


import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月1日 下午6:50:02
 */

public interface IContractInfoDAO extends IBaseDao<TOrderInfo> {
	
	/**
	 * @description 分页查询合同信息
	 * @param qContext
	 * @return QueryContext<ContractInfoBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<ContractInfoBean> queryContractInfoListForPagination(QueryContext<ContractInfoBean> qContext);
	
	/**
	 * @description 根据合同ID和企业ID查询合同信息
	 * @param cid, contractId
	 * @return ContractInfoBean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	ContractInfoBean queryContractInfoWithId(String cid,String contractId);
	
	/**
	 * @description 通过仲裁编号获取合同信息
	 * @param aid
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo queryContractWithAID(String aid);
	
	/**
	 * 根据企业和合同的生命周期获得已结束的合同数
	 * @param cid 企业ID，必填
	 * @param lifecycles 合同生命周期(多个状态用逗号间隔) 非必填
	 * @param status 合同大状态
	 * @return
	 */
	public int countByCid(String cid, String lifecycle, String status);

	/**
	 * 获取买家和卖家共同交易比数（）
	 * @param buyerid 买家企业ID
	 * @param sellerid 卖家企业ID
	 * @return
	 */
	int countByBuyerAndSeller(String buyerid, String sellerid);

	/**
	 * 获取询单被撮合的次数
	 * @param fid
	 * @return
	 */
	int getMatchingNumByFid(String fid);

	/**
	 * @description 分页查询我的合同信息
	 * @param qContext
	 * @return QueryContext<ContractInfoBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<ContractInfoBean> queryContractListOfMineForPagination(QueryContext<ContractInfoBean> qContext);
	
	/**
	 * @description : 分页查询我的合同详情列表,给后台CMS提供的接口.
	 * 	参数：cid :查询属于当前cid的合同;type:分为当前交易状态和历史交易状态.
	 * @param qContext
	 * @return QueryContext<ContractInfoBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	@Deprecated
	QueryContext<ContractInfoBean> queryContractListOfMineForPaginationToWebCms(QueryContext<ContractInfoBean> qContext);
	
	
	/**
	 * 分页查询我的合同详情列表,给后台CMS提供的接口
	 * @param qContext
	 * @return
	 */
	QueryContext<TOrderInfo> queryContractListForPaginationOfUserToWebCms(QueryContext<TOrderInfo> qContext);
	
	public int queryCount(TOrderInfo entity);
	
	public int queryCountOfFinished();
}
