//
//  TraderStatusModel.h
//  Glshop
//
//  Created by River on 15-1-30.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface TraderStatusModel : WXBaseModel

@property (nonatomic, copy) NSString *oid;
/**
 *@brief 合同操作类型
 */
@property (nonatomic, strong) NSDictionary *type;
/**
 *@brief 合同生命周期 ContractLifeCycle
 */
@property (nonatomic, strong) NSDictionary *orderstatus;

/**
 *@brief 旧的合同生命周期 ContractLifeCycle
 */
@property (nonatomic, strong) NSDictionary *oldstatus;

@property (nonatomic, copy) NSString *remark;

@end
