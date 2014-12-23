package com.glshop.net.logic.upgrade;

import com.glshop.platform.api.setting.data.model.AppInfoModel;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 升级模块业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public interface IUpgradeLogic extends ILogic {

	/**
	 * 获取最新升级信息
	 */
	void getUpgradeInfo(AppInfoModel info);

	/**
	 * 下载手机包
	 * @param info
	 */
	void downloadApp(UpgradeInfoModel info);

	/**
	 * 取消当前下载
	 */
	void cancelDownload();

}
