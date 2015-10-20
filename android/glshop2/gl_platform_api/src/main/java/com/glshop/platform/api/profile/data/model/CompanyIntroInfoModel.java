package com.glshop.platform.api.profile.data.model;

import java.util.List;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 企业介绍信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class CompanyIntroInfoModel extends ResultItem implements Cloneable {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 企业介绍
	 */
	public String introduction;

	/**
	 * 企业照片链接地址
	 */
	public List<ImageInfoModel> imgList;

	@Override
	public Object clone() {
		CompanyIntroInfoModel o = null;
		try {
			o = (CompanyIntroInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CompanyIntroInfoModel[");
		sb.append("companyId=" + companyId);
		sb.append(", introduction=" + introduction);
		sb.append(", imgList=" + imgList);
		sb.append("]");
		return sb.toString();
	}

}
