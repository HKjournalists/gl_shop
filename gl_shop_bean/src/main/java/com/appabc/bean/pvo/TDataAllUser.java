package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 用户整体数据统计表（T_DATA_ALL_USER）
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月14日 下午5:07:07
 */
public class TDataAllUser extends BaseBean {

	private static final long serialVersionUID = 1L;

    /**
     * 所有注册用户
     */
    private Integer allUserNum;

    /**
     * 所有已认证通过用户
     */
    private Integer authUserNum;

    /**
     * 已缴纳保证金用户数
     */
    private Integer bailUserNum;

    /**
     * 出售类询单数
     */
    private Integer sellGoodsNum;

    /**
     * 购买类询单数
     */
    private Integer buyGoodsNum;

    /**
     * 所有进行中的合同数
     */
    private Integer contractIngNum;

    /**
     * 已结束合同数
     */
    private Integer contractEndNum;

    /**
     * 当天登录用户数
     */
    private Integer loginUserNum;

    /**
     * 
     */
    private Date createDate;

    public Integer getAllUserNum() {
        return allUserNum;
    }

    public void setAllUserNum(Integer allUserNum) {
        this.allUserNum = allUserNum;
    }

    public Integer getAuthUserNum() {
        return authUserNum;
    }

    public void setAuthUserNum(Integer authUserNum) {
        this.authUserNum = authUserNum;
    }

    public Integer getBailUserNum() {
        return bailUserNum;
    }

    public void setBailUserNum(Integer bailUserNum) {
        this.bailUserNum = bailUserNum;
    }

    public Integer getSellGoodsNum() {
        return sellGoodsNum;
    }

    public void setSellGoodsNum(Integer sellGoodsNum) {
        this.sellGoodsNum = sellGoodsNum;
    }

    public Integer getBuyGoodsNum() {
        return buyGoodsNum;
    }

    public void setBuyGoodsNum(Integer buyGoodsNum) {
        this.buyGoodsNum = buyGoodsNum;
    }

    public Integer getContractIngNum() {
        return contractIngNum;
    }

    public void setContractIngNum(Integer contractIngNum) {
        this.contractIngNum = contractIngNum;
    }

    public Integer getContractEndNum() {
        return contractEndNum;
    }

    public void setContractEndNum(Integer contractEndNum) {
        this.contractEndNum = contractEndNum;
    }

    public Integer getLoginUserNum() {
        return loginUserNum;
    }

    public void setLoginUserNum(Integer loginUserNum) {
        this.loginUserNum = loginUserNum;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}