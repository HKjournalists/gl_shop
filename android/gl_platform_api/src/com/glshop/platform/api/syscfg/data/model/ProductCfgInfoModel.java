package com.glshop.platform.api.syscfg.data.model;

import java.util.List;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统货物信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class ProductCfgInfoModel extends ResultItem {

	public String mTypeCode;
	public String mTypeName;
	public String mTypeOrder;

	public String mCategoryId;
	public String mCategoryCode;
	public String mCategoryName;
	public String mCategoryOrder;

	public String mSubCategoryId;
	public String mSubCategoryCode;
	public String mSubCategoryName;
	public String mSubCategoryOrder;

	public float mMaxSize;
	public float mMinSize;

	public List<ProductPropInfoModel> mPropList;

	public ProductCfgInfoModel parent;
	public List<ProductCfgInfoModel> childList;

	public boolean isSelectedForUI;
	public boolean isSelectedForDB;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ProductCfgInfoModel[");
		sb.append("mTypeCode=" + mTypeCode);
		sb.append(", mTypeName=" + mTypeName);
		sb.append(", mCategoryCode=" + mCategoryCode);
		sb.append(", mCategoryName=" + mCategoryName);
		sb.append(", mCategoryOrder=" + mCategoryOrder);
		sb.append(", mSubCategoryCode=" + mSubCategoryCode);
		sb.append(", mSubCategoryName=" + mSubCategoryName);
		sb.append(", mSubCategoryOrder=" + mSubCategoryOrder);
		sb.append(", mMaxSize=" + mMaxSize);
		sb.append(", mMinSize=" + mMinSize);
		sb.append("]");
		return sb.toString();
	}

}
