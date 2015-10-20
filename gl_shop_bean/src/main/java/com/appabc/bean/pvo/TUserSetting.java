package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

/**
 * @Description : 用户设置信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月7日 下午4:38:39
 */
public class TUserSetting extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3692509385899889091L;

	/**
     * 上次认证提醒时间
     */
    private Date authremindtime;

    /**
     * 企业ID
     */
    private String cid;

    public Date getAuthremindtime() {
        return authremindtime;
    }

    public void setAuthremindtime(Date authremindtime) {
        this.authremindtime = authremindtime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }
}