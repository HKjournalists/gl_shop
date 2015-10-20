//
//  PayTradeViewController.h
//  Glshop
//
//  Created by River on 15-1-29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  支付交易货款

#import "BaseViewController.h"

@interface PayTradeViewController : BaseViewController

@property (nonatomic, copy) NSString *contractId;
@property (nonatomic, strong) NSNumber *payAmount;

@end
