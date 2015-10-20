//
//  ChargeViewController.h
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  充值

typedef NS_ENUM(NSUInteger, ChargeType) {
    ChargeTypeUnknow,
    ChargeTypeMargin,
    ChargeTypePayment,
};

#import "BaseViewController.h"

@interface ChargeViewController : BaseViewController

@property (nonatomic, assign) double chartAmount;

@property (nonatomic, assign) ChargeType chargeType;

@end
