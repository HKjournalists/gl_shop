package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.common.base.bean.BaseBean;

public class TSystemMessage extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2229648798709014992L;

    /**
     * 企业编号
     */
    private String qyid;

    /**
     * 消息内容
     * 参考 SystemMsgInfo.MsgContent
     */
    private String content; 

    /**
     * 消息类型
     */
    private MsgType type;

    /**
     * 业务ID
     */
    private String businessid;

    /**
     * 业务类型
     */
    private MsgBusinessType businesstype;

    /**
     * 消息状态:已读，未读
     */
    private MsgStatus status;

    /**
     * 消息创建时间
     */
    private Date createtime;

    /**
     * 读取时间
     */
    private Date readtime;

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }

	/**  
	 * type  
	 *  
	 * @return  the type  
	 * @since   1.0.0  
	*/  
	
	public MsgType getType() {
		return type;
	}

	/**  
	 * @param type the type to set  
	 */
	public void setType(MsgType type) {
		this.type = type;
	}

	/**  
	 * businesstype  
	 *  
	 * @return  the businesstype  
	 * @since   1.0.0  
	*/  
	
	public MsgBusinessType getBusinesstype() {
		return businesstype;
	}

	/**  
	 * @param businesstype the businesstype to set  
	 */
	public void setBusinesstype(MsgBusinessType businesstype) {
		this.businesstype = businesstype;
	}

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public MsgStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(MsgStatus status) {
		this.status = status;
	}
}