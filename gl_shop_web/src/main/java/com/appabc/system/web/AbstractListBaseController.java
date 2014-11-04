package com.appabc.system.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : zouxifeng
 * @version : 1.0 Create Date : Oct 24, 2014 1:59:25 PM
 */
public class AbstractListBaseController {

	public final static int DEFAULT_SHOW_PAGE = 10;
	public final static int DEFAULT_PAGE_SIZE = 20;
	public final static String NAV_PAGING_ATTR_NAME = "paging";
	public final static String PAGE_MORE_TEXT = "...";
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected int calculateStartRow(int page) {
		return this.calculatePageCount(page, DEFAULT_PAGE_SIZE);
	}
	
	protected int calculateStartRow(int page, int pageSize) {
		return (page - 1) * pageSize;
	}
	
	protected int calculatePageCount(int rowCount) {
		return this.calculatePageCount(rowCount, DEFAULT_PAGE_SIZE);
	}
	
	protected int calculatePageCount(int rowCount, int pageSize) {
		return (rowCount + 1) / pageSize + 1;
	}
	
	protected void calculatePagingData(ModelMap model, String urlBase,
			int currentPage, int rowCount) {
		calculatePagingData(model, urlBase, currentPage, rowCount, DEFAULT_PAGE_SIZE);
	}

	protected void calculatePagingData(ModelMap model, String urlBase,
			int currentPage, int rowCount, int pageSize) {
		int pageCount = this.calculatePageCount(rowCount, pageSize);
		List<ListPage> paging = new ArrayList<ListPage>();
		model.addAttribute(NAV_PAGING_ATTR_NAME, paging);
		if (pageCount <= 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Page count [{}] is less than 1, no paging bar will be generated.", pageCount);
			}
			return;
		}

		int showPage = DEFAULT_SHOW_PAGE;
		String firstPage = "首页";
		String lastPage = "末页";
		ListPage page = new ListPage();
		page.setActive(false);
		page.setDisabled(currentPage == 1);
		page.setUrl(urlBase.replace("{}", String.valueOf(1)));
		page.setText(firstPage);
		paging.add(page);

		int currentPageGroup = (currentPage - 1) / showPage + 1;
		int basePage = (currentPageGroup - 1) * showPage + 1;
		if (currentPageGroup >= 1) {
			page = new ListPage();
			page.setActive(false);
			page.setUrl(urlBase.replace("{}", String.valueOf(basePage - showPage)));
			page.setText(PAGE_MORE_TEXT);
			paging.add(page);
		}

		for (int i = 0; i < showPage; i++) {
			if ((basePage + i) > pageCount)
				break;

			page = new ListPage();
			page.setActive(currentPage == basePage + i);
			page.setDisabled(false);
			page.setUrl(urlBase.replace("{}", String.valueOf(basePage + i)));
			page.setText(String.valueOf(basePage + i));
			paging.add(page);
		}

		int pageGroups = (pageCount - 1) / showPage + 1;
		if (currentPageGroup < pageGroups) {
			page = new ListPage();
			page.setActive(false);
			page.setUrl(urlBase.replace("{}", String.valueOf(basePage + showPage)));
			page.setText(PAGE_MORE_TEXT);
			paging.add(page);
		}

		page = new ListPage();
		page.setActive(false);
		page.setDisabled(currentPage == pageCount);
		page.setUrl(urlBase.replace("{}", String.valueOf(pageCount)));
		page.setText(lastPage);
		paging.add(page);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Page groups: [{}], current page group: [{}], current page: [{}], base page: [{}]",
					pageGroups, currentPageGroup, currentPage, basePage);
			for (ListPage p : paging) {
				logger.debug(p.toString());
			}
		}
	}
}
