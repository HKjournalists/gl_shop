package com.glshop.net.logic.profile;

import java.util.List;

import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 用户资料接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午5:14:40
 */
public interface IProfileLogic extends ILogic {

	/**
	 * 获取卸货地址列表
	 */
	void getAddrList(String companyId);

	/**
	 * 新增卸货地址
	 * @param info
	 */
	void addAddrInfo(AddrInfoModel info);

	/**
	 * 更新卸货地址
	 * @param info
	 */
	void updateAddrInfo(AddrInfoModel info);

	/**
	 * 删除卸货地址
	 * @param id
	 */
	void deleteAddrInfo(String id);

	/**
	 * 设置默认卸货地址
	 * @param id
	 */
	void setDefaultAddrInfo(String id);

	/**
	 * 获取联系人列表
	 */
	void getContactList(String companyId);

	/**
	 * 更新联系人信息
	 * @param list
	 */
	void updateContactInfo(String companyId, List<ContactInfoModel> list);

	/**
	 * 更新企业介绍信息
	 */
	void updateCompanyIntroInfo(CompanyIntroInfoModel info);

	/**
	 * 获取我的个人资料信息
	 */
	void getMyProfileInfo(String companyId);

	/**
	 * 获取对方的个人资料信息
	 */
	void getOtherProfileInfo(String companyId);

	/**
	 * 上传认证信息
	 */
	void submitAuthInfo(AuthInfoModel info);

	// 获取我的资料基本信息

	// 买/卖企业详情

	// 首次提交 & 重新认证信息

	// 查看认证详情

	// 获取企业联系人列表

	// 编辑企业联系人

	// 获取卸货地址列表

	// 编辑卸货地址

	// 编辑企业介绍
}
