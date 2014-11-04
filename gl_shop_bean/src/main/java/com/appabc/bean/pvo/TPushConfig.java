package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

/**
 * @Description : XMPP配置
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午4:34:53
 */
public class TPushConfig extends BaseBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -551492098697348766L;

	/**
     * AppID
     */
    private String appid;

    /**
     * AppKey
     */
    private String appkey;

    /**
     * AppSecret
     */
    private String appsecret;

    /**
     * MasterSecret
     */
    private String mastersecret;

    /**
     * url
     */
    private String url;

    /**
     * 状态(启用、停用)
     */
    private Integer status;
    
    /**
     * 服务器端口
     */
    private Integer port;

    /**
     * 类型(ANDROID,IOS)
     */
    private String type;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updatetiime;
    
    /**
     * 证书路径
     */
    private String certificatepath;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey == null ? null : appkey.trim();
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    public String getMastersecret() {
        return mastersecret;
    }

    public void setMastersecret(String mastersecret) {
        this.mastersecret = mastersecret == null ? null : mastersecret.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetiime() {
        return updatetiime;
    }

    public void setUpdatetiime(Date updatetiime) {
        this.updatetiime = updatetiime;
    }

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getCertificatepath() {
		return certificatepath;
	}

	public void setCertificatepath(String certificatepath) {
		this.certificatepath = certificatepath;
	}
}