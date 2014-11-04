package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TBankInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6910107551430033068L;

    /**
     * 银行卡号
     */
    private String bankcardnum;

    /**
     * 持卡人姓名
     */
    private String carduser;

    /**
     * 招商、建行的类型
     */
    private String blanktype;

    /**
     * 开户行
     */
    private String bankname;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date upatetime;

    public String getBankcardnum() {
        return bankcardnum;
    }

    public void setBankcardnum(String bankcardnum) {
        this.bankcardnum = bankcardnum == null ? null : bankcardnum.trim();
    }

    public String getCarduser() {
        return carduser;
    }

    public void setCarduser(String carduser) {
        this.carduser = carduser == null ? null : carduser.trim();
    }

    public String getBlanktype() {
        return blanktype;
    }

    public void setBlanktype(String blanktype) {
        this.blanktype = blanktype == null ? null : blanktype.trim();
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpatetime() {
        return upatetime;
    }

    public void setUpatetime(Date upatetime) {
        this.upatetime = upatetime;
    }
}