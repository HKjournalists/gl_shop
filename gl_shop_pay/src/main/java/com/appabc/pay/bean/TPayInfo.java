package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TPayInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -738135578764835580L;

    /**
     * 合作身份者ID，以2088开头由16位纯数字组成的字符串
     */
    private String partnerid;

    /**
     * ,支付机构提供
     */
    private String paykey;

    /**
     * 支付第三方机构提供
     */
    private String gatewayurl;

    /**
     * 支付第三方机构提供，如:及时到账，担保交易
     */
    private String servicename;

    /**
     * 签名类型
     */
    private String signtype;

    /**
     * 卖家在第三方支付机构注册的账号，即收款账号
     */
    private String selleraccount;

    /**
     * 如：支付宝，财付通，银联
     */
    private String payorgname;

    /**
     * 第三方支付机构名称编码
     */
    private String payorgcode;

    /**
     * 图片地址;  图片地址或者LOGO地址
     */
    private String imgurl;

    /**
     * 状态
     */
    private Integer status;

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

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid == null ? null : partnerid.trim();
    }

    public String getPaykey() {
        return paykey;
    }

    public void setPaykey(String paykey) {
        this.paykey = paykey == null ? null : paykey.trim();
    }

    public String getGatewayurl() {
        return gatewayurl;
    }

    public void setGatewayurl(String gatewayurl) {
        this.gatewayurl = gatewayurl == null ? null : gatewayurl.trim();
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename == null ? null : servicename.trim();
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype == null ? null : signtype.trim();
    }

    public String getSelleraccount() {
        return selleraccount;
    }

    public void setSelleraccount(String selleraccount) {
        this.selleraccount = selleraccount == null ? null : selleraccount.trim();
    }

    public String getPayorgname() {
        return payorgname;
    }

    public void setPayorgname(String payorgname) {
        this.payorgname = payorgname == null ? null : payorgname.trim();
    }

    public String getPayorgcode() {
        return payorgcode;
    }

    public void setPayorgcode(String payorgcode) {
        this.payorgcode = payorgcode == null ? null : payorgcode.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}