//
//  UserInstance.h
//  Glshop
//
//  Created by River on 14-11-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UserModel.h"

@interface UserInstance : NSObject

@property (nonatomic, strong) UserModel *user;

+ (id)sharedInstance;

/**
 *@brief 清空用户信息
 */
- (void)destory;

/**
 *@brief 用户是否登入
 */
- (BOOL)login;

@end
