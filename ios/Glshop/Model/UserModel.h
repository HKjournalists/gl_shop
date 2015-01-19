//
//  UserModel.h
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface UserModel : WXBaseModel

@property (nonatomic, copy) NSString *username;
@property (nonatomic, copy) NSString *cid; // 企业id
@property (nonatomic, copy) NSString *cname;
@property (nonatomic, copy) NSString *phone;
@property (nonatomic, copy) NSString *password;
@property (nonatomic, copy) NSString *clienttype;
@property (nonatomic, copy) NSString *userToken;
@property (nonatomic, copy) NSString *clientid;
@property (nonatomic, strong) NSDictionary *ctype;
@property (nonatomic, strong) NSDictionary *authstatus;
@property (nonatomic, strong) NSDictionary *bailstatus;

@end
