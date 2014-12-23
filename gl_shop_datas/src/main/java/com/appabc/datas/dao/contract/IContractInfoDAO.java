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
	
	QueryContext<ContractInfoBean> queryContractInfoListForPagination(QueryContext<ContractInfoBean> qContext);
	
	ContractInfoBean queryContractInfoWithId(String cid,String contractId);
	
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

}
