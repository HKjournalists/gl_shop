package com.glshop.platform.api.syscfg.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统货物属性信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class ProductPropInfoModel extends ResultItem implements Cloneable {

	public String mPropId;
	public String mPropCode;

	public String mTypeCode;
	public String mCategoryCode;
	public String mSubCategoryCode;

	public float mRealSize;
	public float mDefaultSize;
	public float mMaxSize;
	public float mMinSize;

	@Override
	public Object clone() {
		ProductPropInfoModel o = null;
		try {
			o = (ProductPropInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
