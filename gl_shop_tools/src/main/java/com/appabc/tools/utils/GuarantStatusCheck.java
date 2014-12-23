/**
 *
 */
package com.appabc.tools.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	 * 获取企业应该缴纳的保证金额度,未知企业类型按船舶最低吨位额度缴纳
	 * @param cid
	 * @return -1为异常数据
	 */
	public float getGuarantStatus(String cid) {
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

}
