package com.appabc.common.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : Query Result
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午1:39:21
 */

public class QueryResult<T extends BaseBean> {
	/*
	 * pagination result set
	 * data set
	 * */
	private List<T> result = new ArrayList<T>();
	
	/*
	 * total size
	 * */
	private int totalSize = 0;
	
	/**  
	 * result  
	 *  
	 * @return  the result  
	 * @since   1.0.0  
	 */
	
	public List<T> getResult() {
		return result;
	}

	/**  
	 * @param result the result to set  
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	/**  
	 * totalSize  
	 *  
	 * @return  the totalSize  
	 * @since   1.0.0  
	 */
	
	public int getTotalSize() {
		return totalSize;
	}

	/**  
	 * @param totalSize the totalSize to set  
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
