/**
 *
 */
package com.appabc.bean.bo;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 用与返回图片信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月5日 上午9:49:10
 */
public class ViewImgsBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2199010730819046172L;
	/**
	 * 原始图片地址
	 */
	private String url;
	/**
	 * 缩略图，（小100*100）
	 */
	private String thumbnailSmall;
   
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnailSmall() {
		return thumbnailSmall;
	}
	public void setThumbnailSmall(String thumbnailSmall) {
		this.thumbnailSmall = thumbnailSmall;
	}
}
