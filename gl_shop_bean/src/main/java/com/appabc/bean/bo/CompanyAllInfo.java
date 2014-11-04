/**
 *
 */
package com.appabc.bean.bo;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 企业认证资料
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月28日 下午4:12:47
 */
public class CompanyAllInfo extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4085222334594221317L;

	/**
     * 企业名称
     */
    private String cname;

    /**
     * 企业介绍
     */
    private String mark;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系人电话
     */
    private String cphone;

    /**
     * 企业类型（区分企业、船舶、个人）
     */
    private String ctype;

    /**
     * 认证状态(是否认证)
     */
    private String authstatus;

    /**
     * 固定电话
     */
    private String tel;
    
    /**
     * 保证金缴纳状态（是否缴纳足额）
     */
    private String bailstatus;
    
    private Integer imgid; // 认证图片ID
    
    private String authImgUrl; //  认证图片显示URL
    
    private String companyImgUrls; // 企业相关照片显示路径，多个URL用逗号间隔,例: url1,url2,url3
    
    private String addressImgUrls; //  企业卸货地址图片显示路径，多个URL用逗号间隔,例: url1,url2,url3
    
    private String address; // 卸货地址
    
    private String areacode; // 卸货地址地区编码（最后一级编码）
    
    private Float deep; // 水深
    
    private Float realdeep; // 实际吃水深度
    
    private EvaluationInfoBean evaluationInfo; // 企业评价信息

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCphone() {
		return cphone;
	}

	public void setCphone(String cphone) {
		this.cphone = cphone;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBailstatus() {
		return bailstatus;
	}

	public void setBailstatus(String bailstatus) {
		this.bailstatus = bailstatus;
	}

	public Integer getImgid() {
		return imgid;
	}

	public void setImgid(Integer imgid) {
		this.imgid = imgid;
	}

	public String getAuthImgUrl() {
		return authImgUrl;
	}

	public void setAuthImgUrl(String authImgUrl) {
		this.authImgUrl = authImgUrl;
	}

	public String getCompanyImgUrls() {
		return companyImgUrls;
	}

	public void setCompanyImgUrls(String companyImgUrls) {
		this.companyImgUrls = companyImgUrls;
	}

	public String getAddressImgUrls() {
		return addressImgUrls;
	}

	public void setAddressImgUrls(String addressImgUrls) {
		this.addressImgUrls = addressImgUrls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public Float getDeep() {
		return deep;
	}

	public void setDeep(Float deep) {
		this.deep = deep;
	}

	public Float getRealdeep() {
		return realdeep;
	}

	public void setRealdeep(Float realdeep) {
		this.realdeep = realdeep;
	}

	public EvaluationInfoBean getEvaluationInfo() {
		return evaluationInfo;
	}

	public void setEvaluationInfo(EvaluationInfoBean evaluationInfo) {
		this.evaluationInfo = evaluationInfo;
	}

    

}
