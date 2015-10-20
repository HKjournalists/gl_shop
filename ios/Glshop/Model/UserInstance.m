//
//  UserInstance.m
//  Glshop
//
//  Created by River on 14-11-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UserInstance.h"

@implementation UserInstance

+ (id)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });;
    return sharedInstance;
}

- (void)updateUserInfo:(NSDictionary *)newUserInfoDic {
    self.user.authstatus = newUserInfoDic[@"authstatus"];
    self.user.bailstatus = newUserInfoDic[@"bailstatus"];
    self.user.ctype = newUserInfoDic[@"ctype"];
    NSString *cname = [newUserInfoDic objectForKey:@"cname"];
    if (cname && cname.length) {
        self.user.cname = cname;
    }
}

- (void)destory {
    _user.cid = nil;
    _user.username = nil;
    _user.phone = nil;
    _user.clientid = nil;
    _user = nil;
}

- (BOOL)login {
    if (_user.username.length > 0 && _user.userToken.length > 0) {
        return YES;
    }
    return NO;
}

- (BOOL)isBeAuthed {
    if ([_user.authstatus[DataValueKey] integerValue] == 1) {
        return YES;
    }
    return NO;
}

- (BOOL)isPaymentMargin {
    if ([_user.bailstatus[DataValueKey] integerValue] == 1) {
        return YES;
    }
    return NO;
}

- (UserType)userType {
    NSInteger t = [_user.ctype[DataValueKey] integerValue];
    if (![_user.ctype[DataValueKey] length]) {
        return NSNotFound;
    }else {
        if (t == 0) {
            return user_company;
        }else if (t == 1) {
            return user_ship;
        }else if (t == 2) {
            return user_personal;
        }
        return NSNotFound;
    }
    
}


@end
