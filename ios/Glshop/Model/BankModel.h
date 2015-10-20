//
//  BankModel.h
//  Glshop
//
//  Created by River on 15-3-31.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface BankModel : WXBaseModel

/**
 *@brief 银行编号
 */
@property (nonatomic, copy) NSString *val;
/**
 *@brief 排序号
 */
@property (nonatomic, copy) NSString *orderno;
/**
 *@brief 银行名称
 */
@property (nonatomic, copy) NSString *name;


@end
