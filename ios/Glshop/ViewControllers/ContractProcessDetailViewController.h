//
//  ContractProcessDetailViewController.h
//  Glshop
//
//  Created by River on 15-1-29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  进行中合同的详细页面

#import "BaseViewController.h"
#import "ContractEnum.h"
#import "ContractModel.h"

@interface ContractProcessDetailViewController : BaseViewController

/**
 *@brief 合同id
 */
@property (nonatomic, copy) NSString *contractId;

@property (nonatomic, assign) ContractBigStatus contractBigStatus;

@property (nonatomic, strong,readonly) ContractModel *contractModel;

@end
