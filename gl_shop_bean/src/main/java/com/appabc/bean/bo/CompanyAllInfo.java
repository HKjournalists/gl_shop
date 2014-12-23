/**
 *
 */
package com.appabc.bean.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appabc.bean.enums.CompanyInfo.CompanyAuthStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
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
    private CompanyType ctype;

    /**
     * 认证状态(是否认证)
     */
    private CompanyAuthStatus authstatus;

    /**
     * 固定电话
     */
    private String tel;
    
    /**
     * 保证金缴纳状态（是否缴纳足额）
     */
    private CompanyBailStatus bailstatus;
    
    private String authid; // 认证记录ID
    
    private String authimgid; // 认证图片ID
    
    private String address; // 卸货地址
    
    private String areacode; // 卸货地址地区编码（最后一级编码）
    
    private Float deep; // 水深
    
    private Float realdeep; // 实际吃水深度
    
    private EvaluationInfoBean evaluationInfo; // 企业评价信息
    
    private Date createdate; // 创建时间
    
    private List<ViewImgsBean> companyImgList = new ArrayList<ViewImgsBean>() ; // 企业相关照片
    private List<ViewImgsBean> authImgList = new ArrayList<ViewImgsBean>();// 认证图片信息
    private List<ViewImgsBean> addressImgList = new ArrayList<ViewImgsBean>();// 企业卸货地址图片

	public List<ViewImgsBean> getCompanyImgList() {
		return companyImgList;
	}

	public void setCompanyImgList(List<ViewImgsBean> companyImgList) {
		this.companyImgList = companyImgList;
	}

	public List<ViewImgsBean> getAuthImgList() {
		return authImgList;
	}

	public void setAuthImgList(List<ViewImgsBean> authImgList) {
		this.authImgList = authImgList;
	}

	public List<ViewImgsBean> getAddressImgList() {
		return addressImgList;
	}

	public void setAddressImgList(List<ViewImgsBean> addressImgList) {
		this.addressImgList = addressImgList;
	}

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

	public CompanyType getCtype() {
		return ctype;
	}

	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}

	public CompanyAuthStatus getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(CompanyAuthStatus authstatus) {
		this.authstatus = authstatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public CompanyBailStatus getBailstatus() {
		return bailstatus;
	}

	public void setBailstatus(CompanyBailStatus bailstatus) {
		this.bailstatus = bailstatus;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
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

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getAuthimgid() {
		return authimgid;
	}

	public void setAuthimgid(String authimgid) {
		this.authimgid = authimgid;
	}

}
