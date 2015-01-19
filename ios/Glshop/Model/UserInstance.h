//
//  UserInstance.h
//  Glshop
//
//  Created by River on 14-11-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UserModel.h"

typedef NS_ENUM(NSInteger, UserType) {
    /**
     *@brief 企业用户
     */
    user_company,
    /**
     *@brief 船舶用户
     */
    user_ship,
    /**
     *@brief 个人用户
     */
    user_personal,
    
    unKnowType,
};

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

/**
 *@brief 用户身份。（企业、船舶、个人）
 */
@property (nonatomic, assign, readonly) UserType userType;

@end
