/**
 *
 */
package com.appabc.datas.service.data.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.bo.DataUserSingleCount;
import com.appabc.bean.pvo.TDataAllUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.dao.data.IDataAllUserDao;
import com.appabc.datas.dao.data.IDataUserSingleCountDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.data.IDataAllUserService;
import com.appabc.tools.mail.javamail.MailMessageSender;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月17日 下午3:03:24
 */
@Service
public class DataAllUserServiceImpl extends BaseService<TDataAllUser> implements IDataAllUserService {
	
	@Autowired
	private IDataAllUserDao dataAllUserDao;
	@Autowired
	private IDataUserSingleCountDao dataUserSingleCountDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TDataAllUser entity) {
		dataAllUserDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TDataAllUser entity) {
		dataAllUserDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TDataAllUser entity) {
		dataAllUserDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		dataAllUserDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TDataAllUser query(TDataAllUser entity) {
		return dataAllUserDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TDataAllUser query(Serializable id) {
		return dataAllUserDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TDataAllUser> queryForList(TDataAllUser entity) {
		return dataAllUserDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TDataAllUser> queryForList(Map<String, ?> args) {
		return dataAllUserDao.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TDataAllUser> queryListForPagination(
			QueryContext<TDataAllUser> qContext) {
		return dataAllUserDao.queryListForPagination(qContext);
	}
	
	public QueryContext<DataUserSingleCount> queryListForPaginationOfDataUserSingleCount(
			QueryContext<DataUserSingleCount> qContext) {
		return dataUserSingleCountDao.queryListForPaginationOfDataUserSingleCount(qContext);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.data.IDataAllUserService#queryAllUserSingleCountForList()
	 */
	@Override
	public List<DataUserSingleCount> queryAllUserSingleCountForList() {
		return dataUserSingleCountDao.queryAllForList();
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.data.IDataAllUserService#jobAutoSendAllUserDataWithEmail()  
	 */
	@Override
	public void jobAutoSendAllUserDataWithEmail() throws ServiceException {
		QueryContext<TDataAllUser> qContext = new QueryContext<TDataAllUser>();
		Date now = DateUtil.getNowDate();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.MONTH, -1);
		Date startTime = c.getTime();
		
        qContext.addParameter("startTime", startTime);
        qContext.addParameter("endTime", now);
        qContext.getPage().setPageIndex(-1);
        //qContext.setBeanParameter(new TDataAllUser());
        qContext = this.queryListForPagination(qContext);
        List<TDataAllUser> datas = qContext.getQueryResult().getResult();
        MailMessageSender mms = new MailMessageSender();
        String title = DateUtil.DateToStr(now, "yyyyMMdd")+" 长江电商用户整体数据报表";
        mms.setSubject(title);
        StringBuilder html = new StringBuilder();
        StringBuilder threadstyle = new StringBuilder();
        threadstyle.append("class='theadstyle'");
        html.append("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'></head><body>");
        html.append("<style type='text/css'>");
        html.append(" .theadstyle {border-bottom:1px solid #000000;border-right:1px solid #000000;text-align:center;} ");
        html.append(" .headstyle {color:#FF0000;border-bottom:1px solid #000000;border-right:1px solid #000000;text-align:left;} ");
        html.append(" .bodystyle {border-bottom:1px solid #000000;border-right:1px solid #000000;text-align:left;} ");
        html.append(" .tablestyle {border-left:1px solid #000000;border-top:1px solid #000000;} ");
        html.append("</style>");
        html.append("<center><h1>"+title+"</h1></center><br/>");
        html.append("<table width='96%' cellspacing='0' cellpadding='0' class='tablestyle'>");
        html.append("<thread><tr><td "+threadstyle.toString()+"> 日期</td>");
        html.append("<td "+threadstyle.toString()+"> 注册用户数</td>");
        html.append("<td "+threadstyle.toString()+"> 认证用户数</td>");
        html.append("<td "+threadstyle.toString()+"> 保证金缴纳用户数</td>");
        html.append("<td "+threadstyle.toString()+"> 供求信息数</td>");
        html.append("<td "+threadstyle.toString()+"> 出售信息数</td>");
        html.append("<td "+threadstyle.toString()+"> 进行中的合同数</td>");
        html.append("<td "+threadstyle.toString()+"> 已完成合同数</td>");
        html.append("<td "+threadstyle.toString()+"> 当天登录人数</td></tr></thread>");
        html.append("<tbody>");
        StringBuilder style = new StringBuilder();
        for(int i = 0 ; i < datas.size() ; i ++){
        	TDataAllUser tdau = datas.get(i);
        	style.delete(0, style.length());
        	if(i==0){
        		style.append("class='headstyle'");
        	}else{        		
        		style.append("class='bodystyle'");
        	}
        	html.append("<tr><td "+style.toString()+"> "+ DateUtil.DateToStr(tdau.getCreateDate(), "yyyy/MM/dd") +"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getAllUserNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getAuthUserNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getBailUserNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getSellGoodsNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getBuyGoodsNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getContractIngNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getContractEndNum()+"</td>");
        	html.append("<td "+style.toString()+"> "+tdau.getLoginUserNum()+"</td></tr>");
        }
        html.append("</tbody>");
        html.append("</table>");
        html.append("</body></html>");
        mms.setText(html.toString()).send();
        log.info(html.toString());
	}

}
