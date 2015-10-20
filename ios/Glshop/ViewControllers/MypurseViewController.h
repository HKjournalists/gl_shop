//
//  MypurseViewController.h
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"
#import "MyPurseInfoModel.h"

@interface MypurseViewController : BaseViewController

@property (nonatomic, strong, readonly) MyPurseInfoModel *currentPurse;
@property (nonatomic, strong, readonly) UISegmentedControl *segment;

@end
