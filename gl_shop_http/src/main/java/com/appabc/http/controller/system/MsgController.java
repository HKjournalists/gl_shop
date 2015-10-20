/**
 *
 */
package com.appabc.http.controller.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.tools.service.system.ISystemMessageService;

/**
 * @Description : 系统消息CONTROLLER
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月15日 上午11:15:20
 */
@Controller
@RequestMapping(value="/msg")
public class MsgController extends BaseController<TSystemMessage> {
	
	@Autowired
	private ISystemMessageService systemMessageService;
	
	/**
	 * 获取消息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList")
	public Object getMsgList(HttpServletRequest request,HttpServletResponse response) {

		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}
		QueryContext<TSystemMessage> qContext = initializeQueryContext(request);
		qContext.addParameter("cid", cid);
		try {
			qContext = systemMessageService.queryListForPagination(qContext);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		int unreadTotalSize = this.systemMessageService.getUnreadMsgCountByCid(cid);
		
		qContext.getQueryResult().addResoutParam("unreadTotalSize", unreadTotalSize);
		return qContext.getQueryResult();
	}
	
	/**
	 * 根据消息类型获取未删除消息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getListByType")
	public Object getMsgListByType(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		String type=request.getParameter("type");
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}
		QueryContext<TSystemMessage> qContext = initializeQueryContext(request);
		if(!StringUtils.isEmpty(type)){
			qContext.addParameter("type", type);
		}
		qContext.addParameter("cid", cid);
		try {
			qContext = systemMessageService.queryListByTypeForPagination(qContext);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		int unreadTotalSize = this.systemMessageService.getUnreadMsgCountByCid(cid);
		
		qContext.getQueryResult().addResoutParam("unreadTotalSize", unreadTotalSize);
		return qContext.getQueryResult();
	}
	
	/**
	 * 获取消息内容
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo")
	public Object getMsgInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String msgid = request.getParameter("msgid");
		if(StringUtils.isEmpty(msgid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "消息ID不能为空");
		}
		
		TSystemMessage msg;
		try {
			msg = this.systemMessageService.query(msgid);
			return buildFilterResultWithBean(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
	}
	
	/**
	 * 设置消息为已读
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/read")
	public Object msgRead(HttpServletRequest request,HttpServletResponse response) {
		
		String msgids = request.getParameter("msgids");
		if(StringUtils.isEmpty(msgids)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "消息ID不能为空");
		}
		String ids[] = msgids.split(",");
		for(String id : ids){
			TSystemMessage msg = this.systemMessageService.query(id);
			msg.setStatus(MsgStatus.STATUS_IS_READ_YES); // 设置已读
			msg.setReadtime(Calendar.getInstance().getTime());
			try {
				this.systemMessageService.modify(msg);
			} catch (Exception e) {
				e.printStackTrace();
				return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
			}
		}
		return buildSuccessResult("设置已读成功", "");
	}
	
	/**
	 * 设置消息为已删除
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public Object msgDelete(HttpServletRequest request,HttpServletResponse response) {
		
		String msgids = request.getParameter("msgids");
		if(StringUtils.isEmpty(msgids)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "消息ID不能为空");
		}
		String ids[] = msgids.split(",");
		for(String id : ids){
			TSystemMessage msg = this.systemMessageService.query(id);
			msg.setStatus(MsgStatus.STATUS_IS_DELETE_YES); // 设置已删除
			msg.setDeletetime(Calendar.getInstance().getTime());
			try {
				this.systemMessageService.modify(msg);
			} catch (Exception e) {
				e.printStackTrace();
				return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
			}
		}
		return buildSuccessResult("设置已删除成功", "");
	}
	
	/**
	 * 获取新消息数
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newTotal",method=RequestMethod.GET)
	public Object getNewTotal(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}
		int unreadTotalSize = this.systemMessageService.getUnreadMsgCountByCid(cid);
		
		Map<String, Integer> map = new  HashMap<String, Integer>();
		map.put("total", unreadTotalSize);
		
		return buildSuccessResult("未读消息数", map);
	}

}
