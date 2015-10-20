//
//  EvaluationModel.h
//  Glshop
//
//  Created by River on 15-1-19.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface EvaluationModel : WXBaseModel

/**
 *@brief 信用评分
 */
@property (nonatomic, strong) NSNumber *averageCredit;
/**
 *@brief 满意度评分
 */
@property (nonatomic, strong) NSNumber *averageEvaluation;
/**
 *@brief 交易成功数
 */
@property (nonatomic, strong) NSNumber *transactionSuccessNum;
/**
 *@brief 交易成功率
 */
@property (nonatomic, strong) NSNumber *transactionSuccessRate;

@end
