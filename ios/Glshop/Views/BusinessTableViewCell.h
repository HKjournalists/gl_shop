//
//  BusinessTableViewCell.h
//  Glshop
//
//  Created by River on 14-11-20.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OrderModel.h"

typedef NS_ENUM(NSInteger, BusinessLogoType)
{
    LogoSaller,    // 出售
    LogoPurchase, // 购买
};

@interface BusinessTableViewCell : UITableViewCell

@property (nonatomic, assign) BusinessLogoType logoType;
@property (nonatomic, strong) OrderModel *orderModel;

@end
