package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.ClientEnum.ChannelType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.common.base.bean.BaseBean;

public class TClient extends BaseBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1637483381397623758L;

	/**
     * 客户端ID
     */
    private String clientid;
    
    /**
     * 客户端类型
     */
    private ClientTypeEnum clienttype;

    /**
     * 用户名
     */
    private String username;

    /**
     * 图标小红圈的数值
     */
    private Integer badge;

    /**
     * 更新时间
     */
    private Date updatetime;
    
    /**
     * 下载渠道(网站、APPSTORE)
     */
    private ChannelType channeltype;
    
	public ChannelType getChanneltype() {
		return channeltype;
	}

	public void setChanneltype(ChannelType channeltype) {
		this.channeltype = channeltype;
	}

	public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid == null ? null : clientid.trim();
    }

    public ClientTypeEnum getClienttype() {
		return clienttype;
	}

	public void setClienttype(ClientTypeEnum clienttype) {
		this.clienttype = clienttype;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}