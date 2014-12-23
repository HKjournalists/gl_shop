package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.common.base.bean.BaseBean;

public class TPayDetail extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5905145013433218300L;

    /**
     * 支付账户编号
     */
    private String payaccountid;

    /**
     * 支付流水编号
     */
    private String payflowid;

    /**
     * 支付流水号
     */
    private String payorgnum;

    /**
     * 支付交易状态
     */
    private String paytradestatus;

    /**
     * 订单编号【业务系统编号】
     */
    private String buztradenum;

    /**
     * 支付操作类型，支付请求，支付返回
     */
    private Integer payoperatetype;

    /**
     * 业务系统通知地址
     */
    private String buznotifyurl;

    /**
     * 交易时间
     */
    private Date tradetime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 请求参数内容
     */
    private String paramscontent;

    public String getPayaccountid() {
        return payaccountid;
    }

    public void setPayaccountid(String payaccountid) {
        this.payaccountid = payaccountid == null ? null : payaccountid.trim();
    }

    public String getPayflowid() {
        return payflowid;
    }

    public void setPayflowid(String payflowid) {
        this.payflowid = payflowid == null ? null : payflowid.trim();
    }

    public String getPayorgnum() {
        return payorgnum;
    }

    public void setPayorgnum(String payorgnum) {
        this.payorgnum = payorgnum == null ? null : payorgnum.trim();
    }

    public String getPaytradestatus() {
        return paytradestatus;
    }

    public void setPaytradestatus(String paytradestatus) {
        this.paytradestatus = paytradestatus == null ? null : paytradestatus.trim();
    }

    public String getBuztradenum() {
        return buztradenum;
    }

    public void setBuztradenum(String buztradenum) {
        this.buztradenum = buztradenum == null ? null : buztradenum.trim();
    }

    public Integer getPayoperatetype() {
        return payoperatetype;
    }

    public void setPayoperatetype(Integer payoperatetype) {
        this.payoperatetype = payoperatetype;
    }

    public String getBuznotifyurl() {
        return buznotifyurl;
    }

    public void setBuznotifyurl(String buznotifyurl) {
        this.buznotifyurl = buznotifyurl == null ? null : buznotifyurl.trim();
    }

    public Date getTradetime() {
        return tradetime;
    }

    public void setTradetime(Date tradetime) {
        this.tradetime = tradetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getParamscontent() {
        return paramscontent;
    }

    public void setParamscontent(String paramscontent) {
        this.paramscontent = paramscontent == null ? null : paramscontent.trim();
    }
}