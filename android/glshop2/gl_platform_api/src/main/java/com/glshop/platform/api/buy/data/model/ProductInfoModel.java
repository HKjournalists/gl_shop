package com.glshop.platform.api.buy.data.model;

import java.io.Serializable;

import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;

/**
 * @Description : 货物规格实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午4:34:09
 */
public class ProductInfoModel implements Serializable, Cloneable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 7975932672464415714L;

	/**
	 * 种类
	 */
	public int type;

	/**
	 * 规格
	 */
	public int specifications;

	/**
	 * 含泥量（%）
	 */
	public ProductPropInfoModel sedimentPercentage;

	/**
	 * 泥块含量（%）
	 */
	public ProductPropInfoModel sedimentBlockPercentage;

	/**
	 * 含水率（%）
	 */
	public ProductPropInfoModel waterPercentage;

	/**
	 * 表观密度（kg/m3）
	 */
	public ProductPropInfoModel appearanceDensity;

	/**
	 * 堆积密度（kg/m3）
	 */
	public ProductPropInfoModel stackingPercentage;

	/**
	 * 压碎值指标（%）
	 */
	public ProductPropInfoModel crunchPercentage;

	/**
	 * 针片状颗粒含量（%）
	 */
	public ProductPropInfoModel needlePlatePercentage;

	/**
	 * 坚固性指标（%）
	 */
	public ProductPropInfoModel sturdinessPercentage;

	@Override
	public Object clone() {
		ProductInfoModel o = null;
		try {
			o = (ProductInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ProductInfoModel[");
		sb.append("type=" + type);
		sb.append(", specifications=" + specifications);
		sb.append(", sedimentPercentage=" + sedimentPercentage);
		sb.append(", sedimentBlockPercentage=" + sedimentBlockPercentage);
		sb.append(", waterPercentage=" + waterPercentage);
		sb.append(", appearanceDensity=" + appearanceDensity);
		sb.append(", stackingPercentage=" + stackingPercentage);
		sb.append(", sturdinessPercentage=" + sturdinessPercentage);
		sb.append("]");
		return sb.toString();
	}

}
