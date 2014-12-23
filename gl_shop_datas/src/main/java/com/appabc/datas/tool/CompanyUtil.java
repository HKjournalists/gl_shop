/**
 *
 */
package com.appabc.datas.tool;

import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.utils.SystemParamsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月19日 下午2:55:35
 */
@Repository
public class CompanyUtil {

	@Autowired
	private SystemParamsManager spm;

	/**
	 * 根据企业类型，判断是否已足额缴纳保证金; 船舶需要根据吨位(tonnag)进行判断，未认证的船舶
	 * @param companyType 企业类型
	 * @param tonnage 吨位(企业类型为船舶时用)
	 * @param money 保证金额度
	 * @return
	 */
	public CompanyBailStatus checkCashDeposit(CompanyType companyType, int tonnage, float money) {

		if(money <= 0){
			return CompanyBailStatus.BAIL_STATUS_NO;
		}else if(companyType == null){ // 未认证用户不知道企业类型，按船舶最低标准计算
			if(money >= this.spm.getInt(SystemConstant.BOND_SHIP_0_1000)){
				return CompanyBailStatus.BAIL_STATUS_YES;
			}else{
				return CompanyBailStatus.BAIL_STATUS_NO;
			}
		}else{

			if(companyType.equals(CompanyType.COMPANY_TYPE_ENTERPRISE) && money >= this.spm.getFloat(SystemConstant.BOND_ENTERPRISE)){ // 企业
				return CompanyBailStatus.BAIL_STATUS_YES;
			}else if(companyType.equals(CompanyType.COMPANY_TYPE_PERSONAL) && money >= this.spm.getFloat(SystemConstant.BOND_PERSONAL)){ // 个人
				return CompanyBailStatus.BAIL_STATUS_YES;
			}else if(companyType.equals(CompanyType.COMPANY_TYPE_SHIP)){ // 船舶
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
			}
		}

		return CompanyBailStatus.BAIL_STATUS_NO;
	}


}
