//
//  AddressViewController.h
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"
#import "PublicInfoModel.h"

@interface AddressViewController : BaseViewController

@property (nonatomic, weak) PublicInfoModel *publicModel;
@property (nonatomic, strong, readonly) UITableView *tableView;

@end
