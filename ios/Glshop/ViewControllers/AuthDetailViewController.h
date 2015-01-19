//
//  AuthDetailViewController.h
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@class CopyRightModel;
@interface AuthDetailViewController : BaseViewController

@property (nonatomic, assign) UserType userType;

@property (nonatomic, weak) CopyRightModel *cjCopyModel;

@end
