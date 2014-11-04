package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

/**
 * @Description : XMPP推送结果表
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午4:35:33
 */
public class TPushResult extends BaseBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7978608340472148878L;

	/**
     * 消息类型(通知、透传)
     */
    private Integer msgtype;

    /**
     * 消息标题
     */
    private String msgtitle;

    /**
     * 消息内容
     */
    private String msgcontent;

    /**
     * 推送类型（单个，群休）
     */
    private Integer pushtype;

    /**
     * 推送目标（消息接收者）
     */
    private String pushtarget;

    /**
     * 推送状态（成功、失败）
     */
    private Integer pushstatus;

    /**
     * 推送时间
     */
    private Date pushtime;

    /**
     * 推送结果状态码
     */
    private String resultcode;

    /**
     * 推送结果消息
     */
    private String resultcontent;

    /**
     * 备注
     */
    private String remark;

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgtitle() {
        return msgtitle;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle == null ? null : msgtitle.trim();
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent == null ? null : msgcontent.trim();
    }

    public Integer getPushtype() {
        return pushtype;
    }

    public void setPushtype(Integer pushtype) {
        this.pushtype = pushtype;
    }

    public String getPushtarget() {
        return pushtarget;
    }

    public void setPushtarget(String pushtarget) {
        this.pushtarget = pushtarget == null ? null : pushtarget.trim();
    }

    public Integer getPushstatus() {
        return pushstatus;
    }

    public void setPushstatus(Integer pushstatus) {
        this.pushstatus = pushstatus;
    }

    public Date getPushtime() {
        return pushtime;
    }

    public void setPushtime(Date pushtime) {
        this.pushtime = pushtime;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode == null ? null : resultcode.trim();
    }

    public String getResultcontent() {
        return resultcontent;
    }

    public void setResultcontent(String resultcontent) {
        this.resultcontent = resultcontent == null ? null : resultcontent.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}