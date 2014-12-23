//
//  ChoseTypeViewController.h
//  Glshop
//
//  Created by River on 14-12-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"
#import "PublicInfoModel.h"

typedef NS_ENUM(NSInteger, ProductType) {
    sendsSubType,
    sendsGroundsonType,
    stoneType,
};

@interface ChoseTypeViewController : BaseViewController

@property (nonatomic, assign) ProductType productType;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, weak) PublicInfoModel *publicModel;

@end
