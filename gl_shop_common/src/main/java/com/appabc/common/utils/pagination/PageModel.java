package com.appabc.common.utils.pagination;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:39:20
 */

public class PageModel {
	
	/*
	 * page size
	 * */
	private int pageSize = 10;
	
	/*
	 * total size
	 * */
	private int totalSize = 0;
	
	/*
	 * total page
	 * */
	private int totalPage = 0;
	
	/*
	 * current page number 
	 * */
	private int pageIndex = 1;
	
	public PageModel(){}

	/*
	 * get the start page index
	 * */
	public int getStartPageIndex(){
		return 1;
	}
	
	/*
	 * get previous page index
	 * */
	public int getPrevPageIndex(){
		if(this.pageIndex<=1){
			return 1;
		}
		return pageIndex-1;
	}
	
	/*
	 * get the next page index
	 * */
	public int getNextPageIndex(){
		if(this.pageIndex>=this.totalPage){
			return this.totalPage;
		}
		return pageIndex+1;
	}
	
	/*
	 * get the end page index
	 * */
	public int getEndPageIndex(){
		return this.totalPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		this.totalPage = (int)Math.ceil(Double.valueOf(this.totalSize)/Double.valueOf(this.pageSize));
		/*if(this.pageIndex>=this.totalPage){
			this.pageIndex = this.totalPage;
		}*/
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(getClass().getName()+"@");
		str.append("pageSize="+pageSize+";");
		str.append("totalSize="+totalSize+";");
		str.append("totalPage="+totalPage+";");
		str.append("pageIndex="+pageIndex+";");
		return str.toString();
	}

}
