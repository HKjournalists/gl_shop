package com.appabc.datas.service.contract;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月27日 上午11:24:31
 */

public interface IContractBaseService<T extends BaseBean> extends IBaseService<T> {
	
	/**
	 * @Description : 获取配置消息根据CODE
	 * @param code
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	String getMessage(String code);
	
	/**
	 * @Description : 获取配置消息根据CODE和local
	 * @param code;localTag
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	String getMessage(String code,String localTag);
	
	/**
	 * @Description : 获取表主键,根据业务ID
	 * @param bid;
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	String getKey(String bid);
	
	/**
	 * @Description : 检查企业是否保证金足额
	 * @param cid;
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	boolean checkCashGuarantyEnough(String cid);
	
	/**
	 * @Description : 合同结束后,如果有效期到了,就自动移到合同结束列表.
	 * @param bean;operator;other
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void contractTimeOutMoveToFinishList(TOrderInfo bean,String operator,String...other) throws ServiceException;
	
	/**
	 * @Description : 绑定合同冻结保证金操作
	 * @param oid;buyerId;sellerId;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	double guarantyToGelation(String oid,String buyerId,String sellerId,double totalAmount) throws ServiceException;
	
	/**
	 * @Description : 绑定合同冻结保证金操作
	 * @param oid;cid;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	double guarantyToGelation(String oid,String cid,double totalAmount) throws ServiceException;
	
	/**
	 * @Description : 绑定合同解冻保证金操作
	 * @param oid;cid;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	double guarantyToUngelation(String oid,String cid,double totalAmount) throws ServiceException;
	
	/**
	 * @Description : 绑定合同解冻保证金操作
	 * @param oid;buyerId;sellerId;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	double guarantyToUngelation(String oid,String buyerId,String sellerId,double totalAmount) throws ServiceException;
	
	/**
	 * @Description : 进行合同结算操作[支持正常结算,单方取消结算,仲裁结算,未付款超时结束合同结算]
	 * @param bean;cid;cname;others
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void contractFinalEstimate(TOrderInfo bean,String cid,String cname,String... others) throws ServiceException;
	
	/**
	 * @Description : 发送消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg);
	
	/**
	 * @Description : 发送消息接口,包含系统消息,短消息,xmpp消息
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendAllMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content);
	
	/**
	 * @Description : 发送系统消息和xmpp消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content);
	
	/**
	 * @Description : 仅发送短消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendOnlyShortMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content);
	
	/**
	 * @Description : 仅发送系统消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendOnlySystemMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content);
	
	/**
	 * @Description : 仅发送XMPP消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void sendOnlyXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content);
}
