//
//  ContractDetailModel.h
//  Glshop
//
//  Created by River on 15-1-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ContractModel.h"
#import "TraderStatusModel.h"

@interface ContractDetailModel : ContractModel

@property (nonatomic, strong) TraderStatusModel *buyerStatus;
@property (nonatomic, strong) TraderStatusModel *sellerStatus;

@end
