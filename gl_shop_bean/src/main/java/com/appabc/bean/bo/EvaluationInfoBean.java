/**
 *
 */
package com.appabc.bean.bo;

/**
 * @Description : 企业评价信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月20日 下午1:49:49
 */
public class EvaluationInfoBean {
	
	private Float averageCredit; // 企业平均信用度
    
    private Float averageEvaluation; // 企业服务平均满意度
    
    private Float transactionSuccessRate; // 交易成功率
    
    private int transactionSuccessNum; // 交易成功数

	public Float getAverageCredit() {
		return averageCredit;
	}

	public void setAverageCredit(Float averageCredit) {
		this.averageCredit = averageCredit;
	}

	public Float getAverageEvaluation() {
		return averageEvaluation;
	}

	public void setAverageEvaluation(Float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}

	public Float getTransactionSuccessRate() {
		return transactionSuccessRate;
	}

	public void setTransactionSuccessRate(Float transactionSuccessRate) {
		this.transactionSuccessRate = transactionSuccessRate;
	}

	public int getTransactionSuccessNum() {
		return transactionSuccessNum;
	}

	public void setTransactionSuccessNum(int transactionSuccessNum) {
		this.transactionSuccessNum = transactionSuccessNum;
	}
    
    

}
