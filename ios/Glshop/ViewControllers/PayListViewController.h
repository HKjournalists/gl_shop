//
//  PayListViewController.h
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, PayListType) {
    /**
     *@brief 保证金
     */
    pay_margin,
    /**
     *@brief 货款账户
     */
    pay_payment,
};

@interface PayListViewController : BaseViewController

@property (nonatomic, assign) PayListType listType;

@end
