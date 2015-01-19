//
//  AuthViewController.h
//  Glshop
//
//  Created by River on 15-1-7.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  用户认证

#import "BaseViewController.h"
#import "AuthModel.h"

@interface AuthViewController : BaseViewController

@property (nonatomic, strong, readonly) UITableView *tableView;
@property (nonatomic, strong) AuthModel *authModel;

@end
