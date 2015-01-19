package com.glshop.net.logic.db.dao.syscfg;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;

/**
 * @Description : 系统同步管理接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public interface ISyscfgDao {

	/**
	 * 查询对应Type的Timestamp信息
	 * @param context
	 * @param type
	 * @return
	 */
	Map<String, String> querySyscfgTimestamp(Context context, String type);

	/**
	 * 查询对应Type List中的Timestamp信息
	 * @param context
	 * @param type
	 * @return
	 */
	Map<String, String> querySyscfgTimestamp(Context context, List<String> typeList);

	/**
	 * 添加系统参数类型
	 * @param context
	 * @param info
	 * @return
	 */
	long insertSyscfgType(Context context, String type, String timestamp);

	/**
	 * 批量添加系统参数类型
	 * @param context
	 * @param info
	 * @return
	 */
	boolean bulkInsertSyscfgType(Context context, Map<String, String> typeList);

	/**
	 * 更新系统参数类型
	 * @param context
	 * @param timestampList
	 * @return
	 */
	boolean updateSyscfgType(Context context, String type, String timestamp);

	/**
	 * 批量更新系统参数类型
	 * @param context
	 * @param timestampList
	 * @return
	 */
	boolean bulkUpdateSyscfgType(Context context, Map<String, String> timestampList);

	/**
	 * 删除系统参数类型
	 * @param context
	 * @param type
	 * @return
	 */
	boolean deleteSyscfgType(Context context, List<String> typeList);

	/**
	 * 查询所有货物信息
	 * @param context
	 * @return
	 */
	List<ProductCfgInfoModel> queryProductInfo(Context context);

	/**
	 * 插入货物信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertProductInfo(Context context, List<ProductCfgInfoModel> infoList);

	/**
	 * 删除所有货物信息
	 * @param context
	 * @return
	 */
	boolean deleteProductInfo(Context context);

	/**
	 * 查询所有港口信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> queryPortInfo(Context context);

	/**
	 * 插入港口信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertPortInfo(Context context, List<AreaInfoModel> infoList);

	/**
	 * 删除所有港口信息
	 * @param context
	 * @return
	 */
	boolean deletePortInfo(Context context);

	/**
	 * 查询目前支持的省份列表信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> querySupportProvinceList(Context context);

	/**
	 * 插入支持的省份列表信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertSupportProvinceInfo(Context context, List<AreaInfoModel> infoList);

	/**
	 * 删除所有支持的省份列表信息
	 * @param context
	 * @return
	 */
	boolean deleteSupportProvinceInfo(Context context);

	/**
	 * 查询系统参数数据
	 * @param context
	 * @return
	 */
	List<SysParamInfoModel> querySysParamInfo(Context context);

	/**
	 * 插入系统参数信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertSysParamInfo(Context context, List<SysParamInfoModel> infoList);

	/**
	 * 删除所系统参数信息
	 * @param context
	 * @return
	 */
	boolean deleteSysParamInfo(Context context);

	/**
	 * 插入地域配置信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertAreaCfgInfo(Context context, List<AreaInfoModel> infoList);

	/**
	 * 查询所有地域配置信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> queryAreaCfgInfo(Context context);

	/**
	 * 删除所有地域配置信息
	 * @param context
	 * @return
	 */
	boolean deleteAllAreaCfgInfo(Context context);

	/**
	 * 插入地域列表信息
	 * @param context
	 * @param infoList
	 * @return
	 */
	boolean insertAreaInfo(Context context, List<AreaInfoModel> infoList);

	/**
	 * 查询所有地域信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> queryAllAreaInfo(Context context);

	/**
	 * 查询地域父列表信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> queryParentAreaInfo(Context context, String code);

	/**
	 * 查询地域子列表信息
	 * @param context
	 * @return
	 */
	List<AreaInfoModel> queryChildAreaInfo(Context context, String code);

	/**
	 * 删除所有地域信息
	 * @param context
	 * @return
	 */
	boolean deleteAllAreaInfo(Context context);

}
