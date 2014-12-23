//
//  DetailStandardViewController.h
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  货物详细规格

#import "BaseViewController.h"
#import "PublicInfoModel.h"

@interface DetailStandardViewController : BaseViewController

@property (nonatomic, copy) NSString *productName;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, weak) PublicInfoModel *publicModel;

@end
