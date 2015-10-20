//
//  MainViewController.h
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@interface MainViewController : BaseViewController

- (void)refrushMessageBox:(NSInteger)unReadCount;

- (void)alertToAuth;

/**
 *@brief 更新用户token，保持token有效
 */
- (void)updateToken;

/**
 *@brief 用户收到推送认证消息或者保证金变更消息后，更新用户信息。
 */
- (void)updateUserInfo;

/**
 *@brief 将应用程序角标数字消除为0
 */
- (void)seqlinBage;

@end
