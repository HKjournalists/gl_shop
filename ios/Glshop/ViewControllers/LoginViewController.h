//
//  LoginViewController.h
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@interface LoginViewController : BaseViewController

/**
 *@brief 登录成功后，是否跳到认证页面
 *@discussion 用户注册成功后可选择跳到认证页面进行认证
 */
@property (nonatomic, assign) BOOL skipToAuth;

@end
