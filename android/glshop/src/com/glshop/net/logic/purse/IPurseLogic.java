package com.glshop.net.logic.purse;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.platform.api.DataConstants.PayeeStatus;
import com.glshop.platform.api.DataConstants.PurseDealFilterType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.api.purse.data.model.RolloutInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 我的钱包业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:17:12
 */
public interface IPurseLogic extends ILogic {

	/**
	 * 获取钱包基本信息
	 */
	public void getPurseInfo();

	/**
	 * 获取钱包交易流水记录列表
	 * @param account
	 * @param type
	 */
	public void getDealList(PurseType type, PurseDealFilterType filterType, int pageIndex, int pageSize, DataReqType reqType);

	/**
	 * 获取钱包交易流水详情消息
	 * @param account
	 * @param id
	 */
	public void getDealInfo(String id);

	/**
	 * 获取收款人列表
	 */
	public void getPayeeList(String companyId, PayeeStatus status, DataReqType reqType);

	/**
	 * 新增收款人
	 */
	public void addPayeeInfo(PayeeInfoModel info, String smsCode);

	/**
	 * 修改收款人
	 */
	public void updatePayeeInfo(PayeeInfoModel info, String smsCode);

	/**
	 * 删除收款人
	 */
	public void deletePayeeInfo(String payeeId);

	/**
	 * 设置默认收款人
	 */
	public void setDefaultPayeeInfo(String payeeId);

	/**
	 * 提现操作
	 * @param smsCode
	 * @param password
	 */
	public void rollOut(RolloutInfoModel rollInfo, String smsCode, String password);

	/**
	 * 转账货款至保证金
	 * @param money
	 * @param smsCode
	 * @param password
	 */
	public void rollOutToDeposit(String money, String smsCode, String password);

	/**
	 * 钱包或货款账户线下充值
	 * @param contractId
	 */
	public void purseRecharegeOffline(PurseType type);

}
