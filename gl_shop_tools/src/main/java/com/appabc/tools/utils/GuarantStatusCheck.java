/**
 *
 */
package com.appabc.tools.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.dao.user.IToolCompanyDao;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月12日 下午4:12:37
 */
@Repository(value="GuarantStatusCheck")
public class GuarantStatusCheck {
	
	@Autowired
	IToolCompanyDao toolCompanyDao;
	@Autowired
	private SystemParamsManager spm;
	
	/**
	 * 长江电商平台所有的保证金额度都调整为3000f
	 * @param cid
	 * @return float
	 */
	public float getGuarantStatus(String cid){
		return 3000f;
	}
	
	/**
	 * 获取企业应该缴纳的保证金额度,未知企业类型按船舶最低吨位额度缴纳
	 * @param cid
	 * @return -1为异常数据
	 */
	@Deprecated
	public float getGuarantStatusV2(String cid) {
		if(StringUtils.isNotEmpty(cid)){
			try {
				TCompanyInfo ci = toolCompanyDao.query(cid);
				if(ci != null && ci.getCtype() != null){
					if(CompanyType.COMPANY_TYPE_ENTERPRISE.equals(ci.getCtype())){ // 企业
						return this.spm.getFloat(SystemConstant.BOND_ENTERPRISE);
					}else if(CompanyType.COMPANY_TYPE_PERSONAL.equals(ci.getCtype())){ // 个人
						return this.spm.getFloat(SystemConstant.BOND_PERSONAL);
					}else if(CompanyType.COMPANY_TYPE_SHIP.equals(ci.getCtype())){ // 船舶
						
						TCompanyShipping shipping = this.toolCompanyDao.queryShippingByCid(cid);
						if(shipping != null && shipping.getStotal() != null) {
							float total = shipping.getStotal().floatValue();
							if(total <= 1000 ){
								return this.spm.getFloat(SystemConstant.BOND_SHIP_0_1000);
							}else if(total <= 5000 ){
								return this.spm.getFloat(SystemConstant.BOND_SHIP_1001_5000);
							}else if(total <= 10000 ){
								return this.spm.getFloat(SystemConstant.BOND_SHIP_5001_10000);
							}else if(total <= 15000 ){
								return this.spm.getFloat(SystemConstant.BOND_SHIP_10001_15000);
							}
						}
						
						
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		
		return this.spm.getFloat(SystemConstant.BOND_SHIP_0_1000); // 未知企业类型按船舶最低吨位额度缴纳
	}
	
	/**
	 * 长江电商平台所有的保证金额度都调整为3000f
	 * @param cid
	 * @param money
	 * @return
	 */
	public CompanyBailStatus checkCashDeposit(String cid, float money) {
		if(money >= 3000){
			return CompanyBailStatus.BAIL_STATUS_YES;
		}

		return CompanyBailStatus.BAIL_STATUS_NO;
	}
	
	/**
	 * 根据企业类型，判断是否已足额缴纳保证金; 船舶需要根据吨位(tonnag)进行判断，未认证的船舶
	 * @param companyType 企业类型
	 * @param tonnage 吨位(企业类型为船舶时用)
	 * @param money 已缴纳保证金额度
	 * @return
	 */
	@Deprecated
	public CompanyBailStatus checkCashDepositV2(String cid, float money) {
		if(StringUtils.isNotEmpty(cid)){
			TCompanyInfo ci = toolCompanyDao.query(cid);
			if(ci == null) return CompanyBailStatus.BAIL_STATUS_NO;
			CompanyType companyType = ci.getCtype();
			if(money <= 0){
				return CompanyBailStatus.BAIL_STATUS_NO;
			}else if(companyType == null){ // 未认证用户不知道企业类型，按船舶最低标准计算
				if(money >= this.spm.getInt(SystemConstant.BOND_SHIP_0_1000)){
					return CompanyBailStatus.BAIL_STATUS_YES;
				}else{
					return CompanyBailStatus.BAIL_STATUS_NO;
				}
			}else{ // 已认证用户(已申请了认证或认证通过的用户)
				
				if(companyType.equals(CompanyType.COMPANY_TYPE_ENTERPRISE) && money >= this.spm.getFloat(SystemConstant.BOND_ENTERPRISE)){ // 企业
					return CompanyBailStatus.BAIL_STATUS_YES;
				}else if(companyType.equals(CompanyType.COMPANY_TYPE_PERSONAL) && money >= this.spm.getFloat(SystemConstant.BOND_PERSONAL)){ // 个人
					return CompanyBailStatus.BAIL_STATUS_YES;
				}else if(companyType.equals(CompanyType.COMPANY_TYPE_SHIP)){ // 船舶
					TCompanyShipping shipping = this.toolCompanyDao.queryShippingByCid(cid);
					if(shipping != null && shipping.getStotal() != null) {
						float tonnage = shipping.getStotal().floatValue();
						if(tonnage <= 1000){
							if(money >= this.spm.getFloat(SystemConstant.BOND_SHIP_0_1000)){
								return CompanyBailStatus.BAIL_STATUS_YES;
							}else{
								return CompanyBailStatus.BAIL_STATUS_NO;
							}
						}else if(tonnage <= 5000){
							if(money >= this.spm.getFloat(SystemConstant.BOND_SHIP_1001_5000)){
								return CompanyBailStatus.BAIL_STATUS_YES;
							}else{
								return CompanyBailStatus.BAIL_STATUS_NO;
							}
						}else if(tonnage <= 10000){
							if(money >= this.spm.getFloat(SystemConstant.BOND_SHIP_5001_10000)){
								return CompanyBailStatus.BAIL_STATUS_YES;
							}else{
								return CompanyBailStatus.BAIL_STATUS_NO;
							}
						}else if(tonnage > 10000){
							if(money >= this.spm.getFloat(SystemConstant.BOND_SHIP_10001_15000)){
								return CompanyBailStatus.BAIL_STATUS_YES;
							}else{
								return CompanyBailStatus.BAIL_STATUS_NO;
							}
						}
					} else { // 已知道企业类型and未查到已认证信息，认证中的船舶，按最小吨位计算
						if(money >= this.spm.getFloat(SystemConstant.BOND_SHIP_0_1000)){
							return CompanyBailStatus.BAIL_STATUS_YES;
						}else{
							return CompanyBailStatus.BAIL_STATUS_NO;
						}
					}
				}
			}
		}

		return CompanyBailStatus.BAIL_STATUS_NO;
	}



}
